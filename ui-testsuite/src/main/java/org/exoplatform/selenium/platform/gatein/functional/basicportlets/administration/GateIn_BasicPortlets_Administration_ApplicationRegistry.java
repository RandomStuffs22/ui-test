package org.exoplatform.selenium.platform.gatein.functional.basicportlets.administration;

import static org.exoplatform.selenium.TestLogger.info;

import java.util.HashMap;
import java.util.Map;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.DashBoard;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.ManageAccount.userType;
import org.exoplatform.selenium.platform.ManageApplications;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
/**
 * 
 * @author phuongdt
 * @date 12/11/2013
 *
 */
public class GateIn_BasicPortlets_Administration_ApplicationRegistry extends DashBoard {

	ManageAccount magAc;
	NavigationToolbar navTool;
	PageManagement pageMag;
	PageEditor pageE;
	ManageApplications magApp;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navTool = new NavigationToolbar(driver);
		pageMag = new PageManagement(driver);
		pageE = new PageEditor(driver);
		magApp = new ManageApplications(driver);
		button = new Button(driver);
		magAc.signIn(DATA_USER1,DATA_PASS);
	}

	@AfterMethod
	public void afterTest(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/** Add new category with valid value
	 * CaseID 73345
	 * Step 1: Show Application Registry form
	 * Step 2: Show Add new category form
	 * Step 3: Add new category with valid value
	 */
	@Test
	public void test01_AddNewCategoryWithValidValue() {
		/*Declare variables*/
		String categoryName = "Gateinsniff_Category73345";
		String displayName = "Category 73345";
		String categoryDescription = "Add new category";
		/* Step 1: Show Application Registry form */
		//- Login by admin
		//- Choose Administration/Application Registry on UserToolBarGroupPortlet
		//Application Registry form is shown, Categories form is selected as default
		navTool.goToApplicationRegistry();

		/* Step 2: Show Add new category form */
		//Click Add Category icon the action bar
		//Add category form appear includes:
		//- Category Settings
		//- Permission Settings
		/* Step 3: Add new category with valid value */
		//In add new category form in Application Registry:
		//- Input valid Name 
		//- Input valid values for others
		//- Input valid values for Permission Settings 
		//- Click Save
		//- New Category is added successfully and have alert message category does not have any application, click (+) button to add application
		info("Add new category");
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, null, true);

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName, true);
	}

	/** Copy remote gadget to local repository
	 * CaseID 73356
	 * Step 1: Show form to add a remote gadget
	 * Step 2: Add gadget
	 * Step 3: Copy remote gadget to local repository
	 * PENDING: Need to remove step 3 on qmetry
	 */
	@Test (groups = "pending")
	public void test02_CopyRemoteGadgetToLocalRepository() {
		/*Declare variables*/
		String url = "http://www.labpixies.com/campaigns/memory_game/memory_game.xml";
		String title = "Memory Game";

		/* Step 1: Show form to add a remote gadget */
		//Click Add a remote gadget
		//A form is shown in one field to input ULR		
		/* Step 2: Add gadget */
		//- Input valid URL
		//- Click Add
		//New gadget is added successfully and displayed in the left pane
		navTool.goToApplicationRegistry();
		click(magApp.ELEMENT_SHOW_GADGET_ICON);
		info("Create new remote gadget");
		magApp.addRemoteGadget(url);
		waitForAndGetElement(magApp.ELEMENT_GADGET_DELETE_ICON.replace("${title}", title));

		/* Step 3: Copy remote gadget to local repository */
		//- Select added gadget
		//- Click Copy this gadget to local repository icon in the left pane
		//- This gadget is saved to local repository

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteGadget(title);
	}

	/** Show Application portlet when check or un-check Change show import
	 * CaseID 73361
	 * Step 1: Show Application Registry form
	 * Step 2: Show edit portlet form
	 * Step 3: Do un-check change show import
	 * Step 4: Check display after un-check change show import
	 * Step 5: Do check change show import
	 * Step 6: Check display after check change show import
	 */
	@Test
	public void test03_ShowApplicationPortletWhenCheckOrUnCheckChangeShowImport() {
		/* Step 1: Show Application Registry form */
		//- Login by admin
		//- Choose Administration/Application Registry on UserToolBarGroupPortlet
		//Application Registry form is shown, Categories form is selected as default
		/* Step 2: Show edit portlet form */
		//- Choose Edit page on AdminToolbarPortlet
		//- Move mouse on portlet and click [Edit portlet]
		//- Edit page form appear
		//- Display Edit portlet form	
		/* Step 3: Do un-check change show import */
		//- In edit mode form: click un-check Change show import, after click Save and Close 
		//- Click Finish on the top of Page Editor composer
		//- Application registry edit mode form is change and close edit portlet form
		//- Edit page form is closed
		//- Page is changed successfully
		/* Step 4: Check display after un-check change show import */
		//- View Application registry 
		//- Don't show Import application icon on Control bar
		magApp.showImportApplication(false);

		/* Step 5: Do check change show import */
		//- In edit mode form: click check Change show import
		//- Click Save and Close 
		//- Click Finish on the top of Page Editor composer
		//- Application registry edit mode form is change and close edit portlet form
		//- Edit page form is closed
		//- Page is changed successfully
		/* Step 6: Check display after check change show import */
		//- View Application registry 
		//- Show Import application icon on Control bar and can click to import all category
		magApp.showImportApplication(true);
	}

	/** Add a remote gadget with valid URL
	 * CaseID 73451
	 * Step 1: Show form to add a remote gadget
	 * Step 2: Add gadget
	 */
	@Test
	public void test04_AddARemoteGadgetWithValidURL() {
		/*Declare variables*/
		String url = "http://www.labpixies.com/campaigns/memory_game/memory_game.xml";
		String title = "Memory Game";

		/* Step 1: Show form to add a remote gadget */
		//Click Add a remote gadget
		//A form is shown in one field to input ULR		
		/* Step 2: Add gadget */
		//- Input valid URL
		//- Click Add
		//New gadget is added successfully and displayed in the left pane
		navTool.goToApplicationRegistry();
		click(magApp.ELEMENT_SHOW_GADGET_ICON);
		info("Create new remote gadget");
		magApp.addRemoteGadget(url);
		waitForAndGetElement(magApp.ELEMENT_GADGET_DELETE_ICON.replace("${title}", title));

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteGadget(title);
	}

	/** Change access right on category from public to be limited by group(s)
	 * CaseID 73523
	 * Step 1: Add public category
	 * Step 2: Check public category
	 * Step 3: Edit category
	 * Step 4: Check accessing above category without right
	 * Step 5: Check accessing above category with right
	 */
	@Test
	public void test05_ChangeAccessRightOnCategoryFromPublicToBeLimitedByGroupS() {
		/*Declare variables*/
		String categoryName = "Category73523";
		String displayName = "Category 73523";
		String categoryDescription = "Add new category";
		String appName1 = "Application735231";
		String appName2 = "Advanced Search";

		/* Step 1: Add public category */
		//Create new category with checked Make it public option
		//Create new category successfully
		info("Add new category");
		navTool.goToApplicationRegistry();
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, null, true);

		/* Step 2: Check public category */
		//- Add some application (public) into added category
		info("Add Applications into category");
		magApp.addApplicationToCategory(categoryName, true, appName1, "Portlet", null, true, null, null);
		magApp.addApplicationToCategory(categoryName, false, null, "Portlet", appName2, false, "Development", "manager");

		//Everyone can see this category 
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.PUBLISHER,displayName,true);
		magAc.signIn(DATA_USER1,DATA_PASS);
		navTool.goToApplicationRegistry();

		/* Step 3: Edit category */
		//Edit above category:
		//- Uncheck Make it public option
		//- Select group/membership to limit number of users can access this category
		//Edit category successfully
		info("Edit category");
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "*");
		magApp.editCategoryAtManageApplications(categoryName, displayName, categoryDescription, false, permissions, true);

		/* Step 4: Check accessing above category without right */
		//- Use account of user who is not in the selected group to assign right at step 3
		//Can't see category which user does not have right
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.PUBLISHER,displayName,false);

		/* Step 5: Check accessing above category with right */
		//- Login by user who is in selected group/membership when assign right at step 3
		//This category is listed for this user to use
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.ROOT,displayName,true);
		magAc.signIn(DATA_USER1,DATA_PASS);
		navTool.goToApplicationRegistry();

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName, true);
	}

	/** Limit access right for application in not public category when user who has right to access application also has right to access its category
	 * CaseID 73526
	 * Step 1: Add application
	 * Step 2: Set permission for some users on application
	 * Step 3: Check accessing above application without right
	 * Step 4: Check accessing above application with right
	 */
	@Test
	public void test06_LimitAccessRightForApplicationInNotPublicCategoryWhenUserWhoHasRightToAccessApplicationAlsoHasRightToAccessItsCategory() {
		/*Declare variables*/
		String categoryName = "Category73526";
		String displayName = "Category 73526";
		String categoryDescription = "Add new category";
		String appName1 = "Application73526";
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "*");
		String groupId ="Platform/Content Management";
		String membership ="*";

		/* Step 1: Add application */
		//In Application Registry form:
		//- Create category & assign group A with membership B to access
		info("Add new category");
		navTool.goToApplicationRegistry();
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, permissions, true);

		//- Add application into category
		//Application is added into that category
		/* Step 2: Set permission for some users on application */
		//- Select to view new added application
		//- Click Add Permission and select group A with membership B to access this application
		//Selected group/membership are displayed as one row in Group and Membership form of selecting application
		info("Add Applications into category");
		magApp.addApplicationToCategory(categoryName, true, appName1, "Portlet", null, true, groupId, membership);

		/* Step 3: Check accessing above application without right */
		//- Login by user who is not in group/membership assigned on added application in above category
		//- Go to Group editor/add new page
		//- In add new page by wizard form:
		//- Input valid values at step 1 & 2
		//- Click Next from step 2 to go to step 3
		//Don't show category and application for this user to use
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.PUBLISHER,displayName,false);

		/* Step 4: Check accessing above application with right */
		//- Login by user who is in group/membership assigned on added application in above category
		//- Go to Group editor/add new page
		//- In add new page by wizard form:
		//- Input valid values at step 1 & 2
		//- Click Next from step 2 to go to step 3
		//Above application is listed for this user to use
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.ROOT,displayName,true,appName1,true);
		magAc.signIn(DATA_USER1,DATA_PASS);
		navTool.goToApplicationRegistry();

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName, true);
	}

	/** Check add and copy remote gadget to local repository
	 * CaseID 73530
	 * Step 1: Add gadget
	 * Step 2: Copy remote gadget to local repository
	 * Step 3: Add new same URL
	 * PENDING: Need to remove step 3 on qmetry
	 */
	@Test (groups = "pending")
	public void test07_CheckAddAndCopyRemoteGadgetToLocalRepository() {
		/*Declare variables*/
		String url = "http://www.labpixies.com/campaigns/memory_game/memory_game.xml";
		String title = "Memory Game";

		/* Step 1: Add gadget */
		//- Input valid URL ( URL of gadget 's xml or RSS feed)
		//- Click Add
		//New gadget is added successfully and displayed in the left pane
		navTool.goToApplicationRegistry();
		click(magApp.ELEMENT_SHOW_GADGET_ICON);
		info("Create new remote gadget");
		magApp.addRemoteGadget(url);
		waitForAndGetElement(magApp.ELEMENT_GADGET_DELETE_ICON.replace("${title}", title));

		/* Step 2: Copy remote gadget to local repository */
		//- Select added gadget
		//- Click Copy this gadget to local repository icon in the left pan
		//- This gadget is saved to local repository

		/* Step 3: Add new same URL */
		//- Input valid URL same with URL above
		//- Click Add
		//- New gadget is added successfully and displayed in the left pane
		//- In gadget list have two gadget are the same name but have different view URL

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteGadget(title);
	}

	/** Change access right on category from limited by group(s) to be public
	 * CaseID 73592
	 * Step 1: Add category with limit of access users
	 * Step 2: Check accessing category with right
	 * Step 3: Check accessing category without right
	 * Step 4: Change access right of category to public
	 * Step 5: Check accessing public category
	 */
	@Test
	public void test08_ChangeAccessRightOnCategoryFromLimitedByGroupsToBePublic() {
		/*Declare variables*/
		String categoryName = "Category73592";
		String displayName = "Category 73592";
		String categoryDescription = "Add new category";
		String appName1 = "Application735921";
		String appName2 = "Advanced Search";
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "*");
		String groupId ="Platform/Content Management";
		String membership ="*";

		/* Step 1: Add category with limit of access users */
		//- Create new category
		//- Select some groups/membership for new added category
		//- Add some applications (public) into it
		//Create new category successfully
		info("Add new category");
		navTool.goToApplicationRegistry();
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, false, permissions, true);
		info("Add Applications into category");
		magApp.addApplicationToCategory(categoryName, true, appName1, "Portlet", null, false, groupId, membership);
		magApp.addApplicationToCategory(categoryName, false, null, "Portlet", appName2, false, "Development", "manager");

		/* Step 2: Check accessing category with right */
		//Go to create page by for group by user who is in selected group/membership that has access right on added category
		//This category is listed for this user to use
		//Everyone can see this category 
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.ROOT,displayName,true);
		magAc.signIn(DATA_USER1,DATA_PASS);
		navTool.goToApplicationRegistry();

		/* Step 3: Check accessing category without right */
		//Go to create page by for group by user who is not in selected group/membership that has access right on added category
		//This category is not listed for this user to use
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.PUBLISHER,displayName,false);

		/* Step 4: Change access right of category to public */
		//Edit above category with checked Make it public option
		//Edit category successfully
		magAc.signIn(DATA_USER1,DATA_PASS);
		navTool.goToApplicationRegistry();
		magApp.editCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, permissions, true);

		/* Step 5: Check accessing public category */
		//- Login by any user
		//- Go to create page by group at step 3
		//This category is listed for this user to use
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.PUBLISHER,displayName,true);
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.ROOT,displayName,true);
		magAc.signIn(DATA_USER1,DATA_PASS);
		navTool.goToApplicationRegistry();

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName, true);
	}

	/** Limit access right for application in not public category group/membership to access application & category are not the same
	 * CaseID 73595
	 * Step 1: Add application
	 * Step 2: Set permission for some users on application
	 * Step 3: Check accessing above application by user has right to access category but does not have right to access application
	 * Step 4: Check accessing above application by user has right to access the application but does not have right to access the category
	 */
	@Test
	public void test09_LimitAccessRightForApplicationInNotPublicCategoryGroupMembershipToAccessApplicationAndCategoryAreNotTheSame() {
		/*Declare variables*/
		String categoryName = "Category73595";
		String displayName = "Category 73595";
		String categoryDescription = "Add new category";
		String appName1 = "Application73595";
		String appName2 = "Advanced Search";
		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "editor");
		String groupId1 ="Platform/Content Management";
		String membership1 ="manager";
		String groupId2 ="Platform/Content Management";
		String membership2 ="author";

		/* Step 1: Add application */
		//In Application Registry form:
		//- Create category & assign group A with membership B to access
		info("Add new category");
		navTool.goToApplicationRegistry();
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, permissions, true);

		//- Add application into category
		//Application is added into that category
		/* Step 2: Set permission for some users on application */
		//- Select to view new added application
		//- Click Add Permission and select group C with any membership to access this application
		//Selected group/membership are displayed as one row in Group and membership Selector form of selecting application
		info("Add Applications into category");
		magApp.addApplicationToCategory(categoryName, true, appName1, "Portlet", null, false, groupId1, membership1);
		magApp.addApplicationToCategory(categoryName, false, null, "Portlet", appName2, false, groupId2, membership2);

		/* Step 3: Check accessing above application by user has right to access category but does not have right to access application */
		//- Login by user in group A with membership B
		//- Go to Group editor/add new page
		//- In add new page by wizard form:
		//- Input valid values at step 1 & 2
		//- Click Next from step 2 to go to step 3
		//Show all application which user has right or not but 
		//+ with application which user has right: can drag & drop to user
		//+ with application which user does not have right, when drag & drop application -> Show protected content.
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.PUBLISHER,displayName,true,appName1,true);
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.PUBLISHER,displayName,true,appName2,false);

		/* Step 4: Check accessing above application by user has right to access the application but does not have right to access the category */
		//- Login by user in group C
		//- Add new page
		//- In add new page by wizard form:
		//- Input valid values at step 1 & 2
		//- Click Next from step 2 to go to step 3
		//The category and of course its application(s) can not be displayed for user to use
		magApp.viewCategoryAtManageApplicationsWithOtherUser(userType.ROOT,displayName,false);
		magAc.signIn(DATA_USER1,DATA_PASS);
		navTool.goToApplicationRegistry();

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName, true);
	}

	/** Edit Category which was deleted
	 * CaseID 73648
	 * Step 1: Show form to edit category( at window 1)
	 * Step 2: Delete category( at window 2)
	 * Step 3: Edit category( at window 1)
	 */
	@Test
	public void test10_EditCategoryWhichWasDeleted() {
		/*Declare variables*/
		String categoryName = "Category73648";
		String displayName = "Category 73648";
		String categoryDescription = "Add new category";
		String newDisplayName = "Category 03 update";
		String imageFileName = "case73648_EditCategoryWhichWasDeleted";

		/*Create data*/
		info("-- Create data --");
		info("Add new category");
		navTool.goToApplicationRegistry();
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, null, true);

		/* Step 1: Show form to edit category( at window 1) */
		//- Select a category in list
		//- Click Edit 

		//Edit selecting category form is shown:
		//- All current values of category are displayed in form
		//- Category Name is disable, can not be changed, others can be changed
		magApp.goToEditCategoryAtManageApplications(categoryName);
		WebElement elemSave = waitForAndGetElement(button.ELEMENT_SAVE_BUTTON);
		WebElement elemDisplayName = waitForAndGetElement(magApp.ELEMENT_FIELD_DISPLAY_NAME);

		/* Step 2: Delete category( at window 2) */
		//- Select category above from left pane and click Delete
		//- Click OK to confirm
		//The category is removed from left pane
		loginWithAnotherAccOnThesameBrowser(USER_ROOT, PASS_ROOT);
		navTool = new NavigationToolbar(newDriver);
		magApp = new ManageApplications(newDriver);
		navTool.goToApplicationRegistry();
		magApp.deleteCategoryAtManageApplications(categoryName, true);
		
		/* Step 3: Edit category( at window 1) */
		//- Change current some value
		//- Click Save
		//Show message alert the category doesn't exist or has been deleted.
		elemDisplayName.clear();
		elemDisplayName.sendKeys(newDisplayName);
		elemSave.click();
		Utils.captureScreen(imageFileName);
		newDriver.close();
	}

	/** Add a remote gadget with URL existing
	 * CaseID 73651
	 * Step 1: Show form to add a remote gadget
	 * Step 2: Add a remote gadget 
	 */
	@Test
	public void test11_AddARemoteGadgetWithURLExisting() {
		/*Declare variables*/
		String url = "http://www.labpixies.com/campaigns/memory_game/memory_game.xml";
		String title = "Memory Game";

		/* Step 1: Show form to add a remote gadget */
		//Click Add a remote gadget
		//A form is shown in one field to input ULR
		/* Step 2: Add a remote gadget  */
		//- Enter URL have value is existing in list
		//- Click Add 
		//Show message: The "URL" is existing, please select another one.
		navTool.goToApplicationRegistry();
		click(magApp.ELEMENT_SHOW_GADGET_ICON);
		info("Create new remote gadget");
		magApp.addRemoteGadget(url);
		waitForAndGetElement(magApp.ELEMENT_GADGET_DELETE_ICON.replace("${title}", title));
		info("Create new remote gadget again");
		magApp.addRemoteGadget(url);
		waitForAndGetElement(magApp.MESSAGE_GADGET_INFO_EXISTING_GADGET);
		button.ok();

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteGadget(title);
	}

	/** Add same name categories
	 * CaseID 73740
	 * Step 1: Add the first category
	 * Step 2: Add same name category
	 */
	@Test
	public void test12_AddSameNameCategories() {
		/*Declare variables*/
		String categoryName = "Category73740";
		String displayName = "Category 73740";
		String categoryDescription = "Add new category";

		/* Step 1: Add the first category */
		//In Application Registry:
		//Add a category with valid values
		//New category is added successfully and displayed in the left pane with no application inside
		info("Add new category");
		navTool.goToApplicationRegistry();
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, null, true);

		/* Step 2: Add same name category  */
		//Add new category that has name the same with new added category at step 1
		//Show message alert this category name already exists
		magApp.addNewCategoryAtManageApplications(categoryName, displayName, categoryDescription, true, null, false);
		waitForAndGetElement(magApp.MESSAGE_CATEGORY_EXISTING);
		button.ok();

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName, true);
	}

	/** Add new category with category name the same with existing but different by lower/upper case
	 * CaseID 73776
	 * Step 1: Show Application Registry form
	 * Step 2: Show Add new category form
	 * Step 3: Add the first category
	 * Step 4: Create new same Category name
	 */
	@Test
	public void test13_AddNewCategoryWithCategoryNameTheSameWithExistingButDifferentByLowerUpperCase() {
		/*Declare variables*/
		String categoryName1 = "Category73776";
		String categoryName2 = "category73776";
		String displayName = "Category 73776";
		String categoryDescription = "Add new category";

		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "editor");

		/* Step 1: Show Application Registry form */
		//- Login by admin
		//- Choose Administration/Application Registry on UserToolBarGroupPortlet
		//Application Registry form is shown, Categories form is selected as default
		navTool.goToApplicationRegistry();

		/* Step 2: Show Add new category form */
		//Click Add Category icon the action bar
		//Add category form appear includes:
		//- Category Settings
		//- Permission Settings
		/* Step 3: Add the first category */
		//Add a category with valid values ( e.g: test)
		//New category is added successfully and displayed in the left pane with no application inside
		magApp.addNewCategoryAtManageApplications(categoryName1, displayName, categoryDescription, true, permissions, true);

		/* Step 4: Create new same Category name */
		//Create the second category name with the same category name but different with existing by lower/upper case (e.g: Test)
		//New category is added successfully and displayed in the left pane with no application inside
		magApp.addNewCategoryAtManageApplications(categoryName2, displayName, categoryDescription, true, permissions, true);

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName1, true);
		magApp.deleteCategoryAtManageApplications(categoryName2, true);
	}

	/** Add public category  in Application registry
	 * CaseID 73920
	 * Step 1: Show Application Registry form
	 * Step 2: Show Add new category form
	 * Step 3: Check when choose public option for new category
	 * Step 4: Complete creating public category
	 */
	@Test
	public void test14_AddPublicCategoryInApplicationRegistry() {
		/*Declare variables*/
		String categoryName1 = "Category73920";
		String displayName = "Category 73920";
		String categoryDescription = "Add new category";

		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "editor");

		/* Step 1: Show Application Registry form */
		//- Login by admin
		//- Choose Administration/Application Registry on UserToolBarGroupPortlet
		//Application Registry form is shown, Categories form is selected as default
		navTool.goToApplicationRegistry();

		/* Step 2: Show Add new category form */
		//Click Add Category icon the action bar
		//Add category form appear includes:
		//- Category Settings
		//- Permission Settings
		/* Step 3: Check when choose public option for new category */
		//- Input valid values for Category Settings
		//- Choose Make it publication option in Permission Settings
		//The form to list specific groups/memberships below disappears
		/* Step 4: Complete creating public category */
		//Click Save
		//New category is added successfully and displayed in the left pane with no application inside
		magApp.addNewCategoryAtManageApplications(categoryName1, displayName, categoryDescription, true, permissions, true);

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName1, true);
	}

	/** Add private category  in Application registry
	 * CaseID 73938
	 * Step 1: Show Application Registry form
	 * Step 2: Show Add new category form
	 * Step 3: Check form to select group/membership for permission
	 * Step 4: Complete creating public category
	 * Step 5: Complete adding new category
	 */
	@Test
	public void test15_AddPrivateCategoryInApplicationRegistry() {
		/*Declare variables*/
		String categoryName1 = "Category73920";
		String displayName = "Category 73920";
		String categoryDescription = "Add new category";

		Map<String, String> permissions = new HashMap<String, String>();
		permissions.put("Platform/Content Management", "editor");

		/* Step 1: Show Application Registry form */
		//- Login by admin
		//- Choose Administration/Application Registry on UserToolBarGroupPortlet
		//Application Registry form is shown, Categories form is selected as default
		navTool.goToApplicationRegistry();

		/* Step 2: Show Add new category form */
		//Click Add Category icon the action bar
		//Add category form appear includes:
		//- Category Settings
		//- Permission Settings
		/* Step 3: Check form to select group/membership for permission */
		//- Input valid values for Category Settings
		//- Click Add Permission
		//Group and Membership form appears:
		//- Existing groups list is displayed in the left pane
		//- Members of selected group from left pane are displayed in right pane
		/* Step 4: Complete creating public category */
		//Select group from left pane then select membership from right pane
		//Selected group/membership are displayed as one row in Group and Membership form
		/* Step 5: Complete adding new category */
		//Click Save
		//New category is added successfully and displayed in the left pane with no application inside
		magApp.addNewCategoryAtManageApplications(categoryName1, displayName, categoryDescription, true, permissions, true);

		/*Clear data*/
		info("-- Clear data --");
		magApp.deleteCategoryAtManageApplications(categoryName1, true);
	}
}
