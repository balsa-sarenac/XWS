package xws.team16.carservice.service;

import com.netflix.discovery.converters.Auto;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xws.team16.carservice.client.SearchClient;
import xws.team16.carservice.dto.PriceListDTO;
import xws.team16.carservice.exceptions.NotFoundException;
import xws.team16.carservice.generated.car.EditPriceListResponse;
import xws.team16.carservice.generated.car.PostPriceListRequest;
import xws.team16.carservice.generated.car.PostPriceListResponse;
import xws.team16.carservice.generated.car.TPriceList;
import xws.team16.carservice.model.PriceList;
import xws.team16.carservice.model.User;
import xws.team16.carservice.repository.PriceListRepository;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class PriceListService {

    private PriceListRepository priceListRepository;
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    private SearchClient searchClient;


    @Autowired
    public PriceListService(PriceListRepository priceListRepository, ModelMapper modelMapper, UserService userService) {
        this.priceListRepository = priceListRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public PriceList getPriceListById(Long priceListId) {
        log.info("PriceList service - get price list by id");
        return this.priceListRepository.findById(priceListId).orElseThrow(() -> new NotFoundException("PriceList with given id was nto found"));
    }

    public ResponseEntity<?> createPriceList(PriceListDTO priceListDTO) {
        log.info("Price list service - creating price list");
        PriceList priceList = modelMapper.map(priceListDTO, PriceList.class);
        User user = this.userService.getUserByUsername(priceListDTO.getUserUsername());
        priceList.setUser(user);

        log.info("Price list service - calling feign search");
        try {
            this.searchClient.createPriceList(priceListDTO);
            log.info("Successufully called search service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling search service");
        }

        this.priceListRepository.save(priceList);
        log.info("Price list service - price list created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> editPriceList(PriceListDTO priceListDTO) {
        log.info("Price list service - editing price list");
        PriceList priceList = this.priceListRepository.getOne(priceListDTO.getId());
        priceList.setCdw(priceListDTO.getCdw());
        priceList.setDiscount(priceListDTO.getDiscount());
        priceList.setExtraKilometrage(priceListDTO.getExtraKilometrage());
        priceList.setDiscountDays(priceListDTO.getDiscountDays());
        priceList.setPerDay(priceListDTO.getPerDay());

        log.info("Price list service - calling feign search");
        try {
            this.searchClient.editPriceList(priceListDTO);
            log.info("Successufully called search service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling search service");
        }

        this.priceListRepository.save(priceList);
        log.info("Price list service - price list edited");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> getPriceListUser(String username) {
        log.info("Price list service - getting price lists for user");
        List<PriceList> priceLists = this.priceListRepository.findAllByUserUsername(username);
        List<PriceListDTO> priceListDTOS = new ArrayList<>();

        for(PriceList p: priceLists){
            PriceListDTO pdto =  modelMapper.map(p, PriceListDTO.class);
            pdto.setUserUsername(p.getUser().getUsername());

            priceListDTOS.add(pdto);
        }
        return new ResponseEntity<>(priceListDTOS,HttpStatus.OK);
    }

    public PostPriceListResponse postPriceListSoap(TPriceList priceListRequest) {
        log.info("Price list service - creating price list");
        PriceList priceList = modelMapper.map(priceListRequest, PriceList.class);
        User user = this.userService.getUserByUsername(priceListRequest.getUserUsername());
        priceList.setUser(user);
        PriceListDTO priceListDTO = modelMapper.map(priceListRequest, PriceListDTO.class);

        log.info("Price list service - calling feign search");
        try {
            this.searchClient.createPriceList(priceListDTO);
            log.info("Successufully called search service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling search service");
        }

        priceList = this.priceListRepository.save(priceList);
        log.info("Price list service - price list created");
        PostPriceListResponse response = new PostPriceListResponse();
        response.setPriceListResponse(priceList.getId());
        return response;
    }

    public EditPriceListResponse editPriceListSoap(TPriceList priceListRequest) {
        log.info("Price list service - editing price list");
        PriceList priceList = this.priceListRepository.getOnePriceList(priceListRequest.getId());
        priceList.setCdw(priceListRequest.getCdw());
        priceList.setDiscount(priceListRequest.getDiscount());
        priceList.setExtraKilometrage(priceListRequest.getExtraKilometrage());
        priceList.setDiscountDays(priceListRequest.getDiscountDays());
        priceList.setPerDay(priceListRequest.getPerDay());

        PriceListDTO priceListDTO = modelMapper.map(priceListRequest, PriceListDTO.class);
        log.info("Price list service - calling feign search");
        try {
            this.searchClient.editPriceList(priceListDTO);
            log.info("Successufully called search service");
        } catch (FeignException.NotFound e) {
            log.info("Error calling search service");
        }

        priceList = this.priceListRepository.save(priceList);
        log.info("Price list service - price list created");
        EditPriceListResponse response = new EditPriceListResponse();
        response.setPriceListResponse(priceList.getId());
        return response;

    }
}
