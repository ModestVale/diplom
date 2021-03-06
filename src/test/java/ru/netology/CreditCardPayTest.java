package ru.netology;

import jdk.jfr.Description;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.Pages.MainPage;
import ru.netology.Utils.DbUtils;
import ru.netology.data.TestDataService;
import ru.netology.data.TestParameter;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardPayTest {

    private static Stream<TestParameter> getTestData() {
        return Stream.of(
                new TestParameter("http://localhost:8081", "jdbc:postgresql://localhost:5432/app"),
                new TestParameter("http://localhost:8080", "jdbc:mysql://localhost:3306/app")
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("getTestData")
    @Description("Оплата проходит, если карта в статусе 'APPROVED'")
    public void shouldPayIfCardApproved(TestParameter testParameter) {
        DbUtils.clearTables(testParameter.getTestDbUrl());
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.pay();
        val validTestData = TestDataService.GetValidTestData();
        payPage.fillFields(validTestData);
        payPage.clickNextButton();
        payPage.validateSuccessNotification();
        TimeUnit.SECONDS.sleep(2);
        val actualPaymentStatus = DbUtils
                .getPaymentStatus(testParameter.getTestDbUrl())
                .getPaymentStatus();
        assertEquals("APPROVED", actualPaymentStatus);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldPayCreditIfCardApproved(TestParameter testParameter) {
        DbUtils.clearTables(testParameter.getTestDbUrl());
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val validTestData = TestDataService.GetValidTestData();
        payPage.fillFields(validTestData);
        payPage.clickNextButton();
        payPage.validateSuccessNotification();
        TimeUnit.SECONDS.sleep(2);
        val actualPaymentStatus = DbUtils
                .getPaymentStatus(testParameter.getTestDbUrl())
                .getCreditStatus();
        assertEquals("APPROVED", actualPaymentStatus);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldNoPayIfCardNotApproved(TestParameter testParameter) {
        DbUtils.clearTables(testParameter.getTestDbUrl());
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.pay();
        val validTestData = TestDataService.GetTestDataWithDeclinedCard();
        payPage.fillFields(validTestData);
        payPage.clickNextButton();
        payPage.validateErrorNotification();
        TimeUnit.SECONDS.sleep(2);
        val actualPaymentStatus = DbUtils
                .getPaymentStatus(testParameter.getTestDbUrl())
                .getPaymentStatus();
        assertEquals("DECLINED", actualPaymentStatus);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldNoPayCreditIfCardNotApproved(TestParameter testParameter) {
        DbUtils.clearTables(testParameter.getTestDbUrl());
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val validTestData = TestDataService.GetTestDataWithDeclinedCard();
        payPage.fillFields(validTestData);
        payPage.clickNextButton();
        payPage.validateErrorNotification();
        TimeUnit.SECONDS.sleep(2);
        val actualPaymentStatus = DbUtils
                .getPaymentStatus(testParameter.getTestDbUrl())
                .getPaymentStatus();
        assertEquals("DECLINED", actualPaymentStatus);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldNoPayIfCardRandomly(TestParameter testParameter) {
        DbUtils.clearTables(testParameter.getTestDbUrl());
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.pay();
        val validTestData = TestDataService.GetTestDataWithRandomCard();
        payPage.fillFields(validTestData);
        payPage.clickNextButton();
        payPage.validateErrorNotification();
        TimeUnit.SECONDS.sleep(2);
        val actualPaymentStatus = DbUtils
                .getPaymentStatus(testParameter.getTestDbUrl())
                .getPaymentStatus();
        assertEquals("DECLINED", actualPaymentStatus);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldNoPayCreditIfCardRandomly(TestParameter testParameter) {
        DbUtils.clearTables(testParameter.getTestDbUrl());
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val validTestData = TestDataService.GetTestDataWithRandomCard();
        payPage.fillFields(validTestData);
        payPage.clickNextButton();
        payPage.validateErrorNotification();
        TimeUnit.SECONDS.sleep(2);
        val actualPaymentStatus = DbUtils
                .getPaymentStatus(testParameter.getTestDbUrl())
                .getPaymentStatus();
        assertEquals("DECLINED", actualPaymentStatus);
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldShowValidationMessageIfCardNumberEmpty(TestParameter testParameter) {
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val testData = TestDataService.GetTestDataWithEmptyValue(TestDataService.TestDataFieldEnum.CardNumber);
        payPage.fillFields(testData);
        payPage.clickNextButton();
        payPage.checkMessageRequiredField("Неверный формат");
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldShowValidationMessageIfCardCvvEmpty(TestParameter testParameter) {
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val testData = TestDataService.GetTestDataWithEmptyValue(TestDataService.TestDataFieldEnum.CardCvv);
        payPage.fillFields(testData);
        payPage.clickNextButton();
        payPage.checkMessageRequiredField("Неверный формат");
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldShowValidationMessageIfCardMonthEmpty(TestParameter testParameter) {
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val testData = TestDataService.GetTestDataWithEmptyValue(TestDataService.TestDataFieldEnum.CardMont);
        payPage.fillFields(testData);
        payPage.clickNextButton();
        payPage.checkMessageRequiredField("Неверный формат");
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldShowValidationMessageIfCardYearEmpty(TestParameter testParameter) {
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val testData = TestDataService.GetTestDataWithEmptyValue(TestDataService.TestDataFieldEnum.CardYear);
        payPage.fillFields(testData);
        payPage.clickNextButton();
        payPage.checkMessageRequiredField("Неверный формат");
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    public void shouldShowValidationMessageIfCardHolderEmpty(TestParameter testParameter) {
        val mainPage = open(testParameter.getTestUrl(), MainPage.class);
        val payPage = mainPage.payByCredit();
        val testData = TestDataService.GetTestDataWithEmptyValue(TestDataService.TestDataFieldEnum.CardHolder);
        payPage.fillFields(testData);
        payPage.clickNextButton();
        payPage.checkMessageRequiredField("Поле обязательно для заполнения");
    }
}
