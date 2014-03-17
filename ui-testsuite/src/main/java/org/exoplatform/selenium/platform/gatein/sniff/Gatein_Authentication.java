package org.exoplatform.selenium.platform.gatein.sniff;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date July 2013
 * @author lientm
 */
public class Gatein_Authentication extends PlatformBase {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);
		
		magAc.signIn("john", DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68894
	 * Sign In /Sign Out
	 */
	@Test
	public void test01_SignIn_SignOut(){ 
		//signin -> execute in setUpBeforeTest
		//signout -> execute in afterTest
	}
	
	/**CaseId: 68895
	 * Register new account in public mode -> N/A in plf4
	 */

	/**CaseId: 68897
	 * Remember my login
	 */
	@Test (groups = "pending")
	public void test02_RememberMyLogin(){
		FirefoxProfile firefoxProfile = new ProfilesIni().getProfile("default"); 
		FirefoxDriver driver = new FirefoxDriver(firefoxProfile); 
		
		driver.get(DEFAULT_BASEURL);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS);
		Utils.pause(2000);
		driver.close();
		
		Utils.pause(5000);
		FirefoxProfile firefoxProfile1 = new ProfilesIni().getProfile("default"); 
		//driver.close();

		FirefoxDriver driver1 = new FirefoxDriver(firefoxProfile1); 
		driver1.get(DEFAULT_BASEURL);
		//driver.get(baseUrl);		
	}
	
	/**CaseId: 68899
	 * Create new account in private mode
	 */
	@Test
	public void test03_CreateNewAccountInPrivateMode(){
		String username = "gateinsniff";
		String password = "123456";
		String firstName = "gatein";
		String lastName = "sniff";
		String email = "gatein@gmail.com";
		
		navTool.goToNewStaff();
		magAc.addNewUserAccount(username, password, password, firstName, lastName, null, email, null, null, true);
		magAc.signOut();
		
		magAc.signIn(username, password);
		waitForTextPresent(firstName + " " + lastName);
		magAc.signOut();
		
		magAc.signIn("john", DATA_PASS);
		navTool.goToUsersAndGroupsManagement();
		user.deleteUser(username);
	}
	
}
