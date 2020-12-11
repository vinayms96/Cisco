package com.cisco;

import com.cisco.extent_reports.ExtentReport;
import com.cisco.pageModels.LoginModel;
import com.cisco.pageModels.SignupModel;
import com.cisco.project_setup.TestNGBase;
import com.cisco.utilities.ExcelUtils;
import com.cisco.utilities.Property;
import com.magento.loggers.Loggers;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserAccounts extends TestNGBase {
    public WebDriver driver;

    /**
     * Setting up Loggers and Extent reports
     */
    @BeforeClass(description = "Pre Test Configurations", alwaysRun = true)
    public void preTestRuns() {
        // Initialize Driver
        driver = initializeDriver();

        // Setting the Loggers and Extent Reports
        Loggers.setLogger(UserAccounts.class.getName());
        ExtentReport.createTest("User Accounts");
        ExcelUtils.getRowData(Integer.parseInt(Property.getProperty("testRow")));
    }

    /**
     * Create New Account
     */
    @Test(description = "Creating the User Account", priority = 1, groups = {"userAccounts.accountCreate"})
    public void accountCreate() {
        // PageModel object
        SignupModel signupModel = new SignupModel(driver);
//        AccountModel accountModel = new AccountModel(driver);
        LoginModel loginModel = new LoginModel(driver);

        signupModel.clickCreateAccountLink(driver);
        signupModel.fillCustomerForm(driver);

//        accountModel.accountCreateVerify();

        if (loginModel.verifyLogin() == true) {
            driver.get(Property.getProperty("url") + "/customer/account/logout/");
        }
    }

    /**
     * Login to User Account
     */
    @Test(description = "Logging into the User Account", priority = 2, groups = {"userAccounts.accountLogin"})
    public void accountLogin() {
        // PageModel object
        LoginModel loginModel = new LoginModel(driver);

        loginModel.fillLoginForm(driver);
        if(loginModel.verifyLogin() == true) {
            ExtentReport.getExtentNode().pass("User logged in Successfully");
        } else {
            ExtentReport.getExtentNode().fail("User could not be logged in");
        }
    }

}
