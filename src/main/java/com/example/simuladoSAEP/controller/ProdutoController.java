package com.example.simuladoSAEP.controller;

import com.example.simuladoSAEP.dto.ProdutoDTO;
import com.example.simuladoSAEP.model.Produto;
import com.example.simuladoSAEP.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping
        public ResponseEntity<List<Produto>> listarTodos(){
            return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable(value = "id") Long id){
        Optional<Produto> produto0= produtoRepository.findById(id);
        if (produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return  ResponseEntity.status(HttpStatus.OK).body(produto0.get());
    }

    @PostMapping
    public ResponseEntity<Produto> salvar (@Valid @RequestBody ProdutoDTO produtoDTO) {
        var produto = new Produto();
        BeanUtils.copyProperties(produtoDTO, produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id,
                                            @RequestBody @Valid ProdutoDTO produtoDTO) {
        Optional<Produto> produto0 = produtoRepository.findById(id);
        if (produto0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");

        }
        var produto = produto0.get();
        BeanUtils.copyProperties(produtoDTO,produto);
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable(value = "id") Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoRepository.delete(produto.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto excluido com sucesso!");
    }
}
