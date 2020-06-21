package com.rest.web.todo.tarefa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Modifying
    @Transactional
    @Query("FROM Tarefa t WHERE t.categoria.id = :idCategoria ORDER BY t.prazo ASC, t.createdDate ASC")
    List<Tarefa> findAllByCategoria(@Param("idCategoria") Long idCategoria);

    @Modifying
    @Transactional
    @Query("FROM Tarefa t WHERE t.responsavel.id = :idResponsavel ORDER BY t.prazo ASC, t.createdDate ASC")
    List<Tarefa> findAllByResponsavel(@Param("idResponsavel") Long idResponsavel);

    @Modifying
    @Transactional
    @Query("FROM Tarefa t WHERE t.concluido = :concluido ORDER BY t.prazo ASC, t.createdDate ASC")
    List<Tarefa> findAllByStatus(@Param("concluido") Boolean concluido);
}
