package com.bridglab.jdbcemployee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDb {
	String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	String userName ="root";
	String password = "qwerty";
	Connection conn;
	static Statement st = null;
	public List<EmployeePayrollData> readData() {
        String sql="SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
        try {
            Connection connection=this.connectDb();
            Statement statement=connection.createStatement();
            ResultSet result=statement.executeQuery(sql);
            while (result.next()){
                int id=result.getInt("id");
                String name=result.getString("name");
                String gender=result.getString("gender");
                int salary=result.getInt("salary");
                LocalDate start=result.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id,name,gender,salary,start));
            }
            connection.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return employeePayrollList;
    }

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
