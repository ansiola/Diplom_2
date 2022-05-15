package praktikum;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserCreateTest {
    private User user;
    private UserClient userClient;
    String bearerToken;

    // Создание рандомного пользователя
    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test
    @Description("Проверка регистрации пользователя с валидными данными")
    public void userCanBeCreatedTest (){

        // Создание пользователя
        ValidatableResponse response = userClient.create(user);
        // Получение статус кода с тела создания пользователя
        int statusCode = response.extract().statusCode();
        // Получение ответа
        boolean isUserNotCreated = response.extract().path("success");
        // Получение токена пользователя
        bearerToken = response.extract().path("accessToken");

        // Проверка что статус код соответствует ожиданиям
        assertThat ("Status code is not correct", statusCode, equalTo(200));
        // Проверка что пользлователь создался
        assertTrue ("The user has been not created", isUserNotCreated);
        // Проверка что токен пользователя не пустой
        assertThat("The user accessToken is empty", bearerToken, notNullValue());
    }
}
