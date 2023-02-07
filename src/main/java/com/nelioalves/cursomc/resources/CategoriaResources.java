package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {

    private final CategoriaService categoriaService;

    public CategoriaResources(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Integer id){
        Optional<Categoria> categoria = categoriaService.buscar(id);
        return ResponseEntity.ok().body(categoria);
    }
}
