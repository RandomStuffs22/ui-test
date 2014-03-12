package org.exoplatform.selenium.platform.wiki.functional.basicaction;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;

/**
 * 
 * @author HangNTT
 * @date: 7/12/2012
 */
public class Wiki_BasicAction_Move extends BasicAction {
	ManageAccount magAcc;
	Button button;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		magAcc = new ManageAccount(driver);
		button = new Button(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	String[] user1= {DATA_USER3};

	/**
	 * Qmetry ID: 69787
	 * Migrate to PLF 4
	 * == Pending: Selenium issue ==
	 * == when editPagePermission (select "Edit Page" in Permissions Tab) ==
	 * --> FIXED this issue <--
	 */
	//Move a page when user have edit permission on page 
	@Test
	public void test01_MoveAPageWhenUserHavePermisionOnPage(){

		String PAGE_NAME1 = "wiki1";
		String PAGE_NAME2 = "wiki2";
		
		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);
		By ELEMENT_PAGE2 = By.linkText(PAGE_NAME2);
		
		goToWiki();

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		//click(ELEMENT_PAGE1);
		
		deletePagePermission("any");

		addPagePermission(0, user1);
		
		editPagePermission(DATA_USER3, true, true, false, 2);
		
		click(ELEMENT_WIKI_HOME);

		addBlankWikiPage(PAGE_NAME2, PAGE_NAME2, 0);

		//click(ELEMENT_PAGE2);
		
		deletePagePermission("any");

		addPagePermission(0, user1);
		
		editPagePermission(DATA_USER3, true, true, false, 2);
		
		magAcc.signOut();

		magAcc.signIn(DATA_USER3, DATA_PASS);

		goToWiki();
		
		click(ELEMENT_PAGE1);		
			
		movePage(PAGE_NAME1, PAGE_NAME2);

		click(ELEMENT_PAGE2);

		deleteCurrentWikiPage();
	}
	
	//Qmetry ID: 
	//Move a page when any user have edit permission on page 
	@Test
	public void test02_MoveAPageWhenAnyUserHavePermisionOnPage() {

		String PAGE_NAME1 = "wiki3";
		String PAGE_NAME2 = "wiki4";
		
		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);
		By ELEMENT_PAGE2 = By.linkText(PAGE_NAME2);

		goToWiki();

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		editPagePermission("any", true, true, false, 2);
		
		click(ELEMENT_WIKI_HOME);

		addBlankWikiPage(PAGE_NAME2, PAGE_NAME2, 0);
		
		editPagePermission("any", true, true, false, 2);

		magAcc.signOut();

		magAcc.signIn(DATA_USER2, DATA_PASS);

		goToWiki();

		click(ELEMENT_PAGE1);

		movePage(PAGE_NAME1,PAGE_NAME2);

		click(ELEMENT_PAGE2);

		deleteCurrentWikiPage();
	}
	
	//Qmetry ID: 69786
	//Move a page when user doesn't have edit permission on page 
	@Test
	public void test03_MoveAPageWhenUserDoesNotHavePermisionOnPage() {

		String PAGE_NAME1 = "wiki5";
		
		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);

		goToWiki();

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		addPagePermission(0,user1);

		deletePagePermission("any");

		magAcc.signOut();

		magAcc.signIn(DATA_USER3, DATA_PASS);

		goToWiki();

		click(ELEMENT_PAGE1);

		//mouseOver(ELEMENT_MORE_LINK, true);
		mouseOverAndClick(ELEMENT_MORE_LINK);

		waitForElementNotPresent(ELEMENT_MOVE_PAGE_LINK);

		magAcc.signOut();

		magAcc.signIn(DATA_USER1, DATA_PASS);

		goToWiki();

		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();
	}
	
	//Qmetry ID: 69785
	//Move A Page When User don't have Permission On Destination Page
	@Test
	public void test04_MoveAPageWhenUserDoNotHavePermisionOnDestinationPage() {

		String PAGE_NAME1 = "wiki6";
		String PAGE_NAME2 = "wiki7";
		
		By ELEMENT_PAGE1 = By.linkText(PAGE_NAME1);
		By ELEMENT_PAGE2 = By.linkText(PAGE_NAME2);

		String ELEMENT_VERIRY_MESSAGE = "You have no edit permission at the destination page";

		goToWiki();

		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		addPagePermission(0,user1);

		editPagePermission(DATA_USER3, true, true, false, 2);
		
		deletePagePermission("any");

		click(ELEMENT_WIKI_HOME);

		addBlankWikiPage(PAGE_NAME2, PAGE_NAME2, 0);
		
		editPagePermission("any", true, false, false, 2);

		magAcc.signOut();

		magAcc.signIn(DATA_USER3, DATA_PASS);

		goToWiki();

		click(ELEMENT_PAGE1);

		movePage(PAGE_NAME1,PAGE_NAME2);

		waitForTextPresent(ELEMENT_VERIRY_MESSAGE);

		click(button.ELEMENT_OK_BUTTON);

		waitForElementNotPresent(button.ELEMENT_OK_BUTTON);

		//cancel();
		click(ELEMENT_CANCEL_BUTTON_MOVE_PAGE);
		waitForElementNotPresent(ELEMENT_CANCEL_BUTTON_MOVE_PAGE);

		magAcc.signOut();

		magAcc.signIn(DATA_USER1, DATA_PASS);

		goToWiki();

		click(ELEMENT_PAGE1);

		deleteCurrentWikiPage();

		click(ELEMENT_PAGE2);

		deleteCurrentWikiPage();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
