package enset.ma.thymeleaf.repositories;

import enset.ma.thymeleaf.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findPatientByNomContainsIgnoreCase(String keyword, Pageable pageable);

    //En peut également utiliser HQL
    @Query("select p from Patient p where p.nom like :x")
    Page<Patient> chercherPatient(@Param("x") String keyword, Pageable pageable);
}
