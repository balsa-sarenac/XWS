package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.dto.OccupiedDTO;
import xws.team16.requestservice.dto.RequestDTO;
import xws.team16.requestservice.dto.ShoppingCartDTO;
import xws.team16.requestservice.exceptions.InvalidOperationException;
import xws.team16.requestservice.exceptions.NotFoundException;
import xws.team16.requestservice.model.*;
import xws.team16.requestservice.repository.RentRequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class RentRequestService {

    private RentRequestRepository rentRequestRepository;
    private AdService adService;
    private UserService userService;

    @Autowired
    private RentBundleService rentBundleService;

    @Autowired
    public RentRequestService(RentRequestRepository rentRequestRepository, AdService adService,
                              UserService userService) {
        this.rentRequestRepository = rentRequestRepository;
        this.adService = adService;
        this.userService = userService;
    }

    public ResponseEntity<?> getAll(Long userId) {
        log.info("Rent request service - get all requests for user");
        User user = this.userService.getUserById(userId);
        List<RentRequest> requests = this.rentRequestRepository.findByUser(user);

        List<RequestDTO> retVal = new ArrayList<>();
        for (RentRequest request: requests) {
            retVal.add(RequestDTO.builder()
                    .id(request.getId())
                    .returnDate(request.getReturnDate())
                    .pickUpDate(request.getPickUpDate())
                    .pickUpPlace(request.getPickUpPlace())
                    .adId(request.getAd().getId())
                    .status(String.valueOf(request.getStatus()))
                    .bundleId(request.getBundle() != null ? request.getBundle().getId() : -1)
                    .build());
        }
        log.info("Returning list of requests");
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    public ResponseEntity<?> newRequests(ShoppingCartDTO shoppingCart) {
        log.info("Rent request service - add new ads");
        this.rentBundleService.newBundles(shoppingCart.getBundles());

        for (RequestDTO request : shoppingCart.getRequests()) {
            RentRequest rentRequest = newRequest(request, null);
            rentRequest = this.rentRequestRepository.save(rentRequest);
        }
        log.info("All requests created successfully");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public RentRequest newRequest(RequestDTO request, RentBundle bundle) {
        Ad ad = this.adService.getAdById(request.getAdId());
        User user = this.userService.getUserById(1L);  // GET USER FROM AUTHENTICATION

        RentRequest rentRequest = new RentRequest();
        rentRequest.setPickUpPlace(request.getPickUpPlace());
        rentRequest.setPickUpDate(request.getPickUpDate());
        rentRequest.setReturnDate(request.getReturnDate());
        rentRequest.setDateCreated(new DateTime());
        rentRequest.setAd(ad);
        rentRequest.setStatus(RequestStatus.pending);
        rentRequest.setBundle(bundle);

        rentRequest.setUser(user);

        return rentRequest;
    }

    public ResponseEntity<?> cancelOccupiedRequests(OccupiedDTO occupiedDTO) {
        log.info("Rent request service - cancel occupied requests");
        LocalDate occupiedFrom = occupiedDTO.getDateFrom();
        LocalDate occupiedTo = occupiedDTO.getDateTo();

        for (Long id: occupiedDTO.getAdsId()){
            List<RentRequest> rentRequests = this.rentRequestRepository.findByAdId(id);
            for (RentRequest rent : rentRequests){
                LocalDate rentFrom = rent.getPickUpDate();
                LocalDate rentTo = rent.getReturnDate();
                if(rent.getStatus().equals(RequestStatus.pending)) {
                    if (rentFrom.isAfter(occupiedFrom) && rentTo.isBefore(occupiedTo)) {
                        rent.setStatus(RequestStatus.cancelled);
                        this.rentRequestRepository.save(rent);
                    }
                    if (rentFrom.isBefore(occupiedFrom) && rentTo.isAfter(occupiedTo)) {
                        rent.setStatus(RequestStatus.cancelled);
                        this.rentRequestRepository.save(rent);
                    }
                    if (rentFrom.isBefore(occupiedFrom) && rentTo.isBefore(occupiedTo) && rentTo.isAfter(occupiedFrom)) {
                        rent.setStatus(RequestStatus.cancelled);
                        this.rentRequestRepository.save(rent);
                    }
                    if (rentFrom.isAfter(occupiedFrom) && rentTo.isAfter(occupiedTo) && rentFrom.isBefore(occupiedTo)) {
                        rent.setStatus(RequestStatus.cancelled);
                        this.rentRequestRepository.save(rent);
                    }
                }
            }
        }

        log.info("Rent request service - successfully canceled pending requests");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> cancelRequest(Long requestId) {
        log.info("Rent request service - cancel request");
        RentRequest rentRequest = this.rentRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("Request with given id was not found"));
        if (rentRequest.getStatus().equals(RequestStatus.paid))
            throw new InvalidOperationException("Rent request has already been paid. It cannot be cancelled.");
        rentRequest.setStatus(RequestStatus.cancelled);
        this.rentRequestRepository.save(rentRequest);
        log.info("Request cancelled");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> acceptRequest(Long requestId) {
        log.info("Rent request service - accept request");
        RentRequest request = this.rentRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("Request with given id was not found"));
        acceptRequest(request);

        log.info("Successfully canceled other requests, saving in database");
        this.rentRequestRepository.save(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void acceptRequest(RentRequest request) {
        if (!request.getStatus().equals(RequestStatus.pending))
            throw new InvalidOperationException("Rent request cannot be accepted, it's not in pending state, but has status: " + request.getStatus());

        request.setStatus(RequestStatus.reserved);
        request.setStatus(RequestStatus.paid);
        log.info("Rent request status changed to paid");

        List<RentRequest> requests = this.rentRequestRepository.findByAdId(request.getAd().getId());
        List<Long> adsId = new ArrayList<>();
        for (RentRequest r: requests)
            adsId.add(r.getAd().getId());

        OccupiedDTO occupiedDTO = OccupiedDTO.builder()
                .id(request.getId())
                .dateFrom(request.getPickUpDate())
                .dateTo(request.getReturnDate())
                .adsId(adsId)
                .build();
        cancelOccupiedRequests(occupiedDTO);
    }
}
