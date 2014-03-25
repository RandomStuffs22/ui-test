package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.*;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Event.recurringType;
import org.exoplatform.selenium.platform.calendar.Event.repeatEndType;
import org.exoplatform.selenium.platform.calendar.Event.repeatType;
import org.exoplatform.selenium.platform.social.SpaceManagement;

/**
 * @author thuntn
 * @date 29 Oct 2013 
 */

public class Calendar_PublishActivities_Event extends CalendarBase{

	ManageAccount acc;
	Event evt;
	SpaceManagement sp;
	NavigationToolbar toolBar;
	HomePageActivity homeAct;
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		acc = new ManageAccount(driver, this.plfVersion);
		evt = new Event(driver, this.plfVersion);
		acc.signIn(DATA_USER1, DATA_PASS);
		sp = new SpaceManagement(driver, this.plfVersion);
		toolBar = new NavigationToolbar(driver, this.plfVersion);
		homeAct = new HomePageActivity(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**Publish activity for Event of Group Calendar of a Space, and delete event of space calendar
	 * CaseID 74734, CaseID 74739
	 */
	@Test
	public void test01_PublishActivityForEventOfGroupCalendarOfSpace() {
		String space = "Space74734";
		String event = "Event74734";
		info("Publish activity for Event of Group Calendar of a Space");
		
		//Add space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space,60000);
		sp.goToSpaceMenu("Agenda");
		
		//Add an event
		evt.addQuickEvent(event, event, null, null, false);
		
		//Check activity
		toolBar.goToHomePage();
		homeAct.checkEventActivity(event, getCurrentDate("dd"), getCurrentDate("MMM"));
		
		//Delete event
		sp.goToAllSpaces();
		sp.accessSpace(space);
		sp.goToSpaceMenu("Agenda");
		deleteEventTask(event, selectDayOption.ONEDAY);
		
		//Check activity after deleting
		toolBar.goToHomePage();
		// homeAct.checkEventActivity(event, getCurrentDate("dd"), getCurrentDate("MMM"), false);
		
		//Delete spaces
		sp.goToAllSpaces();
		sp.deleteSpace(space);
	}
	
	/**Update activity for event of Space Calendar- event is updated as all day event a space
	 * CaseID 74735
	 */
	@Test
	public void test02_UpdateActivityForEventWhenUpdateToAllDay() {
		String space = "Space74735";
		String event = "Event74735";
		info("Update activity for event of Space Calendar- event is updated as all day event a space");
		
		//Add a space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		sp.goToSpaceMenu("Agenda");
		
		//Add an event
		evt.addQuickEvent(event, event, null, null, false);
		evt.editEvent(event,null, null, null, null, null, true);
		
		//Check activity
		toolBar.goToHomePage();
		homeAct.checkEventActivity(event, getCurrentDate("dd"), getCurrentDate("MMM"));
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", event).replace("${comment}", homeAct.MSG_EVENT_COMMENT_UPDATE_ALL_DAY));
		
		//Delete space
		sp.goToAllSpaces();
		sp.deleteSpace(space);
	}
	
	/**Update activity for event of Space Calendar - event summary
	 * CaseID 74736
	 */
	@Test
	public void test03_UpdateActivityForEventWhenUpdateSummary() {
		String space = "Space747361";
		String event = "Event747361";
		String newEvent = "new Event74736";
		info("Update activity for event of Space Calendar - event summary");
		
		//Add a space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space,60000);
		sp.goToSpaceMenu("Agenda");
		
		//Add an event
		evt.addQuickEvent(event, event, null, null, false);
		
		//Edit an event
		evt.editEvent(event,newEvent, null, null,null, null, false);
		
		//Check activity
		toolBar.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", newEvent).replace("${comment}", homeAct.MSG_EVENT_COMMENT_UPDATE_SUMMARY.replace("${newTitle}",newEvent)));
		
		//Delete space
		sp.goToAllSpaces();
		sp.deleteSpace(space);
	}
	
	/**Update activity for event of Space Calendar - event description
	 * CaseID 74737
	 */
	@Test
	public void test04_UpdateActivityForEventWhenUpdateDescription() {
		String space = "Space74737";
		String event = "Event74737";
		String desc = "desc Event74737";
		info("Update activity for event of Space Calendar - event description");
		
		//Add a space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		sp.goToSpaceMenu("Agenda");
		
		//Add an event
		evt.addQuickEvent(event, event, null, null, false);
		
		//Edit an event
		evt.editEvent(event,null, desc,null, null, null, false);
		
		//Check activity
		toolBar.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", event).replace("${comment}", homeAct.MSG_EVENT_COMMENT_UPDATE_DESC.replace("${description}",desc)));
		
		//Delete space
		sp.goToAllSpaces();
		sp.deleteSpace(space);
	}
	
	/**Update activity for event of Space Calendar - event location
	 * CaseID 74738
	 */
	@Test
	public void test05_UpdateActivityForEventWhenUpdateLocation() {
		String space = "Space74738";
		String event = "Event74738";
		String location = "location Event74738";
		info("Update activity for event of Space Calendar - event location");
		
		//Add a space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		sp.goToSpaceMenu("Agenda");
		
		//Add an event
		evt.addQuickEvent(event, event, null, null, false);
		
		//Edit an event
		evt.editEvent(event,null, null, location, null, null, false);
		
		//Check activity
		toolBar.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", event).replace("${comment}", homeAct.MSG_EVENT_COMMENT_UPDATE_LOCATION.replace("${location}",location)));
		
		//Delete space
		sp.goToAllSpaces();
		sp.deleteSpace(space);
	}
	
	/**Redirect to Calendar Application
	 * CaseID 75290
	 */
	@Test
	public void test06_RedirectToSpaceCalendarApplication() {
		String space = "Space75290";
		String event = "Event75290";
		info("Redirect to Calendar Application");
		
		//Add a space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		sp.goToSpaceMenu("Agenda");
		
		//Add an event
		evt.addQuickEvent(event, event, null, null, false);
		
		//Open event from activity
		toolBar.goToHomePage();
		homeAct.checkEventActivity(event, getCurrentDate("dd"), getCurrentDate("MMM"));
		click(By.linkText(event));
		waitForAndGetElement(evt.ELEMENT_EVENT_PREVIEW_TITLE.replace("${event}", event));
		button.close();
		waitForAndGetElement(sp.ELEMENT_SPACE_BREAD.replace("${space}", space));
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event));
		
		//Delete space
		sp.goToAllSpaces();
		sp.deleteSpace(space);
	}
	
	/**
	 *Case ID:109227.
	 *Test Case Name: Activities should be updated after deleting of an edited recurring event
	 *Pre-Condition: 
	 * 	- Event should be in group calendars for spaces
	 *	- An edited recurring event is displayed, edited by drag & drop or by only this event option
	 *	- An activity of edited recurring event is displayed in the activity stream
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/03/24 14:55:52
	 */
	@Test
	public  void test07_ActivitiesShouldBeUpdatedAfterDeletingOfAnEditedRecurringEvent() {
		String name ="event 109227";
		String description = "description 109227";
		String space = "Space109227";
		/*creaate data*/
		info("Create data");
		sp.goToAllSpaces();
		sp.addNewSpace(space,space,60000);
		sp.goToSpaceMenu("Agenda");
		
		info("Test 7: Activities should be updated after deleting of an edited recurring event");
		/*Step Number: 1
		 *Step Name: Step 1: Open calendar application
		 *Step Description: 
			- Connect to Intranet
			- Open "Calendar" application
		 *Input Data: 

		 *Expected Outcome: 
			- The calendar application is displayed
			- An edited recurring event is displayed 
		 */
		evt.goToAddEvent();
		evt.inputBasicQuickEvent(name, description);
		evt.inputFromToEvent(getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false);
		click(evt.ELEMENT_BUTTON_EVENT_MORE_DETAILS);
		Utils.pause(1000);
		check(evt.ELEMENT_IS_REPEAT_CHECKBOX,2);
		evt.inputRecurringInfoEvent(repeatType.Daily, null, null, repeatEndType.After);
		click(evt.ELEMENT_ADD_EVENT_SAVE_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		Utils.pause(1000);
		info("Confirm added event displays in the calendar");
		assert evt.verifyEventInWeekView(name, getDate(0, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(1, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(2, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(3, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(4, "MMM dd yyyy"), selectDayOption.ONEDAY);
		dragAndDropToObject(By.xpath(ELEMENT_EVENT_TASK_DETAIL_DATE.replace("${taskName}", name).replace("${date}", getDate(0, "MMM dd yyyy"))),By.xpath(ELEMENT_ANY_TARGET_DATE.replace("${targetDate}", getDate(-1, "MMM dd yyyy HH")+":00:00")));
		Utils.pause(2000);
		mouseOver(By.xpath(ELEMENT_EVENT_TASK_DETAIL_DATE.replace("${taskName}", name).replace("${date}", getDate(-1, "MMM dd yyyy"))),true);
		waitForAndGetElement(evt.ELEMENT_TITLE_RECURRING_EVENT);
		waitForAndGetElement(evt.ELEMENT_DATE_TIME_RECURRING_EVENT);
		assert waitForAndGetElement(evt.ELEMENT_EDITED_RECURRING_TEXT_RECURRING_EVENT).getText().contains("Edited Recurring event");
		toolBar.goToHomePage();
		homeAct.checkEventActivity(name, getCurrentDate("dd"), getCurrentDate("MMM"));
		
		/*Step number: 2
		 *Step Name: Step 2: Delete the edited recurring event
		 *Step Description: 
			- Right click on the edited recurring event
			- Click Delete
		 *Input Data: 

		 *Expected Outcome: 
			- The edited recurring event is deleted*/
		goToCalendarPage();
		evt.deleteRecurringEvent(name, selectDayOption.ONEDAY, recurringType.ONLY_EVENT,getDate(-1, "MMM dd yyyy"));
		
		/*Step number: 3
		 *Step Name: Step 3: Check activity
		 *Step Description: 
			- Go to the Homepage
		 *Input Data: 

		 *Expected Outcome: 
			- In the activity stream: Activity of the edited recurring event is
			- A comment is added to the main activity (of series): Event cancelled for $CANCEL_DATE
			where $CANCEL_DATE : the date of the event removed*/
		toolBar.goToHomePage();
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", name).replace("${comment}", homeAct.MSG_EVENT_COMMENT_DELETE_EDITED_EVENT.replace("${date}",getDate(-1, "EEEE, MMMM dd, yyyy"))));
		
		/*Clear data*/
		info("Clear data");
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}
	
	/**
	 *Case ID:109225.
	 *Test Case Name: Activity of recurring event should be deleted after deleting all events
	 *Pre-Condition: 
	 * 	- Event should be in group calendars for spaces
	 * 	- A recurring event is displayed in the Calendar
	 * 	- An activity recurring event is displayed in the activity stream
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/03/24 14:55:52
	 */
	@Test
	public  void test08_ActivityOfRecurringEventShouldBeDeletedAfterDeletingAllEvents() {
		String name ="event 109225";
		String description = "description 109225";
		String space = "Space109225";
		
		/*creaate data*/
		info("Create data");
		sp.goToAllSpaces();
		sp.addNewSpace(space,space,60000);
		sp.goToSpaceMenu("Agenda");
		
		info("Test 8: Activity of recurring event should be deleted after deleting all events");
		/*Step Number: 1
		 *Step Name: Step 1: Open calendar application
		 *Step Description: 
			- Connect to Intranet
			- Open "Calendar" application
		 *Input Data: 

		 *Expected Outcome: 
			- The calendar application is displayed
			- An edited recurring event is displayed 
		 */
		evt.goToAddEvent();
		evt.inputBasicQuickEvent(name, description);
		evt.inputFromToEvent(getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false);
		click(evt.ELEMENT_BUTTON_EVENT_MORE_DETAILS);
		Utils.pause(1000);
		check(evt.ELEMENT_IS_REPEAT_CHECKBOX,2);
		evt.inputRecurringInfoEvent(repeatType.Daily, null, null, repeatEndType.After);
		click(evt.ELEMENT_ADD_EVENT_SAVE_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		Utils.pause(1000);
		toolBar.goToHomePage();
		homeAct.checkEventActivity(name, getCurrentDate("dd"), getCurrentDate("MMM"));
		
		/*Step number: 2
		 *Step Name: Step 2: Choose an event
		 *Step Description: 
			- Right click on an event from the series
			- Click Delete
		 *Input Data: 

		 *Expected Outcome: 
			- The Delete pop up is displayed*/

		/*Step number: 3
		 *Step Name: Step 3: Delete event
		 *Step Description: 
			- Choose "All events" option
			- Click on Delete
		 *Input Data: 

		 *Expected Outcome: 
			- All events of the series are deleted*/
		goToCalendarPage();
		evt.deleteRecurringEvent(name, selectDayOption.ONEDAY, recurringType.ALL_EVENT,getDate(0, "MMM dd yyyy"));
		assert !evt.verifyEventInWeekView(name, getDate(0, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert !evt.verifyEventInWeekView(name, getDate(1, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert !evt.verifyEventInWeekView(name, getDate(2, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert !evt.verifyEventInWeekView(name, getDate(3, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert !evt.verifyEventInWeekView(name, getDate(4, "MMM dd yyyy"), selectDayOption.ONEDAY);
		
		/*Step number: 4
		 *Step Name: Step 4: Check activity after delete event
		 *Step Description: 
			- Go to the Homepage
		 *Input Data: 

		 *Expected Outcome: 
			- In the activity stream, recurring event activity is deleted*/
		toolBar.goToHomePage();
		homeAct.checkEventActivity(name, getCurrentDate("dd"), getCurrentDate("MMM"),false);
		
		/*Clear data*/
		info("Clear data");
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}
	
	/**
	 *Case ID:109224.
	 *Test Case Name: A comment to event activity should be added after adding a repeat event
	 *Pre-Condition: 
	 * Event should be in group calendars for spaces
	 * An event is displayed in Calendar
	 * An activity event is displayed in the activity stream
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/03/24 14:55:52
	 */
	@Test
	public  void test09_ACommentToEventActivityShouldBeAddedAfterAddingARepeatEvent() {
		String name ="event 109224";
		String description = "description 109224";
		String space = "Space109224";
		
		/*creaate data*/
		info("Create data");
		sp.goToAllSpaces();
		sp.addNewSpace(space,space,60000);
		sp.goToSpaceMenu("Agenda");
		info("Test 9: A comment to event activity should be added after adding a repeat event");
		/*Step Number: 1
		 *Step Name: Step 1: Show application calendar
		 *Step Description: 
			- Connect to Intranet
			- Open "Calendar" application
		 *Input Data: 

		 *Expected Outcome: 
			- An event is displayed 
		 */
		evt.addQuickEvent(name, description,getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false);
		
		/*Step number: 2
		 *Step Name: Step 2: Chang event to recurring event
		 *Step Description: 
			- Edit the event
			- Click "More details"
			- Check "Repeat" option
			- Click Save
		 *Input Data: 

		 *Expected Outcome: 
			- A recurring event is added*/
		evt.goToEditEventForm(name);
		check(evt.ELEMENT_IS_REPEAT_CHECKBOX,2);
		evt.inputRecurringInfoEvent(repeatType.Daily, null, null, repeatEndType.After);
		click(evt.ELEMENT_ADD_EVENT_SAVE_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		Utils.pause(1000);
		
		/*Step number: 3
		 *Step Name: Step 3: Check acitity after add recurring event
		 *Step Description: 
			- Go to the Homepage
		 *Input Data: 

		 *Expected Outcome: 
			- In the activity stream, a comment is added to the main activity event: "Event will be repeated $REPETITION"
			where $REPETITION : strong format of the recurring settings (e.g : every week on Wednesday)*/
		toolBar.goToHomePage();
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", name).replace("${comment}", homeAct.MSG_EVENT_REPEAT_EVERYDAY));

		/*Clear data*/
		info("Clear data");
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}
	
	/**
	 *Case ID:109226.
	 *Test Case Name: An activity should displayed after drag and drop an event
	 *Pre-Condition: 
	 * - Event should be in group calendars for spaces
	 * - A recurring event is displayed in Calendar
	 *Post-Condition: 
	 *Done with OSs and browsers : 
	 *Generated by phuongdt at 2014/03/24 14:55:52
	 */
	@Test
	public  void test10_AnActivityShouldDisplayedAfterDragAndDropAnEvent() {
		String name ="event 109226";
		String description = "description 109226";
		info("Test 10: An activity should displayed after drag and drop an event");
		
		String space = "Space109226";
		
		/*creaate data*/
		info("Create data");
		sp.goToAllSpaces();
		sp.addNewSpace(space,space,60000);
		sp.goToSpaceMenu("Agenda");
		
		/*Step Number: 1
		 *Step Name: Step 1: Show application calendar
		 *Step Description: 
			- Connect to Intranet
			- Open "Calendar" application
		 *Input Data: 

		 *Expected Outcome: 
			- An event is displayed 
		 */
		evt.goToAddEvent();
		evt.inputBasicQuickEvent(name, description);
		evt.inputFromToEvent(getDate(0,"MM/dd/yyyy"), getDate(0,"MM/dd/yyyy"), false);
		click(evt.ELEMENT_BUTTON_EVENT_MORE_DETAILS);
		Utils.pause(1000);
		check(evt.ELEMENT_IS_REPEAT_CHECKBOX,2);
		evt.inputRecurringInfoEvent(repeatType.Daily, null, null, repeatEndType.After);
		click(evt.ELEMENT_ADD_EVENT_SAVE_BUTTON);
		waitForElementNotPresent(evt.ELEMENT_ADD_EVENT_POPUP);
		Utils.pause(1000);
		info("Confirm added event displays in the calendar");
		assert evt.verifyEventInWeekView(name, getDate(0, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(1, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(2, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(3, "MMM dd yyyy"), selectDayOption.ONEDAY);
		assert evt.verifyEventInWeekView(name, getDate(4, "MMM dd yyyy"), selectDayOption.ONEDAY);
		
		/*Step number: 2
		 *Step Name: Step 2: Drag & drop event
		 *Step Description: 
			- Drag and drop an event from the series
			- Click Save
		 *Input Data: 

		 *Expected Outcome: 
			- Starts and End time are edited*/

		dragAndDropToObject(By.xpath(ELEMENT_EVENT_TASK_DETAIL_DATE.replace("${taskName}", name).replace("${date}", getDate(0, "MMM dd yyyy"))),By.xpath(ELEMENT_ANY_TARGET_DATE.replace("${targetDate}", getDate(-1, "MMM dd yyyy HH")+":00:00")));
		Utils.pause(2000);
		mouseOver(By.xpath(ELEMENT_EVENT_TASK_DETAIL_DATE.replace("${taskName}", name).replace("${date}", getDate(-1, "MMM dd yyyy"))),true);
		waitForAndGetElement(evt.ELEMENT_TITLE_RECURRING_EVENT);
		waitForAndGetElement(evt.ELEMENT_DATE_TIME_RECURRING_EVENT);
		assert waitForAndGetElement(evt.ELEMENT_EDITED_RECURRING_TEXT_RECURRING_EVENT).getText().contains("Edited Recurring event");
		
		/*Step number: 3
		 *Step Name: Step 3: Check activity after drap & drop event
		 *Step Description: 
			- Go to the Homepage
		 *Input Data: 

		 *Expected Outcome: 
			- A new activity is created for the recurring event*/
		toolBar.goToHomePage();
		waitForAndGetElement(homeAct.ELEMENT_EVENT_ACTIVITY_DATE_TIME_INFO.replace("${index}", "1")).getText().contains(getDate(-1, "EEEE, MMMM dd, yyyy"));
		waitForAndGetElement(homeAct.ELEMENT_EVENT_ACTIVITY_DATE_TIME_INFO.replace("${index}", "2")).getText().contains(getDate(0, "EEEE, MMMM dd, yyyy"));

		/*Clear data*/
		info("Clear data");
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}
}
