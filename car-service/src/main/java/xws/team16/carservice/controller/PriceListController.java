package xws.team16.carservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.carservice.dto.PriceListDTO;
import xws.team16.carservice.service.PriceListService;


@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping(value = "/priceList")
public class PriceListController {

    private PriceListService priceListService;

    @Autowired
    public PriceListController(PriceListService priceListService) {
        this.priceListService = priceListService;
    }

    @PostMapping()
    public ResponseEntity<?> createPriceList(@RequestBody PriceListDTO priceListDTO){
        log.info("Price list controller - creating price list");
        return this.priceListService.createPriceList(priceListDTO);
    }

    @PatchMapping( consumes = "application/json")
    public ResponseEntity<?> editPriceList(@RequestBody PriceListDTO priceListDTO){
        log.info("Price list controller - editing price list");
        return this.priceListService.editPriceList(priceListDTO);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getPriceListUser(@PathVariable String username){
        log.info("Price list controller - getting price lists for user");
        return this.priceListService.getPriceListUser(username);
    }

}
