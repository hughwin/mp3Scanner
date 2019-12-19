package com.hughwin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.newDirectoryStream;
import static java.nio.file.Files.notExists;


public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println(args.length);
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
                System.out.println(entry.getFileName());
            }
        }catch (IOException x){
            System.err.println();
        }


    }
}
