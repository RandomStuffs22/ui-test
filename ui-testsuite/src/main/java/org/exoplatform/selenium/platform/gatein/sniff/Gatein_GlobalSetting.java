package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 19 July 2013
 * @author lientm
 *
 */
public class Gatein_GlobalSetting extends PlatformBase {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	Button but;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);
		but = new Button(driver);
		
		magAc.signIn("john", DATA_PASS); 
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68849
	 * Change User Profile
	 */
	@Test
	public void test01_ChangeUserProfile(){
		String username = getRandomString();
		String password = "123456";
		String firstName = "User";
		String lastName = "Profile";
		String displayName = "New User";
		String email = username + "@gmail.com";
		
		String newFirstName = "UserUpdate";
		String newLastName = "ProfileUpdate";
		String newDisplayName = "New User Update";
		String newEmail = "newmail@gmail.com";
		
		navTool.goToNewStaff();
		magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);
		magAc.signOut();
		
		info("Change User Profile");
		magAc.signIn(username, password);
		waitForTextPresent(displayName);
		navTool.goToMySetting();
		magAc.editUserSetting(newFirstName, newLastName, newEmail, newDisplayName, null, null, null);
		magAc.signOut();
		magAc.signIn(username, password);
		waitForTextPresent(newDisplayName);
		magAc.signOut();

		magAc.signIn("john", DATA_PASS);
		navTool.goToUsersAndGroupsManagement();
		user.deleteUser(username);
	}
	
	/**CaseId: 68850
	 * Change Password
	 */
	@Test
	public void test02_ChangePassword(){
		String username = getRandomString();
		String password = "123456";
		String firstName = "User";
		String lastName = "Profile";
		String displayName = "New User";
		String email = username + "@gmail.com";
		
		String newpass = "123456789";
		
		navTool.goToNewStaff();
		magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);
		magAc.signOut();
		
		info("Change password of user");
		magAc.signIn(username, password);
		navTool.goToMySetting();
		magAc.editUserSetting(null, null, null, null, password, newpass, newpass);
		Utils.pause(2000);
		magAc.signOut();
		
		info("Login with new pass");
		magAc.signIn(username, newpass);
		magAc.signOut();

		magAc.signIn("john", DATA_PASS);
		navTool.goToUsersAndGroupsManagement();
		user.deleteUser(username);
	}
	
	/**CaseId: 70613
	 * Change language for another user
	 */
	@Test
	public void test03_ChangeLanguageForAnotherUser(){
		navTool.goToUsersAndGroupsManagement();
		user.goToEditUserInfo("mary");
		info("Change language of user mary to French");
		click(ELEMENT_USER_PROFILE_TAB);
		select(ELEMENT_SELECT_USER_LANGUAGE, "French");
		but.save();
		but.ok();
		magAc.signOut();
		
		info("Check language display with user mary");
		magAc.signIn("mary", DATA_PASS);
		waitForAndGetElement(By.xpath("//*[text()='Accueil']"));
		magAc.signOut();
		
		info("Rollback language for user");
		magAc.signIn("john", DATA_PASS);
		navTool.goToUsersAndGroupsManagement();
		user.goToEditUserInfo("mary");
		click(ELEMENT_USER_PROFILE_TAB);
		select(ELEMENT_SELECT_USER_LANGUAGE, "");
		but.save();
		but.ok();
	}
	
	/**CaseId: 70628
	 * Change language with demo account
	 */
	@Test
	public void test04_ChangeLanguageWithDemoAccount(){
		By ELEMENT_HOME_TEXT = By.xpath("//*[text()='Home']");
		
		info("Check language default when do not set language for user is English");
		waitForAndGetElement(ELEMENT_HOME_TEXT);
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.close();
		
		info("Check displaying language when language of browser don't support by portal with user account demo");
		getDriverSetLanguage(Language.lo);
		magAc = new ManageAccount(driver);
		magAc.signIn("demo", DATA_PASS);
		waitForAndGetElement(ELEMENT_HOME_TEXT);
		
		info("Check when change language to French");
		magAc.changeLanguageForUser("French");
		waitForAndGetElement(By.xpath("//*[text()='Accueil']"));
		
		magAc.changeLanguageForUser("Anglais");
		waitForAndGetElement(ELEMENT_HOME_TEXT);
	}
}
