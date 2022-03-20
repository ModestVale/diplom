package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TestDataService {
    private static Faker faker = new Faker(new Locale("ru"));
    private static Faker fakerEn = new Faker(new Locale("en"));

    public static TestData GetValidTestData() {
        val output = new TestData();
        output.setCardCvv(faker.number().digits(3));
        output.setCardHolder(fakerEn.name().fullName());
        output.setCardYear(LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy")));
        output.setCardMonth("01");
        output.setCardNumber("4444 4444 4444 4441");

        return output;
    }

    public static TestData GetTestDataWithDeclinedCard() {

        val output = new TestData();
        output.setCardCvv(faker.number().digits(3));
        output.setCardHolder(fakerEn.name().fullName());
        output.setCardYear(LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy")));
        output.setCardMonth("01");
        output.setCardNumber("4444 4444 4444 4442");

        return output;
    }
    public static TestData GetTestDataWithRandomCard() {

        val output = new TestData();
        output.setCardCvv(faker.number().digits(3));
        output.setCardHolder(fakerEn.name().fullName());
        output.setCardYear(LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy")));
        output.setCardMonth("01");
        output.setCardNumber(fakerEn.business().creditCardNumber());

        return output;
    }

    public static TestData GetTestDataWithEmptyValue(TestDataFieldEnum emptyDataField) {

        val output = new TestData();

        if(emptyDataField != TestDataFieldEnum.CardCvv) {
            output.setCardCvv(faker.number().digits(3));
        }

        if(emptyDataField != TestDataFieldEnum.CardHolder) {
            output.setCardHolder(fakerEn.name().fullName());
        }

        if(emptyDataField != TestDataFieldEnum.CardYear) {
            output.setCardYear(LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy")));
        }

        if(emptyDataField != TestDataFieldEnum.CardMont) {
            output.setCardMonth("01");
        }

        if(emptyDataField != TestDataFieldEnum.CardNumber) {
            output.setCardNumber(fakerEn.business().creditCardNumber());
        }

        return output;
    }

    public enum TestDataFieldEnum{
        CardNumber,
        CardMont,
        CardYear,
        CardHolder,
        CardCvv
    }

}
