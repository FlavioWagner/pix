package br.com.flaviowagner.projetos.pix.controller;

import br.com.flaviowagner.projetos.pix.model.Contato;
import br.com.flaviowagner.projetos.pix.model.PSPInfo;
import br.com.flaviowagner.projetos.pix.request.RequestPIX;
import br.com.flaviowagner.projetos.pix.response.ResponsePix;
import br.com.flaviowagner.projetos.pix.response.ResponseToken;
import br.com.flaviowagner.projetos.pix.service.PixService;
import br.com.flaviowagner.projetos.pix.utils.PSPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PixController {
    @Autowired
    PixService pixService;

    @GetMapping
    public ResponseEntity<Contato> getInfo() {
        return ResponseEntity.ok(new Contato());
    }

    @PostMapping("/{psp}")
    public ResponseEntity<ResponseToken> getToken(@PathVariable("psp") String psp) {
        return ResponseEntity.ok(pixService.getToken(psp));
    }

    @GetMapping("/consultar/{psp}/{txid}")
    public ResponseEntity<ResponsePix> consultar(@PathVariable("psp") String psp, @PathVariable("txid") String txid) {
        return ResponseEntity.ok(pixService.consultar(psp, txid));
    }

    @PutMapping("/criar/{psp}/{txid}")
    public ResponseEntity<ResponsePix> criar(@PathVariable("psp") String psp, @PathVariable("txid") String txid, @RequestBody RequestPIX pix) {
        return ResponseEntity.ok(pixService.criar(psp, txid, pix));
    }

    @GetMapping("/psp")
    public ResponseEntity<List<PSPInfo>> listarPSPs() {
        return ResponseEntity.ok(PSPUtil.listarPSPs());
    }

    @GetMapping("/psp/{psp}")
    public ResponseEntity<?> getPsp(@PathVariable("psp") String psp) {
        return pixService.recuperarPSP(psp);
    }
}
