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
public class GroupTest extends NavigationToolbar{
	
	public GroupTest(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	ManageAccount magAcc = new ManageAccount(driver);
	UserGroupManagement userGroup = new UserGroupManagement(driver);
	
	String groupName = "testgroup"	  ; 
	String groupLabel= ""			  ; 
	String groupDesc = "group example"; 
	boolean verify   = true			  ;
	
	String userName  = "demo";
	String memberShip= "member"; 
	boolean select   =  true; 
	
	@BeforeTest(groups = {"platform"})
	public void beforeTest()  {
		driver = new FirefoxDriver();
    	baseUrl = "http://localhost:8080";
    	driver.get(baseUrl + "/portal/intranet/");
    	Utils.pause(3000);
		
	}

	@AfterTest(groups = {"platform"})
	public void afterTest()  {
		driver.quit();
	}
	
	@Test(groups={"platform", "add new group"})
	public void testAddNewGroup(){
		System.out.println("--Create a new group--");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(1000);
		goToUsersAndGroupsManagement();
		Utils.pause(1000);
		userGroup.chooseGroupTab();
		Utils.pause(1000);
		userGroup.addGroup(groupName, groupLabel, groupDesc,verify);
		magAcc.signOut();
		Utils.pause(1000);
		
	}
	
	@Test(groups={"platform", "add new group"})
	public void testSelectAndEditGroup(){
		System.out.println("--Edit a new group--");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(1000);
		goToUsersAndGroupsManagement();
		Utils.pause(1000);
		userGroup.chooseGroupTab();
		Utils.pause(1000);
		userGroup.selectGroup(groupName);
		//userGroup.editGroup(groupName, true);
		type(ELEMENT_INPUT_LABEL, "", true);
		type(ELEMENT_TEXTAREA_DESCRIPTION, "edit a group description", true);
		button.save();
		Utils.pause(1000);
		magAcc.signOut();
		Utils.pause(1000);
	}
	
	@Test(groups={"platform", "add new group"})
	public void testAddUserIntoGroup(){
		System.out.println("--Add user to a group--");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(1000);
		goToUsersAndGroupsManagement();
		Utils.pause(1000);
		userGroup.chooseGroupTab();
		Utils.pause(1000);
		userGroup.selectGroup(groupName);
		userGroup.addUsersToGroup(userName, memberShip, select, verify);
		Utils.pause(1000);
		magAcc.signOut();
		Utils.pause(1000);
		
	}
	
	
	@Test(groups={"platform", "add new group"})
	public void testDeleteGroup(){
		System.out.println("--Delete a new group--");
		magAcc.signIn("root", DATA_PASS);
		Utils.pause(1000);
		goToUsersAndGroupsManagement();
		Utils.pause(1000);
		userGroup.chooseGroupTab();
		Utils.pause(1000);
		userGroup.selectGroup(groupName);
		userGroup.deleteGroup(groupName, true);
		Utils.pause(3000);
		magAcc.signOut();
		Utils.pause(1000);		
	}
}
