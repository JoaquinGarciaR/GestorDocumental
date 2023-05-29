package com.msi.gestordocumental.repos;

import com.msi.gestordocumental.entities.CoAuthorPermission;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CoAuthorPermissionRepository extends JpaRepository<CoAuthorPermission, Long> {

    @Query(value = "SELECT user_id FROM co_author_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public List<String> findCoAuthorsDocID(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from co_author_permission WHERE office_id = ?1", nativeQuery = true)
    Integer deleteRowsByDocumentID(Integer id);

    @Query(value = "SELECT COUNT(*) FROM  co_author_permission c WHERE c.office_id = ?1", nativeQuery = true)
    Integer countRowsDocumentID(Integer id);

    @Query(value = "SELECT top 1 departament_id FROM co_author_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public Integer getDepartmentID(Integer id);

    @Query(value = "SELECT TOP 1 unit_id FROM co_author_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public Integer getUnitID(Integer id);

    @Query(value = "SELECT COUNT(*) FROM  co_author_permission c WHERE c.office_id = ?1 and c.user_id = ?2", nativeQuery = true)
    Integer countPermisson(Integer docu, String id_user);

    @Query(value = "SELECT * FROM co_author_permission c WHERE c.office_id = ?1", nativeQuery = true)
    public List<CoAuthorPermission> findAllbyOffice(Integer id);
}