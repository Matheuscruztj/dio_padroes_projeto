package one.digitalinnovation.desafio.service;

import one.digitalinnovation.desafio.model.Pedido;

public interface PedidoService {

    Iterable<Pedido> buscarTodos();

    Pedido buscarPorId(Long id);

    void inserir(Pedido pedido);

    void atualizar(Long id, Pedido pedido);

    void deletar(Long id);
}
