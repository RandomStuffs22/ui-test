package org.exoplatform.selenium.platform.gatein.functional.portalnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author havtt
 * @date 24-Dec-2013
 *
 */
public class Gatein_PortalNavigation_Edit_EditNavigation_OtherNodeActions extends PlatformBase{
	
		//General
		Button button;
		Dialog dialog;
		ManageAlert alert;

		//Platform
		ManageAccount magAc;
		NavigationToolbar navToolbar;
		NavigationManagement navMag;
		PageManagement pageMag;
		PageEditor pageEditor;
		UserGroupManagement userGroupMag;
		
		public String username = DATA_USER_JOHN;
		public String password = DATA_PASS;
		public String nodeNamelocator = "//*[@id='UINavigationNodeSelector']//a[@title='${nodeName}']";
		public By WarningMsgFailPasteNode = By.xpath("//span[contains(text(),'This node name already exists')]");
		public By WarningMsgFailPasteNode2 = By.xpath("//span[contains(text(),'The source and destination')]");
		
		@BeforeMethod
		public void setUpBeforeTest(){
			initSeleniumTest();
			driver.get(baseUrl);
			button = new Button(driver);
			dialog = new Dialog(driver);
			alert = new ManageAlert(driver);
			magAc = new ManageAccount(driver);
			navToolbar = new NavigationToolbar(driver);
			userGroupMag = new UserGroupManagement(driver);
			pageMag = new PageManagement(driver);
			navMag = new NavigationManagement(driver);
			pageEditor = new PageEditor(driver);
			magAc.signIn(username, password);
			driver.navigate().refresh();
		}

		@AfterMethod
		public void afterTest(){
			info("Gatein_Navigation_PortalNavigation_EditNavigation: Finish testing");
			driver.manage().deleteAllCookies();
			driver.quit();
		}

		/**
		 * caseID: 73429
		 * Copy/Paste a node into another node in the same navigation
		 * 
		 */
		@Test
		public void test01_CopyPasteNodeToAnotherAtTheSameNavigation(){
			String portalName = "intranet";
			String parentNode = "Home";
			String nodeName1 = "Node0101";
			String nodeName2 = "Node0102";
			String pageSelectorName = "test01pageSelector";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");

			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();
			
			info("Add the first node by right click");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName1, true, languages, nodeName1, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Add another node by click [Add New Button]");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, true, nodeName2, true, languages, nodeName2, pageSelectorName, pageSelectorName, true, false);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Copy and paste node 1 to node 2");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
//			click(nodeNamelocator.replace("${nodeName}", parentNode));
			copyNode(nodeNamelocator.replace("${nodeName}", nodeName1));
			pasteNode(nodeNamelocator.replace("${nodeName}", nodeName2));
			
			info("Check the existence of copied node");
			waitForElementNotPresent(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", nodeName2).replace("${number}", "1").replace("${childNode}", nodeName1));
			
			info("Delete node for Portal");
			navMag.deleteNode(portalName, parentNode, nodeName1, false);
			navMag.deleteNode(portalName, parentNode, nodeName2, false);
			navToolbar.goToManagePages();
			pageMag.deletePage(PageType.PORTAL, pageSelectorName);
		}
		
		/**
		 * caseID: 73430
		 * Cut/Paste node to same place
		 * 
		 */
		@Test
		public void test02_CutPasteNodeAtTheSamePlace(){
			String portalName = "intranet";
			String parentNode = "Home";
			String nodeName = "Node02";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");

			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();

			info("Add the first node by right click");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Cut and paste node to the same place");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			cutNode(nodeNamelocator.replace("${nodeName}", nodeName));
			pasteNode(nodeNamelocator.replace("${nodeName}", parentNode));
			isElementPresent(WarningMsgFailPasteNode);
			button.ok();
			
			info("Delete node for Portal");
			navMag.deleteNode(portalName, parentNode, nodeName, false);
		}
		
		/**
		 * caseID: 73510
		 * Cut/Paste a node to new place in the same navigation
		 * 
		 */
		@Test
		public void test03_CutPasteNodeToAnotherAtTheSameNavigation(){
			String portalName = "intranet";
			String parentNode = "Home";
			String nodeName1 = "Node0301";
			String nodeName2 = "Node0302";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");

			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();

			info("Add the first node by right click");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName1, true, languages, nodeName1, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Add the first node by right click");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName2, true, languages, nodeName2, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Copy and paste node 1 to node 2");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			cutNode(nodeNamelocator.replace("${nodeName}", nodeName1));
			pasteNode(nodeNamelocator.replace("${nodeName}", nodeName2));
			
			info("Check the existence of cut node");
			waitForElementNotPresent(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", nodeName2).replace("${number}", "1").replace("${childNode}", nodeName1));
			
			info("Delete node for Portal");
			navMag.deleteNode(portalName, parentNode, nodeName2, false);
		}
		
		
		/**
		 * caseID: 73576
		 * Copy/Paste node to same place
		 * 
		 */
		@Test
		public void test04_CopyPasteNodeAtTheSamePlace(){
			String portalName = "intranet";
			String parentNode = "Home";
			String nodeName = "Node04";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");

			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();

			info("Add the first node by right click");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Copy and paste node to the same place");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			copyNode(nodeNamelocator.replace("${nodeName}", nodeName));
			pasteNode(nodeNamelocator.replace("${nodeName}", parentNode));
			isElementPresent(WarningMsgFailPasteNode);
			button.ok();
			
			info("Delete node for Portal");
			navMag.deleteNode(portalName, parentNode, nodeName, false);
		}
		
		/**
		 * caseID: 73687
		 * Cut/Paste node to the node itself
		 * 
		 */
		@Test
		public void test05_CutPasteNodeToNodeItself(){
			String portalName = "intranet";
			String parentNode = "Home";
			String nodeName = "Node05";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");

			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();

			info("Add node by right click");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Copy and paste node to the same place");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			cutNode(nodeNamelocator.replace("${nodeName}", nodeName));
			pasteNode(nodeNamelocator.replace("${nodeName}", nodeName));
			isElementPresent(WarningMsgFailPasteNode2);
			button.ok();
			
			info("Delete node for Portal");
			navMag.deleteNode(portalName, parentNode, nodeName, false);
		}
		
		

		/**
		 * caseID: 74282
		 * Cut/Paste a node created automatically by add page wizard
		 * 
		 */
		@Test
		public void test06_CutPasteNodeCreatedByAddPageWizard(){
			String pageName = "Page74282";
			String parentNode = "Home";
			String portalName = "intranet";
			String targetNode = "Documents";
					
			info("Add new page by Wizard");
			navToolbar.goToPageCreationWizard();
			pageEditor.createNewPageEmptyLayout(pageName);
			
			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			
			info("Cut and paste node to some other node ");
			cutNode(nodeNamelocator.replace("${nodeName}", pageName));
			pasteNode(nodeNamelocator.replace("${nodeName}", targetNode));
			button.save();
			
			info("Check the existence of cut node");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			waitForElementNotPresent(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNode).replace("${number}", "1").replace("${childNode}", pageName));
			waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", targetNode).replace("${number}", "1").replace("${childNode}", pageName));
			
			info("Restore data");
			navMag.deleteNode(portalName, targetNode, pageName, false);
			navToolbar.goToManagePages();
			pageMag.deletePage(PageType.PORTAL, pageName);
		}
		
		/**
		 * caseID: 73432
		 * Change order of node
		 * 
		 */
		@Test
		public void test07_ChangeOrderOfNode(){
			String portalName = "intranet";
			String nodeName = "Documents";


			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			
			info("Change order of node: Move up");
			rightClickOnElement(nodeNamelocator.replace("${nodeName}", nodeName));
			click(ELEMENT_NAVIGATION_MOVE_UP_NODE);
			
			info("Change order of node: Move down");
			rightClickOnElement(nodeNamelocator.replace("${nodeName}", nodeName));
			click(ELEMENT_NAVIGATION_MOVE_DOWN_NODE);
		}
		
		/**
		 * caseID: 73433
		 * Check Clone node does not contain sub node
		 * 
		 */
		@Test
		public void test08_CloneNodeNotContainingSubnode(){
			String pageName = "Page73433";
			String portalName = "intranet";
			String parentNode = "Home";
			String targetNode = "Documents";
			
			By PageID1 = By.xpath("//*[@id='UIRepeater']//tr[1]/td[1]/span");
			By PageID2 = By.xpath("//*[@id='UIRepeater']//tr[2]/td[1]/span");
			
			String ELEMENT_PAGE_DELETE_ICON_BY_ID1 = "//span[@title='"+pageName+"']/../..//*[@class='uiIconDelete uiIconLightGray']";
					
			info("Add new page by Wizard");
			navToolbar.goToPageCreationWizard();
			pageEditor.createNewPageEmptyLayout(pageName);
			
			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			
			info("Clone and paste node to some other node ");
			cloneNode(nodeNamelocator.replace("${nodeName}", pageName));
			pasteNode(nodeNamelocator.replace("${nodeName}", targetNode));
			button.save();
			
			info("Check page list");
			navToolbar.goToManagePages();
			pageMag.searchPageInManagementPage(PageType.PORTAL,pageName,true);
			waitForAndGetElement(PageID1);
			waitForAndGetElement(PageID2);
			
			info("Restore data");
			info("--Delete pages--");
			click(ELEMENT_PAGE_DELETE_ICON_BY_ID1);
			alert.waitForConfirmation(pageMag.MESSAGE_DELETE_PAGE);
			click(ELEMENT_PAGE_DELETE_ICON_BY_ID1);
			alert.waitForConfirmation(pageMag.MESSAGE_DELETE_PAGE);
			dialog.closeMessageDialog();
			info("--Delete nodes--");
			navToolbar.goToPortalSites();
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.deleteNode(portalName, parentNode, pageName, false);
			navMag.deleteNode(portalName, targetNode, pageName, false);
		}
		
		/**
		 * caseID: 73512
		 * Check clone node containing sub node
		 * 
		 */
		@Test
		public void test09_CloneNodeContainingSubnode(){
			String pageName = "Page73512";
			String portalName = "intranet";
			String targetNode = "Connections";
			String parentNode = "Home";
			String subnodeName = "Subnode73512";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");
			
			By PageID1 = By.xpath("//*[@id='UIRepeater']//tr[1]/td[1]/span");
			By PageID2 = By.xpath("//*[@id='UIRepeater']//tr[2]/td[1]/span");
			String ELEMENT_PAGE_DELETE_ICON_BY_ID1 = "//span[@title='"+pageName+"']/../..//*[@class='uiIconDelete uiIconLightGray']";
					
			info("Add new page by Wizard");
			navToolbar.goToPageCreationWizard();
			pageEditor.createNewPageEmptyLayout(pageName);
			
			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));

			info("Create a sub-node for the existing node");
			navMag.addNodeForPortal(portalName, pageName, false, subnodeName, true, languages, subnodeName, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
			
			info("Clone and paste node to some other node ");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			cloneNode(nodeNamelocator.replace("${nodeName}", pageName));
			pasteNode(nodeNamelocator.replace("${nodeName}", targetNode));
			button.save();
			
			info("Check page list");
			navToolbar.goToManagePages();
			pageMag.searchPageInManagementPage(PageType.PORTAL,pageName,true);
			waitForAndGetElement(PageID1);
			waitForAndGetElement(PageID2);
			
			info("Restore data");
			info("--Delete pages--");
			click(ELEMENT_PAGE_DELETE_ICON_BY_ID1);
			alert.waitForConfirmation(pageMag.MESSAGE_DELETE_PAGE);
			click(ELEMENT_PAGE_DELETE_ICON_BY_ID1);
			alert.waitForConfirmation(pageMag.MESSAGE_DELETE_PAGE);
			dialog.closeMessageDialog();
			info("--Delete nodes--");
			navToolbar.goToPortalSites();
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.deleteNode(portalName, parentNode, pageName, false);
			navMag.deleteNode(portalName, targetNode, pageName, false);
		}
	
		
		/**
		 * caseID: 73579
		 * Check Clone node does not link to any page
		 * 
		 */
		@Test
		public void test10_CloneNodeNotLinkingToAnyPage(){
			String portalName = "intranet";
			String parentNode = "Home";
			String nodeName = "Node10";
			String targetNode = "Documents";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");


			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			
			info("Add node by right click");
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
			
			info("Clone and paste node to some other node ");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			cloneNode(nodeNamelocator.replace("${nodeName}", nodeName));
			pasteNode(nodeNamelocator.replace("${nodeName}", targetNode));
			button.save();
			
			info("Check the existence of cloned node");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNode).replace("${number}", "1").replace("${childNode}", nodeName));
			
			if(isElementNotPresent(By.xpath("//*[@class='uiIconNode expandIcon nodeSelected' and @title = '"+targetNode+"']")))
				click(ELEMENT_NODE_LINK.replace("${nodeLabel}", targetNode));
			waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", targetNode).replace("${number}", "1").replace("${childNode}", nodeName));
			
			info("Restore data");
			navMag.deleteNode(portalName, targetNode, nodeName, false);			
			navMag.deleteNode(portalName, parentNode, nodeName, false);
		}

		/**
		 * caseID: 73635
		 * Copy/Paste node to the node itself
		 * 
		 */
		@Test
		public void test11_CutPasteNodeToNodeItself(){
			String portalName = "intranet";
			String parentNode = "Home";
			String nodeName = "Node11";
			Map<String, String> languages = new HashMap<String, String>();
			languages.put("English", "");

			info("Go to Administration/Portal Sites");
			navToolbar.goToPortalSites();

			info("Add node by right click");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			navMag.addNodeForPortal(portalName, parentNode, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
			waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

			info("Copy and paste node to the same place");
			click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
			cutNode(nodeNamelocator.replace("${nodeName}", nodeName));
			pasteNode(nodeNamelocator.replace("${nodeName}", nodeName));
			isElementPresent(WarningMsgFailPasteNode);
			button.ok();
			
			info("Delete node for Portal");
			navMag.deleteNode(portalName, parentNode, nodeName, false);
		}
}



