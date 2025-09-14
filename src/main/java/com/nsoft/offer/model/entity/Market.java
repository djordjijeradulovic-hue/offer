package com.nsoft.offer.model.entity;

import com.nsoft.offer.model.enums.MarketStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Market {

    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private MarketStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "market_id")
    private List<MarketOutcome> outcomes;

}
