package com.msi.gestordocumental.repos;

import com.msi.gestordocumental.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface OfficeRepository extends JpaRepository<Office, Integer> {

    @Query(value = "SELECT doc.* FROM Office AS doc WHERE (" +
            "doc.author_id = ?1 OR " +
            "doc.id_office IN(SELECT red.office_id from reader_permission red WHERE red.departament_id = ?2 or red.unit_id = ?3 or red.user_id = ?1) OR " +
            "doc.id_office IN(SELECT coa.office_id from co_author_permission coa WHERE coa.departament_id = ?2 or coa.unit_id = ?3 or coa.user_id = ?1) ) AND ( " +
            "doc.id_office IN(SELECT MAX(o.id_office) id_office FROM Office o GROUP BY o.name, o.author_id) AND doc.state = 1) ",
            nativeQuery = true)
    List<Office> getAllOfficeByUser(String idUser, Integer idDepartament, Integer idUnit);

    @Query(value = "SELECT * FROM office AS doc WHERE (doc.author_id = ?1) AND " +
            "( doc.id_office IN(SELECT MAX(o.id_office) id_office FROM Office o GROUP BY o.name, o.author_id) ) AND ( doc.state = 1 )",
            nativeQuery = true)
    List<Office> getAllMyOffice(String idUser);

    @Query(value = "SELECT * FROM office AS doc WHERE ( " +
            "doc.id_office IN(SELECT coa.office_id from co_author_permission coa WHERE coa.departament_id = ?1) OR " +
            "doc.id_office IN(SELECT red.office_id from reader_permission red WHERE red.departament_id = ?1) " +
            ") AND ( doc.id_office IN(SELECT max(id_office) id_office FROM Office GROUP BY name, author_id) AND doc.state = 1)",
            nativeQuery = true)
    List<Office> getAllOfficeByDepartament(Integer id_departament);

    @Query(value = "SELECT * FROM office AS doc WHERE ( " +
            "doc.id_office IN(SELECT coa.office_id from co_author_permission coa WHERE coa.unit_id = ?1) OR " +
            "doc.id_office IN(SELECT red.office_id from reader_permission red WHERE red.unit_id = ?1) " +
            ") AND ( doc.id_office IN(SELECT max(id_office) id_office FROM Office GROUP BY name, author_id) AND doc.state = 1)",
            nativeQuery = true)
    List<Office> getAllOfficeByUnit(Integer id_unit);

    @Query(value = "SELECT COUNT(*) FROM office AS doc WHERE doc.name = ?1",
            nativeQuery = true)
    Integer getVersionCount(String name);

    @Query(value = "SELECT doc.* FROM office AS doc WHERE doc.name = ?1", nativeQuery = true)
    public List<Office> getAllOfficeVersionsr(String OfficeName);

    @Query(value = "SELECT doc.author_id FROM office AS doc WHERE doc.name = ?1 AND doc.version=1", nativeQuery = true)
    public String getOfficeAuthor(String OfficeName);

    @Query(value = "SELECT doc.id_office FROM office AS doc WHERE doc.name = ?1 AND doc.version=1", nativeQuery = true)
    public Integer getfirstVersionID(String OfficeName);
    @Transactional
    @Modifying
    @Query(value = "UPDATE office SET state = 0 WHERE id_office = ?1",
            nativeQuery = true)
    void deactivateOfficeById(Integer idOffice);

    @Query(value = "SELECT * FROM Office WHERE ( " +
            "date_created BETWEEN isnull(?1, CAST('1753-01-01 0:0:0.0' AS DATETIME)) AND isnull(?2, CAST('9999-12-31 23:59:59.998' AS DATETIME)) " +
            ") AND ( id_office in (SELECT max(id_office) id_office FROM Office Group by name, author_id) )" +
            "ORDER BY Name ASC",
            nativeQuery = true)
    List<Office> filterOfficeByDates(String startRangeDate, String endRangeDate);

    @Query(value = "SELECT id_office FROM office AS doc WHERE doc.name = ?1 and doc.version=?2",nativeQuery = true)
    Integer getID(String name, Integer version);
}

