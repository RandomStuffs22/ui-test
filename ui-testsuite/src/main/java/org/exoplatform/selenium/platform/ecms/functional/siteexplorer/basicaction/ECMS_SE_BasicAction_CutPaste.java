package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.BrowserPreferences;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import static org.exoplatform.selenium.TestLogger.*;

/*
 * @author: Thuntn
 * @date: 08/10/2012
 */
public class ECMS_SE_BasicAction_CutPaste extends PlatformBase {
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
	ManageView magView;
	SitesExplorer siteExp;

	public String USER = "john";
	public String PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethod() {
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
		magView = new ManageView(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(USER,PASS);
		navToolBar.goToSiteExplorer();
	}

	//CaseID: 66802
	//cut a content folder and paste it to another node
	@Test
	public void test01_CutContentFolderPasteInOtherNode() {
		String title = "ECMS_DMS_SE_BasicAction_CutPaste_01";
		String targetNode = "target_CutPaste_01";
		By bCont = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title)); 
		By bTarget = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", targetNode));

		info("Cut a content folder and paste it to another node!");
		//create a content folder 
		cTemplate.createNewFolder(targetNode, folderType.Content);
		cTemplate.createNewFolder(title, folderType.Content);
		//cut and paste content folder to acme folder
		cMenu.cutAndPasteNode(bCont, bTarget);

		//verify if paste the content folder successfully in acme folder 
		waitForElementNotPresent(bCont);
		ecms.goToNode(targetNode);
		waitForAndGetElement(bCont);

		//delete data
		cMenu.deleteDocument(bTarget);
	}

	// CaseID 66803
	//cut a document folder and paste it to a Document folder
	@Test
	public void test02_CutDocumentFolderPasteInDocumentFolder() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_02";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_02_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a document folder and paste it to another node!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create a document folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Document);

		//cut and paste the document folder to acme folder
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste document folder successfully in destination folder 
		ecms.goToNode(bDocDes);
		waitForAndGetElement(bDocDes);

		//delete data
		cMenu.deleteDocument(bDocDes);
	}

	//CaseID 66803
	//cut a document folder and paste it to a content node
	@Test
	public void test02_CutDocumentFolderPasteInContentFolder() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_02";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_02_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a document folder and paste it to another node!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create a document folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//cut and paste the document folder to acme folder
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste document folder successfully in destination folder 
		ecms.goToNode(bDocDes);
		waitForAndGetElement(bDocDes);

		//delete data
		cMenu.deleteDocument(bDocDes);
	}
	
	//CaseID: 66804
	//Cut a document folder and paste it to file document!
	@Test
	public void test04_CutDocumentFolderPasteIntoFileDocument() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_04";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_04_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a document folder and paste it to file document!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create a file document -destination
		actBar.goToAddNewContent();
		cTemplate.createNewFile(titleDes, titleDes, titleDes);

		//cut and paste the document folder to file document
		cMenu.cutNode(bDoc);
		rightClickOnElement(bDocDes);
		waitForElementNotPresent(ELEMENT_PASTE_NODE);
		
		//verify if paste document folder successfully in a file document
		
		cMenu.deleteDocument(bDocDes);
		cMenu.deleteDocument(bDoc);
	}
	
	//CaseID: 66804
	//Cut a document folder and paste it to uploaded file!
	@Test
	public void test04_CutDocumentFolderPasteToUploadedFile() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_04";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_04_des.zip";
		String img="TestData/ECMS_DMS_SE_BasicAction_CutPaste_04_des.zip";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a document folder and paste it to uploaded file!");
		//create a document folder - source
		cTemplate.createNewFolder(title, folderType.Document);

		//create an uploaded file -destination
		ecms.uploadFile(img);
		waitForAndGetElement(bDocDes);

		//cut and paste the document folder to uploaded file
		cMenu.cutNode(bDoc);
		rightClickOnElement(bDocDes);
		waitForElementNotPresent(ELEMENT_PASTE_NODE);

		//delete data
		cMenu.deleteDocument(bDocDes);
		cMenu.deleteDocument(bDoc);
	}
	
	//CaseID 66806
	//Cut a file document and paste it to a node!
	@Test
	public void test09_CutFilePasteToANode() {
		String title="ecmsbasicactio09";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ecmsbasicactio09des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a file document and paste it to a node!");
		//create a content folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//create a file document - source
		actBar.goToAddNewContent();
		cTemplate.createNewFile(title, title, title);

		//cut and paste the file document to a node
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste file document in a node
		waitForElementNotPresent(bDoc);
		ecms.goToNode(bDocDes);
		ecms.goToNode(bDoc);
		waitForAndGetElement(By.xpath("//*[contains(@value,'"+ titleDes + "/" + title + "')]"));

		//delete data
		cMenu.deleteDocument(bDocDes);
	}

	// CaseID 66819
	//Cut an uploaded file and paste it to a Node
	@Test
	public void test20_CutUploadedFilePasteToANode() {
		String img = "TestData/ECMS_DMS_SE_BasicAction_CutPaste.png";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "ECMS_DMS_SE_BasicAction_CutPaste.png"));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_20_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));
		
		info("Cut an uploaded file and paste it to a content node!");

		//upload an uploaded file - source
		ecms.uploadFile(img);
		waitForAndGetElement(bDoc);

		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		//create a node -destination
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//cut and paste the uploaded file to a content folder
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste uploaded file in a content folder
		waitForElementNotPresent(bDoc);
		
		ecms.goToNode(titleDes);
		waitForAndGetElement(bDoc);

		//delete data
		cMenu.deleteDocument(bDocDes);
	}
	
	// CaseID 66816
	//Cut a checked in node and paste it to a content folder!
	@Test
	public void test21_CutCheckInNodePasteToContentFolder() {
		String title="ECMS_DMS_SE_BasicAction_CutPaste_21";
		By bDoc=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));
		String titleDes="ECMS_DMS_SE_BasicAction_CutPaste_21_des";
		By bDocDes=By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", titleDes));

		info("Cut a checkin node and paste it to a content folder!");

		//create a file document
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(title, title);
		cMenu.contextMenuAction(bDoc, cMenu.ELEMENT_MENU_CHECKIN);

		//add icon Version to action bar
		actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
		actBar.chooseDrive(ecms.ELEMENT_SITES_MANAGEMENT_DRIVE);
		actBar.addVersionForNode(bDoc, "version file");

		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);

		//create a content folder -destination
		cTemplate.createNewFolder(titleDes, folderType.Content);

		//cut and paste the uploaded file to an article
		cMenu.cutAndPasteNode(bDoc, bDocDes);

		//verify if paste the uploaded file in an article
		waitForElementNotPresent(bDoc);
		ecms.goToNode(titleDes);
		waitForAndGetElement(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", title));

		//delete data
		cMenu.deleteDocument(bDocDes);
	}	

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
}
