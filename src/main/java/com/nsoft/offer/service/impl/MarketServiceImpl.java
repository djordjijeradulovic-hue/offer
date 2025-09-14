package com.nsoft.offer.service.impl;

import com.nsoft.offer.model.dto.MarketDto;
import com.nsoft.offer.model.dto.MarketOutcomeDto;
import com.nsoft.offer.model.entity.Market;
import com.nsoft.offer.model.entity.MarketOutcome;
import com.nsoft.offer.model.enums.MarketOutcomeStatus;
import com.nsoft.offer.model.enums.MarketStatus;
import com.nsoft.offer.repository.MarketRepository;
import com.nsoft.offer.service.IMarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MarketServiceImpl implements IMarketService {

    @Autowired
    MarketRepository marketRepository;

    @Override
    public MarketDto saveMarket(MarketDto marketDto) {
        log.info("[{}] Saving market with id: {}", this.getClass().getSimpleName(), marketDto.getId());
        var market = toMarketEntity(marketDto);
        var saved = marketRepository.save(market);
        return toMarketDto(saved);
    }

    @Override
    public void saveMarkets(List<MarketDto> markets) {
        log.info("[{}] Saving markets", this.getClass().getSimpleName());
        marketRepository.saveAll(markets.stream().map(this::toMarketEntity).toList());
    }

    private Market toMarketEntity(MarketDto dto) {
        var market = new Market();
        market.setId(dto.getId());
        market.setName(dto.getName());
        market.setStatus(MarketStatus.fromValue(dto.getStatus().getValue()));

        List<MarketOutcome> outcomes = new ArrayList<>();
        for (MarketOutcomeDto outcomeDTO : dto.getOutcomes()) {
            var outcome = new MarketOutcome();
            outcome.setId(outcomeDTO.getId());
            outcome.setName(outcomeDTO.getName());
            outcome.setStatus(MarketOutcomeStatus.fromValue(outcomeDTO.getStatus().getValue()));
            outcomes.add(outcome);
        }
        market.setOutcomes(outcomes);
        return market;
    }

    private MarketDto toMarketDto(Market market) {
        var dto = new MarketDto();
        dto.setId(market.getId());
        dto.setName(market.getName());
        dto.setStatus(MarketDto.StatusEnum.fromValue(market.getStatus().ordinal()));
        List<MarketOutcomeDto> outcomes = new ArrayList<>();
        for (MarketOutcome outcome : market.getOutcomes()) {
            var outcomeDto = new MarketOutcomeDto();
            outcomeDto.setId(outcome.getId());
            outcomeDto.setName(outcome.getName());
            outcomeDto.setStatus(MarketOutcomeDto.StatusEnum.fromValue(outcome.getStatus().ordinal()));
            outcomes.add(outcomeDto);
        }
        dto.setOutcomes(outcomes);
        return dto;
    }

}
