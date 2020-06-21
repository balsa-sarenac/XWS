package xws.team16.carservice.service;


import xws.team16.carservice.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.dto.AdDTO;
import xws.team16.carservice.generated.ad.PostAdRequest;
import xws.team16.carservice.generated.ad.TAd;
import xws.team16.carservice.dto.AdInfoDTO;
import xws.team16.carservice.dto.CarInfoDTO;
import xws.team16.carservice.model.Ad;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.PriceList;
import xws.team16.carservice.model.User;
import xws.team16.carservice.model.*;
import xws.team16.carservice.repository.AdRepository;

import java.sql.SQLException;

@Service @Slf4j
public class AdService {

    private AdRepository adRepository;
    private CarService carService;
    private PriceListService priceListService;
    private ModelMapper modelMapper;

    @Autowired
    public AdService(ModelMapper modelMapper, AdRepository adRepository, CarService carService, PriceListService priceListService) {
        this.adRepository = adRepository;
        this.carService = carService;
        this.priceListService = priceListService;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<Void> newAd(AdDTO adDTO) throws SQLException {
        log.info("Ad service - add new ad and car");
        Car car = this.carService.newCar(adDTO.getCarDTO());
        PriceList priceList = this.priceListService.getPriceListById(adDTO.getPriceListId());
        User user = car.getOwner();

        Ad ad = new Ad();
        ad.setCar(car);
        ad.setAllowedKilometrage(adDTO.getAllowedKilometrage());
        ad.setCdwAvailable(adDTO.isCdwAvailable());
        ad.setPickUpPlace(adDTO.getPickUpPlace());
        ad.setFromDate(adDTO.getFromDate());
        ad.setToDate(adDTO.getToDate());
        ad.setUser(user);
        ad.setPriceList(priceList);

        ad = this.adRepository.save(ad);
        log.info("Ad created with id " + ad.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Ad getAd(Long id) {
        Ad ad = this.adRepository.findById(id).orElseGet(null);
        return  ad;
    }

    public ResponseEntity<?> getOneAdById(Long id) {
        Ad ad = this.adRepository.getOne(id);
        AdInfoDTO adDTO = new AdInfoDTO();
        adDTO.setAllowedKilometrage(ad.getAllowedKilometrage());
        adDTO.setCdwAvailable(ad.isCdwAvailable());
        adDTO.setFromDate(ad.getFromDate());
        adDTO.setToDate(ad.getToDate());
        adDTO.setId(ad.getId());
        adDTO.setPickUpPlace(ad.getPickUpPlace());
        adDTO.setPriceListId(ad.getPriceList().getId());
        CarInfoDTO car = modelMapper.map(ad.getCar(), CarInfoDTO.class);
        float grade  = 0;
        if(ad.getCar().getGrades().size()!=0){
             float sum = 0;
             for(Grade g: ad.getCar().getGrades()){
                 sum = sum + g.getGrade();
             }

             grade = sum / ad.getCar().getGrades().size();
        }
        car.setOverallGrade(grade);
        adDTO.setCar(car);

        return new ResponseEntity<>(adDTO,HttpStatus.OK);

    }

    public long newAd(PostAdRequest adRequest) {
        log.info("Ad service - add new ad and car");
        TAd adType = adRequest.getAdRequest();
        Car car = this.carService.newCar(adType.getCar());
        PriceList priceList = this.priceListService.getPriceListById(adType.getPriceListId());
        User user = car.getOwner();

        Ad ad = new Ad();
        ad.setCar(car);
        ad.setAllowedKilometrage(adType.getAllowedKilometrage());
        ad.setCdwAvailable(adType.isCdwAvailable());
        ad.setPickUpPlace(adType.getPickUpPlace());
        ad.setFromDate(new DateTime(adType.getFromDate()));
        ad.setToDate(new DateTime(adType.getToDate()));
        ad.setUser(user);
        ad.setPriceList(priceList);

        ad = this.adRepository.save(ad);
        log.info("Ad created with id " + ad.getId());
        return ad.getId();
    }

    public Ad getAdById(Long id) {
        log.info("Ad service - getAdById(" + id + ")");
        return adRepository.findById(id).orElseThrow(() -> new NotFoundException("Ad with id " + id + " was not found."));
    }
}
