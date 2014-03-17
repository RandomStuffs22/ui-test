package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.social.ApplicationManagement;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 04/11/2013
 *
 */
public class PLF_Navigation_SpaceNavigation extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	SpaceManagement spaceMag;
	ApplicationManagement appMag;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver, this.plfVersion);
		spaceMag = new SpaceManagement(driver, this.plfVersion);

		appMag = new ApplicationManagement(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Show the space menu ==
	 * Test case ID: 74379
	 * Step 1: Show space applications
	 */
	@Test
	public void test01_ShowTheSpaceMenu(){
		/*Declare variables*/
		String spaceName = "space74379";
		
		/*Create data*/
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName, "");
		
		/*Step 1: Show space applications*/ 
		//- Connect to Intranet
		//- Open a Space
		spaceMag.goToMySpacePage();
		click(spaceMag.ELEMENT_SPACE_TITLE.replace("${spaceName}", spaceName));
		
		//- The Horizontal toolbar is displayed
		//- On the left of the Space toolbar, we display the icon and name of the current space.
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_CURRENT_AVATAR.replace("${spaceName}", spaceName));
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_CURRENT_NAME.replace("${spaceName}", spaceName));
		
		//- All applications dedicated to the space are shown on space menu
		//- Click on each applications, the application will show up in the main page
		spaceMag.goToSpaceMenu("Activity Stream");
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_ACTIVITY_STREAM_PORTLET);
		spaceMag.goToSpaceMenu("Forums");
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_FORUM_PORTLET);
		spaceMag.goToSpaceMenu("Wiki");
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_WIKI_PORTLET);
		spaceMag.goToSpaceMenu("Documents");
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_DOCUMENTS_PORTLET);
		spaceMag.goToSpaceMenu("Space Setting");
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_SETTING_PORTLET);
		spaceMag.goToSpaceMenu("Members");
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_MEMBER_PORTLET);

		/*Clear data*/
		info("-- Clear data --");
		spaceMag.goToAllSpaces();
		spaceMag.deleteSpace(spaceName,300000);
	}
	
	/**
	 * == Remove application of space's toolbar ==
	 * Test case ID: 74380
	 * Step 1: Connect to intranet
	 * Step 2: Remove an application
	 */
	@Test
	public void test02_RemoveApplicationOfSpaceToolbar(){
		/*Declare variables*/
		String spaceName = "space74380";
		
		/*Create data*/
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName, "");
		
		/*Step 1: Show space applications*/ 
		//- Login as a normal user
		//- Connect to Intranet
		//- Open a Space
		spaceMag.goToMySpacePage();
		click(spaceMag.ELEMENT_SPACE_TITLE.replace("${spaceName}", spaceName));
		
		//- The Horizontal toolbar is displayed
		//- The list of applications of space are displayed
		spaceMag.verifySpaceMenu("Activity Stream");
		spaceMag.verifySpaceMenu("Forums");
		spaceMag.verifySpaceMenu("Wiki");
		spaceMag.verifySpaceMenu("Documents");
		spaceMag.verifySpaceMenu("Space Setting");
		spaceMag.verifySpaceMenu("Members");
		
		/*Step 2: Remove an application*/ 
		//- From space's settings, remove an application
		spaceMag.goToSpaceMenu("Space Setting");
		
		//- The application is removed from the space's toolbar
		appMag.removeApplication("Members");
		
		/*Clear data*/
		info("-- Clear data --");
		spaceMag.goToAllSpaces();
		spaceMag.deleteSpace(spaceName,300000);
	}

}
