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
 * @date 10/10/2013
 *
 */
public class Social_Space_SpaceManagement_ManageSpace extends SocialBase{
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
		magAcc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		spaceMag = new SpaceManagement(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * == Spaces list ==
	 * Test case ID: 67665
	 * Step 1: Check All Spaces list
	 */
	@Test
	public void test01_SpacesList(){
		//Declare variable
		String spaceName = "space67665";

		/*Step 1: Check All Spaces list*/ 
		//Create new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		
		//Login by other user
		//Access Space/All spaces
		//Show created space. User can send request to join space
		magMember.userRequestToJoinSpace(userType.PUBLISHER, spaceName);
		
		/*Clear data*/
		magAcc.userSignIn(userType.ADMIN);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
	}
	
	/**
	 * == Create a new Space - Edit a space - Delete a space ==
	 * Test case ID: 67672, 67895, 67896
	 * Step 1: Create a new Space
	 * Step 2: Edit a space
	 * Step 3: Delete a space
	 */
	@Test
	public void test02_CreateEditDeleteASpace(){
		//Declare variable
		String spaceName = "space67672";
		String newSpaceName = "newspace67672";
		String newDescription = "newDescription";
		String avatarFile = "TestData/ECMS_DMS_SE_Article.jpg";

		/*Step 1: Create a new Space*/ 
		//- Login Intranet
		//- Click on My space on Admin bar
		//- Click Add new space and input valid value into create space form
		//- Click on Create button
		//- New space is displayed on My space list of user and Publics space list of other user.
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, "");
		
		//- New space is displayed on Publics space list of other user.
		magAcc.userSignIn(userType.PUBLISHER);
		magMember.goToAllSpaces();
		waitForTextPresent(spaceName);
		
		/*Step 2: Edit a space*/
		magAcc.userSignIn(userType.ADMIN);
		//On My spaces list, click Edit: Edit information, visibility, edit avatar and click save
		//All changed of space is saved. User see it when access space
		magMember.goToMySpacePage();
		spaceMag.editSpace(spaceName, newSpaceName, newDescription, true, avatarFile);
		
		/*Step 3: Delete a space*/
		//- Go to My Space page,  select the space 
		//- Click on Delete Space icon
		//- Click on OK button to confirm
		magMember.goToAllSpaces();
		magMember.deleteSpace(newSpaceName,300000);
		//Space is removed. It doesn't display on My space list of user and all spaces list of other user.
		magAcc.userSignIn(userType.PUBLISHER);
		magMember.goToAllSpaces();
		waitForTextNotPresent(newSpaceName);
		waitForTextNotPresent(spaceName);
	}

}
