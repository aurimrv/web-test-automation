import com.microsoft.playwright.*;

public class PlaywrightExampleMain {
   public static void main(String[] args) {
      try (Playwright playwright = Playwright.create()) {
         Browser browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions()
               .setHeadless(false)
               .setSlowMo(800)
               .setArgs(java.util.List.of("--start-maximized")));

         Page page = browser.newPage();
         page.navigate("https://www.saucedemo.com/");

         page.locator("#user-name").fill("standard_user");
         page.locator("#password").fill("secret_sauce");
         page.locator("#login-button").click();

         System.out.println(
            page.locator(".title").textContent());
         page.waitForTimeout(3000);  // keeps the page visible for 3s
         browser.close();
      }
   }
}