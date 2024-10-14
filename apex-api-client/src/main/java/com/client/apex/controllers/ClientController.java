package com.client.apex.controllers;

import com.client.apex.entities.ClientDTO;
import com.client.apex.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/massive")
    public ResponseEntity<List<ClientDTO>> getClients(@RequestBody List<Long> clientIds) {
        List<ClientDTO> clients = clientService.getClientsByIds(clientIds);
        return ResponseEntity.ok(clients);
    }
}
