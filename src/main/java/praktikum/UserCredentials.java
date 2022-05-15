package praktikum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.javafaker.Faker;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCredentials {
    public static Faker faker = new Faker();
    public String email;
    public String password;
    public String name;

    public UserCredentials() {}

    public UserCredentials (String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserCredentials from (User user) {
        return new UserCredentials(user.email, user.password, user.name);
    }

    public UserCredentials setEmail (String email){
        this.email = email;
        return this;
    }
    public UserCredentials setName (String name) {
        this.name = name;
        return this;
    }
    public UserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public static UserCredentials getUserWithEmail (User user) {
        return new UserCredentials().setEmail(user.email);
    }
    public static UserCredentials getUserWithPassword (User user) {
        return new UserCredentials().setPassword(user.password);
    }
    public static UserCredentials getUserWithName (User user) {
        return new UserCredentials().setName(user.name);
    }

    public static UserCredentials getUserWithRandomEmailAndPassword () {
        return new UserCredentials().setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password());
    }
    public static UserCredentials getUserWithRandomEmail () {
        return new UserCredentials().setEmail(faker.internet().emailAddress());
    }
    public static UserCredentials getUserWithRandomPassword() {
        return new UserCredentials().setPassword(faker.internet().password());
    }
    public static UserCredentials getUserWithRandomName () {
        return new UserCredentials().setName(faker.name().name());
    }
}
