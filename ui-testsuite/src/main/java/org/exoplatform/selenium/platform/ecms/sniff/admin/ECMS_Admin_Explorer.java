package org.exoplatform.selenium.platform.ecms.sniff.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.admin.ManageTag;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * May, 2013
 */
public class ECMS_Admin_Explorer extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	NavigationToolbar nav;

	//Ecms
	EcmsBase ecms;
	ECMainFunction ecMain;
	ManageDrive magDrv;
	ManageTag magTag;
	ManageView magView;
	ActionBar actBar;

	String DATA_USER = "john";
	//String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		info("LogIn to Intranet, User... " + DATA_USER);
		magAcc = new ManageAccount(driver);
		nav = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		ecMain = new ECMainFunction(driver);
		magDrv = new ManageDrive(driver);
		magView = new ManageView(driver);
		magTag = new ManageTag(driver);
		actBar = new ActionBar(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods(){
		info("-- LogOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 65854
	 * Add Drive
	 * ================
	 * Qmetry ID: 67835
	 * Edit Drive
	 * ================
	 * Qmetry ID: 67836
	 * Delete Drive
	 * 
	 */
	@Test
	public void test01_AddEditDeleteDrive(){
		String drive = "Ecms_Admin_Drive_Test";
		By eDrive = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", drive));

		info("-- Go to ECMS/Admin/Drives --");
		ecMain.goToManageDrive();

		//Add a drive
		magDrv.addNewDrive(drive, "collaboration","sites", "Platform/Administration", "*", "Non-document Nodes/Sidebar", "Admin", false, true);
		nav.goToSiteExplorer();
		actBar.showDrives();
		waitForAndGetElement(eDrive);

		//Edit a drive
		ecMain.goToManageDrive();
		info("-- Edit drive -- " + drive);
		magDrv.addNewDrive(drive, "system","jcr:system", "Organization/Management/Executive Board","manager",
				"Non-document Nodes/Sidebar/Hidden Nodes", "Admin/Icons/List", true, true);

		WebElement element = waitForAndGetElement(magDrv.ELEMENT_VERIFY_WORKSPACE_NAME.replace("${driveName}", drive), 3000, 1, 2);
		String dr = element.getAttribute("data-original-title");	
		assert dr.equals("system"): "[Workspace] is not updated";

		//Delete a drive
		info("-- Delete drive --");
		magDrv.deleteDrive(drive);		
	}

	/**
	 * Qmetry ID: 65866
	 * Add Tags
	 * ================
	 * Qmetry ID: 67833
	 * Edit Tags
	 * ================
	 * Qmetry ID: 67834
	 * Delete Tags
	 * 
	 */
	@Test
	public void test02_AddEditAndDeleteTags(){
		String styleName = "curious"; 
		String range = "0..5";
		String htmlStyle = "font-size: 12px;";
		String editRange = "2..6";
		String editHtmlStyle = "font-size: 12px; font-weight: bold; color: #6b6b6b; font-family: verdana; text-decoration:none;";

		ecMain.goToTagsTab();

		//Add a new TAG
		magTag.addNewTag(styleName, range, htmlStyle);

		//Edit a TAG
		magTag.editTag(styleName, editRange, editHtmlStyle, true);

		//Delete a TAG
		magTag.deleteTag(styleName);
	}

	/**
	 * Qmetry ID: 67839
	 * Add a View
	 * ================
	 * Qmetry ID: 65868
	 * View a View
	 * ================
	 * Qmetry ID: 67837
	 * Edit a View
	 * ================
	 * Qmetry ID: 67838
	 * Delete a View
	 * 
	 */
	@Test
	public void test03_AddViewEditAndDeleteView(){
		String viewName = "New View Test";
		String editTemplate = "Content";
		String actionTab = "Test";

		ecMain.goToManageViews();

		//Open [Add New View] Form
		magView.openAddViewForm("Add View", "Add View");

		//Fill data for Add New View Form
		magView.fillAddNewViewForm(viewName, "List", actionTab, "addCategory", true, true, "Organization/Management", "*");

		//Edit a View
		magView.editView(viewName, editTemplate, false, true, true, actionTab, "manageCategories/manageActions", true, false, "", "", true, "james");

		//View a View
		magView.viewSelectedView(viewName, true, editTemplate, true, actionTab, "Manage Categories", true, "James");

		//Delete a View
		//magView.deleteView(viewName, "Are you sure you want to delete this view?", true);
		magView.actionOnSelectedView(viewName, "Delete", true);
	}
}