package tests;

import enums.BrowserType;
import pages.PillowStoreHomePage;

public class TestExecuter {

    String DEFAULT_SUCCESSFUL_CREDIT_CARD_NUMBER = "4811111111111114";
    String DEFAULT_FAILED_CREDIT_CARD_NUMBER = "4911111111111113";
    PillowStoreHomePage pillowStoreWebsite;

    public void init(BrowserType browserType) {
        pillowStoreWebsite = new PillowStoreHomePage(browserType);
    }

    public boolean verifySuccessfulPurchase() {
        return verifySuccessfulPurchase(DEFAULT_SUCCESSFUL_CREDIT_CARD_NUMBER, null, true);
    }

    public boolean verifySuccessfulPurchase(String cardNumber) {
        return verifySuccessfulPurchase(cardNumber, null, true);
    }

    public boolean verifySuccessfulPurchase(String cardNumber, String email) {
        return verifySuccessfulPurchase(cardNumber, email, false);
    }

    public boolean verifySuccessfulPurchase(String cardNumber, String email, boolean useDefaultContext) {
        return true == pillowStoreWebsite.purchasePillowWithCreditCard(cardNumber, email, useDefaultContext);
    }

    public boolean verifyFailedPurchase() {
        return verifyFailedPurchase(DEFAULT_FAILED_CREDIT_CARD_NUMBER, null, true);
    }

    public boolean verifyFailedPurchase(String cardNumber) {
        return verifyFailedPurchase(cardNumber, null, true);
    }

    public boolean verifyFailedPurchase(String cardNumber, String email) {
        return verifyFailedPurchase(cardNumber, email, false);
    }

    public boolean verifyFailedPurchase(String cardNumber, String email, boolean useDefaultContext) {
        return false == pillowStoreWebsite.purchasePillowWithCreditCard(cardNumber, email, useDefaultContext);
    }

    public void printLogs() {
        try {
            String logs = pillowStoreWebsite.getLogs();
            if (logs != null && logs.length() > 0)
                System.out.println(logs);
        } catch (Exception e) {
        }
    }

    public void stop() {
        pillowStoreWebsite.stop();
    }
}
