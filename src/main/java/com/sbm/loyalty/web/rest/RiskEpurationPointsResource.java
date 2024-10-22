package com.sbm.loyalty.web.rest;

import com.sbm.loyalty.domain.RiskEpurationPoints;
import com.sbm.loyalty.repository.RiskEpurationPointsRepository;
import com.sbm.loyalty.service.RiskEpurationPointsService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sbm.loyalty.domain.RiskEpurationPoints}.
 */
@RestController
@RequestMapping("/api/risk-epuration-points")
public class RiskEpurationPointsResource {

    private static final Logger LOG = LoggerFactory.getLogger(RiskEpurationPointsResource.class);

    private static final String ENTITY_NAME = "riskEpurationPoints";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiskEpurationPointsService riskEpurationPointsService;

    private final RiskEpurationPointsRepository riskEpurationPointsRepository;

    public RiskEpurationPointsResource(
        RiskEpurationPointsService riskEpurationPointsService,
        RiskEpurationPointsRepository riskEpurationPointsRepository
    ) {
        this.riskEpurationPointsService = riskEpurationPointsService;
        this.riskEpurationPointsRepository = riskEpurationPointsRepository;
    }

    /**
     * {@code POST  /risk-epuration-points} : Create a new riskEpurationPoints.
     *
     * @param riskEpurationPoints the riskEpurationPoints to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riskEpurationPoints, or with status {@code 400 (Bad Request)} if the riskEpurationPoints has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RiskEpurationPoints> createRiskEpurationPoints(@RequestBody RiskEpurationPoints riskEpurationPoints)
        throws URISyntaxException {
        LOG.debug("REST request to save RiskEpurationPoints : {}", riskEpurationPoints);
        if (riskEpurationPoints.getId() != null) {
            throw new BadRequestAlertException("A new riskEpurationPoints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        riskEpurationPoints = riskEpurationPointsService.save(riskEpurationPoints);
        return ResponseEntity.created(new URI("/api/risk-epuration-points/" + riskEpurationPoints.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, riskEpurationPoints.getId().toString()))
            .body(riskEpurationPoints);
    }

    /**
     * {@code PUT  /risk-epuration-points/:id} : Updates an existing riskEpurationPoints.
     *
     * @param id the id of the riskEpurationPoints to save.
     * @param riskEpurationPoints the riskEpurationPoints to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskEpurationPoints,
     * or with status {@code 400 (Bad Request)} if the riskEpurationPoints is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riskEpurationPoints couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RiskEpurationPoints> updateRiskEpurationPoints(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RiskEpurationPoints riskEpurationPoints
    ) throws URISyntaxException {
        LOG.debug("REST request to update RiskEpurationPoints : {}, {}", id, riskEpurationPoints);
        if (riskEpurationPoints.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskEpurationPoints.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskEpurationPointsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        riskEpurationPoints = riskEpurationPointsService.update(riskEpurationPoints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskEpurationPoints.getId().toString()))
            .body(riskEpurationPoints);
    }

    /**
     * {@code PATCH  /risk-epuration-points/:id} : Partial updates given fields of an existing riskEpurationPoints, field will ignore if it is null
     *
     * @param id the id of the riskEpurationPoints to save.
     * @param riskEpurationPoints the riskEpurationPoints to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riskEpurationPoints,
     * or with status {@code 400 (Bad Request)} if the riskEpurationPoints is not valid,
     * or with status {@code 404 (Not Found)} if the riskEpurationPoints is not found,
     * or with status {@code 500 (Internal Server Error)} if the riskEpurationPoints couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RiskEpurationPoints> partialUpdateRiskEpurationPoints(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RiskEpurationPoints riskEpurationPoints
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RiskEpurationPoints partially : {}, {}", id, riskEpurationPoints);
        if (riskEpurationPoints.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riskEpurationPoints.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riskEpurationPointsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RiskEpurationPoints> result = riskEpurationPointsService.partialUpdate(riskEpurationPoints);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riskEpurationPoints.getId().toString())
        );
    }

    /**
     * {@code GET  /risk-epuration-points} : get all the riskEpurationPoints.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riskEpurationPoints in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RiskEpurationPoints>> getAllRiskEpurationPoints(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("loyaltypoints-is-null".equals(filter)) {
            LOG.debug("REST request to get all RiskEpurationPointss where loyaltyPoints is null");
            return new ResponseEntity<>(riskEpurationPointsService.findAllWhereLoyaltyPointsIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of RiskEpurationPoints");
        Page<RiskEpurationPoints> page = riskEpurationPointsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /risk-epuration-points/:id} : get the "id" riskEpurationPoints.
     *
     * @param id the id of the riskEpurationPoints to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riskEpurationPoints, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RiskEpurationPoints> getRiskEpurationPoints(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RiskEpurationPoints : {}", id);
        Optional<RiskEpurationPoints> riskEpurationPoints = riskEpurationPointsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riskEpurationPoints);
    }

    /**
     * {@code DELETE  /risk-epuration-points/:id} : delete the "id" riskEpurationPoints.
     *
     * @param id the id of the riskEpurationPoints to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiskEpurationPoints(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RiskEpurationPoints : {}", id);
        riskEpurationPointsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
