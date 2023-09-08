package com.devhoss.app;

import com.devhoss.app.repositories.IBancoRepository;
import com.devhoss.app.repositories.ICuentaRepository;
import com.devhoss.app.services.CuentaServiceImpl;
import com.devhoss.app.services.ICuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class SprintBootTestApplicationTests {


	ICuentaRepository icuentaRepository;


	IBancoRepository ibancoRepository;


	ICuentaService icuentaService;


	@BeforeEach
	void setUp() {
		System.out.println("Inicial Metodo");
		icuentaRepository = mock(ICuentaRepository.class);
		ibancoRepository = mock(IBancoRepository.class);
		icuentaService = new CuentaServiceImpl(icuentaRepository, ibancoRepository);

	}
	@Test
	void contextLoads() {
		when(icuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(icuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(ibancoRepository.findById(1L)).thenReturn(Datos.crearBanco());

		//verificando los saldos de las cuentas inicialmente
		BigDecimal saldoOrigen = icuentaService.revisarSaldo(1L);
		BigDecimal saldoDestino = icuentaService.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		//transferir 100  de una cuenta a otra
		icuentaService.transferir(1L,2L,new BigDecimal("100"),1L);

		//verificando los saldos despues de la transferencia
		saldoOrigen = icuentaService.revisarSaldo(1L);
		saldoDestino = icuentaService.revisarSaldo(2L);
		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());
	}

}
