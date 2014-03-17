package org.exoplatform.selenium.platform.wiki.functional.template;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.wiki.Template;

import static org.exoplatform.selenium.TestLogger.info;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*--
 *-- Author: HaKT
 *-- Date: 18 Dec 2012
 **/

public class Wiki_Template_Delete extends Template{

	ManageAccount magAc;
	ManageAlert magAlert;
	
	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		magAlert = new ManageAlert(driver);
		magAc.signIn("john", DATA_PASS); 
		goToWiki();
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 69750
	 * Delete template when OK with confirmation
	 * Create a new template
	 * Delete template, click OK with confirmation message
	 * Verify template is deleted
	 */
	@Test
	public void test01_deleteTemplateWithOKConfirmation(){

		String DATA_TEMPLATE_TITLE="Delete_Template_01";
		String DATA_TEMPLATE_DESC="This case is delete a template";
		String DATA_TEMPLATE_CONTENT="Content of Delete_Template_01";

		info("Delete template when OK with confirmation");
		goToTemplateManagement();
		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);

		click(ELEMENT_DELETE_TEMPLATE_ICON.replace("{$template}", DATA_TEMPLATE_TITLE));

		magAlert.acceptAlert();

		waitForElementNotPresent(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));
	}

	/**
	 * Qmetry ID: 69749
	 * Delete template when Cancel with confirmation
	 * Create a new template
	 * Delete template, click Cancel with confirmation message
	 * Verify template is not deleted
	 */
	@Test
	public void test02_deleteTemplateWithCancelConfirmation(){

		String DATA_TEMPLATE_TITLE="Delete_Template_02";
		String DATA_TEMPLATE_DESC="This case is delete a template";
		String DATA_TEMPLATE_CONTENT="Content of Delete_Template_02";

		info("Delete template when Cancel with confirmation");

		goToTemplateManagement();

		addTemplate(DATA_TEMPLATE_TITLE, DATA_TEMPLATE_DESC, DATA_TEMPLATE_CONTENT);

		click(ELEMENT_DELETE_TEMPLATE_ICON.replace("{$template}", DATA_TEMPLATE_TITLE));

		magAlert.cancelAlert();

		waitForAndGetElement(ELEMENT_NEW_TEMPLATE_LINK.replace("${TEMPLATE_TITLE}", DATA_TEMPLATE_TITLE));

		goToWikiHome();

		deleteTemplate(DATA_TEMPLATE_TITLE);
	}
}