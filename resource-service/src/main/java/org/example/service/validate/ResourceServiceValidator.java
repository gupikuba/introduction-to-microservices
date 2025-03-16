package org.example.service.validate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.example.dto.SongDTO;
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

    public boolean areIdsForDeletionValid(String idsString) {
        return idsString.length() < 200 && idsString.matches("(\\d+,)*\\d+");
    }
}
