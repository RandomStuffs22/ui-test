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
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: Lientm
 * @date: 16/10/2012
 */
public class ECMS_Admin_ManageDriver_Display extends PlatformBase{
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
	ActionBar actBar;

	public final String DATA_USER = "john";
	//public final String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethods(){
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
		actBar = new ActionBar(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods(){
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*case01: Check the displaying of drive after being created by user has access right
	 * add new drive
	 * check displaying of drive in site explorer when user has access permission
	 */
	@Test
	public void test01_CheckDisplayOfDriverByUserHasAccessPermission(){
		String DATA_DRIVE_NAME = "ECMS_Admin_ContentPresentation_ManageDriver_Display_01";
		By ELEMENT_DRIVE = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", DATA_DRIVE_NAME));

		//add drive
		ecMain.goToManageDrive();
		magDrv.addNewDrive(DATA_DRIVE_NAME, "collaboration","sites", "Platform/Administration","*",
				"Non-document Nodes/Sidebar", "Admin/Icons/List", false, true);

		//check displaying of drive in site explorer when user has access permission
		nav.goToSiteExplorer();
		//click(ecms.ELEMENT_SHOW_DRIVES);
		actBar.showDrives();
		waitForAndGetElement(ELEMENT_DRIVE);
		info("Drive display true");

		//delete data
		magDrv.deleteDrive(DATA_DRIVE_NAME);
	}

	/*case02: Check the displaying of drive after being created by user does not have access right with this drive
	 * add new drive
	 * check displaying of driver in site explorer when user does not have access permission
	 */
	@Test
	public void test02_CheckDisplayOfDriverOfUserNotHaveAccessPermission(){
		String DATA_DRIVER_NAME = "ECMS_Admin_ContentPresentation_ManageDriver_Display_02";
		By ELEMENT_DRIVER = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", DATA_DRIVER_NAME));

		//add drive
		ecMain.goToManageDrive();
		magDrv.addNewDrive(DATA_DRIVER_NAME,"collaboration","sites", "Platform/Administration","*",
				"Non-document Nodes/Sidebar", "Admin/Icons/List", false, true);
		magAcc.signOut();

		//login with user mary
		info("Login as mary who does not have access permission on drive");
		magAcc.signIn("mary", DATA_PASS);
		nav.goToSiteExplorer();
		//click(ecms.ELEMENT_SHOW_DRIVES);
		actBar.showDrives();
		waitForElementNotPresent(ELEMENT_DRIVER);
		info("User does not see driver");
		magAcc.signOut();

		//delete data
		magAcc.signIn(DATA_USER, DATA_PASS);
		magDrv.deleteDrive(DATA_DRIVER_NAME);
	}

	/*case03: Check the displaying of drive after deleting it
	 * add new drive
	 * delete drive
	 * check displaying of drive in site explorer
	 */
	@Test
	public void test03_CheckDisplayOfDriverAfterDelete(){
		String DATA_DRIVER_NAME = "ECMS_Admin_ContentPresentation_ManageDriver_Display_03";
		By ELEMENT_DRIVER = By.xpath(ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", DATA_DRIVER_NAME));

		//add driver
		ecMain.goToManageDrive();
		magDrv.addNewDrive(DATA_DRIVER_NAME,"collaboration","sites", "Platform/Administration","*",
				"Non-document Nodes/Sidebar", "Admin/Icons/List", false, true);

		//check displaying of driver in site explorer when user has access permission
		nav.goToSiteExplorer();
		//click(ecms.ELEMENT_SHOW_DRIVES);
		actBar.showDrives();
		waitForAndGetElement(ELEMENT_DRIVER);
		info("Drive display true");

		//delete data
		magDrv.deleteDrive(DATA_DRIVER_NAME);

		//check displaying of drive in site explorer
		nav.goToSiteExplorer();
		waitForElementNotPresent(ELEMENT_DRIVER);
		info("Can not see driver after being deleted");	
	}
}