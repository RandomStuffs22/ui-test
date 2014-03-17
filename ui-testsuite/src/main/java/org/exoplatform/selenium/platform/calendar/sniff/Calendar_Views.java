package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;

/**
 * @author havtt
 * @date 23 Oct 2013
 */


public class Calendar_Views extends CalendarBase{

	ManageAccount acc;
	Event evt;
	Task tsk;
	Button but;

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


	/** Check displaying added task/event in all views
	 * CaseID 68658
	 */
	@Test
	public void test01_checkEventAllViews() {
		String EVENT_NAME_01 = "VIEWS_EVENT_01";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add new event");
		evt.addQuickEvent(EVENT_NAME_01,EVENT_NAME_01,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);

		info("Confirm in Week view");
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}",EVENT_NAME_01));

		info("Switch to Day view");
		click(ELEMENT_BUTTON_DAY_VIEW);
		waitForAndGetElement(EVENT_DAY_VIEW.replace("${eventTitle}",EVENT_NAME_01),DEFAULT_TIMEOUT,1);

		info("Switch to Month view");
		click(ELEMENT_BUTTON_MONTH_VIEW);
		waitForAndGetElement(EVENT_MONTH_VIEW.replace("${eventTitle}",EVENT_NAME_01),60000);

		info("Switch to List view");
		click(ELEMENT_BUTTON_LIST_VIEW);
		waitForAndGetElement(EVENT_LIST_VIEW.replace("${eventTitle}",EVENT_NAME_01));

		info("Switch to Work Week view");
		//*[@class='btn-group containerMoreItem']/*[@data-toggle='dropdown']
		if(waitForAndGetElement(ELEMENT_BUTTON_WORK_WEEK_VIEW,5000,0) == null)
			click(ELEMENT_BUTTON_MORE);
		click(ELEMENT_BUTTON_WORK_WEEK_VIEW);
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}",EVENT_NAME_01));

		info("Restore data");
		click(ELEMENT_BUTTON_WEEK_VIEW);
		deleteEventTask(EVENT_NAME_01,selectDayOption.ONEDAY);
	}

	/** Check category filter in Week view
	 * CaseID 75247
	 */
	@Test
	public void test02_checkEventinCategoryinWeekView() {
		String EVENT_NAME_02 = "VIEWS_EVENT_02";
		String CategoryName = "Meeting";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Choose Week view of Calendar");
		if(waitForAndGetElement(ELEMENT_BUTTON_VIEW_ACTIVE.replace("${view}", "Week"),10000,0) == null){
			click(ELEMENT_BUTTON_WEEK_VIEW);
			waitForAndGetElement(ELEMENT_BUTTON_VIEW_ACTIVE.replace("${view}", "Week"));
		}
		info("Add new event with specific category");
		Utils.pause(5000);
		evt.addQuickEvent(EVENT_NAME_02,EVENT_NAME_02,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false,null,CategoryName);

		info("Check event displayed in ALL category");
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}",EVENT_NAME_02));

		info("Check event displayed in chosen category");
		chooseEventCategoryOpt(CategoryName);
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}",EVENT_NAME_02));

		info("Restore data");
		deleteEventTask(EVENT_NAME_02,selectDayOption.ONEDAY);

	}

	/** Check category filter in List view
	 * CaseID 75248
	 */
	@Test
	public void test03_checkEventinCategoryinListView() {
		String EVENT_NAME_03 = "VIEWS_EVENT_03";
		String CategoryName = "Calls";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Choose Week view of Calendar");
		if(waitForAndGetElement(ELEMENT_BUTTON_VIEW_ACTIVE.replace("${view}", "Week"),10000,0) == null){
			click(ELEMENT_BUTTON_WEEK_VIEW);
			waitForAndGetElement(ELEMENT_BUTTON_VIEW_ACTIVE.replace("${view}", "Week"));
		}

		info("Add new event with specific category");
		Utils.pause(5000);
		evt.addQuickEvent(EVENT_NAME_03,EVENT_NAME_03,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false,null,CategoryName);
		
		
		info("Choose List view of Calendar");
		if(waitForAndGetElement(ELEMENT_BUTTON_VIEW_ACTIVE.replace("${view}", "List"),10000,0) == null){
			click(ELEMENT_BUTTON_LIST_VIEW);
			waitForAndGetElement(ELEMENT_BUTTON_VIEW_ACTIVE.replace("${view}", "List"));
		}
		info("Check event displayed in ALL category");
		waitForAndGetElement(EVENT_LIST_VIEW.replace("${eventTitle}",EVENT_NAME_03));

		info("Check event displayed in chosen category");
		chooseEventCategoryOpt(CategoryName);
		waitForAndGetElement(EVENT_LIST_VIEW.replace("${eventTitle}",EVENT_NAME_03));

		info("Restore data");
		click(ELEMENT_BUTTON_WEEK_VIEW);
		deleteEventTask(EVENT_NAME_03, selectDayOption.ONEDAY);

	}
}

