
package br.com.construmize.construmize.controller;

import br.com.construmize.construmize.dto.request.CriarObraRequest;
import br.com.construmize.construmize.dto.response.ObraResponse;
import br.com.construmize.construmize.service.ObraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras")
public class ObraController {

    private final ObraService obraService;

    public ObraController(ObraService obraService) {
        this.obraService = obraService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObraResponse criar(
            @Valid @RequestBody CriarObraRequest requisicao
    ) {
        return obraService.criar(requisicao);
    }

    @GetMapping
    public List<ObraResponse> listarTodas() {
        return obraService.listarTodas();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        obraService.excluir(id);
    }
}
