package org.exoplatform.selenium.platform.ecms.functional.admin.repository;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageCategory;
import org.exoplatform.selenium.platform.ecms.admin.Permission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * @author: VuNA
 * @date: 02/10/2012
 */
public class ECMS_Admin_ManageLock extends PlatformBase{

	//Platform
	UserGroupManagement userGroup;
	NavigationToolbar nav;
	ManageAccount magAcc;
	Dialog dialog;
	Button button;
	//Ecms
	EcmsBase ecms;
	ActionBar actBar;
	ContextMenu cMenu;
	ManageCategory magCa;
	ContentTemplate cTemplate;
	ECMainFunction ecMain;
	Permission adminPer;

	public String DATA_USER = "john";
	//public String DATA_PASS = DATA_PASS;

	//Data for these test cases
	public By ELEMENT_GROUP_MEMBERSHIP = By.linkText("manager");
	public String ELEMENT_GROUP_MEMBERSHIP_DISPLAY = "manager";
	public String ELEMENT_GROUP_TO_SELECT = "Platform/Content Management";
	public String ELEMENT_GROUP_DISPLAY = "platform/web-contributors";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER);
		userGroup = new UserGroupManagement(driver);
		nav = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		dialog = new Dialog(driver);
		ecms = new EcmsBase(driver);
		magCa = new ManageCategory(driver);
		cMenu = new ContextMenu(driver);
		ecMain = new ECMainFunction(driver);
		adminPer = new Permission(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		driver.quit();
	}

	/*-- Case ID 001
	 *-- Unlock node when administrator has permission to view node
	 * --*/
	@Test
	public void test01_UnlockNodeWhenAdminHasPermissionToViewNode(){
		String titleArticle = "test01";
		String nameArticle = "test01";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleToLock = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleArticle));

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		info("-- Step 3: Unlock node by administrator --");
		//Select “Locked Node” tab

		//click(cMenu.ELEMENT_UNLOCK_ARTICLE);
		click(ecMain.ELEMENT_UNLOCK_NODE.replace("${lockedNode}", titleArticle));

		waitForTextNotPresent(titleArticle);

		info("-- Restore original data --");

		nav.goToSiteExplorer();

		cMenu.deleteDocument(elementArticleToLock);

		info("-- SignOut--");

		//logoutEcms();
	}

	/*-- Case ID 002
	 *-- Unlock node when administrator does not have permission to view node
	 *--*/
	@Test
	public void test02_UnlockNodeWhenAdminDoesNotHavePermissionToViewNode(){
		String titleArticle = "test02";
		String nameArticle = "test02";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleLockedByAdmin = By.xpath(cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", titleArticle));

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		driver.close();

		info("-- Login with admin who does not have permission to unlock node --");

		initNewDriverSeleniumTest();
		dialog = new Dialog(driver);
		magAcc = new ManageAccount(driver);
		nav = new NavigationToolbar(driver);
		cMenu = new ContextMenu(driver);
		ecms = new EcmsBase(driver);
		magAcc.signIn("mary", DATA_PASS);

		info("-- Step 2: Open form to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		info("-- Step 3: Unlock node by administration with admin who does not have permission --");
		//Select “Locked Node” tab

		//click(cMenu.ELEMENT_UNLOCK_ARTICLE);
		click(ecMain.ELEMENT_UNLOCK_NODE.replace("${lockedNode}", titleArticle));

		waitForMessage(dialog.MESSAGE_UNLOCK_WITHOUT_PERMISSION);

		dialog.closeMessageDialog();

		info("-- Restore original data --");
		//logoutEcms();
		magAcc.signOut();	

		magAcc.signIn(DATA_USER, DATA_PASS);

		nav.goToSiteExplorer();

		ecms.goToNode("intranet/documents");

		cMenu.deleteDocument(elementArticleLockedByAdmin);

		info("-- SignOut --");
	}

	/*-- Case ID 003
	 *-- Show list locked node when node is renamed
	 * --*/
	@Test
	public void test03_ShowListLockedNodeWhenNodeIsRenamed(){
		String titleArticle = "test03";
		String nameArticle = "test03";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleToLock = By.linkText(titleArticle);

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		info("-- Step 3: Rename node --");

		nav.goToSiteExplorer();

		//Rename locked node
		rightClickOnElement(elementArticleToLock);

		click(cMenu.ELEMENT_MENU_RENAME_NODE);

		waitForTextPresent("Rename");

		//type(cMenu.ELEMENT_INPUT_TITLE_NODE, "newsTest03", true);

		type(cMenu.ELEMENT_INPUT_RENAME_NODE, "newstest03", true);

		//button.save();
		button.rename();

		waitForTextPresent("newstest03");

		info("-- Step 4: Check list locked node after renaming --");

		//goToAdminManageLockAndVerify("newsTest03");
		nav.goToContentAdministration();
		waitForAndGetElement("//*[contains(text(), 'newstest03')]");

		info("-- Restore original data --");

		nav.goToSiteExplorer();

		cMenu.deleteDocument(By.xpath(cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", "newstest03")));

		info("-- SignOut --");

		//signOut();
	}

	/*-- Case ID 004
	 *-- Show list locked node when node is copied to other path
	 *--*/
	@Test
	public void test04_ShowListLockedNodeWhenNodeIsCopiedToOtherPath(){
		String titleArticle = "test04";
		String nameArticle = "test04";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		String elementLockedArticleIcon = cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", titleArticle);
		By elementCopiedArticleUnLock = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleArticle));	
		By elementArticleToLock = By.xpath(elementLockedArticleIcon);

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		info("-- Step 3: Copy and paste node other path --");

		nav.goToSiteExplorer();

		//Copy locked node to intranet
		rightClickOnElement(elementLockedArticleIcon);
		if(waitForAndGetElement(ELEMENT_COPY_NODE,5000,0)!=null)
			click(ELEMENT_COPY_NODE);
		else
			click(ELEMENT_ECMS_COPY_NODE);

		ecms.goToNode("intranet");

		pasteNode(By.linkText("intranet"));

		waitForTextPresent(titleArticle);

		info("-- Step 4: Check list locked node after coyping --");

		//goToAdminManageLockAndVerify(titleArticle);
		nav.goToContentAdministration();
		//waitForTextPresent("/sites/intranet/documents/test04");
		//waitForTextNotPresent("/sites/intranet/test04");
		waitForTextPresent("test04");
		Utils.captureScreen("Check_List_Locked_Node_After_Coyping");

		info("-- Restore original data --");

		nav.goToSiteExplorer();

		cMenu.deleteDocument(elementCopiedArticleUnLock);

		ecms.goToNode("documents");

		cMenu.deleteDocument(elementArticleToLock);

		info("-- SignOut--");

		//magAcc.signOut();
	}

	/*-- Case ID 005
	 *-- Show list locked node when node is deleted
	 * --*/
	@Test
	public void test05_ShowListLockedNodeWhenNodeIsDeleted(){
		String titleArticle = "test05";
		String nameArticle = "test05";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleToLock = By.xpath(cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", titleArticle));

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		info("-- Step 3: Delete locked node --");

		nav.goToSiteExplorer();

		//Delete locked node
		cMenu.deleteDocument(elementArticleToLock);

		info("-- Step 4: Check list locked node after deleting node--");

		nav.goToContentAdministration();

		//click(ELEMENT_REPOSITORY_TAB);

		//click(ELEMENT_MANAGE_LOCKS);

		waitForTextNotPresent(titleArticle);

		info("-- SignOut --");	

		//magAcc.signOut();
	}

	/*-- Case ID 006
	 *-- Add permission for user to unlock node
	 *--*/
	@Test
	public void test06_AddPermissionForUserToUnLockNode(){

		String titleArticle = "test06";
		String nameArticle = "test06";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleToLock = By.xpath(cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", titleArticle));

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to add permission to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		click(ELEMENT_MANAGE_LOCK_TAB);

		info("-- Step 3: Add permission for group to unlock node --");

		userGroup.selectGroup(ELEMENT_GROUP_TO_SELECT);

		click(ELEMENT_GROUP_MEMBERSHIP);

		//Groups are listed on the table
		waitForTextPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		click(adminPer.ELEMENT_DELETE_SELECTED_GROUP.replace("${selectedGroup}", ELEMENT_GROUP_DISPLAY));

		waitForTextNotPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		info("-- Restore original data --");

		nav.goToSiteExplorer();

		//Delete locked node
		cMenu.deleteDocument(elementArticleToLock);

		info("-- SignOut--");

		//magAcc.signOut();
	}

	/*-- Case ID 007
	 *-- Delete permission of group
	 *--*/
	@Test
	public void test07_DeletePermissionOfGroup(){
		String titleArticle = "test07";
		String nameArticle = "test07";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleUnLock = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleArticle));

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to add permission to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		click(ELEMENT_MANAGE_LOCK_TAB);

		info("-- Step 3: Add permission for group to unlock node --");

		userGroup.selectGroup(ELEMENT_GROUP_TO_SELECT);

		click(ELEMENT_GROUP_MEMBERSHIP);

		//Groups are listed on the table
		waitForTextPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		driver.close();

		initNewDriverSeleniumTest();
		//WebDriver driverTest = initNewDriverSeleniumTest();
		magAcc = new ManageAccount(driver);
		ecMain = new ECMainFunction(driver);
		nav = new NavigationToolbar(driver);
		cMenu = new ContextMenu(driver);
		adminPer = new Permission(driver);

		info("-- Login with user in the selected group and un lock that node --");

		ecms = new EcmsBase(driver);
		magAcc.signIn("mary", DATA_PASS);

		goToAdminManageLockAndVerify(titleArticle);

		//click(cMenu.ELEMENT_UNLOCK_ARTICLE);
		click(ecMain.ELEMENT_UNLOCK_NODE.replace("${lockedNode}", titleArticle));

		waitForTextNotPresent(titleArticle);

		info("-- Step 4: Delete group --");

		magAcc.signOut();

		//driverTest.get(baseUrl);

		magAcc.signIn(DATA_USER, DATA_PASS);

		ecMain.goToManageLockTab();

		click(adminPer.ELEMENT_DELETE_SELECTED_GROUP.replace("${selectedGroup}", ELEMENT_GROUP_DISPLAY));

		waitForTextNotPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		info("-- Restore original data --");

		nav.goToSiteExplorer();

		ecms.goToNode("intranet/documents");

		//Delete locked node
		cMenu.deleteDocument(elementArticleUnLock);

		info("-- Sign Out--");
	}

	/*-- Case ID 008
	 *-- Delete permission of *:/platform/administrators group
	 *--*/
	@Test
	public void test08_DeletePermissionOfGroupAdmin(){
		String titleArticle = "test08";
		String nameArticle = "test08";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleToLock = By.xpath(cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", titleArticle));

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to add permission to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		click(ELEMENT_MANAGE_LOCK_TAB);

		info("-- Step 3: Add permission for group to unlock node --");

		userGroup.selectGroup(ELEMENT_GROUP_TO_SELECT);

		click(ELEMENT_GROUP_MEMBERSHIP);

		//Groups are listed on the table
		waitForTextPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		click(adminPer.ELEMENT_DELETE_SELECTED_GROUP.replace("${selectedGroup}", ELEMENT_GROUP_DISPLAY));

		waitForTextNotPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		info("-- Step 4: Delete permission of *:/platform/administrators group --");

		//click(ELEMENT_DELETE_ADMIN_GROUP);
		click(adminPer.ELEMENT_DELETE_SELECTED_GROUP.replace("${selectedGroup}", "platform/administrators"));

		waitForMessage(dialog.MESSAGE_CANNOT_DELETE_ADMIN_GROUP);

		dialog.closeMessageDialog();

		info("-- Restore original data --");

		nav.goToSiteExplorer();

		cMenu.deleteDocument(elementArticleToLock);

		info("-- SignOut --");

		//magAcc.signOut();
	}

	/*-- Case ID 009
	 *-- Check unlock permission
	 *--*/
	@Test
	public void test09_CheckUnlockPermission(){
		String titleArticle = "test09";
		String nameArticle = "test09";
		String summaryArticle = "summary of article";
		String contentArticle = "content of article";
		By elementArticleToLock = By.xpath(cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", titleArticle));
		By elementArticleUnLock = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleArticle));

		info("-- Step 1: Lock node --");

		lockNodeInSE(titleArticle, nameArticle, summaryArticle, contentArticle);

		info("-- Step 2: Open form to add permission to unlock node --");

		goToAdminManageLockAndVerify(titleArticle);

		click(ELEMENT_MANAGE_LOCK_TAB);

		info("-- Step 3: Add permission for group to unlock node --");

		userGroup.selectGroup(ELEMENT_GROUP_TO_SELECT);

		click(ELEMENT_GROUP_MEMBERSHIP);

		//Groups are listed on the table
		waitForTextPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		info("-- Step 4: Check unlock permission --");

		driver.close();

		initNewDriverSeleniumTest();
		//WebDriver driverTest = initNewDriverSeleniumTest();
		magAcc = new ManageAccount(driver);
		ecMain = new ECMainFunction(driver);
		nav = new NavigationToolbar(driver);
		cMenu = new ContextMenu(driver);
		adminPer = new Permission(driver);
		actBar = new ActionBar(driver);

		info("-- Login with user in the selected group and un-lock that node --");

		ecms = new EcmsBase(driver);
		magAcc.signIn("mary", DATA_PASS);

		nav.goToSiteExplorer();

		//actBar.chooseDrive(actBar.ELEMENT_SITES_MANAGEMENT_DRIVE);
		actBar.showDrives();

		ecms.goToNode("intranet/documents");

		rightClickOnElement(elementArticleToLock);

		click(cMenu.ELEMENT_MENU_UNLOCK);

		waitForElementNotPresent(elementArticleToLock);

		magAcc.signOut();

		//driverTest.get(baseUrl);

		magAcc.signIn(DATA_USER, DATA_PASS);

		info("-- Restore original data --");

		nav.goToSiteExplorer();

		ecms.goToNode("intranet/documents");

		cMenu.deleteDocument(elementArticleUnLock);

		ecMain.goToManageLockTab();

		click(adminPer.ELEMENT_DELETE_SELECTED_GROUP.replace("${selectedGroup}", ELEMENT_GROUP_DISPLAY));

		waitForTextNotPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		info("-- SignOut --");

		//magAcc.signOut();
	}

	/*----- Auxiliary function -----*/
	public void initNewDriverSeleniumTest(){
		driver = new FirefoxDriver();
		driver.get(baseUrl);
		//return driver;
	}

	//Create a node and lock it in Sites Explorer
	public void lockNodeInSE(String titleArticle, String nameArticle, String summaryArticle, String contentArticle){
		By elementCopiedArticleUnLock = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleArticle));
		String elementLockedArticleIcon = cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", titleArticle);

		nav = new NavigationToolbar(driver);
		nav.goToSiteExplorer();

		actBar = new ActionBar(driver);
		//actBar.chooseDrive(actBar.ELEMENT_SITES_MANAGEMENT_DRIVE);
		actBar.showDrives();

		ecms.goToNode("intranet/documents");

		actBar.goToAddNewContent();

		cTemplate = new ContentTemplate(driver);
		cTemplate.createNewFile(nameArticle, contentArticle, titleArticle);

		waitForTextPresent(titleArticle);

		//Lock node
		//lockNode(elementCopiedArticleUnLock);
		cMenu = new ContextMenu(driver);
		cMenu.contextMenuAction(elementCopiedArticleUnLock, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		click(cMenu.ELEMENT_DOCUMENT);

		waitForAndGetElement(elementLockedArticleIcon);	
	}

	//Verify locked node at Administration/Manage Lock
	public void goToAdminManageLockAndVerify(String titleOfArticle){
		nav = new NavigationToolbar(driver);
		nav.goToContentAdministration();
		click(ELEMENT_REPOSITORY_TAB);
		click(ELEMENT_MANAGE_LOCKS);
		//waitForTextPresent(titleOfArticle);
		waitForAndGetElement("//*[contains(text(), '"+ titleOfArticle +"')]");
	}
}
