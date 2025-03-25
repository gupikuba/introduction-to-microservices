package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.example.Song;
import org.example.dto.SongDTO;
import org.example.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("songs")
@AllArgsConstructor
@Validated
public class SongController {
    private final SongService songService;

    @PostMapping()
    ResponseEntity<Map<String, Integer>> saveSong(@RequestBody @Valid SongDTO songDTO) {
        Song song = songService.save(songDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("id",song.getId()));
    }

    @GetMapping("/{id}")
    ResponseEntity<SongDTO> getSong(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(songService.convertToSongDTO(songService.findById(id)));
    }

    @DeleteMapping()
    ResponseEntity<?> delete(@RequestParam("id")String idsString) {
        List<Integer> deleted = songService.deleteByIds(idsString);
        return new ResponseEntity<>(Map.of("ids",deleted), HttpStatus.OK);
    }
}
