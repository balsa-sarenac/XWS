package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.AdDTO;
import xws.tim16.rentacar.dto.AdInfoDTO;
import xws.tim16.rentacar.dto.CarInfoDTO;
import xws.tim16.rentacar.exception.NotFoundException;
import xws.tim16.rentacar.dto.SearchDTO;
import xws.tim16.rentacar.generated.*;
import xws.tim16.rentacar.model.*;
import xws.tim16.rentacar.repository.AdRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class AdService {

    private AdRepository adRepository;
    private CarService carService;
    private PriceListService priceListService;
    private CarClient carClient;
    private ModelMapper modelMapper;

    @Autowired
    public AdService(AdRepository adRepository, CarService carService, PriceListService priceListService,
                     ModelMapper modelMapper, CarClient carClient) {
        this.adRepository = adRepository;
        this.carService = carService;
        this.priceListService = priceListService;
        this.modelMapper = modelMapper;
        this.carClient = carClient;
    }

    public ResponseEntity<Void> newAd(AdDTO adDTO) {
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

        log.info("Sending soap request to car service");
        TAd tAd = createAdFromDTO(adDTO);
        try {
            PostAdResponse response = this.carClient.postNewCar(tAd);
            ad.setRefId(response.getAdResponse());
            log.info("Soap request successfully finished");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.adRepository.save(ad);
        log.info("Ad created with id " + ad.getId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private TAd createAdFromDTO(AdDTO adDTO) {
        TAd tAd = new TAd();
        tAd.setAllowedKilometrage(adDTO.getAllowedKilometrage());
        tAd.setCdwAvailable(adDTO.isCdwAvailable());
        tAd.setFromDate(adDTO.getFromDate().getMillis());
        tAd.setToDate(adDTO.getToDate().getMillis());
        tAd.setPickUpPlace(adDTO.getPickUpPlace());
        tAd.setPriceListId(adDTO.getPriceListId());
        TCar tCar = new TCar();
        tCar.setCarClassId(adDTO.getCarDTO().getCarClassId());
        tCar.setFuelId(adDTO.getCarDTO().getFuelId());
        tCar.setGearboxId(adDTO.getCarDTO().getGearboxId());
        tCar.setMarkId(adDTO.getCarDTO().getMarkId());
        tCar.setModelId(adDTO.getCarDTO().getModelId());
        tCar.setKilometrage(adDTO.getCarDTO().getKilometrage());
        tCar.setNumberOfChildSeats(adDTO.getCarDTO().getNumberOfChildSeats());
//        tCar.setUserId(adDTO.getCarDTO().getUserId());
//        tCar.setId(adDTO.getCarDTO().getId());
        tAd.setCar(tCar);
        return tAd;

    }


    public Ad getAdById(Long adId) {
        log.info("Ad service - get ad by id");
        return this.adRepository.findById(adId).orElseThrow(() -> new NotFoundException("Ad with given id was nto found."));
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

    public ResponseEntity<List<AdInfoDTO>> searchAds(SearchDTO search, int page) {
        log.info("Ad service - searching ads");
        if(search.getFromDate().isAfter(search.getToDate()) || search.getFromDate().isBefore(DateTime.now())){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<AdInfoDTO> adDTOS = new ArrayList<>();
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
            AdInfoDTO adInfoDTO = modelMapper.map(ad, AdInfoDTO.class);
            int sum = 0;
            for(Grade g: ad.getCar().getGrades()){
                sum = sum + g.getGrade();
            }
            if(ad.getCar().getGrades().size() !=0)
                adInfoDTO.getCar().setOverallGrade(sum/ad.getCar().getGrades().size());
            else
                adInfoDTO.getCar().setOverallGrade(0);
            adInfoDTO.setPages(ads.getTotalPages());
            adDTOS.add(adInfoDTO);
        }

        log.info("Ad service - sending founded ads");
        return new ResponseEntity<>(adDTOS, HttpStatus.OK);
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


}
