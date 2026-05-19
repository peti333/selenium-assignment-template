import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

public class HoverTest extends BaseTest {

  private final By MenuForumsLocator = By.xpath("//div[@class='menubar']//a[text()='forums']");

  @Test
  public void MenuBarHoverTest() throws IOException, InterruptedException {
    try {
      this.driver.get("http://testasp.vulnweb.com/Templatize.asp?item=html/about.html");

      WebElement Forums = waitVisibilityAndFindElement(MenuForumsLocator);
      String defaultBackgroundColor = Forums.getCssValue("background-color");
      String defaultTextColor = Forums.getCssValue("color");

      new Actions(this.driver)
          .moveToElement(Forums)
          .perform();

      String hoverBackgroundColor = Forums.getCssValue("background-color");
      String hoverTextColor = Forums.getCssValue("color");

      Assert.assertNotEquals(defaultBackgroundColor, hoverBackgroundColor);
      Assert.assertNotEquals(defaultTextColor, hoverTextColor);

      // BackgroundColor and TextColor switch on hover
      Assert.assertEquals("rgba(191, 255, 191, 1)", defaultBackgroundColor);
      Assert.assertEquals("rgba(0, 143, 0, 1)", defaultTextColor);

      Assert.assertEquals("rgba(191, 255, 191, 1)", hoverTextColor);
      Assert.assertEquals("rgba(0, 143, 0, 1)", hoverBackgroundColor);
    } catch (Exception e) {
      File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenShot, new File("./screenShots/testHover.png"));
      throw e;
    }
  }

}
