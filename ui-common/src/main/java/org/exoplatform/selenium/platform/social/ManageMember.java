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

import static org.exoplatform.selenium.platform.ManageAccount.signIn;
import static org.exoplatform.selenium.platform.ManageAccount.signOut;
import static org.exoplatform.selenium.platform.NavigationToolbar.goToUsersAndGroupsManagement;
import static org.exoplatform.selenium.platform.UserGroupManagement.addUsersToGroup;
import static org.exoplatform.selenium.platform.UserGroupManagement.chooseGroupTab;
import static org.exoplatform.selenium.platform.UserGroupManagement.selectGroup;
import static org.exoplatform.selenium.platform.social.SpaceManagement.*;
import static org.exoplatform.selenium.TestLogger.info;

import org.openqa.selenium.By;

/**
 * Created by The eXo Platform SAS Author : Hoang Manh Dung
 * dunghm@exoplatform.com Nov 9, 2012
 */
public class ManageMember extends SocialBase {

  //Go to My Spaces > Select a space > Settings
  //Member Tab 
  public static final By ELEMENT_SELECT_MEMBER_BUTTON = By.xpath("//a[contains(@title,'Select Users')]");

  public static final By ELEMENT_INVITE_MEMBER_BUTTON = By.xpath("//a[contains(@title,'Invite')]");
  
  public static final By ELEMENT_INVITE_MEMBER_BUTTON_AUX = By.xpath("//*[@id='UISpaceMember']/*[@title='Invite']");

  public static final By ELEMENT_SELECT_MEMBER_FORM   = By.xpath("//span[@class='PopupTitle' and contains(text(),'Select Users')]");
  
  public static final String ELEMENT_MEMBERS_TABLE = "//th[contains(text(),'Members')]/ancestor::table";
  
  public static final String ELEMENT_PENDING_TABLE = "//th[contains(text(),'Pending')]/ancestor::table";
  
  public static final String ELEMENT_INVITED_TABLE = "//th[contains(text(),'Invited')]/ancestor::table";

  public static final String ELEMENT_SELECTED_USER_BOX = "//*[@title='${userName}']/../../td/div/input[@class='checkbox']";
  
  //Verify message for user is manager of space  
  public static final String VERIFY_MESSAGE = "You are the last manager of this space. You need to promote another member as manager of the space before you can leave it.";
  
  //Warning message: users have already existed in the space
  public static final String MESSAGE_USER_EXISTED_IN_SPACE = "Some users have already existed in the space, including:" + " ${username}";
  
  public static final String MESSAGE_USER_EXISTED_IN_INVITING_LIST = "Some users have already existed in the inviting list, including:" + " ${username}";
  
  
  /*-------------------------------- Common functions for SOCIAL -----------------------------------*/
  
  public static By getCheckBox(String text) {
    By checkbox = By.xpath("//div[@class='Text' and contains(text(),'" + text
        + "')]/ancestor::tr//input[@type='checkbox']");
    return checkbox;
  }

  /**
   * Invite a single user
   * 
   * @param name : username or name of a member
   */

  public static void inviteSingleUser(String name, String...params ) {
    String member = "//th[text()='Members']/ancestor::table//td[text()='Root Root']";  
    
    selectUser(name);
    if (name.equals("root")){
    	 info("--------------------------------");
    	 waitForElementPresent(By.xpath(member));
    }else{
    	if(params.length > 0){    	
        	name = params[0];
        }
	    click(ELEMENT_INVITE_MEMBER_BUTTON);
	    waitForElementPresent(By.xpath(ELEMENT_INVITED_TABLE + "//td[contains(text(),'" + name + "')]"));
    }
  }
  
  /**
   * Select user by name
   * 
   * @param name : user's name
   */
  public static void selectUser(String name){    
    String user1 = "//input[@id='user' and contains(@value,'${user}')]";
    String user2 = user1.replace("${user}", name.toLowerCase());
    click(ELEMENT_SELECT_MEMBER_BUTTON);
    waitForElementPresent(ELEMENT_SELECT_MEMBER_FORM);
    By checkbox = getCheckBox(name);
    check(checkbox);
    clickButton("Add");

    if (name.equals("Jack")){
    	waitForElementPresent(By.xpath(user1.replace("${user}", "demo")));
    }else{
    	waitForElementPresent(By.xpath(user2));
    }

    if (name.equals("root")){
        //waitForElementPresent(By.xpath(user1.replace("${user}", "demo")));
    	info("-- Root is a super user so we don't need to add him as a member --");
    }else{
        waitForElementPresent(By.xpath(user2));
    }
    	//waitForElementPresent(By.xpath(user)); 
  }

  /**
   * Invite multi users
   * 
   * @param userlist : list user separate by a comma
   */
  public static void inviteMultiUser(String userlist) {
    String[] userArr = userlist.split(",");
    int len = userArr.length;

    for (int i = 0; i < len; i++) {
      selectUser(userArr[i].trim());
    }    
    
    click(ELEMENT_INVITE_MEMBER_BUTTON);
    
    for (int i = 0; i < len; i++) {
      waitForElementPresent(By.xpath(ELEMENT_INVITED_TABLE + "//td[contains(text(),'" + userArr[i].trim() + "')]"));
      pause(500);
    }
    
  }

  /**
   * Accept a space join invitation
   * 
   * @param spaceName : space name
   */
  public static void acceptInvitation(String spaceName, int... params) {
    int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
    goToInvitationReceives();
    doAction("Accept", spaceName);
    waitForElementPresent(By.xpath("//div[contains(@class,'UISpaceName')]/a[@title='" + spaceName
                              + "']"),
                          iTimeout);
  }

  /**
   * Reject a space join invitation
   * 
   * @param spaceName : space name
   */
  public static void ignoreInvitation(String spaceName, int... params) {
    goToInvitationReceives();
    int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
    doAction("Ignore", spaceName);
    waitForElementNotPresent(By.xpath("//a[text()='" + spaceName
                                 + "']/ancestor::div[contains(@class,'ContentBox')]"),
                             iTimeout);
  }
  
  /**
   * Request to join a space
   * @param spaceName
   */
  public static void requestToJoin(String spaceName){
    String actionName = "Request to Join";
    doAction(actionName, spaceName);
    By actionLink = By.xpath("//a[text()='" + spaceName + "']/ancestor::div[contains(@class,'ContentBox')]//a[text()='" + actionName + "']");
    waitForElementNotPresent(actionLink);
  }
  
  /**
   * Validate a join space request
   * 
   * @param name : user's name
   */
  public static void validateInvitation(String name) {    
    String validateButton = ELEMENT_PENDING_TABLE + "//td[contains(text(),'" + name
        + "')]/ancestor::tr//a[contains(@title,'Validate Invitation')]";
    click(validateButton);
    waitForElementPresent(By.xpath(ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'" + name + "')]"));
  }

  /**
   * Decline a join space request
   * 
   * @param name : user's name
   */
  public static void declineInvitation(String name) {
    String declineButton = ELEMENT_PENDING_TABLE + "//td[contains(text(),'" + name
        + "')]/ancestor::tr//a[contains(@title,'Decline Invitation')]";
    click(declineButton);
    waitForElementNotPresent(By.xpath(ELEMENT_PENDING_TABLE + "//td[contains(text(),'" + name + "')]"));
  }
  
  /**
   * Grant Manger Permission for a space member
   * @param name : User's name
   */
  public static void grantManager(String name) {
    String grantButtonLabel = "Grant Manager";
    String revokeButtonLabel = "Revoke Manager";
    String grantManagerButton = ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'" + name
        + "')]/ancestor::tr//a[contains(@title,'" + grantButtonLabel + "')]";
    String revokeManagerButton = ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'" + name
        + "')]/ancestor::tr//a[contains(@title,'" + revokeButtonLabel + "')]";
    click(grantManagerButton);
    waitForElementPresent(By.xpath(revokeManagerButton));
  }
  
  /**
   * Revoke manager permission of a space member
   * @param name : User's name
   */
  public static void revokeManager(String name) {
    String grantButtonLabel = "Grant Manager";
    String revokeButtonLabel = "Revoke Manager";
    String grantManagerButton = ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'" + name
        + "')]/ancestor::tr//a[contains(@title,'" + grantButtonLabel + "')]";
    String revokeManagerButton = ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'" + name
        + "')]/ancestor::tr//a[contains(@title,'" + revokeButtonLabel + "')]";
    click(revokeManagerButton);
    waitForElementPresent(By.xpath(grantManagerButton));
  }

  /**
   * Remove a member from space
   * 
   * @param name : User's name
   */
  public static void removeMember(String name) {
    String removeButton = ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'" + name
        + "')]/ancestor::tr//a[contains(@title,'Remove Member')]";
    click(removeButton);
    waitForElementNotPresent(By.xpath(ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'" + name + "')]"));
  }
  
  /**
   * @author vuna2
   * @param spaceName: name of Space (String)
   * @param params
   */
  public static void joinOpenSpace(String spaceName, int...params){
	  info("-- Joining the open space: " + spaceName);
	  int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
	  goToAllSpaces();
	  doAction("Join", spaceName);
	  waitForElementPresent(By.xpath("//*[@class='TitleContent']/text()['(Member)']/../a[text()='"+ spaceName +"']"), iTimeout);
	  pause(1000);
  }
  
  /**
   * @author vuna2
   * @param user: (type: Root, Admin, Author, Developer or Publisher)
   * @param spaceName: name of Space (String)
   * @param params
   */
  public static void joinOpenSpace(userType user, String spaceName, int...params){
	  info("-- Joining the open space: " + spaceName);
	  int iTimeout = params.length > 0 ? params[0] : DEFAULT_TIMEOUT;
	  if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
		  signOut();
	  }else{
		  info("-- User.logIn: " + user);
	  }	  
	  userSignIn(user);
	  goToAllSpaces();
	  doAction("Join", spaceName);
	  waitForElementPresent(By.xpath("//*[@class='TitleContent']/text()['(Member)']/../a[text()='"+ spaceName +"']"), iTimeout);
	  pause(1000);
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
	public static void inviteSingleUser(userType userName){
		info("-- Invite the user: " + userName + " to join our space");
		click(ELEMENT_SELECT_MEMBER_BUTTON);
		waitForElementPresent(ELEMENT_SELECT_MEMBER_FORM);
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
		pause(1000);
	}

	/**
	 * @author vuna2
	 * Action: add an user (used in the method: inviteSingleUser)
	 * @param userRoot: boolean
	 * @param userName: example: John or Mary, etc...
	 */
	public static void addUserToSpace(boolean userRoot, String userName){
		info("-- Action: adding the user: " + userName);
		if (userRoot){
			check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${userName}", userName)));
			clickButton("Add");
			waitForElementPresent(By.xpath(ELEMENT_MEMBERS_TABLE + "//td[contains(text(),'"+ userName +"')]"));
		}else{
			check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${userName}", userName)));
			clickButton("Add");
			//click(ELEMENT_INVITE_MEMBER_BUTTON);
			click(ELEMENT_INVITE_MEMBER_BUTTON_AUX);
			waitForElementPresent(By.xpath(ELEMENT_INVITED_TABLE + "//td[contains(text(),'"+ userName +"')]"));
		}
	}
	
	/**
	 * @author vuna2
	 * Action: add an user to a space
	 * @param spaceName: name of Space (String)
	 * @param userRoot: boolean
	 * @param userName: name of user (Jack, James, Mary, etc...)
	 */
	public static void addUserToSpace(String spaceName, boolean userRoot, String userName){
		info("-- Action: adding the user: " + userName);
		goToMembers(spaceName);
		click(ELEMENT_SELECT_MEMBER_BUTTON);
		waitForElementPresent(ELEMENT_SELECT_MEMBER_FORM);
		if (userRoot){
			check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${userName}", userName)));
			clickButton("Add");
		}else{
			check(By.xpath(ELEMENT_SELECTED_USER_BOX.replace("${userName}", userName)));
			clickButton("Add");
			//click(ELEMENT_INVITE_MEMBER_BUTTON);
			click(ELEMENT_INVITE_MEMBER_BUTTON_AUX);
		}
	}

	/**
	 * @author vuna2
	 * Manger of space give an invitation to an user
	 * @param manager: (type: Root, Admin, etc...)
	 * @param spaceName: name of space (String)
	 * @param user: name of the invited (type: Root, Admin, Author, Developer or Publisher)
	 */
	public static void managerInviteUserToJoinSpace(userType manager, String spaceName, userType user){
		info("-- Invite an user to join: " + spaceName);
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		userSignIn(manager);
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
	public static void grantManagerForUser (String spaceName, String name){
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
	public static void revokeManagerForUser (String spaceName, String name){
		info("Grant manager for user : "+ spaceName);
		goToMySpacePage();
		gotoEditSpace(spaceName);
		goToMembers();
		removeMember(name);
	}
	
	/**
	 * @author vuna2
	 * Manager (of space) accept a request from an user
	 * @param accept: boolean
	 * @param manager: (type: Root, Admin, etc...)
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 * @param spaceName: name of space (String)
	 */
	public static void managerAcceptRequestFromUser(boolean accept, userType manager, userType user, String spaceName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		userSignIn(manager);
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
	/*public static void managerDeclineRequestFromUser(userType manager, userType user, String spaceName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		userSignIn(manager);
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
	public static void managerRemoveMemberFromSpace(userType userTypeManager, String spaceName, String memberName){
		info("-- Removing the member: " + memberName + " from the space: " + spaceName);
		signOut();
		userSignIn(userTypeManager);
		goToMySpacePage();
		gotoEditSpace(spaceName);
		goToMembers();
		removeMember(memberName);
		pause(500);
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
	public static void managerAddNewSpaceAndInviteUser(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, userType user){
		goToMySpacePage();
		if (basicSpace){
			addNewSpace(spaceName, spaceDescription);
		}else if (advanceSpace){
			addNewSpace(spaceName, spaceDescription, advanceParam[0], advanceParam[1], advanceParam[2], advanceParam[3]);
		}	
		goToSettings();
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
	public static void managerReInviteUser(userType manager, String spaceName, boolean userRoot, String userName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		userSignIn(manager);
		addUserToSpace(spaceName, userRoot, userName);
		pause(500);
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
	public static void managerAddNewSpaceAndAddUserInToSpace(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, String username, String membership){
		goToMySpacePage();
		if (basicSpace){
			addNewSpace(spaceName, spaceDescription);
		}else if (advanceSpace){
			addNewSpace(spaceName, spaceDescription, advanceParam[0], advanceParam[1], advanceParam[2], advanceParam[3]);
		}
		pause(1000);
		goToUsersAndGroupsManagement();
		chooseGroupTab();
		selectGroup("Spaces/"+spaceName);
		addUsersToGroup(username, membership, false, true);
		pause(1000);
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
	public static void managerAddNewSpaceAndUserSendRequest(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, userType user){
		goToMySpacePage();
		if (basicSpace){
			addNewSpace(spaceName, spaceDescription);
		}else if (advanceSpace){
			addNewSpace(spaceName, spaceDescription, advanceParam[0], advanceParam[1], advanceParam[2], advanceParam[3]);
		}	
		userRequestToJoinSpace(user, spaceName);
		goToRequestsPeding();
		waitForElementPresent(ELEMENT_CANCEL_LINK.replace("${spaceName}", spaceName));
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
	public static void managerAddNewOpenSpaceAndUserJoinSpace(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, userType user){
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
	public static void adminInviteUserAndCheckInvitation(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, userType user, boolean capture, String imageFileName){
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
	public static void adminInviteUserAndUserAcceptInvitation(boolean accept, boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, userType user){
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
	/*public static void adminInviteUserAndUserIgnoreInvitation(boolean basicSpace, boolean advanceSpace, String spaceName, String spaceDescription, String[] advanceParam, userType user){
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
	public static void managerGoToMemberListTab(userType manager, userType user, String spaceName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		userSignIn(manager);
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
	public static void managerGoToMembersTab(userType manager, String spaceName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + manager);
		}
		userSignIn(manager);
		goToMembers(spaceName);
	}

	/**
	 * @author vuna2
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 */
	public static void managerValidateInvitation(userType user){
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
	public static void managerDeclineInvitation(userType user){
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
	public static void userAcceptInvitationToJoinSpace(boolean accept, userType user, String spaceName){
		signOut();
		userSignIn(user);
		if (accept){
			acceptInvitation(spaceName);
			verifyUserJoinedSpace(user);
			goToMySpacePage();
			waitForTextPresent(spaceName);
		}else{
			ignoreInvitation(spaceName);
			goToMySpacePage();
			waitForTextNotPresent(spaceName);
		}
	}
	
	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	/*public static void userIgnoreInvitationToJoinSpace(userType user, String spaceName){
		signOut();
		userSignIn(user);
		ignoreInvitation(spaceName);
		goToMySpacePage();
		waitForTextNotPresent(spaceName);
	}*/

	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	public static void userRequestToJoinSpace(userType user, String spaceName){
		signOut();
		userSignIn(user);
		goToAllSpaces();
		requestToJoin(spaceName);
	}
	
	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 */
	public static void userCancelRequest(userType user, String spaceName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		userSignIn(user);
		goToRequestsPeding();
		click(ELEMENT_CANCEL_LINK.replace("${spaceName}", spaceName));
		pause(500);
		//goToAllSpaces();
		click(ELEMENT_ALL_SPACE_LINK);
		waitForElementPresent(ELEMENT_SEND_REQUEST_LINK.replace("${spaceName}", spaceName));
	}

	/**
	 * Description: User check if he/she receives an invitation
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 * @param spaceName: name of space (String)
	 * @param capture: boolean
	 * @param imageFileName: input a name of captured image (String)
	 */
	public static void checkInvitation(userType user, String spaceName, boolean capture, String imageFileName){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		userSignIn(user);
		goToInvitationReceives();
		// Display the space name: spaceName with Accept | Ignore button
		waitForElementPresent(ELEMENT_INVITATION_ACCEPT_LINK.replace("${spaceName}", spaceName));
		waitForElementPresent(ELEMENT_INVITATION_IGNORE_LINK.replace("${spaceName}", spaceName));
		if (capture){
			captureScreen(imageFileName);
		}else{
			info("---- The invitation is sent successfully ----");
		}
	}
	
	/**
	 * @author vuna2
	 * @param user: (type: Root, Admin, Author, Developer or Publisher)
	 */
	public static void userGoToAllSpacesPage(userType user){
		if (isElementNotPresent(ELEMENT_SIGN_IN_LINK) && isElementNotPresent(ELEMENT_GO_TO_PORTAL) ){
			signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		userSignIn(user);
		goToAllSpaces();
		pause(500);
	}
	
	/**
	 * @author vuna2
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 */
	public static void verifyUserJoinedSpace(userType user){
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

	/**
	 * @author vuna2
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 */
	public static void userSignIn(userType user){
		switch (user) {
		case ROOT:
			signIn("root", "gtn");
			break;
		case ADMIN:
			signIn("john", "gtn");
			break;	
		case AUTHOR:
			signIn("james", "gtn");
			break;
		case DEVELOPER:
			signIn("demo", "gtn");
			break;
		case PUBLISHER:
			signIn("mary", "gtn");
			break;
		default:
			break;
		}	
	} 

	/**
	 * Define a type of user 
	 * Root
	 * John Smith: administrator
	 * James Davis: author
	 * Jack Miller: developer
	 * Mary Williams: publisher 
	 */
	public static enum userType {
		ROOT, ADMIN, AUTHOR, DEVELOPER, PUBLISHER;
	}
	
	/*------------------------ End of common code for User -----------------------------*/
}

