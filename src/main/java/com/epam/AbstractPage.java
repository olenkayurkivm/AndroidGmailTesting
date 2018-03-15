package com.epam;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class AbstractPage {

    private WebDriver driver;

    public AbstractPage() {
        driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void openPage(String pageURL) {
        driver.get(pageURL);
    }

    public WebDriverWait getWait() {
        return (new WebDriverWait(driver, 45));
    }

}
