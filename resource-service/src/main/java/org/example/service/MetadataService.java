package org.example.service;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.dto.SongDTO;
import org.example.service.validate.ResourceServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    private ResourceServiceValidator validator;

    public SongDTO parseSongDTO(byte[] mp3) throws IOException, TikaException, SAXException {
        InputStream inputstream = new ByteArrayInputStream(mp3);
        Mp3Parser.parse(inputstream, handler, metadata, pcontext);
        SongDTO songDTO =  SongDTO.builder()
                .name(metadata.get(NAME))
                .album(metadata.get(ALBUM))
                .artist(metadata.get(ARTIST))
                .year(metadata.get(YEAR))
                .duration(parseDuration((metadata.get(DURATION))))
                .build();
        validator.validateSong(songDTO);
        return songDTO;
    }

    private String parseDuration(String duration) {
        if (duration.isBlank()) return null;
        int secs = Integer.parseInt(duration.split("\\.")[0]);
        return String.format("%02d:%02d", secs/60, secs - (secs/60)*60);
    }

}
