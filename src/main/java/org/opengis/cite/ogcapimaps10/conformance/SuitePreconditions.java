package org.opengis.cite.ogcapimaps10.conformance;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

/**
 * Checks that various preconditions are satisfied before the test suite is run. If any of
 * these (BeforeSuite) methods fail, all tests will be skipped.
 */
public class SuitePreconditions {

	private static final Logger LOGR = Logger.getLogger(SuitePreconditions.class.getName());

	/**
	 * Verifies that the referenced test subject exists and has the expected type.
	 */
	@BeforeSuite
	@SuppressWarnings("rawtypes")
	public void verifyTestSubject() {
		// ITestcontext was removed from the BeforeSuite annotation, see here:
		// https://github.com/testng-team/testng/issues/3032
		ITestContext testContext = Reporter.getCurrentTestResult().getTestContext();
		SuiteAttribute testFileAttr = SuiteAttribute.TEST_SUBJ_FILE;
		Object sutObj = testContext.getSuite().getAttribute(testFileAttr.getName());
		Class expectedType = testFileAttr.getType();
		if (null != sutObj && expectedType.isInstance(sutObj)) {
			// TODO: Verify test subject
		}
		else {
			String msg = String.format("Value of test suite attribute '%s' is missing or is not an instance of %s",
					testFileAttr.getName(), expectedType.getName());
			LOGR.log(Level.SEVERE, msg);
			throw new AssertionError(msg);
		}
	}

}
