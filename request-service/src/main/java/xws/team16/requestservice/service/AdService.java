package xws.team16.requestservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xws.team16.requestservice.exceptions.NotFoundException;
import xws.team16.requestservice.model.Ad;
import xws.team16.requestservice.repository.AdRepository;

@Service @Slf4j
public class AdService {

    private AdRepository adRepository;

    @Autowired
    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }


    public Ad getAdById(Long adId) {
        log.info("Ad service - get ad by id");
        return this.adRepository.findById(adId).orElseThrow(() -> new NotFoundException("Ad with given id was nto found."));
    }
}
