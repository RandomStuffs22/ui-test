package org.exoplatform.selenium.platform.ecms.contentexplorer;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Dialog;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageView;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.HashMap;

import static org.exoplatform.selenium.TestLogger.info;


/**
 * 
 * @author vuna2
 *
 */
public class ActionBar extends EcmsBase{

	public ActionBar(WebDriver dr, String...plfVersion) {
		super(dr);
		this.plfVersion = plfVersion.length>0?plfVersion[0]:"4.0";
		// TODO Auto-generated constructor stub
	}

	Button button = new Button(driver);
	Dialog dialog = new Dialog(driver);
	ManageAlert alt = new ManageAlert(driver);
	NavigationToolbar navToolBar = new NavigationToolbar(driver);
	ManageAccount magAcc = new ManageAccount(driver,this.plfVersion);
	ManageView magView = new ManageView(driver);
	ContextMenu cMenu = new ContextMenu(driver);
	ManageAlert alert = new ManageAlert(driver);
	ECMainFunction ecMain = new ECMainFunction(driver);
	PageEditor ePage = new PageEditor(driver, this.plfVersion);
	/*
	 * Added by PhuongDT
	 * Date: 04/09/2013
	 * */
	public final By ELEMENT_MORE_LINK_ACTION_BAR = By.xpath("//*[@class='uiContextMenuContainer']//*[@style='display: block;']//*[contains(text(), 'More')]");
	public final By ELEMENT_RENAME_NODE = By.className("uiIconEcmsRename");
	public final By ELEMENT_DOWNLOAD_NODE = By.className("uiIconDownload");
	public final By ELEMENT_COPY_TO_URL_NODE = By.className("uiIconEcmsCopyUrlToClipboard");
	public final By ELEMENT_ADD_TO_FAVORITE_NODE = By.className("uiIconEcmsAddToFavourite");
	public final By ELEMENT_VIEW_DOCUMENT_NODE = By.className("uiIconEcmsViewDocument");
	public final By ELEMENT_DELETE_NODE = By.xpath("//*[@id='ECMContextMenu']//*[@class='uiIconEcmsDelete']");
	public final By ELEMENT_ADD_SYMLINK_NODE = By.xpath("//*[@id='ECMContextMenu']//*[@class='uiIconEcmsAddSymLink']");
	public final By ELEMENT_LOCK_NODE = By.xpath("//*[@id='ECMContextMenu']//i[@class='uiIconEcmsLock']");
	public final By ELEMENT_UNLOCK_NODE = By.xpath("//*[@id='ECMContextMenu']//i[@class='uiIconEcmsUnlock']");
	/*End added*/

	/*
	 * @Added by: PhuongDT
	 * @date: 27/08/2013
	 * @Function: Manage Action Bar
	 */
	public final By ELEMENT_ACTION_ICON = By.className("uiIconEcmsManageActions");
	public final By ELEMENT_ADD_ACTION_TAB = By.xpath("//*[text()='Add Action']");
	public final By ELEMENT_AVAILABLE_ACTIONS = By.xpath("//*[text()='Available Actions']");
	public final By ELEMENT_ACTION_NAME = By.id("actionName");
	public final By ELEMENT_ACTION_LIFE_CYCLE = By.name("lifecycle");
	public final By ELEMENT_ACTION_TYPE = By.name("actionType");
	public final By ELEMENT_ACTION_METADATA = By.name("metadata");
	public final By ELEMENT_ACTION_NODE_TYPE = By.name("nodetypes0");
	public final By ELEMENT_ACTION_DESCRIPTION = By.name("description");
	public final By ELEMENT_ACTION_AFFECTED_NODE_TYPE = By.name("affectedNodetypes0");
	public final By ELEMENT_ACTION_NODE_TYPE_SEARCH = By.xpath("//*[@class='uiIconSearch']");
	public final By ELEMENT_ACTION_AFFECTED_NODE_TYPE_SEARCH = By.xpath("//*[@text()='Affected Node Types:']//*[@class='uiIconSearch']");
	public final By ELEMENT_ACTION_SELECT_ALL_DOC = By.xpath(".//*[@id='ALL_DOCUMENT_TYPES']");
	public final String ELEMENT_ACTION_AVAILABLE_ACTION = "//*[@id='UIActionList']//*[@class='text' and contains(text(), '${actionname}')]";
	public final String ELEMENT_EDIT_ACTION_ICON = "//*[@id='UIActionList']//*[@class='text' and contains(text(), '${actionname}')]/../..//*[contains(@class, 'uiIconEdit')]";
	public final String ELEMENT_DELETE_ACTION_ICON = "//*[@id='UIActionList']//*[@class='text' and contains(text(), '${actionname}')]/../..//*[contains(@class, 'uiIconDelete')]";
	/*End Add*/

	//Export Form
	public By ELEMENT_DOC_VIEW = By.xpath("//form[@id='UIExportNode']//input[@name='format' and @value='docview']");
	public By ELEMENT_ZIP = By.name("zip");
	public By ELEMENT_EXPORT_VERSION = By.xpath("//button[text()='Export Version History']");
	public By ELEMENT_EXPORT = By.xpath("//button[text()='Export']");
	public String ELEMENT_IMPORT_FILE_LABEL = "//div[@class='fileNameLabel' and contains(text(),'${fileName}')]";

	//Import Form
	public By ELEMENT_UPLOAD_FILE_FRAME = By.xpath("//label[contains(text(),'Upload File:')]/following::div/iframe[contains(@id,'uploadFrame')]");
	public By ELEMENT_UPLOAD_VERSION_FRAME = By.xpath("//label[contains(text(),'Version History:')]/following::div/iframe[contains(@id,'uploadFrame')]");
	public By ELEMENT_BEHAVIOR = By.name("behavior");
	public By ELEMENT_IMPORT = By.xpath("//button[text()='Import']");

	//Publication > Add Category Form
	public By ELEMENT_CATEGORIES_LINK = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsManageCategories']"); 
	public By ELEMENT_CATEGORIES_MORE_LINK = By.xpath("//*[text()='More']/..//a[text()='Categories']");
	public By ELEMENT_SELECT_CATEGORY_TAB = By.xpath("//*[text()='Select Category']");
	public By ELEMENT_CATEGORY_TREE_BOX = By.name("taxonomyTree");
	public By ELEMENT_ADD_ROOT_BUTTON = By.xpath("//label[text()='Root Tree']/following::img[@title='Add Root Node']");
	public By ELEMENT_REFERENCE_TAB = By.xpath("//a[contains(text(),'Referenced Categories')]");
	public String ELEMENT_DELETE_CATEGORY_ICON = "//td[text()='{$categoryPath}']/..//i[@class='uiIconDelete']";
	public String MSG_DELETE_CATEGORY = "Are you sure you want to delete this reference?";

	//Version Info form
	public By ELEMENT_ICON_VERSION_ADD=By.xpath("//*[@data-original-title='Add Label']");
	public By ELEMENT_TEXTBOX_VERSION=By.id("label");
	public By ELEMENT_ACTIVATE_VERSION_BUTTON = By.xpath("//*[text()='Activate']");
	public By ELEMENT_CANCEL_ACTIVATE_VERSION_BUTTON = By.xpath("//*[text()='Cancel']");
	public By ELEMENT_VERSION_INFO_FORM = By.xpath("//*[text()='Close']");

	//publication form

	public final By ELEMENT_PUBLIC_STATUS = By.xpath("//*[contains(text(),'Published')]/..//a");
    public final By ELEMENT_STAGED_STATUS = By.xpath("//*[contains(text(),'Staged')]/..//a");
    public final By ELEMENT_PENDING_STATUS = By.xpath("//*[contains(text(),'Pending')]/..//a");
    public final By ELEMENT_APPROVED_STATUS = By.xpath("//*[contains(text(),'Approved')]/..//a");
	public final By ELEMENT_CURRENT_STATUS = By.xpath("//*[@class='currentStatus']");
	public final String ELEMENT_CURRENT_SPECIFIC_STATUS = "//*[@class='currentStatus']/p[contains(text(),'${status}')]";
	public final By ELEMENT_CURRENT_PUBLIC_STATUS = By.xpath("//*[@class='currentStatus']/*[text()='Published']");
    public final By ELEMENT_CURRENT_DRAFT_STATUS = By.xpath("//*[@class='currentStatus']/*[text()='Draft']");
    public final By ELEMENT_CURRENT_PENDING_STATUS = By.xpath("//*[@class='currentStatus']/*[text()='Pending']");
    public final By ELEMENT_CURRENT_APPROVED_STATUS = By.xpath("//*[@class='currentStatus']/*[text()='Approved']");
    public final By ELEMENT_CURRENT_STAGED_STATUS = By.xpath("//*[@class='currentStatus']/*[text()='Staged']");
    public final String MSG_INVALID_DATE_TIME = "The date format is invalid. Please check again.";
    public final String ELEMENT_REVISION = "//td[contains(text(),'${state}[Current Revision]')]";
    public final String ELEMENT_HISTORY_ITEM = "//div[text()='${state}']";

	//View Properties form
	public final By ELEMENT_VIEW_PROPERTIES_ICON = By.xpath("//i[@class='uiIconEcmsViewProperties']");
	public final By ELEMENT_PROPERTIES_TAB = By.linkText("Properties");
	public final By ELEMENT_ADD_PROPERTY_TAB = By.linkText("Add New Property");
	public final By ELEMENT_VALUE_INPUT = By.xpath("//input[contains(@id,'value')]");
	public final By ELEMENT_ADD_PROPERTY_INPUT = By.name("property_select");
	public final String ELEMENT_PROPERTY = "//td[text()='{$property}']/..//div[contains(text(),'{$value}')]"; 
	//Metadata form
	public final By ELEMENT_VIEW_METADATA_ICON = By.xpath("//i[@class='uiIconEcmsViewMetadatas']");
	public final By ELEMENT_METADATA_POPUP_TEXT = By.xpath("//span[@class='PopupTitle popupTitle' and text()='View Metadata']");
	public final String ELEMENT_EDIT_PROPERTY_ICON = "//*[@class='uiPropertyTab']//*[text()='${property}']/..//*[contains(@class, 'uiIconEdit')]";
	public final String ELEMENT_DELETE_PROPERTY_ICON = "//*[@class='uiPropertyTab']//*[text()='${property}']/..//*[contains(@class, 'uiIconDelete')]";
	public final String ELEMENT_VALUE_PROPERTY = "//*[@class='uiPropertyTab']//*[text()='${property}']/..//*[@class='text']";

	//Show Drives link
	public final By ELEMENT_SHOW_DRIVES = By.xpath("//*[@data-original-title = 'Show Drives']");

	//Action bar 
	public final By ELEMENT_ADD_ITEM = By.xpath("//*[@data-original-title='Add Item']");	
	public final By ELEMENT_DELETE_NODE_ICON = By.xpath("//*[@id='JCRContextMenu']//*[@class='uiIconEcmsDelete']");
	public final By ELEMENT_CUT_NODE_ICON = By.xpath("//*[@id='JCRContextMenu']//*[@class='uiIconEcmsCut']");
	public final By ELEMENT_COPY_NODE_ICON = By.xpath("//*[@id='JCRContextMenu']//*[@class='uiIconEcmsCopy']");
	public final By ELEMENT_ADD_SYMLINK_LIST_VIEW = By.xpath("//*[@id='JCRContextMenu']//i[@class='uiIconEcmsAddSymLink']");
	public final By ELEMENT_LOCK_ICON = By.xpath("//*[@id='JCRContextMenu']//i[@class='uiIconEcmsLock']");
	public final By ELEMENT_UNLOCK_ICON = By.xpath("//*[@id='JCRContextMenu']//i[@class='uiIconEcmsUnlock']");

	//publication TAB
	public final By ELEMENT_PUBLICATION_TAB = By.xpath("//a[contains(text(),'Publication')]");
	public final By ELEMENT_TEMPLATE_LIST_TEXT = By.xpath("//div[contains(text(),'Select your template in the list below')]");
	public final By ELEMENT_EDIT_LINK = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsEditDocument']");
	public final By ELEMENT_NEW_CONTENT_LINK = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsAddDocument']");
	public final By ELEMENT_PUBLICATION = By.xpath("//a[contains(text(),'Publications')]");
    public final By ELEMENT_PUBLISH_ICON = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsPublicationPublish']");
	public final By ELEMENT_PUBLICATION_ICON = By.className("uiIconEcmsManagePublications");
	/*
	 * Added by PhuongDT
	 * Date 06/09/2013
	 */
	public final String ELEMENT_PUBLICATION_STATUS = "//*[@class = 'activeStatus']/*[text()='${status}']/../a[@class='node']";
	public final String ELEMENT_REVISION_STATUS = "//*[@class = 'currentStatus']/*[text()='${status}']";
	public final By ELEMENT_CLEAR_SELECTION = By.xpath("//*[@class='fileViewStatus']//*[@id='FileViewClearSelection']");
	/*End Added*/

	public final By ELEMENT_VERSIONS_LINK = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsManageVersions']");
	public final String ELEMENT_RESTORE_VERSION_ICON = "//*[contains(text(), 'Version: ${version}')]/..//*[@class = 'uiIconRestore uiIconLightGray']";
	public final String ELEMENT_PUBLICATION_STATE = "//p[contains(text(),'{$state}')]/../a[@class='node']";	
	public final By ELEMENT_SCHEDULE_TAB = By.xpath("//a[text()='Scheduled']");	
	public final By ELEMENT_PUB_FROM_INPUT = By.name("UIPublicationPanelStartDateInput");
    public final By ELEMENT_HISTORY_TAB = By.linkText("History");
    public final By ELEMENT_REVISION_TAB = By.linkText("Revision");

	public final By ELEMENT_PUB_TO_INPUT = By.name("UIPublicationPanelEndDateInput");
	public final String ELEMENT_REVISION_DATE = "//*[contains(text(), '${status}')]/../td[2]";
	public final By ELEMENT_FIRST_REVISION_DATE = By.xpath(ELEMENT_REVISION_DATE.replace("${status}", "Draft[Current Revision]"));
	public final By ELEMENT_ADD_RELATION_LINK = By.xpath("//*[@class='actionIcon']//*[@class='uiIconEcmsManageRelations']");
	public final By ELEMENT_SELECT_RELATION_TAB = By.xpath("//*[contains(text(), 'Select Relation')]");
	public final By ELEMENT_RELATION_LIST_TAB = By.xpath("//*[contains(text(), 'Relation List')]");
	public final By ELEMENT_SHOW_RELATION_ICON = By.xpath("//i[@class='uiIconEcmsRelationMini']");
	public final String ELEMENT_RELATION_LINK = "//a[text()='{$relation}']";
	public final String ELEMENT_DELETE_RELATION_ICON = "//span[contains(text(),'{$relation}')]/../..//i[@class='uiIconDelete uiIconLightGray']";
	public final String MESSAGE_DELETE_RELATION = "Are you sure you want to delete this relation?";

	//Action Bar > Categories
	public final String ELEMENT_CATEGORY_OPTION = "//*[@name='taxonomyTree']/option[@value='${CATEGORY_TREE_NAME}']";

	//Personal Documents > Action Bar > Sort By 
	public final By ELEMENT_SORT_BY_BUTTON = By.xpath("//*[@id='FileViewBreadcrumb']//*[@class='btn dropdown-toggle']");
	public final String ELEMENT_SORT_BY_TYPE = "//*[@class='dropdown-menu']//*[contains(text(), '${type}')]";
	public final By ELEMENT_SORT_DOWN_ARROW = By.className("uiIconSortDown");
	public final By ELEMENT_SORT_UP_ARROW = By.className("uiIconSortUp");
	
	// Add site path
	public final String ELEMENT_SITE_PATH = "//span[@class='nodeName' and text()='${title}']";
	public final String ELEMENT_ACTIONS = "//*[contains(text(), '${action}')]";
	public By ELEMENT_ADD_ACTION_LINK = By.linkText("Add Action");
	public By ELEMENT_UPLOAD_FILE = By.linkText("Upload");
	public By ELEMENT_PUBLISH_FILE = By.linkText("Publish");
	public String ELEMENT_STATUS_FILE = "//span[@class='nodeName' and text()='${title}']/../../../..//div[@data-original-title= 'status' and text() = '${status}']";
	public final By ELEMENT_MORE_LINK = By.linkText("More");
	public final By ELEMENT_NAVIGATION_LINK = By.linkText("Content Navigation");
	public final By ELEMENT_MANAGE_ACTION_LINK = By.linkText("Actions");
	public final By ELEMENT_VISIBLE_CHECKBOX = By.id("Visible");
	public final By ELEMENT_NAVIGATION_NODE = By.id("NavigationNode");
	public final By ELEMENT_CLICKABLE_CHECKBOX = By.id("Clickable");
	public final By ELEMENT_NAVIGATION_SELECT_NODE = By.xpath("//i[@class= 'uiIconSelectNavigationNode uiIconLightGray']");
	public final String ELEMENT_NAVIGATION_PATH = "//td[contains(text(),'${path}')]/..//i[@class='uiIconSelectPage']";
	public final By ELEMENT_NAVIGATION_SELECT_LIST = By.xpath("//i[@class='uiIconSelectListTargetPage uiIconLightGray']");
	public final By ELEMENT_NAVIGATION_SELECT_DETAIL = By.xpath("//i[@class='uiIconSelectDetailTargetPage uiIconLightGray']");
	public final String ELEMENT_NAVIGATION_LIST_PATH = "//div[contains(text(),'${path}')]/../..//div[@class='Select16x16Icon']";
	public final By ELEMENT_NAVIGATION_DISPLAY_ORDER = By.id("Index");
	public final By ELEMENT_REFRESH_BUTTON = By.xpath("//*[@class = 'uiIconRefresh']");
	/*==================================================================================*/
	//Go to Sites Management
	public void showDrives(){
		Utils.pause(500);
		if (waitForAndGetElement(ELEMENT_SHOW_DRIVES, 3000, 0) != null){
			click(ELEMENT_SHOW_DRIVES);
		}else {
			click(By.xpath("//*[@title = 'Show Drives']"));
		}
		Utils.pause(1000);
	}

	//Go to add new content
	public void goToAddNewContent(){
		for (int repeat = 1;; repeat++)	{	
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot perform the action after " + ACTION_REPEAT + "tries");
			}
			WebElement newContent = waitForAndGetElement(ELEMENT_NEW_CONTENT_LINK, 10000, 0);
			if (newContent == null){
				WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 10000, 0);
				if (more != null){
					mouseOverAndClick(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
					Utils.pause(1000);
				}else {
					info("There is not Add New content icon in action bar");
					break;
				}
			}else break;
			Utils.pause(WAIT_INTERVAL);
			info("retry...[" + repeat + "]");
		}
		//mouseOverAndClick(ELEMENT_NEW_CONTENT_LINK);
		click(ELEMENT_NEW_CONTENT_LINK);
		waitForElementNotPresent(ELEMENT_NEW_CONTENT_LINK, DEFAULT_TIMEOUT, 1);
	}

	//Go to add new folder
	public void goToAddNewFolder(){	
		Utils.pause(1000);
		for (int repeat = 0;; repeat++)	{	
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot perform the action after " + ACTION_REPEAT + "tries");
			}
			WebElement newFolder = waitForAndGetElement(ELEMENT_NEW_FOLDER_LINK, 5000, 0);
			if (newFolder == null){
				WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
				if (more != null){
					mouseOverAndClick(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
					Utils.pause(1000);
				}else {
					info("There is not Add New Folder icon in action bar");
					break;
				}
			} 
			mouseOverAndClick(ELEMENT_NEW_FOLDER_LINK);
			if (waitForAndGetElement(ELEMENT_FOLDER_TITLE_TEXTBOX,30000,0) != null) break;
			Utils.pause(WAIT_INTERVAL);
			info("retry...[" + repeat + "]");
		}
	}

	//Collaboration Tab
	public void goToCollaboration(){
		for (int repeat = 0;; repeat++)	{	
			if (repeat >= ACTION_REPEAT) {
				Assert.fail("Cannot perform the action after " + ACTION_REPEAT + "tries");
			}
			mouseOver(ELEMENT_COLLABORATION_TAB, true);
			click(ELEMENT_COLLABORATION_TAB);

			if (waitForAndGetElement(ELEMENT_TAG, 30000, 0) != null) return;
			Utils.pause(WAIT_INTERVAL);
			info("retry...[" + repeat + "]");
		}
	}

	//Edit a document
	public void goToEditDocument(String title)
	{	
		if (title != null){

			goToNode(title);
		}
		for(int loop = 1;;loop ++)
		{
			if (loop >= ACTION_REPEAT) {
				Assert.fail("Cannot go to the edit page: " + title );
			}
			if (waitForAndGetElement(ELEMENT_EDIT_LINK, 3000, 0) != null){
				click(ELEMENT_EDIT_LINK);
			}else{
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				click(ELEMENT_EDIT_LINK);
			}
			if (waitForAndGetElement(button.ELEMENT_SAVE_CLOSE_BUTTON, 50000).isDisplayed()) 
				break;
		}
	}

	// switch to view mode (eg: Web view, List view ...)
	public void goToViewMode(String viewType){
		info("-- Change to view mode... --" + viewType);
		Utils.pause(1000);
		click(By.xpath(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", viewType)));
		Utils.pause(1000);
	}

	/*Modified by PhuongDT
	 * Date 11/09/2013
	 * Content: check condition to verify path when go to root node (sendKeys("//"))
	 * */
	//Go to 1 node by path in Intranet/document
	public void goToNodeByAddressPath(String path){
		WebElement address = waitForAndGetElement(ELEMENT_ADDRESS_BAR);
		address.clear();
		address.sendKeys(path);
		//address.sendKeys(Keys.ENTER);
		String pageId = waitForAndGetElement(By.xpath("//*[@id='UIPage']/div/div")).getAttribute("id");
		((JavascriptExecutor) driver).executeScript("javascript:eXo.webui.UIForm.submitForm('" + pageId + "#UIAddressBar','ChangeNode',true)");
		String[] temp = path.split("/");
		if(temp.length>0)
			waitForAndGetElement(By.xpath("//*[@id='FileViewBreadcrumb']//a[@data-original-title='" + temp[temp.length - 1] + "']"));

	}

	// Add a category in DMS Administration - Simple View
	public void addCategoryInSimpleView(String categoryName, Object...params){
		Boolean checkCategory = (Boolean) (params.length > 0 ? params[0]:true);

		info("Add a simple category");
		click(ELEMENT_BUTTON_ADD_CATEGORY);
		waitForAndGetElement(ELEMENT_ADD_CATEGORY_FORM);
		type(ELEMENT_INPUT_CATEGORY_NAME, categoryName, false);
		click(button.ELEMENT_SAVE_BUTTON);
		if (checkCategory){
			waitForAndGetElement(ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", categoryName));
			//(By.xpath("//a[@title='"+ name + " ']"));
		}
		Utils.pause(500);
	}

	//Export node
	public void exportNode(boolean systemView, boolean zip, boolean exportVersionHistory) {
		/*waitForElementPresent(ELEMENT_SYSTEM_TAB);
		click(ELEMENT_SYSTEM_TAB);
		Utils.pause(500);*/
		WebElement eProperties = waitForAndGetElement(ELEMENT_VIEW_PROPERTIES_ICON,10000,0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
		if(eProperties == null)
			if (more !=null )
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);

		waitForAndGetElement(ELEMENT_EXPORT_LINK);
		click(ELEMENT_EXPORT_LINK);
		if (!systemView)
		{
			click(ELEMENT_DOC_VIEW,2);
		}

		if (zip)
		{
			click(ELEMENT_ZIP,2);
		}

		if (exportVersionHistory)
		{
			click(ELEMENT_EXPORT_VERSION);
			Utils.pause(5000);
			waitForAndGetElement(ELEMENT_EXPORT_VERSION);
			button.cancel();
		}
		else
		{
			click(ELEMENT_EXPORT);
		}
		Utils.pause(20000);
		waitForElementNotPresent(ELEMENT_EXPORT);
	}

	/**Import node
	 * @param linkFile
	 * @param linkVersion
	 * @param behavior
	 * @param version
	 */
	public void importNode(String linkFile, String linkVersion, String behavior, boolean version) {
		WebElement eProperties = waitForAndGetElement(ELEMENT_VIEW_PROPERTIES_ICON,10000,0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
		String[] files = linkFile.split("/");
		int length = files.length;
		if(eProperties == null)
			if (more !=null )
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		//Click import button
		waitForAndGetElement(ELEMENT_IMPORT_LINK);
		click(ELEMENT_IMPORT_LINK);
		//Switch to frame upload file
		//		driver.switchTo().frame(waitForAndGetElement(ELEMENT_UPLOAD_FILE_FRAME));
		WebElement upload = waitForAndGetElement(ELEMENT_UPLOAD_IMG_ID, DEFAULT_TIMEOUT,1,2);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", upload);
		upload.sendKeys(Utils.getAbsoluteFilePath(linkFile));	
		//		type(ELEMENT_UPLOAD_IMG_ID, Utils.getAbsoluteFilePath(linkFile), false);
		Utils.pause(500);
		//		switchToParentWindow();

		select(ELEMENT_BEHAVIOR, behavior);
		if (version)
		{		
			//			driver.switchTo().frame(waitForAndGetElement(ELEMENT_UPLOAD_VERSION_FRAME));
			WebElement uploadVersion = waitForAndGetElement(ELEMENT_UPLOAD_VERSION_ID, DEFAULT_TIMEOUT,1,2);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.display = 'block';", uploadVersion);
			uploadVersion.sendKeys(Utils.getAbsoluteFilePath(linkVersion));
			Utils.pause(500);
			//			switchToParentWindow();
			click(ELEMENT_IMPORT);
			Utils.pause(500);
			waitForMessage("Imported successfully.");
			click(button.ELEMENT_OK_BUTTON);
		}
		else 
		{
			waitForAndGetElement(ELEMENT_IMPORT_FILE_LABEL.replace("${fileName}", files[length-1]));
			Utils.pause(1000);
			click(ELEMENT_IMPORT);
			
			waitForMessage("Imported successfully.");
			click(button.ELEMENT_OK_BUTTON);
		}
	}

	//Add category for node
	public void addCategoryForNode(String categoryTree, boolean rootTree, Object...params) {
		String categoryPath = (String) (params.length > 0 ? params[0]:"");
		String categoryName = (String) (params.length > 1 ? params[1]:"");

		By ELEMENT_ADD_CATEGORY_SPECIFIC = By.xpath("//div[contains(text(),'"+categoryName+"')]/following::a[@title='select']");
		// By ELEMENT_CATEGORY_LIST = By.xpath("//th[text()='Category']")
		By ELEMENT_ADD_CATEGORY_SPECIFIC_OTHER = By.xpath("//div[contains(text(),'"+categoryName+"')]/following::a[@data-original-title='select']");

		if (waitForAndGetElement(ELEMENT_CATEGORIES_LINK, 5000, 0) == null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			waitForAndGetElement(ELEMENT_CATEGORIES_LINK);
		}
		click(ELEMENT_CATEGORIES_LINK);

		waitForAndGetElement(ELEMENT_SELECT_CATEGORY_TAB);
		click(ELEMENT_SELECT_CATEGORY_TAB);
		Utils.pause(500);
		select(ELEMENT_CATEGORY_TREE_BOX, categoryTree);
		if (rootTree) {
			click(ELEMENT_ADD_ROOT_BUTTON);
			waitForTextPresent(categoryTree);
			checkUnexpectedError();	
		}
		else {
			if (categoryPath != ""){
				String paths [] = categoryPath.split("/");
				for (String path : paths)
					click(By.xpath("//*[@title='"+path+"']"));
			}
			if (waitForAndGetElement(ELEMENT_ADD_CATEGORY_SPECIFIC, 5000, 0) != null){
				click(ELEMENT_ADD_CATEGORY_SPECIFIC);	
			}else {
				click(ELEMENT_ADD_CATEGORY_SPECIFIC_OTHER);
			}
			Utils.pause(500);
			checkUnexpectedError();
			//waitForTextPresent(categoryPath);
		}
		if (waitForAndGetElement(button.ELEMENT_CLOSE_BUTTON, 3000, 0) != null ){
			click(button.ELEMENT_CLOSE_BUTTON);
		}
		/*waitForElementNotPresent(ELEMENT_SELECT_CATEGORY_TAB);*/
		info ("------Category " + categoryName + " is added succesfully");
	}

	/*
	 * Add version for a node
	 * + locator: locator of node
		   + version: version name
	 */
	public void addVersionForNode(By locator, String vesion){
		info("-- Add a version for a document... --");
		goToNode(locator);
		//click(ELEMENT_PUBLICATION_TAB);
		clearCache();
		if (waitForAndGetElement(ELEMENT_VERSIONS_LINK,10000,0)!=null){
			info("-- Versions tab is already displayed --");
		}else if (isElementPresent(ELEMENT_MORE_LINK_WITHOUT_BLOCK)){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (isElementPresent(ELEMENT_VERSIONS_LINK)){
				info("-- Versions tab is already displayed --");
			}
		}
		click(ELEMENT_VERSIONS_LINK);
		click(ELEMENT_ICON_VERSION_ADD);
		type(ELEMENT_TEXTBOX_VERSION,vesion,true);
		click(button.ELEMENT_SAVE_BUTTON);
		waitForElementNotPresent(button.ELEMENT_SAVE_BUTTON);
		click(button.ELEMENT_CLOSE_BUTTON);
	}

	//Choose a drive
	public void chooseDrive(By locator){
		info("-- Select a drive --");
		//click(ELEMENT_SHOW_DRIVES);
		showDrives();
		Utils.pause(1000);
		//button = new Button(driver);
		//button.refresh();
		click(locator);
		Utils.pause(1000);
	}

	//function public a document
	public void publishDocument(){
		button = new Button(driver);
		info("Publish a document");
		openManagePublicationForm();
		WebElement current = waitForAndGetElement(ELEMENT_CURRENT_STATUS);
		info(current.getText());
		if (current.getText().contains("Published") == false){
			click(ELEMENT_PUBLIC_STATUS);
		}
		waitForAndGetElement(ELEMENT_CURRENT_PUBLIC_STATUS);
		button.close();
		info("Publish a document is successful");
	}

	// Node Permission
	public void goToNodePermissionManagement(){
		if ( waitForAndGetElement(ELEMENT_PERMISSION_LINK, 5000, 0) !=null){
			info("-- Permission tab is already displayed --");
			click(ELEMENT_PERMISSION_LINK);
		}else{
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(ELEMENT_PERMISSION_LINK);
		}
		Utils.pause(1000);
	}

	//Add an action to Action Bar
	//Item: viewPermissions, addSymLink, addDocument
	//      manageVersions, manageRelations, comment
	//      exportNode, viewProperties, importNode, viewMetadatas
	public void addItem2ActionBar(String item, By eItem, Object...params){
		String view = (String) (params.length > 0 ? params[0] : "Web");
		String tab = (String) (params.length > 1 ? params[1] : "Authoring");
		WebElement element = waitForAndGetElement(eItem, 5000, 0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (element != null){
			info(item + " tab is already displayed --");
		}else if (more != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(eItem, 5000, 0) != null){
				info(item + " tab is already displayed --");
			}else{
				magView.setup2ShowViewAction(item, view, tab);
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}else {
			magView.setup2ShowViewAction(item, view, tab);
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
			navToolBar.goToSiteExplorer();
		}
		driver.navigate().refresh();
		Utils.pause(2000);
	}

	/**
	 * @author phuongdt
	 * @date 05/09/2013
	 * @function Add node to favorite
	 */
	//Go To Add Symlink 
	public void goToAddToFavorite(){
		if (isTextPresent("Add To Favorite")){
			info("-- Add To Favorite tab is already displayed --");
			click(ELEMENT_ADD_TO_FAVORITE_NODE);
		}else{
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(ELEMENT_ADD_TO_FAVORITE_NODE);
		}
		Utils.pause(1000);
	}

	/**
	 * @modified by phuongdt
	 * @modified date: 05/09/2013
	 */
	//Go To Add Symlink 
	public void goToAddSymlinkTab(){
		if (isTextPresent("Add Symlink")){
			info("-- Add Symlink tab is already displayed --");
			if(isElementPresent(ELEMENT_ACTION_BAR_ADD_SYMLINK))
				click(ELEMENT_ACTION_BAR_ADD_SYMLINK);
			else
				click(ELEMENT_ADD_SYMLINK_NODE);
		}else{
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if(isElementPresent(ELEMENT_ACTION_BAR_ADD_SYMLINK))
				click(ELEMENT_ACTION_BAR_ADD_SYMLINK);
			else
				click(ELEMENT_ADD_SYMLINK_NODE);
		}
		Utils.pause(1000);
	}

	//Go to the target node
	public void goToTargetNodeWhenAddSymlink(String path){
		goToAddSymlinkTab();
		waitForAndGetElement(ELEMENT_ADD_SYMLINK_POPUP);
		click(ELEMENT_ADD_ITEM);	
		String[] temp;
		String delimiter = "/";
		temp = path.split(delimiter);
		for(int i =0; i < temp.length; i++){
			info("Go to "+temp[i]);
			click(By.xpath(ELEMENT_SYMLINK_PATH_NODE_TITLE.replace("${node}", temp[i])));
			Utils.pause(100);
		}
		Utils.pause(1000);
	}

	//Add symlink for node with target node is documents
	public void addSymlink(String workspace, String path, String name){
		goToAddSymlinkTab();
		waitForAndGetElement(ELEMENT_ADD_SYMLINK_POPUP);
		if (path != null && path != ""){
			click(ELEMENT_ADD_ITEM);
			select(ELEMENT_SYMLINK_WORKSPACE, workspace);
			selectHomePathForCategoryTree(path);
		}
		String[] temp;
		String delimiter = "/";
		temp = path.split(delimiter);
		if (name.equalsIgnoreCase(temp[temp.length - 1] + ".lnk") == false){
			type(ELEMENT_SYMLINK_NAME, name, true);
		}
		assert getValue(ELEMENT_SYMLINK_NAME).equalsIgnoreCase(name);
		click(button.ELEMENT_SAVE_BUTTON); 
	}

	//Add symlink to action bar in site explorer if it does not exited
	/*public void addSymlinkToActionBar(){	
		WebElement syml = waitForAndGetElement(ELEMENT_ADD_SYMLINK, 5000, 0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (syml != null){
			info("-- Add Symlink tab is already displayed --");
		} else if (more != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(ELEMENT_ADD_SYMLINK, 5000, 0, 2) != null){
				info("-- Add Symlink tab is already displayed --");
			}else{
				magView.setup2ShowViewAction("addSymLink", "Web");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}else {
			magView.setup2ShowViewAction("addSymLink", "Web");
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
			navToolBar.goToSiteExplorer();
		}
		Utils.pause(1000);
	}*/

	//A Function to copy/cut/paste/delete an Element (Document/Folder) in Sites Explorer
	//Check the box on the right side of Element
	//Select Action "Delete" on Action Bar
	/*Modified by PhuogDT
	 * Date 10/09/2013
	 * Content: change condition to click ELEMENT_PERSONAL_DOCUMENTS*/
	public void actionsOnElement(String elementName, ContextMenu.actionType action, Object...params){
		Boolean mDelete = (Boolean) (params.length > 0 ? params[0]: false);
		Boolean notPersonalDoc = (Boolean) (params.length > 1 ? params[1]: false);
		info("-- Action: "+ action + " the element: " + elementName);
		if(!notPersonalDoc)
			click(ELEMENT_PERSONAL_DOCUMENTS);
		if (mDelete){
			String[] nodes = elementName.split("/");
			for (String node : nodes){
				info("-- Delete node: " + node); 
				if (waitForAndGetElement(ELEMENT_SELECT_CHECKBOX.replace("${name}", node), 3000, 0, 2) != null){
					click(ELEMENT_SELECT_CHECKBOX.replace("${name}", node), 2);
				}else{
					click(ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", node) + "/../../../div[@class='columnCheckbox']", 2);
				}
			}
		}else{
			if (waitForAndGetElement(ELEMENT_UI_CHECKBOX.replace("${element}", elementName), 3000, 0, 2) != null){
				click(ELEMENT_UI_CHECKBOX.replace("${element}", elementName), 2);
			}else{
				click(ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", elementName) + "/../../../div[@class='columnCheckbox']", 2);
			}
		}
		/*else {
			click(ELEMENT_PERSONAL_DOCUMENTS);
			if (mDelete){
				String[] nodes = elementName.split("/");
				for (String node : nodes){
					click(ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", node) + "/../../../div[@class='columnCheckbox']", 2);
				}
			}else{
				click(ELEMENT_NODE_ADMIN_VIEW.replace("${nodeName}", elementName) + "/../../../div[@class='columnCheckbox']", 2);
			}
		}*/
		switch (action){
		case COPY:
			click(ELEMENT_COPY_NODE);
			break;
		case CUT:
			click(ELEMENT_CUT_NODE);
			break;
		case EDIT:
			click(cMenu.ELEMENT_MENU_EDIT);
			break;
		case DELETE:
			if (waitForAndGetElement(cMenu.ELEMENT_MENU_DELETE, 3000, 0) != null){
				click(cMenu.ELEMENT_MENU_DELETE);
			}else {
				click(By.className("uiIconEcmsDelete"));
			}
			//waitForTextPresent("Delete");
			dialog.deleteInDialog();
			waitForElementNotPresent(ELEMENT_UI_CHECKBOX.replace("${element}", elementName));
			break;
		case PASTE:
			click(ELEMENT_PASTE_NODE);
			break;
		default:
			break;
		}
		Utils.pause(1000);
	}

	/** function add "New Content" to File Management view if it is not existed
	 * @author lientm
	 */
	/*public void addNewContentToFileManagementView(){
		WebElement syml = waitForAndGetElement(ELEMENT_NEW_CONTENT_LINK, 5000, 0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (syml != null){
			info("-- New content is already displayed --");
		} else if (more != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(ELEMENT_NEW_CONTENT_LINK, 5000, 0) != null){
				info("-- New content is already displayed --");
			}else{
				magView.setup2ShowViewAction("addDocument", "List", "List");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToPersonalDocuments();
				goToViewMode("List");
			}
		}else {
			magView.setup2ShowViewAction("addDocument", "List", "List");
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
			navToolBar.goToPersonalDocuments();
			goToViewMode("List");
		}
		Utils.pause(1000);
	}*/

	/** function add "Add Symlink" to File Management view if it is not existed
	 * @author lientm
	 */
	/*public void addSymlinkToFileManagementView(){
		WebElement syml = waitForAndGetElement(ELEMENT_ADD_SYMLINK, 5000, 0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (syml != null){
			info("-- Add Symlink tab is already displayed --");
		} else if (more != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(ELEMENT_ADD_SYMLINK, 5000, 0) != null){
				info("-- Add Symlink tab is already displayed --");
			}else{
				magView.setup2ShowViewAction("addSymLink", "List", "List");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToPersonalDocuments();
				goToViewMode("List");
			}
		}else {
			magView.setup2ShowViewAction("addSymLink", "List", "List");
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
			navToolBar.goToPersonalDocuments();
			goToViewMode("List");
		}
		Utils.pause(1000);
	}*/

	/** function add version management to web Management view if it is not existed
	 * @author lientm
	 */
	/*public void addVersionMangementForActionBar(){
		WebElement ver = waitForAndGetElement(ELEMENT_VERSIONS_LINK, 5000, 0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (ver != null){
			info("-- Version mangement is already displayed --");
		} else if (more != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(ELEMENT_VERSIONS_LINK, 5000, 0) != null){
				info("-- Version mangement is already displayed --");
			}else{
				magView.setup2ShowViewAction("manageVersions");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}else {
			magView.setup2ShowViewAction("manageVersions");
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
			navToolBar.goToSiteExplorer();
		}
	}*/

	//Add [Manage Relation] tab to Sites Explorer > Action Bar
	/*public void addRelationToActionBar(){	
		WebElement addRelation = waitForAndGetElement(ELEMENT_ADD_RELATION_LINK, 5000, 0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (addRelation != null){
			info("-- Add Relation tab is already displayed --");
		} else if (more != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(ELEMENT_ADD_RELATION_LINK, 5000, 0, 2) != null){
				info("-- Add Relation tab is already displayed --");
			}else{
				magView.setup2ShowViewAction("manageRelations", "Web");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}else {
			magView.setup2ShowViewAction("manageRelations", "Web");
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
			navToolBar.goToSiteExplorer();
		}
		Utils.pause(1000);
	}*/

	//Create a relation between 2 nodes
	public void createRelation(String nodeName1, String pathToNodeName2, Object...params){
		//WebElement addRelation = waitForAndGetElement(ELEMENT_ADD_RELATION_LINK, 5000, 0);
		String[] temp;
		String delimiter = "/";
		temp = pathToNodeName2.split(delimiter);
		Boolean nodeAdminView = (Boolean) (params.length > 0 ? params[0]: false);

		info("-- Create a relation:--" + nodeName1 + " and " + temp[temp.length - 1]);
		if (nodeAdminView){
			goToNode(nodeName1, true);
		}else {
			goToNode(nodeName1);
		}
		if (isTextPresent("Relations")){
			info("-- Add Relation tab is already displayed --");
			click(ELEMENT_ADD_RELATION_LINK);
		}else {
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(ELEMENT_ADD_RELATION_LINK);
		}
		click(ELEMENT_SELECT_RELATION_TAB);
		selectHomePathForCategoryTree(pathToNodeName2);
		waitForTextPresent(temp[temp.length - 1]);
		button.close();	
		Utils.pause(500);
	}

	//Undo deleted Items
	public void undoDeletion(String...nodeName){
		String node = nodeName.length > 0 ? nodeName[0]: "";
		
		info("-- Undo deletion --");
		if (node != ""){
			waitForTextPresent("\'" + node + "' was deleted succesfully.");
		}
		if(this.plfVersion.equalsIgnoreCase("4.1"))
			((JavascriptExecutor)driver).executeScript("arguments[0].click();",waitForAndGetElement(ELEMENT_UNDO_DELETED_ITEM));
		else if(this.plfVersion.equalsIgnoreCase("4.0"))
			click(ELEMENT_UNDO_DELETED_ITEM);
		if (waitForAndGetElement(button.ELEMENT_OK_BUTTON, 3000, 0) != null){
			click(button.ELEMENT_OK_BUTTON);
		}
		if (node != ""){
			waitForTextPresent("\'" + node + "' was successfully restored.");
		}
		Utils.pause(1000);		
	}

	/**Delete relation for a node
	 * @author thuntn
	 */
	public void deleteRelation(String relation){
		WebElement addRelation = waitForAndGetElement(ELEMENT_ADD_RELATION_LINK, 5000, 0);
		if(addRelation == null)
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		click(ELEMENT_ADD_RELATION_LINK);

		click(ELEMENT_DELETE_RELATION_ICON.replace("{$relation}", relation));
		alert.waitForConfirmation(MESSAGE_DELETE_RELATION);

		waitForTextNotPresent(relation);

		button.close();
	}

	//Delete data in Admin view, List view
	/*public void deleteDataInAdminView(String name){
		click(By.xpath(ELEMENT_SELECT_CHECKBOX.replace("${name}", name)), 2);
		click(ELEMENT_DELETE_NODE_ICON);
		dialog.deleteInDialog();
		waitForElementNotPresent(By.xpath(ELEMENT_SELECT_CHECKBOX.replace("${name}", name)), DEFAULT_TIMEOUT, 1, 2);
		Utils.pause(1000);
	}*/

	//Go to Manage Categories
	public void goToManageCategories(){
		info("-- Go to Action Bar/Categories Tab --");
		if(waitForAndGetElement(ELEMENT_CATEGORIES_LINK, 5000, 0) == null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			//click(ELEMENT_CATEGORIES_MORE_LINK);
			click(ELEMENT_CATEGORIES_LINK);
		}
		else{
			goToNode(ELEMENT_CATEGORIES_LINK);
			waitForAndGetElement(ELEMENT_PERMISSION_MANAGEMENT_POPUP);
		} 
	}
	/**function add Comment icon for action with web view
	 * @author lientm
	 */
	/*public void addCommentIconInActionBar(){
		navToolBar.goToSiteExplorer();
		click(By.linkText("acme"));
		click(By.linkText("documents"));
		click(By.linkText("metro.pdf"));
		WebElement comment = waitForAndGetElement(ELEMENT_ADD_COMMENT_LINK, 5000, 0);

		if (comment != null){
			info("-- Add comment icon is already displayed --");
		} else if (waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(ELEMENT_ADD_COMMENT_LINK, 5000, 0) != null){
				info("-- Add comment icon is already displayed --");
			}else{
				magView.setup2ShowViewAction("comment");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
			}
		}else {
			magView.setup2ShowViewAction("comment");
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
		}
	}*/

	/** function go to add comment in action bar
	 * @author lientm
	 */
	public void goToAddComment(){
		WebElement comment = waitForAndGetElement(ELEMENT_ADD_COMMENT_LINK, 5000, 0);
		if (comment == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
			if (more != null){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			} else {
				info("Do not have add comment icon in action bar");
				return;
			}
		}
		click(ELEMENT_ADD_COMMENT_LINK);
		waitForAndGetElement(ELEMENT_ADD_COMMENT_POPUP);
		Utils.pause(1000);
	}

	/**function add comment for node
	 * @author lientm
	 * @param comment: comment need to add
	 */
	public void addComment(String comment){
		goToAddComment();
		if(this.plfVersion.equalsIgnoreCase("4.1"))
			inputDataToFrame(ELEMENT_ADD_COMMENT_FRAME_41, comment, true);
		else// if(this.plfVersion.equalsIgnoreCase("4.0"))
			inputDataToFrame(ELEMENT_ADD_COMMENT_FRAME, comment, true);
		switchToParentWindow();
		button.save();
		waitForElementNotPresent(ELEMENT_ADD_COMMENT_POPUP);
		click(ELEMENT_SHOW_COMMENT_LINK);
		waitForAndGetElement(By.xpath(ELEMENT_SHOW_COMMENT_CONTENT.replace("${comment}", comment)));
	}

	/**function edit a comment
	 * @author lientm
	 * @param oldComment
	 * @param newComment
	 */
	public void editComment(String oldComment, String newComment){
		click(ELEMENT_SHOW_COMMENT_LINK);
		click(By.xpath(ELEMENT_EDIT_COMMENT_ICON.replace("${comment}", oldComment)));
		waitForAndGetElement(ELEMENT_ADD_COMMENT_POPUP);
		if(this.plfVersion.equalsIgnoreCase("4.1"))
			inputDataToFrame(ELEMENT_ADD_COMMENT_FRAME_41, newComment, false);
		else if(this.plfVersion.equalsIgnoreCase("4.0"))
			inputDataToFrame(ELEMENT_ADD_COMMENT_FRAME, newComment, false);
		switchToParentWindow();
		button.save();
		waitForElementNotPresent(ELEMENT_ADD_COMMENT_POPUP);
		click(ELEMENT_SHOW_COMMENT_LINK);
		waitForAndGetElement(By.xpath(ELEMENT_SHOW_COMMENT_CONTENT.replace("${comment}", newComment)));
	}

	/**function delete a comment
	 * @author lientm
	 * @param comment
	 */
	public void deleteComment(String comment){
		click(ELEMENT_SHOW_COMMENT_LINK);
		click(By.xpath(ELEMENT_DELETE_COMMENT_ICON.replace("${comment}", comment)));
		alert.acceptAlert();
		if (waitForAndGetElement(ELEMENT_SHOW_COMMENT_LINK, 5000, 0) != null){
			click(ELEMENT_SHOW_COMMENT_LINK);
		}
		waitForElementNotPresent(By.xpath(ELEMENT_SHOW_COMMENT_CONTENT.replace("${comment}", comment)));
	}

	/** function vote for a document/uploaded file
	 * @author lientm
	 * @param rate
	 */
	public void voteDocument(int rate){
		WebElement comment = waitForAndGetElement(ELEMENT_VOTE_LINK, 5000, 0);
		if (comment == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
			if (more != null){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			} else {
				info("Do not have add comment icon in action bar");
				return;
			}
		}
		click(ELEMENT_VOTE_LINK);
		waitForAndGetElement(ELEMENT_VOTE_POPUP);
		Utils.pause(1000);

		HashMap<Integer, String> rateVote = new HashMap<Integer, String>();
		rateVote.put(1, "Poor");
		rateVote.put(2, "Below average");
		rateVote.put(3, "Average");
		rateVote.put(4, "Above average");
		rateVote.put(5, "Good");

		click(By.xpath(ELEMENT_VOTE_RATE.replace("${rate}", rateVote.get(rate))));
		waitForAndGetElement(ELEMENT_VOTE_COMPONENT);
	}

	/** function and translation for document
	 * @author lientm
	 * @param paths: path to folder contains destination file
	 * @param fileName: name of destination file
	 */
	public void addTranslationForDocument(String paths, String fileName){
		WebElement comment = waitForAndGetElement(ELEMENT_ADD_TRANSLATION_LINK, 5000, 0);
		if (comment == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
			if (more != null){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			} else {
				info("Do not have add comment icon in action bar");
				return;
			}
		}
		click(ELEMENT_ADD_TRANSLATION_LINK);
		Utils.pause(1000);

		click(ELEMENT_SELECT_DOCUMENT_BUTTON);

		if (paths != ""){
			goToNode(paths);
		}
		click(By.xpath("//*[@id='ListRecords']//a[text()='" + fileName + "']"));
		waitForElementNotPresent(ELEMENT_SELECT_DOCUMENT_POPUP);
		button.save();
		waitForElementNotPresent(ELEMENT_ADD_TRANSLATION_POPUP);
	}

	/** Check if Export button is available in action bar
	 * @author thuntn
	 */
	/*public void addExportButton(){
		WebElement eExport = waitForAndGetElement(ELEMENT_EXPORT_LINK,10000,0);

		info("Check if Export button is available in action bar");
		if(eExport == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
			if (more !=null ){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				eExport = waitForAndGetElement(ELEMENT_EXPORT_LINK,30000,0);

				if (eExport == null){
					navToolBar.goToContentAdministration();
					magView.setup2ShowViewAction("exportNode");
					magAcc.signOut();
					magAcc.signIn("john", DATA_PASS);
					navToolBar.goToSiteExplorer();
				}
			}else{
				navToolBar.goToContentAdministration();
				magView.setup2ShowViewAction("exportNode");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}
	}*/

	/** Check if Category button is available in action bar
	 * @author thuntn
	 */
	/*public void checkCategoryButton(){
		WebElement eCategory = waitForAndGetElement(ELEMENT_CATEGORIES_LINK,10000,0);

		info("Check if Category button is available in action bar");
		if(eCategory == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
			if (more !=null ){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				eCategory = waitForAndGetElement(ELEMENT_CATEGORIES_LINK,30000,0);

				if (eCategory == null){
					navToolBar.goToContentAdministration();
					magView.setup2ShowViewAction("addCategory");
					magAcc.signOut();
					magAcc.signIn("john", DATA_PASS);
					navToolBar.goToSiteExplorer();
				}
			}else{
				navToolBar.goToContentAdministration();
				magView.setup2ShowViewAction("addCategory");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}
	}*/

	/**Delete category for a node
	 * @author thuntn
	 * @param categoryPath
	 */
	public void deleteCategory(String categoryPath){
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
		if (more !=null ){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		if (waitForAndGetElement(ELEMENT_CATEGORIES_LINK, 3000, 0) != null){
			click(ELEMENT_CATEGORIES_LINK);
		}else {
			click(By.className("uiIconEcmsManageCategories"));
		}
		click(ELEMENT_DELETE_CATEGORY_ICON.replace("{$categoryPath}", categoryPath));
		alert.waitForConfirmation(MSG_DELETE_CATEGORY);

		waitForTextNotPresent(categoryPath);

		button.close();
	}

	/**Manage publication state
	 * @author thuntn
	 */
	public void managePublication(String state, String...date){
		By bState = By.xpath(ELEMENT_PUBLICATION_STATE.replace("{$state}", state));
		String date1 = (String) (date.length > 0 ? date[0]:"");
		String date2 = (String) (date.length > 1 ? date[1]:"");

		info("Manage publication state");
		openManagePublicationForm();
		click(bState);
		waitForAndGetElement(ELEMENT_CURRENT_SPECIFIC_STATUS.replace("${status}", state));
		if (state.equals("Staged") & (date.length > 0)){
			click(ELEMENT_SCHEDULE_TAB);
			if((date1!=""&& date1!= null))
				type(ELEMENT_PUB_FROM_INPUT,date1,true);
			if((date2!="" && date2!= null))
				type(ELEMENT_PUB_TO_INPUT,date2,true);
			button.save();
			if(date.length > 2){
				waitForMessage(MSG_INVALID_DATE_TIME);
				button.ok();
				button.close();
				return;
			}
			waitForMessage("Your new publication schedule was saved successfully.");
			button.ok();
			click(ELEMENT_REVISION_TAB);
		}
		waitForAndGetElement(ELEMENT_HISTORY_TAB);
		waitForAndGetElement(ELEMENT_REVISION_TAB);
		
		click(ELEMENT_HISTORY_TAB);
		waitForAndGetElement(ELEMENT_HISTORY_ITEM.replace("${state}", state));
		
		button.close();
		
	}

	/**Add View properties to action bar if it is not shown on action bar
	 * @author thuntn
	 */
	/*public void addViewPropertiesButton(){
		WebElement eProperties = waitForAndGetElement(ELEMENT_VIEW_PROPERTIES_ICON,10000,0);

		info("Add View properties to action bar if it is not shown on action bar");
		if(eProperties == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
			if (more !=null ){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				eProperties = waitForAndGetElement(ELEMENT_VIEW_PROPERTIES_ICON,20000,0);

				if (eProperties == null){
					navToolBar.goToContentAdministration();
					magView.setup2ShowViewAction("viewProperties");
					magAcc.signOut();
					magAcc.signIn("john", DATA_PASS);
					navToolBar.goToSiteExplorer();
				}
			}else{
				navToolBar.goToContentAdministration();
				magView.setup2ShowViewAction("viewProperties");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}
	}*/

	/**Add property for a node
	 * @author thuntn
	 */
	public void addProperty(String property, String value){

		info("Add property for a node");
		WebElement eProperties = waitForAndGetElement(ELEMENT_VIEW_PROPERTIES_ICON,10000,0);

		if(eProperties == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
			if (more !=null )
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		click(ELEMENT_VIEW_PROPERTIES_ICON);
		waitForAndGetElement(ELEMENT_PROPERTIES_TAB);

		click(ELEMENT_ADD_PROPERTY_TAB);
		Utils.pause(1000);
		select(ELEMENT_ADD_PROPERTY_INPUT, property);
		type(ELEMENT_VALUE_INPUT,value,true);
		button.save();

		//Check if a property is added successfully.
		waitForAndGetElement(ELEMENT_PROPERTY.replace("{$property}", property).replace("{$value}", value));
		button.close();
	}

	//Go to View properties tab
	public void goToPropertiesTab(){
		info("-- Go to Properties Tab --");
		/*WebElement eProperties = waitForAndGetElement(ELEMENT_VIEW_PROPERTIES_ICON,10000,0);
		if(eProperties == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
			if (more !=null )
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}*/
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (more !=null ){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		click(ELEMENT_VIEW_PROPERTIES_ICON);
		waitForAndGetElement(ELEMENT_PROPERTIES_TAB);
	}

	//Actions on selected property: Edit/Delete
	public void actionsOnSelectedProperty(String property, String option, Object...params){
		String editProperty = (String) (params.length > 0 ? params[0]: "");
		String value = (String) (params.length > 1 ? params[1]: "");

		info(option + "-- property --" + property);
		if (option.equals("Edit")){
			click(ELEMENT_EDIT_PROPERTY_ICON.replace("${property}", property));
			select(ELEMENT_ADD_PROPERTY_INPUT, editProperty);
			type(ELEMENT_VALUE_INPUT, value, true);
			button.save();
			//Check if a property is added successfully.
			waitForAndGetElement(ELEMENT_PROPERTY.replace("{$property}", property).replace("{$value}", value));
			button.close();

		}else if (option.equals("Delete")){
			click(ELEMENT_DELETE_PROPERTY_ICON.replace("${property}", property));
			alt.acceptAlert();
			waitForElementNotPresent(ELEMENT_DELETE_PROPERTY_ICON.replace("${property}", property));
			button.close();
		}
		Utils.pause(500);
	}

	/** Add Import button to action bar if it is not available on action bar
	 * @author thuntn
	 */
	/*public void addImportButton(){
		WebElement eExport = waitForAndGetElement(ELEMENT_IMPORT_LINK,10000,0);

		info("Add Import button to action bar if it is not available on action bar");
		if(eExport == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK,5000,0);
			if (more !=null ){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				eExport = waitForAndGetElement(ELEMENT_IMPORT_LINK,30000,0);

				if (eExport == null){
					navToolBar.goToContentAdministration();
					magView.setup2ShowViewAction("importNode");
					magAcc.signOut();
					magAcc.signIn("john", DATA_PASS);
					navToolBar.goToSiteExplorer();
				}	
			}else{
				navToolBar.goToContentAdministration();
				magView.setup2ShowViewAction("importNode");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}
	}*/

	/**Add View metadata icon to action bar if it is not shown on action bar
	 * @author thuntn
	 */
	/*public void addViewMetadataToActionBar(){
		WebElement addMetadata = waitForAndGetElement(ELEMENT_VIEW_METADATA_ICON, 10000, 0);
		WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
		if (addMetadata != null){
			info("-- View metadata is already displayed --");
		} else if (more != null){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (waitForAndGetElement(ELEMENT_VIEW_METADATA_ICON, 5000, 0, 2) != null){
				info("-- View metadata is already displayed --");
			}else{
				magView.setup2ShowViewAction("viewMetadatas", "Web");
				magAcc.signOut();
				magAcc.signIn("john", DATA_PASS);
				navToolBar.goToSiteExplorer();
			}
		}else {
			magView.setup2ShowViewAction("viewMetadatas", "Web");
			magAcc.signOut();
			magAcc.signIn("john", DATA_PASS);
			navToolBar.goToSiteExplorer();
		}
	}*/

	/**View metadata
	 * @author thuntn
	 */
	public void viewMetadata(){
		info("View metadata of a node");
		WebElement viewMetadata = waitForAndGetElement(ELEMENT_VIEW_METADATA_ICON,5000,0);
		if (viewMetadata == null)
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		click(ELEMENT_VIEW_METADATA_ICON);
		waitForAndGetElement(ELEMENT_METADATA_POPUP_TEXT);
		button.cancel();
	}

	/** function lock nodes from clicking Lock icon in action bar
	 * @author lientm
	 * @param nodes
	 * @modified phuongdt
	 * @modified date: 05/09/2013
	 * @modified content: condition to click lock action
	 */
	public void lockNodeFromActionBar(String nodes){
		String ELEMENT_LOCKED_NODE_LIST_VIEW = "//*[contains(@data-original-title, 'Locked by')]//*[contains(text(),'${name}')]";

		String[] node = nodes.split("/");
		for (int i = 0; i < node.length; i ++){
			click(By.xpath(ELEMENT_SELECT_CHECKBOX.replace("${name}", node[i])), 2);
		}
		if(isElementPresent(ELEMENT_LOCK_ICON))
			click(ELEMENT_LOCK_ICON);
		else
			click(ELEMENT_LOCK_NODE);
		for (int j = 0; j < node.length; j ++){
			waitForAndGetElement(ELEMENT_LOCKED_NODE_LIST_VIEW.replace("${name}", node[j]));
		}
	}

	/**function unlock nodes from clicking Unlock icon in action bar
	 * @author lientm
	 * @param nodes
	 * @modified phuongdt
	 * @modified date: 05/09/2013
	 * @modified content: condition to click unlock action
	 */
	public void unLockNodeFromActionBar(String nodes){
		String ELEMENT_LOCKED_NODE_LIST_VIEW = "//*[contains(@data-original-title, 'Locked by')]//*[contains(text(),'${name}')]";

		String[] node = nodes.split("/");
		for (int i = 0; i < node.length; i ++){
			click(By.xpath(ELEMENT_SELECT_CHECKBOX.replace("${name}", node[i])), 2);
		}
		if(isElementPresent(ELEMENT_UNLOCK_ICON))
			click(ELEMENT_UNLOCK_ICON);
		else
			click(ELEMENT_UNLOCK_NODE);
		for (int j = 0; j < node.length; j ++){
			waitForElementNotPresent(ELEMENT_LOCKED_NODE_LIST_VIEW.replace("${name}", node[j]));
		}
	}

	/**
	 * Open [Manage Publication] form
	 */
	public void openManagePublicationForm(){
		info("-- Open Manage Publication Form --");
		if ((waitForAndGetElement(ELEMENT_PUBLICATION, 5000, 0) == null )){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		if (waitForAndGetElement(ELEMENT_PUBLICATION, 3000, 0) != null){
			click(ELEMENT_PUBLICATION);
		}else {
			click(ELEMENT_PUBLICATION_ICON);
		}
		Utils.pause(500);
	}

	/**
	 * Manage Publication > get a publish date
	 * @param revision: locator of revision date
	 */
	public String getPublishDate(By revision){
		String date = "";
		WebElement element = waitForAndGetElement(revision);
		date = element.getText();
		info("-- Publish date is " + date);
		return date;	
	}

	/**
	 * @author phuongdt
	 * @date 06/09/2013
	 * @function change status in [Manage Publication] form
	 * @param: Status: Draft, Pending, Approved, Staged, Published
	 * [@class = 'activeStatus']/*[text()='Published']/../a[@class='node']
	 */
	public void changeStatusPublication(String status){
		openManagePublicationForm();
		click(By.xpath(ELEMENT_PUBLICATION_STATUS.replace("${status}", status)));
		waitForAndGetElement(By.xpath(ELEMENT_REVISION_STATUS.replace("${status}", status)));
		button.close();
	}

	/**
	 * Personal Documents > Action Bar > Sort By
	 * @param type: NAME, SIZE, DATE
	 */
	public void sortBy(String type){
		info("-- Choose a field type to sort: " + type);
		click(ELEMENT_SORT_BY_BUTTON);
		click(ELEMENT_SORT_BY_TYPE.replace("${type}", type));
		Utils.pause(1000);	
	}

	/*
	 * @Added by: PhuongDT
	 * @date: 27/08/2013
	 */
	public void goToAction(){
		WebElement actionicon = waitForAndGetElement(ELEMENT_ACTION_ICON, 5000, 0);
		if (actionicon == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0);
			if (more != null){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			} else {
				info("Do not have action icon in action bar");
				return;
			}
		}
		click(ELEMENT_ACTION_ICON);
		Utils.pause(1000);
	}

	public void addNewAction(String actionName, String lifeCycle, String actionType, Object... params){
		goToAction();
		click(ELEMENT_ADD_ACTION_TAB); 
		Utils.pause(1000);
		select (ELEMENT_ACTION_TYPE, actionType);
		type(ELEMENT_ACTION_NAME, actionName, true);
		select(ELEMENT_ACTION_LIFE_CYCLE, lifeCycle);
		if (isElementPresent(ELEMENT_ACTION_NODE_TYPE_SEARCH)){
			click(ELEMENT_ACTION_NODE_TYPE_SEARCH);
			check(ELEMENT_ACTION_SELECT_ALL_DOC,2);	
		}
		waitForAndGetElement(button.ELEMENT_SAVE_BUTTON);
		button.save();
		Utils.pause(2000);
		button.close();
	}

	public Boolean isActionPresent(String actionName){
		goToAction();
		click(ELEMENT_AVAILABLE_ACTIONS);
		String verifyXPath = ELEMENT_ACTION_AVAILABLE_ACTION.replace("${actionname}", actionName);
		Boolean isElement = isElementPresent(By.xpath(verifyXPath));
		Utils.pause(1000);
		button.close();
		return isElement;
	}

	public void deleteAction(String actionName){
		goToAction();
		click(ELEMENT_AVAILABLE_ACTIONS);
	}

	public void actionsOnActionsOfNode(String actionName, String option, Object...params){
		String newactionname = (String) (params.length > 0 ? params[0]: "");
		String newactiontype = (String) (params.length > 1 ? params[1]: "");
		String newlifecycle = (String) (params.length > 1 ? params[1]: "");

		goToAction();
		click(ELEMENT_AVAILABLE_ACTIONS);

		info(option + "-- actionName --" + actionName);
		if (option.equals("Edit")){
			if(!newactionname.isEmpty())
				select (ELEMENT_ACTION_TYPE, newactiontype);
			if (!newactionname.isEmpty())
				type(ELEMENT_ACTION_NAME, newactionname, true);
			if (newlifecycle.isEmpty())
				select(ELEMENT_ACTION_LIFE_CYCLE, newlifecycle);
			button.save();
			//Check if a property is added successfully.
			waitForElementNotPresent(ELEMENT_ACTION_AVAILABLE_ACTION.replace("${actionname}", newactionname));
			button.close();

		}else if (option.equals("Delete")){
			click(ELEMENT_DELETE_ACTION_ICON.replace("${actionname}", actionName));
			alt.acceptAlert();
			waitForElementNotPresent(ELEMENT_ACTION_AVAILABLE_ACTION.replace("${actionname}", actionName));
			button.close();
		}
		Utils.pause(500);
	}

	public void goToRelation(String nodeName1, Object...params){
		//WebElement addRelation = waitForAndGetElement(ELEMENT_ADD_RELATION_LINK, 5000, 0);
		Boolean nodeAdminView = (Boolean) (params.length > 0 ? params[0]: false);
		if (nodeAdminView){
			goToNode(nodeName1, true);
		}else {
			goToNode(nodeName1);
		}
		if (isTextPresent("Relations")){
			info("-- Add Relation tab is already displayed --");
			click(ELEMENT_ADD_RELATION_LINK);
		}else {
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			click(ELEMENT_ADD_RELATION_LINK);
		}
		click(ELEMENT_RELATION_LIST_TAB);
	}
	/*End Add*/
	/**
	 * @author phuongdt
	 * @date	30/08/2013
	 * @function	Open version info form
	 * @param	locator
	 */
	public void openVersionInfoForm(By locator){
		info("-- Open version Info form of a document... --");
		goToNode(locator);
		if (waitForAndGetElement(ELEMENT_VERSIONS_LINK,10000,0)!=null){
			info("-- Versions tab is already displayed --");
		}else if (isElementPresent(ELEMENT_MORE_LINK_WITHOUT_BLOCK)){
			click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
			if (isElementPresent(ELEMENT_VERSIONS_LINK)){
				info("-- Versions tab is already displayed --");
			}
		}
		click(ELEMENT_VERSIONS_LINK);
		if (waitForAndGetElement(ELEMENT_ACTIVATE_VERSION_BUTTON, DEFAULT_TIMEOUT, 0, 2) != null){
			click(ELEMENT_ACTIVATE_VERSION_BUTTON);
			if (waitForAndGetElement(ELEMENT_VERSIONS_LINK,10000,0)!=null){
				info("-- Versions tab is already displayed --");
			}else if (isElementPresent(ELEMENT_MORE_LINK_WITHOUT_BLOCK)){
				click(ELEMENT_MORE_LINK_WITHOUT_BLOCK);
				if (isElementPresent(ELEMENT_VERSIONS_LINK)){
					info("-- Versions tab is already displayed --");
				}
			}
			click(ELEMENT_VERSIONS_LINK);
		}
	}

	/**
	 * @author phuongdt
	 * @date 19/09/2013
	 * @function restore version
	 * @param locator
	 * @param version
	 */
	public void restoreVersion(By locator, String version){
		openVersionInfoForm(locator);
		waitForAndGetElement(By.xpath(ELEMENT_RESTORE_VERSION_ICON.replace("${version}", version)));
		click(By.xpath(ELEMENT_RESTORE_VERSION_ICON.replace("${version}", version)));
		waitForElementNotPresent(By.xpath(ELEMENT_RESTORE_VERSION_ICON.replace("${version}", version)));
		button.close();
		Utils.pause(3000);
	}

	/**
	 * @author phuongdt
	 * @date 04/09/2013
	 * @function: verify action icon is available on action bar
	 */
	public Boolean isActionsOnActionBarPresent(Object locator){
		WebElement actionicon = waitForAndGetElement(locator, 5000, 0);
		if (actionicon == null){
			WebElement more = waitForAndGetElement(ELEMENT_MORE_LINK_ACTION_BAR, 5000, 0);
			if (more != null){
				info("-- Click more link --");
				click(ELEMENT_MORE_LINK_ACTION_BAR);
				actionicon = waitForAndGetElement(locator, 5000, 0);
				if (actionicon == null)
					return false;
				else
					return true;

			} else {
				info("Do not have action icon in action bar");
				return false;
			}
		}
		else{
			return true;
		}
	}
	
	/**
	 * 
	 */
	public void addContentNavigation(boolean visible, String path, String displayOrder, boolean clickable, String forList, String forDetail, boolean verify){
		info("Add Content Navigation");
		if (!visible) check(ELEMENT_VISIBLE_CHECKBOX, 2);
		else uncheck(ELEMENT_VISIBLE_CHECKBOX, 2);
		if(path!=null){
			click(ELEMENT_NAVIGATION_SELECT_NODE);
			check(By.xpath(ELEMENT_NAVIGATION_PATH.replace("${path}", path)),2);			
		}
		if(displayOrder!=null){
			WebElement order = waitForAndGetElement(ELEMENT_NAVIGATION_DISPLAY_ORDER);
			order.sendKeys(displayOrder);
		}
		if (clickable) check(ELEMENT_CLICKABLE_CHECKBOX, 2);
		else uncheck(ELEMENT_CLICKABLE_CHECKBOX, 2);
		if(forList!=null){
			click(ELEMENT_NAVIGATION_SELECT_LIST);
			check(By.xpath(ELEMENT_NAVIGATION_LIST_PATH.replace("${path}", forList)),2);	
		}
		if(forDetail!=null){
			click(ELEMENT_NAVIGATION_SELECT_DETAIL);
			check(By.xpath(ELEMENT_NAVIGATION_LIST_PATH.replace("${path}", forDetail)),2);	
		}
		if(verify){
		Utils.pause(500);
		button.save();	
		}
		else button.cancel();
			
	}
}
