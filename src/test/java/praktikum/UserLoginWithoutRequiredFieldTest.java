package praktikum;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserLoginWithoutRequiredFieldTest {
    private static final UserClient userClient = new UserClient ();
    private static final User user = User.getRandom ();
    private final int expectedStatus;
    private final String expectedErrorMessage;
    private final UserCredentials userCredentials;


    public UserLoginWithoutRequiredFieldTest (UserCredentials userCredentials, int expectedStatus, String expectedErrorMessage) {
        this.userCredentials = userCredentials;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData () {
        return new Object[][] {
                {UserCredentials.getUserWithEmail(user), 401, "email or password are incorrect"},
                {UserCredentials.getUserWithPassword(user), 401, "email or password are incorrect"},
                {UserCredentials.getUserWithName(user), 401, "email or password are incorrect"}
        };
    }

    @Test
    @Description("Проверка что пользователь не может авторизоваться " +
            "1. Только с логином " +
            "2. Только с паролем " +
            "3. Только с именем")

    public void courierLoginWithoutNecessaryField () {

        // Создаем пользователя
        userClient.create(user);
        // Пытаемся авторизоваться с данными из условия
        ValidatableResponse login = new UserClient().login(userCredentials);
        // Получение статус кода из тела ответа
        int ActualStatusCode = login.extract().statusCode();
        // Получение сообщения об ошибке из тела ответа
        String errorMessage = login.extract ().path ("message");

        // Проверяем что статус код соответствует ожидаемому
        assertEquals ("Status code is not correct",expectedStatus, ActualStatusCode);
        // Проверяем что сообщение об ошибке соответствует ожидаемому
        assertEquals ("The error message is not correct", expectedErrorMessage, errorMessage);

    }
}
