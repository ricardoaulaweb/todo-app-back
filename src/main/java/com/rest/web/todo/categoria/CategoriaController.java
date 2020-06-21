package com.rest.web.todo.categoria;

import com.rest.web.todo.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> getAll() {
        return this.categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Categoria categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o id :: " + id));
        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping
    public Categoria create(@RequestBody Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable(value = "id") Long id, @RequestBody Categoria categoriaAtualizada) throws ResourceNotFoundException {
        Categoria categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o id :: " + id));
        categoria.setDescricao(categoriaAtualizada.getDescricao());
        return ResponseEntity.ok().body(this.categoriaRepository.save(categoria));
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Categoria categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o id :: " + id));
        this.categoriaRepository.delete(categoria);
        return true;
    }
}
