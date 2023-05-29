package com.msi.gestordocumental.repos;

import com.msi.gestordocumental.entities.ReaderPermission;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReaderPermissionRepository extends JpaRepository<ReaderPermission, Long> {
    @Query(value = "SELECT user_id FROM reader_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public List<String> findReadersDocID(Integer id);

    @Query(value = "SELECT top 1 departament_id FROM reader_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public Integer getDepartmentID(Integer id);

    @Query(value = "SELECT TOP 1 unit_id FROM reader_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public Integer getUnitID(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from reader_permission WHERE office_id = ?1", nativeQuery = true)
    Integer deleteRowsByDocumentID(Integer id);

    @Query(value = "SELECT COUNT(*) FROM reader_permission c WHERE c.office_id = ?1", nativeQuery = true)
    Integer countRowsDocumentID(Integer id);

    @Query(value = "SELECT * FROM reader_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public List<ReaderPermission> findAllbyOffice(Integer id);
}
