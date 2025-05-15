package com.pragma.challenge.technology_service.infrastructure.entrypoints.util;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

@Component
public class DefaultServerResponseContext implements ServerResponse.Context {
  private final List<HttpMessageWriter<?>> messageWriters;
  private final List<ViewResolver> viewResolvers;

  public DefaultServerResponseContext() {
    HandlerStrategies strategies = HandlerStrategies.withDefaults();
    this.messageWriters = strategies.messageWriters();
    this.viewResolvers = strategies.viewResolvers();
  }

  @Override
  public List<HttpMessageWriter<?>> messageWriters() {
    return messageWriters;
  }

  @Override
  public List<ViewResolver> viewResolvers() {
    return viewResolvers;
  }
}
