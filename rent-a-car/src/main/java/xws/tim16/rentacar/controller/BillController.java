package xws.tim16.rentacar.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xws.tim16.rentacar.service.BillService;

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

    @GetMapping(value = "/{username}")
    private ResponseEntity<?> getAllBillsUser(@PathVariable String username){
        log.info("Bill controller - getting all bills");
        return this.billService.getAllBilsUser(username);
    }

    @PatchMapping(value = "/pay/{id}")
    private ResponseEntity<?> payBill(@PathVariable Long id){
        log.info("Bill controller - paying bill");
        return this.billService.payBill(id);
    }


}
