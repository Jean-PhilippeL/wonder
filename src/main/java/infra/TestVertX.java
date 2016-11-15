package infra;

import api.GameRepresentation;
import com.google.common.base.Strings;
import domaine.Joueur;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import service.GameManager;
import service.GameService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jean_letard on 10/11/2016.
 */
public class TestVertX {

    public static GameManager gameManager = null;
    final public static ApiAdapter apiAdapter = new ApiAdapter();

    public static void main(String[] args) {

        HttpServer server = Vertx.vertx().createHttpServer();

        Router router = Router.router(Vertx.vertx());

        router.route().path("/start").handler(routingContext -> {

            HttpServerResponse response = routingContext.response();
            response.setChunked(true);

            MultiMap params = routingContext.request().params();
            final String joueurs = params.get("joueurs[]");
            if(Strings.isNullOrEmpty(joueurs)){
                response.setStatusCode(400);
                response.write("joueurs[] is a required param");
            } else {
                final String[] joueurNames = joueurs.split(",");
                if(joueurNames.length < 3){
                    response.setStatusCode(400);
                    response.write("not enough players");
                } else {
                    if(gameManager == null){
                        gameManager = new GameManager(new GameService());
                        gameManager.startGame(Arrays.asList(joueurNames));
                        response.write("jeu créé\n");
                    }else {
                        response.write("jeu déjà créé\n");
                    }
                }
            }

            response.end();

        });

        router.route().path("/joueurStatus").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            MultiMap params = routingContext.request().params();
            final String joueurName = params.get("joueur");
            final List<Joueur> joueurs = gameManager.getJoueurs();
            final Joueur joueur =  joueurs.stream().filter(j -> joueurName.equals(j.getName())).findFirst().get();
            final List<Joueur> autresJoueurs =  joueurs.stream().filter(j -> !joueurName.equals(j.getName())).collect(Collectors.toList());
            final GameRepresentation gameRepresentation = new GameRepresentation();
            gameRepresentation.setMe(apiAdapter.toMyPlayerRepresentation(joueur));
            gameRepresentation.setOthers(autresJoueurs.stream().map(j -> apiAdapter.toOtherPlayerRepresentation(j)).collect(Collectors.toList()));
            gameRepresentation.setCurrentAge(gameManager.getCurrentAge().name());
            response.end(Json.encodePrettily(gameRepresentation));
        });

        router.route().path("/status").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.setChunked(false);
                    if(gameManager == null){
                        response.end("pas de partie en cours\n");
                    }else {
                        response.end(Json.encodePrettily(gameManager.getStatus()));
                    }
        });

        router.route().handler(routingContext -> {

            // This handler will be called for every request
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");

            // Write to the response and end it
            response.end("Hello World from Vert.x-Web!");
        });

        server.requestHandler(router::accept).listen(8089);
    }
}
