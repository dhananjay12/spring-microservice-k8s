package com.mynotes.spring.cloud.eureka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/contactUs")
public class ContactUsController {
	
	@Autowired
	DiscoveryClient client;
	
	RestTemplate rest=new RestTemplate();
	
	@RequestMapping(value = "/getMailingAddress", method = RequestMethod.GET)
	@ResponseBody
	public String getContactUsDetails() {
		List<ServiceInstance> serviceList=client.getInstances("user-service");		
		if(serviceList!=null && serviceList.size()>0){
			System.out.println("Sevice list===>"+serviceList.size());
			String result=rest.getForObject(serviceList.get(0).getUri()+"/users/getPublicMailingAddress", String.class);
			return "Contact Address ==> "+ result;
		}
		return "Error: Please Try again later";
	}

}
