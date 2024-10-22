package com.sbm.loyalty.web.rest;

import com.sbm.loyalty.domain.LoyaltyPoints;
import com.sbm.loyalty.repository.LoyaltyPointsRepository;
import com.sbm.loyalty.service.LoyaltyPointsService;
import com.sbm.loyalty.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sbm.loyalty.domain.LoyaltyPoints}.
 */
@RestController
@RequestMapping("/api/loyalty-points")
public class LoyaltyPointsResource {

    private static final Logger LOG = LoggerFactory.getLogger(LoyaltyPointsResource.class);

    private static final String ENTITY_NAME = "loyaltyPoints";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoyaltyPointsService loyaltyPointsService;

    private final LoyaltyPointsRepository loyaltyPointsRepository;

    public LoyaltyPointsResource(LoyaltyPointsService loyaltyPointsService, LoyaltyPointsRepository loyaltyPointsRepository) {
        this.loyaltyPointsService = loyaltyPointsService;
        this.loyaltyPointsRepository = loyaltyPointsRepository;
    }

    /**
     * {@code POST  /loyalty-points} : Create a new loyaltyPoints.
     *
     * @param loyaltyPoints the loyaltyPoints to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loyaltyPoints, or with status {@code 400 (Bad Request)} if the loyaltyPoints has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LoyaltyPoints> createLoyaltyPoints(@RequestBody LoyaltyPoints loyaltyPoints) throws URISyntaxException {
        LOG.debug("REST request to save LoyaltyPoints : {}", loyaltyPoints);
        if (loyaltyPoints.getId() != null) {
            throw new BadRequestAlertException("A new loyaltyPoints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        loyaltyPoints = loyaltyPointsService.save(loyaltyPoints);
        return ResponseEntity.created(new URI("/api/loyalty-points/" + loyaltyPoints.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, loyaltyPoints.getId().toString()))
            .body(loyaltyPoints);
    }

    /**
     * {@code PUT  /loyalty-points/:id} : Updates an existing loyaltyPoints.
     *
     * @param id the id of the loyaltyPoints to save.
     * @param loyaltyPoints the loyaltyPoints to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loyaltyPoints,
     * or with status {@code 400 (Bad Request)} if the loyaltyPoints is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loyaltyPoints couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoyaltyPoints> updateLoyaltyPoints(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoyaltyPoints loyaltyPoints
    ) throws URISyntaxException {
        LOG.debug("REST request to update LoyaltyPoints : {}, {}", id, loyaltyPoints);
        if (loyaltyPoints.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loyaltyPoints.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loyaltyPointsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        loyaltyPoints = loyaltyPointsService.update(loyaltyPoints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loyaltyPoints.getId().toString()))
            .body(loyaltyPoints);
    }

    /**
     * {@code PATCH  /loyalty-points/:id} : Partial updates given fields of an existing loyaltyPoints, field will ignore if it is null
     *
     * @param id the id of the loyaltyPoints to save.
     * @param loyaltyPoints the loyaltyPoints to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loyaltyPoints,
     * or with status {@code 400 (Bad Request)} if the loyaltyPoints is not valid,
     * or with status {@code 404 (Not Found)} if the loyaltyPoints is not found,
     * or with status {@code 500 (Internal Server Error)} if the loyaltyPoints couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoyaltyPoints> partialUpdateLoyaltyPoints(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoyaltyPoints loyaltyPoints
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LoyaltyPoints partially : {}, {}", id, loyaltyPoints);
        if (loyaltyPoints.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loyaltyPoints.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loyaltyPointsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoyaltyPoints> result = loyaltyPointsService.partialUpdate(loyaltyPoints);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loyaltyPoints.getId().toString())
        );
    }

    /**
     * {@code GET  /loyalty-points} : get all the loyaltyPoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loyaltyPoints in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LoyaltyPoints>> getAllLoyaltyPoints(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of LoyaltyPoints");
        Page<LoyaltyPoints> page = loyaltyPointsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loyalty-points/:id} : get the "id" loyaltyPoints.
     *
     * @param id the id of the loyaltyPoints to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loyaltyPoints, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoyaltyPoints> getLoyaltyPoints(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LoyaltyPoints : {}", id);
        Optional<LoyaltyPoints> loyaltyPoints = loyaltyPointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loyaltyPoints);
    }

    /**
     * {@code DELETE  /loyalty-points/:id} : delete the "id" loyaltyPoints.
     *
     * @param id the id of the loyaltyPoints to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoyaltyPoints(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete LoyaltyPoints : {}", id);
        loyaltyPointsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
