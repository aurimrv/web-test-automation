import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

//import java.util.logging.Level;
//import java.util.logging.Logger;

public class SeleniumExampleMain {
   static int slowMo = 800; // ms between actions

   static WebElement find(WebDriver driver, By locator) {
      try { Thread.sleep(slowMo); } catch (InterruptedException ignored) {}
      return driver.findElement(locator);
   }

   public static void main(String[] args) {
      //Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
      //Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(Level.OFF);
      WebDriver driver = new ChromeDriver();
      driver.get("https://www.saucedemo.com/");

      find(driver, By.id("user-name")).sendKeys("standard_user");
      find(driver, By.id("password")).sendKeys("secret_sauce");
      find(driver, By.id("login-button")).click();

      System.out.println(find(driver, By.className("title")).getText());

      try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
      driver.quit();
   }
}