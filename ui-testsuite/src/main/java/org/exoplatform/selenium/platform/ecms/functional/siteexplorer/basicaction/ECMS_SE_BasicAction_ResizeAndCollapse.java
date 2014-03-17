package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.basicaction;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ManageDrive;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ResizeAndCollapse;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.exoplatform.selenium.TestLogger.info;

/**
 * @date 7/6/2013
 * @author lientm
 *
 */
public class ECMS_SE_BasicAction_ResizeAndCollapse extends PlatformBase {
	ManageAccount magAcc;
	ResizeAndCollapse resize;
	NavigationToolbar nav;
	ActionBar actBar;
	EcmsBase ecms;
	ManageDrive magDri;
	ManageMember magMember;

	public final String DATA_USER = "john";
	//public final String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		magAcc = new ManageAccount(driver);
		resize = new ResizeAndCollapse(driver);
		nav = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		magDri = new ManageDrive(driver);

		magAcc.signIn(DATA_USER, DATA_PASS);
		magMember = new ManageMember(driver);
	}

	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 74870 -> Show the resize pointer of the left panel from Intranet/Documents
	 * 
	 */
	@Test
	public void test01_ShowResizePointerOfLeftPanel_IntranetDocuments(){
		nav.goToPersonalDocuments();
		resize.showSideBar();

		info("Panel limit is not displayed");
		waitForElementNotPresent(resize.ELEMENT_PANEL_COLLAPSE_BUTTON);

		info("Mouse over panel limit -> panel limit is displayed");
		mouseOverAndClick(resize.ELEMENT_PANEL_LIMIT);
		waitForAndGetElement(resize.ELEMENT_PANEL_COLLAPSE_BUTTON);
	}

	/**CaseId: 74871 -> Saved resize of the right panel from Intranet/documents
	 * Resize column in right panel
	 * -> Do not saved this resize as test case in Qmetry
	 */
	//	REMOVED
	//	@Test
	//	public void test02_SavedResizeOfTheRightPanel_IntranetDocuments(){
	//		magDri.addView2Drive("Web", "Personal Documents");
	//		actBar.goToViewMode("Web");
	//		
	//		String before = waitForAndGetElement(resize.ELEMENT_COLUMN_NAME.replace("${name}", "Document Title")).getAttribute("style").replace("width: ", "").replace(";", "");		
	//		info("Size of column Document Title before resize is " + before);
	//		resize.resizeColumnInRightPanel("2", 100);
	//		String after = waitForAndGetElement(resize.ELEMENT_COLUMN_NAME.replace("${name}", "Document Title")).getAttribute("style").replace("width: ", "").replace(";", "");		
	//		info("Size of column Document Title after resize is " + after);
	//		assert before != after;
	//		
	//		nav.goToHomePage();
	//		nav.goToPersonalDocuments();
	//		actBar.goToViewMode("Web");
	//		String after1 = waitForAndGetElement(resize.ELEMENT_COLUMN_NAME.replace("${name}", "Document Title")).getAttribute("style").replace("width: ", "").replace(";", "");		
	//		info("Size of column Document Title saving is " + after1);
	//		
	//	}

	/**CaseId: 74873 -> Resize the left panel from Intranet/Documents
	 * this case is having issue ECMS-5368
	 */
	@Test
	public void test03_ResizeLeftPanel_IntranetDocuments(){
		nav.goToPersonalDocuments();
		resize.showSideBar();

		resize.resizeLimitPanel(200);
		String left = waitForAndGetElement(resize.ELEMENT_LEFT_CONTAINER).getAttribute("style").replace("width: ", "");
		info("Size of left container after resize " + left);
		assert left != "";
		assert left != "240px;";
	}

	/**CaseId: 74881 -> Actions in the right panel after resize of left panel from Intranet Documents
	 * -> this case is having issue ECMS-5368
	 */
	//@Test
	public void test04_ActionInRightPanelAfterResizeLeftPanel_IntranetDocument(){
		nav.goToPersonalDocuments();
		resize.showSideBar();

		resize.resizeLimitPanel(500);
		String left = waitForAndGetElement(resize.ELEMENT_LEFT_CONTAINER).getAttribute("style").replace("width: ", "").replace(";", "");
		info("Size of left container to maximum is" + left);

		waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForElementNotPresent(actBar.ELEMENT_NEW_FOLDER_LINK);
		waitForElementNotPresent(ecms.ELEMENT_UPLOAD_FILE_LINK);
		waitForElementNotPresent(ecms.ELEMENT_PERMISSION_LINK);

		click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForAndGetElement(actBar.ELEMENT_NEW_FOLDER_LINK);
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_FILE_LINK, DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ecms.ELEMENT_PERMISSION_LINK);	
	}

	/**CaseId: 74883 -> Show the resize pointer of the left panel from Content explorer
	 * 
	 */
	@Test
	public void test05_ShowResizePointerOfLeftPanel_ContentExplorer(){
		nav.goToSiteExplorer();

		info("Panel limit is not displayed");
		waitForElementNotPresent(resize.ELEMENT_PANEL_COLLAPSE_BUTTON);

		info("Mouse over panel limit -> panel limit is displayed");
		mouseOverAndClick(resize.ELEMENT_PANEL_LIMIT);

		waitForAndGetElement(resize.ELEMENT_PANEL_COLLAPSE_BUTTON);
	}

	/**CaseId: 74884 -> Resize the left panel from Content explorer
	 * 
	 */
	@Test
	public void test06_ResizeLeftPanel_ContentExplorer(){
		nav.goToSiteExplorer();

		resize.resizeLimitPanel(200);
		String left = waitForAndGetElement(resize.ELEMENT_LEFT_CONTAINER).getAttribute("style").replace("width: ", "").replace(";", "");
		info("Size of left container after resize left panel is" + left);
		assert left != "";
		assert left != "240px;";
	}

	/**CaseId: 74890 -> Saved resize of the right panel from Content explorer
	 * resize "Document title" column
	 * -> Do not saved this resize as test case in Qmetry
	 */
	@Test
	public void test07_SaveResizeOfRightPanel_ContentExplorer(){
		nav.goToSiteExplorer();

		String before = waitForAndGetElement(resize.ELEMENT_COLUMN_NAME.replace("${name}", "Document Title")).getAttribute("style").replace("width: ", "").replace(";", "");		
		info("Size of column Document Title before resize is " + before);
		resize.resizeColumnInRightPanel("2", 100);
		String after = waitForAndGetElement(resize.ELEMENT_COLUMN_NAME.replace("${name}", "Document Title")).getAttribute("style").replace("width: ", "").replace(";", "");		
		info("Size of column Document Title after resize is " + after);
		assert before != after;

		nav.goToHomePage();
		nav.goToSiteExplorer();
		String after1 = waitForAndGetElement(resize.ELEMENT_COLUMN_NAME.replace("${name}", "Document Title")).getAttribute("style").replace("width: ", "").replace(";", "");		
		info("Size of column Document Title saving is " + after1);
	}

	/**CaseId: 74894 -> Actions in the right panel after resize of left panel from Settings
	 * 
	 */
	@Test
	public void test08_ActionInRightPanelAfterResizeLeftPanel_ContentExplorer(){
		nav.goToSiteExplorer();

		resize.resizeLimitPanel(600);
		String left = waitForAndGetElement(resize.ELEMENT_LEFT_CONTAINER).getAttribute("style").replace("width: ", "").replace(";", "");
		info("Size of left container to maximum is" + left);

		waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		//waitForElementNotPresent(actBar.ELEMENT_NEW_CONTENT_LINK);
		waitForElementNotPresent(ecms.ELEMENT_UPLOAD_FILE_LINK);
		waitForElementNotPresent(ecms.ELEMENT_PERMISSION_LINK);
		waitForElementNotPresent(ecms.ELEMENT_ADD_TRANSLATION_LINK);

		click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForAndGetElement(actBar.ELEMENT_NEW_CONTENT_LINK);
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_FILE_LINK, DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ecms.ELEMENT_PERMISSION_LINK);
		waitForAndGetElement(ecms.ELEMENT_ADD_TRANSLATION_LINK);
	}

	/**CaseId: 74896 -> Show the resize pointer of the left panel from Space/Document
	 * @author hzekri
	 */
	@Test
	public void test09_ShowResizePointerOfLeftPanel_SpaceDocuments(){

		//Declare variables
		String spacename = "SpaceTestResize09";
		String spacedesc = "Description Of SpaceTestResize09";

		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);

		//Open Documents in this space
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);

		// Display Left Panel
		resize.showSideBar();

		info("Panel limit is not displayed");
		waitForElementNotPresent(resize.ELEMENT_PANEL_COLLAPSE_BUTTON);

		info("Mouse over panel limit -> panel limit is displayed");
		mouseOverAndClick(resize.ELEMENT_PANEL_LIMIT);
		waitForAndGetElement(resize.ELEMENT_PANEL_COLLAPSE_BUTTON);

		// remove space after using it
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename,300000);
	}

	/**CaseId: 74899 -> Resize the left panel from Space/Documents
	 *  @author hzekri
	 */
	@Test
	public void test10_ResizeLeftPanel_SpaceDocuments(){
		//Declare variables
		String spacename = "SpaceTestResize10";
		String spacedesc = "Description Of SpaceTestResize10";

		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);

		//Open Documents in this space
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);

		resize.showSideBar();

		resize.resizeLimitPanel(200);
		String left = waitForAndGetElement(resize.ELEMENT_LEFT_CONTAINER).getAttribute("style").replace("width: ", "");
		info("Size of left container after resize " + left);
		assert left != "";
		assert left != "240px;";

		// reset width of left panel  to default
		resize.resizeLimitPanel(240);

		// remove space after using it
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename,300000);
	}

	/**CaseId: 74907 -> Actions in the right panel after resize of left panel from Space Documents
	 * @author hzekri
	 */
	@Test
	public void test11_ActionInRightPanelAfterResizeLeftPanel_SpaceDocument(){
		//Declare variables
		String spacename = "SpaceTestResize11";
		String spacedesc = "Description Of SpaceTestResize11";

		//Add new space
		magMember.goToMySpacePage();
		magMember.addNewSpace(spacename, spacedesc);

		//Open Documents in this space
		waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		click(magMember.ELEMENT_DOCUMENTS_TAB);

		resize.showSideBar();

		resize.resizeLimitPanel(500);
		String left = waitForAndGetElement(resize.ELEMENT_LEFT_CONTAINER).getAttribute("style").replace("width: ", "").replace(";", "");
		info("Size of left container to maximum is" + left);

		waitForAndGetElement(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForElementNotPresent(actBar.ELEMENT_NEW_FOLDER_LINK);
		waitForElementNotPresent(ecms.ELEMENT_UPLOAD_FILE_LINK);
		waitForElementNotPresent(ecms.ELEMENT_PERMISSION_LINK);

		click(ecms.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		waitForAndGetElement(actBar.ELEMENT_NEW_FOLDER_LINK);
		waitForAndGetElement(ecms.ELEMENT_UPLOAD_FILE_LINK, DEFAULT_TIMEOUT, 1, 2);
		waitForAndGetElement(ecms.ELEMENT_PERMISSION_LINK);

		// reset width of left panel  to default
		resize.resizeLimitPanel(240);

		// remove space after using it
		magMember.goToMySpacePage();
		magMember.deleteSpace(spacename,300000);
	}
}