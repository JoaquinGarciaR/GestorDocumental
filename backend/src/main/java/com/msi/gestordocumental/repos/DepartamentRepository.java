package com.msi.gestordocumental.repos;

import com.msi.gestordocumental.entities.Departament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentRepository extends JpaRepository<Departament, Integer> {
}
