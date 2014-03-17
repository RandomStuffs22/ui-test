package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publishactivities;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author havtt
 * Sep, 18th, 2013
 *
 */

public class ECMS_SE_PublishActivities_ContentActivities_Delete extends PlatformBase{

	ManageAlert magAlert = new ManageAlert(driver);

	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	SitesExplorer SE;

	//Ecms
	ContextMenu cMenu;
	ContentTemplate cTemplate;
	HomePageActivity sActivity;

	public String DATA_USER = "john";
	//public String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		sActivity = new HomePageActivity(driver);
		cMenu = new ContextMenu(driver);
		cTemplate = new ContentTemplate(driver);
		SE = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);

	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/**
	 * Qmetry ID: 76986
	 * Check content activity after deleting a content
	 * 
	 */
	@Test
	public void test01_CheckContentActivityAfterDeleteContent(){
		String FILE_TITLE_01 = "ECMS_SE_Content_01";
		By bNode = By.xpath(SE.ELEMENT_SE_NODE.replace("{$node}", FILE_TITLE_01));

		info("Go to Content Explorer");
		navToolBar.goToSiteExplorer();

		info("Create a node in root path");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(FILE_TITLE_01, FILE_TITLE_01, "", "", "", "");

		info("Delete a node in Site Explorer");
		cMenu.deleteDocument(bNode);

		info("Go to Intranet Homepage");
		navToolBar.goToHomePage();

		info("Check if a node activity is displayed or not");
		waitForElementNotPresent(By.linkText(FILE_TITLE_01));
	}

	/**
	 * Qmetry ID: 76989
	 * Delete a content activity
	 * 
	 */
	@Test
	public void test02_DeleteContentActivity(){
		String FILE_TITLE_02 = "ECMS_SE_Content_02";
		By bNode = By.xpath(SE.ELEMENT_SE_NODE.replace("{$node}", FILE_TITLE_02));

		info("Go to Content Explorer");
		navToolBar.goToSiteExplorer();

		info("Create a node in root path");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(FILE_TITLE_02, FILE_TITLE_02, "", "", "", "");

		info("Go to Intranet Homepage");
		navToolBar.goToHomePage();

		info("Delete a node activity");
		sActivity.deleteActivity(FILE_TITLE_02);

		info("Check if a node activity is displayed or not");
		waitForElementNotPresent(By.linkText(FILE_TITLE_02));

		info("Restore data");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);

	}
}
