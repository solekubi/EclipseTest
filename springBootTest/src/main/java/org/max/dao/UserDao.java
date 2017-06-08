package org.max.dao;

import org.max.model.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface UserDao  extends BaseUuidDao<User>{

}
