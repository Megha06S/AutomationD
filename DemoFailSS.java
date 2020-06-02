package com.extentReport;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.Utility.ScreenShotUtility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class DemoFailSS {
	
	public ExtentHtmlReporter reporter;
	public ExtentReports extent;
	public ExtentTest logger;
	WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{
		try{
		reporter= new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/RahulCase2.html");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		reporter.config().setDocumentTitle("Automation Report");//title of report
		reporter.config().setReportName("Functional Report");//name of the report
		reporter.config().setTheme(Theme.DARK);

		extent= new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Hostname", "LocalHost");
		extent.setSystemInfo("OS", "Windows10");
		extent.setSystemInfo("Tester name", "Megha Singh");
		extent.setSystemInfo("Browser", "Firefox");
		
		logger= extent.createTest("Login");
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			String temp = ScreenShotUtility.getScreenshot(driver);
			logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			
		}
		extent.flush();
		driver.quit();
	}
	
	@Test
	public void loginTest() throws IOException
	{
		driver=new FirefoxDriver();
		driver.navigate().to("https://www.google.com/");
		System.out.println("title is "+driver.getTitle());
		Assert.assertTrue(driver.getTitle().contains("Singh"));
		
		
	}
	
}
