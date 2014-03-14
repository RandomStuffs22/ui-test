package org.exoplatform.selenium.platform.ecms.sniff.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageNodeType;
import org.exoplatform.selenium.platform.ecms.admin.ManageTemplate;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * May, 2013
 *
 */
public class ECMS_Admin_Template extends PlatformBase{
	//General
	ManageAlert alt;
	ManageAccount magAcc;

	//Ecms
	EcmsBase ecms; 
	ECMainFunction ecMain;
	ManageTemplate magTem;
	ManageNodeType magNType;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		info("LogIn to Intranet with User..." + DATA_USER1);
		alt = new ManageAlert(driver);
		magAcc = new ManageAccount(driver);
		ecms = new EcmsBase(driver);
		ecMain = new ECMainFunction(driver);
		magTem = new ManageTemplate(driver);
		magNType = new ManageNodeType(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod(){
		info("-- LogOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 65867
	 * Add Document Template
	 * ================
	 * Qmetry ID: 67844
	 * Edit Document Template
	 * ================
	 * Qmetry ID: 67845
	 * Delete Document Template
	 * 
	 */
	@Test(priority=0)
	public void test00_AddEditAndDeleteDocumentTemplate(){
		String templateName = "exo:answer";
		String label = "answerTest";
		String editLabel = "answerEditTest";
		String group = "Platform/Administration";
		String membership = "*";
		String fileDialogContent = "TestData/ECMS_Admin_GetMailScript_Template.txt";
		String dialogName = "mailTest";

		info("-- Open Manage Template screen --");
		ecMain.goToTemplateTab();

		info("-- Open Add New Template Form --");
		magTem.openAddNewTemplateForm();

		info("-- Add a new template --");
		magTem.fillInTemplateForm(templateName, label, group, membership);

		info("-- Edit a document template --");
		magTem.editTemplate(templateName, editLabel, true, fileDialogContent, dialogName, group, membership);   

		info("-- Delete a document template --");
		magTem.deleleTemplate(editLabel);
	}

	/**
	 * Qmetry ID: 67846
	 * Add List Template
	 * ================
	 * Qmetry ID: 67847
	 * Edit List Template
	 * ================
	 * Qmetry ID: 67848
	 * Delete List Template
	 * 
	 */
	@Test
	public void test02_AddEditAndDeleteListTemplate(){
		String templateTitle_01 = "Ecms_Sniff_Admin_Template_01";
		String editTemplateTitle_01 = "Edit_Ecms_Sniff_Admin_Template_01";
		String templateTitle_02 = "Ecms_Sniff_Admin_Template_02";
		String editTemplateTitle_02 = "Edit_Ecms_Sniff_Admin_Template_02";
		String fileContent_02 =	"TestData/ECMS_Admin_GetMailScript_Template.txt";
		String templateTitle_03 = "Ecms_Sniff_Admin_Template_03";
		String editTemplateTitle_03 = "Edit_Ecms_Sniff_Admin_Template_03";
		String templateName_01  = "Ecms_Sniff_Admin_Template_01";
		String templateName_02  = "Ecms_Sniff_Admin_Template_02";
		String templateName_03  = "Ecms_Sniff_Admin_Template_03";
		String templateType_01  = "Content";
		String templateType_02  = "Navigation";
		String templateType_03  = "Paginator";

		info("-- Go to [List Template] --"); 
		ecMain.goToListTemplateTab();

		info("-- Add a new list template --");
		magTem.addNewListTemplate(templateTitle_01, templateName_01, templateType_01, true, "List_Template_Content_01");
		magTem.addNewListTemplate(templateTitle_02, templateName_02, templateType_02, true, "List_Template_Content_02", false, true);
		magTem.addNewListTemplate(templateTitle_03, templateName_03, templateType_03, true, "List_Template_Content_03", false, true);

		info("-- Edit a list template --");
		magTem.editListTemplate(templateTitle_01, editTemplateTitle_01, templateType_01);
		magTem.editListTemplate(templateTitle_02, editTemplateTitle_02, templateType_02, true, fileContent_02, true);
		magTem.editListTemplate(templateTitle_03, editTemplateTitle_03, templateType_03);

		info("-- Delete a list template --");
		magTem.deleleTemplate(editTemplateTitle_03);

		click(magTem.ELEMENT_LIST_TEMPLATE_NAVIGATION_TAB);
		magTem.deleleTemplate(editTemplateTitle_02);

		click(magTem.ELEMENT_LIST_TEMPLATE_CONTENT_TAB);
		magTem.deleleTemplate(editTemplateTitle_01);
	}

	/**
	 * Qmetry ID: 67842
	 * View Metadata
	 * ================
	 * Qmetry ID: 65856
	 * Edit Metadata
	 * ================ 
	 * Qmetry ID: 67843 
	 * Delete Metadata
	 * 
	 */
	@Test(priority=1)
	public void test00_ViewEditAndDeleteMetadata(){
		String metadata = "metadata67842";
		String[] metadataPer = {"Platform/Administration", "*"};
		String editMetadataLabel = "Edit_Ecms_Sniff_Metadata";
		String dialogTemplate = "TestData/ECMS_Admin_GetMailScript_Template.txt";
		String viewTemplate = "TestData/ECMS_Admin_SendMailScript_Template.txt";    	 

		info("-- Prepare data --");
		ecMain.goToNodeTypeTab();
		magNType.addNewNodeType(metadata, "exo:metadata", "", true);

		info("-- View metadata --");
		ecMain.goToMetadataTab();
		magTem.actionsOnMetadata(metadata, "View");

		info("-- Edit metadata --");
		magTem.editMetadata(metadata, metadataPer, editMetadataLabel, true, dialogTemplate, true, viewTemplate);

		info("-- Delete metadata --");
		magTem.actionsOnMetadata(metadata, "Delete");
	}
}