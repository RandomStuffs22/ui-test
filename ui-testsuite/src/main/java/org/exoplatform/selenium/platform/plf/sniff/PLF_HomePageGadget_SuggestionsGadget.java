package org.exoplatform.selenium.platform.plf.sniff;
import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.platform.HomePageGadget;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleConnection;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author hangNTT
 * @date 31 Oct 2013
 */
public class PLF_HomePageGadget_SuggestionsGadget extends Activity{

	HomePageGadget homeGad;
	ManageAccount acc; 
	ManageMember magMember;
	PeopleConnection peoConn;
	NavigationToolbar navToolBar;

	@BeforeMethod
	public void setUpBeforeTest(){
		getDriverAutoSave();
		acc = new ManageAccount(driver);
		homeGad = new HomePageGadget(driver);
		magMember = new ManageMember(driver);
		peoConn = new PeopleConnection(driver);
		navToolBar = new NavigationToolbar(driver);
		acc.signIn(DATA_USER_JOHN, DATA_PASS);

	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * CaseID 70596
	 * Not show suggestion gadget
	 * 
	 * CaseID 70601
	 * Accept a space suggestion
	 * 
	 * CaseID 70602
	 * Accept a people suggestion
	 * 
	 * CaseID 70751
	 * Cancel a space connection
	 * 
	 * CaseID 70729
	 * Cancel a people connection
	 * 
	 */
	@Test
	public void test01_NotShowOrAcceptOrCancelSuggestionGadget() { 
		/*Declare variables*/
		String user1="FQA VN";
		String user2="Jack Miller";
		String user3= "James Davis";
		String user4="Mary Williams";
		String spaceName1 = "space707291";
		String spaceName2 = "space707292";
		String spaceName3 = "space707293";

		//Login with mary and create new space
		acc.userSignIn(userType.PUBLISHER);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, "", 60000);

		//Login with demo and create new space
		acc.userSignIn(userType.DEVELOPER);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, "", 60000);

		//Login with james and create new space
		acc.userSignIn(userType.AUTHOR);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName3, "", 60000);

		//Login with user john
		acc.userSignIn(userType.ADMIN);

		//	CaseID 70591: Check Check display of Suggestion Gadget
		//- This gadget is displayed at the right, as attachment SuggestionsGadget.png
		//- The suggestion gadget always displays 2 people suggestions and 2 space suggestions.
		//- The 2 people suggestions are people with the most common connections with the users and ordered by alphabet
		waitForAndGetElement(homeGad.ELEMENT_SUGGESTION_GADGET_FORM);
		waitForAndGetElement(homeGad.ELEMENT_VERIFY_USER_SUGGESTIONS.replace("${peopleName}", user1));
		waitForAndGetElement(homeGad.ELEMENT_VERIFY_USER_SUGGESTIONS.replace("${peopleName}", user2));
		waitForElementNotPresent(homeGad.ELEMENT_VERIFY_USER_SUGGESTIONS.replace("${peopleName}", user3));

		//- The 2 space suggestions are spaces that have the most members who are user's connections and ordered by alphabet
		waitForAndGetElement(homeGad.ELEMENT_SUGGESTION_GADGET_FORM);
		waitForAndGetElement(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName2));
		waitForAndGetElement(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName3));
		waitForElementNotPresent(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName1));

		//	CaseID 70602: Accept a people suggestion
		//Accept a people suggestions
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_VERIFY_USER_SUGGESTIONS.replace("${peopleName}", user2)));
		homeGad.connectUserSuggestionsGadget(user2);

		/* CaseID 70601: Accept a space suggestion*/
		//Accept a space suggestions
		waitForAndGetElement(By.xpath(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName3)));
		homeGad.connectSpaceSuggestionsGadget(spaceName3);

		//		 CaseID 70729: Cancel a people connection
		//Remove a people suggestions
		homeGad.removeUserSuggestionsGadget(user3);
		homeGad.removeUserSuggestionsGadget(user1);
		homeGad.removeUserSuggestionsGadget(user4);

		//		CaseID 70751: Cancel a space connection
		//Remove a space suggestion
		homeGad.removeSpaceSuggestionsGadget(spaceName2);

		//Login with mary and invite "John" to space
		acc.userSignIn(userType.PUBLISHER);
		magMember.managerInviteUserToJoinSpace(userType.PUBLISHER,spaceName1,userType.ADMIN,false);

		//Login with demo and invite "John" to space
		acc.userSignIn(userType.DEVELOPER);
		magMember.managerInviteUserToJoinSpace(userType.DEVELOPER,spaceName2,userType.ADMIN,false);

		//Login with "John"
		acc.userSignIn(userType.ADMIN);
		waitForElementNotPresent(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName1));
		waitForElementNotPresent(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS.replace("${spaceName}", spaceName2));

		//Connect people
		navToolBar.goToConnectionPage();
		peoConn.connectPeople(user1);
		peoConn.connectPeople(user3);
		peoConn.connectPeople(user4);
		navToolBar.goToHomePage();

		/* CaseID 70596: Not show suggestion gadget*/
		waitForElementNotPresent(homeGad.ELEMENT_SUGGESTION_GADGET_FORM);

		/*Clear data*/
		info("-- Clear data --");
		//Cancel request
		navToolBar.goToConnectionPage();
		peoConn.cancelRequest(user1);
		peoConn.cancelRequest(user2);
		peoConn.cancelRequest(user3);
		peoConn.cancelRequest(user4);

		//Login with mary and delete space
		acc.userSignIn(userType.PUBLISHER);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName1,300000);

		//Login with demo and delete space
		acc.userSignIn(userType.DEVELOPER);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName2,300000);

		//Login with james and delete space
		acc.userSignIn(userType.AUTHOR);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName3,300000);
	}

	/* CaseID 70591
	 * Check Check display of Suggestion Gadget
	 * PENDING: Confirm display of suggestion Gadget by alphabe and the latest pepple and space
	 * Refer: http://int.exoplatform.org/portal/g/:spaces:platform_40/platform_40/wiki/Homepage_Gadgets_Specification#HSuggestions
	 * SUGGESTIONS_02,SUGGESTIONS_03, SUGGESTIONS_09
	 */ 
	@Test(groups="pending")
	public void test02_CheckCheckDisplayOfSuggestionGadget() { 
		/*Declare variables*/
		String spaceName1 = "space705911";
		String spaceName2 = "space705912";
		String spaceName3 = "space705913";

		//Login with mary and create new space
		acc.userSignIn(userType.PUBLISHER);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, "");

		//Login with demo and create new space
		acc.userSignIn(userType.DEVELOPER);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, "");

		//Login with james and create new space
		acc.userSignIn(userType.AUTHOR);
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName3, "");

		//Login with user john
		acc.userSignIn(userType.ADMIN);

		/*CaseID 70591: Check Check display of Suggestion Gadget*/
		//- This gadget is displayed at the right, as attachment SuggestionsGadget.png
		//- The suggestion gadget always displays 2 people suggestions and 2 space suggestions.
		//- The 2 people suggestions are people with the most common connections with the users and ordered by alphabet
		waitForAndGetElement(homeGad.ELEMENT_SUGGESTION_GADGET_FORM);
		String peoSugest1 = waitForAndGetElement(homeGad.ELEMENT_VERIFY_USER_SUGGESTIONS_INDEX.replace("${index}", "1")).getText().trim();
		String peoSugest2 = waitForAndGetElement(homeGad.ELEMENT_VERIFY_USER_SUGGESTIONS_INDEX.replace("${index}", "2")).getText().trim();
		assert peoSugest1.compareTo(peoSugest2)<0;

		//- The 2 space suggestions are spaces that have the most members who are user's connections and ordered by alphabet
		String spaceSugest1 = waitForAndGetElement(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS_INDEX.replace("${index}", "1")).getText().trim();
		String spaceSugest2 = waitForAndGetElement(homeGad.ELEMENT_VERIFY_SPACE_SUGGESTIONS_INDEX.replace("${index}", "2")).getText().trim();
		assert spaceSugest1.compareTo(spaceSugest2)<0;

		/*Clear data*/
		info("-- Clear data --");
		//Login with mary and delete space
		acc.userSignIn(userType.PUBLISHER);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName1,300000);

		//Login with demo and delete space
		acc.userSignIn(userType.DEVELOPER);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName2,300000);

		//Login with james and delete space
		acc.userSignIn(userType.AUTHOR);
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName3,300000);
	}

}
