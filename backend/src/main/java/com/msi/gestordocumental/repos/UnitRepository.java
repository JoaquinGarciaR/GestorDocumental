package com.msi.gestordocumental.repos;

import com.msi.gestordocumental.entities.Unit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
    
    @Query(value = "SELECT * FROM Unit u WHERE u.departament_id = ?1", nativeQuery = true)
    public List<Unit> findByDepartament(Integer id);
}
