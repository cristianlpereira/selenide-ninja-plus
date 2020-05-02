package common;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.Sidebar;

public class BaseTest {
    protected static LoginPage loginPage;
    protected static Sidebar sidebar;
    protected static MoviePage moviePage;

    @BeforeMethod
    public void setup() {
        Configuration.browser = "firefox";
        Configuration.baseUrl = "http://ninjaplus-web:5000";
        Configuration.timeout = 30000;

        loginPage = new LoginPage();
        sidebar = new Sidebar();
        moviePage = new MoviePage();
    }
}
