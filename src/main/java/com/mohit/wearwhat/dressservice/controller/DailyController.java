package com.mohit.wearwhat.dressservice.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohit.wearwhat.dressservice.model.Garment;
import com.mohit.wearwhat.dressservice.repository.GarmentRepository;

@RestController
@RequestMapping("/today")
public class DailyController {
	
	@Autowired GarmentRepository garmentRepository;
	
	@RequestMapping(value = "/history", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<Object> displayAll() {
		Path resourceDirectory = Paths.get("src","main","resources", "data");
		String content = null;
		Garment garment = null;
		try {
			content = new String ( Files.readAllBytes(resourceDirectory.resolve("newFile.txt")) );
			garment = new ObjectMapper().readValue(content, Garment.class);

		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	    return ResponseEntity.ok().body(garment);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<Object> listAll() throws Exception {
		String files = "";
//		Files.list(Paths.get("src","main","resources", "data")).forEach(System.out::println);
//		Files.list(Paths.get("src","main","resources", "data")).forEach(files.);
		Path start = Paths.get("src","main","resources", "data");
		Stream<Path> stream = Files.walk(start, Integer.MAX_VALUE);
	    List<String> collect = stream
	        .map(String::valueOf)
	        .sorted()
	        .collect(Collectors.toList());
	    collect.add("---end of list---"); //dummy checkin statement
		return ResponseEntity.ok().body(collect);
	}
	
	@RequestMapping(value = "/registertest", method = RequestMethod.GET)
	public ResponseEntity<Object> registerTest() {
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
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public ResponseEntity<Object> register(@ModelAttribute Garment garment) throws Exception, IOException {	
		Path resourceDirectory = Paths.get("src","main","resources", "data");
		File file = new File(resourceDirectory.toFile().getAbsolutePath() + "/" + garment.getName() + ".json");
		
//		garment.getPics().transferTo(resourceDirectory.resolve(garment.getPics().getOriginalFilename()));
		int index = 1;
		for (MultipartFile pic : garment.getPics()) {
			String ext = FilenameUtils.getExtension(garment.getPics().get(index-1).getOriginalFilename());
			String picName = garment.getName() + "-" + index++ + "." + ext; 
			pic.transferTo(resourceDirectory.resolve(picName));	
		}
		
		String content = null;
		try {
			if (file.createNewFile())
			{
			    System.out.println("File is created!");
			  
			} else {
			    System.out.println("File already exists.");
			}
			
			//Write Content
			ObjectMapper om = new ObjectMapper();
			content = om.writeValueAsString(garment);
		    FileWriter writer = new FileWriter(file);
		    writer.write(content);
		    writer.close();
		    
		    content = new String ( Files.readAllBytes(resourceDirectory.resolve(garment.getName() + ".json")) );
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.notFound().build();
		}
	    return ResponseEntity.ok().body(content);
	}

	@RequestMapping(value = "/mongo", method = RequestMethod.GET)
	public ResponseEntity<Object> displayMongoDocs() {
		List<Garment> total = garmentRepository.findAll();
		return ResponseEntity.ok().body(total);
		
	}
}
