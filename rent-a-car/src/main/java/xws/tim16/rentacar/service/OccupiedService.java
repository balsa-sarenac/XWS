package xws.tim16.rentacar.service;


import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
import xws.tim16.rentacar.dto.CarInfoDTO;
import xws.tim16.rentacar.dto.OccupiedDTO;
import xws.tim16.rentacar.generated.PostOccupiedResponse;
import xws.tim16.rentacar.generated.TOccupied;
import xws.tim16.rentacar.model.*;
import xws.tim16.rentacar.repository.OccupiedRepository;
import xws.tim16.rentacar.repository.RentRequestRepository;


import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class OccupiedService {
    private OccupiedRepository occupiedRepository;
    private CarService carService;
    private RentRequestRepository rentRequestRepository;
    private CarClient carClient;
    private ModelMapper modelMapper;

    @Autowired
    private RentBundleService rentBundleService;
    public OccupiedService(OccupiedRepository occupiedRepository, CarService carService, RentRequestRepository rentRequestRepository, CarClient carClient, ModelMapper modelMapper) {
        this.occupiedRepository = occupiedRepository;
        this.carService = carService;
        this.rentRequestRepository = rentRequestRepository;
        this.carClient = carClient;
        this.modelMapper = modelMapper;
    }

    public boolean checkPaids(List< RentRequest> rentRequests, OccupiedDTO occupiedDTO){
        LocalDate occupiedFrom = occupiedDTO.getDateFrom();
        LocalDate occupiedTo = occupiedDTO.getDateTo();

        for(RentRequest r: rentRequests){
            LocalDate rentFrom = r.getPickUpDate();
            LocalDate rentTo = r.getReturnDate();
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

        List<RentRequest> rentRequestss = this.rentRequestRepository.findAllByAdCarIdAndStatus(occupiedDTO.getCarId(), RequestStatus.paid);
        List<Occupied> occupiedss = this.occupiedRepository.findAllByCarId(occupiedDTO.getCarId());

        boolean flag = checkPaids(rentRequestss, occupiedDTO);

        if(flag == true){
            return  new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        flag = checkOccupied(occupiedss, occupiedDTO);

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

        occupied = this.occupiedRepository.save(occupied);
        log.info("Created occupied with id " + occupied.getId());

        LocalDate occupiedFrom = occupiedDTO.getDateFrom();
        LocalDate occupiedTo = occupiedDTO.getDateTo();

        for (Long id: occupiedDTO.getAdsId()){
            List<RentRequest> rentRequests = this.rentRequestRepository.findByAdId(id);
            for (RentRequest rent : rentRequests){
                LocalDate rentFrom = rent.getPickUpDate();
                LocalDate rentTo = rent.getReturnDate();
                if(rent.getStatus().equals(RequestStatus.pending)) {
                    if (!rentFrom.isBefore(occupiedFrom) && !rentTo.isAfter(occupiedTo)) {
                        rent.setStatus(RequestStatus.cancelled);
                        if(rent.getBundle() != null){
                            this.rentBundleService.cancelBundle(rent.getBundle().getId());
                        }
                        this.rentRequestRepository.save(rent);
                    }
                    if (!rentFrom.isAfter(occupiedFrom) && !rentTo.isBefore(occupiedTo)) {
                        rent.setStatus(RequestStatus.cancelled);
                        if(rent.getBundle() != null){
                            this.rentBundleService.cancelBundle(rent.getBundle().getId());
                        }
                        this.rentRequestRepository.save(rent);
                    }
                    if (!rentFrom.isAfter(occupiedFrom) && !rentTo.isAfter(occupiedTo) && !rentTo.isBefore(occupiedFrom)) {
                        rent.setStatus(RequestStatus.cancelled);
                        if(rent.getBundle() != null){
                            this.rentBundleService.cancelBundle(rent.getBundle().getId());
                        }
                        this.rentRequestRepository.save(rent);
                    }
                    if (!rentFrom.isBefore(occupiedFrom) && !rentTo.isBefore(occupiedTo) && !rentFrom.isAfter(occupiedTo)) {
                        rent.setStatus(RequestStatus.cancelled);
                        if(rent.getBundle() != null){
                            this.rentBundleService.cancelBundle(rent.getBundle().getId());
                        }
                        this.rentRequestRepository.save(rent);
                    }
                }
            }
        }

        /*
        log.info("Sending soap request to car service");
        TOccupied tOccupied = new TOccupied();
        tOccupied.setCarId(occupied.getId());
        DateTime dateTime =  DateTime.now();
        tOccupied.setDateFrom(dateTime.getMillis());
        dateTime = dateTime.plusDays(3);
        tOccupied.setDateTo(dateTime.getMillis());
        try {
            PostOccupiedResponse response = this.carClient.postNewOccupied(tOccupied);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Soap request successfully finished");*/

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

    public void saveRequestAsOccupied(RentRequest request) {
        log.info("Adding new occupation for accepted request");
        Occupied occupied = new Occupied();
        occupied.setCar(request.getAd().getCar());
        occupied.setDateFrom(request.getPickUpDate());
        occupied.setDateTo(request.getReturnDate());
        this.occupiedRepository.save(occupied);
    }
}
