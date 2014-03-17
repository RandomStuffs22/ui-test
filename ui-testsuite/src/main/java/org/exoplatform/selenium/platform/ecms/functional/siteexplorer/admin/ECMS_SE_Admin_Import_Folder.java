package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Thuntn
 * @date 27/08/2013
 */

public class ECMS_SE_Admin_Import_Folder extends PlatformBase {
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	ContentTemplate cTemplate;
	ContextMenu cMenu;
	SitesExplorer siteExp;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);

		info("Add Import icon to action bar if it does not existed");
		navToolBar.goToSiteExplorer();
		actBar.addItem2ActionBar("importNode", actBar.ELEMENT_IMPORT_LINK);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//		logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * CaseID: 66506
	 * Import a node into Content folder
	 */
	@Test
	public void test01_ImportNodeIntoContentFolder(){
		String title2 = "test01_Import_ContentFolder";
		By bNode = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", title2));
		String linkFile = "TestData/sysview1.xml";
		String fileImport = "test01_ImportFolder_01";
		By bImport = By.xpath(siteExp.ELEMENT_NODE_LINK.replace("${nodeLabel}", fileImport));

		info("Import a node into Content folder");
		info("Create a node");
		cTemplate.createNewFolder(title2, folderType.Content);

		actBar.goToNode(bNode);

		info("Import a node into this content folder");
		actBar.importNode(linkFile, "","Create New", false);
		waitForAndGetElement(bImport);

		//Delete data
		cMenu.deleteDocument(bNode);
	}

}