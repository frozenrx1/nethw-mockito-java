package ru.netology;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageSenderImplTest {

    @Mock
    private GeoService geoService;

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private MessageSenderImpl messageSender;

    @Test
    void testSend_RussianIP_returnsRussianMessage() {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");

        Location mockLocation = new Location("Moscow", Country.RUSSIA, null, 0);
        when(geoService.byIp("172.123.12.19")).thenReturn(mockLocation);
        when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        String result = messageSender.send(headers);

        verify(geoService, times(1)).byIp("172.123.12.19");
        verify(localizationService, times(2)).locale(Country.RUSSIA);
        assertEquals("Добро пожаловать", result);
    }

    @Test
    void testSend_AmericanIP_returnsEnglishMessage() {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.123.12.19");

        Location mockLocation = new Location("New York", Country.USA, null, 0);
        when(geoService.byIp("96.123.12.19")).thenReturn(mockLocation);
        when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        String result = messageSender.send(headers);

        verify(geoService, times(1)).byIp("96.123.12.19");
        verify(localizationService, times(2)).locale(Country.USA);
        assertEquals("Welcome", result);
    }

    @Test
    void testSend_UnknownIP_returnsDefaultEnglishMessage() {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "192.168.1.1");

        Location mockLocation = new Location("Unknown", Country.USA, null, 0);
        when(geoService.byIp("192.168.1.1")).thenReturn(mockLocation);
        when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        String result = messageSender.send(headers);
        
        verify(geoService, times(1)).byIp("192.168.1.1");
        verify(localizationService, times(2)).locale(Country.USA);
        assertEquals("Welcome", result);
    }
}