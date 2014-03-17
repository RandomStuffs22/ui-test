package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.*;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
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
		acc = new ManageAccount(driver);
		evt = new Event(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		sp = new SpaceManagement(driver);
		toolBar = new NavigationToolbar(driver);
		homeAct = new HomePageActivity(driver);
		button = new Button(driver);
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
		sp.goToMySpacePage();
		sp.accessSpace(space);
		sp.goToSpaceMenu("Agenda");
		deleteEventTask(event, selectDayOption.ONEDAY);
		
		//Check activity after deleting
		toolBar.goToHomePage();
		// homeAct.checkEventActivity(event, getCurrentDate("dd"), getCurrentDate("MMM"), false);
		
		//Delete spaces
		sp.goToMySpacePage();
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
		sp.goToMySpacePage();
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
		sp.goToMySpacePage();
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
		sp.goToMySpacePage();
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
		sp.goToMySpacePage();
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
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}
}
