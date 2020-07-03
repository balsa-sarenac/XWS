package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.dto.PriceListDTO;
import xws.tim16.rentacar.exception.NotFoundException;
import xws.tim16.rentacar.model.PriceList;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.PriceListRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class PriceListService {

    private PriceListRepository priceListRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public PriceListService(PriceListRepository priceListRepository, ModelMapper modelMapper, UserService userService) {
        this.priceListRepository = priceListRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public PriceList getPriceListById(Long priceListId) {
        log.info("PriceList service - get price list by id");
        return this.priceListRepository.findById(priceListId).orElseThrow(() -> new NotFoundException("PriceList with given id was nto found"));
    }

    public ResponseEntity<?> createPriceList(PriceListDTO priceListDTO) {
        log.info("Price list service - creating price list");
        PriceList priceList = modelMapper.map(priceListDTO, PriceList.class);
        User user = this.userService.getUserByUsername(priceListDTO.getUserUsername());
        priceList.setUser(user);
        this.priceListRepository.save(priceList);
        log.info("Price list service - price list created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> editPriceList(PriceListDTO priceListDTO) {
        log.info("Price list service - editing price list");
        PriceList priceList = this.priceListRepository.getOne(priceListDTO.getId());
        priceList.setCdw(priceListDTO.getCdw());
        priceList.setDiscount(priceListDTO.getDiscount());
        priceList.setExtraKilometrage(priceListDTO.getExtraKilometrage());
        priceList.setDiscountDays(priceListDTO.getDiscountDays());
        priceList.setPerDay(priceListDTO.getPerDay());
        this.priceListRepository.save(priceList);
        log.info("Price list service - price list edited");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> getPriceListUser(String username) {
        log.info("Price list service - getting price lists for user");
        List<PriceList> priceLists = this.priceListRepository.findAllByUserUsername(username);
        List<PriceListDTO> priceListDTOS = new ArrayList<>();

        for(PriceList p: priceLists){
            PriceListDTO pdto =  modelMapper.map(p, PriceListDTO.class);
            pdto.setUserUsername(p.getUser().getUsername());

            priceListDTOS.add(pdto);
        }
        return new ResponseEntity<>(priceListDTOS,HttpStatus.OK);
    }
}
