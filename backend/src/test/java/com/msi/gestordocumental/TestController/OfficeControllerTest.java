package com.msi.gestordocumental.TestController;

import com.msi.gestordocumental.entities.Departament;
import com.msi.gestordocumental.entities.Office;
import com.msi.gestordocumental.entities.Unit;
import com.msi.gestordocumental.entities.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfficeControllerTest {

    private final Integer port = 8000;
    private final String BASE_URL = "http://localhost:";
    private final String CONTROLLER_ENDPOINT = "/api/v1/office";

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void TestPostOffice() throws Exception {
        URI uri = new URI(BASE_URL + port + CONTROLLER_ENDPOINT);
        Office o1 = new Office();
        o1.setValid(true);
        o1.setState(true);
        o1.setType("pdf");
        o1.setVersion(1);
        o1.setName("Plantilla1");

        User author = new User();
        author.setIdUser("11111111");
        o1.setLastModifier(author);
        o1.setAuthor(author);

        Departament departament = new Departament();
        departament.setIdDepartament(100);
        o1.setDepartament(departament);

        Unit unit = new Unit();
        unit.setIdUnit(100);
        o1.setUnit(unit);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Office> request = new HttpEntity<>(o1, headers);
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        assertEquals(200, result.getStatusCodeValue());
	}

    @Test
    public void TestGetOfficeById() throws Exception {
        int idOffice = 100;
        ResponseEntity<String> result = restTemplate.getForEntity(BASE_URL + port + CONTROLLER_ENDPOINT + '/' + idOffice, String.class);
        assertEquals(result.getStatusCodeValue(), 200);
        assertNotEquals(result.getBody(), null);
    }

    @Test
    public void TestGetAllOffices() throws Exception {
        ResponseEntity<String> result = restTemplate.getForEntity(BASE_URL + port + CONTROLLER_ENDPOINT, String.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    public void TestGetAllOfficesByAuthor() throws Exception {
        URI uri = new URI(BASE_URL + port + CONTROLLER_ENDPOINT+ "/author-user");
        User user = new User();
        user.setIdUser("11111111");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri,request, String.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    public void TestGetAllOfficesByUserAccess() throws Exception {
        URI uri = new URI(BASE_URL + port + CONTROLLER_ENDPOINT+ "/user");
        User user = new User();
        user.setIdUser("11111111");

        Departament departament = new Departament();
        departament.setIdDepartament(100);
        user.setDepartament(departament);

        Unit unit = new Unit();
        unit.setIdUnit(100);
        user.setUnit(unit);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri,request, String.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    public void TestGetAllOfficesByDepartament() throws Exception {
        URI uri = new URI(BASE_URL + port + CONTROLLER_ENDPOINT+ "/departament");
        User user = new User();
        user.setIdUser("11111111");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri,request, String.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    public void TestGetAllOfficesByUnit() throws Exception {
        URI uri = new URI(BASE_URL + port + CONTROLLER_ENDPOINT+ "/unit");
        User user = new User();
        user.setIdUser("11111111");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri,request, String.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

    @Test
    public void TestDesactivateOfficeById() throws Exception {
        URI uri = new URI(BASE_URL + port + CONTROLLER_ENDPOINT+ "/desactivate");
        Office office = new Office();
        office.setIdOffice(101);
        User user = new User();
        user.setIdUser("11111111");

        HashMap<Object, Object> body = new HashMap<>();
        body.put("user", user);
        body.put("office", office);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HashMap> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        assertEquals(result.getStatusCodeValue(), 200);
    }

}