package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @date 6 Aug 2013
 * @author lientm
 *
 */

public class Gatein_Navigation_GroupNavigation_ManageNavigation extends GroupNavigation {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement group;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver, this.plfVersion);
		navTool = new NavigationToolbar(driver, this.plfVersion);
		group = new UserGroupManagement(driver, this.plfVersion);
		
		magAc.signIn("john", DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**CaseId: 68869 -> Manage group navigation
	 * include 2 other cases in Qmetry (70446, 70450)
	 */
	@Test
	public void test01_ManageGroupNavigation(){
		String groupName = "sniffgroup01";
		
		info("Create new group with John");
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		group.addGroup(groupName, null, null, true);
		
		info("Show navigation list default of user John");
		navTool.goToGroupSites();
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Content Management"));
		//waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Development"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Administration"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Executive Board"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Employees"));
		waitForAndGetElement(ELEMENT_GROUP_NAME.replace("${groupName}", "Users"));
		
		info("Add new navigation for new group");
		navTool.goToGroupSites();
		addNewNavigationForGroup(groupName);
		
		info("Delete navigation of group");
		deleteNavigationForGroup(groupName);
		
		info("Delete group");
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupName));
		group.deleteGroup(groupName, true, 60000);
	}
}