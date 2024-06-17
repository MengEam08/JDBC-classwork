package model.dao;

import model.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao {
    final String url = "jdbc:postgresql://localhost:5432/postgres";
    final String user = "postgres";
    final String password = "20030830";

    @Override
    public Customer searchCustomerById(Integer id) {
        String sql = """
                SELECT * FROM customer WHERE id = ?
                """;
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pre = connection.prepareStatement(sql)
        ) {
            pre.setInt(1, id);
            ResultSet resultSet = pre.executeQuery();
            Customer customer = null;
            if (resultSet.next()) {
                customer = Customer.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .is_deleted(resultSet.getBoolean("is_deleted"))
                        .created_date(resultSet.getDate("created_date"))
                        .build();
            }
            return customer;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<Customer> queryAllCustomers() {
        String sql = """
                SELECT * FROM customer
                """;
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("is_deleted"),
                        resultSet.getDate("created_date")
                ));
            }
            return customers;
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public int deleteCustomerById(Integer id) {
        String sql = """
                DELETE FROM customer WHERE id = ?
                """;
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pre = connection.prepareStatement(sql)
        ) {
            pre.setInt(1, id);
            return pre.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int updateCustomerById(Integer id) {
        String sql = """
                UPDATE customer SET name = ?, email = ?, password = ? WHERE id = ?
                """;
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pre = connection.prepareStatement(sql)
        ) {
            Customer customer = searchCustomerById(id);
            if (customer == null) {
                System.out.println("[!!!]No customer");
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.print("(*)Insert New Name: ");
                pre.setString(1, scanner.nextLine());
                System.out.print("(*)Insert New Email: ");
                pre.setString(2, scanner.nextLine());
                System.out.print("(*)Insert New Password: ");
                pre.setString(3, scanner.nextLine());
                pre.setInt(4, id);
                return pre.executeUpdate();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }

    @Override
    public int addNewCustomer(Customer customer) {
        String sql = """
                INSERT INTO customer (name, email, password, is_deleted, created_date)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pre = connection.prepareStatement(sql)
        ) {
            pre.setString(1, customer.getName());
            pre.setString(2, customer.getEmail());
            pre.setString(3, customer.getPassword());
            pre.setBoolean(4, customer.getIs_deleted());
            pre.setDate(5, customer.getCreated_date());
            return pre.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return 0;
    }
}
