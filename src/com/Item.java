package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Item {
	
	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
	 Class.forName("com.mysql.cj.jdbc.Driver");
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/OrderService?serverTimezone=UTC", "root", "071lakshan071");
	 
	 //For testing
	 	System.out.print("Successfully connected");
	 	
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }

	 return con;
	}
	
	// read item
	public String readItems()
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for reading."; 
	 } 
	 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Item ID</th>" 
	 + "<th>Item Name</th><th>Item Code</th>"
	 + "<th>Item Price</th><th>Item Description</th>" 
	 + "<th>Update</th><th>Remove</th></tr>"; 
	 
	 String query = "select * from Orders_FE"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
	 String itemID    = Integer.toString(rs.getInt("ItemID")); 
	 String itemName  = rs.getString("Iname"); 
	 String itemCode  = rs.getString("Icode"); 
	 Double itemPrice = rs.getDouble("Iprice"); 
	 String itemDesc  = rs.getString("Idesc"); 
	 
	// Add into the html table
	 output += "<tr><td>" + itemID + "</td>"; 
	 output += "<td>" + itemName + "</td>";
	 output += "<td>" + itemCode + "</td>"; 
	 output += "<td>" + itemPrice + "</td>"; 
	 output += "<td>" + itemDesc + "</td>"; 
	 
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update' "
	+ "class='btnUpdate btn btn-secondary' data-itemid='" + itemID + "'></td>"
	+ "<td><input name='btnRemove' type='button' value='Remove' "
	+ "class='btnRemove btn btn-danger' data-itemid='" + itemID + "'></td></tr>"; 
	 } 
	 con.close(); 
	 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	catch (Exception e) 
	 { 
	 output = "Error while reading the items."; 
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
	}

	// insert item
	public String insertItem(String code, String name, String price, String desc)
	{	
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting  to the database for inserting."; 
			 } 
			 
			 // create a prepared statement
			 String query = " insert into Orders_FE(`ItemID`,`Iname`,`Icode`,`Iprice`,`Idesc`)"
			+ " values (?, ?, ?, ?, ?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			// preparedStmt.setInt(1,Integer.parseInt(id));
			 preparedStmt.setString(2, name); 
			 preparedStmt.setString(3, code); 
			 preparedStmt.setDouble(4, Double.parseDouble(price)); 
			 preparedStmt.setString(5, desc); 
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readItems(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
	
			// update item
			public String updateItem(String ID, String name, String code, 
			 String price, String desc) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for updating."; 
			 } 
			 
			 // create a prepared statement
			 String query = "UPDATE Orders_FE SET Iname=?,Icode=?,Iprice=?,Idesc=? WHERE ItemID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setString(1, name); 
			 preparedStmt.setString(2, code); 
			 preparedStmt.setDouble(3, Double.parseDouble(price)); 
			 preparedStmt.setString(4, desc); 
			 preparedStmt.setInt(5, Integer.parseInt(ID)); 
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readItems(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 
			
			// delete item
			public String deleteItem(String itemID) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for deleting."; 
			 } 
			 
			 // create a prepared statement
			 String query = "delete from Orders_FE where ItemID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(itemID)); 
			 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readItems(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 } 


}
