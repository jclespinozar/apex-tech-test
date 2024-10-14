package com.worker.apex.services;

import com.worker.apex.models.Client;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<Client> fetchClientData(String clientId);
}
