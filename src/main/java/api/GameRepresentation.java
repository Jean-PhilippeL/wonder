package api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by jean_letard on 15/11/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameRepresentation {

    private String currentAge;
    private int remainingLap;
    private MyPlayerRepresentation me;
    private List<PlayerRepresentation> others;
    private List<String> waitingPlayers;

    public String getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(String currentAge) {
        this.currentAge = currentAge;
    }

    public int getRemainingLap() {
        return remainingLap;
    }

    public void setRemainingLap(int remainingLap) {
        this.remainingLap = remainingLap;
    }

    public MyPlayerRepresentation getMe() {
        return me;
    }

    public void setMe(MyPlayerRepresentation me) {
        this.me = me;
    }

    public List<PlayerRepresentation> getOthers() {
        return others;
    }

    public void setOthers(List<PlayerRepresentation> others) {
        this.others = others;
    }


    public List<String> getWaitingPlayers() {
        return waitingPlayers;
    }

    public void setWaitingPlayers(List<String> waitingPlayers) {
        this.waitingPlayers = waitingPlayers;
    }
}
