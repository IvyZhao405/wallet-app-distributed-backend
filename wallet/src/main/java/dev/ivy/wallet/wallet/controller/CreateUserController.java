package dev.ivy.wallet.wallet.controller;

import dev.ivy.wallet.wallet.log.LogConstants;
import dev.ivy.wallet.wallet.log.LogGenerator;
import dev.ivy.wallet.wallet.service.IUserService;
import dev.ivy.wallet.wallet.vo.Response;
import dev.ivy.wallet.wallet.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>Create User Service</h1>
 */
@Slf4j
@RestController
@RequestMapping("/wallet")
public class CreateUserController {

    /** Create user service **/
    private final IUserService userService;

    /** HttpServletRequest */
    private final HttpServletRequest httpServletRequest;

    public CreateUserController(IUserService userService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * <h2>Create new user</h2>
     * @param user {@link User}
     * @return {@link Response}
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/createuser")
    Response createUser(@RequestBody User user) throws Exception {
        LogGenerator.genLog(
                httpServletRequest,
                -1L,
                LogConstants.ActionName.CREATE_USER,
                user
        );
        return userService.createUser(user);
    }
}
