import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DbWorkerTest extends DBTestCase {
    private DbWorker worker;
    private Properties properties;


    public DbWorkerTest(String name) {
        //прописываем проперти для работы с ДБ
        super(name);
        properties = new Properties();
        try(FileInputStream fis = new FileInputStream("src\\test\\resourses\\db.config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_DRIVER_CLASS,properties.getProperty("db.driver"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_CONNECTION_URL,properties.getProperty("db.url"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_USERNAME,properties.getProperty("db.user"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_PASSWORD,properties.getProperty("db.password"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                        DBUNIT_SCHEMA, properties.getProperty("db.schema"));
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        //делаем таблицу в ХМЛ
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src\\test\\resourses\\user.xml"));
    }

    @Override
    //метод перед каждым тестом
    protected DatabaseOperation getSetUpOperation() throws Exception {
        worker = new DbWorker();

        //Эта операция вставляет содержимое набора данных в базу данных.
        // Эта операция предполагает, что данные таблицы не существуют в целевой базе данных, и завершается ошибкой, если это не так.
        // Чтобы избежать проблем с внешними ключами, таблицы должны быть соответствующим образом упорядочены в наборе данных.
        //DatabaseOperation.INSERT;

        //Эта операция обновляет базу данных из содержимого набора данных.
        // Эта операция предполагает, что данные таблицы уже существуют в целевой базе данных, и завершается ошибкой, если это не так.
        //DatabaseOperation.UPDATE;

        //Эта операция удаляет только содержимое набора данных из базы данных.
        // Эта операция не удаляет все содержимое таблицы, а только данные, которые присутствуют в наборе данных.
        //DatabaseOperation.DELETE;

        //Удаляет все строки таблиц, присутствующих в указанном наборе данных.
        // Если набор данных не содержит конкретной таблицы, но эта таблица существует в базе данных, на таблицу базы данных это не влияет.
        // Таблица усекается в обратной последовательности.
        //DatabaseOperation.DELETE_ALL;

        //Эта составная операция выполняет операцию DELETE_ALL, за которой следует операция INSERT.
        // Это самый безопасный подход для обеспечения того, чтобы база данных находилась в известном состоянии.
        // Это подходит для тестов, которые требуют, чтобы база данных содержала только определенный набор данных.
        //return DatabaseOperation.CLEAN_INSERT;


        //Эта операция буквально обновляет содержимое набора данных в целевой базе данных.
        // Это означает, что данные существующих строк обновляются, а несуществующие строки вставляются.
        // Любые строки, которые существуют в базе данных, но отсутствуют в наборе данных, остаются неизменными.
        // Этот подход больше подходит для тестов, которые предполагают, что в базе данных могут существовать другие данные.
        //если они написаны правильно, тесты, использующие эту стратегию,
        // могут даже выполняться на заполненной базе данных, например на копии рабочей базы данных.
        return DatabaseOperation.REFRESH;
    }

    @Override
    //метод после теста
    protected DatabaseOperation getTearDownOperation() throws Exception {
        //Операция, не делающая абсолютно ничего
        return DatabaseOperation.DELETE_ALL;
    }


    public void testworkWithDataWhithoutAnswer() throws Exception {

        String sqlRequest= "INSERT INTO public.user (firstname, lastname) VALUES('bob','marley');";
        worker.workWithDataWhithoutAnswer(sqlRequest);

        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("user");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
                new FileInputStream("src\\test\\resourses\\expUser.xml"));
        ITable expectedTable = expectedDataSet.getTable("user");
        ITable filteredTable = DefaultColumnFilter.includedColumnsTable(
                actualTable,
                expectedTable.getTableMetaData().getColumns());
        Assertion.assertEquals(expectedTable,filteredTable);

    }

}