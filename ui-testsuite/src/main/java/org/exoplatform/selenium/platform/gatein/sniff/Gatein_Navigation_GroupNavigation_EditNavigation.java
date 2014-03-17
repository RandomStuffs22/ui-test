package org.exoplatform.selenium.platform.gatein.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.GroupNavigation;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * August, 12th, 2013
 * 
 * 
 */
public class Gatein_Navigation_GroupNavigation_EditNavigation extends GroupNavigation{
	//General
	Button button;
	ManageAlert magAlert;

	//Platform
	ManageAccount magAc;
	NavigationToolbar navToolbar;
	NavigationManagement navMag;
	PageManagement pageMag;
	PageEditor pageEditor;
	UserGroupManagement userGroupMag;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		button = new Button(driver, this.plfVersion);
		magAlert = new ManageAlert(driver, this.plfVersion);
		magAc = new ManageAccount(driver, this.plfVersion);
		navToolbar = new NavigationToolbar(driver, this.plfVersion);
		userGroupMag = new UserGroupManagement(driver, this.plfVersion);
		pageMag = new PageManagement(driver, this.plfVersion);
		navMag = new NavigationManagement(driver, this.plfVersion);
		pageEditor = new PageEditor(driver, this.plfVersion);
		magAc.signIn("john", DATA_PASS);

		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterTest(){
				info("Gatein_Navigation_GroupNavigation_EditNavigation: Finish testing");
				driver.manage().deleteAllCookies();
				driver.quit();
	}

	/**
	 * QmetryID: 68870
	 * Edit priority for group navigation
	 * 
	 */
	@Test
	public void test01_EditPriorityForGroupNavigation(){
		String groupAdmin = "/platform/administrators";
		String groupAdminDisplayName = "Administration";
		String groupAdminOldPosition = ELEMENT_GROUP_NAVIGATION_POSITION.replace("${index}","1").replace("${number}", "2").replace("${groupTitle}", groupAdmin);
		String groupAdminNewPosition = ELEMENT_GROUP_NAVIGATION_POSITION.replace("${index}","1").replace("${number}", "1").replace("${groupTitle}", groupAdmin);

		info("Go to Group Sites");
		navToolbar.goToGroupSites();

		//Verify position of Administration before change order
		waitForAndGetElement(groupAdminOldPosition);

		info("Select group navigation [Administration] and click [Edit Properties]");
		click(ELEMENT_EDIT_PROPERTIES_ICON.replace("${groupName}", groupAdminDisplayName));

		info("Change priority for this group");
		select(ELEMENT_GROUP_NAVIGATION_PRIORITY, "1");
		button.save();

		info("Verify position of Administration after changing order");
		waitForAndGetElement(groupAdminNewPosition);
		magAc.signOut();
		magAc.signIn("john", DATA_PASS);
		navToolbar.goToGroupSites();
		//Verify position of Administration after SignOut and SignIn  
		waitForElementNotPresent(groupAdminOldPosition);
		waitForAndGetElement(groupAdminNewPosition);

		info("Reset order of navigation list");
		click(ELEMENT_EDIT_PROPERTIES_ICON.replace("${groupName}", groupAdminDisplayName));
		select(ELEMENT_GROUP_NAVIGATION_PRIORITY, "2");
		button.save();
		waitForAndGetElement(groupAdminOldPosition);
	}

	/**
	 * Qmetry ID: 70580
	 * Add node for group
	 * 
	 * Qmetry ID: 70581
	 * Edit node for group
	 * 
	 * Qmetry ID: 68877
	 * Delete node for group
	 */
	@Test
	public void test02_AddEditDeleteNodeForGroup(){
		String groupAdminDisplayName = "Administration";
		String nodePortalAdministration = "Portal Administration";
		String nodeName = "nodeTest02";
		String nodeNameEdit = "nodeTest02Edit";
		String pageSelectorName = "test02pageSelector";

		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Group Sites/Edit navigation");
		navToolbar.goToGroupSites();

		info("Add a new node for group Administration");
		addNodeForGroup(groupAdminDisplayName, nodePortalAdministration, false, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, false);
		button.save();	
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		if(this.plfVersion.contains("4.0"))
			click(ELEMENT_GROUP_NAVIGATION_ICON_LEFT_PANEL.replace("${groupName}", "Portal Admin"));
		else
			click(ELEMENT_GROUP_NAVIGATION_ICON_LEFT_PANEL_PLF41.replace("${groupName}", "Portal Admin"));
		waitForTextPresent(nodeName);
		info("A new node has been added...successful");

		info("Edit node for group");
		editNodeInGroupNavigation(groupAdminDisplayName, nodeName, nodeNameEdit, "profile", "portal");
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		if(this.plfVersion.contains("4.0"))
			click(ELEMENT_GROUP_NAVIGATION_ICON_LEFT_PANEL.replace("${groupName}", "Portal Admin"));
		else
			click(ELEMENT_GROUP_NAVIGATION_ICON_LEFT_PANEL_PLF41.replace("${groupName}", "Portal Admin"));
		click(ELEMENT_NODE_NAVIGATION_LEFT_PANEL.replace("${groupName}", nodeNameEdit));
		waitForTextPresent("Basic information");
		waitForTextPresent("Contact");
		waitForTextPresent("Experience");

		info("Reset data");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageSelectorName, false, "", true, groupAdminDisplayName, nodeNameEdit);
	}

	/**
	 * Qmetry ID: 70576
	 * Copy and Paste node
	 * 
	 * Qmetry ID: 70577
	 * Cut and Paste Node
	 * 
	 * Qmetry ID: 70578
	 * Clone and Paste Node
	 * 
	 */
	@Test
	public void test03_CopyCutCloneAndPasteNode(){
		String groupAdminDisplayName = "Administration";
		String nodePortalAdministration = "Portal Administration";
		String nodeName = "nodeTest03";
		String pageSelectorName = "test03pageSelector";
		String nodeAdmin = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodePortalAdministration);
		String nodeLinkToCopy = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		String nodeLinkToPaste = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Sites Management");
		String nodeLinkToClone = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Management");
		String nodeAfterCopy = ELEMENT_CHILD_NODE_LINK.replace("${nodeLabel}", "Sites Management").replace("${childNode}", nodeName);

		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Group Sites/Edit navigation");
		navToolbar.goToGroupSites();

		info("Add a new node for group Administration");
		addNodeForGroup(groupAdminDisplayName, nodePortalAdministration, false, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, false);

		info("Copy that node and paste to group Site Management");
		copyNode(nodeLinkToCopy);
		pasteNode(nodeLinkToPaste);
		waitForAndGetElement(nodeAfterCopy);	
		rightClickOnElement(nodeAfterCopy);
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		magAlert.acceptAlert();
		waitForElementNotPresent(nodeAfterCopy);

		info("Cut and Paste Node");
		click(nodeAdmin);
		cutNode(nodeLinkToCopy);
		pasteNode(nodeLinkToPaste);
		waitForAndGetElement(nodeAfterCopy);
		click(nodeAdmin);
		waitForElementNotPresent(nodeLinkToCopy);

		info("Clone and Paste Node");
		click(nodeLinkToPaste);
		cloneNode(nodeAfterCopy);
		pasteNode(nodeLinkToClone);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Verify that a new node is created in Pages Management");
		navToolbar.goToManagePages();
		pageMag.searchPageInManagementPage(PageType.GROUP, pageSelectorName, true);
		waitForAndGetElement(pageMag.ELEMENT_LIST_PAGE.replace("${number}", "1").replace("${titlePage}", pageSelectorName));
		waitForAndGetElement(pageMag.ELEMENT_LIST_PAGE.replace("${number}", "2").replace("${titlePage}", pageSelectorName));

		click(pageMag.ELEMENT_PAGE_DELETE_ICON_AUX.replace("${number}", "2").replace("${titlePage}", pageSelectorName));
		magAlert.acceptAlert();
		waitForElementNotPresent(pageMag.ELEMENT_PAGE_DELETE_ICON_AUX.replace("${number}", "2").replace("${titlePage}", pageSelectorName));

		info("Reset data");
		pageMag.deletePage(PageType.GROUP, pageSelectorName);
		navToolbar.goToGroupSites();
		navMag.deleteNode(groupAdminDisplayName, "Sites Management", nodeName, false);
		navMag.deleteNode(groupAdminDisplayName, "Management", nodeName, false);
	}

	/**
	 * Qmetry ID: 68878
	 * Change node order
	 * 
	 */
	@Test
	public void test04_ChangeNodeOrder(){
		String groupAdminDisplayName = "Administration";
		String nodeSitesManagement = "Sites Management";
		String nodeName1 = "node1Test04";
		String pageSelectorName1 = "test04page1Selector";
		String nodeName2 = "node2Test04";
		String pageSelectorName2 = "test04page2Selector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeLink = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeSitesManagement);
		String nodeLinkToMove = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName2);

		info("Go to Group Sites/Edit navigation");
		navToolbar.goToGroupSites();

		info("Add a new node for group Sites Management");
		addNodeForGroup(groupAdminDisplayName, nodeSitesManagement, false, 
				nodeName1, true, languages, nodeName1, 
				pageSelectorName1, pageSelectorName1, true, false);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		addNodeForGroup(groupAdminDisplayName, nodeSitesManagement, false, 
				nodeName2, true, languages, nodeName2, 
				pageSelectorName2, pageSelectorName2, true, false);

		info("Select Move Up from the drop-down menu");
		rightClickOnElement(nodeLinkToMove);
		click(ELEMENT_NAVIGATION_MOVE_UP_NODE);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Verify the position of node....");
		editNavigation(groupAdminDisplayName);
		click(nodeLink);
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", nodeSitesManagement).replace("${number}", "1").replace("${childNode}", nodeName2));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", nodeSitesManagement).replace("${number}", "2").replace("${childNode}", nodeName1));	
		rightClickOnElement(nodeLinkToMove);
		click(ELEMENT_NAVIGATION_MOVE_DOWN_NODE);
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", nodeSitesManagement).replace("${number}", "1").replace("${childNode}", nodeName1));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", nodeSitesManagement).replace("${number}", "2").replace("${childNode}", nodeName2));
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Reset data");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageSelectorName1);
		pageMag.deletePage(PageType.GROUP, pageSelectorName2);
		navToolbar.goToGroupSites();
		navMag.deleteNode(groupAdminDisplayName, "Sites Management", nodeName1, false);
		navMag.deleteNode(groupAdminDisplayName, "Sites Management", nodeName2, false);
	}

	/**
	 * Qmetry ID: 68879
	 * Edit node's page properties
	 * 
	 */
	@Test
	public void test05_EditNodePageProperties(){
		String nodeName = "nodeTest05";
		String pageSelectorName = "test05page1Selector";
		String pageSelectorNameEdit = "test05page1SelectorEdit";
		String groupAdminDisplayName = "Administration";
		String nodeSitesManagement = "Sites Management";
		String nodeLink = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeSitesManagement);
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Group Sites/Edit navigation");
		navToolbar.goToGroupSites();

		info("Add a node for group Sites Management");
		addNodeForGroup(groupAdminDisplayName, nodeSitesManagement, false, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, false);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Edit node's page properties");
		editNavigation(groupAdminDisplayName);
		click(nodeLink);
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		click(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		//Page Settings
		type(pageEditor.ELEMENT_VIEWPAGE_PAGETITLE, pageSelectorNameEdit, true);
		//Permission Settings
		click(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		setEditPermissions("Platform/Content Management ", "manager");
		button.save();
		waitForElementNotPresent(ELEMENT_EDIT_PERMISSION_SETTING);
		pageEditor.finishEditLayout();
		waitForElementNotPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Verify [Page Settings] is updated");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageSelectorNameEdit);

		info("Reset data");
		navToolbar.goToGroupSites();
		navMag.deleteNode(groupAdminDisplayName, "Sites Management", nodeName, false);
	}

	/**
	 * Qmetry ID: 68880
	 * Move container when edit page properties of node
	 * 
	 * Qmetry ID: 70573
	 * Add container when edit page properties of node
	 * 
	 * Qmetry ID: 70574
	 * Edit container when edit page properties of node
	 * 
	 * Qmetry ID: 70575
	 * Delete container when edit page properties of node
	 * 
	 */
	@Test
	public void test06_AddEditMoveAndDeleteContainerWhenEditPagePropertiesOfNode(){
		String groupAdminDisplayName = "Administration";
		String nodeLink = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Add User");
		String containerTitle = "test06ContainerTitle";
		String newContainerPos = "//*[@class='UIRowContainer']/div[1]";

		info("Go to Group Sites/Edit navigation");
		navToolbar.goToGroupSites();

		info("Edit node's page properties");
		editNavigation(groupAdminDisplayName);
		rightClickOnElement(nodeLink);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		info("Add a container...");
		pageEditor.addNewContainer("Rows Layout", "oneRow");
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_DROP_TARGET_HAS_LAYOUT);
		click(ELEMENT_SWITCH_VIEW_MODE);

		info("Edit a container...");
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		click(ELEMENT_EDIT_CONTAINER_ICON);
		type(ELEMENT_CONTAINER_TITLE, containerTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "150px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "150px", true);
		button.save();
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		if(this.plfVersion.contains("4.0"))
			waitForAndGetElement(ELEMENT_NAME_CONTAINER.replace("${nameContainer}", containerTitle));
		else
			waitForAndGetElement(ELEMENT_NAME_CONTAINER_PLF41.replace("${nameContainer}", containerTitle));
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(button.ELEMENT_SAVE_BUTTON);
		WebElement element = waitForAndGetElement(ELEMENT_EDITING_CONTAINER);
		String valueStyle = element.getAttribute("style");
		assert valueStyle.equals("width: 150px; height: 150px;"): "Failed to edit the value of container: " + containerTitle;

		info("Move a container...");
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		pageEditor.addNewContainer("Rows Layout", "oneRow");
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "2").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", containerTitle), DEFAULT_TIMEOUT, 1, 2);

		mouseOver(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), true);
		if(this.plfVersion.contains("4.0"))
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_PORTLET_LAYOUT_DECORATOR);
		else
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER_PLF41.replace("${nameContainer}", "Container"), newContainerPos);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", containerTitle), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);
		click(ELEMENT_ABORTEDIT_BUTTON);
		click(pageEditor.ELEMENT_CONFIRM_YES_BUTTON);
		waitForElementNotPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		info("Delete a container...");
		rightClickOnElement(nodeLink);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		pageEditor.addNewContainer("Rows Layout", "oneRow");
		pageEditor.removeContainer(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_DELETE_CONTAINER_ICON);
		pageEditor.finishEditLayout();	
		waitForElementNotPresent(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
	}

	/**
	 * Qmetry ID: 68881
	 * Move application when edit page properties of node
	 * 
	 * Qmetry ID: 70570
	 * Add application when edit page properties of node
	 * 
	 * Qmetry ID: 70571
	 * Edit application when edit page properties of node
	 * 
	 * Qmetry ID: 70572
	 * Delete application when edit page properties of node
	 * 
	 */
	@Test
	public void test07_AddEditMoveAndDeleteApplicationWhenEditPagePropertiesOfNode(){
		String nodeSitesManagement = "Sites Management";
		String groupAdminDisplayName = "Administration";	
		String pageSelectorName = "test07pageSelector";
		String portletTitle = "test07PortletTitle";
		String nodeName = "test07NodeName";
		String nodeLink = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);

		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Group Sites/Edit navigation");
		navToolbar.goToGroupSites();

		info("Add a node for group Sites Management");
		addNodeForGroup(groupAdminDisplayName, nodeSitesManagement, false, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, false);
		rightClickOnElement(nodeLink);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		info("Add an application when edit page...");
		click(ELEMENT_CATEGORY_ADMINISTRATION);
		dragAndDropToObject(ELEMENT_ACCOUNT_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_INPUT_USERNAME);
		waitForAndGetElement(ELEMENT_INPUT_PASSWORD);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForElementNotPresent(ELEMENT_INPUT_USERNAME);

		info("Edit an application when edit page...");
		pageEditor.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);
		type(ELEMENT_CONTAINER_TITLE, portletTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "600px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "600px", true);
		button.saveAndClose();
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(button.ELEMENT_SAVE_BUTTON);
		waitForAndGetElement(ELEMENT_NAME_PORTLET.replace("${portletName}", portletTitle));
		WebElement element = waitForAndGetElement(ELEMENT_PORTLET_FRAGMENT.replace("${portletName}", "UIAccountPortlet"));
		String valueStyle = element.getAttribute("style");
		assert valueStyle.equals("width: 100%; height: 600px;"): "Failed to edit portlet: " + portletTitle;

		info("Move an application when edit page...");
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		dragAndDropToObject(ELEMENT_PAGE_MANAGEMENT_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);
		dragAndDropToObject(ELEMENT_ORGANIZATION_PORTLET, ELEMENT_DROP_TARGET_NO_LAYOUT);
		mouseOver(ELEMENT_LIST_PORTLET_LAYOUT_DECORATOR.replace("${portletName}", portletTitle), true);
		if(this.plfVersion.contains("4.0"))
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_PORTLET.replace("${portletName}", portletTitle), ELEMENT_LIST_PORTLET_LAYOUT_DECORATOR.replace("${portletName}", "Organization Portlet"));
		else
			dragAndDropToObject(eLEMENT_DRAG_CURRENT_PORTLET_PLF41.replace("${portletName}", portletTitle), ELEMENT_LIST_PORTLET_LAYOUT_DECORATOR.replace("${portletName}", "Organization Portlet"));

		info("Verify that the page is updated after moving...");
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "2").replace("${nameContainer}", portletTitle));
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "3").replace("${nameContainer}", "Organization Portlet"));
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", "Page Management Portlet"));

		info("Delete an application when edit page...");
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);
		pageEditor.removePortlet(ELEMENT_LIST_PORTLET_LAYOUT_DECORATOR.replace("${portletName}", portletTitle), ELEMENT_DELETE_PORTLET_ICON, false);
		pageEditor.removePortlet(ELEMENT_LIST_PORTLET_LAYOUT_DECORATOR.replace("${portletName}", "Organization Portlet"), ELEMENT_DELETE_PORTLET_ICON, false);
		pageEditor.removePortlet(ELEMENT_LIST_PORTLET_LAYOUT_DECORATOR.replace("${portletName}", "Page Management Portlet"), ELEMENT_DELETE_PORTLET_ICON, false);
		pageEditor.finishEditLayout();

		info("Reset data");
		rightClickOnElement(nodeLink);
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		magAlert.acceptAlert();
		waitForElementNotPresent(nodeLink);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageSelectorName);
	}

	/**
	 * Qmetry ID: 68882
	 * Add application into container when edit page properties of node
	 * 
	 */
	@Test
	public void test08_AddApplicationIntoContainerWhenEditPagePropertiesOfNode(){
		String nodeSitesManagement = "Sites Management";
		String groupAdminDisplayName = "Administration";	
		String pageSelectorName = "test08pageSelector";
		String nodeName = "test08NodeName";
		String nodeLink = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		String categoryContainer = "Rows Layout";
		String typeContainer = "oneRow";

		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Group Sites/Edit navigation");
		navToolbar.goToGroupSites();

		info("Add a node for group Sites Management");
		addNodeForGroup(groupAdminDisplayName, nodeSitesManagement, false, 
				nodeName, true, languages, nodeName, 
				pageSelectorName, pageSelectorName, true, false);
		rightClickOnElement(nodeLink);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		info("Add an application into selected container");
		pageEditor.addNewContainerAndPortlet(categoryContainer, typeContainer, "Collaboration", "Collaboration/AnswersPortlet", false);	
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageEditor.finishEditLayout();

		info("Reset data");
		rightClickOnElement(nodeLink);
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		magAlert.acceptAlert();
		waitForElementNotPresent(nodeLink);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageSelectorName);
	}
}