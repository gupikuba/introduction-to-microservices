package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MP3 {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Lob()
    byte[]mp3;

    public MP3(){}

    public MP3(byte[] mp3) {
        this.mp3 = mp3;
    }
}
