package com.mynotes.spring.cloud.eureka;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
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

    @Value("${data.file.path}")
    private String filePath;

    RestTemplate rest = new RestTemplate();

    @RequestMapping(value = "/getMailingAddress", method = RequestMethod.GET)
    @ResponseBody
    public String getContactUsDetails() {
        List<ServiceInstance> serviceList = client.getInstances("user-service");
        if (serviceList != null && serviceList.size() > 0) {
            System.out.println("Sevice list===>" + serviceList.size());
            String result = rest.getForObject(serviceList.get(0)
                .getUri() + "/users/getPublicMailingAddress", String.class);
            return "Contact Address ==> " + result;
        }
        return "Error: Please Try again later";
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public ResponseEntity<?> file() throws FileNotFoundException, IOException {
        File csvFile = new File(filePath + File.separator + "data.csv");
        Pattern pattern = Pattern.compile(",");
        List<FileData> data = new ArrayList<FileData>();
        try (BufferedReader in = new BufferedReader(new FileReader(csvFile));) {
            data = in.lines()
                .skip(1)
                .map(line -> {
                    String[] x = pattern.split(line);
                    return new FileData(Integer.parseInt(x[0]), x[1]);
                })
                .collect(Collectors.toList());

        }

        return ResponseEntity.ok(data);
    }

}
