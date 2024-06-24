package com.mykhailotiutiun.productservice.repository;

import com.mykhailotiutiun.productservice.model.Product;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceList;
import org.springframework.stereotype.Repository;

@Repository
public class ProductResourceRepository implements ResourceRepositoryV2<Product, Long> {

    private final ProductRepository productRepository;

    public ProductResourceRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Class<Product> getResourceClass() {
        return Product.class;
    }

    @Override
    public Product findOne(Long id, QuerySpec querySpec) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ResourceList<Product> findAll(QuerySpec querySpec) {
        return querySpec.apply(productRepository.findAll());
    }

    @Override
    public ResourceList<Product> findAll(Iterable<Long> ids, QuerySpec querySpec) {
        return querySpec.apply(productRepository.findAllById(ids));
    }

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }

    @Override
    public <S extends Product> S create(S entity) {
        return save(entity);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
