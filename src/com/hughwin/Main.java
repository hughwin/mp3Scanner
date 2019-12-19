package com.hughwin;

import com.mpatric.mp3agic.Mp3File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.newDirectoryStream;
import static java.nio.file.Files.notExists;


public class Main {

    public static void main(String[] args) throws Exception{
        if (args.length == 0) {
            throw new IllegalArgumentException("Please specify a file");
        }
        if (args.length > 1){
            throw new IllegalArgumentException(("You have specified too many files"));
        }
        Path path = Paths.get(args[0]);
        if (notExists(path)){
            throw new FileNotFoundException("File not found at " + path);
        }
        System.out.println("File found!");
        try (DirectoryStream<Path> stream =  newDirectoryStream(path, "*.mp3")){
            for (Path entry : stream){
                System.out.println(entry.getFileName() + " is an MP3");
                Mp3File mp3File = new Mp3File(entry);
                System.out.println(mp3File.getLengthInMilliseconds());
                System.out.println(mp3File.getId3v2Tag().getArtist());
            }
        }catch (IOException x){
            System.err.println();
        }


    }



    public static class Song {

        private final String artist;
        private final String year;
        private final String album;
        private final String title;

        public Song(String artist, String year, String album, String title) {
            this.artist = artist;
            this.year = year;
            this.album = album;
            this.title = title;
        }

        public String getArtist() {
            return artist;
        }

        public String getYear() {
            return year;
        }

        public String getAlbum() {
            return album;
        }

        public String getTitle() {
            return title;
        }
    }
}
