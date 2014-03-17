package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Gatein_BasicPortlets_Organization_UserManagement extends PlatformBase{
	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	Button but;
	Dialog dialog;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);
		but = new Button(driver);
		dialog = new Dialog(driver);

		magAc.signIn("john", DATA_PASS); 
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**
	 * Delete specific user from existing user list in User Management
	 * CaseId: 73376
	 * 
	 */
	@Test
	public void test01_DeleteUserFromUserManagement(){
		String username = "funcmanageuser01";
		String password = "123456";
		String firstName = "Func";
		String lastName = "Manage User";
		String displayName = "New User";
		String email = "funcmanageuser01@gmail.com";

		navTool.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);

		info("Delete user");
		navTool.goToUsersAndGroupsManagement();
		user.deleteUser(username);
	}

	/**
	 * Delete specific user from Search Result list in User Management
	 * CaseId: 73540
	 * 
	 */
	@Test
	public void test02_DeleteUserFromSearchResultOfUserManagement(){
		String username = "funcmanageuser02";
		String password = "123456";
		String firstName = "Func";
		String lastName = "Manage User";
		String displayName = "New User";
		String email = "funcmanageuser02@gmail.com";

		navTool.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);

		info("Search user by username");
		navTool.goToUsersAndGroupsManagement();
		user.searchUser(username, "User Name");

		info("Delete user");
		user.deleteUser(username);
	}

	/**
	 * Edit user profile in User Management
	 * CaseId: 73745
	 * 
	 */
	@Test
	public void test03_EditUserProfile(){
		String username = "funcmanageuser03";
		String password = "123456";
		String firstName = "Func";
		String lastName = "Manage User";
		String displayName = "New User";
		String email = "funcmanageuser03@gmail.com";

		String newFirstName = "UserUpdate";
		String newLastName = "ProfileUpdate";
		String newDisplayName = "New User Update";
		String newEmail = "newmail@gmail.com";

		navTool.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(username, password, password, firstName, lastName, displayName, email, null, null, true);

		info("Edit user information");
		navTool.goToUsersAndGroupsManagement();
		user.goToEditUserInfo(username);
		user.editUserInfo_AccountTab(newFirstName, newLastName, newDisplayName, newEmail);
		but.save();
		dialog.closeMessageDialog();
		waitForTextPresent(newFirstName);

		info("Restore data");
		user.deleteUser(username);
	}
}
