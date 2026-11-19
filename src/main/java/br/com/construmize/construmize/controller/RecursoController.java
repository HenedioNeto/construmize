
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.dto.request.CriarRecursoRequest;
import br.com.construmize.construmize.dto.response.RecursoResponse;
import br.com.construmize.construmize.service.RecursoService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecursoResponse criar(
            @Valid @RequestBody CriarRecursoRequest requisicao
    ) {
        return recursoService.criar(requisicao);
    }

    @GetMapping
    public List<RecursoResponse> listarTodos() {
        return recursoService.listarTodos();
    }

    @DeleteMapping("/{recursoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long recursoId) {
        recursoService.excluir(recursoId);
    }
}
