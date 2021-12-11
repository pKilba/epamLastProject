package com.epam.ratingmovies.controller.command.impl.user;

import com.epam.ratingmovies.util.Attribute;
import com.epam.ratingmovies.controller.ParameterTaker;
import com.epam.ratingmovies.controller.command.api.Command;
import com.epam.ratingmovies.controller.command.CommandName;
import com.epam.ratingmovies.controller.command.CommandResponse;
import com.epam.ratingmovies.controller.command.util.Parameter;
import com.epam.ratingmovies.controller.command.request.RequestContext;
import com.epam.ratingmovies.dao.entity.User;
import com.epam.ratingmovies.service.AccountChangePassword;
import com.epam.ratingmovies.service.UserService;
import com.epam.ratingmovies.service.validator.UserValidator;
import com.google.protobuf.ServiceException;

import java.sql.SQLException;

public class ChangePasswordCommand implements Command {
    private static final String PROFILE_PAGE_COMMAND = "poker?command=" + CommandName.PROFILE_PAGE + "&id=";
    private static final String INVALID_DATA_KEY = "invalid.pass";
    private static final String VALID_DATA_KEY = "success.pass";
    UserService userService = new UserService();
    UserValidator userValidator = UserValidator.getInstance();
    AccountChangePassword accountChangePassword = new AccountChangePassword();


    @Override
    public CommandResponse execute(RequestContext requestContext)
            throws ServiceException, SQLException {
        long id = ParameterTaker.takeId(requestContext);
        User user = userService.findUserById(id);
        requestContext.addAttribute(Attribute.USER, user);
        String currentPassword = ParameterTaker.takeString(Parameter.CURRENT_PASSWORD, requestContext);
        String newPassword = ParameterTaker.takeString(Parameter.NEW_PASSWORD, requestContext);

        if (accountChangePassword.isCorrectPassword(newPassword, user, currentPassword)) {

            userService.updatePasswordByUserId(id, newPassword);
        }


        return CommandResponse.forward("");
    }
}
