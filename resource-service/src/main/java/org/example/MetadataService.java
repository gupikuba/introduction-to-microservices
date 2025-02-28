package org.example;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class MetadataService {
    private static final String ARTIST = "xmpDM:artist";
    private static final String ALBUM = "xmpDM:album";
    private static final String NAME = "dc:title";
    private static final String DURATION = "xmpDM:duration";
    private static final String YEAR = "xmpDM:releaseDate";
    private final BodyContentHandler handler = new BodyContentHandler();
    private final Metadata metadata = new Metadata();
    private final ParseContext pcontext = new ParseContext();
    private final Mp3Parser Mp3Parser = new  Mp3Parser();
    @Autowired
    private Validator validator;
    public SongDTO parseSongDTO(byte[] mp3) throws IOException, TikaException, SAXException {
        InputStream inputstream = new ByteArrayInputStream(mp3);
        Mp3Parser.parse(inputstream, handler, metadata, pcontext);
        SongDTO songDTO =  SongDTO.builder()
                .name(metadata.get(NAME))
                .album(metadata.get(ALBUM))
                .artist(metadata.get(ARTIST))
                .year(metadata.get(YEAR).isBlank() ? null : Integer.parseInt(metadata.get(YEAR)))
                .duration(parseDuration((metadata.get(DURATION))))
                .build();
        validate(songDTO);
        return songDTO;
    }

    private void validate(SongDTO songDTO) {
        Set<ConstraintViolation<SongDTO>> validation = validator.validate(songDTO);
        if (!validation.isEmpty()) {
            Map<String, String> details = new HashMap<>();
            for (var v : validation) {
                details.put(v.getPropertyPath().toString(), v.getMessage());
            }
            throw new ValidationErrorResponse(details);
        }
    }

    private String parseDuration(String duration) {
        if (duration.isBlank()) return null;
        int secs = Integer.parseInt(duration.split("\\.")[0]);
        return String.format("%02d:%02d", secs/60, secs - (secs/60)*60);
    }

}
