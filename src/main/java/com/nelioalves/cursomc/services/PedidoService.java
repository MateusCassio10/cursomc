package com.nelioalves.cursomc.services;

import com.nelioalves.cursomc.domain.*;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.repositories.*;
import com.nelioalves.cursomc.security.UserSpringSecurity;
import com.nelioalves.cursomc.services.exceptions.AuthorizationException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteRepository clienteRepository;

    private final EmailService emailService;
    private final CategoriaRepository categoriaRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         PagamentoRepository pagamentoRepository,
                         ProdutoRepository produtoRepository, ProdutoService produtoService,
                         ItemPedidoRepository itemPedidoRepository,
                         ClienteRepository clienteRepository, EmailService emailService,
                         CategoriaRepository categoriaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.produtoRepository = produtoRepository;
        this.produtoService = produtoService;
        this.itemPedidoRepository = itemPedidoRepository;
        this.clienteRepository = clienteRepository;
        this.emailService = emailService;
        this.categoriaRepository = categoriaRepository;
    }

    public Optional<Pedido> find(Integer id)  {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if(pedido.isEmpty()) {
            throw new ObjectNotFoundException("Objeto não encontrado! id: " + id
                    + ", Tipo: " + Categoria.class.getName());
        }
        return pedido;
    }

    public Pedido save(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteRepository.getById(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if(pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
            BoletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for (ItemPedido itemPedido : pedido.getItens()) {
            itemPedido.setDesconto(0.0);
            itemPedido.setProduto(produtoRepository.getById(itemPedido.getProduto().getId()));
            itemPedido.setPreco(itemPedido.getProduto().getPreco());
            itemPedido.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationHtmlEmail(pedido);
        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSpringSecurity user = UserServices.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteRepository.getById(user.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }
}
