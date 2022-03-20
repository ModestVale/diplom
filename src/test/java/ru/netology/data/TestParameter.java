package ru.netology.data;

import lombok.Data;

@Data
public class TestParameter {
    private String testUrl;
    private String testDbUrl;

    public  TestParameter(String testUrl, String testDbUrl)
    {
        this.testUrl = testUrl;
        this.testDbUrl = testDbUrl;
    }
}
