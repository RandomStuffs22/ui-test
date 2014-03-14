package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.createnode;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * July, 29th, 2013
 *
 */
public class ECMS_SE_CreateNode_Link extends PlatformBase{
	//Platform
	Button button;
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ManageAlert magAlt;

	//Ecms
	EcmsBase ecms;
	ContextMenu cMenu;
	ContentTemplate cTemplate;
	SitesExplorer sExplorer;
	
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
		cTemplate = new ContentTemplate(driver);
		magAlt = new ManageAlert(driver);
		sExplorer = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * Qmetry ID: 66733 
	 * Create Link in Site Explorer
	 * 
	 */
	@Test
	public void test01_CreateLinkInSiteExplorer(){
		String linkName = "ECMS_SE_CreateNode_Link_01";
		String url = "http://www.google.com";
		
		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/links");
		
		info("Create a new link");
		actBar.goToAddNewContent();
		cTemplate.createNewLink(linkName, url, "en", linkName);
		
		info("Check the link");
		waitForAndGetElement(By.linkText(url));
		String handlesBefore = driver.getWindowHandle();
		click(By.linkText(url));
		
		switchToNewWindow();
		waitForAndGetElement(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", "Google"));
		driver.switchTo().window(handlesBefore);
		
		info("Restore data");
		cMenu = new ContextMenu(driver);
		cMenu.deleteDocument(By.linkText(linkName));
	}
	
	/**
	 * Qmetry ID: 66734
	 * Create Link in Site Explorer with invalid URL
	 * 
	 */
	@Test
	public void test02_CreateLinkInSiteExplorerWithInvalidURL(){
		String linkName = "ECMS_SE_CreateNode_Link_02";
		String invalidUrl = "google.com";
		
		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/links");
		
		info("Create a new link");
		actBar.goToAddNewContent();
		cTemplate.createNewLink(linkName, invalidUrl, "en", linkName);
		waitForAndGetElement(By.linkText(invalidUrl));
		
		info("Verify that nothing happens when we open that file and click the link");
		String handlesBefore = driver.getWindowHandle();
		click(By.linkText(invalidUrl));	
		switchToNewWindow();
		waitForAndGetElement(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", linkName));
		waitForAndGetElement(By.linkText(invalidUrl));
		driver.switchTo().window(handlesBefore);
		
		info("Restore data");
		cMenu = new ContextMenu(driver);
		cMenu.deleteDocument(By.linkText(linkName));
	}
		
}