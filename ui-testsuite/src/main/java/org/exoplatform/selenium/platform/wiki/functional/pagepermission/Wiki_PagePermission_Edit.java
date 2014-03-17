package org.exoplatform.selenium.platform.wiki.functional.pagepermission;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 18/12/2012
 * @author lientm
 *
 */
public class Wiki_PagePermission_Edit extends BasicAction {	
	ManageAccount magAc;
	
	public String DATA_USER_ADMIN = "john";
	public String DATA_PASS_ADMIN = DATA_PASS;
	
	@BeforeMethod
	public void beforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn(DATA_USER_ADMIN, DATA_PASS_ADMIN);		
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
		
	/*case01: Edit permission when change value
	 * add page
	 * add user/group/memebership permission for page
	 * check user can view page but can not edit page (default)
	 * add edit page permission
	 * check user can edit page
	 * remove view/edit pege permission 
	 * check user can not see page
	 */
	@Test
	public void test01_EditPermissionWhenChangeValue_User(){
		String title = "KS_Wiki_PagePermission_Edit_Page_01_1";
		String content = "KS_Wiki_PagePermission_Edit_Page_Content_01_1";
		String new_content = "KS_Wiki_PagePermission_Edit_Page_Content_new_01_1";
		By element_page = By.linkText(title);
		String user = "james";
		String[] userGroup = {user};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
		
		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();
		
		checkEditPage(ManageAccount.userType.AUTHOR, element_page, content);
		
		addEditPagePermission(ManageAccount.userType.ADMIN, user, element_page);
		
		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut(); 
		
		removePagePermission(ManageAccount.userType.ADMIN, element_page, user);
		
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);
		
		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}
	
	@Test
	public void test01_EditPermissionWhenChangeValue_Group(){
		String title = "KS_Wiki_PagePermission_Edit_Page_01_2";
		String content = "KS_Wiki_PagePermission_Edit_Page_Content_01_2";
		String new_content = "KS_Wiki_PagePermission_Edit_Page_Content_new_01_2";
		By element_page = By.linkText(title);
		String group = "*:/platform/web-contributors";
		String[] userGroup = {"Platform/Content Management"};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
		
		info("Add group has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(2, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();
		
		info("Check user James belong to group can view wiki page");
		checkEditPage(ManageAccount.userType.AUTHOR, element_page, content);
		
		addEditPagePermission(ManageAccount.userType.ADMIN, group, element_page);
		
		info("Check user can view/edit wiki page");
		magAc.signIn("james", DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut(); 
		
		removePagePermission(ManageAccount.userType.ADMIN, element_page, group);
		
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);
		
		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}
	
	@Test
	public void test01_EditPermissionWhenChangeValue_Membership(){
		String title = "KS_Wiki_PagePermission_Edit_Page_01_3";
		String content = "KS_Wiki_PagePermission_Edit_Page_Content_01_3";
		String new_content = "KS_Wiki_PagePermission_Edit_Page_Content_new_01_3";
		By element_page = By.linkText(title);
		String group = "redactor:/platform/web-contributors";
		String[] userGroup = {"Platform/Content Management", "redactor"};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
		
		info("Add group has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(3, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		magAc.signOut();
		
		info("Check user James belong to group and has membership can view wiki page");
		checkEditPage(ManageAccount.userType.AUTHOR, element_page, content);
		
		addEditPagePermission(ManageAccount.userType.ADMIN, group, element_page);
		
		info("Check user can view/edit wiki page");
		magAc.signIn("james", DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut(); 
		
		removePagePermission(ManageAccount.userType.ADMIN, element_page, group);
		
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);
		
		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}
	
	/* case02: Edit permission when values are blank
	 * add page
	 * add page permission for user/group/membership
	 * remove view/edit page
	 * check user/group/membership permission is not listed in page permission list
	 */
	@Test
	public void test02_EditPermissionWhenValueBlank_User(){
		String title = "KS_Wiki_PagePermission_Edit_Page_02_1";
		String content = "KS_Wiki_PagePermission_Edit_Page_Content_02_1";
		String user = "james";
		String[] userGroup = {user};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
		
		info("Add user has permission default");
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		editPagePermission(user, false, false, false, 2);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		
		checkUserNotInPagePermission(user);
		
		//delete page
		deleteCurrentWikiPage();
	}
	
	@Test
	public void test02_EditPermissionWhenValueBlank_Group(){
		String title = "KS_Wiki_PagePermission_Edit_Page_02_2";
		String content = "KS_Wiki_PagePermission_Edit_Page_Content_02_2";
		String group = "*:/platform/web-contributors";
		String[] userGroup = {"Platform/Content Management"};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
		
		info("Add group has permission default");
		addPagePermission(2, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		editPagePermission(group, false, false, false, 2);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		
		checkUserNotInPagePermission(group);
		
		//delete page
		deleteCurrentWikiPage();
	}
	
	@Test
	public void test02_EditPermissionWhenValueBlank_Membership(){
		String title = "KS_Wiki_PagePermission_Edit_Page_02_3";
		String content = "KS_Wiki_PagePermission_Edit_Page_Content_02_3";
		String group = "redactor:/platform/web-contributors";
		String[] userGroup = {"Platform/Content Management", "redactor"};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
		
		info("Add membership has permission default");
		addPagePermission(3, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		editPagePermission(group, false, false, false, 2);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		
		checkUserNotInPagePermission(group);
		
		//delete page
		deleteCurrentWikiPage();
	}
}
