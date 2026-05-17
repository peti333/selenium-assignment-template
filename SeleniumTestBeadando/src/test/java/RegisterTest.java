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
import java.util.Random;

//Successful Register redirects you to Login Page
//UnSuccessful Register keeps you on Register Page
public class RegisterTest extends BaseTest {

  private WebDriver driver;
  private WebDriverWait wait;

  private String RandomUsername;
  private String RandomRealName;
  private String RandomEmail;
  private String RandomPassword;

  private final By UsernameLocator = By.id("tfUName");
  private final By RealnameLocator = By.id("tfRName");
  private final By EmailLocator = By.id("tfEmail");
  private final By PasswordLocator = By.id("tfUPass");
  private final By SignInButtonLocator = By.cssSelector("input[type='submit']");

  public String getRandomXletterWord(int cnt) {
    Random random = new Random();
    StringBuilder word = new StringBuilder(cnt);

    for (int i = 0; i < cnt; i++) {
      char randomChar = (char) (random.nextInt(97, 123));
      word.append(randomChar);
    }

    return word.toString();
  }

  public void generateRandomUserData() {
    RandomUsername = getRandomXletterWord(5);
    RandomRealName = getRandomXletterWord(5) + " " + getRandomXletterWord(6);
    RandomPassword = getRandomXletterWord(8);
    RandomEmail = getRandomXletterWord(7) + "@mail.com";
  }

  @Before
  public void setup() throws MalformedURLException {
    baseSetup();
    generateRandomUserData();
  }

  @Test
  public void testSuccessfulRegister() throws IOException, InterruptedException {
    try {

      this.driver.get("http://testasp.vulnweb.com/Register.asp?RetURL=%2FSearch%2Easp%3F");

      WebElement UsernameInput = waitVisibilityAndFindElement(UsernameLocator);
      WebElement RealnameInput = waitVisibilityAndFindElement(RealnameLocator);
      WebElement EmailInput = waitVisibilityAndFindElement(EmailLocator);
      WebElement PasswordInput = waitVisibilityAndFindElement(PasswordLocator);
      WebElement SignInButton = waitVisibilityAndFindElement(SignInButtonLocator);

      UsernameInput.sendKeys(RandomUsername);
      RealnameInput.sendKeys(RandomRealName);
      PasswordInput.sendKeys(RandomPassword);
      EmailInput.sendKeys(RandomEmail);
      SignInButton.click();

      Assert.assertTrue(this.driver.getTitle().toLowerCase().contains("login"));

    } catch (Exception e) {
      File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenShot, new File("./screenShots/testSuccessfulRegister.png"));
      throw e;
    }
  }

  @Test
  public void TestUnsuccessfulRegister() throws IOException, InterruptedException {
    try {

      this.driver.get("http://testasp.vulnweb.com/Register.asp?RetURL=%2FSearch%2Easp%3F");

      WebElement UsernameInput = waitVisibilityAndFindElement(UsernameLocator);
      WebElement RealnameInput = waitVisibilityAndFindElement(RealnameLocator);
      WebElement SignInButton = waitVisibilityAndFindElement(SignInButtonLocator);

      UsernameInput.sendKeys(RandomUsername);
      RealnameInput.sendKeys(RandomRealName);
      SignInButton.click();

      Assert.assertTrue(this.driver.getTitle().toLowerCase().contains("register"));

    } catch (Exception e) {
      File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(screenShot, new File("./screenShots/testUnsuccessfulRegister.png"));
      throw e;
    }

  }

}
