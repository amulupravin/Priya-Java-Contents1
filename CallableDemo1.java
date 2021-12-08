package com.training.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class CallableDemo1 {

	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false", "root", "root");

			String procedureCall = "{ call get_employee_by_id(?,?,?,?) }";

			CallableStatement callableStatement = conn.prepareCall(procedureCall);

			callableStatement.setInt(1, 1001);
			callableStatement.registerOutParameter(2, Types.VARCHAR);
			callableStatement.registerOutParameter(3, Types.INTEGER);
			callableStatement.registerOutParameter(4, Types.VARCHAR);

			callableStatement.executeUpdate();

			String name = callableStatement.getString(2);
			int salary = callableStatement.getInt(3);
			String job = callableStatement.getString(4);

			System.out.println("Name: " + name);
			System.out.println("Salary: " + salary);
			System.out.println("Job: " + job);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

/*
 * CREATE OR REPLACE PROCEDURE get_employee_by_id(emp_id IN
 * EMPLOYEE.employee_id%TYPE, emp_name out EMPLOYEE.first_name%TYPE, emp_salary
 * out EMPLOYEE.SALARY%TYPE, emp_job out employee.job_id %type) AS BEGIN SELECT
 * first_name, salary,job_id into emp_name, emp_salary, emp_job from EMPLOYEE
 * WHERE employee_id = emp_id; end;
 */