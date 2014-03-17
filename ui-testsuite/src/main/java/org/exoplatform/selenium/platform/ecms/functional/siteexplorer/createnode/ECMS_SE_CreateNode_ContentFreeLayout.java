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
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * July, 25th, 2013
 *
 */
public class ECMS_SE_CreateNode_ContentFreeLayout extends PlatformBase{
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
	SitesExplorer sitesExp;

	public String DATA_USER = "john";
	//public String DATA_PASS = DATA_PASS;

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
		sitesExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 66728
	 * Create free layout content in Web Content folder with Name in different languages
	 * 
	 */
	@Test
	public void test01_CreateFreeLayoutContentInWebContentFolderWithNameInDifferentLanguages(){
		String WEB_CONTENT_VN_TITLE = "Lập_trình_viên_eXo_01";
		By vnTitle = By.linkText(WEB_CONTENT_VN_TITLE);

		String WEB_CONTENT_FR_TITLE = "Développer_eXo_01";
		By frTitle = By.linkText(WEB_CONTENT_FR_TITLE);

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/web contents");

		info("Add new [Free Layout Webcontent]");
		actBar.goToAddNewContent();

		info("...with name in Vietnamese");
		cTemplate.createNewWebContent(WEB_CONTENT_VN_TITLE, WEB_CONTENT_VN_TITLE, "", "", "", "", false, "vi");
		waitForAndGetElement(vnTitle);

		info("...with name in French");
		ecms.goToNode("web contents");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(WEB_CONTENT_FR_TITLE, WEB_CONTENT_FR_TITLE, "", "", "", "", false, "fr");
		waitForAndGetElement(frTitle);

		info("Restore data");
		cMenu.deleteDocument(vnTitle);
		cMenu.deleteDocument(frTitle);
	}

	/**
	 * Qmetry ID: 67349
	 * Add free layout content in Web Content folder
	 * 
	 */
	@Test
	public void test02_AddFreeLayoutContentInWebContentFolder(){
		String WEB_CONTENT_TITLE = "ECMS_WebContent_02";
		By wTitle = By.linkText(WEB_CONTENT_TITLE);

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/web contents");

		info("Add new [Free Layout Webcontent]");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(WEB_CONTENT_TITLE, WEB_CONTENT_TITLE, "", "", "", "");
		waitForAndGetElement(wTitle);

		info("Check the status of the new webcontent");
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_FIRST_REVISION_DATE);
		button.close();
		ecms.goToNode("web contents");
		WebElement element = waitForAndGetElement(sitesExp.ELEMENT_STATUS_DOCUMENT.replace("${title}", WEB_CONTENT_TITLE));
		String status = element.getText();
		assert status.contains("Draft") : "Check the status...Failed";
		info("Status is [Draft]...");

		info("Restore data");
		cMenu.deleteDocument(wTitle);
	}

	/**
	 * Qmetry ID: 67350
	 * Add free layout content in web content folder with blank Name
	 * 
	 */
	@Test
	public void test03_AddFreeLayoutContentInWebContentFolderWithBlankName(){
		String WEB_CONTENT_TITLE = "ECMS_WebContent_03";

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/web contents");

		info("Add new [Free Layout Webcontent] with blank name");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent("", WEB_CONTENT_TITLE, "", "", "", "");
		magAlt.verifyAlertMessage(cTemplate.MESSAGE_NAME_REQUIRED_FIELD);
		button.close();
	}

	/**
	 * Qmetry ID: 67351
	 * Add free layout content in Web Content folder with special characters in Name
	 * 
	 */
	@Test
	public void test04_AddFreeLayoutContentInWebContentFolderWithSpecialCharactersInName(){
		String WEB_CONTENT_TITLE = cTemplate.DATA_SPECIAL_CHARACTER_STRING + "WebContent_04";

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/web contents");
		
		info("Add new [Free Layout Webcontent] with special characters in name field");
		for (int i = 0 ; i < cTemplate.DATA_SPECIAL_CHARACTER_2.length; i++) { 
			actBar.goToAddNewContent();
			cTemplate.createNewWebContent(cTemplate.DATA_SPECIAL_CHARACTER_2[i], WEB_CONTENT_TITLE, "", "", "", "");
			info("Verify showing message alerts: [Field Name contains invalid characters]");
			magAlt.verifyAlertMessage(cTemplate.MESSAGE_FIELD_NAME_INVALID_CHARS);
			button.close();
		}
	}
}