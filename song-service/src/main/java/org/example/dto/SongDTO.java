package org.example.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SongDTO {
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
    @Pattern(regexp = "\\d{2}:[0-5]\\d", message = "must be in format mm:ss with leading zeros.")
    String duration;
    @NotNull
    @Pattern(regexp = "(19|20)\\d{2}", message = "must be in YYYY format between 1900 and 2099")
    String year;
}
