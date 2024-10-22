package com.sbm.loyalty.service.impl;

import com.sbm.loyalty.domain.Client;
import com.sbm.loyalty.repository.ClientRepository;
import com.sbm.loyalty.service.ClientService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.sbm.loyalty.domain.Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        LOG.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        LOG.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        LOG.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getFirstName() != null) {
                    existingClient.setFirstName(client.getFirstName());
                }
                if (client.getLastName() != null) {
                    existingClient.setLastName(client.getLastName());
                }
                if (client.getEmail() != null) {
                    existingClient.setEmail(client.getEmail());
                }
                if (client.getDateOfBirth() != null) {
                    existingClient.setDateOfBirth(client.getDateOfBirth());
                }
                if (client.getEmailVerified() != null) {
                    existingClient.setEmailVerified(client.getEmailVerified());
                }
                if (client.getSignUpDate() != null) {
                    existingClient.setSignUpDate(client.getSignUpDate());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        LOG.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Integer id) {
        LOG.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        LOG.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
