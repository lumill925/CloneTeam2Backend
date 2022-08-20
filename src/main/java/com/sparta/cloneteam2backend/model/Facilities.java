package com.sparta.cloneteam2backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Facilities {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long facilitiesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    private Post post;

    private Boolean facilitiesParking;

    private Boolean facilitiesWifi;

    private Boolean facilitiesSwimmingpool;

    private Boolean facilitiesAirconditioner;

    private Boolean facilitiesTv;

    public Facilities(Post post, boolean facilitiesParking, boolean facilitiesWifi, boolean facilitiesSwimmingpool, boolean facilitiesAirconditioner, boolean facilitiesTv){
        this.post = post;
        this.facilitiesParking = facilitiesParking;
        this.facilitiesWifi = facilitiesWifi;
        this.facilitiesSwimmingpool = facilitiesSwimmingpool;
        this.facilitiesAirconditioner = facilitiesAirconditioner;
        this.facilitiesTv = facilitiesTv;
    }

}
