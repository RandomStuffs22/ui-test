package org.exoplatform.selenium.platform.wiki.functional.basicaction;

import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.wiki.BasicAction;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.exoplatform.selenium.platform.ManageAccount;


/**
 * 
 * @author HangNTT
 * @date: 10/12/2012
 */
public class Wiki_BasicAction_Intergration extends BasicAction {
	ManageAccount magAcc;
	ManageMember magMember;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		magMember = new ManageMember(driver);
		magAcc.signIn("john", DATA_PASS);
	}

	/**
	 * Migrate to PLF 4
	 * == Pending: Cannot Edit a Wiki page in Space ==
	 * ==> FIXED (@vuna)
	 * */
	//Check links between page from different spaces
	@Test
	public void test01_CheckLinksBetweenPageFromDifferentSpaces() {

		String PAGE_NAME1 = "wiki1";
		String PAGE_NAME2 = "wiki2";

		By ELEMENT_PAGE1_LINK = By.linkText(PAGE_NAME1);
		
		magMember.goToMySpacePage();

		magMember.addNewSpace("Space1", "Description Of Space1");

		//goToWiki();
		click(ELEMENT_WIKI_LINK_IN_SPACE);
		
		addBlankWikiPage(PAGE_NAME1, PAGE_NAME1, 0);

		magMember.goToMySpacePage();

		magMember.addNewSpace("Space2", "Description Of Space2");

		//goToWiki();
		click(ELEMENT_WIKI_LINK_IN_SPACE);

		addBlankWikiPage(PAGE_NAME2, PAGE_NAME2, 0);

		editWikiPage(PAGE_NAME2, "[[wiki1>>/spaces/space1.wiki1]]", 0);

		click(ELEMENT_PAGE1_LINK);

		waitForTextNotPresent(PAGE_NAME2);
		
		magMember.goToMySpacePage();

		magMember.deleteSpace("Space1", 120000);

		magMember.deleteSpace("Space2", 120000);
	}
	
	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}