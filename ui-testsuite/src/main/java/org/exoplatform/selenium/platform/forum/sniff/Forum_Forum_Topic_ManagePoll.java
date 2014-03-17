package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePoll;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Thuntn
 * @date 12 Sep 2013
 */
public class Forum_Forum_Topic_ManagePoll extends ForumBase{
	ManageAccount magAc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;
	ForumManagePoll mngPoll;
	ForumManagePost mngPost;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		mngCat = new ForumManageCategory(driver);
		mngFru = new ForumManageForum(driver);
		mngTopic = new ForumManageTopic(driver);
		mngPoll = new ForumManagePoll(driver);
		mngPost = new ForumManagePost(driver);

		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**Add a new poll
	 * CaseID: 74762
	 */
	@Test
	public void test01_AddPoll() {
		String titleCat = "Category 01";
		String titleForum = "Forum 01";
		String titleTop = "Topic 01";
		String poll = "Poll of topic 01";
		String[] options =  {"Option 01","Option 02"};
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		
		info("Add a new poll");
		mngPoll.addPoll(poll, options, "2", true, true);
		
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}
	
	/**Edit a poll
	 * CaseID: 71202
	 */
	@Test
	public void test02_EditPoll() {
		String titleCat = "Category 02";
		String titleForum = "Forum 02";
		String titleTop = "Topic 02";
		String poll = "Poll of topic 02";
		String newPoll = "New Poll of topic 02";
		String[] options =  {"Option 01","Option 02"};
		String[] newOptions = {"New Option 01", "New Option 02"};
		
		
		info("Edit a poll");
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		
		mngPoll.addPoll(poll, options, "2", true, true);

		mngPoll.editPoll(newPoll, newOptions, "2", true, true);
		
		waitForTextPresent(newPoll);
		for(int i = 0; i < options.length; i ++)
			waitForAndGetElement(mngPoll.ELEMENT_OPTION.replace("${option}", newOptions[i]));
		
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}
	
	/**Delete a poll
	 * CaseID: 71202
	 */
	@Test
	public void test03_DeletePoll() {
		String titleCat = "Category 03";
		String titleForum = "Forum 03";
		String titleTop = "Topic 03";
		String poll = "Poll of topic 03";
		String[] options =  {"Option 01","Option 02"};
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		
		info("Delete a poll");
		mngPoll.addPoll(poll, options, "2", true, true);

		mngPoll.deletePollInTopic(poll);
		
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}
	
	/**Close / Reopen a poll
	 * CaseID: 71205
	 */
	@Test
	public void test04_ClosePoll() {
		String titleCat = "Category 04";
		String titleForum = "Forum 04";
		String titleTop = "Topic 04";
		String poll = "Poll of topic 04";
		String[] options =  {"Option 01","Option 02"};
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop); 
		click(mngFru.ELEMENT_TOPIC_LINK.replace("${topic}", titleTop));
		
		info("Close / Reopen a poll");
		mngPoll.addPoll(poll, options, "2", true, true);
		
		//Close poll
		mngPoll.closeReopenPoll(options,true);
		
		//Reopen poll
		mngPoll.closeReopenPoll(options,false);
		
		//Delete data
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}
}
