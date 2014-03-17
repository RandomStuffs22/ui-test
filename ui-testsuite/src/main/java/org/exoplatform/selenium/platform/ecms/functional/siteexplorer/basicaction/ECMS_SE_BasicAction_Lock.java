package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ECMS_SE_BasicAction_Lock extends PlatformBase {
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	String DATA_USER = "john";
	//String DATA_PASS = DATA_PASS;
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		button = new Button(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		ecmsPer = new EcmsPermission(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 66035
	 * Case 01: Lock a node
	 * Create a node
	 * Lock this node
	 */
	@Test
	public void test01_LockANode() {
		String DATA_FILE_TITLE = "EMCS_SE_BasicAction_Lock_Case01";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_TITLE));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_TITLE);

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new file");
		cTemplate.createNewFile(DATA_FILE_TITLE, DATA_FILE_TITLE, DATA_FILE_TITLE);

		info("Lock node");
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		info("Check locked node");
		assert cMenu.isLockedNode(FILE_PATH_LOCKED);

		//Delete data
		cMenu.deleteDocument(FILE_PATH_LOCKED);
	}

	/**CaseId: 66193
	 * Case02: Unlock a node by locker
	 * Create a node
	 * Lock node
	 * Unlock node
	 */
	@Test
	public void test02_UnlockNodeByLocker () {
		String DATA_FILE_TITLE = "EMCS_SE_BasicAction_Lock_Case02";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_TITLE));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_TITLE);

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Go to amce node");
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);

		info("Click add new content");
		actBar.goToAddNewContent();

		info("Create new article");
		cTemplate.createNewFile(DATA_FILE_TITLE, DATA_FILE_TITLE, DATA_FILE_TITLE);

		info("Lock node");
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		info("Check locked node");
		assert cMenu.isLockedNode(FILE_PATH_LOCKED);

		info("Unlock node by locker");
		cMenu.contextMenuAction(FILE_PATH_LOCKED, cMenu.ELEMENT_MENU_UNLOCK);
		waitForAndGetElement(FILE_PATH);

		//Delete data
		cMenu.deleteDocument(FILE_PATH);
	}

	/**CaseId: 66194
	 * Case03: Unlock a node by user is  not locker 
	 * create new node
	 * lock node by user John
	 * login with mary
	 * check mary can not unlock this node
	 */
	@Test
	public void test03_UnlockNodeByUserIsNotLocker(){
		String DATA_FILE_TITLE = "EMCS_SE_BasicAction_Lock_Case03";
		By FILE_PATH = By.linkText(DATA_FILE_TITLE);

		//create new node: File document
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();
		info("Create a new File document");
		cTemplate.createNewFile(DATA_FILE_TITLE, DATA_FILE_TITLE, DATA_FILE_TITLE);

		//lock node
		ecms.goToNode(FILE_PATH);
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		//check lock node
		assert cMenu.isLockedNode(FILE_PATH):"Lock node is not successful";
		driver.close();

		//login with mary
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		cMenu = new ContextMenu(driver);
		ecms = new EcmsBase(driver);
		magAcc.signIn("mary", DATA_PASS);
		navToolBar.goToSiteExplorer();

		//check mary cannot unlock this node
		ecms.goToNode(FILE_PATH);
		rightClickOnElement(FILE_PATH);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_UNLOCK);
		//waitForElementNotPresent(cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		info("User cannot lock or unlock node");
		magAcc.signOut();

		//delete data with user John
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(FILE_PATH);
	}

	/**CaseID: 66536
	 * Case 04: Lock a node while parent node is being in locked status
	 * Login
	 * Create node and its child node
	 * Lock parent node
	 * Lock child node
	 */
	@Test
	public void test04_LockANodeWhileParentNodeIsBeingLocked() {
		String DATA_ANNOUN_TITLE = "EMCS_SE_BasicAction_Lock_Case04";
		By ANNOUN_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ANNOUN_TITLE));
		By ANNOUN_PATH_LOCKED = By.linkText(DATA_ANNOUN_TITLE);

		String DATA_FILE_NAME = "test04";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_NAME);

		/*Step 1: Create parent node and child node, lock parent node*/
		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Create new announcement document");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(DATA_ANNOUN_TITLE, DATA_ANNOUN_TITLE);

		info("Add new File document");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Lock parent node");
		cMenu.contextMenuAction(ANNOUN_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(ANNOUN_PATH_LOCKED);

		info("Lock child node");
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(FILE_PATH_LOCKED);
		
		//Delete data
		cMenu.deleteDocument(ANNOUN_PATH_LOCKED);
	}

	/**CaseId: 66195
	 * Case05: Unlock a node while parent node is being in lock status
	 * Login
	 * Create node and its child node
	 * Lock parent node
	 * Lock child node
	 * Unlock child node
	 */
	@Test
	public void test05_UnLockANodeWhileParentNodeIsBeingLocked() {
		String DATA_ANNOUN_TITLE = "EMCS_SE_BasicAction_Lock_Case05";
		By ANNOUN_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_ANNOUN_TITLE));
		By ANNOUN_PATH_LOCKED = By.linkText(DATA_ANNOUN_TITLE);

		String DATA_FILE_NAME = "test05";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_NAME);

		/*Step 1: Create parent node and child node, lock parent node*/
		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Create new announcement document");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(DATA_ANNOUN_TITLE, DATA_ANNOUN_TITLE);

		info("Add new file document");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Lock parent node");
		cMenu.contextMenuAction(ANNOUN_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(ANNOUN_PATH_LOCKED);
		
		info("Lock child node");
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(FILE_PATH_LOCKED);

		info("Unlock child node");
		cMenu.contextMenuAction(FILE_PATH_LOCKED, cMenu.ELEMENT_MENU_UNLOCK);
		waitForAndGetElement(FILE_PATH);

		//Delete data
		cMenu.deleteDocument(ANNOUN_PATH_LOCKED);
	}

	/**CaseId: 66539
	 * Case 06: Lock node while it is being checked-in
	 * Create node
	 * Check in this node
	 * Verify cannot lock check in node
	 */
	@Test
	public void test06_LockNodeWhileItIsBeingCheckedIn() {
		String DATA_FILE_NAME = "EMCS_DMS_SE_Lock_Case06";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Create new file document");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Check in file document");
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_MENU_CHECKIN);

		info("Verify cannot lock checked-in node");
		rightClickOnElement(FILE_PATH);
		waitForElementNotPresent(cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		//Delete data
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteDocument(FILE_PATH);
	}

	/**CaseId: 66537
	 * Case 07: Lock child node while its parent is being check in status
	 * Create parent node
	 * Create child node
	 * Check in parent node
	 * Lock child node
	 */
	@Test
	public void test07_LockChildNodeWhileItsParentIsInCheckinStatus() {
		String WEB_CONTENT = "EMCS_DMS_SE_Lock_Case07";
		String WEB_CONTENT_CONTENT = "EMCS_DMS_SE_Lock_Case07_Content";
		By WEB_CONTENT_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", WEB_CONTENT));

		String DATA_FILE_NAME = "dataTest";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();
		
		info("Create new web content");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(WEB_CONTENT, WEB_CONTENT_CONTENT, "", "", "", "");

		info("Create child node is file document");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);

		info("Check in parent node");
		cMenu.contextMenuAction(WEB_CONTENT_PATH, cMenu.ELEMENT_MENU_CHECKIN);
		
		info("Check can not lock child node");
		rightClickOnElement(FILE_PATH);
		waitForElementNotPresent(cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		//Delete data
		ecms.goToNode(WEB_CONTENT_PATH);
		cMenu.contextMenuAction(WEB_CONTENT_PATH, cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteDocument(WEB_CONTENT_PATH);
	}

	/**CaseId: 66538
	 * Case 08: Lock a node when user has not set property right
	 * Login John
	 * Create document
	 * Set right for this document: James has all permission except set property
	 * Login as James
	 * James cannot lock this documents
	 */
	@Test
	public void test08_LockNodeWhenUserHasNotSetPropertyRight() {
		String DATA_FILE_NAME = "ECMS_DMS_SE_BasicAction_Lock_Case08";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create a new file");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME); 

		//Add a view permission to Action Bar 
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);
		
		ecms.goToNode(FILE_PATH);
		actBar.goToNodePermissionManagement();

		info("Delete permissons of all, except John");
		ecmsPer.removeDefaultPermissionOfNode();

		info("Set for user James that does not have set properties permission");
		ecms.selectUser("james");
		ecmsPer.setPermissionForNode(true, false, true);
		button.save();
		button.close();

		info("Verify user James cannot lock document");
		magAcc.signOut();
		magAcc.signIn("james", DATA_PASS);
		navToolBar.goToSiteExplorer();
		rightClickOnElement(FILE_PATH);
		waitForElementNotPresent(cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		info("Logout");
		magAcc.signOut();

		//Login as John and delete data
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(FILE_PATH);
	}

	/**CaseId: 67297
	 * Case 09: Automatically unlock a node when user logout
	 * Login
	 * Create node
	 * Lock this node
	 * Logout
	 * Login --> Locked node is unlock
	 */
	@Test
	public void test09_AutomaticallyUnlockANodeWhenUserLogout () {
		String DATA_FILE_TITLE = "EMCS_SE_BasicAction_Lock_Case09";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_TITLE));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_TITLE);

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Create new file document");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_TITLE, DATA_FILE_TITLE, DATA_FILE_TITLE);

		info("Lock node");
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(FILE_PATH_LOCKED);
		magAcc.signOut();

		info("Check automatically unlock a node when user logout");
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		rightClickOnElement(FILE_PATH_LOCKED);
		waitForAndGetElement(cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		//Delete data
		cMenu.deleteDocument(FILE_PATH);
	}
	
	/**CaseId: Automatically unlock a node after 30 minutes
	 * Create new node
	 * lock node
	 * wait 30 minutes
	 * check auto unlock node
	 */
	@Test
	public void test10_AutomaticallyUnlockNodeAfter30Minutes(){
		String DATA_FILE_TITLE = "EMCS_SE_BasicAction_Lock_Case10";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_TITLE));
		By FILE_PATH_LOCKED = By.linkText(DATA_FILE_TITLE);

		info("Go to site explorer");
		navToolBar.goToSiteExplorer();

		info("Create new file document");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_TITLE, DATA_FILE_TITLE, DATA_FILE_TITLE);
		
		info("Lock node");
		cMenu.contextMenuAction(FILE_PATH, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(FILE_PATH_LOCKED);
		
		info("Wait 30 minutes");
		Utils.pause(1800000);
		
		info("Check automatically unlock node");
		driver.navigate().refresh();
		Utils.pause(3000);
		rightClickOnElement(FILE_PATH_LOCKED);
		waitForAndGetElement(cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		
		cMenu.deleteDocument(FILE_PATH);
	}
	
	/**CaseId: 74529 -> Lock a Parent & Child selection
	 * 
	 */
	@Test
	public void test11_LockParentAndChildNodeSelection(){
		String DATA_FOLDER = "contentfolder11";
		String FILE_NAME = "EMCS_SE_BasicAction_Lock_Case11";
		By ELEMENT_FILE = By.linkText(FILE_NAME);
		
		info("Add New Content icon in action bar of File management view");
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("List");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "List", "List");
		
		info("Create parent node");
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("List");
		cTemplate.createNewFolder(DATA_FOLDER, folderType.None);
		ecms.goToNode(DATA_FOLDER, true);
		
		info("Create child node");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, FILE_NAME, FILE_NAME);		
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", DATA_FOLDER)));
		waitForAndGetElement(ELEMENT_FILE);
		
		info("Lock parent and child node");
		actBar.lockNodeFromActionBar(DATA_FOLDER + "/" + FILE_NAME);
		
		//actBar.deleteDataInAdminView(DATA_FOLDER);
		actBar.actionsOnElement(DATA_FOLDER, actionType.DELETE);
	}
	
	/**CaseId: 74536 -> Unlock a Parent & Child selection
	 * 
	 */
	@Test
	public void test12_UnlockParentAndChildSelection(){
		String DATA_FOLDER = "contentfolder12";
		String FILE_NAME = "EMCS_SE_BasicAction_Lock_Case12";
		By ELEMENT_FILE = By.linkText(FILE_NAME);
		
		info("Add New Content icon in action bar of File management view");
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("List");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "List", "List");
		
		info("Create parent node");
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("List");
		cTemplate.createNewFolder(DATA_FOLDER, folderType.None);
		ecms.goToNode(DATA_FOLDER, true);
		
		info("Create child node");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, FILE_NAME, FILE_NAME);		
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", DATA_FOLDER)));
		waitForAndGetElement(ELEMENT_FILE);
		
		info("Lock parent and child node");
		actBar.lockNodeFromActionBar(DATA_FOLDER + "/" + FILE_NAME);
		
		info("Unlock parent and child node");
		actBar.unLockNodeFromActionBar(DATA_FOLDER + "/" + FILE_NAME);
		
		//actBar.deleteDataInAdminView(DATA_FOLDER);
		actBar.actionsOnElement(DATA_FOLDER, actionType.DELETE);
	}
}