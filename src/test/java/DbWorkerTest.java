import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.*;

public class DbWorkerTest extends DBTestCase {
    private DbWorker worker;

    public DbWorkerTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,"jdbc:mysql://localhost:3306/user");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,"root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,"");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("user.xml"));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {

        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    @Test
    public void workWithDataWhithoutAnswer() {
    }

    @Test
    public void workWhithDataWithAnswer() {
    }

}