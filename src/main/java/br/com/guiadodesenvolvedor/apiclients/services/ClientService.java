package br.com.guiadodesenvolvedor.apiclients.services;



import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.guiadodesenvolvedor.apiclients.dtos.ClientDTO;
import br.com.guiadodesenvolvedor.apiclients.entities.Client;
import br.com.guiadodesenvolvedor.apiclients.repositories.ClientRepository;
import br.com.guiadodesenvolvedor.apiclients.services.exceptions.DataBaseException;
import br.com.guiadodesenvolvedor.apiclients.services.exceptions.ResourceNotFoundException;


@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional
	public ClientDTO findById(Long id) {
		Optional<Client> obj= repository.findById(id);
		Client entity=obj.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado!"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO obj) {
		Client entity = new Client();
		dtoFromEntity(obj,entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	

	@Transactional
	public ClientDTO update(Long id, ClientDTO obj) {
		try {
			Client entity = repository.getOne(id);
			dtoFromEntity(obj,entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		} catch (EntityExistsException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id not found"+id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		
	}
	}
	
	private void dtoFromEntity(ClientDTO obj, Client entity) {
		entity.setName(obj.getName());
		entity.setBirthDate(obj.getBirthDate());
		entity.setCpf(obj.getCpf());
		entity.setChildren(obj.getChildren());
		entity.setIncome(obj.getIncome());
		
	}

}
