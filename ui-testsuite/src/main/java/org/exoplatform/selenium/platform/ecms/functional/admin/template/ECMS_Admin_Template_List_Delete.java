package org.exoplatform.selenium.platform.ecms.functional.admin.template;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * ECMS/Admin/Template/List
 * @author vuna2
 * @date 17th, April, 2013
 */
public class ECMS_Admin_Template_List_Delete extends PlatformBase{
	//General
	ManageAccount magAcc;
	ManageAlert alt;
	Button button;

	//Ecms
	EcmsBase ecms; 
	ECMainFunction ecMain;
	ManageTemplate magTem;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		button = new Button(driver);
		alt = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		ecms = new EcmsBase(driver);
		ecMain = new ECMainFunction(driver);
		magTem = new ManageTemplate(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod(){
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	
	/**
	 * Delete list template
	 */
	@Test
	public void test01_DeleteListTemplate(){
		String templateTitle = "List_Template_13";
		String templateName  = "List_Template_13";
		String templateType  = "Paginator";

		ecMain.goToListTemplateTab();

		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);

		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content", false, true);

		magTem.deleleTemplate(templateTitle);
	}

	/**
	 * Cancel delete list template
	 */
	@Test
	public void test02_CancelDeleteListTemplate(){
		ecMain.goToListTemplateTab();

		magTem.deleleTemplate("Announcement", false);

		//alt.waitForMessage("Are you sure you want to delete this template?");
		String alertMessage = alt.getTextFromAlert();
		assert alertMessage.equals("Are you sure you want to delete this template?"): "wrong message";

		alt.cancelAlert();

		waitForTextPresent("Announcement");
	}
}
