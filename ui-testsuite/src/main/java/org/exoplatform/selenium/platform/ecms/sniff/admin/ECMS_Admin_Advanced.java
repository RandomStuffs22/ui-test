package org.exoplatform.selenium.platform.ecms.sniff.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.EcmsPermission;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageAction;
import org.exoplatform.selenium.platform.ecms.admin.ManageCategory;
import org.exoplatform.selenium.platform.ecms.admin.ManageQuery;
import org.exoplatform.selenium.platform.ecms.admin.ManageScript;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * May, 2013
 *
 */
public class ECMS_Admin_Advanced extends PlatformBase{

	//Platform
	ManageAccount magAcc;
	NavigationToolbar nav;
	Button button;

	//Ecms
	ECMainFunction ecMain;
	ManageAction magAction;
	ManageScript magScript;
	EcmsBase ecms; 
	ActionBar actBar;
	ContextMenu cMenu;
	ManageCategory magCa;
	ContentTemplate cTemplate;
	EcmsPermission ecmsPer;
	ManageQuery magQuery;

	//Categories
	String categoryTreeName = "Sniff_Ecms_Admin_Category_Test";
	String categoryName_0 = "category1";
	String newCategoryName_0 = "category2";
	String categoryName_1 = "category3";
	String newCategoryName_1 = "category4";
	String categoryName_2 = "category5";
	String newCategoryName_2 = "category6";
	String categoryWorkspace = "collaboration";
	String nodeHomePath = "sites/intranet";
	String groupID = "Platform/Administration"; 
	String actionName = "test";
	String optionLifeCycle = "Content Addition"; 
	String nodeTargetPath = "jcr:system/exo:namespaces";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		ecMain = new ECMainFunction(driver); 
		magAction = new ManageAction(driver);
		magScript = new ManageScript(driver);
		nav = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		ecms = new EcmsBase(driver);
		magCa = new ManageCategory(driver);
		cMenu = new ContextMenu(driver);
		ecmsPer = new EcmsPermission(driver);
		ecMain = new ECMainFunction(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		magQuery = new ManageQuery(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 65848
	 * Add action type
	 * ================
	 * Qmetry ID: 71621
	 * Edit action type
	 * ================
	 * Qmetry ID: 71622
	 * Delete action type
	 * 
	 */
	@Test
	public void test01_AddEditDeleteActionType(){
		//Prepare test data
		//GetMailScript
		String scriptFileContent_0 = "TestData/ECMS_Admin_GetMailScript_Template.txt"; 
		String scriptLabel_0 = "GetMailScript"; 
		String scriptName_0 = "GetMailScript";
		//SendMailScript
		String scriptFileContent_1 = "TestData/ECMS_Admin_SendMailScript_Template.txt"; 
		String scriptLabel_1 = "SendMailScript"; 
		String scriptName_1 = "SendMailScript";

		String actionTypeName = "Get Emails";
		String newActionTypeName = "Send Emails";
		String variable = "exo:description";

		ecMain.goToScriptsTabInContentAdmin();

		magScript.addScript(scriptFileContent_0, scriptLabel_0, scriptName_0);
		magScript.addScript(scriptFileContent_1, scriptLabel_1, scriptName_1);

		//ecMain.goToActionsTabInContentAdmin();
		click(ecMain.ELEMENT_MANAGE_ACTIONS_LINK);

		magAction.addActionType(actionTypeName, scriptName_0 + ".groovy");

		magAction.editActionType(actionTypeName, newActionTypeName, scriptName_1 + ".groovy", variable);

		magAction.deleteActionType(newActionTypeName);

		//Reset data
		click(ecMain.ELEMENT_MANAGE_SCRIPTS_LINK);
		magScript.deleteScript(scriptLabel_0);
		magScript.deleteScript(scriptLabel_1);
	}

	/**
	 * Qmetry ID: 67827  
	 * Add a category
	 * ==============
	 * Qmetry ID: 67828
	 * Edit a category
	 * ==============
	 * Qmetry ID: 65850
	 * Delete a category
	 * 
	 */
	@Test
	public void test02_AddEditDeleteCategory(){
		String DATA_FILE = "FileTest";
		By ELEMENT_FILE = By.linkText(DATA_FILE);

		nav.goToContentAdministration();

		//Add a new category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, true, true};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER1, setPermission, form3, true);

		//Add/copy/cut/paste/delete category in category tree
		magCa.addChildCategory(categoryTreeName, categoryName_0, false);
		ecms.clickUpLevel();
		magCa.addChildCategory(categoryTreeName, newCategoryName_0, true);
		magCa.copyAndPasteCategory(categoryName_0, newCategoryName_0);

		ecms.clickUpLevel();
		ecms.clickUpLevel();
		magCa.addChildCategory(categoryTreeName, categoryName_1, true);
		ecms.clickUpLevel();
		magCa.addChildCategory(categoryTreeName, newCategoryName_1, true);
		magCa.cutAndPasteCategory(categoryName_1, newCategoryName_1);	
		magCa.deleteCategory(newCategoryName_1);
		button.close();
		waitForTextPresent(categoryTreeName);

		//Edit a category
		magCa.addNewCategoryTree_Step4(categoryTreeName, categoryName_2, newCategoryName_2, DATA_USER2, true, true, true);
		click(magCa.ELEMENT_EDIT_CATEGORY_TREE.replace("${categoryTreeName}", categoryTreeName));
		click(button.ELEMENT_PREVIOUS_BUTTON_ADMIN_4);
		click(button.ELEMENT_PREVIOUS_BUTTON_ADMIN_3);
		ecmsPer.removeDefaultPermissionOfNode();
		ecmsPer.deletePermission("*:/platform/users",true);
		button.close();
		magAcc.signOut();

		//create a node by user who hasn't [read] permission
		magAcc.signIn(DATA_USER2, DATA_PASS);
		nav.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		magAcc.userSignIn(userType.PUBLISHER);
		nav.goToSiteExplorer();
		 if(waitForAndGetElement(actBar.ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web"),DEFAULT_TIMEOUT,0)!=null){
			 click(actBar.ELEMENT_VIEW_MODE_LINK.replace("${viewName}", "Web"));
		 }
		ecms.goToNode("intranet/documents");
		actBar.goToAddNewContent();
		info("Create new File document: " + DATA_FILE);
		cTemplate.createNewFile(DATA_FILE, DATA_FILE, DATA_FILE);

		ecms.goToNode(ELEMENT_FILE);
		actBar.goToManageCategories();
		click(actBar.ELEMENT_SELECT_CATEGORY_TAB);
		click(actBar.ELEMENT_CATEGORY_TREE_BOX);
		waitForElementNotPresent(actBar.ELEMENT_CATEGORY_OPTION.replace("${CATEGORY_TREE_NAME}", categoryTreeName));	
		button.close();
		//delete data
		cMenu.deleteData(ELEMENT_FILE);

		//Delete a Category
		magAcc.signOut();
		magAcc.signIn(DATA_USER1, DATA_PASS);
		ecMain.goToCategoriesTabInContentAdmin();
		magCa.deleteCategory(categoryTreeName);
	}

	/**
	 * Qmetry ID: 65863
	 * Add a Query
	 * ===========
	 * Qmetry ID: 67829
	 * Edit a Query
	 * ===========
	 * Qmetry ID: 67830
	 * Delete a Query
	 * 
	 */
	@Test
	public void test03_AddEditAndDeleteQuery(){
		String queryName = "Created Documents"; 
		String queryType = "SQL";
		boolean enableCacheResult = false;
		String group = "Platform/Users";
		String membership = "*";
		String statement = "//*[(@jcr:primaryType = 'exo:article' or @jcr:primaryType = 'nt:file') " +
				"and @exo:owner='${UserId}$' and not(@jcr:mixinTypes = 'exo:restoreLocation')] order by @exo:dateCreated descending";

		ecMain.goToQueriesTabInContentAdmin();

		magQuery.addQuery(queryName, queryType, enableCacheResult, group, membership);

		magQuery.editQuery(queryName, "xPath", true, true, statement, true, "Platform/Administration", "*");
		waitForTextPresent(statement);

		magQuery.deleteQuery(queryName);
	}

	/**
	 * Qmetry ID: 65865
	 * Add Script 
	 * ==========
	 * Qmetry ID: 67831
	 * Edit Script
	 * ==========
	 * Qmetry ID: 67832
	 * Delete Script
	 * 
	 */
	@Test
	public void test04_AddEditAndDeleteScript(){
		String scriptFileContent = "TestData/ECMS_Admin_SendMailScript_Template.txt"; 
		String scriptLabel = "SendMailScript"; 
		String scriptName = "SendMailScript";

		String newScriptFileContent = ""; 
		String newScriptName = "Edit SendMailScript";

		ecMain.goToScriptsTabInContentAdmin();

		magScript.addScript(scriptFileContent, scriptLabel, scriptName);

		magScript.editScript(scriptName, newScriptFileContent, newScriptName, true);

		magScript.deleteScript(newScriptName);
	}
}