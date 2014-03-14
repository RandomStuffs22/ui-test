package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.createnode;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * August, 2nd, 2013	
 *
 */
public class ECMS_SE_CreateNode_Upload_Action_FileExisting extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	Dialog dialog;
	Button button;

	//Ecms
	EcmsBase ecms;
	ContextMenu cMenu;
	ActionBar actBar;
	SitesExplorer sitesEx;
	ContentTemplate cTemplate; 

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		actBar = new ActionBar(driver);
		dialog = new Dialog(driver);
		sitesEx = new SitesExplorer(driver);
		button = new Button(driver);
		cTemplate = new ContentTemplate(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 66208
	 * Cancel uploading an existing file in Document
	 * 
	 */
	@Test
	public void test01_CancelUploadingExistingFileInDocument(){
		String FILE_PDF_NAME = "ECMS_DMS_SE_Upload_pdffile";

		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();

		info("Create a data test");
		ecms.uploadFile("TestData/" + FILE_PDF_NAME + ".pdf");

		info("Upload an existing file");
		uploadFileAndCheckDisplayedMessage("TestData/" + FILE_PDF_NAME + ".pdf");
		info("The warning message is displayed:  Already in use Keep both, Replace or Cancel");

		info("Cancel uploading an existing file");
		click(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Cancel"));
		info("Verify that File is not uploaded");
		waitForAndGetElement(ecms.ELEMENT_CANCEL_UPLOAD_FILE.replace("${title}", FILE_PDF_NAME + ".pdf"));

		info("Restore data");
		actBar.actionsOnElement(FILE_PDF_NAME, actionType.DELETE);		
	}

	/**
	 * Qmetry ID: 74417
	 * Keep an existing file when uploading in Document
	 * Error: Refer: https://jira.exoplatform.org/browse/ECMS-5117
	 */
	@Test
	public void test02_KeepAnExistingFileWhenUploadingInDocument(){
		String FILE_MOFFICE_NAME = "ECMS_DMS_SE_Upload_docfile";

		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();

		info("Create a data test");
		ecms.uploadFile("TestData/" + FILE_MOFFICE_NAME + ".doc");
		click(ecms.ELEMENT_UPLOAD_CLOSE_TAB);
		click(sitesEx.ELEMENT_BUTTON_REFRESH_TOPBAR_MENU);
		button.ok();
		Utils.pause(3000);

		info("Upload an existing file");
		uploadFileAndCheckDisplayedMessage("TestData/" + FILE_MOFFICE_NAME + ".doc");
		info("The warning message is displayed:  Already in use Keep both, Replace or Cancel");

		info("Click on the link [Keep both]");
		click(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Keep both"));	

		info("Check: A new file is created with an increment number in the path");
		waitForAndGetElement(ecms.ELEMENT_HREF_NODE_LINK.replace("${nodeName}", FILE_MOFFICE_NAME), DEFAULT_TIMEOUT, 1, 2);
		click(ecms.ELEMENT_FILE_CLONE_CHECKBOX.replace("${node}", FILE_MOFFICE_NAME + ".doc[2]"), 2);
		click(cMenu.ELEMENT_MENU_DELETE);
		dialog.deleteInDialog();
		waitForElementNotPresent(ecms.ELEMENT_FILE_CLONE_CHECKBOX.replace("${node}", FILE_MOFFICE_NAME + ".pdf[2]"));

		info("Restore data");
		actBar.goToViewMode("Admin");
		actBar.actionsOnElement(FILE_MOFFICE_NAME, actionType.DELETE);	
	}

	/**
	 * Qmetry ID: 74418
	 * Replace an existing file in Document
	 * 
	 */
	@Test
	public void test03_ReplaceAnExistingFileInDocument(){
		String FILE_OPOFFICE_NAME = "Delete_multiple_files_folder_1";
		String editTitle = "newTitle";

		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();

		info("Create a data test");
		ecms.uploadFile("TestData/" + FILE_OPOFFICE_NAME + ".txt");
		click(ecms.ELEMENT_UPLOAD_CLOSE_TAB);
		click(sitesEx.ELEMENT_BUTTON_REFRESH_TOPBAR_MENU);
		button.ok();
		Utils.pause(3000);

		ecms.goToNode(FILE_OPOFFICE_NAME, true);
		click(actBar.ELEMENT_EDIT_LINK);
		type(cTemplate.ELEMENT_NEWFILE_TITLE_TEXTBOX, editTitle, false);
		button.saveAndClose();
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		WebElement elementBefore = waitForAndGetElement(ecms.ELEMENT_FILE_INFORMATION.replace("${node}", FILE_OPOFFICE_NAME));
		String dateBefore = elementBefore.getText();
		info("Date of document creation..." + dateBefore);

		info("Upload an existing file");
		uploadFileAndCheckDisplayedMessage("TestData/" + FILE_OPOFFICE_NAME + ".txt");
		info("The warning message is displayed:  Already in use Keep both, Replace or Cancel");

		info("Replace an existing file in Document");
		ecms.uploadFile("TestData/" + FILE_OPOFFICE_NAME + ".txt");
		click(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Replace"));
		click(sitesEx.ELEMENT_BUTTON_REFRESH_TOPBAR_MENU);
		button.ok();
		Utils.pause(3000);	
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", FILE_OPOFFICE_NAME));
		WebElement elementAfter = waitForAndGetElement(ecms.ELEMENT_FILE_INFORMATION.replace("${node}", FILE_OPOFFICE_NAME));
		String dateAfter = elementAfter.getText();
		info("Replacement, change a File Date to..." + dateAfter);

		assert !dateBefore.equals(dateAfter): "Error: The current file is NOT overriden";
		info("Restore data");
		actBar.actionsOnElement(FILE_OPOFFICE_NAME, actionType.DELETE);	
	}

	/**
	 * Qmetry ID: 74449
	 * Keep existing files when uploading in Site Explorer
	 * Error: Refer: https://jira.exoplatform.org/browse/ECMS-5117
	 */
	@Test
	public void test04_KeepExistingFilesWhenUploadingInSiteExplorer(){
		String FILE_NAME = "ECMS_CSS_File_After";

		info("Go to Content Explorer");
		navToolBar.goToSiteExplorer();

		info("Create a data test");
		ecms.uploadFile("TestData/" + FILE_NAME + ".txt");
		click(ecms.ELEMENT_UPLOAD_CLOSE_TAB);
		click(sitesEx.ELEMENT_BUTTON_REFRESH_TOPBAR_MENU);
		button.ok();
		Utils.pause(3000);

		info("Upload an existing file");
		uploadFileAndCheckDisplayedMessage("TestData/" + FILE_NAME + ".txt");
		info("The warning message is displayed:  Already in use Keep both, Replace or Cancel");

		info("Click on the link [Keep both]");
		click(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Keep both"));	

		info("Check: A new file is created with an increment number in the path");
		waitForAndGetElement(ecms.ELEMENT_FILE_CLONE.replace("${node}", FILE_NAME + ".txt[2]"), DEFAULT_TIMEOUT, 1, 2);

		info("Restore data");
		cMenu.deleteDocument(ecms.ELEMENT_FILE_CLONE.replace("${node}", FILE_NAME + ".txt[2]"));
		cMenu.deleteDocument(By.linkText(FILE_NAME + ".txt"));
	}

	/**
	 * Qmetry ID: 74450
	 * Replace an existing file in Site Explorer
	 * 
	 */
	@Test
	public void test05_ReplaceAnExistingFileInSiteExplorer(){
		String FILE_NAME = "ECMS_CSS_File_Before.txt";

		info("Go to Content Explorer");
		navToolBar.goToSiteExplorer();

		info("Create a data test");
		ecms.uploadFile("TestData/" + FILE_NAME);
		click(ecms.ELEMENT_UPLOAD_CLOSE_TAB);
		click(sitesEx.ELEMENT_BUTTON_REFRESH_TOPBAR_MENU);
		button.ok();
		Utils.pause(3000);
		WebElement elementBefore = waitForAndGetElement(ecms.ELEMENT_FILE_CREATED_DATE.replace("${nodeTitle}", FILE_NAME));
		String dateBefore = elementBefore.getText();
		info("Date of document creation..." + dateBefore);
		Utils.pause(65000);

		info("Replace an existing file in Document");
		ecms.uploadFile("TestData/" + FILE_NAME);
		click(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Replace"));
		Utils.pause(1000);
		click(sitesEx.ELEMENT_BUTTON_REFRESH_TOPBAR_MENU);
		button.ok();

		WebElement elementAfter = waitForAndGetElement(ecms.ELEMENT_FILE_CREATED_DATE.replace("${nodeTitle}", FILE_NAME));
		String dateAfter = elementAfter.getText();
		info("Replacement, change a File Date to..." + dateAfter);	
		assert !dateBefore.equals(dateAfter): "Error: The current file is NOT overriden";

		info("Restore data");
		cMenu.deleteDocument(By.linkText(FILE_NAME));	
	}

	/**
	 * Qmetry ID: 74842
	 * Cancel uploading an existing file in Site Explorer
	 * 
	 */
	@Test
	public void test06_CancelUploadingAnExistingFileInSiteExplorer(){
		String FILE_NAME = "ECMS_DMS_SE_Upload_htmlfile.html";

		info("Go to Content Explorer");
		navToolBar.goToSiteExplorer();

		info("Create a data test");
		ecms.uploadFile("TestData/" + FILE_NAME);
		click(ecms.ELEMENT_UPLOAD_CLOSE_TAB);
		click(sitesEx.ELEMENT_BUTTON_REFRESH_TOPBAR_MENU);
		button.ok();
		Utils.pause(3000);

		info("Upload an existing file");
		uploadFileAndCheckDisplayedMessage("TestData/" + FILE_NAME);
		info("The warning message is displayed:  Already in use Keep both, Replace or Cancel");

		info("Cancel uploading an existing file");
		click(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Cancel"));
		info("Verify that File is not uploaded");
		waitForAndGetElement(ecms.ELEMENT_CANCEL_UPLOAD_FILE.replace("${title}", FILE_NAME));

		info("Restore data");
		cMenu.deleteDocument(By.linkText(FILE_NAME));
	}	
	
	//Verify warning message after uploading a file in Documents/Content Explorer
	public void uploadFileAndCheckDisplayedMessage(String uploadFileName){
		ecms.uploadFile(uploadFileName);
		waitForAndGetElement(ecms.ELEMENT_WARNING_UPLOAD_FILE_ICON);
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Cancel"));
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Replace"));
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_FILE_ACTION.replace("${action}", "Keep both"));
	}
}