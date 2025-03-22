package org.example.service;

import lombok.AllArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.example.repository.MP3;
import org.example.dto.SongDTO;
import org.example.exception.InvalidCSVException;
import org.example.service.validate.ResourceServiceValidator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class SongRestService {
    MetadataService metadataService;
    MP3Service mp3Service;
    ResourceServiceValidator validator;

    public Map<String, Integer> postToSongService(byte[] mp3) throws TikaException, IOException, SAXException {
        SongDTO songDTO = metadataService.parseSongDTO(mp3);
        RestClient restClient = RestClient.create();
        Map<String, Integer> response = restClient.post()
                .uri("http://localhost:8081/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .body(songDTO)
                .exchange((req, resp) -> {
                    if (resp.getStatusCode().equals(HttpStatus.OK)) {
                        return resp.bodyTo(new ParameterizedTypeReference<>() {
                        });
                    } else {
                        throw new RuntimeException();
                    }
                });
        mp3Service.save(new MP3(response.get("id"), mp3));
        return response;
    }

    public Map<String, List<Integer>> delete(String idsString) {
        validator.validateCsv(idsString);

        RestClient restClient = RestClient.create();
        return restClient.delete()
                .uri("http://localhost:8081/songs?id={ids}", idsString)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((req, resp) -> {
                    if (resp.getStatusCode().equals(HttpStatus.OK)) {
                        return resp.bodyTo(new ParameterizedTypeReference<>() {
                        });
                    } else {
                        throw new RuntimeException();
                    }
                });

    }
}
