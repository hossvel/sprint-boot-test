package com.devhoss.app.repositories;

import com.devhoss.app.models.Banco;

import java.util.List;

public interface IBancoRepository {
    List<Banco> findAll();
    Banco findById(Long id);
    void update(Banco banco);
}
