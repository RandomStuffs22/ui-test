package org.exoplatform.selenium.platform;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.exoplatform.selenium.Utils;
import  org.exoplatform.selenium.platform.ManageAccount;
import  org.exoplatform.selenium.platform.NavigationToolbar;
import  org.exoplatform.selenium.platform.UserGroupManagement;

/**
 * 
 * @author vuna2
 *
 */
public class AccountTest extends NavigationToolbar{
	
	public AccountTest(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	ManageAccount magAcc = new ManageAccount(driver);
	UserGroupManagement userGroup = new UserGroupManagement(driver);
	
	String username      = "testaccountexosea" ; 
	String password		 = "testaccount"	; 
	String confirmPassword = "testaccount"	; 
	String firstName     = "first"			; 
    String lastName      = "last name"		; 
    String email         = "exotestaccount@exo.com"; 
    String userNameGiven = ""		; 
    String language      = "English"; 
    boolean verify       = true     ;
    
    String searchOption = "User Name";
	
	@BeforeTest(groups = {"platform"})
	public void beforeTest()  {
		driver = new FirefoxDriver();
    	baseUrl = "http://localhost:8080";
    	driver.get(baseUrl + "/portal/intranet/");
    	Utils.pause(3000);
		
	}

	@AfterTest(groups = {"platform"})
	public void afterTest() {
		driver.quit();
	}
	
	@Test()
	public void testAccountAddNewUser(){
		System.out.println("--Create new user account--");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(2000);
		goToNewStaff();
		Utils.pause(2000);
//		magAcc.addNewUserAccount(username,password,confirmPassword,firstName,
//				lastName,email,userNameGiven,language,verify);
		Utils.pause(3000);
		magAcc.signOut()  ;
		Utils.pause(1000);
		
	}
	
	@Test()
	public void testSearchUser(){
		System.out.println("--Search user --");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(1000);
		goToUsersAndGroupsManagement();
		Utils.pause(1000);
		userGroup.searchUser(username, searchOption);
		Utils.pause(1000);	
		magAcc.signOut()  ;
		Utils.pause(1000);
	}
	
	@Test(groups={"paltform", "AccountTest"})
	public void testEditUserEmail(){
		System.out.println("--Edit a user email--");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(1000);
		goToUsersAndGroupsManagement();
		userGroup.goToEditUserInfo(username);
		type(ELEMENT_INPUT_EMAIL, "exotestaccountedit@localhost.com", true);
		button.save();
	    waitForMessage("The user profile has been updated.");
	    waitForTextPresent("exotestaccountedit@localhost.com");
	    magAcc.signOut()  ;
		Utils.pause(1000);
		
	}
	
	@Test(groups={"paltform", "AccountTest"})
	public void testDeleteUser(){
		System.out.println("--Delete a user account--");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(1000);
		goToUsersAndGroupsManagement();
		Utils.pause(1000);
		userGroup.deleteUser(username);
		Utils.pause(1000);
		magAcc.signOut()  ;
		Utils.pause(1000);
	}
	
}
