package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.createnode;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
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
 * July, 29th, 2013
 *
 */
public class ECMS_SE_CreateNode_CSSFile extends PlatformBase{
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
		sExplorer = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 66724
	 * Create CSS file on Site explorer with Active is True and the priority is highest
	 *  
	 */
	@Test
	public void test01_CreateCSSFileWithActiveIsTrue(){
		String FILE_TITLE = "ECMS_SE_CSS_FILE_01";
		String filePath = "TestData/ECMS_CSS_File_Color_Red.txt";
		String data = Utils.getFileContent(filePath);

		info("Go to CSS Folder in Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("intranet/css");

		info("Create a new CSS File: change the tags color to red");
		actBar.goToAddNewContent();
		cTemplate.createNewCssFile(FILE_TITLE, "1", data, true);

		info("Check the body tags: changed in red color");
		WebElement element = waitForAndGetElement(sExplorer.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		String styleValue = element.getCssValue("color");
		info("-- Dispalyed Color -- " + styleValue);
		assert styleValue.equals("rgba(255, 0, 0, 1)"): "cannot set the tags color to red...";	

		info("Restore data");
		cMenu.deleteDocument(By.linkText(FILE_TITLE));	
	}

	/**
	 * Qmetry ID: 67038
	 * Check CSS priority on Site Explorer when have two CSS files which have the different content.
	 *  
	 */
	@Test
	public void test02_CheckCSSPriorityOf2CSSFilesThatHaveDifferentContent(){
		String FILE_TITLE_BLUE = "ECMS_SE_CSS_FILE_BLUE_02";
		String data0 = "body {background: black;}";

		String FILE_TITLE_RED = "ECMS_SE_CSS_FILE_RED_02";
		String data1 = "UIOptionBar .ActionIcon:hover {color: blue;}";

		info("Go to CSS Folder in Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/css");


		info("Create a new CSS File: change the text color to red");
		actBar.goToAddNewContent();
		cTemplate.createNewCssFile(FILE_TITLE_RED, "10", data1, true);

		info("Create a new CSS File: change the background color to blue");
		ecms.goToNode("css");
		actBar.goToAddNewContent();
		cTemplate.createNewCssFile(FILE_TITLE_BLUE, "11", data0, true);

		//Switch to ACME Site
		driver.get(DEFAULT_BASEURL + "/acme");
		driver.navigate().refresh();
		Utils.pause(3000);

		info("Check the text color: changed in red color");
		WebElement elementR = waitForAndGetElement(By.xpath("//p"));
		String styleValueR = elementR.getCssValue("color");
		info("-- Dispalyed Color -- " + styleValueR);
		assert styleValueR.equals("rgba(255, 0, 0, 1)"): "cannot set the text color to red...";

		//WebElement elementG = waitForAndGetElement(By.id("SITEBODY-VIEW-BLOCK"));
		//String styleValueG = elementG.getCssValue("color");
		//info("-- Dispalyed Color -- " + styleValueG);
		//assert styleValueR.equals("rgba(0, 0, 255, 1)"): "cannot set the background color to green...";

		//reset data
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(FILE_TITLE_RED));
		cMenu.deleteDocument(By.linkText(FILE_TITLE_BLUE));
	}

	/**
	 * Qmetry ID: 67039
	 * Check CSS priority on Site Explorer when have two CSS files which have the same content and the different priority level.
	 *  
	 */
	@Test
	public void test03_CheckCSSPriorityOf2CSSFilesHaveTheSameContentAndDifferentPriorityLevel(){
		String FILE_TITLE_GREEN = "ECMS_SE_CSS_FILE_GREEN_03";
		String data0 = "p{color:green;}";

		String FILE_TITLE_RED = "ECMS_SE_CSS_FILE_RED_03";
		String data1 = "p{ color:red;}";

		info("Go to CSS Folder in Site Explorer");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("acme/css");


		info("Create a new CSS File: change the text color to red with priority [10]");
		actBar.goToAddNewContent();
		cTemplate.createNewCssFile(FILE_TITLE_RED, "10", data1, true);

		info("Create a new CSS File: change the text color to Green with priority [11]");
		ecms.goToNode("css");
		actBar.goToAddNewContent();
		cTemplate.createNewCssFile(FILE_TITLE_GREEN, "11", data0, true);

		//Switch to ACME Site
		driver.get(DEFAULT_BASEURL + "/acme");
		driver.navigate().refresh();
		Utils.pause(3000);

		info("Verify that the text color has been changed to [Green] and not to [Red] color");
		WebElement element = waitForAndGetElement(By.xpath("//p"));
		String styleValue = element.getCssValue("color");
		info("-- Dispalyed Color -- " + styleValue);
		assert !styleValue.equals("rgba(255, 0, 0, 1)"): "Error: could set the text color to red...";
		assert styleValue.equals("rgba(0, 128, 0, 1)"): "Error: cannot set the text color to green...";

		//reset data
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(FILE_TITLE_RED));
		cMenu.deleteDocument(By.linkText(FILE_TITLE_GREEN));
	}

	/**
	 * Qmetry ID: 67113
	 * Check the affection of CSS file in Share site
	 *  
	 */
	@Test
	public void test04_CheckTheAffectionOfCSSFileInShareSite(){
		String FILE_TITLE_RED = "ECMS_SE_CSS_FILE_RED_04";
		String data1 = "body{ color:red;}";

		info("Go to CSS Folder in Site Explorer/Shared File");
		navToolBar.goToSiteExplorer();
		ecms.goToNode("shared/css");

		info("Create a new CSS File: change the text color to red");
		actBar.goToAddNewContent();
		cTemplate.createNewCssFile(FILE_TITLE_RED, "11", data1, true);

		navToolBar.goToHomePage();
		driver.navigate().refresh();
		Utils.pause(3000);
		WebElement iElement = waitForAndGetElement(By.xpath("//body"));
		String iValue = iElement.getCssValue("color");
		info("-- The dispalyed color on intranet-- " + iValue);
		assert iValue.equals("rgba(255, 0, 0, 1)"): "Error: could set the text color to red...";

		//Switch to ACME Site
		driver.get(DEFAULT_BASEURL + "/acme");
		driver.navigate().refresh();
		Utils.pause(3000);

		WebElement acElement = waitForAndGetElement(By.xpath("//body"));
		String acValue = acElement.getCssValue("color");
		info("-- The dispalyed color on ACME -- " + acValue);
		assert acValue.equals("rgba(255, 0, 0, 1)"): "Error: could set the text color to red...";

		info("Restore data");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(FILE_TITLE_RED));
	}
}