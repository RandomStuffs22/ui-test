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
public class ECMS_SE_CreateNode_JSFile extends PlatformBase{
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
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 66731
	 * Create JS file with Active is True
	 * Qmetry ID: 66732
	 * Create JS file with valid values
	 *  
	 */
	@Test
	public void test01_CreateJSFileWithActiveIsTrue(){
		String JS_FILE = "ECMS_SE_JS_FILE_01";
		String data = "alert(\"exoplatform!\");";

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();

		info("Create a new [JS File] with Active is True and valid values");
		ecms.goToNode("intranet/js");
		actBar.goToAddNewContent();
		cTemplate.createNewJsFile(JS_FILE, "10", data, true);

		info("Check JS popup");
		click(ELEMENT_PERSONAL_DOCUMENTS);
		String message = magAlt.getTextFromAlert();
		assert message.equals("exoplatform!"): "Failed to create JS file...";
		magAlt.acceptAlert();

		info("Restore data");
		navToolBar.goToSiteExplorer();
		magAlt.acceptAlert();
		cMenu.deleteDocument(By.linkText(JS_FILE));
	}

	/**
	 * Qmetry ID: 67070
	 * Check JS priority on Site Explorer when have two JS files which have been defined by calling function
	 *  
	 */
	@Test
	public void test02_CheckJSPriorityOnSiteExplorerWhenHave2JSFilesWhichHaveBeenDefinedByCallingFunction(){
		String JS_FILE_0 = "ECMS_SE_JS_FILE_02_1";
		String data_0 = "alert(\"Hello!\");";

		String JS_FILE_1 = "ECMS_SE_JS_FILE_02_2";
		String data_1 = "alert(\"Goodbye!\");";

		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();

		info("Displaying form [Create JS File]");
		ecms.goToNode("intranet/js");
		actBar.goToAddNewContent();
		cTemplate.createNewJsFile(JS_FILE_0, "1", data_0, true);

		ecms.goToNode("js");
		actBar.goToAddNewContent();
		cTemplate.createNewJsFile(JS_FILE_1, "0", data_1, true);

		driver.navigate().refresh();
		Utils.pause(3000);
		String firstMessage = magAlt.getTextFromAlert();
		assert firstMessage.equals("Hello!"): "Failed to create JS file...";
		magAlt.acceptAlert();

		String secondMessage = magAlt.getTextFromAlert();
		assert secondMessage.equals("Goodbye!"): "Failed to create JS file...";
		magAlt.acceptAlert();
		info("Check JS priority... successful");

		info("Restore data");
		cMenu.deleteDocument(By.linkText(JS_FILE_0));
		cMenu.deleteDocument(By.linkText(JS_FILE_1));
	}

	/**
	 * Qmetry ID: 67071
	 * Check JS priority on Site Explorer when have two JS files which have been defined with the same function
	 *  
	 */
	@Test
	public void test03_CheckJSPriorityOnSiteExplorerWhenHave2JSFilesWhichHaveBeenDefinedWithTheSameFunction(){
		String JS_FILE_0 = "ECMS_SE_JS_FILE_03_1";
		String data_0 = "function b(){alert(\"Hello!\");}";

		String JS_FILE_1 = "ECMS_SE_JS_FILE_03_2";
		String data_1 = "function b(){alert(\"Good bye!\");}";

		String JS_FILE_2 = "ECMS_SE_JS_FILE_03_3";
		String data_2 = "b();";
		
		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();

		info("Displaying form [Create JS File]");
		ecms.goToNode("intranet/js");
		actBar.goToAddNewContent();

		info("Create 2 JS Files");
		cTemplate.createNewJsFile(JS_FILE_0, "0", data_0, true);
		ecms.goToNode("js");
		actBar.goToAddNewContent();
		cTemplate.createNewJsFile(JS_FILE_1, "1", data_1, true);

		info("Add new JS file to call function b()");
		ecms.goToNode("js");
		actBar.goToAddNewContent();
		cTemplate.createNewJsFile(JS_FILE_2, "2", data_2, true);
		
		driver.navigate().refresh();
		Utils.pause(3000);
		String message = magAlt.getTextFromAlert();
		assert message.equals("Hello!"): "Failed to create JS file...";
		magAlt.acceptAlert();
		
		info("Restore data");
		cMenu.deleteDocument(By.linkText(JS_FILE_0));
		cMenu.deleteDocument(By.linkText(JS_FILE_1));
		cMenu.deleteDocument(By.linkText(JS_FILE_2));
	}

	/**
	 * Qmetry ID: 67114
	 * Check the affection of JS file in Shared site
	 * ============ PENDING ========================
	 * ============ Issue: ECMS-5508 =============== 
	 */
	//@Test(groups = {"Error"})
	public void test04_CheckTheAffectionOfJSFileInSharedSite(){
		String JS_FILE = "ECMS_SE_JS_FILE_04";
		String data = "alert(\"Hello!\");";
		
		info("Go to Site Explorer");
		navToolBar.goToSiteExplorer();

		info("Displaying form [Create JS File]");
		ecms.goToNode("shared/js");
		actBar.goToAddNewContent();
		
		info("Add new JS file");
		cTemplate.createNewJsFile(JS_FILE, "0", data, true);
		
		info("Check the affection...");
		driver.navigate().refresh();
		Utils.pause(3000);
		String message = magAlt.getTextFromAlert();
		assert message.equals("Hello!"): "Failed to create JS file...";
		magAlt.acceptAlert();
		
		driver.get(DEFAULT_BASEURL + "/acme");
		Utils.pause(3000);
		String acmeMessage = magAlt.getTextFromAlert();
		assert acmeMessage.equals("Hello!"): "Failed to create JS file...";
		magAlt.acceptAlert();
		
		info("Restore data");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(JS_FILE));
	}
}