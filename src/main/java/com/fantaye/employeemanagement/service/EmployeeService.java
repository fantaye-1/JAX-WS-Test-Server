package com.fantaye.employeemanagement.service;

import java.util.List;

import com.fantaye.employeemanagement.domain.Employee;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/** SOAP contract for looking up employees. */
@WebService(name = "EmployeeService", targetNamespace = EmployeeService.NAMESPACE)
public interface EmployeeService {

	String NAMESPACE = "http://service.employeemanagement.fantaye.com/";

	@WebMethod
	Employee getEmployeeById(@WebParam(name = "id") String id) throws EmployeeNotFoundException;

	@WebMethod
	List<Employee> listEmployees();
}
