package org.exoplatform.selenium.platform.calendar;

import static org.exoplatform.selenium.TestLogger.info;
import java.awt.event.KeyEvent;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.PlatformPermission;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.internal.seleniumemulation.KeyEvent;

/**
 * Provides all basic actions of managing Calendar portlet 
 * 
 */
public class CalendarBase extends PlatformBase {

	PlatformPermission per;

	//Go to the calendar's page
	public String ID_CALENDAR_PAGE = "";
	public By ELEMENT_GET_ID_PAGE = By.xpath("//*[@id='CalendarApplicationMinWidth']/../..");
	public By ELEMENT_CALENDAR_LINK = By.className("uiIconPLFCalendar");
	public By ELEMENT_CALENDAR_PANEL = By.xpath("//div[@class='uiBox uiCalendars']");
	public String ELEMENT_CALENDAR_MINI_DATE= "//td[@class='highLight' and contains(text(),'${date}')]";
	//public String ELEMENT_CALENDAR_SETTING_ICON = "//a[text()='${calendar}']/ancestor::li[contains(@class, 'calendarItem')]/div[contains(@id,'UICalendars')]";
	public String ELEMENT_CALENDAR_SETTING_ICON = "//a[text()='${calendar}']/ancestor::li[contains(@class, 'calendarItem')]/div[@class='uiIconCalSettingMini uiIconLightGray pull-right']";
	public By ELEMENT_CALENDAR_ACTIONS_ICON = By.xpath("//*[@class='uiIconCalSimplePlus uiIconLightGray']");
	public By ELEMENT_CALENDAR_ADD_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'AddCalendar')]");
	public By ELEMENT_CALENDAR_SETTINGS = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'CalendarSetting')]");
	public By ELEMENT_CALENDAR_IMPORT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ImportCalendar')]");
	public String ELEMENT_CALENDAR_GET_BY_TAG_LI = "//a[@class='calendarName' and contains(text(), '${calendar}')]/../..";
	public By ELEMENT_CALENDAR_POPUP_WINDOW = By.xpath("//*[@id='UICalendarPopupWindow']/div[2]");
	public String ELEMENT_VERIFY_CALENDAR_FORM = "//*[@id='defaultCalendarTab'] //div[@class='myCalendar']/*[@class='calendarTitle']/..//li[contains(@class,'calendarItem' )]//*[text()='${UserName}']/../a[@class='${CheckboxColor}']";
	public String ELEMENT_DISPLAY_CALENDAR= "//a[@data-original-title='${calendar}']";
	//--------------Mini calendar-------------------
	public String ELEMENT_MINI_CALENDAR_DATE_HIGHLIGHT = "//td[contains(@class,'highLight') and contains(text(),'${date}')]";

	//---------------Working pane---------------------
	public String ELEMENT_WORKING_PANE_23H = "//td[@class='tdTime center']/div[contains(text(),'23:00')]";

	//-----------Calendar Settings ----------
	public By ELEMENT_SETTINGS_TAB = By.xpath("//a[@data-toggle='tab' and text()='Settings']");
	public By ELEMENT_DISPLAYED_CALENDAR = By.xpath("//a[@data-toggle='tab' and text()='Displayed Calendars']");
	public By ELEMENT_FEEDS = By.xpath("//a[@data-toggle='tab' and text()='Feeds']");
	public By ELEMENT_CALENDAR_TAB_DEFAULT = By.id("defaultCalendarTab");
	public By ELEMENT_PERSONAL_CALENDAR = By.xpath("//*[@id='defaultCalendarTab']//div[@class='myCalendar']/*[@class='calendarTitle' and text()='Personal Calendars']");
	public By ELEMENT_GROUP_CALENDAR = By.xpath("//*[@id='defaultCalendarTab']//div[@class='myCalendar']/*[@class='calendarTitle' and text()='Group Calendars']");
	public String ELEMENT_VERIFY_CALENDAR = "//*[@id='defaultCalendarTab'] //div[@class='myCalendar']/*[@class='calendarTitle']/..//li[contains(@class,'calendarItem' )]//*[text()='${UserName}']/../a[@class='${CheckboxColor}']//span[@class='${checkicon}']";
	public By ELEMENT_UNCHECK_BOX = By.xpath("//span[@class='iconCheckBox checkbox']");
	public By ELEMENT_SETTINGS_FORM_SAVE_BUTTON = By.xpath("//*[@id='UICalendarSettingForm']//*[text()='Save']");

	public By ELEMENT_VIEW_TYPE = By.name("viewType");
	public By ELEMENT_SELECTED_VIEW_TYPE = By.xpath("//*[@name='viewType']//*[@selected='selected']");
	public By ELEMENT_DATE_FORMAT = By.name("dateFormat");
	public By ELEMENT_SELECTED_DATE_FORMAT = By.xpath("//*[@name='dateFormat']//*[@selected='selected']");
	public By ELEMENT_TIME_FORMAT = By.name("timeFormat");
	public By ELEMENT_SELECTED_TIME_FORMAT = By.xpath("//*[@name='timeFormat']//*[@selected='selected']");
	public By ELEMENT_TIME_ZONE = By.name("timeZone");
	public By ELEMENT_SELECTED_TIME_ZONE = By.xpath("//*[@name='timeZone']//*[@selected='selected']");
	public By ELEMENT_WEEK_START_ON = By.name("weekStartOn");
	public By ELEMENT_SELECTED_WEEK_START_ON = By.xpath("//*[@name='weekStartOn']//*[@selected='selected']");
	public String ELEMENT_SEND_EVENT_INVITATION = "//*[text()='${option}']/../input[@name='send']";
	public By ELEMENT_SHOW_WORKING_TIME_CHECKBOX = By.name("showWorkingTime");
	public By ELEMENT_BEGIN_TIME = By.name("beginTime");
	public By ELEMENT_END_TIME = By.name("endTime");
	public By ELEMENT_MONTH_TAB_ACTIVE = By.xpath("//*[text()='Month']/ancestor::li[contains(@class, 'active')]");
	public By ELEMENT_WEEK_TAB = By.xpath("//*[text()='Week']/ancestor::li[contains(@class, 'btn')]");


	//------------Add event category------------------
	public String ELEMENT_ADD_EVENT_CATEGORY_ICON = "//*[@id='tmpMenuElement']//i[@class='uiIconCalCreateEvent uiIconLightGray']";
	public By ELEMENT_ADD_EVENT_CATEGORY_INPUT = By.id("eventCategoryName");
	public By ELEMENT_ADD_EVENT_CATEGORY_BUTTON_ADD = By.id("btnEventCategoryFormContainer");
	public String ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE = "//*[@id='UIEventCategoryForm']//button[contains(text(),'Close')]";
	public By ELEMENT_EDIT_EVENT_CATEGORY_BUTTON_UPDATE = By.id("btnEventCategoryFormContainer");
	public String ELEMENT_EVENT_CATEGORY_FILTER = "//div[@class='pull-right eventCategory']/span[@class='uiSelectbox']";
	public By ELEMENT_EVENT_CATEGORY_COMBOBOX = By.name("eventCategories");
	public String ELEMENT_EVENT_CATEGORY_COMBOBOX_OPTION = "//*[@name='eventCategories']/option[contains(text(),'${categoryName}')]";
	public String ELEMENT_LIST_EVENT_CATEGORY = "//*[@id='UIEventCategoryList']//span[contains(text(),'${categoryName}')]";
	public String ELEMENT_LIST_DELETE_EVENT_BUTTON = "//*[@id='UIEventCategoryList']//span[contains(text(),'${categoryName}')]/parent::td/parent::tr//a[@data-original-title='Delete']/i[@class='uiIconDelete uiIconLightGray']";
	public String ELEMENT_LIST_EDIT_EVENT_BUTTON = ".//*[@id='UIEventCategoryList']//span[contains(text(),'${categoryName}')]/parent::td/parent::tr//a[@data-original-title='Edit']/i[@class='uiIconEdit uiIconLightGray']";

	//-----------Menu of calendar------------
	public By ELEMENT_CAL_ADD_EVENT_MENU = By.id("AddEvent");
	public By ELEMENT_CAL_ADD_TASK_MENU = By.id("AddTask");
	public By ELEMENT_CAL_REMOVE_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'RemoveSharedCalendar')]");
	public By ELEMENT_CAL_IMPORT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ImportCalendar')]");
	public By ELEMENT_CAL_EXPORT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ExportCalendar')]");
	public By ELEMENT_CAL_REFRESH_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'RefreshRemoteCalendar')]");
	public By ELEMENT_CAL_SHARE_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'ShareCalendar')]");
	public By ELEMENT_CAL_EDIT_MENU = By.xpath("//*[@id='tmpMenuElement']//a[contains(@href,'EditCalendar')]");
	public By ELEMENT_CAL_SETTING_MENU = By.xpath("//*[@id='UIActionBar']//i[@class='uiIconSetting uiIconLightGray']");
	public String ELEMENT_CAL_SETTING_TIMEZONE_VALUE = "//*[@id='setting']//select[@name='timeZone']/option[@value='${timezoneOpt}']";

	//------------Export calendar---------------
	public By ELEMENT_CALENDAR_EXPORT = By.xpath("//div[@id='CalendarPopupMenu']//*[@class='uiIconCalExportCalendar uiIconLightGray']");
	public By ELEMENT_CALENDAR_EXPORT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Export Calendar']");
	public By ELEMENT_CAL_EXPORT_FILE_NAME = By.id("name");
	public By ELEMENT_CAL_EXPORT_SAVE_BUTTON = By.xpath("//*[@id='UIExportForm']//*[text()='Save']");

	//---------- Feeds -------------------------
	public By ELEMENT_NAME_FEEDS = By.xpath("//div[@id='UIEditFeed']//input[@id='name']");
	public By ELEMENT_URL_FEEDS = By.id("url");
	public By ELEMENT_RESET_URL = By.xpath("//div[@id='UIEditFeed']//div[@class='controls inputLarge']//a[@title='Reset URL']");
	public By ELEMENT_GENERATE_URL = By.xpath("//div[@id='UIEditFeed']//div[@class='controls inputLarge']//a[@title='Generate URL']");
	public By ELEMENT_ADD_MORE = By.xpath("//select[@name='addMore']");
	public By ELEMENT_ADD_FEEDS_BUTTON = By.xpath("//div[@id='feedTab-tab']//*[text()='Add']");
	public By ELEMENT_ADD_CALENDAR_BUTTON = By.xpath("//i[@class='uiIconPlus uiIconLightGray']");
	public By ELEMENT_FEEDS_SAVE_BUTTON = By.xpath("//*[@id='UIEditFeed']//*[contains(text(),'Save')]");
	public By ELEMENT_OK_POPUP_BUTTON = By.xpath("//div[@class='uiAction uiActionBorder']//*[text()='OK']");
	public String VERIFY_MESSAGE_URL = "The feed ${name} has been generated successfully.";
	public String ELEMENT_EDIT_FEEDS = "//tr/td/span[text()='${namefeeds}']/../..//a[@class='actionIcon']//i[@class='uiIconEdit uiIconLightGray']";
	public By ELEMENT_DELETE_FEEDS = By.xpath("//a[@class='actionIcon']//i[@class='uiIconDelete uiIconLightGray']");
	public String MSG_FEEDS_DELETE = "Are you sure you want to delete this feed from the list?";

	//---------- Add calendar-------------------
	public By ELEMENT_CAL_DISPLAY_NAME_INPUT = By.id("displayName");
	public By ELEMENT_CAL_DESC_INPUT = By.xpath("//*[@id='UICalendarForm']//*[@id='description']");
	public By ELEMENT_CAL_COLOR = By.xpath("//*[contains(@class,'displayValue')]");
	public String ELEMENT_CAL_COLOR_SELECT = "//form[@id='UICalendarForm']//a[contains(@class,'${color}')]";
	public By ELEMENT_CAL_ADD_SAVE_BUTTON = By.xpath("//*[@id='UICalendarForm']//*[text()='Save']");
	public By ELEMENT_CAL_ADD_POPUP= By.xpath("//span[@class='PopupTitle popupTitle' and text()='Calendar']");
	public By ELEMENT_CAL_GROUP_TAB = By.linkText("Show in Groups");
	public By ELEMENT_CAL_GROUP_INPUT = By.id("AddGroupInput");
	public By ELEMENT_CAL_SELECT_GROUP_ICON = By.xpath("//*[@class='uiIconGroup uiIconLightGray']");
	public String ELEMENT_EDIT_PERMISSION_INPUT = "//*[contains(@id,'${groupName}_permission')]";

	//-----------Event/Task -----------
	public String ELEMENT_EVENT_TASK_ALL_DAY = "//*[@id='UIWeekViewGridAllDay']//div[contains(text(),'${event}')]";
	public String ELEMENT_EVENT_TASK_ALL_DAY_PLF41 = "//*[@id='UIWeekViewGridAllDay']//div[contains(@class,'eventAlldayContent') and contains(.,'${event}')]";
	//public String ELEMENT_EVENT_TASK_ONE_DAY = "//*[@id='UIWeekViewGrid']//div[contains(text(),'${taskName}')]/parent::div[@class='clearfix']/div[@class='eventContainerBar eventTitle pull-left']";
	public String ELEMENT_EVENT_TASK_DETAIL_DATE = "//*[@id='UIWeekViewGrid']//*[contains(@startfull,'${date}')]//div[contains(text(),'${taskName}')]";
	public String ELEMENT_EVENT_TASK_DETAIL_ALL_DAY = "//*[@id='UIWeekViewGridAllDay']//*[contains(@starttimefull,'${date}')]//div[contains(text(),'${event}')]";
	public String ELEMENT_EVENT_TASK_ONE_DAY = "//*[@id='UIWeekViewGrid']//div[contains(text(),'${taskName}')]";
	public String ELEMENT_EVENT_TASK_ONE_DAY_1 = "//*[@id='UIWeekView']//div[contains(text(),'${taskName}')]";
	public String ELEMENT_EVENT_TASK_WORKING_PANE = "//*[@id='UIWeekViewGrid']//div[@class='eventContainer' and contains(text(),'${event}')]";
	public String ELEMENT_EVENT_TASK_WORKING_PANE_PLF41 = "//*[@id='UIWeekViewGrid']//div[contains(@class,'eventAlldayContent') and contains(.,'${event}')]";
	public By ELEMENT_EVENT_TASK_DELETE_MENU = By.xpath("//div[@id='tmpMenuElement']//a[@class='eventAction' and contains(@href,'Delete')]");
	public String MSG_EVENT_TASK_DELETE = "Are you sure you want to delete this event/task?";

	public String MSG_CALENDAR_DELETE = "Are you sure you want to delete this calendar and all its events?";
	public By ELEMENT_EVENT_TASK_WEEK_PANEL = By.xpath("//*[@class='eventWeekContent eventWeekContent mainWorkingPanel']");

	public String ELEMENT_TASK_EVENT_MENU_DELETE = "//*[@id='tmpMenuElement']//i[@class='uiIconDelete uiIconLightGray']";
	public String ELEMENT_TASK_EVENT_MENU_EDIT = "//*[@id='tmpMenuElement']//i[@class='uiIconEdit uiIconLightGray']";
	public By ELEMENT_INPUT_TASK_TITLE_EDIT = By.xpath("//*[@id='eventDetail']//*[@id='eventName']");
	public By ELEMENT_INPUT_TASK_NOTE_EDIT = By.xpath("//*[@id='eventDetail']//*[@id='description']");
	public By ELEMENT_BUTTON_TASK_SAVE_EDIT = By.xpath("//*[@id='UITaskForm']//*[text()='Save']");
	public By ELEMENT_BUTTON_EVENT_SAVE_EDIT = By.xpath("//*[@id='UIEventForm']//button[text()='Save']");
	//--------------Import calendar -------------------------
	public By ELEMENT_CAL_IMPORT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Calendar']");
	public By ELEMENT_CAL_IMPORT_TYPE = By.name("type");
	public By ELEMENT_CAL_IMPORT_SELECT_FILE = By.name("file");
	public By ELEMENT_CAL_IMPORT_FRAME_UDLOAD = By.xpath("//div[@id='upload']//iframe");
	public By ELEMENT_CAL_IMPORT_DESC_INPUT = By.xpath("//form[@id='UIImportForm']//*[@id='description']");
	public By ELEMENT_CAL_IMPORT_SAVE_BUTTON = By.xpath("//form[@id='UIImportForm']//*[text()='Save']");
	public By ELEMENT_CAL_IMPORT_DELETE_ICON = By.xpath("//a[@data-original-title='Delete']/i[@class='uiIconDelete uiIconLightGray']");

	//--------------Share calendar --------------------------
	public By ELEMENT_CAL_SELECT_USER_ICON = By.xpath("//*[@class='uiIconUser uiIconLightGray']");
	public By ELEMENT_CAL_SHARE_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Share Calendar']");
	public By ELEMENT_CAL_SHARE_ADD_BUTTON = By.xpath("//form[@id='UISharedForm']//*[text()='Add']");
	public By ELEMENT_CAL_SHARE_SAVE_BUTTON = By.xpath("//form[@id='UISharedForm']//*[text()='Save']");
	public String ELEMENT_CAL_SHARE_EDIT_PERMISSION = "//div[@title='${user}']/../..//input[@class='checkbox']"; 
	public By ELEMENT_CAL_SHARE_INPUT = By.id("PermissionOwnerInput");
	public By ELEMENT_CAL_SELECT_MEMBERSHIP_ICON = By.xpath("//i[@class='uiIconMembership uiIconLightGray']");

	//----------------DateTime of Calendar---------------
	public String ELEMENT_CURRENT_DATE = getCurrentDate("EEE MMM dd yyyy HH"); 
	public String ELEMENT_TARGET_TIME = ELEMENT_CURRENT_DATE +":00:00";
	public By ELEMENT_TARGET_DATE = By.xpath("//*[contains(@startfull, '${targetDate}')]".replace("${targetDate}", ELEMENT_TARGET_TIME));
	public String ELEMENT_ANY_TARGET_DATE = "//*[contains(@startfull, '${targetDate}')]";
	//-----------------Calendar Search-----------------------------
	public String ELEMENT_INPUT_QUICK_SEARCH = "//div[@class='uiSearchForm uiSearchInput pull-right']//*[@id='value']";
	public String ELEMENT_QUICK_SEARCH_FORM = "//div[@class='uiSearchForm uiSearchInput pull-right']";
	public String ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT = "//*[@id='UIListView']//button[contains(text(),'Close Search')]";
	public String ELEMENT_BUTTON_OPEN_ADVANCE_SEARCH_FORM = "//*[@id='UIListView']//button[contains(text(),'Advanced Search')]";
	public String ELEMENT_INPUT_TEXT_ADVANCE_SEARCH = "//*[@id='UIAdvancedSearchForm']//*[@id='text']";
	public String ELEMENT_BUTTON_SEARCH_ADVANCE_SEARCH = "//*[@id='UIAdvancedSearchForm']//button[contains(text(),'Search')]";

	//----------------------Calendar View------------------------------
	public String ELEMENT_BUTTON_DAY_VIEW = "//*[@id='UIActionBar']//a[text()='Day']";
	public String ELEMENT_BUTTON_WEEK_VIEW = "//*[@id='UIActionBar']//a[text()='Week']";
	public String ELEMENT_BUTTON_MONTH_VIEW = "//*[@id='UIActionBar']//a[text()='Month']";
	public String ELEMENT_BUTTON_LIST_VIEW = "//*[@id='UIActionBar']//a[text()='List']";
	public String ELEMENT_BUTTON_WORK_WEEK_VIEW = "//*[@id='UIActionBar']//a[text()='Work Week']";
	public String ELEMENT_BUTTON_VIEW_ACTIVE = "//li[@class='btn active']/a[text()='${view}']";
	public String ELEMENT_BUTTON_MORE = "//*[@class='btn-group containerMoreItem']/*[@data-toggle='dropdown']";

	//public String EVENT_WEEK_VIEW = "//*[@id='UIWeekViewGridAllDay']//div[contains(text(),'${eventTitle}')]";
	public String EVENT_DAY_VIEW = "//*[@id='UIDayView']//div[contains(text(),'${eventTitle}')]/..";
	public String EVENT_MONTH_VIEW = "//*[@id='UIMonthView']//span[contains(text(),'${eventTitle}')]";
	public String EVENT_LIST_VIEW = "//*[@id='UIListUsers']//span[contains(text(),'${eventTitle}')]";
	//	public String EVENT_WORK_WEEK_VIEW = "//*[@id='UIWeekViewGridAllDay']//div[contains(text(),'${eventTitle}')]";

	//----------------Group calendar---------------------------------
	public String ELEMENT_GROUP_CAL = "//*[@id='UICalendars']//a[contains(text(),'${calName}')]";
	public String ELEMENT_SHOW_IN_GROUP_TAB = "//*[@id='uiPopupAddCalendarContainer']//a[@data-target='#public-tab' and text()='Show in Groups']";

	//Add event from toolbar navigation
	public final By ELEMENT_TOOLBAR_INPUT_EVENT_TITLE_TEXTBOX = By.id("Title");
	public final By ELEMENT_TOOLBAR_EVENT_CHECKBOX = By.xpath("//input[@value='Event']");
	public final By ELEMENT_TOOLBAR_TASK_CHECKBOX = By.xpath("//input[@value='Task']");
	public final By ELEMENT_TOOLBAR_START_DATE = By.name("StartEvent");
	public final By ELEMENT_TOOLBAR_END_DATE = By.name("EndEvent");
	public final By ELEMENT_TOOLBAR_START_TIME = By.name("start_time");
	public final By ELEMENT_TOOLBAR_END_TIME = By.name("end_time");
	public final By ELEMENT_TOOLBAR_SELECT_CALENDAR = By.name("Calendar");
	public final String ELEMENT_CREATE_EVENT_MESSAGE = "//*[contains(text(),'The Event was added to ${userName}')]";
	public final String ELEMENT_CREATE_TASK_MESSAGE = "//*[contains(text(),'The Task was added to ${userName}')]";
	/*================== Common functions for Calendar =================*/

	/** 
	 * Go to Intranet Calendar
	 * 
	 */
	public void goToCalendarPage(){	
		info("--Go to calendar--");
		click(ELEMENT_CALENDAR_LINK);
		waitForAndGetElement(ELEMENT_CALENDAR_PANEL);
		ID_CALENDAR_PAGE = waitForAndGetElement(ELEMENT_GET_ID_PAGE).getAttribute("id");
	}

	/** 
	 * Get Property of a calendar
	 * 
	 * @param calendar
	 * 				the target calendar of which property will be get
	 * @param property 
	 * 				the calendar property option.
	 * 				   = 1: ID
	 *                 = 2: type
	 *                 = 3: color
	 *                 
	 * @return property of calendar
	 */
	public String getPropertyOfCalendar(String calendar,int property){
		try{
			WebElement eCalendar = waitForAndGetElement(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("${calendar}", calendar));
			switch (property){
			case 1: 
				return eCalendar.getAttribute("id");
			case 2: 
				return eCalendar.getAttribute("caltype");
			case 3: 
				return eCalendar.getAttribute("calcolor");
			default: 
				return "";
			}
		}catch(org.openqa.selenium.StaleElementReferenceException e){
			driver.navigate().refresh();
			return getPropertyOfCalendar(calendar, property);
		}
	}

	/**
	 * Execute action of calendar: Edit, Delete, Share, export....
	 * 
	 * @param calendar
	 * 				name of calendar
	 * @param action
	 * 				action that needs to be done, e.g.: "ShareCalendar"
	 */
	public void executeActionCalendar(String calendar, String action){
		String idCal = getPropertyOfCalendar(calendar,1);
		String color = getPropertyOfCalendar(calendar,3);
		String type = getPropertyOfCalendar(calendar,2);
		((JavascriptExecutor)driver).executeScript("javascript:eXo.webui.UIForm.submitEvent('"+ID_CALENDAR_PAGE+"#UICalendars','"+action+"','&objectId="+idCal+"&calType="+type+"&calColor="+color+"')");
	}

	/** 
	 * Share a calendar
	 * 
	 * @param calendar
	 * 				name of calendar
	 * @param userGroup
	 * 				array of users or groups that are shared with
	 * @param canEdit
	 * 				array of "canEdit" permissions for users, groups respectively inputed by userGroup variable 
	 * @param mode
	 * 				way to input users, groups.
	 *            =0: type directly
	 *            =1: select user
	 *            =2: select group
	 *            =3: select membership
	 */
	public void shareCalendar(String calendar, String[] userGroup, boolean[] canEdit, int...mode){

		executeActionCalendar(calendar,"ShareCalendar");

		for(int i = 0; i < userGroup.length; i++){
			int modeUser = mode.length > i ? mode[i] : 0;
			switch (modeUser){
			case 0: type(ELEMENT_CAL_SHARE_INPUT,userGroup[i],false);
			break;
			case 1:
				click(ELEMENT_CAL_SELECT_USER_ICON); 
				per.selectUserPermission(userGroup[i]); break;
			case 2: 
				String[] group = userGroup[i].split(":");
				click(ELEMENT_CAL_SELECT_GROUP_ICON);
				per.selectGroupPermission(group[0]); break;
			case 3: 
				String[] groupMem = userGroup[i].split(":");
				String[] membership = groupMem[1].split(".");
				click(ELEMENT_CAL_SELECT_MEMBERSHIP_ICON);
				per.selectGroupMembership(groupMem[0],membership[1]); break;
			}
		}
		click(ELEMENT_CAL_SHARE_ADD_BUTTON);
		for(int j=0; j < canEdit.length; j++){
			check(ELEMENT_CAL_SHARE_EDIT_PERMISSION.replace("${user}", userGroup[j]),2);
		}
		click(ELEMENT_CAL_SHARE_SAVE_BUTTON);
	}

	/** 
	 * Open export calendar form
	 * 
	 * @param calendar
	 * 				name of calendar
	 */
	public void goToExportCalendar(String calendar){

		openMenuOfCalendar(calendar);
		click(ELEMENT_CAL_EXPORT_MENU);
		waitForAndGetElement(ELEMENT_CALENDAR_EXPORT_POPUP);
	}

	/**
	 * Export calendar
	 * 
	 * @param calendar
	 * 				name of calendar
	 * @param name
	 * 				filename of exported calendar
	 */
	public void exportCalendar(String calendar, String name){
		info("Export calendar");
		goToExportCalendar(calendar);
		type(ELEMENT_CAL_EXPORT_FILE_NAME,name,true);
		click(ELEMENT_CAL_EXPORT_SAVE_BUTTON);
		Utils.pause(3000);
		driver.navigate().refresh();
		Utils.pause(3000);
	}

	/** 
	 * Open "Add calendar" form
	 * 
	 */
	public void goToAddCalendar(){
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		Utils.pause(3000);
		click(ELEMENT_CALENDAR_ADD_MENU);
	}

	/** 
	 * Open "Calendar Settings" form
	 * 
	 */
	public void goToCalendarSettings(){
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		click(ELEMENT_CALENDAR_SETTINGS);
	}

	/** 
	 * Input into Add calendar form
	 * 
	 * @param name
	 * 			name of calendar
	 * @param description
	 * 			description of calendar
	 * @param color
	 * 			color of calendar
	 * @param groups
	 * 			optional parameter. If the value of this parameter is not set, this function will create a personal calendar.
	 * 			Vice versa, the function will create a group calendar, based on:
	 * 				+ groups[0]: name of group, eg: /platform/administrators,  
	 * 				+ groups[1]: if not "0", type directly groups into the textbox "Select group"
	 * 			if not pass group[1], the function will click on "Select group" icon
	 */
	public void inputAddCalendarForm(String name, String description, String color, String...groups){
		String type = groups.length > 1 ? (String) groups[1]: "0";
		per = new PlatformPermission(driver);
		button = new Button(driver);

		if(name != null)
			type(ELEMENT_CAL_DISPLAY_NAME_INPUT,name,true);
		if(description != null)
			type(ELEMENT_CAL_DESC_INPUT, description,true);
		if(color != null){
			click(ELEMENT_CAL_COLOR);
			click(ELEMENT_CAL_COLOR_SELECT.replace("${color}", color));
		}
		if(groups.length > 0){
			click(ELEMENT_CAL_GROUP_TAB);
			if(type.equals("0")){
				click(ELEMENT_CAL_SELECT_GROUP_ICON);
				click(ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", groups[0]));
			}else
				type(ELEMENT_CAL_GROUP_INPUT,groups[0],true);
			click(button.ELEMENT_ADD_BUTTON);
		}
		Utils.pause(1000);
	}

	/*================== Calendar Feeds ====================*/
	/** 
	 * Input into calendar feeds form
	 * 
	 * @param name
	 * 			name of feeds
	 * @param calendar
	 * 			array of calendars that you want to add feeds for
	 * @param url
	 * 			optional parameter. URL feed that will be generated if requested.
	 *           = 1: click Reset url icon
	 *           = 2: click Generate url icon
	 *           else: leave the Url field by default
	 */
	public void inputFeedsData(String name,String[] calendar, int...url){
		per = new PlatformPermission(driver);
		button = new Button(driver);

		Utils.pause(1000);
		if(name != null)
			type(ELEMENT_NAME_FEEDS,name,true);
		for(int i = 0; i < calendar.length; i++){
			select(ELEMENT_ADD_MORE,calendar[i]);
			click(ELEMENT_ADD_CALENDAR_BUTTON);
		}
		int urlfeed = url.length > 0 ? url[0] : 0;
		switch (urlfeed){
		case 1:
			click(ELEMENT_RESET_URL); break;
		case 2: 
			click(ELEMENT_GENERATE_URL);break;
		default: break;
		}
		click(ELEMENT_FEEDS_SAVE_BUTTON);
		waitForAndGetElement("//*[contains(text(),'"+VERIFY_MESSAGE_URL.replace("${name}", name)+"')]",60000);
		click(ELEMENT_OK_POPUP_BUTTON);
		waitForAndGetElement("//*[contains(text(),'"+name+"')]");
	}

	/** 
	 * Add calendar feeds
	 * 
	 * @param name
	 * 			name of feeds
	 * @param userGroup
	 * 			array of calendars that you want to add feeds for
	 * @param url
	 * 			optional parameter. URL feed that will be generated if requested.
	 *           = 1: click Reset url icon
	 *           = 2: click Generate url icon
	 *           else: leave the Url field by default
	 */
	public void addFeeds(String name, String[] userGroup,int...url){
		goToCalendarSettings();
		info("--Verify feeds tab--");
		click(ELEMENT_FEEDS);
		waitForAndGetElement(ELEMENT_FEEDS);
		click(ELEMENT_ADD_FEEDS_BUTTON);
		inputFeedsData(name, userGroup, url);

	}

	/** 
	 * Edit calendar feeds
	 * 
	 * @param oldName 
	 * 			old name of feed
	 * @param name
	 * 			new name of feeds
	 * @param userGroup
	 * 			array of new calendars that you want to add feeds for
	 * @param url
	 * 			optional parameter. URL feed that will be generated if requested.
	 *           = 1: click Reset url icon
	 *           = 2: click Generate url icon
	 *           else: leave the Url field by default
	 */
	public void editFeeds(String oldName, String name, String[] userGroup, int...url){
		click(ELEMENT_EDIT_FEEDS.replace("${namefeeds}", oldName));
		waitForAndGetElement(ELEMENT_FEEDS);
		inputFeedsData(name, userGroup, url);
	}

	/** 
	 * Delete calendar feeds
	 * 
	 */
	public void deleteFeeds(String name){
		alert = new ManageAlert(driver);
		info("--Delete event--");
		click(ELEMENT_DELETE_FEEDS);
		alert.verifyAlertMessage(MSG_FEEDS_DELETE);
		waitForElementNotPresent(By.linkText(name));
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);
	}

	/*============== End of Feeds ===============*/

	/** 
	 * Upload calendar
	 * 
	 * @param path
	 * 			path of a file which will be uploaded.
	 */
	public void uploadCalendar(String path){
		info("--Upload Calendar--");
		WebElement element = waitForAndGetElement(ELEMENT_CAL_IMPORT_SELECT_FILE, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", element);
		element.sendKeys(Utils.getAbsoluteFilePath(path));
		String[] links = path.split("/");
		waitForAndGetElement("//*[text()='"+links[links.length-1]+"']");
		info("import " + Utils.getAbsoluteFilePath(path));
		//		driver.switchTo().defaultContent();
		switchToParentWindow();
		waitForAndGetElement(ELEMENT_CAL_IMPORT_DELETE_ICON);
	}

	/**
	 * Open "Import calendar" form
	 * 
	 */
	public void goToImportCalendar(){
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		click(ELEMENT_CALENDAR_IMPORT_MENU);
		waitForAndGetElement(ELEMENT_CAL_IMPORT_POPUP);
	}

	/** 
	 * Import calendar
	 * 
	 * @param path
	 * 				path of a file which is for upload
	 * @param name
	 * 				name of calendar
	 * @param description
	 * 				description of calendar
	 * @param color
	 * 				color of calendar
	 */
	public void importCalendar(String path, String name, String description, String color){
		goToImportCalendar();
		uploadCalendar(path);
		if(name != null)
			type(ELEMENT_CAL_DISPLAY_NAME_INPUT,name,true);
		if(description != null)
			type(ELEMENT_CAL_IMPORT_DESC_INPUT,description,true);

		if (color != null){
			click(ELEMENT_CAL_COLOR);
			click(ELEMENT_CAL_COLOR_SELECT.replace("${color}", color));
		}
		click(ELEMENT_CAL_IMPORT_SAVE_BUTTON);
		waitForElementNotPresent(ELEMENT_CAL_IMPORT_SAVE_BUTTON);
		driver.navigate().refresh();
		waitForAndGetElement(By.linkText(name));
	}

	//	/** 
	//	 * Delete event/task
	//	 * @author thuntn, havtt edited
	//	 * @param String event
	//	 * @param boolean allDay
	//	 */
	//	public void deleteEventTask(String event, boolean allDay){
	//		alert = new ManageAlert(driver);
	//		if (allDay == true)
	//		{
	//			info("--Delete event--");
	//			rightClickOnElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event),2);
	//			click(ELEMENT_EVENT_TASK_DELETE_MENU);
	//			alert.waitForConfirmation(MSG_EVENT_TASK_DELETE);
	//			waitForElementNotPresent(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event));
	//			waitForElementNotPresent(ELEMENT_EVENT_TASK_WORKING_PANE.replace("${event}", event));
	//		}
	//		else
	//		{        
	//			info("--Delete event--");
	//			rightClickOnElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event),2);
	//			click(ELEMENT_EVENT_TASK_DELETE_MENU);
	//			info("--Confirm deleted event--");
	//			alert.waitForConfirmation(MSG_EVENT_TASK_DELETE);
	//			alert.acceptAlert();
	//			waitForElementNotPresent(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event));
	//		}
	//	}

	/**
	 * Select an option when creating an Event/Task: ONE DAY or ALL DAY  
	 */
	public enum selectDayOption{
		ONEDAY, ALLDAY;
	}

	/**
	 * Delete an event or task
	 * 
	 * @param event
	 * 				name of event or task
	 * @param options
	 * 				optional parameter, if not be set, the event/task will be automatically set as "all day" event/task
	 * 				= selectDayOption.ALLDAY: this function consider the event/task as all day
	 * 				= selectDayOption.ONEDAY: this function consider the event/task as one day
	 */
	public void deleteEventTask(String event, selectDayOption... options){
		alert = new ManageAlert(driver);
		button = new Button(driver);
		waitForAndGetElement(ELEMENT_WORKING_PANE_23H);
		selectDayOption optDay = (waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event), 5000,0) == null) ? selectDayOption.ALLDAY : selectDayOption.ONEDAY;

		info("--Delete an Event/Task--");
		switch (optDay) {
		case ALLDAY:
			if(this.plfVersion.contains("4.0")){
				if(waitForAndGetElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event), 5000, 0) == null){
					rightClickOnElement(ELEMENT_EVENT_TASK_WORKING_PANE.replace("${event}", event),2);
				}        
				else{
					rightClickOnElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event),2);
				}
			}
			else{ //this.plfVersion.contains("4.1")
				if(waitForAndGetElement(ELEMENT_EVENT_TASK_ALL_DAY_PLF41.replace("${event}", event), 5000, 0) == null){
					rightClickOnElement(ELEMENT_EVENT_TASK_WORKING_PANE_PLF41.replace("${event}", event),2);
				}        
				else{
					rightClickOnElement(ELEMENT_EVENT_TASK_ALL_DAY_PLF41.replace("${event}", event),2);
				}
			}
			break;
		case ONEDAY:
			Utils.pause(3000);
			rightClickOnElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event),2);
			break;			
		default:
			break;
		}
		click(ELEMENT_EVENT_TASK_DELETE_MENU);
		alert.verifyAlertMessage(MSG_EVENT_TASK_DELETE);
		button.yes();
		driver.navigate().refresh();
		Utils.pause(1000);
		if (optDay.equals(selectDayOption.ALLDAY)){
			if(this.plfVersion.contains("4.0")){
				waitForElementNotPresent(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", event),5000);
				waitForElementNotPresent(ELEMENT_EVENT_TASK_WORKING_PANE.replace("${event}", event),5000);
			}
			else{
				waitForElementNotPresent(ELEMENT_EVENT_TASK_ALL_DAY_PLF41.replace("${event}", event),5000);
				waitForElementNotPresent(ELEMENT_EVENT_TASK_WORKING_PANE_PLF41.replace("${event}", event),5000);
			}
		}else if (optDay.equals(selectDayOption.ONEDAY)){
			waitForElementNotPresent(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", event));
		}	
	}

	/*============Add, Edit, Delete a Calendar ===========*/
	/** 
	 * Add a calendar
	 * 
	 * @param name
	 * 			name of calendar
	 * @param description
	 * 			description of calendar
	 * @param color
	 * 			name of color which is used in @class of the color element, eg: hot_pink
	 * @param groups
	 * 			optional parameter. If the value of this parameter is not set, this function will create a personal calendar.
	 * 			Vice versa, the function will create a group calendar, based on:
	 * 				+ groups[0]: name of group, eg: /platform/administrators,  
	 * 				+ groups[1]: if not "0", type directly groups into the textbox "Select group"
	 * 			if not pass group[1], the function will click on "Select group" icon	 
	 */
	public void addCalendar(String name, String description, String color, String...groups){
		info("Add calendar");
		goToAddCalendar();
		inputAddCalendarForm(name,description,color,groups);
		click(ELEMENT_CAL_ADD_SAVE_BUTTON);
		
		waitForAndGetElement(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("${calendar}", name));
	}

	/** 
	 * Delete a calendar
	 * 
	 * @param name
	 * 				name of calendar
	 * @param verify
	 * 				optional parameter. If not be set, by default, it is considered that calendar is deleted 
	 * 				= true: verify that calendar is deleted, 
	 * 				= false: not verify that calendar is deleted, 
	 */
	public void deleteCalendar(String name, boolean...verify){
		alert = new ManageAlert(driver); 
		boolean check = verify.length > 0 ? verify[0] : true;

		info("--Delete a Calendar-");

		executeActionCalendar(name,"RemoveCalendar");

		if (check){
			waitForElementNotPresent(ELEMENT_CALENDAR_GET_BY_TAG_LI.replace("{$calendar}", name));
			info("Remove calendar successfully");
		}
	}

	/** 
	 * Edit a calendar
	 * 
	 * @param oldName
	 * 				old name of calendar
	 * @param name
	 * 				new name of calendar
	 * @param description
	 * 				new description of calendar
	 * @param color
	 * 				new color of calendar
	 * @param groups
	 * 			optional parameter. If the value of this parameter is not set, this function will create a personal calendar.
	 * 			Vice versa, the function will create a group calendar, based on:
	 * 				+ groups[0]: name of group, eg: /platform/administrators,  
	 * 				+ groups[1]: if not "0", type directly groups into the textbox "Select group"
	 * 			if not pass group[1], the function will click on "Select group" icon
	 */
	public void editCalendar(String oldName,String name, String description, String color, String...groups){

		executeActionCalendar(oldName,"EditCalendar");
		inputAddCalendarForm(name,description,color,groups);
		click(ELEMENT_CAL_ADD_SAVE_BUTTON);
		waitForAndGetElement(By.linkText(name));
	}

	/**
	 * Delete a shared calendar
	 * 
	 * @param calendar
	 * 				name of shared calendar
	 */
	public void deleteSharedCalendar(String calendar){

		executeActionCalendar(calendar,"RemoveSharedCalendar");
		waitForElementNotPresent(By.linkText(calendar));
	}


	/*========== End of Add a Calendar ==========*/

	/**
	 * Open context menu of Calendar
	 * 
	 * @param calendar
	 * 				name of calendar
	 */
	public void openMenuOfCalendar(String calendar){
		WebElement element = waitForAndGetElement(ELEMENT_CALENDAR_SETTING_ICON.replace("${calendar}", calendar), DEFAULT_TIMEOUT, 0, 2);
		mouseOver(By.linkText(calendar),true);
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
	}

	/** 
	 * Show working time on working pane
	 * 
	 * @param beginTime
	 * 				start time, e.g.: 03:00
	 * @param endTime
	 * 				end time, e.g.: 10:00
	 */
	public void showWorkingTimes(String beginTime, String endTime){
		info("Show Working Times");
		WebElement element = waitForAndGetElement(ELEMENT_SHOW_WORKING_TIME_CHECKBOX, 5000, 1, 2);
		if (!element.isSelected()){
			check(ELEMENT_SHOW_WORKING_TIME_CHECKBOX, 2);
			select(ELEMENT_BEGIN_TIME, beginTime);
			select(ELEMENT_END_TIME, endTime);	
		}else{
			info("[Show working times is already checked]");
		}
		Utils.pause(500);
	}

	/** 
	 * Set timezone for Calendar
	 * 
	 * @param timezoneOpt
	 * 				time zone of calendar, e.g.: (GMT -11:00) Pacific/Samoa
	 */
	public void setTimezoneForCalendar(String timezoneOpt){
		click(ELEMENT_CAL_SETTING_MENU);
		Utils.pause(3000);
		info("-- Select filter option of Timezone --");
		select(ELEMENT_TIME_ZONE,timezoneOpt);	
		Utils.pause(1000);
		click(ELEMENT_SETTINGS_FORM_SAVE_BUTTON);
		Utils.pause(3000);
	}

	/** 
	 * Quick search in Calendar
	 * 
	 * @param keyword
	 * 			keyword which is to input into search box 
	 */
	public void quickSearchCalendar(String keyword){
		info("----Type in quick search box----");
		type(By.xpath(ELEMENT_INPUT_QUICK_SEARCH), keyword, true);
		info("----Send search request----");
		Utils.pause(5000);
		click(ELEMENT_INPUT_QUICK_SEARCH);
		Utils.javaSimulateKeyPress(KeyEvent.VK_ENTER);
		info("----Confirm search result page displayed----");
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT);
	}

	/** 
	 * Advance search in Calendar
	 * 
	 * @param keyword
	 * 			keyword which is to input into search box 
	 */
	public void advanceSearchCalendar(String keyword){
		info("----Open Advance Search window----");
		waitForAndGetElement(ELEMENT_BUTTON_OPEN_ADVANCE_SEARCH_FORM);
		click(ELEMENT_BUTTON_OPEN_ADVANCE_SEARCH_FORM);
		info("----Input keyword----");
		waitForAndGetElement(ELEMENT_INPUT_TEXT_ADVANCE_SEARCH);
		type(ELEMENT_INPUT_TEXT_ADVANCE_SEARCH,keyword,true);
		click(ELEMENT_BUTTON_SEARCH_ADVANCE_SEARCH);
		info("----Confirm search result displayed----");
		Utils.pause(3000);
		waitForAndGetElement(ELEMENT_BUTTON_CLOSE_QUICK_SEARCH_RESULT);
	}

	/** 
	 * Go to Calendar Actions> Add Event Category
	 * 
	 * @param categoryName
	 * 				category name of Calendar
	 */
	public void addEventCategory(String categoryName){
		info("----Add new event category----");
		type(ELEMENT_ADD_EVENT_CATEGORY_INPUT,categoryName,true);
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_ADD);
		info("----Verify if event category is added in Category List or not----");
		waitForAndGetElement(ELEMENT_LIST_EVENT_CATEGORY.replace("${categoryName}", categoryName));
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE);	 
	}

	/** 
	 * Choose event category in Categories combo box for filter  
	 * 
	 * @param categoryName
	 * 				category name of calendar
	 */
	public void chooseEventCategoryOpt(String categoryName){
		info("----Verify if new category is displayed in Category option or not----");
		waitForAndGetElement(ELEMENT_EVENT_CATEGORY_FILTER);
		click(ELEMENT_EVENT_CATEGORY_COMBOBOX);
		info("----Choose a category option----");
		click(ELEMENT_EVENT_CATEGORY_COMBOBOX_OPTION.replace("${categoryName}", categoryName));

	}

	/** 
	 * Delete Event Category
	 * 
	 * @param categoryName
	 * 				category name of calendar
	 */
	public void deleteEventCategory(String categoryName){
		alert = new ManageAlert(driver);
		button = new Button(driver);
		waitForAndGetElement(ELEMENT_LIST_DELETE_EVENT_BUTTON.replace("${categoryName}",categoryName));
		click(ELEMENT_LIST_DELETE_EVENT_BUTTON.replace("${categoryName}",categoryName));
		alert.acceptAlert();
		button.yes();
		waitForElementNotPresent(ELEMENT_LIST_DELETE_EVENT_BUTTON.replace("${categoryName}",categoryName));
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE);	
	}

	/** 
	 * Edit Event Category
	 * 
	 * @param categoryName
	 * 						old category name
	 * @param editedCategoryName
	 * 						new category name
	 */

	public void editEventCategory(String categoryName, String editedCategoryName){
		waitForAndGetElement(ELEMENT_LIST_EDIT_EVENT_BUTTON.replace("${categoryName}",categoryName));
		click(ELEMENT_LIST_EDIT_EVENT_BUTTON.replace("${categoryName}",categoryName));
		type(ELEMENT_ADD_EVENT_CATEGORY_INPUT,editedCategoryName,true);
		click(ELEMENT_EDIT_EVENT_CATEGORY_BUTTON_UPDATE);
		waitForAndGetElement(ELEMENT_LIST_EDIT_EVENT_BUTTON.replace("${categoryName}",editedCategoryName));
		click(ELEMENT_ADD_EVENT_CATEGORY_BUTTON_CLOSE);
	}

	/** 
	 * Go to Add Event Category
	 * 
	 */
	public void gotoAddEventCategory(){
		click(ELEMENT_CALENDAR_ACTIONS_ICON);
		info("----Go to Add Event Category form----");
		click(ELEMENT_ADD_EVENT_CATEGORY_ICON);
		Utils.pause(3000);
	}	 


	/** 
	 * Change "Edit Permission" for acc in Add New Calendar>Show In Group
	 * 
	 * @param calName
	 * 			calendar name
	 * @param editAcc	
	 * 			account that will be set Edit permission
	 * @param Group		
	 * 			group name in Show in Group Calendar
	 */
	public void changeEditPermissionForCalShowInGroup (String calName, String editAcc, String Group){
		info("==Open calendar actions menu==");
		executeActionCalendar(calName, "EditCalendar");
		Utils.pause(3000);
		click(ELEMENT_SHOW_IN_GROUP_TAB);
		info("==Update Edit permission setting of calendar==");
		//waitForAndGetElement(ELEMENT_EDIT_PERMISSION_INPUT.replace("${groupName}", Group));
		type(ELEMENT_EDIT_PERMISSION_INPUT.replace("${groupName}", Group),editAcc, true);
		click(ELEMENT_CAL_ADD_SAVE_BUTTON);
		Utils.pause(3000);
	}
	/**
	 * Add Event/Task from ToolbarNavigation by mousing over on the button "Create" (+) --> Select the item "Event/Task"
	 *
	 * @param title
	 * 			title of event/task
	 * @param from
	 * 			start date of event/task
	 * @param to
	 * 			end date of event/task
	 * @param allDay
	 * 			if true, the event/task will be set to "All Day" event/task
	 * @param verify
	 * 			if true, the Add Event/Task action's result will be verified			
	 * @param isEvent
	 * 			if true, Event will be added. Otherwise, task will be added instead.
	 * @param opt 
	 * 			optional parameter to choose the calendar group.
	 * 			
	 */
	public void addEventTaskFromToolbarNavigation(Boolean isEvent, String title, String from, String to, boolean allDay, boolean verify, String...opt){
		button = new Button(driver);
		//Add event
		if(isEvent)
			check(ELEMENT_TOOLBAR_EVENT_CHECKBOX,2);
		else //Add task
			check(ELEMENT_TOOLBAR_TASK_CHECKBOX,2);
		if(title!="")
			type(ELEMENT_TOOLBAR_INPUT_EVENT_TITLE_TEXTBOX, title, true);
		if (from != ""){
			if (allDay){
				type(ELEMENT_TOOLBAR_START_DATE, from, true);
				select(ELEMENT_TOOLBAR_START_TIME, "All Day");
			}else {
				type(ELEMENT_TOOLBAR_START_DATE, from.substring(0, from.indexOf(" ")).trim(), true);
				select(ELEMENT_TOOLBAR_START_TIME, from.substring(from.indexOf(" ")).trim());
				Utils.pause(5000);
			}
		}
		if (to != ""){
			if (allDay){
				type(ELEMENT_TOOLBAR_END_DATE, to, true);
				select(ELEMENT_TOOLBAR_END_TIME, "All Day");
			}else{
				type(ELEMENT_TOOLBAR_END_DATE, to.substring(0, to.indexOf(" ")).trim(), true);
				select(ELEMENT_TOOLBAR_END_TIME,  to.substring(to.indexOf(" ")).trim());
			}

		}
		if (opt.length > 0 && opt[0] != null){
			select(ELEMENT_TOOLBAR_SELECT_CALENDAR,opt[0]);
			button.save();
			if(verify){
				if(isEvent){
					waitForAndGetElement(ELEMENT_CREATE_EVENT_MESSAGE.replace("${userName}", opt[0]));
					Utils.pause(3000);
					waitForElementNotPresent(ELEMENT_CREATE_EVENT_MESSAGE.replace("${userName}", opt[0]));
				}
				else{
					waitForAndGetElement(ELEMENT_CREATE_TASK_MESSAGE.replace("${userName}", opt[0]));
					Utils.pause(3000);
					waitForElementNotPresent(ELEMENT_CREATE_TASK_MESSAGE.replace("${userName}", opt[0]));
				}
			}
		}
		else{
			button.save();
			if(verify){
				if(isEvent){
					waitForAndGetElement(ELEMENT_CREATE_EVENT_MESSAGE.replace("${userName}", waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK).getText()));
					Utils.pause(3000);
					waitForElementNotPresent(ELEMENT_CREATE_EVENT_MESSAGE.replace("${userName}",  waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK).getText()));
				}
				else{
					waitForAndGetElement(ELEMENT_CREATE_TASK_MESSAGE.replace("${userName}", waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK).getText()));
					Utils.pause(3000);
					waitForElementNotPresent(ELEMENT_CREATE_TASK_MESSAGE.replace("${userName}",  waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK).getText()));

				}
			}
		}
	}
}