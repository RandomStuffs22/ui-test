package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.createnode;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * July, 15th, 2013
 */
public class ECMS_SE_CreateNode_File extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ManageAlert magAlt;

	//Ecms
	EcmsBase ecms;
	ContextMenu cMenu;
	ContentTemplate cTemplate;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		button = new Button(driver);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		cTemplate = new ContentTemplate(driver);
		magAlt = new ManageAlert(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 67321
	 * Add File document in Content folder
	 *  
	 */
	@Test
	public void test01_AddFileDocumentInContentFolder(){
		String CONTENT_FOLDER_TITLE = "ECMS_SE_File_ContentFolder_01";
		String FILE_TITLE = "ECMS_SE_File_Document_01";

		info("Step 1: Add Content folder");
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(CONTENT_FOLDER_TITLE, folderType.Content);

		info("Step 2 & 3: Show form to Add Content & Do Add Content");
		ecms.goToNode(By.linkText(CONTENT_FOLDER_TITLE));
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_TITLE, FILE_TITLE, FILE_TITLE);

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_TITLE));		
	}

	/**
	 * Qmetry ID: 67322
	 * Add File document in Document folder
	 *  
	 */
	@Test
	public void test02_AddFileDocumentInDocumentFolder(){
		String DOCUMENT_FOLDER_TITLE = "ECMS_SE_File_DocumentFolder_02";
		String FILE_TITLE = "ECMS_SE_File_Document_02";

		info("Step 1: Add Document folder");
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(DOCUMENT_FOLDER_TITLE, folderType.Document);

		info("Step 2 & 3: Show form to Add Content & Do Add Content");
		ecms.goToNode(By.linkText(DOCUMENT_FOLDER_TITLE));
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_TITLE, FILE_TITLE, FILE_TITLE);

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_TITLE));		
	}

	/**
	 * Qmetry ID: 67323
	 * Add File document in File Document
	 *  
	 */
	@Test
	public void test03_AddFileDocumentInFileDocument(){
		String FILE_TITLE = "ECMS_SE_File_Document_03";

		info("Add new file document");
		navToolBar.goToSiteExplorer();
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_TITLE, FILE_TITLE, FILE_TITLE);

		info("Users cannot see [Add Content] icon on action bar");
		ecms.goToNode(By.linkText(FILE_TITLE));
		waitForElementNotPresent(actBar.ELEMENT_NEW_CONTENT_LINK);
		mouseOverAndClick(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForElementNotPresent(actBar.ELEMENT_NEW_CONTENT_LINK);

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_TITLE));
	}

	/**
	 * Qmetry ID: 67328
	 * Add File document in uploaded file
	 *  
	 */
	@Test
	public void test04_AddFileDocumentInUploadedFile(){
		String FILE_LINK = "TestData/ECMS_DMS_SE_Upload_docfile.doc";
		String FILE_TITLE = "ECMS_DMS_SE_Upload_docfile.doc";

		info("Select an uploaded file ");
		navToolBar.goToSiteExplorer();
		ecms.uploadFile(FILE_LINK);

		info("Users cannot see [Add Content] icon on action bar");
		ecms.goToNode(By.linkText(FILE_TITLE));
		waitForElementNotPresent(actBar.ELEMENT_NEW_CONTENT_LINK);
		mouseOverAndClick(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForElementNotPresent(actBar.ELEMENT_NEW_CONTENT_LINK);

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", FILE_TITLE));
	}

	/**
	 * Qmetry ID: 67397
	 * Add new File document in locked node by locker 
	 *  
	 */
	@Test
	public void test05_AddNewFileDocumentInLockedNodeByLocker(){
		String DOCUMENT_FOLDER_TITLE = "ECMS_SE_File_DocumentFolder_05";
		String FILE_TITLE = "ECMS_SE_File_Document_05";

		info("Add a new folder/document and lock this node");
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(DOCUMENT_FOLDER_TITLE, folderType.Document);
		cMenu.contextMenuAction(By.linkText(DOCUMENT_FOLDER_TITLE), cMenu.ELEMENT_CONTEXT_MENU_LOCK);
		assert cMenu.isLockedNode(By.linkText(DOCUMENT_FOLDER_TITLE)): "Failed to lock the node..." + DOCUMENT_FOLDER_TITLE;

		info("Add a File document into selected locked node by locker");
		ecms.goToNode(By.linkText(DOCUMENT_FOLDER_TITLE));
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_TITLE, FILE_TITLE, FILE_TITLE);

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DOCUMENT_FOLDER_TITLE));
	}

	/**
	 * Qmetry ID: 67398
	 * Add new File document with blank Name or Content
	 *  
	 */
	@Test
	public void test06_AddNewFileDocumentWithBlankNameOrContent(){
		String CONTENT_FOLDER_TITLE = "ECMS_SE_File_ContentFolder_06";
		String FILE_TITLE = "ECMS_SE_File_Document_06";

		info("Add a new content folder");
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(CONTENT_FOLDER_TITLE, folderType.Content);

		info("Add a new file with blank required fields");
		ecms.goToNode(By.linkText(CONTENT_FOLDER_TITLE));
		actBar.goToAddNewContent();
		//with name field is blank
		click(cTemplate.ELEMENT_NEWFILE_LINK);
		type(cTemplate.ELEMENT_NEWFILE_NAME_TEXTBOX, "", false);
		inputDataToFrame(cTemplate.ELEMENT_NEWFILE_CONTENT_FRAME, FILE_TITLE, true);
		switchToParentWindow();
		type(cTemplate.ELEMENT_NEWFILE_TITLE_TEXTBOX, FILE_TITLE, false);
		button.saveAndClose();
		magAlt.verifyAlertMessage(cTemplate.MESSAGE_NAME_REQUIRED_FIELD);

		//with content is blank
		type(cTemplate.ELEMENT_NEWFILE_NAME_TEXTBOX, FILE_TITLE, false);
		inputDataToFrame(cTemplate.ELEMENT_NEWFILE_CONTENT_FRAME, "", true);
		switchToParentWindow();
		type(cTemplate.ELEMENT_NEWFILE_TITLE_TEXTBOX, FILE_TITLE, true);
		button.saveAndClose();
		magAlt.verifyAlertMessage(cTemplate.MESSAGE_CONTENT_REQUIRED_FIELD);
		button.close();
		
		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_TITLE));
	}

	/**
	 * Qmetry ID: 67417
	 * Add new text/html File document (2)
	 * Create a new text/html file: mime type =text/html, do not choose source in FCK Editor
	 */
	@Test
	public void test07_AddNewTextHtmlFileDocument_2(){
		String CONTENT_FOLDER_TITLE = "ECMS_SE_File_ContentFolder_07_2";
		String FILE_TITLE = "ECMS_SE_File_Document_07_2";

		info("Create a node that allow to add a File document");
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(CONTENT_FOLDER_TITLE, folderType.Content);

		info("Add a new file without using source mode in FCK Editor");
		ecms.goToNode(By.linkText(CONTENT_FOLDER_TITLE));
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_TITLE, FILE_TITLE, FILE_TITLE);
		ecms.goToNode(By.linkText(FILE_TITLE));

		//View as [html] tab
		driver.switchTo().frame(waitForAndGetElement(cTemplate.ELEMENT_NEWFILE_FRAME_HTML_TAB_XPATH));
		assert getText(cTemplate.ELEMENT_NEWFILE_HTML_TAB_P_XPATH).contains(FILE_TITLE) : "Failed to verify content..." + FILE_TITLE;
		switchToParentWindow();

		//View as plan text
		click(cTemplate.ELEMENT_NEWFILE_TEXT_TAB_XPATH);
		assert getText(By.cssSelector(cTemplate.ELEMENT_NEWFILE_TEXT_TAB_P_CSS)).contains("<p>  "+FILE_TITLE+"</p>") : "Failed to verify content..." + FILE_TITLE;

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_TITLE));
	}

	/**
	 * Qmetry ID: 67418
	 * Add new text/plain File document 
	 *  
	 */
	@Test
	public void test08_AddNewTextPlainFileDocument(){
		String CONTENT_FOLDER_TITLE = "ECMS_SE_File_ContentFolder_08";
		String FILE_TITLE = "ECMS_SE_File_Document_08";

		info("Create a node that allow to add a File document");
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(CONTENT_FOLDER_TITLE, folderType.Content);

		info("Add a new file using [Text/Plain] Mime Type");
		ecms.goToNode(By.linkText(CONTENT_FOLDER_TITLE));
		actBar.goToAddNewContent();
		cTemplate.createNewFile(FILE_TITLE, FILE_TITLE, FILE_TITLE, "", false, true, "text/plain");

		info("New File with plain type is added");
		//ecms.goToNode(By.linkText(FILE_TITLE));
		assert getText(By.cssSelector(cTemplate.ELEMENT_NEWFILE_PRE_CSS)).contains(FILE_TITLE) : "Failed to verify content..." + FILE_TITLE; 

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_TITLE));
	}

	/**
	 * Qmetry ID: 78909
	 * Add new text/html File document 
	 * Create a new text/html file: mime type =text/html, using source mode in FCK Editor 
	 */
	@Test
	public void test07_AddNewTextHtmlFileDocument_1(){
		String CONTENT_FOLDER_TITLE = "ECMS_SE_File_ContentFolder_07_1";
		String FILE_TITLE = "ECMS_SE_File_Document_07_1";

		info("Create a node that allow to add a File document");
		navToolBar.goToSiteExplorer();
		cTemplate.createNewFolder(CONTENT_FOLDER_TITLE, folderType.Content);

		info("Add a new file using source mode in FCK Editor");
		ecms.goToNode(By.linkText(CONTENT_FOLDER_TITLE));
		actBar.goToAddNewContent();
		click(cTemplate.ELEMENT_NEWFILE_LINK);
		type(cTemplate.ELEMENT_NEWFILE_NAME_TEXTBOX, FILE_TITLE, false);
		click(cTemplate.ELEMENT_NEWFILE_SOURCE_LINK_XPATH);
		type(cTemplate.ELEMENT_NEWFILE_SOURCE_TEXTAREA_XPATH, "<p>"+FILE_TITLE+"</p>", false);
		button.saveAndClose();

		//View as [html] tab
		driver.switchTo().frame(waitForAndGetElement(cTemplate.ELEMENT_NEWFILE_FRAME_HTML_TAB_XPATH));
		assert getText(cTemplate.ELEMENT_NEWFILE_HTML_TAB_P_XPATH).contains(FILE_TITLE) : "Failed to verify content..." + FILE_TITLE;
		switchToParentWindow();

		//View as plan text
		click(cTemplate.ELEMENT_NEWFILE_TEXT_TAB_XPATH);
		assert getText(By.cssSelector(cTemplate.ELEMENT_NEWFILE_TEXT_TAB_P_CSS)).contains("<p>"+FILE_TITLE+"</p>") : "Failed to verify content..." + FILE_TITLE;

		info("Restore data");
		cMenu.deleteDocument(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", CONTENT_FOLDER_TITLE));
	}
}