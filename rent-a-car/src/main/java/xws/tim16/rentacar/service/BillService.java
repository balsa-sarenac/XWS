package xws.tim16.rentacar.service;

import javafx.scene.layout.BackgroundImage;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.dto.BillDTO;
import xws.tim16.rentacar.model.Bill;
import xws.tim16.rentacar.model.PriceList;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.BillRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BillService {

    private BillRepository billRepository;
    private UserService userService;
    private CustomUserDetailsService userDetailsService;
    private ModelMapper modelMapper;

    @Autowired
    public BillService(BillRepository billRepository, UserService userService, CustomUserDetailsService userDetailsService, ModelMapper modelMapper) {
        this.billRepository = billRepository;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
    }

    public void createBill(double kilometrage, PriceList priceList, Long user_id) {
        Bill bill = new Bill();
        bill.setPaid(false);
        User user = this.userService.getUserById(user_id);
        bill.setUser(user);

        double price = kilometrage * priceList.getExtraKilometrage();
        bill.setPrice(price);
        this.userDetailsService.rentPrivilege(false, user_id);

        this.billRepository.save(bill);
    }

    public ResponseEntity<?> getAllBilsUser(String username) {
        log.info("user: " + username);
        List<Bill> bills = this.billRepository.findAllByUserUsername(username);
        List<BillDTO> billDTOS = new ArrayList<>();

        for(Bill b: bills){
            billDTOS.add(modelMapper.map(b, BillDTO.class));
        }

        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> payBill(Long id) {
        Bill bill = this.billRepository.getOne(id);
        bill.setPaid(true);
        this.billRepository.save(bill);
        List<Bill> bills = this.billRepository.findAllByUserIdAndPaid(bill.getUser().getId(), false);
        if(bills.size() == 0) {
            this.userDetailsService.rentPrivilege(true, bill.getUser().getId());
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
