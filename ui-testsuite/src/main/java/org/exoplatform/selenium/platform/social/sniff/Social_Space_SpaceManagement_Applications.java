package org.exoplatform.selenium.platform.social.sniff;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ApplicationManagement;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SocialBase;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 09/10/2013
 *
 */
public class Social_Space_SpaceManagement_Applications extends SocialBase{
	//Platform
	ManageAccount magAcc;
	ManageMember magMember;

	//Space
	SpaceManagement spaceMag;
	ApplicationManagement appMag;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		spaceMag = new SpaceManagement(driver);
		appMag = new ApplicationManagement(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**
	 * == Add application on Space ==
	 * Test case ID: 67663
	 * Step 1: Add application
	 * Note: need to update expected outcome (Will verify header menu instead of right menu)
	 */
	@Test
	public void test01_AddApplicationOnSpace(){
		//Declare variable
		String spaceName = "space67663";
		String categoryName = "Adoption";
		String applicationTitle = "Forum Statistics";

		//- Create new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		
		/*Step 1: Add application*/ 
		//- Access Space, select Setting tab/Application
		//- Click on Add Application, select application and click on "Install" icon to add
		//- Application is added on space. Name of application is display on right of space menu portlet
		spaceMag.goToSpaceMenu("Space Settings");
		appMag.addApplication(categoryName, applicationTitle);
		spaceMag.goToSpaceMenu(applicationTitle);
		
		/*Clear data*/
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
	
	/**
	 * == Remove application on space ==
	 * Test case ID: 67901
	 * Step 1: Remove application on space
	 */
	@Test
	public void test02_RemoveApplicationOnSpace(){
		//Declare variable
		String spaceName = "space67901";
		String categoryName = "Adoption";
		String applicationTitle = "Forum Statistics";

		//- Create new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		
		/*Step 1: Remove application on space*/ 
		//- Access Space, select Setting tab/Application
		//- Click on Add Application, select application and click on "Install"icon to add
		spaceMag.goToSpaceMenu("Space Settings");
		appMag.addApplication(categoryName, applicationTitle);
		
		//- Click on remove icon beside application
		//- Application is move out space, name of one is not display on left menu
		appMag.removeApplication(applicationTitle);
		
		/*Clear data*/
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
}
