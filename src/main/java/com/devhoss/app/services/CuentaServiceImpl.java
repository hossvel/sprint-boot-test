package com.devhoss.app.services;

import com.devhoss.app.models.Cuenta;
import com.devhoss.app.models.Banco;
import com.devhoss.app.repositories.IBancoRepository;
import com.devhoss.app.repositories.ICuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Component
public class CuentaServiceImpl implements ICuentaService{

    private ICuentaRepository icuentaRepository;

    private IBancoRepository ibancoRepository;

    @Autowired
    public CuentaServiceImpl(ICuentaRepository cuentaRepository,IBancoRepository bancoRepository) {
        this.icuentaRepository = cuentaRepository;
        this.ibancoRepository = bancoRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return icuentaRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = ibancoRepository.findById(bancoId).orElseThrow();
        return banco.getTotalTransferencias();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = icuentaRepository.findById(cuentaId).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    @Transactional
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = icuentaRepository.findById(numCuentaOrigen).orElseThrow();
        cuentaOrigen.debito(monto);
        icuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = icuentaRepository.findById(numCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        icuentaRepository.save(cuentaDestino);

        Banco banco = ibancoRepository.findById(bancoId).orElseThrow();
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        ibancoRepository.save(banco);
    }
}
