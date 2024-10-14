package com.client.apex.services;

import com.client.apex.entities.Client;
import com.client.apex.entities.ClientDTO;
import com.client.apex.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<ClientDTO> getClientsByIds(List<Long> clientIds) {
        List<Client> clients = clientRepository.findByIdIn(clientIds);

        if (clients.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron clientes con los IDs proporcionados");
        }

        return clients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + id));

        return convertToDTO(client);
    }

    private ClientDTO convertToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setNombre(client.getNombre());
        dto.setDireccion(client.getDireccion());
        return dto;
    }
}
