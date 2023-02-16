package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //private final ClienteRepository clienteRepository;

//    public UserDetailsServiceImpl(ClienteRepository clienteRepository) {
//        this.clienteRepository = clienteRepository;
//    }

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserSpringSecurity(cliente.getId(),cliente.getEmail(),cliente.getSenha(),cliente.getPerfis());
    }
}
