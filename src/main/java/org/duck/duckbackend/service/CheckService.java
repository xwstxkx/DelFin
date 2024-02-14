package org.duck.duckbackend.service;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CheckService {

//    public String checkFind(String date, String uid) throws MalformedURLException {
////        System.setProperty("webdriver.chrome.driver", "D:\\Files\\Chrome Driver\\chromedriver.exe");
////        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
//        //remote (localhost:4444/wb/hub)
//        DesiredCapabilities cap = new DesiredCapabilities();
//        URL url = new URL("http://localhost:4444/wd/hub");
//        WebDriver driver = new RemoteWebDriver(url, cap);
//        String year = yearManage(date);
//        String month = monthManage(date);
//        String day = dayManage(date);
//        String check;
//        boolean bool = true;
//        //Сайт с которым будем работать
//        driver.get("https://ch.info-center.by/");
//
//        //Начало ввода даты/открытие окна с вводом
//        WebElement dateElement = driver.findElement(By
//                .xpath("//input[@placeholder='Выберите дату']"));
//        dateElement.click();
//
//        //Переключатель
//        WebElement switchLeft = driver.findElement(By
//                .xpath("//div[@class='air-datepicker-nav--action']"));
//
//        //Открытие окна с датой
//        WebElement dateClick = driver.findElement(By
//                .xpath("//div[@class='air-datepicker-nav--title']"));
//        dateClick.click();
//
//        //Поиск подходящего года
//        while (bool) {
//            WebElement yearFind = driver.findElement(By
//                    .xpath("//div[@class='air-datepicker-nav--title']"));
//            if (!yearFind.getText().equalsIgnoreCase(year)) {
//                switchLeft.click();
//            } else {
//                bool = false;
//            }
//        }
//
//        //Поиск подходящего месяца
//        WebElement monthFind = driver.findElement(By
//                .xpath("//div[@class='air-datepicker-body--cells -months-']" +
//                       "//div[@data-month='" + month + "']"));
//        monthFind.click();
//
//        //Поиск подходящей даты
//        WebElement dayFind = driver.findElement(By
//                .xpath("//div[@class='air-datepicker-body--cells -days-']" +
//                       "//div[@data-date='" + day + "']"));
//        dayFind.click();
//
//        //Ввод Уникального идентификатора
//        WebElement uidElement = driver.findElement(By
//                .xpath("//div[@class='qr-inp']//input[@class='_required']"));
//        uidElement.sendKeys(uid);
//
//        //Кнопка "Проверить чек"
//        WebElement submitButton = (new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(ExpectedConditions.elementToBeClickable(By
//                        .xpath("(//button[@form='check1'])[1]"))));
//        System.out.println(submitButton);
//        submitButton.click();
//
//        //Вычесление итоговой суммы
//        WebElement checkElement = (new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(ExpectedConditions.elementToBeClickable(By
//                        .xpath("//div[@class='check-full__total-main']//span[2]"))));
//        check = checkElement.getText();
//
//        return check;
//    }

    public String checkFind() throws MalformedURLException {
//        System.setProperty("webdriver.chrome.driver", "D:\\Files\\Chrome Driver\\chromedriver.exe");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName("chrome");
        cap.setPlatform(Platform.LINUX);
        URL url = new URL("http://chrome:4444/wd/hub");
        WebDriver driver = new RemoteWebDriver(url, cap);
//        WebDriver driver = new ChromeDriver();
        driver.get("https://www.facebook.com/");
        String check = driver.getTitle();

        driver.close();
        driver.quit();

        return check;
    }

    public static String yearManage(String date) {
        String[] dates = date.split("\\.");

        return dates[2];
    }

    public static String monthManage(String date) {
        String[] dates = date.split("\\.");
        int monthInt;
        String month;

        monthInt = Integer.parseInt(dates[1]) - 1;
        month = String.valueOf(monthInt);

        return month;
    }

    public static String dayManage(String date) {
        String[] dates = date.split("\\.");
        String day;
        int dayInt;

        dayInt = Integer.parseInt(dates[0]);
        day = String.valueOf(dayInt);

        return day;
    }
}