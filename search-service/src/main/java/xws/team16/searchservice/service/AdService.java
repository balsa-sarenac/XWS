package xws.team16.searchservice.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.AdDTO;
import xws.team16.searchservice.dto.CarDTO;
import xws.team16.searchservice.dto.PriceListDTO;
import xws.team16.searchservice.dto.SearchDTO;
import xws.team16.searchservice.model.Ad;
import xws.team16.searchservice.model.PriceList;
import xws.team16.searchservice.repository.AdRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class AdService {
    private AdRepository adRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AdService(AdRepository adRepository, ModelMapper modelMapper) {
        this.adRepository = adRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<List<AdDTO>> searchAds(SearchDTO search, int page) {
        log.info("Ad service - searching ads");
        List<AdDTO> adDTOS = new ArrayList<>();
        String pickUpPlace = search.getPickUpPlace();
        DateTime fromDate = search.getFromDate();
        DateTime toDate = search.getToDate();
        Long markId = search.getMark().getId();
        Long modelId = search.getModel().getId();
        Long fuelId = search.getFuel().getId();
        Long gearboxId = search.getGearbox().getId();
        Long carClassId = search.getCarClass().getId();
        Double priceFrom = search.getPriceFrom();
        Double priceTo = search.getPriceTo();
        if(priceTo == 0)
            priceTo = 1000000.0;
        Double kilometrageTo = search.getKilometrageTo();
        if (kilometrageTo == 0)
            kilometrageTo = 1000000.0;
        Double kilomterageFrom = search.getKilometrageFrom();
        Double kilometrageDrive = search.getKilometrageDrive();
        Integer numberOfChildSeats = search.getNumberOfChildSeats();
        Boolean cdw = search.getCdw();

        log.info("Ad service - searching...");
        Pageable pageable = PageRequest.of(page, 10);
        Page<Ad> ads = this.adRepository.searchAds(pickUpPlace, fromDate, toDate, modelId, markId, carClassId, fuelId, gearboxId, priceFrom, priceTo,
                                                   kilomterageFrom, kilometrageTo, kilometrageDrive, cdw, numberOfChildSeats, pageable);

        for (Ad ad: ads){
            adDTOS.add(modelMapper.map(ad, AdDTO.class));
        }

        log.info("Ad service - sending founded ads");
        return new ResponseEntity<>(adDTOS, HttpStatus.OK);
    }

    public ResponseEntity<AdDTO> getAd(Long id) {
        log.info("Ad service - getting ad");
        Ad ad = this.adRepository.getOne(id);
        AdDTO adDTO = modelMapper.map(ad,AdDTO.class);
        return new ResponseEntity<>(adDTO, HttpStatus.OK);
    }
}
