package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
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
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Optional<Categoria> find(Integer id)  {
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

    public Categoria update(Categoria categoria) {
       Categoria newCategoria = categoriaRepository.getById(categoria.getId());
        updateData(newCategoria, categoria);
        return categoriaRepository.save(newCategoria);
    }
    private void updateData(Categoria categoriaNew, Categoria categoria) {
        categoriaNew.setNome(categoria.getNome());
    }

    public void delete(Integer id) {
        find(id);
        try {
            categoriaRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
        }
    }

    public List<Categoria> findAll() {
       return categoriaRepository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }

    public Categoria fromDto(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
    }
}
