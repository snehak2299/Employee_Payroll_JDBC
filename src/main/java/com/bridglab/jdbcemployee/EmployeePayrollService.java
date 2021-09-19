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
	public void updateEmployeeSalary(String name, int salary) {
		int result = new EmployeePayrollDb().updateEmployeeData(name,salary);
		if(result==0) return ;
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if(employeePayrollData!=null)employeePayrollData.salary=salary;
	}
	private EmployeePayrollData getEmployeePayrollData(String name) {
		return this.employeePayrollList.stream().filter(employeePayrollDataItem ->employeePayrollDataItem.name.equals(name)).findFirst().orElse(null);
	}
	public boolean checkEmployeeSyncWithDb(String name) {
		List <EmployeePayrollData>employeePayrollList= new EmployeePayrollDb().getEmployeePayrolldata(name);
		return employeePayrollList.get(0).equals(getEmployeePayrollData(name));
	}
}
