package com.sygneto.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.sygneto.domain.TaskMgt;


/**
 * Spring Data  repository for the task_mgt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskMgtRepository extends JpaRepository<TaskMgt, Long> {

}
