package com.extentReport;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.Utility.CustomizedReport;

public class RahulCase1 extends CustomizedReport{

	
	@Test()
	public void skipTestMethod() {
		test=extent.createTest("Skipped");
		throw new SkipException("Skipped Test");
	}
	
	// Pass Test
		@Test()
		public void passTestMethod() {
			
			test=extent.createTest("passedTest");
			Assert.assertTrue(true);
		}

		// Fail Test
		@Test()
		public void failTestMethod() {
			
			test=extent.createTest("failedTest");
			Assert.assertTrue(false);
		}

}
	
