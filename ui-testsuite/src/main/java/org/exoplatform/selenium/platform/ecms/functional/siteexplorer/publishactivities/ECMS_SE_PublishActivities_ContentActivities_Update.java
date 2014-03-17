package org.exoplatform.selenium.platform.ecms.functional.siteexplorer.publishactivities;

import static org.testng.AssertJUnit.assertEquals;
import static org.exoplatform.selenium.TestLogger.info;

import org.exoplatform.selenium.platform.HomePageActivity;
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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author: PhuongDT
 * @date: 18/09/2013
 */
public class ECMS_SE_PublishActivities_ContentActivities_Update extends PlatformBase{
	//Platform
	ManageAccount magAcc;
	ActionBar actBar;
	NavigationToolbar navToolBar;
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
		info("Login ECMS with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver);
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		siteExp = new SitesExplorer(driver);
		navToolBar = new NavigationToolbar(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		activity = new HomePageActivity(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Update Content activity after edit title of content ==
	 * Test case ID: 76962
	 * Step 1: Add new content
	 * Step 2: Edit title of content
	 * Step 3: Check content activity after edit title of content
	 */
	@Test
	public void test01_UpdateContentActivityAfterEditTitleOfContent(){
		//Declare variable
		String node = "node01";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String newnode = "newnode1";
		By bNewNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", newnode));

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Edit title of content*/
		//Edit the title of the shared content
		cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_RENAME_NODE, newnode);

		/*Step 3: Check content activity after edit title of content*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content activity is updated in the activity stream with the new title
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(newnode, "", "Web Content", "", "", "", "", "");

		//A comment is added: Title has been updated to: $value.
		activity.checkTitleAfterEditing(newnode,newnode);
		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNewNode);
	}

	/**
	 * == Update Content activity after edit summary of content ==
	 * Test case ID: 76963
	 * Step 1: Add new content
	 * Step 2: Edit summary of content
	 * Step 3: Check content activity after edit summary
	 */
	@Test
	public void test02_UpdateContentActivityAfterEditSummaryOfContent(){
		//Declare variable
		String node = "node02";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String summary = "Summary of web content";
		String comment = "Summary has been updated to:";
		String sumcomment = comment+"\n"+summary;

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Edit summary of content*/
		//Edit the summary of the shared content
		cTemplate.editWebContent(node, "", "", summary, "", "");

		/*Step 3: Check content activity after edit summary*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content activity is updated in the activity stream with the new summary
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", summary, "", "", "");

		//A comment is added: Summary has been updated to: $value.
		info("-- Verify comment --");
		assertEquals(sumcomment,driver.findElement(By.xpath(activity.ELEMENT_ACTIVITY_COMMENT_CONTENT_2.replace("${title}", node).replace("${comment}", comment))).getText());

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after edit content by user creator ==
	 * Test case ID: 76964
	 * Step 1: Add new content
	 * Step 2: Edit content
	 * Step 3: Check content activity after edit content
	 */
	@Test
	public void test03_UpdateContentActivityAfterEditContentByUserCreator(){
		//Declare variable
		String node = "node03";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String content = "Content of web content";
		String comment = "Content has been updated.";

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Edit content*/
		//Edit the summary of the shared content
		cTemplate.editWebContent(node, content, "", "", "", "");

		/*Step 3: Check content activity after edit content*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
		waitForTextNotPresent(content);

		//A comment is added: Content has been updated.
		info("-- Verify comment --");
		assertEquals(comment,driver.findElement(By.xpath(activity.ELEMENT_ACTIVITY_COMMENT_CONTENT_2.replace("${title}", node).replace("${comment}", comment))).getText());

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after attach files to a content ==
	 * Test case ID: 76965
	 * Step 1 : Add content
	 * Step 2: Attach file to content
	 * Step 3: Check activity content after attach file
	 */
	@Test
	public void test04_UpdateContentActivityAfterAttachFilesToAContent(){
		//Declare variable
		String node = "node04";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String file = "ECMS_DMS_SE_Upload_docfile.doc";

		/*Step 1 : Add content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Attach file to content*/
		// Attach file to the content
		ecms.uploadFile("TestData/"+file);

		/*Step 3: Check activity content after attach file*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
		waitForTextNotPresent(file);

		//A comment is added: File(s) has been attached
		info("-- Verify comment --");
		activity.checkAttachFileAfterAddingToContent(node);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after remove attached document to a content ==
	 * Test case ID: 76966
	 * Step 1 : Add content
	 * Step 2: Attach file to content
	 * Step 3: Remove attached file
	 * Step 4: Check content activity after remove attached file
	 * Note: need update test case in qmetry.  There is comment after deleting file: File(s) has been removed.
	 * Refer CONTENT_06: http://int.exoplatform.org/portal/intranet/wiki/group/spaces/platform_40/Activity_Types#HContentActivities
	 */
	@Test
	public void test05_UpdateContentActivityAfterRemoveAttachedDocumentToAContent(){
		//Declare variable
		String node = "node05";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String file = "ECMS_DMS_SE_Upload_docfile.doc";

		/*Step 1 : Add content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Attach file to content*/
		// Attach file to the content
		ecms.uploadFile("TestData/"+file);

		/*Step 3: Remove attached file*/
		//- Select file which uploaded on step 2
		//- Right click on it and click [Delete]
		cMenu.deleteData(By.linkText(file));

		/*Step 4: Check content activity after remove attached file*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
		waitForTextNotPresent(file);

		// Check comment is added to activity stream
		info("-- Verify comment --");
		activity.checkAttachFileAfterRemovingToContent(node);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after add a category to a content ==
	 * Test case ID: 76967
	 * Step 1 : Add content
	 * Step 2: Add category to the content
	 * Step 3: Check content activity after add category to content
	 * Step 4: Add 2 categories to content
	 */
	@Test
	public void test06_UpdateContentActivityAfterAddACategoryToAContent(){
		//Declare variable
		String node = "node06";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String categoryPath = "Defense";
		String categoryTree = "powers";
		String categoryNode = "Healing";
		String categoryNode1 = "Vision";

		/*Step 1 : Add content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Add category to the content*/
		//- Select [More] choose [Categories]
		//- Add a category to the content
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		//Add category for node
		cTemplate.goToNode(bNode);
		actBar.addCategoryForNode(categoryTree, false, categoryPath, categoryNode);

		/*Step 3: Check content activity after add category to content*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
		waitForElementNotPresent(activity.ELEMENT_CONTENT.replace("@{fileName}", node).replace("${text}", categoryNode));

		//A comment is added: Category: $value has been added.
		info("-- Verify comment --");
		activity.checkCategoryAfterAddingToContent(node, categoryNode);

		/*Step 4: Add 2 categories to content*/
		navToolBar.goToSiteExplorer();
		cTemplate.goToNode(bNode);
		actBar.addCategoryForNode(categoryTree, false, categoryPath, categoryNode1);

		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
		waitForElementNotPresent(activity.ELEMENT_CONTENT.replace("@{fileName}", node).replace("${text}", categoryNode1));

		//A comment is added: Category: $value has been added.
		info("-- Verify comment --");
		activity.checkCategoryAfterAddingToContent(node, categoryNode1);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after remove a category to a content ==
	 * Test case ID: 76968
	 * Step 1 : Add content
	 * Step 2: Add category to the content
	 * Step 3: Remove a category in content
	 * Step 4: Check content activity after remove a category
	 */
	@Test
	public void test07_UpdateContentActivityAfterRemoveACategoryToAContent(){
		//Declare variable
		String node = "node07";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String categoryPath = "Defense";
		String categoryTree = "powers";
		String categoryNode = "Healing";

		/*Step 1 : Add content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Add category to the content*/
		//- Select [More] choose [Categories]
		//- Add a category to the content
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		//Add category for node
		cTemplate.goToNode(bNode);
		actBar.addCategoryForNode(categoryTree, false, categoryPath, categoryNode);

		/*Step 3: Remove a category in content*/
		actBar.deleteCategory(categoryTree + "/"+ categoryPath + "/" + categoryNode);

		/*Step 4: Check content activity after remove a category*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");
		waitForElementNotPresent(activity.ELEMENT_CONTENT.replace("@{fileName}", node).replace("${text}", categoryNode));

		//A comment is added: Category: $value has been removed.
		info("-- Verify comment --");
		activity.checkCategoryAfterRemovingToContent(node, categoryNode);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after add tag to a content ==
	 * Test case ID: 76969
	 * Step 1: Add tag to content
	 * Step 2: Check content activity after add tag
	 * Step 3: Add more tag
	 * Step 4: Check content activity when add more tag
	 */
	@Test
	public void test08_UpdateContentActivityAfterAddTagToAContent(){
		//Declare variable
		String node = "node08";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String tag1[] = {"Activity_Tag1"};
		String tag2[] = {"Activity_Tag2", "Activity_Tag3"};
		String tag[] = {tag1[0], tag2[0], tag2[1]};

		/*Step 1: Add tag to content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		//- Select [More] choose [Categories]
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		//select [Tag] 
		//- Input name for tag
		//- Click [Add]
		cTemplate.goToNode(bNode);
		siteExp.addTagForNode(tag1);

		/*Step 2: Check content activity after add tag*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//A comment is added: Tag: $value has been added.
		info("-- Verify comment --");
		activity.checkTagAfterAddingToContent(node, tag1[0], 1);


		/*Step 3: Add more tag*/
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cTemplate.goToNode(bNode);
		siteExp.addTagForNode(tag2);

		/*Step 4: Check content activity when add more tag*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//Two comments are added two the activity: Tag: $value has been added.
		info("-- Verify comment --");
		activity.checkTagAfterAddingToContent(node, tag2[0], 1);
		activity.checkTagAfterAddingToContent(node, tag2[1], 1);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		siteExp.goToEditTag();
		siteExp.deleteTag(tag);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after remove tag to a content ==
	 * Test case ID: 76970
	 * Step 1: Step 1: Add new content
	 * Step 2: Add a tag for content
	 * Step 3: Add more tags for content
	 * Step 4: Remove a tag 
	 * Step 5: Check content activity after remove a tags
	 * Step 6: Remove more tags
	 * Step 7: Check content activity after remove more tags
	 * Pending: Test fail. Wait for comfirming test case again (don't have new comment after removing tag
	 * Refer: https://jira.exoplatform.org/browse/ECMS-5663
	 */
	@Test (groups={"pending"})
	public void test09_UpdateContentActivityAfterRemoveTagToAContent(){
		//Declare variable
		String node = "node09";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String tag1[] = {"Activity_Tag1"};
		String tag2[] = {"Activity_Tag2", "Activity_Tag3"};
		String tag[] = {tag1[0], tag2[0], tag2[1]};

		/*Step 1: Add tag to content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Add a tag for content*/
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("addCategory", actBar.ELEMENT_CATEGORIES_LINK);
		//select [Tag] 
		//- Input name for tag
		//- Click [Add]
		cTemplate.goToNode(bNode);
		siteExp.addTagForNode(tag1);

		/*Step 3: Add more tags for content*/
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		siteExp.addTagForNode(tag2);

		/*Step 4: Remove a tag*/
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		siteExp.goToEditTag();
		siteExp.deleteTag(tag1);

		/*Step 5: Check content activity after remove a tags*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//A comment is added: Tag: $value has been removed.
		info("-- Verify comment --");
		activity.checkTagAfterRemovingToContent(node, tag1[0], 1);

		/*Step 6: Remove more tag*/
		navToolBar.goToSiteExplorer();
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		siteExp.goToEditTag();
		siteExp.deleteTag(tag2);

		/*Step 7: Check content activity after remove a tags*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//Two new comments are added to the activity: Tag: $value has been removed.
		info("-- Verify comment --");
		activity.checkTagAfterRemovingToContent(node, tag2[0], 1);
		activity.checkTagAfterRemovingToContent(node, tag2[1], 1);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		siteExp.goToEditTag();
		siteExp.deleteTag(tag);
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		waitForAndGetElement(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after add a comment a content ==
	 * Test case ID: 76972
	 * Step 1: Add new content
	 * Step 2: Add comment for content
	 * Step 3: Check content activity after comment a content
	 */
	@Test
	public void test10_UpdateContentActivityAfterAddACommentAContent(){
		//Declare variable
		String node = "node10";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String comment = "Comment of content";

		/*Step 1 : Add content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Add comment for content*/
		//Select comment on action bar
		//Add a comment  to the content
		//Check if comment button is shown on action bar
		actBar.addItem2ActionBar("comment", actBar.ELEMENT_ADD_COMMENT_LINK);
		cTemplate.goToNode(bNode);
		actBar.addComment(comment);

		/*Step 3: Check content activity after add category to content*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//The comment is added to the activity
		info("-- Verify comment --");
		activity.checkCommentOfContentAfterAddingToContent(node, comment);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after edit a comment of a content ==
	 * Test case ID: 76973
	 * Step 1: Add new content
	 * Step 2: Add comment for content
	 * Step 3: Edit comment
	 * Step 4: Check content activity after edit comment
	 */
	@Test
	public void test11_UpdateContentActivityAfterEditACommentOfAContent(){
		//Declare variable
		String node = "node11";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String oldComment = "Comment to file document";
		String newComment = "New comment to file document";

		/*Step 1 : Add content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Add comment for content*/
		//Select comment on action bar
		//Add a comment  to the content
		//Check if comment button is shown on action bar
		actBar.addItem2ActionBar("comment", actBar.ELEMENT_ADD_COMMENT_LINK);
		cTemplate.goToNode(bNode);
		actBar.addComment(oldComment);

		/*Step 3: Edit comment*/
		//- Click [Show comment]
		//- Edit the comment of the content
		cTemplate.goToNode(bNode);
		actBar.editComment(oldComment, newComment);

		/*Step 4: Check content activity after edit comment*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//The comment of the activity is updated
		info("-- Verify comment --");
		activity.checkCommentOfContentAfterAddingToContent(node, newComment);
		activity.checkCommentOfContentAfterRemovingToContent(node, oldComment);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after remove a comment of a content ==
	 * Test case ID: 76974
	 * Step 1: Add new content
	 * Step 2: Add comment for content
	 * Step 3: Remove content
	 * Step 4: Check content activity after edit comment
	 */
	@Test
	public void test12_UpdateContentActivityAfterRemoveACommentOfAContent(){
		//Declare variable
		String node = "node12";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String comment = "Comment to file document";

		/*Step 1 : Add content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Add comment for content*/
		//Select comment on action bar
		//Add a comment  to the content
		//Check if comment button is shown on action bar
		actBar.addItem2ActionBar("comment", actBar.ELEMENT_ADD_COMMENT_LINK);
		cTemplate.goToNode(bNode);
		actBar.addComment(comment);

		/*Step 3: Remove content*/
		//- Click [Show comment] and click [Delete]
		cTemplate.goToNode(bNode);
		actBar.deleteComment(comment);

		/*Step 4: Check content activity after edit comment*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content of the content activity isn't updated in the activity stream 
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//The comment of the activity is updated
		info("-- Verify comment --");
		activity.checkCommentOfContentAfterRemovingToContent(node, comment);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after edit publication status to Pending of content ==
	 * Test case ID: 76977
	 * Step 1: Add new content
	 * Step 2: Change status of content to "pending"
	 * Step 3: Check content activity after change status
	 */
	@Test
	public void test13_UpdateContentActivityAfterEditPublicationStatusToPendingOfContent(){
		//Declare variable
		String node = "node13";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String comment = "Publication status is now pending.";

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Change status of content to "pending"*/
		//- Click [More] choose [Publications]
		//- Update the status of the shared content to "Pending"
		//Check if publication button is shown on action bar
		actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION_ICON, "Admin", "Admin");
		cTemplate.goToNode(bNode);
		actBar.changeStatusPublication("Pending");

		/*Step 3: Check content activity after change status*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content activity is updated in the activity stream with the new summary
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "Pending");

		//A comment is added: Publication status is now pending.
		info("-- Verify comment --");
		activity.checkCommentOfContentAfterAddingToContent(node, comment);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after edit publication status to Approved of content ==
	 * Test case ID: 76978
	 * Step 1: Add new content
	 * Step 2: Approved content
	 * Step 3: Check content activity after change status
	 */
	@Test
	public void test14_UpdateContentActivityAfterEditPublicationStatusToApprovedOfContent(){
		//Declare variable
		String node = "node14";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String comment = "Document has been approved.";

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Approved content*/
		//- Click [More] choose [Publications]
		//- Update the status of the shared content to Approved
		//Check if publication button is shown on action bar
		actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION_ICON, "Admin", "Admin");
		cTemplate.goToNode(bNode);
		actBar.changeStatusPublication("Approved");

		/*Step 3: Check content activity after change status*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content activity is updated in the activity stream with the new summary
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "Approved");

		//A comment is added: Publication has been approved.
		info("-- Verify comment --");
		activity.checkCommentOfContentAfterAddingToContent(node, comment);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after edit publication status to Staged of content ==
	 * Test case ID: 76979
	 * Step 1: Add new content
	 * Step 2: Staged content
	 * Step 3: Check content activity after change status
	 */
	@Test
	public void test15_UpdateContentActivityAfterEditPublicationStatusToStagedOfContent(){
		//Declare variable
		String node = "node15";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String comment = "Publication has been staged.";

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Staged content*/
		//- Click [More] choose [Publications]
		//- Update the status of the shared content to Staged
		//Check if publication button is shown on action bar
		actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION_ICON, "Admin", "Admin");
		cTemplate.goToNode(bNode);
		actBar.changeStatusPublication("Staged");

		/*Step 3: Check content activity after change status*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content activity is updated in the activity stream with the new summary
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "Staged");

		//A comment is added: Publication has been staged.
		info("-- Verify comment --");
		activity.checkCommentOfContentAfterAddingToContent(node, comment);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after edit publication status to Published of content ==
	 * Test case ID: 76980
	 * Step 1: Add new content
	 * Step 2: Change status of content to "Published"
	 * Step 3: Check content activity after change status
	 */
	@Test
	public void test16_UpdateContentActivityAfterEditPublicationStatusToPublishedOfContent(){
		//Declare variable
		String node = "node16";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String comment = "Document has been published.";

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Change status of content to "Published"*/
		//- Click [More] choose [Publications]
		//- Update the status of the shared content to Published
		//Check if publication button is shown on action bar
		actBar.addItem2ActionBar("managePublications", actBar.ELEMENT_PUBLICATION_ICON, "Admin", "Admin");
		cTemplate.goToNode(bNode);
		actBar.changeStatusPublication("Published");

		/*Step 3: Check content activity after change status*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content activity is updated in the activity stream with the new summary
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "Published");

		//A comment is added: Publication has been published
		info("-- Verify comment --");
		activity.checkCommentOfContentAfterAddingToContent(node, comment);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after restore a revision of content ==
	 * Test case ID: 76981
	 * Step 1: Add new content
	 * Step 2: Check in content
	 * Step 3: Checkout content
	 * Step 4: Edit the content
	 * Step 5: Check in again
	 * Step 6: Checkout again
	 * Step 7: Restore version
	 * Step 8: Check content activity after restore a revision
	 */
	@Test
	public void test17_UpdateContentActivityAfterRestoreARevisionOfContent(){
		//Declare variable
		String node = "node17";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		String version = "1";
		String summary = "Summary of web content";

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Check in content*/
		//- Select content above and right click on it
		//- Choose [Check In]
		cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);

		/*Step 3: Checkout content*/
		//- Select content above and right click on it
		//- Choose [Check out]
		cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);

		/*Step 4: Edit the content*/
		//- Choose [More] and click [Edit]
		//- Change something on the contend
		cTemplate.goToNode(bNode);
		cTemplate.editWebContent(node, "", "", summary, "", "");

		/*Step 5: Check in again*/
		//- Select content above and right click on it
		//- Choose [Check In]
		cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);

		/*Step 6: Checkout again*/
		//- Select content above and right click on it
		//- Choose [Check out]
		cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);

		/*Step 7: Restore version*/
		//Check if Category button is shown on action bar
		actBar.addItem2ActionBar("manageVersions", actBar.ELEMENT_VERSIONS_LINK);
		//- Click [More] and choose [Versions]
		//- Select version which want to restore and click [Restore Version] icon
		//- Click [Close] button
		actBar.restoreVersion(bNode, version);

		/*Step 8: Check content activity after restore a revision*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//The content activity is updated in the activity stream with the new summary
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", version, "", "");

		//A comment is added: Publication has been restored to version: $value where $value is the version number.
		info("-- Verify comment --");
		activity.checkRestoreVersionAfterRestoringToContent(node, version);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Not Display Content activity after check in a content ==
	 * Test case ID: 76983
	 * Step 1: Check in content
	 * Step 2: Check content activity 
	 * Pending: Test fail. Wait for confirming test case again (step 2)
	 * Refer https://jira.exoplatform.org/browse/ECMS-5662
	 */
	@Test (groups={"pending"})
	public void test18_NotDisplayContentActivityAfterCheckInAContent(){
		//Declare variable
		String node = "node18";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		Integer version = 0;

		/*Step 1: Check in content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//- The content of the content activity isn't updated in the activity stream
		//The version number displayed is updated +1
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", String.valueOf(version), "", "");

		//Open Sites Explorer
		navToolBar.goToSiteExplorer();

		//- Select content above and right click on it
		//- Choose [Check In]
		cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKIN);

		/*Step 2: Check content activity */
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//- The content of the content activity isn't updated in the activity stream
		//The version number displayed is updated +1
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", String.valueOf(version+1), "", "");

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.contextMenuAction(bNode, cMenu.ELEMENT_MENU_CHECKOUT);
		cMenu.deleteDocument(bNode);
	}

	/**
	 * == Update Content activity after move a content ==
	 * Test case ID: 76985
	 * Step 1: Add new content
	 * Step 2: Move content to new place
	 * Step 3: Check content activity after move content
	 */
	@Test
	public void test19_UpdateContentActivityAfterMoveAContent(){
		//Declare variable
		String node = "node19";
		String folder = "folder19";
		By bNode = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", node));
		By bFolder = By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}", folder));

		/*Step 1: Add new content*/ 
		//Open Sites Explorer
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();

		//Create new folder
		cTemplate.createNewFolder(folder, folderType.Content);

		//Add a new content
		actBar.goToAddNewContent();
		cTemplate.createNewWebContent(node,node,"", "", "", "");

		/*Step 2: Move content to new place*/
		//- Right click on this content
		//- Choose [Cut] and select new place 
		//- Right click on it and select [Paste]
		cMenu.cutAndPasteNode(bNode, bFolder);
		waitForElementNotPresent(bNode);
		ecms.goToNode(bFolder);
		waitForAndGetElement(bNode);

		/*Step 3: Check content activity after move content*/
		//Back to the Home page
		info("-- Back to the Home page --");
		navToolBar.goToHomePage();

		//- The content of the content activity isn't updated in the activity stream
		info("-- Check activity after adding a content --");
		activity.checkInforAfterAddingDocument(node, "", "Web Content", "", "", "", "", "");

		//A comment is added: Publication has been moved to: $value with $value = path of the content.
		info("-- Verify comment --");
		activity.checkContentAfterMovingANode(node, "/sites/" + folder + "/", true);

		/*Clear data*/
		info("clear data");
		info("-- Open Sites Explorer --");
		navToolBar.goToSiteExplorer();
		cMenu.deleteDocument(bFolder);
	}
}
