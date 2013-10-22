package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.List;
import org.exoplatform.selenium.platform.ManageAccount;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.calendar.CalendarBase;

import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;

/**
 * 
 * @author thuntn
 * @date 10 Oct 2013
 */
public class Calendar_Calendar extends CalendarBase{
	ManageAccount acc;
	Event evt;
	Task tsk;
	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		evt = new Event(driver);
		tsk = new Task(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
		goToCalendarPage();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**Check highlighted mini calendar
	 * CaseID 68653
	 */
	@Test
	public void test01_CheckHighlightedMiniCalendar() {
		String eventName = "event68653";
		String taskName = "task 68653";
		info("Check highlighted mini calendar");

		evt.addEvent(eventName,eventName,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true);
		tsk.addTask(taskName,taskName,getDate(2,"MM/dd/yyyy"),getDate(2,"MM/dd/yyyy"),true);
		driver.navigate().refresh();

		List <WebElement> highLight = driver.findElements(By.xpath("//td[@class='highLight']"));
		for(WebElement we:highLight){
			boolean verify = we.getText().equals(getDate(1,"dd")) || we.getText().equals(getDate(2,"dd"));
			assert verify;
		}

		deleteEventTask(eventName);
		deleteEventTask(taskName);
	}

	/**Export calendar, Import calendar
	 * CaseID 68662, 68661
	 */
	@Test
	public void test02_ExportImportCalendar() {
		String eventName = "event68662";
		String calendar ="Calendar68662";
		String fileName = "export68662.ics";

		info("Export/Import calendar");
		addCalendar(calendar,calendar,null);
		evt.addEvent(eventName,eventName,getCurrentDate("MM/dd/yyyy"),addMinuteToCurrentDateTime(30),true,calendar);
		exportCalendar(calendar,fileName);
		deleteCalendar(calendar);
		importCalendar("TestData/TestOutput/" + fileName,calendar,null,null);

		//Delete data
		deleteFile("TestOutput/" + fileName);
		deleteCalendar(calendar);		

	}

	/** Add Personal Calendar, Edit Personal Calendar, Delete Personal Calendar
	 * CaseID 69258, 69259, 68648
	 */
	@Test
	public void test03_AddEditDeletePersonalCalendar() {
		String calendar = "Calendar 69258";
		String newCalendar = "new Calendar 69258";
		addCalendar(calendar,calendar,"red");
		editCalendar(calendar,newCalendar, newCalendar,"light_purple");
		deleteCalendar(newCalendar);
	}

	/** Add Group Calendar, Edit Group Calendar, Delete Group Calendar
	 * CaseID 69649, 69660, 69661
	 */
	@Test
	public void test04_AddEditDeleteGroupCalendar() {
		String calendar = "Calendar 69649";
		String newCalendar = "new Calendar 69649";
		info("Add/Edit/Delete Group Calendar");
		addCalendar(calendar,calendar,"red","/platform/administrators");
		editCalendar(calendar,newCalendar, newCalendar,"light_purple","/platform/web-contributors");
		deleteCalendar(newCalendar);
	}

	/** Add Shared Calendar, Delete Shared Calendar
	 * CaseID 69650, 69662
	 * 
	 */
	@Test
	public void test05_AddDeleteSharedCalendar() {
		String calendar = "Calendar 69650";
		String[] user = {"mary"};
		boolean[] canEdit = {true};

		info("Add/Delete Shared Calendar");
		addCalendar(calendar,calendar,"red");
		shareCalendar(calendar,user,canEdit);
		acc.signOut();
		acc.signIn(DATA_USER2,DATA_PASS);
		goToCalendarPage();

		deleteSharedCalendar(calendar);
		acc.signOut();
		acc.signIn(DATA_USER1,DATA_PASS);
		goToCalendarPage();
		deleteCalendar(calendar);
	}

	/**Edit Shared Calendar, 
	 * Case 69263: pending because cannot click on icon Settings of calendar
	 */
	@Test
	public void test06_EditSharedCalendar() {
		String calendar = "Calendar 69263";
		String[] user = {"mary"};
		boolean[] canEdit = {true};

		info("Edit Shared Calendar");
		addCalendar(calendar,calendar,"red");
		shareCalendar(calendar,user,canEdit);
		acc.signOut();
		acc.signIn(DATA_USER2,DATA_PASS);
		goToCalendarPage();

		openMenuOfCalendar(calendar);

		waitForAndGetElement(ELEMENT_CAL_IMPORT_MENU);
		waitForAndGetElement(ELEMENT_CAL_EXPORT_MENU);
		waitForAndGetElement(ELEMENT_CAL_REMOVE_MENU);
		waitForAndGetElement(ELEMENT_CAL_REFRESH_MENU);
		waitForAndGetElement(ELEMENT_CAL_ADD_EVENT_MENU,DEFAULT_TIMEOUT,0,2);
		waitForAndGetElement(ELEMENT_CAL_ADD_TASK_MENU,DEFAULT_TIMEOUT,0,2);

		acc.signOut();
		acc.signIn(DATA_USER1,DATA_PASS);
		goToCalendarPage();
		deleteCalendar(calendar);
	}
}
