package xws.team16.requestservice.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.client.SecurityClient;
import xws.team16.requestservice.dto.BillDTO;
import xws.team16.requestservice.dto.PriceListDTO;
import xws.team16.requestservice.model.Bill;
import xws.team16.requestservice.model.User;
import xws.team16.requestservice.repository.BillRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BillService {

    private BillRepository billRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    private SecurityClient securityClient;

    @Autowired
    public BillService(BillRepository billRepository, UserService userService,  ModelMapper modelMapper) {
        this.billRepository = billRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void createBill(double kilometrage, PriceListDTO priceList, Long user_id) {
        log.info("Bill service - create bill");
        Bill bill = new Bill();
        bill.setPaid(false);
        User user = this.userService.getUserById(user_id);
        bill.setUser(user);

        double price = kilometrage * priceList.getExtraKilometrage();
        bill.setPrice(price);

        log.info("Bill service - calling feign security");
        try {
            this.securityClient.rentPrivileges(false,user_id);
            log.info("Successufully called security service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling security service");
        }

        this.billRepository.save(bill);
    }

    public ResponseEntity<?> getAllBillsUser(String username) {
        log.info("user: " + username);
        List<Bill> bills = this.billRepository.findAllByUserUsername(username);
        List<BillDTO> billDTOS = new ArrayList<>();

        for(Bill b: bills){
            billDTOS.add(modelMapper.map(b, BillDTO.class));
        }

        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> payBill(Long id)   {
        log.info("Bill service - paying bill");
        Bill bill = this.billRepository.getOne(id);
        bill.setPaid(true);
        this.billRepository.save(bill);
        List<Bill> bills = this.billRepository.findAllByUserIdAndPaid(bill.getUser().getId(), false);
        if(bills.size() == 0) {
            log.info("Bill service - calling feign security");
            try {
                this.securityClient.rentPrivileges(true, bill.getUser().getId());
                log.info("Successufully called security service");
            } catch (FeignException.NotFound e) {
                log.info("Error calling security service");
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public boolean checkPaid(Long id) {
        List<Bill> bills = this.billRepository.findAllByUserIdAndPaid(id, false);
        if(bills.size() == 0) {
            return false;
        }
        return  true;
    }

}
