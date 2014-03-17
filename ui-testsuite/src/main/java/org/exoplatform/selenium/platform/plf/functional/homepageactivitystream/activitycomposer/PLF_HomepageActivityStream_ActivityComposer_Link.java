package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activitycomposer;



import static org.exoplatform.selenium.TestLogger.info;
import junit.framework.Assert;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.Activity;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

/**
 * @author chinhdt
 *
 */

public class PLF_HomepageActivityStream_ActivityComposer_Link extends Activity{

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
	* Case ID:77775.
	* Test Case Name: Attach a link.
	* Created by chinhdt at 2013/12/16 09:28:47
	*/
	@Test
	public  void test01_AttachALink() {
		info("Test 1: Attach a link"); 

		/*Declare variables*/ 
		String link = "http://www.yahoo.com"; 
		/* Step: Attach a link */
		//-  Connect to Intranet
		//- From "Activity Composer" box, click on "Link"
		//- Enter a valid URL (e.g  http://www.yahoo.com)
		//- Click on the button [Attach]
		info("----Click on Link----");
		waitForAndGetElement(ELEMENT_LINK);
		click(ELEMENT_LINK);
		info("----Input link into link box-----");
		waitForAndGetElement(ELEMENT_INPUT_LINK_BOX);
		type(ELEMENT_INPUT_LINK_BOX, link, true);
		waitForAndGetElement(ELEMENT_ATTACH_BUTTON);
		click(ELEMENT_ATTACH_BUTTON);
		waitForTextPresent("Loading...");
		waitForTextNotPresent("Loading", 100000);
		waitForAndGetElement(ELEMENT_SHARE_DISPLAY);
		waitForAndGetElement(ELEMENT_URL_SHARE); 
		waitForAndGetElement(ELEMENT_PICTURE_SHARE); 
		waitForAndGetElement(ELEMENT_TITLE_SHARE); 	
 	}



	/**
	* Case ID:77790.
	* Test Case Name: Display the field to add a link.
	* Created by chinhdt at 2013/12/16 09:28:47
	*/
	@Test
	public  void test02_DisplayTheFieldToAddALink() {
		info("Test 2: Display the field to add a link"); 

		/* Step: Show a field to input a link */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [Link] button
		info("----Click on Link----");
		waitForAndGetElement(ELEMENT_LINK).click();
		info("Link box is shown"); 
		waitForAndGetElement(ELEMENT_INPUT_LINK_BOX);		
		WebElement shareButton = waitForAndGetElement(ELEMENT_SHARE_BUTTON);
		WebElement attachButton = waitForAndGetElement(ELEMENT_ATTACH_BUTTON); 
		
		Assert.assertEquals(shareButton.isEnabled(), false);
		
		Assert.assertEquals(attachButton.isDisplayed(), true);
 	}



	/**
	* Case ID:77796.
	* Test Case Name: Remove a link to share.
	* Created by chinhdt at 2013/12/16 09:28:47
	*/
	@Test
	public  void test03_RemoveALinkToShare() {
		info("Test 3: Remove a link to share"); 

		/*Declare variables*/ 
		String link = "http://www.yahoo.com"; 

		/* Step1: Open [Select File] popup */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [Link] button
		info("----Click on Link----");
		click(ELEMENT_LINK);
		waitForAndGetElement(ELEMENT_INPUT_LINK_BOX);
		/* Step2: Attach a link*/
		// - Enter a valid URL (e.g http://www.yahoo.com)
		//- Click on the button [Attach]
		info("----Input link into link box-----");
		type(ELEMENT_INPUT_LINK_BOX, link, true);
		waitForAndGetElement(ELEMENT_ATTACH_BUTTON);
		click(ELEMENT_ATTACH_BUTTON);
		waitForTextPresent("Loading...");
		waitForTextNotPresent("Loading", 100000);
		waitForAndGetElement(ELEMENT_SHARE_DISPLAY);
		waitForAndGetElement(ELEMENT_PICTURE_SHARE); 
		waitForAndGetElement(ELEMENT_TITLE_SHARE); 
		waitForAndGetElement(ELEMENT_CROSS_BUTTON); 
		/* Step3: Cancel attaching file*/
		// - Click on the cross [x] icon
		Utils.pause(1000);
		click(ELEMENT_CROSS_BUTTON);

		info("Remove attach"); 		
 	}



	/**
	* Case ID:77803.
	* Test Case Name: Select an image from the link to share.
	* Created by chinhdt at 2013/12/16 09:28:47
	*/
	@Test
	public  void test04_SelectAnImageFromTheLinkToShare() {
		info("Test 4: Select an image from the link to share"); 

		/*Declare variables*/ 
		String link = "http://yahoo.com"; 

		/* Step1: Show the field to input URL link */
		//- Connect to Intranet
		//- From [Activity Composer] box, click on [Link] button
		info("----Click on Link----");
		waitForAndGetElement(ELEMENT_LINK); 
		click(ELEMENT_LINK);

		/* Step2: Input an URL of page*/
		// - Input an URL of page with multiple images (e.g http://yahoo.com) then click Attach button
		info("----Input link into link box-----");
		waitForAndGetElement(ELEMENT_INPUT_LINK_BOX);
		type(ELEMENT_INPUT_LINK_BOX, link, true);
		waitForAndGetElement(ELEMENT_ATTACH_BUTTON);
		info("----Click attach button-----");
		click(ELEMENT_ATTACH_BUTTON);

		/* Step3: Share the link*/
		// - Choose an image 
		//- Click on the button [Share]
		waitForAndGetElement(ELEMENT_THUMBNAIL_SHOW); 		
		waitForAndGetElement(ELEMENT_NEXT_THUMBNAIL); 
		click(ELEMENT_NEXT_THUMBNAIL);
		waitForAndGetElement(ELEMENT_THUMBNAIL_NEXT); 
		waitForAndGetElement(ELEMENT_SHARE_BUTTON);
		info("----Click share button----");
		click(ELEMENT_SHARE_BUTTON);

		//delete Activity
		home.deleteActivity(link);
 	}



	/**
	* Case ID:77807.
	* Test Case Name: Share a link.
	* Created by chinhdt at 2013/12/16 09:28:47
	*/
	@Test
	public  void test05_ShareALink() {
		info("Test 5: Share a link"); 

		/*Declare variables*/ 
		String text = "";
		String link = "http://yahoo.com"; 
		String workingLabelText = "What are you working on?";
		String lighterColor = "rgba(153, 153, 153, 1)";
		/* Step: Show a field to input a link */
		//- Connect to Intranet
		//- From the [Activity Composer] box, click on [Link] button
		//- Input a link
		//- Click on the button [Share]
		addActivity(false, text, true, link);
		WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL);
		//Check color
		info("--Check color of string--");
		
		Assert.assertEquals(workingLabel.getText(), workingLabelText);
		Assert.assertEquals(waitForAndGetElement(home.ELEMENT_ACTIVITY_TEXTBOX).getCssValue("color"), lighterColor);
		
		//delete Activity
		home.deleteActivity(link);
 	}



}