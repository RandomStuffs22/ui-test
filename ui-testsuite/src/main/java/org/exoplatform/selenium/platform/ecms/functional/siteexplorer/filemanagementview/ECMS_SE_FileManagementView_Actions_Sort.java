package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.filemanagementview;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author hangntt
 * August, 2013
 *
 */
public class ECMS_SE_FileManagementView_Actions_Sort extends PlatformBase {

	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	ActionBar actBar;

	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemp;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		navToolBar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		cTemp = new ContentTemplate(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}
	
	/**
	 * Qmetry ID: 74532 & 74533
	 * Sort & Revert the default sort of documents by "Date"
	 */
	@Test
	public void test01_RevertTheDefaultSortOfDocumentsByDate(){
		String dFile = "TestData/ECMS_DMS_SE_Upload_docfile.doc";
		String document = "ECMS_DMS_SE_Upload_docfile";
		String aFolder = "testdocuments";

		info("-- Go to Personal Documents --");
		navToolBar.goToPersonalDocuments();

		ecms.uploadFile(dFile);
		cTemp.createNewFolder(aFolder, folderType.None);

		actBar.sortBy("Date");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", document).replace("${node2}", aFolder));

		actBar.sortBy("Date");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", aFolder).replace("${node2}", document));

		//reset data
		actBar.actionsOnElement(document + ".doc" + "/" + aFolder, actionType.DELETE, true);
	}

	/**
	 * Qmetry ID: 74531 & 74534
	 * Sort & Revert the default sort of documents by "Name"
	 */
	@Test
	public void test02_RevertTheDefaultSortOfDocumentsByName(){
		String aFolder = "adocuments";
		String bFolder = "bdocuments";

		info("-- Go to Personal Documents --");
		navToolBar.goToPersonalDocuments();

		cTemp.createNewFolder(aFolder, folderType.None);
		cTemp.createNewFolder(bFolder, folderType.None);

		info("-- Select & Revert [Sort By] Name --");
		actBar.sortBy("Name");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", aFolder).replace("${node2}", bFolder));
		actBar.sortBy("Name");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", bFolder).replace("${node2}", aFolder));

		//reset data
		actBar.actionsOnElement(bFolder + "/" + aFolder, actionType.DELETE, true);
	}

	/**
	 * Qmetry ID: 74530 & 74535
	 * Sort & Revert the default sort of documents by "Size"
	 */
	@Test
	public void test03_RevertTheDefaultSortOfDocumentsBySize(){
		String dFile = "TestData/ECMS_DMS_SE_Upload_docfile.doc";
		String document = "ECMS_DMS_SE_Upload_docfile";
		String iFile = "TestData/ECMS_DMS_SE_Upload_imgfile.jpg";
		String image = "ECMS_DMS_SE_Upload_imgfile";

		info("-- Go to Personal Documents --");
		navToolBar.goToPersonalDocuments();

		ecms.uploadFile(dFile);
		ecms.uploadFile(iFile);

		info("-- Select & Revert [Sort By] Size --");
		actBar.sortBy("Size");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", document).replace("${node2}", image));
		actBar.sortBy("Size");
		waitForAndGetElement(ecms.ELEMENT_NODE_NAME_CONSECUTIVE.replace("${node1}", image).replace("${node2}", document));

		//reset data
		actBar.actionsOnElement(document + ".doc" + "/" + image + ".jpg" + "/", actionType.DELETE, true);
	}

	/**
	 * Qmetry ID: 74581 & 74587
	 * Show the DOWN/UP arrow for Descending ordering/ Ascending ordering"
	 */
	@Test
	public void test04_ShowTheDownUpArrowForDescendingAscendingOrdering(){
		info("-- Go to Personal Documents --");
		navToolBar.goToPersonalDocuments();
		actBar.sortBy("Size");
		click(actBar.ELEMENT_SORT_BY_BUTTON);
		waitForAndGetElement(actBar.ELEMENT_SORT_UP_ARROW);
		click(actBar.ELEMENT_SORT_BY_BUTTON);
		actBar.sortBy("Size");
		click(actBar.ELEMENT_SORT_BY_BUTTON);
		waitForAndGetElement(actBar.ELEMENT_SORT_DOWN_ARROW);
	}
	
	/**
	 * Qmetry ID: 74547
	 * Show "Sort By" list"
	 */
	@Test
	public void test05_SortBylist(){
		info("-- Go to Personal Documents --");
		navToolBar.goToPersonalDocuments();
		click(actBar.ELEMENT_SORT_BY_BUTTON);
		waitForTextPresent("Date");
		waitForTextPresent("Name");
		waitForTextPresent("Size");
		click(actBar.ELEMENT_SORT_BY_BUTTON);
	}
	
	@AfterMethod
	public void afterMethod() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}