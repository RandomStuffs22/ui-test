package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.BrowserPreferences;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 27/05/2013
 * @author thuntn
 *
 */

public class ECMS_SE_Setting extends PlatformBase {
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ContentTemplate cTemplate;
	EcmsBase ecms;
	ContextMenu cMenu;
	ManageAccount magAcc;
	
	SitesExplorer siteExp;
	BrowserPreferences preferences;
	
	/**
	 * CaseID 65872: Set up browsing Preferences
	 */
	@Test
	public void test01_SetupBrowsingPreferences() {
		String node1= "test01AdvancedSearch";
		String des = "test01 desc";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		
		info("Setup browsing preferences");
		
		actBar.chooseDrive(siteExp.ELEMENT_SITES_MANAGEMENT_DRIVE);
		//Create document
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1, des);
		
		
		//Set up preferences
		preferences.setUpPreferenceOption("enableStructure");
		preferences.setUpPreferenceOption("showHiddenNode");
		//waitForAndGetElement(siteExp.ELEMENT_THUMBNAIL_HIDDEN_NODE);
		waitForAndGetElement(siteExp.ELEMENT_DMS_STRUCTURE);
		
		//Delete data
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bNode1);
	}
	
	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		navToolBar.goToSiteExplorer();
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		preferences = new BrowserPreferences(driver);

	}
	
	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
