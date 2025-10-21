package com.example.demo.service;

import com.example.demo.model.Stock;
import com.example.demo.model.User;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    public Stock createStock(Stock stock) {
        if (stock.getUser() != null && stock.getUser().getId() != null) {
            User user = userRepository.findById(stock.getUser().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
            stock.setUser(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário é obrigatório");
        }
        return stockRepository.save(stock);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Integer id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado"));
    }
}
