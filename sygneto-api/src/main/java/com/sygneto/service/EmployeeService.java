package com.sygneto.service;

import com.sygneto.domain.Employees;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

/**
 * Service Interface for managing {@link Employee}.
 */
public interface EmployeeService {

    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    Employees save(Employees employee);

    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
//   @Query(value="SELECT C FROM employees ORDER BY firstName ASC")
    List<Employees> findAll();
   

    /**
     * Get the "id" employee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Employees> findOne(Long id);

    /**
     * Delete the "id" employee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    
	

	List<Employees> findAllOrderByDateOfJoiningDesc();

	Employees findByEmployeeId(Long employeeId);

	Long findByCount();

	//List<Employees> findAllOrderByFirstNameAsc();

}
