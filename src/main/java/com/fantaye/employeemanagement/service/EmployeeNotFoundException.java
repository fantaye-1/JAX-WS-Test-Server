package com.fantaye.employeemanagement.service;

/** Thrown (and sent to SOAP clients as a fault) when no employee has the requested id. */
public class EmployeeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
