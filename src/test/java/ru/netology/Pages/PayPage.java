package ru.netology.Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.TestData;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PayPage {
    private SelenideElement cardNumberInput = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement cardMonthInput = $("[placeholder='08']");
    private SelenideElement cardYearInput = $("[placeholder='22']");
    private SelenideElement cardCvvInput = $("[placeholder='999']");
    private SelenideElement cardHolderInput = $$("[class='input__control']").get(3);

    private ElementsCollection notifications = $$(".notification");
    private SelenideElement singleNotifications = $(".notification");
    private SelenideElement nextButton = $$(".button__content").find(exactText("Продолжить"));

    public PayPage() {
        cardNumberInput.shouldBe(visible);
        cardMonthInput.shouldBe(visible);
        cardYearInput.shouldBe(visible);
        cardCvvInput.shouldBe(visible);
        cardHolderInput.shouldBe(visible);
        nextButton.shouldBe(visible);
    }

    public void fillFields(TestData testData) {
        cardNumberInput.setValue(testData.getCardNumber());
        cardHolderInput.setValue(testData.getCardHolder());
        cardMonthInput.setValue(testData.getCardMonth());
        cardYearInput.setValue(testData.getCardYear());
        cardCvvInput.setValue(testData.getCardCvv());
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void validateSuccessNotification() {
        notifications
                .filter(text("Успешно\nОперация одобрена Банком."))
                .first()
                .shouldBe(visible, Duration.ofSeconds(30));
        notifications
                .filter(visible)
                .shouldBe(size(1));
    }

    public void validateErrorNotification() {
        notifications
                .filter(text("Ошибка\nОшибка! Банк отказал в проведении операции."))
                .first()
                .shouldBe(visible, Duration.ofSeconds(30));
    }

    public void checkMessageRequiredField(String message) {
        $$(".input__sub")
                .filter(exactText(message))
                .filter(visible)
                .shouldBe(size(1));
    }
}
