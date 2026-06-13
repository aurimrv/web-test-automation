import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions
                 .PlaywrightAssertions.assertThat;

public class SauceDemoTest {
   static Playwright playwright;
   static Browser browser;
   BrowserContext context;
   Page page;
   private String testName;

   @BeforeAll
   static void launchBrowser() {
      playwright = Playwright.create();
      browser = playwright.chromium()
                   .launch(new BrowserType.LaunchOptions()
                              .setHeadless(false)
                              .setSlowMo(800)
                              .setArgs(java.util.List.of("--start-maximized")));

   }

   @AfterAll
   static void closeBrowser() {
      playwright.close();
   }

   @BeforeEach
   void createContextAndPage(TestInfo testInfo) {
      testName = testInfo.getTestMethod()
                    .map(Method::getName)
                    .orElse("unknown");

      context = browser.newContext(
         new Browser.NewContextOptions()
            .setRecordVideoDir(Paths.get("videos/"))
            .setRecordVideoSize(1280, 720));

      context.tracing().start(
         new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));

      page = context.newPage();
   }

   @AfterEach
   void closeContext() {
      page.screenshot(new Page.ScreenshotOptions()
                         .setPath(Paths.get(testName+".png"))
                         .setFullPage(true));
      context.tracing().stop(
         new Tracing.StopOptions()
            .setPath(Paths.get(testName+".zip")));
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