package com.bridglab.jdbcemployee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePayrollDb {
	String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	String userName ="root";
	String password = "qwerty";
	Connection conn;
	private PreparedStatement getEmployeePayrolldataStatement;
	static Statement st = null;
	private static EmployeePayrollDb employeePayrollDb;
	public EmployeePayrollDb() {
		
	}
	public static EmployeePayrollDb getInstance() {
		if (employeePayrollDb==null)
			employeePayrollDb = new EmployeePayrollDb();
		return employeePayrollDb;	
	}
	public List<EmployeePayrollData> readData() {
        String sql="SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
        try {
            Connection connection=this.connectDb();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            employeePayrollList=this.getEmployeePayrolldata(resultSet);

        }
        catch (SQLException e)
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

	public int updateEmployeeData(String name, int salary) {
		return this.updateEmployeeDataUsingStatement(name,salary);
	}

	public int updateEmployeeDataUsingStatement(String name, double salary) {
       String sql=String.format("update employee_payroll set salary=%2f where name='%s';",salary,name);
        try(Connection connection=this.connectDb()) {
            Statement statement=connection.createStatement();
           return statement.executeUpdate(sql);
       }
        catch (SQLException e)
       {
           e.printStackTrace();
        }

        return 0;
    }
	public List<EmployeePayrollData> getEmployeePayrolldata(String name) {
		List <EmployeePayrollData>employeePayrollList= null;
		if(this.getEmployeePayrolldataStatement==null)
			this.preparedStatementForEmployeeData();
		try {
			ResultSet resultSet= getEmployeePayrolldataStatement.executeQuery();
			employeePayrollList=this.getEmployeePayrolldata(resultSet);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	private List<EmployeePayrollData> getEmployeePayrolldata(ResultSet resultSet) {
		List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
        try {
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String gender=resultSet.getString("gender");
                int salary=resultSet.getInt("salary");
                LocalDate start=resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id,name,gender,salary,start));
            }
           
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return employeePayrollList;
	}

	private void preparedStatementForEmployeeData() {
		try(Connection connection=this.connectDb()){
			String sql = "SELECT * FROM employee_payroll WHERE name=?";
			getEmployeePayrolldataStatement = connection.prepareStatement(sql);
		}catch(Exception e)
        {
            e.printStackTrace();
        }
	}

	public  List<EmployeePayrollData> getEmployeeRanged(LocalDate startDate, LocalDate endDate) {
		String sql = String.format("SELECT * FROM employee_payroll WHERE start between '%s' and '%s';",
		Date.valueOf(startDate),Date.valueOf(endDate));
		List <EmployeePayrollData>employeePayrollList= null;
		return this.getEmployeeDataUsingDb(sql);
	
	//return null;
	}
	private List<EmployeePayrollData>  getEmployeeDataUsingDb(String sql){
		List <EmployeePayrollData>employeePayrollList= null;
		if(this.getEmployeePayrolldataStatement==null)
			this.preparedStatementForEmployeeData();
		try {
			ResultSet resultSet= getEmployeePayrolldataStatement.executeQuery();
			employeePayrollList=this.getEmployeePayrolldata(resultSet);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public Map<String, Integer> getAvgSalaryByGender() {
		String sql = "select gender,AVG(salary) as avg_salary from employee_payroll group by gender";
		Map <String,Integer>genderToAvgSalaryMap= new HashMap<>();
		try(Connection connection=this.connectDb()) {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while(resultSet.next()) {
            	String gender = resultSet.getString("gender");
            	int salary = resultSet.getInt("avg_salary");
            }
            
       }
        catch (SQLException e)
       {
           e.printStackTrace();
        }
		return genderToAvgSalaryMap;
	}


}
