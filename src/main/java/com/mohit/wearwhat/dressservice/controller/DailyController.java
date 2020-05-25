package com.mohit.wearwhat.dressservice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mohit.wearwhat.dressservice.model.Garment;
import com.mohit.wearwhat.dressservice.repository.GarmentRepository;
import com.mohit.wearwhat.dressservice.service.ImageService;

@RestController
@RequestMapping("/today")
public class DailyController {
	
	@Autowired GarmentRepository garmentRepository;
	
	@Autowired ImageService imageService; 
	
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
		List<MultipartFile> pics = garment.getPics();
		garment.setPics(null);
		Garment garment1 = garmentRepository.save(garment);
		for (MultipartFile pic : pics) {
			long start = System.currentTimeMillis();
//			garment.getPictures().add( new Binary(BsonBinarySubType.BINARY, pic.getBytes()));
			System.out.println("Posrig image " + (System.currentTimeMillis() - start));
			/*CompletableFuture<String> resp = */
			imageService.persistImage(pic, garment1.getId());
			System.out.println("After Posrig image " + (System.currentTimeMillis() - start));
		}
		return ResponseEntity.ok().body(garment1);
	}
}
