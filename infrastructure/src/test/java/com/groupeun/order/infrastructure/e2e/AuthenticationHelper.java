package com.groupeun.order.infrastructure.e2e;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class AuthenticationHelper {

    public static final String KEYCLOAK_URL = "http://localhost:8090/realms/cookeasy/protocol/openid-connect/token";
    private static RestTemplate keycloakClient = new RestTemplate();

    public static String getAuthenticationToken() {
        // Get authentication token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("client_id", "order-app");
        map.add("username", "pierre.dupont@test.com");
        map.add("password", "azerty");
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Map> response = keycloakClient.exchange(KEYCLOAK_URL,
                HttpMethod.POST,
                entity,
                Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return "Bearer " + String.valueOf(response.getBody().get("access_token"));
    }
}
