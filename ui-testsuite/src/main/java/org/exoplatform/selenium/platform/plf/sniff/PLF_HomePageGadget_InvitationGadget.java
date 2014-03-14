package org.exoplatform.selenium.platform.plf.sniff;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.social.PeopleConnection;

/**
 * 
 * @author hangNTT
 * @date 18 Oct 2013
 */
public class PLF_HomePageGadget_InvitationGadget extends Activity{
	ManageAccount acc;
	Event evt;
	Task tsk;
	Button btn;
	ManageMember magMember;
	PeopleConnection peoConn;
	NavigationToolbar navToolBar;
	HomePageGadget homeGad;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		evt = new Event(driver);
		tsk = new Task(driver);
		btn = new Button(driver);
		homeGad = new HomePageGadget(driver);
		acc.signIn(DATA_USER1, DATA_PASS);
		magMember = new ManageMember(driver);
		peoConn = new PeopleConnection(driver);
		navToolBar = new NavigationToolbar(driver);		
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Check Invitation Gadget
	 * CaseID 70579: Check display of Invitation Gadget
	 * 70582, Accept a request
	 * 70586, Refuse a request
	 * 70604, Not show Invitation gadget
	 * 
	 */
	@Test
	public void test01_InvitationGadget() {

		String spaceName1 = "space705791";
		String spaceName2 = "space705792";
		String user2="Jack Miller";
		String user3="James Davis";
		String user5="John Smith";
		String user_login2 = DATA_USER4;
		String user_login3 = DATA_USER2;
		String user_login4 = DATA_USER3;
		String number_gadget = "4";
		String status = "Private Space";

		// Check not show Invitation gadget
		waitForElementNotPresent(homeGad.ELEMENT_INVITATION_GADGET);
		acc.signOut();

		//Create new space1 by mary
		acc.signIn(user_login3, DATA_PASS);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, "");
		magMember.managerInviteUserToJoinSpace(userType.PUBLISHER,spaceName1,userType.ADMIN,false);
		acc.signOut();

		//Create space for demo
		acc.signIn(user_login2, DATA_PASS);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, "");
		magMember.managerInviteUserToJoinSpace(userType.PUBLISHER,spaceName2,userType.ADMIN,false);
		acc.signOut();

		//Login by demo
		acc.signIn(user_login2,DATA_PASS);
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user5);
		acc.signOut();

		//Login by james
		acc.signIn(user_login4,DATA_PASS);
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user5);
		acc.signOut();
		acc.signIn(DATA_USER1, DATA_PASS);
		waitForAndGetElement(homeGad.ELEMENT_INVITATION_GADGET);
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_SHOW_CONNECTIONS_REQUEST_USER.replace("${nameinvitation}",user3)));
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_SHOW_CONNECTIONS_REQUEST_USER.replace("${nameinvitation}",user2)));
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_SHOW_CONNECTIONS_REQUEST_SPACE.replace("${namespace}",spaceName2)));
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_VERIFY_STATUS_SPACE.replace("${namespace}",spaceName2).replace("${statusspace}", status)));
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_SHOW_CONNECTIONS_REQUEST_SPACE.replace("${namespace}",spaceName1)));
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_VERIFY_STATUS_SPACE.replace("${namespace}",spaceName1).replace("${statusspace}", status)));

		//Accept invitation
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_TITLE_OF_GAGDET.replace("${number}", number_gadget)));
		homeGad.acceptInvitationGadget(user2);

		//Remove invitation
		homeGad.removeInvitationGadget(user3);
		
		/*Clear data*/
		info("-- clear data --");
		//remove connection
		navToolBar.goToConnectionPage();
		peoConn.removeConnection(user2);
		acc.signOut();

		//Delete space
		acc.signIn(user_login3,DATA_PASS);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName1,300000);
		acc.signOut();
		acc.signIn(user_login2,DATA_PASS);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName2,300000);		
	}
}