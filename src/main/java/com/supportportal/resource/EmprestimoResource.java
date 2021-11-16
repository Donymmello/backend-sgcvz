package com.supportportal.resource;

import com.supportportal.domain.Emprestimo;
import com.supportportal.exception.domain.EmprestimoNotFoundException;
import com.supportportal.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("http://localhost:4200")

public class EmprestimoResource {
    private final EmprestimoService emprestimoService;

    @Autowired
    public EmprestimoResource(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;

    }
    @PostMapping("/emp_register")
    public ResponseEntity<Emprestimo> register(@RequestBody Emprestimo emprestimo) throws EmprestimoNotFoundException {
        Emprestimo newEmprestimo = emprestimoService.register(emprestimo.getUsername(), emprestimo.getMontante(), emprestimo.getData_emp(), emprestimo.getData_pag(), emprestimo.getData_rec());
        return new ResponseEntity<>(newEmprestimo, OK);
    }

    @PostMapping("/emp_add")
    public ResponseEntity<Emprestimo> addNewUser(@RequestParam("username") String username,
                                                 @RequestParam("montante") double montante,
                                                 @RequestParam("data_emp") long data_emp,
                                                 @RequestParam("data_pag") long data_pag,
                                                 @RequestParam("data_rec") long data_rec) {
        Emprestimo newEmprestimo = emprestimoService.addNewEmprestimo(username, montante, data_emp, data_pag, data_rec);
        return new ResponseEntity<>(newEmprestimo, OK);
    }

    @GetMapping("/emp_find/{id}")
    public ResponseEntity<Emprestimo> getEmprestimo(@PathVariable("id") long id) {
        Emprestimo emprestimo = emprestimoService.findEmprestimoById(id);
        return new ResponseEntity<>(emprestimo, OK);
    }

    @GetMapping("/emp_find/{username}")
    public ResponseEntity<Emprestimo> getEmprestimo(@PathVariable("username") String username) {
        Emprestimo emprestimo = emprestimoService.findEmprestimoByUsername(username);
        return new ResponseEntity<>(emprestimo, OK);
    }

    @GetMapping("/emp_list")
    public ResponseEntity<List<Emprestimo>> getAllEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.getEmprestimos();
        return new ResponseEntity<>(emprestimos, OK);
    }
}

