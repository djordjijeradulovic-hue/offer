package com.nsoft.offer.model.entity;

import com.nsoft.offer.model.enums.EventMarketOutcomeStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EventMarketOutcome {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "outcome_id", referencedColumnName = "id")
    private MarketOutcome outcome;

    @Enumerated(EnumType.ORDINAL)
    private EventMarketOutcomeStatus status;

    private Double odd;

}
