package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.admin;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: Thuntn
 * @date: 26/08/2013
 */
public class ECMS_SE_Admin_Export extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	@BeforeMethod
	public void beforeMethods() {
		getDriverAutoSave();
		
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		siteExp = new SitesExplorer(driver);
		cMenu = new ContextMenu(driver);
		
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		//driver.get(baseUrl + "/acme");

		navToolBar.goToSiteExplorer();

		info("Add Export button for action bar if it does not existed");
		actBar.addItem2ActionBar("exportNode", actBar.ELEMENT_EXPORT_LINK);
		siteExp.goToOverviewPage();
		navToolBar.goToSiteExplorer();
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseID: 66470
	 * Export a folder
	 * Step 1: Create a folder
	 * Step 2: Export this folder
	 */
	@Test
	public void test01_ExportFolder(){	
		String title = "test01_ExportFolder";
		By eFolder = By.xpath(ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		info("Export a folder");

		info("Create a folder");
		cTemplate.createNewFolder(title, folderType.Content);
		
		info("Export this folder");
		siteExp.goToNode(eFolder);
		actBar.exportNode(true, true, false);
		
		//Remove data
		cMenu.deleteDocument(eFolder);
	}

	/**CaseID: 66472
	 * Export a node when format is Document View
	 * Step 1: Create a node
	 * Step 2: Export this node
	 */
	@Test
	public void test02_ExportNodeInDocumentView(){	
		String title = "test02_ExportNodeInDocumentView";
		By bNode = By.xpath(ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		info("Export a node when format is Document View");

		info("Create a content");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(title, title,title);
		
		info("Export this node");
		actBar.exportNode(false, true, false);
		
		//Remove data
		cMenu.deleteDocument(bNode);
	}
	
	/**CaseID: 66478
	 * Export a node when node is in 'check in' status
	 * Step 1: Create a node
	 * Step 2: Check in this node
	 * Step 3: Export this node
	 */
	@Test
	public void test03_ExportNodeWithCheckInStatus(){	
		String title = "test03_ExportNodeWithCheckInStatus";
		By bNode = By.xpath(ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		info("Export a node when node is in 'check in' status");

		info("Create a content");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(title, title);
		
		//Check in this node
		cMenu.contextMenuAction(bNode,cMenu.ELEMENT_MENU_CHECKIN);
		
		info("Export this node");
		actBar.exportNode(false, true, false);
		
		//Check out this node
		cMenu.contextMenuAction(bNode,cMenu.ELEMENT_MENU_CHECKOUT);
		
		//Remove data
		cMenu.deleteDocument(bNode);
	}
	
	/**CaseID: 66481
	 * Export a document
	 * Step 1: Create a node
	 * Step 2: Export this node
	 */
	@Test
	public void test04_ExportDocument(){	
		String title = "test04_ExportDocument";
		By bNode = By.xpath(ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		info("Export a document");

		info("Create a content");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(title, title);
		
		info("Export this node");
		actBar.exportNode(true, true, false);
		
		//Remove data
		cMenu.deleteDocument(bNode);
	}

	/**CaseID: 66482
	 * Export an uploaded file
	 * Step 1: Upload a file
	 * Step 2: Export this file
	 */
	@Test
	public void test05_ExportUploadedFile(){	
		String title = "ECMS_se_admin_export.jpg";
		String link = "TestData/ECMS_se_admin_export.jpg";
		By bNode = By.xpath(ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		info("Export an uploaded file");

		info("Upload a file");
		cTemplate.uploadFile(link);
		
		cTemplate.goToNode(bNode);
		
		info("Export this file");
		actBar.exportNode(true, true, false);
		
		//Remove data
		cMenu.deleteDocument(bNode);
	}
	
	/**CaseID: 66485
	 * Export root path
	 */
	@Test
	public void test06_ExportRootPath(){	
		info("Export root path");
		
		actBar.exportNode(true, true, false);

	}
	
	/**CaseID: 66486
	 * Export version history of node when current node have a version
	 * Step 1: Create a node
	 * Step 2: Check in this node
	 * Step 3: Export this node
	 */
	@Test
	public void test07_ExportVersionHistory(){	
		String title = "test07_ExportVersionHistory";
		By bNode = By.xpath(ELEMENT_NODE_LINK.replace("${nodeLabel}", title));
		info("Export version history of node when current node have a version");

		info("Create a content");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(title, title);
		
		//Check in this node
		cMenu.contextMenuAction(bNode,cMenu.ELEMENT_MENU_CHECKIN);
		
		info("Export this node");
		actBar.exportNode(false, true, true);
		
		//Check out this node
		cMenu.contextMenuAction(bNode,cMenu.ELEMENT_MENU_CHECKOUT);
		
		//Remove data
		cMenu.deleteDocument(bNode);
	}
}