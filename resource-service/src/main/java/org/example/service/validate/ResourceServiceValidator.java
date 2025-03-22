package org.example.service.validate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.example.dto.SongDTO;
import org.example.exception.InvalidCSVException;
import org.example.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class ResourceServiceValidator {
    @Autowired
    private Validator validator;

    public void validateSong(SongDTO songDTO) {
        Set<ConstraintViolation<SongDTO>> validation = validator.validate(songDTO);
        if (!validation.isEmpty()) {
            Map<String, String> details = new HashMap<>();
            for (var v : validation) {
                details.put(v.getPropertyPath().toString(), v.getMessage());
            }
            throw new ValidationException(details);
        }
    }
    public void validateCsv(String csv) {
        if (csv.length() >= 200) {
            throw new InvalidCSVException(String.format("CSV string is too long: received %d characters, maximum allowed is 200", csv.length()));
        }

        String[] ids = csv.split(",");

        for(String id : ids) {
            try {
                Integer.parseInt(id);
            } catch (NumberFormatException e) {
                throw new InvalidCSVException(String.format("Invalid ID format: %s. Only positive integers are allowed", id));
            }
        }
    }
}
