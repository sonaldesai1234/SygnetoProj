package com.sygneto.repository;

import com.sygneto.domain.Node;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Node entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

	List<Node> findByTreeId(Long id);

}
