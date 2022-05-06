package praktikum;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserLoginTest {
    private User user;
    private UserClient userClient;

    //Создаем нового рандомного курьера
    @Before
    public void setUp(){
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test
    @Description("Проверка что пользователь может авторизоваться")
    public void courierCanBeCreatedTest (){

        // Создание пользователя
        userClient.create(user);
        // Авторизация созданого пользователя
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        // Получение статус кода с тела авторизации пользователя
        int statusCode = login.extract().statusCode();
        // Получение ответа
        boolean isUserLogin = login.extract().path("success");
        // Получение токена авторизированого пользователя
        String bearerToken = login.extract().path("accessToken");

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is not correct", statusCode, equalTo(200));
        // Проверка что пользователь авторизовался
        assertTrue ("User did not login", isUserLogin);
        // Проверка что токен пользователя не пустой
        assertThat("The user token is empty", bearerToken, notNullValue());
    }
}
