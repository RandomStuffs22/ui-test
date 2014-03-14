package org.exoplatform.selenium.platform.ecms.functional.admin.advanced;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageCategory;
import org.exoplatform.selenium.platform.ecms.admin.Permission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.AdvancedSearch;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ECMS_Admin_ManageCategories_Display extends PlatformBase {

	//Platform
	NavigationToolbar nav;
	ManageAccount magAcc;
	UserGroupManagement userGrp;
	Button button;
	ManageAlert alt;

	//Ecms
	EcmsBase ecms; 
	ActionBar actBar;
	ContextMenu cMenu;
	ManageCategory magCa;
	ContentTemplate cTemplate;
	SitesExplorer sitesExp;
	EcmsPermission ecmsPer;
	Permission adminPer;
	ECMainFunction ecMain;
	AdvancedSearch advSrc;

	//public final String DATA_USER = DATA_USER1;
	//public final String DATA_PASS = DATA_PASS;
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		nav = new NavigationToolbar(driver); magAcc = new ManageAccount(driver);
		userGrp = new UserGroupManagement(driver); button = new Button(driver);
		alt = new ManageAlert(driver);
		ecms = new EcmsBase(driver); actBar = new ActionBar(driver);
		magCa = new ManageCategory(driver); cMenu = new ContextMenu(driver);
		ecmsPer = new EcmsPermission(driver); adminPer = new Permission(driver);
		ecMain = new ECMainFunction(driver); sitesExp = new SitesExplorer(driver);
		cTemplate = new ContentTemplate(driver); advSrc = new AdvancedSearch(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
		//actions = null;
	}
	
	/*case01: Check the displaying of Category in Sites Explorer/ System drive
	 * add new category tree with workspace = dms system
	 * go to DMS Administration Drive
	 * check can see category tree just added in /exo:ecm/exo:taxonomyTrees/definition
	 */
	@Test
	public void test01_CheckDisplayOfCategoryInSystemDrive(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_01";
		By ELEMENT_CATEGORY_TREE = By.xpath("//*[@data-original-title = '" + DATA_CATEGORY_TREE_NAME + "']");
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_01";

		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2, DATA_USER2, setPermission, DATA3);

		//go to DMS Administration Drive
		nav.goToSiteExplorer();
		actBar.showDrives();
		
		sitesExp.createDriverInSitesExplorer("DMS Administration", "dms-system", "/", "Platform/Administration", 
				"*", "Non-document Nodes/Sidebar", "Admin/Icons");
		
		click(ecms.ELEMENT_DMS_ADMIN_DRIVE);
		//userGrp.selectGroup("exo:ecm/exo:taxonomyTrees/definition");
		ecms.goToNode("exo:ecm/exo:taxonomyTrees/definition", true);
		
		waitForAndGetElement(ELEMENT_CATEGORY_TREE);
		assert isElementPresent(ELEMENT_CATEGORY_TREE):"display category tree is false";
		info("Category tree is displayed...");

		//delete data
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);		  
	}

	/*case02: Check the displaying of Category when add category for node (document/uploaded file)
	 * add new category tree
	 * create new document: article document
	 * go to add category/action bar
	 * check category tree, user can see just added category 
	 */
	@Test
	public void test02_CheckDisplayOfCategoryWhenAddCategoryForNode(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_02";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_02";
		String DATA_ARTICLE = "ECMS_Admin_ManageCategories_Display_article_02";
		By ELEMENT_ARTICLE = By.linkText(DATA_ARTICLE);
		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2,DATA_USER2, setPermission, DATA3);

		info("--complete--");
		//create new document: article document
		nav.goToSiteExplorer();	
		ecms.goToNode("intranet/documents");
		actBar.goToAddNewContent();
		info("Create new article title: " + DATA_ARTICLE);
		cTemplate.createNewFile(DATA_ARTICLE, DATA_ARTICLE, DATA_ARTICLE);
		waitForAndGetElement(ELEMENT_ARTICLE);
		info("Creating new article document is successful");

		//go to add category/action bar
		ecms.goToNode(ELEMENT_ARTICLE);
		actBar.goToManageCategories();
		click(actBar.ELEMENT_SELECT_CATEGORY_TAB);
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForAndGetElement(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", DATA_CATEGORY_TREE_NAME));
		info("Category tree is diplaying");
		button.close();

		//delete data
		cMenu.deleteData(ELEMENT_ARTICLE);
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);	
	}

	/*case03: Check the displaying of Category tree when user does not have permission to view when add category for node (document/uploaded file) 
	 * create new category tree
	 * set permission for user does not have read node permission
	 * create node by user does not have read node permission
	 * check can not read node by user does not have read node permission
	 */
	@Test
	public void test03_CheckDisplayOfCategoryWhenUserNotHaveReadPermission(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_03";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_03";
		String DATA_ARTICLE = "ECMS_Admin_ManageCategories_Display_article_03";
		By ELEMENT_ARTICLE = By.linkText(DATA_ARTICLE);
		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2,DATA_USER2, setPermission, DATA3);

		//delete permission default
		info("Edit category tree");
		click(magCa.ELEMENT_EDIT_CATEGORY_TREE.replace("${categoryTreeName}", DATA_CATEGORY_TREE_NAME));
		click(button.ELEMENT_PREVIOUS_BUTTON_ADMIN_4);
		click(button.ELEMENT_PREVIOUS_BUTTON_ADMIN_3);
		ecmsPer.deletePermission("*:/platform/administrators",true);
		ecmsPer.deletePermission("*:/platform/users",true);
		ecmsPer.deletePermission(DATA_USER2,true);
		ecmsPer.deletePermission("any",true);
		ecmsPer.deletePermission("*:/platform/web-contributors",true);
		button.close();
		magAcc.signOut();

		//create node by user does not have read node permission
		magAcc.signIn(DATA_USER2, DATA_PASS);
		nav.goToSiteExplorer();
		ecms.goToNode("intranet/documents");
		actBar.goToAddNewContent();
		info("Create new article title: " + DATA_ARTICLE);
		cTemplate.createNewFile(DATA_ARTICLE, DATA_ARTICLE, DATA_ARTICLE);
		waitForAndGetElement(ELEMENT_ARTICLE);
		info("Creating new article document is successfully");

		//check can not read node by user not having read permission
		ecms.goToNode(ELEMENT_ARTICLE);
		actBar.goToManageCategories();
		click(actBar.ELEMENT_SELECT_CATEGORY_TAB);
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForElementNotPresent(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", DATA_CATEGORY_TREE_NAME));
		info("category tree is not diplaying");
		button.close();

		//delete data
		cMenu.deleteData(ELEMENT_ARTICLE);
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);
	}

	/*case04: Check the displaying of Category in Sites Explorer while creating document or uploading file
	 * create new category tree
	 * upload file, check can see new category when upload file
	 */
	@Test
	public void test04_CheckDisplayOfCategoryWhileUploadFile(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_04";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_04";
		//String DATA_UPLOAD_FILE_NAME = "ECMS_Admin_ManageCategories_Display_UploadFile_04";
		String DATA_UPLOAD_FILE_LINK = "TestData/ECMS_Admin_ManageCategories_Display.jpg";
		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2,DATA_USER2, setPermission, DATA3);

		//upload new file
		nav.goToSiteExplorer();
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", waitForAndGetElement(ecms.ELEMENT_UPLOAD_LINK, DEFAULT_TIMEOUT, 0, 2));
		type(ecms.ELEMENT_UPLOAD_LINK, Utils.getAbsoluteFilePath(DATA_UPLOAD_FILE_LINK), false);
		
		info("Upload file "+ Utils.getAbsoluteFilePath(DATA_UPLOAD_FILE_LINK));
		//waitForTextPresent("ECMS_Admin_ManageCategories_Display.jpg");
		waitForAndGetElement(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "ECMS_Admin_ManageCategories_Display.jpg"));
		ecms.goToNode("ECMS_Admin_ManageCategories_Display.jpg");
		//click(actBar.ELEMENT_CATEGORIES_LINK);
		actBar.goToManageCategories();
		click(actBar.ELEMENT_SELECT_CATEGORY_TAB);
		
		//check can see new category in add category
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForAndGetElement(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", DATA_CATEGORY_TREE_NAME));
		info("category tree is diplaying");
		click(button.ELEMENT_CLOSE_WINDOW);

		//delete data
		cMenu.deleteData(By.linkText("ECMS_Admin_ManageCategories_Display.jpg"));
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);
	}

	/*case04: Check the displaying of Category in Sites Explorer while creating document or uploading file
	 * create new category tree
	 * create document: kofax document, check that user can see new category when creating document
	 */
	@Test
	public void test04_CheckDisplayOfCategoryWhileCreatingDocument(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_04_2";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_04_2";
		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2, DATA_USER2, setPermission, DATA3);

		//create a new document
		nav.goToSiteExplorer();
		actBar.goToAddNewContent();
		click(cTemplate.ELEMENT_NEWFILE_LINK);
		cTemplate.openAddCategoryInFileTemplate();

		//check user can see new category while adding category
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForAndGetElement(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", DATA_CATEGORY_TREE_NAME));
		info("Category tree is diplaying");
		click(button.ELEMENT_CLOSE_WINDOW);

		//delete data
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);
	}

	/*case05: Check the displaying of Category when do advanced search using category
	 * create new category tree
	 * check can see category when do advanced search using category
	 */
	@Test
	public void test05_CheckDisplayOfCategoryWhenDoAdvancedSearch(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_05";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_05";
		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2, DATA_USER2, setPermission, DATA3);

		//check user can see category when do advanced search using category
		info("Go to Saved Search/Advanced Search/Constraint Form");
		nav.goToSiteExplorer();
		advSrc.goToAdvancedSearch();
		advSrc.openAddCategoryInAdvancedSearch();
		
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForAndGetElement(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", DATA_CATEGORY_TREE_NAME));
		info("Category tree is diplaying");

		//delete data
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);
	}

	/*case06: Check the displaying of Category when do advanced search using category in case user does not have permission to view Category tree
	 * create new category tree
	 * set permission for user does not have read child category permission
	 * check can not see category when do advanced search using category
	 */
	@Test
	public void test06_CheckDisplayOfCategoryWhenAdvancedSearchInCaseUserNotHavePermission(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_06";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_06";
		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2,DATA_USER2, setPermission, DATA3);

		//delete permission default
		info("Edit category tree");
		click(magCa.ELEMENT_EDIT_CATEGORY_TREE.replace("${categoryTreeName}", DATA_CATEGORY_TREE_NAME));
		click(button.ELEMENT_PREVIOUS_BUTTON_ADMIN_4);
		click(button.ELEMENT_PREVIOUS_BUTTON_ADMIN_3);
		ecmsPer.deletePermission("*:/platform/administrators",true);
		ecmsPer.deletePermission("*:/platform/users",true);
		ecmsPer.deletePermission(DATA_USER2,true);
		ecmsPer.deletePermission("any",true);
		ecmsPer.deletePermission("*:/platform/web-contributors",true);
		magAcc.signOut();

		//check can not see category when do advanced search using category
		magAcc.signIn(DATA_USER2, DATA_PASS);
		nav.goToSiteExplorer();
		info("Go to Saved Search/Advanced Search/Constraint Form");
		advSrc.goToAdvancedSearch();
		advSrc.openAddCategoryInAdvancedSearch();
		
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForElementNotPresent(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", DATA_CATEGORY_TREE_NAME));
		info("Category tree is not diplaying");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER1, DATA_PASS);
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);
	}

	/*case07: Check the displaying of some Category when do advanced search using category in case user does not have permission to view Category tree
	 * create new category tree and 2 categories to tree
	 * set permission for user does not have read child category
	 * check that user can not see child category when do advanced search using category
	 */
	@Test
	public void test07_CheckDisplayOfSomeCategoryWhenAdvancedSearchInCaseUserNotHavePermission(){
		String DATA_CATEGORY_TREE_NAME = "ECMS_Admin_ManageCategories_Display_tree_07";
		String DATA_ACTION_NAME = "ECMS_Admin_ManageCategories_Display_action_07";
		String DATA_CATEGORY_TREE_CHILD_01 = "ECMS_Admin_ManageCategories_Create_tree_child_01";
		String[] DATA1 = {DATA_CATEGORY_TREE_NAME, "collaboration","sites/intranet"};
		String[] DATA2 = {"",""};
		String[] DATA3 = {DATA_ACTION_NAME,"Content Addition","jcr:system/exo:namespaces"};
		boolean[] setPermission = {true, false, false};
		
		//add new category tree
		nav.goToContentAdministration();
		magCa.addNewCategoryTree(DATA1, true, false, DATA2,DATA_USER2, setPermission, DATA3);

		//create 2 new categories
		click(magCa.ELEMENT_EDIT_CATEGORY_TREE.replace("${categoryTreeName}", DATA_CATEGORY_TREE_NAME));
		magCa.addChildCategory(DATA_CATEGORY_TREE_NAME, DATA_CATEGORY_TREE_CHILD_01);
		magCa.expandNode(DATA_CATEGORY_TREE_NAME, true);

		//set permission for mary not having read permission in these two categories
		info("Set permission for category");
		click(adminPer.ELEMENT_PERMISSION_MANAGEMENT_ICON.replace("${categoryName}", DATA_CATEGORY_TREE_CHILD_01));
		waitForAndGetElement(ecmsPer.ELEMENT_DELETE_USER_PERMISSION_AUX_1.replace("${userOrGroupName}", "*:/platform/administrators"));
		click(ecmsPer.ELEMENT_DELETE_USER_PERMISSION_AUX_1.replace("${userOrGroupName}", "*:/platform/administrators"));
		alt.acceptAlert();
		driver.navigate().refresh();
		click(ecmsPer.ELEMENT_DELETE_USER_PERMISSION_AUX_1.replace("${userOrGroupName}", "*:/platform/users"));
		alt.acceptAlert();
		driver.navigate().refresh();
		click(ecmsPer.ELEMENT_DELETE_USER_PERMISSION_AUX_1.replace("${userOrGroupName}", DATA_USER2));
		alt.acceptAlert();
		driver.navigate().refresh();
		click(ecmsPer.ELEMENT_DELETE_USER_PERMISSION_AUX_1.replace("${userOrGroupName}", "any"));
		alt.acceptAlert();
		driver.navigate().refresh();
		click(ecmsPer.ELEMENT_DELETE_USER_PERMISSION_AUX_1.replace("${userOrGroupName}", "*:/platform/web-contributors"));
		alt.acceptAlert();

		click(button.ELEMENT_CLOSE_WINDOW);
		magAcc.signOut();

		//check can not see category when do advanced search using category
		magAcc.signIn(DATA_USER2, DATA_PASS);
		nav.goToSiteExplorer();
		info("Go to Saved Search/Advanced Search/Constraint Form");
		advSrc.goToAdvancedSearch();
		advSrc.openAddCategoryInAdvancedSearch();
		
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForAndGetElement(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", DATA_CATEGORY_TREE_NAME));
		info("Category tree is displaying");
		select(actBar.ELEMENT_CATEGORY_TREE_BOX, DATA_CATEGORY_TREE_NAME);
		waitForElementNotPresent(By.linkText(DATA_CATEGORY_TREE_CHILD_01));
		info("Category is not diplaying");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER1, DATA_PASS);
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(DATA_CATEGORY_TREE_NAME);
		//waitForElementNotPresent(ELEMENT_ALERT_VISIBLE);
	}
}