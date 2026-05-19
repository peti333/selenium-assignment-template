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

public class NavigationTest extends BaseTest {

  private final By MenuSearchLocator = By.xpath("//div[@class='menubar']//a[text()='search']");

  @Test
  public void NavigateMenubarTest() {
    this.driver.get("http://testasp.vulnweb.com/");
    Assert.assertTrue(this.driver.getTitle().contains("acuforum forums"));

    WebElement MenuSearch = waitVisibilityAndFindElement(MenuSearchLocator);
    MenuSearch.click();

    Assert.assertTrue(this.driver.getTitle().contains("acuforum search"));

    this.driver.navigate().back();
    Assert.assertTrue(this.driver.getTitle().contains("acuforum forums"));

    this.driver.navigate().forward();
    Assert.assertTrue(this.driver.getTitle().contains("acuforum search"));
  }

}
