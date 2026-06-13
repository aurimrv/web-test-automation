import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.*;

/** Test created with Codegen tool */
public class Example {
   public static void main(String[] args) {
      try (Playwright playwright = Playwright.create()) {
         Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                                                           .setHeadless(false).setSlowMo(800));
         BrowserContext context = browser.newContext();
         Page page = context.newPage();
         page.navigate("https://www.saucedemo.com/");
         page.locator("[data-test=\"username\"]").click();
         page.locator("[data-test=\"username\"]").fill("visual_user");
         page.locator("[data-test=\"username\"]").press("Tab");
         page.locator("[data-test=\"password\"]").fill("secret_sauce");
         page.locator("[data-test=\"login-button\"]").click();
         page.locator("[data-test=\"add-to-cart-sauce-labs-backpack\"]").click();
         page.locator("[data-test=\"shopping-cart-link\"]").click();
         page.locator("[data-test=\"checkout\"]").click();
         page.locator("[data-test=\"firstName\"]").click();
         page.locator("[data-test=\"firstName\"]").fill("User");
         page.locator("[data-test=\"firstName\"]").press("Tab");
         page.locator("[data-test=\"lastName\"]").fill("Fulano");
         page.locator("[data-test=\"lastName\"]").press("Tab");
         page.locator("[data-test=\"postalCode\"]").fill("17230");
         page.locator("[data-test=\"continue\"]").click();
         page.locator("[data-test=\"finish\"]").click();
      }
   }
}