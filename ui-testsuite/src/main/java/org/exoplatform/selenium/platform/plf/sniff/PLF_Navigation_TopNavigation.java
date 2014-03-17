package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePoll;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 31/10/2013
 *
 */
public class PLF_Navigation_TopNavigation extends BasicAction {
	//Platform
	ManageAccount magAcc;
	NavigationToolbar naviToolbar;
	PeopleProfile peoPro;
	ActionBar actBar;
	EcmsBase ecms;

	//Calendar base
	Event evt;

	//Forum
	ForumManageTopic mngTopic;
	ForumManagePoll mngPoll;
	ForumManageCategory mngCat;
	ForumManageForum mngFru;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver, this.plfVersion);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		peoPro = new PeopleProfile(driver, this.plfVersion);
		evt = new Event(driver, this.plfVersion);
		mngTopic = new ForumManageTopic(driver, this.plfVersion);
		mngPoll = new ForumManagePoll(driver, this.plfVersion);
		mngCat = new ForumManageCategory(driver, this.plfVersion);
		mngFru = new ForumManageForum(driver, this.plfVersion);
		actBar = new ActionBar(driver, this.plfVersion);
		ecms = new EcmsBase(driver, this.plfVersion);

		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Create a new Event via the top navigation ==
	 * Test case ID: 74363
	 * Step 1: Connect to intranet
	 * Step 2: Open form "Add new" to input data	
	 * Step 3: Create an event
	 */
	@Test
	public void test01_CreateANewEventViaTheTopNavigation(){
		/*Declare variables*/
		String eventName = "event74363";

		/*Step 1: Connect to intranet*/ 
		//- Login as a normal user
		//- Connect to Intranet
		//- The Top Navigation bar is displayed
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*Step 2: Open form "Add new" to input data*/ 
		//- Mouse over on the button "Create" (+)
		//- Select the item "Event/Task"
		//- Form to create Event/Task is displayed
		naviToolbar.goToEventTask();

		/*Step 3: Create an event*/ 
		//- Select "event" from "Add new"
		//- Input available data of date and times
		//- Select the calendar
		//- Click on the button "Next"
		//- Wait 2 second
		//- A message is displayed: "The event was added to $calendar_name"
		//- The message disappears with a fadeout effect
		evt.addEventTaskFromToolbarNavigation(true,eventName,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true,true);

		/*Clear data*/
		info("-- Clear data --");
		evt.goToCalendarPage();
		evt.deleteEventTask(eventName);
	}

	/**
	 * == Create a new Poll via the top navigation ==
	 * Test case ID: 74361
	 * Step 1: Connect to intranet
	 * Step 2: Open poll application	
	 */
	@Test
	public void test02_CreateANewPollViaTheTopNavigation(){
		/*Declare variables*/
		String titleCat = "Category 74361";
		String titleForum = "Forum 74361";
		String titleTop = "Topic 74361";
		String poll = "Poll of topic 74361";
		String[] options =  {"Option 7436101","Option 7436102"};

		/*Step 1: Connect to intranet*/ 
		//- Login as normal user
		//- Connect to Intranet
		//- The Top Navigation bar is displayed 
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);
		//Create forum (create data)
		mngTopic.goToForums();
		mngTopic.addCategoryForumTopic(titleCat, titleForum, titleTop,titleTop);
		naviToolbar.goToHomePage();

		/*Step 2: Open poll application	*/ 
		//- Mouse over on the button "Create" (+)
		//- Select "Poll"
		//- Click on the button "Next"
		//- Form to create new Poll is displayed
		//- A new Post editor is opened ready to create a new poll
		info("Add a new poll");
		mngPoll.addPoll(poll, options, "2", true, true,true,true,titleForum);

		/*Clear data*/
		click(By.linkText(titleCat));
		mngCat.deleteCategoryInForum(titleCat, true);
	}

	/**
	 * == Create a new Task via the top navigation ==
	 * Test case ID: 74364
	 * Step 1: Connect to Intranet
	 * Step 2: Open form "Add new Task"
	 * Step 3: Create a task
	 */
	@Test
	public void test03_CreateANewTaskViaTheTopNavigation(){
		/*Declare variables*/
		String taskName = "task74364";

		/*Step 1: Connect to intranet*/ 
		//- Login as normal user
		//- Connect to Intranet
		//- The Top Navigation bar is displayed
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*Step 2: Open form "Add new Task"	*/ 
		//- Mouse over on the button "Create" (+)
		//- Select the item "Event/Task"
		//- Form to create new Event/Task is displayed
		naviToolbar.goToEventTask();

		/*Step 3: Create a task*/ 
		//- Select "Task" from "Add new"
		//- Input available data of date and times
		//- Select the calendar
		//- Click on the button "Next"
		//- A message is displayed: "The task was added to $calendar_name"
		//- The message disappears with a fadeout effect
		evt.addEventTaskFromToolbarNavigation(false,taskName,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true,true);

		/*Clear data*/
		info("-- Clear data --");
		evt.goToCalendarPage();
		evt.deleteEventTask(taskName);
	}

	/**
	 * == Create a new Topic via the top navigation ==
	 * Test case ID: 74362
	 * Step 1: Connect to Intranet
	 * Step 2: Open Topic application
	 */
	@Test
	public void test04_CreateANewTopicViaTheTopNavigation(){
		/*Declare variables*/
		String category = "Category 74362";
		String forum = "Forum 74362";
		String[] permission = {};
		String[] addForum = {forum, "1","Open","Unlocked",forum};
		String topic1 = "Topic 74362";
		
		/*Step 1: Connect to intranet*/ 
		//- Login as normal user
		//- Connect to Intranet
		//- The Top Navigation bar is displayed 
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		//Create data
		mngTopic.goToForums();
		info("Add a forum, Edit a forum, Delete a forum");
		mngCat.addNewCategoryInForum(category, "1", 0,permission, category, 0,permission);

		//Add forum
		mngFru.addForum(category, addForum, true, "", "", true, 0, permission);

		/*Step 2: Open Topic application*/ 
		//- Mouse over on the button "Create" (+)
		//- Select "Topic"
		//- Click on the button "Next"
		//- Form to create new Topic is displayed
		//- A new Post editor is opened 
		naviToolbar.goToHomePage();
		mngTopic.addTopicFromTopNavigation(topic1, topic1, forum);
		
		/*Clear data*/
		info("-- Clear data --");
		click(By.linkText(category));
		mngCat.deleteCategoryInForum(category, true);
	}

	/**
	 * == Create a new Wiki page via the top navigation ==
	 * Test case ID: 74360
	 * Step 1: Connect to Intranet
	 * Step 2: Open wiki application
	 */
	@Test
	public void test05_CreateANewWikiPageViaTheTopNavigation(){
		/*Declare variables*/
		String title = "Wiki title 74360";
		String content = "Wiki content 74360";
		
		/*Step 1: Connect to intranet*/ 
		//- Login as normal user
		//- Connect to Intranet
		//- The Top Navigation bar is displayed
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*Step 2: Open wiki application*/ 
		//- Mouse over on the button "Create" (+)
		//- Select "Wiki Page"
		//- Choose a location to add a new wiki page from the space switcher
		//- Click on the button "Next"
		naviToolbar.goToWiki();
		//- The menu is updated to "Create a new Wiki Page:"
		//- The wiki application is opened with the New Page editor opened
		info("Add new wiki page at Rich Text mode");
		addWikiPageSourceEditor(title, content);
		click(ELEMENT_SAVE_BUTTON_ADD_PAGE);
		
		/*Clear data*/
		info("-- Clear data --");
		goToWikiHome();
		click(By.linkText(title));
		deleteCurrentWikiPage();
	}

	/**
	 * == Open user guide ==
	 * Test case ID: 74369
	 * Step 1: Connect to Intranet
	 * Step 2: Open user guide
	 */
	@Test
	public void test06_OpenUserGuide(){
		//Create data
		//Follow the rules of http://int.exoplatform.org/portal/intranet/wiki/group/spaces/platform_40/Navigation_Specification#HHelpFeature

		/*Step 1: Connect to intranet*/ 
		//- Login as normal user
		//- Connect to Intranet
		//- The Homepage is displayed 
		//- The Help button is displayed in the right of the bar
		/*Step 2: Open user guide*/ 
		//- Click on the button "?"
		click(ELEMENT_HELP_ICON);
		//- A new tab in the internet browser is opened
		//- The user guide is opened and the chapter displayed matched with the current navigation of the user.
		Utils.pause(1000);
		switchToNewWindow();
		String url = driver.getCurrentUrl();
		Utils.pause(1000);
		assert url.contains("http://docs.exoplatform.com");
	}

	/**
	 * == Upload a new file via the top navigation ==
	 * Test case ID: 74366
	 * Step 1: Connect to Intranet
	 * Step 2: Open Document selector popup
	 * ERROR: Refer https://jira.exoplatform.org/browse/PLF-5532
	 */
	@Test(groups="error")
	public void test07_UploadANewFileViaTheTopNavigation(){
		String driverName = "Personal Drives";
		String folderPath = "Personal Documents";
		String uploadFileName = "ECMS_DMS_SE_Upload_pdffile.pdf";
		String folder = "folder74366";
		/*Step 1: Connect to intranet*/ 
		//- Login as normal user
		//- Connect to Intranet
		//The Top navigation bar is displayed
		waitForAndGetElement(ELEMENT_NAVIGATION_TOOLBAR_HOMEPAGE);

		/*Step 2: Open Document selector popup*/ 
		//- From the menu Create "+", choose the option "Upload a file"
		//- Browse to file to upload
		//- The "Select File" pop up is displayed
		naviToolbar.goToUploadFile();
		//- File is uploaded successfully
		uploadFileFromTopNavigation(driverName,true,folderPath,"",uploadFileName,folder);
		
		/*Clear data*/
		info("-- Clear data --");
		naviToolbar.goToSiteExplorer();
		actBar.chooseDrive(ecms.ELEMENT_PERSONAL_DRIVE);
		actBar.actionsOnElement(folder, actionType.DELETE,true,true);
	}
}
