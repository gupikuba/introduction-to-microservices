package org.example.controller;

import jakarta.validation.constraints.Min;
import org.apache.tika.exception.TikaException;
import org.example.repository.MP3;
import org.example.service.MP3Service;
import org.example.service.SongRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

@RestController
public class ResourceServiceController {
    @Autowired
    MP3Service mp3Service;
    @Autowired
    SongRestService songRestService;
    @PostMapping(value = "/resources", consumes = "audio/mpeg")
    ResponseEntity<Map<String, Integer>> createMp3(@RequestBody byte[]mp3) throws IOException, TikaException, SAXException{
        Map<String, Integer> songServiceResponse = songRestService.postToSongService(mp3);
        return new ResponseEntity<>(songServiceResponse, HttpStatus.OK);
    }

    @GetMapping("/resources/{id}")
    ResponseEntity<byte[]> getMp3(@PathVariable("id") @Min(1) Integer id) {
        MP3 mp3 = mp3Service.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(mp3.getMp3());
    }

    @DeleteMapping("/resources")
    ResponseEntity<Map<String, List<Integer>>> delete(@RequestParam("id")String idsString) {
        Map<String, List<Integer>> songServiceResponse = songRestService.delete(idsString);
        mp3Service.deleteAll(songServiceResponse.get("ids"));

        return new ResponseEntity<>(songServiceResponse, HttpStatus.OK);
    }

}
