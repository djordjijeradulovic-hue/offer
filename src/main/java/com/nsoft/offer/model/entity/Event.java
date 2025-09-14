package com.nsoft.offer.model.entity;

import com.nsoft.offer.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Event {

    @Id
    private String id;

    private String name;

    private Date startsAt;

    @Enumerated(EnumType.ORDINAL)
    private EventStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private List<EventMarket> markets;

}
