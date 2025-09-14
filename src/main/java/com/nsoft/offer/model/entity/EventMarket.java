package com.nsoft.offer.model.entity;

import com.nsoft.offer.model.enums.EventMarketStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class EventMarket {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "market_id", referencedColumnName = "id")
    private Market market;

    @Enumerated(EnumType.ORDINAL)
    private EventMarketStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_market_id")
    private List<EventMarketOutcome> outcomes;

}
