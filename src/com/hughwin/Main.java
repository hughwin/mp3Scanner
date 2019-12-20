package com.hughwin;

import com.mpatric.mp3agic.Mp3File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        Path path = Paths.get(args[0]); //args[0]
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


        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:~/mydatabase;AUTO_SERVER=TRUE;INIT=runscript from '/Users/amanawinchester/mp3Scanner/create.sql'");
            PreparedStatement st = conn.prepareStatement("insert into SONGS (artist, year, album, title) values (?, ?, ?, ?);");

            for (Song song : repertoire) {
                st.setString(1, song.getArtist());
                st.setString(2, song.getYear());
                st.setString(3, song.getAlbum());
                st.setString(4, song.getTitle());
                st.addBatch();
            }

            int[] updates = st.executeBatch();
            System.out.println("Inserted [=" + updates.length + "] records into the database");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}






