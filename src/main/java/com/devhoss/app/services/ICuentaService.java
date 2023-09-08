package com.devhoss.app.services;

import com.devhoss.app.models.Cuenta;

import java.math.BigDecimal;

public interface ICuentaService {
    Cuenta findById(Long id);

    int revisarTotalTransferencias(Long bancoId);

    BigDecimal revisarSaldo(Long cuentaId);

    void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto,
                    Long bancoId);
}
