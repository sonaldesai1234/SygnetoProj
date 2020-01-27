package com.sygneto.repository;
import com.sygneto.domain.GstSlabs;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GstSlabs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GstSlabsRepository extends JpaRepository<GstSlabs, Long> {


}
