package org.exoplatform.selenium.platform.gatein.functional.registration;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author havtt
 *
 */

public class Gatein_Registration_PrivateMode extends PlatformBase {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	Dialog dialog;
	Button button;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);
		dialog = new Dialog(driver);
		button = new Button(driver);

		magAc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Create new account with valid value
	 * CaseId: 73343
	 */
	 @Test
	 public void test01_createNewValidAccount(){
		String username = getRandomString();
		String password = "123456";
		String firstName = "Func";
		String lastName = "Manage User";
		String displayName = "New User";
		String email = username+"@gmail.com";

		info("Go to User Register");
		driver.navigate().refresh();
		navTool.goToNewStaff();

		info("Create new user");
		magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);

		info("Restore data");
		navTool.goToUsersAndGroupsManagement();
		user.chooseUserTab();
		user.deleteUser(username);
	 }

	 /**
	  * Create new account with user name the same with existing but different by lower/upper case
	  * CaseId: 73520
	  */
	 @Test
	 public void test02_createSameAccountButUpperLetter(){

		 String username = getRandomString();
		 String sameusername = username.toUpperCase();
		 String password = "123456";
		 String firstName = "Func";
		 String lastName = "Manage User";
		 String displayName = "New User";
		 String email = username+"@gmail.com";

		 info("Go to User Register");
		 driver.navigate().refresh();
		 navTool.goToNewStaff();

		 info("Create new user");
		 magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);

		 info("Create the same user account but upper case");
		 magAc.addNewUserAccount(sameusername, password, password, firstName, lastName, displayName, email, null, null, false);
		 waitForAndGetElement(USER_REG_UPPER_LETTER_FAIL_MSG);
		 dialog.closeMessageDialog();

		 info("Restore data");
		 navTool.goToUsersAndGroupsManagement();
		 user.chooseUserTab();
		 user.deleteUser(username);
	 }

	 /**
	  * Create new account with User name the same with existing
	  * CaseId: 73774
	  */
	 @Test
	 public void test03_createSameAccountWithExistingOne(){
		 String username = getRandomString();
		 String password = "123456";
		 String firstName = "Func";
		 String lastName = "Manage User";
		 String displayName = "New User";
		 String email = username+"@gmail.com";

		 info("Go to User Register");
		 driver.navigate().refresh();
		 navTool.goToNewStaff();

		 info("Create new user");
		 magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);

		 info("Create new user having the same acc with existing one");
		 magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, false);
		 waitForAndGetElement(USER_REG_SAME_ACC_FAIL_MSG);
		 dialog.closeMessageDialog();

		 info("Restore data");
		 navTool.goToUsersAndGroupsManagement();
		 user.chooseUserTab();
		 user.deleteUser(username);
	 }

	 /**
	  * Create new account when copy from Password and paste to Confirm Password field.
	  * CaseId: 73874
	  */
	 @Test
	 public void test04_copyPastePassword(){
		 String username = getRandomString();
		 String password = "123456";
		 String firstName = "Func";
		 String lastName = "Manage User";
		 String displayName = "New User";
		 String email = username+"@gmail.com";

		 info("Go to User Register");
		 navTool.goToNewStaff();

		 info("Fill valid data for registering new user");
		 type(ELEMENT_INPUT_USERNAME, username, true);
		 type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		 type(ELEMENT_INPUT_LASTNAME, lastName, true);
		 type(ELEMENT_INPUT_DISPLAY_NAME, displayName, true);
		 type(ELEMENT_INPUT_EMAIL, email, true);

		 info("Copy paste password");
		 copyPasteString(ELEMENT_INPUT_PASSWORD, ELEMENT_INPUT_CONFIRM_PASSWORD, password);
		 Utils.pause(3000);

		 info("Confirm copy-paste action is denied");
		 button.save();
		 waitForAndGetElement(USER_REG_COPYPASTE_PASSWORD_FAIL_MSG);
		 dialog.closeMessageDialog();
	 }

	 /**
	  * Create new account with invalid format Email address
	  * CaseId: 74052
	  */
	 @Test
	 public void test05_invalidMailAddress(){
		 String username = getRandomString();
		 String password = "123456";
		 String firstName = "Func";
		 String lastName = "Manage User";
		 String displayName = "New User";
		 String email = username+"@";

		 info("Go to User Register");
		 navTool.goToNewStaff();

		 info("Fill valid data for registering new user");
		 type(ELEMENT_INPUT_USERNAME, username, true);
		 type(ELEMENT_INPUT_PASSWORD, password, true);
		 type(ELEMENT_INPUT_CONFIRM_PASSWORD, password, true);
		 type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		 type(ELEMENT_INPUT_LASTNAME, lastName, true);
		 type(ELEMENT_INPUT_DISPLAY_NAME, displayName, true);

		 info("Fill invalid email address");
		 type(ELEMENT_INPUT_EMAIL, email, true);

		 info("Confirm invliad e-mail address");
		 button.save();
		 waitForAndGetElement(USER_REG_INVALID_EMAIL_FAIL_MSG);
		 dialog.closeMessageDialog();
	 }
}