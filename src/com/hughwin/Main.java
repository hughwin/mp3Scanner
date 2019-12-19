package com.hughwin;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception{
        System.out.println(args.length);
        if (args.length == 0) {
            throw new Exception("Please specify a file");
        }
        if (args.length > 1){
            throw new Exception(("You have specified too many files"));
        }
        Path path = Paths.get(args[0]);
        if (Files.notExists(path)){
            throw new FileNotFoundException("File not found at location");
        }

    }
}
