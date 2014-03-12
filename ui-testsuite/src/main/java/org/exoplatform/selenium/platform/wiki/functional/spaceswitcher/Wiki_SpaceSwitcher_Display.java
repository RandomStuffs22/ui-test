package org.exoplatform.selenium.platform.wiki.functional.spaceswitcher;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.exoplatform.selenium.platform.wiki.WikiBase;

import static org.exoplatform.selenium.TestLogger.info;

/**
 * @author havtt
 * @date: 04-Nov-2013
 */
public class Wiki_SpaceSwitcher_Display extends WikiBase {

	ManageAccount magAcc;
	SpaceManagement magSpace;

	String DATA_USER_ADMIN = DATA_USER1;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		magSpace = new SpaceManagement(driver);
		magAcc.signIn(DATA_USER_ADMIN, DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Input text should display a placeholder
	 * caseID: 79657
	 */
	@Test(groups="pending")
	//Related issue: FQA-1372
	public void test01_CheckFullDisplayOfSpaceSearch() {

		info("User go to Intranet Wiki");
		goToWikiHome();

		info("User open Space Switcher breadcrumb");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		info("Check the full display of Input Text with PlaceHolder");
		//Check the display of Input text
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);
		//Check the display of placeholder in Input Text
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_PLACEHOLDER);		
	}

	/**
	 * When user is member of at least one space, input text should be displayed
	 * caseID: 79641
	 */
	@Test
	public void test02_CheckDisplayOfInputTextWhenUserJoinASpace() {
		String SPACE_02 = "SPACE02";

		info("User create a new space");
		magSpace.goToAllSpaces();
		magSpace.addNewSpace(SPACE_02, SPACE_02);

		info("User go to Intranet Wiki");
		magSpace.goToSpaceMenu("Wiki");

		info("User open Space Switcher breadcrumb");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_BREADCRUMB);
		click(ELEMENT_SPACE_SWITCHER_BREADCRUMB);

		info("Check the full display of Input Text with PlaceHolder");
		waitForAndGetElement(ELEMENT_SPACE_SWITCHER_INPUT);

		info("Restore data");
		//Delete space
		magSpace.restoreData(SPACE_02);

	}

}
