package org.example;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
public class SongRestHandler {
    MetadataService metadataService;
    Gson gson;
    public Integer sendToSongService(byte[] mp3) throws TikaException, IOException, SAXException {
        SongDTO songDTO = metadataService.parseSongDTO(mp3);
        String json = gson.toJson(songDTO);
        RestClient restClient = RestClient.create();
        ResponseEntity<Map<String, Integer>> responseEntity = restClient.post()
                .uri("http://localhost:8081/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException();
        }
        return 2;
    }

}
