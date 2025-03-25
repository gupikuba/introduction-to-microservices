package org.example.service;

import lombok.AllArgsConstructor;
import org.example.Song;
import org.example.dto.SongDTO;
import org.example.repository.SongRepository;
import org.example.exception.InvalidCSVException;
import org.example.exception.MetadataAlreadyExistsException;
import org.example.exception.SongNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public Song save(SongDTO songDTO) {
        return save(convertToSongEntity(songDTO));
    }

    private Song convertToSongEntity(SongDTO songDTO) {
        return Song.builder()
                .id(songDTO.getId())
                .album(songDTO.getAlbum())
                .name(songDTO.getName())
                .year(songDTO.getYear())
                .artist(songDTO.getArtist())
                .duration(songDTO.getDuration())
                .build();
    }

    private Song save(Song song) {
        if (song.getId() != null && songRepository.existsById(song.getId())) {
            throw new MetadataAlreadyExistsException(song.getId());
        }
         return songRepository.save(song);
    }

    public Song findById(Integer id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isEmpty()) {
            throw new SongNotFoundException(id);
        }
        return song.get();
    }

    public List<Integer> deleteByIds(String idsString) {
       validateCsv(idsString);

        List<Integer> ids = parseIds(idsString);
        return ids.stream()
                .filter(id -> songRepository.customDeleteById(id) > 0)
                .collect(Collectors.toList());
    }

    private void validateCsv(String csv) {
        if (csv.length() >= 200) {
            throw new InvalidCSVException(String.format("CSV string is too long: received %d characters, maximum allowed is 200", csv.length()));
        }

        String[] ids = csv.split(",");

        for(String id : ids) {
            try {
                Integer.parseInt(id);
            } catch (NumberFormatException e) {
                throw new InvalidCSVException(String.format("Invalid ID format: %s. Only positive integers are allowed", id));
            }
        }
    }

    private List<Integer> parseIds(String idsString) {
        return Arrays.stream(idsString.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public SongDTO convertToSongDTO(Song song) {
        return SongDTO.builder()
                .id(song.getId())
                .album(song.getAlbum())
                .artist(song.getArtist())
                .year(song.getYear())
                .duration(song.getDuration())
                .name(song.getName())
                .build();
    }
}
