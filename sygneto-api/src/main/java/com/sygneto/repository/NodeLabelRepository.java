package com.sygneto.repository;

import com.sygneto.domain.NodeLabel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NodeLabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodeLabelRepository extends JpaRepository<NodeLabel, Long> {

}
