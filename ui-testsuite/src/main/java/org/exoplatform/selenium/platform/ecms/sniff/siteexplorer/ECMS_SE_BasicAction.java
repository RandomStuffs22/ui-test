package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
/**
 * @date: 21/05/2013
 * @author thuntn
 *
 */
public class ECMS_SE_BasicAction extends PlatformBase {
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ContentTemplate cTemplate;
	EcmsBase ecms;
	ContextMenu cMenu;
	ManageAccount magAcc;
	public final String DATA_USER = "john";
	//public final String DATA_PASS = DATA_PASS;
	ManageAlert alt;
	SitesExplorer siteExp;
	Dialog dialog;

	/**caseID: 65824 - Add symlink for a node
	 * Step 1: Add symlink for a node
	 * Step 2 : Open destination
	 * Step 3 : Add file in the node
	 */
	@Test
	public void test01_AddSymlinkForNode() {
		String node1= "test01_AddSymlinkForNode1";
		String node2= "test01_AddSymlinkForNode2";
		String symlink = "Symlink65824";
		String webContent= "test01_webcontent";

		info("Add symlink for a node");
		//Add "Add symlink" into action bar if there is not available yet.
		actBar.addItem2ActionBar("addSymLink", actBar.ELEMENT_ACTION_BAR_ADD_SYMLINK);

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		cTemplate.createNewFolder(node2, folderType.Content);

		//Add symlink for node2 inside node1
		ecms.goToNode(node1);
		actBar.addSymlink("collaboration", "sites/" + node2, symlink);

		//Go to node2, add a document
		ecms.goToNode(node2);
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(webContent, webContent, "", webContent, "", "");

		//Check if a symlink is added into symlink of node2
		ecms.goToNode(node1);
		ecms.goToNode(symlink);
		waitForAndGetElement(siteExp.ELEMENT_SE_NODE.replace("{$node}", webContent));

		//Delete data
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
	}

	/**caseID: 65828 - Copy/paste a node
	 * Step 1: Copy/paste a node
	 */
	@Test
	public void test02_CopyPasteNode() {
		String node1= "test02_CopyPasteNode1";
		String node2= "test02_CopyPasteNode2";
		String node1New = cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", node2).replace("${title2}", node1) ;

		info("Copy/paste a node");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);

		cTemplate.createNewFolder(node2, folderType.Content);

		//Copy node1 to node2
		cMenu.copyAndPasteNode(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1), siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));

		ecms.goToNode(node2);
		waitForAndGetElement(node1New);

		//Delete data
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
	}


	/**caseID: 65840 - Cut/paste a node
	 * Step 1: Cut/paste a node
	 */

	@Test
	public void test03_CutPasteNode() {
		String node1= "test03_CutPasteNode1";
		String node2= "test03_CutPasteNode2";
		String node1New = cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", node2).replace("${title2}", node1) ;

		info("Cut/paste a node");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		cTemplate.createNewFolder(node2, folderType.Content);

		//Cut node1 into node2
		cMenu.cutAndPasteNode(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1), siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
		waitForElementNotPresent(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));

		//Check if node1 is in node2 
		ecms.goToNode(node2);
		waitForAndGetElement(node1New);

		//Delete data
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));

	}

	/**caseID: 65841 - Download and allow edition
	 * Step 1: Download and Allow Edition
	 */
	@Test
	public void test04_DownloadAllowEdition() {
		String node1= "test04DownloadAllowEdition1";
		String node2= "test04DownloadAllowEdition2";
		By bNode = By.xpath("//*[@id='main']/a[2]");

		info("Download and allow edition");

		//Create node1, node2 in node1
		cTemplate.createNewFolder(node1, folderType.Content);
		ecms.goToNode(node1);
		cTemplate.createNewFolder(node2, folderType.Content);

		//Click on "Download and allow edition" of node1 in a new window
		rightClickOnElement(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		if(isElementPresent("//a[@class='itemDownload']"))
			click("//a[@class='itemDownload']");
		else if(isElementPresent("//a[@class='uiIconDownload uiIconLightGray']"))
			click("//a[@class='uiIconDownload uiIconLightGray']");

		//Swith to new window containing Webdav of node1
		switchToNewWindow();
		//Check if node2 is shown in node1
		WebElement node2WebDav= waitForAndGetElement(bNode);
		info("Text is "+node2WebDav.getText());
		assert node2WebDav.getText().equalsIgnoreCase(node2): "Webdav doesn't contains correct child-node";

		switchToParentWindow();
		//Delete data
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));

	}

	/**caseID: 65842 - Drag and drop a node
	 *   Step 1: Drag and drop a node
	 */
	@Test
	public void test05_DragAndDropNode() {
		String node1= "test05DragAndDropNode1";
		String node2= "test05DragAndDropNode2";
		String node2New = cMenu.ELEMENT_FILE_TITLE_AUX.replace("${title1}", node1).replace("${title2}", node2) ;
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		info("Drag and drop a node");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		cTemplate.createNewFolder(node2, folderType.Content);

		//Drag and drop node2 into node1
		dragAndDropToObject(siteExp.ELEMENT_SE_NODE.replace("{$node}",node2), siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		alt.waitForConfirmation("Are you sure you want to move?");

		//Check if node2 is in node1
		ecms.goToNode(bNode1);
		waitForAndGetElement(node2New);

		//Delete data
		cMenu.deleteDocument(bNode1);

	}
	
	/**caseID: 65847 - Lock a node
	 * Step 1: Lock a node
	 */
	@Test
	public void test06_LockNode() {
		String node1= "test06LockNode1";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		info("Lock a node");
		//Create file document
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1);

		//Lock node
		cMenu.contextMenuAction(bNode1, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		//Check locked node
		cMenu.isLockedNode(bNode1);

		//Delete data
		cMenu.deleteDocument(bNode1);

	}

	/**caseID: 67874 - Unlock a node
	 *   Step 1: Unlock a node
	 */
	@Test
	public void test07_UnlockNode() {
		String node1 = "test07UnlockNode1";
		String bNode1 = siteExp.ELEMENT_SE_NODE.replace("{$node}",node1); 
		//ecms.ELEMENT_DATA_TITLE.replace("${dataTitle}", node1);
		String bNodeLock = cMenu.ELEMENT_FILE_LOCKED_BY_ADMIN.replace("${titleOfFile}", node1);
		info("Unlock a node");

		//Create file document
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1);

		//Lock node
		cMenu.contextMenuAction(bNode1, cMenu.ELEMENT_CONTEXT_MENU_LOCK);

		//Check locked node
		waitForAndGetElement(bNodeLock);
		cMenu.isLockedNode(bNodeLock);

		//Unlock node
		cMenu.contextMenuAction(bNodeLock, cMenu.ELEMENT_MENU_UNLOCK);
		waitForAndGetElement(bNode1);

		//Delete data
		cMenu.deleteDocument(bNode1);

	}

	/**caseID: 65849 - Manage Actions on WebDAV
    This case is blocked. Because this case requires to access and control Network place. But we cannot access and control anything other than web browser  
	 */

	/**caseID: 65852: - Delete Clipboard
	 * Step 1: Delete Clipboard
	 */
	@Test
	public void test08_DeleteClipboard() {
		String node1= "test08DeleteClipboard1";
		String node2= "test08DeleteClipboard2";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		By bNode2= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
		info("Delete Clipboard");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		cTemplate.createNewFolder(node2, folderType.Document);

		//Copy node1, cut node2
		cMenu.copyNode(bNode1);
		cMenu.cutNode(bNode2);

		//Open clipboard
		click(siteExp.ELEMENT_CLIPBOARD_ICON);
		waitForAndGetElement(siteExp.ELEMENT_CLIPBOARD_NODE.replace("{$node}", node1.toLowerCase()));
		waitForAndGetElement(siteExp.ELEMENT_CLIPBOARD_NODE.replace("{$node}", node2.toLowerCase()));

		//Delete action copy, cut
		click(siteExp.ELEMENT_CLIPBOARD_DELETE_ICON.replace("{$node}", node1.toLowerCase()));
		click(siteExp.ELEMENT_CLIPBOARD_DELETE_ICON.replace("{$node}", node2.toLowerCase()));

		//Open File explorer tab
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);

		//Delete data
		cMenu.deleteDocument(bNode1);
		cMenu.deleteDocument(bNode2);

	}

	/**caseID: 65852: - Paste Clipboard
	 * Step 1: Paste Clipboard
	 */
	@Test
	public void test09_PasteClipboard() {
		String node1= "test09PasteClipboard1";
		String node2= "test09PasteClipboard2";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		By bNode2= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node2));
		info("Paste Clipboard");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);
		cTemplate.createNewFolder(node2, folderType.Document);

		//Copy node1, cut node2
		cMenu.copyNode(bNode1);
		cMenu.cutNode(bNode2);

		//Open clipboard
		click(siteExp.ELEMENT_CLIPBOARD_ICON);
		waitForAndGetElement(siteExp.ELEMENT_CLIPBOARD_NODE.replace("{$node}", node1.toLowerCase()));
		waitForAndGetElement(siteExp.ELEMENT_CLIPBOARD_NODE.replace("{$node}", node2.toLowerCase()));

		//Paste action copy, cut
		click(siteExp.ELEMENT_CLIPBOARD_PASTE_ICON.replace("{$node}", node1.toLowerCase()));
		click(siteExp.ELEMENT_CLIPBOARD_PASTE_ICON.replace("{$node}", node2.toLowerCase()));

		//Open File explorer tab
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);

		//Delete data
		cMenu.contextMenuAction(bNode1, cMenu.ELEMENT_MENU_DELETE);
		dialog.deleteInDialog();
		cMenu.deleteDocument(bNode1);
		cMenu.deleteDocument(bNode2);

	}

	/**caseID: 65870: - Rename a node
	 * Step 1: Rename a node
	 */
	@Test
	public void test10_RenameNode() {
		String node1= "test10RenameNode1";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node1));
		String newNode="New test10RenameNode1";
		info("Rename node");

		//Create node1, node2
		cTemplate.createNewFolder(node1, folderType.Content);

		//Rename node1 with new name as newnode
		cMenu.contextMenuAction(bNode1, cMenu.ELEMENT_MENU_RENAME_NODE, newNode);

		//Delete data
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", newNode));

	}

	@BeforeMethod
	public void beforeMethod() {
		getDriverAutoOpenWindow();
		driver.get(baseUrl);
		dialog = new Dialog(driver);
		navToolBar = new NavigationToolbar(driver,this.plfVersion);
		magAcc = new ManageAccount(driver,this.plfVersion);
		magAcc.signIn(DATA_USER, DATA_PASS);
		navToolBar.goToSiteExplorer();
		actBar = new ActionBar(driver,this.plfVersion);
		cTemplate = new ContentTemplate(driver,this.plfVersion);
		ecms = new EcmsBase(driver,this.plfVersion);
		cMenu = new ContextMenu(driver,this.plfVersion);
		alt = new ManageAlert(driver,this.plfVersion);
		siteExp = new SitesExplorer(driver,this.plfVersion);
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}