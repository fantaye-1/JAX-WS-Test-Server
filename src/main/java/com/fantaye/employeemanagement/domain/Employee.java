package com.fantaye.employeemanagement.domain;

import java.util.Objects;

/**
 * An employee as exposed by the SOAP service.
 * <p>
 * JAXB needs a public no-arg constructor and getters/setters to marshal this
 * class into the SOAP response.
 */
public class Employee {

	private String id;
	private String name;

	public Employee() {
	}

	public Employee(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Employee)) {
			return false;
		}
		Employee other = (Employee) o;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "Employee[id=" + id + ", name=" + name + "]";
	}
}
