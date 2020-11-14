package org.optaweb.vehiclerouting.plugin.persistence.passenger;


import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.optaweb.vehiclerouting.domain.Planner;
import org.optaweb.vehiclerouting.plugin.persistence.PointEntity;
import org.optaweb.vehiclerouting.plugin.persistence.planner.PlannerEntity;


/**
 * Point
 */

@Entity
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String matriculation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
            this.id = id;
    }
    public String getMatriculation() {
        return this.matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }
    
    // https://wiki.openstreetmap.org/wiki/Node#Structure
    @Column(precision = 9, scale = 7)
    private BigDecimal latitude;

   
    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @ManyToOne(targetEntity=PlannerEntity.class)
    private PlannerEntity planner;
    
    @ManyToOne(targetEntity=PointEntity.class, fetch=FetchType.EAGER) 
    private PointEntity waypoint;
    
    private String cep;
 
    protected PassengerEntity() {
        // for JPA
    }

    PassengerEntity(long id, BigDecimal latitude, BigDecimal longitude, String matriculation,PlannerEntity planner) {
        this.id = id;
        this.latitude = Objects.requireNonNull(latitude);
        this.longitude = Objects.requireNonNull(longitude);
        this.matriculation = Objects.requireNonNull(matriculation);
        this.planner = Objects.requireNonNull(planner);
        
    }

  
    public BigDecimal getLatitude() {
        return latitude;
    }
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    public BigDecimal getLongitude() {
        return longitude;
    }
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    public PlannerEntity getPlanner() {return this.planner;}
    public void setPlanner(PlannerEntity planner) {this.planner = planner;}

    public String getCep() {return this.cep;}
    public void setCep(String cep) {this.cep = cep;}

    private String city;
    public String getCity() {return this.city;}
    public void setCity(String city) {this.city = city;}

    private String name;
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    private String street;
    public String getStreet() {return this.street;}
    public void setStreet(String street) {this.street = street;}

    public PointEntity getWaypoint() {return this.waypoint;}
    public void setWaypoint(PointEntity waypoint) {this.waypoint = waypoint;}
    

    @Override
    public String toString() {
        return "PassengerEntity{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", matriculation='" + matriculation + '\'' +
                '}';
    }

  
      
}