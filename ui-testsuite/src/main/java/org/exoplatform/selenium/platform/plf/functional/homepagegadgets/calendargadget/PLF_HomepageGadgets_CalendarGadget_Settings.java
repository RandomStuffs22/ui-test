package org.exoplatform.selenium.platform.plf.functional.homepagegadgets.calendargadget;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.BrandingManagement;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lanltk
 * @date 24 Jan 2014
 */

public class PLF_HomepageGadgets_CalendarGadget_Settings extends CalendarBase{

	ManageAccount magAc;
	NavigationToolbar naviToolbar;
	BrandingManagement brandMag;
	Event event;

	HomePageActivity hpActivity;
	SpaceManagement spaceMag;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
	    hpActivity = new HomePageActivity(driver, this.plfVersion);
		spaceMag = new SpaceManagement(driver, this.plfVersion);

		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		magAc = new ManageAccount(driver, this.plfVersion);
		brandMag = new BrandingManagement(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		event = new Event(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
			    
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	  /** Functional-PLF-HompageGadgets-CalendarGadget-Settings
	   *  CaseId: 69133, 69135, 69136,69137, 69138, 69139, 69176, 69177
	   */
	  /*
	   *  CaseId 69133: Display the settings view of Calendar Gadget
	   *  CaseId 69138: Display many calendars in the list "Display additional calendar"
	   *  CaseId 69176: Display all calendars in SETTINGS view
	   */
	  @Test  
	  public void test01_DisplaySettingsCalendarGadget(){	
		String Calendar1 = "Calendar_69133_1";
		String Calendar2 = "Calendar_69138_2";
		String Calendar3 = "Calendar_69176_3";

		String Calendar1_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar1);
		String Calendar2_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar2);
		String Calendar3_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar3);
		
		String spaceName = "space69176";
		
		// Create a Space
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName, "");
		
		// Create Calendars
		info("Create calendars");
		goToCalendarPage();
		addCalendar(Calendar1, Calendar1,null);
		addCalendar(Calendar2, Calendar2,null,"/platform/administrators");
		addCalendar(Calendar3, Calendar3,null);
		
		// Go to Home page to check CAL Gadget
		info("Go to Calendar Gadget");
		naviToolbar.goToHomePage();
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		Utils.pause(5000);

		// Verify the display of calendar settings in CAL Gadget
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_ICON);
		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_VERIFIED_TEXT_LABEL);
		waitForAndGetElement(By.xpath(Calendar1_Link_In_Setting));
		waitForAndGetElement(By.xpath(Calendar2_Link_In_Setting));
		waitForAndGetElement(By.xpath(Calendar3_Link_In_Setting));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", spaceName));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_ADDITIONAL_DISPLAY);
		waitForAndGetElement(ELEMENT_SEARCH_IN_CALENDAR_GADGET_SETTING);
		
		// Delete calendars before exit
		goToCalendarPage();
		deleteCalendar(Calendar1, true);
		deleteCalendar(Calendar2, true);
		deleteCalendar(Calendar3, true);
		
		//Delete space before exit test cases
		spaceMag.goToMySpacePage();
		spaceMag.deleteSpace(spaceName,300000);
	  }

	  /*
	   *  CaseId 69137: Remove a Calendar from the list "Displayed Calendars"
	   *  CaseId 69177: Remove Calendar from default user's calendar list
	   *  CaseId 69139: Search an additional calendar
	   *  CaseId 69135: Add an additional calendar to Displayed Calendars list
	   *  CaseId 69136: Save settings changes in the Calendar Gadget
	   */
	  @Test  
	  public void test02_ModifySettingsCalendarGadget(){	
		String spaceName = "space69137";
		String Calendar1 = "Calendar_69137_1";
		String Calendar2 = "Calendar_69177_2";
		String Calendar3 = "Calendar_69135_3";

		String Calendar1_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar1);
		String Calendar2_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar2);
		String Calendar3_Link_In_Setting = ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", Calendar3);
		
		String Calendar1_In_CAL_Gadget = ELEMENT_CALENDAR_IN_CALENDAR_GADGET.replace("${calendar}", Calendar1);
		String Delete_Calendar1 = ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}", Calendar1);
		String Calendar1_In_Additional_List = ELEMENT_CALENDAR_IN_ADDITIONAL_LIST.replace("${calendar}", Calendar1);

		String Calendar2_In_CAL_Gadget = ELEMENT_CALENDAR_IN_CALENDAR_GADGET.replace("${calendar}", Calendar2);
		String Delete_Calendar2 = ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}", Calendar2);
		String Calendar2_In_Additional_List = ELEMENT_CALENDAR_IN_ADDITIONAL_LIST.replace("${calendar}", Calendar2);

		String Calendar_SPACE_In_CAL_Gadget = ELEMENT_CALENDAR_IN_CALENDAR_GADGET.replace("${calendar}", spaceName);
		String Delete_Calendar_SPACE = ELEMENT_DELETE_CALENDAR_ICON.replace("${calendar}", spaceName);
		String Calendar_SPACE_In_Additional_List = ELEMENT_CALENDAR_IN_ADDITIONAL_LIST.replace("${calendar}", spaceName);
		
		// Create a Space
		spaceMag.goToMySpacePage();
		spaceMag.addNewSpace(spaceName, "");
		
		// Create Calendars
		info("Create calendars");
		goToCalendarPage();
		addCalendar(Calendar1, Calendar1,null);
		addCalendar(Calendar2, Calendar2,null,"/platform/users");
		addCalendar(Calendar3, Calendar3,null);
		
		// Go to Home page to check CAL Gadget
		info("Go to Calendar Gadget");
		naviToolbar.goToHomePage();
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		Utils.pause(5000);

		// Verify the display of calendar settings in CAL Gadget
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_ICON);
		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_VERIFIED_TEXT_LABEL);
		waitForAndGetElement(By.xpath(Calendar1_Link_In_Setting));
		waitForAndGetElement(By.xpath(Calendar2_Link_In_Setting));
		waitForAndGetElement(By.xpath(Calendar3_Link_In_Setting));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_SETTING_LINK_ITEM.replace("${calendar}", spaceName));
		waitForAndGetElement(ELEMENT_CALENDAR_GADGET_ADDITIONAL_DISPLAY);
		waitForAndGetElement(ELEMENT_SEARCH_IN_CALENDAR_GADGET_SETTING);
		
		// Remove calendars to CAL Gadget
		click(By.xpath(Delete_Calendar1));
		click(By.xpath(Delete_Calendar2));
		click(By.xpath(Delete_Calendar_SPACE));
		mouseOver(ELEMENT_CALENDAR_GADGET_VERIFIED_TEXT_LABEL, true);
		waitForElementNotPresent(By.xpath(Calendar1_In_CAL_Gadget));
		waitForElementNotPresent(By.xpath(Calendar2_In_CAL_Gadget));
		waitForElementNotPresent(By.xpath(Calendar_SPACE_In_CAL_Gadget));
		mouseOver(ELEMENT_CALENDAR_GADGET_ADDITIONAL_DISPLAY, true);
		waitForAndGetElement(By.xpath(Calendar1_In_Additional_List));
		waitForAndGetElement(By.xpath(Calendar2_In_Additional_List));
		waitForAndGetElement(By.xpath(Calendar_SPACE_In_Additional_List));
		button.ok();
		
		// Search calendars from Additional Calendars
		mouseOver(ELEMENT_CALENDAR_GADGET, true);
		click(ELEMENT_CALENDAR_GADGET_SETTING_ICON, 2);
		Utils.pause(5000);
		info("Search calendars:");
		type(ELEMENT_SEARCH_IN_CALENDAR_GADGET_SETTING,Calendar2, false);
		waitForAndGetElement(By.xpath(Calendar2_In_Additional_List));
		waitForElementNotPresent(By.xpath(Calendar1_In_Additional_List));
		waitForElementNotPresent(By.xpath(Calendar_SPACE_In_Additional_List));
		
		// Add calendars to Displayed Calendars
		mouseOver(By.xpath(Calendar2_In_Additional_List), true);
		Utils.pause(10000);
		if(isElementPresent(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST))
			click(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST);
		else
			click(ELEMENT_ADD_CALENDAR_IN_ADDITION_LIST_PLF_41);	
		
		// Save CAL Gadget Setting
		button.ok();
		
		// Verify the display of calendar settings in CAL Gadget after modifying
		waitForElementNotPresent(By.xpath(Calendar2_In_Additional_List));
		waitForElementNotPresent(By.xpath(Calendar1_In_Additional_List));
		waitForElementNotPresent(By.xpath(Calendar_SPACE_In_Additional_List));
		
		// Delete calendars before exit
		goToCalendarPage();
		deleteCalendar(Calendar1, true);
		deleteCalendar(Calendar2, true);
		deleteCalendar(Calendar3, true);
		
		//Delete space before exit test cases
		spaceMag.goToMySpacePage();
		spaceMag.deleteSpace(spaceName,300000);
	  }
}
