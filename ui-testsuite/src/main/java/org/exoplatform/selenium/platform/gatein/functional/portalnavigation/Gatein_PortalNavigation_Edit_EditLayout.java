package org.exoplatform.selenium.platform.gatein.functional.portalnavigation;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.PortalManagement;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author HangNTT
 * @date 21/11/2013
 *
 */

public class Gatein_PortalNavigation_Edit_EditLayout extends PortalManagement{
	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement group;
	NavigationToolbar navToolbar;
	ManageAlert magAlert;
	PageEditor pageEditor;
	PageManagement pageMag;
	NavigationManagement navMag;
	Button button;
	Dialog dialog;

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
		dialog = new Dialog(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		info("Gatein_Navigation_GroupNavigation_EditNavigation: Finish testing");
		driver.manage().deleteAllCookies();
		driver.quit();

	}

	/** Check edited container with new title and after click Finish function 
	 * CaseID 74084, 74025
	 * Step 1: go to Sites
	 * Step 2: Show form to edit added container
	 * Step 3: Edit container
	 * Step 4: Check  Finish
	 */
	@Test
	public void test01_CheckEditedContainerWithNewTitleAndClickFinishFunction() {
		String categoryContainer = "Rows Layout";
		String typeContainer = "oneRow";
		String containerTitle = "title74084";
		String portalName = "intranet";

		/* Step 1: go to Sites */
		info("Go to sites");
		navTool.goToSites();
		/* Step 2: Go to Edit layout of portal */
		goToPortalEditLayout(portalName);
		info("Add new container and application to page layout");
		pageEditor.addNewContainer(categoryContainer, typeContainer, ELEMENT_NAVIGATION_BODY_RIGHT);

		//- Move mouse on the container and click on Edit icon of added container on mark layer
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
		type(ELEMENT_WIDTH_TEXTBOX, "300px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "300px", true);
		button.save();
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		waitForAndGetElement(ELEMENT_NAME_CONTAINER.replace("${nameContainer}", containerTitle));
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);

		/* Step 4: Check  Finish */
		//- Click on Finish icon on Page editor
		//- Page editor is closed
		//- Changes on edited container above was saved
		pageEditor.finishEditLayout();

		/*Clear data*/
		goToPortalEditLayout(portalName);
		click(ELEMENT_CONTAINER_TAB);
		pageEditor.removeContainer(ELEMENT_DROP_TARGET_HAS_LAYOUT, ELEMENT_DELETE_CONTAINER_ICON);
		waitForElementNotPresent(ELEMENT_DROP_TARGET_HAS_LAYOUT);	
		pageEditor.finishEditLayout();
	}
	
	/** Check Finish function after change container layout
	 * CaseID 74000
	 * Step 1: go to Sites
	 * Step 2: add container
	 * Step 3: Edit container
	 * Step 4: Drag&Drop container 
	 * Step 5: Check  Finish
	 */
	@Test
	public void test02_CheckFinishFunctionAfterChangeContainerLayout() {
		String categoryContainer = "Rows Layout";
		String typeContainer = "oneRow";
		String containerTitle = "title74000";
		String portalName = "intranet";
				
		/* Step 1: go to Sites */
		info("Go to sites");
		navTool.goToSites();
		/* Step 2: Add container */ 
		goToPortalEditLayout(portalName);
		info("Add new container and application to page layout");
		pageEditor.addNewContainer(categoryContainer, typeContainer, ELEMENT_NAVIGATION_BODY_RIGHT);

		//- Move mouse on the container and click on Edit icon of added container on mark layer
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
		type(ELEMENT_WIDTH_TEXTBOX, "200px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "300px", true);
		button.save();
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		waitForAndGetElement(ELEMENT_NAME_CONTAINER.replace("${nameContainer}", containerTitle));
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		mouseOver(ELEMENT_DROP_TARGET_HAS_LAYOUT, true);
		
		 /* Step 4: Drag and drop container */ 
		dragAndDropToObject(ELEMENT_DROP_TARGET_HAS_LAYOUT, ELEMENT_NAVIGATION_BODY_LEFT);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);

		/* Step 5: Check  Finish */
		//- Click on Finish icon on Page editor
		//- Page editor is closed
		//- Changes on edited container above was saved
		pageEditor.finishEditLayout();
		
		/*Clear data*/
		goToPortalEditLayout(portalName);
		click(ELEMENT_CONTAINER_TAB);
		pageEditor.removeContainer(ELEMENT_DROP_TARGET_HAS_LAYOUT,ELEMENT_DELETE_CONTAINER_ICON);
		waitForElementNotPresent(ELEMENT_DROP_TARGET_HAS_LAYOUT);	
		pageEditor.finishEditLayout();
	}
			
	/** Check when change width/height of portlet with valid value and click Finish 
	 * CaseID 74143,74180
	 * Step 1: Go to Site
	 * Step 2: Show form to added application
	 * Step 3: Edit Application
	 * Step 4: Check  Finish
	 */
	@Test
	public void test03_CheckChangeWidthHeightAndClickFinishFunctionAfterEditPortlet() {
		String category = "Collaboration";
		String portletId = "Collaboration/AnswersPortlet";
		String portletTitle = "title74180";
		String portalName = "intranet";
		
		/* Step 1: go to Sites */
		info("Go to sites");
		navTool.goToSites();
		/* Step 2: Go to Edit layout of portal */
		goToPortalEditLayout(portalName);
		info("Add application when edit layout of portal");
		pageEditor.addNewPortlet(category, portletId);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		pageEditor.goToEditPortlet(ELEMENT_PORTLET_TITLE.replace("${portletTitle}", "Answers Portlet"));

		//Edit portlet form is shown properly with current main information in form includes:
		//- Portlet Setting
		//- Select Icon
		//- Decoration theme
		//- Access permission
		waitForAndGetElement(ELEMENT_EDIT_MODE_TAB);
		waitForAndGetElement(ELEMENT_PORLET_SETTING_TAB);
		waitForAndGetElement(ELEMENT_SELECT_ICON_TAB);
		waitForAndGetElement(ELEMENT_DECORATION_THEMES_TAB);
		waitForAndGetElement(ELEMENT_ACCESS_PERMISSION_TAB);

		/* Step 3: Edit portlet */
		//- Make change for current portlet
		//- Click Save in Edit portlet form
		//Change is saved & displayed as edited when open form to edit that portlet again
		click(ELEMENT_PORLET_SETTING_TAB);
		type(ELEMENT_CONTAINER_TITLE, portletTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "300px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "400px", true);
		button.saveAndClose();
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForAndGetElement(ELEMENT_NAME_PORTLET.replace("${portletName}", portletTitle));
		WebElement element = waitForAndGetElement(ELEMENT_PORTLET_FRAGMENT.replace("${portletName}", "UIAnswersPortlet"));
		String valueStyle = element.getAttribute("style");
		assert valueStyle.contains("width: 100%; height: 400px;"): "Failed to edit portlet: " + portletTitle;

		/* Step 4: Check  Finish */
		//- Click finish icon on page editor
		//- Click Save button on navigation management
		//- Edit page & Nav form is closed
		//- When shown form to edit above portlet like step 3, change was saved
		pageEditor.finishEditLayout();
		
		/*Clear data*/
		goToPortalEditLayout(portalName);
		pageEditor.removePortlet(ELEMENT_PORTLET_TITLE.replace("${portletTitle}", portletTitle), ELEMENT_DELETE_PORTLET_ICON);
		waitForElementNotPresent(ELEMENT_PORTLET_TITLE.replace("${portletTitle}", portletTitle));
	}
	
	/** Check Finish function after Change portlet Layout 
	 * CaseID 74127
	 * Step 1: Go to Site
	 * Step 2: Show form to added application
	 * Step 3: Edit Application
	 * Step 4: Change portlet layout
	 * Step 5: Check  Finish
	 */
	@Test
	public void test04_CheckFinishFunctionAfterChangePortletLayout() {
		String category = "Collaboration";
		String portletId = "Collaboration/AnswersPortlet";
		String portletTitle = "title74127";
		String portalName = "intranet";

		/* Step 1: go to Sites */
		info("Go to sites");
		navTool.goToSites();
		/* Step 2: Go to Edit layout of portal */
		goToPortalEditLayout(portalName);
		info("Add application when edit layout of portal");
		pageEditor.addNewPortlet(category, portletId, ELEMENT_NAVIGATION_BODY_RIGHT);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForAndGetElement(ELEMENT_ANWSER_PORTLET_IN_VIEW_PAGE);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		pageEditor.goToEditPortlet(ELEMENT_PORTLET_TITLE.replace("${portletTitle}", "Answers Portlet"));

		//Edit portlet form is shown properly with current main information in form includes:
		//- Portlet Setting
		//- Select Icon
		//- Decoration theme
		//- Access permission
		waitForAndGetElement(ELEMENT_EDIT_MODE_TAB);
		waitForAndGetElement(ELEMENT_PORLET_SETTING_TAB);
		waitForAndGetElement(ELEMENT_SELECT_ICON_TAB);
		waitForAndGetElement(ELEMENT_DECORATION_THEMES_TAB);
		waitForAndGetElement(ELEMENT_ACCESS_PERMISSION_TAB);

		/* Step 3: Edit portlet */
		//- Make change for current portlet
		//- Click Save in Edit portlet form
		//Change is saved & displayed as edited when open form to edit that portlet again
		click(ELEMENT_PORLET_SETTING_TAB);
		type(ELEMENT_CONTAINER_TITLE, portletTitle, true);
		type(ELEMENT_WIDTH_TEXTBOX, "300px", true);
		type(ELEMENT_HEIGHT_TEXTBOX, "400px", true);
		button.saveAndClose();
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		waitForAndGetElement(ELEMENT_NAME_PORTLET.replace("${portletName}", portletTitle));
		WebElement element = waitForAndGetElement(ELEMENT_PORTLET_FRAGMENT.replace("${portletName}", "UIAnswersPortlet"));
		String valueStyle = element.getAttribute("style");
		assert valueStyle.contains("width: 100%; height: 400px;"): "Failed to edit portlet: " + portletTitle;
		
		/* Step 4: Drag and drop container */
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		dragAndDropToObject(ELEMENT_PORTLET_TITLE.replace("${portletTitle}", portletTitle), ELEMENT_NAVIGATION_BODY_LEFT);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		
		/* Step 4: Check  Finish */
		//- Click finish icon on page editor
		//- Click Save button on navigation management
		//- Edit page & Nav form is closed
		//- When shown form to edit above portlet like step 3, change was saved
		pageEditor.finishEditLayout();

		/*Clear data*/
		goToPortalEditLayout(portalName);
		pageEditor.removePortlet(ELEMENT_PORTLET_TITLE.replace("${portletTitle}", portletTitle), ELEMENT_DELETE_PORTLET_ICON);
		waitForElementNotPresent(ELEMENT_PORTLET_TITLE.replace("${portletTitle}", portletTitle));
	}
	
	/** Check when drag & drop Page Body in edit current portal
	 * CaseID 73974
	 * Step 1: Go to Site
	 * Step 2: Show form to added application
	 * Step 3: Drag&Drop Pagebody
	 * Step 4: Add new container
	 * Step 5: Drag&Drop Pagebody into container
	 * PENDING: can't locate container to remove container at step 5
	 * Refer: https://jira.exoplatform.org/browse/FQA-1521
	 */
	@Test (groups="pending")
	public void test05_CheckDragDropPageBodyInCurrentPortal() {
		String categoryContainer = "Rows Layout";
		String typeContainer = "oneRow";
		String portalName = "intranet";
		
		String deletePageBoy = "This component contains PageBody. Can not delete !";
		 /*Step 1: go to Sites */
		info("Go to sites");
		navTool.goToSites();
		/* Step 2: Go to Edit layout of portal */
		goToPortalEditLayout(portalName);
		/* Step 3: Drag and drop PageBody */
		dragAndDropToObject(ELEMENT_DROP_TARGET_NO_LAYOUT_PORTAL, ELEMENT_NAVIGATION_BODY_LEFT);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		click(ELEMENT_SWITCH_VIEW_MODE_PORTAL);
		/*Step 4: Add new container */
		info("Add new container and application to page layout");
		pageEditor.addNewContainer(categoryContainer, typeContainer, ELEMENT_DROP_TARGET_HAS_LAYOUT);
		/* Step 5: Add Page Body into Container */
		click(ELEMENT_APPLICATION_TAB);
		dragAndDropToObject(ELEMENT_DROP_TARGET_NO_LAYOUT_PORTAL, ELEMENT_DROP_TARGET_HAS_LAYOUT);
		click(ELEMENT_CONTAINER_TAB);
		pageEditor.removeContainer(ELEMENT_PAGE_PORTAL,ELEMENT_DELETE_CONTAINER_ICON,false);
		waitForMessage(deletePageBoy);
		button.ok();
		pageEditor.finishEditLayout();
		
		/*Clear data*/
		goToPortalEditLayout(portalName);
		click(ELEMENT_APPLICATION_TAB);
		dragAndDropToObject(ELEMENT_DROP_TARGET_NO_LAYOUT_PORTAL, ELEMENT_NAVIGATION_BODY_LEFT);
		click(ELEMENT_CONTAINER_TAB);
		pageEditor.removeContainer(ELEMENT_NAVIGATION_BODY_RIGHT,ELEMENT_DELETE_CONTAINER_ICON);
		click(ELEMENT_APPLICATION_TAB);
		dragAndDropToObject(ELEMENT_DROP_TARGET_NO_LAYOUT_PORTAL, ELEMENT_DROP_TARGET_HAS_LAYOUT);
		pageEditor.finishEditLayout();
	}
	/** Edit current portal with no one for edit right
	 * CaseID 73764
	 * Step 1: Go to Site
	 * Step 2: Go to Site Config
	 * Step 3: Delete edit permission
	 
	 */
	@Test
	public void test06_CheckEditCurrentPortalWithNoEditRight() {
		
		String portalName = "intranet";
		String DeleteEditPermision = "The ${field} list can not be empty.";
		
		/* Step 1: go to Sites */
		info("Go to sites");
		navTool.goToSites();
		/* Step 2: Go to Edit Configuration of portal */
		goToEditSiteConfiguration(portalName);
		click(ELEMENT_PERMISSION_SETTING_TAB);
		click(ELEMENT_EDIT_PERMISSION_SETTING);
		click(By.xpath("//a[text()='Delete Permission']"));
		button.save();
		waitForMessage(DeleteEditPermision.replace("${field}", "\"Edit Permission Settings\""));
		button.ok();
		button.cancel();
	}
}
