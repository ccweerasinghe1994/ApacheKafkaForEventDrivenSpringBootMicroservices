package com.wchamarakafka.ws.products.service;

import com.wchamarakafka.ws.products.rest.CreateProductResModel;

public interface ProductService {
    String createProduct(CreateProductResModel createProductResModel);
}
