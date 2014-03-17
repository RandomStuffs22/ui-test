package org.exoplatform.selenium.platform.gatein.functional.others;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
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
 * @author Hangntt
 * @date 25 Nov 2013
 * 
 */

public class Gatein_Others_SiteMap extends DashBoard {

	ManageAccount magAc;
	NavigationToolbar navTool;
	PageManagement pageMag;
	Button but;
	ManageApplications app;
	NavigationManagement navMag;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		pageMag = new PageManagement(driver);
		but = new Button(driver);
		app = new ManageApplications(driver);
		navMag = new NavigationManagement(driver);
		magAc.signIn("john", DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**CaseId: 73388 -> Add Site Map page and check display
	 * 
	 */
	@Test
	public void test01_CheckSiteMapPortlet(){
		String nodeName = "SiteMapPage";
		String portalName = "intranet";
		String parentNode = "";

		info("Import Application");
		navTool.goToApplicationRegistry();
		app.importApplication();
		waitForAndGetElement(app.ELEMENT_CATEGORY_NAME.replace("${categoryName}", "Integration"), 60000);
		waitForAndGetElement(app.ELEMENT_CATEGORY_NAME.replace("${categoryName}", "Navigation"));
		waitForAndGetElement(app.ELEMENT_CATEGORY_NAME.replace("${categoryName}", "System"));
		click(ELEMENT_HOME_PAGE);

		navTool.goToPageCreationWizard();
		click(ELEMENT_UP_LEVEL);
		Map<String, String> portal = new HashMap<String, String>();
		portal.put("Navigation/local._web.SiteMapPortlet", "");
		pageMag.addNewPageEditor(nodeName, "", null, "Navigation", portal, false,false);
		click(pageMag.ELEMENT_SITEMAP_PAGE);

		info("Show site map page");
		click(pageMag.ELEMENT_PORTAL_ADMINISTRATION);
		waitForAndGetElement(pageMag.ELEMENT_APPLICATION_REGISTRY_PAGE);
		waitForAndGetElement(pageMag.ELEMENT_MANAGE_PAGES);
		waitForAndGetElement(pageMag.ELEMENT_ADD_USER_PAGES);
		waitForAndGetElement(pageMag.ELEMENT_MANAGE_USERS_AND_GROUPS_PAGES);

		info("Show page name in site map");
		click(pageMag.ELEMENT_APPLICATION_REGISTRY_PAGE);

		info("Go to Navigation Management menu");
		navTool.goToPortalSites();
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		info("Delete page (node)");
		navMag.deleteNode(portalName, parentNode, nodeName, false);

	}
}
