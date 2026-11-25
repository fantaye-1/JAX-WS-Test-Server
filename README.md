# JAX-WS Test Server

A small SOAP web service for looking up employees, built with **Jakarta XML Web
Services** (the successor to JAX-WS, which was removed from the JDK in Java 11).
It runs on the JDK's built-in HTTP server, so no application server is needed.

## Operations

| Operation | Input | Result |
|---|---|---|
| `getEmployeeById` | `id` | The employee, or an `EmployeeNotFoundException` SOAP fault if the id is unknown or blank |
| `listEmployees` | none | All employees, sorted by id |

The service starts with three sample employees held in memory.

## Requirements

- Java 17+
- Maven 3.8+

## Build and test

```bash
mvn verify
```

The tests (JUnit 5) cover the service logic and also publish the real endpoint on a
free local port, check the WSDL and call it with a SOAP client.
GitHub Actions runs the same build on every push and pull request.

## Run

```bash
mvn -q compile exec:java
```

- Endpoint: `http://localhost:8080/EmployeeService`
- WSDL: `http://localhost:8080/EmployeeService?wsdl`

To use another address, pass it as an argument
(`mvn -q compile exec:java -Dexec.args="http://localhost:9090/EmployeeService"`)
or set `EMPLOYEE_SERVICE_URL`.

Try it with curl:

```bash
curl -s -H 'Content-Type: text/xml' http://localhost:8080/EmployeeService -d '
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:emp="http://service.employeemanagement.fantaye.com/">
  <soapenv:Body><emp:getEmployeeById><id>1</id></emp:getEmployeeById></soapenv:Body>
</soapenv:Envelope>'
```

## Project layout

```
src/main/java/com/fantaye/employeemanagement/
  EmployeeServer.java              publishes the endpoint
  domain/Employee.java             data returned to clients
  service/EmployeeService.java     SOAP contract (@WebService interface)
  service/EmployeeServiceImpl.java in-memory implementation
src/test/java/...                  unit and end-to-end tests
```

## Tech

Java 17, Jakarta XML Web Services 4 (Metro `jaxws-rt`), JUnit 5, Maven, GitHub Actions.
