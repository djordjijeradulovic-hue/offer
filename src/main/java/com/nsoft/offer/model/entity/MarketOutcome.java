package com.nsoft.offer.model.entity;

import com.nsoft.offer.model.enums.MarketOutcomeStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MarketOutcome {

    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private MarketOutcomeStatus status;

}
