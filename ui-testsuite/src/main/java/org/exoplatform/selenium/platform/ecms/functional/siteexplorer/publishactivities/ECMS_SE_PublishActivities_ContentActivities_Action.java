package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publishactivities;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author havtt
 * Sep, 18th, 2013
 *
 */

public class ECMS_SE_PublishActivities_ContentActivities_Action extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	ContextMenu cMenu;
	ContentTemplate cTemplate;

	//Social
	HomePageActivity sActivity;

	public String DATA_USER = "john";
	//public String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		cMenu = new ContextMenu(driver);
		cTemplate = new ContentTemplate(driver);
		sActivity = new HomePageActivity(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 76990
	 * Dislike a content activity
	 * 
	 */
	@Test
	public void test01_DislikeContentActivity(){
		String FILE_TITLE_01 = "ECMS_SE_Content_00";

		info("Go to Content Explorer");
		navToolBar.goToSiteExplorer();

		info("Create a node in root path");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(FILE_TITLE_01, FILE_TITLE_01, "", "", "", "");
		waitForAndGetElement(By.linkText(FILE_TITLE_01));

		info("Go to Intranet Homepage");
		navToolBar.goToHomePage();
		WebElement LIKE = waitForAndGetElement(sActivity.ELEMENT_LIKE_ICON.replace("${activityText}",FILE_TITLE_01));
		//System.out.print(LIKE);
		String nbLike_old = LIKE.getText();
		info(nbLike_old);

		info("Like a node activity");
		//like
		sActivity.likeOrUnlikeActivity(FILE_TITLE_01);
		Utils.pause(5000);

		//dislike
		info("Dislike a node activity");
		sActivity.likeOrUnlikeActivity(FILE_TITLE_01);
		Utils.pause(5000);

		info("Restore data");
		//By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(By.linkText(FILE_TITLE_01));

	}
}