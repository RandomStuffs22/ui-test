package org.exoplatform.selenium.platform.wiki.functional.pagepermission;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformPermission;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import static org.exoplatform.selenium.TestLogger.info;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 17/12/2012
 * @author lientm
 *
 */
public class Wiki_PagePermission_Add extends BasicAction {

	ManageAccount magAc;
	Button button;
	PlatformPermission per;
	
	public String DATA_USER_ADMIN = "john";
	public String DATA_PASS_ADMIN = DATA_PASS;

	@BeforeMethod
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		button = new Button(driver);
		per = new PlatformPermission(driver);
		magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);	
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/* case 01: Add permission for user by putting user's name
	 * add page
	 * set permission for user have view/edit permission by putting user's name
	 * check user can view/edit page
	 */
	@Test
	public void test01_AddPermissionForUser_PutUserName(){
		String title = "KS_Wiki_PagePermission_Add_Page_01";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_01";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_01";
		By element_page = By.linkText(title);
		String user = "james";
		String[] userGroup = {user};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default by putting user's name");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(0, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		checkAndEditPagePermission(user, 2);
		magAc.signOut();

		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/*case02: Add permission for user by selecting directly
	 * add page
	 * set permission for user have view/edit permission by select user
	 * check user can view/edit page
	 */
	@Test
	public void test02_AddPermissionForUser_SelectUser(){
		String title = "KS_Wiki_PagePermission_Add_Page_02";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_02";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_02";
		By element_page = By.linkText(title);
		String user = "james";
		String[] userGroup = {user};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default by selecting directly");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		checkAndEditPagePermission(user, 2);
		magAc.signOut();

		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/*case03: Add permission for user by searching user by user name
	 * add page
	 * set permission for user have view/edit permission by search user name
	 * check user can view/edit page
	 */
	@Test
	public void test03_AddPermissionForUser_SearchUserName(){
		String title = "KS_Wiki_PagePermission_Add_Page_03";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_03";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_03";
		By element_page = By.linkText(title);
		String user = "james";

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default by searching user by user name");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		goToPagePermission();
		click(ELEMENT_SELECT_USER);
		per.selectUserPermission(user, 1);

		click(button.ELEMENT_ADD_BUTTON);
		button.save();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Set edit page permission for user");
		checkAndEditPagePermission(user, 2);
		magAc.signOut();

		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/* case04: Add permission for user by searching user by first name
	 * add page
	 * set permission for user have view/edit permission by search first name
	 * check user can view/edit page
	 */
	@Test
	public void test04_AddPermissionForUser_SearchFirstName(){
		String title = "KS_Wiki_PagePermission_Add_Page_04";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_04";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_04";
		By element_page = By.linkText(title);
		String user = "james";

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default by searching user by first name");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		goToPagePermission();
		click(ELEMENT_SELECT_USER);
		per.selectUserPermission("James", 2);

		click(button.ELEMENT_ADD_BUTTON);
		button.save();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Set edit page permission for user");
		checkAndEditPagePermission(user, 2);
		magAc.signOut();

		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/* case05: Add permission for user by searching user by last name
	 * add page
	 * set permission for user have view/edit permission by search last name
	 * check user can view/edit page
	 */
	@Test
	public void test05_AddPermissionForUser_SearchLastName(){
		String title = "KS_Wiki_PagePermission_Add_Page_05";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_05";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_05";
		By element_page = By.linkText(title);
		String user = "james";

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default by searching user by last name");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		goToPagePermission();
		click(ELEMENT_SELECT_USER);
		per.selectUserPermission("Davis", 3);

		click(button.ELEMENT_ADD_BUTTON);
		button.save();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Set edit page permission for user");
		checkAndEditPagePermission(user, 2);
		magAc.signOut();

		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/* case06: Add permission for user by searching user by email
	 * add page
	 * set permission for user have view/edit permission by search email
	 * check user can view/edit page
	 */
	@Test
	public void test06_AddPermissionForUser_SearchEmail(){
		String title = "KS_Wiki_PagePermission_Add_Page_06";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_06";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_06";
		By element_page = By.linkText(title);
		String user = "james";

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add user has permission default by searching user by email");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		goToPagePermission();
		click(ELEMENT_SELECT_USER);
		per.selectUserPermission("james.davis@acme.exoplatform.com", 4);

		click(button.ELEMENT_ADD_BUTTON);
		button.save();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Set edit page permission for user");
		checkAndEditPagePermission(user, 2);
		magAc.signOut();

		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/* case07: Add permission for multi-user at the same time
	 * add page
	 * set permission for multi-user have view/edit permission
	 * check user can view/edit page
	 */
	@Test
	public void test07_AddPermissionForMultiUser(){
		String title = "KS_Wiki_PagePermission_Add_Page_07";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_07";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_07";
		By element_page = By.linkText(title);
		String user1 = "james";
		String user2 = "mary";

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add multi-user have permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		goToPagePermission();
		click(ELEMENT_SELECT_USER);		
		per.selectUserPermission("mary/james");

		click(button.ELEMENT_ADD_BUTTON);
		button.save();
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);

		info("Set edit page permission for users");
		checkAndEditPagePermission(user1, 2);
		checkAndEditPagePermission(user2, 2);
		magAc.signOut();

		info("Check user james can view/edit wiki page");
		magAc.signIn(user1, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		info("Check user mary can view/edit wiki page");
		magAc.signIn(user2, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, new_content, content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);	
	}

	/* case08: Add permission for group by putting group's name
	 * add page
	 * set permission for group have view/edit permission by putting group's name
	 * check user belong to group can view/edit page  
	 */
	@Test
	public void test08_AddPermissionForGroup_PutGroupName(){
		String title = "KS_Wiki_PagePermission_Add_Page_08";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_08";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_08";
		By element_page = By.linkText(title);
		String group = "*:/platform/web-contributors";
		String[] userGroup = {group};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add group has permission default by putting group's name");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(0, userGroup);		

		info("Set edit page permission for group");
		checkAndEditPagePermission(group, 2);
		magAc.signOut();

		info("Check user James belong to group can view/edit wiki page");
		magAc.signIn("james", DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/* case09: Add permission for group by selecting directly
	 * add page
	 * set permission for group have view/edit permission by selecting directly
	 * check user belong to group can view/edit page 
	 */
	@Test
	public void test09_AddPermissionForGroup_SelectGroup(){
		String title = "KS_Wiki_PagePermission_Add_Page_09";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_09";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_09";
		By element_page = By.linkText(title);
		String group = "*:/platform/web-contributors";
		String[] userGroup = {"Platform/Content Management"};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add group has permission default by selecting directly");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(2, userGroup);

		info("Set edit page permission for group");
		checkAndEditPagePermission(group, 2);
		magAc.signOut();

		info("Check user James belong to group can view/edit wiki page");
		magAc.signIn("james", DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/*case10: Add permission for user by putting group's membership
	 * add page
	 * set permission for group's membership have view/edit permission by putting group's membership
	 * check user belong to group can view/edit page 
	 */
	@Test
	public void test10_AddPermissionForUser_PutGroupMembership(){
		String title = "KS_Wiki_PagePermission_Add_Page_10";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_10";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_10";
		By element_page = By.linkText(title);
		String group = "redactor:/platform/web-contributors";
		String[] userGroup = {group};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add group has permission default by putting group's membership");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(0, userGroup);		

		info("Set edit page permission for group's membership");
		checkAndEditPagePermission(group, 2);
		magAc.signOut();

		info("Check user James belong to group and has membership can view/edit wiki page");
		magAc.signIn("james", DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		info("Check user Mary belong to group but does not have membership that can not view/edit wikipage");
		magAc.signIn("mary", DATA_PASS_ADMIN);
		goToWiki();
		waitForElementNotPresent(element_page);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}

	/* case11: Add permission for group's membership  by selecting directly
	 * add page
	 * set permission for group's membership have view/edit permission by selecting directly
	 * check user belong to group can view/edit page 
	 */
	@Test
	public void test11_AddPermissionForGroup_SelectGroupMembership(){
		String title = "KS_Wiki_PagePermission_Add_Page_11";
		String content = "KS_Wiki_PagePermission_Add_Page_Content_11";
		String new_content = "KS_Wiki_PagePermission_Add_Page_Content_new_11";
		By element_page = By.linkText(title);
		String group = "redactor:/platform/web-contributors";
		String[] userGroup = {"Platform/Content Management", "redactor"};

		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);

		info("Add group's membership has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(3, userGroup);		

		info("Set edit page permission for group's membership");
		checkAndEditPagePermission(group, 2);
		magAc.signOut();

		info("Check user James belong to group and has membership can view/edit wiki page");
		magAc.signIn("james", DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();

		info("Check user Mary belong to group but does not have membership that can not view/edit wikipage");
		magAc.signIn("mary", DATA_PASS_ADMIN);
		goToWiki();
		waitForElementNotPresent(element_page);
		magAc.signOut();

		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}
}
