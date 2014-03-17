package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.exoplatform.selenium.platform.social.PeopleSearch;
import org.exoplatform.selenium.platform.social.SocialBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 24/09/2013
 *
 */

public class Social_People extends SocialBase {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	HomePageActivity activity;

	//Social
	PeopleConnection peoConn;
	PeopleProfile peoPro;
	PeopleSearch peoSearch;
	
	String user = "John Smith";
	String user1="Mary Williams";
	String user2="Jack Miller";
	String user3="James Davis";
	String user4="Root Root";
	String user_login1 = "mary";
	String user_login2 = "demo";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		activity = new HomePageActivity(driver);
		peoConn = new PeopleConnection(driver);
		peoPro = new PeopleProfile(driver);
		peoSearch = new PeopleSearch(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Verify mouse over account name link
	 * This test case is not included in qmetry
	 * Purpose of selenium test case: verify bug: https://jira.exoplatform.org/browse/PLF-4862
	 */
	@Test
	public void test00_VerifyMouseOver(){
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		mouseOver(ELEMENT_TOOLBAR_NETWORKS_ICON, true);
		click(ELEMENT_TOOLBAR_NETWORKS_ICON);
		waitForAndGetElement(ELEMENT_SEARCH_BUTTON);
	}
	
	/**
	 * == Accept/Deny Invitation ==
	 * Test case ID: 67657
	 * Step 1: Send invitation
	 */
	@Test
	public void test01_AcceptDenyInvitation(){
		/*Step 1: Send invitation*/ 
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		//Display list of people
		//Click on Connect button to invite about 2 users
		peoConn.connectPeople(user1);
		//Invitation is sent to user, Connect button is changed to Cancel Request
		peoConn.connectPeople(user2);

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.PUBLISHER);
		//An user click on Confirm button
		peoConn.acceptInvitation(user);

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.DEVELOPER);
		//Another user Ignore the invitation
		peoConn.ignoreInvitation(user);

		magAcc.userSignIn(userType.ADMIN);
		navToolBar.goToConnectionPage();
		click(peoConn.ELEMENT_MY_CONNECTIONS_TAB);
		//With user confirmed the invitation, user  becomes friend and user's name is displayed on user's network list
		peoSearch.searchPeople(true,user1);
		waitForAndGetElement("//*[contains(text(),'"+user1+"')]");
		
		//With user ignored the invitation, User isn't displayed on user's network list
		peoSearch.searchPeople(true,user2);
		waitForElementNotPresent("//*[contains(text(),'"+user2+"')]");

		/*Clear data*/
		info("clear data");
		peoSearch.searchPeople(false,"");
		peoConn.removeConnection(user1);
	}

	/**
	 * == Check People listing ==
	 * Test case ID: 67668
	 * Step 1: People listing
	 */
	@Test
	public void test02_CheckPeopleListing(){
		/*Step 1: People listing*/ 
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		//Show all users on Social and user can send friend request to connect with other users
		waitForAndGetElement(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user1));
		waitForAndGetElement(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user2));
		waitForAndGetElement(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user3));
		waitForAndGetElement(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user4));
	}

	/**
	 * == Network ==
	 * Test case ID: 67674
	 * Step 1: Open Network list
	 */
	@Test
	public void test03_Network(){
		/*Step 1: Open Network list*/ 
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();
		//Access people list, invite an user
		peoConn.connectPeople(user1);

		//Invited user accept invitation
		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.PUBLISHER);
		//An user click on Confirm button
		peoConn.acceptInvitation(user);

		//Go to My Connections 
		//Click Remove Connection button
		peoConn.removeConnection(user);

		magAcc.userSignIn(userType.ADMIN);
		//Display user's relation
		//Selected friend is removed. User can re-add friend
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user1);

		//Clear data
		peoSearch.searchPeople(false,"");
		peoConn.cancelRequest(user1);
	}

	/**
	 * == Pending Requests list ==
	 * Test case ID: 67675
	 * Step 1: Open Pending Requests list
	 */
	@Test
	public void test04_PendingRequestsList(){
		/*Step 1: Open Pending Requests list*/ 
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();
		//Access people list, invite an user
		peoConn.connectPeople(user1);

		//Go to My Connections/Requests Sent
		click(peoConn.ELEMENT_REQUEST_PENDING_TAB);

		//Display all user's requests
		peoSearch.searchPeople(true,user1);
		waitForAndGetElement("//*[contains(text(),'"+user1+"')]");

		//Click Cancel Request
		peoSearch.searchPeople(false,"");
		peoConn.cancelRequest(user1);
	}

	/**
	 * == Received Invitations ==
	 * Test case ID: 67676
	 * Step 1: Open Requests Received  list
	 */
	@Test
	public void test05_ReceivedInvitations(){
		/*Step 1: Open Requests Received  list*/ 
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();
		//Access people list, invite an user
		peoConn.connectPeople(user1);
		
		//Login by invited user, go to My Connections/ Requests Received
		magAcc.userSignIn(userType.PUBLISHER);
		navToolBar.goToConnectionPage();
		click(peoConn.ELEMENT_REQUESTS_RECEIVED_TAB);

		//Display all user's invitations
		peoSearch.searchPeople(true,user);
		waitForAndGetElement("//*[contains(text(),'"+user+"')]");

		//Invited user can Accept/Ignore the invitation
		peoSearch.searchPeople(false,"");
		peoConn.acceptInvitation(user);
		peoConn.removeConnection(user);
	}

	/**
	 * == Search people ==
	 * Test case ID: 67679
	 * Step 1: Search people
	 * Test fail: Can't search people by position 
	 * Refer: https://jira.exoplatform.org/browse/SOC-3807
	 */
	@Test(groups={"error"})
	public void test06_SearchPeople(){
		//Declare variable
		String nameOfOrganization ="exoplatform company";
		String nameOfPosition="sale";
		String nameOfSkill="Testing";

		//Edit information of user
		//Login by user1
		magAcc.userSignIn(userType.PUBLISHER);

		//Hover the mouse over the name of user, click on My profile
		navToolBar.goToMyProfile();

		//Click on Edit button to change user's information
		peoPro.editUserExperience(nameOfOrganization, nameOfPosition, nameOfSkill);

		//Login by DATA_USER1
		magAcc.userSignIn(userType.ADMIN);

		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		/*Step 1: Search people*/ 
		//Enter name of people into search text box and press Enter
		peoSearch.searchPeople(true,user1);
		//Display all results match with keyword 
		waitForAndGetElement("//*[contains(text(),'"+user1+"')]");
		peoSearch.searchPeople(true,"", "", "");

		//Search by Role/Skill:
		//Enter text into field skill and press Enter
		peoSearch.searchPeople(true,"", "", nameOfSkill);
		//Display all results match with keyword 
		waitForAndGetElement("//*[contains(text(),'"+user1+"')]");
		peoSearch.searchPeople(true,"", "", "");

		//Enter text into Position field and press Enter
		peoSearch.searchPeople(true,"",nameOfPosition);
		//Display all results match with keyword 
		waitForAndGetElement("//*[contains(text(),'"+user1+"')]");
		peoSearch.searchPeople(true,"", "", "");

		//Click on character from people directory characters list
		peoSearch.searchPeople(true,"", "", "","W");
		//Use search by directory, display all user which has name start by selected char
		waitForAndGetElement("//*[contains(text(),'"+user1+"')]");
		waitForElementNotPresent("//*[contains(text(),'"+user2+"')]");
	}

	/** 
	 * == View and edit user's profile ==
	 * Test case ID: 67683
	 * Step 1: View and edit user's profile
	 */
	@Test
	public void test07_ViewAndEditUserProfile(){
		//Declare variable
		String firstName="John update";
		String lastName = "Smith update";
		String email = "john1.smith1@acme.exoplatform.com";
		String file = "ECMS_DMS_SE_Upload_imgfile.jpg";
		String oldFirstnName = "John";
		String oldLastName = "Smith";
		String oldEmail = "john.smith@acme.exoplatform.com";

		/*Step 1: View and edit user's profile*/ 
		//Hover the mouse over the name of user, click on My profile
		navToolBar.goToMyProfile();

		//Click on Edit button to change user's information
		peoPro.editUserBasicInformation(firstName, lastName, email);

		//Get old avatar
		WebElement element = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", firstName+" "+lastName)));
		String oldsrc = element.getAttribute("src"); 

		//Click on Change avatar and upload new image and Confirm
		peoPro.changeAvatar("TestData/"+file);

		//Show information of user
		//User's information is changed and saved.
		waitForAndGetElement("//*[contains(text(),'"+firstName+"')]");
		waitForAndGetElement("//*[contains(text(),'"+lastName+"')]");
		waitForAndGetElement("//*[contains(text(),'"+email+"')]");

		//User has new avatar
		element = waitForAndGetElement(By.xpath(ELEMENT_GET_URL_IMAGE.replace("${name}", firstName+" "+lastName)));
		String newsrc = element.getAttribute("src");
		assert (!oldsrc.contentEquals(newsrc));

		//Clear data
		navToolBar.goToMyProfile();
		peoPro.editUserBasicInformation(oldFirstnName, oldLastName, oldEmail);
	}

	/**
	 * == View Connections list of other user ==
	 * Test case ID: 67684
	 * Step 1: View Connections list
	 */
	@Test
	public void test08_ViewConnectionsListOfOtherUser(){
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		//Display list of people
		//Click on Connect button to invite about 2 users
		peoConn.connectPeople(user1);
		//Invitation is sent to user, Connect button is changed to Cancel Request
		peoConn.connectPeople(user2);

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.PUBLISHER);

		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();
		peoPro.goToUserProfile(user);

		//Click on My network tab
		click(peoConn.ELEMENT_CONNECTION_OF_USER);
		click(peoConn.ELEMENT_MY_CONNECTIONS_TAB);

		//Verify there is not any user in my network tab
		waitForElementNotPresent(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user1));
		waitForElementNotPresent(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user2));

		//An user click on Confirm button
		navToolBar.goToConnectionPage();
		peoConn.acceptInvitation(user);

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.DEVELOPER);
		//Another user Ignore the invitation
		peoConn.acceptInvitation(user);

		/*Step 1: View Connections list*/ 
		//Click on name or avatar
		peoPro.goToUserProfile(user);

		//Click on My network tab
		click(peoConn.ELEMENT_CONNECTION_OF_USER);
		click(peoConn.ELEMENT_MY_CONNECTIONS_TAB);

		//Display user's relation
		waitForAndGetElement(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user1));
		waitForAndGetElement(peoConn.ELEMENT_PEOPLE_SEARCH.replace("${peopleName}", user2));

		/*Clear data*/
		magAcc.userSignIn(userType.ADMIN);
		navToolBar.goToConnectionPage();
		click(peoConn.ELEMENT_MY_CONNECTIONS_TAB);
		peoConn.removeConnection(user1);
		peoConn.removeConnection(user2);
	}

	/**
	 * == View profile of other user ==
	 * Test case ID: 67685
	 * Step 1: View profile 
	 */
	@Test
	public void test09_ViewProfileOfOtherUser(){
		//Declare variable
		String firstName="Mary update";
		String lastName = "Williams update";
		String email = "mary1.williams1@acme.exoplatform.com";
		String typeOfGender ="Female";
		String typeOfAddPhone="Work";
		String numberOfPhone="09880000";
		String typeOfIMS="Gtalk";
		String nameOfIMS="marywill";
		String nameOfURLS="exoplatform.com.vn";
		String nameOfOrganization ="exoplatform company";
		String nameOfPosition="Tester";
		String nameOfSkill="Analysis";
		String oldFirstnName = "Mary";
		String oldLastName = "Williams";

		//Edit information of user
		//Login by user1
		magAcc.userSignIn(userType.PUBLISHER);

		//Hover the mouse over the name of user, click on My profile
		navToolBar.goToMyProfile();

		//Click on Edit button to change user's information
		peoPro.editUserBasicInformation(firstName, lastName, email);
		peoPro.editUserContact(typeOfGender,true,typeOfAddPhone,numberOfPhone,true,typeOfIMS,nameOfIMS,true,nameOfURLS);
		peoPro.editUserExperience(nameOfOrganization, nameOfPosition, nameOfSkill);

		/*Step 1: View profile*/ 
		magAcc.userSignIn(userType.ADMIN);
		//magAcc.signIn(DATA_USER1, DATA_PASS);

		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		//Click on name or avatar of other user
		peoPro.goToUserProfile(firstName+" "+lastName);

		//Show all information of friend, default tab is My profile
		waitForAndGetElement("//*[contains(text(),'"+firstName+"')]");
		waitForAndGetElement("//*[contains(text(),'"+lastName+"')]");
		waitForAndGetElement("//*[contains(text(),'"+email+"')]");
		waitForAndGetElement("//*[contains(text(),'"+typeOfGender+"')]");
		waitForAndGetElement("//div[contains(text(),'"+typeOfAddPhone+"')]");
		waitForAndGetElement("//*[contains(text(),'"+numberOfPhone+"')]");
		waitForAndGetElement("//*[contains(text(),'"+typeOfIMS+"')]");
		waitForAndGetElement("//*[contains(text(),'"+nameOfIMS+"')]");
		waitForAndGetElement("//a[contains(text(),'"+nameOfURLS+"')]");
		waitForAndGetElement("//*[contains(text(),'"+nameOfOrganization+"')]");
		waitForAndGetElement("//*[contains(text(),'"+nameOfPosition+"')]");
		waitForAndGetElement("//*[contains(text(),'"+nameOfSkill+"')]");

		//Clear data
		//Login by user1
		magAcc.userSignIn(userType.PUBLISHER);
		//magAcc.signIn(user_login1, DATA_PASS);
		navToolBar.goToMyProfile();
		peoPro.editUserBasicInformation(oldFirstnName, oldLastName);
		peoPro.removeUserExperience();
		peoPro.removeUserContact(true, true, true);
	}
}
