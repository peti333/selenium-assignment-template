import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

public class StaticPageTest extends BaseTest {

  private final By FooterLocator = By.className("footer");
  private final By MenuBarLocator = By.className("menubar");

  @Test
  public void staticPageTesting() {

    try{

    this.driver.get("http://testasp.vulnweb.com/Templatize.asp?item=html/about.html");

    WebElement Footer = waitVisibilityAndFindElement(FooterLocator);
    Assert.assertTrue(Footer.getText().contains("Copyright 2019 Acunetix Ltd."));

    WebElement MenuBar = waitVisibilityAndFindElement(MenuBarLocator);
    Assert.assertTrue(MenuBar.getText().contains("about"));
    Assert.assertTrue(MenuBar.getText().contains("forums"));
    Assert.assertTrue(MenuBar.getText().contains("search"));
    Assert.assertTrue(MenuBar.getText().contains("login"));
    Assert.assertTrue(MenuBar.getText().contains("register"));
    Assert.assertTrue(MenuBar.getText().contains("SQL scanner"));
    Assert.assertTrue(MenuBar.getText().contains("SQL vuln help"));
  }
  catch( Exception e)
  {
    File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screenShot, new File("./screenShots/testStaticPage.png"));
    throw e;
  }

}
