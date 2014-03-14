package org.exoplatform.selenium.platform.ecms.sniff.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageCategory;
import org.exoplatform.selenium.platform.ecms.admin.ManageNamespace;
import org.exoplatform.selenium.platform.ecms.admin.ManageNodeType;
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
 * 
 * @author vuna2
 * May, 2013
 *
 */
public class ECMS_Admin_Repository extends PlatformBase{
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
	ManageNamespace magNamespace;
	ManageNodeType magNodeType;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("LogIn to Intranet with User..." + DATA_USER1);
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
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		magNamespace = new ManageNamespace(driver);
		magNodeType = new ManageNodeType(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		driver.quit();
	}

	/**
	 * Qmetry ID: 65855
	 * Unlock a Node
	 * ================
	 * 
	 */
	@Test
	public void test01_UnlockNode(){
		String fileTitle = "Ecms_Admin_Unlock_Node";
		String content = "Unlock A Node In Content Administration";
		By eLockedFile = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", fileTitle));
		String elementLockedArticleIcon = cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", fileTitle);

		info("-- Test case: Unlock a node --");
		
		//Go to Site Explorer
		nav.goToSiteExplorer();
		//actBar.goToSitesManagement();
		ecms.goToNode("intranet/documents");

		//Create a new File
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileTitle, content, fileTitle);

		//Lock this File
		cMenu.contextMenuAction(eLockedFile, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		click(cMenu.ELEMENT_DOCUMENT);
		waitForAndGetElement(elementLockedArticleIcon);

		//Unlock Node in Admin
		ecMain.goToLockedTab();
		click(ecMain.ELEMENT_UNLOCK_NODE.replace("${lockedNode}", fileTitle));
		waitForElementNotPresent(ecMain.ELEMENT_UNLOCK_NODE.replace("${lockedNode}", fileTitle));

		info("-- Restore data --");
		nav.goToSiteExplorer();
		cMenu.deleteDocument(eLockedFile);
	}

	/**
	 * Qmetry ID: 67841
	 * Manage Lock
	 * ================
	 * 
	 */
	@Test
	public void test02_ManageLock(){
		String fileTitle = "Ecms_Admin_Manage_Lock";
		String content = "All users in the selected group, would be able to unlock";
		By eLockedFile = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", fileTitle));
		String elementLockedArticleIcon = cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", fileTitle);
		
		By ELEMENT_GROUP_MEMBERSHIP = By.linkText("manager");
		String ELEMENT_GROUP_MEMBERSHIP_DISPLAY = "manager";
		String ELEMENT_GROUP_TO_SELECT = "Platform/Content Management";
		String ELEMENT_GROUP_DISPLAY = "platform/web-contributors";

		info("-- Test case: Manage lock --");
		
		//Go to Site Explorer
		nav.goToSiteExplorer();
		//actBar.goToSitesManagement();
		ecms.goToNode("intranet/documents");

		//Create a new File
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileTitle, content, fileTitle);

		//Lock this File
		cMenu.contextMenuAction(eLockedFile, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		click(cMenu.ELEMENT_DOCUMENT);
		waitForAndGetElement(elementLockedArticleIcon);
		
		//Open form to add permission for Users/Group (unlock node)
		//Add permission for group to unlock node
		ecMain.goToManageLockTab();
		userGroup.selectGroup(ELEMENT_GROUP_TO_SELECT,true);
		click(ELEMENT_GROUP_MEMBERSHIP);

		//Groups are listed on the table
		waitForTextPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);

		//Login with user in the selected group and un lock that node
		driver.close();
		driver = new FirefoxDriver();
		driver.get(baseUrl);
		nav = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		ecMain = new ECMainFunction(driver);
		adminPer = new Permission(driver);
		cMenu = new ContextMenu(driver);
		ecms = new EcmsBase(driver);
		
		magAcc.signIn(DATA_USER2, DATA_PASS);
		ecMain.goToLockedTab();
		click(ecMain.ELEMENT_UNLOCK_NODE.replace("${lockedNode}", fileTitle));
		waitForTextNotPresent(fileTitle);
		
		//Admin re-LogIn and Delete Permission...
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		ecMain.goToManageLockTab();
		click(adminPer.ELEMENT_DELETE_SELECTED_GROUP.replace("${selectedGroup}", ELEMENT_GROUP_DISPLAY));
		waitForTextNotPresent(ELEMENT_GROUP_MEMBERSHIP_DISPLAY + ":/" + ELEMENT_GROUP_DISPLAY);
		
		//Restore data
		nav.goToSiteExplorer();
		ecms.goToNode("intranet/documents");
		cMenu.deleteDocument(eLockedFile);
	}
	
	/**
	 * Qmetry ID: 65869
	 * Namespace registry
	 * ==================
	 * 
	 */
	@Test
	public void test03_NamespaceRegistry(){
		String namespace = "zBlog";
		String uri = "http://blog.exoplatform.com/z";
		
		info("-- Add a new namespace --");
		ecMain.goToNamespacesTab();
		
		//Add a new namespace
		magNamespace.addNewNamespace(namespace, uri);
		
		//Verify
		magNamespace.checkDisplayNamespace(namespace);
	}
	
	/**
	 * Qmetry ID: 65857
	 * Add Node Types
	 * ================
	 * Qmetry ID: 67840
	 * View Node Types
	 * 
	 */
	@Test
	public void test04_AddAndViewNodeType(){
		String nodeTypeName = "exoplatform65857";
		String superTypes = "app:application/app:category";

		info("-- Add a new node type --");
		ecMain.goToNodeTypeTab();
		
		//Add new Node Type
		magNodeType.addNewNodeType(nodeTypeName, superTypes);
		
		//Verify and view node type
		magNodeType.viewNodeType(nodeTypeName);		
	}
}