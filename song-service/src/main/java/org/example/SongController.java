package org.example;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("songs")
@AllArgsConstructor
@Validated
public class SongController {
    SongService songService;

    @PostMapping()
    ResponseEntity<Map<String, Integer>> saveSong(@RequestBody @Valid Song song) {
        songService.save(song);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("id",song.getId()));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getSong(@PathVariable @Min(1) Integer id) {
        return ResponseEntity.ok(songService.findById(id));
    }

    @DeleteMapping()
    ResponseEntity<?> delete(@RequestParam("id")String idsString) {
        List<Integer> deleted = songService.deleteByIds(idsString);
        return new ResponseEntity<>(Map.of("ids",deleted), HttpStatus.OK);
    }
}
