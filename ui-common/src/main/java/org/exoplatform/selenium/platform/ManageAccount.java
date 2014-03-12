package org.exoplatform.selenium.platform;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.exoplatform.selenium.TestLogger.info;

public class ManageAccount extends PlatformBase {

	//[Create a New Account] Screen (Public Mode)
	public final By ELEMENT_SUBSCRIBE_BUTTON = By.xpath("//button[text()='Subscribe']");
	public final By ELEMENT_RESET_BUTTON = By.xpath(".//*[@id='UIRegisterForm']/div[2]/div/div/a[2]");
	public final By ELEMENT_REGISTER_LINK = By.xpath("//b[contains(text(),'Register')]");
	public final By ELEMENT_INPUT_CONFIRM_PASSWORD_PUBLIC_MODE = By.id("confirmPassword");
	public final By ELEMENT_INPUT_EMAIL_PUBLIC_MODE = By.id("emailAddress");
	public final By ELEMENT_REGISTER_ACCOUNT_LINK = By.xpath("//b[contains(text(),'Register')]");

	public final String MESSAGE_SUCCESSFULLY_REGISTERED_ACCOUNT = "You have successfully registered a new account!";
	public final By MESSAGE_SUCCESSFULLY_REGISTERED_ACCOUNT_FRENCH = By.xpath("//*[contains(text(),'Vous venez de créer un nouveau compte.')]");
	public final String MESSAGE_DUPLICATE_ACCOUNT = "This username already exists, please enter another one.";
	public final String MESSAGE_ALERT_PASSWORD = "Password and Confirm Password must be the same.";
	public final String MESSAGE_INVALID_EMAIL_ADDRESS = "Your email address is invalid. Please enter another one.";

	//Login form
	public final By ELEMENT_LOGIN_CONTAINER = By.className("loginContainer");
	public final String ELEMENT_LOGIN_HEADER_LABEL = "//div[@class='loginHeader introBox']//div[@class='userLoginIcon' and text()='Connect to your account']";
	public final String MESSAGE_LOGIN_FAILED = "//*[contains(text(),'Sign in failed. Wrong username or password.')]";
	public final String ELEMENT_REMEMBER_MY_LOGIN_YES = "//label[@class='iPhoneCheckLabelOn']//span[text()='Yes']";


	public ManageAccount(WebDriver dr,String...plfVersion){
		driver = dr;
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
	}

	Dialog dialog;
	Button button;
	ManageAlert magAlert;
	NavigationToolbar navTool;

	//Sign-in function for eXoGTN
	public void signIn(String username, String password,Boolean...opParams) {
		Boolean verify = (Boolean) (opParams.length > 0 ? opParams[0]: true);
		Boolean maxWin = (Boolean) (opParams.length > 1 ? opParams[1]: true);
		if(maxWin){
			driver.manage().window().maximize();
			driver.navigate().refresh();
			Utils.pause(2000);
		}
		if (firstTimeLogin){
			signOut();
			firstTimeLogin = false;
		}
		info("--Sign in as " + username + "--");
		/*if (isElementPresent(ELEMENT_GO_TO_PORTAL) ){
			click(ELEMENT_GO_TO_PORTAL);		
		}
		click(ELEMENT_SIGN_IN_LINK);*/
		Utils.pause(1000);
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		click(ELEMENT_SIGN_IN_BUTTON);
		if(verify)
			waitForElementNotPresent(ELEMENT_SIGN_IN_BUTTON);
	}

	/** Login to acme portal
	 * @author hela
	 * @param username
	 * @param password
	 */
	public void signInAcme(String username, String password) {
		info("--Sign in as " + username + "--");
		click(ELEMENT_LOGIN_ACME_LINK);
		Utils.pause(1000);
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		click(ELEMENT_ACME_SIGN_IN_BUTTON);
		waitForElementNotPresent(ELEMENT_ACME_SIGN_IN_BUTTON);
	}

	//Sign-out for eXoGTN
	public void signOut(){
		//mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
		//mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		info("Sign out");
		for(int repeat=0;; repeat ++){
			if (repeat > 1){
				mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
				break;
			}
			click(ELEMENT_ACCOUNT_NAME_LINK);
			if (waitForAndGetElement(ELEMENT_SIGN_OUT_LINK, 5000, 0) != null){
				info("Element " + ELEMENT_SIGN_OUT_LINK + "... is displayed");
				break;
			}
			info("Retry...[" + repeat + "]");
		}
		click(ELEMENT_SIGN_OUT_LINK);
		Utils.pause(1000);
		if ( ExpectedConditions.alertIsPresent() != null ){
			magAlert = new ManageAlert(driver);
			magAlert.acceptAlert();
		}
		//driver.get(baseUrl);
	}

	// Edit account info in Setting
	// Hover [user name] -> Setting
	public void editUserSetting(String firstName, String lastName, String email, String displayName, String currentPassword, String newPassword,
			String confirmNewPassword){
		button = new Button(driver);
		dialog = new Dialog(driver);
		info("-- Edit user in My Account --");

		if (firstName != null || lastName != null || email != null || displayName != null){
			if (firstName != null){
				type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
			}
			if (lastName != null){
				type(ELEMENT_INPUT_LASTNAME, lastName, true);
			}
			if (email != null){
				type(ELEMENT_INPUT_EMAIL, email, true);
			}
			if (displayName != null){
				type(ELEMENT_INPUT_DISPLAY_NAME, displayName, true);
			}
			button.save();	
			waitForMessage(MESSAGE_UPDATE_ACCOUNT);
			dialog.closeMessageDialog();
		}

		if (currentPassword != null || newPassword != null || confirmNewPassword != null){
			click(ELEMENT_CHANGE_PASSWORD_TAB);
			type(ELEMENT_INPUT_CURRENTPASSWORD, currentPassword, true);
			type(ELEMENT_INPUT_NEW_PASSWORD_MYACCOUNT, newPassword, true);
			type(ELEMENT_INPUT_NEW_CONFIRM_PASSWORD_MYACCOUNT, confirmNewPassword, true);
			click(ELEMENT_SAVE_CHANGE_PASS_BUTTON);
			waitForMessage(MESSAGE_UPDATE_PASSWORD);
			dialog.closeMessageDialog();
		}
		button.close();
	}

	// Add a new user account
	// setting -> user -> add users
	public void addNewUserAccount(String username, String password, String confirmPassword, String firstName, 
			String lastName, String displayName, String email, String userNameGiven, String language, boolean verify) {

		button = new Button(driver);
		dialog = new Dialog(driver);

		info("--Create a new user using \"New Staff\" portlet--");
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		type(ELEMENT_INPUT_CONFIRM_PASSWORD, confirmPassword, true);
		type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		type(ELEMENT_INPUT_LASTNAME, lastName, true);

		if (displayName != null){
			type(ELEMENT_INPUT_DISPLAY_NAME, displayName, true);
		}

		type(ELEMENT_INPUT_EMAIL, email, true);
		if (userNameGiven != null || language != null){
			click(ELEMENT_USER_PROFILE_TAB);
			waitForTextPresent("Given Name:");
			if (userNameGiven != null){
				type(ELEMENT_INPUT_USER_NAME_GIVEN, userNameGiven, true);	
			}
			if (language != null){
				select(ELEMENT_SELECT_USER_LANGUAGE, language);
			}
			click(ELEMENT_ACCOUNT_SETTING_TAB);
		}
		button.save();

		if (verify) {
			waitForMessage("You have registered a new account.");
			dialog.closeMessageDialog();
		}
		Utils.pause(1000);
	}

	//Add a new user account in public mode
	public void addNewUserAccountInPublicMode(String username, String password, String confirmPassword, String firstName,
			String lastName, String email, boolean verify){
		dialog = new Dialog(driver);
		info("-- Add new user account in public mode --");
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		type(ELEMENT_INPUT_CONFIRM_PASSWORD_PUBLIC_MODE, confirmPassword, true);
		type(ELEMENT_INPUT_FIRSTNAME, firstName, true);
		type(ELEMENT_INPUT_LASTNAME, lastName, true);
		type(ELEMENT_INPUT_EMAIL_PUBLIC_MODE, email, true);
		click(ELEMENT_SUBSCRIBE_BUTTON);
		if (verify) {
			if(waitForAndGetElement(MESSAGE_SUCCESSFULLY_REGISTERED_ACCOUNT_FRENCH,5000,0)==null)
				waitForMessage(MESSAGE_SUCCESSFULLY_REGISTERED_ACCOUNT);
			dialog.closeMessageDialog();
		}
	}

	public void changeLanguageForUser(String language){
		button = new Button(driver);
		info("Change language for user");
		//mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
		driver.navigate().refresh();
		Utils.pause(2000);
		mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
		if (waitForAndGetElement(ELEMENT_MY_SETTING, 5000, 0) == null){
			for(int repeat=0;; repeat ++){
				if (repeat > 1){
					mouseOverAndClick(ELEMENT_ACCOUNT_NAME_LINK);
					info("--Error mouse over and click: can't mouseover, need to use mouse over and click --");
					break;
				}
				mouseOver(ELEMENT_ACCOUNT_NAME_LINK, true);
			}
		}
		if (language == "French"){
			mouseOverAndClick(ELEMENT_CHANGE_LANGUAGE_LINK);
			waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP);
			click(By.linkText(language));
			button.apply();
			waitForElementNotPresent(ELEMENT_CHANGE_LANGUAGE_POPUP);
		}else {
			mouseOverAndClick(ELEMENT_CHANGE_LANGUAGE_LINK_FRENCH);
			waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP_FRENCH);
			click(By.linkText(language));
			click(button.ELEMENT_APPLY_FRENCH_BUTTON);
			waitForElementNotPresent(ELEMENT_CHANGE_LANGUAGE_POPUP_FRENCH);
		}
	}

	public void changeLanguageWithoutLogin(String language){
		button = new Button(driver);
		info("Change language for user without login");
		click(ELEMENT_CHANGE_LANGUAGE_LINK_ACME);
		if (language == "French"){
			if(waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP,5000,0)!=null){
				click(ELEMENT_FRENCH_LANGUAGE);
				button.apply();
				waitForAndGetElement(PRODUCTS_LABEL_FRENCH);
			}
			else{//(waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP_GERMAN,5000,0)!=null){
				click(ELEMENT_FRENCH_LANGUAGE_GER);
				click(button.ELEMENT_APPLY_GERMAN_BUTTON);
			}
			waitForAndGetElement(PRODUCTS_LABEL_FRENCH);
		} else if (language == "English"){
			if(waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP_FRENCH,5000,0)!=null){
				click(ELEMENT_ENGLISH_LANGUAGE);
				click(button.ELEMENT_APPLY_FRENCH_BUTTON);
			}else{//(waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP_GERMAN,5000,0)!=null){
				click(ELEMENT_ENGLISH_LANGUAGE_GER);
				click(button.ELEMENT_APPLY_GERMAN_BUTTON);
			}
			waitForAndGetElement(PRODUCTS_LABEL_ENGLISH);
		}
		else{
			if(waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP_FRENCH,5000,0)!=null){
				click(ELEMENT_GERMANY_LANGUAGE_FRENCH);
				click(button.ELEMENT_APPLY_FRENCH_BUTTON);
			}else{//(waitForAndGetElement(ELEMENT_CHANGE_LANGUAGE_POPUP_GERMAN,5000,0)!=null){
				click(ELEMENT_GERMANY_LANGUAGE_ENG);
				click(button.ELEMENT_APPLY_BUTTON);
			}
			waitForAndGetElement(PRODUCTS_LABEL_GERMAN);
		}
	}

	/** function edit information of user profile
	 * @author lientm
	 * @param pos
	 * @param first
	 * @param last
	 * @param email
	 */
	public void updateUserProfile(String pos, String first, String last, String email){
		navTool = new NavigationToolbar(driver);

		navTool.goToMyProfile();
		if (pos != null){
			mouseOverAndClick(ELEMENT_EDIT_POSITION);
			type(ELEMENT_POSITION_TEXTBOX_EDIT, pos, true);
			click(ELEMENT_EDIT_POSITION_SAVE_BUTTON);
		}
		if (first != null || last != null || email != null){
			click(ELEMENT_EDIT_BASIC_INFORMATION);
			if (first != null){
				type(ELEMENT_FIRST_NAME_TEXTBOX_EDIT, first, true);
			}
			if (last != null){
				type(ELEMENT_LAST_NAME_TEXTBOX_EDIT, last, true);
			}
			if (email != null){
				type(ELEMENT_EMAIL_TEXTBOX_EDIT, email, true);
			}
			click(ELEMENT_EDIT_BASIC_INFO_SAVE_BUTTON);
		}
	}

	/**
	 * Define a type of user 
	 * Root
	 * John Smith: administrator
	 * James Davis: author
	 * Jack Miller: developer
	 * Mary Williams: publisher 
	 */
	public enum userType {
		ROOT, ADMIN, AUTHOR, DEVELOPER, PUBLISHER;
	}

	/**
	 * @author vuna2
	 * @param user: type: Root, Admin, Author, Developer or Publisher
	 */
	public void userSignIn(userType user){
		if (isElementNotPresent(ELEMENT_INPUT_USERNAME)){
			signOut();
		}else{
			info("-- User.logIn: " + user);
		}
		switch (user) {
		case ROOT:
			signIn(USER_ROOT, PASS_ROOT);
			break;
		case ADMIN:
			signIn(DATA_USER1, DATA_PASS);
			break;	
		case AUTHOR:
			signIn(DATA_USER3, DATA_PASS);
			break;
		case DEVELOPER:
			signIn(DATA_USER4, DATA_PASS);
			break;
		case PUBLISHER:
			signIn(DATA_USER2, DATA_PASS);
			break;
		default:
			break;
		}	
	}
}
