package org.exoplatform.selenium.platform.gatein.sniff;

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
import org.exoplatform.selenium.platform.PortalManagement;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * August 15th 2013
 *
 */
public class Gatein_Navigation_PortalNavigation_EditNavigation extends PortalManagement{
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
		button = new Button(driver);
		magAlert = new ManageAlert(driver);
		magAc = new ManageAccount(driver);
		navToolbar = new NavigationToolbar(driver);
		userGroupMag = new UserGroupManagement(driver);
		pageMag = new PageManagement(driver);
		navMag = new NavigationManagement(driver);
		pageEditor = new PageEditor(driver);
		magAc.signIn("john", DATA_PASS);
		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterTest(){
		info("Gatein_Navigation_PortalNavigation_EditNavigation: Finish testing");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 68865
	 * Edit node's page properties
	 * 
	 */
	@Test
	public void test01_EditNodePageProperties(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName = "test01EditNode";
		String pageSelectorName = "test01pageSelector";
		String pageSelectorNameEdit = "test01pageSelectorEdit";
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a node for testing");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);

		info("Edit node's page: " + nodeName);
		editNavigation(portalName);
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
		pageMag.deletePage(PageType.PORTAL, pageSelectorNameEdit);

		info("Reset data");
		navToolbar.goToPortalSites();
		navMag.deleteNode(portalName, parentNode, nodeName, false);
	}

	/**
	 * Qmetry ID: 68868
	 * Add application into container when edit page properties of node
	 * 
	 */
	@Test
	public void test02_AddApplicationIntoContainerWhenEditPagePropertiesOfNode(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName = "test02EditNode";
		String pageSelectorName = "test02pageSelector";
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		String categoryContainer = "Rows Layout";
		String typeContainer = "oneRow";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a node for testing");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);

		info("Edit node's page: " + nodeName);
		editNavigation(portalName);
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		waitForAndGetElement(pageEditor.ELEMENT_VIEW_PAGE_PROPERTIES);

		info("Add an application into selected container");
		pageEditor.addNewContainerAndPortlet(categoryContainer, typeContainer, "Collaboration", "Collaboration/AnswersPortlet", false);	
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageEditor.finishEditLayout();

		info("Reset data");
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		magAlert.acceptAlert();
		waitForElementNotPresent(nodeLinkToEdit);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.PORTAL, pageSelectorName);
	}

	/**
	 * Qmetry ID: 70583
	 * Delete node for Portal
	 * 
	 * Qmetry ID: 70584
	 * Add node for portal
	 * 
	 * Qmetry ID: 70585
	 * Edit node for portal
	 *  
	 */
	@Test
	public void test03_AddEditDeleteNodeForPortal(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName = "test03EditNode";
		String pageSelectorName = "test03pageSelector";
		String nodeName2 = "test03EditNode2";
		String nodeName2Edit = "test03EditNode2Label";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a new node by click [Add New Button]");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);

		info("Add a new node by right click");
		navMag.addNodeForPortal(portalName, parentNode, false, nodeName2, true, languages, nodeName2, "", "Profile", true, true);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("View Page before editing...");
		driver.get(DEFAULT_BASEURL + "/intranet/home/" + nodeName2);
		waitForTextPresent("Basic information");
		waitForTextPresent("Contact");
		waitForTextPresent("Experience");

		info("Edit the current node: " + nodeName2);
		navToolbar.goToPortalSites();
		navMag.editNodeInPortalNavigation(portalName, parentNode, nodeName2, true, languages, nodeName2Edit, "", "wiki", true);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("View Page after editing...");
		driver.get(DEFAULT_BASEURL + "/intranet/home/" + nodeName2);
		waitForTextNotPresent("Basic information");
		waitForTextPresent("Wiki Home");

		info("Delete node for Portal");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageSelectorName, true, portalName, false, "", nodeName);
		navMag.deleteNode(portalName, parentNode, nodeName2, false);
	}


	/**
	 * Qmetry ID: 70597
	 * Change node order
	 *  
	 */
	@Test
	public void test04_ChangeNodeOrder(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName = "test04EditNode";
		String pageSelectorName = "test04pageSelector";
		String nodeName2 = "test04EditNode2";
		String nodeLinkToMove = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName2);
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a new node by click [Add New Button]");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);

		info("Add a new node by right click");
		navMag.addNodeForPortal(portalName, parentNode, false, nodeName2, true, languages, nodeName2, "", "Profile", true, true);
		editNavigation(portalName);
		rightClickOnElement(nodeLinkToMove);
		click(ELEMENT_NAVIGATION_MOVE_UP_NODE);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Select Move Up from the drop-down menu");
		editNavigation(portalName);
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNode).replace("${number}", "1").replace("${childNode}", nodeName2));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNode).replace("${number}", "2").replace("${childNode}", nodeName));	
		rightClickOnElement(nodeLinkToMove);
		click(ELEMENT_NAVIGATION_MOVE_DOWN_NODE);
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNode).replace("${number}", "1").replace("${childNode}", nodeName));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNode).replace("${number}", "2").replace("${childNode}", nodeName2));
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Reset data");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageSelectorName, true, portalName, false, "", nodeName);
		navMag.deleteNode(portalName, parentNode, nodeName2, false);
	}

	/**
	 * Qmetry ID: 70598
	 * Copy and Paste node
	 * 
	 * Qmetry ID: 70599
	 * Cut and Paste node
	 * 
	 * Qmetry ID: 70600
	 * Clone and Paste node
	 * 
	 */
	@Test
	public void test05_CopyCutCloneAndPasteNode(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName = "test05EditNode";
		String pageSelectorName = "test05pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeParentLink = ELEMENT_NODE_LINK.replace("${nodeLabel}", parentNode);
		String nodeLinkToCopy = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		String nodeLinkToPaste = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Wiki");
		String nodeAfterCopy = ELEMENT_CHILD_NODE_LINK.replace("${nodeLabel}", "Wiki").replace("${childNode}", nodeName);
		String nodeLinkToClone = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Documents");

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a new node by click [Add New Button]");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);

		info("Copy that node and paste to group Wiki");
		editNavigation(portalName);
		copyNode(nodeLinkToCopy);
		pasteNode(nodeLinkToPaste);
		waitForAndGetElement(nodeAfterCopy);	
		rightClickOnElement(nodeAfterCopy);
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		magAlert.acceptAlert();
		waitForElementNotPresent(nodeAfterCopy);

		info("Cut and Paste Node");
		click(nodeParentLink);
		cutNode(nodeLinkToCopy);
		pasteNode(nodeLinkToPaste);
		waitForAndGetElement(nodeAfterCopy);
		click(nodeParentLink);
		waitForElementNotPresent(nodeLinkToCopy);

		info("Clone and Paste Node");
		click(nodeLinkToPaste);
		cloneNode(nodeAfterCopy);
		pasteNode(nodeLinkToClone);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		info("Verify that a new node is created in Pages Management");
		navToolbar.goToManagePages();
		pageMag.searchPageInManagementPage(PageType.PORTAL, pageSelectorName, true);
		waitForAndGetElement(pageMag.ELEMENT_LIST_PAGE.replace("${number}", "1").replace("${titlePage}", pageSelectorName));
		waitForAndGetElement(pageMag.ELEMENT_LIST_PAGE.replace("${number}", "2").replace("${titlePage}", pageSelectorName));

		click(pageMag.ELEMENT_PAGE_DELETE_ICON_AUX.replace("${number}", "2").replace("${titlePage}", pageSelectorName));
		magAlert.acceptAlert();
		waitForElementNotPresent(pageMag.ELEMENT_PAGE_DELETE_ICON_AUX.replace("${number}", "2").replace("${titlePage}", pageSelectorName));

		info("Reset data");
		pageMag.deletePage(PageType.PORTAL, pageSelectorName);
		navToolbar.goToPortalSites();
		navMag.deleteNode(portalName, "Wiki", nodeName, false);
		navMag.deleteNode(portalName, "Documents", nodeName, false);
	}

	/**
	 * Qmetry ID: 70605
	 * Move application when edit page properties of node
	 * 
	 * Qmetry ID: 70606
	 * Add application when edit page properties of node
	 * 
	 * Qmetry ID: 70607
	 * Edit application when edit page properties of node
	 * 
	 * Qmetry ID: 70608
	 * Remove application when edit page properties of node
	 * 
	 */
	@Test
	public void test06_AddEditMoveAndRemoveApplicationWhenEditPagePropertiesOfNode(){
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName = "test06EditNode";
		String pageSelectorName = "test06pageSelector";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName);
		String portletTitle = "test06PortletTitle";

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Add a new node");
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName, true, languages, nodeName, pageSelectorName, pageSelectorName, true, true);

		info("Edit node's page: " + nodeName);
		editNavigation(portalName);
		rightClickOnElement(nodeLinkToEdit);
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
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_DELETE_NODE);
		magAlert.acceptAlert();
		waitForElementNotPresent(nodeLinkToEdit);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.PORTAL, pageSelectorName);
	}

	/**
	 * Qmetry ID: 70609
	 * Move container when edit page properties of node
	 * 
	 * Qmetry ID: 70610
	 * Add container when edit page properties of node
	 * 
	 * Qmetry ID: 70611
	 * Edit container when edit page properties of node
	 * 
	 * Qmetry ID: 70612
	 * Delete container when edit page properties of node
	 * 
	 */
	@Test
	public void test00_AddEditMoveAndDeleteContainerWhenEditPagePropertiesOfNode(){
		String portalName = "intranet";
		String nodeLink = ELEMENT_NODE_LINK.replace("${nodeLabel}", "Documents");
		String containerTitle = "test07EditContainerTitle";

		info("Go to Administration/Portal Sites");
		navToolbar.goToPortalSites();

		info("Edit node's page properties");
		editNavigation(portalName);
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
		waitForAndGetElement(ELEMENT_CONTAINER_SETTING_TAB);
		type(ELEMENT_CONTAINER_TITLE, containerTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "150px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "150px", true);
		button.save();
		waitForElementNotPresent(ELEMENT_CONTAINER_SETTING_TAB);
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		if(this.plfVersion.contains("4.0"))
			waitForAndGetElement(ELEMENT_NAME_CONTAINER.replace("${nameContainer}", containerTitle));
		else
			waitForAndGetElement(ELEMENT_NAME_CONTAINER_PLF41.replace("${nameContainer}", containerTitle));
		click(ELEMENT_SWITCH_VIEW_MODE);
		WebElement element = waitForAndGetElement(ELEMENT_EDITING_CONTAINER);
		String valueStyle = element.getAttribute("style");
		assert valueStyle.equals("width: 150px; height: 150px;"): "Failed to edit the value of container: " + containerTitle;

		info("Move a container...");
		click(ELEMENT_SWITCH_VIEW_MODE);
		pageEditor.addNewContainer("Rows Layout", "oneRow");
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "2").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", containerTitle), DEFAULT_TIMEOUT, 1, 2);

		mouseOver(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), true);
		if(this.plfVersion.contains("4.0"))
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_PORTLET_LAYOUT_DECORATOR);
		else
			dragAndDropToObject(ELEMENT_DRAG_CURRENT_CONTAINER_PLF41.replace("${nameContainer}", "Container"), ELEMENT_PORTLET_LAYOUT_DECORATOR);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", containerTitle), DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", "Container"), DEFAULT_TIMEOUT, 1, 2);

		info("Delete a container...");
		pageEditor.removeContainer(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", "Container"), ELEMENT_DELETE_CONTAINER_ICON);
		pageEditor.removeContainer(ELEMENT_NAME_CURRENT_CONTAINER.replace("${nameContainer}", containerTitle), ELEMENT_DELETE_CONTAINER_ICON);
		waitForElementNotPresent(ELEMENT_LIST_CONTAINER.replace("${number}", "1").replace("${nameContainer}", containerTitle));
		waitForElementNotPresent(ELEMENT_LIST_CONTAINER.replace("${number}", "2").replace("${nameContainer}", "Container"));
		pageEditor.finishEditLayout();		
	}
}