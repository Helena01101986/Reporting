package ru.netology.test;


import com.codeborne.selenide.Configuration;
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
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").setValue(Keys.CONTROL + "a" + Keys.DELETE);
        $("[data-test-id=date] input").setValue(generateDate(10));
        $x("//span[contains(text(), 'Запланировать')]").click();
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible, Duration.ofSeconds(15));
        $x("//span[contains(text(), 'Перепланировать')]").click();
        $x("//div[contains(text(), 'Встреча успешно запланирована')]")
                .shouldBe(visible, Duration.ofSeconds(15));

    }

}
