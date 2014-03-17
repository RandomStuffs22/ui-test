package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.filemanagementview;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.*;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;

/**
 * @author: HangNTT
 * @date: 29/08/2013
 */
public class ECMS_SE_FileManagementView_Configure extends PlatformBase {
	
	ManageAccount magAc;
	NavigationToolbar navToolBar;
	ECMainFunction ecMain;
	ManageView magView;
	EcmsBase ecms;
	SitesExplorer sitesEx;
	ActionBar actBar;
	BrowserPreferences BroPres; 
	Button button;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS); 
		ecMain = new ECMainFunction(driver);
		magView = new ManageView(driver);
		ecms = new EcmsBase(driver);
		sitesEx = new SitesExplorer(driver);
		actBar = new ActionBar(driver);
		BroPres = new BrowserPreferences(driver);
		button = new Button(driver);
	}
	
	/**CaseId: 74505 -> Hide the explorer tree panel in side bar
	 */
	@Test
	public void test01_HideTheExplorerTreePanelInSideBar(){
		navToolBar.goToSiteExplorer();
		click(sitesEx.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(ecms.ELEMENT_SIDEBAR_ACME);
		waitForAndGetElement(ecms.ELEMENT_SITEBAR_INTRANET);
		waitForAndGetElement(ecms.ELEMENT_SITEBAR_SHARED);
		navToolBar.goToContentAdministration();
		ecMain.goToManageViews();
		waitForElementNotPresent(magView.ELEMENT_HIDE_EXPLORER_PANEL);
		magView.editView("Web", "Content", false, true);
		navToolBar.goToSiteExplorer();
		click(sitesEx.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForElementNotPresent(ecms.ELEMENT_SIDEBAR_ACME);
		waitForElementNotPresent(ecms.ELEMENT_SITEBAR_INTRANET);
		waitForElementNotPresent(ecms.ELEMENT_SITEBAR_SHARED);
	}
	
	/**CaseId: 74582 -> Show the explorer tree panel in side bar
	 */
	@Test
	public void test02_ShowTheExplorerTreePanelInSideBar(){	
		navToolBar.goToSiteExplorer();
		waitForElementNotPresent(ecms.ELEMENT_SIDEBAR_ACME);
		waitForElementNotPresent(ecms.ELEMENT_SITEBAR_INTRANET);
		waitForElementNotPresent(ecms.ELEMENT_SITEBAR_SHARED);
		navToolBar.goToContentAdministration();
		ecMain.goToManageViews();
		waitForElementNotPresent(magView.ELEMENT_HIDE_EXPLORER_PANEL);
		magView.editView("Web", "Content", false, false);
		magAc.signOut();
		magAc.signIn("john", DATA_PASS); 
		navToolBar.goToSiteExplorer();
		actBar.showDrives();
		actBar.chooseDrive(sitesEx.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		navToolBar.goToSiteExplorer();
		click(sitesEx.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(ecms.ELEMENT_SIDEBAR_ACME);
		waitForAndGetElement(ecms.ELEMENT_SITEBAR_INTRANET);
		waitForAndGetElement(ecms.ELEMENT_SITEBAR_SHARED);
	}
	
	/**CaseId: 74592 -> Verify the box "Show Side bar" in preference
	 */
	@Test
	public void test03_VerifyHideExplorerPanelInSideBarBox(){	
		navToolBar.goToPersonalDocuments();
		click(BroPres.ELEMENT_PREFERENCE_LINK);
		WebElement checkbox = waitForAndGetElement(BroPres.ELEMENT_SHOW_SIDEBAR, DEFAULT_TIMEOUT, 1, 2);
		assert !checkbox.isSelected();
		info("Checkbox show sidebar is not checked");		
	}
	
	/**CaseId: 74591 -> Verify "Hide explorer panel in side bar" box for File Management View
	 */
	@Test
	public void test04_VerifyTheBoxShowSidebarInPreference(){		
		navToolBar.goToPersonalDocuments();
		waitForElementNotPresent(ecms.ELEMENT_SIDEBAR_ACME);
		waitForElementNotPresent(ecms.ELEMENT_SITEBAR_INTRANET);
		waitForElementNotPresent(ecms.ELEMENT_SITEBAR_SHARED);
		navToolBar.goToContentAdministration();
		ecMain.goToManageViews();
		magView.actionOnSelectedView("Admin", "Edit");
		WebElement checkbox = waitForAndGetElement(magView.ELEMENT_HIDE_EXPLORER_PANEL, DEFAULT_TIMEOUT, 1, 2);
		assert checkbox.isSelected();
		info("Checkbox Hide explorer panel in side bar is checked");
		button.save();
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}