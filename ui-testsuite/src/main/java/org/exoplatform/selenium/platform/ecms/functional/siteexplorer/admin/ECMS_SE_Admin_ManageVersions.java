package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.admin;

import static org.exoplatform.selenium.TestLogger.info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: PhuongDT
 * @date: 27/08/2013
 */

public class ECMS_SE_Admin_ManageVersions  extends PlatformBase {
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

		@BeforeMethod
		public void beforeMethods() {
			initSeleniumTest();
			driver.get(baseUrl);
			info("Login ECMS with " + DATA_USER1);
			magAcc = new ManageAccount(driver);
			actBar = new ActionBar(driver);
			cTemplate = new ContentTemplate(driver,this.plfVersion);
			siteExp = new SitesExplorer(driver);
			navToolBar = new NavigationToolbar(driver);
			ecms = new EcmsBase(driver);
			cMenu = new ContextMenu(driver);
			btn = new Button(driver);
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
		 * == Activate version for File document ==
		 * Test case ID: 67473
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test01_ActivateVersionForFileDocument(){
			/*Declare variable*/
			String node1 = "test01_ActivateVersionForFileDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			info("Create a node");
			actBar.goToAddNewContent();
			cTemplate.createNewFile(node1,node1,node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for JS File document ==
		 * Test case ID: 79693
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test02_ActivateVersionForJSFileDocument(){
			/*Declare variable*/
			String node1 = "test02_ActivateVersionForJSFileDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			info("Create Create  JS File document");
			actBar.goToAddNewContent();
			click(cTemplate.ELEMENT_NEW_JS_FILE_LINK);
			cTemplate.createNewJsFile(node1,"1",node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for HTML File document ==
		 * Test case ID: 79694
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test03_ActivateVersionForHTMLFileDocument(){
			/*Declare variable*/
			String node1 = "test03_ActivateVersionForHTMLFileDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			info("Create Create  HTML File document");
			actBar.goToAddNewContent();
			cTemplate.createNewHtmlFile(node1,"en",node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Webcontent document ==
		 * Test case ID: 79695
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test04_ActivateVersionForWebcontentDocument(){
			/*Declare variable*/
			String node1 = "test04_ActivateVersionForWebcontentDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			info("Create Create  Web content document");
			actBar.goToAddNewContent();
			cTemplate.createNewWebContent(node1,node1,"", "", "", "");
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for WebLink document ==
		 * Test case ID: 79696
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test05_ActivateVersionForWebLinkDocument(){
			/*Declare variable*/
			String node1 = "test05_ActivateVersionForWebLinkDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			info("Create WebLink document");
			actBar.goToAddNewContent();
			cTemplate.createNewWebLink(node1,node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Product document ==
		 * Test case ID: 79697
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test06_ActivateVersionForProductDocument(){
			/*Declare variable*/
			String node1 = "test06_ActivateVersionForProductDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create Product document
			info("Create Product document");
			actBar.goToAddNewContent();
			cTemplate.createFullNewProduct(node1, "", node1, node1, node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Illustrated Web Content document ==
		 * Test case ID: 79698
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test07_ActivateVersionForIllustratedWebContentDocument(){
			/*Declare variable*/
			String node1 = "test07_ActivateVersionForIllustratedWebContentDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create Illustrated Web Content document
			info("Create Illustrated Web Content document");
			actBar.goToAddNewContent();
			cTemplate.createNewIllustratedWebContent(node1, "", "", "", "", "", "");
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Contact Us document ==
		 * Test case ID: 79699
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test08_ActivateVersionForContactUsDocument(){
			/*Declare variable*/
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			String folder = "Folder79699";
		 
			/*Step 1: Create node*/
			//Create Contact Us document
			info("Create Contact Us document");
			cTemplate.createNewFolder(folder, folderType.Content);
			cTemplate.goToNode(folder);
			Utils.pause(1000);
			actBar.goToAddNewContent();
			cTemplate.createNewContactUs();
		    //get current date time with Date()
			Date date = new Date();

			String node1 = dateFormat.format(date);
			By bNode = By.xpath("//*[contains(text(), '"+node1+"')]");
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteDocument(ecms.ELEMENT_NODE_LINK.replace("${nodeLabel}", folder));
		}
		
		/**
		 * == Activate version for CSS File document ==
		 * Test case ID: 79701
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test09_ActivateVersionForCSSFileDocument(){
			/*Declare variable*/
			String node1 = "test09_ActivateVersionForCSSFileDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create CSS File document
			info("Create CSS File document");
			actBar.goToAddNewContent();
			click (cTemplate.ELEMENT_CSS_FILE_LINK);
			cTemplate.createNewCssFile(node1, "1", node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Accessible Breadcrumb document ==
		 * Test case ID: 79702
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3: Active version
		 * Note: To create this template, before starting application, it need to run all extenstion
		 */
		@Test
		public void test10_ActivateVersionForAccessibleBreadcrumbDocument(){
			/*Declare variable*/
			String node1 = "test10_ActivateVersionForAccessibleBreadcrumbDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create Accessible Breadcrumb document
			info("Create Accessible Breadcrumb document");
			actBar.goToAddNewContent();
			click(cTemplate.ELEMENT_ACCESSIBLE_BREADCRUMB_LINK);
			cTemplate.createNewAccessibleDocument(node1, node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			/*Step 3: Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Accessible Navigation document ==
		 * Test case ID: 79704
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3: Active version
		 */
		@Test
		public void test11_ActivateVersionForAccessibleNavigationDocument(){
			/*Declare variable*/
			String node1 = "test11_ActivateVersionForAccessibleNavigationDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create Accessible Navigation document
			info("Create Accessible Navigation document");
			actBar.goToAddNewContent();
			click(cTemplate.ELEMENT_ACCESSIBLE_NAVIGATION_LINK);
			cTemplate.createNewAccessibleDocument(node1, node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Accessible Site Search Box document ==
		 * Test case ID: 79705
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3: Active version
		 */
		@Test
		public void test12_ActivateVersionForAccessibleSiteSearchBoxDocument(){
			/*Declare variable*/
			String node1 = "test12_ActivateVersionForAccessibleSiteSearchBoxDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create Accessible Site Search Box document
			info("Create Accessible Site Search Box document");
			actBar.goToAddNewContent();
			click(cTemplate.ELEMENT_ACCESSIBLE_SEARCH_BOX_LINK);
			cTemplate.createNewAccessibleDocument(node1, node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
		
		/**
		 * == Activate version for Announcement document ==
		 * Test case ID: 79706
		 * Step 1: Create node
		 * Step 2: Check node before active
		 * Step 3:Active version
		 */
		@Test
		public void test13_ActivateVersionForAnnouncementDocument(){
			/*Declare variable*/
			String node1 = "test13_ActivateVersionForAnnouncementDocument";
			By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
			
			/*Step 1: Create node*/
			//Create Announcement document
			info("Create Announcement document");
			actBar.goToAddNewContent();
			cTemplate.createNewAnnouncement(node1, node1);
			cTemplate.goToNode(bNode);
			
			/*Step 2: Check node before active*/
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
			info("Click [Version] on action bar");
			actBar.openVersionInfoForm(bNode);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			assert isTextNotPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Step 3:Active version*/
			//Click [Check In] from the Right-click menu for a node 
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);
			
			//Verify: Version info form appears and node isn't activated version
			info("Version info form appears and node isn't activated version");
			actBar.openVersionInfoForm(bNode);
			assert isTextPresent("(Base Version)");
			
			//Close VersionInfoForm()
			btn.close();
			
			/*Clear data*/
			info("clear data");
			cTemplate.goToNode(bNode);
			cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
			cMenu.deleteData(bNode);
		}
}
