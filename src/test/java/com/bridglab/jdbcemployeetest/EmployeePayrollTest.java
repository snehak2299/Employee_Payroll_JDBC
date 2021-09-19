package com.bridglab.jdbcemployeetest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bridglab.jdbcemployee.EmployeePayrollData;
import com.bridglab.jdbcemployee.EmployeePayrollDb;
import com.bridglab.jdbcemployee.EmployeePayrollService;
import com.bridglab.jdbcemployee.EmployeePayrollService.IOService;

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
	 public void check() {
		EmployeePayrollService employeePayrollService = new EmployeePayrollService();
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2018, 1, 1);
		LocalDate endDate = LocalDate.now();
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollForDateRange(IOService.DB_IO,startDate,endDate);
		Assert.assertEquals(4,employeePayrollData.size() );
	 }
}
