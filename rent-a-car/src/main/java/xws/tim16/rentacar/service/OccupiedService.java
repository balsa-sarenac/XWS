package xws.tim16.rentacar.service;


import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.client.CarClient;
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

    @Autowired
    public OccupiedService(OccupiedRepository occupiedRepository, CarService carService, RentRequestRepository rentRequestRepository, CarClient carClient) {
        this.occupiedRepository = occupiedRepository;
        this.carService = carService;
        this.rentRequestRepository = rentRequestRepository;
        this.carClient = carClient;
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

        LocalDate occupiedFrom = occupiedDTO.getDateFrom();
        LocalDate occupiedTo = occupiedDTO.getDateTo();

        for (Long id: occupiedDTO.getAdsId()){
            List<RentRequest> rentRequests = this.rentRequestRepository.findByAdId(id);
            for (RentRequest rent : rentRequests){
                LocalDate rentFrom = rent.getPickUpDate();
                LocalDate rentTo = rent.getReturnDate();
                if(rent.getStatus().equals(RequestStatus.pending)) {
                    if (rentFrom.isAfter(occupiedFrom) && rentTo.isBefore(occupiedTo)) {
                        rent.setStatus(RequestStatus.canceled);
                        this.rentRequestRepository.save(rent);
                    }
                    if (rentFrom.isBefore(occupiedFrom) && rentTo.isAfter(occupiedTo)) {
                        rent.setStatus(RequestStatus.canceled);
                        this.rentRequestRepository.save(rent);
                    }
                    if (rentFrom.isBefore(occupiedFrom) && rentTo.isBefore(occupiedTo) && rentTo.isAfter(occupiedFrom)) {
                        rent.setStatus(RequestStatus.canceled);
                        this.rentRequestRepository.save(rent);
                    }
                    if (rentFrom.isAfter(occupiedFrom) && rentTo.isAfter(occupiedTo) && rentFrom.isBefore(occupiedTo)) {
                        rent.setStatus(RequestStatus.canceled);
                        this.rentRequestRepository.save(rent);
                    }
                }
            }
        }

        log.info("Sending soap request to car service");
        TOccupied tOccupied = new TOccupied();
        tOccupied.setCarId(occupied.getId());
        DateTime dateTime =  DateTime.now();
        tOccupied.setDateFrom(dateTime.getMillis());
        dateTime = dateTime.plusDays(3);
        tOccupied.setDateTo(dateTime.getMillis());
        PostOccupiedResponse response = this.carClient.postNewOccupied(tOccupied);
        log.info("Soap request successfully finished");

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
