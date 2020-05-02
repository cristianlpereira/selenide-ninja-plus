package tests;

import common.BaseTest;
import libs.Database;
import models.MovieModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MovieTests extends BaseTest {
    @BeforeMethod
    public void login() {
        loginPage.open().with("papito@ninjaplus.com", "pwd123");
        sidebar.loggedUser().shouldHave(text("Papito"));
    }

    @Test
    public void shouldRegisterANewMovie() {
        MovieModel movieModel = new MovieModel(
                "Jumanji - Próxima Fase",
                "Pré-venda",
                2020,
                "16/01/2020",
                Arrays.asList("Dwayne Johnson", "Jack Black", "Kevin Hart", "Karen Gillan",
                        "Danny DeVito", "Awkwafina"),
                "Spencer volta ao mundo fantástico de Jumanji. Os amigos Martha, Fridge e Bethany entram " +
                        "no jogo e tentam trazê-lo para casa. Mas eles logo descobrem mais obstáculos e perigos a " +
                        "serem superados.",
                "jumanji_next_fase_cover.jpg"
        );

        Database database = new Database();
        database.deleteMovie(movieModel.getTitle());

        moviePage.add().create(movieModel).items().findBy(text(movieModel.title)).shouldBe(visible);
    }
}
