package org.exoplatform.selenium.platform.forum.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
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
 * @author lientm
 * @date 9 Sep 2013
 */
public class Forum_Setting extends ForumBase {
	
	ManageAccount magAc;
	ForumManageCategory cat;
	ForumManageForum forum;
	ForumManageTopic magtopic;
	ForumManagePost post;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver, this.plfVersion);
		cat = new ForumManageCategory(driver, this.plfVersion);
		forum = new ForumManageForum(driver, this.plfVersion);
		magtopic = new ForumManageTopic(driver, this.plfVersion);
		post = new ForumManagePost(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		navTool = new NavigationToolbar(driver);
		pageE = new PageEditor(driver);
		magAc.signIn("john", DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68912 -> User setting
	 * 
	 */
	@Test
	public void test01_UserSetting(){
		String catName = "Category Forum Setting 01";
		String description = "Add new category in forum";
		
		cat.addNewCategoryInForum(catName, "1", 0, null, description, 0, null);
		watchItem(true);
		
		settingProfileUser("John Smith Update", "Thanks. John Smith", true, true, true, true);
		goToSetting();
		forumsSettings("+7.00", "dd-MM-yyyy", "EEEE,=MMMM dd, yyyy", "24-hour", "5", "15");
		goToMySubscriptions();
		assert getValue(ELEMENT_FEED_URL_TEXTBOX) != null;
		waitForAndGetElement(ELEMENT_CHECKBOX_EMAIL.replace("${name}", catName), DEFAULT_TIMEOUT, 1, 2);
		type(ELEMENT_SETTING_EMAIL_ADDRESS, EMAIL_ADDRESS1, true);
		click(ELEMENT_SETTING_EMAIL_UPDATE);
		button.save();
		
		info("Reset data");
		cat.deleteCategoryInForum(catName);
		settingProfileUser("John Smith", "", false, true, false, false);
		goToSetting();
		forumsSettings("0.00", "MM/dd/yyyy", "EEE,=MMMM dd, yyyy", "12-hour", "10", "10");
	}
	
	/**CaseId: 68940 -> User Management
	 * 
	 */
	@Test
	public void test02_UserManagement(){
		String catName = "Category User Management 02";
		String description = "Add new category in forum";
		String forumName = "Forum User Management 02";
		String title = "Topic User Management 02";
		String message = "Create new topic";
		String postname = "Post User Management 02";
		String messagePost = "Reply topic 02";
		
		cat.addNewCategoryInForum(catName, "1", 0, null, description, 0, null);
		forum.quickAddForum(forumName);
		magAc.signOut();
		
		info("Create topic and post with user demo");
		magAc.signIn("demo", DATA_PASS);
		goToForums();
		click(By.linkText(forumName));
		magtopic.quickStartTopic(title, message);
		click(By.linkText(title));
		post.postReply(postname, messagePost, null, null);
		magAc.signOut();
		magAc.signIn("john", DATA_PASS);
		
		goToForums();
		goToUserManagement("demo");
		settingUserManagementProfile("Jack Miller Update", "User", catName, forumName, "Thanks. Jack Miller", true, true, true);
		forumsSettings("+7.00", "dd-MM-yyyy", "EEEE,=MMMM dd, yyyy", "24-hour", "5", "15", "false");
		banUser(true, null, "abc");
		click(ELEMENT_USER_MANAGEMENT_TOPIC_TAB);
		waitForAndGetElement(By.linkText(title));
		click(ELEMENT_USER_MANAGEMENT_POST_TAB);
		waitForAndGetElement(By.linkText(postname));
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
		magAc.signOut();
		
		info("Check ban user demo");
		magAc.signIn("demo", DATA_PASS);
		goToForums();
		click(By.linkText(forumName));
		waitForAndGetElement(ELEMENT_START_TOPIC_DISABLE);
		magAc.signOut();
		
		info("Reset data");
		magAc.signIn("john", DATA_PASS);
		goToForums();
		click(By.linkText(catName));
		cat.deleteCategoryInForum(catName);
		goToUserManagement("demo");
		settingUserManagementProfile("Jack Miller", "User", null, null, "", true, false, true);
		forumsSettings("0.00", "MM/dd/yyyy", "EEE,=MMMM dd, yyyy", "12-hour", "10", "10", "false");
		banUser(false, null, null);
		button.save();
		button.close();
		waitForElementNotPresent(ELEMENT_USER_MANAGEMENT_POPUP);
	}
	
	/**CaseId: 68918 -> Setting Forum Portlet
	 * 
	 */
	@Test
	public void test03_SettingForumPortlet(){
		String catName1 = "Category setting forum 01";
		String description1 = "Add new category 1 in forum";
		String catName2 = "Category setting forum 02";
		String description2 = "Add new category 2 in forum";
		
		cat.addNewCategoryInForum(catName1, "1", 0, null, description1, 0, null);
		goToForumHome();
		cat.addNewCategoryInForum(catName2, "1", 0, null, description2, 0, null);
		
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting to show/hire category");
		selectDisplayCategoryAndForum(catName2,false);
		info("Setting panel for forum portlet");
		selectPanel(true, true, true, false);
		info("Setting not user ajax for forum portlet");
		selectOptions(false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		waitForElementNotPresent(By.linkText(catName2));
		waitForElementNotPresent(ELEMENT_LEGEN_PANEL);
		click(By.linkText(catName1));
		String url = driver.getCurrentUrl();
		assert url.contains("http://localhost:8080/portal/intranet/forum/category/forumCategory");
		
		info("Reset Data");
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		selectDisplayCategoryAndForum(catName2, true);
		selectPanel(true, true, true, true);
		selectOptions(true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		
		click(By.linkText(catName1));
		cat.deleteCategoryInForum(catName1);
		click(By.linkText(catName2));
		cat.deleteCategoryInForum(catName2);
	}
} 
