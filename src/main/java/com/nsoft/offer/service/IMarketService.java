package com.nsoft.offer.service;

import com.nsoft.offer.model.dto.MarketDto;

import java.util.List;

public interface IMarketService {

    MarketDto saveMarket(MarketDto market);

    void saveMarkets(List<MarketDto> markets);

}
