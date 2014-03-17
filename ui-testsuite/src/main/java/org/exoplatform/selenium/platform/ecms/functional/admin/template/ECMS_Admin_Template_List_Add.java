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
public class ECMS_Admin_Template_List_Add extends PlatformBase{

	//General
	ManageAccount magAcc;
	ManageAlert alt;
	Button button;

	//Ecms
	EcmsBase ecms; 
	ECMainFunction ecMain;
	ManageTemplate magTem;

	public final String DATA_USER_ADMIN = "john";
	//public final String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER_ADMIN);
		button = new Button(driver);
		alt = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		ecms = new EcmsBase(driver);
		ecMain = new ECMainFunction(driver);
		magTem = new ManageTemplate(driver);
		magAcc.signIn(DATA_USER_ADMIN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod(){
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * <li>Step 1: Go to List templates</li>
	 * <li>Step 2: Open Add List Template form</li>
	 * <li>Step 3: Add list template with all valid values</li>
	 */
	@Test
	public void test01_AddListTemplateWhenInputValidDataIntoAllRequiredFields(){
		String templateTitle = "List_Template_02";
		String templateName  = "List_Template_02";
		String templateType  = "Navigation";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content");
		magTem.deleleTemplate(templateTitle);
	}

	/**
	 * Add List template when Content field is blank
	 * <li>Step 1: Go to List templates</li>
	 * <li>Step 2: Open Add List Template form</li>
	 * <li>Step 3: Add List template with blank content</li>
	 */
	@Test
	public void test02_AddListTemplateWhenContentFieldIsBlank(){
		String templateTitle = "test02_AddListTemplate";
		String templateName  = "test02_AddListTemplate";
		String templateType  = "Content";
		String content = "";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, false, content, false, true);
		magTem.deleleTemplate(templateName);
	}

	/**
	 * 
	 * <li>Step 1: Go to List templates</li>
	 * <li>Step 2: Open Add List Template form</li>
	 * <li>Step 3: Add List template when Name field is blank</li>
	 */
	@Test
	public void test03_AddListTemplateWhenNameFieldIsBlank(){
		String templateTitle = "";
		String templateName  = "List_Template_03";
		String templateType  = "Paginator";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content");
		magTem.deleleTemplate(templateName);
	}

	/**
	 * 
	 * <li>Step 1: Go to List templates</li>
	 * <li>Step 2: Open Add List Template form</li>
	 * <li>Step 3: Add List template when Template Name field is blank</li>
	 */
	@Test
	public void test04_AddListTemplateWhenTemplateNameFieldIsBlank(){
		String templateTitle = "List_Template_04";
		String templateName  = "";
		String templateType  = "Content";
		String message = "The field \"Template Name\" is required.";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content", false, false);
		alt.verifyAlertMessage(message);
		button.cancel();
	}

	/**
	 * 
	 * <li>Step 1: Go to List templates</li>
	 * <li>Step 2: Open Add List Template form</li>
	 * <li>Step 3: Add List template with default template type</li>
	 */
	@Test
	public void test05_AddListTemplateWithDefaultTemplateType(){
		String templateTitle = "List_Template_05";
		String templateName  = "List_Template_05";
		String templateType  = "Content";

		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content", false, true);
		magTem.deleleTemplate(templateName);
	}

	/**
	 * Reset while adding list template
	 */
	@Test
	public void test06_ResetWhileAddingListTemplate(){
		String templateTitle = "List_Template_11";
		String templateName  = "List_Template_11";
		String templateType  = "Navigation";
		String templateContent = "List_Template_Content";

		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		type(magTem.ELEMENT_LIST_TEMPLATE_CONTENT, templateContent, true);
		type(magTem.ELEMENT_LIST_TEMPLATE_TITLE, templateTitle, false);
		type(magTem.ELEMENT_LIST_TEMPLATE_NAME, templateName, false);
		select(magTem.ELEMENT_LIST_TEMPLATE_TYPE, templateType);
		click(button.ELEMENT_RESET_BUTTON);

		//Verify that added values are reset to blank
		//Selected value (Template type) is reset to default value
		assert !magTem.ELEMENT_LIST_TEMPLATE_CONTENT.equals(templateContent): "Cannot reset data (List Content)";
		assert !magTem.ELEMENT_LIST_TEMPLATE_TITLE.equals(templateTitle): "Cannot reset data (Name)";
		assert !magTem.ELEMENT_LIST_TEMPLATE_NAME.equals(templateName): "Cannot reset data (Template Name)";
		assert !magTem.ELEMENT_LIST_TEMPLATE_TYPE.equals(templateType): "Cannot reset data (Template Type)";
		waitForElementNotPresent(magTem.ELEMENT_DELETE_TEMPLATE_ICON.replace("${templateName}", templateName));
	}

	/**
	 * 
	 * <li>Step 1: Go to List templates</li>
	 * <li>Step 2: Open Add List Template form</li>
	 * <li>Step 3: Input all values on Add list template form</li>
	 * <li>Step 4: Cancel add list template</li>
	 */
	@Test
	public void test07_CancelAddListTemplate(){
		String templateTitle = "List_Template_10";
		String templateName  = "List_Template_10";
		String templateType  = "Content";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		type(magTem.ELEMENT_LIST_TEMPLATE_CONTENT, "templateContent", true);
		type(magTem.ELEMENT_LIST_TEMPLATE_TITLE, templateTitle, false);
		type(magTem.ELEMENT_LIST_TEMPLATE_NAME, templateName, false);
		select(magTem.ELEMENT_LIST_TEMPLATE_TYPE, templateType);
		button.cancel();
		waitForElementNotPresent(magTem.ELEMENT_DELETE_TEMPLATE_ICON.replace("${templateName}", templateName));
	}

	/**
	 * Add List Template with Template type is Navigation
	 * 
	 */
	@Test
	public void test08_AddListTemplateWithTemplateTypeIsNavigation(){
		String templateTitle = "List_Template_08";
		String templateName  = "List_Template_08";
		String templateType  = "Navigation";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content", false, true);
		magTem.deleleTemplate(templateTitle);
	}


	/**
	 * Add List Template with Template type is Paginator
	 */
	@Test
	public void test09_AddListTemplateWithTemplateTypeIsPaginator(){
		String templateTitle = "List_Template_09";
		String templateName  = "List_Template_09";
		String templateType  = "Paginator";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content", false, true);
		magTem.deleleTemplate(templateTitle);
	}

	/**
	 * 
	 * <li>Step 1: Go to List templates</li>
	 * <li>Step 2: Open Add List Template form</li>
	 * <li>Step 3: Add List template with name is same as existing one</li>
	 */
	@Test
	public void test10_AddListTemplateWithNameIsSameAsExistingOne(){
		String templateTitle = "Announcement";
		String templateName  = "List_Template_06";
		String templateType  = "Content";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content", false, true);
		magTem.deleleTemplate("List_Template_06.gtmpl");
	}

	/**
	 * Add List template with Template Name is same as existing one
	 */
	@Test
	public void test11_AddListTemplateWithTemplateNameIsSameAsExistingOne(){
		String templateTitle = "List_Template_07";
		String templateName  = "Documents.gtmpl";
		String templateType  = "Content";
		String message = "This template name already exists. Please select another one";
		ecMain.goToListTemplateTab();
		click(magTem.ELEMENT_ADD_TEMPLATE_BUTTON);
		magTem.addNewListTemplate(templateTitle, templateName, templateType, true, "List_Template_Content", false, false);
		alt.verifyAlertMessage(message);
		button.cancel();
	} 
}