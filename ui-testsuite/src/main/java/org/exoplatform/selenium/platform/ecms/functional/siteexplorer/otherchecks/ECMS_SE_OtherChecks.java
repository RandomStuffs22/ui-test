package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.otherchecks;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: PhuongDT
 * @date: 10/09/2013
 */
public class ECMS_SE_OtherChecks extends PlatformBase {
			//Platform
			ManageAccount magAcc;

			//Ecms
			ECMainFunction ecMain;
			NavigationToolbar navToolBar;
			EcmsBase ecms;
			ContextMenu cMenu;
			ContentTemplate cTemplate;
			ActionBar actBar;
			SitesExplorer siteExp;
			PageManagement pMag;
			ManageDrive magDrv;

			public final String DATA_USER = "john";
			//public final String DATA_PASS = DATA_PASS;

			@BeforeMethod
			public void beforeMethods() {
				initSeleniumTest();
				driver.get(baseUrl);
				info("Login ECMS with " + DATA_USER);
				magAcc = new ManageAccount(driver);
				ecMain = new ECMainFunction(driver); 
				navToolBar = new NavigationToolbar(driver);
				cTemplate = new ContentTemplate(driver);
				cMenu = new ContextMenu(driver);
				actBar = new ActionBar(driver);
				ecms = new EcmsBase(driver);
				pMag = new PageManagement(driver);
				siteExp = new SitesExplorer(driver);
				magDrv=new ManageDrive(driver);
				magAcc.signIn(DATA_USER, DATA_PASS);
				
			}

			@AfterMethod
			public void afterMethods() {
				info("Logout ECMS");
				driver.manage().deleteAllCookies();
				driver.quit();
			}
			
			/**
			 * == Sort nodes in admin view ==
			 * Test case ID: 66152
			 * Step 1: Go to Site Explorer
			 * Step 2: Select the way to view node
			 * Step 3:Sort nodes 
			 */
			@Test
			public void test01_SortNodesInAdminView(){
				/*Declare variable*/
				String DATA_DRIVE_NAME = "test01_SortNodesInAdminView";
				By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
				String aFolder = "adocuments";
				String bFolder = "bdocuments";
				
				/* Step 1: Go to Site Explorer*/
				// Go to Site Administration
				ecMain.goToManageDrive();
			
				//Perform to create new driver with Collaboration workspace, 
				//and Admin View, icons View, simple view, Timeline View, Cover flow view,Simple view,slide show view
				//Note: on plf4.0.x, can't create cover flow view, slide show view, timeline view
				magDrv.addNewDrive(DATA_DRIVE_NAME, "collaboration","sites", "Platform/Administration","*",
						"", "Admin/Icons/List", false, true);
				
				navToolBar.goToSiteExplorer();
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				info("-- the driver is created --");
				
				/* Step 2: Select the way to view node*/
				//In 'Select view', select Admin view
				click(pdriver);
				actBar.goToViewMode("Admin");
				cTemplate.createNewFolder(aFolder, folderType.None);
				cTemplate.createNewFolder(bFolder, folderType.None);
				
				/* Step 3:Sort nodes */			
				//Click on the label of information of nodes
				actBar.sortBy("Name");
				//Node are sorted by ascending or descending
				waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", aFolder).replace("${node2}", bFolder));
				actBar.sortBy("Name");
				waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", bFolder).replace("${node2}", aFolder));
				
				/*Clear data*/
				info("-- Clear data --");
				actBar.actionsOnElement(bFolder + "/" + aFolder, actionType.DELETE, true, true);
				magDrv.deleteDrive(DATA_DRIVE_NAME);
			}
			
			/**
			 * == Remove thumbnail image ==
			 * Test case ID: 66283
			 * Step 1: Go to Site Explorer
			 * Step 2: Create node
			 * Step 3: Add thumbnail image for node
			 * Step 4: Remove thumbnail image
			 * PENDING: refer issue https://jira.exoplatform.org/browse/FQA-1057: [SELENIUM] Cannot upload thumbnail on ecms
			 */
			@Test(groups={"pending"})
			public void test02_RemoveThumbnailImage(){
				/*Declare variable*/
				String document = "test02_RemoveThumbnailImage";
				String image = "TestData/test02_RemoveThumbnailImage.jpg";
				By pdocument = By.linkText(document);
				String DATA_DRIVE_NAME = "test01_SortNodesInAdminView";
				By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
				
				/* Step 1: Go to Site Explorer*/
				// Go to Site Administration
				ecMain.goToManageDrive();
			
				//Perform to create new driver with Collaboration workspace, 
				//and Admin View, icons View, simple view, Timeline View, Cover flow view,Simple view,slide show view
				//Note: on plf4.0.x, can't create cover flow view, slide show view, timeline view
				magDrv.addNewDrive(DATA_DRIVE_NAME, "collaboration","sites", "Platform/Administration","*",
						"", "Admin/Icons/List", false, true);
				
				//Go to Site Explorer
				navToolBar.goToSiteExplorer();
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				click(pdriver);
				
				/* Step 2: Create node*/
				//Check if create content button is shown on action bar
				actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
				navToolBar.goToSiteExplorer();
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				click(pdriver);
				
				//Create node (folder, document) or upload file
				actBar.goToAddNewContent();
				cTemplate.createNewWebContent(document,document,"", "", "", "");
				
				/* Step 3:Add thumbnail image for node */			
				actBar.addItem2ActionBar("overloadThumbnail", actBar.ELEMENT_OVERLOAD_THUMBNAIL, "Icons", "Icons");
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				click(pdriver);
				actBar.goToNode(pdocument);
				actBar.uploadThumbnail(image);
				actBar.goToViewMode("Icons");
				
				/*Step 4: Remove thumbnail image*/
				//Click on 'Overload thumbnail' icon
				//Click 'Delete' icon near the image in 'Add thumbnail image' pop-up
				
				//The thumbnail image of node is removed. 
				
				/*Clear data*/
				info("-- Clear data --");
				actBar.actionsOnElement(document,actionType.DELETE,false,true);
				magDrv.deleteDrive(DATA_DRIVE_NAME);
			}
			
			/**
			 * == Icons view ==
			 * Test case ID: 66505
			 * Step 1: Create new driver
			 * Step 2: Select the way to view node
			 */
			@Test
			public void test03_IconsView(){
				/*Declare variable*/
				String DATA_DRIVE_NAME = "test03_IconsView";
				By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
				String aFolder = "adocuments";
				By pnode = By.xpath(ecms.ELEMENT_VERIFY_THUMBNAIL.replace("${name}", aFolder));
				/* Step 1: Go to Site Explorer*/
				// Go to Site Administration
				ecMain.goToManageDrive();
			
				//Perform to create new driver with Collaboration workspace, 
				//and Admin View, icons View, simple view, Timeline View, Cover flow view,Simple view,slide show view
				magDrv.addNewDrive(DATA_DRIVE_NAME, "collaboration","sites", "Platform/Administration","*",
						"", "Admin/Icons/List", false, true);
				
				//the driver is created
				navToolBar.goToSiteExplorer();
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				info("-- the driver is created --");
				
				/* Step 2: Select the way to view node*/
				//Click Icons View
				click(pdriver);
				actBar.goToViewMode("Icons");
				cTemplate.createNewFolder(aFolder, folderType.None);
				
				//Tree folder will be viewed at Icons View
				//Nodes are viewed as icon
				waitForAndGetElement(pnode);
				actBar.goToViewMode("Admin");
				
				/*Clear data*/
				info("-- Clear data --");
				actBar.actionsOnElement(aFolder, actionType.DELETE, true, true);
				magDrv.deleteDrive(DATA_DRIVE_NAME);
			}
			
			/**
			 * == Cover flow ==
			 * Test case ID: 66712
			 * Step 1: Create new driver
			 * Step 2: Select the way to view node
			 * Pending: Not apply on plf4.0.x
			 */
			@Test(groups={"pending"})
			public void test04_CoverFlow(){
			}
			
			/**
			 * == Add thumbnail image for node ==
			 * Test case ID: 66283
			 * Step 1: Go to Site Explorer
			 * Step 2: Create node
			 * Step 3: Open form to add thumbnail image
			 * Step 4: Add image
			 * PENDING: refer issue https://jira.exoplatform.org/browse/FQA-1057: [SELENIUM] Cannot upload thumbnail on ecms
			 */
			@Test(groups={"pending"})
			public void test05_AddThumbnailTmageForNode(){
				/*Declare variable*/
				String document = "test05_AddThumbnailTmageForNode";
				String image = "TestData/test05_AddThumbnailTmageForNode.jpg";
				By pdocument = By.linkText(document);
				String DATA_DRIVE_NAME = "test01_SortNodesInAdminView";
				By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
				/* Step 1: Go to Site Explorer*/
				//Go to Site Explorer
				navToolBar.goToSiteExplorer();
				
				/* Step 2: Create node*/
				//Check if create content button is shown on action bar
				actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
				navToolBar.goToSiteExplorer();
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				click(pdriver);
				
				//Create node (folder, document) or upload file
				actBar.goToAddNewContent();
				cTemplate.createNewWebContent(document,document,"", "", "", "");
				
				/* Step 3:Open form to add thumbnail image */
				/*Step 4: Add image*/
				actBar.addItem2ActionBar("overloadThumbnail", actBar.ELEMENT_OVERLOAD_THUMBNAIL, "Icons", "Icons");
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				click(pdriver);
				actBar.goToNode(pdocument);
				actBar.uploadThumbnail(image);
				actBar.goToViewMode("Icons");
				//Node is added thumbnail image. Back to parent node, you will see thumbnail image of node
				/*Clear data*/
				info("-- Clear data --");
				actBar.actionsOnElement(document,actionType.DELETE,false,true);
				magDrv.deleteDrive(DATA_DRIVE_NAME);
			}
			
			/**
			 * == Admin view ==
			 * Test case ID: 67281
			 * Step 1: Create new driver
			 * Step 2: Select the way to view node
			 */
			@Test
			public void test06_AdminView(){
				/*Declare variable*/
				String DATA_DRIVE_NAME = "test06_AdminView";
				By pdriver = By.xpath(magDrv.ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", DATA_DRIVE_NAME));
				String aFolder = "adocuments";
				By pnode = By.xpath(ecms.ELEMENT_VERIFY_DATE_NODE.replace("${namenode}", aFolder));
				
				/* Step 1: Go to Site Explorer*/
				// Go to Site Administration
				ecMain.goToManageDrive();
			
				//Perform to create new driver with Collaboration workspace, 
				//and Admin View, icons View, simple view, Timeline View, Cover flow view,Simple view,slide show view
				magDrv.addNewDrive(DATA_DRIVE_NAME, "collaboration","sites", "Platform/Administration","*",
						"", "Admin/Icons/List", false, true);
				
				//the driver is created
				navToolBar.goToSiteExplorer();
				actBar.showDrives();
				waitForAndGetElement(pdriver);
				info("-- the driver is created --");
				
				/* Step 2: Select the way to view node*/
				//Click Admin View
				click(pdriver);
				actBar.goToViewMode("Admin");
				cTemplate.createNewFolder(aFolder, folderType.None);
				
				//Tree folder will be viewed at Admin View
				waitForAndGetElement(pnode);
				
				/*Clear data*/
				info("-- Clear data --");
				actBar.actionsOnElement(aFolder, actionType.DELETE, true, true);
				magDrv.deleteDrive(DATA_DRIVE_NAME);
			}
}
