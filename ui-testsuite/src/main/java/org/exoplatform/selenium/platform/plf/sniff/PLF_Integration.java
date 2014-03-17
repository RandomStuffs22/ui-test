package org.exoplatform.selenium.platform.plf.sniff;
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PlatformBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 22/10/2013
 *
 */
public class PLF_Integration extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar naviToolbar;
	ManageApplications magApp;
	PageEditor pagEditor;
	PageManagement pageMag;
	HomePageActivity hpActivity;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		naviToolbar = new NavigationToolbar(driver);
		magApp = new ManageApplications(driver);
		pagEditor = new PageEditor(driver);
		pageMag = new PageManagement(driver);
		hpActivity = new HomePageActivity(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Check Home page ==
	 * Test case ID: 70863
	 * Step 1: Show Intranet Home page
	 */
	@Test
	public void test01_CheckHomePage(){
		/*Step 1: Show Intranet Home page*/ 
		//- Login intranet site by root
		//Home page is show properly, inlcuding activity stream at the center, gadgets that are well displayed at the right
		waitForAndGetElement(hpActivity.ELEMENT_ACTIVITY_TEXTBOX);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET);
		waitForAndGetElement(ELEMENT_GETTING_STARTED_GADGET);
		waitForAndGetElement(ELEMENT_GETTING_SUGGESTIONS);
	}

	/**
	 * == Check IDE ==
	 * Test case ID: 70870
	 * Step 1: Check IDE page
	 * Step 2: Check showing IDE page
	 */
	@Test
	public void test02_CheckIDE(){
		/*Step 1: Check IDE page*/ 
		//- Login and go to intranet home page
		//- Move mouse over the Setup Menu icon on the top-right, click on IDE
		//There's IDE page
		naviToolbar.goToIDEPage();

		/*Step 2: Check showing IDE page*/ 
		//Check look of IDE page
		/*switch to ckeditor frame*/
		Utils.pause(2000);
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_GADGET_WORKSPACE_FRAME));
		//IDE page is shown correctly, dev-monit workspace is selected as default
		waitForAndGetElement(ELEMENT_IDE_WORKSPACE_DEFAULT);
		driver.switchTo().defaultContent();
	}

	/**
	 * == Install CMIS Expert gadget ==
	 * Test case ID: 70871
	 * Step 1: Open applications page
	 * Step 2: Add gadget
	 * Step 3: Add the gadget to Gadgets category
	 * Step 4: Import application
	 * Step 5: Add page with CMIS
	 * Error: Refer: https://jira.exoplatform.org/browse/EXOGTN-991
	 */
	@Test (groups="error")
	public void test03_InstallCMISExpertGadget(){
		/*Declare variables*/
		String url = "http://www.xcmis.org/CmisExpert4Platform/org.exoplatform.cmis.CmisExpertGadget/org.exoplatform.cmis.client.CmisExpertGadget.gadget.xml";
		String title = "CmisExpertGadget";
		String category = "Administration";
		String pageName = "CMISExpertGadgetPage70871";
		
		/*Step 1: Open applications page*/ 
		//- Login and go to intranet home page
		//- Move mouse over the Setup Menu icon on the top-right and select Applications
		//Application registry form is shown
		naviToolbar.goToApplicationRegistry();

		/*Step 2: Add gadget*/
		//- Click Gadgets and Add a remote gadget. Enter URL given bellow and click Add.
		//http://www.xcmis.org/CmisExpert4Platform/org.exoplatform.cmis.CmisExpertGadget/org.exoplatform.cmis.client.CmisExpertGadget.gadget.xml
		//New Gadget is added
		info("Create new remote gadget");
		magApp.addRemoteGadget(url);
		waitForAndGetElement(magApp.ELEMENT_GADGET_DELETE_ICON.replace("${title}", title));

		/*Step 3: Add the gadget to Gadgets category*/
		//- Click on "Click here to add into categories" 
		//This gadget is added to the Gadget category.
		info("Add gadget to category");
		magApp.addGadgetToCategory(category);

		/*Step 4: Import application*/
		//- Go to Categories and do Import Applications
		//Applications are imported & listed in left pane
		info("Import application");
		magApp.importApplication();

		/*Step 5: Add page with CMIS*/
		//- Go to Edit Page or  Add page
		naviToolbar.goToAddPageManagement();
		//- Choose the path for new page
		//- Input CMISexpert for page name
		//- At step 3 of Add page wizard, drag & drop CMIS Expert gadget portlet into page
		//- Save
		//New page with CMIS is added & well displayed
		info("Add page for portal");
		pagEditor.createNewPageEmptyLayoutWithGadget(pageName,title);
		
		/*Clear data*/
		info("-- Clear data --");
		naviToolbar.goToApplicationRegistry();
		magApp.deleteGadget(title);
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, false, "intranet", true, "Administration");	
	}
}
