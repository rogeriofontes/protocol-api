package br.com.unipac.protocoloapi.api.resources;

import br.com.unipac.protocoloapi.model.domain.Protocol;
import br.com.unipac.protocoloapi.model.service.ProtocolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/protocols")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcoloResources {

    private final ProtocolService protocolService;

    @GetMapping
    public ResponseEntity<List<Protocol>> findAll(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                                  @RequestParam(value = "size", defaultValue = "1") int size,
                                                  @RequestParam(required = false) String name) {
        //if (name != null) {
        if (!StringUtils.isEmpty(name)) {
            List<Protocol> allByName = protocolService.findAllByName(name, pageNumber, size);
            if (allByName.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            ResponseEntity.ok(allByName);
        } else {
            List<Protocol> all = protocolService.findAll(pageNumber, size);
            if (all.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(all);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Protocol> add(@Valid @RequestBody Protocol protocol) throws URISyntaxException {
        try {
            Protocol savedProtocol = protocolService.save(protocol);
            return ResponseEntity.created(new URI("/api/protocols/" + savedProtocol.getId())).body(savedProtocol);
        } catch (Exception ex) {
            // log exception first, then return Conflict (409)
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Protocol> updateContact(@Valid @RequestBody Protocol protocol,
                                                 @PathVariable(value = "id") long id) {
        try {
            //protocol.setId(id);
            protocolService.update(id, protocol);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            // log exception first, then return Not Found (404)
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Protocol> findById(@PathVariable long id) {
        try {
            Protocol protocol = protocolService.findById(id);
            return ResponseEntity.ok(protocol);  // return 200, with json body
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // return 404, with null body
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteContactById(@PathVariable long id) {
        try {
            protocolService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
