package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.*;
import xws.tim16.rentacar.exception.NotFoundException;
import xws.tim16.rentacar.generated.*;
import xws.tim16.rentacar.model.*;
import xws.tim16.rentacar.repository.AdRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service @Slf4j
public class AdService {

    private AdRepository adRepository;
    private CarService carService;
    private PriceListService priceListService;
    private CarClient carClient;
    private ModelMapper modelMapper;
    @Autowired
    private RentRequestService rentRequestService;
    @Autowired
    private RentBundleService rentBundleService;

    @Autowired
    public AdService(AdRepository adRepository, CarService carService, PriceListService priceListService, CarClient carClient, ModelMapper modelMapper) {
        this.adRepository = adRepository;
        this.carService = carService;
        this.priceListService = priceListService;
        this.carClient = carClient;
        this.modelMapper = modelMapper;
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
        log.info("Ad service - getting one ad");
        Ad ad = this.adRepository.getOne(id);
        AdInfoDTO adDTO = new AdInfoDTO();
        adDTO.setAllowedKilometrage(ad.getAllowedKilometrage());
        adDTO.setCdwAvailable(ad.isCdwAvailable());
        adDTO.setFromDate(ad.getFromDate());
        adDTO.setToDate(ad.getToDate());
        adDTO.setId(ad.getId());
        adDTO.setPickUpPlace(ad.getPickUpPlace());
        adDTO.setPriceListId(ad.getPriceList().getId());
        PriceListDTO priceListDTO = modelMapper.map(ad.getPriceList(), PriceListDTO.class);
        adDTO.setPriceList(priceListDTO);
        CarInfoDTO car = modelMapper.map(ad.getCar(), CarInfoDTO.class);
        float grade  = 0;
        int numberGrades = 0;

        if(ad.getCar().getGrades().size()!=0){
            float sum = 0;
            for(Grade g: ad.getCar().getGrades()){
                sum = sum + g.getGrade();
            }
            numberGrades = ad.getCar().getGrades().size();
            grade = sum / numberGrades;
        }

        car.setOverallGrade(grade);
        car.setNumberGrades(numberGrades);
        adDTO.setCar(car);

        return new ResponseEntity<>(adDTO,HttpStatus.OK);
    }

    public Ad getAd(Long id) {
        Ad ad = this.adRepository.findById(id).orElseGet(null);
        return  ad;
    }

    public ResponseEntity<List<AdInfoDTO>> searchAds(SearchDTO search, int page, String sort) {
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

        Sort sortby = sortBy(sort);

        log.info("Ad service - searching...");
        Pageable pageable = PageRequest.of(page, 10, sortby);
        Page<Ad> ads = this.adRepository.searchAds(pickUpPlace, fromDate, toDate, modelId, markId, carClassId, fuelId, gearboxId, priceFrom, priceTo,
                kilomterageFrom, kilometrageTo, kilometrageDrive, cdw, numberOfChildSeats, pageable);

        for (Ad ad: ads){
            AdInfoDTO adInfoDTO = modelMapper.map(ad, AdInfoDTO.class);
            adInfoDTO.getCar().setOverallGrade(ad.getCar().getOverallGrade());
            adInfoDTO.setPages(ads.getTotalPages());
            adInfoDTO.setUserId(ad.getUser().getId());
            adDTOS.add(adInfoDTO);
        }

        log.info("Ad service - sending founded ads");
        return new ResponseEntity<>(adDTOS, HttpStatus.OK);
    }

    public Sort sortBy(String sort){
        if(sort.equals("Price higher")){
            return Sort.by(Sort.Direction.ASC, "priceList.perDay");
        }else if(sort.equals("Price lower")){
            return Sort.by(Sort.Direction.DESC, "priceList.perDay");
        }else if(sort.equals("Kilometrage higher")){
            return Sort.by(Sort.Direction.ASC, "car.kilometrage");
        }else if(sort.equals("Kilometrage lower")){
            return Sort.by(Sort.Direction.DESC, "car.kilometrage");
        }else if(sort.equals("Grade higher")){
            return Sort.by(Sort.Direction.ASC, "car.overallGrade");
        }else if(sort.equals("Grade lower")){
            return Sort.by(Sort.Direction.DESC, "car.overallGrade");
        }
        return Sort.by(Sort.Direction.ASC, "fromDate");
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


    public void deleteAds(Set<Ad> ads) {
        for(Ad a: ads){
            a.setToDate(DateTime.now().minusDays(1));
            a.setFromDate(DateTime.now().minusDays(2));
            for(RentRequest r: a.getRequest()){
                if (!r.getStatus().equals(RequestStatus.paid))
                    this.rentRequestService.cancelRequest(r.getId());
                if(r.getBundle() != null){
                    this.rentBundleService.cancelBundleRequest(r.getBundle().getId());
                }
            }
            this.adRepository.save(a);
        }
    }

    public Ad getCar(Long ad_id) {
        log.info("Ad service - get ad");
        if (ad_id == null) ad_id = 1L;
        Ad ad = adRepository.findById(ad_id).orElseThrow(() -> new NotFoundException("Ad with given id was not found"));

        log.info("Ad getted with id " + ad.getId());
        return  ad;
    }
}
