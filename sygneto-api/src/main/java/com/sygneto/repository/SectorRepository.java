package com.sygneto.repository;
import com.sygneto.domain.Sector;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectorRepository extends JpaRepository<Sector, String> {

	Object findBySectorName(String sectorName);

	void deleteBySectorName(String sectorName);

}
