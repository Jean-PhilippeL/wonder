package api;

import java.util.List;

/**
 * Created by jean_letard on 15/11/2016.
 */
public class MyPlayerRepresentation extends  PlayerRepresentation{

    private List<CardRepresentation> cartesEnMain;

    public List<CardRepresentation> getCartesEnMain() {
        return cartesEnMain;
    }

    public void setCartesEnMain(List<CardRepresentation> cartesEnMain) {
        this.cartesEnMain = cartesEnMain;
    }
}
