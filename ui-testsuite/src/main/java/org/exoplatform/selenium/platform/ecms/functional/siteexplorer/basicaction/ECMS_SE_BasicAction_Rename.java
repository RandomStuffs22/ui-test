package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * May, 2013
 *
 */
public class ECMS_SE_BasicAction_Rename extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	ContextMenu cMenu;
	SitesExplorer siteExp;
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
		siteExp = new SitesExplorer(driver);
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
	 * Qmetry ID: 74551
	 * Cancel button should dismiss the form without renaming
	 * 
	 */
	@Test
	public void test01_CancelButtonShouldDismissTheFormWithoutRenaming(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		String newFileName = "rename01";

		info("-- Test case: Cancel button should dismiss the form without renaming --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//right click on eFile
		rightClickOnElement(eFile);

		//Rename a file and click [Cancel Button]
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		type(cMenu.ELEMENT_INPUT_RENAME_NODE, newFileName, true);
		button.cancel();
		waitForAndGetElement(eFile);

		//Restore data
		cMenu.deleteDocument(eFile);
	}

	/**
	 * Qmetry ID: 74577
	 * Check the same name when accessed via WEBDAV
	 * 
	 */
	@Test
	public void test02_CheckTheSameNameWhenAccessedViaWEBDAV(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		String newFileName = "rename02";
		By editFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", newFileName + ".txt"));

		info("-- Test case: Check the same name when accessed via WEBDAV --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//Rename a file
		cMenu.contextMenuAction(eFile, cMenu.ELEMENT_MENU_RENAME_NODE, newFileName);

		//View information
		cMenu.contextMenuAction(editFile, cMenu.ELEMENT_VIEW_INFORMATION);
		waitForAndGetElement(cMenu.ELEMENT_POPUP_VIEW_INFORMATION_NAME.replace("${fileName}", newFileName + ".txt"));
		button.close();

		//Download and Allow Edition
		cMenu.contextMenuAction(editFile, cMenu.ELEMENT_MENU_DOWNLOAD);
		switchToNewWindow();

		//The url of the browser should end by rename02.txt
		String fileName = Utils.getFileNameFromCurrentUrl(driver);
		assert fileName.equals(newFileName + ".txt"): "Failed: check the same name when accessed via WEBDAV";

		//Restore data
		switchToParentWindow();
		cMenu.deleteDocument(editFile);
	}

	/**
	 * Qmetry ID: 74553
	 * File extension not shown in rename form
	 * 
	 */
	@Test
	public void test03_FileExtensionNotShownInRenameForm(){
		String filePath = "TestData/ECMS_Undo_Delete_1.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "ECMS_Undo_Delete_1.txt"));

		info("-- Test case: File extension not shown in rename form --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//right click on eFile
		rightClickOnElement(eFile);

		//Verify that File extension is not shown
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		WebElement element = waitForAndGetElement(cMenu.ELEMENT_INPUT_RENAME_NODE);
		String fileName = element.getAttribute("value");
		assert fileName.equals("ECMS_Undo_Delete_1"): "Failed: check File extension is not shown";
		button.cancel();

		//Restore data
		cMenu.deleteDocument(eFile);
	}


	/**
	 * Qmetry ID: 74555
	 * File name is restored if leaving name blank while renaming
	 * 
	 */
	@Test
	public void test04_FileNameIsRestoredIfLeavingNameBlankWhileRenaming(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));

		info("-- Test case: File name is restored if leaving name blank while renaming --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//right click on eFile
		rightClickOnElement(eFile);

		//leaving name blank while renaming and hit [Enter] key
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		type(cMenu.ELEMENT_INPUT_RENAME_NODE, "", true);
		WebElement element = waitForAndGetElement(cMenu.ELEMENT_INPUT_RENAME_NODE);
		element.sendKeys(Keys.RETURN);
		waitForAndGetElement(eFile);

		//Restore data
		cMenu.deleteDocument(eFile);
	}

	/**
	 * Qmetry ID: 74557
	 * File name is updated after the rename operation is finished
	 * ================
	 * Qmetry ID: 74574
	 * Rename on Enter key
	 * 
	 */
	@Test
	public void test05_FileNameIsUpdatedAfterTheRenameOperationIsFinished(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		String newFileName = "rename05";
		By editFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", newFileName + ".txt"));

		info("-- Test case: Rename on Enter key --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//right click on eFile
		rightClickOnElement(eFile);

		//Rename on Enter key
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		type(cMenu.ELEMENT_INPUT_RENAME_NODE, newFileName, true);
		WebElement element = waitForAndGetElement(cMenu.ELEMENT_INPUT_RENAME_NODE);
		element.sendKeys(Keys.RETURN);
		waitForAndGetElement(editFile);

		//Restore data
		cMenu.deleteDocument(editFile);
	}

	/**
	 * Qmetry ID: 74559
	 * File name should change to "Renaming..." during the rename operation
	 * ======== PENDING ======
	 * Not able to automate
	 */
	/*@Test
	public void test06_CheckIfRenamingIsShown(){

	}*/

	/**
	 * Qmetry ID: 74560
	 * Focus is set in rename field
	 * 
	 */
	@Test
	public void test07_FocusIsSetInRenameField(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		String newFileName = "rename07";
		By editFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", newFileName + ".txt"));

		info("-- Test case: Focus is set in rename field --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//right click on eFile
		rightClickOnElement(eFile);

		//Focus is set in rename field
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		type(cMenu.ELEMENT_INPUT_RENAME_NODE, newFileName, false);
		WebElement element = waitForAndGetElement(cMenu.ELEMENT_INPUT_RENAME_NODE);
		String fileName = element.getAttribute("value");
		assert fileName.equals(newFileName): "Failed: Focus is set in rename field";
		element.sendKeys(Keys.RETURN);
		waitForAndGetElement(editFile);

		//Restore data
		cMenu.deleteDocument(editFile);
	}

	/**
	 * Qmetry ID: 74568
	 * Name should be populated in field
	 * ======== PENDING =======
	 * Not able to automate
	 */
	/*@Test
	public void test08_NameShouldBePopulatedInField(){

	}*/

	/**
	 * Qmetry ID: 74570
	 * No restriction on name
	 * 
	 */
	@Test
	public void test09_NoRestrictionOnName(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		String newFileName = "^/@%è$=c";
		By editFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", newFileName + ".txt"));

		info("-- Test case: No restriction on name --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//Rename a file
		cMenu.contextMenuAction(eFile, cMenu.ELEMENT_MENU_RENAME_NODE, newFileName);
		waitForAndGetElement(editFile);

		//Restore data
		cMenu.deleteDocument(editFile);
	}

	/**
	 * Qmetry ID: 74572 
	 * Rename form should dismiss when clicking outside
	 * Drive: Sites Management
	 * 
	 */
	@Test
	public void test10_RenameFormShouldDismissWhenClickingOutside(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));

		info("-- Test case:  Rename form should dismiss when clicking outside --");

		//Go to sites management
		navToolBar.goToSiteExplorer();

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//Rename form
		rightClickOnElement(eFile);
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		waitForAndGetElement(cMenu.ELEMENT_INPUT_RENAME_NODE);

		//user clicks out of the field
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		waitForElementNotPresent(cMenu.ELEMENT_INPUT_RENAME_NODE);

		//Restore data
		cMenu.deleteDocument(eFile);
	}  

	/**
	 * Qmetry ID: 74573
	 * Rename form ui
	 * Note: [Main area] and [Left panel] are displayed in Sites Management View  
	 * 
	 */
	@Test
	public void test11_RenameFormUI(){
		String filePath = "TestData/test.txt";
		By mFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		By eFile = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", "test.txt"));

		info("-- Test case: Rename Form UI --");

		//Go to Sites Management 
		navToolBar.goToSiteExplorer();
		actBar.showDrives();

		//Test Data > Upload a file: filename.txt
		click(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		ecms.uploadFile(filePath);

		//Main area > Open rename form
		rightClickOnElement(mFile);
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		waitForAndGetElement(cMenu.ELEMENT_INPUT_RENAME_NODE);
		waitForAndGetElement(button.ELEMENT_RENAME_BUTTON);
		waitForAndGetElement(button.ELEMENT_CANCEL_BUTTON);
		button.cancel();

		//Left panel > Open rename form
		rightClickOnElement(eFile);
		click(cMenu.ELEMENT_MENU_RENAME_NODE);
		waitForAndGetElement(cMenu.ELEMENT_INPUT_RENAME_NODE);
		waitForAndGetElement(button.ELEMENT_RENAME_BUTTON);
		waitForAndGetElement(button.ELEMENT_CANCEL_BUTTON);
		button.cancel();

		//Restore data
		cMenu.deleteDocument(eFile);
	}

	/**
	 * Qmetry ID: 74676
	 * Rename on Rename button
	 * 
	 */
	@Test
	public void test13_RenameOnRenameButton(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		String newFileName = "rename13";
		By editFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", newFileName + ".txt"));

		info("-- Test case: Rename on Rename button --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//Rename a file
		cMenu.contextMenuAction(eFile, cMenu.ELEMENT_MENU_RENAME_NODE, newFileName);
		waitForAndGetElement(editFile);

		//Restore data
		cMenu.deleteDocument(editFile);
	}

	/**
	 * Qmetry ID: 74579
	 * Should add title on the fly
	 * 
	 */
	@Test
	public void test14_ShouldAddTitleOnTheFly(){
		String filePath = "TestData/test.txt";
		By eFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", "test.txt"));
		String newFileName = "rename14";
		By editFile = By.xpath(siteExp.ELEMENT_DOCUMENT_TITLE.replace("${title}", newFileName + ".txt"));
		String property = "exo:title";

		info("-- Test case: Should add title on the fly --");

		//Go to Personal Drive
		navToolBar.goToPersonalDocuments();
		magDrv.addView2Drive("Web", "Personal Documents");

		actBar.goToViewMode("Web");
		actBar.addItem2ActionBar("viewProperties", actBar.ELEMENT_VIEW_PROPERTIES_ICON);
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Web");

		//Test Data > Upload a file: filename.txt
		ecms.uploadFile(filePath);

		//Go to properties tab
		doubleClickOnElement(eFile);
		actBar.goToPropertiesTab();

		//Remove property [exo:title]
		actBar.actionsOnSelectedProperty(property, "Delete");
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);

		//Rename a file
		cMenu.contextMenuAction(eFile, cMenu.ELEMENT_MENU_RENAME_NODE, newFileName);
		waitForAndGetElement(editFile);

		//Go to properties tab
		doubleClickOnElement(editFile);
		actBar.goToPropertiesTab();
		waitForAndGetElement(actBar.ELEMENT_PROPERTY.replace("{$property}", property).replace("{$value}", newFileName + ".txt"));
		button.close();

		//Restore data
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		cMenu.deleteDocument(editFile);
	}
}