package com.cyb01b.dbmsprojectapi;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.sql.DataSource;

import org.junit.experimental.results.PrintableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WebDao {
	
	@Autowired
	DataSource datasource;

	public Customer getCustomerData(String userName) throws LoginException {
		String sqlCustomer = "select * from cyb01b_dbms_project_database.customer where lower(user_name) = lower(?) ";
		String sqlAddress = "select * from cyb01b_dbms_project_database.customer c "
				+ "left outer join cyb01b_dbms_project_database.address a on c.customer_id = a.customer_id "
				+ "where lower(c.user_name) = lower(?) ";
		String sqlPayment = "select * from cyb01b_dbms_project_database.customer c "
				+ "left outer join cyb01b_dbms_project_database.payment p on c.customer_id = p.customer_id "
				+ "where lower(c.user_name) = lower(?) ";

		Customer customer = null;
		List<Address> addresses = new ArrayList<Address>();
		Connection connection = null;
		
		System.out.println("WebDao: processing request for customer_id " + userName);
		System.out.println("WebDao: executing sql: " + sqlCustomer);
	
		try {
			if (null == connection || connection.isClosed())
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
			throw new LoginException("Customer " + userName + " was not authenticated succesfully");
		
		System.out.println("WebDao: executing sql: " + sqlAddress);
		
		try {
			if (null == connection || connection.isClosed())
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
		
		System.out.println("WebDao: executing sql: " + sqlPayment);
		
		List<Payment> payments = new ArrayList<Payment>();
		
		try {
			if (null == connection || connection.isClosed())
				connection = datasource.getConnection();
			
			PreparedStatement ps = connection.prepareStatement(sqlPayment);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
				
			while (rs.next()) {
				Payment payment = new Payment();
				payment.setPaymentId(rs.getInt("payment_id"));
				payment.setCustomerId(rs.getInt("customer_id"));
				payment.setCardNumber(rs.getDouble("card_number"));
				payment.setName(rs.getString("name"));
				payment.setExpirationDate(rs.getString("exp_date"));
				payment.setCvv(rs.getDouble("cvv"));
				
				payments.add(payment);
			}
			
			rs.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		customer.setPayments(payments);

		return customer;
	}

	public PageData getLandingPageData(String userName) {
		PageData pageData;
		
		// do a search but return everything
		pageData = searchForItems(null, userName);
		
		return pageData;
	}
	
	public PageData searchForItems(String searchTerm, String userName) {
		PageData pageData = new PageData();
		
		String sqlSearch = "select * from cyb01b_dbms_project_database.item "
				+ (searchTerm != null ? 
				"where name like '%" + searchTerm + "%' order by name " 
				: 
				"order by rand() limit 4 ");
		
		Connection connection = null;

		System.out.println("WebDao: processing request for customer_id " + userName);
		System.out.println("WebDao: executing sql: " + sqlSearch);
	
		ArrayList<Item> items = new ArrayList<Item>();
		
		try {
			if (null == connection || connection.isClosed())
				connection = datasource.getConnection();
			
			PreparedStatement ps = connection.prepareStatement(sqlSearch);
//			if (searchTerm != null) ps.setString(1, searchTerm);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Item item = new Item();
				
				item.setItemId(rs.getInt("item_id"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setCost(rs.getDouble("cost"));
				
				items.add(item);
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		pageData.setItems(items);
	
//		if (customer == null)
//			throw new LoginException("Customer " + userName + " was not authenticated succesfully");
		
		
		
		StringJoiner sj = new StringJoiner(",");
		
		for (Item item : items) {
			sj.add(String.valueOf(item.getItemId()));
		}
		
		String sqlPictures = "select * " + 
				"from cyb01b_dbms_project_database.picture " + 
				"where item_id in (" + sj.toString() + ") " +
				"order by item_id, file_name ";
		
		System.out.println("WebDao: executing sql: " + sqlPictures + " " + sj.toString());
	
		ArrayList<Picture> pictures = new ArrayList<Picture>();
		
		try {
			PreparedStatement ps = connection.prepareStatement(sqlPictures);
//			ps.setString(1, sj.toString());
			ResultSet rs = ps.executeQuery();
				
			while (rs.next()) {
				Picture picture = new Picture();				
				picture.setItemId(rs.getInt("item_id"));
				picture.setPictureId(rs.getInt("picture_id"));
				picture.setFileName(rs.getString("file_name"));
				
				pictures.add(picture);
			}
			
			rs.close();
			ps.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		// insert pictures into item
		for (Picture picture : pictures) {
			for (Item item : items) {
				if (picture.getItemId() == item.getItemId()) {
					item.getPictures().add(picture);
				}
			}
		}
		
		return pageData;
	}
	
	public PageData submitOrder(RequestObject requestObject, ResponseObject responseObject, String userName) throws SubmitOrderException {
		PageData pageData = responseObject.getPageData();
		
		// First get the new order id for insertion into order, order_item tables 
		
		String sqlNewOrderId = "select max(order_id) + 1 as new_order_id from cyb01b_dbms_project_database.order";
		
		Connection connection = null;

		System.out.println("WebDao: processing request for customer_id " + userName);
		System.out.println("WebDao: executing sql: " + sqlNewOrderId);
		
		int newOrderId = 0;
		
		try {
			if (null == connection || connection.isClosed())
				connection = datasource.getConnection();
			PreparedStatement ps = connection.prepareStatement(sqlNewOrderId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) 
				newOrderId = rs.getInt("new_order_id");
				
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		try {
			
			// Second insert the items and total the order
			if (null == connection || connection.isClosed())
				connection = datasource.getConnection();
			
			String sqlInsertItems = "insert into cyb01b_dbms_project_database.order_item " + 
					"(order_id, item_id, qty) " + 
					"values " + 
					"(?, ?, ?) ";
			
			List<Item> items = requestObject.getOrderItems();
			
			Double totalCost = 0D;

			{
				PreparedStatement ps = connection.prepareStatement(sqlInsertItems);
				
				for (Item item : items) {
					int i = 1;

					ps.setInt(i++, newOrderId);
					ps.setInt(i++, item.getItemId());
					ps.setInt(i++, item.getQty());

					ps.executeUpdate();
					
					totalCost += item.getCost() * item.getQty();
				}
				
				
				ps.close();
			}
			
			// Lastly, insert the order now that we have totaled our items' cost
			
			String sqlInsertOrder = "insert into cyb01b_dbms_project_database.order " + 
					"(order_id, customer_id, date, address_id, payment_id, total, status) " + 
					"values " + 
					"(?, ?, current_time, ?, ?, ?, 'new') ";
			
			System.out.println("WebDao: executing sql: " + sqlInsertOrder);
			
			{
				PreparedStatement ps = connection.prepareStatement(sqlInsertOrder);
				int i = 1;

				ps.setInt(i++, newOrderId);
				ps.setString(i++, requestObject.getUserId());
				ps.setInt(i++, requestObject.getAddressId());
				ps.setInt(i++, requestObject.getPaymentId());
				ps.setDouble(i++, totalCost);
				
				ps.executeUpdate();
				ps.close();
			}
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();
			
			throw new SubmitOrderException("Something failed during order submission");
		} 
		
		return pageData;
	}
	
	public List<Order> getOrderHistory(String userName) {
		
		String sqlOrderHistory = "select * from cyb01b_dbms_project_database.customer c " + 
				"left outer join cyb01b_dbms_project_database.order o on c.customer_id = o.customer_id " + 
				"left outer join cyb01b_dbms_project_database.order_item oi on o.order_id = oi.order_id " + 
				"left outer join cyb01b_dbms_project_database.item i on oi.item_id = i.item_id " + 
				"where lower(c.user_name) = lower(?) " +
				"order by o.order_id desc";
		
		Connection connection = null;

		System.out.println("WebDao: processing order history request for customer_id " + userName);
		System.out.println("WebDao: executing sql: " + sqlOrderHistory);
	
		List<Order> orders = new ArrayList<Order>();
		
		try {
			if (null == connection || connection.isClosed())
				connection = datasource.getConnection();
			
			PreparedStatement ps = connection.prepareStatement(sqlOrderHistory);
			
			ps.setString(1, userName);
			
			Order order = null;
			int orderId = -1;
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				if (orderId != rs.getInt("order_id")) {
					
					// create new order object for each new order id
					orderId = rs.getInt("order_id");
					
					if (order != null)
						orders.add(order);
					
					order = new Order();
					order.setOrderId(rs.getInt("order_id"));
					order.setAddressId(rs.getInt("address_id"));
					order.setCustomerId(rs.getInt("customer_id"));
					order.setDate(rs.getDate("date"));
					order.setPaymentId(rs.getInt("payment_id"));
					order.setStatus(rs.getString("status"));
					order.setTotal(rs.getDouble("total"));
					order.setOrderItems(new ArrayList<OrderItem>());
				}
				
				OrderItem orderItem = new OrderItem();
				
				orderItem.setOrderId(rs.getInt("order_id"));
				orderItem.setItemId(rs.getInt("item_id"));
				orderItem.setQuantity(rs.getInt("qty"));
				
				Item item = new Item();
				item.setItemId(rs.getInt("item_id"));
				item.setCost(rs.getDouble("cost"));
				item.setDescription(rs.getString("description"));
				item.setName(rs.getString("name"));
				
				orderItem.setItem(item);
				
				order.getOrderItems().add(orderItem);
			}
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return orders;
	}

}
