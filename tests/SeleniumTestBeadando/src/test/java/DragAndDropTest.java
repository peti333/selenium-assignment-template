import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

public class DragAndDropTest extends BaseTest {

  private final By MenuForumsLocator = By.xpath("//div[@class='menubar']//a[text()='forums']");
  private final By SearchInputLocator = By.cssSelector("input[name='tfSearch']");

  @Test
  public void DragAndDropMenuElementIntoSearch() throws IOException, InterruptedException {

    try {
      this.driver.get("http://testasp.vulnweb.com/Search.asp");

      WebElement Forums = waitVisibilityAndFindElement(MenuForumsLocator);
      WebElement SearchInput = waitVisibilityAndFindElement(SearchInputLocator);

      new Actions(this.driver)
          .dragAndDrop(Forums, SearchInput)
          .perform();

      String searchInputValue = SearchInput.getAttribute("value");

      Assert.assertTrue(searchInputValue.contains("http://testasp.vulnweb.com/Default.asp"));

    } catch (Exception e) {
      File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenShot, new File("./screenShots/dragAndDropMenuElementIntoSearchTest.png"));
      throw e;
    }

  }

}
