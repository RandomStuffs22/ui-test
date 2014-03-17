package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.filemanagementview;

import static org.exoplatform.selenium.TestLogger.info;
import static org.testng.AssertJUnit.assertTrue;

import org.openqa.selenium.WebElement;
import org.exoplatform.selenium.Dialog;
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
 * Created date: 04-Sep-2013
 * author: havtt
 * 
 * */
public class ECMS_SE_FileManagementView_Actions_Select extends PlatformBase{

	//Platform
	ManageAccount magAcc;
	NavigationToolbar navToolBar;
	Dialog Dialog;

	//ECMS
	EcmsBase ecms;
	ContentTemplate cTemplate;
	ActionBar actBar;
	ContextMenu cMenu;

	public String DATA_USER = "john";
	//public String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethod(){
		initSeleniumTest();
		driver.get(baseUrl);
		actBar = new ActionBar(driver);
		magAcc = new ManageAccount(driver);		
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cTemplate = new ContentTemplate(driver);
		cMenu= new ContextMenu(driver);
	}

	@AfterMethod
	public void afterMethod() {
		info("-- User signOut --");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseID: 74543
	 * author: havtt
	 * Show "Clear Selection" link
	 */
	@Test
	public void test01_showClearSelection(){
		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();

		info("Tick 3 check boxes");
		String[] nodes = {"Documents", "Favorites", "Music"};
		for (int i=0 ; i < nodes.length ; i++){
			click(ecms.ELEMENT_UI_CHECKBOX.replace("${element}", nodes[i]), 2);
			WebElement element = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}", nodes[i]), 3000, 1, 2);
			assert element.isSelected() : "Failed to checkbox...." + nodes[i];  		
		}
		
		//Check if Clear Selection is displayed or not
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_CLEAR_SELECTION));
	}

	/**CaseID: 74541
	 * author: havtt
	 * Select multiple nodes
	 */
	@Test
	public void test02_selectMultipleNodes() {
		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();
		
		//Choose node 1
		info("Choose Document node");
		WebElement document_blankspace = waitForAndGetElement(ecms.ELEMENT_NODE_ROW_VIEW.replace("${nodeName}", "Documents"));
		document_blankspace.click();
		
		info("Verify Document checkbox");
		WebElement document_checkbox = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}","Documents"), 3000, 1, 2);
		assert document_checkbox.isSelected() : "Document unchecked";

		//Choose node 2
		info("Choose Videos node");
		WebElement videos = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}","Videos"), 3000, 1, 2);
		videos.click();
		info("Verify Videos checkbox");
		assert videos.isSelected() : "Video unchecked";

		//Confirm again status of node 1's check box
		assert document_checkbox.isSelected() : "Document unchecked after checking node 2";
	}

	/**CaseID: 74552
	 * author: havtt
	 * Show actions of a node
	 */
	@Test
	public void test03_showActionNode() {
		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();
		
		//Check a node
		info("Choose Music node");
		WebElement music = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}","Music"), 3000, 1, 2);
		music.click();
		assert music.isSelected() : "Music unchecked";

		//Verify context menu
		info("Verify menu");
		waitForAndGetElement(actBar.ELEMENT_ECMS_CUT_NODE);
		waitForAndGetElement(actBar.ELEMENT_ECMS_COPY_NODE);
		waitForAndGetElement(actBar.ELEMENT_DELETE_NODE);
		waitForAndGetElement(actBar.ELEMENT_LOCK_NODE);
		waitForAndGetElement(actBar.ELEMENT_RENAME_NODE);
		waitForAndGetElement(actBar.ELEMENT_ADD_SYMLINK_NODE);
		waitForAndGetElement(cMenu.ELEMENT_VIEW_INFORMATION);
		waitForAndGetElement(actBar.ELEMENT_DOWNLOAD_NODE);
	}

	/**CaseID: 74558
	 * author: havtt
	 * Show commons actions for multiple items
	 */
	@Test
	public void test04_showCommonActionNode() {
		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();
		
		//Choose node 1
		info("Choose Document node");
		WebElement document = waitForAndGetElement(ecms.ELEMENT_NODE_ROW_VIEW.replace("${nodeName}", "Documents"));
		document.click();
		info("Verify Document checkbox");
		WebElement document_checkbox = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}","Documents"), 3000, 1, 2);
		assert document_checkbox.isSelected() : "Document unchecked";

		//Choose node 2
		info("Choose Videos node");
		WebElement videos = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}","Videos"), 3000, 1, 2);
		videos.click();
		info("Verify Videos checkbox");
		assert videos.isSelected() : "Video unchecked";

		//Verify context menu
		info("Verify menu");
		waitForAndGetElement(actBar.ELEMENT_CUT_NODE_ICON);
		waitForAndGetElement(actBar.ELEMENT_COPY_NODE_ICON);
		waitForAndGetElement(actBar.ELEMENT_DELETE_NODE_ICON);
		waitForAndGetElement(actBar.ELEMENT_LOCK_ICON);
		waitForAndGetElement(actBar.ELEMENT_ADD_SYMLINK_LIST_VIEW);
		
		waitForElementNotPresent(actBar.ELEMENT_RENAME_NODE);
		waitForElementNotPresent(cMenu.ELEMENT_VIEW_INFORMATION);
		waitForElementNotPresent(actBar.ELEMENT_DOWNLOAD_NODE);
	}

	/**CaseID: 74539
	 * author: havtt
	 * Select all items in the current view
	 */
	@Test
	public void test05_selectAllCheckbox(){
		String PARENT_DOCUMENT_FOLDER_TITLE = "ECMS_SE_DocumentFolder_01";
		String NODE1_TITLE = "NODE1";
		String NODE2_TITLE = "NODE2";

		info("Go to Personal Document");
		navToolBar.goToPersonalDocuments();

		info("Create a new folder");
		cTemplate.createNewFolder(PARENT_DOCUMENT_FOLDER_TITLE, folderType.None);

		info("-- Create child nodes --");
		ecms.goToNode(PARENT_DOCUMENT_FOLDER_TITLE, true);
		cTemplate.createNewFolder(NODE1_TITLE, folderType.None);
		cTemplate.createNewFolder(NODE2_TITLE, folderType.None);

		info("Check all check boxes");
		click(ecms.ELEMENT_VIEW_CHECKBOX_ALL, 2);
		WebElement checkall = waitForAndGetElement(ecms.ELEMENT_VIEW_CHECKBOX_ALL, 3000, 1, 2);
		assert checkall.isSelected() : "All unchecked";
		
		info("Verify node 1, node 2");
		WebElement node1 = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}", NODE1_TITLE), 3000, 1, 2);
		WebElement node2 = waitForAndGetElement(ecms.ELEMENT_UI_CHECKBOX.replace("${element}", NODE2_TITLE), 3000, 1, 2);
		assert node1.isSelected() : "node1 unchecked";
		assert node2.isSelected() : "node1 unchecked";

		//Delete all nodes
		info("Delete all created");
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		actBar.actionsOnElement(PARENT_DOCUMENT_FOLDER_TITLE, actionType.DELETE);
	}
}