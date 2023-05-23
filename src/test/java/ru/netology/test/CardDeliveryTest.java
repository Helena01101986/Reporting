package ru.netology.test;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataGenerator.generateDate;

public class CardDeliveryTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestValidData() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        Configuration.holdBrowserOpen = true;
        $x("//input[@placeholder='Город']").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").setValue(Keys.CONTROL + "a" + Keys.DELETE);
        $("[data-test-id=date] input").setValue(generateDate(7));
        $x("//input[@name='name']").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.generatePhone("ru"));
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + generateDate(7)))
                .shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").setValue(Keys.CONTROL + "a" + Keys.DELETE);
        $("[data-test-id=date] input").setValue(generateDate(10));
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible, Duration.ofSeconds(15));
        $x("//span[contains(text(), 'Перепланировать')]").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + generateDate(10)))
                .shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    void shouldTestInvalidData() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        Configuration.holdBrowserOpen = true;
        $x("//input[@placeholder='Город']").setValue(DataGenerator.generateCity());
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").setValue(Keys.CONTROL + "a" + Keys.DELETE);
        $("[data-test-id=date] input").setValue(generateDate(7));
        $x("//input[@name='name']").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id=phone] input").setValue(DataGenerator.generateInvalidPhone("en"));
        $("[data-test-id=agreement]").click();
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldHave(text("Формат номера телефона введен неверно"))
                .shouldBe(visible);

         /*       .shouldHave(text("Встреча успешно запланирована на " + generateDate(7)))
                .shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").setValue(Keys.CONTROL + "a" + Keys.DELETE);
        $("[data-test-id=date] input").setValue(generateDate(10));
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible, Duration.ofSeconds(15));
        $x("//span[contains(text(), 'Перепланировать')]").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + generateDate(10)))
                .shouldBe(visible, Duration.ofSeconds(15));

    }*/

    }
}
