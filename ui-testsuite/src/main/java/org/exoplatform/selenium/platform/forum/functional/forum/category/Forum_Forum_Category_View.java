package org.exoplatform.selenium.platform.forum.functional.forum.category;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.Set;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.exoplatform.selenium.platform.forum.ForumPermission;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Thuntn
 * @date 21 Nov 2013
 */
public class Forum_Forum_Category_View extends ForumBase {

	ManageAccount magAc;
	ForumManageCategory cat;
	ForumManageForum mForum;
	ForumManageTopic magtopic;
	UserGroupManagement userGroup;
	ForumPermission forPermission;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		magAc = new ManageAccount(driver);
		cat = new ForumManageCategory(driver);
		mForum = new ForumManageForum(driver);
		magtopic = new ForumManageTopic(driver);
		userGroup = new UserGroupManagement(driver);
		navTool = new NavigationToolbar(driver);
		button = new Button(driver);
		forPermission = new ForumPermission(driver);

		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToForums();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 72279 
	 * View a category in case it is not restricted audience
	 */
	@Test
	public void test01_ViewCategoryInCaseNotRestrictedAudience(){
		String category = "Category 72279";
		String descCate = "Description category 72279";
		
		String[] permission = {};
		
		info("View a category in case it is not restricted audience");
		//Create category
		cat.addNewCategoryInForum(category, "1", 0,permission, descCate, 0,null);

		cat.checkRightOfViewCategory("demo", DATA_PASS, category, descCate, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72440
	 * View a category in case it is restricted for specific users
	 */
	@Test
	public void test02_ViewCategoryIncaseRestrictedForSpecificUsers(){
		String category = "Category 72440";
		String descCate = "Description category 72440";
		String[] permission = {"mary"};

		info("View a category in case it is restricted for specific users");
		//Create category
		cat.addNewCategoryInForum(category, "1", 1,permission, descCate, 0,null);

		//Check if demo cannot see the category
		cat.checkRightOfViewCategory("demo", DATA_PASS, category, descCate, false);

		//Check if mary can see the category
		cat.checkRightOfViewCategory(DATA_USER_MARY, DATA_PASS, category, descCate, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72563
	 * View a category in case it is restricted for specific roles
	 */
	@Test
	public void test03_ViewCategoryIncaseRestrictedForSpecificRole(){
		String category = "Category 72563";
		String descCate = "Description category 72563";
		String[] permission = {"Platform/Content Management","*"};

		info("View a category in case it is restricted for specific roles");
		//Create category
		cat.addNewCategoryInForum(category, "1", 4,permission, descCate, 0,null);

		//Check if demo cannot see the category
		cat.checkRightOfViewCategory("demo", DATA_PASS, category, descCate, false);

		//Check if mary can see the category
		cat.checkRightOfViewCategory(DATA_USER_MARY, DATA_PASS, category, descCate, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72667
	 * View a category in case it is restricted for specific group
	 */
	@Test
	public void test04_ViewCategoryIncaseRestrictedForSpecificGroups(){
		String category = "Category 72667";
		String descCate = "Description category 72667";
		String[] permission = {"Platform/Content Management"};

		info("View a category in case it is restricted for specific group");
		//Create category
		cat.addNewCategoryInForum(category, "1", 3,permission, descCate, 0,null);

		//Check if demo cannot see the category
		cat.checkRightOfViewCategory("demo", DATA_PASS, category, descCate, false);

		//Check if mary can see the category
		cat.checkRightOfViewCategory(DATA_USER_MARY, DATA_PASS, category, descCate, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72745
	 * View a category in case its is restricted for user, role and group at once
	 */
	@Test
	public void test05_ViewCategoryIncaseRestrictedForUserRoleGroup(){
		String category = "Category 72745";
		String descCate = "Category 72745";
		String[] groups = {"Development"};
		String[] users = {"james"};
		String[] membership = {"Platform/Content Management","*"};
		String user = getRandomString();
		String pass = "gtngtn";

		info("View a category in case its is restricted for user, role and group at once");
		navTool.goToNewStaff();
		magAc.addNewUserAccount(user, pass, pass, "Test", "eXo", "Test", "exomailtestq@gmail.com", null, null, true);

		goToForums();
		//Create category
		cat.goToAddCategory();
		cat.inputTitleOrderDescriptionCategory(category, "0", descCate);
		cat.selectRestricted(1, users);
		cat.selectRestricted(3, groups);
		cat.selectRestricted(4, membership);
		button.save();

		cat.checkRightOfViewCategory(user, pass, category, descCate, false);

		//Check if demo cannot see the category
		cat.checkRightOfViewCategory("demo", DATA_PASS, category, descCate, true);

		//Check if mary can see the category
		cat.checkRightOfViewCategory(DATA_USER_MARY, DATA_PASS, category, descCate, true);

		//Check if james can the category
		cat.checkRightOfViewCategory("james", DATA_PASS, category, descCate, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);

		navTool.goToUsersAndGroupsManagement();
		userGroup.deleteUser(user);
	}

	/**CaseId: 72815, 72816
	 * View a category with restricted audience in case the administrator login
	 */
	@Test
	public void test06_AdministratorViewCategoryWithRestrictedAudience(){
		String category = "Category 72815";
		String descCate = "Description category 72815";
		String[] permission = {"demo"};

		info("View a category with restricted audience in case the administrator login");
		//Create category
		cat.addNewCategoryInForum(category, "1", 1,permission, descCate, 0,null);

		//Check if root can see the category
		cat.checkRightOfViewCategory("root", "gtngtn", category, descCate, true);

		//Delete data
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72871
	 * View a category with restricted audience in case the administrator login
	 */
	@Test
	public void test07_AdministratorViewCategoryWithoutRestrictedAudience(){
		String category = "Category 72871";
		String descCate = "Description category 72871";

		info("View a category with restricted audience in case the administrator login");
		//Create category
		cat.addNewCategoryInForum(category, "1", 0,null, descCate, 0,null);

		//Check if root can see the category
		cat.checkRightOfViewCategory("root", "gtngtn", category, descCate, true);

		//Delete data
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72921
	 * View a forum belong category which assigned moderator for specific user
	 */
	@Test
	public void test08_ViewForumOfCategoryWhichAssignModeratorForSpecificUser(){
		String category = "Category 72921";
		String descCate = "Description category 72921";
		String[] userGroup = {"demo"};
		String forumName = "Forum 72921";
		String[] addForum = {forumName, "1",null,null,forumName};

		info("View a forum belong category which assigned moderator for specific user");
		//Create category
		cat.addNewCategoryInForum(category, "1", 0,null, descCate, 1,userGroup,true,false,false,false);
		mForum.addForum(category, addForum, false, null, null, false, 0, null);

		mForum.checkRightOfCategoryForum(DATA_USER_MARY,DATA_PASS, category, forumName, false);

		mForum.checkRightOfCategoryForum("demo",DATA_PASS, category, forumName, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 72966
	 * View a forum belong category which assigned moderator for specific role
	 */
	@Test
	public void test09_ViewForumOfCategoryWhichAssignModeratorForSpecificRole(){
		String category = "Category 72966";
		String descCate = "Description category 72966";
		String[] userGroup = {"Platform/Content Management","*"};
		String forumName = "Forum 72966";
		String[] addForum = {forumName, "1",null,null,forumName};

		info("View a forum belong category which assigned moderator for specific role");
		//Create category
		cat.addNewCategoryInForum(category, "1", 0,null, descCate, 4,userGroup,true,false,false,false);
		mForum.addForum(category, addForum, false, null, null, false, 0, null);

		mForum.checkRightOfCategoryForum("demo",DATA_PASS, category, forumName, false);

		mForum.checkRightOfCategoryForum(DATA_USER_MARY,DATA_PASS, category, forumName, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 73006
	 * View a forum belong category which assigned moderator for specific group
	 */
	@Test
	public void test10_ViewForumOfCategoryWhichAssignModeratorForSpecificGroup(){
		String category = "Category 73006";
		String descCate = "Description category 73006";
		String[] userGroup = {"Development"};
		String forumName = "Forum 73006";
		String[] addForum = {forumName, "1",null,null,forumName};

		info("View a forum belong category which assigned moderator for specific group");
		//Create category
		cat.addNewCategoryInForum(category, "1", 0,null, descCate, 3,userGroup,true,false,false,false);
		mForum.addForum(category, addForum, false, null, null, false, 0, null);

		mForum.checkRightOfCategoryForum(DATA_USER_MARY,DATA_PASS, category, forumName, false);

		mForum.checkRightOfCategoryForum("demo",DATA_PASS, category, forumName, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);
	}

	/**CaseId: 73041
	 * View a forum in case it is assigned moderator for user, role and group at once
	 */
	@Test
	public void test11_ViewForumOfCategoryWhichAssignModeratorForUserRoleGroup(){
		String category = "Category 73041";
		String descCate = "Description category 73041";
		String[] groups = {"Development"};
		String[] users = {"mary"};
		String[] membership = {"Organization/Management/Human Resources","*"};
		String forumName = "Forum 73041";
		String[] addForum = {forumName, "1",null,null,forumName};
		String user = getRandomString();
		String pass = "gtngtn";

		info("View a forum in case it is assigned moderator for user, role and group at once");

		navTool.goToNewStaff();
		magAc.addNewUserAccount(user, pass, pass, "Test", "eXo", "Test", "exoservice@gmail.com", null, null, true);

		goToForums();

		//Create category, assign moderator for user, role, group
		cat.goToAddCategory();
		cat.inputTitleOrderDescriptionCategory(category, "0", descCate);
		forPermission.configPermission4ForumCategory(1, users, true,false,false,false);
		forPermission.configPermission4ForumCategory(3, groups, true,false,false,false);
		forPermission.configPermission4ForumCategory(4, membership, true,false,false,false);
		button.save();

		//Add forum
		mForum.addForum(category, addForum, false, null, null, false, 0, null);

		//Check right of users
		mForum.checkRightOfCategoryForum(user, pass, category, forumName, false);

		mForum.checkRightOfCategoryForum(DATA_USER_MARY, DATA_PASS, category, forumName, true);

		mForum.checkRightOfCategoryForum("demo", DATA_PASS, category, forumName, true);

		mForum.checkRightOfCategoryForum("james", DATA_PASS, category, forumName, true);

		//Delete data
		magAc.userSignIn(userType.ADMIN);
		goToForums();
		click(By.linkText(category));
		cat.deleteCategoryInForum(category);

		navTool.goToUsersAndGroupsManagement();
		userGroup.deleteUser(user);
	}

	/**CaseId: 73076
	 * View a category in case it no longer exists
	 */
	@Test
	public void test12_ViewForumOfCategoryWhichAssignModeratorForUserRoleGroup(){
		String category = "Category 73076";
		String descCate = "Description category 73076";
		String handlesBefore = driver.getWindowHandle();
		Set<org.openqa.selenium.Cookie> cookieBefore = driver.manage().getCookies();

		info("View a category in case it no longer exists");
		//Create category, assign moderator for user, role, group
		cat.addNewCategoryInForum(category, "1", 0, null, descCate, 0, null);
		goToForumHome();

		//Login as root and delete category
		cat.loginInNewWindowToDeleteCategory("root", "gtngtn", category);

		//Check the message when view a deleted category
		backToPreviousBrowser(cookieBefore, handlesBefore);
		click(By.linkText(category));
		waitForMessage(cat.MSG_CATEGORY_NO_EXIST);
		click(cat.ELEMENT_CATEGORY_NO_EXIST_OK_BUTTON);

	}
}