package com.project.msy.product.service.impl;

import com.project.msy.product.dto.CreateProductRequest;
import com.project.msy.product.dto.ProductResponse;
import com.project.msy.product.dto.SpecificationDto;
import com.project.msy.product.dto.UpdateProductRequest;
import com.project.msy.product.entity.*;
import com.project.msy.product.repository.*;
import com.project.msy.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final ProductOriginRepository originRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        ProductCategory category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id"));
        ProductOrigin origin = originRepository.findById(request.getOrigin())
                .orElseThrow(() -> new IllegalArgumentException("Invalid origin id"));

        Product product = Product.builder()
                .name(request.getName())
                .category(category)
                .price(request.getPrice())
                .discountPrice(request.getDiscountPrice())
                .stockQuantity(request.getStock())
                .description(request.getDescription())
                .shippingFee(request.getShippingFee())
                .manufacturer(request.getManufacturer())
                .origin(origin)
                .build();

        if (request.getSpecifications() != null) {
            request.getSpecifications().forEach(dto -> {
                ProductSpecification spec = ProductSpecification.builder()
                        .product(product)
                        .specName(dto.getLabel())
                        .specValue(dto.getValue())
                        .build();
                product.getSpecifications().add(spec);
            });
        }

        var saved = productRepository.save(product);
        return ProductResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .category(saved.getCategory().getId())
                .price(saved.getPrice())
                .discountPrice(saved.getDiscountPrice())
                .stockQuantity(saved.getStockQuantity())
                .description(saved.getDescription())
                .shippingFee(saved.getShippingFee())
                .manufacturer(saved.getManufacturer())
                .origin(saved.getOrigin().getId())
                .createdAt(saved.getCreatedAt())
                .specifications(saved.getSpecifications().stream()
                        .map(s -> new SpecificationDto(s.getSpecName(), s.getSpecValue()))
                        .collect(Collectors.toList()))
                .build();
    }

    // src/main/java/com/project/msy/product/service/impl/ProductServiceImpl.java
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(p -> ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .category(p.getCategory().getId())
                .categoryName(p.getCategory().getName())
                .price(p.getPrice())
                .discountPrice(p.getDiscountPrice())
                .stockQuantity(p.getStockQuantity())
                .description(p.getDescription())
                .shippingFee(p.getShippingFee())
                .manufacturer(p.getManufacturer())
                .origin(p.getOrigin().getId())
                .originName(p.getOrigin().getName())
                .createdAt(p.getCreatedAt())
                .specifications(p.getSpecifications().stream()
                        .map(s -> new SpecificationDto(s.getSpecName(), s.getSpecValue()))
                        .toList()
                )
                .build()
        ).toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다: " + productId));

        ProductCategory category = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 카테고리: " + request.getCategory()));
        ProductOrigin origin = originRepository.findById(request.getOrigin())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 원산지: " + request.getOrigin()));

        // 필드 업데이트
        product.setName(request.getName());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        product.setDiscountPrice(request.getDiscountPrice());
        product.setStockQuantity(request.getStock());
        product.setDescription(request.getDescription());
        product.setShippingFee(request.getShippingFee());
        product.setManufacturer(request.getManufacturer());
        product.setOrigin(origin);

        // 사양 업데이트: 기존 모두 제거 후 재추가
        product.getSpecifications().clear();
        if (request.getSpecifications() != null) {
            request.getSpecifications().forEach(dto -> {
                ProductSpecification spec = ProductSpecification.builder()
                        .product(product)
                        .specName(dto.getLabel())
                        .specValue(dto.getValue())
                        .build();
                product.getSpecifications().add(spec);
            });
        }

        Product updated = productRepository.save(product);
        return ProductResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .category(updated.getCategory().getId())
                .categoryName(updated.getCategory().getName())
                .price(updated.getPrice())
                .discountPrice(updated.getDiscountPrice())
                .stockQuantity(updated.getStockQuantity())
                .description(updated.getDescription())
                .shippingFee(updated.getShippingFee())
                .manufacturer(updated.getManufacturer())
                .origin(updated.getOrigin().getId())
                .originName(updated.getOrigin().getName())
                .createdAt(updated.getCreatedAt())
                .specifications(updated.getSpecifications().stream()
                        .map(s -> new SpecificationDto(s.getSpecName(), s.getSpecValue()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다: " + productId);
        }
        productRepository.deleteById(productId);
    }

}