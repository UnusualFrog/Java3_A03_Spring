package org.example.spring2025demo3rest.dataaccess;

import org.example.spring2025demo3rest.pojos.Auto;
import org.springframework.data.repository.CrudRepository;

/**
 * Auto Repository interface that will be used by String to create a bean that handles all the CRUD operations
 */
public interface AutoRepository extends CrudRepository<Auto, Long> {

    /**
     * Get all auto for a user
     * @param userId
     * @return
     */
    Iterable<Auto> getAllByUserId(Long userId);
}
