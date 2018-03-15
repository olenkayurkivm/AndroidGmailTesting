package com.epam;

import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverManager {

    private static final Logger LOG = Logger.getLogger(DriverManager.class);

    private static Map<Long,WebDriver> drivers = new HashMap<>();
    private static Semaphore semaphore = new Semaphore(3);

    private DriverManager() {
    }

    private static WebDriver getInstance() {
        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capability.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1");
        capability.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung Galaxy S6");
        capability.setCapability(MobileCapabilityType.BROWSER_NAME, "Browser");
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:4723/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new AppiumDriver(url, capability) {
            {
                manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
            }
        };
    }

    public static WebDriver getDriver() {
        Long currentThread = Thread.currentThread().getId();
        WebDriver driver = drivers.get(currentThread);
        if (driver == null) {
            driver = getInstance();
            LOG.info(String.format("Thread.currentThread().getId()  %d", Thread.currentThread().getId()));
            drivers.put(currentThread, driver);
        }
        return driver;
    }

    public static void getThread() {
        try {
            semaphore.acquire();
            getDriver();
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }

    }

    public static void releaseThread() {
        if (DriverManager.getDriver() != null)
            DriverManager.getDriver().quit();
        drivers.remove(Thread.currentThread().getId());
        semaphore.release();
    }
}
