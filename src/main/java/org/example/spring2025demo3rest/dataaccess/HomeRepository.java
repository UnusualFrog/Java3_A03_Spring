package org.example.spring2025demo3rest.dataaccess;

import org.example.spring2025demo3rest.pojos.Home;
import org.springframework.data.repository.CrudRepository;

/**
 * Home Repository interface that will be used by String to create a bean that handles all the CRUD operations
 */
public interface HomeRepository extends CrudRepository<Home, Long> {

    /**
     * Get all homes for a user
     * @param userId
     * @return
     */
    Iterable<Home> getAllByUserId(Long userId);
}
