package com.sygneto.repository;
import com.sygneto.domain.Media;

import java.util.Set;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Media entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

	//Set<Media> findByItemItemId(Long itemId);

	Media findBymId(Long itemPriceId);

	//Set<Media> findByProductProductId(Long productId);

}
