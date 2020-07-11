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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.searchservice.dto.*;
import xws.team16.searchservice.model.Ad;
import xws.team16.searchservice.model.Car;
import xws.team16.searchservice.model.PriceList;
import xws.team16.searchservice.repository.AdRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service @Slf4j
public class AdService {
    private AdRepository adRepository;
    private ModelMapper modelMapper;
    private CarService carService;
    private PriceListService priceListService;

    @Autowired
    public AdService(AdRepository adRepository, ModelMapper modelMapper, CarService carService, PriceListService priceListService) {
        this.adRepository = adRepository;
        this.modelMapper = modelMapper;
        this.carService = carService;
        this.priceListService = priceListService;
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

    public ResponseEntity<List<AdInfoDTO>> searchAds(SearchDTO search, int page, String sort) throws SQLException {
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
            adInfoDTO.setImages(this.carService.transformImages(ad));
            adDTOS.add(adInfoDTO);
        }

        log.info("Ad service - sending founded ads");
        return new ResponseEntity<>(adDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAds(Set<Ad> ads) {
        for(Ad a: ads){
            a.setToDate(DateTime.now().minusDays(1));
            a.setFromDate(DateTime.now().minusDays(2));
            /*
            for(RentRequest r: a.getRequest()){
                this.rentRequestService.cancelRequest(r.getId());
                if(r.getBundle() != null){
                    this.rentBundleService.cancelBundleRequest(r.getBundle().getId());
                }
            }*/
            this.adRepository.save(a);
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
        car.setOverallGrade(ad.getCar().getOverallGrade());
        car.setNumberGrades(ad.getCar().getNumberOfGrades());
        adDTO.setCar(car);

        return new ResponseEntity<>(adDTO,HttpStatus.OK);
    }

    public ResponseEntity<?> remove(List<Long> ads) {
        log.info("Ad service - removing ads");
        for(Long id: ads){
            Ad ad = this.adRepository.getOne(id);
            ad.setToDate(DateTime.now().minusDays(1));
            ad.setFromDate(DateTime.now().minusDays(2));
            this.adRepository.save(ad);
            log.info("Ad service - ads removed");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Void> newAd(NewAdRequestDTO adDTO) throws SQLException {
        log.info("Ad service - add new ad and car");
        Car car = this.carService.newCar(adDTO.getCarDTO());
        PriceList priceList = this.priceListService.getPriceListById(adDTO.getPriceListId());
//        User user = car.getOwner();

        Ad ad = new Ad();
        ad.setCar(car);
        ad.setAllowedKilometrage(adDTO.getAllowedKilometrage());
        ad.setCdwAvailable(adDTO.isCdwAvailable());
        ad.setPickUpPlace(adDTO.getPickUpPlace());
        ad.setFromDate(adDTO.getFromDate());
        ad.setToDate(adDTO.getToDate());
//        ad.setUser(user);
        ad.setPriceList(priceList);

        ad = this.adRepository.save(ad);
        log.info("Ad created with id " + ad.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
