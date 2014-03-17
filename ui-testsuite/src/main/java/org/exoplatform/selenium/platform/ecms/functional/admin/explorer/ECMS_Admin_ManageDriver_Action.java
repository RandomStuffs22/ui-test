package org.exoplatform.selenium.platform.ecms.functional.admin.explorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.admin.Permission;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * PLF4: Add a new test case
 * <li>Add a Drive with no applied view</li> 
 * @author vuna2
 * @date April, 15th, 2013
 */

/*
 * @author: Lientm
 * @date: 9/10/2012
 */
public class ECMS_Admin_ManageDriver_Action extends PlatformBase{

	//Platform
	NavigationToolbar nav;
	ManageAccount magAcc;
	UserGroupManagement userGrp;
	Button button;
	ManageAlert alt;

	//Ecms
	EcmsBase ecms;
	ECMainFunction ecMain;
	SitesExplorer sitesExp;
	Permission adminPer;
	ManageDrive magDrv;

	public final String DATA_USER = "john";
	//public final String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with "+ DATA_USER);
		nav = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		userGrp = new UserGroupManagement(driver);
		button = new Button(driver);
		alt = new ManageAlert(driver);
		ecms = new EcmsBase(driver);
		ecMain = new ECMainFunction(driver);
		sitesExp = new SitesExplorer(driver);
		adminPer = new Permission(driver);
		magDrv = new ManageDrive(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/*case01+02+03: Add drive
	 * go to add drive
	 * add new drive
	 * edit drive
	 * delete drive
	 */
	@Test
	public void test01_02_03_AddEditDeleteDrive(){
		String DATA_DRIVE_NAME = "ECMS_Admin_Explorer_ManageDriver_Action_01";
		//By ELEMENT_DRIVE = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", DATA_DRIVE_NAME));

		//go to add drive
		info("Go to add new drive");
		ecMain.goToManageDrive();
		magDrv.addNewDrive(DATA_DRIVE_NAME,"dms-system","exo:ecm", "Organization/Management/Executive Board","member",
				"Non-document Nodes/Sidebar", "Admin/Icons/List", false, true);

		//edit drive
		info("Edit drive");
		magDrv.addNewDrive(DATA_DRIVE_NAME,"system","jcr:system", "Organization/Management/Executive Board","manager",
				"Non-document Nodes/Sidebar/Hidden Nodes", "Admin/List", true, true);

		//delete drive
		info("Delete drive");
		magDrv.deleteDrive(DATA_DRIVE_NAME);
		info("Delete driver successfully");
	}
	
	/**
	 * Step 1: Open [Manage Drive] form
	 * Step 2: Open [Add drive] form
	 * Step 3: Input data in [Drive] form
	 */
	@Test
	public void test04_AddDriveWithNoAppliedView(){
		String DATA_DRIVE_NAME = "Add_Drive_Without_View";

		info("-- Open Manage Drive Form --");
		ecMain.goToManageDrive();

		magDrv.addNewDrive(DATA_DRIVE_NAME, "dms-system", "exo:ecm", "Organization/Management/Executive Board","member",
				"Non-document Nodes/Sidebar", "", false, false);	
	}
}