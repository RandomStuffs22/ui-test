package org.exoplatform.selenium.platform.plf.sniff;

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
 * 
 * @author hakt
 * @date 25/10/2013
 *
 */
public class PLF_HomepageGadget_CalendarGadget extends CalendarBase{

	ManageAccount magAcc;
	NavigationToolbar naviToolbar;
	BrandingManagement brandMag;
	Event event;


	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		naviToolbar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		brandMag = new BrandingManagement(driver);
		button = new Button(driver);
		event = new Event(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == SwitchBetweenDays ==
	 * Test case ID: 70423
	 * Step 1: - Login and connect to intranet home page
	 * Step 2: - Click on arrow on the right of date on calendar gadget -->  "TOMORROW" is displayed in title
	 * Step 3: - Click on arrow on the left of date on calendar gadget -->  "YESTERDAY" is displayed in title
	 */
	@Test
	public void test01_SwitchBetweenDays(){
		info("Check Today label when just logging in");
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TODAY_LABEL);

		info("Click next day arrow icon and check Tomorrow label");
		click(ELEMENT_CALENDAR_GADGET_NEXTDAY_ARROW);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_TOMORROW_LABEL);

		info("Click previous day arrow icon and check Tomorrow label");
		click(ELEMENT_CALENDAR_GADGET_PREVIOUSDAY_ARROW);
		click(ELEMENT_CALENDAR_GADGET_PREVIOUSDAY_ARROW);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_YESTERDAY_LABEL);
	}

	/**
	 * == ConfigureCalendarGadget ==
	 * Test case ID: 70425
	 * Step 1: - Login and connect to intranet home page - Create some Calendars, some events, tasks
	 * Step 2: - Login and go to intranet- Hovering this gadget, - Clicks on Setting button
	 * Check result: - A small configuration (Setting) button will appear
	                 - Settings are displayed.
                     - User can see the list of already selected calendars. 
                     - A small sentence "Displayed calendars" is displayed before the list of events' calendar.
	 */
	@Test
	public void test02_ConfigureCalendarGadget(){
		String Calendar_Information_1 = "Calendar_70425_1";
		String Calendar_Information_2 = "Calendar_70425_2";
		String Event_11 = "Event 11";
		String Event_12 = "Event 12";
		String Event_21 = "Event 21";
		String Event_22 = "Event 22";

		String Calendar_1_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar_Information_1);
		String Calendar_2_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar_Information_2);

		info("Create 2 calendars");
		goToCalendarPage();
		addCalendar(Calendar_Information_1, Calendar_Information_1, null);
		addCalendar(Calendar_Information_2, Calendar_Information_2, null);

		info("Add 2 events in each calendar");
		event.addQuickEvent(Event_11, Event_11, "", "", false, Calendar_Information_1);
		event.addQuickEvent(Event_12, Event_12, "", "", false, Calendar_Information_1);
		event.addQuickEvent(Event_21, Event_21, "", "", false, Calendar_Information_2);
		event.addQuickEvent(Event_22, Event_22, "", "", false, Calendar_Information_2);

		info("Go to calendar gadget");
		naviToolbar.goToHomePage();
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		Utils.pause(5000);

		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);
		waitForAndGetElement(By.xpath(Calendar_1_Link_In_Setting));
		waitForAndGetElement(By.xpath(Calendar_2_Link_In_Setting));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_VERIFIED_TEXT_LABEL);

		goToCalendarPage();
		deleteCalendar(Calendar_Information_1, true);
		deleteCalendar(Calendar_Information_2, true);
	}

	/**
	 * == AddCalendarToListOfFollowedCalendar ==
	 * Test case ID: 70426
	 * Step 1: - Login and connect to intranet home page - Create some Calendars		
	 * Step 2: - Login and go to intranet- Hovering this gadget, - Clicks on Setting button
	 * Step 3: - Remove calendar from list of followed calendar
	 * Step 4: - Add removed calendar to list of followed calendar
	 */
	@Test
	public void test03_AddCalendarToFollowedCalendarList(){
		String Calendar_Information_1 = "Calendar_70426";
		String Event_11 = "Event_70426_1";
		String Event_12 = "Event_70426_2";

		String Calendar_In_Calendar_Gadget = ELEMENT_CALENDAR_IN_CALENDAR_GADGET.replace("${calendar}", Calendar_Information_1);
		String Delete_Calendar_1_Path = ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}", Calendar_Information_1);
		String Calendar_In_Additional_List = ELEMENT_CALENDAR_IN_ADDITIONAL_LIST.replace("${calendar}", Calendar_Information_1);

		info("Create 1 calendar");
		goToCalendarPage();
		addCalendar(Calendar_Information_1, Calendar_Information_1, null);

		info("Add 2events in each calendar");
		event.addQuickEvent(Event_11, Event_11, "", "", false, Calendar_Information_1);
		event.addQuickEvent(Event_12, Event_12, "", "", false, Calendar_Information_1);

		info("Go to calendar gadget");
		naviToolbar.goToHomePage();
		waitForAndGetElement(By.xpath(Calendar_In_Calendar_Gadget));
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		Utils.pause(5000);

		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);

		click(By.xpath(Delete_Calendar_1_Path));
		button.ok();
		waitForElementNotPresent(By.xpath(Calendar_In_Calendar_Gadget));

		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		Utils.pause(5000);
		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);

		mouseOver(By.xpath(Calendar_In_Additional_List), true);
		Utils.pause(10000);
		if(plfVersion =="4.1"){
			
			click(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST_PLF_41);
		}
		else{// if (plfVersion =="4.0"){
			click(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST);
		}		
		
		button.ok();
		driver.navigate().refresh();
		waitForAndGetElement(By.xpath(Calendar_In_Calendar_Gadget));

		goToCalendarPage();
		deleteCalendar(Calendar_Information_1, true);
	}

	/**
	 * == RemoveCalendarFromFollowedCalendarList ==
	 * Test case ID: 70427
	 * Step 1: - Login and connect to intranet home page - Create 1 Calendar	
	 * Step 2: - Login and go to intranet- Hovering this gadget, - Clicks on Setting button
	 * Step 3: - Remove calendar from list of followed calendar --> Check results
	 */
	@Test
	public void test04_RemoveCalendarFromFollowedCalendarList(){
		String Calendar_Information = "Calendar_70427";
		String Event_11 = "Event_70427_1";
		String Event_12 = "Event_70427_2";

		String Calendar_In_Calendar_Gadget = ELEMENT_CALENDAR_IN_CALENDAR_GADGET.replace("${calendar}", Calendar_Information);
		String Delete_Calendar_1_Path = ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}", Calendar_Information);

		info("Create 1 calendar");
		goToCalendarPage();
		addCalendar(Calendar_Information, Calendar_Information, null);

		info("Add 2events in each calendar");
		event.addQuickEvent(Event_11, Event_11, "", "", false, Calendar_Information);
		event.addQuickEvent(Event_12, Event_12, "", "", false, Calendar_Information);

		info("Go to calendar gadget");
		naviToolbar.goToHomePage();
		waitForAndGetElement(By.xpath(Calendar_In_Calendar_Gadget));
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		Utils.pause(5000);

		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);

		click(By.xpath(Delete_Calendar_1_Path));
		button.ok();
		waitForElementNotPresent(By.xpath(Calendar_In_Calendar_Gadget));

		goToCalendarPage();
		deleteCalendar(Calendar_Information, true);
	}

	/*
	 * - Create 2 calendar with different name
	 * - Move mouse over Calendar gadget
	 * - Clicks on Setting button
	 * - Enter text into the Search field in settings form --> check result
	 */
	@Test
	public void test05_FilterCalendarInSettings(){
		String Calendar_Information_1 = "Calendar_70562";
		String Calendar_Information_2 = "Other_70562";

		String Delete_Calendar_1_Path = ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}", Calendar_Information_1);
		String Delete_Calendar_2_Path = ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}", Calendar_Information_2);
		String Calendar_1_Link_In_Setting = ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", Calendar_Information_1);
		String Calendar_2_Link_In_Setting = ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", Calendar_Information_2);

		info("Create 2 calendars");
		goToCalendarPage();
		addCalendar(Calendar_Information_1, Calendar_Information_1, null);
		addCalendar(Calendar_Information_2, Calendar_Information_2, null);

		info("Go to calendar gadget");
		naviToolbar.goToHomePage();
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		Utils.pause(5000);
		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);

		info("Delete 2 calendars from followed list");
		click(By.xpath(Delete_Calendar_1_Path));
		click(By.xpath(Delete_Calendar_2_Path));

		info("Fill in search box and check");
		type(ELEMENT_SEARCH_IN_CALENDAR_GADGET_SETTING,Calendar_Information_1, false);
		waitForAndGetElement(By.xpath(Calendar_1_Link_In_Setting));
		waitForElementNotPresent(By.xpath(Calendar_2_Link_In_Setting));

		goToCalendarPage();
		deleteCalendar(Calendar_Information_1, true);
		Utils.pause(1000);
		deleteCalendar(Calendar_Information_2, true);
	}
}
