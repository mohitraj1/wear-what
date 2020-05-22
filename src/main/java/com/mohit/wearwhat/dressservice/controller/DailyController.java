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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohit.wearwhat.dressservice.model.Garment;
import com.mohit.wearwhat.dressservice.repository.GarmentRepository;

@RestController
@RequestMapping("/today")
public class DailyController {
	
	@Autowired GarmentRepository garmentRepository;
	
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

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<Object> displayMongoDocs() {
		List<Garment> garments = garmentRepository.findAll();
		return ResponseEntity.ok().body(garments);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ResponseEntity<Object> displayGarmentDetails(@RequestParam(value = "name") String garName) {
		Garment garment = garmentRepository.findByName(garName);
		return ResponseEntity.ok().body(garment);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ResponseEntity<Object> addGarmentToDB() {
		Garment garment = new Garment();
		garment.setBrand("Kelvin Klien");
		garment.setColor("Yellow");
		garment.setDescription("Yellow Kelvin client t shirt ON " + System.currentTimeMillis());
		garment.setName("Yellow stripes");
		garment.setPlaceOfPurchase("Pittsburgh");
		garment.setPrice(755.55);
		garment.setPurchaseDate("31/05/2010");
		Garment garment1 = garmentRepository.save(garment);
		return ResponseEntity.ok().body(garment1);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE  )
	public ResponseEntity<Object> addNew(@ModelAttribute Garment garment) throws Exception, IOException {	
		Garment garment1 = garmentRepository.save(garment);
		return ResponseEntity.ok().body(garment1);
	}
}
