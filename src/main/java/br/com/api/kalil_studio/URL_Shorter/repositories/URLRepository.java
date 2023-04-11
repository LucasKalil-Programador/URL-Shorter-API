package br.com.api.kalil_studio.URL_Shorter.repositories;

import br.com.api.kalil_studio.URL_Shorter.models.URLModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URLModel, Long> {

    @Query(value = "SELECT EXISTS (SELECT * from url u where u.originalurl = :originalURL " +
            "and u.expiration_date_time = u.registration_date_time and u.max_access = -1)" , nativeQuery = true)
    Long existsByOriginalURL(@Param("originalURL") String originalURL);

    @Query(value = "SELECT * from url where originalurl = :originalURL " +
            "and expiration_date_time = registration_date_time and max_access = -1 LIMIT 1", nativeQuery = true)
    Optional<URLModel> findFirstByOriginalURL(@Param("originalURL") String originalURL);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM url \n" +
            "WHERE (UTC_TIMESTAMP() > expiration_date_time AND expiration_date_time != registration_date_time)" +
            "OR (access_count >= max_access AND max_access != -1) LIMIT 256000", nativeQuery = true)
    Integer deleteExpiredRegisters();
}
