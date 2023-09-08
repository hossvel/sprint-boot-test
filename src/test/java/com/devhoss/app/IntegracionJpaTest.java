package com.devhoss.app;

import com.devhoss.app.models.Cuenta;
import com.devhoss.app.repositories.ICuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class IntegracionJpaTest {
    @Autowired
    ICuentaRepository icuentaRepository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = icuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Hossmell", cuenta.orElseThrow().getPersona());
    }

    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta = icuentaRepository.findByPersona("Hossmell");
        assertTrue(cuenta.isPresent());
        assertEquals("Hossmell", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }
}
