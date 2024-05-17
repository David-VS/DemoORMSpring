package be.ehb.demoorm.model.dao;

import be.ehb.demoorm.model.tables.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeDAO extends CrudRepository<Employee, Integer> {

}
