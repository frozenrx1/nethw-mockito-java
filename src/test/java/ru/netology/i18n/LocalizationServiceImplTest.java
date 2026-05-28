package ru.netology.i18n;

import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    private final LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

    @Test
    void testLocale_Russia_returnsRussianMessage() {
        String message = localizationService.locale(Country.RUSSIA);
        assertEquals("Добро пожаловать", message);
    }

    @Test
    void testLocale_USA_returnsEnglishMessage() {
        String message = localizationService.locale(Country.USA);
        assertEquals("Welcome", message);
    }

    @Test
    void testLocale_Germany_returnsEnglishMessage() {
        String message = localizationService.locale(Country.GERMANY);
        assertEquals("Welcome", message);
    }
}