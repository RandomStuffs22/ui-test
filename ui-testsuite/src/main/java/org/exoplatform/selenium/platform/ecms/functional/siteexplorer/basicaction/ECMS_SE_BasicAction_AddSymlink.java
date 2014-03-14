package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date: 17/04/2013
 * @author lientm
 * Update follow new test cases for plf4
 */

/**
 * <li>PLF4: Update by
 * @author vuna2
 * </li>
 *
 */
/*
 * @author: Lientm
 * @date: 24/09/2012
 */
public class ECMS_SE_BasicAction_AddSymlink extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	Dialog dialog;
	ManageAlert magAlert;

	//Ecms
	EcmsBase ecms;
	EcmsPermission ecmsPer;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		dialog = new Dialog(driver);
		button = new Button(driver);
		magAlert = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		ecmsPer = new EcmsPermission(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);

		info("Add symlink for action bar if it does not existed");
		navToolBar.goToSiteExplorer();
		//actBar.addSymlinkToActionBar();
		actBar.addItem2ActionBar("addSymLink", actBar.ELEMENT_ACTION_BAR_ADD_SYMLINK);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//		logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseID: 66548
	 * case01: Open form to add Symlink for node using icon in action bar
	 * go to add symlink
	 * check form
	 */
	@Test
	public void test01_CheckFormAddSymlinkForNode(){		  
		info("Check Add Symlink Form");
		actBar.goToAddSymlinkTab();

		//check Add symlink form
		waitForAndGetElement(ecms.ELEMENT_ADD_SYMLINK_POPUP);
		waitForTextPresent("Symlink Manager");
		waitForAndGetElement(ecms.ELEMENT_SYMLINK_PATH_NODE);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK_NAME);  
		info("Check add symlink popup successfully");
		dialog.closeMessageDialog();
	}

	/**CaseID: 67256
	 * case02: Add Symlink when right click on node but do not select this node
	 * create new node (folder, file document)
	 * right click on node -> add symlink
	 * check symlink
	 */
	@Test
	public void test02_AddSymlinkWhenRightClick(){
		String DATA_FOLDER = "ECMS_AddSymlink_Content_folder_02";
		By ELEMENT_FOLDER = By.linkText(DATA_FOLDER);
		String FILE_NAME = "ECMS_AddSymlink_file_02";
		By ELEMENT_FILE = By.linkText(FILE_NAME);

		//create node: content folder
		cTemplate.createNewFolder(DATA_FOLDER, folderType.Content);
		info("Create new content folder successfully");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, FILE_NAME, FILE_NAME);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		// add symlink
		info("Add symlink for node by right click");
		cMenu.contextMenuAction(ELEMENT_FOLDER, ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK);
		cMenu.contextMenuAction(ELEMENT_FILE, ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK);

		// check symlink
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_FOLDER.replaceAll("_", "").toLowerCase() + ".lnk"));
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", FILE_NAME + ".lnk"));
		info("Add symlink for nodes successful");	

		//delete symlink
		cMenu.deleteData(ELEMENT_FOLDER);
		cMenu.deleteData(ELEMENT_FILE);
	}

	/**CaseId: 67252
	 * case03: Add Symlink for some nodes at the same time
	 * select some nodes
	 * add symlink for some node
	 */
	//@Test(groups = {"pending"})
	public void test03_AddSymlinkForSomenode(){
		String folderName = "ECMS_AddSymlink_Content_folder_03";
		String fileName = "ECMS_AddSymlink_file_03";
		By elementFolder = By.xpath("//div/span[text()='" + folderName + "']");
		By elementFile = By.xpath("//div/span[text()='" + fileName + "']");

		cTemplate.createNewFolder(folderName, folderType.Content);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, fileName, fileName);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		WebElement folder = waitForAndGetElement(elementFolder);
		WebElement file = waitForAndGetElement(elementFile);

		Actions actions = new Actions(driver);
		actions.keyDown(folder, Keys.CONTROL).moveToElement(file).click().contextClick(file).
		moveToElement(waitForAndGetElement(ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK)).click().build().perform();

		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", folderName + ".lnk"));
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", fileName + ".lnk"));

		//delete data
		cMenu.deleteData(elementFolder);
		cMenu.deleteData(elementFile);
	}

	/** CaseId: 67251
	 * case04: Add Symlink for root
	 * go to site explorer
	 * add symlink at root
	 */
	@Test
	public void test04_AddSymlinkForRoot(){	
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		info("Add symlink for root");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		waitForAndGetElement(ELEMENT_SYMLINK);
		info("Add symlink successfully");

		//delete symlink
		cMenu.deleteData(ELEMENT_SYMLINK);
	}

	/** CaseId: 67235
	 * case05: Add Symlink for node is Content Folder
	 * create new content folder
	 * add symlink for node with target node = intranet/documents
	 * check symlink
	 */
	@Test
	public void test05_AddSymlinkForContentFolder(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_05";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for content folder");
		actBar.addSymlink("collaboration", "sites/intranet/documents" , DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 67236
	 * case06: Add Symlink for node is Document Folder
	 * create new document folder
	 * add symlink for node with target node = Intranet/documents
	 * check symlink
	 */
	@Test
	public void test06_AddSymlinkForDocumentFolder(){
		String DATA_DOCUMENT_FOLDER = "ECMS_AddSymlink_Document_folder_06";
		By ELEMENT_DOCUMENT_FOLDER = By.linkText(DATA_DOCUMENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";

		info("Create new document folder");
		cTemplate.createNewFolder(DATA_DOCUMENT_FOLDER, folderType.Document);
		waitForAndGetElement(ELEMENT_DOCUMENT_FOLDER);
		info("Create new document folder successfully");

		//add symlink
		ecms.goToNode(ELEMENT_DOCUMENT_FOLDER);
		info("Add symlink for document folder");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		//delete data
		cMenu.deleteData(ELEMENT_DOCUMENT_FOLDER);
	}

	/** --> remove this testcase in plf4 (Can not add a subnode for nt:file)
	 * case08: Add Symlink for node is File document
	 * create new file document
	 * add symlink to file document
	 * check add successfully
	 */
	/*@Test
	public void test08_AddSymlinkForFileDocument(){
		String DATA_FILE_DOCUMENT = "filedocument08";
		By ELEMENT_FILE_DOCUMENT = By.linkText(DATA_FILE_DOCUMENT);
		String DATA_SYMLINK = "filedocument08.lnk";

		//create new file document
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();

		info("Create new file document");
		cTemplate.createNewFile(DATA_FILE_DOCUMENT, DATA_FILE_DOCUMENT, DATA_FILE_DOCUMENT);
		assert isElementPresent(ELEMENT_FILE_DOCUMENT):"Create new file document unsuccessfully";
		info("Create new file document successfully");

		//add symlink
		info("Add symlink for file document");
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		actBar.addSymlink("collaboration", "sites/" + DATA_FILE_DOCUMENT, DATA_SYMLINK);

		//bPre.setUpPreferenceOption("enableStructure");
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_FILE_DOCUMENT));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_FILE_DOCUMENT)): "Add symlink for file document unsuccessfully";

		//delete data
		cMenu.deleteData(ELEMENT_FILE_DOCUMENT);
	}*/

	/** --> --> remove this testcase in plf4 (Can not add a subnode for uploaded file)
	 * case13: Add Symlink for node is uploaded file
	 * upload file
	 * add symlink
	 * check add symlink successfully
	 */
	/*@Test
	public void test13_AddSymlinkForUploadedFile(){
		String DATA_UPLOAD_FILE_LINK = "TestData/ECMS_DMS_SE_Addsymlink.jpg";
		String DATA_SYMLINK = "ECMS_DMS_SE_Addsymlink.jpg.lnk";
		By ELEMENT_DOCUMENT_SYMLINK = By.linkText("ECMS_DMS_SE_Addsymlink.jpg");

		//create a new file document
		navToolBar.goToSiteExplorer();

		info("upload new file");
		ecms.uploadFile(DATA_UPLOAD_FILE_LINK);
		waitForTextPresent("ECMS_DMS_SE_Addsymlink.jpg");
		assert isElementPresent(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "ECMS_DMS_SE_Addsymlink.jpg")): "Create new file unsuccessfully";
		info("Create new file successfully");

		//add symlink
		actBar.addSymlink("collaboration", "sites/ECMS_DMS_SE_Addsymlink.jpg", DATA_SYMLINK);
		//goToNode(ELEMENT_UPLOAD_FILE);

		info("Add symlink for uploaded file");
		//checkPreferenceOption("enableStructure");
		waitForElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "ECMS_DMS_SE_Addsymlink.jpg"));
		assert isElementPresent(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", "ECMS_DMS_SE_Addsymlink.jpg")): "Add symlink for upload file unsuccessfully";
		info("Add symlink for upload file successfully");

		//delete data
		cMenu.deleteData(ELEMENT_DOCUMENT_SYMLINK);
	}*/

	/** CaseId: 67242
	 * case14: Add Symlink for node is Symlink
	 * create new node (content folder)
	 * add symlink for this node
	 * add symlink for symlink node
	 * check add successfully
	 */
	@Test
	public void test14_AddSymlinkForSymlinkNode(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_14";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String ELEMENT_SYMLINK = ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_CONTENT_FOLDER.replaceAll("_", "").toLowerCase() + ".lnk");
		String DATA_SYMLINK = "documents.lnk";

		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		info("Add symlink for node by right click");
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, ecms.ELEMENT_CONTEXT_MENU_ADD_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK);

		//add symlink for symlink node
		info("Add symlink for exiting symlink");
		ecms.goToNode(By.xpath(ELEMENT_SYMLINK));
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));
		info("Add symlink for symlink node successfully");

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 67245
	 * case15: Add Symlink for node to the node is Symlink
	 * create 1 symlink at root
	 * create new node
	 * Check can not add symlink for this node with target is a symlink
	 */
	@Test
	public void test15_AddSymlinkForNodeToSymlinkNode(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_15";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		info("Add symlink for root");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK);

		info("Add new content folder");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//check cannot add symlink for node with target node is symlink documents.lnk
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToTargetNodeWhenAddSymlink("sites");
		waitForElementNotPresent(By.xpath(ecms.ELEMENT_SYMLINK_PATH_NODE_TITLE.replace("${node}", DATA_SYMLINK)));
		info("cannot select target node is a symlink node");
		click(ecms.ELEMENT_ADD_SYMLINK_CLOSE_WINDOWS);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
		cMenu.deleteDocument(ELEMENT_SYMLINK);
	}

	/** CaseId: 67253
	 * case16: Add Symlink when 'Symlink Name' field is blank
	 */
	@Test
	public void test16_AddSymlinkWithBlankName(){
		info("Add symlink with blank name");
		actBar.addSymlink("collaboration", "sites/intranet/documents", "");

		//check alert
		magAlert.verifyAlertMessage("The field \"Symlink Name\" is required.");
		button.cancel();
		info("cannot add symlink with blank name");
	}

	/** CaseId: 67253
	 * case17: Add Symlink when “Path Node” field is blank
	 */
	@Test
	public void test17_AddSymlinkWithBlankPathNode(){
		info("Add symlink with blank name");
		actBar.addSymlink("collaboration", "", "symlink.lnk");

		//check alert
		magAlert.verifyAlertMessage("Please enter the path node.");
		button.cancel();
		info("cannot add symlink with blank path node");
	}

	/**
	 * case18: Add Symlink when 'Symlink Name' field contains special characters like @, #,$..
	 * 
	 */
	@Test
	public void test18_AddSymlinkWithNameContainsSpecialCharacter(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_18";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		info("create new content folder");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		info("Add symlink with name contains special characters");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToAddSymlinkTab();
		click(actBar.ELEMENT_ADD_ITEM);
		actBar.selectHomePathForCategoryTree("sites/intranet/documents");
		for(int i = 0; i < cTemplate.DATA_SPECIAL_CHARACTER.length; i++){
			info("Input symlink name contains character: " + cTemplate.DATA_SPECIAL_CHARACTER[i]);
			type(ecms.ELEMENT_SYMLINK_NAME, cTemplate.DATA_SPECIAL_CHARACTER[i], true);
			button.save();

			//check alert
			magAlert.verifyAlertMessage("Please enter the symlink name.");
			info("cannot add symlink with name contains qspecial characters");
		}
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 67146
	 * case19: Check the displaying of workspace when user dose not permission to view it when add Symlink 
	 * login with mary
	 * create new node - content folder
	 * check cannot add symlink for this node with system workspace
	 */
	@Test
	public void test19_CheckDisplayWorkspaceWhenUserHasNotPermission(){
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_19";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//login with mary
		magAcc.signOut();
		info("Login ECMS with user: mary");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		info("create new content folder");
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);

		//check cannot add symlink for this node with system workspace
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.addSymlink("dms-system", "exo:ecm", "exo:ecm.lnk");

		//check alert
		boolean checkMessage = isTextPresent("Access denied! You do not have the permission to add symlink to this node.");
		if (checkMessage){
			magAlert.verifyAlertMessage("Access denied! You do not have the permission to add symlink to this node.");
		}else{
			magAlert.verifyAlertMessage("Access denied. You do not have permission to add symlink to this node.");
		}
		info("cannot add symlink with user has not permission to view it");
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 67138
	 * case20: Check the displaying of node when user dose not permission to view it when add Symlink 
	 * login with john
	 * create new node - content folder
	 * set permission for this node: user mary does not have permission view node
	 * login with mary
	 * add symlink and choose target node
	 * check user does not see that content node
	 */
	@Test
	public void test20_CheckDisplayNodeWhenUserNotHaveViewPermission(){	  
		String DATA_CONTENT_FOLDER = "ECMS_AddSymlink_Content_folder_20";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//set permission for this node: user mary does not have permission view node 
		info("Set for user mary does not have view permission");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToNodePermissionManagement();
		ecmsPer.removeDefaultPermissionOfNode();
		button.close();
		magAcc.signOut();

		info("Login ECMS with user: mary");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();

		//check user does not see that content node
		actBar.goToTargetNodeWhenAddSymlink("sites");
		waitForElementNotPresent(By.xpath(ecms.ELEMENT_SYMLINK_PATH_NODE_TITLE.replace("${node}", DATA_CONTENT_FOLDER)));
		info("cannot see node when user does not have view permission");
		click(ecms.ELEMENT_ADD_SYMLINK_CLOSE_WINDOWS);

		//delete data
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 67407
	 * case21: Add new Symlink has the same name with existing Symlink in Content Folder
	 * create new content folder
	 * add 2 symlink have same name for content folder
	 * check add successfully
	 */
	@Test
	public void test21_Add2SymlinksHaveSameNameInContentFolder(){
		String DATA_CONTENT_FOLDER = "contentfolder21";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK2 = By.xpath("//*[contains(@mousedown,'collaboration:/sites/"
				+ DATA_CONTENT_FOLDER + "/" + DATA_SYMLINK.replace(".", "") + "[2]')]");

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		info("Add 2 symlinks have same name in content folder");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);

		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK2);
		info("Add 2 symlink same name in content folder successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);		  
	}

	/** CaseId: 67409
	 * case22: Add new Symlink has the same name with existing Symlink in Document Folder
	 * create new document folder
	 * add 2 symlink have same name for document folder
	 * check cannot add
	 */
	@Test
	public void test22_Add2SymlinksHaveSameNameInDocumentFolder(){
		String DATA_DOCUMENT_FOLDER = "documentfolder22";
		By ELEMENT_DOCUMENT_FOLDER = By.linkText(DATA_DOCUMENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";

		info("Create new document folder");
		cTemplate.createNewFolder(DATA_DOCUMENT_FOLDER, folderType.Document);

		info("Add 2 symlinks have same name in document folder");
		ecms.goToNode(ELEMENT_DOCUMENT_FOLDER);
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));	
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		//check alert
		magAlert.verifyAlertMessage("The node name already exists.");
		button.cancel();
		info("cannot add 2 symlink same name in document folder");

		//delete data
		cMenu.deleteDocument(ELEMENT_DOCUMENT_FOLDER);		  
	}

	/** CaseId: 67408
	 * case23: Add new Symlink has the same name with existing Symlink in document  
	 * create new document - document has parent type is nt:nt:unstructured: web content
	 * check cannot add 2 symlinks have samve name in document
	 */
	@Test
	public void test23_Add2SymlinksHaveSameNameInDocument(){
		String WEB_CONTENT_NAME = "Webcontentname_23";
		By ELEMENT_WEB_CONTENT = By.linkText(WEB_CONTENT_NAME);
		String DATA_SYMLINK = "symlink23.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));
		By ELEMENT_SYMLINK2 = By.xpath("//*[contains(@mousedown,'collaboration:/sites/"
				+ WEB_CONTENT_NAME + "/" + DATA_SYMLINK.replace(".", "") + "[2]')]");

		//create new file document
		actBar.goToAddNewContent();

		info("Create new file document with user john");
		cTemplate.createNewWebContent(WEB_CONTENT_NAME, WEB_CONTENT_NAME, "", "", "", "");

		//add 2 symlink have same name for file document
		info("Add 2 symlinks have same name in file document");
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK);
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);
		waitForAndGetElement(ELEMENT_SYMLINK2);

		//delete data
		cMenu.deleteDocument(ELEMENT_WEB_CONTENT);
	}

	/** --> remove this testcase in plf4 (Can not add a subnode for uploaded file)
	 * case24: Add new Symlink has the same name with existing Symlink in uploaded file
	 * upload new file
	 * check cannot add 2 symlinks have same name in uploaded file
	 */
	/*@Test
	public void test24_Add2SymlinksHaveSameNameInUploadFile(){
		String DATA_UPLOAD_FILE_LINK = "TestData/ECMS_DMS_SE_Addsymlink.jpg"; 
		By ELEMENT_UPLOAD_FILE = By.linkText("ECMS_DMS_SE_Addsymlink.jpg");
		By ELEMENT_DOCUMENT_SYMLINK =  By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "ECMS_DMS_SE_Addsymlink.jpg"));

		//upload new file
		navToolBar.goToSiteExplorer();

		info("Upload new file");
		ecms.uploadFile(DATA_UPLOAD_FILE_LINK);
		waitForTextPresent("ECMS_DMS_SE_Addsymlink.jpg");

		ecms.goToNode(ELEMENT_UPLOAD_FILE);
		assert isElementPresent(ELEMENT_UPLOAD_FILE):"Upload file unsuccessfully";
		info("Upload file successfully");

		//add 2 symlink have same name for uploaded file
		info("Add 2 symlinks have same name in upload file");
		//addSymlink(ELEMENT_SELECT_PATH_DOCUMENTS, DATA_SYMLINK);
		actBar.addSymlink("collaboration", "sites/ECMS_DMS_SE_Addsymlink.jpg", "");

		//checkPreferenceOption("enableStructure");
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		//addSymlink(ELEMENT_SELECT_PATH_DOCUMENTS,DATA_SYMLINK);
		actBar.addSymlink("collaboration", "sites/ECMS_DMS_SE_Addsymlink.jpg", "");

		//check alert
		magAlert.verifyAlertMessage("The node name already exists.");
		button.cancel();
		info("cannot add 2 symlink same name in uploaded file");

		//delete data
		cMenu.deleteData(ELEMENT_UPLOAD_FILE);
	}*/

	/** CaseId: 67250
	 * case25: Add Symlink for node when user does not have permission to add in this node
	 * create new node - content folder
	 * set for user mary does not have add node permission
	 * login with mary
	 * check cannot add symlink for node
	 */
	@Test
	public void test25_AddSymlinkForNodeWhenUserNotHasAddNodePermission(){
		String DATA_CONTENT_FOLDER = "contentfolder25";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		info("Set for user mary does not have add node permission");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToNodePermissionManagement();
		ecmsPer.removeDefaultPermissionOfNode();
		ecms.selectUser(DATA_USER2);
		ecmsPer.setPermissionForNode(true, false, false);

		button.save();
		magAcc.signOut();

		info("Login with mary");
		magAcc.signIn(DATA_USER2, DATA_PASS);
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);

		//check cannot add symlink for node
		waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		if (waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null){
			click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		}
		rightClickOnElement(ELEMENT_CONTENT_FOLDER);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		info("Cannot add symlink for node because user does not have add node permission");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 67248
	 * Case26: Add Symlink for node when node is locked by locker
	 * create new node - content folder
	 * lock node
	 * add symlink for node
	 * check add node successfully
	 */
	/*@Test
	public void test26_AddSymlinkForNodeIsLockedByLocker(){
		String DATA_CONTENT_FOLDER = "contentfolder26";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//lock node
		info("Lock node");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, cMenu.ELEMENT_MENU_LOCK);
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER): "Lock node unsuccessfully";

		//add symlink for node
		info("Add symlink for locked node");
		//addSymlink(ELEMENT_SELECT_PATH_DOCUMENTS,DATA_SYMLINK);
		actBar.addSymlink("collaboration", "sites/contentfolder26", "contentfolder26.lnk");

		//check add symlink successful
		waitForElementPresent(ELEMENT_DOCUMENT_SYMLINK);
		assert isElementPresent(ELEMENT_DOCUMENT_SYMLINK):"Add symlink for content folder unsuccessfully";
		info("Add symlink for content folder successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}*/

	/** CaseId: 67249
	 * case27: Add Symlink for node when node is locked by user is not locker
	 * create new node with user John
	 * lock node by John
	 * login with mary
	 * check user mary can not add symlink for node
	 */
	@Test
	public void test27_AddSymlinkForNodeIsLockedByUserIsNotLocker(){
		String DATA_CONTENT_FOLDER = "contentfolder27";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER); 

		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		info("Lock node");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER): "Lock node unsuccessfully";
		driver.close();

		//login with mary
		info("Init new session and Login with Mary");
		loginWithAnotherAccOnThesameBrowser(DATA_USER2, DATA_PASS);
		magAcc = new ManageAccount(newDriver);
		navToolBar = new NavigationToolbar(newDriver);
		ecms = new EcmsBase(newDriver);
		cMenu = new ContextMenu(newDriver);
		siteExp = new SitesExplorer(newDriver);

		//check cannot add symlink for node by user mary
		navToolBar.goToSiteExplorer();
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		if (waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null){
			click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			//waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		}
		rightClickOnElement(ELEMENT_CONTENT_FOLDER);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		info("cannot add symlink for node by user is not locker");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);			
	}

	/**
	 * case28: Add Symlink for node to the node is being locked
	 * create new node - content folder
	 * lock this node
	 * go to acme/document to add symlink
	 * add symlink to locked node
	 * check add successful
	 */
	/*@Test(groups={"pending"})
	public void test28_AddSymlinkForNodeToLockedNode(){
		String DATA_CONTENT_FOLDER = "contentfolder28";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		By ELEMENT_CONTENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK_TITLE.replace("${symlinkTitle}", DATA_CONTENT_FOLDER));

		//create new content folder
		navToolBar.goToSiteExplorer();
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//lock node
		info("Lock node");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER, cMenu.ELEMENT_MENU_LOCK);
		assert cMenu.isLockedNode(ELEMENT_CONTENT_FOLDER): "Lock node unsuccessfully";

		//go to acme/documents
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME);
		ecms.goToNode(ecms.ELEMENT_SIDEBAR_ACME_DOCUMENTS);

		//add symlink for node to locked content node
		info("Add symlink for documents to locked node");
		actBar.goToAcmeNodeInAddSymlink();
		//usePaginator(ELEMENT_CONTENT_FOLDER_DIV,"cannot choose target node");
		//click(ELEMENT_CONTENT_FOLDER_DIV);
		//click(ELEMENT_SAVE_BUTTON);

		//check add symlink successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		waitForElementPresent(ELEMENT_CONTENT_SYMLINK);
		assert isElementPresent(ELEMENT_CONTENT_SYMLINK):"Add symlink unsuccessfully";
		info("Add symlink to locked node successfully");

		//delete data
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER);	  
	}*/

	/**
	 * case29: Add Symlink for node when node is check in status
	 * create new content folder
	 * check in node
	 * check cannot add symlink for node
	 */
	@Test
	public void test29_AddSymlinkForCheckInNode(){
		String DATA_FILE = "ECMS_DMS_SE_Addsymlink_FILE_29";
		By ELEMENT_FILE = By.linkText(DATA_FILE);

		//create new file
		actBar.goToAddNewContent();

		info("Create new file document with user john");
		cTemplate.createNewFile(DATA_FILE, DATA_FILE, DATA_FILE);
		waitForAndGetElement(ELEMENT_FILE);
		info("Create new file document successfully");

		//check in node
		info("Check in node");
		cMenu.contextMenuAction(ELEMENT_FILE, cMenu.ELEMENT_MENU_CHECKIN);

		//check cannot add symlink
		ecms.goToNode(ELEMENT_FILE);
		waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		if (waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null){
			click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			waitForElementNotPresent(ecms.ELEMENT_ACTION_BAR_ADD_SYMLINK);
		}
		rightClickOnElement(ELEMENT_FILE);
		waitForElementNotPresent(cMenu.ELEMENT_MENU_ADD_SYMLINK);
		info("Cannot add symlink for checked in node");

		//delete data
		cMenu.contextMenuAction(ELEMENT_FILE, cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteDocument(ELEMENT_FILE);
	}

	/** Case30: Add Symlink for node using drag and drop
	 * create new node - content folder
	 * create new document - file
	 * drag drop file to content folder
	 * check symlink of article in content folder
	 */
	//@Test(groups = {"pending"})
	public void test30_AddSymlinkForNodeUsingDragDrop(){
		String folderName = "ECMS_AddSymlink_Content_folder_30";
		String fileName = "ECMS_AddSymlink_file_30";
		By elementFolder = By.xpath("//div/span[text()='" + folderName + "']");
		By elementFile = By.xpath("//div/span[text()='" + fileName + "']");

		cTemplate.createNewFolder(folderName, folderType.Content);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(fileName, fileName, fileName);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		//		dragAndDropToObject(elementFile, elementFolder);

		WebElement folder = waitForAndGetElement(elementFolder);
		WebElement file = waitForAndGetElement(elementFile);

		Actions actions = new Actions(driver);
		actions.keyDown(file, Keys.CONTROL).keyDown(Keys.SHIFT).moveToElement(folder).release().build().perform();

		ecms.goToNode(elementFolder);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", fileName + ".lnk"));

		//delete data
		cMenu.deleteData(elementFolder);
	}

	/** CaseId: 66457
	 * Case31: Edit the name of added link node in “Symlink Name”
	 * create node - content folder
	 * add symlink with input name
	 */
	@Test
	public void test31_AddSymlinkWithInputName(){
		String DATA_CONTENT_FOLDER = "contentfolder31";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "symlink31.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", DATA_SYMLINK));

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink
		info("Add symlink for content folder with name is node default name");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.addSymlink("collaboration", "sites/intranet/documents", DATA_SYMLINK);

		//check add successfully
		waitForAndGetElement(ELEMENT_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 66252
	 * case32: View content of Symlink
	 * create new node - content folder
	 * add symlink for node with target node is folder: acme/documents
	 * check can view all subnode of documents in document.lnk
	 */
	@Test
	public void test32_ViewContentOfSymlink(){
		String DATA_CONTENT_FOLDER = "contentfolder32";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);
		String DATA_SYMLINK = "documents.lnk";
		By ELEMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", "documents.lnk"));

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink with target node - acme/documents for this content folder
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		info("Add symlink for node with target node is documents");
		actBar.addSymlink("collaboration", "sites/acme/documents", DATA_SYMLINK);

		waitForAndGetElement(ELEMENT_SYMLINK);
		click(ELEMENT_SYMLINK);

		//check subnode of symlink
		waitForAndGetElement(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "conditions.doc")));
		waitForAndGetElement(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "metro.pdf")));
		waitForAndGetElement(By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", "offices.jpg")));

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 66279
	 * case33: Remove Symlink of node
	 * create new node - content folder
	 * add symlink for node
	 * add path node -> remove path node
	 */
	@Test
	public void test33_RemoveSymlinkOfNode(){
		String DATA_CONTENT_FOLDER = "contentfolder33";
		By ELEMENT_CONTENT_FOLDER = By.linkText(DATA_CONTENT_FOLDER);

		//create new content folder
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER, ELEMENT_CONTENT_FOLDER);

		//add symlink for node
		ecms.goToNode(ELEMENT_CONTENT_FOLDER);
		actBar.goToTargetNodeWhenAddSymlink("sites/intranet");
		click(ecms.ELEMENT_TARGET_NODE.replace("${node}", "documents"));
		waitForAndGetElement(ecms.ELEMENT_REMOVE_PATH_NODE);
		click(ecms.ELEMENT_REMOVE_PATH_NODE);

		//check after remove
		assert getValue(ecms.ELEMENT_SYMLINK_PATH_NODE).isEmpty(): "Path target node is not empty";
		assert getValue(ecms.ELEMENT_SYMLINK_NAME).isEmpty(): "Name symlink is not empty";
		button.cancel();

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER);
	}

	/** CaseId: 66301
	 * case34: Rename the node which is the target node
	 * create 2 new node - content folder 1, content folder 2
	 * add symlink for content folder 1 with target node is content folder 2
	 * rename content folder 2
	 * check symlink is not change name
	 */
	@Test
	public void test34_RenameNodeIsTargetNodeOfSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder34_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder34_2";
		String SYMLINK_NAME = "symlink34.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));
		String DATA_CONTENT_FOLDER_2_NEW = "contentfolder34_2_edited";
		By ELEMENT_CONTENT_FOLDER_2_NEW = By.linkText(DATA_CONTENT_FOLDER_2_NEW);

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//rename content folder 2
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER_2, cMenu.ELEMENT_MENU_RENAME_NODE, DATA_CONTENT_FOLDER_2_NEW);

		//check name of symlink is not change
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Symlink is kept old name");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2_NEW);		  
	}

	/** CaseId: 66621
	 * case35: Delete the node which is the target node
	 * create 2 content folder: 1 and 2
	 * add symlink for content folder 1 with target node = content folder 2
	 * delete folder 2
	 * check symlink is deleted too
	 */
	@Test
	public void test35_DeleteNodeWithIsTargetNodeOfSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder35_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder35_2";
		String SYMLINK_NAME = "symlink35.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//delete content folder 2
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER_2);

		//check symlink is deleted 
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForElementNotPresent(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Symlink is deleted too");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
	}

	/** CaseId: 66300
	 * case36: Rename Symlink node
	 * create new node - content folder
	 * add symlink for node
	 * rename symlink, check name of target node is not changed
	 */
	@Test
	public void test36_RenameSymlink(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder36_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder36_2";
		String SYMLINK_NAME = "symlink36.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));
		String SYMLINK_NAME_NEW = "symlink36_edited.lnk";
		By ELEMENT_CONTENT_FOLDER_SYMLINK_NAME = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME_NEW));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//rename symlink
		cMenu.contextMenuAction(ELEMENT_CONTENT_FOLDER_SYMLINK, cMenu.ELEMENT_MENU_RENAME_NODE, SYMLINK_NAME_NEW);
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK_NAME); 

		//folder 2 is not renamed
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_2);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);	  
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);	 
	}

	/** CaseId: 66618
	 * case37: Delete Symlink node
	 * create new node - content folder
	 * add symlink for node
	 * delete symlink
	 */
	@Test
	public void test37_DeleteSymlinkOfNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder37_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder37_2";
		String SYMLINK_NAME = "symlink37.lnk";
		By ELEMENT_CONTENT_FOLDER_2 = By.xpath(ecms.ELEMENT_SIDEBAR_NODE_TITLE.replace("${nodeName}", DATA_CONTENT_FOLDER_2));

		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2, ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//delete symlink
		cMenu.deleteData(ELEMENT_CONTENT_FOLDER_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}

	/** CaseId: 66988
	 * case38: Copy Symlink node to other node
	 * create 2 node: content folder 1 and 2
	 * create symlink for content folder 1
	 * copy symlink from content folder 1 to content folder 2
	 */
	@Test
	public void test38_CopySymlinkNodeToOtherNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder38_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder38_2";
		By ELEMENT_CONTENT_FOLDER_2 = By.linkText(DATA_CONTENT_FOLDER_2);
		String SYMLINK_NAME = "symlink38.lnk";
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/intranet/documents", SYMLINK_NAME);
		info("Add symlink successfully");

		//copy symlink from content folder 1 to content folder 2
		cMenu.copyAndPasteNode(ELEMENT_DOCUMENT_SYMLINK, ELEMENT_CONTENT_FOLDER_2);

		//check copy successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		waitForAndGetElement(ELEMENT_DOCUMENT_SYMLINK);
		info("Copy symlink successfully");

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);		  
	}

	/** CaseId: 66826
	 * case39: Cut Symlink node to other node
	 * create 2 node: content folder 1 and 2
	 * create symlink for content folder 1
	 * cut symlink form content folder 1 to content folder 2 
	 */
	@Test
	public void test39_CutSymlinkNodeToOtherNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder39_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder39_2";
		By ELEMENT_CONTENT_FOLDER_2 = By.linkText(DATA_CONTENT_FOLDER_2);
		String SYMLINK_NAME = "symlink39.lnk";
		By ELEMENT_DOCUMENT_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/intranet/documents", SYMLINK_NAME);
		info("Add symlink successfully");

		//cut symlink from content folder 1 to content folder 2
		cMenu.cutAndPasteNode(ELEMENT_DOCUMENT_SYMLINK, ELEMENT_CONTENT_FOLDER_2);

		//check cut successfully
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_2);
		waitForAndGetElement(ELEMENT_DOCUMENT_SYMLINK);
		info("Copy symlink successfully");

		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		waitForElementNotPresent(ELEMENT_DOCUMENT_SYMLINK);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}

	/** CaseId: 66722
	 * case40: Create child nodes in Symlink node
	 * create new node: content folder
	 * add symlink for node
	 * add content folder for symlink
	 * check add successfully
	 */
	@Test
	public void test40_CreateChildNodeInSymlinkNode(){
		String DATA_CONTENT_FOLDER_1 = "contentfolder40_1";
		By ELEMENT_CONTENT_FOLDER_1 = By.linkText(DATA_CONTENT_FOLDER_1);
		String DATA_CONTENT_FOLDER_2 = "contentfolder40_2";
		By ELEMENT_CONTENT_FOLDER_2 =  By.linkText(DATA_CONTENT_FOLDER_2);
		String SYMLINK_NAME = "symlink40.lnk";
		By ELEMENT_CONTENT_FOLDER_SYMLINK = By.xpath(ecms.ELEMENT_SYMLINK.replace("${symlinkTitle}", SYMLINK_NAME));
		String DATA_CONTENT_FOLDER_3 = "contentfolder40_3";
		By ELEMENT_CONTENT_FOLDER_3 = By.linkText(DATA_CONTENT_FOLDER_3);

		//create new content folder 1
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_1, ELEMENT_CONTENT_FOLDER_1);

		//create new content folder 2
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_2,ELEMENT_CONTENT_FOLDER_2);

		info("Add symlink for content folder 1 with target node is content folder 2");
		ecms.goToNode(ELEMENT_CONTENT_FOLDER_1);
		actBar.addSymlink("collaboration", "sites/" + DATA_CONTENT_FOLDER_2, SYMLINK_NAME);

		//check add successfully
		waitForAndGetElement(ELEMENT_CONTENT_FOLDER_SYMLINK);
		info("Add symlink for content folder 1 successfully");

		//add content folder for symlink
		click(ELEMENT_CONTENT_FOLDER_SYMLINK);
		cTemplate.createAndCheckContentFolder(DATA_CONTENT_FOLDER_3, ELEMENT_CONTENT_FOLDER_3);

		//delete data
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_1);
		cMenu.deleteDocument(ELEMENT_CONTENT_FOLDER_2);
	}

	/** CaseId: 69349
	 * Add Symlink to a Parent & Child selection
	 * setup "Add new content" and "Add symlink" for File management view
	 * create new parent node and child node
	 * add symlink for parent and child node simultaneously
	 */
	@Test
	public void test41_AddSymlinkToParentAndChild(){
		String DATA_FOLDER = "contentfolder41";
		String FILE_NAME = "ECMS_AddSymlink_file_41";
		By ELEMENT_FILE = By.linkText(FILE_NAME);

		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("List");
		//actBar.addNewContentToFileManagementView();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "List", "List");
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("List");
		//actBar.addSymlinkToFileManagementView();
		actBar.addItem2ActionBar("addSymLink", actBar.ELEMENT_ACTION_BAR_ADD_SYMLINK, "List", "List");
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("List");

		//create parent and child node
		cTemplate.createNewFolder(DATA_FOLDER, folderType.None);
		info("Add folder successfully");
		actBar.goToNodeByAddressPath("/" + DATA_FOLDER);

		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_NAME, FILE_NAME, FILE_NAME);
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", DATA_FOLDER)));
		waitForAndGetElement(ELEMENT_FILE);

		//select parent and child node
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", DATA_FOLDER)), 2);
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", FILE_NAME)), 2);
		click(actBar.ELEMENT_ADD_SYMLINK_LIST_VIEW);
		waitForAndGetElement(ecms.ELEMENT_SYMLINK_OTHER.replace("${name}", DATA_FOLDER));
		waitForAndGetElement(ecms.ELEMENT_SYMLINK_OTHER.replace("${name}", FILE_NAME));
		info("Add symlink successfully");

		//delete data
		//actBar.deleteDataInAdminView(DATA_FOLDER);
		actBar.actionsOnElement(DATA_FOLDER, actionType.DELETE);
	}		
}