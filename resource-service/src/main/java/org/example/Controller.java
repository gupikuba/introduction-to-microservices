package org.example;

import jakarta.validation.constraints.Min;
import org.apache.coyote.Response;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

@RestController
public class Controller {
    @Autowired
    Mp3Repository mp3Repository;
    @Autowired
    SongRestHandler songRestHandler;
    @PostMapping("/resources")
    ResponseEntity<Map<String, Integer>> createMp3(@RequestBody byte[]mp3) throws IOException, TikaException, SAXException{
        Integer id = songRestHandler.sendToSongService(mp3);
        return new ResponseEntity<>(Map.of("id", id), HttpStatus.OK);
    }

//200 OK – Resource retrieved successfully.
//400 Bad Request – The provided ID is invalid (e.g., contains letters, decimals, is negative, or zero).
//404 Not Found – Resource with the specified ID does not exist.
//500 Internal Server Error – An error occurred on the server.
    @GetMapping("/resources/{id}")
    ResponseEntity<byte[]> getMp3(@PathVariable("id") @Min(1) Integer id) {
        Optional<MP3> mp3 = mp3Repository.findById(id);
        if (mp3.isEmpty()) throw new Mp3NotFoundException();
        return new ResponseEntity<>(mp3Repository.findById(id).get().getMp3(), HttpStatus.OK);
    }

//200 OK – Request successful, resources deleted as specified.
//400 Bad Request – CSV string format is invalid or exceeds length restrictions.
//500 Internal Server Error – An error occurred on the server.
    @DeleteMapping("/resources")
    ResponseEntity<?> delete(@RequestParam("id")String idsString) {
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
        List<Integer> deleted = new ArrayList<>();
        RestClient restClient = RestClient.create();
        ResponseEntity<String> response = restClient.delete()
                .uri("http://localhost:8081/songs?id={ids}", idsString)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        for(Integer id : ids) {
//            restClient.delete().uri("http://localhost/8081/songs?")
            int count = mp3Repository.customDeleteById(id);
            if(count > 0) {
                deleted.add(id);
            }
        }

        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}
