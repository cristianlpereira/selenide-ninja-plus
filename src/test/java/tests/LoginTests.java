package tests;

import common.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

public class LoginTests extends BaseTest {
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
        loginPage.open().with("papito@ninjaplus.com", "pwd123");
        sidebar.loggedUser().shouldHave(text("Papito"));
    }

    @Test(dataProvider = "login-alerts")
    public void shouldSeeLoginAlerts(String email, String pass, String expectAlert) {
        loginPage.open().with(email, pass).alert().shouldHave(text(expectAlert));
    }

    @AfterMethod
    public void cleanup() {
        loginPage.clearSession();
    }
}
