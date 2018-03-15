package com.epam;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailHomePage extends AbstractPage {

    @FindBy(xpath = "//div[@class='M j T b hc Zm  Ke']")
    private WebElement composeButton;

    @FindBy(xpath = "//input[@class='on Cv']")
    private WebElement gmailToAddress;

    @FindBy(xpath = "//input[@class='tn']")
    private WebElement gmailSubject;

    @FindBy(xpath = "//div[@class='qn']")
    private WebElement gmailBody;

    @FindBy(xpath = "//div[@class='M j T b hc en Ee']")
    private WebElement sendButton;

    @FindBy(xpath = "//div[@class='M j T b hc Zm  Wm']")
    private WebElement gmailMenu;

    @FindBy(xpath = "//div[@class='dl ql ec']//span")
    private WebElement sentMailsButton;

    @FindBy(xpath = "//div[@class='V j cb cm']")
    private WebElement lastSentMail;

    @FindBy(xpath = "//div[@class='V j Vd']")
    private WebElement trashButton;

    @FindBy(xpath = "//div[@class='CB Yg']//span[text()='Отправлено в корзину']")
    private WebElement noMessageImage;


    public void composeMail(String sentTo, String subject, String body) {
        composeButton.click();
        gmailToAddress.sendKeys(sentTo);
        gmailSubject.sendKeys(subject);
        gmailBody.sendKeys(body);
        sendButton.click();
    }

    public void deleteMail() {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.elementToBeClickable(gmailMenu));
        gmailMenu.click();
        sentMailsButton.click();
        lastSentMail.click();
        wait.until(ExpectedConditions.elementToBeClickable(trashButton));
        trashButton.click();
    }

    public Boolean verifyMailIsDeleted() {
        WebDriverWait wait = getWait();
        wait.until(ExpectedConditions.visibilityOf(noMessageImage));
        return noMessageImage.isDisplayed();
    }
}
