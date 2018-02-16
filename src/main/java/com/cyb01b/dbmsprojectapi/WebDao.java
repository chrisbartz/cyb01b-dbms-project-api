package com.cyb01b.dbmsprojectapi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WebDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Customer getCustomerData(String userName) throws LoginException {
		String sqlCustomer = "select * from cyb01b_dbms_project_database.customer where user_name = ? ";
		String sqlAddress = "select * from cyb01b_dbms_project_database.address where customer_id = ? ";

		Customer customer;
		System.out.println("WebDao: processing request for customer_id " + userName);

//		List<Customer> customers = jdbcTemplate.query(sqlCustomer, new RowMapper<Customer>() {
//			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Customer customer = new Customer();
//
//				if (rs.next()) {
//					customer.setCustomerId(rs.getInt("customer_id"));
//					customer.setUserName(rs.getString("user_name"));
//					customer.setLastName(rs.getString("last_name"));
//					customer.setFirstName(rs.getString("first_name"));
//					customer.setEmailAddress(rs.getString("email_address"));
//				}
//
//				return customer;
//			}
//		});
		
		List<Customer> customers = jdbcTemplate.query(sqlCustomer, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userName);
			}
		}, new RowMapper<Customer>() {
			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Customer customer = new Customer();

				if (rs.next()) {
					customer.setCustomerId(rs.getInt("customer_id"));
					customer.setUserName(rs.getString("user_name"));
					customer.setLastName(rs.getString("last_name"));
					customer.setFirstName(rs.getString("first_name"));
					customer.setEmailAddress(rs.getString("email_address"));
				}

				return customer;
			}
		});

		if (customers != null && !customers.isEmpty())
			customer = customers.get(0);
		else
			throw new LoginException("Customer was not authenticated succesfully");
		
//		List<Address> addresses = jdbcTemplate.query(sqlAddress, new RowMapper<Address>() {
//			public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Address address = new Address();
//
//				while (rs.next()) {	
//					address.setAddressId(rs.getInt("address_id"));
//					address.setStreetNumber(rs.getString("street_number"));
//					address.setStreetName(rs.getString("street_name"));
//					address.setState(rs.getString("state"));
//					address.setZipcode(rs.getString("zipcode"));
//					address.setCustomerId(rs.getInt("customer_id"));
//				}
//
//				return address;
//			}
//		});
		
		List<Address> addresses = jdbcTemplate.query(sqlAddress, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userName);
			}
		}, new RowMapper<Address>() {
			public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
				Address address = new Address();

				if (rs.next()) {
					address.setAddressId(rs.getInt("address_id"));
					address.setStreetNumber(rs.getString("street_number"));
					address.setStreetName(rs.getString("street_name"));
					address.setState(rs.getString("state"));
					address.setZipcode(rs.getString("zipcode"));
					address.setCustomerId(rs.getInt("customer_id"));
				}

				return address;
			}
		});
		
		customer.setAddresses(addresses);

		return customer;

	}

	public PageData getLandingPageData() {
		PageData pageData = new PageData();
		
		// do stuff here
		
		return pageData;
	}

}
