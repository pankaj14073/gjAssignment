package baseModels;

import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public abstract class BasePage {
    protected WebDriver driver;
    protected ArrayList<String> logger;
    protected WebDriverWait wait;

    public BasePage(enums.BrowserType browserType) {
        try {
            this.setBrowser(browserType);
            this.logger = new ArrayList<String>();
            wait = new WebDriverWait(driver, 10);
        } catch (Exception e) {
            this.logger.add(e.getMessage());
        }
    }

    public void loadUrl(String url) throws Exception {
        driver.get(url);
    }

    public WebDriver setBrowser(enums.BrowserType browserType) throws Exception {
        return setBrowser(browserType, false);
    }

    public String getTextOfElement(String cssLocator)
    {
        return driver.findElement(By.cssSelector(cssLocator)).getText();
    }
    public WebDriver setBrowser(enums.BrowserType browserType, boolean headless) throws Exception {
        switch (browserType) {
            case CHROME: {
                System.setProperty("webdriver.chrome.driver", "/home/pankaj/Desktop/jars/drivers/chromedriver");
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless)
                    chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
                break;
            }
            case FIREFOX: {
                System.setProperty("webdriver.firefox.marionette", "/home/pankaj/Desktop/jars/drivers/geckodriver");
                driver = new FirefoxDriver();
                break;
            }
        }
        return driver;
    }

    public WebElement findElement(String cssLocator) throws Exception {
        return driver.findElement(By.cssSelector(cssLocator));
    }

    public void waitForElementToBeClickable(String cssLocator) throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssLocator)));
    }

    public void waitForElementPresence(String cssLocator) throws Exception {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssLocator)));

    }

    public void waitForElementToBeVisible(String cssLocator) throws Exception {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssLocator)));
    }

    public void clickOnElement(String cssLocator) throws Exception {
        driver.findElement(By.cssSelector(cssLocator)).click();
    }

    public void inputValueToElement(String cssLocator, String inputValue) throws Exception {
        driver.findElement(By.cssSelector(cssLocator)).sendKeys(inputValue);
    }

    public String getLogs() {
        String logs = "";
        for (String e : logger) {
            logs = logs + e + "\n";
        }
        return logs;
    }

    public void stop() {
        try {
            driver.quit();
        } catch (
                Exception e) {
            this.logger.add(e.getMessage());
        }
    }
}
