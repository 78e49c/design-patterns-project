package shuttlemanager;

import movement.Position;
import movement.movable.MovableBehavior;
import observer.passenger.Passenger;
import observer.shuttle.Shuttle;

import java.util.ArrayList;

import static movement.Movement.calculateVelocityDirection;


public class ShuttleManager {
    Shuttle shuttle;

    public ShuttleManager(Shuttle shuttle) {
        this.shuttle = shuttle;
    }

    // en yakından başladığı için yolcular bittikten sonraki dönüşü uzayabilir

    public void findShortestRoute(ArrayList<Passenger> passengers, int startLocation, Route[][] route) {
        if (passengers.isEmpty()) {
            return;
        }

        ClosestPassenger closestPassenger = new ClosestPassenger(passengers.get(0), passengers.get(0).getStation());

        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < passengers.size(); i++) {
            int passengerDistance = route[startLocation][passengers.get(i).getStation()].getDistance();
            System.out.println(passengerDistance);
            if (minDistance > passengerDistance) {
                minDistance = passengerDistance;
                closestPassenger = new ClosestPassenger(passengers.get(i), passengers.get(i).getStation());
            }
        }
        System.out.println("min distance: " + minDistance);

        passengers.remove(closestPassenger.passenger);
        passengers.forEach((Passenger p) -> System.out.print("kalanlar: " + p.getStation()));
        System.out.println();

        findShortestRoute(passengers, closestPassenger.location, route);
    }

    public void shuttleE()
    {
        //shuttle.getMovable().setVelocityDirection(Motion.calculateVelocityDirection(shuttle.getMovable(),5,5));
        shuttle.getMovable().setSpeed(5);
        System.out.println( shuttle.getMovable().getPosition().getI() + " " +  shuttle.getMovable().getPosition().getJ());
        //shuttle.updatePosition(Motion.calculateNextPositionWithDestination( shuttle.getMovable(),20,20));

    }

    public static void shuttleGo(Shuttle shuttle)
    {
        Station station = Station.getStation(10);

        shuttle.setTargetPosition(new Position(station.getLocationI(),station.getLocationI()));
        //shuttle.setTargetPosition(station.getLocationI(),station.getLocationI());

        MovableBehavior movable = shuttle.getMovable();
        Position destinationPosition = shuttle.getTargetPosition();

        movable.setVelocityDirection(calculateVelocityDirection(movable,destinationPosition));

    }


}