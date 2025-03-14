package org.opengis.cite.ogcapimaps10.conformance.crs.query.crs.features;

import static net.jadler.Jadler.closeJadler;
import static net.jadler.Jadler.initJadlerListeningOn;
import static net.jadler.Jadler.onRequest;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.opengis.cite.ogcapimaps10.OgcApiMaps10.UNSUPPORTED_CRS;

import java.net.URLEncoder;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.ISuite;
import org.testng.ITestContext;

import io.restassured.path.json.JsonPath;

/**
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz </a>
 */
public class FeaturesCrsParameterInvalidTest {

	private static ITestContext testContext;

	private static ISuite suite;

	@BeforeClass
	public static void initTestFixture() throws Exception {
		testContext = mock(ITestContext.class);
		suite = mock(ISuite.class);
		when(testContext.getSuite()).thenReturn(suite);
	}

	@Before
	public void setUp() {
		initJadlerListeningOn(8090);
	}

	@After
	public void tearDown() {
		closeJadler();
	}

	@Test
	public void test() {
		prepareJadler();
		FeaturesCrsParameterInvalid featuresInvalidCrsParameter = new FeaturesCrsParameterInvalid();
		featuresInvalidCrsParameter.initCommonFixture(testContext);

		JsonPath collection = prepareCollection();
		featuresInvalidCrsParameter.verifyFeaturesCrsParameterInvalid("flurstueck", collection);
	}

	@Test(expected = AssertionError.class)
	public void test_unexpectedResponse() {
		prepareJadlerResponseStatusCode200();
		FeaturesCrsParameterInvalid featuresInvalidCrsParameter = new FeaturesCrsParameterInvalid();
		featuresInvalidCrsParameter.initCommonFixture(testContext);

		JsonPath collection = prepareCollection();
		featuresInvalidCrsParameter.verifyFeaturesCrsParameterInvalid("flurstueck", collection);
	}

	private static JsonPath prepareCollection() {
		return new JsonPath(FeaturesCrsParameterInvalidTest.class
			.getResourceAsStream("../../../../core/collections/collection-flurstueck.json"));
	}

	private void prepareJadler() {
		onRequest().havingPath(endsWith("collections/flurstueck/items"))
			.havingQueryString(containsString("crs=" + URLEncoder.encode(UNSUPPORTED_CRS)))
			.respond()
			.withStatus(400);
	}

	private void prepareJadlerResponseStatusCode200() {
		onRequest().havingPath(endsWith("collections/flurstueck/items"))
			.havingQueryString(containsString("crs=" + URLEncoder.encode(UNSUPPORTED_CRS)))
			.respond()
			.withStatus(200);
	}

}
