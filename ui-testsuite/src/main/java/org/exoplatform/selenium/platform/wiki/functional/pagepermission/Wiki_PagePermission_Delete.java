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
public class Wiki_PagePermission_Delete extends BasicAction{
	
	ManageAccount magAc;
	
	public String DATA_USER_ADMIN = DATA_USER1;
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
		
	/*case01: Delete permission
	 * Add page
	 * set view/edit page permission for user/group/membership
	 * delete permission
	 * check user can not view/edit page
	 */
	@Test
	public void test01_DeletePermission_User(){
		String title = "KS_Wiki_PagePermission_Delete_Page_01_1";
		String content = "KS_Wiki_PagePermission_Delete_Page_Content_01_1";
		String new_content = "KS_Wiki_PagePermission_Delete_Page_Content_new_01_1";
		By element_page = By.linkText(title);
		String user = DATA_USER3;
		String[] userGroup = {user};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
				
		info("Add user has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(1, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		
		info("Add edit page permission for " + user);
		checkAndEditPagePermission(user, 2);
		magAc.signOut();
		
		info("Check user can view/edit wiki page");
		magAc.signIn(user, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();
		
		deletePermissionWithUserAdmin(user, element_page);
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);
		
		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}
	
	@Test
	public void test01_DeletePermission_Group(){
		String title = "KS_Wiki_PagePermission_Delete_Page_01_2";
		String content = "KS_Wiki_PagePermission_Delete_Page_Content_01_2";
		String new_content = "KS_Wiki_PagePermission_Delete_Page_Content_new_01_2";
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
		
		info("Add edit page permission for " + group);
		checkAndEditPagePermission(group, 2);
		magAc.signOut();
		
		info("Check user James belong to group can view/edit wiki page");
		magAc.signIn(DATA_USER3, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);
		magAc.signOut();
		
		deletePermissionWithUserAdmin(group, element_page);
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);
		
		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}
	
	@Test
	public void test01_DeletePermission_Membership(){
		String title = "KS_Wiki_PagePermission_Delete_Page_01_3";
		String content = "KS_Wiki_PagePermission_Delete_Page_Content_01_3";
		String new_content = "KS_Wiki_PagePermission_Delete_Page_Content_new_01_3";
		By element_page = By.linkText(title);
		String group = "redactor:/platform/web-contributors";
		String[] userGroup = {"Platform/Content Management", "redactor"};
		
		info("Add a wiki page");
		addBlankWikiPage(title, content, 0);
		
		info("Add membership has permission default");
		deletePagePermission("any");
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		addPagePermission(3, userGroup);
		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_POPUP);
		
		info("Add edit page permission for " + group);
		checkAndEditPagePermission(group, 2);
		magAc.signOut();
		
		info("Check user James belong to group and has membership can view/edit wiki page");
		magAc.signIn(DATA_USER3, DATA_PASS_ADMIN);
		checkViewEditPage(element_page, content, new_content);		
		magAc.signOut();
				
		deletePermissionWithUserAdmin(group, element_page);
		checkViewPage(ManageAccount.userType.AUTHOR, element_page);
		
		//delete page
		//deleteWikiPageWithUserAdmin(element_page);
		String[] wikiPath = {"Wiki Home/" + title};
		resetDataByDeleteWikiPage(ManageAccount.userType.ADMIN, wikiPath);
	}
}