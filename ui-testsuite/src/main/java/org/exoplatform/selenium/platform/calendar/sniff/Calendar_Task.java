package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;

/**
 * 
 * @author havtt
 * @date 17 Oct 2013
 */

public class Calendar_Task extends CalendarBase {

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
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}


	/**
	 * Add new task to Calendar
	 * CaseID 68652
	 */
	@Test
	public void test01_AddNewTask() {
		String CALENDAR01 = "CALENDAR_01";

		info("Go to Intranet Calendar");
		goToCalendarPage();
		setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");
		driver.navigate().refresh();

		info("Add a new task");
		tsk.addQuickTask(CALENDAR01,CALENDAR01,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);

		info("Confirm added task displays in the calendar");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", CALENDAR01)));

		info("restore data");
		deleteEventTask(CALENDAR01,selectDayOption.ONEDAY);
	}

	/** 
	 * Check pop-up reminder of a task
	 * CaseID: 68654
	 * PENDING: refer https://jira.exoplatform.org/browse/FQA-1352
	 */
	@Test(groups={"pending"})
	public void test02_CheckPopupReminderOfTask() {

		String CALENDAR02 = "CALENDAR_02";

		info("Go to Intranet Calendar");
		goToCalendarPage();
		driver.navigate().refresh();

		info("Add a new task");
		tsk.goToAddTask();
		tsk.inputBasicQuickTask(CALENDAR02,CALENDAR02);
		tsk.inputFromToTask(getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);
		info("Setting reminder for task");
		tsk.gotoSetPopupReminder();
		click(tsk.ELEMENT_BUTTON_TASK_SAVE);

		info("Check pop-up reminder appear");
		//TO-DO: need to add confirmation here after finishing setting reminder methods

		info("Restore data");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ALL_DAY.replace("${taskTitle}", CALENDAR02)));
		deleteEventTask(CALENDAR02,selectDayOption.ONEDAY);

	}

	/** 
	 * Check E-mail reminder of a task
	 * CaseID: 68655
	 * PENDING: refer https://jira.exoplatform.org/browse/FQA-1352
	 */
	@Test(groups={"pending"})
	public void test03_CheckEmailReminderOfTask() {

		String CALENDAR03 = "CALENDAR_03";
		//TO-DO: update later
		String ELEMENT_CAL_GMAIL_REMINDER = "";
		String ELEMENT_GMAIL_CONTENT = "";
		String FROM_TIME = getDate(0,"MM/dd/yyyy");
		String TO_TIME = getDate(0,"MM/dd/yyyy");

		info("Go to Intranet Calendar");
		goToCalendarPage();
		driver.navigate().refresh();

		info("Add a new task");
		tsk.goToAddTask();
		tsk.inputBasicQuickTask(CALENDAR03,CALENDAR03);
		tsk.inputFromToTask(FROM_TIME,TO_TIME,false);
		info("Setting reminder for task");
		tsk.gotoSetEmailReminder();
		//TO-DO: update after finishing setting reminder methods
		click(tsk.ELEMENT_BUTTON_TASK_SAVE);
		Utils.pause(3000);

		info("Check if e-mail is sent");
		goToMail(EMAIL_ADDRESS1, EMAIL_PASS);
		checkAndDeleteMail(By.xpath(ELEMENT_CAL_GMAIL_REMINDER.replace("${taskName}", CALENDAR03)), ELEMENT_GMAIL_CONTENT);
		//TO-DO: update after finishing setting reminder methods
		switchToParentWindow();

		info("Restore data");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ALL_DAY.replace("${taskTitle}", CALENDAR03)));
		deleteEventTask(CALENDAR03,selectDayOption.ONEDAY);
	}

	/** 
	 * Edit a task
	 * CaseID: 69266
	 */
	@Test
	public void test04_EditTask(){

		String CALENDAR04 = "CALENDAR_04";
		String TITLE = "CALENDAR_04_edited";
		String DESCRIPTION = "CALENDAR_04_description_edited";

		info("Go to Intranet Calendar");
		goToCalendarPage();
		//setTimezoneForCalendar("(GMT +07:00) Asia/Ho_Chi_Minh");
		driver.navigate().refresh();

		info("Add a new task");
		tsk.addQuickTask(CALENDAR04,CALENDAR04,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);

		info("Edit a task");
		tsk.editTask(CALENDAR04,TITLE,DESCRIPTION,null,null, false,"");
		
		info("Restore data");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", CALENDAR04)));
		deleteEventTask(CALENDAR04, selectDayOption.ONEDAY);
	}

	/** 
	 * Delete a task
	 * CaseID: 69267
	 */
	@Test
	public void test05_DeleteTask(){
		String CALENDAR05 = "CALENDAR_05";

		info("Go to Intranet Calendar");
		goToCalendarPage();
		driver.navigate().refresh();

		info("Add a new task");
		tsk.addQuickTask(CALENDAR05,CALENDAR05,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);

		info("Delete a task");
		Utils.pause(5000);
		deleteEventTask(CALENDAR05, selectDayOption.ONEDAY);
	}

	/** 
	 * Drag & drop a task
	 * CaseID: 75249
	 */
	@Test
	public void test06_DragDropTask(){
		String CALENDAR06 = "CALENDAR_06";

		info("Go to Intranet Calendar");
		goToCalendarPage();
		driver.navigate().refresh();

		info("Add a new task");
		tsk.addQuickTask(CALENDAR06,CALENDAR06,getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);

		info("Drag & drop a task");
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", CALENDAR06)));
		dragAndDropToObject(By.xpath(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", CALENDAR06)),ELEMENT_TARGET_DATE);

		info("Restore data");
		Utils.pause(5000);
		driver.navigate().refresh();
		deleteEventTask(CALENDAR06, selectDayOption.ONEDAY);
	}

	/** 
	 * Resize a task
	 * CaseID: 75250
	 * PENDING: refer https://jira.exoplatform.org/browse/FQA-1351
	 */
	@Test(groups={"pending"})
	public void test07_ResizeTask(){
		String CALENDAR07 = "CALENDAR_07";

		info("Go to Intranet Calendar");
		goToCalendarPage();

		info("Add a new task");
		tsk.goToAddTask();
		tsk.inputBasicQuickTask(CALENDAR07,CALENDAR07);
		tsk.inputFromToTask(getDate(0,"MM/dd/yyyy"),getDate(0,"MM/dd/yyyy"),false);
		waitForAndGetElement(By.xpath(ELEMENT_EVENT_TASK_ALL_DAY.replace("${taskTitle}", CALENDAR07)));

		info("Resize a task to change date-time");
		//TO-DO: update later

		info("Restore data");
		Utils.pause(3000);
		deleteEventTask(CALENDAR07, selectDayOption.ONEDAY);
	}
}