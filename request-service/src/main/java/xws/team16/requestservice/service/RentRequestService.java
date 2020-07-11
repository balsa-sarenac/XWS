package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.client.MailClient;
import xws.team16.requestservice.client.SecurityClient;
import xws.team16.requestservice.dto.*;
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
    private SecurityClient securityClient;

    @Autowired
    private MailClient mailClient;

    @Autowired
    public RentRequestService(RentRequestRepository rentRequestRepository, AdService adService,
                              UserService userService) {
        this.rentRequestRepository = rentRequestRepository;
        this.adService = adService;
        this.userService = userService;
    }


    public ResponseEntity<?> getAll() {
        log.info("Get all requests for user");
        User user = this.userService.getLoggedInUser();
        List<RentRequest> requests = this.rentRequestRepository.findByUser(user);

        List<RequestDTO> retVal = getRequestDTOS(requests);
        AllRequestsDTO requestsDTO = this.filterRequests(retVal);

        log.info("Returning list of requests");
        return new ResponseEntity<>(requestsDTO, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllReceived() {
        log.info("Get all received requests for user");
        User user = this.userService.getLoggedInUser();
        List<RentRequest> requests = this.rentRequestRepository.findAllByAd_User_Id(user.getId());

        List<RequestDTO> retVal = getRequestDTOS(requests);
        AllRequestsDTO requestsDTO = this.filterRequests(retVal);

        log.info("Returning list of requests");
        return new ResponseEntity<>(requestsDTO, HttpStatus.OK);
    }

    public AllRequestsDTO filterRequests(List<RequestDTO> retVal) {
        AllRequestsDTO requestsDTO = new AllRequestsDTO();
        List<RequestDTO> pending = new ArrayList<>();
        List<RequestDTO> paid = new ArrayList<>();
        List<RequestDTO> finished = new ArrayList<>();
        retVal.forEach(req -> {
            if (req.getStatus().equals(RequestStatus.pending.toString())) {
                pending.add(req);
            } else if (req.getStatus().equals(RequestStatus.paid.toString())) {
                if (req.getReturnDate().isBefore(new LocalDate())) {
                    finished.add(req);
                } else {
                    paid.add(req);
                }
            }
        });

        requestsDTO.setAll(retVal);
        requestsDTO.setPending(pending);
        requestsDTO.setPaid(paid);
        requestsDTO.setFinished(finished);
        return requestsDTO;
    }


    public List<RequestDTO> getRequestDTOS(List<RentRequest> requests) {
        List<RequestDTO> retVal = new ArrayList<>();
        for (RentRequest request : requests) {
            retVal.add(RequestDTO.builder()
                    .id(request.getId())
                    .returnDate(request.getReturnDate())
                    .pickUpDate(request.getPickUpDate())
                    .pickUpPlace(request.getPickUpPlace())
                    .adId(request.getAd().getId())
                    .status(String.valueOf(request.getStatus()))
                    .userId(request.getUser().getId())
                    .bundleId(request.getBundle() != null ? request.getBundle().getId() : -1)
                    .build());
        }
        return retVal;
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

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlAllowOrigin("*");

//        try {
//            sendMail("Rent request approved!", "You rent request has been approved");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return new ResponseEntity<>(headers, HttpStatus.OK);
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

    public ResponseEntity<?> refuseRequest(Long requestId) {
        log.info("Rent request service - refuse request");
        RentRequest request = this.rentRequestRepository.findById(requestId).orElseThrow(() -> new NotFoundException("Rent request with given id was not found"));
        if (!request.getStatus().equals(RequestStatus.pending))
            throw new InvalidOperationException("Rent request cannot be refused, it's not in pending state, but has status: " + request.getStatus());
        request.setStatus(RequestStatus.refused);
        this.rentRequestRepository.save(request);
        log.info("Successfully refused rent request");

//        try {
//            sendMail("Rent request refused!", "You rent request has been refused");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> cancel(Long id) {
        log.info("Rent request service - cancel request");
        RentRequest rentRequest = this.rentRequestRepository.findById(id).orElseThrow(() -> new NotFoundException("Request with given id was not found"));
        if (rentRequest.getStatus().equals(RequestStatus.paid))
            throw new InvalidOperationException("Rent request has already been paid. It cannot be cancelled.");
        rentRequest.setStatus(RequestStatus.cancelled);
        this.rentRequestRepository.save(rentRequest);
        log.info("Request cancelled");
        if(rentRequest.getBundle() != null){
            log.info("Rent request service - canceling bundle");
            this.rentBundleService.cancelBundle(rentRequest.getBundle().getId());
            log.info("Rent request service - bundle cancelled");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void cancelPendingRequests() {
        List<RentRequest> requests = this.rentRequestRepository.findAllByStatus(RequestStatus.pending);
        List<RentRequest> cancelled = new ArrayList<>();
        DateTime now = DateTime.now();
        for (RentRequest request: requests) {
            if (request.getDateCreated().plusDays(1).isBefore(now)) {
                request.setStatus(RequestStatus.cancelled);
                cancelled.add(request);
            }
        }
        this.rentRequestRepository.saveAll(cancelled);
    }

    public void sendMail(String subject, String message) {
        ResponseEntity<String> retVal = (ResponseEntity<String>) this.securityClient.getEmailForUser(SecurityContextHolder.getContext().getAuthentication().getName());
        String email = retVal.getBody();
        log.info(email);
        MailDTO mailDTO = new MailDTO();
        mailDTO.setEmail(email);
        mailDTO.setMessage(message);
        mailDTO.setSubject(subject);
        this.mailClient.sendMail(mailDTO);
        log.info("Mail sent");
    }
}
