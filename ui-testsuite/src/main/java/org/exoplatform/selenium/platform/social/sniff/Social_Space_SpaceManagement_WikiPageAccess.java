package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.exoplatform.selenium.platform.wiki.Permalink;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 10/10/2013
 *
 */
public class Social_Space_SpaceManagement_WikiPageAccess extends Permalink{
	//Platform
	ManageAccount magAcc;
	ManageMember magMember;
	Dialog dialog;

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

		dialog = new Dialog(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Check when user is member of space ==
	 * Test case ID: 68826
	 * Step 1: Check when user is member of space
	 */
	@Test
	public void test01_CheckWhenUserIsMemberOfSpace(){
		//Declare variable
		String spaceName = "space68826";
		String title = "Wiki_manage_page_title_01";
		String content = "Wiki_manage_page_content_01";

		/*Step 1: Check when user is member of space*/ 
		//Add new space
		//Add space successfully
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		//- Invite a user become member of space
		magMember.managerInviteUserToJoinSpace(userType.ADMIN,spaceName,userType.PUBLISHER,false);

		//- User accept invitation
		//- User become member of Space
		magMember.userAcceptInvitationToJoinSpace(true,userType.PUBLISHER,spaceName);

		//- Add new page for space/wiki
		//- Add space and wiki of space successfully
		magAcc.userSignIn(userType.ADMIN);
		spaceMag.goToMySpacePage();
		magMember.gotoEditSpace(spaceName);
		spaceMag.goToSpaceMenu("Wiki");
		addBlankWikiPage(title, content, 0);

		//- From the list "More", choose the link "Permalink" and copy this link
		goToPermalink();
		String permalink = getPermalink();
		dialog.closeMessageDialog();

		//- Login by user B is member of space
		//- Paste the permalink
		//- The member of space can view the page created by the manager
		magAcc.signOut();
		goToWikiByPermalink("mary", permalink, true, content);

		/*Clear data*/
		info("-- Clear data --");
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}

	/**
	 * == Check when user is not member of space ==
	 * Test case ID: 68829
	 * Step 1: Check when user is not member of space
	 */
	@Test
	public void test02_CheckWhenUserIsNotMemberOfSpace(){
		//Declare variable
		String spaceName = "space68829";
		String title = "Wiki_manage_page_title_02";
		String content = "Wiki_manage_page_content_02";

		/*Step 1: Check when user is not member of space*/ 
		//Add new space
		//Add space successfully
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");

		//- Add new page for space/wiki
		//- Add space and wiki of space successfully
		spaceMag.goToSpaceMenu("Wiki");
		addBlankWikiPage(title, content, 0);

		//- From the list "More", choose the link "Permalink" and copy this link
		goToPermalink();
		String permalink = getPermalink();
		dialog.closeMessageDialog();

		//- Login by user B is not member of space
		//- Paste the permalink
		//- The "Page Not found" is displayed, the user B cannot view the page
		magAcc.signOut();
		goToWikiByPermalink("mary", permalink, false, content);

		/*Clear data*/
		info("-- Clear data --");
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
}
