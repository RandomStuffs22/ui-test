package org.exoplatform.selenium.platform.gatein.sniff;

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

/**
 * @date 29 July 2013
 * @author lientm
 */
public class Gatein_Manage_UserAndGroup extends PlatformBase{
	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	Button but;
	Dialog dialog;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver, this.plfVersion);
		navTool = new NavigationToolbar(driver, this.plfVersion);
		user = new UserGroupManagement(driver, this.plfVersion);
		but = new Button(driver, this.plfVersion);
		dialog = new Dialog(driver);
		
		magAc.signIn("john", DATA_PASS); 
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 70418 + 68901 + 68900 -> Edit info, search and Delete user
	 * 
	 */
	@Test
	public void test01_EditSearchDeleteUser(){
		String username = getRandomString();
		String password = username;
		String firstName = username;
		String lastName = username;
		String displayName = username;
		String email = username+"@gmail.com";
		
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
		
		info("Search user follow username, last name, first name, email");
		user.searchUser("john", "User Name");
		user.searchUser("Davis", "Last Name");
		user.searchUser("Mary", "First Name");
		user.searchUser(newEmail, "Email");
		
		info("Delete user");
		user.deleteUser(username);
	}
	
	/**CaseId: 68902 + 70412 + 68903 + 70414 -> Add, edit group, add users to group, delete group
	 * 
	 */
	@Test
	public void test02_AddEditDeleteGroup(){
		String groupName = "Gateinsniffgroup01";
		String groupLabel = "Group Label 01";
		String groupDesc = "create new group";
		
		String newLabel = "Group Label 01 update";
		String newDesc = "create new group update";
		
		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);
		
		info("Edit group");
		user.editGroup(groupLabel, null, newLabel, newDesc, true);
		
		info("Add some user into group");
		user.addUsersToGroup("mary", "member", true, true);
		user.addUsersToGroup("demo", "manager", false, true);
		
		info("Delete group");
		user.deleteGroup(groupLabel, true);
	}
	
	/**CaseId: 68904 + 70415 + 70416 -> Add, edit, delete membership
	 * 
	 */
	@Test
	public void test03_AddEditDeleteMembership(){
		String membershipName = "Membership03";
		String membershipDesc = "Add new membership";
		String newDesc = "Add new membership update";
		
		info("Add new membership");
		navTool.goToUsersAndGroupsManagement();
		user.addMembership(membershipName, membershipDesc, true);
		
		info("Edit membership");
		user.editMembership(membershipName, newDesc);
		
		info("Delete membership");
		user.deleteMembership(membershipName, true);
	}
}
