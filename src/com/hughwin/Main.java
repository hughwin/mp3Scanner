package com.hughwin;

import com.mpatric.mp3agic.Mp3File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import static java.nio.file.Files.newDirectoryStream;
import static java.nio.file.Files.notExists;


public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Please specify a file");
        }
        if (args.length > 1) {
            throw new IllegalArgumentException(("You have specified too many files"));
        }
        Path path = Paths.get(args[0]);
        if (notExists(path)) {
            throw new FileNotFoundException("File not found at " + path);
        }
        System.out.println("File found!");

        ArrayList<Song> repertoire = new ArrayList<Song>();

        try (DirectoryStream<Path> stream = newDirectoryStream(path, "*.mp3")) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName() + " is an MP3");
                Mp3File mp3File = new Mp3File(entry);
                System.out.println(mp3File.getLengthInMilliseconds());
                System.out.println(mp3File.getId3v2Tag().getArtist());
                repertoire.add(new Song(mp3File.getId3v2Tag().getArtist(), mp3File.getId3v2Tag().getYear(), mp3File.getId3v2Tag().getYear(), mp3File.getId3v2Tag().getTitle()));
            }
        } catch (IOException x) {
            System.err.println();
        }
        try (Connection conn = DriverManager.getConnection("jdbc:h2:~/mydatabase;AUTO_SERVER=TRUE;INIT=runscript from './create.sql'")) {
        } catch (Exception e) {
            System.err.println();
        }
        for (Song song : repertoire){

        }


    }
}






