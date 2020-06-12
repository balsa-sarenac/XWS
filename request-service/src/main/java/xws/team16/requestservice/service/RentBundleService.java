package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.dto.BundleDTO;
import xws.team16.requestservice.dto.RequestDTO;
import xws.team16.requestservice.exceptions.InvalidOperationException;
import xws.team16.requestservice.exceptions.NotFoundException;
import xws.team16.requestservice.model.RentBundle;
import xws.team16.requestservice.model.RentRequest;
import xws.team16.requestservice.model.RequestStatus;
import xws.team16.requestservice.repository.RentBundleRepository;

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
            rentBundle.setRequests(new HashSet<>());
            for (RequestDTO request : bundle.getRequests()) {
                RentRequest rentRequest = this.rentRequestService.newRequest(request);
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
        return null;
    }
}
