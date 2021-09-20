package com.bridglab.jdbcemployee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;


public class EmployeePayrollDbService {
	private String url = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
    private String username = "root";
    private String password = "qwerty";
    Connection con;
    public List<EmployeePayrollData> readData() {
        String sql="SELECT * FROM employee_payroll;";
        List<EmployeePayrollData> employeePayrollList=new ArrayList<>();
        try {
            Connection connection=this.getConnection();
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

    private Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //System.out.println(Class.forName("com.mysql.cj.jdbc.Driver"));

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            System.out.println("Connecting to database"+url);
            con= DriverManager.getConnection(url,username,password);
            System.out.println("Connection is successful!!"+con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public int updateEmployeeData(String name, double salary) {
     return this.updateEmployeeDataUsingStatement(name,salary);
    }

    public int updateEmployeeDataUsingStatement(String name, double salary) {
        String sql=String.format("update employee_payroll set salary=%2f where name='%s';",salary,name);
        try(Connection connection=this.getConnection()) {
            Statement statement=connection.createStatement();
            return statement.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public ResultSet retrieveAccordingToDate(String s) {

        String sql=String.format("select * from employee_payroll where start BETWEEN CAST('%s' AS DATE) AND DATE(NOW());",s);
        try(Connection connection=this.getConnection()) {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next())
            {
                System.out.println(
                        resultSet.getString(1)+" "+
                                resultSet.getString(2)+ " "+
                                resultSet.getString(3)+" "+
                                resultSet.getString(4)

                );
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet SumUsingGroupby(String value) {
        String sql=String.format("Select gender,sum(%s) from employee_payroll group by gender;",value);
        try(Connection connection=this.getConnection()) {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next())
            {
                System.out.println(
                        resultSet.getString(1)+" "+
                               resultSet.getString(2)

                );
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public int addNewEmployee(int id,String name, String gender, int salary, String date) {
		String sql = String.format("insert into employee_payroll(id,name,gender,salary,start) values (%2f,'%s','%s',%2f,CAST('%s' AS DATE));",id, name, gender, salary, date);
        try (Connection connection = this.connectDb()) {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sql);

            printEntries();
            return result;
      }
       catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
		
	}
	private void printEntries() {
		String sql="SELECT * FROM employee_payroll;";
        try {
            Connection connection=this.connectDb();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println(
                        resultSet.getString(1)+" "+
                                resultSet.getString(2)+" "+
                                resultSet.getString(3)+" "+
                                resultSet.getString(4)+" "+
                                resultSet.getString(5));
            }

            }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
		
	}
	 public void addEmployeeToPayroll(String name, char gender, double salary, String date) throws SQLException {
	        int employeeID = -1;
	        Connection connection = null;
	        connection = this.getConnection();
	        Statement statement = connection.createStatement();
	        try {
	            String sql = String.format("insert into employee_payroll(name,gender,salary,start) values" +"('%s','%s',%2f,CAST('%s' AS DATE))", name, gender, salary, date);
	            int rowAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
	            if (rowAffected == 1) {

	                System.out.println(employeeID);
	                ResultSet resultSet = statement.getGeneratedKeys();
	                if (resultSet.next()) employeeID = resultSet.getInt(1);
	                System.out.println(employeeID);
	          }
	            printEntries();
	        } catch (SQLException throwables) {
	            throwables.printStackTrace();
	        }
	        try {
	            double deductions = salary * 0.2;
	            double taxablePay = salary - deductions;
	            double tax = taxablePay * 0.1;
	            double netPay = salary - tax;
	            String sql = String.format("INSERT INTO payroll_details" +
	                   "(employee_id,basic_pay,deductions,taxable_pay,tax,net_pay) values"+"(%s,%s,%s,%s,%s,%s)",employeeID, salary,deductions, taxablePay, tax, netPay);
	            int rowAffected = statement.executeUpdate(sql);
	        }
	        catch (SQLException e){
	            e.printStackTrace();
	        }



	    }
}
