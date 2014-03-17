package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 04/10/2013
 *
 */
public class Social_Space_MemberManagement extends Activity{
	//Platform
	ManageAccount magAcc;
	ManageMember magMember;

	//Space
	SpaceManagement spaceMag;
	
	String user1="Mary Williams";
	String user2="Jack Miller";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver,this.plfVersion);
		magMember = new ManageMember(driver,this.plfVersion);
		spaceMag = new SpaceManagement(driver,this.plfVersion);

		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Change member's role in Space ==
	 * Test case ID: 67664
	 * Step 1: Change member's role
	 */
	@Test(priority=0)
	public void test01_ChangeMemberRoleInSpace(){
		//Declare variable
		String spaceName = "space67664";

		//Create data
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		/*Step 1: Change member's role*/ 
		//- Invite a user become member of space
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);

		//- User accept invitation
		//- User become member of Space
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);

		//user can't access space setting of space
		magMember.goToMySpacePage();
		waitForElementNotPresent(spaceMag.ELEMENT_SPACE_SETTING_MENU);

		//- Login by manager of requested space: Access space -> space setting -> member tab
		magAcc.userSignIn(userType.ADMIN);

		//- Select user and click on Remove Leader or Make Leader icon
		magMember.grantManagerForUser(spaceName,user1);

		//- After make user is leader, user can access space setting of space.
		magAcc.userSignIn(userType.PUBLISHER);
		magMember.goToMySpacePage();
		magMember.gotoEditSpace(spaceName);

		/*Clear data*/
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Invite/Accept user to Space ==
	 * Test case ID: 67670
	 * Step 1: Invite/Accept user
	 */
	@Test
	public void test02_InviteAcceptUserToSpace(){
		//Declare variable
		String spaceName = "space67670";

		//Create data
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		/*Step 1: Invite/Accept user*/ 
		//- Access to a space from my space list
		//- Click on Space Settings icon, select Member tab
		//- Click on User icon, select some users from list and click on Invite icon
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.DEVELOPER,false);
		
		//- Login by invited users:
		//+ Go to My space -> Invitations Received tab and click on Accept 
		//- Space settings is displayed, focus on Member tab
		//- Name of invited user is displayed on invited list.
		//- After invited user accept invitation, invited  space will move from invitation space list to my space list of user
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);

		//+ Another user click on Ignore button
		//- Space settings is displayed, focus on Member tab
		//- Name of invited user is displayed on invited list.
		magMember.userAcceptInvitationToJoinSpace(false,userType.DEVELOPER,spaceName);
		
		//-  Accepted user is displayed on Member List
		//- After user ignored, the invitation is removed from Received Invitations list and this user does not displayed on Member list of space
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToMembers(spaceName);
		waitForAndGetElement(By.xpath(magMember.ELEMENT_MEMBER_USER_ITEM.replace("${userName}", user1)));
		waitForElementNotPresent(By.xpath(magMember.ELEMENT_MEMBER_USER_ITEM.replace("${userName}", user2)));

		/*Clear data*/
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Leave Space ==
	 * Test case ID: 67671
	 * Step 1: Leave Space
	 */
	@Test
	public void test03_LeaveSpace(){
		//Declare variable
		String spaceName = "space67671";

		//Create data
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		//Invite/Accept user
		//- Access to a space from my space list
		//- Click on Space Settings icon, select Member tab
		//- Click on User icon, select some users from list and click on Invite icon
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);
		//- User accept invitation
		//- User become member of Space
		//- Display list of space which user Is member
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);

		/*Step 1: Leave Space*/ 
		//- Go to My space page
		//- Select a space and Click on Leave button on space
		//- Space is disappeared from list and moved to All spaces list, user is not member of space
		magMember.leaveFromSpace(spaceName, user1);

		/*Clear data*/
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Remove user from space ==
	 * Test case ID: 67677
	 * Step 1: Remove user
	 */
	@Test
	public void test04_RemoveUserFromSpace(){
		//Declare variable
		String spaceName = "space67677";

		//Create data
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		/*Step 1: Remove user*/ 
		//- Invite a user become member of space
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);
		//- User accept invitation
		//- User become member of Space
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);

		//- Managers of space go to Space settings -> member
		//- Select user and click on Remove icon
		//- User is remove out space. User is not display on Member list
		magMember.managerRemoveMemberFromSpace(userType.ADMIN, spaceName, user1);

		/*Clear data*/
		magMember.goToMySpacePage();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Request/Accept user to Space ==
	 * Test case ID: 67678
	 * Step 1: Request/Accept user
	 */
	@Test
	public void test05_RequestAcceptUserToSpace(){
		//Declare variable
		String spaceName1 = "space676781";
		String spaceName2 = "space676782";

		//Create data
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, "");
		
		/*Step 1: Request/Accept user*/ 
		//- Go to My space page and select All Spaces tab
		//- Click on Request to Join button corresponding of a specific Space
		//- Public space list is accessed
		//- After send request, the button Request to Join is changed to Cancel
		magMember.userRequestToJoinSpace(userType.PUBLISHER, spaceName1);
		magMember.userRequestToJoinSpace(userType.PUBLISHER, spaceName2,false);
		
		//- Login by manager of requested space: Access space -> space setting -> member tab
		//- Accept request by clicking on Validate or Decline icon
		//- Verify requested user is displayed Member List
		//- Display the space user has just requested to join in Requests Pending list
		//- After manager accept the request, sent user is member of space
		//- Accepted user is displayed Member List
		magMember.managerAcceptRequestFromUser(true,userType.ADMIN, userType.PUBLISHER,spaceName1);
		
		//Deny request by clicking on Validate or Decline icon
		magMember.managerAcceptRequestFromUser(false,userType.ADMIN, userType.PUBLISHER,spaceName2,false);

		/*Clear data*/
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName1,300000);
		magMember.deleteSpace(spaceName2,300000);
	}
}
