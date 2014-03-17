package org.exoplatform.selenium.platform.ecms.functional.admin.repository;

import static org.exoplatform.selenium.TestLogger.info;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.exoplatform.selenium.Button;
import org.exoplatform.selenium.ManageAlert;
import org.exoplatform.selenium.Utils;
import org.exoplatform.selenium.platform.ManageAccount;
import org.exoplatform.selenium.platform.PlatformBase;
import org.exoplatform.selenium.platform.ecms.EcmsBase;
import org.exoplatform.selenium.platform.ecms.admin.ECMainFunction;
import org.exoplatform.selenium.platform.ecms.admin.ManageNodeType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by The eXo Platform SAS
 * Author : Hoang Manh Dung
 * dunghm@exoplatform.com
 * Oct 11, 2012
 */
public class ECMS_Admin_ManageNodeType_Search extends PlatformBase {

	//General
	Button button;
	ManageAccount magAcc;

	//Ecms
	EcmsBase ecms;
	ECMainFunction ecMain;
	ManageNodeType magNode;

	String DATA_USER = "john";
	//String DATA_PASS = DATA_PASS;

	@BeforeMethod
	public void beforeMethods() {
		initSeleniumTest();
		driver.get(baseUrl);
		info("Login ECMS with " + DATA_USER);
		button = new Button(driver);
		magAcc = new ManageAccount(driver);
		ecms = new EcmsBase(driver);
		ecMain = new ECMainFunction(driver);
		magNode = new ManageNodeType(driver);
		magAcc.signIn(DATA_USER, DATA_PASS);
		alert = new ManageAlert(driver, this.plfVersion);
		ecMain.goToNodeTypeTab();
	}

	@AfterMethod
	public void afterMethods() {
		info("Logout ECMS");
		//logoutEcms();
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	@Test
	public void test01_SearchNodeTypeNotInputKeyword(){
		info("Search Node Type when don't input keyword");
		magNode.doNodeTypeSearch("");	
		waitForTextPresent(magNode.MESSAGE_FOR_NO_INPUT_KEYWORD);
		alert.verifyAlertMessage(magNode.MESSAGE_FOR_NO_INPUT_KEYWORD);
	}

	@Test
	public void test02_SearchNodeTypeWithSpecialChars(){
		info("Search node type with a set of special characters");
		magNode.doNodeTypeSearch("!@#$%^&()_{}[]|\"/?><,~`");
		alert.verifyAlertMessage(magNode.MESSAGE_FOR_SPECIAL_KEYWORD);
	}

	@Test
	public void test03_SearchNodeTypeNotMatch(){
		String keyword = RandomStringUtils.randomAlphabetic(20);
		info("Search Node Type when no Node type match with keyword");
		magNode.doNodeTypeSearch(keyword);
		waitForAndGetElement(magNode.MESSAGE_FOR_NOT_MATCH_KEYWORD);
	}

	@Test
	public void test04_SearchNodeTypeMatch(){
		String keyword = "application";
		info("Search Node type when there are some corresponding node types with keyword");
		magNode.doNodeTypeSearch(keyword);
		Utils.pause(500);
		List<WebElement> result = driver.findElements(By.xpath("//table[contains(@class, 'uiGrid')]/tbody//tr"));
		for(WebElement tr : result) {
			if(tr.findElement(By.xpath("//div[@class='text' and contains(text(),'" + keyword + "')]")) == null) {
				assert false: ("This row don't contain keyword");
			}
		}
	}

	@Test
	public void test05_SearchNodeTypeAll(){
		String keyword = "*";
		info("Search all node types");
		magNode.doNodeTypeSearch(keyword);
		Utils.captureScreen("test05_SearchNodeTypeAll");
	}
}