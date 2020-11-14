package org.optaweb.vehiclerouting.plugin.websocket;



import org.optaweb.vehiclerouting.plugin.persistence.routing.RoutingEntity;
import org.optaweb.vehiclerouting.service.location.LocationRepository;

import java.util.Optional;

import org.optaweb.vehiclerouting.plugin.persistence.LocationCrudRepository;
import org.optaweb.vehiclerouting.plugin.persistence.LocationEntity;
import org.optaweb.vehiclerouting.plugin.persistence.PointCrudRepository;
import org.optaweb.vehiclerouting.plugin.persistence.PointEntity;
import org.optaweb.vehiclerouting.plugin.persistence.VehicleCrudRepository;
import org.optaweb.vehiclerouting.plugin.persistence.VehicleRoutingCrudRepository;
import org.optaweb.vehiclerouting.plugin.persistence.passenger.PassengerCrudRepository;
import org.optaweb.vehiclerouting.plugin.persistence.passenger.PassengerEntity;
import org.optaweb.vehiclerouting.plugin.persistence.planner.PlannerCrudRepository;
import org.optaweb.vehiclerouting.plugin.persistence.planner.PlannerEntity;
import org.optaweb.vehiclerouting.plugin.persistence.route.RouteCrudRepository;
import org.optaweb.vehiclerouting.plugin.persistence.route.RouteEntity;
import org.optaweb.vehiclerouting.plugin.persistence.routing.RoutingCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RoutingController {

	@Autowired
	private RoutingCrudRepository routingRepository;
	@Autowired
	private RouteCrudRepository routeRepository;
	@Autowired
	private VehicleRoutingCrudRepository vehicleRepository;
	@Autowired
	private PointCrudRepository visitRepository;
	@Autowired
	private PlannerCrudRepository plannerRepository;
	@Autowired
	private PassengerCrudRepository passengerRepository;
	private static final Logger logger = LoggerFactory.getLogger(RoutingController.class);

	@RequestMapping(value = "/routing", method = RequestMethod.POST)
	public ResponseEntity<?> store(@RequestBody RoutingEntity routing) throws Exception {
		logger.info(routing.rangePoint.toString());
		logger.info(String.valueOf(routing.routes.size()));
		routing.routes.forEach(route -> {
			vehicleRepository.save(route.vehicle);
			route.getVisits().forEach(visit -> {
				visitRepository.save(visit);
			});
			routeRepository.save(route);
		});
		routing.getPassengers().forEach(passenger -> {
			visitRepository.save(passenger.getWaypoint());
			passengerRepository.save(passenger);
		});
		visitRepository.save(routing.origin);
		visitRepository.save(routing.destiny);
		routingRepository.save(routing);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/routing{id}")
	public ResponseEntity update(@PathVariable("id") long id, @RequestBody RoutingEntity routing) throws Exception {
	
		return routingRepository.findById(id)
           .map(record -> {
			record.routes.forEach(route -> {
				RouteEntity routeEntity = routeRepository.findById(route.getId()).get();
				routeEntity.track = route.track;
				routeEntity.vehicle = route.vehicle;
				routeEntity.setVisits(route.getVisits());
				routeRepository.save(routeEntity);
			});

			routing.getPassengers().forEach(passenger -> {
				PassengerEntity passengerEntity = passengerRepository.findById(passenger.getId()).get();
				passengerEntity.setMatriculation(passenger.getMatriculation());
				passengerEntity.setLatitude(passenger.getLatitude());
				passengerEntity.setLongitude(passenger.getLongitude());
				passengerRepository.save(passengerEntity);
			});
			PointEntity originEntity = visitRepository.findById(routing.origin.getId()).get();
			originEntity.setLatitude(routing.origin.getLatitude());
			originEntity.setLongitude(routing.origin.getLongitude());
			originEntity.setDemand(routing.origin.getDemand());
			originEntity.setDescription(routing.origin.getDescription());
			visitRepository.save(originEntity);

			PointEntity destinyEntity = visitRepository.findById(routing.destiny.getId()).get();
			destinyEntity.setLatitude(routing.destiny.getLatitude());
			destinyEntity.setLongitude(routing.destiny.getLongitude());
			destinyEntity.setDemand(routing.destiny.getDemand());
			destinyEntity.setDescription(routing.destiny.getDescription());
			visitRepository.save(destinyEntity);
			
			record.setRangePoint(routing.getRangePoint());
			
			RoutingEntity updated = routingRepository.save(record);
			return ResponseEntity.ok().body(updated);
           }).orElse(ResponseEntity.notFound().build());
	}
	@GetMapping("routing/{id}")
	private ResponseEntity<?> routing(@PathVariable("id") long id) throws Exception {
		try {
			return ResponseEntity.ok(routingRepository.findById(id));
		} catch (Exception e) {
			throw new Exception("USER_DISABLED", e);
		}
	}
	@DeleteMapping("/routing/{id}")
	private ResponseEntity<?> delete(@PathVariable("id") long id) throws Exception {
		try {
			Optional<RoutingEntity> optionalRouting = routingRepository.findById(id);
			final RoutingEntity routing = optionalRouting.orElseThrow(
                () -> new IllegalArgumentException("Routing{id=" + id + "} doesn't exist")
			);
			routingRepository.delete(routing);
			routing.getPassengers().forEach(passenger -> {
				passengerRepository.delete(passenger);
			});
			routing.routes.forEach(route -> {
				routeRepository.delete(route);
				vehicleRepository.delete(route.vehicle);
				route.getVisits().forEach(visit -> {
					visitRepository.delete(visit);
				});
				
			});
			
			
			visitRepository.delete(routing.origin);
			visitRepository.delete(routing.destiny);
			

			
			return ResponseEntity.ok(null);
		} catch (Exception e) {
			throw new Exception(e);
		} 
	}
	@GetMapping("routing/all/{planner}")
	private ResponseEntity<?> all(@PathVariable("planner") long id) throws Exception {
		try {
			Optional<PlannerEntity> optionalPlanner = plannerRepository.findById(id);
			final PlannerEntity planner = optionalPlanner.orElseThrow(
                () -> new IllegalArgumentException("Planner{id=" + id + "} doesn't exist")
        	);
			return ResponseEntity.ok(routingRepository.findByPlanner(planner));
		} catch (Exception e) {
			throw new Exception("USER_DISABLED", e);
		} 
	}
}