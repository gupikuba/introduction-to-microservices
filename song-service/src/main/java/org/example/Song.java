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
    Integer metadataId;
    @JsonProperty("id")
    Integer resourceId;
    @NotNull
    @Size(min = 1, max = 100)
    String name;
    @NotNull
    @Size(min = 1, max = 100)
    String artist;
    @NotNull
    @Size(min = 1, max = 100)
    String album;
    @NotNull
    @Pattern(regexp = "\\d{2}:\\d{2}")
    String duration;
    @NotNull
    @Min(1900)
    @Max(2099)
    Integer year;
}
