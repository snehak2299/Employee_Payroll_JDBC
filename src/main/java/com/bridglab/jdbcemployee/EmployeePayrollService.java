package com.bridglab.jdbcemployee;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeePayrollService {
	public enum IOService { CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
	private List <EmployeePayrollData>employeePayrollList;
	public EmployeePayrollService() {}
	public void EmployeePayrollService(List<EmployeePayrollData>employeePayrollList) {
		
	}
	public List<EmployeePayrollData> readEmployeePayrollData(IOService dbIo) {
		if(IOService.DB_IO != null)
            this.employeePayrollList=EmployeePayrollDb.getInstance().readData();
        return this.employeePayrollList;
	}
	
	public void updateEmployeeSalary(String name, int salary) {
		int result = EmployeePayrollDb.getInstance().updateEmployeeData(name,salary);
		if(result==0) return ;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if(employeePayrollData!=null)employeePayrollData.salary=salary;
	}
	private EmployeePayrollData getEmployeePayrollData(String name) {
		return this.employeePayrollList.stream().filter(employeePayrollDataItem ->employeePayrollDataItem.name.equals(name)).findFirst().orElse(null);
	}
	public boolean checkEmployeeSyncWithDb(String name) {
		List <EmployeePayrollData>employeePayrollDataList= EmployeePayrollDb.getInstance().getEmployeePayrolldata(name);
		return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}

	public EmployeePayrollDb readEmployeePayrollForDateRange(IOService dbIo, LocalDate startDate,
			LocalDate endDate) {
		if(dbIo.equals(IOService.DB_IO))
			return  EmployeePayrollDb.getInstance().getEmployeeRanged(startDate,endDate);
				
		return null;
	}
	public Map<String, Integer> readAvgSalaryByGender(IOService dbIo) {
		if(dbIo.equals(IOService.DB_IO))
			return EmployeePayrollDb.getInstance().getAvgSalaryByGender();

		return null;
	}
}
