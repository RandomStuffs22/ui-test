package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 26/09/2013
 *
 */
public class Social_Dashboard extends DashBoard {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	HomePageActivity activity;
	ManageApplications app;

	//Social

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		activity = new HomePageActivity(driver);
		app = new ManageApplications(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Drag & drop gadget in Dashboard ==
	 * Test case ID: 67669
	 * Step 1: Drag & drop gadget in Dashboard
	 */
	@Test
	public void test01_DragDropGadgetInDashboard(){
		//Declare variable
		String gadgetName = "My Spaces Gadget";

		/*Step 1: Drag & drop gadget in Dashboard*/ 
		//- Click on Dashboard on Admin bar
		//- Dashboard page is displayed  with a default tab
		navToolBar.goToDashboard();

		//- Click on Add Gadgets
		//- Drag & drop the gadget which you want to add ( calendar, todo, calculator)
		//- Gadgets pop up is shown
		//- New gadget is added into dashboard
		dragDropGadget(gadgetName);
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);

		/*Clear data*/
		info("clear data");
		actionOnGadgetOnDashboard(gadgetName, "Delete Gadget");
	}

	/**
	 * == Share link in RSS fetcher ==
	 * Test case ID: 67681
	 * Step 1: Share link in RSS fetcher
	 */	
	@Test
	public void test02_ShareLinkInRSSFetcher(){
		//Declare variable
		String gadgetName = "Social Rss Reader";
		String newUrl = "http://feeds.feedburner.com/eXo-Platform";

		/*Step 1: Share link in RSS fetcher*/ 
		//Import all applications (gadget)
		navToolBar.goToApplicationRegistry();
		app.importApplication();

		//- Access dashboard
		navToolBar.goToDashboard();

		//- Drag/drop Social Rss Reader
		dragDropGadget(gadgetName);
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);
		
		//Edit url link of gadget
		editRSSReaderGadgetonDashboard(gadgetName,newUrl);

		//- Select a summary of News
		//- Click on Share Link
		actionOnGadgetOnDashboard(gadgetName,"Maximize");
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_DASHBOARD_FRAME));
		Utils.pause(1000);
		click(By.xpath("//*[@id='link_0']/a[text()='Share']"));
		Utils.pause(1000);

		//- Show message to user known links was shared
		waitForTextPresent("Successfully shared.");
		
		//- Shared link is displayed on User's activity
		String linkText = waitForAndGetElement(By.id("link_title_0")).getText();
		switchToParentWindow();
		navToolBar.goToHomePage();
		waitForAndGetElement(By.linkText(linkText));

		/*Clear data*/
		info("clear data");
		activity.deleteActivity("Shared a link");
		navToolBar.goToDashboard();
		actionOnGadgetOnDashboard(gadgetName, "Delete Gadget");
	}

	/**
	 * == View a link in RSS fetcher ==
	 * Test case ID: 67889
	 * Step 1: View a link in RSS fetcher
	 * Note: Default: RSS reader is not displayed. 
	 * To import this gadget, The user need to import all gadget by: Administration -> Application -> Import Applications
	 * Pending: Can't open many tab on same browser on selenium webdriver
	 * Refer: https://jira.exoplatform.org/browse/FQA-1282
	 */	
	@Test 
	public void test03_ViewALinkInRSSFetcher(){
		//Declare variable
		String gadgetName = "Social Rss Reader";
		String newUrl = "http://feeds.feedburner.com/eXo-Platform";

		/*Step 1: Share link in RSS fetcher*/ 
		//Import all applications (gadget)
		navToolBar.goToApplicationRegistry();
		app.importApplication();
		
		//- Access dashboard
		navToolBar.goToDashboard();

		//- Drag/drop Social Rss Reader
		dragDropGadget(gadgetName);
		click(ELEMENT_CLOSE_ADD_GADGET_WINDOW);
		
		//Edit url link of gadget
		editRSSReaderGadgetonDashboard(gadgetName,newUrl);
		
		//- Click on icon of maximize view
		//- Show content of gadget at maximize
		actionOnGadgetOnDashboard(gadgetName,"Maximize");

		//- Select a link on list in gadget and click View link
		driver.switchTo().frame(waitForAndGetElement(ELEMENT_DASHBOARD_FRAME));
		Utils.pause(1000);
		if(waitForAndGetElement(By.xpath("//*[@id='link_0']/a[text()='view link »']"),5000,0)==null){
			click(By.xpath("//*[@id='link_0']/a[text()='view link']"));
		}
		else
			click(By.xpath("//*[@id='link_0']/a[text()='view link »']"));
		Utils.pause(1000);

		//- This link is displayed on other tab of browser
		//Note: 
		switchToNewWindow();
		waitForTextPresent("Company News");
		driver.close();
		
		/*Clear data*/
		info("clear data");
		switchToParentWindow();
		actionOnGadgetOnDashboard(gadgetName, "Delete Gadget");
	}
}
