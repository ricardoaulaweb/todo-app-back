package com.rest.web.todo.responsavel;

import com.rest.web.todo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/responsavel")
public class ResponsavelController {

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @GetMapping
    public List<Responsavel> getAll() {
        return this.responsavelRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responsavel> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Responsavel responsavel = this.responsavelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Responsavel não encontrada com o id :: " + id));
        return ResponseEntity.ok().body(responsavel);
    }

    @PostMapping
    public Responsavel create(@RequestBody Responsavel responsavel) {
        return this.responsavelRepository.save(responsavel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responsavel> update(@PathVariable(value = "id") Long id, @RequestBody Responsavel responsavelAtualizada) throws ResourceNotFoundException {
        Responsavel responsavel = this.responsavelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Responsavel não encontrada com o id :: " + id));
        responsavel.setNome(responsavelAtualizada.getNome());
        return ResponseEntity.ok().body(this.responsavelRepository.save(responsavel));
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Responsavel responsavel = this.responsavelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Responsavel não encontrada com o id :: " + id));
        this.responsavelRepository.delete(responsavel);
        return true;
    }
}
