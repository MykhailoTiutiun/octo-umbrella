package com.mykhailotiutiun.userservice.repository;

import com.mykhailotiutiun.userservice.model.User;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceList;
import org.springframework.stereotype.Repository;

@Repository
public class UserResourceRepository implements ResourceRepositoryV2<User, Long> {

    private final UserRepository userRepository;

    public UserResourceRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Class<User> getResourceClass() {
        return User.class;
    }

    @Override
    public User findOne(Long id, QuerySpec querySpec) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public ResourceList<User> findAll(QuerySpec querySpec) {
        return querySpec.apply(userRepository.findAll());
    }

    @Override
    public ResourceList<User> findAll(Iterable<Long> ids, QuerySpec querySpec) {
        return querySpec.apply(userRepository.findAllById(ids));
    }

    @Override
    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    @Override
    public <S extends User> S create(S entity) {
        return save(entity);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
