package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.services.CategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {

    private final CategoriaService categoriaService;

    public CategoriaResources(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Integer id){
        Optional<Categoria> categoria = categoriaService.find(id);
        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDto){
        Categoria categoria = categoriaService.fromDto(categoriaDto);
        categoria = categoriaService.save(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id){
        Categoria categoria = categoriaService.fromDto(categoriaDTO);
        categoria.setId(id);
        categoria = categoriaService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll(){
       List<Categoria> listCategoria = categoriaService.findAll();
       List<CategoriaDTO> listDto = listCategoria.stream().map(CategoriaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
           @RequestParam(value = "page", defaultValue = "0") Integer page,
           @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
           @RequestParam(value = "orderBy", defaultValue = "nome")  String orderBy,
           @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        Page<Categoria> list = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDto = list.map(CategoriaDTO::new);
        return ResponseEntity.ok().body(listDto);
    }



}

