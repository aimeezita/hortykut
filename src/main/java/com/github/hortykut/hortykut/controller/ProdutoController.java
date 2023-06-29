package com.github.hortykut.hortykut.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.hortykut.hortykut.model.Produto;
import com.github.hortykut.hortykut.repository.CategoriaRepository;
import com.github.hortykut.hortykut.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")	
public class ProdutoController {
	
    @Autowired
    private ProdutoRepository produtoRepository ;
    
    @Autowired
	private CategoriaRepository categoriaRepository; 
    
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Produto> buscarProdutoPorId(@PathVariable Long id) {
      return produtoRepository.findById(id);
    }
  @PostMapping
	public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
		if (categoriaRepository.existsById(produto.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existe!", null);
}
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isPresent()) {
        	Produto produto = produtoExistente.get();
            produto.setProduto(produtoAtualizado.getProduto());
            produto.setResponsavel(produtoAtualizado.getResponsavel());
            produto.setValor(produtoAtualizado.getValor());
		 produto.setDescricao(produtoAtualizado.getDescricao());
		produto.setFoto(produtoAtualizado.getFoto());
            return produtoRepository.save(produto);
        } else {
            throw new IllegalArgumentException("ID do usuário inválido: " + id);
        }
    }
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }
}
