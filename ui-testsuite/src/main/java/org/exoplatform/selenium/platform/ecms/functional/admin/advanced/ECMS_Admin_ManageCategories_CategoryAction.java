package org.exoplatform.selenium.platform.ecms.functional.admin.advanced;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ManageCategory;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *@author VuNA
 *@date: 15/10/2012
 */
public class ECMS_Admin_ManageCategories_CategoryAction extends PlatformBase{

	//Platform
	NavigationToolbar nav;
	ManageAccount magAcc;
	Dialog dialog;
	Button button;
	//Ecms
	EcmsBase ecms; 
	ActionBar actBar;
	ContextMenu cMenu;
	ManageCategory magCa;
	ContentTemplate cTemplate;

	//Data for these test cases
	public String DATA_USER = "john";
	//public String DATA_PASS = DATA_PASS;

	String categoryName = "category1";
	String newCategoryName = "category2";
	String categoryWorkspace = "collaboration";
	String nodeHomePath = "sites/intranet";
	//String username = "john";
	String groupID = "Platform/Administration"; 
	String actionName = "test";
	String optionLifeCycle = "Content Addition"; 
	String nodeTargetPath = "jcr:system/exo:namespaces";	
	//By ELEMENT_SELECTED_CATEGORY_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", categoryName));
	//By ELEMENT_SELECTED_CATEGORY_CHILD_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", newCategoryName));

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER);
		nav = new NavigationToolbar(driver, this.plfVersion);
		magAcc = new ManageAccount(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		dialog = new Dialog(driver);
		ecms = new EcmsBase(driver, this.plfVersion);
		magCa = new ManageCategory(driver);
		cMenu = new ContextMenu(driver, this.plfVersion);
		alert = new ManageAlert(driver, this.plfVersion);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.quit();
		//actions = null;
	}

	/** Qmetry: ID 67006
	 *-- Case No 034 / ID 001
	 *-- Copy/Paste a Category to a Category
	 *--*/
	@Test
	public void test01_CopyPasteACategoryToACategory(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "CategoryAction01";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Copy a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		ecms.clickUpLevel();
		magCa.addChildCategory(categoryTreeName, newCategoryName, true);

		info("-- Step 3: Paste a copied Category --");
		magCa.copyAndPasteCategory(categoryName, newCategoryName);
		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//magAcc.signOut();	
	}

	/** Qmetry: ID 66908
	 *-- Case No 035 / ID 002
	 *-- Copy a Category and paste into its child node
	 * --*/
	@Test
	public void test02_CopyACategoryAndPasteIntoItsChildNode(){
		By ELEMENT_SELECTED_CATEGORY_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", categoryName));
		By ELEMENT_SELECTED_CATEGORY_CHILD_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", newCategoryName));

		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction02";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath};
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Copy a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		magCa.addChildCategory(categoryName, newCategoryName, true);
		ecms.clickUpLevel();

		info("-- Step 3: Paste copied Category into its child node --");
		click(magCa.ELEMENT_COPY_CATEGORY_ICON.replace("${categoryName}", categoryName));
		click(ELEMENT_SELECTED_CATEGORY_NAME);
		click(magCa.ELEMENT_PASTE_TO_CATEGORY_ICON.replace("${categoryName}", newCategoryName));
		click(ELEMENT_SELECTED_CATEGORY_CHILD_NAME);
		waitForTextPresent(categoryName);

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);
		info("-- Sign Out --");
		//magAcc.signOut();	
	}

	/** Qmetry: ID 66909
	 *-- Case No 036 / ID 003
	 *-- Copy a Category and paste into itself
	 * --*/
	@Test
	public void test03_CopyACategoryAndPasteIntoItself(){
		By ELEMENT_SELECTED_CATEGORY_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", categoryName));

		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction03";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {"", ""};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, true, false, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Copy a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		click(magCa.ELEMENT_COPY_CATEGORY_ICON.replace("${categoryName}", categoryName));

		info("-- Step 3: Paste copied Category into itself --");
		click(magCa.ELEMENT_PASTE_TO_CATEGORY_ICON.replace("${categoryName}", categoryName));
		click(ELEMENT_SELECTED_CATEGORY_NAME);
		waitForTextPresent(categoryName);

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();	
	}

	/** Qmetry: ID 67007
	 *-- Case No 037 / ID 004
	 *-- Copy/Paste a deleted Category
	 * --*/
	@Test
	public void test04_CopyPasteADeletedCategory(){
		info("-- Step 1: Create a Category --");
		String categoryTreeName = "categoryAction04";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Copy a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		ecms.clickUpLevel();
		magCa.addChildCategory(categoryTreeName, newCategoryName);

		click(magCa.ELEMENT_COPY_CATEGORY_ICON.replace("${categoryName}", newCategoryName));

		info("-- Step 3: Delete Category --");
		magCa.deleteCategory(newCategoryName);

		info("-- Step 4: Paste deleted Category --"); 
		click(magCa.ELEMENT_PASTE_TO_CATEGORY_ICON.replace("${categoryName}", categoryName));
		waitForMessage(magCa.MESSAGE_INFO_PASTE_TO_CATEGORY);
		//dialog.closeMessageDialog();
		click(button.ELEMENT_OK_BUTTON);

		info("-- Restore original data --");
		button.close();

		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();	
	}

	/** Qmetry: ID 66794
	 *-- Case No 038 / ID 005
	 *-- Cut a Category and paste into other Category
	 * --*/
	@Test
	public void test05_CutACategoryAndPasteIntoOtherCategory(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction05";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Copy a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		ecms.clickUpLevel();
		magCa.addChildCategory(categoryTreeName, newCategoryName);

		info("-- Step 3: Paste a cut Category --");
		magCa.cutAndPasteCategory(categoryName, newCategoryName);

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();
	}

	/** Qmetry: ID 66792
	 *-- Case No 039 / ID 006
	 *-- Cut a Category and paste into its child node
	 * --*/
	@Test
	public void test06_CutACategoryAndPasteIntoItsChildNode(){
		By ELEMENT_SELECTED_CATEGORY_NAME = By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", categoryName));

		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction06";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Cut a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		magCa.addChildCategory(categoryName, newCategoryName, true);
		ecms.clickUpLevel();
		click(magCa.ELEMENT_CUT_CATEGORY_ICON.replace("${categoryName}", categoryName));

		info("-- Step 3: Paste a cut Category into its child node --");
		click(ELEMENT_SELECTED_CATEGORY_NAME);
		click(magCa.ELEMENT_PASTE_TO_CATEGORY_ICON.replace("${categoryName}", newCategoryName));
		waitForMessage(magCa.MESSAGE_INFO_PASTE_TO_CATEGORY);
		//dialog.closeMessageDialog();
		click(button.ELEMENT_OK_BUTTON);

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();	
	}

	/** Qmetry: ID 66793
	 *-- Case No 040 / ID 007
	 *-- Cut a Category and paste into itself
	 * --*/
	@Test
	public void test07_CutACategoryAndPasteIntoItself(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction07";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Cut a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		click(magCa.ELEMENT_CUT_CATEGORY_ICON.replace("${categoryName}", categoryName));

		info("-- Step 3: Paste cut Category into itself --");
		click(magCa.ELEMENT_PASTE_TO_CATEGORY_ICON.replace("${categoryName}", categoryName));
		alert.verifyAlertMessage(magCa.MESSAGE_INFO_CUT_TO_CATEGORY.replace("${pathCategory}", "/sites/intranet/"+ categoryTreeName +"/"+ categoryName +""));

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();	
	}

	/** Qmetry: ID 66827
	 *-- Case No 041 / ID 008
	 *-- Cut/Paste a deleted Category
	 * --*/
	@Test
	public void test08_CutPasteADeletedCategory(){
		info("-- Step 1: Create a Category --");
		String categoryTreeName = "categoryAction08";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);

		info("-- Step 2: Cut a Category --");
		magCa.addChildCategory(categoryTreeName, categoryName);
		ecms.clickUpLevel();
		magCa.addChildCategory(categoryTreeName, newCategoryName);
		click(magCa.ELEMENT_CUT_CATEGORY_ICON.replace("${categoryName}", newCategoryName));

		info("-- Step 3: Delete Category --");
		magCa.deleteCategory(newCategoryName);

		info("-- Step 4: Paste deleted Category --");
		click(magCa.ELEMENT_PASTE_TO_CATEGORY_ICON.replace("${categoryName}", categoryName));
		waitForMessage(magCa.MESSAGE_INFO_PASTE_TO_CATEGORY);
		//dialog.closeMessageDialog();
		click(button.ELEMENT_OK_BUTTON);

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();	
	}

	/** Qmetry: ID 66854
	 *-- Case No 042 / ID 009
	 *-- Delete Category
	 * --*/
	@Test
	public void test09_DeleteCategory(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction09";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);
		waitForTextPresent(categoryTreeName);
		magCa.addChildCategory(categoryTreeName, categoryName);

		info("-- Step 2: Delete a Category --");
		magCa.deleteCategory(categoryName);

		info("-- Restore original data --");
		button.close();
		magCa.deleteCategory(categoryTreeName);

		info("-- Sign Out --");
		//signOut();	
	}

	/** Qmetry: ID 66841
	 *-- Case No 043 / ID 010
	 *-- Delete a Category when it is added for a document
	 *-- Status: pending*/
	/*@Test
	public void test10_DeleteACategoryWhenItIsAddedForADocument(){
		info("-- Step 1: Create a Category --");	
		info("-- Step 2: Add category for document --");
		info("-- Step 3: Delete Category --");
	}*/

	/**
	 * Qmetry: ID 66856
	 * Delete Category Tree
	 */
	@Test
	public void test11_DeleteCategoryTree(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction11";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, false, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);

		info("-- Step 2: Delete a Category --");
		magCa.deleteCategory(categoryTreeName);
	}

	/**
	 * Qmetry: ID 66701
	 * Edit Category Tree
	 */
	@Test
	public void test12_EditCategoryTree(){
		info("-- Step 1: Create a Category --");

		String categoryTreeName = "categoryAction12";

		nav.goToContentAdministration();

		//Add category tree
		String[] form1 = {categoryTreeName, categoryWorkspace, nodeHomePath};
		String[] form2 = {groupID, "*"};
		String[] form3 = {actionName, optionLifeCycle, nodeTargetPath}; 
		boolean[] setPermission = {true, true, false};
		magCa.addNewCategoryTree(form1, false, true, form2, DATA_USER, setPermission, form3);

		info("-- Step 2: Edit a Category --");
		magCa.addNewCategoryTree_Step4(categoryTreeName, categoryName, newCategoryName, "mary", true, true, true);

		info("-- Reset Data --");
		magCa.deleteCategory(categoryTreeName);
	}
}