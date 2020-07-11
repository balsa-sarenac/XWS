package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.PriceListDTO;
import xws.team16.searchservice.model.PriceList;
import xws.team16.searchservice.repository.PriceListRepository;

@Service @Slf4j
public class PriceListService {

    private PriceListRepository priceListRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PriceListService(PriceListRepository priceListRepository, ModelMapper modelMapper) {
        this.priceListRepository = priceListRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> createPriceList(PriceListDTO priceListDTO) {
        log.info("Price list service - creating price list");
        PriceList priceList = modelMapper.map(priceListDTO, PriceList.class);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public PriceList getPriceListById(Long priceListId) {
        log.info("PriceList service - get price list by id");
        return this.priceListRepository.findById(priceListId).orElseThrow(() -> new NotFoundException("PriceList with given id was nto found"));
    }
}

