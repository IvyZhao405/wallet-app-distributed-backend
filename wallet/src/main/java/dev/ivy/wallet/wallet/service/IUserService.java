package dev.ivy.wallet.wallet.service;

import dev.ivy.wallet.wallet.vo.Response;
import dev.ivy.wallet.wallet.vo.User;

/**
 * <h1>User service: create new User service </h1>
 */
public interface IUserService {

    /**
     * <h2> Create User</h2>
     * @param user {@link User}
     * @return {@link Response}
     * @throws Exception
     */
    Response createUser(User user) throws Exception;
}
