package com.bridglab.jdbcemployeetest;

import java.sql.Connection;
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
		List<EmployeePayrollData> employeePayrollData=employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.updateEmployeeSalary("terisa",30000);
//		boolean result = employeePayrollService.checkEmployeeSyncWithDb("terisa");
//		Assert.assertTrue(result);
	}

}
