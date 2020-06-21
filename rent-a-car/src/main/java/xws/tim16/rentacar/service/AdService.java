package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.AdDTO;
import xws.tim16.rentacar.generated.*;
import xws.tim16.rentacar.model.Ad;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.PriceList;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.AdRepository;

@Service @Slf4j
public class AdService {

    private AdRepository adRepository;
    private CarService carService;
    private PriceListService priceListService;
    private CarClient carClient;

    @Autowired
    public AdService(AdRepository adRepository, CarService carService, PriceListService priceListService,
                     CarClient carClient) {
        this.adRepository = adRepository;
        this.carService = carService;
        this.priceListService = priceListService;
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
        PostAdResponse response = this.carClient.postNewCar(tAd);
        ad.setId(response.getAdResponse());
        log.info("Soap request successfully finished");

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
}
