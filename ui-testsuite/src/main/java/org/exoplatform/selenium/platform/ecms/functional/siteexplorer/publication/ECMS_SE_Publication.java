package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publication;

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

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: HaKT
 * @date: 10/09/2013
 */
public class ECMS_SE_Publication extends PlatformBase{

	ManageAccount magAc;
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ContentTemplate conTemp;
	ContextMenu conMenu;
	EcmsBase ecms;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAc = new ManageAccount(driver);
		magAc.signIn("john", DATA_PASS); 
		actBar = new ActionBar(driver);
		conTemp =  new ContentTemplate(driver);
		ecms = new EcmsBase(driver);
		conMenu = new ContextMenu(driver);
		button= new Button(driver);
		navToolBar.goToSiteExplorer();
	}

	@AfterMethod
	public void afterTest(){
		magAc.signOut();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**CaseId: 66269 -> Publish content when only set time in From field
	 * Create new content on site explorer
	 * Click Publication on action bar
	 * Choose staged
	 * Move to scheduled tab
	 * Choose Only From field
	 * Save
	 */
	@Test
	public void test01_PublishContentWhenOnlySetTimeInFromField(){

		String doc_Name = "doc_test_66269";
		String doc_Content = "Content of File: Publish content when only set time in From field";
		By file_locator = By.linkText(doc_Name);

		info("Publish content when only set time in From field");

		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form, check draft status and change status to Staged
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_DRAFT_STATUS);
		button.close();

		// Set time in From Field and check status
		String oneMinuteAfterCurrentTime = addMinuteToCurrentDateTime(1);
		actBar.managePublication("Staged",oneMinuteAfterCurrentTime,"");

		// Wait for one minute plus time so that changes take effect
		Utils.pause(180000);
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);

		// Remove test data
		conMenu.deleteDocument(file_locator);
	}

	/**CaseId: 66270 -> Publish content when only set time in To field
	 * @author hzekri
	 */
	@Test
	public void test02_PublishContentWhenOnlySetTimeInToField(){

		String doc_Name = "doc_test_66270";
		String doc_Content = "Content of File: Publish content when only set time in To field";
		By file_locator = By.linkText(doc_Name);

		info("Publish content when only set time in To field");
		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form, change status to Staged and Set time in To Field and check status
		String oneMinuteAfterCurrentTime = addMinuteToCurrentDateTime(3);
		actBar.managePublication("Staged","",oneMinuteAfterCurrentTime);
		Utils.pause(90000);
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
		Utils.pause(90000);
		waitForElementNotPresent(actBar.ELEMENT_CURRENT_STATUS);
		button.close();

		// Remove test data
		conMenu.deleteDocument(file_locator);
	}

	/**CaseId: 66271 -> Publish content when put invalid date format
	 * @author hzekri
	 */
	@Test
	public void test03_PublishContentWhenPutInvalidDateFormat(){

		String doc_Name = "doctest66271";
		String doc_Content = "Content of File: Publish content when put invalid date format";
		By file_locator = By.linkText(doc_Name);
		String invalidDate = "10/10/2013";

		info("Publish content when put invalid date format");

		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form and change status to Staged
		actBar.managePublication("Staged", invalidDate,null,"0");

		// Remove test data
		conMenu.deleteDocument(file_locator);
	}

	/**CaseId: 66272 -> Publish content with setting publication time in both Form and To field
	 * @author hzekri
	 */
	@Test
	public void test04_PublishContentWithTimeInBothFromAndToFields(){

		String doc_Name = "doc_test_66272";
		String doc_Content = "Content of doc_66272";
		By file_locator = By.linkText(doc_Name);

		info("Publish content with setting publication time in both Form and To field");
		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form, change status to Staged, Set time in From and To Fields and check status
		String oneMinuteAfterCurrentTime = addMinuteToCurrentDateTime(1);
		String fourMinuteAfterCurrentTime = addMinuteToCurrentDateTime(4);
		actBar.managePublication("Staged",oneMinuteAfterCurrentTime,fourMinuteAfterCurrentTime);
		Utils.pause(100000);

		//Check status of document
		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);
		Utils.pause(130000);
		waitForElementNotPresent(actBar.ELEMENT_CURRENT_PUBLIC_STATUS);

		// Remove test data
		conMenu.deleteDocument(file_locator);
	}

	/**CaseId: 66273, 67233 -> Add Stage status from Draft status
	 * @author hzekri
	 */
	@Test
	public void test05_AddStageStatusFromDraftStatus(){

		String doc_Name = "doc_test_66273";
		String doc_Content = "Content of File: Add Stage status from Draft status";
		By file_locator = By.linkText(doc_Name);

		info("Add Stage status from Draft status");
		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form and change status to Staged
		actBar.managePublication("Staged");

		// Remove test data
		conMenu.deleteDocument(file_locator);
	}

	/**CaseId: 67105 -> Check showing Manage publication form
	 * @author hzekri
	 * This case has an issue ECMS-5815
	 */
	@Test(groups=("error"))
	public void test06_CheckShowingManagePublicationForm(){

		String doc_Name = "doc_test_67105";
		String doc_Content = "Content of File: Check showing Manage publication form";
		By file_locator = By.linkText(doc_Name);

		info("Check showing Manage publication form");
		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form and change status
		actBar.managePublication("Published");

		actBar.openManagePublicationForm();
		waitForAndGetElement(actBar.ELEMENT_REVISION.replace("${state}", "Published"));
		button.close();
		// Remove test data
		conMenu.deleteDocument(file_locator);
	}

	/**CaseId: 67190 -> Add Pending revision from Draft revision
	 * @author hzekri
	 */
	@Test
	public void test07_AddPendingRevisionFromDraftRevision(){

		String doc_Name = "doc_test_67190";
		String doc_Content = "Content of File: Add Pending revision from Draft revision";
		By file_locator = By.linkText(doc_Name);

		info("Add Pending revision from Draft revision");
		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form and change status
		actBar.managePublication("Pending");

		// Remove test data
		conMenu.deleteDocument(file_locator);

	}

	/**CaseId: 67191 -> Add Published status from Draft status
	 * @author hzekri
	 */
	@Test
	public void test08_AddPublishedStatusFromDraftStatus(){

		String doc_Name = "doc_test_67191";
		String doc_Content = "Content of File: Add Published status from Draft status";
		By file_locator = By.linkText(doc_Name);

		info("Add Published status from Draft status");
		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form and change status
		actBar.managePublication("Published");

		// Remove test data
		conMenu.deleteDocument(file_locator);
	}

	/**CaseId: 67519 -> Add Approve status from Draft status
	 * @author hzekri
	 */
	@Test
	public void test09_AddApproveStatusFromDraftStatus(){

		String doc_Name = "doc_test_67519";
		String doc_Content = "Content of File: Add Approve status from Draft status";
		By file_locator = By.linkText(doc_Name);

		info("Add Approve status from Draft status");
		// Add a content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(doc_Name, doc_Content, null);

		// Show Publication Form and change status
		actBar.managePublication("Approved");

		// Remove test data
		conMenu.deleteDocument(file_locator);

	}

	/**CaseId: 94785 -> Publish child node which is nt:file when parent has been published
	 * @author hzekri
	 */
	@Test
	public void test10_PublishChildNodeNTFileWhenParentPublished(){

		String parent_Name = "parent_test_94785";
		String parent_Content = "Parent node test 94785";
		String child_Name = "child_test_94785";
		String child_Content = "Child node test 94785";
		By file_locator_parent = By.linkText(parent_Name);

		info("Publish child node which is nt:file when parent has been published");
		// Add parent content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(parent_Name, parent_Content, "", "", "", "");

		// Add child content
		actBar.goToAddNewContent();
		conTemp.createNewFile(child_Name, child_Content, null);

		// Publish parent content
		ecms.goToNode(file_locator_parent);
		actBar.managePublication("Published");

		// Check Publish icon for child content
		ecms.goToNode(child_Name);
		if ((waitForAndGetElement(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK, 5000, 0) != null )){
			click(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		waitForElementNotPresent(actBar.ELEMENT_PUBLISH_ICON);

		// Remove test data
		conMenu.deleteDocument(file_locator_parent);


	}

	/**CaseId: 94786 -> Publish child node which is not nt:file when parent has been published
	 * @author hzekri
	 */
	@Test
	public void test11_PublishChildNodeNotNTFileWhenParentPublished(){

		String parent_Name = "parent_test_94786";
		String parent_Content = "Parent node test 94786";
		String child_Name = "child_test_94786";
		String child_Sum = "Child node test 94786";
		By file_locator = By.linkText(parent_Name);

		info("Publish child node which is not nt:file when parent has been published");
		// Add parent content
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(parent_Name, parent_Content, "", "", "", "");

		// Add child content
		actBar.goToAddNewContent();
		conTemp.createNewAnnouncement(child_Name, child_Sum);

		// Publish parent content
		ecms.goToNode(file_locator);
		actBar.managePublication("Published");

		// Check Publish icon for child content
		ecms.goToNode(parent_Name+"/"+child_Name);
		if ((waitForAndGetElement(actBar.ELEMENT_PUBLICATION, 10000, 0) == null )){
			click(actBar.ELEMENT_MORE_LINK_WITHOUT_BLOCK);
		}
		waitForAndGetElement(actBar.ELEMENT_PUBLICATION);

		// Remove test data
		conMenu.deleteDocument(file_locator);
	}
}
