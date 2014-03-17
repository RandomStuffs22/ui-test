package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activity;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 */
public class PLF_HomePageActivityStream_Activity_Likes extends Activity {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	EcmsBase ecms;
	HomePageActivity hpActivity;
	PeopleConnection peoConn;

	String user = "John Smith";
	String user1="Mary Williams";
	String user2="Jack Miller";
	String user3="James Davis";

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		button = new Button(driver);
		hpActivity = new HomePageActivity(driver);
		actBar = new ActionBar(driver);
		peoConn = new PeopleConnection(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Dislike an activity (78618)==
	 * == Like activity (78621)==
	 * Test case ID: 78618
	 * Step 1: Like ativity
	 * Step 2: Dislike activity
	 */
	@Test
	public void test01_DislikeAnActivity(){
		//Declare variable
		String activity = "activity 78618";

		//Create data
		addActivity(true, activity, false,"");

		/* Step 1: Like ativity */
		//- Connect to Intranet
		//- Click on [Like] icon
		//- Number of likes is updated to +1
		hpActivity.likeOrUnlikeActivity(activity);

		//- Number of likes is updated to +1
		/* Step 2: Dislike activity */
		//- Click on [Like] icon again
		//- The activity is disliked by the user, the number of like is updated to -1
		//- The activity is disliked by the user, the number of like is updated to -1
		hpActivity.likeOrUnlikeActivity(activity);

		/*Clear data*/
		info("clear data");
		hpActivity.deleteActivity(activity);
	}

	/**
	 * == Display avatar of likers ==
	 * Test case ID: 78622
	 * Step 1: Check avatar of likers
	 */
	@Test
	public void test02_DisplayAvatarOfLikers(){
		//Declare variable
		String activity = "activity 78622";

		//Create data
		addActivity(true, activity, false,"");

		/* Step 1: Check avatar of likers */
		//- Connect to Intranet
		//- Check avatar of likers on activity
		//- The number of like is displayed
		hpActivity.likeOrUnlikeActivity(activity);
		//- Avatars of likers are displayed
		String avatarName = waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity).replace("${index}", "1")).getAttribute("alt");
		assert(avatarName.contains(user));

		/*Clear data*/
		info("clear data");
		hpActivity.deleteActivity(activity);
	}

	/**
	 * == Display message after 1 like (78625)==
	 * == Display message after 1 like by connected user (78628)==
	 * == Display message after 2 likes (78626)==
	 * == Display message after like by logging-in user and other user (78629)==
	 * == Display message after liking by connected user and other users (78630) ==
	 * == Display message after more than 2 user like activity ==
	 * Test case ID: 78625, 78628, 78626, 78629, 78630
	 * Step 1: Check message after 1 like
	 */
	@Test
	public void test03_DisplayMessageAfter1Like(){
		//Declare variable
		String activity = "activity 78625";

		//Create data
		//- User A and User B are connected (or User A and user B are member of the same space)
		//- An activity is shared
		//- User B and User C like activity
		//- User A can see activity liked by B and C
		//Create connection
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user1);
		magAcc.userSignIn(userType.PUBLISHER);
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user2);
		peoConn.acceptInvitation(user);
		magAcc.userSignIn(userType.DEVELOPER);
		peoConn.acceptInvitation(user1);
		magAcc.userSignIn(userType.PUBLISHER);

		//Create activity
		navToolBar.goToHomePage();
		addActivity(true, activity, false,"");

		/* Step 1: Check message after 1 like */
		//- Connect to Intranet with User A
		magAcc.userSignIn(userType.ADMIN);
		hpActivity.likeOrUnlikeActivity(activity);

		//- The message is dislayed: You liked this. (1 liker = you)
		waitForAndGetElement(ELEMENT_YOU_LIKE_THIS_ACTIVITY.replace("${activityText}", activity));

		//- The activity liked by the user B is displayed in the activity stream
		//- The message is displayed: User B liked this. (1 liker)
		magAcc.userSignIn(userType.PUBLISHER);
		waitForAndGetElement(ELEMENT_USER_NAME_LIKE_THIS_ACTIVITY.replace("${activityText}", activity).replace("${userName}", user));

		//user C like activity
		magAcc.userSignIn(userType.DEVELOPER);
		hpActivity.likeOrUnlikeActivity(activity);

		//- The message is dislayed:You and B liked this. (2 likers including you)
		waitForAndGetElement(ELEMENT_USER_NAME_LIKE_THIS_ACTIVITY.replace("${activityText}", activity).replace("${userName}", "You and "+user));

		//- The message is displayed: User B and User C liked this. (2 likers)
		magAcc.userSignIn(userType.PUBLISHER);
		waitForAndGetElement(ELEMENT_USER_NAME_LIKE_THIS_ACTIVITY.replace("${activityText}", activity).replace("${userName}", user2+" and "+user));

		//- The message is displayed: You and X others liked this. (number of likers = X+1) -- 78630
		hpActivity.likeOrUnlikeActivity(activity);
		int newNumLike = Integer.parseInt(waitForAndGetElement(hpActivity.ELEMENT_UNLIKE_ICON.replace("${activityText}", activity)).getText().trim());
		waitForAndGetElement(ELEMENT_USER_NAME_LIKE_THIS_ACTIVITY.replace("${activityText}", activity).replace("${userName}", "You and "+String.valueOf(newNumLike-1)+" others"));

		/*Clear data*/
		info("clear data");
		hpActivity.deleteActivity(activity);
		peoConn.removeConnection(user);
		peoConn.removeConnection(user2);
	}

	/**
	 * == Display message after more than 2 user like activity (78627) ==
	 * Test case ID: 78627
	 * Step 1: Check message
	 * ERROR: Refer: https://jira.exoplatform.org/browse/SOC-3072
	 */
	@Test (groups="error")
	public void test04_DisplayMessageAfterMoreThan2UserLikeActivity(){
		//Declare variable
		String activity = "activity 78627";

		//Create data
		//- An activity is added
		navToolBar.goToHomePage();
		addActivity(true, activity, false,"");

		//- Use A can see this activity
		//- There are some users like this
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user1);
		peoConn.connectPeople(user2);
		peoConn.connectPeople(user3);

		magAcc.userSignIn(userType.PUBLISHER);
		peoConn.acceptInvitation(user);
		navToolBar.goToHomePage();
		hpActivity.likeOrUnlikeActivity(activity);

		magAcc.userSignIn(userType.DEVELOPER);
		peoConn.acceptInvitation(user);
		navToolBar.goToHomePage();
		hpActivity.likeOrUnlikeActivity(activity);

		magAcc.userSignIn(userType.AUTHOR);
		peoConn.acceptInvitation(user);
		navToolBar.goToHomePage();
		hpActivity.likeOrUnlikeActivity(activity);

		magAcc.userSignIn(userType.ADMIN);
		peoConn.removeConnection(user1);
		peoConn.removeConnection(user2);
		peoConn.removeConnection(user3);

		/* Step 1: Check message */
		//- Connect to Intranet with User A
		navToolBar.goToHomePage();
		int newNumLike = Integer.parseInt(waitForAndGetElement(hpActivity.ELEMENT_LIKE_ICON.replace("${activityText}", activity)).getText().trim());
		waitForAndGetElement(ELEMENT_USER_NAME_LIKE_THIS_ACTIVITY.replace("${activityText}", activity).replace("${userName}", user3 +" and "+String.valueOf(newNumLike-1)+" others"));

		/*Clear data*/
		info("clear data");
		hpActivity.deleteActivity(activity);
	}

	/**
	 * == Display profile's popup of user liker (78631) ==
	 * == Display the like button after liking actvity (78632) ==
	 * == Displayed the oldest/newest liker (78624)
	 * Test case ID: 78631, 78632, 78624
	 * Step 1: Click like icon
	 * Step 2: Check Likes part
	 * - Show avatar of liker
	 * - Show user profile pop up
	 * - The like button in action bar with an highlighted color.
	 * - Avatar of the user B is displayed at the left
	 * - Avatar of the user A is displayed at the right
	 */
	@Test
	public void test07_CheckLikesPart(){
		//Declare variable
		String activity1 = "activity 67659";

		//Create data
		info("-- Create activity --");
		addActivity(true, activity1, false,"");

		//Connect people
		//Click on Connections on the left panel
		navToolBar.goToConnectionPage();

		//Display list of people
		//Click on Connect button to invite about 2 users
		peoConn.connectPeople(user1);

		//Login by invited users, go to My Connections/Requests Received
		magAcc.userSignIn(userType.PUBLISHER);
		//An user click on Confirm button
		peoConn.acceptInvitation(user);

		//- Go to Intranet home
		//- Click on Like activity in action bar part of an activity
		//- Like button is highlighted and the number of likers is updated
		navToolBar.goToHomePage();
		hpActivity.likeOrUnlikeActivity(activity1);

		/*Step 1: Like/Unlike Activity*/ 
		//- Go to Intranet home
		//- Click on Like activity in action bar part of an activity
		//- Like button is highlighted and the number of likers is updated
		magAcc.userSignIn(userType.ADMIN);
		hpActivity.likeOrUnlikeActivity(activity1);

		/*Step 2: Check Likes part*/
		//- Check avatar
		//- Avatar of liker is added into likes part, the oldest liker is displayed at the right and the newest at the left.
		info("-- Check mouse over avatar --");
		String avatarName = waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "1")).getAttribute("alt");
		String avatarName1 = waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "2")).getAttribute("alt");
		assert(avatarName.contains(user));
		assert(avatarName1.contains(user1));

		//- Mouse over the avatar
		mouseOver(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity1).replace("${index}", "1"),true);
		//- Profile pictures of users popup
		waitForAndGetElement(ELEMENT_USER_PROFILE_POPUP.replace("${userName}", user));

		//Clear data
		info("clear data");
		peoConn.removeConnection(user1);
		navToolBar.goToHomePage();
		hpActivity.deleteActivity(activity1);
	}

	/**
	 * == Expand the list of avatars ==
	 * Test case ID: 78623
	 * Step 1:Show [More] link
	 * Step 2: Click [More] link
	 * Note: not finish test case because this function is fail -> can't locate element to complete test case
	 * ERROR: Refer https://jira.exoplatform.org/browse/UI-2031
	 */
	@Test (groups="error")
	public void test05_ExpandTheListOfAvatars(){
		//Declare variable
		String activity = "activity 78623";
		String[] username = new String[15];

		//Create data
		addActivity(true, activity, false,"");

		//Create many user
		navToolBar.goToNewStaff();
		for(int i=0; i<15; i++){
			username[i] = getRandomString();
			String password = username[i];
			String firstName = username[i];
			String lastName = username[i];
			String displayName = username[i] + " " + username[i];
			String email = username[i]+"@gmail.com";
			magAcc.addNewUserAccount(username[i], password, password, firstName, lastName, displayName, email, null, null, true);
		}

		//Connect people
		navToolBar.goToConnectionPage();
		for(int i=0; i<15; i++){
			peoConn.connectPeople(username[i]+" "+username[i]);
		}

		for(int i=0; i<15; i++){
			magAcc.signOut();
			magAcc.signIn(username[i], username[i]);
			peoConn.acceptInvitation(user);
			navToolBar.goToHomePage();
			hpActivity.likeOrUnlikeActivity(activity);
		}

		//- Many user like activity more than the width
		//- User A can see this activity

		/* Step 1:Show [More] link */
		//- Connect to Intranet with User A
		magAcc.userSignIn(userType.ADMIN);

		//- The activity is displayed in the activity stream
		//- The icon Like is displayed 
		//- The number of like is displayed
		//- Avatars of user's likers are displayed
		//- The link "More" is displayed
		int j=1;
		for(int i=14;i>0;i--){
			String avatarName = waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity).replace("${index}", String.valueOf(j))).getAttribute("alt");
			assert(avatarName.contains(username[i]+" "+username[i]));
			j++;
		}
		String avatarName = waitForAndGetElement(ELEMENT_AVATAR_LIST_LIKER_INDEX.replace("${activityText}", activity).replace("${index}", String.valueOf(j))).getAttribute("alt");
		assert!(avatarName.contains(username[0]+" "+username[0]));

		/* Step 1:Show [More] link */
		//- Click on the link "More"
		//- Avatars are displayed in the next line
		
		/*Clear data*/
		info("clear data");
		hpActivity.deleteActivity(activity);
	}
}
