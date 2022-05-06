package praktikum;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class UserCreateSameTest {
    private User user;
    private UserClient userClient;

    // Создание рандомного пользователя
    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test
    @Description("Проверка что нельзя зарегистрировать 2х одинаковых пользователей")
    public void userCanBeCreatedTest (){

        // Создание клиента
        userClient.create(user);
        // Попытка создания пользователя с теми же данными
        ValidatableResponse response = userClient.create(user);
        // Получение статус кода с тела создания одинакового пользователя
        int statusCode = response.extract().statusCode();
        // Получение тела ответа при создании одинакового пользователя
        boolean isUserNotCreated = response.extract().path("success");
        // Получение тела сообщения
        String errorMessage = response.extract().path("message");

        // Проверка что статус код соответсвует ожидаемому
        assertThat ("Status code is not correct", statusCode, equalTo(403));
        // Проверка что одинаковый пользователь не создался
        assertFalse ("The user has been created", isUserNotCreated);
        // Проверка что сообщение об ошибке соответсвует ожидаемому
        assertEquals("The error massage is not correct", "User already exists", errorMessage);
    }
}
