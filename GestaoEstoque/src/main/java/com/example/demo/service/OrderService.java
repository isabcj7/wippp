package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // Criação de um pedido
    public Order createOrder(Order order) {
        // Verifica se o estoque existe
        if (!stockRepository.existsById(order.getStock().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque não encontrado");
        }

        // Verifica se o fornecedor existe
        if (!supplierRepository.existsById(order.getSupplier().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fornecedor não encontrado");
        }

        // Salva e retorna o pedido criado
        return orderRepository.save(order);
    }

    // Recupera todos os pedidos
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Recupera um pedido pelo ID
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    // Atualiza um pedido existente
    public Order updateOrder(Integer id, Order order) {
        // Busca o pedido existente pelo ID
        Order existingOrder = getOrderById(id);

        // Atualiza as informações do pedido
        existingOrder.setStock(order.getStock());
        existingOrder.setSupplier(order.getSupplier());
        existingOrder.setStatus(order.getStatus());

        // Salva as mudanças e retorna o pedido atualizado
        return orderRepository.save(existingOrder);
    }

    // Deleta um pedido pelo ID
    public void deleteOrder(Integer id) {
        // Verifica se o pedido existe antes de tentar excluir
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }

        // Exclui o pedido
        orderRepository.deleteById(id);
    }
}

