import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbWorker {
    private Connection connection;

    public DbWorker() {
        connectToDb();
    }

    private void connectToDb(){
        String url = "jdbc:postgresql://localhost:5432/dbunittut";
        String user = "postgres";
        String password = "root";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,user,password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void workWithDataWhithoutAnswer(String command){
        try(Statement statement = connection.createStatement()){
            statement.execute("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String workWhithDataWithAnswer(String command){
        return "";
    }
}
