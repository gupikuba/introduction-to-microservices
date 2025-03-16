package org.example.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Mp3Repository extends CrudRepository<MP3, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM MP3 m WHERE m.id = ?1")
    int customDeleteById(Integer id);
}
