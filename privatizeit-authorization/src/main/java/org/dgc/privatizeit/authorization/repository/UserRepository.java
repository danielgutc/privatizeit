package org.dgc.privatizeit.authorization.repository;

import org.dgc.privatizeit.authorization.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String>
{
}
