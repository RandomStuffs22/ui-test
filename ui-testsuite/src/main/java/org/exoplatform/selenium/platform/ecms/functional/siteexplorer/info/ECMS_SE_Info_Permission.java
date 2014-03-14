package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.info;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * 
 * @author PhuongDT
 * @date: 11/09/2013
 *
 */
public class ECMS_SE_Info_Permission  extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	ActionBar actBar;
	NavigationToolbar navToolBar;

	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	SitesExplorer siteExp;
	ContextMenu cMenu;
	Button btn;
	ManageDrive mDrive;
	EcmsPermission ePerm;
	ManageAlert magAlert;
	UserGroupManagement ugMan;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		siteExp = new SitesExplorer(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		btn = new Button(driver);
		mDrive = new ManageDrive(driver);
		ePerm=new EcmsPermission(driver);
		magAlert = new ManageAlert(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
		ugMan = new UserGroupManagement(driver);
		navToolBar.goToSiteExplorer();
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == View Permission of folder ==
	 * Test case ID: 66066
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 */
	@Test
	public void test01_ViewPermissionOfFolder(){
		/*Declare variable*/
		String folder = "folder01";
		By bFolder = By.linkText(folder);

		/*Step 1: Select node to view permission of node*/
		//Create a node is folder [Detail]
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == View permission of locked node by locker ==
	 * Test case ID: 66067
	 * Step 1: Select node to view permission of node
	 * Step 2: Lock node
	 * Step 3: Open 'Permission Management' pop-up
	 */
	@Test
	public void test02_ViewPermissionOfLockedNodeByLocker(){
		/*Declare variable*/
		String folder = "folder66067";
		By bFolder = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", folder));
		By bFolderLocked = By.linkText(folder);

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/* Step 2: Lock node*/
		info("Lock node");
		cMenu.contextMenuAction(bFolder, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		info("Check locked node");
		assert cMenu.isLockedNode(bFolderLocked);

		/*Step 3: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolderLocked);
	}

	/**
	 * == View permission of locked node by user is not locker ==
	 * Test case ID: 66069
	 * Step 1: Select node to view permission of node
	 * Step 2: Lock node
	 * Step 3: Open 'Permission Management' pop-up
	 */
	@Test
	public void test03_ViewPermissionOfLockedNodeByUserIsNotLocker(){
		/*Declare variable*/
		String folder = "folder03";
		By bFolder = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", folder));
		By bFolderLocked = By.linkText(folder);

		/*Step 1: Select node to view permission of node*/
		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/* Step 2: Lock node*/
		info("Lock node");
		cMenu.contextMenuAction(bFolder, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		info("Check locked node");
		assert cMenu.isLockedNode(bFolderLocked);

		/*Step 3: Open 'Permission Management' pop-up*/
		//Login by user is not locker
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolderLocked);
	}

	/**
	 * == View Permission of node is document ==
	 * Test case ID: 66069
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 */
	@Test
	public void test04_ViewPermissionOfNodeIsDocument(){
		/*Declare variable*/
		String document = "document04";
		By bDocument = By.linkText(document);

		/*Step 1: Select node to view permission of node*/
		//Create a document [Detail]
		mDrive.addView2Drive("Admin", "Managed Sites");
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);		
		actBar.goToAddNewContent();
		cTemplate.createNewFile(document,document,document);

		/*Step 2: Open 'Permission Management' pop-up*/						
		//Click on 'View Permissions' icon 
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bDocument);
	}

	/**
	 * == View Permission of node is uploaded file ==
	 * Test case ID: 66070
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 */
	@Test
	public void test05_ViewPermissionOfNodeIsUploadedFile(){
		/*Declare variable*/
		String file = "ECMS_DMS_SE_Upload_xmlfile.xml";
		By bFile = By.linkText(file);

		/*Step 1: Select node to view permission of node*/
		//Upload a file [Detail]
		//Check if upload button is shown on action bar
		actBar.addItem2ActionBar("upload", actBar.ELEMENT_UPLOAD_FILE_LINK);
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);		
		ecms.uploadFile("TestData/"+file);

		/*Step 2: Open 'Permission Management' pop-up*/		
		actBar.goToNode(bFile);
		//Click on 'View Permissions' icon 
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFile);
	}

	/**
	 * == Edit permission of node ==
	 * Test case ID: 66445
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 * Step 3: Edit permission of user or group
	 * Step 4: Edit permission
	 */
	@Test
	public void test06_EditPermissionOfNode(){
		/*Declare variable*/
		String folder = "folder06";
		By bFolder = By.linkText(folder);
		String user = "*:/platform/web-contributors";

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Edit permission of user or group*/
		/*Step 4: Edit permission*/
		//Click 'Edit' icon to edit permission of user or group you want
		//Add permission "remove", "write" for *:/platform/web-contributors
		uncheck(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",user),2);
		btn.close();

		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Check if mary has edit, read on node1
		rightClickOnElement(bFolder);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_DELETE);

		/*Clear data*/
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Edit permission of owner ==
	 * Test case ID: 66446
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' popup
	 * Step 3: Edit permission of owner's node
	 */
	@Test
	public void test07_EditPermissionOfOwner(){
		/*Declare variable*/
		String folder = "folder07";
		By bFolder = By.linkText(folder);

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Edit permission of user or group*/
		//Click on 'Edit' icon to edit permission of user is owner of this node
		//Form to edit permission appears but you can not edit permission of this user
		assert (!waitForAndGetElement(ePerm.ELEMENT_READ_CHECK.replace("{$user}",DATA_USER1),DEFAULT_TIMEOUT,1,2).isEnabled());
		assert (!waitForAndGetElement(ePerm.ELEMENT_MODIFY_CHECK.replace("{$user}",DATA_USER1),DEFAULT_TIMEOUT,1,2).isEnabled());
		assert (!waitForAndGetElement(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",DATA_USER1),DEFAULT_TIMEOUT,1,2).isEnabled());

		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Delete permission of node ==
	 * Test case ID: 66596
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' popup
	 * Step 3: Delete permission of node
	 */
	@Test
	public void test08_DeletePermissionOfNode(){
		/*Declare variable*/
		String folder = "folder08";
		By bFolder = By.linkText(folder);
		String user = "*:/platform/web-contributors";

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Delete permission of node*/
		//Click 'Delete' icon to delete permission of user 
		//Permission of node is deleted
		ePerm.deletePermission(user, true);
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Delete permission of owner ==
	 * Test case ID: 66597
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' popup
	 * Step 3: Delete permission of user 'root' 
	 * Note: need to update step 3 of test case
	 */
	@Test
	public void test09_DeletePermissionOfOwner(){
		/*Declare variable*/
		String folder = "folder09";
		By bFolder = By.linkText(folder);

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Delete permission of user 'root' */
		//Can't see delete icon of owner
		waitForElementNotPresent(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", DATA_USER1));

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Add/Edit/Delete Permission of locked node by locker ==
	 * Test case ID: 67276
	 * Step 1: Select node to view permission of node
	 * Step 2: Lock node
	 * Step 3: Open 'Permission Management' pop-up
	 */
	@Test
	public void test10_AddEditDeletePermissionOfLockedNodeByLocker(){
		/*Declare variable*/
		String folder = "folder10";
		By bFolder = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", folder));
		By bFolderLocked = By.linkText(folder);
		String user1 = "*:/platform/web-contributors";

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/* Step 2: Lock node*/
		info("Lock node");
		cMenu.contextMenuAction(bFolder, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		info("Check locked node");
		assert cMenu.isLockedNode(bFolderLocked);

		/*Step 3: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();

		//Locker add permission of this node
		//Add permission "read", "write" for mary
		ePerm.selectUser(DATA_USER2);
		ePerm.setPermissionForNode(true, true, false);
		btn.save();
		btn.close();
		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();
		//Check if mary has edit, read on node1
		rightClickOnElement(bFolder);
		waitForAndGetElement(cMenu.ELEMENT_MENU_DELETE);

		/*Login again*/
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();

		//Click 'Delete' icon to delete permission of user 
		//Locker delete permission of this node
		ePerm.deletePermission(DATA_USER2, true);

		//Locker edit permission of this node
		uncheck(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",user1),2);
		btn.close();

		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Check if mary has edit, read on node1
		rightClickOnElement(bFolder);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_DELETE);

		/*Clear data*/
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolderLocked);
	}

	/**
	 * == Add/Edit/Delete Permission of locked node by user is not locker ==
	 * Test case ID: 67277
	 * Step 1: Select node to view permission of node
	 * Step 2: Lock node
	 * Step 3: Open 'Permission Management' pop-up
	 */
	@Test
	public void test11_AddEditDeletePermissionOfLockedNodeByUserIsNotLocker(){	
		/*Declare variable*/
		String folder = "folder11";
		By bFolder = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", folder));
		By bFolderLocked = By.linkText(folder);
		String user = "any";

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/* Step 2: Lock node*/
		info("Lock node");
		cMenu.contextMenuAction(bFolder, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		info("Check locked node");
		assert cMenu.isLockedNode(bFolderLocked);
		driver.close();

		/*Step 3: Open 'Permission Management' pop-up*/
		//Login by user is not locker
		loginWithAnotherAccOnThesameBrowser(DATA_USER2, DATA_PASS);
		magAcc = new ManageAccount(newDriver);
		actBar= new ActionBar(newDriver);
		navToolBar = new NavigationToolbar(newDriver);
		ePerm = new EcmsPermission(newDriver);
		btn = new Button(newDriver);
		cMenu = new ContextMenu(newDriver);
		navToolBar.goToSiteExplorer();

		//Click on 'View Permissions' icon 
		actBar.goToNode(By.linkText(folder));
		actBar.goToNodePermissionManagement();

		//User can not add/delete or edit permission of this node
		waitForElementNotPresent(By.className("permission"),DEFAULT_TIMEOUT,1);
		waitForElementNotPresent(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", user), DEFAULT_TIMEOUT,1);
		btn.close();
		magAcc.signOut();

		/*Clear data*/
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolderLocked);
	}

	/**
	 * == Add/Edit/Delete Permission of node when node is in 'check in' status ==
	 * Test case ID: 67278
	 * Step 1: Select node to view permission of node
	 * Step 2: Activate version for selected node
	 * Step 3: Open 'Permission Management' pop-up
	 */
	@Test
	public void test12_AddEditDeletePermissionOfNodeWhenNodeIsInCheckInStatus(){
		/*Declare variable*/
		String document = "document12";
		By bDocument = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", document));
		String user = "*:/platform/web-contributors";

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		mDrive.addView2Drive("Admin", "Managed Sites");
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);		
		actBar.goToAddNewContent();
		cTemplate.createNewFile(document,document,document);

		/* Step 2: Activate version for selected node*/
		actBar.goToViewMode("Web");
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		info("-- Activate version for selected node --");
		cMenu.contextMenuAction(bDocument, cMenu.ELEMENT_MENU_CHECKIN);

		/*Step 3: Open 'Permission Management' pop-up*/
		actBar.goToNode(bDocument);
		actBar.goToViewMode("Admin");
		//Click on 'View Permissions' icon 
		actBar.goToNodePermissionManagement();
		//Edit permission
		click(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",user),2);
		waitForAndGetElement(ePerm.ELEMENT_WARNING_DIALOG_PERMISSION);
		magAlert.click(By.xpath("//*[text()='OK']"));

		//Delete permission
		click(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", user));
		magAlert.acceptAlert();
		waitForAndGetElement(ePerm.ELEMENT_WARNING_DIALOG_PERMISSION);
		magAlert.click(By.xpath("//*[text()='OK']"));

		//Add new permission
		ePerm.selectUser(DATA_USER2);
		ePerm.setPermissionForNode(true, true, false);
		btn.save();
		waitForAndGetElement(ePerm.ELEMENT_WARNING_DIALOG_PERMISSION);
		magAlert.click(By.xpath("//*[text()='OK']"));

		//close permission form
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.contextMenuAction(bDocument, cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteDocument(bDocument);
	}

	/**
	 * == Add/Edit/Delete Permission of node when parent node is in 'check in' status ==
	 * Test case ID: 67279
	 * Step 1: Select node to view permission of node
	 * Step 2: Activate version for selected node
	 * Step 3: Open 'Permission Management' pop-up
	 */
	@Test
	public void test13_AddEditDeletePermissionOfNodeWhenParentNodeIsInCheckInStatus(){
		/*Declare variable*/
		String document = "document13";
		String subdocument = "subdocument13";
		By bDocument = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", document));
		By bSubDocument = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", subdocument));
		String user = "*:/platform/web-contributors";

		mDrive.addView2Drive("Admin", "Managed Sites");
		actBar.goToViewMode("Admin");
		
		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);	
		//Create parent node
		actBar.goToAddNewContent();
		cTemplate.createNewProduct(document, document);
		//Create child node
		actBar.goToAddNewContent();
		cTemplate.createNewFile(subdocument,subdocument,subdocument);

		/* Step 2: Activate version for selected node*/
		cMenu.contextMenuAction(bDocument, cMenu.ELEMENT_MENU_CHECKIN);

		/*Step 3: Open 'Permission Management' pop-up*/
		actBar.goToNode(bSubDocument);
		actBar.goToViewMode("Admin");
		//Click on 'View Permissions' icon 
		actBar.goToNodePermissionManagement();
		//Edit permission
		uncheck(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",user),2);

		//Add new permission
		ePerm.selectUser(DATA_USER2);
		ePerm.setPermissionForNode(true, true, false);
		btn.save();

		//Delete permission
		ePerm.deletePermission(DATA_USER2, true);

		//close permission form
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.contextMenuAction(bDocument, cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteDocument(bDocument);
	}

	/**
	 * == Add/Edit/Delete permission of node when user does not have 'set property' right ==
	 * Test case ID: 67280
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 */
	@Test
	public void test14_AddEditDeletePermissionOfLockedNodeByUserIsNotLocker(){	
		/*Declare variable*/
		String folder = "folder14";
		By bFolder = By.linkText(folder);
		String defaultuser1 = "*:/platform/web-contributors";
		String defaultuser2 = "*:/platform/administrators";
		String defaultuser3 = "any";
		String user = DATA_USER4;

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		//Delete the default permission
		ePerm.deletePermission(defaultuser1, true);
		ePerm.deletePermission(defaultuser2, true);
		ePerm.deletePermission(defaultuser3, true);

		//Add user and her permission
		ePerm.selectUser(user);
		ePerm.setPermissionForNode(true, true, true);
		btn.save();

		ePerm.selectUser(DATA_USER2);
		ePerm.setPermissionForNode(true, false, true);
		btn.save();
		btn.close();

		/*Login again*/
		magAcc.signOut();
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//Click on 'View Permissions' icon 
		actBar.goToNode(bFolder);
		actBar.goToNodePermissionManagement();

		//User can not add/delete or edit permission of this node
		waitForElementNotPresent(By.className("permission"),DEFAULT_TIMEOUT,1);
		waitForElementNotPresent(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", user), DEFAULT_TIMEOUT,1);
		btn.close();

		/*Clear data*/
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Add permission for any user to node ==
	 * Test case ID: 67436
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 * Step 3: Add permission for users
	 * Step 4: Select permission for users
	 */
	@Test
	public void test15_AddPermissionForAnyUserToNode(){	
		/*Declare variable*/
		String folder = "folder15";
		By bFolder = By.linkText(folder);

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Add permission for users*/
		/*Step 4: Select permission for users*/
		ePerm.selectEveryone();
		assert waitForAndGetElement(ePerm.ELEMENT_READ_CHECKBOX, DEFAULT_TIMEOUT, 1, 2).isSelected();
		ePerm.setPermissionForNode(true, true, true);
		btn.save();
		waitForAndGetElement(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", "any"),DEFAULT_TIMEOUT, 1);
		assert waitForAndGetElement(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}","any"),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_MODIFY_CHECK.replace("{$user}","any"),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_READ_CHECK.replace("{$user}","any"),DEFAULT_TIMEOUT,1,2).isSelected();
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Add permission for group to node (1) ==
	 * Test case ID: 67441
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 * Step 3: Add permission for group
	 * Step 4: Select group in form to add permission
	 * Step 5: Select permission for group
	 */
	@Test
	public void test16_AddPermissionForGroupToNode1(){	
		/*Declare variable*/
		String folder = "folder16";
		By bFolder = By.linkText(folder);
		String groupPath = "Platform";
		String membership = "author";

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Add permission for group*/
		/*Step 4: Select group in form to add permission*/
		/*Step 5: Select permission for group*/
		//Select the group in 'Select group' form
		//Select membership is author
		ePerm.selectMembership(groupPath, membership, "Select Membership");
		ePerm.setPermissionForNode(true, true, true);
		btn.save();
		//Node is added permission. All the users in the group at step 5 can do these  actions
		waitForAndGetElement(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT, 1);
		assert waitForAndGetElement(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_MODIFY_CHECK.replace("{$user}",membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_READ_CHECK.replace("{$user}",membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT,1,2).isSelected();
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Add permission for group to node (2) ==
	 * Test case ID: 67442
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 * Step 3: Add permission for group
	 * Step 4: Select group in form to add permission
	 * Step 5: Select permission for group
	 */
	@Test
	public void test17_AddPermissionForGroupToNode2(){	
		/*Declare variable*/
		String folder = "folder17";
		By bFolder = By.linkText(folder);
		String groupPath = "Platform";
		String membership = "*";

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Add permission for group*/
		/*Step 4: Select group in form to add permission*/
		/*Step 5: Select permission for group*/
		//Select the group in 'Select group' form
		//Select membership is *
		ePerm.selectMembership(groupPath, membership, "Select Membership");
		ePerm.setPermissionForNode(true, true, true);
		btn.save();
		//Node is added permission. All the users in the group at step 5 can do these  actions
		waitForAndGetElement(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT, 1);
		assert waitForAndGetElement(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_MODIFY_CHECK.replace("{$user}",membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_READ_CHECK.replace("{$user}",membership.toLowerCase()+":/"+groupPath.toLowerCase()),DEFAULT_TIMEOUT,1,2).isSelected();
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}

	/**
	 * == Add permission for user to node ==
	 * Test case ID: 67443
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 * Step 3: Add permission for user
	 * Step 4: Select user to add permission
	 * Step 5: Select permission for user
	 */
	@Test
	public void test18_AddPermissionForUserToNode(){	
		/*Declare variable*/
		String folder = "folder18";
		By bFolder = By.linkText(folder);

		/*Step 1: Select node to view permission of node*/
		//Create node (folder, document) or upload file
		cTemplate.createNewFolder(folder,folderType.None);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Add permission button to action bar if it is not on action bar
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		//Add "Admin" view for managed sites
		mDrive.addView2Drive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();

		//Select 'Admin view'
		actBar.goToViewMode("Admin");

		//Click on 'View Permissions' icon 
		actBar.goToNodeByAddressPath("/"+folder);
		actBar.goToNodePermissionManagement();
		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		/*Step 3: Add permission for user*/
		/*Step 4: Select user to add permission*/
		/*Step 5: Select permission for user*/
		//Select the group in 'Select group' form
		//Select membership is *
		ePerm.selectUser(DATA_USER2);
		ePerm.setPermissionForNode(true, true, true);
		btn.save();
		//Node is added permission. All the users in the group at step 5 can do these  actions
		waitForAndGetElement(ePerm.ELEMENT_DELETE_USER_PERMISSION_1.replace("${userOrGroupName}", DATA_USER2),DEFAULT_TIMEOUT, 1);
		assert waitForAndGetElement(ePerm.ELEMENT_REMOVE_CHECK.replace("{$user}",DATA_USER2),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_MODIFY_CHECK.replace("{$user}",DATA_USER2),DEFAULT_TIMEOUT,1,2).isSelected();
		assert waitForAndGetElement(ePerm.ELEMENT_READ_CHECK.replace("{$user}",DATA_USER2),DEFAULT_TIMEOUT,1,2).isSelected();
		btn.close();

		/*Clear data*/
		mDrive.removeViewFromDrive("Admin", "Managed Sites");
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bFolder);
	}
}
