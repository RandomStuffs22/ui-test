package org.exoplatform.selenium.platform.ecms.admin;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * 
 * @author vuna2
 *
 */
public class ManageDrive extends EcmsBase{

	public ManageDrive(WebDriver dr) {
		super(dr);
		// TODO Auto-generated constructor stub
	}

	NavigationToolbar nav = new NavigationToolbar(driver);
	Button button = new Button(driver);
	ActionBar actBar = new ActionBar(driver);
	NavigationToolbar navToolbar = new NavigationToolbar(driver);
	ECMainFunction ecMain = new ECMainFunction(driver);
	ManageAlert alt = new ManageAlert(driver);
	ManageAccount magAcc = new ManageAccount(driver,this.plfVersion);

	/*Manage Drive Page */
	//Add a drive
	public final By ELEMENT_ADD_DRIVER_BUTTON = By.xpath("//*[text()='Add Drive']"); 

	//Edit a drive
	public final By ELEMENT_DRIVE_EDIT_POPUP = By.id("EditDriveManagerPopup");
	public final String ELEMENT_DRIVE_EDIT = "//*[@title='${DATA_DRIVE_NAME}']/../..//*[@title='Edit']";
	public final String ELEMENT_DRIVE_EDIT_AUX = "//*[@data-original-title='${driveName}']/../..//*[@data-original-title='Edit']";

	//Delete a drive
	public final String ELEMENT_DRIVE_DELETE = "//*[@title='${DATA_DRIVE_NAME}']/../..//*[@title='Delete']";
	public final String ELEMENT_DRIVE_DELETE_AUX = "//*[@data-original-title='${DATA_DRIVE_NAME}']/../..//*[@data-original-title='Delete']";

	//Add a Driver form 
	public final By ELEMENT_ADD_DRIVE_POPUP = By.id("AddDriveManagerPopup");
	public final By ELEMENT_APPLY_VIEW_TAB = By.xpath("//*[contains(text(),'Apply Views')]");
	public final By ELEMENT_DRIVE_TAB = By.xpath("//*[contains(text(),'Drive')]");
	public final By ELEMENT_WORKSPACE = By.name("workspace");
	public final By ELEMENT_ADD_PATH = By.xpath("//*[@title='Add Path']");
	public final By ELEMENT_ADD_PATH_AUX = By.xpath("//*[@data-original-title='Add Path']");
	public final By ELEMENT_ADD_ROOT_NODE = By.xpath("//*[@title='AddRootNode']");
	public final By ELEMENT_ADD_ROOT_NODE_AUX = By.xpath("//*[@data-original-title='AddRootNode']");
	public final By ELEMENT_ADD_PATH_POPUP = By.id("JCRBrowser");
	public final By ELEMENT_JCR_SYSTEM = By.xpath("//*[contains(text(),'jcr:system')]/../..//*[@title='select']");
	public final String ELEMENT_SELECT_PATH_ITEM = "//*[contains(text(),'${node}')]/../..//*[@title='select']";
	public final String ELEMENT_SELECT_PATH_ITEM_AUX = "//*[contains(text(),'${node}')]/../..//*[@data-original-title='select']";
	public final By ELEMENT_HOME_PATH = By.id("homePath");
	public final By ELEMENT_ADD_PERMISSION = By.xpath("//*[@title='Add Permission']");
	public final By ELEMENT_ADD_PERMISSION_AUX = By.xpath("//*[@data-original-title='Add Permission']");
	public final By ELEMENT_PERMISSION_TEXTBOX = By.id("permissions");
	public final By ELEMENT_ALLOW_CREATE_FOLDER = By.name("allowCreateFolders");
	public final String ELEMENT_ALLOW_CREATE_FOLDER_OPTIONS = "//*[@name='allowCreateFolders']/option[text()='${option}']";
	public final By ELEMENT_DRIVE_NAME = By.id("name");
	public final By ELEMENT_MANAGE_DRIVE_LINK = By.linkText("Manage Drives");
	public final String ELEMENT_VERIFY_DRIVE = "//div[@data-original-title='${driveName}']";
	public final String ELEMENT_VERIFY_WORKSPACE_NAME = ELEMENT_VERIFY_DRIVE + "/../../td[2]/div"; 

	//------------Manage driver------------------//

	//Function to add new drive in [Manage drive] form
	public void addNewDrive(String driverName, String workspace, String path, String group, 
			String member, String viewOption, String view, Object...params){
		By ELEMENT_EXO_ECM = By.xpath(ELEMENT_SELECT_PATH_ITEM.replace("${node}", path));
		//By ELEMENT_DRIVER = By.xpath(ELEMENT_VERIFY_DRIVE.replace("${driveName}", driverName));

		Boolean editDrv = (Boolean) (params.length > 0 ? params[0]: false);
		Boolean selectView = (Boolean) (params.length > 1 ? params[1]: false);

		if (!editDrv){
			click(ELEMENT_ADD_DRIVER_BUTTON);

			//check add driver form
			info("Add new driver with name " + driverName);
			waitForAndGetElement(ELEMENT_ADD_DRIVE_POPUP);
			assert isElementPresent(ELEMENT_DRIVE_TAB):"Add driver form is wrong";
			assert isElementPresent(ELEMENT_APPLY_VIEW_TAB):"Add driver form is wrong";

			//input name
			type(ELEMENT_DRIVE_NAME,driverName,false);
		}else if (editDrv){
			if (isElementPresent(ELEMENT_DRIVE_EDIT.replace("${DATA_DRIVE_NAME}", driverName))){
				click(ELEMENT_DRIVE_EDIT.replace("${DATA_DRIVE_NAME}", driverName));
			}else {
				click(By.xpath(ELEMENT_DRIVE_EDIT_AUX.replace("${driveName}", driverName)));
			}
			waitForAndGetElement(ELEMENT_DRIVE_EDIT_POPUP);
		}

		// Select workspace for new drive
		select(ELEMENT_WORKSPACE, workspace);

		//select path for new drive
		if (path.equals("/") || path.equals("Root")){
			if (isElementPresent(ELEMENT_ADD_PATH)){
				click(ELEMENT_ADD_PATH);
			}else {
				click(ELEMENT_ADD_PATH_AUX);
			}
			waitForAndGetElement(ELEMENT_ADD_PATH_POPUP);
			if (isElementPresent(ELEMENT_ADD_ROOT_NODE)){
				click(ELEMENT_ADD_ROOT_NODE);
			}else {
				click(ELEMENT_ADD_ROOT_NODE_AUX);
			}
			Utils.pause(1000);
		}else{
			if (isElementPresent(ELEMENT_ADD_PATH)){
				click(ELEMENT_ADD_PATH);
			}else {
				click(ELEMENT_ADD_PATH_AUX);
			}
			waitForAndGetElement(ELEMENT_ADD_PATH_POPUP);
			assert waitForAndGetElement(By.xpath("//*[@name='workspaceName']/option[@value='" + workspace + "']")).isSelected():"Homepath display wrong";
			if (isElementPresent(ELEMENT_EXO_ECM)){
				click(ELEMENT_EXO_ECM);
			}else if (isElementPresent(By.xpath(ELEMENT_SELECT_PATH_ITEM_AUX.replace("${node}", path)))){
				click(By.xpath(ELEMENT_SELECT_PATH_ITEM_AUX.replace("${node}", path)));
			}else if (isElementNotPresent(By.xpath(ELEMENT_SELECT_PATH_ITEM_AUX.replace("${node}", path)))){
				selectHomePathForCategoryTree(path);
			}
			else{
				info("list node of homepath display false");
			}
			assert getValue(ELEMENT_HOME_PATH).equals("/" + path + ""):"select homepath is false";
		}

		//Set permission on new drive
		info("Set Permission for driver");
		type(By.xpath("//input[@name='permissions']"), "", true);
		if (isElementPresent(ELEMENT_ADD_PERMISSION)){
			click(ELEMENT_ADD_PERMISSION);
		}else {
			click(ELEMENT_ADD_PERMISSION_AUX);
		}
		userGroup.selectGroup(group, false);
		Utils.pause(1000);
		click(By.linkText(member));
		Utils.pause(1000);
		assert getValue(ELEMENT_PERMISSION_TEXTBOX).contains(member):"Set permission is not true";

		setViewPreferenceForDriver(viewOption);

		//Select Allow Folder Creation
		select(ELEMENT_ALLOW_CREATE_FOLDER, "Content Folder");

		//Allowance node type on left tree
		type(By.id("allowNodeTypesOnTree"), "*", true);

		if(selectView){
			//Choose applied view
			click(ELEMENT_APPLY_VIEW_TAB);
			selectCheckBoxList(view);
			//Complete add new drive
			click(button.ELEMENT_SAVE_BUTTON);
			select(By.xpath("//select[contains(@id,'maxPageSize')]"),"20");
			driver.navigate().refresh();
			Utils.pause(500);
			//waitForElementPresent(ELEMENT_DRIVER);
			//assert isElementPresent(ELEMENT_DRIVER):"Add new driver is unsuccessful";
			waitForAndGetElement(ELEMENT_DRIVE_EDIT_AUX.replace("${driveName}", driverName));
			info("Create new driver is successful");
		}else{
			//Complete add new drive
			click(button.ELEMENT_SAVE_BUTTON);
			alt.waitForMessage("Please select a view.");
			click(button.ELEMENT_OK_BUTTON);
		}
		Utils.pause(500);
	} 

	//Set a view preference for Drive
	public void setViewPreferenceForDriver(String viewOption){
		Utils.pause(1000);
		String[] temp = viewOption.split("/");
		if (temp.length != 0){
			for (int i=0; i < temp.length ; i++ ){
				if (temp[i].equals("Referenced Documents")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('viewPreferences').click();");
				}
				if (temp[i].equals("Non-document Nodes")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('viewNonDocument').click();");
				}
				if (temp[i].equals("Sidebar")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('viewSideBar').click();");
				}
				if (temp[i].equals("Hidden Nodes")){
					((JavascriptExecutor) driver).executeScript("document.getElementById('showHiddenNode').click();");
				}
			}
		}
		Utils.pause(1000);
	}

	//function to delete drive on manage drive
	public void deleteDrive(String driver){
		By ELEMENT_DRIVER = By.xpath(ELEMENT_VERIFY_DRIVE.replace("${driveName}", driver));
		if (isElementNotPresent(ELEMENT_ADD_DRIVER_BUTTON)){
			ecMain.goToManageDrive();
		}
		info("Delete driver");
		if (isElementPresent(ELEMENT_DRIVE_DELETE.replace("${DATA_DRIVE_NAME}", driver))){
			click(ELEMENT_DRIVE_DELETE.replace("${DATA_DRIVE_NAME}", driver));
		}else {
			click(By.xpath(ELEMENT_DRIVE_DELETE_AUX.replace("${DATA_DRIVE_NAME}", driver)));
		}
		alt.acceptAlert();
		waitForElementNotPresent(ELEMENT_DRIVER);
		info("Delete driver successfully");
	}

	//Add [Web View] to Action Bar in Personal Documents
	public void addView2Drive(String view, String drive){
		info("-- Add [Web View] to Action Bar --");
		if (waitForAndGetElement(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", view), 3000, 0) == null){
			ecMain.goToManageDrive();
			click(By.xpath(ELEMENT_DRIVE_EDIT_AUX.replace("${driveName}", drive)));
			waitForAndGetElement(ELEMENT_DRIVE_EDIT_POPUP);
			click(ELEMENT_APPLY_VIEW_TAB);
			selectCheckBoxList(view);
			button.save();
			magAcc.signOut();
			magAcc.signIn("john", "gtngtn");
		}else {
			info("-- Web View is already displayed --");
		}
		if (drive.equals("Personal Documents")){
			nav.goToPersonalDocuments();
			click(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", view));
			Utils.pause(500);
		}
	}
	
	/**
	 * remove a view from drive
	 * @param view
	 * @param drive
	 */
	public void removeViewFromDrive(String view, String drive){
		info("-- Remove [Web View] to Action Bar --");
		if (waitForAndGetElement(ELEMENT_VIEW_MODE_LINK.replace("${viewName}", view), 3000, 0) != null){
			ecMain.goToManageDrive();
			click(By.xpath(ELEMENT_DRIVE_EDIT_AUX.replace("${driveName}", drive)));
			waitForAndGetElement(ELEMENT_DRIVE_EDIT_POPUP);
			click(ELEMENT_APPLY_VIEW_TAB);
			unSelectCheckBoxList(view);
			button.save();
			magAcc.signOut();
			magAcc.signIn("john", "gtn");
		}else {
			info("-- Web View is not displayed --");
		}
	}

	//Option to select [Folder Creation] 
	public void selectTypeOfFolderCreation(String folderType, Object...params){
		Actions actions = new Actions(driver);
		String[] options = folderType.split("/");
		Boolean isOneValue = (Boolean) (params.length > 0 ? params[0]: false); 
		if (isOneValue){
			String fType = "Content Folder/CSS Folder/Document Folder/Javascript Folder/Link Folder/Web Content Folder";
			String[] opt = fType.split("/");
			for (int i = 0; i < opt.length; i++){
				WebElement element = waitForAndGetElement(ELEMENT_ALLOW_CREATE_FOLDER_OPTIONS.replace("${option}", opt[i]));
				boolean isSeclectedValue = element.isSelected(); 
				if(isSeclectedValue){
					click(ELEMENT_ALLOW_CREATE_FOLDER_OPTIONS.replace("${option}", opt[i]));
				}
			}
			Utils.pause(500);
			info("Select only one type..." + folderType);
			click(ELEMENT_ALLOW_CREATE_FOLDER_OPTIONS.replace("${option}", folderType));
		}else{
			select(ELEMENT_ALLOW_CREATE_FOLDER, options[0]);
			actions.keyDown(Keys.CONTROL);
			for (int i = 1; i < options.length; i++) {
				info("Select a type of folder..." + options[i]);	
				WebElement element = waitForAndGetElement(ELEMENT_ALLOW_CREATE_FOLDER_OPTIONS.replace("${option}", options[i]));
				boolean isSeclectedValue = element.isSelected(); 
				if(!isSeclectedValue){
					click(ELEMENT_ALLOW_CREATE_FOLDER_OPTIONS.replace("${option}", options[i]));
				}
			}
		}
		Utils.pause(500);
	}
}