package com.mohit.wearwhat.dressservice.controller;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/today")
public class DailyController {
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<Object> showHistory() {
		Path resourceDirectory = Paths.get("src","main","resources", "data");
		String content = null;
		try {
			content = new String ( Files.readAllBytes(resourceDirectory.resolve("week1.txt")) );
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	    return ResponseEntity.ok().body(content);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ResponseEntity<Object> register() {
		Path resourceDirectory = Paths.get("src","main","resources", "data");
		File file = new File(resourceDirectory.toFile().getAbsolutePath() + "/newFile.txt");
		
		String content = null;
		try {
			if (file.createNewFile())
			{
			    System.out.println("File is created!");
			  
			} else {
			    System.out.println("File already exists.");
			}
			
			//Write Content
		    FileWriter writer = new FileWriter(file);
		    writer.write("new dress registered " + System.currentTimeMillis());
		    writer.close();
		    
		    content = new String ( Files.readAllBytes(resourceDirectory.resolve("newFile.txt")) );
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	    return ResponseEntity.ok().body(content);
	}

}
