package org.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mp3Repository extends CrudRepository<MP3, Integer> {

}
