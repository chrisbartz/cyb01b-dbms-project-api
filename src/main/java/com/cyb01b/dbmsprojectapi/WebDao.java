package com.cyb01b.dbmsprojectapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
	
	@Autowired
	DataSource datasource;

	public Customer getCustomerData(String userName) throws LoginException {
		String sqlCustomer = "select * from cyb01b_dbms_project_database.customer where lower(user_name) = lower(?) ";
		String sqlAddress = "select * from cyb01b_dbms_project_database.customer c "
				+ "left outer join cyb01b_dbms_project_database.address a on c.customer_id = a.customer_id "
				+ "where lower(c.user_name) = lower(?) ";

		Customer customer = null;
		List<Address> addresses = new ArrayList<Address>();
		Connection connection = null;
		
		System.out.println("WebDao: processing request for customer_id " + userName);
		System.out.println("WebDao: executing sql: " + sqlCustomer);
	
		try {
			connection = datasource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sqlCustomer);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				customer = new Customer();
				customer.setCustomerId(rs.getInt("customer_id"));
				customer.setUserName(rs.getString("user_name"));
				customer.setLastName(rs.getString("last_name"));
				customer.setFirstName(rs.getString("first_name"));
				customer.setEmailAddress(rs.getString("email_address"));
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	
		if (customer == null)
			throw new LoginException("Customer was not authenticated succesfully");
		
		System.out.println("WebDao: executing sql: " + sqlAddress);
		
		try {
			if (connection.isClosed())
				connection = datasource.getConnection();
			
			PreparedStatement ps = connection.prepareStatement(sqlAddress);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Address address = new Address();
				address.setAddressId(rs.getInt("address_id"));
				address.setStreetNumber(rs.getString("street_number"));
				address.setStreetName(rs.getString("street_name"));
				address.setState(rs.getString("state"));
				address.setZipcode(rs.getString("zipcode"));
				address.setCustomerId(rs.getInt("customer_id"));
				
				addresses.add(address);
			}
			
			rs.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		customer.setAddresses(addresses);

		return customer;
	}

	public PageData getLandingPageData() {
		PageData pageData = new PageData();
		
		// do stuff here
		
		return pageData;
	}

}
