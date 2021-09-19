package com.bridglab.jdbcemployeetest;

import java.sql.Connection;

import org.junit.Test;

import com.bridglab.jdbcemployee.EmployeePayrollDb;

public class EmployeePayrollTest {
	@Test
    public void connect_Db() {
        Connection dbConnection=new EmployeePayrollDb().connectDb();
        System.out.println(dbConnection);
    }

}
