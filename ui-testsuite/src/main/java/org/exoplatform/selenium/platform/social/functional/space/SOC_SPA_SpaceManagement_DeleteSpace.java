/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.selenium.platform.social.functional.space;

import static org.exoplatform.selenium.TestLogger.info;
import static org.exoplatform.selenium.platform.ManageAccount.signIn;
import static org.exoplatform.selenium.platform.social.SpaceManagement.*;
import static org.exoplatform.selenium.platform.NavigationToolbar.*;

import org.exoplatform.selenium.platform.social.SocialBase;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by The eXo Platform SAS
 * Author : Hoang Manh Dung
 *          dunghm@exoplatform.com
 * Nov 20, 2012  
 */
public class SOC_SPA_SpaceManagement_DeleteSpace extends SocialBase{
  public static final String DATA_USER_ADMIN = "john";
  public static final String DATA_PASS = "gtn";
  
  @BeforeMethod
  public void beforeMethod(){
    initSeleniumTest();
    driver.get(baseUrl);
    actions = new Actions(driver);
    driver.manage().window().maximize();
    signIn(DATA_USER_ADMIN, DATA_PASS);
  }
  
  @AfterMethod
  public void afterMethod(){
    driver.manage().deleteAllCookies();
    driver.quit();
    actions = null;
  }
  
  /**
   * Test case ID 001
   * Delete a space
   */
  @Test
  public static void test01_deleteSpace(){
    info("Create a new space");
    String spaceName = "Test SpaceName";
    String spaceNav = "/spaces/" + spaceName.replace(" ", "_").toLowerCase();
    
    info(spaceNav + " - " + spaceName);
    
    goToMySpacePage();
    addNewSpace(spaceName, "Test Description");    
    goToGroupSites();
    waitForTextPresent(spaceNav);
    
    info("Delete a space");
    //goToMySpacePage();    
    //deleteSpace(spaceName, 120000);
    restoreData(spaceName, 120000);
    
    info("Verification group disappear");
    goToGroupSites();
    waitForTextNotPresent(spaceNav);    
  }
}
