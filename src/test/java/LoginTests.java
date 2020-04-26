import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.isFirefox;

public class LoginTests {
    @DataProvider(name = "login-alerts")
    public Object[][] loginProvider() {
        return new Object[][]{
                {"papito@ninjaplus.com", "abc123", "Usuário e/ou senha inválidos"},
                {"404@ninjaplus.com", "pwd123", "Usuário e/ou senha inválidos"},
                {"", "pwd123", "Opps. Cadê o email?"},
                {"papito@ninjaplus.com", "", "Opps. Cadê a senha?"}
        };
    }

    @Test
    public void shouldSeeLoggedUser() {
        isFirefox();
        open("http://ninjaplus-web:5000");

        $("#emailId").setValue("papito@ninjaplus.com");
        $("#passId").setValue("pwd123");
        $(byText("Entrar")).click();

        $(".user .info span").shouldHave(text("Papito"));
    }

    @Test(dataProvider = "login-alerts")
    public void shouldSeeLoginAlerts(String email, String pass, String expectAlert) {
        isFirefox();
        executeJavaScript("localStorage.clear()");
        open("http://ninjaplus-web:5000");

        $("#emailId").setValue(email);
        $("#passId").setValue(pass);
        $(byText("Entrar")).click();

        $(".alert span").shouldHave(text(expectAlert));
    }
}
