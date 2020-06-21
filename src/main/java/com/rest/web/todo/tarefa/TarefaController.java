package com.rest.web.todo.tarefa;

import com.rest.web.todo.categoria.Categoria;
import com.rest.web.todo.categoria.CategoriaRepository;
import com.rest.web.todo.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/tarefa")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Tarefa> findAll() {
        return this.tarefaRepository.findAll(Sort.by(Sort.Direction.ASC, "prazo", "createdDate"));
    }

    @GetMapping(params = "idCategoria")
    public List<Tarefa> findAllByCategoria(@RequestParam Long idCategoria) {
        return this.tarefaRepository.findAllByCategoria(idCategoria);
    }

    @GetMapping(params = "idResponsavel")
    public List<Tarefa> findAllByResponsavel(@RequestParam Long idResponsavel) {
        return this.tarefaRepository.findAllByResponsavel(idResponsavel);
    }

    @GetMapping(params = "concluido")
    public List<Tarefa> findAllByStatus(@RequestParam Boolean concluido) {
        return this.tarefaRepository.findAllByStatus(concluido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> getById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Tarefa tarefa = this.tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o id :: " + id));
        return ResponseEntity.ok().body(tarefa);
    }

    @PostMapping
    public Tarefa create(@RequestBody Tarefa tarefa) {
        return this.tarefaRepository.save(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> update(@PathVariable(value = "id") Long id, @RequestBody Tarefa tarefaAtualizada) throws ResourceNotFoundException {
        Long idCategoria = tarefaAtualizada.getCategoria().getId();
        Categoria categoria = this.categoriaRepository.findById(idCategoria).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o id :: " + idCategoria));
        Tarefa tarefa = this.tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o id :: " + id));

        tarefa.setPrazo(tarefaAtualizada.getPrazo());
        tarefa.setDescricao(tarefaAtualizada.getDescricao());
        tarefa.setConcluido(tarefaAtualizada.isConcluido());
        tarefa.setResponsavel(tarefaAtualizada.getResponsavel());
        tarefa.setCategoria(categoria);

        return ResponseEntity.ok().body(this.tarefaRepository.save(tarefa));
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Tarefa tarefa = this.tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o id :: " + id));
        this.tarefaRepository.delete(tarefa);
        return true;
    }
}
