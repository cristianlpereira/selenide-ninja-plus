package libs;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.nio.file.Files.readAllBytes;

public class Database {
    private final String url = "jdbc:postgresql://pgdb/ninjaplus";
    private final String user = "postgres";
    private final String pass = "qaninja";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public void resetMovies() {
        String executionPath = System.getProperty("user.dir");
        String os = System.getProperty("os.name");
        String target;
        String moviesSql;

        if (os.contains("Windows")) {
            target = executionPath + "\\src\\main\\resources\\sql\\movies.sql";
        } else {
            target = executionPath + "/src/main/resources/sql/movies.sql";
        }

        try {
            moviesSql = new String(readAllBytes(Paths.get(target)));
            PreparedStatement preparedStatement = this.connect().prepareStatement(moviesSql);
            preparedStatement.executeQuery();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
