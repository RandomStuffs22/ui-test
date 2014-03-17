package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author NhungVT
 *@date: 02/10/2012
 */

public class ECMS_SE_BasicAction_Clipboard extends PlatformBase {
	
	//Platform
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ManageAccount magAcc;
	
	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;
	
	//Define data
	public String DATA_CONTENT_FOLDER = "contentfolder";
	public String DATA_DOCUMENT_FOLDER = "documentfolder";
	public String DATA_FILE_NAME = "filename";
	public String DATA_UPLOAD_FILE_PATH ="TestData/Winter.jpg";
	//public String ELEMENT_TITLE = "//*[@title='${DATA}']";	

	@BeforeMethod()
	public void beforeTest()
	{
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn("john", DATA_PASS);
	}

	//Delete  all action in clipboard
	@Test()
	public void test20_DeleteActionInClipboard()
	{
		//goto Site Explorer
		navToolBar.goToSiteExplorer();

		//Create & Copy a Content Folder
		cTemplate.createNewFolder(DATA_CONTENT_FOLDER, folderType.Content);
		cMenu.contextMenuAction(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_CONTENT_FOLDER), cMenu.ELEMENT_COPY_NODE);

		//Create & Cut a Document Folder
		cTemplate.createNewFolder(DATA_DOCUMENT_FOLDER, folderType.Document);
		cMenu.contextMenuAction(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_DOCUMENT_FOLDER), cMenu.ELEMENT_CUT_NODE);
		
		//Create & Copy a File Document
		actBar.goToAddNewContent();
		cTemplate.createNewFile(DATA_FILE_NAME, DATA_FILE_NAME, DATA_FILE_NAME);
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cMenu.contextMenuAction(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME), cMenu.ELEMENT_COPY_NODE);

		//Upload & Cut a File
		ecms.uploadFile(DATA_UPLOAD_FILE_PATH);
		waitForTextPresent("Winter.jpg");
		cMenu.contextMenuAction(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "Winter.jpg"), cMenu.ELEMENT_CUT_NODE);

		//Select acme > documents node
		doubleClickOnElement(actBar.ELEMENT_SIDEBAR_ACME);
		waitForAndGetElement(actBar.ELEMENT_SIDEBAR_ACME_DOCUMENTS);
		doubleClickOnElement(actBar.ELEMENT_SIDEBAR_ACME_DOCUMENTS);

		//Click on Clipboard
		waitForAndGetElement(siteExp.ELEMENT_CLIPBOARD_ICON);
		click(siteExp.ELEMENT_CLIPBOARD_ICON);

		//Verify Path & Cm of Nodes
		waitForAndGetElement(siteExp.ELEMENT_VERIFY_ACTION.replace("${titleOfFile}", DATA_CONTENT_FOLDER));
		waitForAndGetElement(siteExp.ELEMENT_VERIFY_ACTION.replace("${titleOfFile}", DATA_DOCUMENT_FOLDER));
		waitForAndGetElement(siteExp.ELEMENT_VERIFY_ACTION.replace("${titleOfFile}", DATA_FILE_NAME));
		waitForAndGetElement(siteExp.ELEMENT_VERIFY_ACTION.replace("${titleOfFile}", "Winter.jpg"));
		
		//Clear all action in list
		click(siteExp.ELEMENT_CLEAR_ALL_ICON);
		waitForAndGetElement(siteExp.MESSAGE_CLEAR_ALL);

		//Delete data
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cMenu.deleteDocument(By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_CONTENT_FOLDER)));
		cMenu.deleteDocument(By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_DOCUMENT_FOLDER)));
		cMenu.deleteDocument(By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", DATA_FILE_NAME)));
		cMenu.deleteDocument(By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "Winter.jpg")));
	}

	@AfterMethod()
	public void afterTest()
	{
		//logoutEcms();
		driver.quit();
	}
}