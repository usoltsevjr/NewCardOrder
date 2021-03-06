import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {

    @BeforeEach
    void SetUpAll(){
        open("http://localhost:9999");
    }

    @Test
    void shouldSendCorrectForm() {
        $("[data-test-id=name] input").setValue("Антон Антонов");
        $("[data-test-id=phone] input").setValue("+79217895546");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitIfNameIsIncorrect() {
        $("[data-test-id=name] input").setValue("Anton Antonov");
        $("[data-test-id=phone] input").setValue("+79217895546");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitIfPhoneIsIncorrect() {
        $("[data-test-id=name] input").setValue("Антон Антонов");
        $("[data-test-id=phone] input").setValue("+792178955460");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $$("[data-test-id=phone].input_invalid .input__sub").last().shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSubmitAgreement() {
        $("[data-test-id=name] input").setValue("Антон Антонов");
        $("[data-test-id=phone] input").setValue("+79217895546");
        $("[type=button]").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки"));
    }

    @Test
    void shouldNotSubmitWithoutData() {
        $("[data-test-id=agreement]").click();
        $("[type='button']").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
