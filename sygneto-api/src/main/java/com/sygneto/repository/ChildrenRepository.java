package com.sygneto.repository;

import com.sygneto.domain.Children;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Children entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> {

	void deleteByNextAction(long parseLong);

}
