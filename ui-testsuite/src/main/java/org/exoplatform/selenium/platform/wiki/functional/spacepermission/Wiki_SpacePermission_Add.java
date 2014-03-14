package org.exoplatform.selenium.platform.wiki.functional.spacepermission;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformPermission;
import org.exoplatform.selenium.platform.wiki.BasicAction;

import static org.exoplatform.selenium.TestLogger.info;

/*
 *-- 
 *-- Update by VuNA2 
 *-- Make a group of common code for these test cases
 *-- Fix error: UnhandledAlertException: Modal dialog present   
 *--
 */
/**
 *
 * @author HangNTT
 * @date: 17/12/2012
 */
public class Wiki_SpacePermission_Add extends BasicAction {

	ManageAccount magAcc;
	Button button;
	PlatformPermission per;
	
	String DATA_USER_ADMIN = DATA_USER1;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		per = new PlatformPermission(driver);
		magAcc.signIn(DATA_USER_ADMIN, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 69679 
	 * case01: Add permission for user by putting user's name
	 * add page
	 * Check permission for user have add/edit page by putting user's name
	 * check user can view/edit page
	 */
	@Test
	public void test01_AddPermissionForUserByPuttingUserName() {

		String PAGE_NAME1 = "Add permission for user by putting user name";

		String[] user2= {DATA_USER3};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV1(PAGE_NAME1, 0, user2, DATA_USER3);

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, DATA_USER3);
	}

	/**
	 * Qmetry ID: 69689
	 *  case02: Add permission for user by selecting user's name
	 * add page
	 * Check permission for user have add/edit page by selecting directly
	 * check user can view/edit page
	 */
	@Test
	public void test02_AddPermissionForUserBySelectingDirectly() {

		String PAGE_NAME1 = "Add permission for user by selecting directly";

		String[] user2= {DATA_USER3};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV1(PAGE_NAME1, 1, user2, DATA_USER3);

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);

		goToWiki();
		
		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, DATA_USER3);
	}

	/**
	 * Qmetry ID: 69687 
	 * case03: Add permission for user by Searching UserName
	 * add page
	 * Check permission for user have add/edit page by searching user name
	 * check user can view/edit page
	 */
	@Test
	public void test03_AddPermissionForUserBySearchingUserByUserName() {

		String PAGE_NAME1 = "add permission by search user";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV2(PAGE_NAME1, DATA_USER3, DATA_USER3, 1);

		info("Check user after set permisison");

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, DATA_USER3);
	}

	/**
	 * Qmetry ID: 69683
	 *  case04: Add permission for user by Searching First Name
	 * add page
	 * Check permission for user have add/edit page by searching first name
	 * check user can view/edit page
	 */
	@Test
	public void test04_AddPermissionForUserBySearchingUserByFirstName() {

		String PAGE_NAME1 = "add permission by search first name";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV2(PAGE_NAME1, "James", DATA_USER3, 2);

		info("Check after edit user");

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, DATA_USER3);
	}

	/**
	 * Qmetry ID: 69685
	 *  case05: Add permission for user by Searching Last Name
	 * add page
	 * Check permission for user have add/edit page by searching last name
	 * check user can view/edit page
	 */
	@Test
	public void test05_AddPermissionForUserBySearchingUserByLastName() {

		String PAGE_NAME1 = "add permission by search last name";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV2(PAGE_NAME1, "Davis", DATA_USER3, 3);

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, DATA_USER3);
	}

	/**
	 * Qmetry ID: 69681
	 *  case06: Add permission for user by Searching Email Name
	 * add page
	 * Check permission for user have add/edit page by searching email name
	 * check user can view/edit page
	 */
	@Test
	public void test06_AddPermissionForUserBySearchingUserByEmailName() {

		String PAGE_NAME1 = "add permission by search email name";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV2(PAGE_NAME1, "james.davis@acme.exoplatform.com", DATA_USER3, 4);

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, DATA_USER3);
	}

	/**
	 * Qmetry ID: 69677
	 *  case07: Add permission for muilti user at the same time
	 * add page
	 * Check permission for multi-user have add/edit page 
	 * check user can view/edit page
	 */
	@Test
	public void test07_AddPermissionForMultiUser() {

		String PAGE_NAME1 = "add permission for multi user";

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV2(PAGE_NAME1, "james/mary", DATA_USER3);

		editSpacePermission(DATA_USER2, true, true, true, true, 2);

		magAcc.userSignIn(ManageAccount.userType.PUBLISHER);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		deleteSpacePermission(DATA_USER2);

		resetDataWikiSpacePermission(ELEMENT_PAGE1, DATA_USER3);
	}

	/* case08: Add permission for user by putting group name
	 * add page
	 * Check permission for user have add/edit page by putting group name
	 * check user can view/edit page
	 */
	@Test
	public void test08_AddPermissionForGroupByPuttingGroupName() {

		String PAGE_NAME1 = "Add permission for group by putting group name";

		String[] user3={"*:/platform/users"};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV1(PAGE_NAME1, 0, user3, "*:/platform/users");

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		info("reset data");

		resetDataWikiSpacePermission(ELEMENT_PAGE1, "*:/platform/users");
	}

	/**Test Case ID: 69672
	 *  case09: Add permission for user by selecting directly
	 * add page
	 * Check permission for user have add/edit page by selecting directly
	 * check user can view/edit page
	 */
	@Test
	public void test09_AddPermissionForGroupBySelectingDirectly() {

		String PAGE_NAME1 = "Add permission for group by selecting directly";

		String[] user3={"Development/Select this Group"};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV1(PAGE_NAME1, 2, user3, "*:/developers");

		magAcc.userSignIn(ManageAccount.userType.DEVELOPER);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, "*:/platform/users");
	}

	/* case10: Add permission for user by putting group membership name
	 * add page
	 * Check permission for user have add/edit page by putting group membership name
	 * check user can view/edit page
	 */
	@Test
	public void test10_AddPermissionForGroupByPuttingGroupMemebershipName() {

		String PAGE_NAME1 = "Add permission for group by putting group membership name";

		String[] user4={"Platform/Content Management", "author"};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV1(PAGE_NAME1, 3, user4, "author:/platform/web-contributors");

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, "author:/platform/web-contributors");
	}

	/* case11: Add permission for group by selecting
	 * add page
	 * Check permission for user have add/edit page 
	 * check user can view/edit page
	 */
	@Test
	public void test11_AddPermissionForGroupBySelecting() {

		String PAGE_NAME1 = "Add permission for group by selecting group membership name";

		String[] user4={"Platform/Users","member"};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		prepareDataWikiSpacePermissionV1(PAGE_NAME1, 3, user4, "member:/platform/users");

		magAcc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		waitForAndGetElement(ELEMENT_EDIT_PAGE_LINK);

		info("reset data");

		magAcc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		resetDataWikiSpacePermission(ELEMENT_PAGE1, "member:/platform/users");		
	}

	/////
	// Make a group of common code for this class
	/////
	/**
	 *<li>Add a blank wiki page</li>
	 *<li>Delete a space permission for Group: Any</li>
	 *<li>Add a space permission</li>
	 *<li><Edit a space permission</li> 
	 */
	public void prepareDataWikiSpacePermissionV1(String pageName, int option, String[] userGroupToAddPermission, String userGroupToEditPermission){
		goToWiki();
		addBlankWikiPage(pageName, pageName, 0);
		deleteSpacePermission("any");
		addSpacePermission(option, userGroupToAddPermission, 2);
		editSpacePermission(userGroupToEditPermission, true, true, true, true, 2);
	}

	/**
	 *<li>Add a blank wiki page</li>
	 *<li>Go to Space Permission</li>
	 *<li>Delete Space Permission Group: Any</li>
	 *<li>Select an User Permission</li> 
	 *<li>Edit Space Permission </li>
	 */
	public void prepareDataWikiSpacePermissionV2(String pageName, String selectUserPermission, String username, int...type){
		goToWiki();
		addBlankWikiPage(pageName, pageName, 0);
		goToSpacePermission();
		deleteSpacePermission("any");
		click(ELEMENT_SELECT_USER);
		per.selectUserPermission(selectUserPermission, type);
		click(button.ELEMENT_ADD_BUTTON);
		button.save();
		waitForMessage(MSG_PERMISSION_SAVE);
		click(button.ELEMENT_OK_BUTTON);
		editSpacePermission(username, true, true, true, true, 2);
	}

	/**
	 * Reset data after testing
	 */
	public void resetDataWikiSpacePermission(By pageName, String userGroupToDeletePermission){
		String[] user = {"any"};
		click(pageName);
		deleteCurrentWikiPage();
		deleteSpacePermission(userGroupToDeletePermission);
		addSpacePermission(0, user, 2);
		editSpacePermission("any", true, true, false, false, 2);
	}
}