package xws.team16.carservice.service;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.model.PriceList;
import xws.team16.carservice.repository.PriceListRepository;

@Service @Slf4j
public class PriceListService {

    private PriceListRepository priceListRepository;

    @Autowired
    public PriceListService(PriceListRepository priceListRepository) {
        this.priceListRepository = priceListRepository;
    }


    public PriceList getPriceListById(Long priceListId) {
        log.info("PriceList service - get price list by id");
        return this.priceListRepository.findById(priceListId).orElseThrow(() -> new NotFoundException("PriceList with given id was nto found"));
    }
}
