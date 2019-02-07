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
        String url = "jdbc:mysql://localhost:3306/user";
        String user = "root";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,"");

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
