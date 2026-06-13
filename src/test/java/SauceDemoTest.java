import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions
                 .PlaywrightAssertions.assertThat;

public class SauceDemoTest {
   static Playwright playwright;
   static Browser browser;
   BrowserContext context;
   Page page;

   @BeforeAll
   static void launchBrowser() {
      playwright = Playwright.create();
      browser = playwright.chromium()
                   .launch(new BrowserType.LaunchOptions()
                              .setHeadless(false));
   }

   @AfterAll
   static void closeBrowser() {
      playwright.close();
   }

   @BeforeEach
   void createContextAndPage() {
      context = browser.newContext();
      page = context.newPage();
   }

   @AfterEach
   void closeContext() {
      context.close();
   }

   @Test
   void loginComSucesso() {
      page.navigate("https://www.saucedemo.com/");

      page.getByPlaceholder("Username")
         .fill("standard_user");
      page.getByPlaceholder("Password")
         .fill("secret_sauce");
      page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Login"))
         .click();
      assertThat(page).hasURL(Pattern.compile(".*/inventory\\.html"));
      assertThat(page.locator(".title"))
         .hasText("Products");
   }

   @Test
   void loginComFalha() {
      page.navigate("https://www.saucedemo.com/");

      page.getByPlaceholder("Username")
         .fill("locked_out_user");
      page.getByPlaceholder("Password")
         .fill("secret_sauce");
      page.getByRole(AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Login"))
         .click();

      assertThat(page.locator("[data-test='error']"))
         .containsText("locked out");
   }
}