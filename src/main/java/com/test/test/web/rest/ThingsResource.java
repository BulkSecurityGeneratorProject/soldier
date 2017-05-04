package com.test.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.test.domain.Arm;
import com.test.test.domain.Supply;
import com.test.test.domain.Things;

import com.test.test.repository.ThingsRepository;
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
 * REST controller for managing Things.
 */
@RestController
@RequestMapping("/api")
public class ThingsResource {

    private final Logger log = LoggerFactory.getLogger(ThingsResource.class);

    private static final String ENTITY_NAME = "things";
        
    private final ThingsRepository thingsRepository;

    public ThingsResource(ThingsRepository thingsRepository) {
        this.thingsRepository = thingsRepository;
    }

    /**
     * POST  /things : Create a new things.
     *
     * @param things the things to create
     * @return the ResponseEntity with status 201 (Created) and with body the new things, or with status 400 (Bad Request) if the things has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/things")
    @Timed
    public ResponseEntity<Things> createThings(@RequestBody Things things) throws URISyntaxException {
        log.debug("REST request to save Things : {}", things);
        if (things.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new things cannot already have an ID")).body(null);
        }
        Things result = thingsRepository.save(things);
        return ResponseEntity.created(new URI("/api/things/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /things : Updates an existing things.
     *
     * @param things the things to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated things,
     * or with status 400 (Bad Request) if the things is not valid,
     * or with status 500 (Internal Server Error) if the things couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/things")
    @Timed
    public ResponseEntity<Things> updateThings(@RequestBody Things things) throws URISyntaxException {
        log.debug("REST request to update Things : {}", things);
        if (things.getId() == null) {
            return createThings(things);
        }
        Things result = thingsRepository.save(things);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, things.getId().toString()))
            .body(result);
    }

    /**
     * GET  /things : get all the things.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of things in body
     */
    @GetMapping("/things")
    @Timed
    public List<Things> getAllThings() {
        log.debug("REST request to get all Things");
        List<Things> things = thingsRepository.findAll();
        return things;
    }

    /**
     * GET  /things/:id : get the "id" things.
     *
     * @param id the id of the things to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the things, or with status 404 (Not Found)
     */
    @GetMapping("/things/{id}")
    @Timed
    public ResponseEntity<Things> getThings(@PathVariable Long id) {
        log.debug("REST request to get Things : {}", id);
        Things things = thingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(things));
    }

    /**
     * DELETE  /things/:id : delete the "id" things.
     *
     * @param id the id of the things to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/things/{id}")
    @Timed
    public ResponseEntity<Void> deleteThings(@PathVariable Long id) {
        log.debug("REST request to delete Things : {}", id);
        thingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/getSupply/{belong}")
    @Timed
    public List<Object> getSupply(@PathVariable int belong){
    	return thingsRepository.findByBelong1(belong);
    }
    
    @GetMapping("/getArm/{belong}")
    @Timed
    public List<Object> getArm(@PathVariable int belong){
    	return thingsRepository.findByBelong2(belong);
    }
}
