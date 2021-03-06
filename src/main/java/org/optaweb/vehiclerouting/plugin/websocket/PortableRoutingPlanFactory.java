/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaweb.vehiclerouting.plugin.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.optaweb.vehiclerouting.domain.Coordinates;
import org.optaweb.vehiclerouting.domain.Location;
import org.optaweb.vehiclerouting.domain.RoutingPlan;
import org.optaweb.vehiclerouting.domain.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Creates instances of {@link PortableRoutingPlan}.
 */
class PortableRoutingPlanFactory {
    private static final Logger logger = LoggerFactory.getLogger(PortableRoutingPlanFactory.class);
    private PortableRoutingPlanFactory() {
        throw new AssertionError("Utility class");
    }

    static PortableRoutingPlan fromRoutingPlan(RoutingPlan routingPlan) {
        
        PortableDistance distance = PortableDistance.fromDistance(routingPlan.distance());
        List<PortableVehicle> vehicles = portableVehicles(routingPlan.vehicles());
        PortableLocation origin = routingPlan.origin().map(PortableLocation::fromLocation).orElse(null);
        PortableLocation destiny = routingPlan.destiny().map(PortableLocation::fromLocation).orElse(null);
        List<PortableLocation> visits = portableVisits(routingPlan.visits());
        List<PortableRoute> routes = routingPlan.routes().stream()
                .map(routeWithTrack -> new PortableRoute(
                        PortableVehicle.fromVehicle(routeWithTrack.vehicle()),
                        origin,
                        destiny,
                        portableVisits(routeWithTrack.visits()),
                        portableTrack(routeWithTrack.track())))
                .collect(Collectors.toList());
        return new PortableRoutingPlan(distance, vehicles, origin,destiny, visits, routes);
    }

    private static List<List<PortableCoordinates>> portableTrack(List<List<Coordinates>> track) {
        ArrayList<List<PortableCoordinates>> portableTrack = new ArrayList<>();
        for (List<Coordinates> segment : track) {
            List<PortableCoordinates> portableSegment = segment.stream()
                    .map(PortableCoordinates::fromCoordinates)
                    .collect(Collectors.toList());
            portableTrack.add(portableSegment);
        }
        return portableTrack;
    }

    private static List<PortableLocation> portableVisits(List<Location> visits) {
        return visits.stream()
                .map(PortableLocation::fromLocation)
                .collect(Collectors.toList());
    }

    private static List<PortableVehicle> portableVehicles(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(PortableVehicle::fromVehicle)
                .collect(Collectors.toList());
    }
}
