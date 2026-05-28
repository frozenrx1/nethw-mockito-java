package ru.netology.geo;

import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class GeoServiceImplTest {

    private final GeoServiceImpl geoService = new GeoServiceImpl();

    @Test
    void testByIp_RussianSegment_returnsMoscowLocation() {
        Location location = geoService.byIp("172.10.20.30");
        assertNotNull(location);
        assertEquals("Moscow", location.getCity());
        assertEquals(Country.RUSSIA, location.getCountry());
    }

    @Test
    void testByIp_AmericanSegment_returnsNewYorkLocation() {
        Location location = geoService.byIp("96.10.20.30");
        assertNotNull(location);
        assertEquals("New York", location.getCity());
        assertEquals(Country.USA, location.getCountry());
    }

    @Test
    void testByIp_UnknownIP_returnsNull() {
        Location location = geoService.byIp("192.168.1.1");
        assertNull(location);
    }
}
