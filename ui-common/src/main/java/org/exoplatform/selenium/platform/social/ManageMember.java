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

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by The eXo Platform SAS Author : Hoang Manh Dung
 * dunghm@exoplatform.com Nov 9, 2012
 */
public class ManageMember extends SpaceManagement {

	public ManageMember(WebDriver dr, String...plfVersion){
		super(dr);
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		magAcc = new ManageAccount(driver,this.plfVersion);
		nav = new NavigationToolbar(driver);
	}

	ManageAccount magAcc;
	NavigationToolbar nav;

	//Go to My Spaces > Select a space > Settings
	//Member Tab 
	public final By ELEMENT_MEMBER_TAB = By.xpath("//a[text()='Members']");
	public final By ELEMENT_SELECT_MEMBER_BUTTON = By.xpath("//i[@class='uiIconUser uiIconLightGray']");
	public final By ELEMENT_INVITE_MEMBER_BUTTON = By.xpath("//*[text()='Invite']");
	public final By ELEMENT_INVITE_MEMBER_BUTTON_AUX = By.xpath("//*[@id='UISpaceMember']/*[@title='Invite']");
	public final By ELEMENT_SELECT_MEMBER_FORM   = By.xpath("//span[contains(@class,'PopupTitle') and contains(text(),'Select Users')]");
	public final String ELEMENT_MEMBERS_TABLE = "//th[contains(text(),'Members')]/ancestor::table";
	public final String ELEMENT_PENDING_TABLE = "//th[contains(text(),'Pending')]/ancestor::table";
	public final String ELEMENT_INVITED_TABLE = "//th[contains(text(),'Invited')]/ancestor::table";
	public final String ELEMENT_SELECTED_USER_BOX = "//span[text()='${username}']/../..//input[@class='checkbox']";
	//Adapt to plf4.1.0
	public final String ELEMENT_SELECTED_USER_BOX_PLF4_1 = "//span[@class='text' and contains(text(),'${username}')]/../..//input[@class='checkbox']";	
	public final String ELEMENT_GRAND_MANAGER_BUTTON = ELEMENT_MEMBERS_TABLE + "/..//td[contains(text(),'${username}')]/..//*[@class='iPhoneCheckHandle']";
	public final String ELEMENT_IS_MANAGER_ICON = ELEMENT_MEMBERS_TABLE + "/..//td[contains(text(),'${username}')]/..//*[@class='iPhoneCheckHandle' and @style='left: 41px;']";
	public final String ELEMENT_IS_NOT_MANAGER_ICON = ELEMENT_MEMBERS_TABLE + "/..//td[contains(text(),'${username}')]/..//*[@class='iPhoneCheckHandle' and @style='left: 1px;']";
	public final String ELEMENT_MEMBER_USER_ITEM = "//th[contains(text(),'Members')]/ancestor::table//td[contains(text(),'${userName}')]";
	public final String ELEMENT_INVITED_USER_ITEM = "//th[contains(text(),'Invited')]/ancestor::table//td[contains(text(),'${userName}')]";
	public final String ELEMENT_PENDING_USER_ITEM = "//th[contains(text(),'Pending')]/ancestor::table//td[contains(text(),'${userName}')]";
	public final String ELEMENT_REMOVE_USER_BUTTON = ELEMENT_MEMBERS_TABLE + "/..//td[contains(text(),'${userName}')]/..//i[@class='uiIconDelete uiIconLightGray']";
	public final String ELEMENT_PENDING_VALIDATE_BUTTON = ELEMENT_PENDING_TABLE+ "/..//td[contains(text(),'${userName}')]/..//i[@class='uiIconValidate uiIconLightGray']";
	public final String ELEMENT_PENDING_DECLINE_BUTTON = ELEMENT_PENDING_TABLE+ "/..//td[contains(text(),'${userName}')]/..//i[@class='uiIconRemove uiIconLightGray']";

	//Verify message for user is manager of space  
	public final String VERIFY_MESSAGE = "You are the last manager of this space. You need to promote another member as manager of the space before you can leave it.";

	//Warning message: users have already existed in the space
	public final String MESSAGE_USER_EXISTED_IN_SPACE = "Some users have already existed in the space, including:" + " ${username}";
	public final String MESSAGE_USER_EXISTED_IN_INVITING_LIST = "Some users have already existed in the inviting list, including:" + " ${username}";
	public final String ELEMENT_LEFT_PANEL_SPACE_ITEM = "//li[contains(@class,'spaceItem')]/a[@title='${spaceName}']";
	
	//Adapt to PLF 4.1
	public final String ELEMENT_LEFT_PANEL_SPACE_ITEM_PLF4_1 = "//li[contains(@class,'spaceItem')]/a/span[@data-original-title='${spaceName}']";


	/*-------------------------------- Common functions for SOCIAL -----------------------------------*/

	public By getCheckBox(String text) {
		By checkbox = By.xpath("//div[@class='Text' and contains(text(),'" + text
				+ "')]/ancestor::tr//input[@type='checkbox']");
		return checkbox;
	}

	/**
	 * Invite a single user
	 * 
	 * @param name : username or name of a member
	 */
	public void inviteSingleUser(String name, String...params ) {
		String member = "//th[text()='Members']/ancestor::table//td[text()='Root Root']";  
		selectUser(name);
		if (name.equals(USER_ROOT)){
			info("--------------------------------");
			waitForAndGetElement(By.xpath(member));
		}else{
			if(params.length > 0){    	
				name = params[0];
			}
			click(ELEMENT_INVITE_MEMBER_BUTTON);
			waitForAndGetElement(By.xpath(ELEMENT_INVITED_TABLE + "//td[contains(text(),'" + name + "')]"));
		}
	}

	/**
	 * Select user by name
	 * @param name : user's name
	 */
	public void selectUser(String name){    
		String user1 = "//input[@id='user' and contains(@value,'${user}')]";
		String user2 = user1.replace("${user}", name.toLowerCase());
		click(ELEMENT_SELECT_MEMBER_BUTTON);
		waitForAndGetElement(ELEMENT_SELECT_MEMBER_FORM);
		check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${username}", name)),2);
			clickButton("Add");
			if (name.equals("Jack")){
				waitForAndGetElement(By.xpath(user1.replace("${user}", DATA_USER4)));
			}else{
				waitForAndGetElement(By.xpath(user2));
			}
			if (name.equals(USER_ROOT)){
				//waitForElementPresent(By.xpath(user1.replace("${user}", DATA_USER4)));
				info("-- Root is a super user so we don't need to add him as a member --");
			}else{
				waitForAndGetElement(By.xpath(user2));
			}		
	}

	/**
	 * Invite multi users
	 * 
	 * @param userlist : list user separate by a comma
	 */
	public void inviteMultiUser(String userlist) {
		String[] userArr = userlist.split(",");
		int len = userArr.length;
		for (int i = 0; i < len; i++) {
			selectUser(userArr[i].trim());
		}    
		click(ELEMENT_INVITE_MEMBER_BUTTON);
		for (int i = 0; i < len; i++) {
			waitForAndGetElement(By.xpath(ELEMENT_INVITED_TABLE + "//td[contains(text(),'" + userArr[i].trim() + "')]"));
			Utils.pause(500);
		}
	}

	/**
	 * Accept a space join invitation
	 * @param spaceName : space name
	 */
	public void acceptInvitation(String spaceName) {
		goToInvitationReceives();
		doAction("Accept", spaceName);
		if(this.plfVersion.equalsIgnoreCase("4.0"))
			waitForAndGetElement(By.xpath(ELEMENT_LEFT_PANEL_SPACE_ITEM.replace("${spaceName}", spaceName)));
		else if(this.plfVersion.equalsIgnoreCase("4.1")){
			waitForAndGetElement(By.xpath(ELEMENT_LEFT_PANEL_SPACE_ITEM_PLF4_1.replace("${spaceName}", spaceName)));
		}
	}

	/**
	 * Reject a space join invitation
	 * @param spaceName : space name
	 */
	public void ignoreInvitation(String spaceName) {
		goToInvitationReceives();
		doAction("Ignore", spaceName);
		waitForElementNotPresent(By.xpath(ELEMENT_LEFT_PANEL_SPACE_ITEM.replace("${spaceName}", spaceName)));
	}

	/**
	 * Request to join a space
	 * @param spaceName
	 */
	public void requestToJoin(String spaceName){
		String actionName = "Request to Join";
		doAction(actionName, spaceName);
		By actionLink = By.xpath(ELEMENT_ACTION_USER_ON_SPACE.replace("${spaceName}", spaceName).replace("${action}", actionName));
		waitForElementNotPresent(actionLink, DEFAULT_TIMEOUT,1);
	}

	/**
	 * Validate a join space request
	 * 
	 * @param name : user's name
	 */
	public void validateInvitation(String name) {    
		click(ELEMENT_PENDING_VALIDATE_BUTTON.replace("${userName}", name));
		waitForAndGetElement(By.xpath(ELEMENT_MEMBER_USER_ITEM.replace("${userName}", name)));
	}

	/**
	 * Decline a join space request
	 * 
	 * @param name : user's name
	 */
	public void declineInvitation(String name) {
		click(ELEMENT_PENDING_DECLINE_BUTTON.replace("${userName}", name));
		waitForElementNotPresent(By.xpath(ELEMENT_MEMBER_USER_ITEM.replace("${userName}", name)));
	}

	/**
	 * Grant Manger Permission for a space member
	 * @param name : User's name
	 */
	public void grantManager(String name) {
		waitForElementNotPresent(By.xpath(ELEMENT_IS_MANAGER_ICON.replace("${username}", name)));
		click(By.xpath(ELEMENT_GRAND_MANAGER_BUTTON.replace("${username}", name)));
		waitForAndGetElement(By.xpath(ELEMENT_IS_MANAGER_ICON.replace("${username}", name)));
	}

	/**
	 * Revoke manager permission of a space member
	 * @param name : User's name
	 */
	public void revokeManager(String name) {
		waitForElementNotPresent(By.xpath(ELEMENT_IS_NOT_MANAGER_ICON.replace("${username}", name)));
		click(By.xpath(ELEMENT_GRAND_MANAGER_BUTTON.replace("${username}", name)));
		waitForAndGetElement(By.xpath(ELEMENT_IS_NOT_MANAGER_ICON.replace("${username}", name)));
	}

	/**
	 * Remove a member from space
	 * 
	 * @param name : User's name
	 */
	public void removeMember(String name) {
		waitForAndGetElement(By.xpath(ELEMENT_REMOVE_USER_BUTTON.replace("${userName}", name)));
		click(By.xpath(ELEMENT_REMOVE_USER_BUTTON.replace("${userName}", name)));
		waitForElementNotPresent(By.xpath(ELEMENT_MEMBER_USER_ITEM.replace("${userName}", name)));
	}

	/**
	 * @author vuna2
	 * @param spaceName: name of Space (String)
	 * @param params
	 */
	public void joinOpenSpace(String spaceName, int...params){
		info("-- Joining the open space: " + spaceName);
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
		goToAllSpaces();
		doAction("Join", spaceName);
		waitForAndGetElement(By.xpath("//*[@class='spaceTitle']/text()['(Member)']/../a[text()='"+ spaceName +"']"), iTimeout);
		Utils.pause(1000);
	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of Space (String)
	 * @param params
	 */
	public void joinOpenSpace(ManageAccount.userType user, String spaceName, int...params){
		magAcc = new ManageAccount(driver,this.plfVersion);
		info("-- Joining the open space: " + spaceName);
		int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + user);
		}	  
		magAcc.userSignIn(user);
		goToAllSpaces();
		doAction("Join", spaceName);
		waitForAndGetElement(By.xpath("//*[@class='TitleContent']/text()['(Member)']/../a[text()='"+ spaceName +"']"), iTimeout);
		Utils.pause(1000);
		click(By.xpath("//*[@id='UIManageAllSpaces']//*[text()= '"+ spaceName +"']"));
		verifyUserJoinedSpace(user);
	}

	//////
	// Common code
	// Social/Space/Manage member

	/*------------------------ Common code for Manager of Space ------------------------------*/
	/**
	 * @author vuna2
	 * Invite an user to join a space
	 * @param userName: type: Root, Admin, Author, Developer or Publisher 
	 */
	public void inviteSingleUser(ManageAccount.userType userName){
		info("-- Invite the user: " + userName + " to join our space");
		click(ELEMENT_SELECT_MEMBER_BUTTON);
		waitForAndGetElement(ELEMENT_SELECT_MEMBER_FORM);
		switch (userName) {
		case ROOT:
			addUserToSpace(true, "Root");
			break;
		case ADMIN:
			addUserToSpace(false, "John");
			break;
		case AUTHOR:
			addUserToSpace(false, "James");
			break;	
		case DEVELOPER:
			addUserToSpace(false, "Jack");
			break;	
		case PUBLISHER:
			addUserToSpace(false, "Mary");
			break;	
		default:
			break;
		}
		Utils.pause(1000);
	}

	/**
	 * @author vuna2
	 * Action: add an user (used in the method: inviteSingleUser)
	 * @param userRoot: boolean
	 * @param userName: example: John or Mary, etc...
	 */
	public void addUserToSpace(boolean userRoot, String userName){
		info("-- Action: adding the user: " + userName);
		if (userRoot){
			if(this.plfVersion.equals("4.0"))
				check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${username}", userName)),2);
			else if(this.plfVersion.equals("4.1"))
				check(By.xpath(ELEMENT_SELECTED_USER_BOX_PLF4_1.replace("${username}", userName)),2);
			button.add();
			click(ELEMENT_INVITE_MEMBER_BUTTON);
			waitForAndGetElement(By.xpath(ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'"+ userName +"')]"));
		}else{
			if(this.plfVersion.equals("4.0"))
				check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${username}", userName)),2);
			else if(this.plfVersion.equals("4.1"))
				check(By.xpath(ELEMENT_SELECTED_USER_BOX_PLF4_1.replace("${username}", userName)),2);
			button.add();
			click(ELEMENT_INVITE_MEMBER_BUTTON);
			//click(ELEMENT_INVITE_MEMBER_BUTTON_AUX);
			waitForAndGetElement(By.xpath(ELEMENT_INVITED_TABLE + "//td[contains(text(),'"+ userName +"')]"));
		}
	}

	/**
	 * @author vuna2
	 * Action: add an user to a space
	 * @param spaceName: name of Space (String)
	 * @param userRoot: boolean
	 * @param userName: name of user (Jack, James, Mary, etc...)
	 */
	public void addUserToSpace(String spaceName, boolean userRoot, String userName){
		info("-- Action: adding the user: " + userName);
		goToMembers(spaceName);
		click(ELEMENT_SELECT_MEMBER_BUTTON);
		waitForAndGetElement(ELEMENT_SELECT_MEMBER_FORM);
		if (userRoot){
			check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${username}", userName)),2);
			button.add();
			click(ELEMENT_INVITE_MEMBER_BUTTON);
			waitForAndGetElement(By.xpath(ELEMENT_MEMBER_USER_ITEM.replace("${userName}", userName)));
		}else{
			check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${username}", userName)),2);
			button.add();
			click(ELEMENT_INVITE_MEMBER_BUTTON);
			waitForAndGetElement(By.xpath(ELEMENT_INVITED_USER_ITEM.replace("${userName}", userName)));
		}
	}

	/**
	 * @author vuna2
	 * Manger of space give an invitation to an user
	 * @param manager: (type: Root, Admin, etc...)
	 * @param spaceName: name of space (String)
	 * @param user: name of the invited (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void managerInviteUserToJoinSpace(ManageAccount.userType manager, String spaceName, ManageAccount.userType user, Boolean... params){
		magAcc = new ManageAccount(driver,this.plfVersion);
		Boolean isLogin = params.length > 0 ? params[0] : true; 
		info("-- Invite an user to join: " + spaceName);
		if (isLogin){
			magAcc.signOut();
			magAcc.userSignIn(manager);
		}		
		goToMySpacePage();
		gotoEditSpace(spaceName);
		goToMembers();
		inviteSingleUser(user);
	}

	/**
	 * @author hangntt
	 * Grant manager for user of space
	 * @param spaceName
	 * @param user: name of user who granted (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void grantManagerForUser (String spaceName, String name){
		info("Grant manager for user : "+ spaceName);
		goToMySpacePage();
		gotoEditSpace(spaceName);
		goToMembers();
		grantManager(name);
	}

	/**
	 * @author hangntt
	 * Revoke Manager for user of space
	 * @param spaceName
	 * @param user: name of user who granted (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void revokeManagerForUser (String spaceName, String name){
		info("Grant manager for user : "+ spaceName);
		goToMySpacePage();
		gotoEditSpace(spaceName);
		goToMembers();
		revokeManager(name);
	}

	/**
	 * @author vuna2
	 * Manager (of space) accept a request from an user
	 * @param accept: boolean
	 * @param manager: (type: Root, Admin, etc...)
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 * @param spaceName: name of space (String)
	 */
	public void managerAcceptRequestFromUser(boolean accept, ManageAccount.userType manager, ManageAccount.userType user, String spaceName,Boolean... params){
		Boolean isLogin = params.length > 0 ? params[0] : true;  
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isLogin){
			magAcc.signOut();
			magAcc.userSignIn(manager);
		}
		goToMembers(spaceName);
		if (accept){
			managerValidateInvitation(user);
		}else{
			managerDeclineInvitation(user);
		}
	}

	/**
	 * @author vuna2
	 * Manager (of space) decline a request from an user
	 * @param manager: (type: Root, Admin, etc...)
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName
	 */
	/*public void managerDeclineRequestFromUser(userType manager, userType user, String spaceName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		magAcc.userSignIn(manager);
		goToMembers(spaceName);
		managerDeclineInvitation(user);
	}*/

	/**
	 * @author vuna2
	 * Manager (of space) remove a member from a space
	 * @param userTypeManager: type: Root, Admin, Author, Developer or Publisher
	 * @param spaceName
	 * @param memberName
	 */
	public void managerRemoveMemberFromSpace(ManageAccount.userType userTypeManager, String spaceName, String memberName){
		magAcc = new ManageAccount(driver,this.plfVersion);
		info("-- Removing the member: " + memberName + " from the space: " + spaceName);
		magAcc.signOut();
		magAcc.userSignIn(userTypeManager);
		goToMySpacePage();
		gotoEditSpace(spaceName);
		goToMembers();
		removeMember(memberName);
		Utils.pause(500);
	}

	/**
	 * Description: Admin create a new space & send an invitation to an user
	 * @author vuna2
	 * @param basicSpace: create a basic space (boolean)
	 * @param advanceSapce: create a space with options (boolean)
	 * @param spaceName: name of space (String) 
	 * @param spaceDescription: description of space (String)
	 * @param advanceParam: [0]->visibility, [1]->registration, [2]->groupPath, [3]->childGroupName
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * 
	 */
	public void managerAddNewSpaceAndInviteUser(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, ManageAccount.userType user){
		goToMySpacePage();
		if (basicSpace){
			addNewSpace(spaceName, spaceDescription);
		}else if (advanceSpace){
			addNewSpace(spaceName, spaceDescription, advanceParam[0], advanceParam[1], advanceParam[2], advanceParam[3]);
		}	
		goToSpaceMenu("Space Settings");
		goToMembers();
		inviteSingleUser(user);
	}		

	/**
	 * @author vuna2
	 * @param manager: manager of space (type: Root, Admin, etc...)
	 * @param spaceName: name of space (String)
	 * @param userRoot: boolean
	 * @param userName: String (eg, Mary, Jack, James, etc...)
	 */
	public void managerReInviteUser(ManageAccount.userType manager, String spaceName, boolean userRoot, String userName){
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		magAcc.userSignIn(manager);
		addUserToSpace(spaceName, userRoot, userName);
		Utils.pause(500);
	}

	/**
	 * @author vuna2
	 * @param basicSpace: create a basic space (boolean)
	 * @param advanceSapce: create a space with options (boolean)
	 * @param spaceName: name of space (String) 
	 * @param spaceDescription: description of space (String)
	 * @param advanceParam: [0]->visibility, [1]->registration, [2]->groupPath, [3]->childGroupName
	 * @param username: (type: mary, john, james, demo, etc...)
	 * @param membership: admin select a membership for user (String) 
	 */
	public void managerAddNewSpaceAndAddUserInToSpace(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, String username, String membership){
		goToMySpacePage();
		if (basicSpace){
			addNewSpace(spaceName, spaceDescription);
		}else if (advanceSpace){
			addNewSpace(spaceName, spaceDescription, advanceParam[0], advanceParam[1], advanceParam[2], advanceParam[3]);
		}
		Utils.pause(1000);
		nav.goToUsersAndGroupsManagement();
		userGroup.chooseGroupTab();
		userGroup.selectGroup("Spaces/"+spaceName);
		userGroup.addUsersToGroup(username, membership, false, true);
		Utils.pause(1000);
	}

	/**
	 * Description: Admin create a new space & users send a request to join a space
	 * @author vuna2
	 * @param basicSpace: create a basic space (boolean)
	 * @param advanceSapce: create a space with options (boolean)
	 * @param spaceName: name of space (String) 
	 * @param spaceDescription: description of space (String)
	 * @param advanceParam: [0]->visibility, [1]->registration, [2]->groupPath, [3]->childGroupName
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void managerAddNewSpaceAndUserSendRequest(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, ManageAccount.userType user){
		goToMySpacePage();
		if (basicSpace){
			addNewSpace(spaceName, spaceDescription);
		}else if (advanceSpace){
			addNewSpace(spaceName, spaceDescription, advanceParam[0], advanceParam[1], advanceParam[2], advanceParam[3]);
		}	
		userRequestToJoinSpace(user, spaceName);
		goToRequestsPeding();
		waitForAndGetElement(ELEMENT_CANCEL_LINK.replace("${spaceName}", spaceName));
	}

	/**
	 * @author vuna2
	 * @param basicSpace: create a basic space (boolean)
	 * @param advanceSapce: create a space with options (boolean)
	 * @param spaceName: name of space (String) 
	 * @param spaceDescription: description of space (String)
	 * @param advanceParam: [0]->visibility, [1]->registration, [2]->groupPath, [3]->childGroupName
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void managerAddNewOpenSpaceAndUserJoinSpace(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, ManageAccount.userType user){
		goToMySpacePage();
		if (basicSpace){
			addNewSpace(spaceName, spaceDescription);
		}else if (advanceSpace){
			addNewSpace(spaceName, spaceDescription, advanceParam[0], advanceParam[1], advanceParam[2], advanceParam[3]);
		}	
		joinOpenSpace(user, spaceName);
	}

	/**
	 * Admin invite an user and check the invitation
	 * @author vuna2
	 * @param basicSpace: create a basic space (boolean)
	 * @param advanceSapce: create a space with options (boolean)
	 * @param spaceName: name of space (String) 
	 * @param spaceDescription: description of space (String)
	 * @param advanceParam: [0]->visibility, [1]->registration, [2]->groupPath, [3]->childGroupName
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param capture: boolean
	 * @param imageFileName: input a name of captured image (String)
	 */
	public void adminInviteUserAndCheckInvitation(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, ManageAccount.userType user, boolean capture, String imageFileName){
		managerAddNewSpaceAndInviteUser(basicSpace, advanceSpace, spaceName, spaceDescription, advanceParam, user);
		checkInvitation(user, spaceName, capture, imageFileName);
	}

	/**
	 * Admin invite an user and User accept the invitation to join a space
	 * @author vuna2
	 * @param accept: boolean
	 * @param basicSpace: create a basic space (boolean)
	 * @param advanceSapce: create a space with options (boolean)
	 * @param spaceName: name of space (String) 
	 * @param spaceDescription: description of space (String)
	 * @param advanceParam: [0]->visibility, [1]->registration, [2]->groupPath, [3]->childGroupName
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void adminInviteUserAndUserAcceptInvitation(boolean accept, boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, ManageAccount.userType user){
		managerAddNewSpaceAndInviteUser(basicSpace, advanceSpace, spaceName, spaceDescription, advanceParam, user);
		if (accept){
			userAcceptInvitationToJoinSpace(true, user, spaceName);
		}else{
			userAcceptInvitationToJoinSpace(false, user, spaceName);
		}
	}

	/**
	 * Admin invite an user and User ignore the invitation to join a space
	 * @author vuna2
	 * @param basicSpace: create a basic space (boolean)
	 * @param advanceSapce: create a space with options (boolean)
	 * @param spaceName: name of space (String) 
	 * @param spaceDescription: description of space (String)
	 * @param advanceParam: [0]->visibility, [1]->registration, [2]->groupPath, [3]->childGroupName
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 */
	/*public void adminInviteUserAndUserIgnoreInvitation(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, userType user){
		managerAddNewSpaceAndInviteUser(basicSpace, advanceSpace, spaceName, spaceDescription, advanceParam, user);
		//userIgnoreInvitationToJoinSpace(user, spaceName);
		userAcceptInvitationToJoinSpace(false, user, spaceName);
	}*/

	/**
	 * @author vuna2
	 * @param manager: manager of space (type: Root, Admin, etc...)
	 * @param user: user of space (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	public void managerGoToMemberListTab(ManageAccount.userType manager, ManageAccount.userType user, String spaceName){
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		magAcc.userSignIn(manager);
		accessSpace(spaceName);
		click(ELEMENT_MEMBERS_TAB_IN_SPACE_MENU);
		switch (user) {
		case ROOT:
			waitForTextPresent("Root Root");
			break;
		case ADMIN:
			waitForTextPresent("John Smith");
			break;	
		case AUTHOR:
			waitForTextPresent("James Davis");
			break;
		case DEVELOPER:
			waitForTextPresent("Jack Miller");
			break;
		case PUBLISHER:
			waitForTextPresent("Mary Williams");
			break;
		default:
			break;
		}
	}

	/**
	 * Admin of space (eg, John) > Members Tab 
	 * @author vuna2
	 * @param manager: manager of space (type: Root, Admin, etc...)
	 * @param spaceName: name of space (String)
	 */
	public void managerGoToMembersTab(ManageAccount.userType manager, String spaceName){
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		magAcc.userSignIn(manager);
		goToMembers(spaceName);
	}

	/**
	 * @author vuna2
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 */
	public void managerValidateInvitation(ManageAccount.userType user){
		switch (user) {
		case ROOT:
			validateInvitation("Root");
			break;
		case ADMIN:
			validateInvitation("John");
			break;	
		case AUTHOR:
			validateInvitation("James");
			break;
		case DEVELOPER:
			validateInvitation("Jack");
			break;
		case PUBLISHER:
			validateInvitation("Mary");
			break;
		default:
			break;
		}
	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void managerDeclineInvitation(ManageAccount.userType user){
		switch (user) {
		case ROOT:
			declineInvitation("Root");
			break;
		case ADMIN:
			declineInvitation("John");
			break;	
		case AUTHOR:
			declineInvitation("James");
			break;
		case DEVELOPER:
			declineInvitation("Jack");
			break;
		case PUBLISHER:
			declineInvitation("Mary");
			break;
		default:
			break;
		}
	}
	/*------------------------ End of common code for Manager -----------------------------*/


	/*------------------------ Common code for user of Space ------------------------------*/
	/**
	 * @author vuna2
	 * @param accept: boolean
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	public void userAcceptInvitationToJoinSpace(boolean accept, ManageAccount.userType user, String spaceName){
		magAcc = new ManageAccount(driver,this.plfVersion);
		magAcc.signOut();
		magAcc.userSignIn(user);
		if (accept){
			acceptInvitation(spaceName);
			goToMySpacePage();
			waitForTextPresent(spaceName);
		}else{
			ignoreInvitation(spaceName);
			goToMySpacePage();
			waitForTextNotPresent(spaceName);
		}
	}

	/**
	 * Leave from Space
	 * @author phuongdt
	 */
	public void leaveFromSpace(String spaceName,String userName){
		goToMySpacePage();
		doAction("Leave", spaceName);
		waitForElementNotPresent(By.xpath(ELEMENT_LEFT_PANEL_SPACE_ITEM.replace("${spaceName}", spaceName)));
	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	/*public void userIgnoreInvitationToJoinSpace(userType user, String spaceName){
		signOut();
		magAcc.userSignIn(user);
		ignoreInvitation(spaceName);
		goToMySpacePage();
		waitForTextNotPresent(spaceName);
	}*/

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	public void userRequestToJoinSpace(ManageAccount.userType user, String spaceName,Boolean... params){
		Boolean isLogin = params.length > 0 ? params[0] : true;  
		magAcc = new ManageAccount(driver,this.plfVersion);
		if(isLogin){
			magAcc.signOut();
			magAcc.userSignIn(user);
		}
		goToAllSpaces();
		requestToJoin(spaceName);
	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	public void userCancelRequest(ManageAccount.userType user, String spaceName){
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		magAcc.userSignIn(user);
		goToRequestsPeding();
		doAction("Cancel", spaceName);
		Utils.pause(500);
		//goToAllSpaces();
		click(ELEMENT_ALL_SPACE_LINK);
		waitForAndGetElement(ELEMENT_SEND_REQUEST_LINK.replace("${spaceName}", spaceName));
	}

	/**
	 * Description: User check if he/she receives an invitation
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 * @param capture: boolean
	 * @param imageFileName: input a name of captured image (String)
	 */
	public void checkInvitation(ManageAccount.userType user, String spaceName, boolean capture, String imageFileName){
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		magAcc.userSignIn(user);
		goToInvitationReceives();
		// Display the space name: spaceName with Accept | Ignore button
		waitForAndGetElement(ELEMENT_INVITATION_ACCEPT_LINK.replace("${spaceName}", spaceName));
		waitForAndGetElement(ELEMENT_INVITATION_IGNORE_LINK.replace("${spaceName}", spaceName));
		if (capture){
			Utils.captureScreen(imageFileName);
		}else{
			info("---- The invitation is sent successfully ----");
		}
	}

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 */
	public void userGoToAllSpacesPage(ManageAccount.userType user){
		magAcc = new ManageAccount(driver,this.plfVersion);
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			magAcc.signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		magAcc.userSignIn(user);
		goToAllSpaces();
		Utils.pause(500);
	}

	/**
	 * @author vuna2
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 */
	public void verifyUserJoinedSpace(ManageAccount.userType user){
		String verifyUser = "${user} " + "joined the space.";
		switch (user) {
		case ROOT:
			waitForTextPresent(verifyUser.replace("${user}", "Root Root"));
			break;
		case ADMIN:
			waitForTextPresent(verifyUser.replace("${user}", "John Smith"));
			break;	
		case AUTHOR:
			waitForTextPresent(verifyUser.replace("${user}", "James Davis"));
			break;
		case DEVELOPER:
			waitForTextPresent(verifyUser.replace("${user}", "Jack Miller"));
			break;
		case PUBLISHER:
			waitForTextPresent(verifyUser.replace("${user}", "Mary Williams"));
			break;
		default:
			break;
		}
	}	
	/*------------------------ End of common code for User -----------------------------*/
}