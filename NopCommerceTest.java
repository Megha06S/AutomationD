package com.extentReport;

import org.testng.SkipException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class NopCommerceTest {

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
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/NopCommerceTestReports.html");
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

	@AfterTest
	public void endReport()
	{
		extent.flush();//everything will be closed
	}

	@BeforeMethod
	public void setup()
	{
		driver= new FirefoxDriver();
		driver.get("http://demo.nopcommerce.com");
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

		@Test
		public void nopCommerceTitleTest()
		{
			test=extent.createTest("nopCommerceTitleTest");
			String title= driver.getTitle();
			System.out.println(title);
			Assert.assertEquals(title, "nopCommerce demo store");
			
		}

		@Test
		public void nopCommerceLogoTest()
		{
			test=extent.createTest("nopCommerceLogoTest");
			Boolean status=driver.findElement(By.xpath("//img[@src='http://demo.nopcommerce.com/Themes/DefaultClean/Content/images/logo.png']")).isDisplayed();
			System.out.println(status);
			Assert.assertTrue(status);
		}

	@Test
	public void failedTest()
	{
		test=extent.createTest("failedTest");	

		String title= driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Commerce demo store");
	}

	@Test
	public void Test2()
	{
		test=extent.createTest("skipTest");	
		System.out.println("Skipping the test");

	}
	
	@Test()
	public void skipTestMethod() {
		throw new SkipException("Skipped Test");
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

//	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException
//	{
//		String dateName= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		TakesScreenshot screen = (TakesScreenshot)driver;
//		File src= screen.getScreenshotAs(OutputType.FILE);
//
//		//After execution , u will see a folder "Screenshots" under src folder
//
//		String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".jpeg";
//		File finalDest = new File(destination);
//		FileUtils.copyFile(src, finalDest);
//		return destination;
//	}

}
