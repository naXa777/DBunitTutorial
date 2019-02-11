import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;

import static org.junit.Assert.*;

public class DbWorkerTest extends DBTestCase {
    private DbWorker worker;

    public DbWorkerTest(String name) {
        //прописываем проперти для работы с ДБ
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_DRIVER_CLASS,"org.postgresql.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_CONNECTION_URL,"jdbc:postgresql://localhost:5432/dbunittut");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_USERNAME,"postgres");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.
                DBUNIT_PASSWORD,"root");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        //делаем таблицу в ХМЛ
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src\\test\\resourses\\user.xml"));
    }

    @Override
    //метод перед каждым тестом
    protected DatabaseOperation getSetUpOperation() throws Exception {


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
        return DatabaseOperation.CLEAN_INSERT;


        //Эта операция буквально обновляет содержимое набора данных в целевой базе данных.
        // Это означает, что данные существующих строк обновляются, а несуществующие строки вставляются.
        // Любые строки, которые существуют в базе данных, но отсутствуют в наборе данных, остаются неизменными.
        // Этот подход больше подходит для тестов, которые предполагают, что в базе данных могут существовать другие данные.
        //если они написаны правильно, тесты, использующие эту стратегию,
        // могут даже выполняться на заполненной базе данных, например на копии рабочей базы данных.
        //return DatabaseOperation.REFRESH;
    }

    @Override
    //метод после теста
    protected DatabaseOperation getTearDownOperation() throws Exception {
        //Операция, не делающая абсолютно ничего
        return DatabaseOperation.NONE;
    }


    public void testworkWithDataWhithoutAnswer() {
    }



}