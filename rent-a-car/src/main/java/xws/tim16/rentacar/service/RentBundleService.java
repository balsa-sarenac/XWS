package xws.tim16.rentacar.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.tim16.rentacar.dto.BundleDTO;
import xws.tim16.rentacar.dto.RequestDTO;
import xws.tim16.rentacar.exception.InvalidOperationException;
import xws.tim16.rentacar.exception.NotFoundException;
import xws.tim16.rentacar.model.RentBundle;
import xws.tim16.rentacar.model.RentRequest;
import xws.tim16.rentacar.model.RequestStatus;
import xws.tim16.rentacar.repository.RentBundleRepository;

import java.util.HashSet;
import java.util.List;

@Service @Slf4j
public class RentBundleService {

    @Autowired
    private RentBundleRepository rentBundleRepository;

    @Autowired
    private RentRequestService rentRequestService;


    public void newBundles(List<BundleDTO> bundles) {
        log.info("Rent bundle service - add bundles");

        for (BundleDTO bundle : bundles) {
            RentBundle rentBundle = new RentBundle();
            rentBundle.setBundleStatus(RequestStatus.pending);
            rentBundle.setRequests(new HashSet<>());
            for (RequestDTO request : bundle.getRequests()) {
                RentRequest rentRequest = this.rentRequestService.newRequest(request, rentBundle);
                rentBundle.getRequests().add(rentRequest);
            }
            this.rentBundleRepository.save(rentBundle);
            log.info("Bundle created");
        }
    }

    public ResponseEntity<?> cancelBundle(Long bundleId) {
        log.info("Rent bundle service - cancel bundle");
        RentBundle bundle = this.rentBundleRepository.findById(bundleId).orElseThrow(() -> new NotFoundException("Bundle with given id was not found"));
        if (bundle.getBundleStatus().equals(RequestStatus.paid))
            throw new InvalidOperationException("Bundle request has already been paid. It cannot be cancelled.");
        bundle.setBundleStatus(RequestStatus.cancelled);
        for (RentRequest request: bundle.getRequests()) {
            request.setStatus(RequestStatus.cancelled);
        }
        this.rentBundleRepository.save(bundle);
        log.info("Bundle and all requests cancelled successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void cancelBundleRequest(Long bundleId) {
        log.info("Rent bundle service - cancel bundle");
        RentBundle bundle = this.rentBundleRepository.findById(bundleId).orElseThrow(() -> new NotFoundException("Bundle with given id was not found"));
        if (bundle.getBundleStatus().equals(RequestStatus.paid))
            throw new InvalidOperationException("Bundle request has already been paid. It cannot be cancelled.");
        bundle.setBundleStatus(RequestStatus.cancelled);
        for (RentRequest request: bundle.getRequests()) {
            request.setStatus(RequestStatus.cancelled);
        }
        this.rentBundleRepository.save(bundle);
        log.info("Bundle and all requests cancelled successfully");
    }

    public ResponseEntity<?> acceptBundle(Long bundleId) {
        log.info("Rent bundle service - accept bundle");
        RentBundle rentBundle = this.rentBundleRepository.findById(bundleId).orElseThrow(() -> new NotFoundException("Bundle with given id was not found"));
        if (!rentBundle.getBundleStatus().equals(RequestStatus.pending))
            throw new InvalidOperationException("Rent request cannot be accepted, it's not in pending state, but has status: " + rentBundle.getBundleStatus());

        rentBundle.setBundleStatus(RequestStatus.reserved);
        rentBundle.setBundleStatus(RequestStatus.paid);

        for (RentRequest request: rentBundle.getRequests()) {
            log.info(String.valueOf(request.getStatus()));
            if (request.getStatus().equals(RequestStatus.pending))
                this.rentRequestService.acceptRequest(request);
        }

        this.rentBundleRepository.save(rentBundle);
        log.info("Successfully saved everything");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> refuseBundle(Long bundleId) {
        log.info("Rent bundle service - refuse bundle");
        RentBundle bundle = this.rentBundleRepository.findById(bundleId).orElseThrow(() -> new NotFoundException("Bundle with given id was not found"));
        if (!bundle.getBundleStatus().equals(RequestStatus.pending))
            throw new InvalidOperationException("Rent bundle cannot be refused, it's not in pending state, but has status: " + bundle.getBundleStatus());
        bundle.setBundleStatus(RequestStatus.refused);

        for (RentRequest request: bundle.getRequests()) {
            log.info(String.valueOf(request.getStatus()));
            request.setStatus(RequestStatus.refused);
        }
        this.rentBundleRepository.save(bundle);
        log.info("Bundle refused successfully");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
