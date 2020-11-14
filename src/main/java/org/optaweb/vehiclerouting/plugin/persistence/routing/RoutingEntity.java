package org.optaweb.vehiclerouting.plugin.persistence.routing;


import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.optaweb.vehiclerouting.domain.Location;
import org.optaweb.vehiclerouting.plugin.persistence.passenger.PassengerEntity;

import org.optaweb.vehiclerouting.plugin.persistence.LocationEntity;
import org.optaweb.vehiclerouting.plugin.persistence.PointEntity;
import org.optaweb.vehiclerouting.plugin.persistence.planner.PlannerEntity;
import org.optaweb.vehiclerouting.plugin.persistence.route.RouteEntity;

import javax.persistence.Id;

/**
 * Point
 */

@Entity
public class RoutingEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    public Long getId() {return this.id;}
   
    public String name;
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}


    public String distance;
    public String getDistance() {return this.distance;}
    public void setDistance(String distance) {this.distance = distance;}

    @OneToMany(targetEntity=RouteEntity.class, fetch=FetchType.EAGER)
    public Set<RouteEntity> routes;
    
    @ManyToOne(targetEntity=PointEntity.class, fetch=FetchType.EAGER) 
    public PointEntity origin;

    @ManyToOne(targetEntity=PointEntity.class, fetch=FetchType.EAGER) 
    public PointEntity destiny; 

    @ManyToMany(targetEntity=PassengerEntity.class, fetch=FetchType.EAGER)
    @JoinColumn(name="passenger_id")
    private Set<PassengerEntity> passengers;
    public Set<PassengerEntity> getPassengers() {return this.passengers;}
    public void setPassengers(Set<PassengerEntity> passengers) {this.passengers = passengers;}
    /*
    @ManyToMany(targetEntity=LocationEntity.class, fetch=FetchType.EAGER)
    @JoinColumn(name="visit_id")
    private List<LocationEntity> visits;
    public List<LocationEntity> getVisits() {return this.visits;}
    public void setVisits(List<LocationEntity> visits) {this.visits = visits;}
    */
    /*
    */
    /*
   
    */
    
    @ManyToOne(targetEntity=PlannerEntity.class, fetch=FetchType.EAGER)
    private PlannerEntity planner;
    public PlannerEntity getPlanner() {return this.planner;}
    public void setPlanner(PlannerEntity planner) {this.planner = planner;}

    public Integer rangePoint;
    public Integer getRangePoint() {return this.rangePoint;}
    public void setRangePoint(Integer rangePoint) {this.rangePoint = rangePoint;}

    
}