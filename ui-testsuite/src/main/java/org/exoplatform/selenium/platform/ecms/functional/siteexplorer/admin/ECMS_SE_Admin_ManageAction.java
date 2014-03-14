package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageAction;
import org.exoplatform.selenium.platform.ecms.admin.ManageScript;
import org.exoplatform.selenium.platform.ecms.admin.ManageTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/*
 * @author: PhuongDT
 * @date: 26/08/2013
 */
public class ECMS_SE_Admin_ManageAction extends PlatformBase{
		//Platform
		ManageAccount magAcc;

		//Ecms
		ECMainFunction ecMain;
		ManageScript magScript;
		NavigationToolbar navToolBar;
		ManageAction magAction;
		ManageTemplate magTemplate;
		EcmsBase ecms;
		ContextMenu cMenu;
		ContentTemplate cTemplate;
		ActionBar actBar;
		SitesExplorer siteExp;

		@BeforeMethod
		public void beforeMethods() {
			initSeleniumTest();
			driver.get(baseUrl);
			info("Login ECMS with " + DATA_USER1);
			magAcc = new ManageAccount(driver);
			ecMain = new ECMainFunction(driver,this.plfVersion); 
			magScript = new ManageScript(driver);
			magAction = new ManageAction(driver);
			magTemplate = new ManageTemplate(driver,this.plfVersion);
			navToolBar = new NavigationToolbar(driver);
			cTemplate = new ContentTemplate(driver);
			cMenu = new ContextMenu(driver);
			actBar = new ActionBar(driver);
			ecms = new EcmsBase(driver);
			siteExp = new SitesExplorer(driver);
			magAcc.signIn(DATA_USER1, DATA_PASS);
		}

		@AfterMethod
		public void afterMethods() {
			info("Logout ECMS");
			driver.manage().deleteAllCookies();
			driver.quit();
		}
		
		/**
		 * == Create action for new type action added manually ==
		 * Test case ID: 85395
		 * Step 1: Go to Content Administration
		 * Step 2: Create script
		 * Step 3: Create an action
		 * Step 4: Create a template
		 * Step 5: Input data into Dialog 
		 * Step 6: Create node
		 * Step 7: Add manual action for node
		 */
		@Test
		public void test01_CreateActionForNewTypeActionAddedManually(){
			/*Declare variable*/
			String scriptFileContent = "TestData/test01_CreateActionForNewTypeActionAddedManually_Script.txt"; 
			String scriptLabel = "sendtomail"; 
			String scriptName = "sendtomail";
			String actionTypeName = "sendtomail";
			String variable = "exo:description";
			
			String templateName = "exo:"+scriptName;
			String label = "labelTest";
			String group = "Platform/Administration";
			String membership = "*";
			
			String fileDialogContent = "TestData/test01_CreateActionForNewTypeActionAddedManually_Dialog.txt";
			String CONTENT_FOLDER_TITLE = "test01_CreateActionForNewTypeActionAddedManually_Folder";
			String FILE_TITLE = "test01_CreateActionForNewTypeActionAddedManually_File";
			
			String actionName = "actionName";
			String lifeCycle = "User Action";
			String actionType = "exo:"+scriptName;
			
			/*Step 1: Go to Content Administration*/
			/*Step 2: Create script*/
			info("Create script");
			ecMain.goToScriptsTabInContentAdmin();
			magScript.addScript(scriptFileContent, scriptLabel, scriptName);
			
			/*Step 3: Create an action*/
			info("Create an action");
			ecMain.goToActionsTabInContentAdmin();
			magAction.addActionType(actionTypeName, scriptName+ ".groovy", variable);
			
			/*Step 4: Create a template
			Step 5: Input data into Dialog*/
			info("Create a template - action tab");
			ecMain.goToTemplateTab();
			magTemplate.openAddNewTemplateForm("Actions");
			magTemplate.fillInTemplateForm(templateName, label, group, membership,false,fileDialogContent);

			/*Step 6: Create node*/
			info("Create node");
			navToolBar.goToSiteExplorer();
			cTemplate.createNewFolder(CONTENT_FOLDER_TITLE, folderType.Content);
			ecms.goToNode(By.linkText(CONTENT_FOLDER_TITLE));
			actBar.goToAddNewContent();
			cTemplate.createNewFile(FILE_TITLE, FILE_TITLE, FILE_TITLE, "", false, true, "text/plain");
			assert getText(By.cssSelector(cTemplate.ELEMENT_NEWFILE_PRE_CSS)).contains(FILE_TITLE) : "Failed to verify content..." + FILE_TITLE;
			
			/*Step 7: Add manual action for node*/
			info("Add manual action for node");
			ecms.goToNode(By.linkText(FILE_TITLE));
			Utils.pause(2000);
			//Check if Category button is shown on action bar
			actBar.addItem2ActionBar("manageActions", actBar.ELEMENT_ACTION_ICON);
			
			actBar.addNewAction(actionName, lifeCycle, actionType);
			assert actBar.isActionPresent("actionName");
			
			/*Clear data*/
			info("Clear data");
			info("Delete node in site explorer");
			/*Delete node in site explorer*/
			navToolBar.goToSiteExplorer();
			click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
			ecms.goToNode(By.linkText(FILE_TITLE));
			actBar.actionsOnActionsOfNode(actionName, "Delete");
			cMenu.deleteData(By.linkText(FILE_TITLE));
			cMenu.deleteData(By.linkText(CONTENT_FOLDER_TITLE));
			
			/*Delete template*/
			info("Delete template");
			navToolBar.goToContentAdministration();
			ecMain.goToTemplateTab();
			magTemplate.deleleTemplate(templateName);
			
			/*Delete action*/
			info("Delete action");
			ecMain.goToActionsTabInContentAdmin();
			magAction.deleteActionType(actionTypeName);
			
			/*Delete script*/
			info("Delete script");
			ecMain.goToScriptsTabInContentAdmin();
			magScript.deleteScript(scriptLabel);	
		}
}
