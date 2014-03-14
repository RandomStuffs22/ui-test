package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.BrowserPreferences;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author HaKT
 * @date: 24/09/2012
 */

/**
 * @date: 06/05/2013
 * @author lientm
 * Update follow plf4
 */
public class ECMS_SE_BasicAction_CopyPaste extends PlatformBase {

	//Platform
	Button button;
	ManageAlert alt;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	BrowserPreferences bPre;
	SitesExplorer siteExp;
	ManageAlert alert;

	public String SHOW_DMS_STRUCTURE="enableStructure";
	public String UPLOADED_FILE_PATH ="TestData/Winter.jpg";
	public String ELEMENT_SUB_NODE = "//*[contains(@id,'${parent}')]//child::*[text()='${child}']";
	public String ELEMENT_NODE_AT_ROOT = "//*[@id='iconTreeExplorer_2fsites_2f${nodeName}']/a/span";
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		button = new Button(driver);
		alt = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		ecmsPer = new EcmsPermission(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		bPre = new BrowserPreferences(driver);
		siteExp = new SitesExplorer(driver);
		alert = new ManageAlert(driver);
		magAcc.signIn(DATA_USER1,DATA_PASS);
	}

	@AfterMethod
	public void afterTest() {
		//logoutEcms ();
		driver.quit();
	}

	/** CaseID: 66954
	 * Go to Sites Management, stand at root path
	 * Create 1 Content folder
	 * Copy it
	 * Paste at root path
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test01_CopyFolderToRootPath()
	{ 
		String CONTENT_FOLDER_NAME="BasicAction_CopyPaste_Content_Folder_01";

		By VERIFY_AT_ROOT_PATH2=By.xpath(cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", CONTENT_FOLDER_NAME).replace("${title2}", CONTENT_FOLDER_NAME));
		By VERIFY_AT_ROOT_PATH1=By.xpath(ELEMENT_NODE_AT_ROOT.replace("${nodeName}", CONTENT_FOLDER_NAME.replaceAll("_", "").toLowerCase()));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Verify folder is created at root path");
		waitForAndGetElement(VERIFY_AT_ROOT_PATH1);

		info("Copy content folder & paste at root path");
		cMenu.copyAndPasteNode(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME), siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Verify copied folder is at root path");
		waitForAndGetElement(VERIFY_AT_ROOT_PATH2);
		
		info("Delete data");
		cMenu.deleteData(VERIFY_AT_ROOT_PATH2);
		cMenu.deleteDocument(VERIFY_AT_ROOT_PATH1);
	}

	/** CaseId: 66927
	 * Go to Sites Management, stand at root path
	 * Create 2 Content folder
	 * Copy folder 1
	 * Paste to folder 2
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test02_CopyContentFolderToContentFolder()
	{
		String CONTENT_FOLDER_NAME="BasicAction_CopyPaste_Content_Folder_02";
		By CONTENT_FOLDER_PATH=By.linkText(CONTENT_FOLDER_NAME);

		String CONTENT_FOLDER_NAME_01="BasicAction_CopyPaste_ContentFolder_021";
		By CONTENT_FOLDER_PATH_01=By.linkText(CONTENT_FOLDER_NAME_01);
		By CONTENT_FOLDER_PATH_01_NEW = By.xpath(ELEMENT_SUB_NODE.replace("${parent}", CONTENT_FOLDER_NAME.replaceAll("_", "").toLowerCase()).replace("${child}", CONTENT_FOLDER_NAME_01));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create Content Folder 01");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME_01, folderType.Content);

		info("Copy content folder 1 to content folder");
		cMenu.copyAndPasteNode(CONTENT_FOLDER_PATH_01, CONTENT_FOLDER_PATH);

		info("Verify Content folder 01 is in content folder");
		ecms.goToNode(CONTENT_FOLDER_PATH);
		waitForAndGetElement(CONTENT_FOLDER_PATH_01_NEW);

		info("Delete data");
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		cMenu.deleteDocument(CONTENT_FOLDER_PATH_01);
	}

	/** CaseId: 66926
	 * Go to Sites Management
	 * Create 1 Content folder, 1 document folder
	 * Copy content folder
	 * Paste to document folder
	 * Verify warning message
	 * Delete data
	 */
	@Test
	public void test03_CopyContentFolderToDocumentFolder()
	{
		String CONTENT_FOLDER_NAME="BasicAction_CopyPaste_Content_Folder_03";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));

		String DOCUMENT_FOLDER_NAME="BasicAction_CopyPaste_Document_Folder_03";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Copy content folder to document folder");
		cMenu.copyAndPasteNode(CONTENT_FOLDER_PATH, DOCUMENT_FOLDER_PATH);

		info("Verify warning message");
		alert.verifyAlertMessage(cMenu.WARNING_MESSAGE_CANNOT_PASTE);

		info("Delete data");
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}

	/** CaseId: 66924 
	 * Go to Sites Management
	 * Create 1 Content folder
	 * Create 1 file document that has parent type is nt:unstructured: web content
	 * Copy then paste this content folder to document
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test04_CopyContentFolderToDocument()
	{
		String WEB_CONTENT_NAME = "BasicAction_CopyPaste_Web_Content_04";
		By WEB_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", WEB_CONTENT_NAME));

		String CONTENT_FOLDER_NAME="BasicAction_CopyPaste_Content_Folder_04";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));
		By CONTENT_FOLDER_PATH_NEW = By.xpath(ELEMENT_SUB_NODE.replace("${parent}", WEB_CONTENT_NAME).replace("${child}", CONTENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create 1 file document");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(WEB_CONTENT_NAME, WEB_CONTENT_NAME, "", "", "", "");

		info("Copy folder to file document");
		cMenu.copyAndPasteNode(CONTENT_FOLDER_PATH, WEB_PATH);

		info("Check content folder in file");
		ecms.goToNode(WEB_PATH);
		waitForAndGetElement(CONTENT_FOLDER_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(WEB_PATH);
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
	}

	/** CaseId: 66931
	 *  Go to Sites Management
	 * Create 1 Content folder
	 * Create 1 document folder
	 * Copy document folder
	 * Paste to content folder
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test08_CopyDocumentFolderToContentFolder()
	{
		String CONTENT_FOLDER_NAME="BasicAction_CopyPaste_Content_Folder_08";
		By CONTENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));

		String DOCUMENT_FOLDER_NAME ="BasicAction_CopyPaste_Document_Folder_08";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));
		By DOCUMENT_FOLDER_PATH_NEW = By.xpath(ELEMENT_SUB_NODE.replace("${parent}", CONTENT_FOLDER_NAME.replaceAll("_", "").toLowerCase()).replace("${child}", DOCUMENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Copy document folder to content folder");
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH, CONTENT_FOLDER_PATH);

		info("Verify document folder is in content folder");
		ecms.goToNode(CONTENT_FOLDER_PATH);
		waitForAndGetElement(DOCUMENT_FOLDER_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}

	/** CaseId: 66937 
	 * Go to Sites Management
	 * Create 2 document folders
	 * Copy document folder 2 to document folder 1
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test09_CopyDocumentFolderToDocumentFolder()
	{
		String DOCUMENT_FOLDER_NAME="BasicAction_CopyPaste_Document_Folder_09";
		By DOCUMENT_FOLDER_PATH=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));

		String DOCUMENT_FOLDER_NAME_01="BasicAction_CopyPaste_Document_Folder_091";
		By DOCUMENT_FOLDER_PATH_01=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME_01));
		By DOCUMENT_FOLDER_PATH_NEW = By.xpath(ELEMENT_SUB_NODE.replace("${parent}", DOCUMENT_FOLDER_NAME_01.replaceAll("_", "").toLowerCase()).replace("${child}", DOCUMENT_FOLDER_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME_01, folderType.Document);

		info("Copy document folder to document folder 1");
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH, DOCUMENT_FOLDER_PATH_01);

		info("Verify document folder is pasted");
		ecms.goToNode(DOCUMENT_FOLDER_PATH_01);
		waitForAndGetElement(DOCUMENT_FOLDER_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH_01);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}

	/** CaseId: 66951
	 * case17: Copy folder and paste into locked node by user is not locker
	 * login with John: create new node (folder/document/upload file). Lock node
	 * login with mary: create new node. Check can not copy this node to locked node
	 */
	@Test
	public void test17_CopyFolderAndPasteIntoLockedNodeByUserIsNotLock(){		
		String DOCUMENT_FOLDER_NAME_1 = "BasicAction_CopyPaste_Document_Folder_17_1";
		String DOCUMENT_FOLDER_TITLE_1 = DOCUMENT_FOLDER_NAME_1.replaceAll("_", "").toLowerCase();
		By DOCUMENT_FOLDER_PATH_1 = By.linkText(DOCUMENT_FOLDER_NAME_1);

		String DOCUMENT_FOLDER_NAME_2 = "BasicAction_CopyPaste_Document_Folder_17_2";
		By DOCUMENT_FOLDER_PATH_2 = By.linkText(DOCUMENT_FOLDER_NAME_2);

		//create new document folder
		navToolBar.goToSiteExplorer();
		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME_1, folderType.Document);
		waitForAndGetElement(DOCUMENT_FOLDER_PATH_1);

		//lock node
		ecms.goToNode(DOCUMENT_FOLDER_PATH_1);
		cMenu.contextMenuAction(DOCUMENT_FOLDER_PATH_1, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		//check lock node
		assert cMenu.isLockedNode(DOCUMENT_FOLDER_PATH_1):"Lock node unsuccessfully";
		driver.close();

		//login with mary
		info("login with mary");
		initSeleniumTest();
		driver.get(baseUrl);
		alt = new ManageAlert(driver);
		ecms = new EcmsBase(driver);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		cMenu = new ContextMenu(driver);
		cTemplate = new ContentTemplate(driver);
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//create new document folder
		info("Create Document folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME_2, folderType.Document);
		waitForAndGetElement(DOCUMENT_FOLDER_PATH_2);

		//Check can not copy this node to locked node
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH_2, DOCUMENT_FOLDER_PATH_1);
		alt.verifyAlertMessage("The item with the path collaboration:/sites/"+ DOCUMENT_FOLDER_TITLE_1 + " or upper level is locked.");
		info("Can not copy a node to locked node by user is not locker");
		cMenu.deleteData(DOCUMENT_FOLDER_PATH_2);
		magAcc.signOut();

		//delete data with john
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(DOCUMENT_FOLDER_PATH_1);
	}

	/** CaseId: 66953
	 * Go to Sites Management
	 * create 2 node: 2 content folder
	 * set permission for user mary not have add right on node 2
	 * login with mary, copy node 1 to node 2
	 * Verify alert
	 * Delete data
	 */
	@Test
	public void test18_CopyFolderPasteIntoNodeUserNotHaveAddNodeRight()
	{
		String CONTENT_FOLDER_NAME_1 = "BasicAction_CopyPaste_Content_Folder_18_1";
		By CONTENT_FOLDER_PATH_1 = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME_1));

		String CONTENT_FOLDER_NAME_2 = "BasicAction_CopyPaste_Content_Folder_18_2";
		By CONTENT_FOLDER_PATH_2 = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME_2));

		info("Go to CE");
		navToolBar.goToSiteExplorer();
		
		actBar.addItem2ActionBar("viewPermissions", actBar.ELEMENT_PERMISSION_LINK);

		info("Create Document folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME_1, folderType.Content);

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME_2, folderType.Content);
		
		ecms.goToNode(CONTENT_FOLDER_NAME_2);
		actBar.goToNodePermissionManagement();

		info("Delete permisson of all except John");
		ecmsPer.removeDefaultPermissionOfNode();
		ecms.selectUser(DATA_USER2);
		ecmsPer.setPermissionForNode(true, false, true);

		info("Save then close");
		click(button.ELEMENT_SAVE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);
		magAcc.signOut();
		
		magAcc.signIn(DATA_USER2,DATA_PASS);
		navToolBar.goToSiteExplorer();		
		cMenu.copyAndPasteNode(CONTENT_FOLDER_PATH_1, CONTENT_FOLDER_PATH_2);
		info("Verify cannot paste");
		waitForTextPresent(ecmsPer.MESSAGE_NO_RIGHT_TO_PASTE_NODE);
		click(button.ELEMENT_OK_BUTTON);

		info("Log out, log in as john to delete data");
		magAcc.signOut();
		magAcc.signIn(DATA_USER1,DATA_PASS);
		navToolBar.goToSiteExplorer();	
		cMenu.deleteDocument(CONTENT_FOLDER_PATH_1);
		cMenu.deleteDocument(CONTENT_FOLDER_PATH_2);
	}

	/**CaseId: 66928
	 * Go to Sites Management, stand at root path
	 * Create 1 Document folder
	 * Copy it
	 * Paste at root path
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test20_CopyDocumentFolderToRootPath()
	{ 
		String DOCUMENT_FOLDER_NAME = "BasicAction_CopyPaste_Document_Folder_20";
		By DOCUMENT_FOLDER_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_NAME));
		By VERIFY_AT_ROOT_PATH2 = By.xpath(cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", DOCUMENT_FOLDER_NAME).replace("${title2}", DOCUMENT_FOLDER_NAME));
		By VERIFY_AT_ROOT_PATH1 = By.xpath(ELEMENT_NODE_AT_ROOT.replace("${nodeName}", DOCUMENT_FOLDER_NAME.replaceAll("_", "").toLowerCase()));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Document Folder");
		cTemplate.createNewFolder(DOCUMENT_FOLDER_NAME, folderType.Document);

		info("Verify folder is created at root path");
		waitForAndGetElement(VERIFY_AT_ROOT_PATH1);

		info("Copy & paste at root path");
		cMenu.copyAndPasteNode(DOCUMENT_FOLDER_PATH, siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Verify copied folder is at root path");
		waitForAndGetElement(VERIFY_AT_ROOT_PATH2);

		info("Delete data");
		cMenu.deleteDocument(VERIFY_AT_ROOT_PATH2);
		cMenu.deleteDocument(DOCUMENT_FOLDER_PATH);
	}
	
	/**CaseId: 66939
	 * Go to Sites Management
	 * Create 1 folder
	 * Create 1 file, Copy it
	 * Paste in folder
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test31_CopyFileToFolder()
	{ 
		String CONTENT_FOLDER_NAME = "BasicAction_CopyPaste_Content_Folder_31";
		By CONTENT_FOLDER_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_NAME));
		String FILE_NAME = "BasicAction_CopyPaste_File_Document_31";
		String FILE_CONTENT="Content of File";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_NAME));
		By FILE_PATH_NEW = By.xpath(ELEMENT_SUB_NODE.replace("${parent}", CONTENT_FOLDER_NAME.replaceAll("_", "").toLowerCase()).replace("${child}", FILE_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create Content Folder");
		cTemplate.createNewFolder(CONTENT_FOLDER_NAME, folderType.Content);

		info("Create File & copy paste to content folder");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, FILE_CONTENT, FILE_NAME);
		cMenu.copyAndPasteNode(FILE_PATH, CONTENT_FOLDER_PATH);

		info("Verify result");
		ecms.goToNode(CONTENT_FOLDER_PATH);
		waitForAndGetElement(FILE_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(CONTENT_FOLDER_PATH);
		cMenu.deleteDocument(FILE_PATH);
	}

	/** CaseId: 66941
	 * Go to Sites Management
	 * Create 1 document: announcement
	 * Create 1 file, Copy it
	 * Paste in announcement
	 * Verify result
	 * Delete data
	 */
	@Test
	public void test32_CopyFileToOtherDocument()
	{ 
		String ANNOUN_NAME = "BasicAction_CopyPaste_Announcement_32";
		String ANNOUN_SUMMARY = "summary of announcement";
		By ANNOUN_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", ANNOUN_NAME));

		String FILE_NAME = "BasicAction_CopyPaste_File_Document_32";
		String FILE_CONTENT="Content of File";
		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_NAME));
		By FILE_PATH_NEW = By.xpath(ELEMENT_SUB_NODE.replace("${parent}", ANNOUN_NAME).replace("${child}", FILE_NAME));

		info("Go to CE");
		navToolBar.goToSiteExplorer();

		info("Create a new File");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, FILE_CONTENT, FILE_NAME);

		info("Choose Site Management");
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		info("Create a new Announcement");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(ANNOUN_NAME, ANNOUN_SUMMARY);

		info("Copy-paste");
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cMenu.copyAndPasteNode(FILE_PATH, ANNOUN_PATH);

		info("Verify");
		ecms.goToNode(ANNOUN_PATH);		
		waitForAndGetElement(FILE_PATH_NEW);

		info("Delete data");
		cMenu.deleteDocument(ANNOUN_PATH);
		cMenu.deleteDocument(FILE_PATH);
	}

	/**CaseId: 66942 -> remove this case: can not create sub node in uploaded file
	 * Go to Sites Management
	 * Create Upload a file
	 * Create 1 file, Copy it
	 * Paste in uploaded file
	 * Verify result
	 * Delete data
	 */
//	@Test
//	public void test33_CopyFileToUploadedFile()
//	{ 
//		By UPLOADED_FILE_XPATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "Winter.jpg"));
//
//		String FILE_NAME="BasicAction_CopyPaste_File_Document_33";
//		String FILE_CONTENT="Content of File";
//		By FILE_PATH = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_NAME));
//		By FILE_PATH_NEW = By.xpath(cMenu.ELEMENT_VERIFY.replace("${destination}", "Winter.jpg").replace("${source}", FILE_NAME));
//
//		info("Go to CE");
//		navToolBar.goToSiteExplorer();
//
//		info("Upload a File");
//		ecms.uploadFile(UPLOADED_FILE_PATH);
//
//		info("Create File");
//		actBar.goToAddNewContent();
//		cTemplate.createNewFile(FILE_NAME, FILE_CONTENT, FILE_NAME);
//		
//		info("Copy paste");
//		cMenu.copyAndPasteNode(FILE_PATH, UPLOADED_FILE_XPATH);
//
//		info("Verify result");
//		ecms.goToNode(UPLOADED_FILE_XPATH);
//		bPre.setUpPreferenceOption(SHOW_DMS_STRUCTURE);
//		waitForElementPresent(FILE_PATH_NEW);
//
//		info("Delete data");
//		ecms.goToNode(UPLOADED_FILE_XPATH);
//		cMenu.deleteDocument(UPLOADED_FILE_XPATH);
//		ecms.goToNode(FILE_PATH);
//		cMenu.deleteDocument(FILE_PATH);
//	}		
	
	/** CaseId: 66925: Copy content folder to uploaded file -> remove this case: can not create sub node in uploaded file
	 * 
	 */
}