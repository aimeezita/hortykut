package com.github.hortykut.hortykut.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.hortykut.hortykut.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public List<Produto> findByProduto(String produto);
	
}