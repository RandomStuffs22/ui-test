package org.exoplatform.selenium.platform.plf.functional.homepageactivitystream.linksharingactivity;
import static org.exoplatform.selenium.TestLogger.info;
import junit.framework.Assert;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.social.Activity;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: LanLTK
 * @date: 01/09/2014
 */
public class PLF_HomepageActivityStream_LinkSharingActivity extends Activity{
	  ManageAccount magAc;
      NavigationToolbar naviToolbar;
	  HomePageActivity hpActivity;
	  SpaceManagement spaceMag;

	  @BeforeMethod
	  public void setUpBeforeTest(){
	    getDriverAutoSave();
	    magAc = new ManageAccount(driver);
	    naviToolbar = new NavigationToolbar(driver);
	    hpActivity = new HomePageActivity(driver);
		spaceMag = new SpaceManagement(driver);
	    magAc.signIn(DATA_USER1, DATA_PASS);
	
	  }

	  @AfterMethod

	  public void afterTest(){
	    driver.manage().deleteAllCookies();
	    driver.quit();
	  }
	  
	  /**CaseId: 77168, 77172, 77175, 77176, 77623, 77624, 77333 -> Functional-PLF/Homepage Activity Stream/Link Sharing Activity
	   */ 
	  
	  /*
	   * CaseId 77168: Add Link Sharing activity after share a link in intranet
	   * CaseId 77172: Verify the size of the content's image in the Link Sharing activity
	   * CaseId 77176: Dislike a Link sharing activity from like icon in intranet
	   * CaseId 77175: Delete a Link sharing activity from activity stream by its user in portal
	   */

	  @Test	
	  public void test01_LinkSharing_Intranet(){
		  String link = "http://yahoo.fr";
		  int height = 75;
		  int width = 75;
		  String title = "Yahoo France";

		  // Add a link shared in intranet
		  naviToolbar.goToHomePage();
		  addActivity(false,"",true,link);
		  
		  // Like a link shared
		  hpActivity.likeOrUnlikeActivity(link);
		  
		  // Dislike a link shared
		  hpActivity.likeOrUnlikeActivity(link);
		  
		  // Verify the size of the content's image
		  String ELEMENT_LINKSHARE_THUMBNAIL = ELEMENT_DATA_ORIGINAL_TITLE.replace("${title}", title);
		  int imageHeight = waitForAndGetElement(ELEMENT_LINKSHARE_THUMBNAIL).getSize().getHeight();
		  int imageWidth = waitForAndGetElement(ELEMENT_LINKSHARE_THUMBNAIL).getSize().getWidth();
		  info("Height = " + String.valueOf(imageHeight));
		  info("Width = " + String.valueOf(imageWidth));
		  Assert.assertEquals(width,imageWidth);
		  Assert.assertEquals(height,imageHeight);
		  
		  // Delete a link shared
		  hpActivity.deleteActivity(link);
	  }


	  /*
	   * CaseId 77623: Add Link Sharing activity after share a link in space
	   * CaseId 77624: Dislike a Link sharing activity from like icon in space
	   * CaseId 77333: Delete a Link sharing activity from activity stream by its user in space
	   */
	  
	  @Test	
	  public void test02_LinkSharing_Space(){
		  String link = "https://www.google.com.vn/";
		  String spaceName = "Space77623";

		  // Create data for Pre-Conditions
		  spaceMag.goToMySpacePage();
		  spaceMag.addNewSpace(spaceName, "");	
		
		  // Start test case
		  naviToolbar.goToHomePage();
		  click(By.linkText(spaceName));
		  info("Go to Space1");
		  waitForAndGetElement(ELEMENT_ACTIVITY_WHAT_ARE_YOU_WORKING_LABEL);
		  
		  // Add a link shared in intranet	  
		  addActivity(false,"",true,link);
		  
		  // Like a link shared
		  hpActivity.likeOrUnlikeActivity(link);
		  
		  // Dislike a link shared
		  hpActivity.likeOrUnlikeActivity(link);
		  
		  // Delete a link shared
		  hpActivity.deleteActivity(link);
	  
		  //Delete activity before exit test cases
		  spaceMag.goToMySpacePage();
		  spaceMag.deleteSpace(spaceName,300000);
	  }
	  
}
