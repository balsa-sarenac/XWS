package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.dto.OccupiedDTO;
import xws.team16.requestservice.dto.RequestDTO;
import xws.team16.requestservice.dto.ShoppingCartDTO;
import xws.team16.requestservice.model.Ad;
import xws.team16.requestservice.model.RegisteredUser;
import xws.team16.requestservice.model.RentRequest;
import xws.team16.requestservice.model.RequestStatus;
import xws.team16.requestservice.repository.RentRequestRepository;

import java.util.List;

@Service @Slf4j
public class RentRequestService {

    private RentRequestRepository rentRequestRepository;
    private AdService adService;
    private RegisteredUserService registeredUserService;

    @Autowired
    private RentBundleService rentBundleService;

    @Autowired
    public RentRequestService(RentRequestRepository rentRequestRepository, AdService adService,
                              RegisteredUserService registeredUserService) {
        this.rentRequestRepository = rentRequestRepository;
        this.adService = adService;
        this.registeredUserService = registeredUserService;
    }

    public ResponseEntity<?> newRequests(ShoppingCartDTO shoppingCart) {
        log.info("Rent request service - add new ads");
        this.rentBundleService.newBundles(shoppingCart.getBundles());

        for (RequestDTO request : shoppingCart.getRequests()) {
            RentRequest rentRequest = newRequest(request);
        }
        log.info("All requests created successfully");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public RentRequest newRequest(RequestDTO request) {
        Ad ad = this.adService.getAdById(request.getAdId());
        RegisteredUser user = this.registeredUserService.findUserById(1L);  // GET USER FROM AUTHENTICATION

        RentRequest rentRequest = new RentRequest();
        rentRequest.setPickUpPlace(request.getPickUpPlace());
        rentRequest.setPickUpDate(request.getPickUpDate());
        rentRequest.setReturnDate(request.getReturnDate());
        rentRequest.setDateCreated(new DateTime());
        rentRequest.setAd(ad);
        rentRequest.setStatus(RequestStatus.pending);

        rentRequest.setUser(user);

        rentRequest = this.rentRequestRepository.save(rentRequest);
        return rentRequest;
    }

    public ResponseEntity<?> cancelOccupiedRequests(OccupiedDTO occupiedDTO) {
        log.info("Rent request service - cencel occupied requests");
        LocalDate occupiedFrom = occupiedDTO.getDateFrom().toLocalDate();
        LocalDate occupiedTo = occupiedDTO.getDateTo().toLocalDate();

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

        log.info("Rent request service - succesfully canceled pending requests");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
