package tests;

import enums.BrowserType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import reports.GenerateReport;

@Listeners(GenerateReport.class)
public class purchaseTests {
    TestExecuter executer;
    @BeforeClass
    public void init() {
        executer = new TestExecuter();
        executer.init(BrowserType.CHROME);
    }

    @Test
    public void testSuccessfulPurchase() {
        boolean result= executer.verifySuccessfulPurchase();
        Assert.assertEquals(result,true);
        //executer.printLogs();
    }
    @Test
    public void testFailingPurchase() {
        boolean result= executer.verifyFailedPurchase();
        Assert.assertEquals(result,true);
        executer.printLogs();
    }
    @AfterClass
    public void exit()
    {
        //executer.stop();
    }
}