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

public class DynamicPageTest extends BaseTest {

  private final By MenuBarLocator = By.className("menubar");
  private final By WarningLocator = By.cssSelector("div[style*='background-color:lightgray']");

  private final String[] URLs = {
      "http://testasp.vulnweb.com/Templatize.asp?item=html/about.html",
      "http://testasp.vulnweb.com/Default.asp",
      "http://testasp.vulnweb.com/Search.asp",
      "http://testasp.vulnweb.com/Login.asp?RetURL=%2FSearch%2Easp%3F",
      "http://testasp.vulnweb.com/Register.asp?RetURL=%2FSearch%2Easp%3F"
  };

  private final String[] titles = {
      "untitled document",
      "acuforum forums",
      "acuforum search",
      "acuforum login",
      "acuforum register"
  };

  @Before
  public void setup() throws MalformedURLException {
    baseSetup();
  }

  @Test
  public void DynamicPageTesting() {

    for (int i = 0; i < URLs.length; ++i) {
      this.driver.get(URLs[i]);

      WebElement MenuBar = waitVisibilityAndFindElement(MenuBarLocator);
      WebElement Warning = waitVisibilityAndFindElement(WarningLocator);

      Assert.assertTrue(this.driver.getTitle().toLowerCase().contains(titles[i]));
      Assert.assertTrue(MenuBar.getText().contains("about"));
      Assert.assertTrue(MenuBar.getText().contains("forums"));
      Assert.assertTrue(MenuBar.getText().contains("search"));
      Assert.assertTrue(MenuBar.getText().contains("login"));
      Assert.assertTrue(MenuBar.getText().contains("register"));
      Assert.assertTrue(MenuBar.getText().contains("SQL scanner"));
      Assert.assertTrue(MenuBar.getText().contains("SQL vuln help"));
      Assert.assertTrue(Warning.getText().contains("Warning:"));

    }

  }

}
