package org.exoplatform.selenium.platform.gatein.functional.dashboard;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author havtt
 * @date 08-Nov-2013
 */

public class Gatein_Dashboard_ManageGadget extends DashBoard{

	ManageAccount magAc;
	NavigationToolbar navTool;
	PageManagement magPage;
	NavigationManagement magNav;
	ManageApplications magApp;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		magPage = new PageManagement(driver);
		magNav = new NavigationManagement(driver);
		magApp = new ManageApplications(driver);

		magAc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Add new gadget into dashboard with valid value by drag&drop gadget
	 * CaseId: 73363
	 */
	@Test
	public void test01_AddnewGadgetToDashboardByDragDrop(){ 
		String gadgetName = "Calendar";

		info("--Import all applications to category--");
		navTool.goToApplicationRegistry();
		magApp.importApplication();

		info("Go to Dashboard");
		navTool.goToDashboard();

		info("Add new gadget");
		dragDropGadget(gadgetName);
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);

		info("Restore data");
		actionOnGadgetOnDashboard(gadgetName,"Delete Gadget");	
	}

	/**
	 * Edit gadget preferences
	 * CaseId: 73371
	 */
	@Test
	public void test02_EditGadgetPreferences(){ 
		String gadgetName = "RSS Reader";

		info("--Import all applications to category--");
		navTool.goToApplicationRegistry();
		magApp.importApplication();

		info("Go to Dashboard");
		navTool.goToDashboard();

		info("Add new gadget");
		dragDropGadget(gadgetName);
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);

		info("Edit RSS Reader gadget preferences");
		editRSSReaderGadgetonDashboard(gadgetName,"3");

		info("Restore data");
		actionOnGadgetOnDashboard(gadgetName,"Delete Gadget");		
	}

	/**
	 * Delete gadget with  deleting confirmation
	 * CaseId: 73372
	 */
	@Test
	public void test03_DelGadgetfromDashboard(){ 
		String gadgetName = "Calculator";

		info("Go to Dashboard");
		navTool.goToDashboard();

		info("Add new gadget");
		dragDropGadget(gadgetName);
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);

		info("Delete gadget");
		actionOnGadgetOnDashboard(gadgetName,"Delete Gadget");		
	}
}
