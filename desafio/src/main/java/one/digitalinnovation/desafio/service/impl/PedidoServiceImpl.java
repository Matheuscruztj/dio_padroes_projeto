package one.digitalinnovation.desafio.service.impl;

import one.digitalinnovation.desafio.model.Endereco;
import one.digitalinnovation.desafio.model.EnderecoRepository;
import one.digitalinnovation.desafio.model.Pedido;
import one.digitalinnovation.desafio.model.PedidoRepository;
import one.digitalinnovation.desafio.service.PedidoService;
import one.digitalinnovation.desafio.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.get();
    }

    @Override
    public void inserir(Pedido pedido) {
        salvarPedidoComCep(pedido);
    }

    @Override
    public void atualizar(Long id, Pedido pedido) {
        Optional<Pedido> clienteBd = pedidoRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarPedidoComCep(pedido);
        }
    }

    @Override
    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

    private void salvarPedidoComCep(Pedido pedido) {
        String cep = pedido.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        pedido.setEndereco(endereco);

        pedidoRepository.save(pedido);
    }
}
