package com.msi.gestordocumental.TestController;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.msi.gestordocumental.entities.Office;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {
    private final Integer port = 8000;
    private final String BASE_URL = "http://localhost:";
    private final String CONTROLLER_ENDPOINT = "/files";
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void TestGetOfficeVersions() throws Exception {
        Office office = new Office();
        office.setIdOffice(101);
        office.setName("Report8VO 11 de Octubre (1).pdf");
            ResponseEntity<String> result = restTemplate.getForEntity(BASE_URL + port + CONTROLLER_ENDPOINT+ "/getVersions/"+ office.getName(), String.class);
            assertEquals(result.getStatusCodeValue(), 200);

    }

    

    @Test
    public void TestVisualize() throws Exception {
        Office office = new Office();
        office.setIdOffice(106);
        office.setName("4 Minuta03_29-04-2022.pdf");
            ResponseEntity<String> result = restTemplate.getForEntity(BASE_URL + port + CONTROLLER_ENDPOINT+ "/visualizeFilexx/"+ office.getIdOffice(), String.class);
            assertEquals(result.getStatusCodeValue(), 200);

    }
}
