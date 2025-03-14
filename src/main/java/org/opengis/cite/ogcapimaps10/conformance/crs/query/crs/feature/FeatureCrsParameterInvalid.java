package org.opengis.cite.ogcapifeatures10.conformance.crs.query.crs.feature;

import static io.restassured.http.Method.GET;
import static org.opengis.cite.ogcapifeatures10.OgcApiFeatures10.CRS_PARAMETER;
import static org.opengis.cite.ogcapifeatures10.OgcApiFeatures10.GEOJSON_MIME_TYPE;
import static org.opengis.cite.ogcapifeatures10.OgcApiFeatures10.UNSUPPORTED_CRS;
import static org.opengis.cite.ogcapifeatures10.util.JsonUtils.findFeatureUrlForGeoJson;

import org.testng.SkipException;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Verifies invalid CRS requesting /collection/{collectionId}/items
 *
 * <pre>
 * Abstract Test 5: /conf/crs/crs-parameter-invalid
 * Test Purpose: Verify that invalid values in the parameter crs are reported
 * Requirement: /req/crs/fc-crs-valid-value
 *
 * Test Method
 * For
 *  * each spatial feature collection collectionId
 * send a request with an unsupported CRS identifier in the parameter crs to
 *  * /collections/{collectionId}/items and
 *  * /collections/{collectionId}/items/{featureId} (with a valid featureId for the collection).
 * Verify that the response has status code 400.
 * Unsupported CRS identifiers are all strings not included in the crs property of the collection and also not included in the global CRS list, if #/crs is included in the crs property.
 * </pre>
 *
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz </a>
 */
public class FeatureCrsParameterInvalid extends AbstractFeatureCrs {

	/**
	 * Test: invalid CRS requesting /collections/{collectionId}/items
	 * @param collectionId id id of the collection, never <code>null</code>
	 * @param collection the /collection object, never <code>null</code>
	 * @param featureId id id of the feature, never <code>null</code>
	 */
	@Test(description = "Implements A.2.1 Query, Parameter crs, Abstract Test 1 (Requirement /req/crs/fc-crs-valid-value), "
			+ "Invalid CRS requesting path /collections/{collectionId}/items/{featureId}",
			dataProvider = "collectionFeatureId", dependsOnGroups = "crs-conformance", priority = 1)
	public void verifyFeatureCrsParameterInvalid(String collectionId, JsonPath collection, String featureId) {
		if ((collectionId == null) & (collection == null) & (featureId == null)) {
			throw new AssertionError("No crs information for collection available.");
		}
		String featureUrl = findFeatureUrlForGeoJson(rootUri, collection, featureId);
		if (featureUrl == null)
			throw new SkipException(
					String.format("Could not find url for collection with id %s supporting GeoJson (type %s)",
							collectionId, GEOJSON_MIME_TYPE));

		Response response = init().baseUri(featureUrl)
			.queryParam(CRS_PARAMETER, UNSUPPORTED_CRS)
			.accept(GEOJSON_MIME_TYPE)
			.when()
			.request(GET);
		response.then().statusCode(400);
	}

}
