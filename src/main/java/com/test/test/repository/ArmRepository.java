package com.test.test.repository;

import com.test.test.domain.Arm;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Arm entity.
 */
@SuppressWarnings("unused")
public interface ArmRepository extends JpaRepository<Arm,Long> {

}
