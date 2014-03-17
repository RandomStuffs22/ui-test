package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Thuntn
 * @date: 18 Sep 2013
 */
public class Forum_Forum_BasicAction extends ForumBase{
	ManageAccount magAc;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		mngCat = new ForumManageCategory(driver, this.plfVersion);
		mngFru = new ForumManageForum(driver, this.plfVersion);
		mngTopic = new ForumManageTopic(driver);

		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Add a forum, Edit a forum, Delete a forum
	 * CaseID 71105, 71106, 71107
	 */
	@Test
	public void test01_AddForum() {
		String category = "Category_71105";
		String forum = "Forum_71105";
		
		String[] permission = {};
		String[] addForum = {forum, "1","Open","Unlocked",forum};

		info("Add a forum, Edit a forum, Delete a forum");
		mngCat.addNewCategoryInForum(category, "1", 0,permission, category, 0,permission);
		
		//Add forum
		mngFru.addForum(category, addForum, true, "", "", true, 0, permission);
		//Edit forum
		mngFru.editForum(addForum, false, EMAIL_ADDRESS2, EMAIL_ADDRESS2, false, 0, permission);
		
		//Delete forum
	mngFru.deleteForum(forum);
		
		//Delete category
		//click(By.linkText(category));
		mngCat.deleteCategoryInForum(category);

	}

	/** Move a forum
	 * CaseID 71109
	 */
	@Test
	public void test02_MoveForum() {
		String category1 = "Category_71109_01";
		String category2 = "Category_71109_02";
		String forum = "Forum_71109";
		String[] permission = {};
		String[] addForum = {forum, "1","Open","Unlocked",forum};

		info("Move a forum");
		//Add category
		mngCat.addNewCategoryInForum(category1, "1", 0,permission, category1, 0,permission);
		goToForumHome();
		mngCat.addNewCategoryInForum(category2, "1", 0,permission, category2, 0,permission);
		
		//Add forum
		mngFru.addForum(category1, addForum, true, "", "", true, 0, permission);
		
		//Move forum
		mngFru.moveForum(forum, category2);
		
		//Delete data
		click(By.linkText(category2));
		mngCat.deleteCategoryInForum(category2);
		click(By.linkText(category1));
		mngCat.deleteCategoryInForum(category1);

	}
	
	/** Watch&Unwatch forum
	 * CaseID 68914
	 */
	@Test
	public void test03_WatchUnwatchForum() {
		String category = "Category_68914";
		String forum = "Forum_68914";
		String[] permission = {};
		String topic1 = "Topic_68914_01";
		String topic2 = "Topic_68914_02";

		info("Watch&Unwatch forum");
		waitForAndGetElement(ELEMENT_WHAT_GOING_ON);
		magAc.updateUserProfile(null,null, null, EMAIL_ADDRESS1);
		goToForums();
		//Add forum
		mngFru.addCategoryForum(category, forum);
		
		//Watch forum
		mngFru.watchItem(true);
		mngTopic.startTopic(topic1, topic1, "", 0, permission, false, false, false);
		
		//Check email after watching
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",category).replace("${forum}", forum).replace("${topic}", topic1)), forum);
		
		switchToParentWindow();
		
		//Unwatch forum
		mngFru.watchItem(false);
		mngTopic.startTopic(topic2, topic2, "", 0, permission, false, false, false);
		
		//Check email after unwatching
		switchToNewWindow();
		Utils.pause(30000);
		waitForElementNotPresent(ELEMENT_GMAIL_EMAIL.replace("${category}",category).replace("${forum}", forum).replace("${topic}", topic2));
		
		switchToParentWindow();
		
		//Delete data
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category);

	}
}
