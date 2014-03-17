package org.exoplatform.selenium.platform.calendar.sniff;

import static org.exoplatform.selenium.TestLogger.*;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.calendar.CalendarBase;
import org.exoplatform.selenium.platform.calendar.Task;
import org.exoplatform.selenium.platform.social.SpaceManagement;

/**
 * @author thuntn
 * @date 29 Oct 2013 
 */

public class Calendar_PublishActivities_Task extends CalendarBase{

	ManageAccount acc;
	Task tsk;
	SpaceManagement sp;
	NavigationToolbar toolBar;
	HomePageActivity homeAct;
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		acc = new ManageAccount(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		sp = new SpaceManagement(driver);
		toolBar = new NavigationToolbar(driver);
		homeAct = new HomePageActivity(driver);
		tsk = new Task(driver);
		button = new Button(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	/**Publish activity for Task of Group Calendar of Space, and Delete task for Space Calendar
	 * CaseID 74740, CaseID 74744
	 */
	@Test
	public void test01_PublishActivityForTaskOfGroupCalendarOfSpace() {
		String space = "Space74740";
		String task = "Task74740";
		info("Publish activity for Task of Group Calendar of Space, "
				+ "and Delete task for Space Calendar");
		//Add space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		Utils.pause(3000);
		sp.goToSpaceMenu("Agenda");

		//Add task
		tsk.addQuickTask(task, task, null, null, false);
		
		//Check activity
		toolBar.goToHomePage();
		homeAct.checkTaskActivity(task);

		//Delete task
		sp.goToMySpacePage();
		sp.accessSpace(space);
		sp.goToSpaceMenu("Agenda");
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", task));
		deleteEventTask(task, selectDayOption.ONEDAY);

		//Check activity after deleting
		toolBar.goToHomePage();
		//homeAct.checkTaskActivity(task, false);

		//Delete data
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}

	/**Update activity for event of Space Calendar- event is updated as all day event a space
	 * CaseID 74741
	 */
	@Test
	public void test02_UpdateActivityForTaskWhenAddAttachment() {
		String space = "Space74741";
		String task = "Task74741";
		String path = "TestData/Calendar_74741.xml";
		info("Update activity for event of Space Calendar- event is updated as all day event a space");
		
		//Add space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space,60000);
		Utils.pause(3000);
		sp.goToSpaceMenu("Agenda");

		//Add task
		tsk.addQuickTask(task, task, null, null, false);
		
		//Edit task- add attachment
		tsk.editTask(task,null, null, null, null, false,path);

		//Check activity
		toolBar.goToHomePage();
		driver.navigate().refresh();
		homeAct.checkTaskActivity(task);
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", task).replace("${comment}", homeAct.MSG_TASK_COMMENT_UPDATE_ATTACHMENT));

		//Delete data
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}

	/**Update Task for a Space Calendar - edit note
	 * CaseID 74742
	 */
	@Test
	public void test03_UpdateActivityForTaskWhenUpdateNote() {
		String space = "Space74742";
		String task = "Event74742";
		String note = "new Event74742";
		info("Update activity for event of Space Calendar - event summary");
		
		//Add a space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		sp.goToSpaceMenu("Agenda");

		//Add task
		tsk.addQuickTask(task, task, null, null, false);
		tsk.editTask(task,null, note, null,null, false,null);
		
		//Check activity
		toolBar.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", note).replace("${comment}", 
				homeAct.MSG_TASK_COMMENT_UPDATE_NOTE.replace("${note}",note)),50000);
		
		//Delete data
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}

	/**Update activity for event of Space Calendar - event description
	 * CaseID 74743
	 */
	@Test
	public void test04_UpdateActivityForTaskWhenUpdateTaskStatus() {
		String space = "Space74743";
		String task = "Task74743";
		info("Update activity for event of Space Calendar - event description");

		//Add space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		sp.goToSpaceMenu("Agenda");

		//Add task
		tsk.addQuickTask(task, task, null, null, false);
		
		//Edit task
		tsk.editTask(task,null, null,null, null, false,null,null,null,"Completed");
		
		//Check activity
		toolBar.goToHomePage();
		driver.navigate().refresh();
		waitForAndGetElement(homeAct.ELEMENT_ACTIVITY_COMMENT_CONTENT.replace("${title}", task).replace("${comment}", homeAct.MSG_TASK_COMMENT_UPDATE_STATUS));
		
		//Delete data
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}

	/**Redirect to Calendar Application
	 * CaseID 75291
	 */
	@Test
	public void test05_RedirectToSpaceCalendarApplication() {
		String space = "Space75291";
		String task = "Task75291";
		info("Redirect to Calendar Application");

		//Add space
		sp.goToAllSpaces();
		sp.addNewSpace(space,space);
		Utils.pause(3000);
		sp.goToSpaceMenu("Agenda");

		//Add task
		tsk.addQuickTask(task, task, null, null, false);
		
		//Check activity
		toolBar.goToHomePage();
		homeAct.checkTaskActivity(task);
		
		//Open task preview
		click(By.linkText(task));
		waitForAndGetElement(tsk.ELEMENT_TASK_PREVIEW_TITLE.replace("${task}", task));
		button.close();
		waitForAndGetElement(sp.ELEMENT_SPACE_BREAD.replace("${space}", space));
		waitForAndGetElement(ELEMENT_EVENT_TASK_ONE_DAY.replace("${taskName}", task));

		//Delete data
		sp.goToMySpacePage();
		sp.deleteSpace(space);
	}
}
