package praktikum;

import com.github.javafaker.Faker;

public class User {
    public static Faker faker = new Faker();
    public String email;
    public String password;
    public String name;

    public User () {
    }

    public User (String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandom() {
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password();
        final String firstName = faker.name().firstName();
        return new User (email, password, firstName);
    }

    public User setEmail (String email){
        this.email = email;
        return this;
    }
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    public User setName (String name) {
        this.name = name;
        return this;
    }

    public static User getUserOnlyRandomEmail () {
        return new User().setEmail(faker.internet().emailAddress());
    }
    public static User getUserOnlyRandomPassword () {
        return new User().setPassword(faker.internet().password());
    }
    public static User getUserOnlyRandomName () {
        return new User().setName(faker.name().firstName());
    }

    public static User getUserWithoutName () {
        return new User().setPassword(faker.internet().password()).setEmail(faker.internet().emailAddress());
    }
    public static User getUserWithoutPassword () {
        return new User().setName(faker.name().firstName()).setEmail(faker.internet().emailAddress());
    }
    public static User getUserWithoutEmail() {
        return new User().setPassword(faker.internet().password()).setName(faker.name().firstName());
    }
}
