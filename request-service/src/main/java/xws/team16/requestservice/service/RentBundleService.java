package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.dto.BundleDTO;
import xws.team16.requestservice.dto.RequestDTO;
import xws.team16.requestservice.model.RentBundle;
import xws.team16.requestservice.model.RentRequest;
import xws.team16.requestservice.repository.RentBundleRepository;

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
            for (RequestDTO request : bundle.getRequests()) {
                RentRequest rentRequest = new RentRequest();
                this.rentRequestService.newRequest(rentRequest, request);
                rentBundle.getRequests().add(rentRequest);
            }
            this.rentBundleRepository.save(rentBundle);
        }
        log.info("All bundles were successfully created");
    }
}
