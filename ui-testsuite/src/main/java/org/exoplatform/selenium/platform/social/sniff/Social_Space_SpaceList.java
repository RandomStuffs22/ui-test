package org.exoplatform.selenium.platform.social.sniff;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SocialBase;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 07/10/2013
 *
 */
public class Social_Space_SpaceList extends SocialBase {
	//Platform
	ManageAccount magAcc;
	ManageMember magMember;

	//Space
	SpaceManagement spaceMag;

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
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**
	 * == Check Pending space list ==
	 * Test case ID: 67666
	 * Step 1: Check displaying on  Invitations Received space list
	 */
	@Test
	public void test01_CheckPendingSpaceList(){
		//Declare variable
		String spaceName = "space67666";

		/*Step 1: Check displaying on  Invitations Received space list*/ 
		//- Create new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		//- Send Invitation to user
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.DEVELOPER,false);

		//- Log in by invited user
		//- Go to My space page and Invitations Received tab
		//- Show all invited space. User can accept the invitation
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);
		//- Show all invited space. User can ignore the invitation
		magMember.userAcceptInvitationToJoinSpace(false,userType.DEVELOPER,spaceName);

		/*Clear data*/
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Check Pending space list ==
	 * Test case ID: 67667
	 * Step 1: Check displaying on Requests Pending list
	 */
	@Test
	public void test02_CheckPendingSpaceList(){
		//Declare variable
		String spaceName1 = "space676671";
		String spaceName2 = "space676672";

		//Create data
		//Add new space
		info("Create data");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, "");

		/*Step 1: Check displaying on Requests Pending list*/ 
		//- Go to My space page and select All spaces tab
		//- Send request to specific Space 
		magMember.userRequestToJoinSpace(userType.PUBLISHER, spaceName1);
		magMember.userRequestToJoinSpace(userType.PUBLISHER, spaceName2,false);

		//- Click on Requests Pending tab
		goToRequestsPeding();
		//- Show all pending requests of this user
		waitForTextPresent(spaceName1);
		waitForTextPresent(spaceName2);

		/*Clear data*/
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName1,300000);
		magMember.deleteSpace(spaceName2,300000);
	}
}
