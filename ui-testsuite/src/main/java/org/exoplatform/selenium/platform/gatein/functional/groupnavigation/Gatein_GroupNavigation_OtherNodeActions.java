package org.exoplatform.selenium.platform.gatein.functional.groupnavigation;

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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 18/11/2013
 *
 */
public class Gatein_GroupNavigation_OtherNodeActions extends GroupNavigation{
	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement group;
	NavigationToolbar navToolbar;
	ManageAlert magAlert;
	PageEditor pageEditor;
	PageManagement pageMag;
	NavigationManagement navMag;

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
		navMag = new NavigationManagement(driver);
		magAc.signIn("john", DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		info("Gatein_Navigation_GroupNavigation_EditNavigation: Finish testing");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Copy/Paste a node into another node in the same navigation
	 * CaseID 73404
	 * Step 1: Show Edit page & Nav form
	 * Step 2: Copy/paste node
	 */
	@Test
	public void test01_CopyPasteANodeIntoAnotherNodeInTheSameNavigation() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup73404";
		String nodeName1 = "nodeTest734041";
		String nodeName2 = "nodeTest734042";
		String subNodeName = "subNode73404";
		String pageSelectorName1 = "page734041";
		String pageSelectorName2 = "page734042";
		String subPageSelectorName = "subpage734041";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeLinkToCopy = ELEMENT_NODE_LINK.replace("${nodeLabel}", subNodeName);
		String nodeLinkToPaste = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName2);
		String nodeAfterCopy = ELEMENT_CHILD_NODE_LINK.replace("${nodeLabel}", nodeName2).replace("${childNode}", subNodeName);

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
				nodeName1, true, languages, nodeName1, 
				pageSelectorName1, pageSelectorName1, true, true);
		info("Add a new node for group");
		addNodeForGroup(groupNameDisplayName, "", true, 
				nodeName2, true, languages, nodeName2, 
				pageSelectorName2, pageSelectorName2, true, true);

		info("Add a sub new node for group");
		addNodeForGroup(groupNameDisplayName, nodeName1, true, 
				subNodeName, true, languages, subNodeName, 
				subPageSelectorName, subPageSelectorName, true, true);

		/* Step 1: Show Edit page & Nav form */
		//- Login by user is manager of at least the exited group navigation
		//- Click on Group link 
		//- Select a Group navigation and click on [Edit navigation] link
		//Edit navigation form is shown properly
		editNavigation(groupNameDisplayName);

		/* Step 2: Copy/paste node */
		//- Choose a node 
		//- Right click on a node and select Copy node
		info("Copy that node and paste to another nodes");
		copyNode(nodeLinkToCopy);

		//- Select another node, right click and select Paste node
		//Node is made a copy in new place
		pasteNode(nodeLinkToPaste);
		waitForAndGetElement(nodeAfterCopy);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, subPageSelectorName);
		pageMag.deletePage(PageType.GROUP, pageSelectorName1);
		pageMag.deletePage(PageType.GROUP, pageSelectorName2);
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}

	/** Cut/Paste node to same place
	 * CaseID 73405
	 * Step 1: Show Edit page & Nav form
	 * Step 2: Cut/paste node
	 */
	@Test
	public void test02_CutPasteNodeToSamePlace() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup73405";
		String nodeName1 = "nodeTest73405";
		String subNodeName = "subNode73405";
		String pageSelectorName1 = "page73405";
		String subPageSelectorName = "subpage73405";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeLinkToCut = ELEMENT_NODE_LINK.replace("${nodeLabel}", subNodeName);
		String nodeLinkToPaste = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName1);

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
				nodeName1, true, languages, nodeName1, 
				pageSelectorName1, pageSelectorName1, true, true);

		info("Add a sub new node for group");
		addNodeForGroup(groupNameDisplayName, nodeName1, true, 
				subNodeName, true, languages, subNodeName, 
				subPageSelectorName, subPageSelectorName, true, true);

		/* Step 1: Show Edit page & Nav form */
		//- Login by user is manager of at least the exited group navigation
		//- Click on Group link 
		//- Select a Group navigation and click on [Edit navigation] link
		//Edit navigation form is shown properly
		editNavigation(groupNameDisplayName);

		/* Step 2: Cut/paste node */
		//- Right click on node and select Cut node
		//- Right click in the same level with cut node and select Paste node
		info("Cut that node and paste to another nodes");
		cutNode(nodeLinkToCut);
		pasteNode(nodeLinkToPaste);

		//Show message alerts can not do this action
		waitForAndGetElement(ELEMENT_WARNING_EXISTING_NODE);
		button.ok();
		button.closeWindow();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, subPageSelectorName);
		pageMag.deletePage(PageType.GROUP, pageSelectorName1);
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}

	/** Change order of node
	 * CaseID 73407
	 * Step 1: Show Edit Navigation form
	 * Step 2: Check move up when node is the first 
	 * Step 3: Check move down when node is the last
	 * Step 4: Move up valid node
	 * Step 5: Move down valid node
	 */
	@Test
	public void test03_ChangeOrderOfNode() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup73407";
		String parentNodeName = "parentNode73407";
		String nodeName1 = "nodeTest734071";
		String nodeName2 = "nodeTest734072";
		String parentPageSelectorName1 = "parentpage73407";
		String pageSelectorName1 = "page734071";
		String pageSelectorName2 = "page734072";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeLinkToMove1 = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName1);
		String nodeLinkToMove2 = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName2);

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
				parentNodeName, true, languages, parentNodeName, 
				parentPageSelectorName1, parentPageSelectorName1, true, true);

		info("Add a sub node for group");
		addNodeForGroup(groupNameDisplayName, parentNodeName, true, 
				nodeName1, true, languages, nodeName1, 
				pageSelectorName1, pageSelectorName1, true, true);

		info("Add a sub node for group");
		addNodeForGroup(groupNameDisplayName, parentNodeName, true, 
				nodeName2, true, languages, nodeName2, 
				pageSelectorName2, pageSelectorName2, true, true);

		/* Step 1: Show Edit Navigation form */
		//- Login by admin or user is manager of at least the exited group navigation
		//- Go to Navigation management
		//Show Navigation management form
		editNavigation(groupNameDisplayName);

		/* Step 2: Check move up when node is the first */
		//- Select a node
		//- Right click on the first node in list and select Move Up
		info("Select Move Up from the drop-down menu");
		rightClickOnElement(nodeLinkToMove1);
		click(ELEMENT_NAVIGATION_MOVE_UP_NODE);

		//Nothing happens with that node
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "1").replace("${childNode}", nodeName1));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "2").replace("${childNode}", nodeName2));

		/* Step 3: Check move down when node is the last */
		//Right click on the last node in list and select Move Down
		info("Select Move Down from the drop-down menu");
		rightClickOnElement(nodeLinkToMove2);
		click(ELEMENT_NAVIGATION_MOVE_DOWN_NODE);

		//Nothing happens with that node
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "1").replace("${childNode}", nodeName1));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "2").replace("${childNode}", nodeName2));

		/* Step 4: Move up valid node */
		//- Right click on node that is not in the first of list and select Move up
		rightClickOnElement(nodeLinkToMove2);
		click(ELEMENT_NAVIGATION_MOVE_UP_NODE);

		//That node and the node above is exchanged
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "1").replace("${childNode}", nodeName2));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "2").replace("${childNode}", nodeName1));

		/* Step 5: Move down valid node */
		//- Right click on node that is not in the last of list and select Move down
		rightClickOnElement(nodeLinkToMove2);
		click(ELEMENT_NAVIGATION_MOVE_DOWN_NODE);

		//That node and the node below is exchanged
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "1").replace("${childNode}", nodeName1));
		waitForAndGetElement(ELEMENT_LIST_NODE_LINK.replace("${nodeLabel}", parentNodeName).replace("${number}", "2").replace("${childNode}", nodeName2));
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, parentPageSelectorName1);
		pageMag.deletePage(PageType.GROUP, pageSelectorName2);
		pageMag.deletePage(PageType.GROUP, pageSelectorName1);
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}

	/** Cut/Paste a node to new place in the same navigation
	 * CaseID 73488
	 * Step 1: Show Edit page & Nav form
	 * Step 2: Cut/paste node
	 */
	@Test
	public void test04_CutPasteANodeToNewPlaceInTheSameNavigation() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup73488";
		String nodeName1 = "nodeTest734881";
		String nodeName2 = "nodeTest734882";
		String subNodeName = "subNode73488";
		String pageSelectorName1 = "page734881";
		String pageSelectorName2 = "page734882";
		String subPageSelectorName = "subpage734881";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeLinkToCut = ELEMENT_NODE_LINK.replace("${nodeLabel}", subNodeName);
		String nodeLinkToPaste = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName2);
		String nodeAfterCut = ELEMENT_CHILD_NODE_LINK.replace("${nodeLabel}", nodeName2).replace("${childNode}", subNodeName);
		String nodeBeforeCut = ELEMENT_CHILD_NODE_LINK.replace("${nodeLabel}", nodeName1).replace("${childNode}", subNodeName);

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
				nodeName1, true, languages, nodeName1, 
				pageSelectorName1, pageSelectorName1, true, true);
		info("Add a new node for group");
		addNodeForGroup(groupNameDisplayName, "", true, 
				nodeName2, true, languages, nodeName2, 
				pageSelectorName2, pageSelectorName2, true, true);

		info("Add a sub new node for group");
		addNodeForGroup(groupNameDisplayName, nodeName1, true, 
				subNodeName, true, languages, subNodeName, 
				subPageSelectorName, subPageSelectorName, true, true);

		/* Step 1: Show Edit page & Nav form */
		//- Login by admin or user is manager of at least the exited group navigation
		//- Go to Navigation management
		//Show Navigation management form
		editNavigation(groupNameDisplayName);

		/* Step 2: Cut/paste node */
		//- Choose a node 
		//- Right click on node and select Cut node 
		info("Cut that node and paste to another nodes");
		waitForAndGetElement(nodeBeforeCut);
		waitForElementNotPresent(nodeAfterCut);
		cutNode(nodeLinkToCut);

		//Node is removed from current place to new place
		pasteNode(nodeLinkToPaste);
		waitForAndGetElement(nodeAfterCut);
		waitForElementNotPresent(nodeBeforeCut);
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, subPageSelectorName);
		pageMag.deletePage(PageType.GROUP, pageSelectorName1);
		pageMag.deletePage(PageType.GROUP, pageSelectorName2);
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}

	/** Copy/Paste a node into the same place
	 * CaseID 73555
	 * Step 1: Show Edit page & Nav form
	 * Step 2: Copy/paste node
	 */
	@Test
	public void test05_CopyPasteANodeIntoTheSamePlace() {
		/*Declare variables*/
		String groupNameDisplayName = "aagroup73555";
		String nodeName1 = "nodeTest73555";
		String subNodeName = "subNode73555";
		String pageSelectorName1 = "page73555";
		String subPageSelectorName = "subpage73555";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String nodeLinkToCopy = ELEMENT_NODE_LINK.replace("${nodeLabel}", subNodeName);
		String nodeLinkToPaste = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeName1);

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
				nodeName1, true, languages, nodeName1, 
				pageSelectorName1, pageSelectorName1, true, true);

		info("Add a sub new node for group");
		addNodeForGroup(groupNameDisplayName, nodeName1, true, 
				subNodeName, true, languages, subNodeName, 
				subPageSelectorName, subPageSelectorName, true, true);

		/* Step 1: Show Edit page & Nav form */
		//- Login by user is manager of at least the exited group navigation
		//- Click on Group link 
		//- Select a Group navigation and click on [Edit navigation] link
		//Edit navigation form is shown properly
		editNavigation(groupNameDisplayName);

		/* Step 2: Cut/paste node */
		//- Right click on node and select Cut node
		//- Right click in the same level with cut node and select Paste node
		info("Copy that node and paste to another nodes");
		copyNode(nodeLinkToCopy);
		pasteNode(nodeLinkToPaste);

		//Show message alerts can not do this action
		waitForAndGetElement(ELEMENT_WARNING_EXISTING_NODE);
		button.ok();
		button.closeWindow();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, subPageSelectorName);
		pageMag.deletePage(PageType.GROUP, pageSelectorName1);
		navTool.goToGroupSites();
		deleteNavigationForGroup(groupNameDisplayName);
		navTool.goToUsersAndGroupsManagement();
		group.chooseGroupTab();
		click(By.linkText(groupNameDisplayName));
		group.deleteGroup(groupNameDisplayName, true, 60000);
	}

	/** Check Finish function after edited container of node's page
	 * CaseID 73621
	 * Step 1: Add page
	 * Step 2: Show form to edit added container
	 * Step 3: Edit container
	 * Step 4: Check  Finish
	 */
	@Test
	public void test06_CheckFinishFunctionAfterEditedContainerOfNodeSPage() {
		/*Declare variables*/
		String groupNameDisplayName = "Administration";
		String nodeNameHome ="Group Navigation";
		String pageName = "page73621";
		String categoryContainer = "Rows Layout";
		String typeContainer = "oneRow";
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", pageName);
		String parentLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeNameHome);
		String containerTitle = "title73621";

		/* Step 1: Add page */
		//Create new page by wizard
		//- Create page successfully
		//- Page is displayed with content as you designed
		info("Go to group site");
		navTool.goToGroupSites();
		info("Create page for portal with empty layout");
		navTool.goToPageCreationWizard();
		pageEditor.createNewPageEmptyLayout(pageName);

		info("Go to page edit layout");
		navTool.goToEditPageEditor();

		info("Add new container and application to page layout");
		pageEditor.addNewContainer(categoryContainer, typeContainer);
		pageEditor.finishEditLayout();
		
		/* Step 2: Show form to edit added container */
		//In Edit Page & Nav form:     
		//- Select above added page (node) 
		//- Right click and select Edit node's page
		//- Select container tab on page editor
		//- Move mouse on the container and click on Edit icon of added container on mark layer
		navTool.goToGroupSites();
		editNavigation(groupNameDisplayName);
		click(parentLinkToEdit);
		info("Edit node's page: " + pageName);
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		pageEditor.goToEditContainer(ELEMENT_DROP_TARGET_HAS_LAYOUT);

		//Edit container form appears with 2 tabs:
		//Container settings:
		//- Container Id: can not be changed
		//- Container Title, Width, Height are blank
		//Access permissions: all user with membership/group can access the container. By default, it is public for all
		assert waitForAndGetElement(ELEMENT_ID_TEXTBOX).getAttribute("readonly")!=null;
		assert waitForAndGetElement(ELEMENT_WIDTH_TEXTBOX).getAttribute("value").equals("");
		assert waitForAndGetElement(ELEMENT_HEIGHT_TEXTBOX).getAttribute("value").equals("");
		assert waitForAndGetElement(ELEMENT_TITLE_TEXTBOX).getAttribute("value").equals("");
		waitForAndGetElement(ELEMENT_CONTAINER_SETTING_TAB);
		click(ELEMENT_CONTAINER_PERMISSION_TAB);
		assert waitForAndGetElement(ELEMENT_CHECKBOX_PUBLIC_MODE, DEFAULT_TIMEOUT,1,2).isSelected();
		click(ELEMENT_CONTAINER_SETTING_TAB);
		
		/* Step 3: Edit container */
		//- Make change(s)
		//- Click Save
		//Change is saved
		type(ELEMENT_CONTAINER_TITLE, containerTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "600px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "600px", true);
		button.save();
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		waitForAndGetElement(ELEMENT_NAME_CONTAINER.replace("${nameContainer}", containerTitle));
		click(ELEMENT_SWITCH_VIEW_MODE);
		WebElement element = waitForAndGetElement(ELEMENT_EDITING_CONTAINER);
		String valueStyle = element.getAttribute("style");
		info(valueStyle);
		assert valueStyle.contains("width: 600px; height: 600px;");
		
		/* Step 4: Check  Finish */
		//- Click on Finish icon on Page editor
		//- Page editor is closed
		//- Changes on edited container above was saved
		pageEditor.finishEditLayout();
		button.save();

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageName);
		navTool.goToGroupSites();
		navMag.deleteNode(groupNameDisplayName,nodeNameHome,pageName,false);
	}
	
	/** Check Finish function after change node's page's container layout
	 * CaseID 73812
	 * Step 1: Add page
	 * Step 2: Make change for page's container layout
	 * Step 3: Check Save & Finish
	 */
	@Test
	public void test07_CheckFinishFunctionAfterchangeNodeSPageSContainerLayout() {
		/*Declare variables*/
		String groupNameDisplayName = "Administration";
		String nodeNameHome ="Group Navigation";
		String pageName = "page73812";
		String categoryContainer = "Rows Layout";
		String typeContainer1 = "oneRow";
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", pageName);
		String parentLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeNameHome);
		By elementLayout1 = By.xpath("//*[@class='UIRowContainer']/div[1]//div[@class='UIRowContainer EmptyContainer']");
		By elementLayout2 = By.xpath("//*[@class='UIRowContainer']/div[2]//div[@class='UIRowContainer EmptyContainer']");

		/* Step 1: Add page */
		//Create new page by wizard
		//- Create page successfully
		//- Page is displayed with content as you designed
		info("Go to group site");
		navTool.goToGroupSites();
		info("Create page for portal with empty layout");
		navTool.goToPageCreationWizard();
		pageEditor.createNewPageEmptyLayout(pageName);

		info("Go to page edit layout");
		navTool.goToEditPageEditor();

		info("Add new container and application to page layout");
		pageEditor.addNewContainer(categoryContainer, typeContainer1);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(elementLayout1);
		waitForElementNotPresent(elementLayout2);
		pageEditor.finishEditLayout();
		
		/* Step 2: Make change for page's container layout */
		//In Edit Page & Nav form:     
		//- Select above added page (node) 
		//- Right click and select Edit node's page
		//- Select container tab on page editor
		//- Make change for layout 
		//- Click Save
		//Change is saved
		navTool.goToGroupSites();
		editNavigation(groupNameDisplayName);
		click(parentLinkToEdit);
		info("Edit node's page: " + pageName);
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		pageEditor.addNewContainer(categoryContainer, typeContainer1);
		click(ELEMENT_SWITCH_VIEW_MODE);
		
		/* Step 3: Check Save & Finish */
		//- Click Finish icon on Page editor
		//- Page editor form is closed
		//- Changes on container layout above was saved
		waitForAndGetElement(elementLayout1);
		waitForAndGetElement(elementLayout2);
		pageEditor.finishEditLayout();
		button.save();
		
		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageName);
		navTool.goToGroupSites();
		navMag.deleteNode(groupNameDisplayName,nodeNameHome,pageName,false);
	}
	
	/** Check Finish function after edit porlet with saving page
	 * CaseID 73982
	 * Step 1: Add page
	 * Step 2: Show form to edit portlet when edit node's page
	 * Step 3: Edit portlet
	 * Step 4: Check Finish
	 */
	@Test
	public void test08_CheckFinishFunctionAfterEditPorletWithSavingPage() {
		/*Declare variables*/
		String groupNameDisplayName = "Administration";
		String nodeNameHome ="Group Navigation";
		String pageName = "page73621";
		String category = "Collaboration";
		String portletId = "Collaboration/AnswersPortlet";
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", pageName);
		String parentLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeNameHome);
		String portletTitle = "title73621";

		/* Step 1: Add page */
		//Create new page by wizard
		//- Create page successfully
		//- Page is displayed with content as you designed
		info("Go to group site");
		navTool.goToGroupSites();
		info("Create page for portal with empty layout");
		navTool.goToPageCreationWizard();
		pageEditor.createNewPageEmptyLayout(pageName);

		info("Go to page edit layout");
		navTool.goToEditPageEditor();

		info("Add new container and application to page layout");
		pageEditor.addNewPortlet(category, portletId);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		pageEditor.finishEditLayout();
		
		/* Step 2: Show form to edit portlet when edit node's page */
		//In Navigation management form: 
		//- Select node above
		//- Right click and select Edit node's page
		//- Select Application tab on Page editor
		//- Move mouse over the portlet and click Edit icon on Mark layer
		navTool.goToGroupSites();
		editNavigation(groupNameDisplayName);
		click(parentLinkToEdit);
		info("Edit node's page: " + pageName);
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		pageEditor.goToEditPortlet(ELEMENT_FRAME_CONTAIN_PORTLET);

		//Edit portlet form is shown properly with current main information in form includes:
		//- Portlet Setting
		//- Select Icon
		//- Decoration theme
		//- Access permission
		waitForAndGetElement(ELEMENT_PORLET_SETTING_TAB);
		waitForAndGetElement(ELEMENT_EDIT_MODE_TAB);
		waitForAndGetElement(ELEMENT_SELECT_ICON_TAB);
		waitForAndGetElement(ELEMENT_DECORATION_THEMES_TAB);
		waitForAndGetElement(ELEMENT_ACCESS_PERMISSION_TAB);
		
		/* Step 3: Edit portlet */
		//- Make change for current portlet
		//- Click Save in Edit portlet form
		//Change is saved & displayed as edited when open form to edit that portlet again
		click(ELEMENT_PORLET_SETTING_TAB);
		type(ELEMENT_CONTAINER_TITLE, portletTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "600px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "600px", true);
		button.saveAndClose();
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_NAME_PORTLET.replace("${portletName}", portletTitle));
		WebElement element = waitForAndGetElement(ELEMENT_PORTLET_FRAGMENT.replace("${portletName}", "UIAnswersPortlet"));
		String valueStyle = element.getAttribute("style");
		assert valueStyle.contains("width: 100%; height: 600px;"): "Failed to edit portlet: " + portletTitle;
		
		/* Step 4: Check  Finish */
		//- Click finish icon on page editor
		//- Click Save button on navigation management
		//- Edit page & Nav form is closed
		//- When shown form to edit above portlet like step 3, change was saved
		pageEditor.finishEditLayout();
		button.save();

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageName);
		navTool.goToGroupSites();
		navMag.deleteNode(groupNameDisplayName,nodeNameHome,pageName,false);
	}
	
	/** Check Finish function on Editing page after edited page portlet layout
	 * CaseID 74042
	 * Step 1: Add page
	 * Step 2: Show portlet control form for editing page
	 * Step 3: Add new portlet
	 * Step 4: Check Save & Finish
	 */
	@Test
	public void test09_CheckFinishFunctionOnEditingPageAfterEditedPagePortletLayout() {
		/*Declare variables*/
		String groupNameDisplayName = "Administration";
		String nodeNameHome ="Group Navigation";
		String pageName = "page74042";
		String category = "Collaboration";
		String portletId = "Collaboration/AnswersPortlet";
		String portletId1 = "Administration/AccountPortlet";
		String category1 = "Administration";
		String nodeLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", pageName);
		String parentLinkToEdit = ELEMENT_NODE_LINK.replace("${nodeLabel}", nodeNameHome);

		/* Step 1: Add page */
		//Create new page by wizard
		//- Create page successfully
		//- Page is displayed with content as you designed
		info("Go to group site");
		navTool.goToGroupSites();
		info("Create page for portal with empty layout");
		navTool.goToPageCreationWizard();
		pageEditor.createNewPageEmptyLayout(pageName);

		info("Go to page edit layout");
		navTool.goToEditPageEditor();

		info("Add new container and application to page layout");
		pageEditor.addNewPortlet(category, portletId);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		waitForElementNotPresent(ELEMENT_ACCOUNT_PORTLET_IN_VIEW_PAGE);
		pageEditor.finishEditLayout();
		
		/* Step 2: Show portlet control form for editing page */
		//In Navigation management form: 
		//- Select node above
		//- Right click and select Edit node's page
		//- Select Application tab on Page editor
		navTool.goToGroupSites();
		editNavigation(groupNameDisplayName);
		click(parentLinkToEdit);
		info("Edit node's page: " + pageName);
		rightClickOnElement(nodeLinkToEdit);
		click(ELEMENT_NAVIGATION_EDIT_PAGE_NODE);
		
		/* Step 3: Add new portlet */
		//Drag & drop portlet from list in left pane to page area in right pane
		//New portlet is added in page area
		pageEditor.addNewPortlet(category1, portletId1);
		click(ELEMENT_SWITCH_VIEW_MODE);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		waitForAndGetElement(ELEMENT_ACCOUNT_PORTLET_IN_VIEW_PAGE);
		
		/* Step 4: Check Save & Finish */
		//- Click Finish icon on Page editor
		//- Click Save on Navigation management
		//- Navigation management is closed
		//- Changes on Portlet layout was saved
		pageEditor.finishEditLayout();
		button.save();

		/*Clear data*/
		info("-- Clear data --");
		navToolbar.goToManagePages();
		pageMag.deletePage(PageType.GROUP, pageName);
		navTool.goToGroupSites();
		navMag.deleteNode(groupNameDisplayName,nodeNameHome,pageName,false);
	}
}
