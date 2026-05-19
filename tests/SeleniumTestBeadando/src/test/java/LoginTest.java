import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;

public class LoginTest extends BaseTest {

    private final String username = "asd";
    private final String password = "asd";

    private final By UsernameLocator = By.id("tfUName");
    private final By PasswordLocator = By.id("tfUPass");
    private final By SignInButtonLocator = By.cssSelector("input[type='submit']");
    private final By LogoutButtonLocator = By.xpath("//div[@class='menubar']//a[text()='logout asd']");

    // Will only work if account with "asd" username and password was recently
    // created because the website removes accounts very fast
    @Test
    public void testLoginSuccessAndLogout() throws IOException, InterruptedException {
        try {

            this.driver.get("http://testasp.vulnweb.com/Login.asp?RetURL=%2FDefault%2Easp%3F");

            WebElement UsernameInput = waitVisibilityAndFindElement(UsernameLocator);
            WebElement PasswordInput = waitVisibilityAndFindElement(PasswordLocator);
            WebElement SignInButton = waitVisibilityAndFindElement(SignInButtonLocator);

            UsernameInput.sendKeys(username);
            PasswordInput.sendKeys(password);
            SignInButton.click();

            WebElement logoutButton = waitVisibilityAndFindElement(LogoutButtonLocator);
            Assert.assertTrue(logoutButton.getText().contains("logout asd"));

            logoutButton.click();

            WebElement loginButton = driver.findElement(By.xpath("//div[@class='menubar']//a[text()='login']"));
            Assert.assertTrue(loginButton.getText().contains("login"));

        } catch (Exception e) {
            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShot, new File("./screenShots/testLoginSuccessAndLogout.png"));
            throw e;
        }
    }

    @Test
    public void testLoginUnSuccessful() throws IOException, InterruptedException {
        try {

            this.driver.get("http://testasp.vulnweb.com/Login.asp?RetURL=%2FDefault%2Easp%3F");

            WebElement UsernameInput = waitVisibilityAndFindElement(UsernameLocator);
            WebElement PasswordInput = waitVisibilityAndFindElement(PasswordLocator);
            WebElement SignInButton = waitVisibilityAndFindElement(SignInButtonLocator);

            UsernameInput.sendKeys("WrongUsername");
            PasswordInput.sendKeys("WrongPassword");
            SignInButton.click();

            WebElement invalidLogin = driver.findElement(By.xpath("//b[text()='Invalid login!']"));
            Assert.assertTrue(invalidLogin.getText().contains("Invalid login!"));

        } catch (Exception e) {
            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShot, new File("./screenShots/testLoginUnSuccessful.png"));
            throw e;
        }

    }

    @Test
    public void testLoginSuccessfulAndDeleteCookie() throws IOException, InterruptedException {
        try {

            this.driver.get("http://testasp.vulnweb.com/Login.asp?RetURL=%2FDefault%2Easp%3F");

            WebElement UsernameInput = waitVisibilityAndFindElement(UsernameLocator);
            WebElement PasswordInput = waitVisibilityAndFindElement(PasswordLocator);
            WebElement SignInButton = waitVisibilityAndFindElement(SignInButtonLocator);

            UsernameInput.sendKeys(username);
            PasswordInput.sendKeys(password);
            SignInButton.click();

            WebElement logoutButton = waitVisibilityAndFindElement(LogoutButtonLocator);
            Assert.assertTrue(logoutButton.getText().contains("logout asd"));

            Cookie cookie = driver.manage().getCookieNamed("ASPSESSIONIDCSABCTCC");
            Assert.assertNotEquals(cookie.getValue(), "");
            this.driver.manage().deleteCookieNamed("ASPSESSIONIDCSABCTCC");

            this.driver.navigate().refresh();

            WebElement loginButton = driver.findElement(By.xpath("//div[@class='menubar']//a[text()='login']"));
            Assert.assertTrue(loginButton.getText().contains("login"));

        } catch (Exception e) {
            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenShot, new File("./screenShots/testLoginUnSuccessfulAndDeleteCookie.png"));
            throw e;
        }
    }

}
