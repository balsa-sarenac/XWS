package xws.team16.carservice.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.client.RequestClient;
import xws.team16.carservice.dto.OccupiedDTO;
import xws.team16.carservice.model.Ad;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.Occupied;
import xws.team16.carservice.repository.OccupiedRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class OccupiedService {
    private OccupiedRepository occupiedRepository;
    private CarService carService;

    @Autowired
    private RequestClient requestClient;

    @Autowired
    public OccupiedService(OccupiedRepository occupiedRepository, CarService carService) {
        this.occupiedRepository = occupiedRepository;
        this.carService = carService;
    }

    public ResponseEntity<Void> newOccupied(OccupiedDTO occupiedDTO) {
        log.info("Occupied service - new occupied");
        Occupied occupied = new Occupied();
        occupied.setDateFrom(occupiedDTO.getDateFrom());
        occupied.setDateTo(occupiedDTO.getDateTo());

        Car car = carService.getCar(occupiedDTO.getCarId());
        occupied.setCar(car);
        List<Long> adsId = new ArrayList<>();
        for (Ad a: car.getAds()){
            adsId.add(a.getId());
        }
        occupiedDTO.setAdsId(adsId);

        occupied = this.occupiedRepository.save(occupied);
        log.info("Created occupied with id " + occupied.getId());

        try {
            this.requestClient.occupiedRequests(occupiedDTO);
            log.info("Successufully called request service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling request service");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<OccupiedDTO> getOccupied(Long id) {
            Occupied occupied = occupiedRepository.getOne(id);
            OccupiedDTO occupiedDTO = new OccupiedDTO();
            occupiedDTO.setDateTo(occupied.getDateTo());
            occupiedDTO.setDateFrom(occupied.getDateFrom());
            occupiedDTO.setCarId(occupied.getCar().getId());
            occupiedDTO.setId(occupied.getId());

            return new ResponseEntity<>(occupiedDTO, HttpStatus.FOUND);
    }
}
