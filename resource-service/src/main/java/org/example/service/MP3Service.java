package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.MP3;
import org.example.repository.Mp3Repository;
import org.example.exception.Mp3NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MP3Service {
    @Autowired
    private Mp3Repository mp3Repository;

    public MP3 findById(Integer id) {
        return mp3Repository.findById(id)
                .orElseThrow(()->new Mp3NotFoundException(id));
    }

    public void save(MP3 mp3) {
        mp3Repository.save(mp3);
    }

    public void deleteAll(List<Integer> ids) {
        mp3Repository.deleteAllById(ids);
    }
}
