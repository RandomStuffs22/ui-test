package org.exoplatform.selenium.platform.wiki.functional.spacepermission;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.exoplatform.selenium.platform.ManageAccount;
import static org.exoplatform.selenium.TestLogger.info;

/**
 *
 * @author HangNTT
 * @date: 18/12/2012
 */
public class Wiki_SpacePermission_Others extends BasicAction {

	ManageAccount magAc;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS);
	}
	
	/**
	 * Qmetry ID: 69708
	 * case01: Check when user/group does not admin page permission
	 * add page
	 * add permission for space
	 * Check permission for user/group does not admin page 
	 */
	@Test
	public void test01_CheckWhenUserOrGroupDoesNotAdminPagePermission() {

		String PAGE_NAME1 = "Check When user does not admin page permission";

		String[] user2= {"james"};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		goToWiki();

		addSpacePermission(0, user2);

		editSpacePermission("james", true, true, false, false, 2);

		click(ELEMENT_WIKI_HOME);

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		magAc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		click (ELEMENT_MORE_LINK);

		waitForElementNotPresent(ELEMENT_PAGE_PERMISSION_LINK);

		magAc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();

		deleteSpacePermission("james");
	}
	
	/**
	 * Qmetry ID: 69708
	 * case02: Check when user/group does not admin space permission
	 * add page
	 * add permission for space without admin space permission 
	 * Check permission for user/group does not admin space page 
	 */
	@Test
	public void test02_CheckWhenUserOrGroupDoesNotSpacePagePermission() {

		String PAGE_NAME1 = "Check When user does not space page permission";

		String[] user2= {"james"};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		goToWiki();

		addSpacePermission(0, user2);

		editSpacePermission("james", true, true, true, false, 2);

		click(ELEMENT_WIKI_HOME);

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);
		
		//goToWikiWithUser(ManageAccount.userType.AUTHOR);
		goToWikiPage("Wiki Home/" + PAGE_NAME1, ManageAccount.userType.AUTHOR);
		 
		//waitForElementNotPresent(ELEMENT_BROWSE_LINK);
		mouseOverAndClick(ELEMENT_BROWSE_LINK);
		waitForElementNotPresent(ELEMENT_WIKI_SETTING_LINK);

		magAc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();

		deleteSpacePermission("james");
	}

	/**
	 * Qmetry ID: 69710
	 * case03: Check when user/group does not permission to edit page
	 * add page
	 * add permission for space
	 * Check permission for user/group does not permission to edit page
	 */
	@Test
	public void test03_CheckWhenUserOrGroupDoesNotPermissionToEditPage() {

		String PAGE_NAME1 = "Check When user does not space permission to edit page";

		String[] user2= {"james"};

		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		boolean[] pageOrSpace1 = {true, false};
		
		boolean[] pageOrSpace2 = {false, true};

		boolean[] type = {true,false};

		goToWiki();

		addSpacePermission(0, user2);

		editSpacePermission("any", true, false, false, false, 2);

		click(ELEMENT_WIKI_HOME);

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);
		
		verifyPermissions(pageOrSpace2, type, "james", 2);
		click(ELEMENT_WIKI_HOME_LINK);
		click(ELEMENT_PAGE1);
		verifyPermissions(pageOrSpace1, type, "james", 2);

		magAc.userSignIn(ManageAccount.userType.AUTHOR);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		//waitForElementNotPresent(ELEMENT_MINOR_EDIT_BUTTON);
		waitForElementNotPresent(ELEMENT_EDIT_PAGE_LINK);

		info("Reset data");

		magAc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();

		editSpacePermission("any", true, true, false, false, 2);

		deleteSpacePermission("james");
	}
	
	/** 
	 * Qmetry ID: 69713
	 * case04: Check when user/group does not permission to view page
	 * add page
	 * add permission for space
	 * Check permission for user/group does not permission to view page
	 */
	@Test
	public void test04_CheckWhenUserOrGroupDoesNotPermissionToViewPage() {

		String VERIFY_MESSAGE = "Page Not Found";

		String[] user2= {"any"};

		goToWiki();

		editSpacePermission("any", false, false, false, false, 2);

		magAc.userSignIn(ManageAccount.userType.AUTHOR);
		
		//goToWiki();
		click(ELEMENT_WIKI_LINK);

		waitForTextPresent(VERIFY_MESSAGE);

		info("Reset data");

		magAc.userSignIn(ManageAccount.userType.ADMIN);
		
		goToWiki();

		addSpacePermission(0, user2);

		editSpacePermission("any", true, true, false, false, 2);
	}
	
	@AfterMethod
	public void afterTest(){
		//signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}