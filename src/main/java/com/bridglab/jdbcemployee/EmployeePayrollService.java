package com.bridglab.jdbcemployee;


import java.util.List;

public class EmployeePayrollService {
	public enum IOService { CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
	private List <EmployeePayrollData>employeePayrollList;
	public EmployeePayrollService() {}
	public void EmployeePayrollService(List<EmployeePayrollData>employeePayrollList) {
		
	}
	public List<EmployeePayrollData> readEmployeePayrollData(IOService dbIo) {
		if(IOService.DB_IO != null)
            this.employeePayrollList=new EmployeePayrollDb().readData();
        return this.employeePayrollList;
	}
}
