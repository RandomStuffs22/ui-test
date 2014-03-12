package org.exoplatform.selenium.platform.forum.functional.forum.forumportletsettings;



import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePoll;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.openqa.selenium.By;
import org.testng.annotations.*;

/**
 * @author khanhnt
 *
 */
public class Forum_Forum_ForumPortletSettings_EnableDisablePanel extends ForumBase{

	ManageAccount acc;
	ForumManageCategory fmCat;
	ForumManageForum fmForum;
	ForumManageTopic fmTopic;
	ForumManagePost fmPost;
	ForumManagePoll fmPoll;
	@BeforeMethod
	public void setUpBeforeTest() {
		initSeleniumTest();
		driver.get(baseUrl);
		fmCat = new ForumManageCategory(driver);
		fmForum = new ForumManageForum(driver);
		fmTopic = new ForumManageTopic(driver);
		fmPost = new ForumManagePost(driver);
		acc = new ManageAccount(driver);
		pageE = new PageEditor(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
		button = new Button(driver);
		fmForum = new ForumManageForum(driver);
		fmPoll = new ForumManagePoll(driver);
		navTool = new NavigationToolbar(driver);
		goToForums();
	}

	@AfterMethod
	public void afterTest() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	* Case ID:72295, Case ID:73315.
	* Test Case Name: Disable/enable Forum Jump panel in Forum porlet setting.
	* Created by khanhnt at 2013/12/17 17:40:54
	* Test case is needed to be removed 
	*//*
	@Test
	public  void test01_DisableForumJumpPanelInForumPorletSetting() {
		info("Test 1: Disable/Enable Forum Jump panel in Forum porlet setting"); 

 	}*/

	/**
	* Case ID:72576 and 72673
	* Test Case Name: Disable Moderator panel in Forum porlet setting.
	* Created by khanhnt at 2013/12/17 17:40:54
	* Done with plfent-4.1.0-20131218.102100-253
	* Done with plfent-4.0.x-20131118.122516-366
	*/
	@Test
	public  void test02_DisableModeratorPanelInForumPorletSetting() {
		info("Test 2: Disable Moderator panel in Forum porlet setting"); 

		String catName = "Category setting forum 01";
		String description = "Add new category 1 in forum";
		String fmName = "Test 02 new forum";
		
		info("TC 72576: Disable Moderator Panel");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting panel for forum portlet");
		selectPanel(true, false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, true);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
		acc.signOut();
		
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		fmCat.addNewCategoryInForum(catName, "1", 0, null, description, 0, null);
		fmForum.quickAddForum(fmName);
		waitForElementNotPresent(ELEMENT_MODERATOR_PANEL);
		acc.signOut();
		
		info("TC 72673: Enable Moderator Panel");
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting panel for forum portlet");
		selectPanel(true, true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		click(By.linkText(fmName));
		waitForAndGetElement(ELEMENT_MODERATOR_PANEL);
		
		info("Remove data");
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, false);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
	}


	/**
	* Case ID:72753, 72822
	* Test Case Name: Disable Poll panel in Forum porlet setting.
	* Created by khanhnt at 2013/12/17 17:40:54
	* Done with plfent-4.1.0-20131218.102100-253
	* Done with plfent-4.0.x-20131118.122516-366
	*/
	@Test
	public  void test04_DisablePollPanelInForumPorletSetting() {
		info("Test 4: Disable Poll panel in Forum porlet setting"); 

		String catName = "Category setting forum 04";
		String fmName = "Test 04 new forum";
		String topName = "Test 04 topic";
		String pollQuestion = "Poll 02";
		String[] pollOptions = {"Option 01", "Option 02"};
		
		//***************************TC 72753: Disable Poll Panel*****************************//
		info("TC 72753: Disable Poll Panel");
		
		//"Disable poll panel for forum portlet
		info("Disable poll panel for forum portlet");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		//Change role of demo to Admin
		info("Change role of demo to Admin");
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, true);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
		
		//Demo signins and create poll
		info("Demo signins and create poll");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		fmTopic.addCategoryForumTopic(catName, fmName, topName,topName);
		waitForAndGetElement(By.linkText(topName)).click();
		fmPoll.addPoll(pollQuestion, pollOptions, "2", true, true,false);
		waitForElementNotPresent(fmPoll.ELEMENT_POLL);
		acc.signOut();
		
		//********************TC 72822: Enable Poll Panel******************************//
		info("TC 72822: Enable Poll Panel");
		
		//Enable poll panel for forum portlet
		info("Enable poll panel for forum portlet");
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();

		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);

		//Demo signins and verify created poll
		info("Demo signins and verify created poll");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		fmTopic.addCategoryForumTopic(catName, fmName, topName,topName);
		waitForAndGetElement(By.linkText(topName)).click();
		fmPoll.addPoll(pollQuestion, pollOptions, "2", true, true,false);
		waitForAndGetElement(fmPoll.ELEMENT_POLL);
		
		//********************************Remove data***********************************//
		info("Remove data");
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, false);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
 	}


	/**
	* Case ID:72877, 72927
	* Test Case Name: Disable/Enable Quick Reply panel in Forum porlet setting.
	* Created by khanhnt at 2013/12/17 17:40:54
	* Done with plfent-4.1.0-20131218.102100-253
	* Done with plfent-4.0.x-20131118.122516-366
	*/
	@Test
	public  void test06_DisableQuickReplyPanelInForumPorletSetting() {
		info("Test 6: Disable Quick Reply panel in Forum porlet setting"); 

		String catName = "Category setting forum 06";
		String fmName = "Test 06 new forum";
		String topName = "Test 06 topic";
		
		//********************TC 72877: Disable Quick Reply panel in Forum porlet setting*****************************//
		info("TC 72877: Disable Quick Reply panel in Forum porlet setting");
		
		//"Disable quick reply panel for forum portlet
		info("Disable quick reply for forum portlet");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		//Change role of demo to Admin
		info("Change role of demo to Admin");
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, true);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
		
		//Demo signins and create poll
		info("Demo signins and create topic");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		fmTopic.addCategoryForumTopic(catName, fmName, topName,topName);
		waitForAndGetElement(By.linkText(topName)).click();
		waitForElementNotPresent(fmPost.ELEMENT_POST_QUICK_MESSAGE);
		acc.signOut();
		
		//********************TC 72927:Enable Quick Reply panel in Forum porlet setting******************************//
		info("TC 72927:Enable Quick Reply panel in Forum porlet setting");
		
		//Enable quick reply panel for forum portlet
		info("Enable quick reply panel for forum portlet");
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();

		//Demo signins and verify quick reply panel
		info("Demo signins and verify if quick reply panel displays");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		click(By.linkText(fmName));
		click(By.linkText(topName));
		waitForAndGetElement(fmPost.ELEMENT_POST_QUICK_MESSAGE);
		
		//********************************Remove data***********************************//
		info("Remove data");
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, false);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
 	}


	/**
	* Case ID:72972, 73012
	* Test Case Name: Disable/Enable Icons Legend panel in Forum porlet setting.
	* Created by khanhnt at 2013/12/17 17:40:54
	* Done with plfent-4.1.0-20131218.102100-253
	* Done with plfent-4.0.x-20131118.122516-366
	*/
	@Test
	public  void test08_DisableIconsLegendPanelInForumPorletSetting() {
		info("Test 8: Disable Icons Legend panel in Forum porlet setting"); 
		
		//********************TC 72972: Disable Icons Legend panel in Forum porlet setting*****************************//
		info("TC 72972: Disable Icons Legend panel in Forum porlet setting");
		
		//"Disable Icons Legend panel for forum portlet
		info("Disable Icons Legend for forum portlet");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,true,false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		//Demo signins and verify Icon Legends
		info("Demo signins and create topic");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		waitForElementNotPresent(ELEMENT_FORUM_STATE);
		acc.signOut();
		
		//********************TC 73012:Enable Icons Legend panel in Forum porlet setting******************************//
		info("TC 73012:Enable  Icons Legend panel in Forum porlet setting");
		
		//Enable Icons Legend panel for forum portlet
		info("Enable Icons Legend panel for forum portlet");
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,true,true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();

		//Demo signins and verify Icons Legend panel
		info("Demo signins and verify if  Icons Legend displays");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		waitForAndGetElement(ELEMENT_FORUM_STATE);
 	}

	/**
	* Case ID:73047, 73082
	* Test Case Name: Disable/Enable Rules panel in Forum porlet setting.
	* Created by khanhnt at 2013/12/17 17:40:54
	* Done with plfent-4.1.0-20131218.102100-253
	* Done with plfent-4.0.x-20131118.122516-366
	*/
	@Test
	public  void test10_DisableRulesPanelInForumPorletSetting() {
		info("Test 10: Disable Rules panel in Forum porlet setting"); 

		String catName = "Category setting forum 10";
		String fmName = "Test 10 new forum";
		String topName = "Test 10 topic";
		
		//********************TC 73047: Disable Rules panel in Forum porlet setting*****************************//
		info("TC 73047: Disable Rules panel in Forum porlet setting");
		
		//"Disable Rules panel panel for forum portlet
		info("Disable Rules panel for forum portlet");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,true,true,false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		//Change role of demo to Admin
		info("Change role of demo to Admin");
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, true);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
		
		//Demo signins and create topic
		info("Demo signins and create topic");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		fmTopic.addCategoryForumTopic(catName, fmName, topName,topName);
		waitForAndGetElement(By.linkText(topName)).click();
		waitForElementNotPresent(fmForum.ELEMENT_RULE_PANEL);
		acc.signOut();
		
		//********************TC 73082:Enable Rules panel in Forum porlet setting******************************//
		info("TC 73082:Enable Rules panel in Forum porlet setting");
		
		//Enable quick reply panel for forum portlet
		info("Enable Rules panel for forum portlet");
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,true,true,true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();

		//Demo signins and verify Rules panel
		info("Demo signins and verify if Rules panel displays");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		click(By.linkText(fmName));
		click(By.linkText(topName));
		waitForAndGetElement(fmForum.ELEMENT_RULE_PANEL);
		
		//********************************Remove data***********************************//
		info("Remove data");
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		click(By.linkText(catName));
		fmCat.deleteCategoryInForum(catName);
		goToUserManagement(DATA_USER4);
		settingUserManagementProfile(null, null, null, null, null, false);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
 	}


	/**
	* Case ID:73110, 73131
	* Test Case Name: Disable/Enable Statistic panel in Forum porlet setting.
	* Created by khanhnt at 2013/12/17 17:40:54
	* Done with plfent-4.1.0-20131218.102100-253
	* Done with plfent-4.0.x-20131118.122516-366
	*/
	@Test
	public  void test12_DisableStatisticPanelInForumPorletSetting() {
		info("Test 12 Disable Statistic panel in Forum porlet setting"); 

		//********************TC 73110: Disable Statistic panel in Forum porlet setting*****************************//
		info("TC 73110: Disable Statistic panel in Forum porlet setting");
		
		//"Disable Statistic panel for forum portlet
		info("Disable Statistic for forum portlet");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,true,true,true,false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		//Demo signins and verify Statistic
		info("Demo signins and  and verify Statistic");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		waitForElementNotPresent(ELEMENT_STATISTIC_PANEL);
		acc.signOut();
		
		//********************TC 73131:Enable Statistic panel in Forum porlet setting.******************************//
		info("TC 73131: Statistic panel in Forum porlet setting.");
		
		//Enable Statistic panel for forum portlet
		info("Enable Statistic panel for forum portlet");
		acc.signIn(DATA_USER1, DATA_PASS);
		goToForums();
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectPanel(true,true,true,true,true,true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();

		//Demo signins and verify Statistic panel
		info("Demo signins and verify if  Statistic displays");
		acc.signOut();
		acc.signIn(DATA_USER4, DATA_PASS);
		goToForums();
		waitForAndGetElement(ELEMENT_STATISTIC_PANEL);
 	}
}