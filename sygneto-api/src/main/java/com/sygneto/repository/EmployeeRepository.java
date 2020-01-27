package com.sygneto.repository;
import com.mysql.cj.Session;
import com.sygneto.domain.Employees;
import com.sygneto.domain.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository

public interface EmployeeRepository extends JpaRepository<Employees, Long> {
	
	Employees findByUserName(String userName);

	Employees findByEmail(String email);

	List<Employees> findByStatus(String status);

	

	@Query("FROM Employees")
	List<Employees> findAllOrderByDateOfJoiningDesc(Sort sort);

	Employees findByEmployeeId(Long employeeId);

	@Query(value="select count(*) from Employees")
	Long findByCount();

	
	/*@Query("from Employees order by first_name DESC")
	List<Employees> findAllOrderByFirstNameAsc();*/

	
	
	


	
}