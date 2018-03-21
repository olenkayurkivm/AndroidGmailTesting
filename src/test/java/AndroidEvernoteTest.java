
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AndroidEvernoteTest {

    private static final Logger LOG = Logger.getLogger(AndroidEvernoteTest.class);

    private static WebDriver driver;

    @BeforeMethod
    public static void setUp(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung Galaxy S6");
        capabilities.setCapability("appPackage", "com.evernote");
        capabilities.setCapability("appActivity", "com.evernote.ui.HomeActivity");
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new AppiumDriver(url, capabilities){
            {
                manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
            }
        };
    }

    @Test
    public void testChromeSelenium() {

        // Log in

        WebElement email = driver.findElement(By.id("com.evernote:id/landing_email"));
        email.sendKeys("olenayurkiv2017@gmail.com");
        driver.findElement(By.id ("com.evernote:id/continue_button")).click();
        WebElement password = driver.findElement(By.id("com.evernote:id/landing_login_password"));
        password.sendKeys("0633787Mm");
        driver.findElement(By.id("com.evernote:id/landing_sign_in_button")).click();
        LOG.info ("Successfully logged in");

        // Add note

        driver.findElement(By.className("android.widget.ImageButton")).click();
        driver.findElement(By.id("com.evernote:id/home_list_text")).click();
        driver.findElement(By.id("com.evernote:id/main_fab_image_view")).click();
        driver.findElement(By.id("com.evernote:id/skittle_0")).click();
        WebElement note = driver.findElement(By.id("com.evernote:id/title"));
        note.sendKeys("My first note");
        driver.findElement(By.id("com.evernote:id/check_mark")).click();
        LOG.info ("Added new note to my notelist");

        // Edit note

        driver.findElement(By.id("com.evernote:id/edit_skittle")).click();
        //WebElement note2 = driver.findElement(By.id("com.evernote:id/title"));
        note.sendKeys("My edited first note");
        driver.findElement(By.id("com.evernote:id/check_mark")).click();
        LOG.info ("Edited my new note");

        // Delete note

        driver.findElement(By.id("com.evernote:id/overflow_icon")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Delete note']")).click();
        driver.findElement(By.id("android:id/button1")).click();
        LOG.info ("Deleted current note");

        // Verify node was deleted

        Boolean isDeletedNodeDisplayed = false;
        List<WebElement> allTextViewElements = driver.findElements(By.className("android.widget.TextView"));
        for(WebElement textElement : allTextViewElements) {
            LOG.info(textElement.getText());
            if (textElement.getText().contains("My edited first note")) {
                isDeletedNodeDisplayed = true;
                break;
            }
        }

        Assert.assertFalse(isDeletedNodeDisplayed);

    }

    @AfterMethod
    public static void cleanUp(){
        if (driver != null) {
            driver.quit();
        }

    }
}
