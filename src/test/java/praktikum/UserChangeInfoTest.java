package praktikum;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class UserChangeInfoTest {
    private static User user;
    private UserClient userClient;
    String bearerToken;

    // Создание рандомного пользователя
    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test
    @Description("Изменение данных пользователя. Одинаковый емаил")
    public void userInfoCantBeChangeWithSameEmailTest (){

        // Создание пользователя
        userClient.create(user);
        // Авторизация пользователя
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        // Получение токена пользователя
        bearerToken = login.extract().path("accessToken");
        // Получение информации о пользователе
        ValidatableResponse info = userClient.userInfoChange(bearerToken, UserCredentials.getUserWithEmail(user));
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean getUserInfo = info.extract().path("success");
        // Получение сообщения об ошибке
        String errorMessage = info.extract().path("message");

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(403));
        // Проверка что информация о пользователе запросилась
        assertFalse ("User info change", getUserInfo);
        // Проверка тела сообщения
        assertEquals("User with such email already exists",errorMessage);
    }

    @Test
    @Description("Изменение данных пользователя. Изменение Email")
    public void userInfoCantBeChangeEmailTest (){

        // Создание пользователя
        userClient.create(user);
        // Авторизация пользователя
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        // Получение токена пользователя
        bearerToken = login.extract().path("accessToken");
        // Изменение информации о пользователе
        ValidatableResponse info = userClient.userInfoChange(bearerToken, UserCredentials.getUserWithRandomEmail());
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean getUserInfo = info.extract().path("success");
        String actualEmail = info.extract().path("email");
        String oldEmail = user.email;

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(200));
        // Проверка что информация о пользователе запросилась
        assertTrue ("User info not change", getUserInfo);
        // Проверка тела сообщения
        assertNotEquals("User email not change",oldEmail,actualEmail);
    }

    @Test
    @Description("Изменение данных пользователя. Изменение пароля")
    public void userInfoCantBeChangePasswordTest (){

        // Создание пользователя
        userClient.create(user);
        // Авторизация пользователя
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        // Получение токена пользователя
        bearerToken = login.extract().path("accessToken");
        // Изменение информации о пользователе
        ValidatableResponse info = userClient.userInfoChange(bearerToken, UserCredentials.getUserWithRandomPassword());
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean getUserInfo = info.extract().path("success");

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(200));
        // Проверка что информация о пользователе запросилась
        assertTrue ("User info not change", getUserInfo);

    }

    @Test
    @Description("Изменение данных пользователя. Изменение Имени")
    public void userInfoCantBeChangeNameTest (){

        // Создание пользователя
        userClient.create(user);
        // Авторизация пользователя
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        // Получение токена пользователя
        bearerToken = login.extract().path("accessToken");
        // Изменение информации о пользователе
        ValidatableResponse info = userClient.userInfoChange(bearerToken, UserCredentials.getUserWithRandomName());
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean getUserInfo = info.extract().path("success");
        String actualName = info.extract().path("name");
        String oldName = user.name;

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(200));
        // Проверка что информация о пользователе запросилась
        assertTrue ("User info not change", getUserInfo);
        // Проверка тела сообщения
        assertNotEquals("User email not change",oldName,actualName);
    }

    @Test
    @Description("Изменение данных пользователя. Не авторизованный пользователь. Email")
    public void userInfoCantBeChangeEmailWithoutAuthTest (){

        bearerToken = "";
        // Получение информации о пользователе
        ValidatableResponse info = userClient.userInfoChange(bearerToken,UserCredentials.getUserWithRandomEmail());
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean getUserInfo = info.extract().path("success");
        String errorMessage = info.extract().path("message");

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(401));
        // Проверка что информация о пользователе запросилась
        assertFalse ("User is not creates", getUserInfo);
        // Проверка тела сообщения
        assertEquals("You should be authorised",errorMessage);
    }

    @Test
    @Description("Изменение данных пользователя. Не авторизованный пользователь. Пароль")
    public void userInfoCantBeChangePasswordWithoutAuthTest (){

        bearerToken = "";
        // Получение информации о пользователе
        UserCredentials.getUserWithName(user);
        ValidatableResponse info = userClient.userInfoChange(bearerToken, UserCredentials.getUserWithRandomPassword());
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean getUserInfo = info.extract().path("success");
        String errorMessage = info.extract().path("message");

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(401));
        // Проверка что информация о пользователе запросилась
        assertFalse ("User is not creates", getUserInfo);
        // Проверка тела сообщения
        assertEquals("You should be authorised",errorMessage);
    }

    @Test
    @Description("Изменение данных пользователя. Не авторизованный пользователь. Имя")
    public void userInfoCantBeChangeNamedWithoutAuthTest (){

        bearerToken = "";
        // Получение информации о пользователе
        ValidatableResponse info = userClient.userInfoChange(bearerToken,UserCredentials.getUserWithRandomName());
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean getUserInfo = info.extract().path("success");
        String errorMessage = info.extract().path("message");

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(401));
        // Проверка что информация о пользователе запросилась
        assertFalse ("User is not creates", getUserInfo);
        // Проверка тела сообщения
        assertEquals("You should be authorised",errorMessage);
    }
}
