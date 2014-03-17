package org.exoplatform.selenium.platform.forum.functional.forum.forum;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author khanhnt
 * 
 */
public class Forum_Forum_Forum_Activities extends ForumBase{

	ManageAccount acc;
	ForumManageCategory fmCat;
	ForumManageForum fmForum;
	ForumManageTopic fmTopic;
	ForumManagePost fmPost;
	HomePageActivity homePage;
	Activity activity;
	ManageMember manMember;
	
	SpaceManagement spaceMan;
	@BeforeMethod
	public void setUpBeforeTest() {
		initSeleniumTest();
		driver.get(baseUrl);
		fmCat = new ForumManageCategory(driver);
		fmForum = new ForumManageForum(driver);
		fmTopic = new ForumManageTopic(driver);
		fmPost = new ForumManagePost(driver);
		homePage = new HomePageActivity(driver);
		spaceMan = new SpaceManagement(driver);
		activity = new Activity();
		manMember = new ManageMember(driver);
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
	 * Case ID:77241. Test Case Name: Add a Forum's activity after create a
	 * Topic of intranet's portal. Created by khanhnt at 2013/12/09 14:03:51
	 */
	@Test
	public void test01_AddAForumsActivityAfterCreateATopicOfIntranetsPortal() {
		info("Test 1: Add a Forum's activity after create a Topic of intranet's portal");

		String catName = "Test 1 new category";
		String fmName = "Test 1 new forum";
		String tpName = "Test 1 new topic";
		goToForums();
		fmForum.addCategoryForum(catName, fmName);
		fmTopic.startTopic(tpName, tpName, "", 0, null, true, true);
		click(ELEMENT_HOME_PAGE);
		homePage.checkNumberOfLineOfContent(tpName, tpName);
		
		goToForums();
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);		
	}

	/**
	 * Case ID:77289. Test Case Name: Add a Forum's activity after create a
	 * Topic of space. Created by khanhnt at 2013/12/09 14:03:51
	 */
	@Test
	public void test02_AddAForumsActivityAfterCreateATopicOfSpace() {
		info("Test 2: Add a Forum's activity after create a Topic of space");
		
		String spaceName = "Test 2 new space";
		String tpName = "Test 2 new topic";
		String tpContent = "Test 2 new content";
		spaceMan.goToAllSpaces();
		spaceMan.addNewSpace(spaceName, spaceName);
		spaceMan.goToSpaceMenu("Forums");		
		fmTopic.quickStartTopic(tpName, tpContent);
		
		click(ELEMENT_HOME_PAGE);
		waitForAndGetElement(By.linkText(spaceName));
		waitForAndGetElement(By.linkText(tpName));
		
		spaceMan.goToAllSpaces();
		spaceMan.deleteSpace(spaceName);
		
		
	}

	/**
	 * Case ID:77285. Test Case Name: Delete a Forum activity from activity
	 * stream by owner. Created by khanhnt at 2013/12/09 14:03:51
	 */
	@Test
	public void test03_DeleteAForumActivityFromActivityStreamByOwner() {
		info("Test 3: Delete a Forum activity from activity stream by owner");

		String catName = "Test 3 new category";
		String fmName = "Test 3 new forum";
		String tpName = "Test 3 new topic";
		goToForums();
		fmForum.addCategoryForum(catName, fmName);
		fmTopic.startTopic(tpName, tpName, "", 0, null, true, true);
		click(ELEMENT_HOME_PAGE);
		homePage.checkNumberOfLineOfContent(tpName, tpName);
		
		homePage.deleteActivity(tpName);
		
		goToForums();
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);		
		
		
	}

	/**
	 * Case ID:77600. Test Case Name: Delete a Forum activity from space
	 * activity stream by not owner. Created by khanhnt at 2013/12/09 14:03:51
	 */
	@Test
	public void test04_DeleteAForumActivityFromSpaceActivityStreamByNotOwner() {
		info("Test 4: Delete a Forum activity from space activity stream by not owner");

		String spaceName = "Test 4 new space";
		String tpName = "Test 4 new topic";
		String tpContent = "Test 4 new content";
		
		manMember.adminInviteUserAndUserAcceptInvitation(true, true, false, spaceName
				, "", null, ManageAccount.userType.PUBLISHER);
		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		
		spaceMan.goToSpaceFromMySpaceNavigation(spaceName);
		spaceMan.goToSpaceMenu("Forums");		
		fmTopic.quickStartTopic(tpName, tpContent);
		
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		
		spaceMan.goToSpaceFromMySpaceNavigation(spaceName);
		spaceMan.goToSpaceMenu("Activity Stream");
		
		waitForAndGetElement(By.linkText(tpName));
		waitForTextPresent(tpContent);
		homePage.deleteActivity(tpName,false);
		
		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		
		spaceMan.goToAllSpaces();
		spaceMan.deleteSpace(spaceName);
	}

	/**
	 * Case ID:77599. Test Case Name: Delete a Forum activity from space
	 * activity stream by owner. Created by khanhnt at 2013/12/09 14:03:51
	 */
	@Test
	public void test05_DeleteAForumActivityFromSpaceActivityStreamByOwner() {
		info("Test 5: Delete a Forum activity from space activity stream by owner");

		String spaceName = "Test 5 new space";
		String tpName = "Test 5 new topic";
		String tpContent = "Test 5 new content";
		spaceMan.goToAllSpaces();
		spaceMan.addNewSpace(spaceName, spaceName);
		spaceMan.goToSpaceMenu("Forums");		
		fmTopic.quickStartTopic(tpName, tpContent);
		
		spaceMan.goToSpaceMenu("Activity Stream");
		
		
		waitForAndGetElement(By.linkText(tpName));
		waitForTextPresent(tpContent);
		
		homePage.deleteActivity(tpName);
		waitForElementNotPresent(By.linkText(tpName));
		
		spaceMan.goToAllSpaces();
		spaceMan.deleteSpace(spaceName);
		
	}

	/**
	 * Case ID:77286. Test Case Name: Dislike a Forum activity from like icon.
	 * Created by khanhnt at 2013/12/09 14:03:51
	 */
	@Test
	public void test06_DislikeAForumActivityFromLikeIcon() {
		info("Test 6: Dislike a Forum activity from like icon");
		
		String catName = "Test 6 new category";
		String fmName = "Test 6 new forum";
		String tpName = "Test 6 new topic";
		String post = "Test 6 new post";
		goToForums();
		fmForum.addCategoryForum(catName, fmName);
		fmTopic.startTopic(tpName, tpName, "", 0, null, true, true);
		click(By.linkText(tpName));
		fmPost.postReply(post, post, "", "");
		
		click(ELEMENT_HOME_PAGE);
		
		info("Like activity");
		homePage.likeOrUnlikeActivity(tpName);
		
		info("Dislike activity");
		homePage.likeOrUnlikeActivity(tpName);
		
		goToForums();
		goToForumHome();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);	

	}

	/**
	 * Case ID:77287. Test Case Name: Update Forum's activity after approving a
	 * censored post in topic. Created by khanhnt at 2013/12/09 14:03:51
	 */
	@Test
	public void test07_UpdateForumsActivityAfterApprovingACensoredPostInTopic() {
		info("Test 7: Update Forum's activity after approving a censored post in topic");
		
		String catName = "Test 7 new category";
		String fmName = "Test 7 new forum";
		String tpName = "Test 7 new topic";
		String post = "censor post";
		String censorText = "censor";
		goToForums();
		fmForum.addCategoryForum(catName, fmName);
		setCensorKeywords(censorText);
		fmTopic.quickStartTopic(tpName, tpName);
		
		
		acc.signOut();
		acc.signIn(DATA_USER_MARY, DATA_PASS);
		goToForums();
		
		click(By.linkText(fmName));
		click(By.linkText(tpName));
		fmPost.quickReply(post,false);
		
		waitForMessage(fmForum.MESSAGE_CENSOR);
		click(ELEMENT_OK_INFOR_POPUP);
		
		click(ELEMENT_HOME_PAGE);
		
		waitForTextNotPresent(post);
		
		acc.signOut();
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
		
		goToPendingJob();
		approvePendingJob("Re: "+tpName, post, true);
		
		click(ELEMENT_HOME_PAGE);
		waitForTextPresent(post);
		
		goToForums();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName, true);	
		
	}

}