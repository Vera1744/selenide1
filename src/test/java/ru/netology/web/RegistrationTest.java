package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class RegistrationTest {


    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegister() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='name'] input").setValue("Чон Хэин");
        $("[data-test-id='phone'] input").setValue("+79198988878");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='agreement']").click();
        $$(".button").findBy(Condition.text("Забронировать")).click();
        $("[data-test-id='notification']").should(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification']").should(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id='notification']").should(Condition.text("Встреча успешно забронирована на"), Duration.ofSeconds(15));
        $("[data-test-id='notification']").should(Condition.text(planningDate), Duration.ofSeconds(15));

    }
}
