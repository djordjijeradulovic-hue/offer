package com.nsoft.offer.api;

import com.nsoft.offer.api.spec.MarketsApi;
import com.nsoft.offer.kafka.producer.MarketProducer;
import com.nsoft.offer.model.dto.MarketDto;
import com.nsoft.offer.service.IMarketService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MarketsApiImpl implements MarketsApi {

    @Autowired
    IMarketService marketService;

    @Autowired
    MarketProducer marketProducer;

    @Override
    @Transactional
    public ResponseEntity<MarketDto> createMarket(MarketDto dto) {
        log.info("[{}] Create market", this.getClass().getSimpleName());
        var response = marketService.saveMarket(dto);
        marketProducer.sendMarket(response.getId(), response);
        return ResponseEntity.ok(response);
    }
}
