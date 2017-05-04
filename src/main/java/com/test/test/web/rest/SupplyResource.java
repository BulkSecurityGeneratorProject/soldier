package com.test.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.test.domain.Supply;

import com.test.test.repository.SupplyRepository;
import com.test.test.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Supply.
 */
@RestController
@RequestMapping("/api")
public class SupplyResource {

    private final Logger log = LoggerFactory.getLogger(SupplyResource.class);

    private static final String ENTITY_NAME = "supply";
        
    private final SupplyRepository supplyRepository;

    public SupplyResource(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    /**
     * POST  /supplies : Create a new supply.
     *
     * @param supply the supply to create
     * @return the ResponseEntity with status 201 (Created) and with body the new supply, or with status 400 (Bad Request) if the supply has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/supplies")
    @Timed
    public ResponseEntity<Supply> createSupply(@RequestBody Supply supply) throws URISyntaxException {
        log.debug("REST request to save Supply : {}", supply);
        if (supply.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new supply cannot already have an ID")).body(null);
        }
        Supply result = supplyRepository.save(supply);
        return ResponseEntity.created(new URI("/api/supplies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /supplies : Updates an existing supply.
     *
     * @param supply the supply to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated supply,
     * or with status 400 (Bad Request) if the supply is not valid,
     * or with status 500 (Internal Server Error) if the supply couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/supplies")
    @Timed
    public ResponseEntity<Supply> updateSupply(@RequestBody Supply supply) throws URISyntaxException {
        log.debug("REST request to update Supply : {}", supply);
        if (supply.getId() == null) {
            return createSupply(supply);
        }
        Supply result = supplyRepository.save(supply);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, supply.getId().toString()))
            .body(result);
    }

    /**
     * GET  /supplies : get all the supplies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of supplies in body
     */
    @GetMapping("/supplies")
    @Timed
    public List<Supply> getAllSupplies() {
        log.debug("REST request to get all Supplies");
        List<Supply> supplies = supplyRepository.findAll();
        return supplies;
    }

    /**
     * GET  /supplies/:id : get the "id" supply.
     *
     * @param id the id of the supply to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the supply, or with status 404 (Not Found)
     */
    @GetMapping("/supplies/{id}")
    @Timed
    public ResponseEntity<Supply> getSupply(@PathVariable Long id) {
        log.debug("REST request to get Supply : {}", id);
        Supply supply = supplyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(supply));
    }

    /**
     * DELETE  /supplies/:id : delete the "id" supply.
     *
     * @param id the id of the supply to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/supplies/{id}")
    @Timed
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        log.debug("REST request to delete Supply : {}", id);
        supplyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
