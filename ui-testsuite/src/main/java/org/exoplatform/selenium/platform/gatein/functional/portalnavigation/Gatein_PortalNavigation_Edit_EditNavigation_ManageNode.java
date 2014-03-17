package org.exoplatform.selenium.platform.gatein.functional.portalnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
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
 * @date 18-Nov-2013
 *
 */

public class Gatein_PortalNavigation_Edit_EditNavigation_ManageNode extends PlatformBase{

	//General
	Button button;

	//Platform
	ManageAccount magAc;
	NavigationToolbar navToolbar;
	NavigationManagement navMag;
	PageManagement pageMag;
	PageEditor pageEditor;
	UserGroupManagement userGroupMag;
	
	public String username = DATA_USER_JOHN;
	public String password = DATA_PASS;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		button = new Button(driver, this.plfVersion);
		alert = new ManageAlert(driver, this.plfVersion);
		magAc = new ManageAccount(driver, this.plfVersion);
		navToolbar = new NavigationToolbar(driver, this.plfVersion);
		userGroupMag = new UserGroupManagement(driver, this.plfVersion);
		pageMag = new PageManagement(driver, this.plfVersion);
		navMag = new NavigationManagement(driver, this.plfVersion);
		pageEditor = new PageEditor(driver, this.plfVersion);
		magAc.signIn(username, password);
		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterTest(){
		info("Gatein_Navigation_PortalNavigation_EditNavigation: Finish testing");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * caseID: 73729
	 * Create new node in the first level
	 * 
	 */
	@Test
	public void test01_CreateNewNodeAtFirstLevelByAddNewButton(){
		String portalName = "intranet";
		String parentNode = "";
		String nodeName = "Node01";
		String pageSelectorName = "test01pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Navigation Management menu");
		navToolbar.goToPortalSites();
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));

		info("Add a new node by click [Add New] button");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);

		info("Delete node for Portal");
		navMag.deleteNode(portalName, parentNode, nodeName, false);
	}

	/**
	 * caseID: 73766
	 * Create new node in the first level when do right click
	 * 
	 */
	@Test
	public void test02_CreateNewNodeAtFirstLevelByRightClick(){
		String portalName = "intranet";
		String parentNode = "";
		String nodeName = "Node02";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));

		info("Add a new node by right click");
		navMag.addNodeForPortal(portalName, parentNode, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Delete node for Portal");
		navMag.deleteNode(portalName, parentNode, nodeName, false);
	}


	/**
	 * caseID: 73963
	 * Add new node with Name is the same with existing one in the same level in 1 navigation
	 * 
	 */
	@Test
	public void test03_AddSameNodeNameAtSameLevel(){
		String portalName = "intranet";
		String parentNode = "";
		String nodeName = "Node03";
		String pageSelectorName = "test03pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add the first node by right click");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		navMag.addNodeForPortal(portalName, parentNode, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Add a new node by click [Add New Button]");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, false);
		waitForAndGetElement(ELEMENT_WARNING_EXISTING_NODE);
		button.ok();
		button.closeWindow();

		info("Delete node for Portal");
		navMag.deleteNode(portalName, parentNode, nodeName, false);

	}

	/**
	 * caseID: 73975
	 * Add new node with Name is the same with existing one in different level in 1 navigation
	 * 
	 */
	@Test
	public void test04_AddSameNodeNameAtDiffLevel(){
		String portalName = "intranet";
		String parentNode1 = "";
		String parentNode2 = "Home";
		String nodeName = "Node04";
		String pageSelectorName = "test04pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String currentNodeName = "//*[@class='node']//*[@title='${parentNode}']/..//*[@class='node']//*[@title='${nodeName}']";

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add the first node by right click");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		navMag.addNodeForPortal(portalName, parentNode1, false, nodeName, true, languages, nodeName, "", "Profile", true, true);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Add a new node by click [Add New Button]");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		navMag.addNodeForPortal(portalName, parentNode2, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, false);
		waitForElementNotPresent(ELEMENT_WARNING_EXISTING_NODE);

		info("Delete node for Portal");
		editNavigation(portalName);
		rightClickOnElement(currentNodeName.replace("${parentNode}", parentNode2).replace("${nodeName}", nodeName));
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		alert.waitForConfirmation("Are you sure you want to delete this node?");
		waitForElementNotPresent(currentNodeName);
		button.save();		
		
		navMag.deleteNode(portalName, parentNode1, nodeName, true);
		
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.PORTAL, pageSelectorName);
	}

	/**
	 * caseID: 74128
	 * Add new node with page name the same with existing
	 * 
	 */
	@Test
	public void test05_AddNodeWithSamePageName(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName1 = "Node0501";
		String nodeName2 = "Node0502";
		String pageSelectorName = "test05pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a new node #1 by click [Add New Button]");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName1, true, languages, nodeName1, pageSelectorName, pageSelectorName, true, true);

		info("Add a new node #2 by click [Add New Button]");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName2, true, languages, nodeName2, pageSelectorName, pageSelectorName, false, false);
		waitForAndGetElement(ELEMENT_WARNING_EXISTING_PAGE);
		button.ok();
		button.closeWindow();

		info("Delete node for Portal");
		navMag.deleteNode(portalName, parentNode, nodeName1, false);
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.PORTAL, pageSelectorName);
	}

	/**
	 * caseID: 74163
	 * Check show Site Editor menu
	 * 
	 */
	@Test
	public void test06_CheckSiteEditorMenu(){
		info("Check menu Edit/Content");
		waitForAndGetElement(ELEMENT_MENU_EDIT_LINK).click();
		info("Click Edit => OK");
		waitForAndGetElement(ELEMENT_MENU_EDIT_CONTENT);
		info("Content displayed => OK");

		info("Check menu Edit/Page");
		waitForAndGetElement(ELEMENT_MENU_PAGE_LINK);
		info("Click Edit/Page => OK");
		navToolbar.goToEditLayout();
		pageEditor.finishEditLayout();
		info("Edit Layout displayed => OK");
		navToolbar.goToSeoManagement();
		info("SEO displayed => OK");
		navToolbar.goToAddPageManagement();
		info("Add Page displayed => OK");
	}

	/**
	 * caseID: 74166
	 * Check when user have right to edit page
	 * 
	 */
	@Test
	public void test07_CheckEditRightofUseronPage(){
		String groupName = "Platform";
		String subgroupName = "Administration";
		String username1 = getRandomString();
		String password1 = username1;
		String email = "test@gmail.com";
		String membership = "*";
		
		String pageName = "PageName07";
		String pageTitle = "PageTitle07";
		String groupPath = "Platform /Administration ";
		String pagemembership = "*";
		
		info("=========Add new user=========");
		navToolbar.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(username1, password1, password1, username1, username1, username1, email, null, null, true);
		
		info("=========Add new user into Management/Admin Group=========");
		navToolbar.goToUsersAndGroupsManagement();
		userGroupMag.chooseGroupTab();
		click(ELEMENT_GROUP_TREE.replace("${groupName}", groupName));
		click(ELEMENT_GROUP_TREE.replace("${groupName}", subgroupName));
		userGroupMag.addUsersToGroup(username1, membership, false, true);
		
		info("=====Add new page=======");
		navToolbar.goToManagePages();
		info("Add page for portal");
		pageMag.addNewPageAtManagePages(PageType.PORTAL, pageName, pageTitle, true, null, groupPath, pagemembership, "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);

		info("Login by added-to-admin-group user");
		magAc.signOut();
		magAc.signIn(username1, password1);
		
		info("==========Check edit right of added-to-admin-group user============");
		navToolbar.goToManagePages();
		pageMag.editPageAtManagePages(PageType.PORTAL, pageTitle);
		info("Add container and portlet");
		pageEditor.addNewContainerAndPortlet("Rows Layout", "oneRow", "Content", "Content/ContentListViewerByQueryPortlet");
		
		info("=========Restore data=========");
		//Delete page
		pageMag.deletePage(PageType.PORTAL, pageTitle);
		info("Delete page done");
		magAc.signOut();
		//Delete user
		magAc.signIn(username, password);
		navToolbar.goToUsersAndGroupsManagement();
		userGroupMag.chooseUserTab();
		userGroupMag.deleteUser(username1);
		info("Delete user done");
	}

	/**
	 * caseID: 74169
	 * Check when user have right to edit layout of page
	 * 
	 */
	@Test
	public void test08_CheckLayoutEditRightOfUserOnPage(){

		//New user
		String groupName = "Platform";
		String subgroupName = "Administration";
		String username1 = getRandomString();
		String password1 = username1;
		String email = username1+"@gmail.com";
		String membership = "*";
		
		//New page
		String pageName = "PageName08";
		String pageTitle = "PageTitle08";
		String groupPath = "Platform /Administration ";
		String pagemembership = "*";
		
		//New node
		String portalName = "intranet";
		String parentNode = "";
		String nodeName = "Node08";
		String nodeLabel = "nodeLabel";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodepath = "//a[@href='/portal/intranet/${nodeName}']";
		
		info("=========Add new user=========");
		navToolbar.goToNewStaff();
		info("Create new user");
		magAc.addNewUserAccount(username1, password1, password1, username1, username1, username1, email, null, null, true);
		
		info("=========Add new user into Management/Admin Group=========");
		navToolbar.goToUsersAndGroupsManagement();
		userGroupMag.chooseGroupTab();
		click(ELEMENT_GROUP_TREE.replace("${groupName}", groupName));
		click(ELEMENT_GROUP_TREE.replace("${groupName}", subgroupName));
		userGroupMag.addUsersToGroup(username1, membership, false, true);
		
		info("=====Add new page=======");
		navToolbar.goToManagePages();
		info("Add page for portal");
		pageMag.addNewPageAtManagePages(PageType.PORTAL, pageName, pageTitle, true, null, groupPath, pagemembership, "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);
		
		info("=======Add new node=======");
		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));

		info("Add a new node by right click");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, "", "Profile", true, true);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("========Add page for that new node===========");
		navMag.editNodeInPortalNavigation(portalName, parentNode, nodeName, true, languages, nodeLabel, pageName, pageTitle, true);
		
		info("Login by added-to-admin-group user");
		magAc.signOut();
		magAc.signIn(username1, password1);
		
		info("==========Check edit right of added-to-admin-group user============");
		waitForAndGetElement(nodepath.replace("${nodeName}", nodeName));
		click(nodepath.replace("${nodeName}", nodeName));
		navToolbar.goToEditLayout();
		
		info("=========Restore data=========");
		
		//Delete page
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.PORTAL, pageTitle);
		info("Delete page done");
		
		//Delete node
		navToolbar.goToPortalSites();
		navMag.deleteNode(portalName, parentNode, nodeName, false);
		info("Delete node done");
	}

	/**
	 * caseID: 74303
	 * 	Create new node as child of existing node
	 * 
	 */
	@Test
	public void test09_AddNewChildNode(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName = "Node09";
		String pageSelectorName = "case09pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a new node which is child node of Intranet/Homepage by click [Add New Button]");
		click(ELEMENT_EDIT_NAVIGATION.replace("${navigation}", portalName));
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Delete node for Portal");
		navMag.deleteNode(portalName, parentNode, nodeName, false);
	}
}
