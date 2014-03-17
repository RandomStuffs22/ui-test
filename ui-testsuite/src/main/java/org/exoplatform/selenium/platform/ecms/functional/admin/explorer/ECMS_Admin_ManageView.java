/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.selenium.platform.ecms.functional.admin.explorer;
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by The eXo Platform SAS
 * Author : Hoang Manh Dung
 * dunghm@exoplatform.com
 * Oct 15, 2012
 */
public class ECMS_Admin_ManageView extends PlatformBase{
	//Platform
	Dialog dialog;
	ManageAccount magAcc;

	//Ecms
	EcmsBase ecms;
	ECMainFunction ecMain;
	ManageView magView;

	String DATA_USER = "john";
	//String DATA_PASS = DATA_PASS;
	
	@BeforeMethod
	public void beforeMethods(){
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with "+ DATA_USER);
		dialog = new Dialog(driver);
		magAcc = new ManageAccount(driver);
		ecms = new EcmsBase(driver);
		ecMain = new ECMainFunction(driver);
		magView = new ManageView(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
		ecMain.goToManageViews();
	}

	@AfterMethod
	public void afterMethods(){
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
		//actions = null;
	}

	/*case 01+15
	 * add new view
	 * delete not used view
	 */
	@Test
	public void test01_AddNewView(){
		info("Add New View");
		//Open Add New View Form
		magView.openAddViewForm("Add View", "Add View");
		//Fill data for Add New View Form
		magView.fillAddNewViewForm("Test Template", "List", "Test", "addCategory", true, true, "Organization/Management", "*");
		info("Delete a view that isn't used"); //Test case 15
		//magView.deleteView("Test Template", "Are you sure you want to delete this view?", true);
		magView.actionOnSelectedView("Test Template", "Delete", true);
	}

	/*
	 * Delete in-use view
	 */
	@Test
	public void test16_DeleteUsedView(){
		info("Delete a view that is in-use");
		//magView.deleteView("Web", "Are you sure you want to delete this view?", false);
		magView.actionOnSelectedView("Web", "Delete", false);
		waitForMessage("Cannot delete Web. It is currently in use.");
		dialog.closeMessageDialog();
	}

	/*
	 * Case 17 + 09
	 * Add new ECM template
	 * Delete ECM template
	 */
	@Test
	public void test17_AddEcmTemplate(){
		info("Add Ecm Template");
		ecMain.goToExplorerTemplates();
		magView.openAddViewForm("Add", "Add Explorer Template");
		magView.fillExplorerTemplateForm("Test Content", "Test Name", "ecm-explorer");
		info("Delete an ECM template"); //Test case 09
		//magView.deleteView("Test Name", "Are you sure you want to delete this template?", true);
		magView.actionOnSelectedView("Test Name", "Delete", true);
	}

	/*
	 * Restore a view at a specific version
	 */
	@Test
	public void test08_RestoreVersion(){
		info("Restore Version");
		String viewName = "Content";
		ecMain.goToExplorerTemplates();
		magView.createVersion(viewName, 2);
		magView.restoreVersion(viewName, 1);
	}
}
