package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.activity;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author chinhdtt
 * @date 30 Nov 2013
 */
public class PLF_HomePageActivityStream_Activity_Comment extends Activity{
	ManageAccount acc;
	HomePageActivity home; 
	Button button; 
	ManageAlert alert; 
	NavigationToolbar nav; 

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		acc = new ManageAccount(driver);
		home = new HomePageActivity(driver); 
		acc.signIn(DATA_USER_JOHN, DATA_PASS);
		button = new Button(driver);
		alert = new ManageAlert(driver);
		nav = new NavigationToolbar(driver);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();		
		driver.quit();
	}

	/** Display 2 latest comments in an activity
	 * Test caseID: 78595
	 * Step 1: Check 2 latest comments
	 */
	@Test
	public void test01_DisplayTwoLatestCommentsInAnActivity(){
		String text = "Activity 78595";
		String link = "";
		String comment01 = "Test comment 01";
		String comment02 = "Test comment 02";
		String comment03 = "Test comment 03"; 
		addActivity(true, text, false, link);
		addComment(text, comment01);
		addComment(text, comment02);
		addComment(text, comment03);
		//Check display last comment
		nav.goToHomePage();
		showHideComments(text, true, false,"3");
		waitForAndGetElement(ELEMENT_COMMENT_LIST.replace("${activityText}", text));
		waitForAndGetElement(home.ELEMENT_COMMENT_LAST.replace("${title}", text).replace("${comment}", comment03));
		//Delete data
		home.deleteActivity(text);
	}

	/** Display date time information of comment
	 * Test caseID: 78597
	 * Step 1: Check date time information of comment
	 */
	@Test
	public void test02_DisplayDateTimeInformationOfComment(){
		String text = "Activity 78597";
		String link = "";
		String comment01 = "Test comment 01";
		String datetime="less than a minute ago";
		addActivity(true, text, false, link);
		addComment(text, comment01);


		//Check display
		waitForAndGetElement(By.xpath(ELEMENT_ACTIVITY_AUTHOR_ACTIVITY.replace("${activityText}", text)));
		info(ELEMENT_ACTIVITY_AUTHOR_ACTIVITY.replace("${activityText}", text));
		//Check date time
		waitForAndGetElement(ELEMENT_COMMENT_DATETIME.replace("${activityText}", text).replace("${DATETIME}", datetime));
		Utils.pause(2000);
		//Delete data
		home.deleteActivity(text);
	}

	/** Check comment allignment
	 * Test caseID: 78600
	 * Step 1: Check comment alignment
	 */
	@Test
	public void test03_CheckCommentAllignment(){
		String text = "Add activity case03";
		String link = "";
		String comment = "Add comment of case03 test for long string input to comment field, "
				+ "text is aligned on the same vertical line on the left";
		addActivity(true, text, false, link);
		click(home.ELEMENT_ICON_COMMENT.replace("${title}", text));
		WebElement commentText = waitForAndGetElement(home.ELEMENT_COMMENTBOX.replace("${title}", text));
		WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_ADD_YOUR_COMMENTLABEL.replace("${activityText}", text));
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '';", workingLabel);
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+comment+"';", commentText);
		
		Assert.assertEquals(commentText.getCssValue("text-align"), "start");
		//delete data
		home.deleteActivity(text);
	}

	/** Submit a comment to an activity
	 * Test caseID 78601
	 * Step 1: Add comment
	 */
	@Test
	public void test04_SubmitACommentToAnActivity(){
		//Step 1: Add comment
		// - Connect to Intranet
		//- Share an activity
		//- Click [Comment] button
		//- Input text into Comment box
		//- Click on the button [Comment]
		String text = "Add Activity to case 04";
		String link = "";
		String comment01 = "Test comment 01";
		addActivity(true, text, false, link);
		addComment(text, comment01);
		//delete data
		home.deleteActivity(text);
	}

	/** Display the comment box
	 * Test caseID: 78599
	 * Step 1: Share an activity
	 * Step 2: Show comment box
	 * Step 3: Input text into comment box
	 */
	@Test
	public void test05_DisplayTheCommentBox(){
		String text = "Activity case 05";
		String link = "";
		String comment = "Add comment of case05";
		/* Step 1: Share an activity*/
		// - Connect to Intranet
		// - Share an activity
		addActivity(true, text, false, link);

		/* Step 2: Show comment box*/
		//- Click on the comment icon
		click(home.ELEMENT_ICON_COMMENT.replace("${title}", text));
		WebElement commentText = waitForAndGetElement(home.ELEMENT_COMMENTBOX.replace("${title}", text));
		WebElement workingLabel = waitForAndGetElement(ELEMENT_ACTIVITY_ADD_YOUR_COMMENTLABEL.replace("${activityText}", text));
		
		Assert.assertEquals(driver.switchTo().activeElement(),commentText);
		
		Assert.assertEquals(workingLabel.isDisplayed(), true);
		Assert.assertEquals(commentText.isDisplayed(), true);
		Assert.assertEquals(workingLabel.getText(), "Add your comment...");
		
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '';", workingLabel);
	  
		Utils.pause(1000);
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+comment+"';", commentText);
		Assert.assertEquals(workingLabel.isDisplayed(), false);
		home.deleteActivity(text);
	}

	/** Display more comments
	 * Test caseID 78604
	 * Step 1: Display link "View all XX comments"
	 * Step 2: Click link "View all XX comments"
	 * Step 3: Click link "View previous comments"
	 */
	@Test
	public void test06_DisplayMoreComments(){
		String text = "Activity case06";
		String link = "";

		String comment = "Add comment";
		addActivity(true, text, false, link);
		for(int i=1; i<=12; i++){
			addComment(text, comment +""+ String.valueOf(i));
		}
		nav.goToHomePage();
		showHideComments(text, true, false, "12");
		showHideComments(text, false, true, "12");
		//delete data
		home.deleteActivity(text);
	}

	/**Display the sentence "View all XX comments"
	 * Test caseID: 78602
	 * - Connect to Intranet
	 * - Add more than 2 comments to an activity
	 */
	@Test 
	public void test07_DisplayTheSentenceViewAllXXComments(){
		String text = "Activity 78602";
		String link = "";
		String comment = "Add comment:";
		addActivity(true, text, false, link);
		for(int i=1; i<=4; i++){
			addComment(text, comment +i);
		}
		nav.goToHomePage();
		showHideComments(text, true, false, "4");
		//Delete data
		home.deleteActivity(text);
	}

	/** Display user profile's popup in a comment
	 * Test caseID: 78598
	 * Step 1: Add comment
	 * Step 2: Show user's profile pop up
	 */
	@Test
	public void test08_DisplayUserProfileISPopupInAComment(){
		String text = "Activity case 08"; 
		String link = "";
		String comment = "Add comment case08";
		addActivity(true, text, false, link);

		//Step 1: Add comment
		addComment(text, comment);
		WebElement element = waitForAndGetElement(ELEMENT_COMMENT_AVATAR.replace("${activityText}", text));
		
		Assert.assertEquals(element.isDisplayed(), true);
		
		//Step 2: Show user's profile pop up
		//- Move the mouse over the avatar or name
		
		mouseOver(ELEMENT_COMMENT_AVATAR.replace("${activityText}", text), true);
		//The user profile's popup is displayed
		waitForAndGetElement(ELEMENT_USER_PROFILE_POPUP.replace("${userName}", "John Smith"));
		
		//delete data
		home.deleteActivity(text);
	}

	/** Display vertical scroll bar for long comment
	 * Test caseID: 78603
	 * Step 1: Check long comment
	 */
	@Test
	public void test09_DisplayVerticalScrollBarForLongComment(){
		String text = "Activity case 09";
		String link = "";
		String comment = "Input a long string to test scroll bar in comment textbox."
				+ " Test caseID: 78603. Input a long string to test scroll bar in comment textbox. "
				+ "Test caseID: 78603. Input a long string to test scroll bar in comment textbox."
				+ "Test caseID: 78603. Input a long string to test scroll bar in comment textbox."
				+ "Test caseID: 78603. Input a long string to test scroll bar in comment textbox."
				+ "Test caseID: 78603. Input a long string to test scroll bar in comment textbox."
				+ "Test caseID: 78603. Input a long string to test scroll bar in comment textbox."
				+ "Test caseID: 78603. Input a long string to test scroll bar in comment textbox. ";
		addActivity(true, text, false, link);
		click(home.ELEMENT_ICON_COMMENT.replace("${title}", text));
		WebElement commentText = waitForAndGetElement(home.ELEMENT_COMMENTBOX.replace("${title}", text));
		((JavascriptExecutor)driver).executeScript("arguments[0].textContent = '"+comment+"';", commentText);
		
		String str1 = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].clientHeight;", commentText));
		String str = String.valueOf(((JavascriptExecutor)driver).executeScript("return arguments[0].scrollHeight;", commentText));
		int clientHeight = Integer.parseInt(str1);
		int scrollHeight = Integer.parseInt(str);
		assert clientHeight<scrollHeight;
		
		//delete data
		home.deleteActivity(text);
	}

	/** Remove comment of user
	 * Test caseID: 78596
	 * Step 1: Add comment 
	 * Step 2: Show (X) icon
	 * Step 3: Click (X) icon
	 */
	@Test
	public void test10_RemoveCommentOfUser(){
		String text="Activity case10";
		String link = "";
		String comment ="Add comment case10";
		addActivity(true, text, false, link);
		addComment(text, comment);
		deleteComment(text, comment);
		//Delete data
		home.deleteActivity(text);
	}

	/** Add comment with special characters
	 * Test caseID: 78605
	 * Step 1: Add comment with special characters
	 */
	@Test
	public void test11_AddCommentWithSpecialCharacters(){
		//- Intranet
		//- Click [Comment] button on activity
		//- Input special characters into comment box and click [Comment]
		String text = "Activity case11";
		String link = "";
		String comment = "Add speacical characters: @#$%^&()+!;:";
		addActivity(true, text, false, link);
		addComment(text, comment);
		//delete data
		home.deleteActivity(text);
	}
}
