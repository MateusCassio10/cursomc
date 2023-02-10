package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> find(Integer id)  {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()) {
            throw new ObjectNotFoundException("Objeto não encontrado! id: " + id
                    + ", Tipo: " + Categoria.class.getName());
        }
        return cliente;
    }
    public Optional<Cliente> findById(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            throw new ObjectNotFoundException("Objeto não encontrado! id: " + id
                    + ", Tipo: " + Categoria.class.getName());
        }
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        Cliente clienteNew = clienteRepository.getOne(cliente.getId());
        updateData(clienteNew, cliente);
        return clienteRepository.save(clienteNew);
    }
    private void updateData(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas!");
        }
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(),clienteDTO.getNome(),clienteDTO.getEmail(), null,null);
    }
}
