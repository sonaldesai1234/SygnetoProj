package com.sygneto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.sygneto.domain.ActivityMgt;


/**
 * Spring Data  repository for the ActivityMgt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityMgtRepository extends JpaRepository<ActivityMgt, Long> {

	List<ActivityMgt> findByReferenceId(Long id);

}
