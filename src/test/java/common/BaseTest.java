package common;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.Sidebar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.screenshot;
import static io.qameta.allure.Allure.addAttachment;

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

    @AfterMethod
    public void screenshotEvidence() {
        String tempShot = screenshot("temp_shot");

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(tempShot));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byte[] finalShot = byteArrayOutputStream.toByteArray();
            addAttachment("evidencia", new ByteArrayInputStream(finalShot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
