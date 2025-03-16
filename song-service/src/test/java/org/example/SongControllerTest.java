package org.example;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @Test
    void test() {
        Validator v = Validation.buildDefaultValidatorFactory().getValidator();
        Song s = new Song();
        s.setName("kub");
        s.setAlbum("alub");
        s.setArtist("pato");
        s.setDuration("22:13");
        s.setYear(2025);
        System.out.println(v.validate(s));
    }

    @Test
    void getTest() {
//        Song s1 = getSong(1);
//        Song s2 = getSong(2);
//        ResponseEntity<?> respo = restTemplate.postForEntity("http://localhost:" + port + "/songs",s1,String.class, new Object());
//        ResponseEntity<?> respo2 = restTemplate.postForEntity("http://localhost:" + port + "/songs",s2,String.class, new Object());
//        ResponseEntity<?> respo3 = restTemplate.d("http://localhost:" + port + "/songs/?id=1,2");
//        assert(respo2.getStatusCode().equals(HttpStatus.OK));
    }

    private Song getSong(int i) {
        Song s = new Song();
        s.setId(i);
        s.setName("kub");
        s.setAlbum("album");
        s.setArtist("pato");
        s.setDuration("22:13");
        s.setYear(2025);
        return s;
    }

}