package employmentmanagement.services;

import javax.xml.ws.Endpoint;

public class Exporter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Endpoint.publish("http://localhost:8080/EmployeServere",new EmployeServiceImpl()); //url,
	}

}
