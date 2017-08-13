package InterfacesFiles;


import AppClasses.ValidPassword;

/**
 * Created by yousry on 4/11/2016.
 */
public interface RegisterationValidation {
    boolean isEmailAddressValid(String email);
    ValidPassword isPasswordValid(String password);
    boolean isPasswordConfirmationValid(String password);
    boolean isJobEmpty();
    boolean isEmpty();
}
