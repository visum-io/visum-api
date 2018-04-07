package org.visum.api.akkahttp;

import akka.actor.ActorSystem;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Uri;
import akka.http.javadsl.model.headers.Location;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.server.values.PathMatcher;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static akka.http.javadsl.marshallers.jackson.Jackson.jsonAs;
import static akka.http.javadsl.model.HttpResponse.create;
import static akka.http.javadsl.server.RequestVals.entityAs;
import static akka.http.javadsl.server.values.PathMatchers.uuid;
import static akka.http.scaladsl.model.StatusCodes.*;

public class Endpoint extends HttpApp {


    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 8080;
    public static final String HTTP_PREFIX = "http://";
    public static final String PORT_DELIMITER = ":";
    public static final String FILE_INDEXES = "indexes";
    private final FileIndexRepository fileIndexes;

    @Inject
    public Endpoint(FileIndexRepository indexes) {
        this.fileIndexes = indexes;
    }

    @Override
    public Route createRoute() {

        PathMatcher<UUID> uuidExtractor = uuid();

        return handleExceptions(e -> {
                    // handle exceptions here
                    e.printStackTrace();
                    return complete(create().withStatus(InternalServerError()));
                },
                pathSingleSlash().route(
                        // static page
                        getFromResource("web/index.html")
                ),
                pathPrefix(FILE_INDEXES).route(
                        get(pathEndOrSingleSlash().route(
                                // returns all indexes
                                handleWith(ctx -> ctx.completeAs(Jackson.json(), fileIndexes.getAll()))
                        )),
                        get(path(uuidExtractor).route(
                                handleWith(uuidExtractor,
                                        // returns index by id
                                        (ctx, uuid) -> ctx.completeAs(Jackson.json(), fileIndexes.get(uuid))
                                )
                        )),
                        post(
                                handleWith(entityAs(jsonAs(FileIndex.class)),
                                        (ctx, group) -> {
                                            // creates a new index (post)
                                            FileIndex saved = fileIndexes.create(group);
                                            return
                                                    ctx.complete(HttpResponse.create()
                                                            .withStatus(Created())
                                                            .addHeader(
                                                                    Location.create(
                                                                            Uri.create(HTTP_PREFIX + HOST_NAME + PORT_DELIMITER + HOST_PORT + "/"+FILE_INDEXES+"/" + saved.getUuid()))));
                                        }
                                )
                        ),
                        put(path(uuidExtractor).route(
                                handleWith(uuidExtractor, entityAs(jsonAs(FileIndex.class)),
                                        (ctx, uuid, group) -> {
                                            // updates a new index (put)
                                            if (!Objects.equals(group.getUuid(), uuid))
                                                return ctx.completeWithStatus(BadRequest());
                                            else {
                                                fileIndexes.update(group);
                                                return ctx.completeWithStatus(OK());
                                            }
                                        }
                                )
                        )),
                        put(path(uuidExtractor).route(
                                // deletes an new index
                                handleWith(uuidExtractor,
                                        (ctx, uuid) -> {
                                            fileIndexes.delete(uuid);
                                            return ctx.completeWithStatus(OK());
                                        }
                                )
                        ))
                )
        );
    }

    public static void main(String[] args) throws IOException {
        ActorSystem akkaSystem = ActorSystem.create("visum-api");
        Injector injector = Guice.createInjector(new App());

        // todo: get more from this
        injector.getInstance(Endpoint.class).bindRoute(HOST_NAME, HOST_PORT, akkaSystem);

        System.out.println("<ENTER> to exit!");
        System.in.read();
        akkaSystem.shutdown();
    }
}