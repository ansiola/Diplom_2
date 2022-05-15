package praktikum;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserGetInfoTest {
    private User user;
    private UserClient userClient;
    String accessToken;
    // Создание рандомного пользователя
    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test
    @Description("Получение данных о пользователе")
    public void userInfoCanBeGetTest (){
        // Создание пользователя
        userClient.create(user);
        // Авторизация пользователя
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        // Получение токена пользователя
        accessToken = login.extract().path("accessToken");
        // Получение информации о пользователе
        ValidatableResponse info = userClient.userInfo(accessToken);
        // Получение статус кода с тела информации о пользователе
        int statusCode = info.extract().statusCode();
        // Получение ответа
        boolean getUserInfo = info.extract().path("success");
        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is not correct", statusCode, equalTo(200));
        // Проверка что информация о пользователе запросилась
        assertTrue ("No user info received ", getUserInfo);
    }
}
