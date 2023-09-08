package com.devhoss.app.repositories;

import com.devhoss.app.models.Cuenta;

import java.util.List;

public interface ICuentaRepository {
    List<Cuenta> findAll();

    Cuenta findById(Long id);

    void update(Cuenta cuenta);
}
