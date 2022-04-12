package sc.stqa.gft.soap;

import net.webservices.GeoIPService;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {

        String xmlAddress = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("195.208.16.54");
        String countryISO = new GeoIPService().getGeoIPServiceSoap12().getCountryISO2ByName("Russian Federation");

        assertEquals(xmlAddress.substring(16,18), countryISO.substring(16,18));
    }
}
