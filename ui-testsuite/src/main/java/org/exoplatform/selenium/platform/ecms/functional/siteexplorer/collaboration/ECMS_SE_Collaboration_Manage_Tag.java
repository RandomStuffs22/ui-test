package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.collaboration;

import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author vuna2
 * August, 21th, 2013
 *
 */
public class ECMS_SE_Collaboration_Manage_Tag extends PlatformBase{
	//Platform
	ManageAccount magAc;
	NavigationToolbar navToolbar;

	//Ecms
	ContentTemplate cTemplate;
	SitesExplorer siteExp;
	ContextMenu cMenu;
	ActionBar actBar;
	EcmsBase ecms;

	@BeforeMethod
	public void setUpBeforeTest(){
		initSeleniumTest();
		driver.get(baseUrl);
		magAc = new ManageAccount(driver);
		navToolbar = new NavigationToolbar(driver);
		actBar = new ActionBar(driver);
		ecms = new EcmsBase(driver);
		cTemplate = new ContentTemplate(driver);
		siteExp = new SitesExplorer(driver);
		cMenu = new ContextMenu(driver);
		magAc.signIn(DATA_USER1, DATA_PASS);
		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterTest(){
		info("ECMS_SE_Collaboration_Manage_Tag: Finish testing");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * Qmetry ID: 67492
	 * Add 1 public tag for  document
	 * 
	 */
	@Test
	public void test01_Add1PublicTagForDocument(){
		String webContentTitle = "Add1PublicTagForDocument";
		String[] tagName = {"Public_Tag_1"};

		info("Go to Sites Explorer");
		navToolbar.goToSiteExplorer();

		info("Create a new document");
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(webContentTitle, webContentTitle, "", "", "", "");

		info("Add a tag for this document: " + webContentTitle);
		siteExp.addTagForNode(tagName);

		info("User [mary] can see this tag");
		magAc.signOut();
		magAc.signIn(DATA_USER2, DATA_PASS);
		navToolbar.goToSiteExplorer();
		click(siteExp.ELEMENT_TAG_CLOUD);
		waitForAndGetElement(siteExp.ELEMENT_TAG_IN_CONTAINER_LIST.replace("${tagName}", tagName[0]));

		info("Reset data");
		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);
		navToolbar.goToSiteExplorer();
		siteExp.goToEditTag();
		siteExp.deleteTag(tagName);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(By.linkText(webContentTitle));
	}

	/**
	 * Qmetry ID: 67493
	 * Add 1 public tag for  uploaded file
	 * 
	 */
	@Test
	public void test01_Add1PublicTagForUploadedFile(){
		String[] tagName = {"Public_Tag_2"};
		String fileToUpload = "ECMS_DMS_SE_Upload_docfile.doc";
		By elementFile = By.linkText(fileToUpload);

		info("Go to Sites Explorer");
		navToolbar.goToSiteExplorer();

		info("Upload a file");
		actBar.uploadFile("TestData/" + fileToUpload);
		ecms.goToNode(elementFile);

		info("Add a tag for this document: " + fileToUpload);
		siteExp.addTagForNode(tagName);

		info("User [mary] can see this tag");
		magAc.signOut();
		magAc.signIn(DATA_USER2, DATA_PASS);
		navToolbar.goToSiteExplorer();
		click(siteExp.ELEMENT_TAG_CLOUD);
		waitForAndGetElement(siteExp.ELEMENT_TAG_IN_CONTAINER_LIST.replace("${tagName}", tagName[0]));

		info("Reset data");
		magAc.signOut();
		magAc.signIn(DATA_USER1, DATA_PASS);
		navToolbar.goToSiteExplorer();
		siteExp.goToEditTag();
		siteExp.deleteTag(tagName);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(elementFile);
	}
}