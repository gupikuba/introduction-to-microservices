package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongService {
    private SongRepository songRepository;

    public void save(Song song) {
        if (song.getId() != null && songRepository.existsById(song.getId())) {
            throw new MetadataAlreadyExistsException(song.getId());
        }
         songRepository.save(song);
    }

    public Song findById(Integer id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isEmpty()) {
            throw new SongNotFoundException(id);
        }
        return song.get();
    }

    public List<Integer> deleteByIds(String idsString) {
        if (!areIdsForDeletionValid(idsString)) {
            throw new InvalidCSVException(idsString, HttpStatus.BAD_REQUEST.value());
        }
        List<Integer> ids = parseIds(idsString);
        return ids.stream()
                .filter(id -> songRepository.customDeleteById(id) > 0)
                .collect(Collectors.toList());
    }

    private boolean areIdsForDeletionValid(String idsString) {
        return idsString.length() < 200 && idsString.matches("(\\d+,)*\\d+");
    }

    private List<Integer> parseIds(String idsString) {
        return Arrays.stream(idsString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}
