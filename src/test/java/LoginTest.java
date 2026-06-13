import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

   private WebDriver driver;

   @BeforeEach
   void setup() {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless=new"); // Chrome moderno
      options.addArguments("--window-size=1920,1080");

      driver = new ChromeDriver(options);
      driver.get("https://www.saucedemo.com/");
   }

   @AfterEach
   void tearDown() {
      if (driver != null) {
         driver.quit();
      }
   }

   @Test
   void deveRealizarLoginComSucesso() {

      // Localização dos elementos
      WebElement username = driver.findElement(By.id("user-name"));
      WebElement password = driver.findElement(By.id("password"));
      WebElement loginButton = driver.findElement(By.id("login-button"));

      // Dados válidos
      username.sendKeys("standard_user");
      password.sendKeys("secret_sauce");

      // Ação
      loginButton.click();

      // Validação
      assertTrue(
         driver.getCurrentUrl().contains("inventory.html"),
         "Usuário não foi redirecionado para a página de produtos"
      );
   }

   @Test
   void loginComSucessoPageObject() {

      LoginPage loginPage = new LoginPage(driver);

      loginPage.login("standard_user", "secret_sauce");

      Assertions.assertTrue(driver.getCurrentUrl().contains("inventory"));
   }
}