package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activitystreamengagement;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.forum.ForumBase;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.exoplatform.selenium.platform.wiki.WikiBase;
import org.testng.annotations.*;

/**
 * @author chinhdtt
 *
 */

public class PLF_HomepageActivityStream_ActivityStreamEngagement extends Activity{
	ManageAccount acc; 
	HomePageActivity home; 
	NavigationToolbar nav; 
	ActionBar actBar;
	ManageMember mMember; 
	PeopleConnection pConn; 
	WikiBase wikiBase; 
	ForumBase forumBase; 
	CalendarBase calBase; 

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver, this.plfVersion);
		home = new HomePageActivity(driver, this.plfVersion); 
		acc.signIn(DATA_USER1, DATA_PASS);
		nav = new NavigationToolbar(driver, this.plfVersion);
		mMember = new ManageMember(driver,this.plfVersion);
		pConn = new PeopleConnection(driver, this.plfVersion);
		wikiBase = new WikiBase();
		forumBase = new ForumBase();
		calBase = new CalendarBase();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();		
		driver.quit();
	}

	/**
	 * Case ID:77752, 77753, 77754, 77756, 77758, 77759, 77760, 77761, 77762, 77763, 77764, 77765, 77766, 77767, 77768, 77769, 77770, 77755, 77757
	 * Test Case Name:
	 * ====== Display the hint block below the activity composer (77752)
	 * ====== Remove the hint block by click on the cross (77753)
	 * ====== Show again hint block (77754)
	 * ====== Display the Welcome activity (77756).
	 * ====== Display the Author field in the Welcome activity. (77758)
	 * ====== Display the Avatar in the Welcome activity. (77759)
	 * ====== Display the Date in the Welcome activity. (77760)
	 * ====== Display the content of the Welcome activity. (77761)
	 * ====== Open Connection page from the Welcome activity. (77762)
	 * ====== Open Wiki page from the Welcome activity. (77763)
	 * ====== Open Forum page from the Welcome activity. (77764)
	 * ====== Open Calendar page from the Welcome activity. (77765)
	 * ====== Open Documents page from the Welcome activity. (77766)
	 * ====== Open Spaces page from the Welcome activity. (77767)
	 * ====== Open Google Play page from the Welcome activity. (77769)
	 * ====== Open iOS App Store page from the Welcome activity.(77768)
	 * ====== Not Display action bar for the Welcome activity. (77770)
	 * ====== Remove the dismissable hint block by adding activity. (77755)
	 * ====== Remove the Welcome activity from the stream. (77757)
	 * Pre-Condition: Activity stream is empty
	 * Post-Condition: 
	 * Done with OSs and browsers : 
	 * Generated by chinhdtt at 2014/01/13 13:48:17
	 */
	@Test
	public  void test01_DisplayTheHintBlockBelowTheActivityComposer() {
		/*Create data*/
		String username = getRandomString();
		String password = username;
		String firstName = "firstName";
		String lastName = "lastName";
		String displayName = "";
		String email = username+"@platform.com";
		String userNameGiven = "";
		String language = "English";
		boolean verify = true;
		String currentURL;
		
		nav.goToNewStaff();
		acc.addNewUserAccount(username,password,password,firstName,lastName,displayName,email,userNameGiven,language,verify);
		acc.signOut();
		acc.signIn(username, password);
		nav.goToHomePage();
		
		info("Display the hint block below the activity composer");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- Below the Activity Composer a dismissable hint block is displayed: "This is the main place to share documents, links, ideas and updates about what you are working on."		*/
		String hintText = "This is the main place to share documents, links, ideas and updates about what you are working on.";
		assert waitForAndGetElement(ELEMENT_HINT_BLOCK).getText().contains(hintText);

		/*
		- A small cross icon is displayed		*/
		waitForAndGetElement(ELEMENT_CLOSE_HINT_BUTTON);

		/*
		- Click on the small cross icon from the hint block
		 *Input Data: 
		 *Expected Outcome: 
		-The hint block is dismissed temporary		*/ 
		click(ELEMENT_CLOSE_HINT_BUTTON);
		waitForElementNotPresent(ELEMENT_HINT_BLOCK);

		/*
		- Refresh the page
		 *Input Data: 
		 *Expected Outcome: 
		- The hint block reappears again		*/ 
		driver.navigate().refresh();
		Utils.pause(500);
		waitForAndGetElement(ELEMENT_CLOSE_HINT_BUTTON);
		
		info("Display the Welcome activity");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- A welcome activity appears in the stream		*/ 
		waitForAndGetElement(ELEMENT_WELCOME_ACTIVITY);			

		/*- Author field of the welcome activity is used to display this label : Welcome to your new Social Intranet!		*/ 
		info("Display the Author field in the Welcome activity");
		waitForAndGetElement(ELEMENT_ACTIVITY_AUTHOR_SPACENAME.replace("${activityText}", "Welcome to your new Social Intranet!"));

		/* - Avatar of the welcome activity isthe generic user avatar, see attachment: GenericAvatar.png		*/ 
		info("Display the Avatar in the Welcome activity.");
		waitForAndGetElement(ELEMENT_WELCOME_AVATAR.replace("${author}", "Welcome to your new Social Intranet!"));

		/* - Date time field displayed is always : less than a minute ago		*/ 
		info("Display the Date in the Welcome activity.");
		waitForAndGetElement(ELEMENT_COMMENT_DATETIME.replace("${activityText}", "Welcome to your new Social Intranet!").replace("${DATETIME}", "less than a minute ago"));
		
		info("Display the content of the Welcome activity");
		String description = "This is your activity stream. It will help you to keep an eye on important things happening in your social intranet."
							+"\nConnect and discuss with others"
							+"\nBuild your network by connecting with colleagues in Connections. They will be able to follow, comment on or like all the ideas, moods, status updates, links or documents that you share above. Let the discussion begin!"
							+"\nDiscover the power of social collaboration"
							+"\nYou can do a lot of useful things with Documents, Wikis, Forums and Calendars applications. The important things you do with them will be summarized automatically in the activity stream with just the right level of information to keep you and your network updated."
							+"\nUse spaces for team or project matters"
							+"\nCreate or join collaboration Spaces for your teams, projects or communities. Spaces are ideal places for collaborating on specific topics. All activity that happens in your spaces is also available in the activity stream."
							+"\nAnywhere, anytime"
							+"\nDownload mobile applications from the iOS App Store and Google Play. With your mobile device, you can also check the activity stream.";
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- A welcome activity appears in the stream
		- The content of the activity is:"This is your activity stream.It will help you to keep an eye on important things happening in your social intranet.Connect and discuss with othersBuild your network by connecting with colleagues in Connections. They will be able to follow, comment or like all the ideas, moods, status updates, links or documents that you share above. Let the discussion start!Discover the power of social collaborationYou can do a lot of useful things with Documents,Wiki, Forums and Calendarsapplications. Important things you do with them will be summarised automatically in the activity stream with just the right level of information to keep you and your network updated. Use spaces for team or project mattersCreate or join collaborationSpaces for your teams, projects or communities. Spaces are ideal places for collaborating on specific topics.All activity happening in your spaces is also available in the activity stream.Anywhere at anytimeDownload mobile applications from the iOS App Store and Google Play. With your mobile device, you can also check the activity stream."
		- Following items are displayed as link: * connections* wiki* forums* calendars* documents* spaces* iOS App Store* Google Play		*/
		assert waitForAndGetElement(ELEMENT_WELCOME_DESCRIPTION).getText().contains(description);	
		waitForAndGetElement(ELEMENT_WELCOME_CONNECTIONS_LINK);
		waitForAndGetElement(ELEMENT_WELCOME_DOCUMENTS_LINK);
		waitForAndGetElement(ELEMENT_WELCOME_WIKIS_LINK);
		waitForAndGetElement(ELEMENT_WELCOME_FORUMS_LINK);
		waitForAndGetElement(ELEMENT_WELCOME_CALENDAR_LINK);
		waitForAndGetElement(ELEMENT_WELCOME_SPACES_LINK);
		waitForAndGetElement(ELEMENT_WELCOME_APPSTORE_LINK);
		waitForAndGetElement(ELEMENT_WELCOME_GOOGLE_LINK);
		
		info("Open Connection page from the Welcome activity");
		/*
		- Click on the link "Connections"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "connexions" is displayed:/portal/intranet/connexions		*/ 
		click(ELEMENT_WELCOME_CONNECTIONS_LINK);
		waitForAndGetElement(pConn.ELEMENT_EVERYONE_TAB);
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("/portal/intranet/connexions");
		nav.goToHomePage();
		
		info("Open Wiki page from the Welcome activity");
		/*
		- Click on the link "Wiki"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "Wiki" is displayed:/portal/intranet/wiki		*/
		click(ELEMENT_WELCOME_WIKIS_LINK);
		waitForAndGetElement(wikiBase.ELEMENT_WIKI_HOME_PAGE);
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("/portal/intranet/wiki");
		nav.goToHomePage();
		
		info("Open Forum page from the Welcome activity");
		/*
		- Click on the link "Forum"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "Forum" is displayed:/portal/intranet/forum		*/ 
		click(ELEMENT_WELCOME_FORUMS_LINK);
		waitForAndGetElement(forumBase.ELEMENT_FORUM_STATE);
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("/portal/intranet/forum");
		nav.goToHomePage();
		
		info("Open Calendar page from the Welcome activity");
		/*
		- Click on the link "Calendar"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "Calendar" is displayed: /portal/intranet/calendar		*/ 
		click(ELEMENT_WELCOME_CALENDAR_LINK);
		waitForAndGetElement(calBase.ELEMENT_CALENDAR_PANEL);
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("/portal/intranet/calendar");
		nav.goToHomePage();
		
		info("Open Documents page from the Welcome activity");
		/*
		- Click on the link "Documents"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "Documents" is displayed: /portal/intranet/documents		*/ 
		click(ELEMENT_WELCOME_DOCUMENTS_LINK);		
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("/portal/intranet/documents");
		nav.goToHomePage();
		
		info("Spaces page from the Welcome activity");
		/*
		- Click on the link "Spaces"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "Spaces" is displayed: /portal/intranet/all-spaces		*/ 
		click(ELEMENT_WELCOME_SPACES_LINK);		
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("/portal/intranet/all-spaces");
		nav.goToHomePage();
		
		info("Open Google Play page from the Welcome activity");
		/*
		- Click on the link "Google Play"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "Google Play" is displayed: https://play.google.com/store/apps/details?id=org.exoplatform		*/ 
		click(ELEMENT_WELCOME_GOOGLE_LINK);
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("https://play.google.com/store/apps/details?id=org.exoplatform");
		driver.navigate().back();
				
		info("Open iOS App Store page from the Welcome activity");
		/*
		- Click on the link "iOS App Store"
		 *Input Data: 
		 *Expected Outcome: 
		- The page "iOS App Store" is displayed: https://itunes.apple.com/fr/app/exo-platform-3.5/id410476273?mt=8		*/ 
		click(ELEMENT_WELCOME_APPSTORE_LINK);
		currentURL = driver.getCurrentUrl();
		assert currentURL.contains("https://itunes.apple.com/fr/app/exo-platform-3.5/id410476273?mt=8");
		driver.navigate().back();
		
		info("Not Display action bar for the Welcome activity");
		/*
		- Connect to Intranet
		 *Input Data: 
		 *Expected Outcome: 
		- A welcome activity appears in the stream
		- The welcome activity cannot be liked or commented, these buttons are not available.		*/ 
		waitForAndGetElement(ELEMENT_WELCOME_ACTIVITY);
		waitForElementNotPresent(home.ELEMENT_ICON_COMMENT.replace("${title}", "Welcome to your new Social Intranet!"));
		waitForElementNotPresent(home.ELEMENT_LIKE_ICON.replace("${activityText}", "Welcome to your new Social Intranet!"));
		
		info("Remove the Welcome activity from the stream & Remove the dismissable hint block by adding activity");
		String text = "Activity 77755 & 77757";
		/*
		- Share an activity in the stream
		 *Input Data: 
		 *Expected Outcome: 
		- The new activity is shared and the welcome activity is not displayed anymore		*/ 
		addActivity(true, text, false,"");
		waitForElementNotPresent(ELEMENT_HINT_BLOCK);
		waitForElementNotPresent(ELEMENT_WELCOME_ACTIVITY);
		//delete data
		home.deleteActivity(text);
	}
}