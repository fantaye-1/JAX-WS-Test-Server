package com.fantaye.employeemanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fantaye.employeemanagement.domain.Employee;

class EmployeeServiceImplTest {

	private final EmployeeServiceImpl service = new EmployeeServiceImpl(List.of(
			new Employee("2", "Sara"),
			new Employee("1", "Abrham")));

	@Test
	void findsEmployeeByRequestedId() throws EmployeeNotFoundException {
		assertEquals(new Employee("2", "Sara"), service.getEmployeeById("2"));
		assertEquals(new Employee("1", "Abrham"), service.getEmployeeById(" 1 "));
	}

	@Test
	void unknownIdThrows() {
		EmployeeNotFoundException e = assertThrows(EmployeeNotFoundException.class,
				() -> service.getEmployeeById("99"));
		assertEquals("No employee with id 99", e.getMessage());
	}

	@Test
	void blankOrNullIdThrows() {
		assertThrows(EmployeeNotFoundException.class, () -> service.getEmployeeById(" "));
		assertThrows(EmployeeNotFoundException.class, () -> service.getEmployeeById(null));
	}

	@Test
	void listsEmployeesSortedById() {
		assertEquals(List.of(new Employee("1", "Abrham"), new Employee("2", "Sara")),
				service.listEmployees());
	}

	@Test
	void defaultConstructorHasSampleData() throws EmployeeNotFoundException {
		EmployeeServiceImpl defaults = new EmployeeServiceImpl();
		assertEquals(3, defaults.listEmployees().size());
		assertEquals("Abrham Fantaye", defaults.getEmployeeById("1").getName());
	}
}
