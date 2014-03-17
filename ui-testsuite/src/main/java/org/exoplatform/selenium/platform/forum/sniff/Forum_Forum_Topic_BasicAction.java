package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info; 
import org.exoplatform.selenium.platform.ManageAccount;
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
 * @author thuntn
 * @date 11 Sep 2013
 */

public class Forum_Forum_Topic_BasicAction extends ForumBase {
	ManageAccount magAc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;
	ForumManagePost mngPost;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		mngCat = new ForumManageCategory(driver);
		mngFru = new ForumManageForum(driver);
		mngTopic = new ForumManageTopic(driver);
		mngPost = new ForumManagePost(driver);

		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**Create new Topic
	 * CaseID: 74749
	 * Create new category
	 * Create new Forum
	 * Create new Topic 
	 */
	@Test
	public void test01_CreateTopic() {
		String titleCat = "Category 01";
		String titleForum = "Forum 01";
		String titleTop = "Topic 01";

		info("Create a topic");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 

		//Check if topic is created in forum
		waitForAndGetElement(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));

		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);

	}
	/** Delete topic
	 * caseID: 74753
	 */
	@Test
	public void test02_DeleteTopic() {
		String titleCat = "Category 02";
		String titleForum = "Forum 02";
		String titleTop = "Topic 02";

		info("Delete topic");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		waitForAndGetElement(mngPost.ELEMENT_POST_QUICK_BUTTON);
		mngTopic.deleteTopic(titleTop);

		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}

	//FQA-1342: remove LockUnlockTopic

	/** Move a topic
	 * caseID: 71197
	 */
	@Test
	public void test04_MoveTopic() {
		String titleCat = "Category 04";
		String titleForum = "Forum 04";
		String titleTop = "Topic 04";
		String forum2 = "Forum 04 2";
		String[] addForum2 = {forum2, "1",null,null,forum2};
		String[] permission = {};

		info("Move a topic");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(By.linkText(titleCat));

		mngFru.addForum(titleCat, addForum2, true, "", "", false,0, permission);

		//Move topic
		click(By.linkText(titleCat));
		click(By.linkText(titleForum));
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngTopic.moveTopic(titleTop, forum2); 

		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}
	
	/** Update topic title, 
	 * caseID: 74750
	 */
	@Test
	public void test05_UpdateTopicTitle() {
		String titleCat = "Category 05";
		String titleForum = "Forum 05";
		String titleTop = "Topic 05";
		String newTopic = "New topic 05";
		String[] userGroup = {};

		info("Update topic title");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngTopic.editTopic(newTopic, titleTop, "",  0, userGroup,false,false,false); 
		waitForAndGetElement(mngTopic.ELEMENT_BREADCRUMB_TOPIC.replace("${forum}", titleForum).replace("${topic}", newTopic));

		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}
	
	/** Update topic content, 
	 * caseID: 74751
	 */
	@Test
	public void test06_UpdateTopicContent() {
		String titleCat = "Category 06";
		String titleForum = "Forum 06";
		String titleTop = "Topic 06";
		String newDesc = "New topic 06";
		String[] userGroup = {};

		info("Update topic title");
		//create category, forum, topic
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		mngTopic.editTopic(titleTop, newDesc, "",  0, userGroup,false,false,false); 
		waitForTextPresent(newDesc);

		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}

}
