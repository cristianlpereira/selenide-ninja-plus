package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import models.MovieModel;
import org.openqa.selenium.Keys;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class MoviePage {
    public MoviePage add() {
        $(".movie-add").click();

        return this;
    }

    public MoviePage create(MovieModel movieModel) {
        $("input[name=title]").setValue(movieModel.getTitle());
        this.selectStatus(movieModel.getStatus());
        $("input[name=year]").setValue(movieModel.getYear().toString());
        $("input[name=release_date]").setValue(movieModel.getReleaseDate());
        this.inputCast(movieModel.getCast());
        $("textarea[name=overview]").setValue(movieModel.getPlot());
        this.upload(movieModel.getCover());
        $("#create-movie").click();

        return this;
    }

    public void selectStatus(String status) {
        $("input[placeholder=Status]").click();
        $$("ul li span").findBy(text(status)).click();
    }

    private void inputCast(List<String> cast) {
        SelenideElement selenideElement = $(".cast");
        for (String actor : cast) {
            selenideElement.setValue(actor);
            selenideElement.sendKeys(Keys.TAB);
        }
    }

    public void upload(File cover) {
        String jsScript = "document.getElementById('upcover').classList.remove('el-upload__input');";
        executeJavaScript(jsScript);
        $("#upcover").uploadFile(cover);
    }

    public ElementsCollection items() {
        return $$("table tbody tr");
    }
}
