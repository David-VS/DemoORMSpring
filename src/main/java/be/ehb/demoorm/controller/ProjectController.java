package be.ehb.demoorm.controller;

import be.ehb.demoorm.model.dao.EmployeeDAO;
import be.ehb.demoorm.model.tables.Employee;
import be.ehb.demoorm.model.tables.Project;
import be.ehb.demoorm.model.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final EmployeeDAO employeeDAO;
    private final ProjectDAO projectDAO;

    @Autowired
    public ProjectController(ProjectDAO projectDAO, EmployeeDAO employeeDAO) {
        this.projectDAO = projectDAO;
        this.employeeDAO = employeeDAO;
    }

    @GetMapping("")
    public Iterable<Project> getAllProjects(){
        return projectDAO.findAll();
    }

    @GetMapping("/search")
    public Iterable<Project> getAllProjectsByName(@RequestParam("q") String q){
        return projectDAO.findProjectByNameContainsIgnoreCase(q);
    }

    @PostMapping("")
    public ResponseEntity<String> createProject(@RequestParam("name") String naam){
        Project newProject = new Project(0, naam);

        projectDAO.save(newProject);

        return new ResponseEntity<String>("saved to database", HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<String> createEmployee(@RequestParam("firstname") String firstname,
                                                 @RequestParam("lastname") String lastname){
        Employee newEmployee = new Employee();
        newEmployee.setFirstname(firstname);
        newEmployee.setLastName(lastname);

        employeeDAO.save(newEmployee);

        return new ResponseEntity<String>("saved to database", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> addEmployeeToProject(@RequestParam("employeeid") int employeeid,
                                                       @RequestParam("projectid") int projectid){
        if (!employeeDAO.existsById(employeeid))
            return new ResponseEntity<String>("employee not found", HttpStatus.NOT_FOUND);
        if (!projectDAO.existsById(projectid))
            return new ResponseEntity<String>("project not found", HttpStatus.NOT_FOUND);

        Employee foundEmployee = employeeDAO.findById(employeeid).get();
        Project foundProject = projectDAO.findById(projectid).get();

        foundEmployee.setProject(foundProject);
        employeeDAO.save(foundEmployee);

        return new ResponseEntity<String>("added to project", HttpStatus.CREATED);
    }
}
