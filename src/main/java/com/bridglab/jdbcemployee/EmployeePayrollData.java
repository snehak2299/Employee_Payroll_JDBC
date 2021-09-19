package com.bridglab.jdbcemployee;

import java.sql.Date;
import java.time.LocalDate;

public class EmployeePayrollData {
	public int id;
	public String name;
	public String gender;
	public int salary;
	public LocalDate start;
	
	public EmployeePayrollData(int id, String name, String gender, int salary, LocalDate start) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.start = start;
	}

	@Override
	public String toString() {
		return "EmployeePayrollData [id=" + id + ", name=" + name + ", gender=" + gender + ", salary=" + salary
				+ ", start=" + start + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if( this == o) return true;
		if(o==null||getClass() !=o.getClass())return false;
		EmployeePayrollData that = (EmployeePayrollData) o;
		return id == that.id &&Integer.compare(that.salary, salary)==0 &&
				name.equals(that.name);
	
	}
	

}
