package org.example;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("songs")
@AllArgsConstructor
public class SongController {
    SongRepository songRepository;

    @PostMapping()
    ResponseEntity<Map<String, Integer>> saveSong(@RequestBody Song song) {
        Song saved = songRepository.save(song);
        return ResponseEntity.ok(Map.of("id",saved.getId()));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getSong(@PathVariable Integer id) {
        Song song = songRepository.findById(id).get();
        return ResponseEntity.ok(song);
    }

    @DeleteMapping()
    ResponseEntity<?> delete(@RequestParam("id")String idsString) {
        List<Integer> ids;
        try {
            ids = Arrays.stream(idsString.split(",")).map(Integer::parseInt).toList();
        }catch (NumberFormatException e){
            return new ResponseEntity<>("CSV string format is invalid or exceeds length restrictions.",
                    HttpStatus.BAD_REQUEST);
        }
        List<Integer> deleted = new ArrayList<>();
        for(Integer id : ids) {
            int count = songRepository.customDeleteById(id);
            if(count > 0) {
                deleted.add(id);
            }
        }

        return new ResponseEntity<>(Map.of("ids",deleted), HttpStatus.OK);
    }
}
