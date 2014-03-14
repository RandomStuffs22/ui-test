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
 * July, 25th, 2013
 *
 */
public class ECMS_SE_CreateNode_PictureOnHeadLayout extends PlatformBase{
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
		sitesExp = new SitesExplorer(driver);
		magAlt = new ManageAlert(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 67454
	 * Add Picture on head content in Web content with Name in different languages
	 * 
	 */
	@Test
	public void test01_AddPictureOnHeadContentInWebContentWithNameInDifferentLanguages(){
		String WEB_CONTENT_VN_TITLE = "Lập_trình_viên_eXo_001";
		By vnTitle = By.linkText(WEB_CONTENT_VN_TITLE);

		String WEB_CONTENT_FR_TITLE = "Développer_eXo_001";
		By frTitle = By.linkText(WEB_CONTENT_FR_TITLE);

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/web contents");

		info("Go to [Add New Webcontent]");
		actBar.goToAddNewContent();

		info("Illustrated Web Content...with name in Vietnamese");
		cTemplate.createNewIllustratedWebContent(WEB_CONTENT_VN_TITLE, WEB_CONTENT_VN_TITLE, "", "", "", "", "", "vi");
		waitForAndGetElement(vnTitle);

		info("Illustrated Web Content...with name in French");
		ecms.goToNode("web contents");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(WEB_CONTENT_FR_TITLE, WEB_CONTENT_FR_TITLE, "", "", "", "", "", "fr");
		waitForAndGetElement(frTitle);

		info("Restore data");
		cMenu.deleteDocument(vnTitle);
		cMenu.deleteDocument(frTitle);	
	}
	
	/**
	 * Qmetry ID: 67456
	 * Add Picture on head layout content in Web content folder
	 * 
	 */
	@Test
	public void test02_AddPictureOnHeadLayoutContentInWebContentFolder(){
		String WEB_CONTENT_TITLE = "ECMS_Picture_WebContent_02";
		By bNode = By.xpath(sitesExp.ELEMENT_SE_NODE.replace("{$node}", WEB_CONTENT_TITLE));
		String path2Image = "TestData/Winter.jpg";
		
		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/web contents");

		info("Add new [Illustrated Webcontent]");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(WEB_CONTENT_TITLE, WEB_CONTENT_TITLE, path2Image, "", "", "", "");
		waitForAndGetElement(By.xpath(cMenu.ELEMENT_FILE_TITLE.replace("${titleOfFile}", WEB_CONTENT_TITLE)));	

		info("Restore data");
		click(By.linkText("web contents"));
		cMenu.deleteDocument(bNode);		
	}
	

	/**
	 * Qmetry ID: 67458
	 * Add Picture on head layout content in Web content folder with blank required fields
	 * 
	 */
	@Test
	public void test03_AddPictureOnHeadLayoutContentInWebContentFolderWithBlankRequiredFields(){
		String WEB_CONTENT_TITLE = "";

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/web contents");

		info("Add new [Illustrated Webcontent] with blank name");
		actBar.goToAddNewContent();
		cTemplate.createNewIllustratedWebContent(WEB_CONTENT_TITLE, "WEB_CONTENT", "", "", "", "", "", "en", false);
		magAlt.verifyAlertMessage(cTemplate.MESSAGE_NAME_REQUIRED_FIELD);
		button.close();	
	}
}