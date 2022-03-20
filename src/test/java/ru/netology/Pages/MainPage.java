package ru.netology.Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    private SelenideElement head = $("[class='heading heading_size_l heading_theme_alfa-on-white']");
    private SelenideElement payButton = $(".button_theme_alfa-on-white");
    private SelenideElement payCreditButton = $(".button_view_extra");

    public MainPage() {
        head.shouldBe(visible);
        payButton.shouldBe(visible);

        head.shouldHave(text("Путешествие дня"));
        payButton.shouldHave(text("Купить"));
        payCreditButton.shouldHave(text("Купить в кредит"));
    }

    public PayPage pay() {
        payButton.click();
        return page(PayPage.class);
    }

    public CreditPayPage payByCredit() {
        payCreditButton.click();
        return page(CreditPayPage.class);
    }
}
