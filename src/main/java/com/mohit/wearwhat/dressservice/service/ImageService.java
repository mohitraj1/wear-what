package com.mohit.wearwhat.dressservice.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mohit.wearwhat.dressservice.model.Garment;
import com.mohit.wearwhat.dressservice.repository.GarmentRepository;

@Service
public class ImageService {
	private final RestTemplate restTemplate;
	
	@Autowired GarmentRepository garmentRepository;
	
	@Value("${image.service.url}")
	private String IMAGE_SERVICE;

	public ImageService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
	@Async("threadPoolTaskExecutor")
    public CompletableFuture <String> persistImage(MultipartFile pic, String garId) throws InterruptedException, IOException {
//        logger.info("Looking up " + user);
        String url = String.format(IMAGE_SERVICE + "/images/add?garmentId=" + garId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//		body.add("file", pic.getBytes());
        ByteArrayResource resource = new ByteArrayResource(pic.getBytes()) {
            @Override
            public String getFilename() {
                return "capture.jpg";
            }
        };
		body.add("file", resource);
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		System.out.println("Before submit");
//		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        
//		String imageId = restTemplate.getForObject(url, String.class);
		System.out.println(response.getBody());
		String imageId = response.getBody();
		System.out.println(imageId);
        
		//update image id back in the garment
		Garment garment = garmentRepository.findById(garId).get();
		garment.getImages().add(imageId);
		garmentRepository.save(garment);
		
        return CompletableFuture.completedFuture(imageId);
    }
}
