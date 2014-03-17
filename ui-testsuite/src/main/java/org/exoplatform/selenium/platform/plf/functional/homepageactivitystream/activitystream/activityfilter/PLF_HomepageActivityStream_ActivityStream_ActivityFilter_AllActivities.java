package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activitystream.activityfilter;



import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.testng.annotations.*;



/**
 * @author khanhnt
 *
 */
public class PLF_HomepageActivityStream_ActivityStream_ActivityFilter_AllActivities extends Activity{

	ManageAccount acc; 
	HomePageActivity home;
	NavigationToolbar nav; 
	PeopleConnection pConn; 
	ManageMember mMember; 
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
		acc.signIn(DATA_USER_JOHN, DATA_PASS);		
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/** Show "All activities" a default stream
	 *  Case ID: 77656
	 *  - Connect to Intranet/Homepage
	 */
	@Test
	public  void test01_ShowAllActivitiesADefaultStream() {
		info("Test 1: Show All activities a default stream"); 
		waitForAndGetElement(ELEMENT_ACTIVITY_FILTER_CURRENT.replace("${filterOption}", "All Activities"));	
 	}



	/** Activity posted by a user that is not a connection should not be visible in "All activities"
	 *  Test caseID: 77663
	 *  Step 1: Post an activity on "All activities"  by user A
	 *  Step 2: Check show "All activities" by user B
	 */
	@Test
	public void test02_ActivityPostedByAUserThatIsNotAConnectionShouldNotBeVisibleInAllActivities(){
		String text = "Activity 77663";
		//Step 1: Post an activity on "All activities"  by user A
		// - Connect to Intranet with User A
		// - Post an activity from the "All activities" stream
		nav.goToHomePage();
		addActivity(true,text, false,"");
		acc.signOut();
		
		//Step 2: Check show "All activities" by user B
		acc.userSignIn(userType.PUBLISHER);
		nav.goToHomePage();
		waitForElementNotPresent(home.ELEMENT_ACTIVITY.replace("${activityText}",text));
		//waitForAndGetElement(home.ELEMENT_ACTIVITY.replace("${activityText}",text));

		//delete data
		acc.userSignIn(userType.ADMIN);
		nav.goToHomePage();
		home.deleteActivity(text);
	}



	/** Activities of my connections should be displayed in "All Activities"
	 *  Test caseID: 77664
	 *  Step 1: Add an activity with user A
	 *  Step 2: Check show activities from "All activities" by user B
	 */
	@Test
	public void test03_ActivitiesOfMyConnectionsShouldBeDisplayedInAllActivities(){
		String text = "Activity 77664";
		// Step 1: Add an activity with user A
		// - Connect to Intranet with User A
		// - Post an activity from the "All activities" stream
		addActivity(true, text, false, "");
		nav.goToConnectionPage();
		pConn.connectPeople(user1);
		acc.signOut();
		
		// Step 2: Check show activities from "All activities" by user B
		acc.userSignIn(userType.DEVELOPER);
		pConn.acceptInvitation(user);
		nav.goToHomePage();
		waitForAndGetElement(home.ELEMENT_ACTIVITY.replace("${activityText}",text));

		//delete data
		acc.userSignIn(userType.ADMIN);
		pConn.removeConnection(user1);
		nav.goToHomePage();
		home.deleteActivity(text);
	}



	/** Activity posted from a space is visible in "All activities" of members even if they are not connections of the poster
	 *  Test caseID: 77665
	 *  Step 1: Add new space
	 *  Step 2: Invited user B is member of space
	 *  Step 3: user B is member of space
	 *  Step 4: Post activity on space
	 *  Step 5: Check activity on "All activities" stream by user B
	 */
	@Test
	public void test04_ActivityPostedFromASpaceIsVisibleInAllActivitiesOfMembersEvenIfTheyAreNotConnections(){
		String text = "Activity 77665";
		String spaceName = "Test 77665";
		// Step 1: Add new space
		// - Connect to Intranet with User A
		// - Click [Join a space]
		// - Add new space "Test"
		mMember.goToMySpacePage();
		mMember.addNewSpace(spaceName,"");

		// Step 2: Invited user B is member of space
		// - Go to [Space Setting]
		// - Invited user B is member of space
		//		mMember.goToMySpacePage();
		goToMembers(spaceName);
		mMember.inviteSingleUser(userType.DEVELOPER);
		acc.signOut();
		
		// Step 3: user B is member of space
		// - Login by user B
		// - Click [Join a space]
		// - Select space at step 1 and click [Accept]
		acc.userSignIn(userType.DEVELOPER);
		mMember.acceptInvitation(spaceName);
		acc.signOut();
		// Step 4: Post activity on space
		// - Login by user A
		// - Open the space "Test"
		// - Post an activity in the space stream
		acc.userSignIn(userType.ADMIN);
		mMember.goToMySpacePage();
		goToActivityStream();
		addActivity(true, text, false, "");
		acc.signOut();
		// Step 5: Check activity on "All activities" stream by user B
		acc.userSignIn(userType.DEVELOPER);
		nav.goToHomePage();
		waitForAndGetElement(home.ELEMENT_ACTIVITY.replace("${activityText}",text));
		acc.signOut();
		
		/*Clear data*/
		acc.userSignIn(userType.ADMIN);
		mMember.goToAllSpaces();
		mMember.deleteSpace(spaceName,300000);
	}



	/** Comment on my activity  from "All activities"
	 * Test caseID: 77675
	 * Step 1: Add comment on activity by owner
	 * Step 2: Check show activity on "My activities" stream
	 */
	@Test
	public void test05_CommentOnMyActivityFromAllActivities(){
		String text = "Activity 77675";
		String comment = "Comment activity 77675";

		//Step 1: Add comment on activity by owner
		//- Connect to Intranet/Homepage
		//- Set activity stream filter to "All Activities"
		//- add a comment to an activity posted by me
		addActivity(true, text, false, "");
		addComment(text, comment);

		//Step 2: Check show activity on "My activities" stream
		// - Switch to the stream "My activities"
		selectFileter("My Activities");
		waitForAndGetElement(home.ELEMENT_TOPIC_COMMENT.replace("${title}",text).replace("${comment}", comment));
		//delete data
		home.deleteActivity(text);
	}



	/** User can comment on an activity posted from home when he is mentioned
	 *  Test caseID: 77689
	 *  Step 1: Mention user
	 *  Step 2: Check activity on 'All activities"  with mentions user
	 *  Step 3: Add comment
	 */
	@Test
	public void test06_UserCanCommentOnAnActivityPostedFromHomeWhenHeIsMentioned(){
		String comment = "Comment activity 77689";
		// Step 1: Mention user
		// - Connect to Intranet with User A
		// - Mention user B in the acitivity shared box
		// - Share the message
		mentionActivity(true,"",user1);

		//  Step 2: Check activity on 'All activities"  with mentions user
		// - Connect to Intranet with the User B
		// - Open the stream "All activities"
		acc.userSignIn(userType.DEVELOPER);
		nav.goToHomePage();
		selectFileter("All Activities");
		waitForAndGetElement(home.ELEMENT_ACTIVITY.replace("${activityText}",user1));
		//		waitForAndGetElement(home.ELEMENT_TOPIC_COMMENT.replace("${title}",user1).replace("${comment}",""));

		// Step 3: Add comment
		// - Add a comment to the activity
		addComment(user1, comment);
		waitForAndGetElement(home.ELEMENT_TOPIC_COMMENT.replace("${title}",user1).replace("${comment}", comment));

		// Delete data
		acc.userSignIn(userType.ADMIN);
		home.deleteActivity(user1);
	}



}