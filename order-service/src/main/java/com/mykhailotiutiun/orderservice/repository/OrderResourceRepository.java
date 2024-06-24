package com.mykhailotiutiun.orderservice.repository;

import com.mykhailotiutiun.orderservice.model.Order;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceList;
import org.springframework.stereotype.Repository;

@Repository
public class OrderResourceRepository implements ResourceRepositoryV2<Order, Long> {

    private final OrderRepository orderRepository;

    public OrderResourceRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Class<Order> getResourceClass() {
        return Order.class;
    }

    @Override
    public Order findOne(Long id, QuerySpec querySpec) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public ResourceList<Order> findAll(QuerySpec querySpec) {
        return querySpec.apply(orderRepository.findAll());
    }

    @Override
    public ResourceList<Order> findAll(Iterable<Long> ids, QuerySpec querySpec) {
        return querySpec.apply(orderRepository.findAllById(ids));
    }

    @Override
    public <S extends Order> S save(S entity) {
        return orderRepository.save(entity);
    }

    @Override
    public <S extends Order> S create(S entity) {
        return save(entity);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
