package pl.denys.gateway_service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient = RestClient.builder().build();

    @GetMapping("/hello")
    public void hello() {
        return;
    }
}
