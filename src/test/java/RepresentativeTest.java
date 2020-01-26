import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.*;

public class RepresentativeTest {
    private Statement statement;
    private Connection connection;

    @BeforeClass
    public void connect() throws SQLException {
        String url = "jdbc:mysql://database-techno.c771qxmldhez.us-east-2.rds.amazonaws.com:3306/ttorun16_students_database";
        String user = "ttorun16";
        String password = "ttorun16@gmail.com";
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    @AfterClass
    public void disconnect() throws SQLException {
        connection.close();
    }

    @DataProvider(name = "representative")
    public Object[][] data() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from Represantatives1");
        resultSet.last();
        int rowNumbers = resultSet.getRow();
        resultSet.beforeFirst();
        int i = 0;
        Object[][] result = new Object[rowNumbers][4];
        while (resultSet.next()) {
            String name = resultSet.getString("first_name");
            String lastname = resultSet.getString("last_name");
            String phone = resultSet.getString("phone");
            String country = resultSet.getString("country");

            result[i][0] = name;
            result[i][1] = lastname;
            result[i][2] = country;
            result[i][3] = phone;
            i++;
        }
        return result;
    }

    @Test(dataProvider = "representative")
    public void test(String c1, String c2, String c3, String c4) {
        System.out.print(c1 + c2 + c3 + c4);
    }
}

