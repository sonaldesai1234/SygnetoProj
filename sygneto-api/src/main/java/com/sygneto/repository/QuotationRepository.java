package com.sygneto.repository;
import com.sygneto.domain.Quotation;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Quotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {

	

}
