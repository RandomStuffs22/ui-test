package org.exoplatform.selenium.platform.gatein.functional.globalsettings;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PortalManagement;
import org.exoplatform.selenium.platform.UserGroupManagement;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: hzekri
 * @date: 08/11/2013
 * @update: phuongdt
 * @date: 20/11/2013
 */
public class GateIn_GlobalSettings_LanguageSettings extends PortalManagement {

	ManageAccount magAc;
	NavigationToolbar navTool;
	UserGroupManagement userGroup;
	EcmsBase ecmsBase;

	public final String LANGUAGE_ENGLISH = "English";
	public final String LANGUAGE_FRENCH = "French";


	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 73516
	 * Change language
	 * Based on version 1(hainh) on qmetry
	 * Step 1: Show form to change language
	 * Step 2: Change language
	 * Step 3: Check displaying language when sign out
	 * Step 4: Check displaying language when sign in again
	 */
	@Test
	public void test01_ChangeLanguage(){
		initFFBrowserWithSetLanguageBrowser("en");
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		userGroup = new UserGroupManagement(driver);
		ecmsBase = new EcmsBase(driver);

		/* Step 1: Show form to change language */
		//- Login by user
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);

		/*Create data*/
		userGroup.editUserAccount("john", false, null, null, null, null, null, null, null, "");

		//Select Star toolbar portlet /Change Language
		//- Language settings form is shown, list all supporting languages in the left pane
		//- Current using language is high light & marked
		/* Step 2: Change language */
		//- Select one language from list
		//- Click Apply
		//Portal is displayed in new selected language
		info("Check when change language to French");
		magAc.changeLanguageForUser("French");
		waitForAndGetElement(ELEMENT_HOME_TEXT_FRENCH);

		/* Step 3: Check displaying language when sign out */
		//Sign out
		magAc.signOut();

		//- Portal is displayed in public mode
		//- It keep Cookies from this last option in your website or 
		//It is displayed in language of current using portal or 
		//in the language of browser that was defined by user, not new selected language at step 2
		waitForAndGetElement(ELEMENT_SIGNIN_TITLE_ENGLISH);

		/* Step 4: Check displaying language when sign in again */
		//Sign In
		magAc.signIn(DATA_USER_JOHN, DATA_PASS);

		//- Portal is displayed in private mode
		//- It is displayed in new selected language at step 2, 
		// not in language of Cookie or current using portal or in the language of browser that was defined by user.
		//  Show form to change language and Change language
		waitForAndGetElement(ELEMENT_HOME_TEXT_FRENCH);

		/*Clear data*/
		info("-- Clear data --");
		magAc.changeLanguageForUser("Anglais");
		waitForAndGetElement(ELEMENT_HOME_TEXT_ENGLISH);
	}

	/**CaseId: 73585
	 * Check display language of portal in public mode
	 * Based on version 1(hainh) on qmetry
	 * Step 1: Show portal in public mode
	 * Step 2: Show form to change language
	 * Step 3: Change language
	 */
	@Test
	public void test02_CheckDisplayLanguageOfPortalInPublicModeUsingLanguageCookie(){
		//Set language of browser is supported by portal
		initFFBrowserWithSetLanguageBrowser("de");

		/* Step 1: Show portal in public mode */
		//-  Input URL into address bar:
		//http://localhost:8080/portal/acme
		//-  Press Enter key
		driver.manage().window().maximize();
		driver.get(baseUrl+"/acme");
		magAc = new ManageAccount(driver);
		//- Portal in public mode is displayed, a link to sign in and change language is displayed at the top right
		//- It is displayed in language of Cookie on website that was supported by portal
		//Default language of portal is english
		waitForAndGetElement(magAc.PRODUCTS_LABEL_GERMAN);

		/* Step 2: Show form to change language */
		//Click the Change Language link on the top right of screen
		//Language settings form is shown
		/* Step 3: Change language */
		//- Select one language from list
		//- Click Apply
		//Portal is displayed in new selected language
		magAc.changeLanguageWithoutLogin(LANGUAGE_FRENCH);

		/*Clear data*/
		info("-- Clear data --");
		magAc.changeLanguageWithoutLogin(LANGUAGE_ENGLISH);
		magAc.signInAcme(DATA_USER_JOHN, DATA_PASS);
	}

	/**CaseId: 73585
	 * Check display language of portal in public mode
	 * Based on version 1(hainh) on qmetry
	 * Step 1: Show portal in public mode
	 * Step 2: Show form to change language
	 * Step 3: Change language
	 */
	@Test
	public void test02_CheckDisplayLanguageOfPortalInPublicModeUsingLanguagePortal(){
		//Set language of browser is not supported by portal
		initFFBrowserWithSetLanguageBrowser("cs");
		driver.manage().window().maximize();

		/* Step 1: Show portal in public mode */
		//-  Input URL into address bar:
		//http://localhost:8080/portal/acme
		//-  Press Enter key
		driver.get(baseUrl+"/acme");
		magAc = new ManageAccount(driver);

		//- Portal in public mode is displayed, a link to sign in and change language is displayed at the top right
		//- It is displayed in language of current using portal
		//Default language of portal is english
		waitForAndGetElement(magAc.PRODUCTS_LABEL_ENGLISH);

		/* Step 2: Show form to change language */
		//Click the Change Language link on the top right of screen
		//Language settings form is shown
		/* Step 3: Change language */
		//- Select one language from list
		//- Click Apply
		//Portal is displayed in new selected language
		magAc.changeLanguageWithoutLogin(LANGUAGE_FRENCH);

		/*Clear data*/
		info("-- Clear data --");
		magAc.changeLanguageWithoutLogin(LANGUAGE_ENGLISH);
		magAc.signInAcme(DATA_USER_JOHN, DATA_PASS);
	}

	/**CaseId: 73641
	 * Check display language of portal in private mode with demo account
	 * Based on version 1(hainh) on qmetry
	 * Step 1: Check display after login to portal
	 * Step 2: Check displaying language when language of browser don't support by portal with user account demo
	 * Step 3: Check when change language
	 */
	@Test
	public void test03_CheckDisplayLanguageOfPortalInPrivateModeWithDemoAccountUsingLanguageCookie(){		
		//Set language of browser is supported by portal
		initFFBrowserWithSetLanguageBrowser("fr");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		userGroup = new UserGroupManagement(driver);
		navTool = new NavigationToolbar(driver);

		/*Create data*/
		magAc.userSignIn(userType.ADMIN);
		userGroup.editUserAccount("demo", false, null, null, null, null, null, null, null, "");

		/* Step 1: Check display after login to portal */
		//Sign In portal by Admin/Manager/User/Demo account
		magAc.userSignIn(userType.DEVELOPER);

		//- Portal in private mode is displayed
		//- It is displayed in language of the language of Cookie on website that was supported by portal 
		//Default language of portal is english
		waitForAndGetElement(ELEMENT_HOME_TEXT_FRENCH);

		/* Step 3: Check when change language */
		//- Click Change Language in Exo Start portlet
		//- Select one language from list  
		//- Click Apply
		//- Language settings form is shown
		//- Portal is displayed in new selected language
		magAc.changeLanguageForUser("Anglais");

		//- At next time when edited user sign in, the displaying language will be in the new selected
		magAc.userSignIn(userType.DEVELOPER);
		waitForAndGetElement(ELEMENT_HOME_TEXT_ENGLISH);
	}
	/**CaseId: 73641
	 * Check display language of portal in private mode with demo account
	 * Based on version 1(hainh) on qmetry
	 * Step 1: Check display after login to portal
	 * Step 2: Check displaying language when language of browser don't support by portal with user account demo
	 * Step 3: Check when change language
	 */
	@Test
	public void test03_CheckDisplayLanguageOfPortalInPrivateModeWithDemoAccountUsingLanguagePortal(){		
		//Set language of browser is not supported by portal
		initFFBrowserWithSetLanguageBrowser("cs");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		userGroup = new UserGroupManagement(driver);
		navTool = new NavigationToolbar(driver);

		/*Create data*/
		magAc.userSignIn(userType.ADMIN);
		userGroup.editUserAccount("demo", false, null, null, null, null, null, null, null, "");

		/* Step 1: Check display after login to portal */
		//Sign In portal by Admin/Manager/User/Demo account
		magAc.userSignIn(userType.DEVELOPER);

		//- Portal in private mode is displayed
		//- It is displayed in language of the language of current using portal ( admin/manager/User/Demo don't set language )
		//Default language of portal is english
		waitForAndGetElement(ELEMENT_HOME_TEXT_ENGLISH);

		/* Step 3: Check when change language */
		//- Click Change Language in Exo Start portlet
		//- Select one language from list  
		//- Click Apply
		//- Language settings form is shown
		//- Portal is displayed in new selected language
		magAc.changeLanguageForUser(LANGUAGE_FRENCH);

		//- At next time when edited user sign in, the displaying language will be in the new selected
		magAc.userSignIn(userType.DEVELOPER);
		waitForAndGetElement(ELEMENT_HOME_TEXT_FRENCH);
		magAc.changeLanguageForUser("Anglais");
	}

	/**CaseId: 73691
	 * Check display language of portal in private mode with new account
	 * Step 1: Register new account
	 * Step 2: Check display after login to portal
	 * Step 3: Check displaying language when language of browser don't support by portal with new account
	 * Step 4: Check when change language
	 */
	@Test
	public void test04_CheckDisplayLanguageOfPortalInPrivateModeWithNewAccountUsingLanguageCookie(){
		//Set language of browser is supported by portal
		initFFBrowserWithSetLanguageBrowser("de");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		userGroup = new UserGroupManagement(driver);
		navTool = new NavigationToolbar(driver);
		String username = getRandomString();
		String password = username;
		String firstName = "firstName";
		String lastName = "lastName";
		String displayName = "";
		String email = username+"@platform.com";
		String userNameGiven = "";
		String language = "French";
		boolean verify = true;
		magAc.userSignIn(userType.ADMIN);

		/* Step 1: Register new account */
		//Register a new account with valid values 
		//New account is registered successfully
		navTool.goToNewStaff();
		magAc.addNewUserAccount(username,password,password,firstName,lastName,displayName,email,userNameGiven,language,verify);

		/* Step 2: Check display after login to portal */
		//Sign In portal by new account
		magAc.signOut();
		magAc.signIn(username, password);

		//- Portal in private mode is displayed
		//- It is displayed in language of current user
		waitForAndGetElement(ELEMENT_HOME_TEXT_FRENCH);

		/* Step 4: Check when change language */
		//- Click the Change Language link
		//- Select one language from list  
		//- Click Apply
		//- Language settings form is shown
		//- Portal is displayed in new selected language
		magAc.changeLanguageForUser("Anglais");

		//- At next time when edited user sign in, the displaying language will be in the new selected
		magAc.signOut();
		magAc.signIn(username, password);
		waitForAndGetElement(ELEMENT_HOME_TEXT_ENGLISH);
	}

	/**CaseId: 73691
	 * Check display language of portal in private mode with new account
	 * Step 1: Register new account
	 * Step 2: Check display after login to portal
	 * Step 3: Check displaying language when language of browser don't support by portal with new account
	 * Step 4: Check when change language
	 */
	@Test
	public void test04_CheckDisplayLanguageOfPortalInPrivateModeWithNewAccountUsingLanguagePortal(){
		//Set language of browser is not supported by portal
		initFFBrowserWithSetLanguageBrowser("cs");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		userGroup = new UserGroupManagement(driver);
		navTool = new NavigationToolbar(driver);
		String username = getRandomString();
		String password = username;
		String firstName = "firstName";
		String lastName = "lastName";
		String displayName = "";
		String email = username+"@platform.com";
		String userNameGiven = "";
		String language = "French";
		boolean verify = true;
		magAc.userSignIn(userType.ADMIN);

		/* Step 1: Register new account */
		//Register a new account with valid values 
		//New account is registered successfully
		navTool.goToNewStaff();
		magAc.addNewUserAccount(username,password,password,firstName,lastName,displayName,email,userNameGiven,language,verify);

		/* Step 3: Check displaying language when language of browser don't support by portal with new account */
		//Sign In portal by new account
		magAc.signOut();
		magAc.signIn(username, password);

		//- Portal in private mode is displayed
		//- It is displayed in language of current user
		waitForAndGetElement(ELEMENT_HOME_TEXT_FRENCH);

		/* Step 4: Check when change language */
		//- Click the Change Language link
		//- Select one language from list  
		//- Click Apply
		//- Language settings form is shown
		//- Portal is displayed in new selected language
		magAc.changeLanguageForUser("Anglais");

		//- At next time when edited user sign in, the displaying language will be in the new selected
		magAc.signOut();
		magAc.signIn(username, password);
		waitForAndGetElement(ELEMENT_HOME_TEXT_ENGLISH);

	}
	/**CaseId: 73736
	 * Check display language of portal after change language in Organization portlet
	 * Step 1: Show form to edit a specific user
	 * Step 2: Change displaying language for user
	 * Step 3: Check when change language
	 */
	@Test
	public void test05_CheckDisplayLanguageOfPortalAfterChangeLanguageInOrganizationPortlet(){
		//Set language of browser is supported by portal
		initFFBrowserWithSetLanguageBrowser("en");
		driver.manage().window().maximize();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		userGroup = new UserGroupManagement(driver);
		magAc.userSignIn(userType.ADMIN);
		String oldLang = "";
		String newLang = "French";

		/* Step 1: Show form to edit a specific user */
		//- Login by admin/HR account
		//- Go to User and Group Management Page
		//- Click Edit user icon of a specific user from list
		//- Select User Profile tab
		//Informations of corresponding user are shown
		/* Step 2: Change displaying language for user */
		//- In Edit User Profile form: Select new language
		//- Click Save
		//- New change is saved
		waitForAndGetElement(ELEMENT_HOME_TEXT_ENGLISH);
		userGroup.editUserAccount("mary", false, null, null, null, null, null, null, null, newLang);

		/* Step 3: Check when change language */
		//Sign In portal by account which edited above
		//Informations of corresponding user are shown
		magAc.userSignIn(userType.PUBLISHER);
		waitForAndGetElement(ELEMENT_HOME_TEXT_FRENCH);

		/*Clear data*/
		info("-- Clear data --");
		magAc.userSignIn(userType.ADMIN);
		userGroup.editUserAccount("mary", false, null, null, null, null, null, null, null, oldLang);
	}

	/**CaseId: 73772
	 * Check display language of portal with new account is created in public mode
	 * Step 1: Create new account in public mode
	 * Step 2: Complete adding new account with valid values
	 * Step 3: Check display after login to portal
	 */
	@Test
	public void test06_CheckDisplayLanguageOfPortalWithNewAccountIsCreatedInPublicMode(){
		initFFBrowserWithSetLanguageBrowser("fr");
		driver.manage().window().maximize();
		driver.get(baseUrl+"/acme");
		magAc = new ManageAccount(driver);
		userGroup = new UserGroupManagement(driver);
		navTool = new NavigationToolbar(driver);
		String username = getRandomString();
		String password = username;
		String firstName = "firstName";
		String lastName = "lastName";
		String email = username+"@platform.com";

		//Set captra to invisible
		magAc.signInAcme("root","gtngtn");
		info("Go to page edit layout");
		driver.get(baseUrl+"/acme/newAccount");
		navTool.goToEditPageEditor();
		setUseCaptcha(false,true);
		magAc.signOut();

		/* Step 1: Create new account in public mode */
		//- Select Register link on the top right
		//Show blank account register account form
		/* Step 2: Complete adding new account with valid values */
		//- Input un-existing, valid value for User name
		//- Input valid values for other fields
		//- Click Subscribe
		//Show message alerts new account is registered successfully
		driver.get(baseUrl+"/acme/newAccount");
		magAc.addNewUserAccountInPublicMode(username, password, password, firstName, lastName, email, true);
		Utils.pause(1000);

		/* Step 3: Check display after login to portal */
		//Sign In portal by new account
		//- Portal in private mode is displayed
		//- It is displayed in language of browsers
		driver.get(baseUrl+"/acme");
		magAc.signInAcme(username,password);
		waitForAndGetElement(magAc.PRODUCTS_LABEL_FRENCH);
	}
}
