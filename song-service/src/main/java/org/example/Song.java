package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotNull()
    @Size(min = 1, max = 100, message = "must be 1-100 characters text")
    String name;
    @NotNull
    @Size(min = 1, max = 100, message = "must be 1-100 characters text")
    String artist;
    @NotNull
    @Size(min = 1, max = 100, message = "must be 1-100 characters text")
    String album;
    @NotNull
    @Pattern(regexp = "\\d{2}:[0-5]\\d", message = "must be in format mm:ss, with leading zeros.")
    String duration;
    @NotNull
    @Pattern(regexp = "(19|20)\\d{2}", message = "must be in YYYY format between 1900-2099")
//    @Max(value = 2099, message = "must be in YYYY format between 1900-2099")
    String year;
}
