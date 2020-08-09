package onlineshop.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRegisterBinding extends BaseBinding{

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String name;
    private String phoneNumber;
    private String country;
    private String city;
    private String street;

    public UserRegisterBinding() {
    }

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @Length(min = 3, max = 15, message = "Username length must be between 3 and 15 characters")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Length(min = 3, max = 20, message = "Password length must be between 3 and 20 characters")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Length(min = 3, max = 20, message = "Password length must be between 3 and 20 characters")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be Empty")
    @Email(message = "Invalid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be Empty")
    @Length(min = 2, message = "Name length must be between 3 and 20 characters")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+ [A-Z][a-zA-Z]+", message = "Name must start with capital letter")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @NotNull(message = "Phone number cannot be null")
    @NotEmpty(message = "Phone number cannot be Empty")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotNull(message = "Country cannot be null")
    @NotEmpty(message = "Country cannot be Empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = "Country must start with capital letter")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull(message = "City cannot be null")
    @NotEmpty(message = "City cannot be Empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = "City must start with capital letter")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull(message = "Street cannot be null")
    @NotEmpty(message = "Street cannot be Empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z]+", message = "Street must start with capital letter")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
