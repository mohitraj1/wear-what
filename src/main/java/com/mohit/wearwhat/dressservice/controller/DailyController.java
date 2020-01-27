package com.mohit.wearwhat.dressservice.controller;

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

}
