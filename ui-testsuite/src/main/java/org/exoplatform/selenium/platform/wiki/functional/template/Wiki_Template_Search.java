package org.exoplatform.selenium.platform.wiki.functional.template;

import static org.exoplatform.selenium.TestLogger.info;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Template;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*--
 *-- Author: HaKT
 *-- Date: 19 Dec 2012
 **/
public class Wiki_Template_Search extends Template{

	ManageAccount magAc;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS); 
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 69794
	 * Case 01: Search template when the key is matched
	 * - Create new Template
	 * - Fill in search form new template title, press Enter
	 * - Verify new template is in list
	 * - Delete template
	 */
	@Test
	public  void test01_SearchTemplateWithMatchedKey(){

		String DATA_TEMPLATE_TITLE="Search_Template_01";
		String DATA_TEMPLATE_DESC="This is New Template 01";
		String DATA_TEMPLATE_CONTENT="Content of New Template 01";
		

		info("Search template when the key is matched");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);

		searchTemplate(DATA_TEMPLATE_TITLE);

		waitForAndGetElement(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));
		
		magAc.signOut();

		magAc.signIn("john", "gtn"); 

		goToWiki();
		goToTemplateManagement();

		
		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69795
	 * Case 02: Search template when the key is not matched
	 * - Create new Template
	 * - Fill in search form with a word not match with any template title, press Enter
	 * - Verify Empty data
	 * - Delete template
	 */
	@Test
	public  void test02_SearchTemplateWithNotMatchedKey(){

		String DATA_TEMPLATE_TITLE="Search_Template_02";
		String DATA_TEMPLATE_DESC="This is New Template 02";
		String DATA_TEMPLATE_CONTENT="Content of New Template 02";
		String DATA_SEARCH_KEY_WORD="Search_Template_0222";
		String DATA_SEARCH_KEY_BLANK="";

		info("Search template when the key is matched");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);

		waitForAndGetElement(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		searchTemplate(DATA_SEARCH_KEY_WORD);

		waitForAndGetElement(By.xpath(ELEMENT_EMPTY_DATA));
		searchTemplate(DATA_SEARCH_KEY_BLANK);

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}

	/**
	 * Qmetry ID: 69793
	 * Case 03: Search template when Search field is blank
	 * - Create new Template
	 * - Leave search field blank, press Enter
	 * - Capture screen to view all template
	 * - Delete template
	 */
	@Test
	public  void test03_SearchTemplateWithBlankSearchField(){

		String DATA_TEMPLATE_TITLE="Search_Template_03";
		String DATA_TEMPLATE_DESC="This is New Template 03";
		String DATA_TEMPLATE_CONTENT="Content of New Template 03";
		String DATA_SEARCH_KEY_WORD="";
		String DATA_BEFORE_CREAT_NEW_TEMPLATE = "Before_New_Template";
		String DATA_AFTER_CREAT_NEW_TEMPLATE = "AFTER_New_Template";

		info("Search template with blank search field");

		goToTemplateManagement();

		Utils.captureScreen(DATA_BEFORE_CREAT_NEW_TEMPLATE);

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);

		waitForAndGetElement(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		searchTemplate(DATA_SEARCH_KEY_WORD);

		Utils.captureScreen(DATA_AFTER_CREAT_NEW_TEMPLATE);

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}
}