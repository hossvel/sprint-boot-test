package com.devhoss.app.services;

import com.devhoss.app.models.Cuenta;
import com.devhoss.app.models.Banco;
import com.devhoss.app.repositories.IBancoRepository;
import com.devhoss.app.repositories.ICuentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public class CuentaServiceImpl implements ICuentaService{
    private ICuentaRepository icuentaRepository;
    private IBancoRepository ibancoRepository;

    public CuentaServiceImpl(ICuentaRepository cuentaRepository, IBancoRepository bancoRepository) {
        this.icuentaRepository = cuentaRepository;
        this.ibancoRepository = bancoRepository;
    }
    @Override
    public Cuenta findById(Long id) {
        return icuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = ibancoRepository.findById(bancoId);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = icuentaRepository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = icuentaRepository.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        icuentaRepository.update(cuentaOrigen);

        Cuenta cuentaDestino = icuentaRepository.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        icuentaRepository.update(cuentaDestino);

        Banco banco = ibancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        ibancoRepository.update(banco);
    }
}
