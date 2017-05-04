package com.test.test.repository;

import com.test.test.domain.Arm;
import com.test.test.domain.Supply;
import com.test.test.domain.Things;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.lang.Integer;

/**
 * Spring Data JPA repository for the Things entity.
 */
@SuppressWarnings("unused")
public interface ThingsRepository extends JpaRepository<Things,Long> {
	
	@Query(value="select things.id,supply.name,supply.price,supply.hp,supply.fatigue from supply inner join things on supply.id=things.pid where things.jhi_type=0 and things.belong=?1",nativeQuery=true)
	public List<Object> findByBelong1(long belong);
	
	@Query(value="select things.id,arm.name,arm.price,arm.attack,arm.defence from arm inner join things on arm.id=things.pid where things.jhi_type=1 and things.belong=?1",nativeQuery=true)
	public List<Object> findByBelong2(long belong);
}
