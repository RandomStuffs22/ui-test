package org.exoplatform.selenium.platform.plf.sniff;

import static org.exoplatform.selenium.TestLogger.info;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationManagement;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PageEditor;
import org.exoplatform.selenium.platform.PageManagement;
import org.exoplatform.selenium.platform.SettingSearchPage;
import org.exoplatform.selenium.platform.SearchAdministration;
import org.exoplatform.selenium.platform.calendar.Event;
import org.exoplatform.selenium.platform.calendar.Task;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.exoplatform.selenium.platform.forum.AnswerManageAnwser;
import org.exoplatform.selenium.platform.forum.AnswerManageQuestion;
import org.exoplatform.selenium.platform.forum.ForumManageCategory;
import org.exoplatform.selenium.platform.forum.ForumManageForum;
import org.exoplatform.selenium.platform.forum.ForumManagePost;
import org.exoplatform.selenium.platform.forum.ForumManageTopic;
import org.exoplatform.selenium.platform.social.ManageMember;
import org.exoplatform.selenium.platform.social.PeopleProfile;
import org.exoplatform.selenium.platform.social.SpaceManagement;
import org.exoplatform.selenium.platform.wiki.Template;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author phuongdt
 * @date 25/10/2013
 *
 */
public class PLF_UnifiedSearch extends Template {
	//General
	Button button;
	
	//Platform
	NavigationToolbar naviToolbar;
	ManageAccount magAcc;
	SearchAdministration searchAdmin;
	SettingSearchPage qsPage;
	PageEditor pEditor;
	PageManagement pageMag;
	ActionBar actBar;
	ContentTemplate conTemp;
	ManageMember magMember;
	ContextMenu cMenu;
	SitesExplorer siteExp;
	PeopleProfile peoPro;
	SpaceManagement spaceMag;
	Event evt;
	Task tsk;
	ForumManageForum mngFru;
	ForumManageTopic mngTopic;
	ForumManagePost mngPost;
	ForumManageCategory mngCat;
	AnswerManageAnwser ansMagAn;
	AnswerManageQuestion magQuest;
	NavigationManagement navMag;
	EcmsBase ecms;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login with " + DATA_USER_JOHN);
		magAcc = new ManageAccount(driver, this.plfVersion);
		naviToolbar = new NavigationToolbar(driver, this.plfVersion);
		searchAdmin = new SearchAdministration(driver);
		qsPage = new SettingSearchPage(driver);
		pEditor = new PageEditor(driver, this.plfVersion);
		pageMag = new PageManagement(driver, this.plfVersion);
		actBar = new ActionBar(driver, this.plfVersion);
		conTemp = new ContentTemplate(driver, this.plfVersion);
		magMember = new ManageMember(driver, this.plfVersion);
		cMenu = new ContextMenu(driver, this.plfVersion);
		siteExp = new SitesExplorer(driver, this.plfVersion);
		evt = new Event(driver, this.plfVersion);
		tsk = new Task(driver, this.plfVersion);
		button = new Button(driver, this.plfVersion);
		peoPro = new PeopleProfile(driver, this.plfVersion);
		spaceMag = new SpaceManagement(driver, this.plfVersion);
		mngFru = new ForumManageForum(driver, this.plfVersion);
		mngTopic = new ForumManageTopic(driver, this.plfVersion);
		mngPost = new ForumManagePost(driver, this.plfVersion);
		mngCat = new ForumManageCategory(driver, this.plfVersion);
		ansMagAn = new AnswerManageAnwser(driver, this.plfVersion);
		magQuest = new AnswerManageQuestion(driver, this.plfVersion);
		navMag = new NavigationManagement(driver, this.plfVersion);
		ecms = new EcmsBase(driver, this.plfVersion);

		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout portal");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	/**
	 * == Administrate the unified search engine ==
	 * Test case ID: 70921
	 * Step 1: Open Search Administration
	 * Step 2: Enable a content type
	 * Step 3: Disable a content type
	 */
	@Test(priority=2)
	public void test01_AdministrateTheUnifiedSearchEngine(){
		/*Declare variables*/
		String contentType = "Documents";
		/*Step 1: Open Search Administration*/
		//- Login and go to intranet home page
		//- Go to Search Administration via the Administration > Content > Search menu of the navigation bar 
		//The search admin has a table with 3 columns : Content Type, Description, and Actions, as attachment searchAdmin.png
		naviToolbar.goToSearch();

		/*Step 2: Enable a content type*/ 
		//Click on Enable button matching a content type
		//- The Enable button label is changed into Disable 
		//- The content type will reappear in the search results, and in the search apps settings.
		searchAdmin.enableContentTypeSearch(contentType);

		/*Step 3: Disable a content type*/
		//Click on Disable button matching a content type
		//- Disable button label is changed into Enable 
		//- This content type will no longer appear in the search results, nor in the search apps settings
		naviToolbar.goToSearch();
		searchAdmin.disableContentTypeSearch(contentType);
		
		//Restore data
		naviToolbar.goToSearch();
		searchAdmin.enableContentTypeSearch(contentType);
	}

	/**
	 * == Configure quick search ==
	 * Test case ID: 70831
	 * Step 1: Configure Quick search
	 */
	@Test(priority=3)
	public void test02_ConfigureQuickSearch(){
		/*Declare variables*/
		String qsGadget = "Quick Search";
		String pageName = "QuickSearch70831";

		/*Create data*/
		info("-- Create data --");
		//There is a page containing a Quick search portlet
		qsPage.addQuickSearchPage(pageName,qsGadget);

		/*Step 1: Configure Quick search*/
		//- Login as admin, go to intranet home page
		//- Open Quick search page
		//- Click Edit this page
		//- Click Edit portlet "Quick search"
		qsPage.goToEditSearchPortlet();
		//- Quick search settings screen is shown.

		//- Set value to fields
		//- Click Save settings,
		//- value is save
		qsPage.editQuickSearchSettingEditMode("10",true,true,true,true);	

		info("-- Clear data --");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName, true, "intranet", false, "Administration");
	}

	/**
	 * == Configure Search page ==
	 * Test case ID: 70919
	 * Step 1: Go to search page 
	 * Step 2: Configure search page
	 */
	@Test(priority=4)
	public void test03_ConfigureSearchPage(){
		/*Declare variables*/
		String searchText = "searchtext70919";

		/*Step 1: Go to search page */
		//- Login and go to intranet home page
		//-Go to search page
		//Seach page is shown
		qsPage.quickSearch(searchText);

		/*Step 2: Configure search page*/
		//- Click Edit this page
		//- Click Edit portlet " search"
		//- Quick search settings screen is shown.
		qsPage.goToEditSearchPortlet();

		//- Set value to fields
		//- Click Save settings, 
		//- value is save
		qsPage.editSearchSettingEditMode("10",true,true,true,true,true,true);	
		
		//Restore value
		qsPage.goToEditSearchPortlet();
		qsPage.editSearchSettingEditMode("10",false,false,false,true,true,true);
	}

	/**
	 * == Filter search ==
	 * Test case ID: 70834
	 * Step 1: Quick search
	 * Step 2: Filter search
	 */
	@Test(priority=0)
	public void test04_FilterSearch(){
		/*Declare variables*/
		String searchText = "qfind";
		String spaceName = "qfindS";
		String wikiName = "qfindW";
		String contentName = "qfindC";
		String[][] wikiPath = {{"Wiki Home/"+wikiName}};

		//Create data
		//Some contents such as wiki, events, tasks, people, pages...are existed, and contain text "cloud"
		//Add new webcontent
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(contentName, contentName, "", "", "", "");

		//Add wiki page
		info("Add new wiki page");		
		goToWiki();
		addBlankWikiPage(wikiName, wikiName, 0);

		//Add space
		info("-- Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceName);

		/*Step 1: Quick search*/
		//- Login and Open intranet home or ACME homepage
		//- Type some text: cloud in search box in top navigation
		qsPage.quickSearch(searchText);

		//- By default, quick search returns results for items located in the current site only, as attachment SearchResult.png
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "S"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "W"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "C"));

		/*Step 2: Filter search*/
		//On filter area, click on fields that you want to search
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_SPACE_CHECKBOX,2);
		//The page will search only selected fields for results
		waitForElementNotPresent(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "S"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "W"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "C"));

		/*clear data*/
		info("-- Clear data --");
		//Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", contentName));
		//Delete space
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
		//Delete wiki
		goToWiki();
		deleteWikiPage(wikiPath[0]);
	}

	/**
	 * == Quick Search ==
	 * Test case ID: 70771
	 * Step 1: Quick search
	 */
	@Test(priority=1)
	public void test05_QuickSearch(){
		/*Declare variables*/
		String searchText = "test70771";
		String spaceName = "test70771S";
		String wikiName = "test70771W";
		String contentName = "test70771C";
		String[][] wikiPath = {{"Wiki Home/"+wikiName}};

		//Create data
		//Some contents such as wiki, events, tasks, people, pages...are existed, and contain text "cloud"
		//Add new webcontent
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewWebContent(contentName, contentName, "", "", "", "");

		//Add wiki page
		info("Add new wiki page");		
		goToWiki();
		addBlankWikiPage(wikiName, wikiName, 0);

		//Add space
		info("-- Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName, spaceName);

		/*Step 1: Quick search*/
		//- Login and Open intranet home
		//- Type some text at Quick search box
		qsPage.quickSearch(searchText);

		//- By default, quick search returns results for items with All types located in the current site only, as attachment SearchResult.png
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "S"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "W"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "C"));

		/*clear data*/
		info("-- Clear data --");
		//Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", contentName));
		//Delete space
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName,300000);
		//Delete wiki
		goToWiki();
		deleteWikiPage(wikiPath[0]);

	}

	/**
	 * == Search answers ==
	 * Test case ID: 71619
	 * Step 1: Search Answers
	 */
	@Test(priority=5)
	public void test06_SearchAnswers(){
		/*Declare variables*/
		String searchText = "Search";
		String questionName = "SearchQ";
		String questionContent = "SearchQC";
		String questionName1 = "questionName1";
		String questionContent1 = "questionContent1";

		//Create data
		//Answers page is existed. And Some Questions and Answers are existed.
		ansMagAn.goToAnswer();
		magQuest.submitQuestion(null, questionName, questionContent, null, false, null);
		magQuest.submitQuestion(null, questionName1, questionContent1, null, false, null);

		/*Step 1: Search Answers*/
		//- Login and go to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_ANSWER_CHECKBOX,2);

		//- Search results should display: the answers icon, the question title, an excerpt of the matching content, the number of replies, the number of comments, the average rating of the question
		info("-- Verify the answers icon --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ANSWER_ICON);
		info("-- Verify the answers title --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_TITLE).getText().contains(questionName);
		info("-- Verify an excerpt --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_EXCERPT.replace("${keySearch}", searchText).replace("${item}", "Q")).getText().contains(questionContent);
		info("-- Verify the number of replies --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains("0 answers");
		info("-- Verify the number of comments --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains("0 comments");
		info("-- Verify the average rating of the question --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_FORUM_VOTE);

		//- Items in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Q")).click();

		/*Clear data*/
		info("-- Clear data --");
		ansMagAn.goToAnswer();
		magQuest.deleteQuestion(1, questionName);
		magQuest.deleteQuestion(1, questionName1);

	}

	/**
	 * == Search Discussions ==
	 * Test case ID: 71612
	 * Step 1: Search Discussions
	 */
	@Test(priority=6)
	public void test07_SearchDiscussions(){
		/*Declare variables*/
		String searchText = "Search";
		String category1 = "SearchCat";
		String forum1 = "SearchFo";
		String topic1 = "SearchTo";
		String category2 = "category2";
		String forum2 = "forum2";
		String topic2 = "topic2";

		//Create data
		//Forums, topics, posts are existed on Forum application.
		info("Add a post");
		mngFru.goToForums();
		mngTopic.addCategoryForumTopic(category1, forum1, topic1, topic1);
		mngFru.goToForumHome();
		mngTopic.addCategoryForumTopic(category2, forum2, topic2, topic2);

		/*Step 1: Search Discussions*/
		//- Login and go to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_DISCUSSION_CHECKBOX,2);

		//- Search results should display:  the topic icon such as displayed in the forum application, the post title, an excerpt, forum name, the post date, the rating, the number of replies in the topic
		info("-- Verify the topic icon --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_FORUM_ICON);
		info("-- Verify the post title --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_TITLE).getText().contains(topic1);
		info("-- Verify an excerpt --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_EXCERPT.replace("${keySearch}", searchText).replace("${item}", "To")).getText().contains(topic1);
		info("-- Verify the forum name --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains(forum1);
		info("-- Verify the post date --");
//		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains("AM");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains(getCurrentDate("EEEEE, MMMMM d, yyyy"));
		info("-- Verify the rating --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_FORUM_VOTE);
		info("-- Verify the the number of replies in the topic --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains("0 replies");

		//- Items in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "To")).click();

		/*Clear data*/
		info("-- Clear data --");
		mngFru.goToForums();
		click(By.linkText(category1));
		mngCat.deleteCategoryInForum(category1, true);
		click(By.linkText(category2));
		mngCat.deleteCategoryInForum(category2, true);
	}

	/**
	 * == Search documents ==
	 * Test case ID: 71611
	 * Step 1: Search documents
	 */
	@Test(priority=7)
	public void test08_SearchDocuments(){
		/*Declare variables*/
		String searchText = "Search71611Con";
		String contentName1 = "Search71611Content";
		String contentName2 = "contentName082";
		String contentName3 = "contentName083";

		//Create data
		//Some documents are existed on Site explorer
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewProduct(contentName1, contentName1);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		Utils.pause(3000);
		actBar.goToAddNewContent();
		conTemp.createNewProduct(contentName2, contentName2);
		ecms.goToNode(siteExp.ELEMENT_SIDEBAR_SITES_MANAGEMENT);
		Utils.pause(3000);
		actBar.goToAddNewContent();
		conTemp.createNewProduct(contentName3, contentName3);

		/*Step 1: Search documents*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_DOCUMENT_CHECKBOX,2);

		//- Search results should display: document title,  and document type icon such as displayed in the content explorer
		info("-- Verify document type icon --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ICON.replace("${keySearch}", searchText).replace("${item}", "tent"), DEFAULT_TIMEOUT,1,2);
		info("-- Verify document title --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "tent"));

		//-In published mode : For members of /platform/web-contributors, return search result with all status of document. Else,  just return search result with published document.
		//-In edit mode: return search result with all status of document
		//--> Note: not check in platform, this step will be checked in acme
		//- Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "tent")).click();

		info("-- Clear data --");
		//Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", contentName1));
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", contentName2));
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", contentName3));
	}

	/**
	 * == Search events ==
	 * Test case ID: 71614
	 * Step 1: Search events
	 */
	@Test(priority=8)
	public void test09_SearchEvents(){
		/*Declare variables*/
		String searchText = "Search71614";
		String eventName1 = "Search71614Event";
		String eventName2 = "event092";
		String eventName3 = "event093";
		String timeZone = "(GMT +07:00) Asia/Saigon";

		//Create data
		//Some events are existed on Calendar.
		evt.goToCalendarPage();
		evt.setTimezoneForCalendar(timeZone);
		evt.addQuickEvent(eventName1,eventName1,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true);
		evt.goToCalendarPage();
		evt.addQuickEvent(eventName2,eventName2,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true);
		evt.goToCalendarPage();
		evt.addQuickEvent(eventName3,eventName3,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true);
		String eventDate=getDate(1,"MMMM d, yyyy ")+"0:00 AM";
		/*Step 1: Search events*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_EVENT_CHECKBOX,2);

		//- Search results should display:  display event icon, event title, event description, start date and location
		info("-- Verify event icon --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_EVENT_ICON.replace("${keySearch}", searchText).replace("${item}", "Event"), DEFAULT_TIMEOUT,1,2);
		info("-- Verify event title --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Event"));
		info("-- Verify event description --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_EXCERPT.replace("${keySearch}", searchText).replace("${item}", "Event")).getText().contains(eventName1);
		info("-- Verify start date and location  --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Event"));
		String searchTaskDate = waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Event")).getText();
		String searchDate = searchTaskDate.substring(searchTaskDate.indexOf(',')+1).trim();
		assert searchDate.contains(eventDate);

		//- Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Event")).click();
		button.close();

		/*clear data*/
		info("-- Clear data --");
		evt.goToCalendarPage();
		evt.deleteEventTask(eventName1);
		evt.deleteEventTask(eventName2);
		evt.deleteEventTask(eventName3);
	}

	/**
	 * == Search files ==
	 * Test case ID: 71610
	 * Step 1: Search files (nt:file)
	 */
	@Test(priority=9)
	public void test10_SearchFiles(){
		/*Declare variables*/
		String searchText = "Search71610";
		String fileName1 = "Search71610File";
		String fileName2 = "fileName102";
		String fileName3 = "fileName103";
		String location = "Managed Sites";

		//Create data
		//Some files are existed
		//Some documents are existed on Site explorer
		info("Add new webcontent");
		naviToolbar.goToSiteExplorer();
		actBar.addItem2ActionBar("addDocument", actBar.ELEMENT_NEW_CONTENT_LINK);
		actBar.goToAddNewContent();
		conTemp.createNewFile(fileName1, fileName1, fileName1);
		click(naviToolbar.ELEMENT_SITE_EXPLORER_HOME);
		actBar.goToAddNewContent();
		conTemp.createNewFile(fileName2, fileName2, fileName2);
		click(naviToolbar.ELEMENT_SITE_EXPLORER_HOME);
		actBar.goToAddNewContent();
		conTemp.createNewFile(fileName3, fileName3, fileName3);

		/*Step 1: Search files*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_FILE_CHECKBOX,2);

		//- Search results of files should display: file title, the file location and the mimetype icon used in content explorer
		info("-- Verify he mimetype --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_FILE_ICON.replace("${keySearch}", searchText).replace("${item}", "File"), DEFAULT_TIMEOUT,1,2);
		info("-- Verify file title --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "File"));
		info("-- Verify file location --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "File")).getText().contains(location);

		//- Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "File")).click();
		driver.navigate().back();

		info("-- Clear data --");
		//Delete file
		naviToolbar.goToSiteExplorer();
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", fileName1));
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", fileName2));
		cMenu.deleteDocument(siteExp.ELEMENT_SE_NODE.replace("{$node}", fileName3));
	}

	/**
	 * == Search pages ==
	 * Test case ID: 71615
	 * Step 1: Search pages
	 */
	@Test(priority=15)
	public void test11_SearchPages(){
		/*Declare variables*/
		String searchText = "Search71615";
		String pageName1 = "Search71615Pa";
		String pageName2 = "page112";
		String pageName3 = "page113";
		String groupPath = "Platform /Content Management ";
		String membership = "*";
		String portalName = "intranet";
		String parentNode = "Home";
		String nodeName1 = "node716151";
		String nodeName2 = "node716152";
		String nodeName3 = "node716153";
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("English", "");
		String url = "8080/portal/"+portalName.toLowerCase()+"/"+parentNode.toLowerCase()+"/"+nodeName1.toLowerCase();

		//Create data
		//Some pages are existed.
		naviToolbar.goToManagePages();
		pageMag.addNewPageAtManagePages(PageType.PORTAL, pageName1, pageName1, true, null, groupPath, membership,  "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);
		pageMag.addNewPageAtManagePages(PageType.PORTAL, pageName2, pageName2, true, null, groupPath, membership,  "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);
		pageMag.addNewPageAtManagePages(PageType.PORTAL, pageName3, pageName3, true, null, groupPath, membership,  "Page Configs", ELEMENT_PAGE_LAYOUT_OPTION_EMPTY, true);
		
		info("Go to Administration/Portal Sites");
		naviToolbar.goToPortalSites();
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName1, true, languages, nodeName1, "", pageName1, true, true);
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName2, true, languages, nodeName2, "", pageName2, true, true);
		navMag.addNodeForPortal(portalName, parentNode, true, nodeName3, true, languages, nodeName3, "", pageName3, true, true);

		/*Step 1: Search pages*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_PAGE_CHECKBOX,2);

		//- Search results should display: page icon, page title, the excerpt, the site that the page belongs to, and the url
		info("-- Verify page icon --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ICON.replace("${keySearch}", searchText).replace("${item}", "Pa"), DEFAULT_TIMEOUT,1,2);
		info("-- Verify page title --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Pa"));
		info("-- Verify the excerpt --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_EXCERPT.replace("${keySearch}", searchText).replace("${item}", "Pa"), DEFAULT_TIMEOUT,1,2);
		info("-- Verify the site that the page belongs to, and the url --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Pa")).getText().contains(url);

		//-Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Pa")).click();

		/*clear data*/
		info("-- Clear data --");
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName1, true, portalName, false, parentNode,nodeName1);
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName2, true, portalName, false, parentNode,nodeName2);
		pageMag.deletePageAtManagePageAndPortalNavigation(pageName3, true, portalName, false, parentNode,nodeName3);
	}

	/**
	 * == Search people ==
	 * Test case ID: 71618
	 * Step 1: Search people
	 */
	@Test(priority=10)
	public void test12_SearchPeople(){
		/*Declare variables*/
		String searchText = "John Smith";
		String typeOfGender ="Female";
		String typeOfAddPhone="Work";
		String numberOfPhone="09880000";
		String email = "john.smith@acme.exoplatform.com";

		//Create data
		naviToolbar.goToMyProfile();
		peoPro.editUserContact(typeOfGender,true,typeOfAddPhone,numberOfPhone,false,"","",false,"");

		/*Step 1: Search people*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_PEOPLE_CHECKBOX,2);

		//- Search results should display: the user profile avatar, the user full name, the user profile title, the user profile email, the user profile phone, gender
		info("-- Verify profile avatar --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_PEOPLE_ICON);
		info("-- Verify the user full name --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_TITLE).getText().contains(searchText);
		info("-- the user profile email --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains(email);
		info("-- the user profile phone --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains(numberOfPhone);
		info("-- genders --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_CONTENT_DETAIL).getText().contains(typeOfGender);

		//- Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_TITLE).click();
		waitForAndGetElement(peoPro.ELEMENT_EDIT_INFORMATION_BUTTON);

		/*Clear data*/
		info("-- Clear data --");
		peoPro.removeUserContact(true, false, false);
	}

	/**
	 * == Search spaces ==
	 * Test case ID: 71617
	 * Step 1: Search spaces
	 */
	@Test(priority=12)
	public void test13_SearchSpaces(){
		/*Declare variables*/
		String searchText = "Search72617";
		String spaceName1 = "Search72617Space";
		String spaceName2 = "space132";

		//Create data
		//Some spaces are existed.
		info("-- Create space --");
		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName1, spaceName1);

		magMember.goToMySpacePage();
		magMember.addNewSpace(spaceName2, spaceName2);

		/*Step 1: Search spaces*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_SPACE_CHECKBOX,2);

		//- Search results should display: the space avatar, the space name, the space description, the members count, the validation status
		info("-- Verify space avatar --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ICON.replace("${keySearch}", searchText).replace("${item}", "Space"), DEFAULT_TIMEOUT,1,2);
		info("-- Verify space name --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Space"));
		info("-- Verify space description --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_EXCERPT.replace("${keySearch}", searchText).replace("${item}", "Space")).getText().contains(spaceName1);
		info("-- the members count, the validation status --");
		String memberCount = waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Space")).getText();
		assert (memberCount.contains("1 Member(s)"));
		assert (memberCount.contains("Register"));

		//- Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Space")).click();
		waitForAndGetElement(spaceMag.ELEMENT_SPACE_SETTING_MENU);

		/*clear data*/
		info("-- Clear data --");
		magMember.goToAllSpaces();
		magMember.deleteSpace(spaceName1,300000);
		magMember.deleteSpace(spaceName2,300000);
	}

	/**
	 * == Search tasks ==
	 * Test case ID: 71613
	 * Step 1: Search tasks
	 */
	@Test(priority=13)
	public void test14_SearchTasks(){
		/*Declare variables*/
		String searchText = "Search71613";
		String taskName1 = "Search71613Task";
		String taskName2 = "task092";
		String taskName3 = "task093";
		String timeZone = "(GMT +07:00) Asia/Saigon";

		//Create data
		//Tasks are existed on calendar
		evt.goToCalendarPage();
		evt.setTimezoneForCalendar(timeZone);
		tsk.addQuickTask(taskName1,taskName1,getDate(2,"MM/dd/yyyy"),getDate(2,"MM/dd/yyyy"),true);
		evt.goToCalendarPage();
		tsk.addQuickTask(taskName2,taskName2,getDate(2,"MM/dd/yyyy"),getDate(2,"MM/dd/yyyy"),true);
		evt.goToCalendarPage();
		tsk.addQuickTask(taskName3,taskName3,getDate(2,"MM/dd/yyyy"),getDate(2,"MM/dd/yyyy"),true);
		String taskDate=getDate(2,"MMMM d, yyyy ")+"11:59 PM";
		/*Step 1: Search tasks*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_TASK_CHECKBOX,2);

		//- Search results should display:  task icon, task name, task note, calendar name, due date 	
		info("-- Verify task icon --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_TASK_ICON.replace("${keySearch}", searchText).replace("${item}", "Task"), DEFAULT_TIMEOUT,1,2);
		info("-- Verify task name --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Task"));
		info("-- Verify task note --");
		assert waitForAndGetElement(qsPage.ELEMENT_RESULT_EXCERPT.replace("${keySearch}", searchText).replace("${item}", "Task")).getText().contains(taskName1);
		info("-- Verify due date  --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Task"));
		String searchTaskDate = waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Task")).getText();
		String searchDate = searchTaskDate.substring(searchTaskDate.indexOf(',')+1).trim();
		assert searchDate.contains(taskDate);

		//-Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Task")).click();
		button.close();

		/*clear data*/
		info("-- Clear data --");
		evt.goToCalendarPage();
		evt.deleteEventTask(taskName1);
		evt.deleteEventTask(taskName2);
		evt.deleteEventTask(taskName3);
	}

	/**
	 * == Search wikis ==
	 * Test case ID: 71616
	 * Step 1: Search wikis
	 */
	@Test(priority=14)
	public void test15_SearchWikis(){
		/*Declare variables*/
		String searchText = "Search72616";
		String wikiName1 = "Search72616Wiki";
		String wikiName2 = "Wiki152";
		String wikiName3 = "Wiki153";
		String wikiSpace = "Intranet";
		String[][] wikiPath = {{"Wiki Home/"+wikiName1},{"Wiki Home/"+wikiName2},{"Wiki Home/"+wikiName3}};

		//Create data
		//Add wiki page
		info("Add new wiki page");		
		goToWiki();
		waitForAndGetElement(ELEMENT_DISPLAY_MODE.replace("${space}", wikiSpace));
		addBlankWikiPage(wikiName1, wikiName1, 0);
		String feedTxt = waitForAndGetElement(ELEMENT_WIKI_PAGE_INFO_FEED).getText();
		String wikiDate = feedTxt.substring(feedTxt.indexOf("at")+3, feedTxt.indexOf('M')+1).trim();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy K:mm a");
		Date date = new Date();
		try {
			date = new SimpleDateFormat("MMMM d, yyyy hh:mm aaa",Locale.ENGLISH).parse(wikiDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wikiDate = formatter.format(date);
		info(wikiDate);
		click(ELEMENT_TITLE_WIKI_HOME_LINK);
		addBlankWikiPage(wikiName2, wikiName2, 0);
		click(ELEMENT_TITLE_WIKI_HOME_LINK);
		addBlankWikiPage(wikiName3, wikiName3, 0);

		/*Step 1: Search tasks*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);
		uncheck(qsPage.ELEMENT_FILTER_SEARCH_ALL_CONTENTTYE_CHECKBOX,2);
		check(qsPage.ELEMENT_FILTER_SEARCH_WIKI_CHECKBOX,2);

		//- Search results should display: the generic wiki icon, the wiki article title, the excerpt, the last modification date and the location (wiki name)
		info("-- Verify Wiki icon --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ICON.replace("${keySearch}", searchText).replace("${item}", "Wiki"));
		info("-- Verify wiki article title --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Wiki"));
		info("-- Verify the excerpt --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_EXCERPT.replace("${keySearch}", searchText).replace("${item}", "Wiki"));
		info("-- Verify location date time --");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Wiki"));
		String searchWikiLocationDate = waitForAndGetElement(qsPage.ELEMENT_RESULT_LOCATION_DATETIME.replace("${keySearch}", searchText).replace("${item}", "Wiki")).getText();
		String searchWikiDate = searchWikiLocationDate.substring(searchWikiLocationDate.indexOf(',')+1).trim();
		info(searchWikiLocationDate);
		info(searchWikiDate);
		assert searchWikiDate.contains(wikiDate);
		assert searchWikiLocationDate.contains(wikiSpace.toLowerCase());

		//- Item in search result is clickable and open it when user click
		waitForAndGetElement(qsPage.ELEMENT_RESULT_ITEM.replace("${keySearch}", searchText).replace("${item}", "Wiki")).click();
		waitForAndGetElement(ELEMENT_TITLE_WIKI_HOME_LINK);

		/*clear data*/
		info("-- Clear data --");
		deleteWikiPage(wikiPath[0]);
		deleteWikiPage(wikiPath[1]);
		deleteWikiPage(wikiPath[2]);
	}

	/**
	 * == Sort search result ==
	 * Test case ID: 71633
	 * Step 1: Quick search
	 * Step 2: Sort search results
	 */
	@Test(priority=11)
	public void test16_SortSearchResult(){
		/*Declare variables*/
		String searchText = "searchtext71633";
		String eventName1 = "searchtext71633A";
		String taskName1 = "searchtext71633B";
		String questionName = "searchtext71633C";

		//Create data
		//Some contents (such as wiki pages, events, tasks...) are existed.
		evt.goToCalendarPage();
		evt.addQuickEvent(eventName1,eventName1,getDate(1,"MM/dd/yyyy"),getDate(1,"MM/dd/yyyy"),true);
		ansMagAn.goToAnswer();
		magQuest.submitQuestion(null, questionName, questionName, null, false, null);
		evt.goToCalendarPage();
		tsk.addQuickTask(taskName1,taskName1,getDate(2,"MM/dd/yyyy"),getDate(2,"MM/dd/yyyy"),true);

		/*Step 1: Search tasks*/
		//- Login and connect to intranet home page
		//- Type some text into search box, Click on Search
		qsPage.quickSearch(searchText);
		qsPage.searchInSearchPage(searchText);

		//-- By default, quick search returns results for items located in the current site only, as attachment SearchResult.png
		/*Step 2: Sort search results*/
		//- Click on Sort by combo, select one of sets "Relevancy", "Date", "title"
		//Sort by Relevancy
		qsPage.sortByItem("Relevance");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "1").replace("${title}", "task"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "2").replace("${title}", "event"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "3").replace("${title}", "answer"));

		//Sort by Date
		qsPage.sortByItem("Date");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "1").replace("${title}", "answer"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "2").replace("${title}", "event"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "3").replace("${title}", "task"));
		
		//Search result will sorted by Relevancy, or date, or title respectively
		//Sort by title
		qsPage.sortByItem("Title");
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "1").replace("${title}", "event"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "2").replace("${title}", "task"));
		waitForAndGetElement(qsPage.ELEMENT_RESULT_INDEX.replace("${index}", "3").replace("${title}", "answer"));
		
		/*clear data*/
		info("-- Clear data --");
		evt.goToCalendarPage();
		evt.deleteEventTask(taskName1);
		evt.deleteEventTask(eventName1);
		ansMagAn.goToAnswer();
		magQuest.deleteQuestion(1, questionName);
	}
}
