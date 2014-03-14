package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publishactivities;

import static org.exoplatform.selenium.TestLogger.info;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.exoplatform.selenium.platform.HomePageActivity;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: PhuongDT
 * @date: 17/09/2013
 */
public class ECMS_SE_PublishActivities_ContentActivities_Add extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	ActionBar actBar;
	NavigationToolbar navToolBar;
	ManageMember magMember;
	HomePageActivity activity;
	
	//Ecms
	EcmsBase ecms;
	ContentTemplate cTemplate;
	SitesExplorer siteExp;
	ContextMenu cMenu;
	
	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER1);
		magAcc = new ManageAccount(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		siteExp = new SitesExplorer(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		activity = new HomePageActivity(driver);
		magMember = new ManageMember(driver);
		magAcc.signIn(DATA_USER1, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}
	 
	 /**
	 * == Add Content activity after Add an Accessible Media content  in CE ==
	 * Test case ID: 76951
	 * Step 1: Add Accessible Media content
	 * Step 2: Check content activity after add Accessible Media
	 */
	 @Test
	 public void test01_AddContentActivityAfterAddAnAccessibleMediaContentInCE(){
		 //Declare variable
		 String node = "node01";
		 By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		 
		 /*Step 1: Add Accessible Media content*/ 
	     //Open Sites Explorer( Except personal drive because document in this drive does not have content activity)
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     
	     //Add a new content: Accessible Media
	     actBar.goToAddNewContent();
	     cTemplate.createNewAccessibleMedia(node);
	     
	     /*Step 2: Check content activity after add Accessible Media*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Accessible Media", "", "", "", "", "");
	     
	     /*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	 }
	 
	 /**
	 * == Add Content activity after add a Contact Us content in CE ==
	 * Test case ID: 76952
	 * Step 1: Add "Contact Us" content
	 * Step 2: Check content activity after add contact us
	 */
	 @Test
	 public void test02_AddContentActivityAfterAddAContactUsContentInCE(){
		 //Declare variable
		 DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd - hh:mm");
		 
		 /*Step 1: Add "Contact Us" content*/ 
	     //Open Sites Explorer( Except personal drive because document in this drive does not have content activity)
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     
	     //Add a new content: Contact Us
	     actBar.goToAddNewContent();
	     cTemplate.createNewContactUs();
	     //get current date time with Date()
	     Date date = new Date();
	     String node = dateFormat.format(date).replace(":", "h");
	     By bNode = By.xpath("//*[contains(text(), '"+node+"')]");
	     
	     /*Step 2: Check content activity after add contact us*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     waitForAndGetElement(bNode);
	     
	     /*Clear data*/
	     info("clear data");
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     cMenu.deleteDocument(bNode);
	 }
	 
	 /**
	 * == Add Content activity after add a File content in CE ==
	 * Test case ID: 76953
	 * Step 1: Step 1: Add File content
	 * Step 2: Check content activity after add File content
	 */
	 @Test
	 public void test03_AddContentActivityAfterAddAFileContentInCE(){
		 //Declare variable
		 String node = "node03";
		 By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		 
		 /*Step 1: Add File content*/ 
	     //Open Sites Explorer( Except personal drive because document in this drive does not have content activity)
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     
	     //Add a new content: File
	     actBar.goToAddNewContent();
	     cTemplate.createNewFile(node, node, node);
	     
	     /*Step 2: Check content activity after add File content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "File", "", "", "", "", "");
	     
	     /*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	 }
	 
	 /**
	 * == Add Content activity after add a Free Layout Webcontent content  in CE ==
	 * Test case ID: 76954
	 * Step 1: Add "Free Layout Webcontent"
	 * Step 2: Check content activity after add Free Layout Webcontent
	 */
	 @Test
	 public void test04_AddContentActivityAfterAddAFreeLayoutWebcontentContentInCE(){
		 //Declare variable
		 String node = "node04";
		 By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		 
		 /*Step 1: Add "Free Layout Webcontent"*/ 
	     //Open Sites Explorer( Except personal drive because document in this drive does not have content activity)
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     
	     //Add a new content: Free Layout
	     actBar.goToAddNewContent();
	     cTemplate.createNewWebContent(node,node,"", "", "", "");
	     
	     /*Step 2: Check content activity after add Free Layout Webcontent*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
	     
	     /*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	 }
	 
	 /**
	 * == Add Content activity after add a Picture on head layout content  in CE ==
	 * Test case ID: 76955
	 * Step 1: Add Picture on Head Layout content
	 * Step 2: Check content activity after add Picture on Head Layout content
	 * //exo:pictureOnHeadWebContent is changed to Illustrated Web Content
	 */
	 @Test
	 public void test05_AddContentActivityAfterAddAPictureOnHeadLayoutContentInCE(){
		 //Declare variable
		 String node = "node05";
		 By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		 
		 /*Step 1: Add Picture on Head Layout content*/ 
	     //Open Sites Explorer( Except personal drive because document in this drive does not have content activity)
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     
	     //Add a new content: Picture on head layout
	     actBar.goToAddNewContent();
	     cTemplate.createNewIllustratedWebContent(node, "", "", "", "", "", "","",true);
	     
	     /*Step 2: Check content activity after add Picture on Head Layout content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Illustrated Web Content", "", "", "", "", "");
	     
	     /*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	 }

	 /**
	 * == Add Content activity after add a Product content  in CE ==
	 * Test case ID: 76956
	 * Step 1: Ad Product content
	 * Step 2: Check content activity after add Product content
	 */
	 @Test
	 public void test06_AddContentActivityAfterAddAProductContentInCE(){
		 //Declare variable
		 String node = "node06";
		 By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		 
		 /*Step 1: Ad Product content*/ 
	     //Open Sites Explorer( Except personal drive because document in this drive does not have content activity)
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     
	     // Add a new content: Product
	     actBar.goToAddNewContent();
	     cTemplate.createFullNewProduct(node, "", node , "", "");
	     
	     /*Step 2: Check content activity after add Product content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Product", "", "", "", "", "");
	     
	     /*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		click(ELEMENT_SITE_EXPLORER_HOME);
		cMenu.deleteDocument(bNode);
	 }
	 
	 /**
	 * == Add Content activity after add a Web Link content  in CE ==
	 * Test case ID: 76957
	 * Step 1: Ad Web Link content
	 * Step 2: Check content activity after add Web Link content
	 */
	 @Test
	 public void test07_AddContentActivityAfterAddAWebLinkContentInCE(){
		 //Declare variable
		 String node = "node07";
		 By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		 
		 /*Step 1: Ad Web Link content*/ 
	     //Open Sites Explorer( Except personal drive because document in this drive does not have content activity)
	     info("-- Open Sites Explorer --");
	     navToolBar.goToSiteExplorer();
	     
	     // Add a new content: Web Link
	     actBar.goToAddNewContent();
	     cTemplate.createNewWebLink(node,node);
	     
	     /*Step 2: Check content activity after add Web Link content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Web Link", "", "", "", "", "");
	     
	     /*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	 }
	 
	 /**
	 * == Add Content activity after Add an Accessible Media content in space ==
	 * Test case ID: 77318
	 * Step 1: Add new space
	 * Step 2: Add Accessible Media content
	 * Step 3: Check content activity after add Accessible Media
	 */
	 @Test
	 public void test08_AddContentActivityAfterAddAnAccessibleMediaContentInSpace(){
		 //Declare variable
		 String node = "node08";
		 String spacename = "Space08";
		 String spacedesc = "Description Of Space08";
		 
		 /*Step 1: Add new space*/
		 //Add new space
		 magMember.goToMySpacePage();
		 magMember.addNewSpace(spacename, spacedesc);
		 
		 /*Step 2: Add Accessible Media content*/
		 //Open Documents in this space
		 waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		 click(magMember.ELEMENT_DOCUMENTS_TAB);
	     
		 //Add new content icon
		 magMember.addItem2ActionBarOfSpace("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, spacename, "List", "List");
		 
	     //Add a new content: Accessible Media
	     actBar.goToAddNewContent();
	     cTemplate.createNewAccessibleMedia(node);
	     
	     /*Step 3: Check content activity after add Accessible Media*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Accessible Media", "", "", "", "", "");
	     
	     /*Clear data*/
	     info("clear data");
	     magMember.goToMySpacePage();
	     magMember.deleteSpace(spacename,300000);
	 }
	 
	 /**
	 * == Add Content activity after add a Contact Us content in space ==
	 * Test case ID: 77319
	 * Step 1: Add new space
	 * Step 2: Add "Contact Us" content
	 * Step 3: Check content activity after add contact us
	 */
	 @Test
	 public void test09_AddContentActivityAfterAddAContactUsContentInSpace(){
		 //Declare variable
		 DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd - hh:mm");
		 String spacename = "Space09";
		 String spacedesc = "Description Of Space09";
		
		 /*Step 1: Add new space*/
		 //Add new space
		 magMember.goToMySpacePage();
		 magMember.addNewSpace(spacename, spacedesc);
		 
		 /*Step 2: Add "Contact Us" content*/ 
		 //Open Documents in this space
		 waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		 click(magMember.ELEMENT_DOCUMENTS_TAB);
		 
		//Add new content icon
		 magMember.addItem2ActionBarOfSpace("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, spacename, "List", "List");
	     
	     //Add a new content: Contact Us
	     actBar.goToAddNewContent();
	     cTemplate.createNewContactUs();
	     //get current date time with Date()
	     Date date = new Date();
	     String node = dateFormat.format(date).replace(":", "h");
	     By bNode = By.xpath("//*[contains(text(), '"+node+"')]");
	     
	     /*Step 2: Check content activity after add contact us*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     waitForAndGetElement(bNode);
	     
	     /*Clear data*/
	     info("clear data");
	     magMember.goToMySpacePage();
	     magMember.deleteSpace(spacename,300000);
	 }
	 
	 /**
	 * == Add Content activity after add a File content in space ==
	 * Test case ID: 77320
	 * Step 1: Add new space
	 * Step 2: Add File content
	 * Step 3: Check content activity after add File content
	 */
	 @Test
	 public void test10_AddContentActivityAfterAddAFileContentInSpace(){
		 //Declare variable
		 String node = "node10";
		 String spacename = "Space10";
		 String spacedesc = "Description Of Space10";
		 
		 /*Step 1: Add new space*/
		 //Add new space
		 magMember.goToMySpacePage();
		 magMember.addNewSpace(spacename, spacedesc);
		 
		 /*Step 2: Add File content*/ 
		 //Open Documents in this space
		 waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		 click(magMember.ELEMENT_DOCUMENTS_TAB);
		 
		//Add new content icon
		 magMember.addItem2ActionBarOfSpace("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, spacename, "List", "List");
	     
	     //Add a new content: File
	     actBar.goToAddNewContent();
	     cTemplate.createNewFile(node, node, node);
	     
	     /*Step 3: Check content activity after add File content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "File", "", "", "", "", "");
	     
	     /*Clear data*/
	     info("clear data");
	     magMember.goToMySpacePage();
	     magMember.deleteSpace(spacename,300000);
	 }
	 
	 /**
	 * == Add Content activity after add a Free Layout Webcontent content  in space ==
	 * Test case ID: 77321
	 * Step 1: Add new space
	 * Step 2: Add "Free Layout Webcontent"
	 * Step 3: Check content activity after add Free Layout Webcontent
	 */
	 @Test
	 public void test11_AddContentActivityAfterAddAFreeLayoutWebcontentContentInSpace(){
		 //Declare variable
		 String node = "node11";
		 String spacename = "Space11";
		 String spacedesc = "Description Of Space11";

		 /*Step 1: Add new space*/
		 //Add new space
		 magMember.goToMySpacePage();
		 magMember.addNewSpace(spacename, spacedesc);
		 
		 /*Step 2: Add "Free Layout Webcontent"*/ 
		 //Open Documents in this space
		 waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		 click(magMember.ELEMENT_DOCUMENTS_TAB);
		 
		//Add new content icon
		 magMember.addItem2ActionBarOfSpace("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, spacename, "List", "List");
	     
	     //Add a new content: Free Layout
	     actBar.goToAddNewContent();
	     cTemplate.createNewWebContent(node,node,"", "", "", "");
	     
	     /*Step 3: Check content activity after add Free Layout Webcontent*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
	     
	     /*Clear data*/
	     info("clear data");
	     magMember.goToMySpacePage();
	     magMember.deleteSpace(spacename,300000);
	 }
	 
	 /**
	 * == Add Content activity after add a Picture on head layout content  in space ==
	 * Test case ID: 77322
	 * Step 1: Add new space
	 * Step 2: Add Picture on Head Layout content
	 * Step 3: Check content activity after add Picture on Head Layout content
	 * //exo:pictureOnHeadWebContent is changed to Illustrated Web Content
	 */
	 @Test
	 public void test12_AddContentActivityAfterAddAPictureOnHeadLayoutContentInSpace(){
		 //Declare variable
		 String node = "node12";
		 String spacename = "Space12";
		 String spacedesc = "Description Of Space12";
		 
		 /*Step 1: Add new space*/
		 //Add new space
		 magMember.goToMySpacePage();
		 magMember.addNewSpace(spacename, spacedesc);
		 
		 /*Step 2: Add Picture on Head Layout content*/ 
		 //Open Documents in this space
		 waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		 click(magMember.ELEMENT_DOCUMENTS_TAB);
		 
		//Add new content icon
		 magMember.addItem2ActionBarOfSpace("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, spacename, "List", "List");
	     
	     //Add a new content: Picture on head layout
	     actBar.goToAddNewContent();
	     cTemplate.createNewIllustratedWebContent(node, "", "", "", "", "", "","",true);
	     
	     /*Step 3: Check content activity after add Picture on Head Layout content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Illustrated Web Content", "", "", "", "", "");
	     
	     /*Clear data*/
	     info("clear data");
	     magMember.goToMySpacePage();
	     magMember.deleteSpace(spacename,300000);
	 }

	 /**
	 * == Add Content activity after add a Product content  in space ==
	 * Test case ID: 77323
	 * Step 1: Add new space
	 * Step 2: Ad Product content
	 * Step 3: Check content activity after add Product content
	 */
	 @Test
	 public void test13_AddContentActivityAfterAddAProductContentInSpace(){
		 //Declare variable
		 String node = "node13";
		 String spacename = "Space13";
		 String spacedesc = "Description Of Space13";
		 
		 /*Step 1: Add new space*/
		 //Add new space
		 magMember.goToMySpacePage();
		 magMember.addNewSpace(spacename, spacedesc);
		 
		 /*Step 2: Ad Product content*/ 
		 //Open Documents in this space
		 waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		 click(magMember.ELEMENT_DOCUMENTS_TAB);
		 
		//Add new content icon
		 magMember.addItem2ActionBarOfSpace("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, spacename, "List", "List");
	     
	     // Add a new content: Product
	     actBar.goToAddNewContent();
	     cTemplate.createFullNewProduct(node, "", node, node, node);
	     
	     /*Step 3: Check content activity after add Product content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Product", "", "", "", "", "");
	     
	     /*Clear data*/
	     info("clear data");
	     magMember.goToMySpacePage();
	     magMember.deleteSpace(spacename,300000);
	 }
	 
	 /**
	 * == Add Content activity after add a Web Link content  in space ==
	 * Test case ID: 77324
	 * Step 1: Add new space
	 * Step 2: Ad Web Link content
	 * Step 3: Check content activity after add Web Link content
	 */
	 @Test
	 public void test14_AddContentActivityAfterAddAWebLinkContentInSpace(){
		 //Declare variable
		 String node = "node14";
		 String spacename = "Space14";
		 String spacedesc = "Description Of Space14";
		 
		 /*Step 1: Add new space*/
		 //Add new space
		 magMember.goToMySpacePage();
		 magMember.addNewSpace(spacename, spacedesc);
		 
		 /*Step 2: Ad Web Link content*/ 
		 //Open Documents in this space
		 waitForAndGetElement(magMember.ELEMENT_DOCUMENTS_TAB);
		 click(magMember.ELEMENT_DOCUMENTS_TAB);
		 
		//Add new content icon
		 magMember.addItem2ActionBarOfSpace("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK, spacename, "List", "List");
	     
	     // Add a new content: Web Link
	     actBar.goToAddNewContent();
	     cTemplate.createNewWebLink(node,node);
	     
	     /*Step 3: Check content activity after add Web Link content*/
	     //Back to the Home page
	     info("-- Back to the Home page --");
	     navToolBar.goToHomePage();
	     
	     //- A new Content activity is added in the activity stream
	     info("-- Check activity after adding a content --");
	     activity.checkInforAfterAddingDocument(node, "", "Web Link", "", "", "", "", "");
	     
	     /*Clear data*/
	     info("clear data");
	     magMember.goToMySpacePage();
	     magMember.deleteSpace(spacename,300000);
	 }
}
