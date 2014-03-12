package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author hangNTT
 * @date 18 Oct 2013
 */
public class Calendar_Settings extends CalendarBase{
	ManageAccount acc;
	Event evt;
	Task tsk;
	Button btn;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		evt = new Event(driver);
		tsk = new Task(driver);
		btn = new Button(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
		goToCalendarPage();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Display Calendar
	 * CaseID 74836
	 */
	@Test
	public void test01_DisplayCalendar() {

		String calendar = "ShareCalendar";
		String[] user = {DATA_USER2};
		boolean[] canEdit = {true};

		info("--Add share calendar--");
		addCalendar(calendar,calendar,"red");
		shareCalendar(calendar,user,canEdit);

		info("--Go to calendar Settings --");
		goToCalendarSettings();
		waitForAndGetElement(ELEMENT_CALENDAR_POPUP_WINDOW);
		waitForAndGetElement(ELEMENT_SETTINGS_TAB);

		info("--Verify display calendar tab--");
		click (ELEMENT_DISPLAYED_CALENDAR);
		waitForAndGetElement(ELEMENT_DISPLAYED_CALENDAR);

		info("--Verify feeds tab--");
		click(ELEMENT_FEEDS);
		waitForAndGetElement(ELEMENT_FEEDS);

		info("-- Check Displayed Calendar tab--");
		click (ELEMENT_DISPLAYED_CALENDAR);
		waitForAndGetElement(ELEMENT_CALENDAR_TAB_DEFAULT);

		info("--Verify personal and share calendar--");
		waitForAndGetElement(ELEMENT_PERSONAL_CALENDAR);
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","ShareCalendar").replace("${CheckboxColor}", "red").replace("${checkicon}", "iconCheckBox checkbox")));

		info("--Verify group calendar--");
		waitForAndGetElement(ELEMENT_GROUP_CALENDAR);
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Employees").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Administration").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Content Management").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Users").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Development").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","Executive Board").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));

		info("--Setup to hide specific calendar--");
		if(waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")),DEFAULT_TIMEOUT,0)!=null){
			click(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
			waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "checkbox iconUnCheckBox")));
		}
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);
		waitForElementNotPresent(By.xpath(ELEMENT_VERIFY_CALENDAR_FORM.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus")));

		info("--Setup to Show specific calendar--");
		goToCalendarSettings();
		click (ELEMENT_DISPLAYED_CALENDAR);
		if(waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconUnCheckBox checkbox")),DEFAULT_TIMEOUT,0)!=null){
			click(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconUnCheckBox checkbox")));
			waitForElementNotPresent(By.xpath(ELEMENT_VERIFY_CALENDAR.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "checkbox iconUnCheckBox")));
		}
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);



//	waitForAndGetElement(By.xpath(ELEMENT_VERIFY_CALENDAR_FORM.replace("${UserName}","John Smith").replace("${CheckboxColor}", "asparagus").replace("${checkicon}", "iconCheckBox checkbox")));
		waitForTextPresent("John Smith");




	//	waitForAndGetElement(ELEMENT_DISPLAY_CALENDAR.replace("${calendar}", "John Smith"));

		info("--Delete Calendar--");
		deleteCalendar(calendar);
	}

	/**
	 * Feeds
	 * CaseID 74837,74838,74841
	 */
	@Test
	public void test02_Feed(){
		String eventName = "eventfeeds";
		String namefeed = "test";
		String[] userGroup = {"John Smith"};
		String newfeed = "newtest";

		info("--Add event--");
		evt.addQuickEvent(eventName,eventName,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),false);

		info("--Add new feeds --");
		addFeeds(namefeed, userGroup,2);

		info("--edit feeds--");
		editFeeds(namefeed,newfeed, userGroup, 2);

		info("--Delete feeds--");
		deleteFeeds(newfeed);

		info("--Delete event--");
		deleteEventTask(eventName, selectDayOption.ONEDAY);
	}

	/**
	 * Settings tab
	 * CaseID 74835
	 */
	@Test
	public void test03_SettingTabs() {
		info("--Go to calendar Settings --");
		goToCalendarSettings();
		waitForAndGetElement(ELEMENT_CALENDAR_POPUP_WINDOW);
		waitForAndGetElement(ELEMENT_SETTINGS_TAB);

		info("--Verify display calendar tab--");
		click (ELEMENT_DISPLAYED_CALENDAR);
		waitForAndGetElement(ELEMENT_DISPLAYED_CALENDAR);

		info("--Verify feeds tab--");
		click(ELEMENT_FEEDS);
		waitForAndGetElement(ELEMENT_FEEDS);

		info("-- Check Displayed Calendar tab --");
		click(ELEMENT_SETTINGS_TAB);
		select(ELEMENT_VIEW_TYPE, "Month");
		select(ELEMENT_DATE_FORMAT, "dd/mm/yyyy");
		select(ELEMENT_TIME_FORMAT, "24 Hours");
		select(ELEMENT_TIME_ZONE, "(GMT +07:00) Asia/Ho_Chi_Minh");
		select(ELEMENT_WEEK_START_ON, "Tuesday");
		showWorkingTimes("08:30", "17:30");
		check(ELEMENT_SEND_EVENT_INVITATION.replace("${option}", "Ask"), 2);
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);

		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		goToCalendarPage();
		waitForAndGetElement(ELEMENT_MONTH_TAB_ACTIVE);
		goToCalendarSettings();

		WebElement viewType = waitForAndGetElement(ELEMENT_SELECTED_VIEW_TYPE, 5000, 1, 2);
		String vType = viewType.getText();
		assert vType.equals("Month") : "Calendar Settings was failed...";

		WebElement dateFormat = waitForAndGetElement(ELEMENT_SELECTED_DATE_FORMAT, 5000, 1, 2);
		String dFormat = dateFormat.getText();
		assert dFormat.equals("dd/mm/yyyy") : "Calendar Settings was failed...";

		WebElement timeFormat = waitForAndGetElement(ELEMENT_SELECTED_TIME_FORMAT, 5000, 1, 2);
		String tFormat = timeFormat.getText();
		assert tFormat.equals("24 Hours") : "Calendar Settings was failed...";

		WebElement timeZone = waitForAndGetElement(ELEMENT_SELECTED_TIME_ZONE, 5000, 1, 2);
		String tZone = timeZone.getText();
		assert tZone.equals("(GMT +07:00) Asia/Ho_Chi_Minh") : "Calendar Settings was failed...";

		WebElement weekStart = waitForAndGetElement(ELEMENT_SELECTED_WEEK_START_ON, 5000, 1, 2);
		String day = weekStart.getText();
		assert day.equals("Tuesday") : "Calendar Settings was failed...";

		WebElement showTimes = waitForAndGetElement(ELEMENT_SHOW_WORKING_TIME_CHECKBOX, 5000, 1, 2);
		assert showTimes.isSelected(): "Calendar Settings was failed...";

		WebElement sendInvitation = waitForAndGetElement(ELEMENT_SEND_EVENT_INVITATION.replace("${option}", "Ask"), 5000, 1, 2);
		assert sendInvitation.isSelected(): "Calendar Settings was failed...";

		select(ELEMENT_VIEW_TYPE, "Week");
		select(ELEMENT_DATE_FORMAT, "mm/dd/yyyy");
		select(ELEMENT_WEEK_START_ON, "Monday");
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);
	}
}