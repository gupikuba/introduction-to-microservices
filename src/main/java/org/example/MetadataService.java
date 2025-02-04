package org.example;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class MetadataService {

    void parse(byte[] mp3) throws IOException, TikaException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        InputStream inputstream = new ByteArrayInputStream(mp3);
        ParseContext pcontext = new ParseContext();

        //Mp3 parser
        Mp3Parser Mp3Parser = new  Mp3Parser();
        Mp3Parser.parse(inputstream, handler, metadata, pcontext);
        LyricsHandler lyrics = new LyricsHandler(inputstream,handler);

        while(lyrics.hasLyrics()) {
            System.out.println(lyrics.toString());
        }

        System.out.println("Contents of the document:" + handler.toString());
        System.out.println("Metadata of the document:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
    }

}
