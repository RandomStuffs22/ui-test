package org.exoplatform.selenium.platform.forum.functional.forum.forumportletsettings;



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
import org.testng.annotations.*;



/**
 * @author khanhnt
 *
 */
public class Forum_Forum_ForumPortletSettings_ShowHideCategory extends ForumBase{

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
		pageE = new PageEditor(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);
		fmForum = new ForumManageForum(driver);
		navTool = new NavigationToolbar(driver);
		goToForums();
	}

	@AfterMethod
	public void afterTest() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	* Case ID:72293,72452.
	* Test Case Name: Hide and Show category in Forum portlet settings.
	* Created by khanhnt at 2013/12/17 17:44:46
	* Done with plfent-4.0.x-20131118.122516-366
	* Done with plfent-4.1.0-20131218.102100-253
	*/
	@Test
	public  void test02_HideShowCategoryInForumPortletSettings() {
		info("Test 1: Hide/show category in Forum portlet settings"); 

		String catName1 = "Category setting forum 01";
		String description1 = "Add new category 1 in forum";
		
		fmCat.addNewCategoryInForum(catName1, "1", 0, null, description1, 0, null);
		
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		info("Setting to show/hire category");
		selectDisplayCategoryAndForum(catName1, false);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		acc.signOut();
		
		acc.signIn("demo", "gtn");
		goToForums();
		waitForElementNotPresent(By.linkText(catName1));
		acc.signOut();
		
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
		navTool.goToEditPageEditor();
		pageE.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		
		selectDisplayCategoryAndForum(catName1, true);
		click(ELEMENT_FORUM_PORTLET_CLOSE_BUTTON);
		pageE.finishEditLayout();
		acc.signOut();
		
		acc.signIn("demo", "gtn");
		goToForums();
		waitForAndGetElement(By.linkText(catName1));
		acc.signOut();
		
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
		click(By.linkText(catName1));
		fmCat.deleteCategoryInForum(catName1);
 	}

}