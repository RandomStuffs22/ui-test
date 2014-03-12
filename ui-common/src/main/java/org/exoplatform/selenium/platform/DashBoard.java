package org.exoplatform.selenium.platform;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.NavigationToolbar;

import static org.exoplatform.selenium.TestLogger.info;

import java.awt.event.KeyEvent;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class DashBoard extends PlatformBase {

	NavigationToolbar nav = new NavigationToolbar(driver);
	Dialog dialog = new Dialog(driver);

	/* Dashboard Page*/
	public  final String MESSAGE_DRAG_GADGETS_HERE = "Drag your gadgets here.";
	public  final By ELEMENT_ADD_GADGETS_LINK = By.xpath("//a[text()='Add Gadgets']");
	public 	final By ELEMENT_DASHBOARD_FRAME = By.xpath("//iframe[contains(@id,'remote_iframe_')]");

	// Gadget Directory form
	public final By ELEMENT_GADGET_URI_INPUT = By.xpath("//input[@id='url']");
	public final By ELEMENT_ADD_GADGET_BUTTON = By.xpath("//img[@title='Add Gadget']");
	public final By ELEMENT_GADGET_CONTAINER = By.xpath("//*[@id='GadgetContainer']//*[text()='Drag your gadgets here.']");
	public final String ELEMENT_GADGET_ON_CONTAINER = "//*[@id='GadgetContainer']//*[text()='${name}']";
	public final String ELEMENT_GADGET_ON_CONTAINER_PLF41 = "//div[@class='GadgetTitle' and @title='${name}']";
	public final By ELEMENT_CLOSE_ADD_GADGET_WINDOW = By.xpath("//*[@id='UIDashboardPortlet']//*[@title='Close Window']");
	public final String ELEMENT_GADGET_NAME = "//*[@title='${name}']";
	public final String ELEMENT_ACTION_ON_GADGET = "//span[text()='${gadgetTitleDisplay}']/..//*[@data-original-title='${action}']/i";
	public final By ELEMENT_GADGET_CONTENT_FORM = By.className("UIGadgetContent");
	public final String ELEMENT_GADGET_TITLE = "//*[@class='gadgetTitle' and text()='${title}']";
	public final String ELEMENT_GADGET_LOCATOR = "//span[@class='gadgetTitle' and text()='${gadgetName}']";

	public final By ELEMENT_RSS_READER_EDIT_BUTTON = By.xpath("//span[@class='gadgetTitle' and text()='RSS Reader']/parent::h5[@class='title gadgetControl clearfix']/span[@class='pull-right']//*[@class='uiIconEdit uiIconLightGray']");
	public final By ELEMENT_RSS_READER_NUM_INPUT = By.id("m_0_0");
	public final By ELEMENT_RESS_READER_SAVE_BUTTON = By.xpath("//input[@value='Save']");

	/*------------- Data for Dashboard tab --------------------------------*/
	public final String ELEMENT_DASHBOARD_NEW_ICON = "//*[@id='UITabPaneDashboard']//*[@class='uiIconSimplePlusMini uiIconLightGray']";
	public final String ELEMENT_DASHBOARD_NEW_INPUT = "//div[@id='UITabPaneDashboard']//input";
	public final String ELEMENT_DASHBOARD_SELECTED_PAGE_WITH_SPECIFIED_NAME = "//div[@id='UITabPaneDashboard']//span[text()='${dashboardName}']";
	public final String ELEMENT_DASHBOARD_SELECTED = "//div[contains(@class, 'SelectedTab')]//span";
	public final String ELEMENT_DASHBOARD_SELECTED_DELETE = "//*[@id='UITabPaneDashboard']//*[@class='uiIconClose uiIconLightGray']";
	public final String ELEMENT_DASHBOARD_HOME_TAB = "div[@class='UITab SelectedTab']";
	public final String ELEMENT_TAB_LINK = "//*[@id='UITabPaneDashboard']//*[text()='${tabName}']";
	/*------------ End of data for Dashboard tab --------------------------*/

	//Add new page on Dashboard
	public void addNewTabOnDashboard(String displayName, boolean verify) {

		info("--Add new page on Dashboard--");
		click(ELEMENT_DASHBOARD_NEW_ICON);
		Utils.pause(1000);
		waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT).clear();
		waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT).sendKeys(displayName);
		for(int repeat=0;;repeat++){
			Utils.javaSimulateKeyPress(KeyEvent.VK_ENTER);
			if(waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT,5000,0) != null){
				if (waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT).getText().equals(displayName))
					break;
			}else
				break;
			if(repeat > 5) break;
		}

		
		if (verify) {
			waitForAndGetElement(ELEMENT_TAB_LINK.replace("${tabName}", displayName));
		}
	}

	//Add new page in Dashboard with Editor
	public void addNewTabOnDashboardWithEditor(String nodeName, boolean extendedLabelMode, String displayName, 
			String language, String categoryTitle, Map<String, String> portletIds, boolean verify){

		nav.goToPageCreationWizard();
		type(ELEMENT_INPUT_NODE_NAME, nodeName, true);
		WebElement element = waitForAndGetElement(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
		if (extendedLabelMode){
			Assert.assertTrue(element.isSelected());
			select(ELEMENT_SELECT_LANGUAGE, language);
		}else {
			uncheck(ELEMENT_CHECKBOX_EXTENDED_LABEL_MODE);
			type(ELEMENT_INPUT_PAGE_DISPLAY_NAME, displayName, true);
		}
		click(ELEMENT_PAGE_EDITOR_NEXT_STEP);
		waitForTextPresent("Empty Layout");
		click(ELEMENT_PAGE_EDITOR_NEXT_STEP);

		String category = ELEMENT_EDIT_PAGE_CATEGORY_MENU.replace("${categoryLabel}", categoryTitle);
		click(category);

		for (String portletId : portletIds.keySet()) {
			String elementEditPagePage = ELEMENT_EDIT_PAGE_PAGE;
			//String verification = PORTLET_LABEL.replace("${portletName}", portletIdsAndVerifications.get(portletId));
			dragAndDropToObject("//div[@id='" + portletId + "']//img", elementEditPagePage);
		}
		Utils.pause(500);
		click(ELEMENT_PAGE_FINISH_BUTTON);
		waitForTextNotPresent("Page Editor");
		if (verify) {
			waitForAndGetElement("//span[text()='" + nodeName + "']");
		}
	}

	//Edit a tab name
	public void editTabNameOnDashboard(String currentName, String newName) {
		info("--Edit page name on dashboard--");

		click(ELEMENT_TAB_LINK.replace("${tabName}", currentName));
		Utils.pause(1000);
		waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT).clear();
		waitForAndGetElement(ELEMENT_DASHBOARD_NEW_INPUT).sendKeys(newName);
		Utils.javaSimulateKeyPress(KeyEvent.VK_ENTER);

		waitForAndGetElement(ELEMENT_TAB_LINK.replace("${tabName}", newName));
		waitForElementNotPresent(ELEMENT_TAB_LINK.replace("${tabName}", currentName));
	}

	//Delete a tab
	public void deleteTabOnDashboard(String currentName){
		alert = new ManageAlert(driver);
		info("--Delete selected page on dashboard--");
		WebElement tab = waitForAndGetElement(By.linkText(currentName), 10000, 0);

		if(tab != null){ 
			mouseOverAndClick(By.linkText(currentName));
		}
		click(ELEMENT_DASHBOARD_SELECTED_DELETE);
		alert.waitForConfirmation("Really want to remove this dashboard?");
		alert.acceptAlert();
		waitForElementNotPresent(ELEMENT_TAB_LINK.replace("${tabName}", currentName));
	}

	/*
	 * Comment by PhuongDT
	 * Date: 30/09/2013
	 * This function is replaced by function actionOnGadgetOnDashboard
	 */
	//	public void deleteGadgetOnDashboard(String gadgetTitleDisplay)
	//	{	alt = new ManageAlert(driver);
	//		info("Delete gadget");
	//		String action = "Delete Gadget";
	//		By deleteGadgetIcon = By.xpath(ELEMENT_ACTION_ON_GADGET.replace("${gadgetTitleDisplay}", gadgetTitleDisplay).replace("${action}", action));
	//		waitForAndGetElement(deleteGadgetIcon);
	//		click(deleteGadgetIcon, 2);
	//		alt.waitForConfirmation("Are you sure you want to delete this gadget?");
	//		waitForTextNotPresent(gadgetTitleDisplay);
	//	}

	public void dragDropGadget(String gadget){
		info("Drag drop " + gadget + " gadget to dashboard");
		click(ELEMENT_ADD_GADGETS_LINK);
		dragAndDropToObject(By.xpath(ELEMENT_GADGET_NAME.replace("${name}", gadget)), ELEMENT_GADGET_CONTAINER);
		waitForAndGetElement(ELEMENT_GADGET_ON_CONTAINER.replace("${name}", gadget));
	}

	public void addNewGadget(String url, String name){
		info("Add new gadget to dashboard");
		click(ELEMENT_ADD_GADGETS_LINK);
		type(ELEMENT_GADGET_URI_INPUT, url, true);
		click(ELEMENT_ADD_GADGET_BUTTON);
		//waitForTextPresent(name);
		waitForAndGetElement(ELEMENT_GADGET_TITLE.replace("${title}", name));
	}

	/**
	 * do action on gadget: maximize, minimize
	 * @author phuongdt
	 * @param: gadgetTitleDisplay
	 * @param: action (Minimize, Maximize, Delete Gadget, Edit Gadget, Restore Down)
	 */
	public void actionOnGadgetOnDashboard(String gadgetTitleDisplay, String action){
		info(action+" gadget" + gadgetTitleDisplay);
		Boolean isDisplay = false;
		button = new Button(driver);
		if(waitForAndGetElement(ELEMENT_GADGET_CONTENT_FORM, DEFAULT_TIMEOUT,0)!=null){
			isDisplay = true;
		}
		By actionGadgetIcon = By.xpath(ELEMENT_ACTION_ON_GADGET.replace("${gadgetTitleDisplay}", gadgetTitleDisplay).replace("${action}", action));
		waitForAndGetElement(actionGadgetIcon);
		click(actionGadgetIcon, 2);
		if(action.contains("Delete Gadget")){
			alert = new ManageAlert(driver);
			alert.waitForConfirmation("Are you sure you want to delete this gadget?");
			//waitForTextNotPresent(gadgetTitleDisplay);
			waitForElementNotPresent(ELEMENT_GADGET_TITLE.replace("${title}", gadgetTitleDisplay));
		}
		else if (action.contains("Minimize")){
			if(isDisplay)
				waitForElementNotPresent(ELEMENT_GADGET_CONTENT_FORM);
			else
				waitForAndGetElement(ELEMENT_GADGET_CONTENT_FORM);
		}
		else if (action.contains("Maximize")){
			waitForAndGetElement(By.xpath(ELEMENT_ACTION_ON_GADGET.replace("${gadgetTitleDisplay}", gadgetTitleDisplay).replace("${action}", "Restore Down")));
		}
		else if (action.contains("Edit Gadget")){
			waitForAndGetElement(ELEMENT_RESS_READER_SAVE_BUTTON);
		}
		else{//(action.contains("Restore Down"))
			waitForAndGetElement(By.xpath(ELEMENT_ACTION_ON_GADGET.replace("${gadgetTitleDisplay}", gadgetTitleDisplay).replace("${action}", "Maximize")));
		}
	}

	/**
	 * Edit RSS Reader gadget on Dashboard
	 * @author havtt
	 * @date 11-Nov-2013
	 * 
	 * @param numRSSdisplay
	 */
	public void editRSSReaderGadgetonDashboard(String gadgetTitleDisplay,String numRSSdisplay){
		info("----Open edit form of gadget----");
		actionOnGadgetOnDashboard(gadgetTitleDisplay,"Edit Gadget");
		Utils.pause(3000);
		info("----Edit number of RSS displayed----");
		type(ELEMENT_RSS_READER_NUM_INPUT,numRSSdisplay, true);
		info("----Save the change----");
		click(ELEMENT_RESS_READER_SAVE_BUTTON);
	}
}
