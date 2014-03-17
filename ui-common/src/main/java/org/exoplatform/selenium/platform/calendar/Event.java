package org.exoplatform.selenium.platform.calendar;

import static org.exoplatform.selenium.TestLogger.info;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;

/**
 * Provides all methods of managing Event in Calendar portlet
 * 
 */
public class Event extends CalendarBase{

	//--------------------Event basic actions------------------------
	public By ELEMENT_INPUT_EVENT_TITLE = By.id("eventName");
	public By ELEMENT_INPUT_EVENT_DESCRIPTION = By.id("description");
	public By ELEMENT_CHECKBOX_EVENT_ALLDAY = By.xpath("//form[@id='UIQuickAddEvent']//input[@id='allDay']");
	public By ELEMENT_INPUT_EVENT_FROM = By.name("from");
	public By ELEMENT_INPUT_EVENT_TO = By.name("to");
	public By ELEMENT_INPUT_EVENT_FROM_TIME = By.xpath("//*[@id='fromTime']/../input");
	public By ELEMENT_INPUT_EVENT_TO_TIME = By.xpath("//*[@id='toTime']/../input");
	public By ELEMENT_INPUT_EVENT_CALENDAR = By.name("calendar");
	public By ELEMENT_INPUT_EVENT_CATEGORY = By.name("category");
	public By ELEMENT_BUTTON_EVENT = By.id("UIActionBarQuickAddEvent");
	public By ELEMENT_MENU_EVENT_EDIT = By.xpath("//*[@id='tmpMenuElement']//i[@class='uiIconEdit uiIconLightGray']");
	public By ELEMENT_ADD_EVENT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Quick Add Event']");

	//Preview form
	public String ELEMENT_EVENT_PREVIEW_TITLE = "//form[@id='UIPreviewPopup']//div[@class='titleList']/strong[text()='${event}']";

	//Form Add event details
	public By ELEMENT_ADD_EDIT_EVENT_ALLDAY = By.xpath("//form[@id='UIEventForm']//input[@id='allDay']");
	public By ELEMENT_ADD_EDIT_EVENT_TITLE = By.xpath("//form[@id='UIEventForm']//*[@id='eventName']");
	public By ELEMENT_ADD_EDIT_EVENT_DESC = By.xpath("//form[@id='UIEventForm']//*[@id='description']");
	public By ELEMENT_ADD_EDIT_EVENT_FROM = By.xpath("//form[@id='UIEventForm']//*[@name='from']");
	public By ELEMENT_ADD_EDIT_EVENT_TO = By.xpath("//form[@id='UIEventForm']//*[@name='to']");
	public By ELEMENT_ADD_EDIT_EVENT_TO_TIME = By.xpath("//form[@id='UIEventForm']//*[@id='toTime']/../input[@class='UIComboboxInput']");
	public By ELEMENT_ADD_EDIT_EVENT_FROM_TIME = By.xpath("//form[@id='UIEventForm']//*[@id='fromTime']/../input[@class='UIComboboxInput']");
	public By ELEMENT_ADD_EDIT_EVENT_CALENDAR = By.xpath("//form[@id='UIEventForm']//*[@name='calendar']");
	public By ELEMENT_ADD_EVENT_SAVE_BUTTON = By.xpath("//form[@id='UIEventForm']//*[text()='Save']");
	public By ELEMENT_ADD_EDIT_EVENT_CATEGORY = By.xpath("//form[@id='UIEventForm']//*[@name='category']");
	public By ELEMENT_ADD_EDIT_EVENT_LOCATION = By.id("place");
	public By ELEMENT_EDIT_EVENT_POPUP = By.xpath("//span[@class='PopupTitle popupTitle' and text()='Add/Edit Event']");
	public By ELEMENT_EDIT_EVENT_CONFIRM_POPUP = By.xpath("//*[@id='UIConfirmFormUpdate']//*[text()='Save']");

	public Event(WebDriver dr, String...plfVersion){
		driver = dr;
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		button = new Button(driver, this.plfVersion);
		alert = new ManageAlert(driver, this.plfVersion);
	}
	
	/******************Go to******************************/
	/**
	 * Open "Add event" form 
	 *
	 */
	public void goToAddEvent(){
		info("Go to Add Event page");
		click(ELEMENT_BUTTON_EVENT);
		waitForAndGetElement(ELEMENT_ADD_EVENT_POPUP);
	}
	
	/**
	 * Open "Edit Event" form 
	 * @param oldEvent
	 * 				Name of event that need editing
	 */
	public void goToEditEventForm(String oldEvent){
		if(waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", oldEvent),15000,0) != null)
			rightClickOnElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", oldEvent),2);
		else
			rightClickOnElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", oldEvent),2);
		click(ELEMENT_MENU_EVENT_EDIT);
		waitForAndGetElement(ELEMENT_EDIT_EVENT_POPUP);
	}
	//---------------End of go to------------------------//

	//---------------Input data form--------------//
	/**
	 * Input into basic fields of "Add quick event" and tab "Details" of "Add/Edit event"
	 * 
	 * @param name
	 * 			name of event
	 * @param description
	 * 			description of event
	 * @param opt
	 * 			optional parameter
	 * 			opt[0]: calendar name of this event
	 * 			opt[1]: category name of this event
	 */
	public void inputBasicQuickEvent(String name, String description, String...opt){
		boolean quick = (waitForAndGetElement(ELEMENT_ADD_EVENT_POPUP,5000,0) != null) ? true : false; 
		if(quick){
			if (name != null){
				type(ELEMENT_INPUT_EVENT_TITLE, name, true);
			}
			if (description != null){
				type(ELEMENT_INPUT_EVENT_DESCRIPTION, description, true);
			}
			if (opt.length > 0 && opt[0] != null){
				select(ELEMENT_INPUT_EVENT_CALENDAR, opt[0]);
			}
			if (opt.length > 1 && opt[1] != null){
				select(ELEMENT_INPUT_EVENT_CATEGORY, opt[1]);
			}
		}else{
			if (name != null){
				type(ELEMENT_ADD_EDIT_EVENT_TITLE, name, true);
			}
			if (description != null){
				type(ELEMENT_ADD_EDIT_EVENT_DESC, description, true);
			}
			if (opt.length > 0 && opt[0] != null){
				select(ELEMENT_ADD_EDIT_EVENT_CALENDAR, opt[0]);
			}
			if (opt.length > 1 && opt[1] != null){
				select(ELEMENT_ADD_EDIT_EVENT_CATEGORY, opt[1]);
			}
		}
	}
	
	/**
	 * Input into "From", "To" fields of "Add quick event" and tab "Details" of "Add/Edit event"
	 * 
	 * @param from
	 * 			From date, time, eg: 11/06/2013 14:00
	 * @param to
	 * 			To date, time fields, eg: 11/06/2013 14:00
	 * @param allDay
	 * 			Choose option "All-Day event" or not
	 */
	public void inputFromToEvent(String from, String to, boolean allDay){
		boolean quick = (waitForAndGetElement(ELEMENT_ADD_EVENT_POPUP,5000,0) != null) ? true : false; 
		if(quick){
			if(allDay){
				check(ELEMENT_CHECKBOX_EVENT_ALLDAY,2);
				if((from != "") & (from != null))
					type(ELEMENT_INPUT_EVENT_FROM, from, true);
				if((to != null) & (to != ""))
					type(ELEMENT_INPUT_EVENT_TO, to, true);
			}
			else{
				uncheck(ELEMENT_CHECKBOX_EVENT_ALLDAY,2);
				if((from != "") & (from != null)){
					String[] dateTime = from.split(" ");
					if(dateTime.length > 0)
						type(ELEMENT_INPUT_EVENT_FROM, dateTime[0], true);
					if(dateTime.length > 1)
						type(ELEMENT_INPUT_EVENT_FROM_TIME, dateTime[1], true);
				}
				if((to != null) & (to != "")){
					String[] dateTime = to.split(" ");
					if(dateTime.length > 0)
						type(ELEMENT_INPUT_EVENT_TO, dateTime[0], true);
					if(dateTime.length > 1)
						type(ELEMENT_INPUT_EVENT_TO_TIME, dateTime[1], true);
				}
			}
		}else{
			if(allDay){
				check(ELEMENT_ADD_EDIT_EVENT_ALLDAY,2);
				if((from != "") & (from != null))
					type(ELEMENT_ADD_EDIT_EVENT_FROM, from, true);
				if((to != "") & (to != null))
					type(ELEMENT_ADD_EDIT_EVENT_TO, to, true);
			}else{
				uncheck(ELEMENT_ADD_EDIT_EVENT_ALLDAY,2);
				if((from != "") & (from != null)){
					String[] dateTime = from.split(" ");
					if(dateTime.length > 0)
						type(ELEMENT_ADD_EDIT_EVENT_FROM, dateTime[0], true);
					if(dateTime.length > 1)
						type(ELEMENT_ADD_EDIT_EVENT_FROM_TIME, dateTime[1], true);
				}
				if((to != null) & (to != "")){
					String[] dateTime = to.split(" ");
					if(dateTime.length > 0)
						type(ELEMENT_ADD_EDIT_EVENT_TO, dateTime[0], true);
					if(dateTime.length > 1)
						type(ELEMENT_ADD_EDIT_EVENT_TO_TIME, dateTime[1], true);
				}
			}

		}
	}
	
	/**
	 * Input into other fields of tab Details of Add/Edit event
	 * 
	 * @param location
	 * 				location of event
	 */
	public void inputOtherFieldsTabDetailsEvent(String location){
		if(location != null){
			type(ELEMENT_ADD_EDIT_EVENT_LOCATION,location,true);
		}
	}
	
	/**
	 * Input into Add/Edit Event form
	 * 
	 * @param name
	 * 				name of event
	 * @param description
	 * 				description of event
	 * @param location
	 * 				location of event
	 * @param from
	 * 				from date, time of event
	 * @param to
	 * 				to date, time of event
	 * @param allDay
	 * 				option "allDay" of event
	 * @param opt
	 * 				optional parameter	
	 * 				opt[0]: calendar name of this event (optional)
	 * 				opt[1]: category of this event (optional)
	 */
	public void inputAddEventForm(String name, String description, String location, String from, String to, boolean allDay, String...opt){
		inputBasicQuickEvent(name, description, opt);
		inputOtherFieldsTabDetailsEvent(location);
		inputFromToEvent(from, to, allDay);
	}
	
	/************End of input data form***************/

	
    /******************Add/Edit Event*************************/
	/** 
	 * Quick add event
	 * 
	 * @param name
	 * 				name of event
	 * @param description
	 * 				description of event
	 * @param from
	 * 				from date, time of event
	 * @param to
	 * 				to date, time of event
	 * @param allDay
	 * 				option "allDay" of event
	 * @param opt
	 * 				optional parameter
	 * 				opt[0]: calendar name of this event (optional)
	 * 				opt[1]: category of this event (optional)
	 */
	public void addQuickEvent(String name, String description, String from, String to, boolean allDay, String...opt){
		info("--Add an event--");
		goToAddEvent();
		inputBasicQuickEvent(name, description, opt);
		inputFromToEvent(from, to, allDay);
		button.save();
		waitForElementNotPresent(ELEMENT_ADD_EVENT_POPUP);
		Utils.pause(1000);
		if(allDay){
			if(this.plfVersion.contains("4.0"))
				waitForAndGetElement(ELEMENT_EVENT_TASK_ALL_DAY.replace("${event}", name));
			else
				waitForAndGetElement(ELEMENT_EVENT_TASK_ALL_DAY_PLF41.replace("${event}", name));
		}
		else{
			if(isElementNotPresent(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", name)))
				waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY_1.replace("${taskName}", name));
		}
	}

	/**
	 * Edit an event
	 *
	 * @param oldEvent
	 * 				old name of event
	 * @param name
	 * 				new name of event
	 * @param description
	 * 				new description of event
	 * @param location
	 * 				new location of event
	 * @param from
	 * 				new from date, time of event
	 * @param to
	 * 				new to date, time of event
	 * @param allDay
	 * 				new value of "allDay" option of event
	 * @param opt
	 * 				optional parameter
	 * 				opt[0]: new calendar name of this event (optional)
	 * 				opt[1]: new category of this event (optional)
	 */
	public void editEvent(String oldEvent, String name, String description, String location,String from, String to, boolean allDay, String...opt){
		info("Edit an event");
		goToEditEventForm(oldEvent);
		inputAddEventForm(name,description, location,from,to,allDay,opt);
		click(ELEMENT_ADD_EVENT_SAVE_BUTTON);
		Utils.pause(1000);
		if(isElementPresent(ELEMENT_EDIT_EVENT_CONFIRM_POPUP))
			click(ELEMENT_EDIT_EVENT_CONFIRM_POPUP);
		waitForElementNotPresent(ELEMENT_EDIT_EVENT_POPUP);
	}
	
	/****************End of Add/Edit Event*******************/
}
