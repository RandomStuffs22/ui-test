package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;

/**
 * @Date: 2013/05/24
 * @author thuntn
 *
 */
public class ECMS_SE_Admin extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	String DATA_USER = "john";
	//String DATA_PASS = DATA_PASS;
	
	/**CaseID 65844 Export a node
	 * Step 1: Export a node
	 */
	@Test
	public void test01_ExportNode() {
		String node1 = "test01_ExportNode";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));

		info("Export a node");
		//Create node
		cTemplate.createNewFolder(node1, folderType.Content);
		cTemplate.goToNode(bNode);
			
		//Check if Export button is shown on action bar
		actBar.addItem2ActionBar("exportNode", actBar.ELEMENT_EXPORT_LINK);
		
		//Export node in zip file
		actBar.exportNode(true, true, false);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**CaseID 65851 Add a category
	 * Step 1: Add Category
	 */
	@Test
	public void test02_AddCategory() {
		String node1 = "test02_AddCategory";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String categoryPath = "Defense";
		String categoryTree = "powers";
		
		info("Add Category");
		//Create node
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		cTemplate.goToNode(bNode);
			
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		
		//Add category for node
		actBar.addCategoryForNode(categoryTree, false, categoryPath, "Healing");
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**CaseID 65861 Manage Publication
	 * Step 1: Manage Publication
	 */
	@Test
	public void test03_ManagePublication() {
		String node1 = "test03_ManagePublication";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date date = new Date();
		
		info("Manage Publication");
		//Create node
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		cTemplate.goToNode(bNode);
			
		//Select Staged state for this document
		actBar.managePublication("Staged", dateFormat.format(date.getTime()),dateFormat.format(date.getTime()));
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**CaseID 65861 Show/ Hide Relation
	 * Step 1: Show/ Hide Relation
	 */
	@Test
	public void test04_ShowHideRelation() {
		String node1 = "test04_ShowHideRelation1";
		String node2 = "test04_ShowHideRelation2";
		By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		By bNode2 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
		
		info("Show/ Hide Relation");
		//Create node1, node2
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.goToAddNewContent();
		cTemplate.createNewProduct(node2, node2);
		
		//Add Relation button to action bar if it is not shown on action bar yet
		cTemplate.goToNode(bNode1);
		actBar.addItem2ActionBar("manageRelations", actBar.ELEMENT_ADD_RELATION_LINK);
		actBar.createRelation(node1, "sites/"+node2);
		
		//Show relation
		click(actBar.ELEMENT_SHOW_RELATION_ICON);
		waitForAndGetElement(actBar.ELEMENT_RELATION_LINK.replace("{$relation}", node2));
		
		//Hide relation
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
//		waitForElementNotPresent(ecms.ELEMENT_RELATION_LINK.replace("{$relation}", node2));
		
		//Delete data
		cMenu.deleteDocument(bNode1);
		cMenu.deleteDocument(bNode2);
	}
	
	/**CaseID 65881 View Node Properties
	 * Step 1: View Node Properties
	 */
	@Test
	public void test05_ViewNodeProperties() {
		String node1 = "test05_ViewNodeProperties1";
		By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String property = "exo:summary";
		
		info("View Node Properties");
		//Create node1
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		
		//Add View properties button to action bar if it is not shown on action bar yet
		actBar.addItem2ActionBar("viewProperties", actBar.ELEMENT_VIEW_PROPERTIES_ICON);
		
		//Add a property
		ecms.goToNode(bNode1);
		actBar.addProperty(property, property);
		
		//Delete data
		cMenu.deleteDocument(bNode1);
	}
	
	/**CaseID 67870 Delete a category
	 * Step 1: Delete a category
	 */
	@Test
	public void test06_DeleteCategory() {
		String node1 = "test06_DeleteCategory1";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String catePath="powers";

		info("Add Category");
		//Create node
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		cTemplate.goToNode(bNode);
			
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		
		//Add category for node
		actBar.addCategoryForNode(catePath, false, "Defense", "Healing");
		
		//Delete category
		actBar.deleteCategory(catePath + "/Defense/Healing");
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	
	/**CaseID 67871 Import a node
	 * Step 1: Import a node
	 */
	@Test
	public void test07_ImportNode() {
		String node1 = "test07_ImportNode1";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String filePath = "TestData/sysview.xml";
		
		info("Import a node");
		
		//Create node
		cTemplate.createNewFolder(node1, folderType.Content);
		cTemplate.goToNode(bNode);
			
		//Check if Import button is shown on action bar
		actBar.addItem2ActionBar("importNode",actBar.ELEMENT_IMPORT_LINK);
		
		//Import node 
		actBar.importNode(filePath,"","Create New",true);
		
		//Delete data
		cMenu.deleteDocument(bNode);
	}
	/**CaseID 67872 Add Relation
	 * Step 1: Add Relation
	 */
	@Test
	public void test08_AddRelation() {
		String node1 = "test08_AddRelation1";
		String node2 = "test08_AddRelation2";
		By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		By bNode2 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
		
		info("Add Relation");
		//Create node1, node2
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.goToAddNewContent();
		cTemplate.createNewProduct(node2, node2);
		
		//Add Relation button to action bar if it is not shown on action bar yet
		cTemplate.goToNode(bNode1);
		actBar.addItem2ActionBar("manageRelations", actBar.ELEMENT_ADD_RELATION_LINK);
		actBar.createRelation(node1, "sites/"+node2);
		
		//Delete data
		cMenu.deleteDocument(bNode1);
		cMenu.deleteDocument(bNode2);
	}
	
	/**CaseID 67873 Delete Relation
	 * Step 1: Delete Relation
	 */
	@Test
	public void test09_DeleteRelation() {
		String node1 = "test09deleterelation1";
		String node2 = "test09deleterelation2";
		By bNode1 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		By bNode2 = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
		
		info("Add Relation");
		//Create node1, node2
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1,node1,node1);
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		actBar.goToAddNewContent();
		cTemplate.createNewProduct(node2, node2);
		
		//Add Relation button to action bar if it is not shown on action bar yet
		cTemplate.goToNode(bNode1);
		actBar.addItem2ActionBar("manageRelations", actBar.ELEMENT_ADD_RELATION_LINK);
		actBar.createRelation(node1, "sites/"+node2);
		
		//Delete relation
		actBar.deleteRelation("sites/"+node2);
		
		//Delete data
		cMenu.deleteDocument(bNode1);
		cMenu.deleteDocument(bNode2);
	}
	
	@BeforeMethod
	public void beforeMethods() {
		getDriverAutoSave();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
		//actions = null;
	}
}
