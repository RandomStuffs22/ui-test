package org.exoplatform.selenium.platform.social.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.SocialBase;
import org.exoplatform.selenium.platform.social.SpaceSearch;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 07/10/2013
 *
 */
public class Social_Space_Search extends SocialBase {
	//Platform
	ManageAccount magAcc;
	ManageMember magMember;

	//Space
	SpaceSearch spaceSearch;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		spaceSearch = new SpaceSearch(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Search Space ==
	 * Test case ID: 67680
	 * Step 1: Search Space
	 */
	@Test
	public void test01_SearchSpace(){
		//Declare variable
		String spaceName1 = "asearchspace01";
		String spaceName2 = "bsearchspace02";

		//Create data
		//Add new space
		info("Create data");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, "");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, "");

		/*Step 1: Search Space*/ 
		//- Click on My space on Admin bar
		//- Input name or description into text box and click on Search button
		//- Display all result match with keyword
		magMember.goToMySpacePage();
		spaceSearch.searchSpaceByName(spaceName1,true);
		//- Search space by directory: Click on each characters (filter by A -> Z) 
		//- Display all spaces which has last name starts by the selected char
		spaceSearch.searchSpaceByName("",false);
		spaceSearch.searchSpaceByDirectory("B");
		waitForAndGetElement("//*[contains(text(),'"+spaceName2+"')]");

		/*Clear data*/
		info("clear data");
		click(By.linkText("All"));
		magMember.deleteSpace(spaceName1,300000);
		magMember.deleteSpace(spaceName2,300000);
	}

}
