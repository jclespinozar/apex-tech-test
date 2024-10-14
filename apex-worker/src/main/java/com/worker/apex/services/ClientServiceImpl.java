package com.worker.apex.services;

import com.worker.apex.models.Client;
import com.worker.apex.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${app.apex.apex-api-client.url}")
    private String apexApiClientUrl;

    @Override
    public Mono<Client> fetchClientData(String clientId) {
        return webClientBuilder.build()
                .get()
                .uri(apexApiClientUrl, clientId)
                .retrieve()
                .bodyToMono(Client.class)
                .onErrorResume(e -> {
                    //log.error("Error fetching client data for clientId: {}", clientId, e);
                    return Mono.empty();
                });
    }
}
