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

public class Calendar_Search extends CalendarBase{

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

	/**Calendar Quick Search
	 * CaseID 68645
	 */
	@Test
	public void test01_calendarQuickSearch() {

		String EVENT_NAME_01 = "QS_EVENT_01";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add new event");
		evt.addQuickEvent(EVENT_NAME_01,EVENT_NAME_01,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),false);

		info("Search an event");
		quickSearchCalendar(EVENT_NAME_01);

		info("Restore data");
		click(ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT);
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT_NAME_01));
		deleteEventTask(EVENT_NAME_01, selectDayOption.ONEDAY);
	}

	/**Calendar Advance Search
	 * CaseID 68646
	 */
	@Test
	public void test02_calendarAdvanceSearch(){
		String EVENT_NAME_02 = "QS_EVENT_02";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add new event");
		evt.addQuickEvent(EVENT_NAME_02,EVENT_NAME_02,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),false);

		info("Quick Search an event");
		quickSearchCalendar(EVENT_NAME_02);

		info("Advance Search an event");
		advanceSearchCalendar(EVENT_NAME_02);

		info("Restore data");
		click(ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT);
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", EVENT_NAME_02));
		deleteEventTask(EVENT_NAME_02, selectDayOption.ONEDAY);
	}
}
