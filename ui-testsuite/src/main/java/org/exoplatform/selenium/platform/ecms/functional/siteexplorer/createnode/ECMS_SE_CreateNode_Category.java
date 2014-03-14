package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.createnode;

import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.TestLogger.warn;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageCategory;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * July, 25th, 2013
 *
 */
public class ECMS_SE_CreateNode_Category extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ECMainFunction ecMain;
	Dialog dialog;

	//Ecms
	EcmsBase ecms;
	ContextMenu cMenu;
	ContentTemplate cTemplate;
	SitesExplorer sitesExp;
	ManageCategory magCa;
	ManageDrive magDrv;

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
		ecMain = new ECMainFunction(driver);
		sitesExp = new SitesExplorer(driver);
		magCa = new ManageCategory(driver);
		dialog = new Dialog(driver);
		magDrv = new ManageDrive(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 67116
	 * Check the displaying of category in ECM Admin
	 * 
	 */
	@Test
	public void test01_CheckTheDisplayingOfCategoryInECMAdmin(){
		String categoryName = "checkdisplaycategory01";
		By bNode1= By.xpath(sitesExp.ELEMENT_SE_NODE.replace("{$node}","definition"));
		By bNode2= By.xpath(sitesExp.ELEMENT_SE_NODE.replace("{$node}","intranet"));
		By bNode3= By.xpath(sitesExp.ELEMENT_SE_NODE.replace("{$node}",categoryName));

		info("Add a new drive [DMS Administration]");
		navToolBar.goToSiteExplorer();
		actBar.showDrives();	
		sitesExp.createDriverInSitesExplorer("DMS Administration", "dms-system", "exo:ecm/exo:taxonomyTrees", "Platform/Administration", 
				"*", "Non-document Nodes/Sidebar", "Web");

		info("Go to [DMS Administration] drive");
		click(ecms.ELEMENT_DMS_ADMIN_DRIVE);
		ecms.goToNode(bNode1);
		ecms.goToNode(bNode2);

		info("Add a new category");
		actBar.addItem2ActionBar("addCategory", ecms.ELEMENT_BUTTON_ADD_CATEGORY, "Web", "Web");
		actBar.showDrives();
		click(ecms.ELEMENT_DMS_ADMIN_DRIVE);
		ecms.goToNode(bNode1);
		ecms.goToNode(bNode2);
		actBar.addCategoryInSimpleView(categoryName,false);
		waitForAndGetElement(bNode3);

		info("Go to Manage Category in Content Administration");
		ecMain.goToCategoriesTabInContentAdmin();
		click(magCa.ELEMENT_EDIT_CATEGORY_TREE.replace("${categoryTreeName}", "intranet"));
		magCa.expandNode("intranet", true);
		waitForAndGetElement(bNode3);
		info("Check the category " + categoryName + " in ECM Admin..." + "succesful!");

		info("Restore data");
		magCa.deleteCategory(categoryName);
		info("Delete drive");
		ecMain.goToManageDrive();
		magDrv.deleteDrive("DMS Administration");
	}
	
	/**
	 * Qmetry ID: 67380
	 * Add new category when put some special characters
	 * 
	 */
	@Test
	public void test02_AddNewCategoryWhenPutSomeSpecialCharacters(){
		String categoryName = "checkcategorywithcharacters02";
		String categorySpecialName = cTemplate.DATA_SPECIAL_CHARACTER_STRING + categoryName;
		By bNode1= By.xpath(sitesExp.ELEMENT_SE_NODE.replace("{$node}","definition"));
		By bNode2= By.xpath(sitesExp.ELEMENT_SE_NODE.replace("{$node}","intranet"));
		By bNode3= By.xpath(sitesExp.ELEMENT_SE_NODE.replace("{$node}",categorySpecialName));

		info("Add a new drive [DMS Administration]");
		navToolBar.goToSiteExplorer();
		actBar.showDrives();	
		sitesExp.createDriverInSitesExplorer("DMS Administration", "dms-system", "exo:ecm/exo:taxonomyTrees", "Platform/Administration", 
				"*", "Non-document Nodes/Sidebar", "Web");

		info("Go to [DMS Administration] drive");
		click(ecms.ELEMENT_DMS_ADMIN_DRIVE);
		ecms.goToNode(bNode1);
		ecms.goToNode(bNode2);

		info("Add a new category with special characters");
		actBar.addItem2ActionBar("addCategory", ecms.ELEMENT_BUTTON_ADD_CATEGORY, "Web", "Web");
		actBar.showDrives();
		click(ecms.ELEMENT_DMS_ADMIN_DRIVE);
		ecms.goToNode(bNode1);
		ecms.goToNode(bNode2);
		actBar.addCategoryInSimpleView(categorySpecialName, false);
		waitForAndGetElement(bNode3);
		
		info("Restore data");
		magCa.deleteCategory(categoryName);
		navToolBar.goToSiteExplorer();
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", categoryName));
		warn("Exception: delete Category with special characters");	
		info("Delete drive");
		ecMain.goToManageDrive();
		magDrv.deleteDrive("DMS Administration");
		info("Delete driver successfully");
	}
}