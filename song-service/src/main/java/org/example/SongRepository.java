package org.example;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SongRepository extends CrudRepository<Song, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Song s WHERE s.metadataId = ?1")
    int customDeleteById(Integer id);
}
