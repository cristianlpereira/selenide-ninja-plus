package common;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.MoviePage;
import pages.Sidebar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.screenshot;
import static io.qameta.allure.Allure.addAttachment;

public class BaseTest {
    protected static LoginPage loginPage;
    protected static Sidebar sidebar;
    protected static MoviePage moviePage;

    @BeforeMethod
    public void setup() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration.browser = properties.getProperty("browser");
        Configuration.baseUrl = properties.getProperty("url");
        Configuration.timeout = Long.parseLong(properties.getProperty("timeout"));

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
