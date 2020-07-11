package xws.team16.carservice.service;


import feign.FeignException;
import xws.team16.carservice.client.RequestClient;
import xws.team16.carservice.client.SearchClient;
import xws.team16.carservice.dto.*;
import xws.team16.carservice.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.generated.ad.PostAdRequest;
import xws.team16.carservice.generated.ad.TAd;
import xws.team16.carservice.model.Ad;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.PriceList;
import xws.team16.carservice.model.User;
import xws.team16.carservice.model.*;
import xws.team16.carservice.repository.AdRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service @Slf4j
public class AdService {

    private AdRepository adRepository;
    private CarService carService;
    private PriceListService priceListService;
    private ModelMapper modelMapper;

    @Autowired
    private RequestClient requestClient;

    @Autowired
    private SearchClient searchClient;

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

        try {
            NewAdRequestDTO newAdRequestDTO = new NewAdRequestDTO();
            newAdRequestDTO.setId(ad.getId());
            newAdRequestDTO.setUserId(ad.getUser().getId());
            this.requestClient.postAd(newAdRequestDTO);
            this.searchClient.postAd(adDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Ad getAd(Long id) {
        Ad ad = this.adRepository.findById(id).orElseGet(null);
        return  ad;
    }

    public ResponseEntity<?> getOneAdById(Long id) throws SQLException {
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

        List<String> images = new ArrayList<>();
        for (MyImage myImage: ad.getCar().getImages()) {
            String image = this.carService.encodeImage(myImage);
            images.add(image);
        }
        adDTO.setImages(images);

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

    public Ad getCar(Long ad_id) {
        log.info("Ad service - get ad");
        Ad ad = adRepository.findById(ad_id).orElseThrow(() -> new NotFoundException("Ad with given id was not found"));

        log.info("Ad getted with id " + ad.getId());
        return  ad;
    }

    public ResponseEntity<?> removeAds(Long user_id) {
        List<Ad> ads  = this.adRepository.findAllByUserId(user_id);
        List<Long> adsId = new ArrayList<>();
        for(Ad a: ads){
            a.setToDate(DateTime.now().minusDays(1));
            a.setFromDate(DateTime.now().minusDays(2));
            adsId.add(a.getId());
            for(RentRequest r: a.getRequest()){
                log.info("Ad service - calling feign request");
                try {
                    this.requestClient.cancelRequest(r.getId());
                    log.info("Successufully called request service");
                } catch (FeignException.NotFound e) {
                    log.info("Error calling request service");
                }
            }
            this.adRepository.save(a);
        }

        log.info("Ad service - calling feign search");
        try {
            this.searchClient.removeAds(adsId);
            log.info("Successufully called search service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling search service");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> findCities() {
        List<String> cities = new ArrayList<>();
        List<Ad> ads = this.adRepository.findAllByToDateAfter(DateTime.now().minusDays(30));

        for(Ad a: ads){
            if(cities.contains(a.getPickUpPlace()))
                continue;

            cities.add(a.getPickUpPlace());
        }

        return new ResponseEntity<>(cities, HttpStatus.OK);

    }

    public ResponseEntity<?> getUsersForCars(AdListDTO adListDTO) {
        List<Ad> ads = this.adRepository.findAllById(adListDTO.getAds());
        AdUsersDTO adUsersDTO = new AdUsersDTO();
        Map<Long, Long> adUsersMap = new HashMap<>();
        for (Ad ad: ads) {
            adUsersMap.put(ad.getId(), ad.getUser().getId());
        }
        adUsersDTO.setAdUserMap(adUsersMap);
        return new ResponseEntity<>(adUsersDTO, HttpStatus.OK);
    }
}
