package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.admin;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: PhuongDT
 * @date: 27/08/2013
 */
public class ECMS_SE_Admin_ManageRelation extends PlatformBase {
	//Platform
		ManageAccount magAcc;
		ActionBar actBar;
		NavigationToolbar navToolBar;

		//Ecms
		EcmsBase ecms;
		ContentTemplate cTemplate;
		SitesExplorer siteExp;
		ContextMenu cMenu;

		public final String file = "phuong_filecontent.txt";

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
			cMenu= new ContextMenu(driver);
			magAcc.signIn(DATA_USER1, DATA_PASS);
			navToolBar.goToSiteExplorer();
		}

		@AfterMethod
		public void afterMethods() {
			info("Logout ECMS");
			driver.manage().deleteAllCookies();
			driver.quit();
		}
		
		/**
		 * == Rename relation document ==
		 * Test case ID: 66299
		 * Step 1: Create node
		 * Step 2: Select relation
		 * Step 3: Rename related document
		 * Step 4: Check relation
		 */
		@Test
		public void test01_RenameRelationDocument(){
			/*Declare variable*/
			String node1 = "test01_RenameRelationDocument_01";
			String node2 = "test01_RenameRelationDocument_02";
			String newNode2="New test01_RenameRelationDocument_02";
			By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			By bNode2 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
			By bNewNode2 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", newNode2));
			
			/*Step 1: Create node*/
			actBar.goToAddNewContent();
			cTemplate.createNewFile(node1,node1,node1);
			click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
			actBar.goToAddNewContent();
			cTemplate.createNewFile(node2,node2,node2);
			cTemplate.goToNode(bNode1);
			
			/*Step 2: Select relation*/
			actBar.addItem2ActionBar("manageRelations", actBar.ELEMENT_ADD_RELATION_LINK);
			actBar.createRelation(node1, "sites/"+node2);
			/*Step 3: Rename related document*/
			cTemplate.goToNode(bNode2);
			cMenu.contextMenuAction(bNode2, cMenu.ELEMENT_MENU_RENAME_NODE, newNode2);
			/*Step 4: Check relation*/
			//verify relation tab
			cTemplate.goToNode(bNode1);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", newNode2)));
			//verify reference tab
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cTemplate.goToNode(bNewNode2);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", node1)));
			
			/*Clear data*/
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cMenu.deleteData(bNode1);
			cMenu.deleteData(bNewNode2);
		}
		
		/**
		 * == Add relation for  document ==
		 * Test case ID: 67198
		 * Step 1: Create node
		 * Step 2: Show form to select relation
		 * Step 3: Select Relation
		 * Step 4: Check relation
		 */
		@Test
		public void test02_AddRelationForDocument(){
			/*Declare variable*/
			String node1 = "test02_AddFelationForDocument_01";
			String node2 = "test02_AddFelationForDocument_02";
			By bNode2 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
			By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create a node
			actBar.goToAddNewContent();
			cTemplate.createNewFile(node1,node1,node1);
			click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
			//Create a document
			actBar.goToAddNewContent();
			cTemplate.createNewFile(node2, node2, node2);
			//go to document
			cTemplate.goToNode(bNode2);
			
			/*Step 2: Show form to select relation*/
			/*Step 3: Select relation*/
			actBar.addItem2ActionBar("manageRelations", actBar.ELEMENT_ADD_RELATION_LINK);
			actBar.createRelation(node2, "sites/"+node1);
			
			/*Step 4: Check relation*/
			//verify relation tab
			cTemplate.goToNode(bNode2);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", node1)));
			//verify reference tab
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cTemplate.goToNode(bNode1);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", node2)));
			
			/*Clear data*/
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cMenu.deleteData(bNode2);
			cMenu.deleteData(bNode1);
		}
		
		/**
		 * == Add relation for  node to uploaded file ==
		 * Test case ID: 67200
		 * Step 1: Create node
		 * Step 2: Open 'Add Relation' pop-up
		 * Step 3: Open 'Select relation' form
		 * Step 4: Check relation
		 */
		@Test
		public void test03_AddRelationRorNodeToUploadedFile(){
			/*Declare variable*/
			String node1 = "test03_AddRelationRorNodeToUploadedFile";
			String file = "test03_AddRelationRorNodeToUploadedFile_file.txt";
			By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			By bFile1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", file));
			/*Step 1: Create node*/
			//Create a node
			actBar.goToAddNewContent();
			cTemplate.createNewFile(node1,node1,node1);
			
			//upload new file
			click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
			ecms.uploadFile("TestData/"+file);
			
			/*Step 2: Open 'Add Relation' pop-up*/
			cTemplate.goToNode(bNode1);
			actBar.addItem2ActionBar("manageRelations", actBar.ELEMENT_ADD_RELATION_LINK);
			
			/*Step 3: Open 'Select relation' form*/
			actBar.createRelation(node1, "sites/"+file);
			
			/*Step 4: Check relation*/
			//verify relation tab
			cTemplate.goToNode(bNode1);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", file)));
			//verify reference tab
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cTemplate.goToNode(bFile1);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", node1)));
			
			/*Clear data*/
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cMenu.deleteData(bFile1);
			cMenu.deleteData(bNode1);
		}
		
		/**
		 * == Add relation for folder ==
		 * Test case ID: 67202
		 * Step 1: Create node
		 * Step 2: Show form to select relation
		 * Step 3: Select relation
		 * Step 4: Check relation
		 */
		@Test
		public void test04_AddRelationForFolder(){
			/*Declare variable*/
			String folder = "test04_AddRelationForFolder_01";
			String node1 = "test04_AddRelationForFolder_node1";
	//		By elementFolder = By.linkText(folder);
			By bFolder = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", folder));
			By bnode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create a new folder
			navToolBar.goToSiteExplorer();
			cTemplate.createNewFolder(folder, folderType.Content);
			//Create a document
			actBar.goToAddNewContent();
			cTemplate.createNewFile(node1, node1, node1);
			//go to folder
			cTemplate.goToNode(bFolder);
			
			/*Step 2: Show form to select relation*/
			/*Step 3: Select relation*/
			actBar.addItem2ActionBar("manageRelations", actBar.ELEMENT_ADD_RELATION_LINK);
			actBar.createRelation(folder, "sites/"+node1);
			
			/*Step 4: Check relation*/
			//verify relation tab
			cTemplate.goToNode(bFolder);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", node1)));
			//verify reference tab
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cTemplate.goToNode(bnode1);
			click(siteExp.ELEMENT_SITEBAR_RELATION);
			waitForAndGetElement(By.xpath(ecms.ELEMENT_REF_IN_RELATION_TAB.replace("${fileName}", folder)));
			
			/*Clear data*/
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			cMenu.deleteData(bFolder);
			cMenu.deleteData(bnode1);
		}
}