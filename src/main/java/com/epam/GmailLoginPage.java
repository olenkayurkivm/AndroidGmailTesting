package com.epam;

import com.epam.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailLoginPage extends AbstractPage {

    @FindBy(xpath = "//a[@href='https://accounts.google.com/AccountChooser?service=mail&continue=https://mail.google.com/mail/']")
    private WebElement signInButton;

    @FindBy(xpath = "//input[@id='identifierId']")
    private WebElement gmailAddress;

    @FindBy(xpath = "//div[@id='identifierNext']")
    private WebElement gmailAddressNext;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement gmailPassword;

    @FindBy(xpath = "//div[@id='passwordNext']")
    private WebElement gmailPasswordNext;


    public void openInitialPage () {
        openPage("https://www.google.com/intl/ru/gmail/about/");
    }

    public void logIn (String login, String password) {
        WebDriverWait wait = getWait();
        signInButton.click();
        gmailAddress.sendKeys(login);
        gmailAddressNext.click();
        wait.until(ExpectedConditions.elementToBeClickable(gmailPassword));
        gmailPassword.sendKeys(password);
        gmailPasswordNext.click();
    }

}
