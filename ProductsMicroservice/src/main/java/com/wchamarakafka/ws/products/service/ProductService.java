package com.wchamarakafka.ws.products.service;

import com.wchamarakafka.ws.products.rest.CreateProductResModel;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductResModel createProductResModel) throws ExecutionException, InterruptedException;
}
