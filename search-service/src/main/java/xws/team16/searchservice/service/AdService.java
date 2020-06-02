package xws.team16.searchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<List<AdDTO>> searchAds(SearchDTO search) {
        log.info("Ad service - searching ads");
        List<AdDTO> adDTOS = new ArrayList<>();
        List<Ad> ads = this.adRepository.findAll();


        return new ResponseEntity<>(adDTOS, HttpStatus.OK);
    }

    public ResponseEntity<AdDTO> getAd(Long id) {
        log.info("Ad service - getting ad");
        Ad ad = this.adRepository.getOne(id);
        AdDTO adDTO = new AdDTO();

        adDTO.setAllowedKilometrage(ad.getAllowedKilometrage());
        adDTO.setFromDate(ad.getFromDate());
        adDTO.setToDate(ad.getToDate());
        adDTO.setPickUpPlace(ad.getPickUpPlace());
        PriceListDTO priceListDTO = modelMapper.map(ad.getPriceList(), PriceListDTO.class);
        adDTO.setPriceList(priceListDTO);
        CarDTO carDTO = modelMapper.map(ad.getCar(), CarDTO.class);
        adDTO.setCar(carDTO);
        return new ResponseEntity<>(adDTO, HttpStatus.OK);
    }
}
