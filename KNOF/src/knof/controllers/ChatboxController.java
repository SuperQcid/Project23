package knof.controllers;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import knof.controllers.ServerController;
import knof.event.EventHandler;
import knof.event.events.MessageEvent;
import knof.model.Server;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Henk Dieter Oordt on 12-5-2016.
 */
public class ChatboxController {

    private final List<Tab> tabs = new ArrayList<>();

    private ServerController serverController;

    public void init(ServerController serverController){
        this.serverController = serverController;
        serverController.server.players.addListener((ListChangeListener<? super String>) (observable)->{
            System.out.println("wut");
        });
    }

}
