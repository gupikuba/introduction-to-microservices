package org.example;

import org.apache.tika.exception.TikaException;
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
import java.util.Arrays;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    Mp3Repository mp3Repository;
    @Autowired
    MetadataService metadataService;

    @PostMapping("/resources")
    ResponseEntity<Integer> createMp3(@RequestBody byte[]mp3) {
        try {
            metadataService.parse(mp3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(mp3Repository.save(new MP3(mp3)).getId(), HttpStatus.OK);
    }

    @GetMapping("/resources/{id}")
    ResponseEntity<byte[]> getMp3(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(mp3Repository.findById(id).get().getMp3(), HttpStatus.OK);
    }

    @DeleteMapping("/resources")
    ResponseEntity<String> delete(@RequestParam("id")String idsString) {
        if (idsString.length() >= 200) {
            return new ResponseEntity<>("CSV string format is invalid or exceeds length restrictions.",
                    HttpStatus.BAD_REQUEST);
        }
        List<Integer> ids;
        try {
            ids = Arrays.stream(idsString.split(",")).map(Integer::parseInt).toList();
        }catch (NumberFormatException e){
            return new ResponseEntity<>("CSV string format is invalid or exceeds length restrictions.",
                    HttpStatus.BAD_REQUEST);
        }
        mp3Repository.deleteAllById(ids);
        return new ResponseEntity<>(idsString, HttpStatus.OK);
    }

}
