package be.ehb.demoorm.model.dao;

import be.ehb.demoorm.model.tables.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectDAO extends CrudRepository<Project, Integer> {

    public List<Project> findProjectByNameContainsIgnoreCase(String search);
}
