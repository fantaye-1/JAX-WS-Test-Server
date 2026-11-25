package com.fantaye.employeemanagement;

import com.fantaye.employeemanagement.service.EmployeeServiceImpl;

import jakarta.xml.ws.Endpoint;

/**
 * Publishes the employee SOAP service on the JDK's built-in HTTP server.
 * <p>
 * The address defaults to {@value #DEFAULT_ADDRESS}; override it with the
 * first program argument or the {@code EMPLOYEE_SERVICE_URL} environment variable.
 */
public final class EmployeeServer {

	public static final String DEFAULT_ADDRESS = "http://localhost:8080/EmployeeService";

	private EmployeeServer() {
	}

	static String resolveAddress(String[] args, String envValue) {
		if (args.length > 0 && !args[0].isBlank()) {
			return args[0];
		}
		if (envValue != null && !envValue.isBlank()) {
			return envValue;
		}
		return DEFAULT_ADDRESS;
	}

	public static Endpoint publish(String address) {
		return Endpoint.publish(address, new EmployeeServiceImpl());
	}

	public static void main(String[] args) {
		String address = resolveAddress(args, System.getenv("EMPLOYEE_SERVICE_URL"));
		Endpoint endpoint = publish(address);
		Runtime.getRuntime().addShutdownHook(new Thread(endpoint::stop));
		System.out.println("Employee service running. WSDL: " + address + "?wsdl");
	}
}
