package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Categoria categoria){
        categoria = categoriaService.save(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
