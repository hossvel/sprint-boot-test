package com.devhoss.app;

import com.devhoss.app.exceptions.DineroInsuficienteException;
import com.devhoss.app.models.Banco;
import com.devhoss.app.models.Cuenta;
import com.devhoss.app.repositories.IBancoRepository;
import com.devhoss.app.repositories.ICuentaRepository;
import com.devhoss.app.services.CuentaServiceImpl;
import com.devhoss.app.services.ICuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SprintBootTestApplicationTests {

	@Mock
	ICuentaRepository icuentaRepository;

	@Mock
	IBancoRepository ibancoRepository;

	@InjectMocks
	CuentaServiceImpl icuentaService;

	@BeforeEach
	void setUp() {
		System.out.println("Inicial Metodo");
		//icuentaRepository = mock(ICuentaRepository.class);
		//ibancoRepository = mock(IBancoRepository.class);
		//icuentaService = new CuentaServiceImpl(icuentaRepository, ibancoRepository);
		//para reiniciar la cuenta para cada metodo, debido a que falla cuando se modifica el mismo objeto
		//Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
        //Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
        //Datos.BANCO.setTotalTransferencias(0);
	}
	@Test
	void contextLoads1() {
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

		int total = icuentaService.revisarTotalTransferencias(1L);
		assertEquals(1, total);

		//verify de cuenta
		verify(icuentaRepository, times(3)).findById(1L);
		verify(icuentaRepository, times(3)).findById(2L);
		verify(icuentaRepository, times(2)).save(any(Cuenta.class));

		//verify de banco
		verify(ibancoRepository, times(2)).findById(1L);
		verify(ibancoRepository).save(any(Banco.class));

		verify(icuentaRepository, times(6)).findById(anyLong());
		verify(icuentaRepository, never()).findAll();
	}

	@Test
	void contextLoads2() {
		when(icuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(icuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(ibancoRepository.findById(1L)).thenReturn(Datos.crearBanco());

		//verificando los saldos de las cuentas inicialmente
		BigDecimal saldoOrigen = icuentaService.revisarSaldo(1L);
		BigDecimal saldoDestino = icuentaService.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		//transferir 1200  de una cuenta a otra pero no hay dinero

		assertThrows(DineroInsuficienteException.class, ()-> {
			icuentaService.transferir(1L, 2L, new BigDecimal("1200"), 1L);
		});

		//verificando los saldos despues de la transferencia
		saldoOrigen = icuentaService.revisarSaldo(1L);
		saldoDestino = icuentaService.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		int total = icuentaService.revisarTotalTransferencias(1L);
		assertEquals(0, total);

		//verify de cuenta
		verify(icuentaRepository, times(3)).findById(1L);
		verify(icuentaRepository, times(2)).findById(2L);
		verify(icuentaRepository, never()).save(any(Cuenta.class));

		//verify de banco
		verify(ibancoRepository, times(1)).findById(1L);
		verify(ibancoRepository,never()).save(any(Banco.class));

		verify(icuentaRepository, times(5)).findById(anyLong());
		verify(icuentaRepository, never()).findAll();
	}

	@Test
	void contextLoads3() {
		when(icuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());

		Cuenta cuenta1 = icuentaService.findById(1L);
		Cuenta cuenta2 = icuentaService.findById(1L);

		assertSame(cuenta1, cuenta2);
		assertTrue(cuenta1 == cuenta2);
		assertEquals("Hossmell", cuenta1.getPersona());
		assertEquals("Hossmell", cuenta2.getPersona());

		verify(icuentaRepository, times(2)).findById(1L);
	}
}
