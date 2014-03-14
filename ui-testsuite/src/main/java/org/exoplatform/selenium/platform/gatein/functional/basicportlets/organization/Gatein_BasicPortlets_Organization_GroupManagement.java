package org.exoplatform.selenium.platform.gatein.functional.basicportlets.organization;


import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
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

public class Gatein_BasicPortlets_Organization_GroupManagement extends PlatformBase{

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement user;
	Button but;
	PageManagement pageMag;
	PageEditor pageEditor;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		user = new UserGroupManagement(driver);
		but = new Button(driver);
		pageEditor = new PageEditor(driver);
		pageMag = new PageManagement(driver);
		button = new Button(driver);
		magAc.signIn("fqa", DATA_PASS); 
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Add new Group in Group  Management
	 * caseID: 73378
	 */
	@Test
	public void test01_AddGroupinGroupManagement() {
		String groupName = "Gateingroup01";
		String groupLabel = "Group Label 01";
		String groupDesc = "create new group";

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Restore data");
		user.deleteGroup(groupName, true);
	}

	/**
	 * Delete group with deleting confirmation
	 * caseID: 73380
	 */

	@Test
	public void test02_DeleteGroupinGroupManagement() {
		String groupName = "Gateingroup02";
		String groupLabel = "Group Label 02";
		String groupDesc = "delete group";

		info("Go to Group Management");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();

		info("Add new group");
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Delete a group");
		user.deleteGroup(groupName, true);
	}

	/**
	 * Remove user from existing group
	 * caseID: 73383
	 */
	@Test
	public void test03_RemoveUserfromGroupinGroupManagement() {
		String groupName = "Gateingroup03";
		String groupLabel = "Group Label 03";
		String groupDesc = "create new group";

		String newUser = getRandomString();
		String membership = "member";
		String email = newUser+"@gmail.com";
		String password = newUser;

		info("Add new user");
		navTool.goToNewStaff();
		magAc.addNewUserAccount(newUser, password, password, newUser, newUser, newUser, email, null, null, true);

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add new user to new group");
		user.addUsersToGroup(newUser, membership, true, true);

		info("Remove user from group");
		user.deleteUserInGroup(groupName, groupLabel, newUser);

		info("Restore data");
		user.deleteGroup(groupName, true);
		Utils.pause(3000);
		user.chooseUserTab();
		Utils.pause(3000);
		user.deleteUser(newUser);
	}


	/**
	 * Add un-available user into group
	 * caseID: 73471
	 */
	@Test
	public void test04_AddInexistentUserinGroupinGroupManagement() {
		String groupName = "Gateingroup01";
		String groupLabel = "Group Label 01";
		String groupDesc = "create new group";

		String newUser = "abc";
		String membership = "*";
		String faildmsg = "//span[@class='infoIcon' and contains(text(),'exist')]";

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add Inexistent user to new group");
		user.addUsersToGroup(newUser, membership, false, false);
		waitForAndGetElement(faildmsg);
		but.ok();

		info("Restore data");
		user.deleteGroup(groupName, true);
	}
	/**
	 * Check search user function when add user into group
	 * caseID: 73544
	 */
	@Test
	public void test05_CheckSearchUserWhenAddUserIntoGroup() {
		String groupName = "Platform";
		String username1 = USER_ROOT;
		String username2 = "abc1234";
		String username3 = "#$%^&";
		
		info("Select group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.selectGroup(groupName);

		info("Search root user");
		user.searchUserInGroupManagement(username1,"User Name", true);
		
		info("Search inexistent user");
		user.searchUserInGroupManagement(username2,"User Name", false);
		
		info("Search with special chars");
		user.searchUserInGroupManagement(username3,"User Name", false);	
	}

	/**
	 * Check Membership of user in User profile after removed from specific group
	 * caseID: 73546
	 */
	@Test
	public void test06_CheckUserProfileAfterMembershipisDeleted() {
		String groupName = "Gateingroup06";
		String groupLabel = "Group Label 06";
		String groupDesc = "create new group";

		String newUser = getRandomString();
		String membership = "member";
		String email = newUser+"@gmail.com";
		String password = newUser;
		
		info("Add new user");
		navTool.goToNewStaff();
		magAc.addNewUserAccount(newUser, password, password, newUser, newUser, newUser, email, null, null, true);

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add new user to new group");
		user.addUsersToGroup(newUser, membership, true, true);
		
		info("Delete user from group");
		user.deleteUserInGroup(groupName, groupLabel, newUser);
		
		info("Check membership information of deleted user in group");
		user.chooseUserTab();
		user.goToEditUserInfo(newUser);
		click(ELEMENT_USER_MANAGEMENT_MEMBERSHIP_TAB);
		waitForElementNotPresent(ELEMENT_USER_MEMBERSHIP_TAB_GROUPID.replace("${groupName}", groupName));
		
		info("Restore data");
		click(ELEMENT_USER_MEMBERSHIP_TAB_CLOSE_BUTTON);
		user.deleteUser(newUser);
		Utils.pause(3000);
		user.chooseGroupTab();
		user.deleteGroup(groupName, true);
	}
	

	/**
	 * Check existing of user in deleted group
	 * caseID: 73606
	 */
	@Test
	public void test07_CheckexistofUserAfterGroupisDeleted() {
		String groupName = "Gateingroup07";
		String groupLabel = "Group Label 07";
		String groupDesc = "create new group";

		String newUser = getRandomString();
		String membership = "member";
		String email = newUser+"@gmail.com";
		String password = newUser;

		info("Add new user");
		navTool.goToNewStaff();
		magAc.addNewUserAccount(newUser, password, password, newUser, newUser, newUser, email, null, null, true);

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add new user to new group");
		user.addUsersToGroup(newUser, membership, true, true);

		info("Delete group");
		user.deleteGroup(groupName, true);

		info("Check user existence after delete group");
		user.chooseUserTab();
		user.searchUser(newUser, "User Name");

		info("Restore data");
		user.deleteUser(newUser);
	}

	/**
	 * Add new group when group name is the same with existing but different with lower/upper case
	 * caseID: 73656
	 */
	@Test
	public void test08_Add2GroupwithSameNamebutUpperorLowercase() {
		String groupName1 = "Gateingroup08";
		String groupLabel = "Group Label 08";
		String groupDesc = "create new group 08";

		String groupName2 = "GATEINGROUP08";

		info("Add 1# group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName1, groupLabel, groupDesc, true);

		info("Add 2# group at lower/upper case ");
		user.addGroup(groupName2, groupLabel, groupDesc, true);

		info("Restore data");
		user.deleteGroup(groupName1, true);
		user.deleteGroup(groupName2, true);
	}

	/**
	 * Check existing of pages in deleted group
	 * caseID: 73658
	 */
	@Test
	public void test09_CheckExistenceOfPageinDeletedGroup() {
		String groupName = "Gateingroup73658";
		String groupLabel = "Group Label 73658";
		String groupDesc = "create new group";
		
		String pageName = "ManagePageName73658";
		String membership = "*";
		String nodeName = "NodeName73658";

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);
		
		info("Add new page in Page Management");
		navTool.goToManagePages();
		pageMag.addNewPageAtManagePages(PageType.GROUP, pageName, pageName, true, null, groupLabel, membership, "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);
		
		info("Add new page by wizard");
		navTool.goToPageCreationWizard();
		String title = "Content";
		Map<String, String> portletId = new HashMap<String, String>();
		portletId.put("Content/ContentListViewerByQueryPortlet", "");
		pageMag.addNewPageEditor(nodeName, nodeName, null, title, portletId, false, false);
		//Add Group permission for page
		navTool.goToEditPageEditor();
		pageEditor.addAccessPermissionforPortlet(ELEMENT_FRAME_CONTAIN_PORTLET, groupLabel, membership);
		pageEditor.finishEditLayout();

		info("Delete group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.deleteGroup(groupName, true);
		
		info("Check the existence of pages after group is deleted");
		navTool.goToManagePages();
		pageMag.searchPageInManagementPage(PageType.GROUP, nodeName, true);
		pageMag.searchPageInManagementPage(PageType.GROUP, pageName, true);
		
		info("Restore data");
		pageMag.deletePage(PageType.GROUP, pageName);
		pageMag.searchPageInManagementPage(PageType.GROUP, nodeName, true);
		pageMag.deletePage(PageType.GROUP, nodeName);
	}

	/**
	 * Check creating page for deleted group
	 * caseID: 73706
	 */
	@Test
	public void test10_CheckExistenceOfDeletedGroupInPagePermission() {
		String groupName = "Gateingroup173706";
		String groupLabel = "Group Label 173706";
		String groupDesc = "create new group";		
		String nodeName = "ManagePageName173706";

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);
		
		info("Delete group");
		user.deleteGroup(groupLabel, true);
		navTool.goToHomePage();
		
		info("Check Group in Add Page Permission of Page Management");
		info("--Click Admin menu--");
		click(ELEMENT_LINK_SETUP);
		Utils.pause(2000);
		info("--Click Portal sub-menu & go to Page Management--");
		mouseOver(ELEMENT_MENU_PORTAL,false);
		Utils.pause(2000);
		click(ELEMENT_MENU_PORTAL_PAGES_MANAGEMENT);
		info("To be in Page Management");
		pageMag.goToPagePermissionOfAddPageInPageManagement();
		click(ELEMENT_ADD_PERMISSION_BUTTON);
		waitForElementNotPresent(ELEMENT_SELECT_ACCESS_GROUP_ITEM.replace("${group}",groupLabel));
		Utils.pause(1000);
		button.closeWindow();
		button.cancel();
		
		info("Check Group in Portlet Permission of Add new page by wizard");
		navTool.goToPageCreationWizard();
		Map<String, String> portletId = new HashMap<String, String>();
		String title = "Content";
		portletId.put("Content/ContentListViewerByQueryPortlet", "");
		pageMag.addNewPageEditor(nodeName, nodeName, null, title, portletId, false, true);
		pageEditor.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		click(ELEMENT_ACCESS_PERMISSION_TAB);
		Utils.pause(3000);
		if(isElementPresent(ELEMENT_PORTLET_ADD_PERMISSION_BUTTON))
			click(ELEMENT_PORTLET_ADD_PERMISSION_BUTTON);
		else
			click(ELEMENT_PORTLET_ADD_PERMISSION_BUTTON_1);
		waitForElementNotPresent(ELEMENT_SELECT_ACCESS_GROUP_ITEM.replace("${group}",groupLabel));
	}

	/**
	 * Add duplicate user into a group with the same role
	 * caseID: 73749
	 */
	@Test
	public void test11_AddDuplicatedUsersIntoGroup() {

		String groupName = "Gateingroup11";
		String groupLabel = "Group Label 11";
		String groupDesc = "create new group";

		String newUser = getRandomString();
		String membership = "member";
		String email = newUser+"@gmail.com";
		String password = newUser;

		String ELEMENT_FAIL_ADD_DUPLICATED_USER_MSG = "//span[contains(text(),'has already the same membership in the group')]";

		info("Add new user");
		navTool.goToNewStaff();
		magAc.addNewUserAccount(newUser, password, password, newUser, newUser, newUser, email, null, null, true);

		info("Add new group");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();
		user.addGroup(groupName, groupLabel, groupDesc, true);

		info("Add duplicated user to new group");
		// the first
		user.addUsersToGroup(newUser, membership, true, true);
		// the second
		user.addUsersToGroup(newUser, membership, true, false);
		waitForAndGetElement(ELEMENT_FAIL_ADD_DUPLICATED_USER_MSG);

		info("Delete group");
		user.deleteGroup(groupName, true);

	}
	/**
	 * Delete group which is mandatory
	 * caseID: 73832
	 */
	@Test
	public void test12_DeleteMandatoryGroup() {
		String groupName = "Platform";
		String ELEMENT_FAIL_DELETE_MANDATORY_GROUP_MSG = "//span[contains(text(),'delete this group because it (or its child) is mandatory.')]";

		info("Go to Group Management tab");
		navTool.goToUsersAndGroupsManagement();
		user.chooseGroupTab();

		info("Delete mandatory user");
		click(ELEMENT_GROUP_LIST.replace("${groupName}", groupName));
		user.deleteGroup(groupName, false);
		waitForAndGetElement(ELEMENT_FAIL_DELETE_MANDATORY_GROUP_MSG);

	}
}
