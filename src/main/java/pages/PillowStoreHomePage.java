package pages;

import baseModels.BasePage;
import enums.BrowserType;
import models.Buyer;
import models.payment.CreditCardPayment;
import context.BuyerContext;
import context.PaymentContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PillowStoreHomePage extends BasePage {

    String baseUrl = "https://demo.midtrans.com/";
    String headerLocator = ".title";
    String buyNowButtonLocator = ".btn.buy";
    String shoppingCartHeaderLocator = ".cart-inner";
    String nameFieldLocator = "tbody>tr:nth-child(1) input";
    String emailFieldLocator = "tbody>tr:nth-child(2) input";
    String phoneNumberFieldLocator = "tbody>tr:nth-child(3) input";
    String cityFieldLocator = "tbody>tr:nth-child(4) input[type='text']";
    String addressFieldLocator = "tbody>tr:nth-child(5) textarea";
    String pinCodeFieldLocator = "tbody>tr:nth-child(6) input";
    String checkOutButtonLocator = ".cart-checkout";
    String checkOutHeaderLocator = "#header";
    String checkOutIframeId = "snap-midtrans";
    String checkOutInnerIframeLocator = "iframe";
    String orderSummaryLabelLocator = ".text-page-title-content";
    String continueLabelLocator = "#application a.button-main-content";
    String paymentListContainerLocator = "#payment-list";
    String creditCardPaymentOptionLocator = "#payment-list>div:nth-child(1) a";
    String creditCardNumberFieldLocator = "input[name='cardnumber']";
    String creditCardExpiringDateFieldLocator = "form>div:nth-child(2)>div:nth-child(2) input";
    String creditCardCvvFieldLocator = "form>div:nth-child(2)>div:nth-child(3) input";
    String payNowButtonLocator = ".button-main-content";
    String creditCardOkButtonLocator = "button[type='submit']";
    String creditCardOtpFieldLocator = "input[type='password']";
    String purchaseSuccessTextElementLocator = ".text-success";
    String purchaseFailedTextElementLocator = ".text-failed";

    public PillowStoreHomePage(BrowserType browserType) {
        super(browserType);
    }

    private void loadBasePage() {
        try {
            this.driver.get(this.baseUrl);
            this.waitForElementToBeVisible(headerLocator);
        } catch (Exception e) {
            this.logger.add(e.getMessage());
        }
    }

    public boolean purchasePillowWithCreditCard(String creditCardNumber, String email, boolean useDefaultContext) {
        try {
            doCheckOutWithEmail(email, useDefaultContext);
            doContinueOverPurchaseSummary();
            doPaymentWithCreditCard(creditCardNumber);
            return verifySuccessMessage();
        } catch (Exception e) {
            this.logger.add(e.getMessage());
            return false;
        }
    }

    private boolean verifySuccessMessage() {
        try {
            driver.switchTo().parentFrame();
            waitForElementPresence(purchaseSuccessTextElementLocator);
            WebElement element = findElement(purchaseSuccessTextElementLocator);
            if (element != null)
                return true;
            else
                return false;
        } catch (Exception e) {
            logger.add(e.getMessage());
            return false;
        }
    }

    private void doPaymentWithCreditCard(String creditCardNumber) {
        try {
            PaymentContext context = new PaymentContext();
            CreditCardPayment creditCardPayment = context.getCreditCardPaymentDetails(creditCardNumber);
            waitForElementToBeVisible(paymentListContainerLocator);
            clickOnElement(creditCardPaymentOptionLocator);
            inputValueToElement(creditCardNumberFieldLocator, creditCardPayment.cardNumber);
            inputValueToElement(creditCardExpiringDateFieldLocator, creditCardPayment.expiringDate);
            inputValueToElement(creditCardCvvFieldLocator, creditCardPayment.cvv);
            clickOnElement(payNowButtonLocator);
            switchToIframeByCssLocator(checkOutInnerIframeLocator);
            waitForElementToBeVisible(creditCardOtpFieldLocator);
            inputValueToElement(creditCardOtpFieldLocator, creditCardPayment.otp);
            clickOnElement(creditCardOkButtonLocator);
        } catch (Exception e) {
            this.logger.add(e.getMessage());
        }
    }

    void switchToIframeByCssLocator(String cssLocator) {
        WebElement iframeElement = driver.findElement(By.cssSelector(cssLocator));
        driver.switchTo().frame(iframeElement);
    }

    void switchToIframeById(String iframeId) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(iframeId)));
        WebElement iframeElement = driver.findElement(By.id(iframeId));
        driver.switchTo().frame(iframeElement);
    }

    private void doContinueOverPurchaseSummary() {
        try {
            switchToIframeById(checkOutIframeId);
            waitForElementToBeClickable(continueLabelLocator);
            clickOnElement(continueLabelLocator);
        } catch (Exception e) {
            this.logger.add(e.getMessage());

        }
    }

    private void doCheckOutWithEmail(String email, boolean useDefaultContext) {
        try {
            loadBasePage();
            clickOnElement(buyNowButtonLocator);
            waitForElementToBeVisible(shoppingCartHeaderLocator);

            if (!useDefaultContext) {
                BuyerContext context = new BuyerContext();
                Buyer buyer = context.getBuyerByEmail(email);
                inputValueToElement(nameFieldLocator, buyer.name);
                inputValueToElement(emailFieldLocator, buyer.email);
                inputValueToElement(phoneNumberFieldLocator, buyer.phoneNumber);
                inputValueToElement(cityFieldLocator, buyer.city);
                inputValueToElement(addressFieldLocator, buyer.address);
                inputValueToElement(pinCodeFieldLocator, buyer.pinCode);
            }
            clickOnElement(checkOutButtonLocator);
        } catch (Exception e) {
            this.logger.add(e.getMessage());
        }

    }
}
