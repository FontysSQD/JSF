package calculator;

import javafx.application.Application;

/**
 * GuiApplicatie week 15 Created by Sven de Vries on 12-12-2017
 */
public class ProtocolHandler {
    private KochManager kochManager;

    public ProtocolHandler(){
        this.kochManager = new KochManager();
    }

    public void messageHandler(String message) {
//        switch (message) {
//            case "level":
//                return changeLevel(message);
//            default:
//                return null;
//        }
        changeLevel(message);
    }

    public Edge changeLevel(String level) {
        int nxt = Integer.valueOf(level);
        kochManager.changeLevel(nxt);
        return null;
    }
}
