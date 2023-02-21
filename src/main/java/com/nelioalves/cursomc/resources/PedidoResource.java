package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.services.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoResource(PedidoService pedidoService,
                          PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Integer id){
        Optional<Pedido> pedido = pedidoService.find(id);
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){
        pedido = pedidoService.save(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").
                buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante")  String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction){
        Page<Pedido> list = pedidoService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }
}
