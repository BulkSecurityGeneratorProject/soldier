package com.test.test.repository;

import com.test.test.domain.Player;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Player entity.
 */
@SuppressWarnings("unused")
public interface PlayerRepository extends JpaRepository<Player,Long> {
	
	@Query(value = "select * from player where mail=?1 AND jhi_password=?2 limit 1",nativeQuery=true)
	public List<Player> findByEmail(String mail,String password);
	
}
