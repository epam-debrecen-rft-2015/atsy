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
import com.epam.rft.atsy.web.controllers.PasswordChangeControllerBackup;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.mapper.PasswordValidationRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import javax.annotation.Resource;

/*
  Ennek a kontrollernek az a célja, hogy helyettesítse a hasonló nevű nem rest-es kontrollert.
  A jelszó változtatásáért felelős oldal ezelőtt nem rest-es kontrollert használt, ami nehézségeket
  okoz ezen task esetében. Főleg az átirányítás az, ami nagyon nem kéne. Ugyanis ha küldünk egy POST
  kérést a nem rest-es kontrollernek, akkor azzal egyben át is iránítunk. Tehát a jelszó változtatása
  már most is megtörténik, az működik rendesen. A gond az, hogy a jelenlegi megoldással nem
  kivitelezhető, hogy az átirányítás ne történjen meg, de a jelszó változtatása és a validáció igen.
  A class tartalma másolva lett a nem rest-es kontrollerből. De azóta módosítva is lett. Pl. az
  annotációk már a rest-es verzióhoz tartoznak.
 */
@RestController
@RequestMapping(value = "/secure/password")
public class PasswordChangeController {
  public static final String LOGIN_ERROR_KEY = "loginErrorKey";
  public static final String VALIDATION_SUCCESS_KEY = "validationSuccessKey";
  public static final String VALIDATION_ERROR_KEY = "validationErrorKey";
  public static final String LOGIN_BACKEND_VALIDATION = "login.backend.validation";
  public static final String
      PASSWORDCHANGE_VALIDATION_SUCCESS =
      "passwordchange.validation.success";
  private static final String VIEW_NAME = "password_change";
  private static Logger
      logger =
      LoggerFactory.getLogger(PasswordChangeControllerBackup.class);

  @Resource
  PasswordChangeService passwordChangeService;

  @Resource
  UserService userService;

  @Resource
  PasswordValidationRuleMapper passwordValidationRuleMapper;

  @Autowired
  PasswordValidator passwordValidator;

  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  /*
    Ez a metódus végzi el a tényleges munkát. Egyetlen változtatás történt a nem rest-es verzióhoz
    képest. Ugye az látszik, hogy más a visszatérési értéke. A változtatás tehát az, hogy nem
    a ModelAndView objektumot töltjük fel és adjuk vissza, hanem egy RestResponse objektumot.
    Ez a változtatás már megtörtént és úgy látszik, hogy rendesen működik is. De refaktorálás még
    várhatóan kelleni fog.
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<RestResponse> saveOrUpdate(
      @ModelAttribute PasswordChangeDTO passwordChangeDTO,
      BindingResult bindingResult,
      @CurrentUser UserDetailsAdapter userDetailsAdapter) {

    ModelAndView model = new ModelAndView(VIEW_NAME);
    RestResponse restResponse;
    HttpStatus status;
    if (bindingResult.hasErrors()) {
      restResponse = new RestResponse(LOGIN_BACKEND_VALIDATION);
      status = HttpStatus.BAD_REQUEST;
    } else {
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
        passwordChangeService.saveOrUpdate(passwordHistoryDTO);
        userDetailsAdapter.setPassword(newPassword);
        model.addObject(VALIDATION_SUCCESS_KEY, PASSWORDCHANGE_VALIDATION_SUCCESS);
        restResponse = RestResponse.NO_ERROR;
        status = HttpStatus.OK;
      } catch (PasswordValidationException e) {
        logger.error(e.getMessage(), e);
        restResponse = new RestResponse(passwordValidationRuleMapper.getMessageKeyByException(e));
        status = HttpStatus.BAD_REQUEST;
      }
    }

    return new ResponseEntity<>(restResponse, status);
  }

}
