package org.optaweb.vehiclerouting.plugin.persistence.route;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;
import org.optaweb.vehiclerouting.domain.Location;
import org.optaweb.vehiclerouting.plugin.persistence.passenger.PassengerEntity;

import org.optaweb.vehiclerouting.plugin.persistence.PointEntity;
import org.optaweb.vehiclerouting.plugin.persistence.VehicleEntity;
import org.optaweb.vehiclerouting.plugin.persistence.VehicleRoutingEntity;
import org.optaweb.vehiclerouting.plugin.persistence.planner.PlannerEntity;

import javax.persistence.Id;

/**
 * Point
 */

@Entity
public class RouteEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    public Long getId() {return this.id;}
    public String name;
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    @Length(max = 10000)
    public String track;
    public String getTrack() {return this.track;}
    public void setTrack(String track) {this.track = track;}


    @ManyToMany(targetEntity=PointEntity.class, fetch=FetchType.EAGER)
    @JoinColumn(name="visit_id")
    private List<PointEntity> visits;
    public List<PointEntity> getVisits() {return this.visits;}
    public void setVisits(List<PointEntity> visits) {this.visits = visits;}
    
    @ManyToOne(targetEntity=VehicleRoutingEntity.class,fetch = FetchType.EAGER)
    public VehicleRoutingEntity vehicle;
   
      
}