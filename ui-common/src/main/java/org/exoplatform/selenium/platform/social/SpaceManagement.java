/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.selenium.platform.social;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by The eXo Platform SAS Author : Hoang Manh Dung
 * dunghm@exoplatform.com Nov 7, 2012
 */
public class SpaceManagement extends SocialBase {
	UserGroupManagement userGroup;
	Dialog dialog;
	Button button;
	ManageAlert magAlert;
	ActionBar actBar;
	SpaceSearch spSeach;

	//Go to My Spaces	> 
	//Add space Form
	protected  int DEFAULT_TIMEOUT = 60000;
	public final By     ELEMENT_ADDNEWSPACE_BUTTON      = By.xpath("//button[text()='Add New Space']");
	//("//a[@class='AddSpaceIcon']");
	public final By     ELEMENT_ADDNEWSPACE_FORM        = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Add New Space']");
	//("//span[@class='PopupTitle' and text()='Add New Space']");
	public final By     ELEMENT_SPACE_NAME_INPUT        = By.xpath("//input[contains(@name,'displayName')]");
	public final By     ELEMENT_SPACE_DESCRIPTION_INPUT = By.xpath("//textarea[contains(@name,'description')]");
	public final By     ELEMENT_ACCESS_TAB              = By.xpath("//*[text()='Access & Edit']");
	//("//div[contains(@class,'MiddleTab') and text()='Access & Edit']");
	public final By     ELEMENT_USER_GROUP_TAB          = By.xpath("//*[text()='Invite users from group']");
	//("//div[contains(@class,'MiddleTab') and text()='Invite users from group']");
	public final By     ELEMENT_USER_GROUP_CHECKBOX     = By.xpath("//*[@id='useExistingGroup']");

	//Edit space
	public final By     ELEMENT_SPACE_SETTING_TAB       = By.xpath("//div[contains(@class,'MiddleTab') and text()='Settings']");
	public final By     ELEMENT_CHANGE_AVATAR_BUTTON      = By.xpath("//button[text()='Change Picture']");
	public final By 	ELEMENT_UPLOAD_NAME 			= By.name("file");
	public final By 	ELEMENT_AVATAR_SAVE_BUTTON		= By.xpath("//div[@id = 'UIAvatarUploadContent']//button[text()='Save']");
	public final String MESSAGE_DELETE_SPACE            = "Cannot undo one deleted space with all its page navigations and group. Are you sure to delete this space?";
	public final String ELEMENT_VERIFY_SPACE_NAME_ACTIVITY = "//div[@class='author']/a[contains(text(),'${spaceName}')]";
	public final String ELEMENT_SPACE_MENU_ITEM = "//span[contains(text(),'${menuItem}')]";
	public final String ELEMENT_SPACE_MENU_ITEM_41 = "//span[@class='tabName' and contains(text(),'${menuItem}')]";
	public final String ELEMENT_SPACE_CURRENT_MENU_ITEM = "//li[@class='active item']//span[text()='${menuItem}']";

	//Space access
	public final By		ELEMENT_ACCESS_EDIT_VISIBLE 	= By.xpath("//span[text()='Visible']/../input[@name='UIVisibility']");
	public final By		ELEMENT_ACCESS_EDIT_HIDDEN 	= By.xpath("//span[text()='Hidden']/../input[@name='UIVisibility']");
	public final By		ELEMENT_ACCESS_EDIT_REGISTRATION_OPEN 	= By.xpath("//span[text()='Open']/../input[@name='UIRegistration']");
	public final By		ELEMENT_ACCESS_EDIT_REGISTRATION_VALIDATION 	= By.xpath("//span[text()='Validation']/../input[@name='UIRegistration']");
	public final By		ELEMENT_ACCESS_EDIT_REGISTRATION_CLOSE 	= By.xpath("//span[text()='Close']/../input[@name='UIRegistration']");
	public final By		ELEMENT_SPACE_ACCESS_INFO = By.xpath("//div[@class='spaceAccessInfo']");
	public final By		ELEMENT_SPACE_ACCESS_ALERT_SUCCESS = By.xpath("//div[@class='alert alert-success']");
	public final By 	ELEMENT_RESTRICT_SPACE_PAGE = By.xpath("//div[@class='spaceAccessBlock lockIcon']/*[text()='Restricted Area']");
	public final By 	ELEMENT_ACCESS_DENIED_SPACE_PAGE = By.xpath("//div[@class='spaceAccessBlock denyIcon']/*[text()='Access Denied.']");
	public final By 	ELEMENT_ACCESS_NOT_FOUND_SPACE_PAGE = By.xpath("//div[@class='spaceAccessBlock warningIcon']/*[text()='Space Not Found']");
	public final String ELEMENT_SPACE_BREAD = "//div[@class='name' and text()='${space}']";
	//Documents
	public final By ELEMENT_DOCUMENTS_TAB = By.id("documents");
	public final By ELEMENT_SPACE_SETTING_MENU = By.id("settings");

	//All space form
	public final String ELEMENT_SPACE_TITLE = "//*[@class='spaceTitle']/a[text()='${spaceName}']";

	//Space information
	public final String ELEMENT_SPACE_CURRENT_AVATAR = "//*[@id='UIBreadCrumbsNavigationPortlet']/*[@class='userAvt pull-left']/img[@title='${spaceName}']";
	public final String ELEMENT_SPACE_CURRENT_NAME = "//*[@id='UIBreadCrumbsNavigationPortlet']//*[@class='name' and text()='${spaceName}']";

	//porlet list
	public final By ELEMENT_SPACE_ACTIVITY_STREAM_PORTLET = By.id("UISpaceActivityStreamPortlet");
	public final By ELEMENT_SPACE_FORUM_PORTLET = By.id("UIForumPortlet");
	public final By ELEMENT_SPACE_WIKI_PORTLET = By.id("UIWikiPortlet");
	public final By ELEMENT_SPACE_DOCUMENTS_PORTLET = By.id("UIJCRExplorerPortlet");
	public final By ELEMENT_SPACE_CALENDAR_PORTLET = By.id("CalendarApplicationMinWidth");
	public final By ELEMENT_SPACE_SETTING_PORTLET = By.id("UISpaceSettingPortlet");
	public final By ELEMENT_SPACE_ANSWER_PORTLET = By.id("UIAnswersPortlet");
	public final By ELEMENT_SPACE_FAQ_PORTLET = By.id("UIFAQPortlet");
	public final By ELEMENT_SPACE_MEMBER_PORTLET = By.id("UIMembersPortlet");
	
	//Space action bar
	public final String ELEMENT_SPACE_TAB_NAME = "//*[@id='spaceMenuTab']/li['${index}']/a/span[text()='${tabName}']";
	
	public SpaceManagement(WebDriver dr, String...plfVersion){
		driver = dr;
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		userGroup = new UserGroupManagement(driver);
		dialog = new Dialog(driver);
		button = new Button(driver);
		magAlert = new ManageAlert(driver);
		actBar = new ActionBar(driver);
		spSeach = new SpaceSearch(driver);
	}

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Click on a button with a specific label
	 * 
	 * @param label : Button label
	 */
	public void clickButton(String label) {
		By button = By.xpath("//div[@class='uiAction']/*[text()='" + label + "']");
		//("//*[contains(@class,'ActionButton') and text()='" + label + "']");
		waitForAndGetElement(button);
		click(button);
	}

	/**
	 * Switch to a tab
	 * 
	 * @param label : Tab label
	 */
	public void switchTabs(By tab) {
		waitForAndGetElement(tab);
		click(tab);
	}
	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Do a action on space such as Edit, delete, ...
	 * 
	 * @param action : Action name 
	 * @param spaceName : Space name
	 */
	/*public void doAction(String action, String spaceName){
		By actionLink = By.xpath("//a[text()='" + spaceName + "']/../../../div/button[text()='" + action + "']");
				//("//a[text()='" + spaceName + "']/ancestor::div[contains(@class,'ContentBox')]//a[text()='" + action + "']");
		click(actionLink);
	}*/

	/**
	 * Migrate to PLF 4
	 * <li>Update by @author vuna2</li>
	 * Create quickly a new space
	 * 
	 * @param name : Space name
	 * @param desc : Space description
	 * 
	 */
	public void addNewSpace(String name, String desc, int... params) {
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT; 
		if (waitForAndGetElement(ELEMENT_ADDNEWSPACE_BUTTON, 3000, 0, 2) != null){
			click(ELEMENT_ADDNEWSPACE_BUTTON);
		}else {
			click(By.xpath("//*[contains(@class, 'uiIconSocSimplePlus')]"));
		}
		waitForAndGetElement(ELEMENT_ADDNEWSPACE_FORM);
		type(ELEMENT_SPACE_NAME_INPUT, name, true);
		type(ELEMENT_SPACE_DESCRIPTION_INPUT, desc, true);
		clickButton("Create");
		waitForAndGetElement(By.linkText(name), iTimeout);
		//waitForElementPresent(By.xpath("//div[contains(@class,'UISpaceName')]/a[@title='" + name + "']"),iTimeout);
		//waitForAndGetElement("//span[contains(text(),'More')]",iTimeout);
		//Utils.pause(1000);
	}

	/**
	 * Update by @author vuna2
	 * -- @param visibility
	 * -- @param registration
	 */
	/**
	 * Create a new space
	 * 
	 * @param name : Space name
	 * @param desc : Space description
	 * @param visibility : Space visibility
	 * @param registration : Space registration
	 * @param groupPath : User Group
	 * @param childGroupName : The child group to invite users
	 */
	public void addNewSpace(String name, String desc, String visibility, String registration, String groupPath, String childGroupName, int... params){
		info("-- Adding a new space --");
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
		if (waitForAndGetElement(ELEMENT_ADDNEWSPACE_BUTTON, 3000, 0, 2) != null){
			click(ELEMENT_ADDNEWSPACE_BUTTON);
		}else {
			click(By.xpath("//*[contains(@class, 'uiIconSocSimplePlus')]"));
		}

		waitForAndGetElement(ELEMENT_ADDNEWSPACE_FORM);
		type(ELEMENT_SPACE_NAME_INPUT, name, true);
		type(ELEMENT_SPACE_DESCRIPTION_INPUT, desc, true);
		switchTabs(ELEMENT_ACCESS_TAB);

		if (visibility != "") {
			info("-- Set visibility --");
			if (visibility.equals("Visible")){
				check(ELEMENT_ACCESS_EDIT_VISIBLE,2);
			}else if (visibility.equals("Hidden")){
				check(ELEMENT_ACCESS_EDIT_HIDDEN,2);
			}	
		}

		if (registration != "") {
			info("-- Set Registration --");
			if (registration.equals("Open")){
				check(ELEMENT_ACCESS_EDIT_REGISTRATION_OPEN,2);
			}else if (registration.equals("Validation")){
				check(ELEMENT_ACCESS_EDIT_REGISTRATION_VALIDATION,2);
			}else if (registration.equals("Close")){
				check(ELEMENT_ACCESS_EDIT_REGISTRATION_CLOSE,2);
			}
		}

		if (groupPath != "" && childGroupName != "") {
			switchTabs(ELEMENT_USER_GROUP_TAB);
			addUserGroupToInvite(groupPath, childGroupName);
		}
		clickButton("Create");
		waitForAndGetElement(By.linkText(name), iTimeout);
		Utils.pause(1000);
		//waitForElementPresent(By.xpath("//div[contains(@class,'UISpaceName')]/a[@title='" + name + "']"), iTimeout);
	}

	/**
	 * Add target group for a space when create new space
	 * 
	 * @param groupPath : Group path separate by a slash
	 * @param childGroupName : The child group to invite users
	 */
	public void addUserGroupToInvite(String groupPath, String childGroupName) {
		click(ELEMENT_USER_GROUP_CHECKBOX);
		userGroup.selectGroup(groupPath);
		click(By.linkText(childGroupName));
	}

	/**
	 * Delete a space
	 * 
	 * @param name : Space name
	 */
	public void deleteSpace(String name, int... params){
		info("-- Deleting Space..." + name);
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
		spSeach.searchSpaceByName(name,true);
		doAction("Delete", name);    
		magAlert = new ManageAlert(driver);
		magAlert.acceptAlert();
		if (waitForAndGetElement(button.ELEMENT_OK_BUTTON, 3000, 0, 2) != null){
			click(button.ELEMENT_OK_BUTTON);
		}
		Utils.pause(1000);
		waitForElementNotPresent(By.xpath(ELEMENT_ACTION_USER_ON_SPACE.replace("${spaceName}", name).replace("${action}", "Delete")), iTimeout);
		info(name + " was deleted successfully");
	}

	/**
	 * Update by @author vuna2
	 * add a @param uploadAvatar: boolean
	 */
	/**
	 * Edit a space
	 * @param name : Space name
	 * @param newName : New space name
	 * @param newDescription : New space description
	 * @param avatar : avatar file path
	 */
	public void editSpace(String name,String newName, String newDescription, boolean uploadAvatar, String avatar){
		gotoEditSpace(name);
		type(ELEMENT_SPACE_NAME_INPUT, newName, true);
		type(ELEMENT_SPACE_DESCRIPTION_INPUT, newDescription, true);
		if (uploadAvatar){
			changeAvatar(avatar);
		}else{
			info("-- Edit a space without changing the user's avatar --");
		}
		button.save();
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		if(name == newName){
			waitForAndGetElement("//*[contains(text(),'Updated space information successfully.')]");
			dialog.closeMessageDialog();
		}else{
			waitForAndGetElement(By.xpath(ELEMENT_VERIFY_SPACE_NAME_ACTIVITY.replace("${spaceName}", newName)));
		}
	}
	/**
	 * Go to edit form of a space
	 * @param name : Space name
	 */
	public void gotoEditSpace(String name){  
		info("-- Go to Edit space page --");
		doAction("Edit", name);
		waitForAndGetElement(ELEMENT_SPACE_SETTING_MENU,60000);    
	}
	/**
	 * Change avatar of a space
	 * @param file : File path of new avatar
	 */
	public void changeAvatar(String file){
		info("-- changeAvatar --");
		click(ELEMENT_CHANGE_AVATAR_BUTTON);
		WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_NAME, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; " +
				"arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", upload);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible'", upload);
		upload.sendKeys(Utils.getAbsoluteFilePath(file));
		Utils.pause(3000);
		info("Upload file " + Utils.getAbsoluteFilePath(file));
		switchToParentWindow();
		button.confirm();
		waitForAndGetElement(ELEMENT_AVATAR_SAVE_BUTTON);
		click(ELEMENT_AVATAR_SAVE_BUTTON);
		Utils.pause(1000);
	}

	/**
	 * Restore original data by deleting a space
	 * @author vuna2
	 * @param spaceName
	 * @param params: time for deleting a space (int)
	 */
	public void restoreData(String spaceName, int... params){
		info("-- Restore Original Data --");
		int timeToDeleteSpace = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;;
		goToMySpacePage();
		deleteSpace(spaceName, timeToDeleteSpace);
		Utils.pause(500);
	}

	/**
	 * 
	 * @author phuongdt
	 * @param item
	 * @param eItem
	 * @param viewType
	 * @param spacename
	 */
	public void addItem2ActionBarOfSpace(String item, By eItem, String spacename,Object...params){
		ActionBar actBar = new ActionBar(driver);
		String view = (String) (params.length > 0 ? params[0] : "Web");
		String tab = (String) (params.length > 1 ? params[1] : "Authoring");
		actBar.goToViewMode(view);
		if(!(actBar.isActionsOnActionBarPresent(eItem))){
			actBar.addItem2ActionBar(item, eItem, view, tab);
			goToMySpacePage();
			click(By.linkText(spacename));
			waitForAndGetElement(ELEMENT_DOCUMENTS_TAB);
			click(ELEMENT_DOCUMENTS_TAB);
			actBar.goToViewMode(view);
		}
	}

	/**
	 * Go to space menu item
	 * @author phuongdt
	 * @param menuItem
	 */
	public void goToSpaceMenu(String menuItem){
		info("-- Go To " + menuItem + " --");
		String eMenuItem = "";
		if(this.plfVersion.equalsIgnoreCase("4.1")){
			eMenuItem = ELEMENT_SPACE_MENU_ITEM_41;
		}else
			eMenuItem = ELEMENT_SPACE_MENU_ITEM;

		if(waitForAndGetElement(eMenuItem.replace("${menuItem}", menuItem),DEFAULT_TIMEOUT,0)!=null)
			click(By.xpath(eMenuItem.replace("${menuItem}", menuItem)));
		else{
			click(By.xpath(eMenuItem.replace("${menuItem}", "More")));
			String []items = menuItem.split(" ");
			if(items.length>1){
				click(By.xpath(eMenuItem.replace("${menuItem}", menuItem.split(" ")[0]+" ...")));
			}
			else{
				click(By.xpath(eMenuItem.replace("${menuItem}", menuItem)));
			}
		}	

	}

	/**
	 * Verify space menu item
	 * @author phuongdt
	 * @param menuItem
	 */
	public void verifySpaceMenu(String menuItem){
		info("-- Verify " + menuItem + " --");
		if(waitForAndGetElement(ELEMENT_SPACE_MENU_ITEM.replace("${menuItem}", menuItem),DEFAULT_TIMEOUT,0)==null){
			info("-- Click More button --");
			click(By.xpath(ELEMENT_SPACE_MENU_ITEM.replace("${menuItem}", "More")));
			String []items = menuItem.split(" ");
			if(items.length>1)
				waitForAndGetElement(ELEMENT_SPACE_MENU_ITEM.replace("${menuItem}", menuItem.split(" ")[0]+" ..."));
			else
				waitForAndGetElement(ELEMENT_SPACE_MENU_ITEM.replace("${menuItem}", menuItem));
		}
	}

	/** go to a space from my space navigation
	 * @author phuongdt
	 * @param spaceName
	 */
	public void goToSpaceFromMySpaceNavigation(String spaceName){
		info("-- Go to space "+spaceName+" --");
		click(ELEMENT_SPACE_NAVIGATION_SPACE_ITEM.replace("${spaceName}", spaceName));
		waitForAndGetElement(ELEMENT_SPACE_ACTIVITY_STREAM_PORTLET,60000,1);
	}
}

