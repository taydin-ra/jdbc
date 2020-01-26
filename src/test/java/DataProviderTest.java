import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.*;

public class DataProviderTest {
    private Statement statement;
    private Connection connection;

    @BeforeClass
    public void connect() throws SQLException {
        String url = "jdbc:mysql://database-techno.c771qxmldhez.us-east-2.rds.amazonaws.com:3306/ttorun_students_database";
        String user = "daulet2030";
        String password = "daulet2030@gmail.com";
        connection = DriverManager.getConnection( url, user, password );
        statement = connection.createStatement();
    }

    @AfterClass
    public void disconnect() throws SQLException {
        connection.close();
    }

    @DataProvider(name = "students")
    public Object[][] studentsData() throws SQLException {
        ResultSet resultSet = statement.executeQuery( "select " +
                "* " +
                "from students" );
        resultSet.last();
        int numberOfRow = resultSet.getRow();
        Object[][] resultData = new Object[numberOfRow][6];
        resultSet.beforeFirst();
        int i = 0;
        while(resultSet.next()) {
            String first_name = resultSet.getString( "first_name" );
            String last_name = resultSet.getString( "last_name" );
            String email = resultSet.getString( "email" );
            String gender = resultSet.getString( "gender" );
            String country = resultSet.getString( "country" );
            Double fee = resultSet.getDouble( "fee" );
            resultData[i][0] = first_name;
            resultData[i][1] = last_name;
            resultData[i][2] = email;
            resultData[i][3] = gender;
            resultData[i][4] = country;
            resultData[i][5] = fee;
            i++;
        }

        return resultData;
    }

    @Test(dataProvider = "students")
    public void test(String c1, String c2, String c3, String c4, String c5, Double c6){
        System.out.print(c1 + c2 + c3 + c4 + c5 + c6);

    }

}

