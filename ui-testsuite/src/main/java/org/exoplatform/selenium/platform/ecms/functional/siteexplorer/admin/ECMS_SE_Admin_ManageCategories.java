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
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/*
 * @author: PhuongDT
 * @date: 27/08/2013
 */
public class ECMS_SE_Admin_ManageCategories extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	ActionBar actBar;
	NavigationToolbar navToolBar;

	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	SitesExplorer siteExp;
	ContextMenu cMenu;	

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
		cMenu = new ContextMenu(driver);
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
	 * == Add category for document ==
	 * Test case ID: 67536
	 * Step 1: Create node
	 * Step 2: Open form to add category
	 * Step 3: Add category for document
	 */
	@Test
	public void test01_AddCategoryForDocument(){
		/*Declare variable*/
		String node1 = "test01_AddCategoryForDocument";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String categoryPath = "Defense";
		String categoryTree = "powers";
		String categoryNode = "Healing";
		
		/*Step 1: Create node*/
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		cTemplate.goToNode(bNode);
		
		/*Step 2: Open form to add category*/
		/*Step 3: Add category for document*/
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		//Add category for node
		actBar.addCategoryForNode(categoryTree, false, categoryPath, categoryNode);
		actBar.goToManageCategories();
		waitForAndGetElement(By.xpath("//*[text() = '" + categoryTree + "/" + categoryPath + "/" + categoryNode+"']"));
		
		/*Clear data*/
		cMenu.deleteData(bNode);
	}
	
	/**
	 * == Add category for uploaded file ==
	 * Test case ID: 67545
	 * Step 1: Create node
	 * Step 2: Open form to add category
	 * Step 3: Add category for uploaded file
	 */
	@Test
	public void test02_AddCategoryForUploadedFile(){
		/*Declare variable*/
		String categoryPath = "Defense";
		String categoryTree = "powers";
		String categoryNode = "Healing";
		String file = "test03_AddRelationRorNodeToUploadedFile_file.txt";
		
		/*Step 1: Create node*/
		ecms.uploadFile("TestData/"+file);
		
		/*Step 2: Open form to add category*/
		/*Step 3: Add category for document*/
		cTemplate.goToNode(By.linkText(file));
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		//Add category for node
		actBar.addCategoryForNode(categoryTree, false, categoryPath, categoryNode);
		actBar.goToManageCategories();
		waitForAndGetElement(By.xpath("//*[text() = '" + categoryTree + "/" + categoryPath + "/" + categoryNode+"']"));
		
		/*Clear data*/
		cMenu.deleteData(By.linkText(file));
	}
}