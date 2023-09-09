package com.devhoss.app.controllers;

import com.devhoss.app.models.Cuenta;
import com.devhoss.app.models.TransaccionDto;
import com.devhoss.app.services.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private ICuentaService icuentaService;
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable Long id) {
        return icuentaService.findById(id);
    }
    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDto dto) {
        icuentaService.transferir(dto.getCuentaOrigenId(),
                dto.getCuentaDestinoId(),
                dto.getMonto(), dto.getBancoId());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con éxito!");
        response.put("transaccion", dto);

        return ResponseEntity.ok(response);
    }
}
