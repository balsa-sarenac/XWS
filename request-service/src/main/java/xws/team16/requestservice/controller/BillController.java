package xws.team16.requestservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.team16.requestservice.dto.PriceListDTO;
import xws.team16.requestservice.service.BillService;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(value = "/bill")
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping(value = "/{kilometrage}/{user_id}")
    private ResponseEntity<?> createBill(@PathVariable double kilometrage, @PathVariable Long user_id, @RequestBody PriceListDTO priceList){
        log.info("Bill controller - getting all bills");
        this.billService.createBill(kilometrage, priceList, user_id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    private ResponseEntity<?> getAllBillsUser(@PathVariable String username){
        log.info("Bill controller - getting all bills");
        return this.billService.getAllBillsUser(username);
    }

    @GetMapping(value = "/check/{id}")
    private ResponseEntity<?> checkPaid(@PathVariable Long id){
        log.info("Bill controller - checking paid");
        return new ResponseEntity<>(this.billService.checkPaid(id), HttpStatus.OK);
    }

    @PatchMapping(value = "/pay/{id}")
    private ResponseEntity<?> payBill(@PathVariable Long id){
        log.info("Bill controller - paying bill");
        return this.billService.payBill(id);
    }


}
