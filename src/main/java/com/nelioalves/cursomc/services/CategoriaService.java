package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Optional<Categoria> buscar(Integer id)  {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if(categoria.isEmpty()) {
            throw new ObjectNotFoundException("Objeto não encontrado! id: " + id
            + ", Tipo: " + Categoria.class.getName());
        }
        return categoria;
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
}
