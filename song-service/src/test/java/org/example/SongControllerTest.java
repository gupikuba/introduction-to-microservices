package org.example;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

class SongControllerTest {
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

}