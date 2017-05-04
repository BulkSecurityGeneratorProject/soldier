package com.test.test.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.test.test.domain.Arm;

import com.test.test.repository.ArmRepository;
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
 * REST controller for managing Arm.
 */
@RestController
@RequestMapping("/api")
public class ArmResource {

    private final Logger log = LoggerFactory.getLogger(ArmResource.class);

    private static final String ENTITY_NAME = "arm";
        
    private final ArmRepository armRepository;

    public ArmResource(ArmRepository armRepository) {
        this.armRepository = armRepository;
    }

    /**
     * POST  /arms : Create a new arm.
     *
     * @param arm the arm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new arm, or with status 400 (Bad Request) if the arm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/arms")
    @Timed
    public ResponseEntity<Arm> createArm(@RequestBody Arm arm) throws URISyntaxException {
        log.debug("REST request to save Arm : {}", arm);
        if (arm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new arm cannot already have an ID")).body(null);
        }
        Arm result = armRepository.save(arm);
        return ResponseEntity.created(new URI("/api/arms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /arms : Updates an existing arm.
     *
     * @param arm the arm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated arm,
     * or with status 400 (Bad Request) if the arm is not valid,
     * or with status 500 (Internal Server Error) if the arm couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/arms")
    @Timed
    public ResponseEntity<Arm> updateArm(@RequestBody Arm arm) throws URISyntaxException {
        log.debug("REST request to update Arm : {}", arm);
        if (arm.getId() == null) {
            return createArm(arm);
        }
        Arm result = armRepository.save(arm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, arm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /arms : get all the arms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of arms in body
     */
    @GetMapping("/arms")
    @Timed
    public List<Arm> getAllArms() {
        log.debug("REST request to get all Arms");
        List<Arm> arms = armRepository.findAll();
        return arms;
    }

    /**
     * GET  /arms/:id : get the "id" arm.
     *
     * @param id the id of the arm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the arm, or with status 404 (Not Found)
     */
    @GetMapping("/arms/{id}")
    @Timed
    public ResponseEntity<Arm> getArm(@PathVariable Long id) {
        log.debug("REST request to get Arm : {}", id);
        Arm arm = armRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(arm));
    }

    /**
     * DELETE  /arms/:id : delete the "id" arm.
     *
     * @param id the id of the arm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/arms/{id}")
    @Timed
    public ResponseEntity<Void> deleteArm(@PathVariable Long id) {
        log.debug("REST request to delete Arm : {}", id);
        armRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
