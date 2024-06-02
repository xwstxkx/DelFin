package org.xwstxkx.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xwstxkx.entity.BudgetEntity;
import org.xwstxkx.entity.UserEntity;
import org.xwstxkx.exceptions.BadCredentials;
import org.xwstxkx.exceptions.ObjectNotFound;
import org.xwstxkx.model.CheckModel;
import org.xwstxkx.model.ExpenseModel;
import org.xwstxkx.repository.BudgetRepository;
import org.xwstxkx.service.crud.ExpensesCRUDService;
import org.xwstxkx.service.security.UserService;
import org.xwstxkx.util.CategoryType;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Service
@Slf4j
public class CheckService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ExpensesCRUDService expensesCRUDService;

    @Value("${selenium.webSiteUrl}")
    private String webSiteUrl;
    @Value("${selenium.dateUrl}")
    private String dateUrl;
    @Value("${selenium.switchUrl}")
    private String switchUrl;
    @Value("${selenium.dateClickUrl}")
    private String dateClickUrl;
    @Value("${selenium.yearUrl}")
    private String yearUrl;
    @Value("${selenium.uidUrl}")
    private String uidUrl;
    @Value("${selenium.buttonUrl}")
    private String buttonUrl;
    @Value("${selenium.checkUrl}")
    private String checkUrl;
    @Value("${selenium.browserName}")
    private String browserName;
    @Value("${selenium.hubUrl}")
    private String hubUrl;

    @Transactional
    public Long checkTransaction(CheckModel checkModel, Long category_id) throws MalformedURLException, ObjectNotFound, BadCredentials {
        String check = checkFind(String.valueOf(checkModel.getDate()), checkModel.getUid());
        if (check != null) {
            log.info("Чек найден");
        }
        String filteredCheck = check.replace(".", "");
        Long checkSum = Long.parseLong(filteredCheck);
        UserEntity userEntity = userService.getCurrentUser();
        BudgetEntity budgetEntity = budgetRepository.findByTitleAndUser(checkModel.getBudgetTitle(), userEntity);
        log.info("Бюджет был найден");
        ExpenseModel expenseModel = ExpenseModel
                .builder()
                .amount(checkSum)
                .date(checkModel.getDate())
                .category_id(category_id)
                .type(CategoryType.valueOf("expense"))
                .description(checkModel.getUid())
                .budget_id(budgetEntity.getId())
                .build();
        expensesCRUDService.saveExpense(expenseModel, category_id);
        log.info("Транзакция была сохранена");

        return checkSum;
    }

    public String checkFind(String date, String uid) throws MalformedURLException {

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(browserName);
        cap.setPlatform(Platform.LINUX);
        URL url = new URL(hubUrl);
        WebDriver driver = new RemoteWebDriver(url, cap);

        String year = yearManage(date);
        String month = monthManage(date);
        String day = dayManage(date);
        String check;
        boolean bool = true;
        //Сайт с которым будем работать
        driver.get(webSiteUrl);
        log.info("Драйвер подключился в сайту");

        //Начало ввода даты/открытие окна с вводом
        WebElement dateElement = driver.findElement(By
                .xpath(dateUrl));
        dateElement.click();

        //Переключатель
        WebElement switchLeft = driver.findElement(By
                .xpath(switchUrl));

        //Открытие окна с датой
        WebElement dateClick = driver.findElement(By
                .xpath(dateClickUrl));
        dateClick.click();

        //Поиск подходящего года
        while (bool) {
            WebElement yearFind = driver.findElement(By
                    .xpath(yearUrl));
            if (!yearFind.getText().equalsIgnoreCase(year)) {
                switchLeft.click();
            } else {
                bool = false;
            }
        }

        //Поиск подходящего месяца
        WebElement monthFind = driver.findElement(By
                .xpath("//div[@class='air-datepicker-body--cells -months-']" +
                        "//div[@data-month='" + month + "']"));
        monthFind.click();

        //Поиск подходящей даты
        WebElement dayFind = driver.findElement(By
                .xpath("//div[@class='air-datepicker-body--cells -days-']" +
                        "//div[@data-date='" + day + "']"));
        dayFind.click();

        //Ввод Уникального идентификатора
        WebElement uidElement = driver.findElement(By
                .xpath(uidUrl));
        uidElement.sendKeys(uid);

        //Кнопка "Проверить чек"
        WebElement submitButton = (new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By
                        .xpath(buttonUrl))));
        System.out.println(submitButton);
        submitButton.click();


        //Вычесление итоговой суммы
        WebElement checkElement = (new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By
                        .xpath(checkUrl))));
        check = checkElement.getText();
        log.info("Драйвер закончил работу");

        //!!!
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
