package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 30/09/2013
 *
 */
public class Social_HomePage_ActivityStream extends Activity {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	HomePageActivity activity;
	ManageMember magMember;
	ActionBar actBar;

	String user = "John Smith";
	String user1="Mary Williams";
	String user2="Jack Miller";
	String user3="James Davis";
	String user4="Root Root";
	String user_login1 = "mary";
	String user_login2 = "demo";

	//Social
	PeopleConnection peoConn;
	PeopleProfile peoPro;
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		activity = new HomePageActivity(driver);
		navToolBar = new NavigationToolbar(driver);
		magMember = new ManageMember(driver,this.plfVersion);
		actBar = new ActionBar(driver);
		peoConn = new PeopleConnection(driver);
		peoPro = new PeopleProfile(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Delete your activity ==
	 * Test case ID: 67892
	 * Step 1: Delete a comment
	 * Note: 
	 * 1. need to update test case in qmetry 
	 * (please remove the verification: Comment will be shown in comment section of activity)
	 * 2. The icon (X) will not be checked in selenium test case
	 */
	@Test
	public void test01_DeleteYourActivity(){
		//Declare variable
		String activity1 = "activity 1";

		/*Step 1: Delete a comment*/ 
		//- Select the activity 
		addActivity(true, activity1, false,"");
		//- Comment will be shown in comment section of activity (need to update test case)
		//- mouse over activity you want to delete
		//- the (x) icon display on the top-right of activity
		//- Click the (x) icon to delete 
		//- activity is deteled successfully
		activity.deleteActivity(activity1);
	}

	/**
	 * == Load previous activity page automatically ==
	 * Test case ID: 64715
	 * Step 1: Create more than 1 page of activities (Default 20)
	 * Step 2: check the loading automatically  previous activity page
	 */
	@Test
	public void test02_LoadPreviousActivityPageAutomatically(){
		//Declare variable

		/*Step 1: Create more than 1 page of activities (Default 20)*/ 
		//- Go to homepage and create more than 20 activities
		//- more than 20 activities are created successfully
		navToolBar.goToHomePage();
		for(int i = 1; i<=22; i++){
			if(i<10)
				addActivity(true, "activity 0"+String.valueOf(i), false,"");
			else
				addActivity(true, "activity "+String.valueOf(i), false,"");
		}

		/*Step 2: check the loading automatically  previous activity page*/
		//- log out and  log in again
		magAcc.signOut();
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);

		//- go to home page
		//-  the first page of last activities is displayeds
		//- scroll to the bottom of the activity stream
		//- previous activities' pages are load automatically
		for(int i=22;i>0;i-- ){
			if(i<10)
				waitForActivityPresent("activity 0"+String.valueOf(i),true);
			else
				waitForActivityPresent("activity "+String.valueOf(i),true);
		}

		/*Clear data*/
		for(int i=22;i>0;i-- ){
			if(i<10){
				activity.deleteActivity("activity 0"+String.valueOf(i));
				driver.navigate().refresh();
			}
			else
				activity.deleteActivity("activity "+String.valueOf(i));
		}
	}

	/**
	 * == Check [All activities] filter ==
	 * Test case ID: 64719
	 * Step 1: Goto social homepage
	 * Step 2: Check [All activity] filter
	 */
	@Test
	public void test03_CheckAllActivitiesFilter(){
		//Declare variable
		String spacename = "Space64719";
		String spacedesc = "Description Of Space03";
		String activityOfUser = "activity of ";
		String activityOfSpace = "activity of space ";

		//Create data
		//Create Space
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);

		//add activity space
		addActivity(true, activityOfSpace+spacename, false,"");

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
		//Add activity of connection user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user1, false,"");

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.DEVELOPER);
		//Another user Ignore the invitation
		peoConn.ignoreInvitation(user);
		//Add activity of connection user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user2, false,"");

		magAcc.userSignIn(userType.ADMIN);

		//sAdd activity of user
		addActivity(true, activityOfUser+user, false,"");

		/*Step 1: Goto social homepage*/ 
		//- Goto homepage
		//- Home page is displayed
		navToolBar.goToHomePage();

		/*Step 2: Check [All activity] filter*/
		//- In the drop-down select box, select [All Activities]
		selectFileter("All Activities");

		//- All activities are displayed in activity stream
		waitForActivityPresent(activityOfUser+user,false);
		waitForActivityPresent(activityOfUser+user1,true);
		waitForActivityPresent(activityOfSpace + spacename,true);
		waitForActivityNotPresent(activityOfUser+user2,false);

		/*Clear data*/
		info("clear data");
		activity.deleteActivity(activityOfUser+user);
		activity.deleteActivity(activityOfSpace + spacename);
		navToolBar.goToConnectionPage();
		peoConn.removeConnection(user1);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename,300000);
		magAcc.userSignIn(userType.PUBLISHER);
		activity.deleteActivity(activityOfUser+user1);
		magAcc.userSignIn(userType.DEVELOPER);
		activity.deleteActivity(activityOfUser+user2);
	}

	/**
	 * == Check [My Spaces] filter ==
	 * Test case ID: 64720
	 * Step 1: Goto social homepage
	 * Step 2: Check [My Spaces] filter
	 */
	@Test
	public void test04_CheckMySpacesFilter(){
		//Declare variable
		String spacename = "Space64720";
		String spacedesc = "Description Of Space04";
		String activityOfUser = "activity of ";
		String activityOfSpace = "activity of space ";

		//Create data
		//Create Space
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);

		//add activity space
		addActivity(true, activityOfSpace+spacename, false,"");

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
		//Add activity of connection user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user1, false,"");

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.DEVELOPER);
		//Another user Ignore the invitation
		peoConn.ignoreInvitation(user);
		//Add activity of connection user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user2, false,"");

		magAcc.userSignIn(userType.ADMIN);

		//Add activity of user
		addActivity(true, activityOfUser+user, false,"");

		/*Step 1: Goto social homepage*/ 
		//- Goto homepage
		//- Home page is displayed
		navToolBar.goToHomePage();

		/*Step 2: Check [My Spaces] filter*/
		//- In the drop-down select box, select [My Space]
		info("-- Check [My Spaces] --");
		selectFileter("My Spaces");

		//- It shows only activities created in space where the user is a member
		info("-- Verify activities --");
		waitForActivityNotPresent(activityOfUser+user,false);
		waitForActivityNotPresent(activityOfUser+user1,false);
		waitForActivityPresent(activityOfSpace + spacename,false);
		waitForActivityNotPresent(activityOfUser+user2,false);

		/*Clear data*/
		info("clear data");
		selectFileter("All Activities");
		activity.deleteActivity(activityOfUser+user);
		activity.deleteActivity(activityOfSpace + spacename);
		navToolBar.goToConnectionPage();
		peoConn.removeConnection(user1);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename,300000);
		magAcc.userSignIn(userType.PUBLISHER);
		activity.deleteActivity(activityOfUser+user1);
		magAcc.userSignIn(userType.DEVELOPER);
		activity.deleteActivity(activityOfUser+user2);
	}

	/**
	 * == Check [Connections]  filter ==
	 * Test case ID: 64721
	 * Step 1: Goto social homepage
	 * Step 2: Check [Connections] filter
	 * Note: See step 2 on qmetry again 
	 * (shows only activities created by the user's connections and by the user himself, outside a space)
	 */
	@Test
	public void test05_CheckConnectionsFilter(){
		//Declare variable
		String spacename = "Space64721";
		String spacedesc = "Description Of Space64721";
		String activityOfUser = "activity of ";
		String activityOfSpace = "activity of space ";

		//Create data
		//Create Space
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);

		//add activity space
		addActivity(true, activityOfSpace+spacename, false,"");

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
		//Add activity of connection user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user1, false,"");

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.DEVELOPER);
		//Another user Ignore the invitation
		peoConn.ignoreInvitation(user);
		//Add activity of connection user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user2, false,"");

		magAcc.userSignIn(userType.ADMIN);

		//Add activity of user
		addActivity(true, activityOfUser+user, false,"");

		/*Step 1: Goto social homepage*/ 
		//- Goto homepage
		//- Home page is displayed
		navToolBar.goToHomePage();

		/*Step 2: Check [Connections] filter*/
		//- In the drop-down select box, select [Connections]
		selectFileter("Connections");

		//shows only activities created by the user's connections and by the user himself, outside a space
		waitForActivityNotPresent(activityOfUser+user,false);
		waitForActivityPresent(activityOfUser+user1,false);
		waitForActivityNotPresent(activityOfSpace + spacename,false);
		waitForActivityNotPresent(activityOfUser+user2,false);

		/*Clear data*/
		info("clear data");
		selectFileter("All Activities");
		activity.deleteActivity(activityOfUser+user);
		activity.deleteActivity(activityOfSpace + spacename);
		navToolBar.goToConnectionPage();
		peoConn.removeConnection(user1);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename,300000);
		magAcc.userSignIn(userType.PUBLISHER);
		activity.deleteActivity(activityOfUser+user1);
		magAcc.userSignIn(userType.DEVELOPER);
		activity.deleteActivity(activityOfUser+user2);
	}

	/**
	 * == Check [My Activities]  filter ==
	 * Test case ID: 64722
	 * Step 1: Goto social homepage
	 * Step 2: Check [My Activities] filter
	 */
	@Test
	public void test06_CheckMyActivitiesFilter(){
		//Declare variable
		String spacename = "Space64722";
		String spacedesc = "Description Of Space64722";
		String activityOfUser = "activity of ";
		String activityOfSpace = "activity of space ";
		String activityComment = "activity comment of ";
		String activityLike = "activity like of ";
		String activityMention = "activity mention of ";

		//Create data
		//Add activity of user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user, false,"");
		
		//Create Space
		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);

		//add activity space
		addActivity(true, activityOfSpace+spacename, false,"");

		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		//Display list of people
		//Click on Connect button to invite about 2 users
		peoConn.connectPeople(user1);

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.PUBLISHER);
		//An user click on Confirm button
		peoConn.acceptInvitation(user);
		//Add activity of connection user
		navToolBar.goToHomePage();
		addActivity(true, activityOfUser+user1, false,"");
		addActivity(true, activityComment+user1, false,"");
		addActivity(true, activityMention+user1, false,"");
		addActivity(true, activityLike+user1, false,"");
		
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user2);
		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.DEVELOPER);
		//An user click on Confirm button
		peoConn.acceptInvitation(user1);
		//Add activity of connection user
		navToolBar.goToHomePage();
		//Mention user in comment
		mentionActivity(false,"activity mention of "+user1, user);
		
		//Remove connection
		navToolBar.goToConnectionPage();
		peoConn.removeConnection(user1);

		//Login user john again
		magAcc.userSignIn(userType.ADMIN);
		
		//Comment activitiy
		addComment(activityComment+user1, activityComment+user);
		
		//Like activity
		activity.likeOrUnlikeActivity(activityLike+user1);

		//Remove connection
		navToolBar.goToConnectionPage();
		peoConn.removeConnection(user1);

		//Step 1: Goto social homepage 
		//- Goto homepage
		//- Home page is displayed
		navToolBar.goToHomePage();

		/*Step 2: Check [My Activities] filter*/
		//- In the drop-down select box, select [My Activities]
		selectFileter("My Activities");

		//shows only activities where the user has been @mentionned, the user has commented or liked, and the user's activities (inside and outside a space)
		waitForActivityPresent(activityOfUser+user,false);
		waitForActivityPresent(activityComment+user1,false);
		waitForActivityPresent(activityLike+user1,false);
		waitForActivityPresent(activityOfSpace + spacename,false);
		waitForActivityNotPresent(activityOfUser+user1,false);

		/*Clear data*/
		info("clear data");
		magAcc.userSignIn(userType.PUBLISHER);
		activity.deleteActivity(activityComment+user1);
		activity.deleteActivity(activityLike+user1);
		activity.deleteActivity(activityOfUser+user1);
		magAcc.userSignIn(userType.ADMIN);
		selectFileter("All Activities");
		activity.deleteActivity(activityMention+user1);
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename,300000);
		navToolBar.goToHomePage();
		activity.deleteActivity(activityOfUser+user);
	}

	/**
	 * == Check activities order ==
	 * Test case ID: 64723
	 * Step 1: Check order of activities
	 */
	@Test
	public void test07_CheckActivitiesOrder(){
		//Declare variable
		String activity1 = "activity 647231";
		String activity2 = "activity 647232";
		String comment1 = "comment 647231";
		String comment2 = "comment 647232";

		/*Step 1: Check order of activities*/ 
		//- Log in and go to homepage
		navToolBar.goToHomePage();

		//Check the order of activities is based on activity's last action date. The last action date is the latest of:
		//1. The publication date
		addActivity(true, activity1, false,"");
		addActivity(true, activity2, false,"");
		navToolBar.goToHomePage();
		waitForAndGetElement(ELEMENT_ACTIVITY_NAME_CONSECUTIVE.replace("${activityText1}", activity2).replace("${activityText2}", activity1));

		//Check the order of activities is based on activity's last action date. The last action date is the latest of:
		//2. The date of the last comment posted
		addComment(activity2, comment2);
		addComment(activity1, comment1);
		navToolBar.goToHomePage();
		waitForAndGetElement(ELEMENT_ACTIVITY_NAME_CONSECUTIVE.replace("${activityText1}", activity1).replace("${activityText2}", activity2));

		/*Clear data*/
		info("clear data");
		activity.deleteActivity(activity1);
		activity.deleteActivity(activity2);
	}

	/**
	 * == Check Activity update - add new activities ==
	 * Test case ID: 64726
	 * Step 1: Check activity update when other user add a new activity
	 * Step 2: Scroll activity stream
	 * Pending: the status of this test case is N/A
	 * --> Need to update test case
	 */
	@Test (groups="pending")
	public void test08_CheckActivityUpdateAddNewActivities(){
		//Declare variable
//		String activitya1 = "activitya1";
//		String activitya2 = "activitya2";
//
//		//Create data
//		//Click on Connections on the left panel
//		navToolBar.goToConnectionPage();
//
//		//Display list of people
//		//Click on Connect button to invite user
//		peoConn.connectPeople(user1);
//
//		//Login by invited users, go to My Connections/Requests Received
//		magAcc.userSignIn(userType.PUBLISHER);
//		//An user click on Confirm button
//		peoConn.acceptInvitation(user);
//		//Add activity of connection user
//		navToolBar.goToHomePage();
//
//		/*Step 1: Check activity update when other user add a new activity*/ 
//		//- Log in as User A and create some activity
//		addActivity(true, activitya1, false, "");
//		addActivity(true, activitya2, false, "");
//		//- Log in as  User B (who is friend of user A) and check Activity stream
//		magAcc.userSignIn(userType.ADMIN);

		//Display made over the timeline with two elements:
		//1. a small panel at the timeline's top to show the number of updates since last visit
		//2. a darker color in the timeline to differentiate which activity hasn't be seen. 

		/*Step 2: Scroll activity stream*/ 
		//- Scroll activity stream

		//the updates will be marked as seen and the small panel change to No updates
		/*Clear data*/
		info("clear data");
	}

	/**
	 * == Check Activity update - add new comments ==
	 * Test case ID: 64727
	 * Step 1: Check activity update when other user add a new activity
	 * Step 2: Scroll activity stream
	 * Pending: the status of this test case is N/A
	 * --> Need to update test case
	 */
	@Test(groups="pending")
	public void test09_CheckActivityUpdateAddNewComments(){
		//Declare variable

		/*Step 1: Check activity update when other user add a new activity*/ 
		//- Log in as User A and make some comment on some activities
		//- Log in as  User B (who is friend of user A) and check Activity stream

		//Display made over the timeline with two elements:
		//1. a small panel at the timeline's top to show the number of updates since last visit
		//2. a darker color in the timeline to differentiate which activity hasn't be seen. 

		/*Step 2:  Scroll activity stream*/ 
		//- Scroll activity stream

		//the updates will be marked as seen and the small panel change to No updates
		/*Clear data*/
		info("clear data");
	}
}
