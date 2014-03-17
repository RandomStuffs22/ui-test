package org.exoplatform.selenium.platform.plf.functional.homepagegadgets.calendargadget;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.BrandingManagement;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author HangNtt
 *
 */
public class PLF_HomepageGadgets_CalendarGadget_Events extends CalendarBase{

	ManageAccount magAcc;
	NavigationToolbar naviToolbar;
	BrandingManagement brandMag;
	Event event;
	CalendarBase Calbase;


	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		magAcc = new ManageAccount(driver, this.plfVersion);
		brandMag = new BrandingManagement(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		event = new Event(driver, this.plfVersion);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**
	 * == Display a past event in Calendar Gadget ==
	 * Test case ID: 69113
	 */

	@Test
	public void test01_DisplayAPastEventInCalendarGadget(){
		String Event_11 = "Event 69113";
		String date = getDate(-1,"M/dd/yyyy");
		info("Go to calendar");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event_11, Event_11, getDate(-2,"MM/dd/yyyy"), getDate(-1, "MM/dd/yyyy"), false);
		naviToolbar.goToHomePage();
		click(ELEMENT_CALENDAR_GADGET_PREVIOUSDAY_ARROW);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_YESTERDAY_LABEL);
		waitForAndGetElement(ELEMENT_EVENT_TASK_ITEM_IN_CALENDAR_GADGET.replace("${event}", Event_11).replace("${time}",date));
		goToCalendarPage();
		event.deleteEventTask(Event_11,selectDayOption.ALLDAY);
		
	}
	/**
	 * == Display an event scheduled for all the day ==
	 * Test case ID: 69103
	 */

	@Test
	public void test02_DisplayAnEventScheduledForAllTheDay(){
		String Event_1 = "Event 69103";
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event_1, Event_1, "", "", true);
		naviToolbar.goToHomePage();
		click(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		waitForAndGetElement(ELEMENT_EVENT_TASK_ITEM_IN_CALENDAR_GADGET.replace("${event}", Event_1).replace("${time}","All Day"));
		goToCalendarPage();
		event.deleteEventTask(Event_1,selectDayOption.ALLDAY);
		
	}
	
	/**
	 * == Display an event with a long title in Calendar Gadget ==
	 * Test case ID: 69112
	 * PENDING: Can't find xpath with long title
	 */

	@Test (groups="pending")
	public void test03_DisplayAnEventWithALongTitle(){
		String Event_1 = "Display an event with a long title in Calendar Gadget Display an event with a long title in Calendar Gadget Display an event with a long title in Calendar Gadget";
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event_1, Event_1, "", "", true);
		naviToolbar.goToHomePage();
		waitForTextPresent(Event_1);
		Utils.pause(5000);
		goToCalendarPage();
		event.deleteEventTask(Event_1,selectDayOption.ONEDAY);
		
	}
	
	/**
	 * == Display an event scheduled on multiple day ==
	 * Test case ID: 69104
	 */
	@Test
	public void test04_DisplayAnEventScheduledOnMultipeDay(){
		String Event = "event 69104";
		String date1 = getDate(1,"M/dd/yyyy");
		String date2 = getDate(2,"M/dd/yyyy");
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event, Event, getDate(1,"MM/dd/yyyy"), getDate(2, "MM/dd/yyyy"), false);
		naviToolbar.goToHomePage();
		click(ELEMENT_CALENDAR_GADGET_TOMORROW_LABEL);
		click(ELEMENT_CALENDAR_GADGET_TOMORROW_LABEL);
		waitForAndGetElement(ELEMENT_EVENT_TASK_MULTIDATE_ITEM_IN_CALENDAR_GADGET.replace("${event}", Event).replace("${totime}",date1).replace("${endtime}",date2));
		goToCalendarPage();
		event.deleteEventTask(Event,selectDayOption.ONEDAY);
	}
	
	/**
	 * == Display an event scheduled on multiple day ==
	 * Test case ID: 69099
	 */

	@Test
	public void test05_DisplayAnEventPartInGadgetCalendar(){
		String Event_1 = "Event 69099";
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event_1, Event_1, "", "", true);
		naviToolbar.goToHomePage();
		click(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		waitForAndGetElement(ELEMENT_EVENT_TASK_ITEM_IN_CALENDAR_GADGET.replace("${event}", Event_1).replace("${time}","All Day"));
		goToCalendarPage();
		event.deleteEventTask(Event_1,selectDayOption.ALLDAY);
	}
	/**
	 * == Display events with different start time ==
	 * Test case ID: 69109
	 */

	@Test
	public void test06_DisplayAnEventWithDifferentStartTime(){

		String Event = "Event691091";
		String Event_1 = "Event691092";
		
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event, Event, getDate(1,"MM/dd/yyyy"), getDate(2, "MM/dd/yyyy"), false);
		event.addQuickEvent(Event_1, Event_1, getDate(2,"MM/dd/yyyy"), getDate(2, "MM/dd/yyyy"), false);
		naviToolbar.goToHomePage();
		click(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		click(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		waitForTextPresent(Event);
		waitForTextPresent(Event_1);
		Utils.pause(5000);
		goToCalendarPage();
		event.deleteEventTask(Event_1,selectDayOption.ONEDAY);
		event.deleteEventTask(Event,selectDayOption.ONEDAY);
		
	}
	/**
	 * == Display events with multiple day ==
	 * Test case ID: 69111
	 */

	@Test
	public void test07_DisplayAnEventWithMultipeDay(){

		String Event = "Display events with multiple day";
		String Event_1 = "Display events with multiple day_1";
		String ELEMENT_VERITY_DATE_OLDER = "//*[contains(@class,'eventsList')]/li[2]//span[text()='${older_date_before}']/../../../../../li[2]//span[text()='${older_date_after}']";
		String ELEMENT_VERITY_DATE_LASTEST = "//*[contains(@class,'eventsList')]/li[1]//span[text()='${lastest_date_before}']/../../../../../li[2]//span[text()='${lastest_date_after}']";
		String older_date_before = getDate(-1,"M/dd/yyyy");
		String older_date_after = getDate(1, "M/dd/yyyy");
		String lastest_date_before = getDate(0,"M/dd/yyyy");
		String lastest_date_after = getDate(1, "M/dd/yyyy");
		
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event, Event, getDate(-1,"M/dd/yyyy"), getDate(1, "M/dd/yyyy"), false);
		event.addQuickEvent(Event_1, Event_1, getDate(0,"M/dd/yyyy"), getDate(1, "M/dd/yyyy"), false);
		naviToolbar.goToHomePage();
		click(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		waitForAndGetElement(ELEMENT_VERITY_DATE_OLDER.replace("${older_date_before}", older_date_before).replace("${older_date_after}", older_date_after));
		waitForAndGetElement(ELEMENT_VERITY_DATE_LASTEST.replace("${lastest_date_before}", lastest_date_before).replace("${lastest_date_after}", lastest_date_after));
		Utils.pause(5000);
		goToCalendarPage();
		event.deleteEventTask(Event_1,selectDayOption.ONEDAY);
		event.deleteEventTask(Event,selectDayOption.ONEDAY);
		
	}
	/**
	 * == Display events with same start time and with different duration ==
	 * Test case ID: 69110
	 */

	@Test
	public void test08_DisplayAnEventWithSameStartTimeAndWithDifferentDuration(){

		String Event = "Display events with same start time and with different duration";
		String Event_1 = "Display events with same start time and with different duration";
		
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event, Event, getDate(1,"MM/dd/yyyy"), getDate(2, "MM/dd/yyyy"), false);
		event.addQuickEvent(Event_1, Event_1, getDate(1,"MM/dd/yyyy"), getDate(3, "MM/dd/yyyy"), false);
		naviToolbar.goToHomePage();
		click(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		click(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		waitForTextPresent(Event);
		waitForTextPresent(Event_1);
		Utils.pause(5000);
		goToCalendarPage();
		event.deleteEventTask(Event_1,selectDayOption.ONEDAY);
		event.deleteEventTask(Event,selectDayOption.ONEDAY);
		
	}
	/**
	 * == Display many events in Gadget Calendar ==
	 * Test case ID: 69108
	 */

	@Test
	public void test09_DisplayManyEventInGagetCalendar(){

		String Event = "Event 69108";
		String Event_1 = "Event 69108_1";
		String Event_2 = "Event 69108_2";
		String Event_3 = "Event 69108_3";
		String Event_4 = "Event 69108_4";
		String Event_5 = "Event 69108_5";
		String Event_6 = "Event 69108_6";
		int oldSize;
		int newSize;
		By element = By.xpath("//div[@class='calendarPortletData uiBox']");
		oldSize = waitForAndGetElement(element).getSize().getHeight();
		
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event, Event, "", "", true);
		event.addQuickEvent(Event_1, Event_1, "", "", true);
		event.addQuickEvent(Event_2, Event_2, "", "", true);
		event.addQuickEvent(Event_3, Event_3, "", "", true);
		event.addQuickEvent(Event_4, Event_4, "", "", true);
		event.addQuickEvent(Event_5, Event_5, "", "", true);
		event.addQuickEvent(Event_6, Event_6, "", "", true);
		naviToolbar.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		element = By.xpath("//div[@class='calendarPortletData uiBox']");
		newSize = waitForAndGetElement(element).getSize().getHeight();
		info(String.valueOf(newSize));
		info(String.valueOf(oldSize));
		assert (oldSize!=newSize);
		Utils.pause(5000);
		goToCalendarPage();
		event.deleteEventTask(Event,selectDayOption.ALLDAY);
		event.deleteEventTask(Event_1,selectDayOption.ALLDAY);
		event.deleteEventTask(Event_2,selectDayOption.ALLDAY);
		event.deleteEventTask(Event_3,selectDayOption.ALLDAY);
		event.deleteEventTask(Event_4,selectDayOption.ALLDAY);
		event.deleteEventTask(Event_5,selectDayOption.ALLDAY);
		event.deleteEventTask(Event_6,selectDayOption.ALLDAY);
}
	/**
	 * == Open the Calendar application on event's details from Calendar Gadget ==
	 * Test case ID: 69108
	 */

	@Test
	public void test10_OpenTheCalendarApplicationOnEventDetailFromCalendarGadget(){

		String Event = "Event 69114";
		String ELEMENT_TITLE_OF_EVENT = "//div[@class='pull-left eventSummary']//a[text()='${Event}']";
		By ELEMENT_PREVIEW_EVENT = By.xpath("//div[contains(@class,'uiPreview noRounded uiBox resizable')]");
		info("Go to Calendar page");
		goToCalendarPage();
		info("Add events in calendar");
		event.addQuickEvent(Event, Event, "", "", true);
		naviToolbar.goToHomePage();
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);
		click(ELEMENT_TITLE_OF_EVENT.replace("${Event}",Event));
		Utils.pause(1000);
		//waitForAndGetElement(ELEMENT_CALENDAR_PANEL);
		waitForAndGetElement(ELEMENT_PREVIEW_EVENT, 30000,1,2);
		button.close();
		info("delete event");
		event.deleteEventTask(Event,selectDayOption.ALLDAY);
		
		
	}
}

