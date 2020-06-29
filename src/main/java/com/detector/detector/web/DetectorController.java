package com.detector.detector.web;

import com.detector.detector.module.DNAdto;
import com.detector.detector.module.Stat;
import com.detector.detector.service.DetectorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DetectorController {

    private static final Logger LOGGER = LogManager.getLogger(DetectorController.class);

    private final DetectorService service;

    @Autowired
    public DetectorController(DetectorService service) {
        this.service = service;
    }

    @PostMapping("/mutant")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity checkMutant(@RequestBody DNAdto dna){
        LOGGER.info(" == Checking... == ");
        return service.isMutant(dna.getDna())? new ResponseEntity(HttpStatus.OK): new ResponseEntity(HttpStatus.FORBIDDEN);

    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Stat> getStats(){
        LOGGER.info(" == Collecting stats == ");
        return new ResponseEntity(service.getStats(), HttpStatus.OK);

    }
}
