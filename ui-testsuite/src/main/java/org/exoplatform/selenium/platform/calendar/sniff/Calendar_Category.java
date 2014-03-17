package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;

/**
 * @author havtt
 * @date 22 Oct 2013
 */
public class Calendar_Category extends CalendarBase{

	ManageAccount acc;
	Event evt;
	Task tsk;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		evt = new Event(driver);
		tsk = new Task(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		goToCalendarPage();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** 
	 * Add event category
	 * CaseID 74832
	 */
	@Test
	public void test01_addEventCategory() {
		String EVENT_CATEGORY_01 = "Event_Category_01";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add new Event category");
		driver.navigate().refresh();
		gotoAddEventCategory();
		addEventCategory(EVENT_CATEGORY_01);

		info("Restore data");
		gotoAddEventCategory();
		driver.navigate().refresh();
		deleteEventCategory(EVENT_CATEGORY_01);
	}

	/** 
	 * Edit event category
	 * CaseID 74833
	 */
	@Test
	public void test02_editEventCategory() {
		String EVENT_CATEGORY_02 = "Event_Category_02";
		String EDITED_EVENT_CATEGORY_02 = "edited_Event_Category_02";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add new Event category");
		gotoAddEventCategory();
		addEventCategory(EVENT_CATEGORY_02);

		info("Edit new Event Category");
		gotoAddEventCategory();
		editEventCategory(EVENT_CATEGORY_02,EDITED_EVENT_CATEGORY_02);

		info("Restore data");
		gotoAddEventCategory();
		deleteEventCategory(EVENT_CATEGORY_02);
	}

	/** 
	 * Delete event category
	 * CaseID 74834
	 */
	@Test
	public void test03_deleteEventCategory() {
		String EVENT_CATEGORY_03 = "Event_Category_03";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add new Event category");
		gotoAddEventCategory();
		addEventCategory(EVENT_CATEGORY_03);

		info("Delete new Event Category");
		gotoAddEventCategory();
		deleteEventCategory(EVENT_CATEGORY_03);
	}
}
