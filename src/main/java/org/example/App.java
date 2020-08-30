package org.example;

import org.postgresql.Driver;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    static class Address {
        String address;
        int id;

        public Address() {
        }

        public Address(String address) {
            this.address = address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "address='" + address + '\'' +
                    ", id=" + id +
                    '}';
        }
    }


    static class Telephone {
        int id;
        String telephone;

        public Telephone() {
        }

        public Telephone(int id, String telephone) {
            this.id = id;
            this.telephone = telephone;
        }

        public Telephone(String telephone) {
            this.telephone = telephone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        @Override
        public String toString() {
            return "Telephone{" +
                    "id=" + id +
                    ", telephone='" + telephone + '\'' +
                    '}';
        }
    }

    static class User {
        int id;
        String fname;
        String lname;
        int age;
        Telephone telephone;
        Address address;

        public User(String fname, String lname, int age, Telephone telephone, Address address) {
            this.fname = fname;
            this.lname = lname;
            this.age = age;
            this.telephone = telephone;
            this.address = address;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", fname='" + fname + '\'' +
                    ", lname='" + lname + '\'' +
                    ", age=" + age +
                    ", telephone=" + telephone +
                    ", address=" + address +
                    '}';
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Telephone getTelephone() {
            return telephone;
        }

        public void setTelephone(Telephone telephone) {
            this.telephone = telephone;
        }


        public User(int id, String fname, String lname, int age, Telephone telephone, Address address) {
            this.id = id;
            this.fname = fname;
            this.lname = lname;
            this.age = age;
            this.telephone = telephone;
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

//    public void addUser(User user) throws SQLException {
//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
//        PreparedStatement preparedStatement = connection.prepareStatement("insert into \"user\" (fname, lname, age, telephone_id, address_id) values (?, ?, ?, ?, ?)");
//        preparedStatement.setString(1, "fname");
//    }

//    public User getUserById(int id) throws SQLException {
//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from \"user\" u join telephone t on u.telephone_id = t.id where u.id=?");
//        preparedStatement.setInt(1, id);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        int userId = resultSet.getInt(1);
//        String fname = resultSet.getString(2);
//        String lname = resultSet.getString(3);
//        int age = resultSet.getInt(4);
//        int telId = resultSet.getInt(5);
//        int tId = resultSet.getInt(6);
//        String tel = resultSet.getString(7);
//
//        User user = new User(userId, fname, lname, age, new Telephone(tId, tel), new Address("Mayak", 28));
//        return user;
//    }

    public void createUser(User user) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");

            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into telephone (telepthone) values (?) returning id");
            preparedStatement.setString(1, user.getTelephone().getTelephone());
//            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int anInt = resultSet.getInt(1);

            PreparedStatement preparedStatement2 = connection.prepareStatement("insert into address (address) values (?) returning id");
            preparedStatement2.setString(1, user.getAddress().getAddress());
            ResultSet resultSet1 = preparedStatement2.executeQuery();
            resultSet1.next();
            int anInt1 = resultSet1.getInt(1);

//            if (anInt == 16) throw new SQLException();
            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into \"user\" (fname, lname, age, telephone_id, address_id) values (?, ?, ?, ?, ?)");
            preparedStatement1.setString(1, user.fname);
            preparedStatement1.setString(2, user.lname);
            preparedStatement1.setInt(3, user.age);
            preparedStatement1.setInt(4, anInt);
            preparedStatement1.setInt(5, anInt1);
            preparedStatement1.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

//    public void createUser(String fname, String lname, int age) throws SQLException {
//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
//        PreparedStatement preparedStatement = connection.prepareStatement("insert into \"user\" (fname, lname, age, telephone_id) values (?, ?, ?, ?)");
//        preparedStatement.setString(1, fname);
//        preparedStatement.setString(2, lname);
//        preparedStatement.setInt(3, age);
//        preparedStatement.setInt(4, 5);
//        preparedStatement.execute();
//    }
//

//    public User getUserById(int id) throws SQLException {
//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from \"user\" where id=?");
//        preparedStatement.setInt(1, id);
//        preparedStatement.execute();
//        ResultSet resultSet = preparedStatement.getResultSet();
//        resultSet.next();

//        int idu = resultSet.getInt(1);
//        String string = resultSet.getString(2);
//        String string1 = resultSet.getString(3);
//        int anInt = resultSet.getInt(4);
//        String string2 = resultSet.getString(5);
//        String string3 = resultSet.getString(6);
//        return new User(idu, string, string1, anInt, new Telephone(string2), new Address(string3));
//    }
//
//    public List<User> getAll() throws SQLException {
//        List<User> userList = new ArrayList<>();
//
//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from \"user\"");
//        preparedStatement.execute();
//
//        ResultSet resultSet = preparedStatement.getResultSet();
//
//        while (resultSet.next()){
//            int idu = resultSet.getInt(1);
//            String string = resultSet.getString(2);
//            String string1 = resultSet.getString(3);
//            int anInt = resultSet.getInt(4);
//            User user = new User(idu, string, string1, anInt);
//            userList.add(user);
//        }
//
//        return userList;
//    }

    public static void main(String[] args) throws SQLException {
        App app = new App();
        app.createUser(new User("Molly", "Jet", 28, new Telephone("1234567"), new Address("Non")));
//        app.createUser("t", "t", 22);
//        User userById = app.getUserById(32);
//        System.out.println(userById);
//        app.createUser(new User("test", "test", 22, new Telephone("2224343")));
//        System.out.println(app.getUserById(28));

//        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
//        Statement statement = connection.createStatement();
//        statement.executeUpdate("insert into address (id, address) values (3, 'Kirova')");
//        statement.executeUpdate("insert into address (id, address) values (2, 'Tepli')");

        //statement.executeUpdate("insert into telephone (telepthone) values (2227878)");
        //statement.executeUpdate("Insert into \"user\" (fname, lname, age, telephone_id) values ('Red', 'Lost', 33, 2)");
        //statement.executeUpdate("update \"user\" set age = 24 where id=26;");
        //statement.executeUpdate("insert into \"user\" (fname, lname, age, telephone_id) VALUES ('Jack', 'Morroy', 55, 3)");
        //statement.executeUpdate("insert into \"user\" (fname, lname, age, telephone_id) VALUES ('Roll', 'Cross', 44, 4)");
        //statement.executeUpdate("delete from \"user\" where id = 26");
        //statement.executeUpdate("update \"user\" set fname = 'Holly', lname = 'Dell' where lname = 'Cross'");
        //statement.execute("select u.age, u.fname, u.lname, t.telepthone from \"user\" u right join telephone t on u.telephone_id=t.id");
//        statement.execute("select * from \"user\" u join telephone t on u.telephone_id = t.id");
    }
}
