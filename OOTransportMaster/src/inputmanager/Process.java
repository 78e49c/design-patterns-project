package inputmanager;

import command.Command;
import command.ShuttleApp;
import command.ShuttleCallCommand;
import display.Display;
import display.Image;
import observer.passenger.Passenger;
import observer.shuttle.Shuttle;
import shuttlemanager.Station;

import javax.swing.*;
import java.util.ArrayList;

public class Process {

    public static ArrayList<Passenger> passengers = new ArrayList<>();
    public static ArrayList<Shuttle> shuttles = new ArrayList<>();
    private static ShuttleApp shuttleApp = new ShuttleApp();

    public static void mainly()
    {
        System.out.println(Image.getPassenger()[0][0]);
        System.out.println(Image.getPassenger()[0][1]);
        //Process.addShuttle(1,1);
        Process.addPassenger(223,775);

        display();
        /*
        for (Passenger p : passengers) {
            callCommand(shuttles.get(0), p);
        }
*/
        /*
        int i = 3;
        for (Passenger p : passengers) {
            p.setLocation(++i);
        }

        // start journey
        ShuttleManager sm = new ShuttleManager(shuttles.get(0));
        Route[][] route = Chart.getChart();

        for (Observer p : shuttles.get(0).getPassengers()) {
            passengers.add((Passenger) p);
        }
        sm.findShortestRoute(passengers, 1, route);
         */

    }

    public static void display()
    {
        //Image.getImages();
        Display display1 = new Display();
        display1.createDisplay(Image.getMap());

        Input.mouseEvent(display1.getFrame());

        Timer timer = new Timer(100, e -> {

            display1.updateDisplay(Display.updateImage(Process.shuttles,Process.passengers));


            Process.checkAddRequests();

        });
        timer.setInitialDelay(0);
        timer.start();
    }

    static void addPassenger(int positionI, int positionJ)
    {

        if(isValid(positionI,positionJ)) {
            System.out.println("valid");
            int station = findClosestStation(positionI,positionJ);
            Passenger passenger = new Passenger(station,positionI,positionJ);
            passengers.add(passenger);}
        // hep çalışıyor
    }

    private static int findClosestStation(int positionI,int positionJ) {
        double minDistance = Double.MAX_VALUE;
        int closestStation = 0;

        for (int i = 0; i < Station.stations.length ; i++) {

            double currentDistance = Math.sqrt(
                    Math.pow(Station.stations[i].getLocationI() - positionI, 2) +
                            Math.pow(Station.stations[i].getLocationJ() - positionJ, 2));

            if(currentDistance < minDistance )
            { minDistance = currentDistance; closestStation = i; }
        }

        return closestStation;
    }

    static void callCommand(Shuttle shuttle, Passenger passenger) {
        Command shuttleCallCommand = new ShuttleCallCommand(shuttle, passenger);
        setCommand(shuttleCallCommand);
    }

    static void setCommand(Command command) {
        shuttleApp.setCommand(command);
        takeCommandCall(command);
    }

    static void takeCommandCall(Command command) {
        shuttleApp.takeCommandCall(command);
    }

    static boolean isValid(int positionI,int positionJ)
    {
        int range = 100;

        for (int i = 0; i < Station.stations.length ; i++) {
            if(range >= Math.sqrt(
                    Math.pow(Station.stations[i].getLocationI() - positionI, 2) +
                            Math.pow(Station.stations[i].getLocationJ() - positionJ, 2)))
            {return true;}
        }
        return false;
    }


    static void addShuttle(int positionI, int positionJ)
    {
        Shuttle shuttle = new Shuttle(positionI,positionJ);
        shuttles.add(shuttle);

        //shuttle.getMovable().setVelocityDirection(Motion.calculateVelocityDirection(shuttle.getMovable(),5,5));
        shuttle.getMovable().setSpeed(5);
        System.out.println( shuttle.getMovable().getPosition().getI() + " " +  shuttle.getMovable().getPosition().getJ());
        //shuttle.updatePosition(Motion.calculateNextPositionWithDestination( shuttle.getMovable(),20,20));

    }

    public static void applyAddRequests()
    {
        while(Input.getClicks().size()>0)
        {
            Click click = Input.getClicks().get(0);
            Process.addPassenger(click.i,click.j);
            Input.getClicks().remove(click);
        }
    }

    public static void checkAddRequests()
    {
        if(0 != Input.getClicks().size())
        {
            applyAddRequests();
        }
    }
}


