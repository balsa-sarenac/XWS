package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.dto.NewAdRequestDTO;
import xws.team16.requestservice.exceptions.NotFoundException;
import xws.team16.requestservice.model.Ad;
import xws.team16.requestservice.model.User;
import xws.team16.requestservice.repository.AdRepository;

@Service @Slf4j
public class AdService {

    private AdRepository adRepository;
    private UserService userService;

    @Autowired
    public AdService(AdRepository adRepository, UserService userService) {
        this.adRepository = adRepository; this.userService = userService;
    }


    public Ad getAdById(Long adId) {
        log.info("Ad service - get ad by id");
        return this.adRepository.findById(adId).orElseThrow(() -> new NotFoundException("Ad with given id was nto found."));
    }

    public ResponseEntity<?> newAd(NewAdRequestDTO adDTO) {
        log.info("Creating new ad");
        User user = this.userService.getUserById(adDTO.getUserId());
        Ad ad = new Ad();

        ad.setId(ad.getId());
        ad.setUser(user);

        this.adRepository.save(ad);
        log.info("Ad created successfully");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
