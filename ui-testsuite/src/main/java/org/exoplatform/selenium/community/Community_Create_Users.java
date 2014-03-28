package org.exoplatform.selenium.community;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
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
 * @author thuntn
 *
 */
public class Community_Create_Users extends PlatformBase{

	ManageAccount magAccount;
	NavigationToolbar navBar;
	UserGroupManagement userGroup;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		magAccount = new ManageAccount(driver,this.plfVersion);
		navBar = new NavigationToolbar(driver);
		userGroup = new UserGroupManagement(driver);
		button = new Button(driver, plfVersion);
		info("== Creating users to eXo Cloud ==");
	}

	@AfterMethod
	public void afterMethods() {
		info("== Creating users: successful ==");
		driver.quit();
	}

	@Test
	public void test01_CreateUser(){

		info("== Signin to Community with user fqa");
		magAccount.signIn("fqa", "gtngtn");
		navBar.goToNewStaff();                
		//Publisher
		magAccount.addNewUserAccount("mary", DATA_PASS, DATA_PASS, "Mary", "Williams", "", "mary.williams@acme.exoplatform.com", "", "", true);
		//Admin
		magAccount.addNewUserAccount("john", DATA_PASS, DATA_PASS, "John", "Smith", "", "john.smith@acme.exoplatform.com", "", "", true);
		//Redactor
		magAccount.addNewUserAccount("james", DATA_PASS, DATA_PASS, "James", "Davis", "", "james.davis@acme.exoplatform.com", "", "", true);
		//Developer
		magAccount.addNewUserAccount("demo", DATA_PASS, DATA_PASS, "Jack", "Miller", "", "jack.miller@acme.exoplatform.com", "", "", true);

		//Group Management
		navBar.goToUsersAndGroupsManagement();
		userGroup.chooseGroupTab();
		//Admin
		userGroup.selectGroup("Platform/Administration", true);
		userGroup.addUsersToGroup("john", "*", false, false);
		userGroup.addUsersToGroup("john", "manager", false, false);
		click(ELEMENT_UP_LEVEL);

		//platform/users
		userGroup.selectGroup("Platform/Users", true);
		userGroup.addUsersToGroup("john", "*", false, false);
		click(ELEMENT_UP_LEVEL);

		//Publisher
		userGroup.selectGroup("Platform/Content Management", true);
		userGroup.addUsersToGroup("john", "*", false, true);
		userGroup.addUsersToGroup("mary", "manager", false, true);
		userGroup.addUsersToGroup("mary", "editor", false, true);
		userGroup.addUsersToGroup("james", "author", false, false);
		userGroup.addUsersToGroup("james", "redactor", false, false);

		click(ELEMENT_UP_LEVEL);
		//Organization/Employees
		userGroup.selectGroup("Organization/Employees", true);
		userGroup.addUsersToGroup("john", "*", false, false);
		userGroup.addUsersToGroup("mary", "member", true, true);
		userGroup.addUsersToGroup("james", "member", true, true);
		userGroup.addUsersToGroup("demo", "member", true, true);

		click(ELEMENT_UP_LEVEL);
		//Organization/Management/Executive Board
		userGroup.selectGroup("Organization/Management/Executive Board", true);
		userGroup.addUsersToGroup("john", "*", true, true);

		click(ELEMENT_UP_LEVEL);
		waitForAndGetElement(userGroup.ELEMENT_GROUP_PERMISSION.replace("${groupName}", "Management"));
		click(ELEMENT_UP_LEVEL);
		waitForAndGetElement(userGroup.ELEMENT_GROUP_PERMISSION.replace("${groupName}", "Development"));

		//Developer
		userGroup.selectGroup("Development", true);
		userGroup.addUsersToGroup("demo", "member", true, true);
		userGroup.addUsersToGroup("john", "member", true, true);
	}

	@Test(priority=0)
	public void test02_UpdateUserAccount(){

		String userEditIcon;
		info("== Signin to Community with user fqa");
		magAccount.signIn("fqa", "gtngtn");

		navBar.goToUsersAndGroupsManagement();                
		info("Change password of users");
		info("--Editing user john ");
		userEditIcon = ELEMENT_USER_EDIT_ICON.replace("${username}", "john");
		userGroup.searchUser("john@localhost", "Email");
		click(userEditIcon);
		Utils.pause(1000);
		userGroup.editUserInfo_AccountTab("John", "Smith", "John Smith", "john.smith@acme.exoplatform.com", "gtngtn");
		button.save();
		waitForMessage(userGroup.MSG_UPDATE_USER_ACCOUNT);
		button.ok();


		userEditIcon = ELEMENT_USER_EDIT_ICON.replace("${username}", "mary");
		userGroup.searchUser("mary@localhost", "Email");
		click(userEditIcon);
		Utils.pause(1000);
		userGroup.editUserInfo_AccountTab("Mary", "Williams", "Mary Williams", "mary.williams@acme.exoplatform.com", "gtngtn");
		button.save();
		waitForMessage(userGroup.MSG_UPDATE_USER_ACCOUNT);
		button.ok();


		//		userGroup.goToEditUserInfo("james");
		//		userGroup.editUserInfo_AccountTab("James", "Davis", "James Davis", "james.davis@acme.exoplatform.com", "gtngtn");
		//		button.save();
		//		waitForMessage(userGroup.MSG_UPDATE_USER_ACCOUNT);
		//		button.ok();


		userEditIcon = ELEMENT_USER_EDIT_ICON.replace("${username}", "demo");
		userGroup.searchUser("demo@localhost", "Email");
		click(userEditIcon);
		Utils.pause(1000);
		userGroup.editUserInfo_AccountTab("Jack", "Miller", "Jack Miller", "jack.miller@acme.exoplatform.com", "gtngtn");
		button.save();
		waitForMessage(userGroup.MSG_UPDATE_USER_ACCOUNT);
		button.ok();


		userEditIcon = ELEMENT_USER_EDIT_ICON.replace("${username}", "root");
		userGroup.searchUser("admin@exoplatform.com", "Email");
		click(userEditIcon);
		Utils.pause(1000);
		userGroup.editUserInfo_AccountTab("Root", "Root", "Root Root", "root.root@acme.exoplatform.com", "gtngtn");
		button.save();
		waitForMessage(userGroup.MSG_UPDATE_USER_ACCOUNT);
		button.ok();

		navBar.goToNewStaff(); 
		magAccount.addNewUserAccount("james", DATA_PASS, DATA_PASS, "James", "Davis", "", "james.davis@acme.exoplatform.com", null, null, true);
	}

	@Test(priority=1)
	public void test03_UpdateMembership(){
		info("== Signin to Community with user root");
		magAccount.signIn("fqa", "gtngtn");
		navBar.goToUsersAndGroupsManagement(); 
		userGroup.chooseGroupTab();
		userGroup.selectGroup("Platform/Administrators", true);
		if(!userGroup.checkUserInGroup("john", "*")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("john", "*", false, false);
		}

		click(ELEMENT_UP_LEVEL);
		userGroup.selectGroup("Platform/Web Contributors", true);
		if(!userGroup.checkUserInGroup("john", "*")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("john", "*", false, false);
		}
		if(!userGroup.checkUserInGroup("mary", "manager")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("mary", "manager", false, false);
		}

		if(!userGroup.checkUserInGroup("mary", "editor")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("mary", "editor", false, false);
		}
		if(!userGroup.checkUserInGroup("james", "author")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("james", "author", false, false);
		}

		if(!userGroup.checkUserInGroup("james", "redactor")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("james", "redactor", false, false);
		}
		click(ELEMENT_UP_LEVEL);
		userGroup.selectGroup("Organization/Employees", true);

		if(!userGroup.checkUserInGroup("john", "*")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("john", "*", false, false);
		}

		if(!userGroup.checkUserInGroup("mary", "member")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("mary", "member", false, false);
		}

		if(!userGroup.checkUserInGroup("james", "member")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("james", "member", false, false);
		}

		if(!userGroup.checkUserInGroup("demo", "member")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("demo", "member", false, false);
		}

		click(ELEMENT_UP_LEVEL);
		//Organization/Management/Executive Board
		userGroup.selectGroup("Organization/Management/Executive Board", true);
		if(!userGroup.checkUserInGroup("john", "*")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("john", "*", false, false);
		}

		click(ELEMENT_UP_LEVEL);
		waitForAndGetElement(userGroup.ELEMENT_GROUP_PERMISSION.replace("${groupName}", "Management"));
		click(ELEMENT_UP_LEVEL);
		waitForAndGetElement(userGroup.ELEMENT_GROUP_PERMISSION.replace("${groupName}", "Development"));

		userGroup.selectGroup("Development", true);
		if(!userGroup.checkUserInGroup("demo", "member")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("demo", "member", false, false);
		}

		if(!userGroup.checkUserInGroup("john", "member")){
			userGroup.goToFirstPage();
			userGroup.addUsersToGroup("john", "member", false, false);
		}

	}
}