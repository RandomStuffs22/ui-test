package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Thuntn
 * @date 05 Sep 2013
 */
public class Forum_Forum_PublishActivity_TopicActivity extends ForumBase{
	
	ManageAccount magAc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;
	ForumManagePost mngPost;
	HomePageActivity hpgAct;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		mngFru = new ForumManageForum(driver,this.plfVersion);
		mngCat = new ForumManageCategory(driver);
		mngPost = new ForumManagePost(driver,this.plfVersion);
		mngTopic = new ForumManageTopic(driver,this.plfVersion);
		hpgAct = new HomePageActivity(driver);
		navTool = new NavigationToolbar(driver);
		
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**
	 * CaseID: 75275
	 * Check topic AS after Move a topic
	 */
	@Test
	public void test01_CheckTopicASafterMoveTopic(){
		String titleCat = "Category 01";
		String titleForum = "Forum 01";
		String titleTop = "Topic 01";
		String forum2 = "Forum 02";
		String[] addForum2 = {forum2, "1",null,null,forum2};
		String[] permission = {};
		
		info("Move a topic");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop);
		click(By.linkText(titleCat));
		waitForElementNotPresent(ELEMENT_BREAD_FORUM.replace("${forum}", titleForum));
		
		//Create a forum - destination
		mngFru.addForum(titleCat, addForum2, true, "", "", false,0, permission);
		
		//Move topic
		click(By.linkText(titleCat));
		click(By.linkText(titleForum));
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		waitForAndGetElement(mngPost.ELEMENT_POST_REPLY_BUTTON);
		mngTopic.moveTopic(titleTop, forum2);
		
		//Check activity
		navTool.goToHomePage();
		hpgAct.checkCommentAfterMoveTopic(titleTop, titleCat + ">" + forum2);
		
		//Delete data
		goToForums();
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);	
	}
	
	/**
	 * CaseID: 75276
	 * Check topic activity after create topic
	 */
	@Test
	public void test02_CheckTopicASafterCreateTopic(){
		String titleCat = "Category 02";
		String titleForum = "Forum 02";
		String titleTop = "Topic 02";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		String reply = "Reply on this topic";
		
		info("Create new Topic");
//		create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngPost.postReply(reply, reply, "", "", "");
		
		mngTopic.voteTopic(1);
			
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(titleTop));
		hpgAct.checkNumberOfLineOfContent(getText(hpgAct.ELEMENT_FORUM_ACT_CONTENT.replace("${title}", titleTop)), descTop);
		hpgAct.checkRateTopic(titleTop, 1.0);
		hpgAct.checkReplyForum(titleTop, reply);
		
		//Delete data
		goToForums();
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);		
	}
	
	/**
	 * CaseID: 75277
	 * Check update topic activity after Update topic title
	 */
	@Test
	public void test03_CheckTopicASafterUpdateTopicTitle(){
		String titleCat = "Category 03";
		String titleForum = "Forum 03";
		String titleTop = "Topic 03";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		String newTopic = "New Topic 03";
		String[] userGroup ={};
		
		info("Update topic title");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngTopic.editTopic(newTopic, "", "",  0, userGroup,false,false,false);
			
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(newTopic));
		hpgAct.checkTitleAfterEditing(titleTop,newTopic);
		
		//Delete data
		goToForums();
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);		
	}
	
	/**
	 * CaseID: 75278
	 * Check update Topic activity after Update topic content
	 */
	@Test
	public void test04_CheckTopicASafterUpdateTopicContent(){
		String titleCat = "Category 04";
		String titleForum = "Forum 04";
		String titleTop = "Topic 04";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		String newDesc = "New<br>";
		String[] userGroup ={};
		
		info("Update topic title");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngTopic.editTopic(titleTop, newDesc, "",  0, userGroup,false,false,false);
			
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(titleTop));
		hpgAct.checkUpdateTopic(titleTop, newDesc + descTop);
		
		//Delete data
		goToForums();
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);	
	}
	
	/**
	 * CaseID: 75279
	 * Check topic activity after Lock/Unlock a topic
	 */
	@Test
	public void test05_CheckTopicASafterLockUnlockTopic(){
		String titleCat = "Category 05";
		String titleForum = "Forum 05";
		String titleTop = "Topic 05";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		
		info("Lock/Unlock a topic");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		//Lock a topic
		mngTopic.actionOnTopic(1);
		
		//Check if topic AS is updated or not after lock topic
		navTool.goToHomePage();
		hpgAct.checkLockTopic(titleTop);
		
		//Unlock a topic
		goToForums();
		mngTopic.actionOnTopic(2);
		
		//Check if topic AS is updated or not after unlock topic
		navTool.goToHomePage();
		hpgAct.checkUnlockTopic(titleTop);
		
		//Delete data
		goToForums();
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);	
	}
	
	/**
	 * CaseID: 75280
	 * Check topic activity after Delete topic
	 */
	@Test
	public void test06_CheckTopicASafterDeleteTopic(){
		String titleCat = "Category 06";
		String titleForum = "Forum 06";
		String titleTop = "Topic 06";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		
		info("Delete topic");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		
		navTool.goToHomePage();
		waitForAndGetElement(By.linkText(titleTop));
		
		goToForums();
		mngTopic.deleteTopic(titleTop);
		
		navTool.goToHomePage();
		assert waitForAndGetElement(By.linkText(titleTop),5000,0) == null;
		
		//Delete data
		goToForums();
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);	
	}
	
	/**
	 * CaseID: 75286
	 * Jump into Reply form by clicking on "Reply" action
	 */
	@Test
	public void test07_OpenReplyFormByClickReplyOnActivity(){
		String titleCat = "Category 07";
		String titleForum = "Forum 07";
		String titleTop = "Topic 07";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		
		info("Jump into Reply form by clicking on Reply action");
		
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);

		//Check activity
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(titleTop));
		
		hpgAct.openReplyFormFromActivity(titleTop);
		
		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);		
	}
	
	/**
	 * CaseID: 75287
	 * Jump into last Reply of Topic
	 */
	@Test
	public void test08_OpenLastReplyOfTopic(){
		String titleCat = "Category 08";
		String titleForum = "Forum 08";
		String titleTop = "Topic 08";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		String reply1 = "Reply to this topic 1";
		String reply2 = "Reply to this topic 2";
		
		info("Jump into last Reply of Topic");
		
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngPost.postReply(reply1, reply1, "", "", "");
		mngPost.postReply(reply2, reply2, "", "", "");
		
		//Check activity
		navTool.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(titleTop));
		
		hpgAct.openLastReplyFromActivity(titleTop,reply2);
		
		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);	
	}
	
	/**
	 * CaseID: 75288
	 * Jump to related reply
	 */
	@Test
	public void test09_OpenRelatedReply(){
		String titleCat = "Category 09";
		String titleForum = "Forum 09";
		String titleTop = "Topic 09";
		String descTop = "line1<br>line2<br>line3<br>line4<br>line5";
		String reply = "Reply to this topic 1";
		
		info("Jump to related reply");
		
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,descTop);
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngPost.postReply(reply, reply, "", "", "");
		
		//Check activity
		navTool.goToHomePage();
		
		if(waitForAndGetElement(By.linkText(titleTop),DEFAULT_TIMEOUT,0) == null)
			driver.navigate().refresh();
		
		hpgAct.viewReplyFromActivity(titleTop, reply);
		
		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);		
	}
}
