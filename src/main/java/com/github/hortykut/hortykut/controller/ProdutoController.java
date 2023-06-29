package com.github.hortykut.hortykut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.hortykut.hortykut.model.Produto;
import com.github.hortykut.hortykut.repository.ProdutoRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "*", allowedHeaders = "*")	
public class ProdutoController {
	
    @Autowired
    private ProdutoRepository produtoRepository ;
    
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
