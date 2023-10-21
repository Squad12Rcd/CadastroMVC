package com.carros.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carros.model.Carro;

public interface CarroRepository  extends JpaRepository<Carro, Long> {

}
