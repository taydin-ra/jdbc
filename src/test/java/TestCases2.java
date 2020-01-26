import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

public class TestCases2 {

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


    @Test

    public void test() throws SQLException {

        ResultSet rs = statement.executeQuery("Select concat(first_name,' ', last_name)As name,country,city,postal_code FROM Students limit 10;");

        while (rs.next()) {
            String name = rs.getString(1);
            String country = rs.getString(2);
            String city = rs.getString(3);
            String postal_code = rs.getString(4);
            System.out.println(name + " " + " " + country + " " + city + " " + postal_code);
        }
    }

    @Test

    public void test2() throws SQLException {

        ResultSet rs = statement.executeQuery("Select * FROM Students order by fee Desc limit 20;");

        while (rs.next()) {
            String name = rs.getString("first_name");
            String fee = rs.getString("fee");
            String email = rs.getString("email");
            String currency = rs.getString("currency");
            System.out.println("name: " + name + " " + "fee: " + fee + " " + "email: " + email + " " + "currency: " + currency);
        }
    }

    @Test

    public void test3() throws SQLException {

        ResultSet rs = statement.executeQuery("Select * FROM Students order by fee Desc limit 20;");


        rs.absolute(5);

        System.out.println("fullname: " + rs.getString("first_name") + " " + rs.getString("last_name") + ",fee:" + rs.getString("fee")
                + " " + rs.getString("currency"));
        rs.relative(3);
        System.out.println("fullname: " + rs.getString("first_name") + " " + rs.getString("last_name") + ",fee:" + rs.getString("fee")
                + " " + rs.getString("currency"));
        rs.first();
        System.out.println("fullname: " + rs.getString("first_name") + " " + rs.getString("last_name") + ",fee:" + rs.getString("fee")
                + " " + rs.getString("currency"));
        rs.last();
        System.out.println("fullname: " + rs.getString("first_name") + " " + rs.getString("last_name") + ",fee:" + rs.getString("fee")
                + " " + rs.getString("currency"));


    }

    @Test

    public void test4() throws SQLException {

        ResultSet rs = statement.executeQuery("Select avg(fee),currency,country from Students group by currency,country;");

        while (rs.next()) {
            Double averageFee = rs.getDouble("avg(fee)");
            String country = rs.getString("country");
            String currency = rs.getString("currency");

            System.out.println("averageFee:" + (averageFee * 1.17) + " " + "country: " + country + " " + "currency: " + currency);
        }


    }

    @Test

    public void test5() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Students SET fee = (fee +?) WHERE gender = ? and country=?;");
        preparedStatement.setDouble(1, 10);
        preparedStatement.setString(2, "Male");
        preparedStatement.setString(3, "United States");
        preparedStatement.executeUpdate();

        ResultSet rs = statement.executeQuery("\n" +
                "Select fee, gender,country from Students where country='United States' and gender='Male' limit 5;");

        while (rs.next()) {
            Double Fee = rs.getDouble("fee");
            String gender = rs.getString("gender");
            String country = rs.getString("country");

            System.out.println("Fee:" + Fee + " " + "gender: " + gender + " " + "country: " + country + " ");
        }

    }

    @Test
    public void testTask6() throws SQLException {
        ResultSet rs = statement.executeQuery( "select fee from students " +
                "where gender = 'male' and country = 'United States' " +
                "limit 5" );
        while(rs.next()) {
            Double fee = rs.getDouble( 1 );
            System.out.println("Fee: " + fee);
        }

        statement.execute( "update students set fee = fee + 10 where gender = 'male' and country = 'United States'" );
        System.out.println("--------------------------------------------");

        rs = statement.executeQuery( "select fee from students " +
                "where gender = 'male' and country = 'United States' " +
                "limit 5" );
        while(rs.next()) {
            Double fee = rs.getDouble( 1 );
            System.out.println("Fee: " + fee);
        }
    }

    @Test
    public void testTask7() throws SQLException {
        String gender = "male";
        String country = "United States";
        int toAdd = 10;

        PreparedStatement preparedStatement = connection.prepareStatement( "select fee from students " +
                "where gender = ? and country = ? " +
                "limit 5" );
        preparedStatement.setString( 1, gender );
        preparedStatement.setString( 2, country );
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            Double fee = rs.getDouble( 1 );
            System.out.println("Fee: " + fee);
        }

        PreparedStatement updateStatement = connection.prepareStatement(
                "update students set fee = fee + ? where gender = ? and country = ?" );

        updateStatement.setInt( 1, toAdd );
        updateStatement.setString( 2, gender );
        updateStatement.setString( 3, country );
        updateStatement.executeUpdate();
        System.out.println("--------------------------------------------");

        rs = preparedStatement.executeQuery();
        while(rs.next()) {
            Double fee = rs.getDouble( 1 );
            System.out.println("Fee: " + fee);
        }
    }
}
