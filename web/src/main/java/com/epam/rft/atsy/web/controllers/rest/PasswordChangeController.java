package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordValidationException;
import com.epam.rft.atsy.service.passwordchange.validation.PasswordValidator;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import com.epam.rft.atsy.web.CurrentUser;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.mapper.RuleValidationExceptionMapper;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/secure/password")
public class PasswordChangeController {

  public static final String LOGIN_BACKEND_VALIDATION = "login.backend.validation";

  private static Logger
      logger =
      LoggerFactory.getLogger(PasswordChangeController.class);

  @Resource
  private MessageKeyResolver messageKeyResolver;

  @Resource
  private PasswordChangeService passwordChangeService;

  @Resource
  private UserService userService;

  @Resource
  private RuleValidationExceptionMapper ruleValidationExceptionMapper;

  @Resource
  private PasswordValidator passwordValidator;

  private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<RestResponse> saveOrUpdate(
      @RequestBody @Valid PasswordChangeDTO passwordChangeDTO,
      BindingResult bindingResult,
      @CurrentUser UserDetailsAdapter userDetailsAdapter) {

    if (!bindingResult.hasErrors()) {

      try {

        passwordValidator.validate(passwordChangeDTO);

        String newPassword = bCryptPasswordEncoder.encode(passwordChangeDTO.getNewPassword());

        PasswordHistoryDTO passwordHistoryDTO = PasswordHistoryDTO.builder()
            .userId(userDetailsAdapter.getUserId())
            .password(newPassword)
            .changeDate(new Date())
            .build();

        UserDTO user = userService.findUserByName(userDetailsAdapter.getUsername());
        user.setPassword(newPassword);
        userService.saveOrUpdate(user);
        userDetailsAdapter.setPassword(newPassword);

        passwordChangeService.saveOrUpdate(passwordHistoryDTO);

      } catch (PasswordValidationException e) {

        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(
            new RestResponse(messageKeyResolver
                .resolveMessageOrDefault(
                    ruleValidationExceptionMapper.getMessageKeyByException(e))),
            HttpStatus.BAD_REQUEST);

      }

      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);

    } else {

      String errorMessage = messageKeyResolver.resolveMessageOrDefault(LOGIN_BACKEND_VALIDATION);

      RestResponse restResponse = new RestResponse(errorMessage);

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);

    }

  }

}
