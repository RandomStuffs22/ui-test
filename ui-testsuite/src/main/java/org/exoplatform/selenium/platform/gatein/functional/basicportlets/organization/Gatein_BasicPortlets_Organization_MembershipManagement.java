package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization;

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

public class Gatein_BasicPortlets_Organization_MembershipManagement extends PlatformBase {
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

		magAc.signIn("fqa", DATA_PASS); 
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Edit Membership for user from existing group
	 * CaseId: 73382
	 * 
	 */
	@Test
	public void test01_EditUserMembershipinGroup(){
		String groupName = "group01";
		String groupLabel = "Group Label 01";
		String groupDesc = "create new group";

		String newUser = "testa";
		String newMembership = "member";
		String EditMembership = "manager";
		String email = "helloworld@gmail.com";
		String password = DATA_PASS;

		navTool.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(newUser, password, password, newUser, newUser, newUser, email, null, null, true);

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add new user into group");
		user.addUsersToGroup(newUser, newMembership, true, true);

		info("Edit user membership in group");
		user.editMembershipInGroup(newUser, EditMembership);

		info("Restore data");
		user.deleteGroup(groupName, true);
		user.chooseUserTab();
		user.deleteUser(newUser);
	}

	/**
	 * Edit Membership in Organization  Management form by  Administrator
	 * CaseId: 73386
	 * 
	 */
	@Test
	public void test02_EditUserMembershipinMembershipManagement(){
		String Membership = "member";
		String EditMembership = "limited access right";

		info("Go to Membership Management");
		navTool.goToUsersAndGroupsManagement();
		user.chooseMembershipTab();

		info("Edit user membership in Membership Management tab");
		user.editMembership(Membership, EditMembership);
	}

	/**
	 * Check existing of user in group after his role in that group was deleted
	 * CaseId: 73662
	 * 
	 */
	@Test
	public void test03_DeleteUserinGroup(){
		String groupName = "group03";
		String groupLabel = "Group Label 03";
		String groupDesc = "create new group";

		String newUser = "testb";
		String membership = "member";
		String email = "helloworld@gmail.com";
		String password = DATA_PASS;

		navTool.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(newUser, password, password, newUser, newUser, newUser, email, null, null, true);

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add some user into group");
		user.addUsersToGroup(newUser, membership, true, true);

		info("Edit user membership in group");
		user.deleteUserInGroup(groupName, groupLabel, newUser);

		info("Restore data");
		user.deleteGroup(groupName, true);
		Utils.pause(3000);
		user.chooseUserTab();
		Utils.pause(3000);
		user.deleteUser(newUser);
	}

	/**
	 * Check membership information of user after role of him in a group was deleted
	 * CaseId: 73709
	 * 
	 */
	@Test
	public void test04_CheckUserMembershipinGroupAfterDeleteMembership(){
		String groupName = "Group04";
		String groupLabel = "Group Label 04";
		String groupDesc = "create new group";

		String newUser = "testc";
		String membership = "test";
		String membershipDesc = "test";
		String email = "helloworld@gmail.com";
		String password = DATA_PASS;

		navTool.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(newUser, password, password, newUser, newUser, newUser, email, null, null, true);

		navTool.goToUsersAndGroupsManagement();
		info("Add new membership");
		user.addMembership(membership, membershipDesc, true);

		info("Add new group");
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add new user into group");
		user.addUsersToGroup(newUser, membership, true, true);

		info("Delete new membership");
		user.chooseMembershipTab();
		user.deleteMembership(membership, true);

		info("Confirm membership information of new user is blank in group management");
		user.chooseGroupTab();
		//user.selectGroup(groupName);
		waitForElementNotPresent(user.ELEMENT_GROUP_MANAGEMENT_TAB_MEMBERSHIP.replace("${username}",newUser).replace("${membership}", membership));

		info("Restore data");
		user.deleteGroup(groupName, true);
		Utils.pause(2000);
		user.chooseUserTab();
		Utils.pause(2000);
		user.deleteUser(newUser);
	}

	/**
	 * Delete membership which is mandatory
	 * CaseId: 73750
	 * 
	 */
	@Test
	public void test05_DeleteMembershipWhichisMandatory(){
		String mandatoryMembership = "*";

		info("Go to Membership Management");
		navTool.goToUsersAndGroupsManagement();
		user.chooseMembershipTab();

		info("Delete mandatory membership of PLF");
		user.deleteMembership(mandatoryMembership, false);
		waitForAndGetElement(user.ELEMENT_MEMBERSHIP_MANAGEMENT_TAB_FAIL_DEL_MSG);
		dialog.closeMessageDialog();
	}

}
