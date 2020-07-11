package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.exception.NotFoundException;
import xws.team16.searchservice.model.PriceList;
import xws.team16.searchservice.repository.PriceListRepository;

@Service @Slf4j
public class PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    public PriceList getPriceListById(Long priceListId) {
        log.info("PriceList service - get price list by id");
        return this.priceListRepository.findById(priceListId).orElseThrow(() -> new NotFoundException("PriceList with given id was nto found"));
    }
}
