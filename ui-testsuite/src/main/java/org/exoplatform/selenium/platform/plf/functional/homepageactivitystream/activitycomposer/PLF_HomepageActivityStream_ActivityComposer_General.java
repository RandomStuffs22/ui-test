package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activitycomposer;



import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.Activity;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;



/**
 * @author chinhdt
 *
 */
public class PLF_HomepageActivityStream_ActivityComposer_General extends Activity{

	ManageAccount acc;
	ManageAlert alert; 
	HomePageActivity home; 

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		acc = new ManageAccount(driver);
		button = new Button(driver);
		alert = new ManageAlert(driver); 
		home = new HomePageActivity(driver); 
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	* Case ID:77777.
	* Test Case Name: Can't share an empty message.
	* Created by chinhdt at 2013/12/16 09:28:17
	*/
	@Test
	public  void test01_CantShareAnEmptyMessage() {
		info("Test 1: Can't share an empty message"); 

		/*Declare variables*/ 
		String text1 = "       ";
		String text2 = "Test case1"; 

		/* Step 1: Don't type in shared activity box */
		//- Connect to Intranet
		//- Click in the composer input box, don't type
		info("----Add text into activity text box-----");
		WebElement inputText = waitForAndGetElement(home.ELEMENT_ACTIVITY_TEXTBOX);
		WebElement shareButton = waitForAndGetElement(ELEMENT_SHARE_BUTTON);
		
		Assert.assertEquals(shareButton.isEnabled(), false);
		
		/*Step 2: Input space characters into shared activity box */
		//- Input space characters into shared activity box
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+text1+"';", inputText);
		Assert.assertEquals(shareButton.isEnabled(), false);
		

		/* Step 3: Input letters into shared activity box */
		// - in put "a" character
		type(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL, "   ", false);
		type(home.ELEMENT_ACTIVITY_TEXTBOX, text2, false);
		Utils.pause(1000);
		Assert.assertEquals(shareButton.isEnabled(), true);
		info("-- Verify Share button --");
 	}



	/**
	* Case ID:77797.
	* Test Case Name: Remove the default value "What are you working on?".
	* Created by chinhdt at 2013/12/16 09:28:17
	*/
	@Test
	public  void test02_RemoveTheDefaultValueWhatAreYouWorkingOn() {
		info("Test 2: Remove the default value What are you working on?"); 

		/*Declare variables*/ 
		String text = "Test case2"; 
		String workingLabelText = "What are you working on?";
		String lighterColor = "rgba(153, 153, 153, 1)";

		/* Step 1: Connect to Intranet */
		//- Connect to Intranet
		info("----Add text into activity text box-----");
		WebElement inputText = waitForAndGetElement(home.ELEMENT_ACTIVITY_TEXTBOX);
		WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL);
	
		//Check color
		info("--Check content and color of default string--");
		Assert.assertEquals(workingLabel.getText(), workingLabelText);
		Assert.assertEquals(inputText.getCssValue("color"), lighterColor);

		/*Step 2: Input data in Shared Activity box */
		//((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '';", workingLabel);
		//((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+text+"';", inputText);
		
		
		type(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL, " ", false);
		type(home.ELEMENT_ACTIVITY_TEXTBOX, text, false);
		
		Utils.pause(1000);
		Assert.assertEquals(workingLabel.isDisplayed(), false);
		
		info("Check string");
 	}



	/**
	* Case ID:78038.
	* Test Case Name: URL is able to be detected.
	* Created by chinhdt at 2013/12/16 09:28:17
	*/
	@Test
	public  void test03_URLIsAbleToBeDetected() {
		info("Test 3: URL is able to be detected"); 

		/*Declare variables*/ 
		String text2 = "http://www.yahoo.com a";

		/* Step: Detect url */
		//- - Connect to Intranet
		//- Enter an URL in the Shared activity box
		info("----Add link into activity text box-----");
		type(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL," ",false);
		type(home.ELEMENT_ACTIVITY_TEXTBOX,text2,false);
		
		waitForTextPresent("Loading...");
		waitForTextNotPresent("Loading", 100000);
		waitForAndGetElement(ELEMENT_SHARE_DISPLAY);
		waitForAndGetElement(ELEMENT_URL_SHARE); 
		waitForAndGetElement(ELEMENT_PICTURE_SHARE); 
		waitForAndGetElement(ELEMENT_TITLE_SHARE); 	
		
 	}



}