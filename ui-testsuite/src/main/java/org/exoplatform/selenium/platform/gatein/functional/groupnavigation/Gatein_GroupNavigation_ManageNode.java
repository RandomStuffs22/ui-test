package org.exoplatform.selenium.platform.gatein.functional.groupnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
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
public class Gatein_GroupNavigation_ManageNode extends GroupNavigation {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement group;
	NavigationToolbar navToolbar;
	ManageAlert magAlert;
	PageEditor pageEditor;
	PageManagement pageMag;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		group = new UserGroupManagement(driver);
		navToolbar = new NavigationToolbar(driver);
		button = new Button(driver);
		magAlert = new ManageAlert(driver);
		pageEditor = new PageEditor(driver);
		pageMag = new PageManagement(driver);
		magAc.signIn("john", DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Delete node with deleting confirmation
	 * CaseID 73406
	 * Step 1: Show Edit Navigation form
	 * Step 2: Delete node
	 * Step 3: Check page of deleted node
	 */
	@Test
	public void test01_DeleteNodeWithDeletingConfirmation() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup73406";
		String nodeName = "nodeTest73406";
		String pageSelectorName = "page73406";
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		/*Create data*/
		info("Create new group with John");
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		group.addGroup(groupNameDisplayName, null, null, true);

		info("Add new navigation for new group");
		navTool.goToGroupSites();
		addNewNavigationForGroup(groupNameDisplayName);

		info("Add a new node for group");
		addNodeForGroup(groupNameDisplayName, "", true, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, true);

		/* Step 1: Show Edit Navigation form */
		//- Login by admin or user is manager of at least the exited group navigation
		//- Click Group link and choose a group navigation
		//-  Click Edit navigation link
		//Show Navigation management form
		editNavigation(groupNameDisplayName);

		/* Step 2: Delete node */
		//- chose a node
		//- Right click on node & select Delete node
		//- Click OK to confirm
		//Node is removed 
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		magAlert.acceptAlert();
		waitForElementNotPresent(nodeLinkToEdit);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		/* Step 3: Check page of deleted node */
		//Go to Page Management, check existing of page that deleted node linked to
		//Page still exists
		navToolbar.goToManagePages();
		pageMag.searchPageInManagementPage(PageType.GROUP, pageSelectorName, true);
		waitForAndGetElement(pageMag.ELEMENT_LIST_PAGE.replace("${number}", "1").replace("${titlePage}", pageSelectorName));

		/*Clear data*/
		info("-- Clear data --");
		pageMag.deletePage(PageType.GROUP, pageSelectorName);
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}

	/** Delete node with deleting confirmation
	 * CaseID 73485
	 * Step 1: Show Edit group's navigation
	 * Step 2: Show form to add new node
	 * Step 3: Create node
	 */
	@Test
	public void test02_CreateNewNodeAsChildOfExistingNode() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup73485";
		String nodeName = "nodeTest73485";
		String subNodeName = "subNode73485";
		String pageSelectorName = "page73485";
		String subPageSelectorName = "subpage73485";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		/*Create data*/
		info("Create new group with John");
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		group.addGroup(groupNameDisplayName, null, null, true);

		info("Add new navigation for new group");
		navTool.goToGroupSites();
		addNewNavigationForGroup(groupNameDisplayName);

		info("Add a new node for group");
		addNodeForGroup(groupNameDisplayName, "", true, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, true);

		/* Step 1: Show Edit group's navigation */
		//- Login by user is manager of at least the exited group navigation
		//- Click on Group link 
		//- Select a Group navigation and click on [Edit navigation] link
		//Edit Group navigation is show with all exited node on group's nav
		editNavigation(groupNameDisplayName);
		
		/* Step 2: Show form to add new node */
		//- Choose a parent node
		//- Right click on one node & select Add new node
		//Add new node form is shown properly
		/* Step 3: Create node */
		//- Input values for required field
		//- Click Save
		//New node is created successfully & displayed as sub node of selected parent
		info("Add a sub new node for group");
		addNodeForGroup(groupNameDisplayName, nodeName, true, 
				subNodeName, true, languages, subNodeName, 
				subPageSelectorName, subPageSelectorName, true, true);

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, subPageSelectorName);
		pageMag.deletePage(PageType.GROUP, pageSelectorName);
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}
}
