package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Sidebar {
    public SelenideElement loggedUser() {
        return $(".user .info span");
    }
}
