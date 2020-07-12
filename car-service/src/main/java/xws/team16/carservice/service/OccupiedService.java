package xws.team16.carservice.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.client.RequestClient;
import xws.team16.carservice.dto.CarInfoDTO;
import xws.team16.carservice.dto.OccupiedDTO;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.generated.car.PostOccupiedRequest;
import xws.team16.carservice.generated.car.TOccupied;
import xws.team16.carservice.model.Ad;
import xws.team16.carservice.model.Car;
import xws.team16.carservice.model.Occupied;
import xws.team16.carservice.repository.AdRepository;
import xws.team16.carservice.repository.OccupiedRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class OccupiedService {
    private OccupiedRepository occupiedRepository;
    private CarService carService;
    private AdRepository adRepository;
    private ModelMapper modelMapper;

    @Autowired
    private RequestClient requestClient;

    @Autowired
    public OccupiedService(ModelMapper modelMapper, OccupiedRepository occupiedRepository, CarService carService, AdRepository adRepository) {
        this.occupiedRepository = occupiedRepository;
        this.carService = carService;
        this.adRepository = adRepository;
        this.modelMapper = modelMapper;
    }

    public boolean checkOccupied(List< Occupied> occupieds, OccupiedDTO occupiedDTO){
        LocalDate occupiedFrom = occupiedDTO.getDateFrom();
        LocalDate occupiedTo = occupiedDTO.getDateTo();

        for(Occupied o: occupieds){
            LocalDate rentFrom = o.getDateFrom();
            LocalDate rentTo = o.getDateTo();
            if(!rentFrom.isBefore(occupiedFrom) && !rentTo.isAfter(occupiedTo))
                return true;
            if(!rentFrom.isAfter(occupiedFrom) && !rentTo.isBefore(occupiedTo))
                return true;
            if(!rentFrom.isAfter(occupiedFrom) && !rentTo.isAfter(occupiedTo) && !rentTo.isBefore(occupiedFrom))
                return true;
            if(!rentFrom.isBefore(occupiedFrom) && !rentTo.isBefore(occupiedTo) && !rentFrom.isAfter(occupiedTo))
                return true;
        }
        return false;
    }

    public ResponseEntity<Void> newOccupied(OccupiedDTO occupiedDTO) {
        log.info("Occupied service - new occupied");
        if(occupiedDTO.getDateFrom().isAfter(occupiedDTO.getDateTo()) || occupiedDTO.getDateFrom().isBefore(LocalDate.now())){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Occupied> occupiedss = this.occupiedRepository.findAllByCarId(occupiedDTO.getCarId());
        boolean flag = checkOccupied(occupiedss, occupiedDTO);
        if(flag == true){
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }

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

        log.info("Occupied service - calling feign request");
        try {
            this.requestClient.occupiedRequests(occupiedDTO);
            log.info("Successufully called request service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling request service");
        }

        occupied = this.occupiedRepository.save(occupied);
        log.info("Created occupied with id " + occupied.getId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public long newOccupied(PostOccupiedRequest postOccupiedRequest) {
        log.info("Occupied service - add new occupied");
        TOccupied tOccupied = postOccupiedRequest.getOccupiedRequest();
        Occupied occupied = new Occupied();
        occupied.setDateFrom(new LocalDate(tOccupied.getDateFrom()));
        occupied.setDateTo(new LocalDate(tOccupied.getDateTo()));


        Car car = carService.getCar(tOccupied.getCarId());
        occupied.setCar(car);


        List<Long> adsId = new ArrayList<>();
        List<Ad> ads = this.adRepository.findAllByCarId(car.getId());
        for (Ad a: ads){
            tOccupied.getAdsId().add(a.getId());
        }

        OccupiedDTO occupiedDTO = new OccupiedDTO();
        occupiedDTO.setAdsId(tOccupied.getAdsId());
        occupiedDTO.setDateTo(occupied.getDateTo());
        occupiedDTO.setDateFrom(occupied.getDateFrom());

        try {
            this.requestClient.occupiedRequests(occupiedDTO);
            log.info("Successufully called request service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling request service");
        }
        occupied = this.occupiedRepository.save(occupied);
        log.info("Created occupied with id " + occupied.getId());

        return occupied.getId();
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

    public ResponseEntity<?> getOccupiedByUser(String username) {
        List<Car> cars = this.carService.getCarByUserUsername(username);
        List<OccupiedDTO> occupiedDTOS = new ArrayList<>();
        for(Car c: cars){
            List<Occupied> occupieds = this.occupiedRepository.findAllByCarAndDateToAfter(c, LocalDate.now());
            for(Occupied o: occupieds){
                OccupiedDTO occupiedDTO = new OccupiedDTO();
                occupiedDTO.setCar(modelMapper.map(o.getCar(), CarInfoDTO.class));
                occupiedDTO.setDateFrom(o.getDateFrom());
                occupiedDTO.setDateTo(o.getDateTo());
                occupiedDTO.setCarId(o.getCar().getId());
                occupiedDTOS.add(occupiedDTO);
            }
        }

        return new ResponseEntity<>(occupiedDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> newOccupiedRequest(OccupiedDTO occupiedDTO) {
        log.info("Adding new occupation for accepted request");
        Ad ad = this.adRepository.findById(occupiedDTO.getCarId()).orElseThrow(() -> new NotFoundException("Ad with given id was not found"));

        Occupied occupied = new Occupied();
        occupied.setCar(ad.getCar());
        occupied.setDateFrom(occupiedDTO.getDateFrom());
        occupied.setDateTo(occupiedDTO.getDateTo());
        this.occupiedRepository.save(occupied);
        log.info("Successfully created occupation");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
