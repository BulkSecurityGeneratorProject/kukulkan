package mx.infotec.dads.archetype.web.rest;

import com.codahale.metrics.annotation.Timed;
import mx.infotec.dads.archetype.domain.Trabajo;
import mx.infotec.dads.archetype.service.TrabajoService;
import mx.infotec.dads.archetype.web.rest.util.HeaderUtil;
import mx.infotec.dads.archetype.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Trabajo.
 */
@RestController
@RequestMapping("/api")
public class TrabajoResource {

    private final Logger log = LoggerFactory.getLogger(TrabajoResource.class);

    private static final String ENTITY_NAME = "trabajo";

    private final TrabajoService trabajoService;

    public TrabajoResource(TrabajoService trabajoService) {
        this.trabajoService = trabajoService;
    }

    /**
     * POST  /trabajos : Create a new trabajo.
     *
     * @param trabajo the trabajo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trabajo, or with status 400 (Bad Request) if the trabajo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trabajos")
    @Timed
    public ResponseEntity<Trabajo> createTrabajo(@RequestBody Trabajo trabajo) throws URISyntaxException {
        log.debug("REST request to save Trabajo : {}", trabajo);
        if (trabajo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new trabajo cannot already have an ID")).body(null);
        }
        Trabajo result = trabajoService.save(trabajo);
        return ResponseEntity.created(new URI("/api/trabajos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trabajos : Updates an existing trabajo.
     *
     * @param trabajo the trabajo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trabajo,
     * or with status 400 (Bad Request) if the trabajo is not valid,
     * or with status 500 (Internal Server Error) if the trabajo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trabajos")
    @Timed
    public ResponseEntity<Trabajo> updateTrabajo(@RequestBody Trabajo trabajo) throws URISyntaxException {
        log.debug("REST request to update Trabajo : {}", trabajo);
        if (trabajo.getId() == null) {
            return createTrabajo(trabajo);
        }
        Trabajo result = trabajoService.save(trabajo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trabajo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trabajos : get all the trabajos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trabajos in body
     */
    @GetMapping("/trabajos")
    @Timed
    public ResponseEntity<List<Trabajo>> getAllTrabajos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Trabajos");
        Page<Trabajo> page = trabajoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trabajos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trabajos/:id : get the "id" trabajo.
     *
     * @param id the id of the trabajo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trabajo, or with status 404 (Not Found)
     */
    @GetMapping("/trabajos/{id}")
    @Timed
    public ResponseEntity<Trabajo> getTrabajo(@PathVariable Long id) {
        log.debug("REST request to get Trabajo : {}", id);
        Trabajo trabajo = trabajoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trabajo));
    }

    /**
     * DELETE  /trabajos/:id : delete the "id" trabajo.
     *
     * @param id the id of the trabajo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trabajos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrabajo(@PathVariable Long id) {
        log.debug("REST request to delete Trabajo : {}", id);
        trabajoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
