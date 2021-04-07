package com.tts.mapsapp.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tts.mapsapp.deserialization.GeocodingResponse;
import com.tts.mapsapp.deserialization.Location;

@Service
public class MapService {
	
	@Value("${api_key}")
	private String apiKey;
	
	public void addCoordinates(Location location) {
		String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + 
		location.getCity() + "," + location.getState() + "&key=" + apiKey;
		
		RestTemplate restTemplate = new RestTemplate();
		
		GeocodingResponse response = 
				restTemplate.getForObject(url, GeocodingResponse.class);
		
		Location coordinates = response.getResults().get(0).getGeometry().getLocation();
		location.setLat(coordinates.getLat());
		location.setLng(coordinates.getLng());
	
	}
	
	public Location getRandomLocation() {
		
		Random random = new Random(); 
		
        double latitude = random.nextDouble() * 180-90; 
        double longitude = random.nextDouble() * 90; 
        
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude 
        + "&key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
		GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);
		Location location = response.getResults().get(0).getGeometry().getLocation();
        return location;
	}

}
