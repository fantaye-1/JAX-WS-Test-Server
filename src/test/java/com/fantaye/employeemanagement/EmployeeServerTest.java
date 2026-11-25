package com.fantaye.employeemanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.xml.namespace.QName;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fantaye.employeemanagement.service.EmployeeNotFoundException;
import com.fantaye.employeemanagement.service.EmployeeService;

import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.Service;

/** Publishes the real endpoint on a free local port and calls it over SOAP. */
class EmployeeServerTest {

	private static Endpoint endpoint;
	private static String address;

	@BeforeAll
	static void start() throws IOException {
		int port;
		try (ServerSocket socket = new ServerSocket(0)) {
			port = socket.getLocalPort();
		}
		address = "http://localhost:" + port + "/EmployeeService";
		endpoint = EmployeeServer.publish(address);
	}

	@AfterAll
	static void stop() {
		if (endpoint != null) {
			endpoint.stop();
		}
	}

	@Test
	void servesWsdl() throws Exception {
		HttpResponse<String> response = HttpClient.newHttpClient().send(
				HttpRequest.newBuilder(URI.create(address + "?wsdl")).build(),
				HttpResponse.BodyHandlers.ofString());
		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("getEmployeeById"));
		assertTrue(response.body().contains("listEmployees"));
	}

	@Test
	void soapClientCanCallService() throws Exception {
		EmployeeService client = client();
		assertEquals("Abrham Fantaye", client.getEmployeeById("1").getName());
		assertEquals(3, client.listEmployees().size());
	}

	@Test
	void unknownIdComesBackAsFault() throws Exception {
		EmployeeService client = client();
		EmployeeNotFoundException e = assertThrows(EmployeeNotFoundException.class,
				() -> client.getEmployeeById("99"));
		assertTrue(e.getMessage().contains("99"));
	}

	@Test
	void addressResolution() {
		assertEquals("http://a/x", EmployeeServer.resolveAddress(new String[] {"http://a/x"}, "http://b/y"));
		assertEquals("http://b/y", EmployeeServer.resolveAddress(new String[0], "http://b/y"));
		assertEquals(EmployeeServer.DEFAULT_ADDRESS, EmployeeServer.resolveAddress(new String[0], null));
	}

	private static EmployeeService client() throws Exception {
		Service service = Service.create(new URL(address + "?wsdl"),
				new QName(EmployeeService.NAMESPACE, "EmployeeService"));
		return service.getPort(EmployeeService.class);
	}
}
