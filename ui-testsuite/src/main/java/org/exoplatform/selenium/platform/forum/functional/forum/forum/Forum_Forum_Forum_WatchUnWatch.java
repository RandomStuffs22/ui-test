package org.exoplatform.selenium.platform.forum.functional.forum.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author khanhnt
 * 
 */
public class Forum_Forum_Forum_WatchUnWatch extends ForumBase {

	ManageAccount acc;
	ForumManageCategory fmCat;
	ForumManageForum fmForum;
	ForumManageTopic fmTopic;
	ForumManagePost fmPost;

	@BeforeMethod
	public void setUpBeforeTest() {
		initSeleniumTest();
		driver.get(baseUrl);
		fmCat = new ForumManageCategory(driver);
		fmForum = new ForumManageForum(driver);
		fmTopic = new ForumManageTopic(driver);
		fmPost = new ForumManagePost(driver);
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);
		fmForum = new ForumManageForum(driver);
	}

	@AfterMethod
	public void afterTest() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * Case ID:73324. Test Case Name: Check send notify after adding new topic
	 * and it's in pending for censor. Created by khanhnt at 2013/12/09 14:31:42
	 */
	@Test
	public void test01_CheckSendNotifyAfterAddingNewTopicAndItsInPendingForCensor() {
		info("Test 1: Check send notify after adding new topic and it's in pending for censor");
		
		String catName = "Test 1 new category";
		String fmName = "Test 1 new forum";
		String tpName = "Test 1 new topic with test censor";
		String censorText = "test censor";
		goToForums();
		fmForum.addCategoryForum(catName, fmName);
		setCensorKeywords(censorText);
		
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		acc.updateUserProfile(null,null, null, EMAIL_ADDRESS1);
		goToForums();
		
		click(By.linkText(fmName));
		
		watchItem(true);
		
		fmTopic.startTopic(tpName, tpName, "", 0, null, true, true);
		waitForMessage(fmForum.MESSAGE_CENSOR);
		click(ELEMENT_OK_INFOR_POPUP);
		
		
		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
		click(By.linkText(fmName));
		waitForTextPresent(fmForum.CENSORED_TITLE.replace("${title}", tpName));
		
		//approve censor topic
		fmTopic.censorTopic(tpName);
		
		
		info("Check if e-mail is sent");
		
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}",
				fmName).replace(" ${topic}", tpName)), tpName);
		
		switchToParentWindow();
		
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);		
		
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		acc.updateUserProfile(null,null, null, "mary.williams@acme.exoplatform.com");

	}

	/**
	 * Case ID:72457. Test Case Name: Check send notify after adding new topic
	 * into forum/category which is watching and it's required for approval.
	 * Created by khanhnt at 2013/12/09 14:31:42
	 */
	@Test
	public void test02_CheckSendNotifyAfterAddingNewTopicIntoForumcategoryWhichIsWatchingAndItsRequiredForApproval() {
		info("Test 2: Check send notify after adding new topic into forum/category which is watching  and it's required for approval");

		String catName = "Test 2 new category";
		String[] fmName = {"Test 2 new forum",null,null,null,null};
		String tpName = "Test 2 new topic";
		goToForums();
		fmCat.addNewCategoryInForum(catName, null, 0, null, "", 0, null);
		fmForum.addForum(catName, fmName, true, null, null, true, 0, null);
		
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		acc.updateUserProfile(null,null, null, EMAIL_ADDRESS1);
		goToForums();
		
		click(By.linkText(fmName[0]));
		fmForum.watchItem(true);
		
		fmTopic.startTopic(tpName, tpName, null, 0, null, true, true);
		
		waitForMessage(fmTopic.MESSAGE_WAIT_APPROVE);
		click(ELEMENT_OK_INFOR_POPUP);
		
		
		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
		click(By.linkText(fmName[0]));
		waitForTextPresent(fmTopic.APPROVE_TITLE.replace("${title}", tpName));
		
		click(By.linkText(tpName));
		
		fmTopic.approveTopic();
		
		info("Check if e-mail is sent");
		
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}",
				fmName[0]).replace(" ${topic}", tpName)), tpName);
		
		switchToParentWindow();
		
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);		
		
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		acc.updateUserProfile(null,null, null, "mary.williams@acme.exoplatform.com");
	}

	/**
	 * Case ID:72974. Test Case Name: Check send notify after move topic in
	 * forum/category that is being watched. Created by khanhnt at 2013/12/09
	 * 14:31:42
	 */
	@Test
	public void test03_CheckSendNotifyAfterMoveTopicInForumcategoryThatIsBeingWatched() {
		info("Test 3: Check send notify after move topic in forum/category that is being watched");

		String catName = "Test 3 new category";
		String fmName = "Test 3 new forum";
		String fmDes = "Test 3 Destination";
		String tpName = "Test 3 new topic";
		acc.updateUserProfile(null,null, null, EMAIL_ADDRESS1);
		goToForums();
		fmForum.addCategoryForum(catName, fmName);
		fmTopic.startTopic(tpName, tpName, "", 0, null, true, true);
		fmForum.watchItem(true);
		goToForumHome();
		click(By.linkText(catName));
		fmForum.quickAddForum(fmDes);
		goToForumHome();
		click(By.linkText(catName));
		click(By.linkText(fmName));
		click(By.linkText(tpName));
		fmTopic.moveTopic(tpName, fmDes);
		
		info("Check if e-mail is sent");
		
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}",
				fmDes).replace("${topic}", tpName)), fmDes);
		
		switchToParentWindow();
		
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);		
		
		
		acc.updateUserProfile(null,null, null, "john.smith@acme.exoplatform.com");
		
	}

	/**
	 * Case ID:72301. Test Case Name: Check send notify when add new topic into
	 * a forum that is being watched. Created by khanhnt at 2013/12/09 14:31:42
	 */
	@Test
	public void test04_CheckSendNotifyWhenAddNewTopicIntoAForumThatIsBeingWatched() {
		info("Test 4: Check send notify when add new topic into a forum that is being watched");

		String catName = "Test 4 new category";
		String fmName = "Test 4 new forum";
		String tpName = "Test 4 new topic";
		acc.updateUserProfile(null,null, null, EMAIL_ADDRESS1);
		goToForums();
		fmForum.addCategoryForum(catName, fmName);		
		fmForum.watchItem(true);
		fmTopic.startTopic(tpName, tpName, "", 0, null, true, true);
		
		info("Check if e-mail is sent");
		
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_GMAIL_EMAIL.replace("${category}",catName).replace("${forum}",
				fmName).replace("${topic}", tpName)), tpName);
		
		switchToParentWindow();
		
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);		
		
		
		acc.updateUserProfile(null,null, null, "john.smith@acme.exoplatform.com");

	}

}