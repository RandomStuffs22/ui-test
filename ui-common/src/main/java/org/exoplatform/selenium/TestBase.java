package org.exoplatform.selenium;

import static org.exoplatform.selenium.TestLogger.debug;
import static org.exoplatform.selenium.TestLogger.info;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class TestBase {

	public WebDriver driver;
	public WebDriver newDriver;
	protected String baseUrl;
	protected int DEFAULT_TIMEOUT = 30000; //milliseconds = 30 seconds
	protected int WAIT_INTERVAL = 1000; //milliseconds  
	public int loopCount = 0;	
	protected boolean ieFlag;	 
	protected boolean chromeFlag;
	/**
	 * 4.0 : Version 4.0.x.
	 * 4.1 : Version 4.1.x.
	 * 4.2 : Version 4.2.x.
	 * ect...
	 */
	protected String plfVersion = "";
	public final int ACTION_REPEAT = 5;
	public static boolean firstTimeLogin = false;
	public Actions action;

	//public final By ELEMENT_MENU_ADD_PAGE_LINK = By.linkText("Add Page");
	//public final By ELEMENT_MENU_EDIT_LINK = By.xpath("//i[@class='uiIconPLF24x24Edit']");
	//public final By ELEMENT_MENU_PAGE_LINK = By.linkText("Page");
	//public final String AJAX_LOADING_MASK = "//div[@id='AjaxLoadingMask']";
	public final String DEFAULT_BASEURL="http://localhost:8080/portal";

	/*======= Welcome Screen (Term and Conditions) =====*/
	public final By ELEMENT_FIRSTNAME_ACCOUNT = By.name("firstNameAccount");
	public final By ELEMENT_LASTNAME_ACCOUNT = By.name("lastNameAccount");
	public final By ELEMENT_EMAIL_ACCOUNT = By.name("emailAccount");
	public final By ELEMENT_CONFIRM_PASS_ACCOUNT = By.name("confirmUserPasswordAccount");
	public final By ELEMENT_ROOT_PASS_ACCOUNT = By.name("adminPassword");
	public final By ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT = By.name("confirmAdminPassword");
	public final By ELEMENT_AGREEMENT_CHECKBOX = By.xpath("//*[@id = 'agreement']");
	public final By ELEMENT_INPUT_USERNAME = By.name("username"); 
	public final By ELEMENT_CONTINUE_BUTTON = By.xpath("//button[text()='Continue' and @class='btn active']");
	public final By ELEMENT_START_BUTTON = By.xpath("//button[text()='Start']");
	public final By ELEMENT_SUBMIT_BUTTON = By.xpath("//*[text()='Submit']");
	public final By ELEMENT_INPUT_PASSWORD = By.name("password");
	By ELEMENT_ACCOUNT_NAME_LINK = By.xpath("//*[@id='UIUserPlatformToolBarPortlet']/a");
	By ELEMENT_PLF_INFORMATION = By.id("platformInfoDiv");

	public final String ELEMENT_TERM_CONDITION_BOX = "//div[@class='header' and text()='Terms and Conditions Agreement']/..";
	public final By ELEMENT_CONTINUE_BUTTON_DISABLE = By.xpath("//button[text()='Continue' and @class='btn inactive']");
	public final By ELEMENT_TERM_CONDITION_CONTENT = By.xpath("//div[@id='AccountSetup' and @class='content']");

	public final By ELEMENT_ACCOUNT_SETUP = By.xpath("//div[@class='header' and text()='Account Setup']");
	public final By ELEMENT_USER_ADMIN = By.id("adminFirstName");
	public final By ELEMENT_SKIP_BUTTON = By.xpath("//button[text()='Skip']");
	public final By ELEMENT_YOUR_ACCOUNT_LABEL = By.xpath("//h5[contains(text(), 'Create your account')]");
	public final By ELEMENT_ADMIN_PASS_LABEL = By.xpath("//h5[contains(text(), 'Admin Password')]");
	public final By ELEMENT_ACCOUNT_ERROR = By.xpath("//*[@class='accountSetupError']");

	/*======== End of Term and conditions =====*/	

	public void initSeleniumTestWithOutTermAndCondition(Object... opParams){
		String browser = System.getProperty("browser");
		if("chrome".equals(browser)){
			driver = new ChromeDriver();
			chromeFlag = true;
		} else if ("iexplorer".equals(browser)){
			driver = new InternetExplorerDriver();
			ieFlag = true;
		} else {
			FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("plugins.hide_infobar_for_missing_plugin", true);
            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			driver = new FirefoxDriver(capabilities);
		}
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
		action = new Actions(driver);
	}

	public void initSeleniumTest(Object... opParams){
		initSeleniumTestWithOutTermAndCondition();
		info("Term and conditions");
		termsAndConditions(opParams);
		info("End of term and conditions");

		if(!firstTimeLogin){
			info("This is not the first time login");
			checkPLFVersion();
		}
		else{
			info("This is the first time login");
			driver.manage().window().maximize();
			driver.navigate().refresh();
			Utils.pause(2000);
			ManageAccount acc = new ManageAccount(driver,this.plfVersion);
			acc.signOut();
			firstTimeLogin=false;
			checkPLFVersion();
		}
	}



	/**
	 * Check term and conditions
	 * 
	 */
	public void termsAndConditions(Object... opParams){
		//By ELEMENT_FIRSTNAME_ACCOUNT = By.name("firstNameAccount");
		//By ELEMENT_LASTNAME_ACCOUNT = By.name("lastNameAccount");
		//By ELEMENT_EMAIL_ACCOUNT = By.name("emailAccount");
		//By ELEMENT_CONFIRM_PASS_ACCOUNT = By.name("confirmUserPasswordAccount");
		//By ELEMENT_ROOT_PASS_ACCOUNT = By.name("adminPassword");
		//By ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT = By.name("confirmAdminPassword");
		//By ELEMENT_AGREEMENT_CHECKBOX = By.xpath("//*[@id = 'agreement']");
		//By ELEMENT_INPUT_USERNAME = By.name("username"); 
		//By ELEMENT_CONTINUE_BUTTON = By.xpath("//button[text()='Continue']");
		//By ELEMENT_START_BUTTON = By.xpath("//button[text()='Start']");
		//By ELEMENT_SUBMIT_BUTTON = By.xpath("//*[text()='Submit']");
		//By ELEMENT_INPUT_PASSWORD = By.name("password");
		//By ELEMENT_ACCOUNT_NAME_LINK = By.xpath("//*[@id='UIUserPlatformToolBarPortlet']/a");
		Boolean isCreateAccount = (Boolean)(opParams.length>0 ? opParams[0]:true);
		driver.get(baseUrl);
		info("Agreement page");
		if (waitForAndGetElement(ELEMENT_AGREEMENT_CHECKBOX, 5000, 0, 2) != null) {
			info("-- Checking the terms and conditions agreement... --");
			click(ELEMENT_AGREEMENT_CHECKBOX, 2);
			click(ELEMENT_CONTINUE_BUTTON);
			waitForTextNotPresent("terms and conditions agreement");

			info("-- Creating an Admin account: FQA... --");
			if(isCreateAccount==true)
				accountSetup();
			firstTimeLogin = true;
			info("-- Administrator account (FQA) has been created successfully... --");
		}else if (waitForAndGetElement(ELEMENT_ROOT_PASS_ACCOUNT, 5000, 0, 2) != null){
			info("-- Creating an Admin account: FQA... --");
			//type(ELEMENT_INPUT_USERNAME, "fqa", true);
			//type(ELEMENT_FIRSTNAME_ACCOUNT, "FQA", true);
			//type(ELEMENT_LASTNAME_ACCOUNT, "VN", true);
			//type(ELEMENT_EMAIL_ACCOUNT, "fqa@exoplatform.com", true);	
			//type(ELEMENT_INPUT_PASSWORD, "gtngtn", true);
			//type(ELEMENT_CONFIRM_PASS_ACCOUNT, "gtngtn", true);	
			//type(ELEMENT_ROOT_PASS_ACCOUNT, "gtngtn", true);
			//type(ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT, "gtngtn", true);
			//click(ELEMENT_SUBMIT_BUTTON);
			//waitForTextNotPresent("Create your account");
			//click(ELEMENT_START_BUTTON);
			//waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK);
			accountSetup();
			firstTimeLogin = true;
			info("-- Administrator account (FQA) has been created successfully... --");
		} 
		Utils.pause(5000);     
	}

	/**
	 * Verify plf version
	 */
	public void checkPLFVersion(){
		waitForTextNotPresent("terms and conditions agreement");
		try{
			info("Verify platform version");
			String des = driver.findElement(ELEMENT_PLF_INFORMATION).getText();
			if(des.contains("v4.0")){
				this.plfVersion = "4.0";
				info("Platform version 4.0.x");
			}
			else if(des.contains("v4.1")){
				this.plfVersion="4.1";
				info("Platform version 4.1.x");
			}
		}catch(Exception e){
			info("Unknown platform version. Set to default version 4.0.x.");
			this.plfVersion="4.0";
		}

	}

	public void accountSetupWithoutGreeting(){
		type(ELEMENT_INPUT_USERNAME, "fqa", true);
		type(ELEMENT_FIRSTNAME_ACCOUNT, "FQA", true);
		type(ELEMENT_LASTNAME_ACCOUNT, "VN", true);
		type(ELEMENT_EMAIL_ACCOUNT, "fqa@exoplatform.com", true);	
		type(ELEMENT_INPUT_PASSWORD, "gtngtn", true);
		type(ELEMENT_CONFIRM_PASS_ACCOUNT, "gtngtn", true);	
		type(ELEMENT_ROOT_PASS_ACCOUNT, "gtngtn", true);
		type(ELEMENT_ROOT_CONFIRM_PASS_ACCOUNT, "gtngtn", true);
		click(ELEMENT_SUBMIT_BUTTON);
		waitForTextNotPresent("Create your account");
	}

	public void accountSetup(){
		accountSetupWithoutGreeting();
		click(ELEMENT_START_BUTTON);
		waitForAndGetElement(ELEMENT_ACCOUNT_NAME_LINK);
	}

	public WebElement getElement(Object locator, Object... opParams) {
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebDriver wDriver = (WebDriver) (opParams.length > 0 ? opParams[0]: driver);	
		WebElement elem = null;
		try {
			elem = wDriver.findElement(by);
		} catch (NoSuchElementException e) {

		}
		return elem;
	}

	//return element only in case the element is displayed.
	public WebElement getDisplayedElement(Object locator, Object... opParams) {
		By by = locator instanceof By ? (By)locator : By.xpath(locator.toString());
		WebDriver wDriver = (WebDriver) (opParams.length > 0 ? opParams[0]: driver);	
		WebElement e = null;
		try {
			if(by != null)
				e = wDriver.findElement(by);
			if (e != null){
				if (isDisplay(by)) return e;
			}
		} catch (NoSuchElementException ex) {
			//			info("NoSuchElementException");
		}catch(StaleElementReferenceException ex)
		{
			checkCycling(ex, 10);
			Utils.pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		}
		finally{
			loopCount=0;
		}
		return null;
	}

	public boolean isElementPresent(Object locator) {
		return getElement(locator) != null;
	}

	public boolean isElementNotPresent(Object locator) {
		return !isElementPresent(locator);
	}

	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 0: No Assert
	 * 1: Assert
	 */
	public WebElement waitForAndGetElement(Object locator, Object... opParams) {
		WebElement elem = null;
		int timeout = (Integer) (opParams.length>0 ? opParams[0] : DEFAULT_TIMEOUT);
		int isAssert = (Integer) (opParams.length > 1 ? opParams[1]: 1);
		int notDisplayE = (Integer) (opParams.length > 2 ? opParams[2]: 0);
		WebDriver wDriver = (WebDriver) (opParams.length > 3 ? opParams[3]: driver);	
		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2){
				elem = getElement(locator,wDriver);
				//elem = getDisplayedElement(locator);
			}else{
				elem = getDisplayedElement(locator,wDriver);
			}
			if (null != elem) return elem;
			Utils.pause(WAIT_INTERVAL);
		}
		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + "ms waiting for element present: " + locator);
		info("cannot find element after " + timeout/1000 + "s.");
		return null;
	}


	/*
	 * @opPram[0]: timeout
	 * @opPram[1]: 0,1
	 * 0: No Assert
	 * 1: Assert
	 */
	public WebElement waitForElementNotPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1]: 1;
		int notDisplayE = opParams.length > 2 ? opParams[2]: 0;

		for (int tick = 0; tick < timeout/WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2){
				elem = getElement(locator);
				//elem = getDisplayedElement(locator);
			}else{
				elem = getDisplayedElement(locator);
			}
			if (null == elem) return null;
			Utils.pause(WAIT_INTERVAL);
		}

		if (isAssert == 1)
			assert false: ("Timeout after " + timeout + "ms waiting for element not present: " + locator);
		info("Element doesn't disappear after " + timeout/1000 + "s.");
		return elem;
	}

	public boolean isTextPresent(String text, int...opts) {
		int display = opts.length > 0 ? opts[0] : 1;
		Utils.pause(500);
		String allVisibleTexts = getText(By.xpath("//body"),display);
		return allVisibleTexts.contains(text);
	}

	public String getText(Object locator,int...opts) {
		WebElement element = null;
		int display = opts.length > 0 ? opts[0] : 1;
		try {
			element = waitForAndGetElement(locator,DEFAULT_TIMEOUT,1,display);
			return element.getText();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			return getText(locator);
		} finally {
			loopCount = 0;
		}
	}

	public List<WebElement> getElements(String xpath) {
		try {
			return driver.findElements(By.xpath(xpath));
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			Utils.pause(1000);
			return getElements(xpath);
		} finally {
			loopCount = 0;
		}
	}

	public boolean isTextNotPresent(String text) {
		return !isTextPresent(text);
	}

	public void dragAndDropToObject(Object sourceLocator, Object targetLocator) {
		info("--Drag and drop to object--");
		Actions action = new Actions(driver);
		try {
			WebElement source = waitForAndGetElement(sourceLocator);
			WebElement target = waitForAndGetElement(targetLocator);

			action.dragAndDrop(source, target).build().perform();

		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			dragAndDropToObject(sourceLocator, targetLocator);
		}catch (UnhandledAlertException e) {
			/*checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			dragAndDropToObject(sourceLocator, targetLocator);*/
			//acceptAlert();
			try {
				Alert alert = driver.switchTo().alert();
				alert.accept();
				switchToParentWindow();
			} catch (NoAlertPresentException eNoAlert) {
			}
		}

		finally {
			loopCount = 0;
		}
		Utils.pause(1000);
	}

	public void click(Object locator, Object... opParams) {
		//Actions actions = new Actions(driver);
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0]: 0);		
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplay);
			if(element.isEnabled())
				actions.click(element).perform();
			else {
				debug("Element is not enabled");
				click(locator, notDisplay);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} finally {
			loopCount = 0;
		}
		Utils.pause(500);
	}

	public void clearCache(){
		Actions actionObject = new Actions(driver);
		try{
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		} catch(WebDriverException e){	
			debug("Retrying clear cache...");
			actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
		}
	}

	//Use this function to verify if a check-box is checked (using when creating a portal/publicMode)
	public void check(Object locator, int... opParams) {
		int notDisplayE = opParams.length > 0 ? opParams[0]: 0;
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplayE);

			if (!element.isSelected()) {
				actions.click(element).perform();
			} else {
				info("Element " + locator + " is already checked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			check(locator, opParams);
		} finally {
			loopCount = 0;
		}
	}

	public String getValue(Object locator) {
		try {
			return waitForAndGetElement(locator).getAttribute("value");
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);			
			Utils.pause(WAIT_INTERVAL);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	public void mouseOver(Object locator, boolean safeToSERE, Object...opParams) {
		WebElement element;
		Actions actions = new Actions(driver);
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0]: 0);
		try {
			if (safeToSERE) {
				for (int i = 1; i < ACTION_REPEAT; i++){
					element = waitForAndGetElement(locator, 5000, 0, notDisplay);
					if (element == null){
						Utils.pause(WAIT_INTERVAL);
					} else {
						actions.moveToElement(element).perform();
						break;
					}
				}
			} else {
				element = waitForAndGetElement(locator);
				actions.moveToElement(element).perform();
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			mouseOver(locator, safeToSERE);
		} finally {
			loopCount = 0;
		}
	}

	public void mouseOverAndClick(Object locator) {
		WebElement element;
		Actions actions = new Actions(driver);
		if (ieFlag) {
			element = getDisplayedElement(locator);
		} else {
			element = waitForAndGetElement(locator);
		}
		actions.moveToElement(element).click(element).build().perform();
	}

	public void waitForTextPresent(String text, int...opts) {
		int waitTime = opts.length > 0 ? opts[0] : DEFAULT_TIMEOUT;
		int display = opts.length > 1 ? opts[1] : 1;
		for (int second = 0;; second++) {
			if (second >= waitTime/WAIT_INTERVAL) {
				Assert.fail("Timeout at waitForTextPresent: " + text);
			}
			if (isTextPresent(text,display)) {
				break;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void waitForTextNotPresent(String text,int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		for (int second = 0;; second++) {
			if (second >= waitTime/WAIT_INTERVAL) {
				Assert.fail("Timeout at waitForTextNotPresent: " + text);
			}
			if (isTextNotPresent(text)) {
				break;
			}
			Utils.pause(WAIT_INTERVAL);
		}
	}

	public void waitForMessage(String message,int...wait) {
		int waitTime = wait.length > 0 ? wait[0] : DEFAULT_TIMEOUT;
		//info("--Verify message: " + message);
		Utils.pause(500);
		//waitForTextPresent(message, waitTime);
		waitForAndGetElement("//*[contains(text(),'"+message+"')]",waitTime);

	}

	public void type(Object locator, String value, boolean validate, Object...opParams) {	
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0]: 0);
		try {
			for (int loop = 1;; loop++) {
				if (loop >= ACTION_REPEAT) {
					Assert.fail("Timeout at type: " + value + " into " + locator);
				}
				WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplay);		
				if (element != null){
					if (validate) element.clear();
					element.click();
					element.sendKeys(value);
					if (!validate || value.equals(getValue(locator))) {
						break;
					}
				}
				info("Repeat action..." + loop + "time(s)");
				Utils.pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			type(locator, value, validate, opParams);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			type(locator, value, validate, opParams);
		} finally {
			loopCount = 0;
		}
	}

	// Select option from combo box
	public void select(Object locator, String option) {
		try {
			for (int second = 0;; second++) {
				if (second >= DEFAULT_TIMEOUT/WAIT_INTERVAL) {
					Assert.fail("Timeout at select: " + option + " into " + locator);
				}
				Select select = new Select(waitForAndGetElement(locator));
				select.selectByVisibleText(option);
				if (option.equals(select.getFirstSelectedOption().getText())) {
					break;
				}
				Utils.pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			select(locator, option);
		} finally {
			loopCount = 0;
		}
	}

	//un-check a checked-box
	public void uncheck(Object locator, int... opParams) {
		int notDisplayE = opParams.length > 0 ? opParams[0]: 0;
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, notDisplayE);

			if (element.isSelected()) {
				actions.click(element).perform();
			} else {
				info("Element " + locator + " is already unchecked.");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			Utils.pause(1000);
			uncheck(locator, opParams);
		} finally {
			loopCount = 0;
		}
	}
	public void rightClickOnElement(Object locator, int...opParams) {
		int display = opParams.length > 0 ? opParams[0]: 0;
		Actions actions = new Actions(driver);
		Utils.pause(500);
		try {
			WebElement element = waitForAndGetElement(locator,DEFAULT_TIMEOUT,1,display);
			actions.contextClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			rightClickOnElement(locator);
		} catch (ElementNotVisibleException e) {
			checkCycling(e, DEFAULT_TIMEOUT/WAIT_INTERVAL);
			Utils.pause(WAIT_INTERVAL);
			click(locator);
		} finally {
			loopCount = 0;
		}
	}

	//doubleClickOnElement
	public void doubleClickOnElement(Object locator) {
		Actions actions = new Actions(driver);
		try {
			WebElement element = waitForAndGetElement(locator);
			actions.doubleClick(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			Utils.pause(1000);
			doubleClickOnElement(locator);
		} finally {
			loopCount = 0;
		}
	}

	public void checkCycling(Exception e, int loopCountAllowed) {
		info("Exception:" + e.getClass().getName());
		if (loopCount > loopCountAllowed) {
			Assert.fail("Cycled: " + e.getMessage());
		}
		info("Repeat... " + loopCount + "time(s)");
		loopCount++;
	}

	//function to switch to parent windows
	public void switchToParentWindow (){
		try
		{
			Set<String> availableWindows = driver.getWindowHandles();
			String WindowIdParent= null;
			int counter = 1;
			for (String windowId : availableWindows) {
				if (counter == 1){
					WindowIdParent = windowId;
				}
				counter++;
			}
			driver.switchTo().window(WindowIdParent);
			Utils.pause(1000);
		}
		catch (WebDriverException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isDisplay(Object locator) {
		boolean bool = false;
		WebElement e = getElement(locator);
		try{
			if (e!=null)
				bool = e.isDisplayed();
		}catch(StaleElementReferenceException ex)
		{
			checkCycling(ex, 10);
			Utils.pause(WAIT_INTERVAL);
			isDisplay(locator);
		}
		finally{
			loopCount=0;
		}
		return bool;
	}

	//////
	/** function: set driver to auto save file to TestData/TestOutput
	 * @author lientm
	 */
	public void getDriverAutoSave(){
		String pathFile = System.getProperty("user.dir") + "/src/main/resources/TestData/TestOutput";

		FirefoxProfile fp = new FirefoxProfile();	
		fp.setPreference("browser.download.folderList", 2);
		info("Save file to " + pathFile);
		fp.setPreference("browser.download.dir", pathFile);
		//		fp.setPreference("browser.helperApps.neverAsk.saveToDisk", 
		//				"application/x-zip;application/x-zip-compressed;application/x-winzip;application/zip;application/bzip2;" +
		//				"gzip/document;multipart/x-zip;application/x-gunzip;application/x-gzip;application/x-gzip-compressed;" +
		//				"application/x-bzip;application/gzipped;application/gzip-compressed;application/gzip;application/octet-stream");

		fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-xpinstall;" +
				"application/x-zip;application/x-zip-compressed;application/x-winzip;application/zip;" +
				"gzip/document;multipart/x-zip;application/x-gunzip;application/x-gzip;application/x-gzip-compressed;" +
				"application/x-bzip;application/gzipped;application/gzip-compressed;application/gzip" +
				"application/octet-stream" +
				";application/pdf;application/msword;text/plain;" +
				"application/octet;text/calendar;text/x-vcalendar;text/Calendar;" +
				"text/x-vCalendar;image/jpeg;image/jpg;image/jp_;application/jpg;" +
				"application/x-jpg;image/pjpeg;image/pipeg;image/vnd.swiftview-jpeg;image/x-xbitmap;image/png;application/xml;text/xml;text/icalendar;");

		fp.setPreference("browser.helperApps.alwaysAsk.force", false);
		driver = new FirefoxDriver(fp);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
		action = new Actions(driver);
		termsAndConditions();
		checkPLFVersion();
	}

	/**function set driver to auto open new window when click link
	 * @author lientm
	 */
	public void getDriverAutoOpenWindow(){
		FirefoxProfile fp = new FirefoxProfile();		
		fp.setPreference("browser.link.open_newwindow.restriction", 2);
		driver = new FirefoxDriver(fp);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
		action = new Actions(driver);
		termsAndConditions();
	}

	/**
	 * function: check a file existed in folder
	 * @author lientm
	 * @param file: file name (eg: export.zip)
	 * @return: true -> file exist
	 * false-> file is not exist
	 */
	public boolean checkFileExisted(String file){
		String pathFile = System.getProperty("user.dir") + "/src/main/resources/TestData/" + file;
		boolean found = false;

		if (new File(pathFile).isFile()){
			found = true;
		}
		info("File exists: " + found);
		return found;
	}

	/**
	 * function delete file in folder test output
	 * @author lientm
	 * @param file: file name
	 */
	public void deleteFile(String file){
		String pathFile = System.getProperty("user.dir") + "/src/main/resources/TestData/" + file;
		File Files = new File(pathFile);
		if(checkFileExisted(file)){
			Files.setWritable(true);
			Files.delete();
		}
		if (checkFileExisted(file) == false){
			info("Delete file successfully");
		}else info("Have error when delete file");
	}

	/**
	 * @author lientm
	 * @param fileName
	 */
	public void cutPasteFileFromOutputToTestData(String fileName){
		String source = System.getProperty("user.dir") + "/src/main/resources/TestData/TestOutput/" + fileName;
		//directory where file will be copied
		String target = System.getProperty("user.dir") + "/src/main/resources/TestData/";

		//name of source file
		File sourceFile = new File(source);
		String name = sourceFile.getName();

		File targetFile = new File(target+name);

		//copy file from one location to other
		try {
			FileUtils.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//delete file in TestOutput
		deleteFile("TestOutput/" + fileName);
	}

	public enum Language{
		en, fr, vi, lo;
	}

	public void getDriverSetLanguage(Language language){
		String locale = language.toString();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", locale);
		driver = new FirefoxDriver(profile);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
		action = new Actions(driver);
		termsAndConditions();
	}

	/**
	 * function get current Date of system follow a format
	 * @author lientm
	 * @param fomat
	 * @return
	 */
	public String getCurrentDate(String fomat){
		DateFormat dateFormat = new SimpleDateFormat(fomat);
		Date date = new Date(); 
		return (dateFormat.format(date));
	}

	//Get current date time
	//	public String getCurrentDateTime(){
	//		//MM/dd/yyyy HH:mm:ss
	//		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	//		Date date = new Date();
	//		return (dateFormat.format(date));
	//	}

	//Add 1 minute to current date time
	public String addMinuteToCurrentDateTime(int min){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, min);
		return (dateFormat.format(cal.getTime()));	
	}

	/** Get date in format "dd"
	 * @author thuntn
	 * @param gap: distance from current date
	 * @return date in format "dd"
	 */
	public String getDate(int gap, String format){
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, gap);
		return (dateFormat.format(cal.getTime()));	
	}

	/**
	 * Get minute in format "mm" from current date
	 * @author havtt
	 * @return minute
	 * 
	 */
	public int getMinute(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		int minute = cal.get(Calendar.MINUTE);
		return (minute); 
	}

	/**
	 * Change attribute "display" of HTML tag from "none" to "block" 
	 * @author havtt
	 */
	public void changeDisplayAttributeHTML(String locator){
		WebElement element = waitForAndGetElement(locator, DEFAULT_TIMEOUT, 1, 2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';",element);	
	}



	public void setPreferenceRunTime(){
		FirefoxProfile fp = new FirefoxProfile();

		fp.setPreference("dom.max_script_run_time", 30);
	}

	/**
	 * Mouse hover by Javascript
	 * @author havtt
	 * @date 06-Nov-2013
	 * @param 
	 */
	public void mouseHoverByJavaScript(WebElement targetElement)
	{
		String argu1 = "var evObj = document.createEvent('MouseEvents');";
		String argu2 = "evObj.initMouseEvent('mouseover',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);";
		String argu3 =  "arguments[0].dispatchEvent(evObj);";
		String javascript = argu1 + argu2 + argu3;
		((JavascriptExecutor)driver).executeScript(javascript, targetElement);
	}

	/** change lanugage of browser
	 * @author phuongdt
	 * @param language
	 * English: "en"
	 * French: "fr"
	 */
	public void initFFBrowserWithSetLanguageBrowser(String language){
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages",language);   
		driver = new FirefoxDriver(profile);
		baseUrl = System.getProperty("baseUrl");
		if (baseUrl==null) baseUrl = DEFAULT_BASEURL;
		action = new Actions(driver);
	}

	/**
	 * get random string
	 * @author phuongdt
	 * @return
	 */
	public String getRandomString(){
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * Copy and paste a string from one locator to other
	 * 
	 * @param destination
	 * @param target
	 */
	public void copyPasteString(By origin, By target, String value) {
		WebElement element1 = driver.findElement(origin);
		WebElement element2 = driver.findElement(target);

		info("Type into the first locator");
		element1.clear();
		element1.click();
		element1.sendKeys(value);

		info("Copy from the first locator");
		element1.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element1.sendKeys(Keys.chord(Keys.CONTROL, "c"));

		info("Paste to the second locator");
		element2.click();
		element2.sendKeys(Keys.chord(Keys.CONTROL, "v"));
	}
	
	/**
	 * Get minute in format "HH" from current date
	 * @author chinhdtt
	 * @return hours
	 * 
	 */
	public int getHours(){
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		int minute = cal.get(Calendar.HOUR);
		return (minute); 
	}
}