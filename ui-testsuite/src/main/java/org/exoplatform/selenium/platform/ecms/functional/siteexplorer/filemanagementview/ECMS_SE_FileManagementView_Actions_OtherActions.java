package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.filemanagementview;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate.folderType;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu.actionType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
 * @author: PhuongDT
 * @date: 04/09/2013
 */
public class ECMS_SE_FileManagementView_Actions_OtherActions extends PlatformBase {
	//Platform
	ManageAccount magAcc;
	ActionBar actBar;
	NavigationToolbar navToolBar;

	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	SitesExplorer siteExp;
	ContextMenu cMenu;
	Button btn;

	@BeforeMethod
	public void beforeMethods() {
		getDriverAutoSave();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		siteExp = new SitesExplorer(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cMenu= new ContextMenu(driver);
		btn = new Button(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
		navToolBar.goToSiteExplorer();
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Add to favorite a Parent & Child selection ==
	 * Test case ID: 74473
	 * Step 1: Open File Management View
	 * Step 2: Create nodes
	 * Step 3: Add parent and child nodes to favorite
	 */
	@Test
	public void test01_AddToFavoriteAParentAndChildSelection(){
		/*Declare variables*/
		String parentDocument = "addtofavoriteparent1";
		String childDocument = "addtofavoritechild1";

		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		/*Step 2: Create nodes*/
		//Create a document which is not nt:file (parent node)
		info("-- Create parent node --");
		actBar.goToAddNewContent();
		cTemplate.createNewAnnouncement(parentDocument, parentDocument);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		/*Step 3: Add parent and child nodes to favorite*/
		////select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("/");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentDocument)));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", parentDocument)), 2);
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", childDocument)), 2);

		//Click add to favorite
		info("-- Click add to favorite --");
		actBar.goToAddToFavorite();

		//Go to Favorite
		info("-- Go to favorite --");
		actBar.goToNodeByAddressPath("/Favorites");

		//Verify Parent and Child node are added in the list "Favorite"
		info("-- Verify Parent and Child node are added in the list --");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentDocument));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));

		/*clear data*/				
		info("-- Clear data in document folder --");
		actBar.goToNodeByAddressPath("/");
		actBar.actionsOnElement(parentDocument, actionType.DELETE);
	}

	/**
	 * == Delete a Parent & Child selection ==
	 * Test case ID: 74476
	 * Step 1: Open File management view
	 * Step 2: Create folders
	 * Step 3: Delete both of parent and child nodes
	 */
	@Test
	public void test02_DeleteAParentAndChildSelection(){
		/*Declare variables*/
		String parentFolder = "parentfolder02";
		String childDocument = "childdocument02";
		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		/*Step 2: Create folders*/
		//Create parent folder 
		info("-- Create parent folder --");
		cTemplate.createNewFolder(parentFolder, folderType.None);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToNodeByAddressPath("//"+parentFolder);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		/*Step 3: Delete both of parent and child nodes*/
		//select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("/");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));			

		//Delete both of parent and child nodes 
		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(childDocument, actionType.DELETE);
		actBar.actionsOnElement(parentFolder, actionType.DELETE);

		//Verify The Parent is deleted, all subnodes are deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));			
	}

	/**
	 * == Hide actions of a node ==
	 * Test case ID: 74503
	 * Step 1: Open File management view
	 * Step 2: Select a node
	 * Step 3: Not select a node
	 */
	@Test
	public void test03_HideActionsOfANode(){
		/*Declare variables*/
		String dnode = "test03_HideActionsOfANode";

		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		/*Step 2: Select a document mode*/
		//Create a document
		info("-- Create a document --");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(dnode, dnode, dnode);

		//Select a node
		actBar.goToNodeByAddressPath("/");
		info("-- Select a node --");
		check(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", dnode)), 2);
		Utils.pause(2000);

		info("--Verify actions for this node are displayed on top of the top action bar --");
		info("--Verify display action copy node --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_COPY_NODE));
		info("--Verify display action cut node --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_CUT_NODE));
		info("--Verify display action delete node --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_DELETE_NODE));
		info("--Verify display action lock node --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_LOCK_NODE));
		info("--Verify display action rename node --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_RENAME_NODE));
		info("--Verify display action add symlink node --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_ADD_SYMLINK_NODE));
		info("--Verify display action view information node --");
		assertTrue(actBar.isActionsOnActionBarPresent(cMenu.ELEMENT_VIEW_INFORMATION));
		info("--Verify display action add to favorite --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_ADD_TO_FAVORITE_NODE));
		info("--Verify display action download --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_DOWNLOAD_NODE));
		info("--Verify display action copy to url --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_COPY_TO_URL_NODE));
		info("--Verify display action view document --");
		assertTrue(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_VIEW_DOCUMENT_NODE));

		/*Step 3: Not select a document node*/		
		info("-- Not select a node --");
		uncheck(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", dnode)), 2);
		Utils.pause(2000);
		info("-- Actions are hidden --");
		info("--Verify hidden action copy node --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_COPY_NODE));
		info("--Verify hidden action cut node --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_CUT_NODE));
		info("--Verify hidden action delete node --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_DELETE_NODE));
		info("--Verify hidden action lock node --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_LOCK_NODE));
		info("--Verify hidden action rename node --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_RENAME_NODE));
		info("--Verify hidden action add symlink node --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_ADD_SYMLINK_NODE));
		info("--Verify hidden action view information node --");
		assertFalse(actBar.isActionsOnActionBarPresent(cMenu.ELEMENT_VIEW_INFORMATION));
		info("--Verify hidden action add to favorite --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_ADD_TO_FAVORITE_NODE));
		info("--Verify hidden action download --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_DOWNLOAD_NODE));
		info("--Verify hidden action copy to url --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_COPY_TO_URL_NODE));
		info("--Verify hidden action view document --");
		assertFalse(actBar.isActionsOnActionBarPresent(actBar.ELEMENT_VIEW_DOCUMENT_NODE));

		//Clear data
		info("-- Clear data --");
		actBar.actionsOnElement(dnode, actionType.DELETE);
	}

	/**
	 * == Hide folder children by clicking on arrow ==
	 * Test case ID: 74504
	 * Step 1: Show sub-nodes of a node
	 * Step 2: Hide sub-nodes of a node
	 */
	@Test
	public void test04_HideFolderChildrenByClickingOnArrow(){
		/*Declare variables*/
		String parentFolder = "parentfolder04";
		String childDocument = "childdocument04";
		/*Step 1: Show sub-nodes of a node*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		//Create parent folder 
		info("-- Create parent folder --");
		cTemplate.createNewFolder(parentFolder, folderType.None);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToNodeByAddressPath("/"+parentFolder);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		//select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("//");

		String beforePath = waitForAndGetElement(actBar.ELEMENT_ADDRESS_BAR).getAttribute("value").substring(1);
		info("before path = " + beforePath);

		//Click on triangle icon beside a document/ folder
		info("-- Click on triangle icon beside a document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));

		//The triangle position of arrow is top -> down
		info("-- The triangle position of arrow is top -> down --");
		waitForAndGetElement(By.xpath(siteExp.ELEMENT_ARROW_DOWN.replace("${nodeName}", parentFolder)));

		//Documents folder is expanded
		//Children of documents folder are displayed
		info("-- Children of documents folder are displayed --");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));			

		//path bar of current node is not changed
		String afterPath = waitForAndGetElement(actBar.ELEMENT_ADDRESS_BAR).getAttribute("value");
		info("after Path = " + afterPath);
		assert(afterPath.equals(beforePath));

		/*Step 2: Hide sub-nodes of a node*/
		//Click  again on arrow beside that document/ folder
		info("-- Click  again on arrow beside that document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_DOWN.replace("${nodeName}", parentFolder)));

		//The triangle position of arrow is left -> right
		info("-- The triangle position of arrow is left -> right --");
		waitForAndGetElement(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));

		//Children of documents folder are hidden
		//Documents folder is collapsed
		info("-- Children of documents folder are hidden --");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));					

		//clear data 
		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(parentFolder, actionType.DELETE);

		//Verify The Parent is deleted, all subnodes are deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));					
	}

	/**
	 * == Lock a Parent & Child selection ==
	 * Test case ID: 74529
	 * Step 1: Open File Management View
	 * Step 2: Lock both of parent and child nodes
	 */
	@Test
	public void test05_LockAParentAndChildSelection(){
		/*Declare variables*/
		String parentFolder = "parentfolder05";
		String childDocument = "childdocument05";
		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		//Create parent folder 
		info("-- Create parent folder --");
		cTemplate.createNewFolder(parentFolder, folderType.None);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToNodeByAddressPath("/"+parentFolder);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		//select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("/");
		/*Step 2: Lock both of parent and child nodes*/		
		//Click on triangle icon beside a document/ folder
		info("-- Click on triangle icon beside a document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));

		//From action bar, choose [Lock]
		actBar.lockNodeFromActionBar(parentFolder);
		actBar.lockNodeFromActionBar(childDocument);

		//clear data 
		info("-- clear data --");
		//unlock node
		info("-- unlock node --");
		actBar.unLockNodeFromActionBar(parentFolder);
		actBar.unLockNodeFromActionBar(childDocument);

		//Click  again on arrow beside that document/ folder
		info("-- Click  again on arrow beside that document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_DOWN.replace("${nodeName}", parentFolder)));

		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(parentFolder, actionType.DELETE);

		//Verify The Parent is deleted, all subnodes are deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
	}

	/**
	 * == Unlock a Parent & Child selection ==
	 * Test case ID: 74536
	 * Step 1: Open File Management View
	 * Step 2: Lock both of parent and child nodes
	 * Step 3: Unlock parent & child nodes
	 */
	@Test
	public void test06_UnlockAParentAndChildSelection(){
		/*Declare variables*/
		String parentFolder = "parentfolder06";
		String childDocument = "childdocument06";
		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		//Create parent folder 
		info("-- Create parent folder --");
		cTemplate.createNewFolder(parentFolder, folderType.None);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToNodeByAddressPath("/"+parentFolder);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		//select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("/");

		/*Step 2: Lock both of parent and child nodes*/		
		//Click on triangle icon beside a document/ folder
		info("-- Click on triangle icon beside a document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));

		//From action bar, choose [Lock]
		actBar.lockNodeFromActionBar(parentFolder);
		actBar.lockNodeFromActionBar(childDocument);

		/*Step 3: Unlock parent & child nodes*/
		info("-- unlock node --");
		actBar.unLockNodeFromActionBar(parentFolder);
		actBar.unLockNodeFromActionBar(childDocument);

		//clear data 
		info("-- clear data --");
		//Click  again on arrow beside that document/ folder
		info("-- Click  again on arrow beside that document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_DOWN.replace("${nodeName}", parentFolder)));

		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(parentFolder, actionType.DELETE);

		//Verify The Parent is deleted, all subnodes are deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
	}

	/**
	 * == Selection Checkboxes should remain visible on browser resize ==
	 * Test case ID: 74542
	 * Step 1: Open File Management View
	 * Step 2: Resize the browser horizontally
	 */
	@Test
	public void test07_SelectionCheckboxesShouldRemainVisibleOnBrowserResize(){
		/*Declare variables*/
		String folder = "folder07";
		/*Step 1: Open File Management View*/
		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		//Create parent folder 
		info("-- Create a folder --");
		cTemplate.createNewFolder(folder, folderType.None);

		/*Step 2: Resize the browser horizontally (744, 1301)*/		
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(300, 744));
		Utils.pause(3000);
		//Selection checkboxes aren't hidden after a resize, they remain at the right
		info("-- Selection checkboxes aren't hidden after a resize, they remain at the right --");
		waitForAndGetElement(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", folder)),DEFAULT_TIMEOUT,1,2);

		/*Clear data*/
		info("-- Delete data --");
		driver.manage().window().maximize();
		actBar.actionsOnElement(folder, actionType.DELETE);

		//Verify the folder is deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", folder));

	}

	/**
	 * == Add Symlink to a Parent & Child selection ==
	 * Test case ID: 77822
	 * Step 1: Open File Management View
	 * Step 2: Create symlink for parent and child nodes
	 */
	@Test
	public void test08_AddSymlinkTAParentAndChildSelection(){
		/*Declare variables*/
		String parentFolder = "parentfolder08";
		String childDocument = "childdocument08";
		By ELEMENT_PARENT_LINK = By.xpath(ecms.ELEMENT_DOCUMENT_NODE_LIST.replace("${node}", parentFolder+".lnk"));
		By ELEMENT_CHILD_LINK = By.xpath(ecms.ELEMENT_DOCUMENT_NODE_LIST.replace("${node}", childDocument+".lnk"));


		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		//Create parent folder 
		info("-- Create parent folder --");
		cTemplate.createNewFolder(parentFolder, folderType.None);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToNodeByAddressPath("/"+parentFolder);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		//select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("/");

		/*Step 2: Create symlink for parent and child nodes*/		
		//Click on triangle icon beside a document/ folder
		info("-- Click on triangle icon beside a document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));

		//Check on checkboxes at the right of child and parent nodes to select them
		info("-- Check on checkboxes at the right of child and parent nodes to select them --");
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", parentFolder)), 2);
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", childDocument)), 2);

		//From action bar, choose [Add Symlink] icon
		info("-- From action bar, choose [Add Symlink] icon --");
		actBar.goToAddSymlinkTab();

		//The child's symlink is created
		info("-- The child's symlink is created --");
		waitForAndGetElement(ELEMENT_CHILD_LINK);
		//The parent's symlink is created
		info("-- The parent's symlink is created --");
		waitForAndGetElement(ELEMENT_PARENT_LINK);

		//clear data 
		//Click  again on arrow beside that document/ folder
		info("-- Click  again on arrow beside that document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_DOWN.replace("${nodeName}", parentFolder)));

		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(parentFolder, actionType.DELETE);

		//Verify The Parent is deleted, all subnodes are deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
		waitForElementNotPresent(ELEMENT_CHILD_LINK);
		waitForElementNotPresent(ELEMENT_PARENT_LINK);
	}

	/**
	 * == Copy a Parent & Child selection ==
	 * Test case ID: 77823
	 * Step 1: Open File Management View
	 * Step 2: Copy paste both of parent and child nodes
	 */
	@Test
	public void test09_CopyAParentAndChildSelection(){
		/*Declare variables*/
		String sourceFolder = "sourcefolder09";
		String targetFolder = "targetfolder09";
		String parentFolder = "parentfolder09";
		String childDocument = "childdocument09";

		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");


		//Create source folder 
		info("-- Create source folder --");
		cTemplate.createNewFolder(sourceFolder, folderType.None);

		//Create target folder 
		info("-- Create target folder --");
		cTemplate.createNewFolder(targetFolder, folderType.None);

		//Go to source folder
		actBar.goToNodeByAddressPath("/"+sourceFolder);

		//Create parent folder 
		info("-- Create parent folder --");
		cTemplate.createNewFolder(parentFolder, folderType.None);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToNodeByAddressPath("/"+sourceFolder+"/"+parentFolder);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		//select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("/");

		/*Step 2: Copy paste both of parent and child nodes*/		
		//Click on triangle icon beside a document/ folder
		info("-- Click on triangle icon beside a document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", sourceFolder)));
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));

		//Check on checkboxes at the right of child and parent nodes to select them
		info("-- Check on checkboxes at the right of child and parent nodes to select them --");
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", parentFolder)), 2);
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", childDocument)), 2);

		//From action bar, choose [Copy]
		info("-- From action bar, choose [Copy] --");
		waitForAndGetElement(ELEMENT_COPY_NODE);
		click(ELEMENT_COPY_NODE);

		//Choose target folder
		info("-- Choose the target folder --");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", targetFolder));
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", targetFolder)),2);
		Utils.pause(2000);

		//Click on [Paste] from the action bar 
		info("-- Click on [Paste] from the action bar  --");
		waitForAndGetElement(ELEMENT_PASTE_NODE);
		click(ELEMENT_PASTE_NODE);

		//In the target folder, the parent and child nodes are shown
		//Go to target folder
		actBar.goToNodeByAddressPath("/"+targetFolder);
		Utils.pause(2000);
		if (waitForAndGetElement(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)), 3000, 0) != null){
			click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		}
		info("-- the parent and child nodes are shown in target folder --");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));

		//In source folder, the parent and child nodes are still shown
		//Go to source folder
		actBar.goToNodeByAddressPath("/"+sourceFolder);
		Utils.pause(2000);
		if (waitForAndGetElement(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)), 3000, 0) != null){
			click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		}
		info("-- the parent and child nodes are shown in source folder--");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));

		//clear data 
		actBar.goToNodeByAddressPath("/");
		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(sourceFolder, actionType.DELETE);
		actBar.actionsOnElement(targetFolder, actionType.DELETE);

		//Verify The Parent is deleted, all sub nodes are deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", sourceFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", targetFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
	}

	/**
	 * == Cut a Parent & Child selection ==
	 * Test case ID: 77824
	 * Step 1: Open File Management View
	 * Step 2: Cut paste both of parent and child nodes
	 */
	@Test
	public void test10_CutAParentAndChildSelection(){
		/*Declare variables*/
		String sourceFolder = "sourcefolder10";
		String targetFolder = "targetfolder10";
		String parentFolder = "parentfolder10";
		String childDocument = "childdocument10";

		/*Step 1: Open File Management View*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		//Create source folder 
		info("-- Create source folder --");
		cTemplate.createNewFolder(sourceFolder, folderType.None);

		//Create target folder 
		info("-- Create target folder --");
		cTemplate.createNewFolder(targetFolder, folderType.None);

		//Go to source folder
		actBar.goToNodeByAddressPath("/"+sourceFolder);

		//Create parent folder 
		info("-- Create parent folder --");
		cTemplate.createNewFolder(parentFolder, folderType.None);

		//Create a document in the parent node (child node)
		info("-- Create child node --");
		actBar.goToNodeByAddressPath("/"+sourceFolder+"/"+parentFolder);
		actBar.goToAddNewContent();
		cTemplate.createNewFile(childDocument, childDocument, childDocument);

		//select parent and child node
		info("-- Select parent and child node --");
		actBar.goToNodeByAddressPath("/");

		/*Step 2: Cut paste both of parent and child nodes*/		
		//Click on triangle icon beside a document/ folder
		info("-- Click on triangle icon beside a document/ folder --");
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", sourceFolder)));
		click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));

		//Check on checkboxes at the right of child and parent nodes to select them
		info("-- Check on checkboxes at the right of child and parent nodes to select them --");
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", parentFolder)), 2);
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", childDocument)), 2);

		//From action bar, choose [Cut]
		info("-- From action bar, choose [Cut] --");
		waitForAndGetElement(ELEMENT_CUT_NODE);
		click(ELEMENT_CUT_NODE);

		//Choose target folder
		info("-- Choose the target folder --");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", targetFolder));
		click(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", targetFolder)), 2);
		Utils.pause(2000);

		//Click on [Paste] from the action bar 
		info("-- Click on [Paste] from the action bar  --");
		waitForAndGetElement(ELEMENT_PASTE_NODE);
		click(ELEMENT_PASTE_NODE);

		//In the target folder, the parent and child nodes are shown
		//Go to target folder
		actBar.goToNodeByAddressPath("/"+targetFolder);
		Utils.pause(2000);
		if (waitForAndGetElement(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)), 3000, 0) != null){
			click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		}
		info("-- the parent and child nodes are shown in target folder --");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));

		//In source folder, The parent and all its sub children disappear
		//Go to source folder
		actBar.goToNodeByAddressPath("/"+sourceFolder);
		Utils.pause(2000);
		if (waitForAndGetElement(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)), 3000, 0) != null){
			click(By.xpath(siteExp.ELEMENT_ARROW_RIGHT.replace("${nodeName}", parentFolder)));
		}
		info("-- the parent and child nodes are shown in source folder--");
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));

		//clear data 
		actBar.goToNodeByAddressPath("/");

		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(sourceFolder, actionType.DELETE);
		actBar.actionsOnElement(targetFolder, actionType.DELETE);

		//Verify The Parent is deleted, all sub nodes are deleted
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", sourceFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", targetFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", parentFolder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", childDocument));
	}

	/**
	 * == Clear selection ==
	 * Test case ID: 78907
	 * Step 1: Open File Management View
	 * Step 2: Select many items
	 * Step 3: Clear selection
	 */
	@Test
	public void test11_ClearSelection(){
		/*Declare variables*/
		String folder1 = "test11_ClearSelection_1";
		String folder2 = "test11_ClearSelection_2";
		/*Step 1: Open File Management View*/
		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		/*Step 2: Select many items*/	
		//Creat 2 item
		info("-- Create 2 folder items --");
		cTemplate.createNewFolder(folder1, folderType.None);
		cTemplate.createNewFolder(folder2, folderType.None);

		//Check on checkboxes at the right of 2 or more nodes to select them
		info("-- Check on checkboxes at the right of 2 or more nodes to select them --");
		check(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", folder1)), 2);
		WebElement thisElement = driver.findElement(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", folder1)));
		assert (thisElement.isSelected());
		check(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", folder2)), 2);
		thisElement = driver.findElement(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", folder2)));
		assert (thisElement.isSelected());

		//A link [Clear selection] is displayed on the right of the action bar
		info("-- A link [Clear selection] is displayed on the right of the action bar --");
		waitForAndGetElement(actBar.ELEMENT_CLEAR_SELECTION);

		/*Step 3: Clear selection*/
		//Click on a link [Clear selection] at the right of action bar
		info("-- Click on a link [Clear selection] at the right of action bar --");
		click(actBar.ELEMENT_CLEAR_SELECTION);

		//This link [Clear selection] disappear
		info("-- This link [Clear selection] disappear --");
		waitForElementNotPresent(actBar.ELEMENT_CLEAR_SELECTION);

		//Nodes are not selected any more
		thisElement = driver.findElement(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", folder1)));
		assert (!thisElement.isSelected());
		thisElement = driver.findElement(By.xpath(siteExp.ELEMENT_SELECT_CHECKBOX.replace("${name}", folder2)));
		assert (!thisElement.isSelected());

		/*Clear data*/
		info("-- Clear data --");
		actBar.actionsOnElement(folder1, actionType.DELETE);
		actBar.actionsOnElement(folder2, actionType.DELETE);

		//Verify folder node is not present
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", folder1));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", folder2));
	}

	/**
	 * == Upload a file from the [Upload] button in Document ==
	 * Test case ID: 78935
	 * Step 1: Upload a file at root path in Document
	 * PENDING: can't verify status: "The message [1 file(s) waiting] -> [1 file(s) progress] -> [1 file(s) uploaded]"
	 * --> REASON: Need to upload a large file to server to verify it.
	 */
	@Test(groups = "pending")
	public void test12_UploadAFileFromTheUploadButtonInDocument(){
		/*Declare variables*/
		String file1 = "ECMS_DMS_SE_Upload_docfile1.docx";
		String file2 = "ECMS_DMS_SE_Upload_docfile2.docx";
		String file3 = "ECMS_DMS_SE_Upload_docfile3.docx";

		/*Step 1: Upload a file at root path in Document*/				
		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");

		/*-Click on the button [Upload]
				- Choose the file to upload
				- Click on the button [Open]
		 */
		ecms.uploadMultiFileSerial(file1, file2, file3);

		//A specific box is shown at the bottom
		//The delete icon [X] appears
		//At the top of the specific zone:
		//Abort all [X] and [?] icons are displayed
		info("-- The  [X] iconis displayed --");
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_CLOSE_TAB, DEFAULT_TIMEOUT,1);

		info("-- The  [?] icons is appears --");
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_INFORMATION_ICON, DEFAULT_TIMEOUT,1);
		//The message [1 file(s) waiting] -> [1 file(s) progress] -> [1 file(s) uploaded]
		info("-- The message [1 file(s) waiting] -> [1 file(s) progress] -> [1 file(s) uploaded] --");
		waitForAndGetElement(ecms.ELEMENT_MESSAGE_FILE_UPLOADED, DEFAULT_TIMEOUT,1);
		//File is uploaded successfully

		/*Clear data*/
		actBar.actionsOnElement(file1.substring(0, file1.indexOf('.')), actionType.DELETE);
		actBar.actionsOnElement(file2.substring(0, file1.indexOf('.')), actionType.DELETE);
		actBar.actionsOnElement(file3.substring(0, file1.indexOf('.')), actionType.DELETE);

	}

	/**
	 * == Drag and Drop a file from Desktop to a document ==
	 * Test case ID: 78936
	 * Step 1: Drag drop a file in a document
	 * PENDING: refer to issue https://jira.exoplatform.org/browse/FQA-1245
	 */
	@Test(groups = "pending")
	public void test13_DragAndDropAFileFromDesktopToADocument(){
		/*Declare variables*/
		String targetNode = "targetNode13";

		/*Step 1: Upload a file at root path in Document*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Select target node is a document (document type is Web Content: Illustrated Web Content, Web Content, Web Link)
		info("-- Create target node --");
		actBar.goToAddNewContent();
		click(By.xpath("//*[@class='templateLabel']//*[text()='Web Link']"));
		cTemplate.createNewLink(targetNode, targetNode);

		info("-- Select target node --");
		actBar.goToNodeByAddressPath("/"+targetNode);

		//Drag and drop a file from the desktop to a document 

		//Drop zone appears at the bottom and on the specific folders to start upload process
		//File is uploaded successfully in this document
	}

	/**
	 * == View Permission of folder ==
	 * Test case ID: 78938
	 * Step 1: Select node to view permission of node
	 * Step 2: Open 'Permission Management' pop-up
	 */
	@Test
	public void test14_ViewPermissionOfFolder(){
		/*Declare variables*/
		String folder = "folder14";
		/*Step 1: Select node to view permission of node*/
		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Create a folder
		info("-- Create a folder --");
		cTemplate.createNewFolder(folder, folderType.None);

		//Select a folder node
		info("-- Select folder node --");
		actBar.goToNodeByAddressPath("/"+folder);

		/*Step 2: Open 'Permission Management' pop-up*/
		//Click on [Permissions] icon
		info("-- Click on [Permissions] icon --");
		actBar.goToNodePermissionManagement();

		//'Permission Management' popup appears
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_POPUP);

		//Permission of node are listed
		waitForAndGetElement(actBar.ELEMENT_PERMISSION_MANAGEMENT_GRID);

		//close popup
		btn.close();

		/*Clear data*/
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		info("-- Delete folder node --");
		actBar.actionsOnElement(folder, actionType.DELETE);

		//Verify folder node is not present
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", folder));
	}


	/**
	 * == Export a folder ==
	 * Test case ID: 78940
	 * Step 1: Create a node
	 * Step 2: Open form to export node
	 * Step 3: Select  format for exported document
	 * Step 4: Export node
	 */
	@Test
	public void test15_ExportAFolder(){
		/*Declare variables*/
		String folder = "folder15";
		/*Step 1: Create a node*/		
		//Add icon "Export" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("exportNode", actBar.ELEMENT_EXPORT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Create a folder
		info("-- Create a folder --");
		cTemplate.createNewFolder(folder, folderType.None);

		//Select a folder node
		info("-- Select folder node --");
		actBar.goToNodeByAddressPath("/"+folder);

		/*Step 2: Open form to export node*/
		/*Step 3: Select  format for exported document*/
		/*Step 4: Export node*/
		/*Note: Use function getDriverAutoSave() instead of initSeleniumTest() in method beforeMethods
		 * to set output folder of export function
		 */
		//Export node in zip file, systemview, 
		info("Export node in zip file, systemview");
		actBar.exportNode(true, true, false);

		//Node is exported into your computer
		assert checkFileExisted("sysview.zip");

		/*Clear data*/
		//delete  node
		click(ecms.ELEMENT_BACK_PREVIOUS_NODE);
		info("-- Delete folder node --");
		actBar.actionsOnElement(folder, actionType.DELETE);
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", folder));

		//delete file on computer
		deleteFile("TestOutput/"+"sysview.zip");
		assert (!checkFileExisted("sysview.zip"));
	}

	/**
	 * == Import a node ==
	 * Test case ID: 78941
	 * Step 1: Create a node
	 * Step 2: Export a node
	 * Step 3: Open 'Import Node' pop-up
	 * Step 4: Browse an .xml file to import
	 * Step 5: Select format of imported node
	 * Step 6: Import node
	 */
	@Test
	public void test16_ImportANode(){
		/*Declare variables*/
		String folder = "folder16";
		String subfolder = "subfolder16";
		String filePath = "TestData/TestOutput/sysview.xml";
		/*Step 1: Create a node*/		
		//Add icon "Export" and "Import" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("importNode", actBar.ELEMENT_IMPORT_LINK, "Admin", "Admin");
		actBar.addItem2ActionBar("exportNode", actBar.ELEMENT_EXPORT_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Create a folder
		info("-- Create a folder --");
		cTemplate.createNewFolder(folder, folderType.None);
		cTemplate.createNewFolder(subfolder, folderType.None);

		//Select a folder node to export
		info("-- Select sub folder node --");
		actBar.goToNodeByAddressPath("/"+subfolder);

		/*Step 2: Export a node*/				
		//Export node in xml file, systemview, 
		info("Export node in xml file, systemview");
		actBar.exportNode(true, false, false);

		//Node is exported into your computer
		assert checkFileExisted("sysview.xml");

		/* Step 3: Open 'Import Node' pop-up */
		/* Step 4: Browse an .xml file to import*/
		/* Step 5: Select format of imported node*/
		/* Step 6: Import node*/
		//Import node
		//Select a folder node to import
		info("-- Select folder node --");
		actBar.goToNodeByAddressPath("/"+folder);

		info("Import node");
		actBar.importNode(filePath,"","Create New",false);

		//Node is imported into Content folder
		info("Node is imported into Content folder");
		waitForAndGetElement(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", subfolder));

		/*Clear data*/
		//Go to root node
		actBar.goToNodeByAddressPath("/");
		info("-- Delete folder node --");
		actBar.actionsOnElement(folder, actionType.DELETE);
		actBar.actionsOnElement(subfolder, actionType.DELETE);

		//Verify folder node is not present
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", folder));
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", subfolder));

		//delete file on computer
		deleteFile("TestOutput/"+"sysview.xml");
		assert (!checkFileExisted("sysview.xml"));
	}

	/**
	 * == Add category for document ==
	 * Test case ID: 78942
	 * Step 1: Create node
	 * Step 2: Open form to add category
	 * Step 3: Add category for document
	 */
	@Test
	public void test17_AddCategoryForDocument(){
		/*Declare variables*/
		String document = "document17";
		String categoryPath = "Defense";
		String categoryTree = "powers";
		String categoryNode = "Healing";

		/*Step 1: Create node*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Add icon "Categories" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("manageCategories", actBar.ELEMENT_CATEGORIES_LINK, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		//Create a document
		info("-- Create document --");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(document, document, document);

		/*Step 2: Open form to add category*/
		/*Step 3: Add category for document*/
		//Add category for node
		actBar.addCategoryForNode(categoryTree, false, categoryPath, categoryNode);
		actBar.goToManageCategories();
		waitForAndGetElement(By.xpath("//*[text() = '" + categoryTree + "/" + categoryPath + "/" + categoryNode+"']"));
		btn.close();

		/*Clear data*/
		info("-- Clear data --");
		//Go to root node
		actBar.goToNodeByAddressPath("/");

		//Delete both of parent and child nodes 
		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(document, actionType.DELETE);
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", document));
	}

	/**
	 * == Publish content with setting publication ==
	 * Test case ID: 78943
	 * Step 1: Show site explorer by legal user
	 * Step 2: Create new node with draft status
	 * Step 3: Show Manage Publication form of a draft node
	 * Step 4: Change status to Published
	 */
	@Test
	public void test18_PublishContentWithSettingPublication(){
		/*Declare variables*/
		String document = "document18";
		/*Step 1: Show site explorer by legal user*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Admin", "Admin");

		//Add icon "managePublications" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Admin");
		actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION_ICON, "Admin", "Admin");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		/*Step 2: Create new node with draft status*/
		//Create a document
		info("-- Create document --");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(document, document, document);

		/*Step 3: Show Manage Publication form of a draft node*/
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_FIRST_REVISION_DATE);
		btn.close();

		/*Step 4: Change status to Published*/
		////*[@class = 'activeStatus']/*[text()='Published']/../a[@class='node']
		actBar.changeStatusPublication("Published");

		/*Clear data*/
		info("-- Clear data --");
		//Go to root node
		actBar.goToNodeByAddressPath("/");

		//Delete both of parent and child nodes 
		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(document, actionType.DELETE);
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", document));
	}

	/**
	 * == Add thumbnail image for node ==
	 * Test case ID: 78944
	 * Step 1: Go to File management view
	 * Step 2: Create node
	 * Step 3: Open form to add thumbnail image
	 * Step 4: Add image
	 * PENDING: refer issue https://jira.exoplatform.org/browse/FQA-1057: [SELENIUM] Cannot upload thumbnail on ecms
	 */
	@Test(groups = "pending")
	public void test19_AddThumbnailImageForNode(){
		/*Declare variables*/
		String document = "document19";
		String image = "TestData/test19_AddThumbnailImageForNode.jpg";
		/*Step 1: Go to File management view*/
		//Add icon "new content" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Icons");
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, "Icons", "Icons");

		//Add icon "overloadThumbnail" to action bar
		navToolBar.goToPersonalDocuments();
		actBar.goToViewMode("Icons");
		actBar.addItem2ActionBar("overloadThumbnail", actBar.ELEMENT_OVERLOAD_THUMBNAIL, "Icons", "Icons");

		//Go to intranet/documents
		navToolBar.goToPersonalDocuments();

		//Click on [Admin] view icon or [List] view icon
		actBar.goToViewMode("Admin");
		//actBar.goToViewMode("List");

		/*Step 2: Create node*/
		//Create a document
		info("-- Create document --");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(document, document, document);

		/*Step 3: Open form to add thumbnail image*/
		/*Step 4: Add image*/
		//Add category for node
		actBar.uploadThumbnail(image);
		//Go to root node
		actBar.goToNodeByAddressPath("/");
		actBar.goToViewMode("Admin");
		/*Clear data*/
		info("-- Clear data --");

		//Delete both of parent and child nodes 
		info("-- Delete both of parent and child nodes --");
		actBar.actionsOnElement(document, actionType.DELETE);
		waitForElementNotPresent(ecms.ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", document));
	}
}