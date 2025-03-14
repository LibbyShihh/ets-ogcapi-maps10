package org.opengis.cite.ogcapimaps10.conformance.crs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.opengis.cite.ogcapimaps10.OgcApiMaps10.DEFAULT_CRS;

import org.junit.Test;
import org.opengis.cite.ogcapimaps10.exception.UnknownCrsException;

/**
 * @author <a href="mailto:goltz@lat-lon.de">Lyn Goltz </a>
 */
public class CoordinateSystemTest {

	@Test
	public void testGetSrid_CRS84() {
		assertThat(DEFAULT_CRS.getSrid(), is(84));
	}

	@Test
	public void testGetSrid_HTTP() {
		CoordinateSystem coordinateSystem = new CoordinateSystem("http://www.opengis.net/def/crs/EPSG/0/3163");
		assertThat(coordinateSystem.getSrid(), is(3163));
	}

	@Test
	public void testGetSrid_URN() {
		CoordinateSystem coordinateSystem = new CoordinateSystem("urn:ogc:def:crs:EPSG::3163");
		assertThat(coordinateSystem.getSrid(), is(3163));
	}

	@Test
	public void testGetCodeWithAuthority_CRS84() {
		assertThat(DEFAULT_CRS.getCodeWithAuthority(), is("OGC:CRS84"));
	}

	@Test
	public void testGetCodeWithAuthority_HTTP() {
		CoordinateSystem coordinateSystem = new CoordinateSystem("http://www.opengis.net/def/crs/EPSG/0/3163");
		assertThat(coordinateSystem.getCodeWithAuthority(), is("EPSG:3163"));
	}

	@Test
	public void testGetCodeWithAuthority_URN() {
		CoordinateSystem coordinateSystem = new CoordinateSystem("urn:ogc:def:crs:EPSG::3163");
		assertThat(coordinateSystem.getCodeWithAuthority(), is("EPSG:3163"));
	}

	@Test(expected = UnknownCrsException.class)
	public void testGetCodeWithAuthority_Unknown() {
		CoordinateSystem coordinateSystem = new CoordinateSystem("urn:io:crs:EPSG::3163");
		coordinateSystem.getCodeWithAuthority();
	}

}
