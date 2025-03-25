package org.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SongDTO {
    Integer id;
    @Size(min = 1, message = "must be present")
    String name;
    @Size(min = 1, message = "must be present")
    String artist;
    @Size(min = 1, message = "must be present")
    String album;
    @Size(min = 1, message = "must be present")
    String duration;
    @NotNull(message = "must be present")
    String year;
}
