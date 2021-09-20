package com.bridglab.jdbcemployeetest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.bridglab.jdbcemployee.EmployeePayrollData;
import com.bridglab.jdbcemployee.EmployeePayrollDb;
import com.bridglab.jdbcemployee.EmployeePayrollDbService;
import com.bridglab.jdbcemployee.EmployeePayrollService;
import com.bridglab.jdbcemployee.EmployeePayrollService.IOService;

import JdbcDemo.EmployeePayrollDBService;

public class EmployeePayrollTest {
	@Test
    public void connect_Db() {
        Connection dbConnection=new EmployeePayrollDb().connectDb();
        System.out.println(dbConnection);
    }
	@Test
	public void givenEmployeePayDb_retriveData_whenMatchCount() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
        Assert.assertEquals(4,employeePayrollData.size());
	}
	@Test
	public void givenNewSalaryEmployee_whenUpdated_SholudMatch() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> employeePayrollDataList=employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("terisa",30000);
		boolean result = employeePayrollService.checkEmployeeSyncWithDb("terisa");
		Assert.assertTrue(result);
	}
	 @Test
	 public void ifstartdateGiven_findall_tillNowDate() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 1, 1);
		LocalDate endDate = LocalDate.now();
		EmployeePayrollData employeePayrollData=employeePayrollService.readEmployeePayrollForDateRange(IOService.DB_IO,startDate,endDate);
		Assert.assertEquals(4,employeePayrollData.size() );
	 }
	 
	 @Test
	 public void givenPayRollData_avgSalaryRecivedByGender() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String,	Integer>avgSalaryByGender=employeePayrollService.readAvgSalaryByGender(IOService.DB_IO);
		Assert.assertTrue(avgSalaryByGender.get("M").equals(100000)&&
				avgSalaryByGender.get("F").equals(243333.333));
		 
	 }
	 @Test
	    public void abilityToAddNewEmployee_wheninserted() {
	        EmployeePayrollDbService employeePayrollDBService=new EmployeePayrollDbService();
	        employeePayrollDBService.addNewEmployee(1,"Poonam","F",500000,"2021-09-09");
	   }
	 @Test
	    public void abilityToAddEmployeeToPayroll_whenNewEmployeeAdded() throws SQLException {
	        EmployeePayrollDbService employeePayrollDBService=new EmployeePayrollDbService();
	        employeePayrollDBService.addEmployeePayroll("dipika","F",652000,"2021-08-02");
	    }
}
