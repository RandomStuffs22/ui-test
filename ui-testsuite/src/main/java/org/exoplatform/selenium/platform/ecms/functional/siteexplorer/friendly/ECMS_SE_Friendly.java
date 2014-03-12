package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.friendly;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
//import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
//import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;

/*
 * @author: PhuongDT
 * @date: 10/09/2013
 */
public class ECMS_SE_Friendly extends PlatformBase {
	//Platform
	ManageAccount magAcc;

	//Ecms
	NavigationToolbar navToolBar;
	EcmsBase ecms;
	ContextMenu cMenu;
	ContentTemplate cTemplate;
	ActionBar actBar;
	SitesExplorer siteExp;
	PageEditor pEditor;
	PageManagement pMag;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		pEditor = new PageEditor(driver);
		pMag = new PageManagement(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);

	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Check friendly URL when open a content on Content List porlet ==
	 * Test case ID: 70772
	 * Step 1: Create free layout web content
	 * Step 2: Add new page with CLV portlet
	 * Step 3: View content from CLV
	 */
	// REMOVED
	//			@Test
	//			public void test01_CheckFriendlyURLWhenOpenAContentOnContentListPorlet(){
	//				/*Declare variable*/
	//				String folder = "folder01";
	//				String node = "webcontent01";
	//				By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
	//				By bContent = By.xpath(siteExp.ELEMENT_TEXT.replace("{$node}", node));
	//				String pCLVName = "CLV_FolderBy_01";
	//				String pCLVPath = "General Drives/Sites Management";
	//
	//				/* Step 1: Create free layout web content*/
	//				//Go to Site Explorer
	//				navToolBar.goToSiteExplorer();
	//				
	//				//Check if create content button is shown on action bar
	//				actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
	//				
	//				//Create and select a folder(documents folder for example)
	//				cTemplate.createNewFolder(folder, folderType.Content);
	//				//Create new free layout web content
	//				ecms.goToNode(By.linkText(folder));
	//				actBar.goToAddNewContent();
	//				cTemplate.createNewWebContent(node,node,"", "", "", "");
	//				
	//				//Publish it and verify Free layout web content is created and published successfully
	//				//Check if publication button is shown on action bar
	//				actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION);
	//				ecms.goToNode(By.linkText(folder));
	//				ecms.goToNode(bNode);
	//				actBar.publishDocument();
	//				
	//				/* Step 2: Add new page with CLV portlet*/
	//				//Go to Edit --> Add Page
	//				//Add new page with folder path to "documents" folder at step 1
	//				driver.get(DEFAULT_BASEURL);
	//				pEditor.addCLVPageAndCLVpath(pCLVName, pCLVPath, folder);
	//				//Page is added successfully
	//				//Content created at step 1 is shown on content list viewer
	//				waitForAndGetElement(bNode);
	//				
	//				/* Step 3: View content from CLV*/			
	//				//Click on content to view detail
	//				click(bContent);
	//				
	//				//URL of content is simple, for example: http://localhost:8080/portal/acme/detail/'CONTENT
	//				String url = driver.getCurrentUrl();
	//				info(url);
	//				assert(url.contains("http://localhost:8080/portal/intranet/detail?content-id=/repository/collaboration/sites/"+folder+"/"+node));
	//				
	//				/*Clear data*/
	//				info("-- Clear data --");
	//				pMag.deletePageAtManagePageAndPortalNavigation(pCLVName, true, "intranet", false, "Home", pCLVName);
	//				navToolBar.goToSiteExplorer();
	//				cMenu.deleteDocument(By.linkText(folder));
	//			}

	/**
	 * == Check friendly URL when open a document on Content List porlet ==
	 * Test case ID: 70773
	 * Step 1: Create free layout web content
	 * Step 2: Add new page with CLV portlet
	 * Step 3: View content from CLV
	 */
	//REMOVED
	//			@Test
	//			public void test02_CheckFriendlyURLWhenOpenADocumentOnContentListPorlet(){
	//				/*Declare variable*/
	//				String folder = "folder02";
	//				String node = "ECMS_DMS_SE_Upload_docfile.doc";
	//				By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
	//				By bContent = By.xpath(siteExp.ELEMENT_TEXT.replace("{$node}", node));
	//				String pCLVName = "CLV_FolderBy_02";
	//				String pCLVPath = "General Drives/Sites Management";
	//
	//				/* Step 1: Create free layout web content*/
	//				//Go to Site Explorer
	//				navToolBar.goToSiteExplorer();
	//				
	//				//Create and select a folder(documents folder for example)
	//				cTemplate.createNewFolder(folder, folderType.Content);
	//
	//				//Check if create content button is shown on action bar
	//				actBar.addItem2ActionBar("upload", actBar.ELEMENT_UPLOAD_FILE_LINK);
	//				//Upload a document (.doc, .pdf...)
	//				ecms.goToNode(By.linkText(folder));
	//				ecms.uploadFile("TestData/"+node);
	//				
	//				//Publish it and verify Free layout web content is created and published successfully
	//				//Check if publication button is shown on action bar
	//				ecms.goToNode(bNode);
	//				actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION);
	//				ecms.goToNode(By.linkText(folder));
	//				ecms.goToNode(bNode);
	//				actBar.publishDocument();
	//				
	//				/* Step 2: Add new page with CLV portlet*/
	//				//Go to Edit --> Add Page
	//				//Add new page with folder path to "documents" folder at step 1
	//				driver.get(DEFAULT_BASEURL);
	//				pEditor.addCLVPageAndCLVpath(pCLVName, pCLVPath, folder);
	//				//Page is added successfully
	//				//Content created at step 1 is shown on content list viewer
	//				waitForAndGetElement(bNode);
	//				
	//				/* Step 3: View content from CLV*/			
	//				//Click on content to view detail
	//				click(bContent);
	//				
	//				//URL of content is simple, for example: http://localhost:8080/portal/acme/detail/'CONTENT
	//				String url = driver.getCurrentUrl();
	//				assert(url.contains("http://localhost:8080/portal/intranet/detail?content-id=/repository/collaboration/sites/"+folder+"/"+node));
	//				
	//				/*Clear data*/
	//				info("-- Clear data --");
	//				pMag.deletePageAtManagePageAndPortalNavigation(pCLVName, true, "intranet", false, "Home", pCLVName);
	//				navToolBar.goToSiteExplorer();
	//				cMenu.deleteDocument(By.linkText(folder));
	//			}
	//			
	/**
	 * == Check friendly URL when open an image file on Content List porlet ==
	 * Test case ID: 70774
	 * Step 1: Create free layout web content
	 * Step 2: Add new page with CLV portlet
	 * Step 3: View content from CLV
	 */
	//REMOVED
	//			@Test
	//			public void test03_CheckFriendlyURLWhenOpenAImageFileOnContentListPorlet(){
	//				/*Declare variable*/
	//				String folder = "folder03";
	//				String node = "ECMS_DMS_SE_Upload_imgfile.jpg";
	//				By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
	//				By bContent = By.xpath(siteExp.ELEMENT_TEXT.replace("{$node}", node));
	//				String pCLVName = "CLV_FolderBy_03";
	//				String pCLVPath = "General Drives/Sites Management";
	//
	//				/* Step 1: Create free layout web content*/
	//				//Go to Site Explorer
	//				navToolBar.goToSiteExplorer();
	//				
	//				//Create and select a folder(documents folder for example)
	//				cTemplate.createNewFolder(folder, folderType.Content);
	//				//Check if create content button is shown on action bar
	//				actBar.addItem2ActionBar("upload", actBar.ELEMENT_UPLOAD_FILE_LINK);
	//				//Upload an image file
	//				ecms.goToNode(By.linkText(folder));
	//				ecms.uploadFile("TestData/"+node);
	//				
	//				//Publish it and verify Free layout web content is created and published successfully
	//				//Check if publication button is shown on action bar
	//				ecms.goToNode(bNode);
	//				actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION);
	//				ecms.goToNode(By.linkText(folder));
	//				ecms.goToNode(bNode);
	//				actBar.publishDocument();
	//				
	//				/* Step 2: Add new page with CLV portlet*/
	//				//Go to Edit --> Add Page
	//				//Add new page with folder path to "documents" folder at step 1
	//				driver.get(DEFAULT_BASEURL);
	//				pEditor.addCLVPageAndCLVpath(pCLVName, pCLVPath, folder);
	//				//Page is added successfully
	//				//Content created at step 1 is shown on content list viewer
	//				waitForAndGetElement(bNode);
	//				
	//				/* Step 3: View content from CLV*/			
	//				//Click on content to view detail
	//				click(bContent);
	//				
	//				//URL of content is simple, for example: http://localhost:8080/portal/acme/detail/'CONTENT
	//				String url = driver.getCurrentUrl();
	//				assert(url.contains("http://localhost:8080/portal/intranet/detail?content-id=/repository/collaboration/sites/"+folder+"/"+node));
	//				
	//				/*Clear data*/
	//				info("-- Clear data --");
	//				pMag.deletePageAtManagePageAndPortalNavigation(pCLVName, true, "intranet", false, "Home", pCLVName);
	//				navToolBar.goToSiteExplorer();
	//				cMenu.deleteDocument(By.linkText(folder));
	//			}
}
