package com.Utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class CustomizedReport {
	
	public WebDriver driver;

	//ExtentHtmlReporter - this class is responsible for look and feel of ur report
	//ExtentReports - this class is used to create entries in ur report(i.e. to create test cases in ur report)
	//ExtentTest - update ur status in report

	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeTest
	public void setExtent()
	{
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/RahulCase1.html");
		htmlReporter.config().setDocumentTitle("Automation Report");//title of report
		htmlReporter.config().setReportName("Functional Report");//name of the report
		htmlReporter.config().setTheme(Theme.DARK);

		extent= new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Hostname", "LocalHost");
		extent.setSystemInfo("OS", "Windows10");
		extent.setSystemInfo("Tester name", "Megha Singh");
		extent.setSystemInfo("Browser", "Firefox");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			test.log(Status.FAIL, "Test case FAILED is " + result.getName());//to add name in extent report
			test.log(Status.FAIL, "Test case FAILED is " + result.getThrowable());

//			String screenshotPath=NopCommerceTest.getScreenshot(driver, result.getName());
//
//			test.addScreenCaptureFromPath(screenshotPath);  
		}

		else if(result.getStatus()==ITestResult.SKIP)
		{
			test.log(Status.SKIP,"Test case SKIPPED is " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
			test.log(Status.PASS,"Test case PASSED is " + result.getName());

	}

	
	@AfterTest
	public void endReport()
	{
		extent.flush();//everything will be closed
	}

}
