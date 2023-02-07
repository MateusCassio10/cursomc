package com.nelioalves.cursomc.resources;

import com.nelioalves.cursomc.domain.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar(){

        Categoria categoria1 = new Categoria(1,"informática");
        Categoria categoria2 = new Categoria(2,"escritório");

        List<Categoria> lista = new ArrayList<>();
        lista.add(categoria1);
        lista.add(categoria2);

        return lista;
    }
}
