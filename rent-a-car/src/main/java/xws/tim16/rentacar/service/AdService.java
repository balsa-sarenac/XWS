package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.AdDTO;
import xws.tim16.rentacar.generated.PostAdResponse;
import xws.tim16.rentacar.model.Ad;
import xws.tim16.rentacar.model.Car;
import xws.tim16.rentacar.model.PriceList;
import xws.tim16.rentacar.model.User;
import xws.tim16.rentacar.repository.AdRepository;
import xws.tim16.rentacar.generated.AdDTOType;
import xws.tim16.rentacar.generated.CarDTOType;

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

        ad = this.adRepository.save(ad);
        log.info("Ad created with id " + ad.getId());

        log.info("Sending soap request to car service");
        AdDTOType adDTOType = createAdFromDTO(adDTO);
        PostAdResponse response = this.carClient.postNewCar(adDTOType);
//        ad.setId(response.);
        log.info("Soap request successfully finished");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private AdDTOType createAdFromDTO(AdDTO adDTO) {
        AdDTOType type = new AdDTOType();
        type.setAllowedKilometrage(adDTO.getAllowedKilometrage());
        type.setCdwAvailable(adDTO.isCdwAvailable());
        type.setFromDate(adDTO.getFromDate().getMillis());
        type.setToDate(adDTO.getToDate().getMillis());
        type.setPickUpPlace(adDTO.getPickUpPlace());
        type.setPriceListId(adDTO.getPriceListId());
        CarDTOType carType = new CarDTOType();
        carType.setCarClassId(adDTO.getCarDTO().getCarClassId());
        carType.setFuelId(adDTO.getCarDTO().getFuelId());
        carType.setGearboxId(adDTO.getCarDTO().getGearboxId());
        carType.setMarkId(adDTO.getCarDTO().getMarkId());
        carType.setModelId(adDTO.getCarDTO().getModelId());
        carType.setKilometrage(adDTO.getCarDTO().getKilometrage());
        carType.setNumberOfChildSeats(adDTO.getCarDTO().getNumberOfChildSeats());
//        carType.setUserId(adDTO.getCarDTO().getUserId());
//        carType.setId(adDTO.getCarDTO().getId());
        type.setCarDTO(carType);
        return type;

    }
}
