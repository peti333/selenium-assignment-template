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

public class BaseTest {

  protected WebDriver driver;
  protected WebDriverWait wait;

  protected WebElement waitVisibilityAndFindElement(By locator) {
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    return this.driver.findElement(locator);
  }

  public void baseSetup() throws MalformedURLException {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
    driver.manage().window().maximize();

    wait = new WebDriverWait(driver, 20);
  }

}
