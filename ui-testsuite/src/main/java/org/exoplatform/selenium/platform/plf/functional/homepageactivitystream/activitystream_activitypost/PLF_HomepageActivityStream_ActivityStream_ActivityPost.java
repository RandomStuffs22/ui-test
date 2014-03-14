package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activitystream_activitypost;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: LanLTK
 * @date: 12/26/2013
 */
public class PLF_HomepageActivityStream_ActivityStream_ActivityPost extends Activity{

	ManageAccount magAc;
	NavigationToolbar naviToolbar;
	HomePageActivity hpActivity;
	SpaceManagement spaceMag;
	PeopleConnection conn;
	String fullname_john = "John Smith";
	String fullname_demo="Jack Miller";
	String user_demo = DATA_USER4;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		magAc = new ManageAccount(driver, this.plfVersion);
		naviToolbar = new NavigationToolbar(driver);
		hpActivity = new HomePageActivity(driver);
		spaceMag = new SpaceManagement(driver);
		conn = new PeopleConnection(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);

	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/*
	 * CaseId 77705: Post an activity with "All activities" filter selected
	 * CaseId 77701: Comment from "All Activities"
	 */
	@Test	
	public void test01_ActivityComment_AllActivitiesFilter(){
		String activity = "All Activities filter: Post an activity.";
		String comment = "All Activities filter: Comment for an activity.";
		naviToolbar.goToHomePage();

		//Step1: All Activities filter is displayed
		selectFileter("All Activities");

		//Add an activity
		addActivity(true,activity,false, "");

		//Step2: Add a comment
		addComment(activity,comment);

		//Delete activity before exit test cases
		hpActivity.deleteActivity(activity);
	}

	/*
	 * CaseId 77702: Post an activity with "My Spaces" filter selected
	 * CaseId 77706: Comment from "My Spaces"
	 * Precondition: Space1 is already exist.
	 */
	@Test	
	public void test02_ActivityComment_MySpacesFilter(){
		String activity = "My Spaces filter: Post an activity.";
		String spaceName = "Space77702";
		String comment = "My Spaces filter: Comment for an activity.";

		// Create data for Pre-Conditions
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName, "");	

		// Start test case
		naviToolbar.goToHomePage();

		//Step1: My Spaces filter is displayed
		selectFileter("My Spaces");

		//Add an activity in Space1
		click(By.linkText(spaceName));
		addActivity(true,activity,false, "");

		//Go to Home, select All Activities filter
		naviToolbar.goToHomePage();
		selectFileter("All Activities");

		//Step2: Add a comment
		addComment(activity,comment);

		//Select My Spaces filter
		selectFileter("My Spaces");

		//Delete activity before exit test cases
		hpActivity.deleteActivity(activity);
		spaceMag.goToMySpacePage();
		spaceMag.deleteSpace(spaceName,300000);
	}

	/*
	 * CaseId 77704: Post an activity with "My activities" filter selected
	 * CaseId 77707: Comment from "My Activities"
	 */
	@Test	
	public void test03_ActivityComment_MyActivitiesFilter(){
		String activity = "My Activities filter: Post an activity.";
		String comment = "My Activities filter: Comment for an activity.";
		naviToolbar.goToHomePage();

		//Step1: My Activities filter is displayed
		selectFileter("My Activities");

		//Add an activity
		addActivity(true,activity,false, "");

		selectFileter("My Activities");

		//Step2: Add a comment
		addComment(activity,comment);

		//Delete activity before exit test cases
		hpActivity.deleteActivity(activity);
	}	

	/*
	 * CaseId 77703: Post an activity with "Connections" filter selected
	 * CaseId 77708: Comment from "Connections"
	 * Pre-Condition: john and demo must be connected each other before tests started
	 */	  
	@Test	
	public void test04_ActivityComment_ConnectionsFilter(){
		String activity = "Connections filter: Post an activity by demo.";
		String comment = "Connections filter: Comment for an activity by john.";

		// Create data for Pre-Conditions 
		naviToolbar.goToConnectionPage();
		conn.connectPeople(fullname_demo);
		magAc.userSignIn(userType.DEVELOPER);
		naviToolbar.goToConnectionPage();
		conn.acceptInvitation(fullname_john);
		magAc.userSignIn(userType.ADMIN);

		// Start test case
		naviToolbar.goToHomePage();

		//Step1: Connections filter is displayed
		selectFileter("Connections");

		//sign-in by demo. Add an activity
		magAc.userSignIn(userType.DEVELOPER);
		naviToolbar.goToHomePage();
		addActivity(true,activity,false, "");

		//sign-in by john. Select Connections filter
		magAc.userSignIn(userType.ADMIN);
		naviToolbar.goToHomePage();  
		selectFileter("Connections");

		//Step2: Add a comment. Select All Activities filter.
		addComment(activity,comment);
		selectFileter("All Activities");

		//Select Connections filter
		selectFileter("Connections");

		//Delete activity before exit test cases
		magAc.userSignIn(userType.DEVELOPER);
		naviToolbar.goToHomePage();
		hpActivity.deleteActivity(activity);
		naviToolbar.goToConnectionPage();
		conn.removeConnection(fullname_john);
	}

}
