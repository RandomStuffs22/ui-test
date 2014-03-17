package org.exoplatform.selenium.platform.gatein.functional.groupnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 18/11/2013
 *
 */
public class Gatein_GroupNavigation_ManageNavigation extends GroupNavigation {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement group;
	NavigationToolbar navToolbar;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		group = new UserGroupManagement(driver);
		navToolbar = new NavigationToolbar(driver);
		button = new Button(driver);
		magAc.signIn("john", DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Edit properties of group navigation
	 * CaseID 74232
	 * Step 1: Show Group navigation form
	 * Step 2: Show form to edit group navigation
	 * Step 3: Change Priority of nav
	 * Step 4: Check after change priority of navigations
	 */
	@Test
	public void test01_EditPropertiesOfGroupNavigation() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup74232";
		String groupName = "/"+groupNameDisplayName;
		String groupAdminOldPosition = ELEMENT_GROUP_NAVIGATION_POSITION.replace("${index}","1").replace("${number}", "1").replace("${groupTitle}", groupName);
		String groupAdminNewPosition = ELEMENT_GROUP_NAVIGATION_POSITION.replace("${index}","2").replace("${number}", "3").replace("${groupTitle}", groupName);

		/*Create data*/
		info("Create new group with John");
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		group.addGroup(groupNameDisplayName, null, null, true);

		/* Step 1: Show Group navigation form */
		//- Login by user is manager of at least 1 group & that group already has navigation
		//- Click on Group link
		//Show Group navigation page with all navigation of group which user is manager
		navTool.goToGroupSites();
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Content Management"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Administration"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Executive Board"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Employees"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Users"));

		info("Add new navigation for new group");
		addNewNavigationForGroup(groupNameDisplayName);

		/* Step 2: Show form to edit group navigation */
		//- Select group navigation and click on Edit properties link
		//Verify position of Administration before change order
		waitForAndGetElement(groupAdminOldPosition);
		info("Select group navigation [groupName] and click [Edit Properties]");
		click(ELEMENT_EDIT_PROPERTIES_ICON.replace("${groupName}", groupNameDisplayName));

		//Page Navigation form is shown: 
		//- Owner type: "group", can not be changed
		//- Owner id: group name,  can not be changed
		assert waitForAndGetElement(ELEMENT_GROUP_NAVIGATION_OWNER_TYPE).getAttribute("readonly")!=null;
		assert waitForAndGetElement(ELEMENT_GROUP_NAVIGATION_OWNER_ID).getAttribute("readonly")!=null;

		//- User can change Priority 
		/* Step 3: Change Priority of nav */
		//- Choose a number for priority of nav
		//- Click Save
		//- Change is saved
		//- The order of navigation in list is changed as edited
		info("Change priority for this group");
		select(ELEMENT_GROUP_NAVIGATION_PRIORITY, "9");
		button.save();
		
		/* Step 4: Check after change priority of navigations */
		//- Sign out and Sign In again
		//- Go to Group link
		//- Show navigation list after change priority successfully
		info("Verify position of Administration after changing order");
		waitForAndGetElement(groupAdminNewPosition);
		
		//Verify position of Administration after SignOut and SignIn
		magAc.signOut();
		magAc.signIn("john", DATA_PASS);
		navToolbar.goToGroupSites();
		waitForElementNotPresent(groupAdminOldPosition);
		waitForAndGetElement(groupAdminNewPosition);
		
		/*Clear data*/
		info("-- Clear data --");
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}

}
