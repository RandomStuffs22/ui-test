package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * June, 2013
 *
 */
public class ECMS_SE_BasicAction_SortAndRevert extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	ContextMenu cMenu;
	ContentTemplate cTemp;

	public String DATA_USER = "john";
	//public String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		cTemp = new ContentTemplate(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 74530 & 74535
	 * Sort & Revert the default sort of documents by "Size"
	 * ================
	 * Qmetry ID: 74531 & 74534
	 * Sort & Revert the default sort of documents by "Name"
	 * ================
	 * Qmetry ID: 74532 & 74533
	 * Sort & Revert the default sort of documents by "Date"
	 *  
	 */
	@Test
	public void test01_SortAndRevertDefaultSortOfDocuments(){
		String dFile = "TestData/ECMS_DMS_SE_Upload_docfile.doc";
		String document = "ECMS_DMS_SE_Upload_docfile";
		String iFile = "TestData/ECMS_DMS_SE_Upload_imgfile.jpg";
		String image = "ECMS_DMS_SE_Upload_imgfile";
		String aFolder = "adocuments";
		String bFolder = "bdocuments";
		
		info("-- Go to Personal Documents --");
		navToolBar.goToPersonalDocuments();
		
		//Test Data
		ecms.uploadFile(dFile);
		ecms.uploadFile(iFile);
		cTemp.createNewFolder(aFolder, folderType.None);
		cTemp.createNewFolder(bFolder, folderType.None);
		
		info("-- Select & Revert [Sort By] Size --");
		actBar.sortBy("Size");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", document).replace("${node2}", image));
		actBar.sortBy("Size");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", image).replace("${node2}", document));
		
		info("-- Select & Revert [Sort By] Name --");
		actBar.sortBy("Name");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", aFolder).replace("${node2}", bFolder));
		actBar.sortBy("Name");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", bFolder).replace("${node2}", aFolder));
		
		info("-- Select & Revert [Sort By] Date --");
		actBar.sortBy("Date");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", image).replace("${node2}", aFolder));
		actBar.sortBy("Date");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", aFolder).replace("${node2}", image));
		
		info("-- Restore data --");
//		actBar.actionsOnElement(document + ".doc", actionType.DELETE);
//		actBar.actionsOnElement(image + ".jpg", actionType.DELETE);
//		actBar.actionsOnElement(aFolder, actionType.DELETE);
//		actBar.actionsOnElement(bFolder, actionType.DELETE);
		actBar.actionsOnElement(document + ".doc" + "/" + image + ".jpg" + "/" + aFolder + "/" + bFolder, actionType.DELETE, true);
	}
	
}