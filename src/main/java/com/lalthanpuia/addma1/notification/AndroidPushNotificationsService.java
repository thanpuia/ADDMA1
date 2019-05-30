package com.lalthanpuia.addma1.notification;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsService {
	 
	  //private static final String FIREBASE_SERVER_KEY = "AAAArLIdfLw:APA91bFAEsG3fJC74d5HTOCGQpaxfDpb_ut2nRYT8CPT6XIw1QAWlHf3WpL_AxEj_MWpMXXcS_Bxg2o1sjV8puuvNBA569HKyDNZgNzPe81O9qPYsYFBVzWVsM8ORe4L091bVSZMPSZx";
	  
	  private static final String FIREBASE_SERVER_KEY = "AAAAr8QqIDU:APA91bGWkfwdi5z5JDN0VAhr8uV5oj2hyEax3qvXEmx7SDu9tH0ApsJWsF2R3b6eumYvIyCnf2fdkuYRF78DJuEsp3xf_Xd2wYwHhrzWr-QV8E__JS8d9nXPWAX_Vq_0NzokrXPbblyY"; 
	  	

	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	  @Async
	  public CompletableFuture<String> send(HttpEntity<String> entity) {
	 
	    RestTemplate restTemplate = new RestTemplate();
	 
	    /**
	    https://fcm.googleapis.com/fcm/send
	    Content-Type:application/json
	    Authorization:key=FIREBASE_SERVER_KEY*/
	 
	    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
	    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
	    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
	    restTemplate.setInterceptors(interceptors);
	 
	    String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
	 
	    return CompletableFuture.completedFuture(firebaseResponse);
	  }
	}