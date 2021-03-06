/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

package org.optaweb.vehiclerouting.domain;



/**
 * A unique location significant to the user.
 */
public class Location extends LocationData {

    private final long id;

    private final Planner planner;
    private final int demand;

    public Location(long id, Coordinates coordinates, String description,int demand,Planner planner) {
        super(coordinates,description);
        this.id = id;
        this.planner = planner;
        this.demand = demand;
    }

    /**
     * Location's ID.
     * @return unique ID
     */
    public long id() {
        return id;
    }
    /**
     * Location's demand.
     * @return unique ID
     */
    public int demand() {
        return demand;
    }
    /**
     * Planner's ID.
     * @return unique ID
     */
    public Planner planner() {
        return planner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return id == location.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return "Location ["
                + id +
                "]: '" ;
    }
}
