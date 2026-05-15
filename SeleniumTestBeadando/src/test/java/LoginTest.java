import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String email = "haylynn.donohoe@openmail.pro";
    private final String username = "TestingLogin";
    private final String password = "BigP4ssW0rd!+2GH.,16";

    private final By SignInLocator = By.cssSelector(".navitem.sign-in-menu a span.label");
    private final By UsernameLocator = By.name("username");
    private final By PasswordLocator = By.name("password");
    private final By SignInButtonLocator = By.cssSelector(".signin-form input[type='submit']");
    private final By SignOutButtonLocator = By.id("sign-out");
    private final By AccountLocator = By.cssSelector(".has-icon.toggle-menu");

    private WebElement waitVisibilityAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testLoginSuccessAndLogout() throws IOException, InterruptedException {
        try{

        this.driver.get("https://letterboxd.com/");

        WebElement SignInElement = waitVisibilityAndFindElement(SignInLocator);
        Assert.assertTrue(SignInElement.getText().contains("SIGN IN"));
        SignInElement.click();

        WebElement UsernameInput = waitVisibilityAndFindElement(UsernameLocator);
        WebElement PasswordInput = waitVisibilityAndFindElement(PasswordLocator);
        WebElement SignInButton = waitVisibilityAndFindElement(SignInButtonLocator);

        UsernameInput.sendKeys(username);
        PasswordInput.sendKeys(password);
        SignInButton.click();

        // Wait for signing in
        this.wait(5000);

        this.wait.until(ExpectedConditions.urlContains("/welcome/"));
        WebElement Account = waitVisibilityAndFindElement(AccountLocator);
        Assert.assertTrue(Account.getText().contains(username));

        // Hover action
        new Actions(driver)
                .moveToElement(Account)
                .perform();

        WebElement SignOutButton = waitVisibilityAndFindElement(SignOutButtonLocator);
        SignOutButton.click();

        WebElement SignInElementAfterSignOut = waitVisibilityAndFindElement(SignInLocator);
        Assert.assertTrue(SignInElement.getText().contains("SIGN IN"));

        }
        catch(Exception e){
            System.err.println("Taking screenshot");
            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShot, new File("./screenShots/testLoginSuccessAndLogout.png"));
            throw e;
        }
    }

}
