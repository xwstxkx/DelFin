package org.duck.duckbackend.service;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CheckServiceTest {

//    @Test
//    public void testCheckFind() throws MalformedURLException {
//        // Создайте макеты для WebDriver и WebElement
//        WebDriver driver = mock(WebDriver.class);
//        WebElement element = mock(WebElement.class);
//
//        // Настройте макеты
//        when(driver.findElement(any())).thenReturn(element);
//        when(element.getText()).thenReturn("2024");
//
//        // Создайте экземпляр вашего класса с макетом WebDriver
//        CheckService checkService = new CheckService(driver);
//
//        // Вызовите метод checkFind
//        String result = checkService.checkFind("2024-12-31", "12345");
//
//        // Проверьте результат
//        assertEquals("Ошибка", result);
//
//        // Проверьте, что методы были вызваны на макетах
//        verify(driver, times(1)).get(anyString());
//        verify(element, times(1)).click();
//    }
}