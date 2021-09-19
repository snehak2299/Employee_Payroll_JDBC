package com.bridglab.jdbcemployee;

import java.sql.Connection;
import java.sql.DriverManager;

public class EmployeePayrollDb {
	String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	String userName ="root";
	String password = "qwerty";
	Connection conn;

	public Connection connectDb() {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(Class.forName("com.mysql.cj.jdbc.Driver"));

        }catch (ClassNotFoundException e){
        	throw new IllegalStateException("cannot find the driver in class path:", e);
        }

        try {
            System.out.println("Connecting to database: "+url);
            conn=DriverManager.getConnection(url,userName,password);
            System.out.println("Connection is successful! "+conn);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return conn;
	}

}
