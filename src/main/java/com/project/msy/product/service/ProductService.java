// src/main/java/com/project/msy/product/service/ProductService.java
package com.project.msy.product.service;

import com.project.msy.product.dto.*;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(Long productId, UpdateProductRequest request);
    void deleteProduct(Long productId);// ← 추가
}
