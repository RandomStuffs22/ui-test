package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SocialBase;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 10/10/2013
 *
 */
public class Social_Space_SpaceManagement_SpaceAccess  extends SocialBase{
	//Platform
	ManageAccount magAcc;
	ManageMember magMember;

	//Space
	SpaceManagement spaceMag;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		spaceMag = new SpaceManagement(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Access Space ==
	 * Test case ID: 67658
	 * Step 1: Access space
	 */
	@Test
	public void test01_AccessSpace(){
		//Declare variable
		String spaceName = "space67658";

		/*Step 1: Access space*/ 
		//- Create new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		//- On Space list, click on space's name or avatar of space
		goToMySpacePage();
		waitForAndGetElement(By.linkText(spaceName));
		click(By.linkText(spaceName));
		//- Show content of space with:
		//+ Focus on home space page
		info("-- Focus on home space page --");
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_CURRENT_MENU_ITEM.replace("${menuItem}", "Activity Stream"));

		//+ All default portlet display: Home space, Discussion, Members, Wiki, Documents, Space settings
		spaceMag.verifySpaceMenu("Activity Stream");
		spaceMag.verifySpaceMenu("Forums");
		spaceMag.verifySpaceMenu("Wiki");
		spaceMag.verifySpaceMenu("Documents");
		spaceMag.verifySpaceMenu("Space Setting");
		spaceMag.verifySpaceMenu("Members");

		/*Clear data*/
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Check access hidden space ==
	 * Test case ID: 68823
	 * Step 1: Check hidden space
	 */
	@Test
	public void test02_CheckAccessHiddenSpace(){
		//Declare variable
		String spaceName = "space68823";

		/*Step 1: Check hidden space*/ 
		//- Login as user1
		//- Add new hidden space
		//- Add new space successfully
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName,"","Hidden","","","");
		String url = driver.getCurrentUrl();

		//- Logout
		//- Login as user2
		magAcc.userSignIn(userType.PUBLISHER);

		//- access the url of an hidden space that she is not member of via url:portal/g/:spaces:hiddenspace/hiddenspace
		Utils.pause(1000);
		driver.get(url);

		//- A page with 'Space not Found' title is displayed
		//Message is :  No space is available at this URL. [Find Spaces]
		info("-- Verify space not found page --");
		waitForAndGetElement(spaceMag.ELEMENT_ACCESS_NOT_FOUND_SPACE_PAGE);
		waitForTextPresent("No space is available at this URL.");
		waitForAndGetElement(By.linkText("Find Spaces"));

		//- Click on [Find Spaces] link
		info("-- Click find space button --");
		click(By.linkText("Find Spaces"));
		
		//- User2 is redirected to Spaces directory page
		waitForTextPresent("Spaces Found");

		/*Clear data*/
		info("-- Clear data --");
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
	
	/**
	 * == Check access visible/close space ==
	 * Test case ID: 68824
	 * Step 1: Check visible/close space
	 */
	@Test
	public void test03_CheckAccessVisibleCloseSpace(){
		//Declare variable
		String spaceName = "space68824";

		/*Step 1: Check visible/close space*/ 
		//- Login as user1
		//- Add new space with visible/closed
		//- Add new space successfully
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName,"","Visible","Close","","");
		String url = driver.getCurrentUrl();

		//- Logout
		//- Login as mary
		magAcc.userSignIn(userType.PUBLISHER);
		Utils.pause(1000);

		// Access the url of an closed space that she is not member of via url :/portal/g/:spaces:closed/closed/forum/
		driver.get(url);

		//A page with 'Access Denied' title is displayed
		//Message is :  You must be invited by an administrator to the Closed space to access this page.
		info("-- Verify Access Denied page --");
		waitForAndGetElement(spaceMag.ELEMENT_ACCESS_DENIED_SPACE_PAGE);
		String verifyText = "You must be invited by an administrator to the "+spaceName+" space to access this page.";
		Assert.assertTrue(waitForAndGetElement(spaceMag.ELEMENT_SPACE_ACCESS_INFO).getText().contains(verifyText));
		
		/*Clear data*/
		info("-- Clear data --");
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
	
	/**
	 * == Check access visible/open space ==
	 * Test case ID: 68822
	 * Step 1: Check visible/open space
	 */
	@Test
	public void test04_CheckAccessVisibleOpenSpace(){
		//Declare variable
		String spaceName = "space68822";

		/*Step 1: Check visible/open space*/ 
		//- Login as user1
		//- Add new space with visible/open
		//- Add new space successfully
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName,"","Visible","Open","","");
		String url = driver.getCurrentUrl();

		//- Logout
		//- Login as mary
		magAcc.userSignIn(userType.PUBLISHER);
		Utils.pause(1000);

		//Access the url of an open space that she is not member of via url : /portal/g/:spaces:open/open/forum/
		driver.get(url);

		//- A page with Restricted Area title is displayed
		//Message is :  You must be a member of the space Open to access this page. [Join]
		info("-- Verify Restricted Area page --");
		waitForAndGetElement(spaceMag.ELEMENT_RESTRICT_SPACE_PAGE);
		String verifyText = "You must be a member of the space "+spaceName+" to view this page.";
		Assert.assertTrue(waitForAndGetElement(spaceMag.ELEMENT_SPACE_ACCESS_INFO).getText().contains(verifyText));

		//Click on Join link
		info("-- Click Join button --");
		click(By.linkText("Join"));
		
		//- Mary joins the space and is redirected to the initially  requested page.
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_CURRENT_MENU_ITEM.replace("${menuItem}", "Activity Stream"));

		/*Clear data*/
		info("-- Clear data --");
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
	
	/**
	 * == Check access visible/validation space ==
	 * Test case ID: 68825
	 * Step 1: Check visible/validation space
	 */
	@Test
	public void test05_CheckAccessVisibleValidationSpace(){
		//Declare variable
		String spaceName = "space68825";

		/*Step 1: Check visible/open space*/ 
		//- Login as user1
		//- Add new space with visible/validation
		//- Add new space successfully
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName,"","Visible","Validation","","");
		String url = driver.getCurrentUrl();

		//- Logout
		//- Login as mary
		magAcc.userSignIn(userType.PUBLISHER);

		//access the url of a space requiring validation that she is not member of via url :/portal/g/:spaces:validation/validation/forum/
		Utils.pause(1000);
		driver.get(url);

		//A page with Restricted Area title is displayed
		//Message is :   You must be a member of the space Validation to access this page. [Request to Join]
		info("-- Verify Restricted Area page --");
		waitForAndGetElement(spaceMag.ELEMENT_RESTRICT_SPACE_PAGE);
		String verifyText = "You must be a member of the space "+spaceName+" to view this page.";
		Assert.assertTrue(waitForAndGetElement(spaceMag.ELEMENT_SPACE_ACCESS_INFO).getText().contains(verifyText));

		//Click on [Request to Join] link
		info("-- Click on [Request to Join] link --");
		click(By.linkText("Request to Join"));
		
		//Restricted Area page remains
		verifyText = "Your request to join "+spaceName+" has been sent";
		Assert.assertTrue(waitForAndGetElement(spaceMag.ELEMENT_SPACE_ACCESS_ALERT_SUCCESS).getText().contains(verifyText));

		/*Clear data*/
		info("-- Clear data --");
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
}
