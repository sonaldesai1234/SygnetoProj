package com.sygneto.service.impl;

import com.sygneto.service.EmployeeService;
import com.sygneto.domain.Employees;
import com.sygneto.domain.User;
import com.sygneto.repository.EmployeeRepository;
import com.sygneto.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Employees save(Employees employee) {
        log.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }
    /**
     * Get all the employees.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employees> findAll() {
    	
        log.debug("Request to get all Employees");
        return employeeRepository.findAll();
        
    }
    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
   @Override
    @Transactional(readOnly = true)
    public Optional<Employees> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        
        return employeeRepository.findById(id);
    }

   
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
	/*
	@Override
	public List<Employees> findAllOrderByFirstNameAsc() {
		Sort sort = new Sort(Sort.Direction.ASC, "first_name");
        return employeeRepository.findAllOrderByFirstNameAsc(sort);
	}*/
	@Override
	public List<Employees> findAllOrderByDateOfJoiningDesc() {
		Sort sort = new Sort(Sort.Direction.DESC, "date_of_joining");
        return employeeRepository.findAllOrderByDateOfJoiningDesc(sort);
	}
	/*@Override
	public List<Employees> findAllOrderByFirstNameAsc() {
		// TODO Auto-generated method stub
		return employeeRepository.findAllOrderByFirstNameAsc();
	}
*/
	@Override
	public Employees findByEmployeeId(Long employeeId) {
		return employeeRepository. findByEmployeeId(employeeId);
	}
	@Override
	public Long findByCount() {
		// TODO Auto-generated method stub
		return employeeRepository.findByCount();
	}
	
}
