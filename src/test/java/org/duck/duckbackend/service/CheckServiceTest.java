package org.duck.duckbackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckServiceTest {
    private String date;

    @BeforeEach
    public void init() {
        date = "30.11.2023";
    }

    @Test
    void checkFind() {
    }

    @Test
    void CheckServiceTests_yearManage_returnsString() {
        String year = CheckService.yearManage(date);
        Assertions.assertEquals(year, "2023");
    }

    @Test
    void CheckServiceTests_monthManage_returnsString() {
        String month = CheckService.monthManage(date);
        Assertions.assertEquals(month, "10");
    }

    @Test
    void CheckServiceTests_dayManage_returnsString() {
        String day = CheckService.dayManage(date);
        Assertions.assertEquals(day, "30");
    }
}