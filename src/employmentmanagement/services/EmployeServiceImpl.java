package employmentmanagement.services;

import javax.jws.WebService;

import employemanagement.domain.Employe;

@WebService
public class EmployeServiceImpl implements EmployeServere {

	@Override
	public Employe getEmployeById(String id) {
		// TODO Auto-generated method stub
		return new Employe("1", "Ab fan");
	}

}
