package tests;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.Sidebar;

import static com.codeborne.selenide.Condition.text;

public class LoginTests {
    protected static LoginPage loginPage;
    protected static Sidebar sidebar;

    @DataProvider(name = "login-alerts")
    public Object[][] loginProvider() {
        return new Object[][]{
                {"papito@ninjaplus.com", "abc123", "Usuário e/ou senha inválidos"},
                {"404@ninjaplus.com", "pwd123", "Usuário e/ou senha inválidos"},
                {"", "pwd123", "Opps. Cadê o email?"},
                {"papito@ninjaplus.com", "", "Opps. Cadê a senha?"}
        };
    }

    @BeforeMethod
    public void start() {
        Configuration.browser = "firefox";
        Configuration.baseUrl = "http://ninjaplus-web:5000";

        loginPage = new LoginPage();
        sidebar = new Sidebar();
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
