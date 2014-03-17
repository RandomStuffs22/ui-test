package org.exoplatform.selenium.platform.ecms.sniff.siteexplorer;

import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.NavigationToolbar;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ActionBar;
import org.exoplatform.selenium.platform.ecms.contentexplorer.AdvancedSearch;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContentTemplate;
import org.exoplatform.selenium.platform.ecms.contentexplorer.ContextMenu;
import org.exoplatform.selenium.platform.ecms.contentexplorer.SitesExplorer;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static org.exoplatform.selenium.TestLogger.info;

/**
 * @date 27/05/2013
 * @author thuntn
 *
 */

public class ECMS_SE_Search extends PlatformBase {
	NavigationToolbar navToolBar;
	ActionBar actBar;
	ContentTemplate cTemplate;
	EcmsBase ecms;
	ContextMenu cMenu;
	ManageAccount magAcc;
	SitesExplorer siteExp;
	Button btn;
	AdvancedSearch aSearch;
	
	
	/**CaseID 85825 Advanced search
	 * Step 1: Advanced search
	 * 
	 */
	@Test
	public void test01_AdvancedSearch() {
		String node1= "test01AdvancedSearch";
		String des = "test01 desc";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		
		info("Advanced search");
		//Create document
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1, des);
		
		//Advance search
		aSearch.goToAdvancedSearch();
		click(aSearch.ELEMENT_CONSTRAINT_FORM);
		aSearch.selectDocumentType("nt:file");
		aSearch.searchContent(node1);
		waitForAndGetElement(aSearch.ELEMENT_SEARCH_RESULT_TEXT.replace("${result}", node1));
		btn.closeWindow();
		
		//Delete data
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bNode1);
		
	}
	/**CaseID 65875 Simple Search
	 * Step 1: Simple Search
	 * 
	 */
	@Test
	public void test02_SimpleSearch() {
		String node1= "test02SimpleSearch";
		String des = "test02 desc";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		
		info("Simple search");
		//Create document
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1, des);
		
		//Simple search
		type(siteExp.ELEMENT_SIMPLESEARCH_TEXTBOX,node1,true);
		click(siteExp.ELEMENT_QUICKSEARCH_ICON);
		waitForAndGetElement(aSearch.ELEMENT_SEARCH_RESULT_TEXT.replace("${result}", node1));
		
		//Delete data
		cMenu.deleteDocument(bNode1);
		
	}
	
	/**CaseID 65862, 67863, 67862, 67861 Create, edit, execute, delete query in Advanced search
	 * 
	 */
	@Test
	public void test03_04_05_CreateQueryInAdvancedSearch() {
		String node1= "test03Create";
		String nameQuery = "Query test03";
		String query = "select * from nt:base where jcr:path like '/sites/%' order by exo:dateCreated DESC";
		String edit = "select * from nt:base where jcr:primaryType like 'nt:file' order by exo:dateCreated DESC";
		By bNode1= By.xpath(siteExp.ELEMENT_SE_NODE.replace("{$node}",node1));
		
		info("Create, edit, execute, delete query in Advanced search");
		actBar.goToAddNewContent();
		cTemplate.createNewFile(node1, node1, node1);
		
		//CaseID 65862 - Create query
		aSearch.goToNewQuery();
		aSearch.addQuery(nameQuery, "SQL", query);
		
		//CaseID 67863 - Edit query
		aSearch.goToEditQuery(nameQuery);
		aSearch.editQuery("", "SQL", edit);
		
		//CaseID 67862 execute query
		aSearch.executeQuery(nameQuery);
		waitForAndGetElement(aSearch.ELEMENT_SEARCH_RESULT_TEXT.replace("${result}", node1));
		btn.closeWindow();
		
		//CaseID 67861 - Delete query
		aSearch.deleteQuery(nameQuery);
		
		//Delete data
		click(siteExp.ELEMENT_SIDEBAR_FILE_EXPLORER);
		cMenu.deleteDocument(bNode1);
	}
	
	
	@BeforeMethod
	public void beforeMethod() {
		initSeleniumTest();
		driver.get(baseUrl);
		navToolBar = new NavigationToolbar(driver);
		magAcc = new ManageAccount(driver);
		magAcc.signIn(DATA_USER_JOHN, DATA_PASS);
		navToolBar.goToSiteExplorer();
		actBar = new ActionBar(driver);
		cTemplate = new ContentTemplate(driver);
		ecms = new EcmsBase(driver);
		cMenu = new ContextMenu(driver);
		siteExp = new SitesExplorer(driver);
		btn = new Button(driver);
		aSearch = new AdvancedSearch(driver);

	}
	@AfterMethod
	public void afterMethods() {
		driver.manage().deleteAllCookies();
		driver.quit();
	}
}
