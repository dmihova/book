package com.tinqin.academy.library.persistence.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    @ToString.Exclude
    private User user;

    @Column(name = "start_date", nullable = false)
    @CreationTimestamp
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false )
    @CreationTimestamp
    private LocalDate endDate;

    @Column(name = "can_rent")
    private Boolean canRent;


}
