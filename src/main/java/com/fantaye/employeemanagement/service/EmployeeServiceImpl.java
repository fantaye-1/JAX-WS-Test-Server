package com.fantaye.employeemanagement.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fantaye.employeemanagement.domain.Employee;

import jakarta.jws.WebService;

/** In-memory implementation of {@link EmployeeService}. */
@WebService(
		endpointInterface = "com.fantaye.employeemanagement.service.EmployeeService",
		serviceName = "EmployeeService",
		portName = "EmployeeServicePort",
		targetNamespace = EmployeeService.NAMESPACE)
public class EmployeeServiceImpl implements EmployeeService {

	private final Map<String, Employee> employees = new ConcurrentHashMap<>();

	/** Starts with a small set of sample employees. */
	public EmployeeServiceImpl() {
		this(List.of(
				new Employee("1", "Abrham Fantaye"),
				new Employee("2", "Sara Kebede"),
				new Employee("3", "Daniel Tesfaye")));
	}

	public EmployeeServiceImpl(Collection<Employee> initial) {
		for (Employee e : initial) {
			employees.put(e.getId(), e);
		}
	}

	@Override
	public Employee getEmployeeById(String id) throws EmployeeNotFoundException {
		if (id == null || id.isBlank()) {
			throw new EmployeeNotFoundException("Employee id is required");
		}
		Employee employee = employees.get(id.trim());
		if (employee == null) {
			throw new EmployeeNotFoundException("No employee with id " + id);
		}
		return employee;
	}

	@Override
	public List<Employee> listEmployees() {
		List<Employee> result = new ArrayList<>(employees.values());
		result.sort((a, b) -> a.getId().compareTo(b.getId()));
		return result;
	}
}
