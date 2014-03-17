package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activitystream.activityfilter;



import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.testng.annotations.*;



public class PLF_HomepageActivityStream_ActivityStream_ActivityFilter_Connections extends Activity{

	ManageAccount acc; 
	HomePageActivity home;
	NavigationToolbar nav; 
	PeopleConnection pConn; 
	ManageMember mMember; 
	SpaceManagement spaceMan;
	String user = "John Smith";
	String user1= "Jack Miller";	

	@BeforeMethod
	public void beforeMethods(){	
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		home = new HomePageActivity(driver);
		nav = new NavigationToolbar(driver);	
		pConn = new PeopleConnection(driver);
		mMember = new ManageMember(driver);
		spaceMan = new SpaceManagement(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);		
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/** Activity posted from Home is visible in "Connections" by my connections
	 *  Test caseID: 77658
	 *  Step 1: Post an activity on "All activities" of user A
	 *  Step 2: Check activity on "Connections" of user B
	 */
	@Test
	public void test01_ActivityPostedFromHomeIsVisibleInConnectionsByMyConnections(){
		String text="Activity 77658";
		//Connect user A & B
		nav.goToConnectionPage();
		pConn.connectPeople(user1);
		acc.userSignIn(userType.DEVELOPER);
		pConn.acceptInvitation(user);

		// Step 1: Post an activity on "All activities" of user A
		// - Connect to Intranet with User A
		// - Post an activity in the stream [All activities]
		acc.userSignIn(userType.ADMIN);
		nav.goToHomePage();
		addActivity(true, text,false,"");
		waitForAndGetElement(home.ELEMENT_ACTIVITY.replace("${activityText}",text));
		// Step 2: Check activity on "Connections" of user B
		// - Connect to Intranet with User B
		// - Open the stream [Connections]
		acc.userSignIn(userType.DEVELOPER);
		nav.goToHomePage();
		selectFileter("Connections");
		waitForAndGetElement(home.ELEMENT_ACTIVITY.replace("${activityText}",text));

		//delete data
		acc.userSignIn(userType.ADMIN);
		home.deleteActivity(text);
	}

	/** Activity posted from a space should not be visible in "Connections" of a connection
	 *  Test caseID: 77662
	 *  Step 1: Posted an activity in space by user A
	 *  Step 2: Check show activity in "Connections" stream
	 */
	@Test
	public void test02_ActivityPostedFromASpaceShouldNotBeVisibleInConnectionsOfAConnection(){
		// Connect userA & userB
		String text="Activity 77662";
		String spaceName = "Space 77662";
		//Connect user A & B
		nav.goToConnectionPage();
		pConn.connectPeople(user1);
		acc.userSignIn(userType.DEVELOPER);
		pConn.acceptInvitation(user);
		acc.userSignIn(userType.ADMIN);
		// Step 1: Posted an activity in space by user A
		// - Connect to Intranet with User A
		// - Open the space "Test"
		// - Post an activity in the space "Stream"

		//Create & join a space 
		mMember.goToMySpacePage();
		mMember.addNewSpace(spaceName,"");
		
		Utils.pause(3000);
		
		addActivity(true, text, false, "");
		waitForAndGetElement(home.ELEMENT_ACTIVITY.replace("${activityText}",text));
		
		goToMembers(spaceName);
		mMember.inviteSingleUser(userType.DEVELOPER);
		acc.userSignIn(userType.DEVELOPER);
		mMember.acceptInvitation(spaceName);
	
		// Step 2: Check show activity in "Connections" stream
		// - Connect to Intranet with User B
		// - Open the stream [Connections]
		
		nav.goToHomePage();
		selectFileter("Connections");
		waitForElementNotPresent(home.ELEMENT_ACTIVITY.replace("${activityText}",text));

		//delete data 
		acc.userSignIn(userType.ADMIN);
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);
		nav.goToConnectionPage();
		pConn.removeConnection(user1);
	}

	/** Comment on a "Connections" activity
	 *  Test caseID: 77677
	 *  Step 1: Add comment for activity by user B
	 *  Step 2: Check activity on "My Activity"
	 */
	@Test
	public void test03_CommentOnAConnectionsActivity(){
		String text = "Activity 77677";
		String comment = "Comment activity 77677";
		/**-Precondition--*/
		//Connect user A & B
		nav.goToConnectionPage();
		pConn.connectPeople(user1);
		acc.userSignIn(userType.DEVELOPER);
		pConn.acceptInvitation(user);
		// UserA create activity 
		acc.userSignIn(userType.ADMIN);
		nav.goToHomePage();
		addActivity(true, text, false,"");
		// Step 1: Add comment for activity by user B
		// - Connect to Intranet by user B
		// - In the stream "Connections", add a comment to an activity posted by user A
		acc.userSignIn(userType.DEVELOPER);
		nav.goToHomePage();
		selectFileter("Connections");
		addComment(text, comment);
		// Step 2: Check activity on "My Activity"
		// - Back to the homepage
		// - Switch to the stream "My activities"
		nav.goToHomePage();
		selectFileter("My Activities");
		waitForAndGetElement(home.ELEMENT_TOPIC_COMMENT.replace("${title}",text).replace("${comment}", comment));
		//delete data
		acc.userSignIn(userType.ADMIN);
		home.deleteActivity(text);
		nav.goToConnectionPage();
		pConn.removeConnection(user1);
	}
	
	/** Not Display the dropdown list of stream in Connection's activity stream
	 * Test caseID: 77693
	 *  Step 1: Check show dropdown list of stream in Connection's activity stream
	 */
	@Test
	public void test04_NotDisplayTheDropdownListOfStreamInConnectionIsActivityStream(){
		//Connect user A & B
		nav.goToConnectionPage();
		pConn.connectPeople(user1);
		acc.userSignIn(userType.DEVELOPER);
		pConn.acceptInvitation(user);
		// Step 1: Check show dropdown list of stream in Connection's activity stream
		// - Connect to Intranet
		// - Click [Connections]
		// - Select a user in list and click on it
		//	- Choose [Activity Stream] tab
		nav.goToHomePage();
		selectFileter("Connections");
		click((home.ELEMENT_ACTIVITY_AUTHOR_NAME.replace("${index}","1").replace("${author}",user)),DEFAULT_TIMEOUT, 0);
		click(ELEMENT_MY_ACTIVITY_STREAM_TAB);
		waitForElementNotPresent(ELEMENT_ACTIVITY_DROPDOWN, DEFAULT_TIMEOUT, 0);
	

		//delete data
		acc.userSignIn(userType.ADMIN);
		nav.goToConnectionPage();
		pConn.removeConnection(user1);
	}



}