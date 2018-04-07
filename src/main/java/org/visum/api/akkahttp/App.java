package org.visum.api.akkahttp;

import com.google.inject.AbstractModule;

public class App extends AbstractModule {
    @Override
    protected void configure() {
        bind(Endpoint.class);
    }
}
