package praktikum;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserCreateWithoutRequiredFieldTest {

    private final User user;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    // Метод для параметризации
    public UserCreateWithoutRequiredFieldTest (User user,int expectedStatus, String expectedErrorMessage) {
        this.user = user;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }
    // Параметризация условий авторизации
    @Parameterized.Parameters
    public static Object[][] getTestData () {
        return new Object[][] {
                {User.getUserOnlyRandomEmail(), 403, "Email, password and name are required fields"},
                {User.getUserOnlyRandomPassword(), 403, "Email, password and name are required fields"},
                {User.getUserOnlyRandomName(), 403, "Email, password and name are required fields"},
                {User.getUserWithoutEmail(),403,"Email, password and name are required fields"},
                {User.getUserWithoutPassword(),403,"Email, password and name are required fields"},
                {User.getUserWithoutName(),403,"Email, password and name are required fields"}
        };
    }

    @Test
    @Description("Проверка что пользователя нельзя создать " +
            "1. Только с полем емаил " +
            "2. Только с полем пароль " +
            "3. Только с полем имя" +
            "4. Без поля емаил" +
            "5. Без поля пароль" +
            "6. Без поля имя")

    public void courierNotCreatedWithoutNecessaryField () {

        // Создание курьера
        ValidatableResponse response = new UserClient().create(user);
        // Получение статус кода запроса
        int statusCode = response.extract().statusCode();
        // Получение тела ответа при создании клиента
        boolean isUserNotCreated = response.extract().path("success");
        // Получение сообщения об ошибке
        String errorMessage = response.extract().path("message");

        // Проверка что статус код соответствует ожиданиям
        assertEquals("Status code is not correct", expectedStatus, statusCode);
        // Проверка что курьер не создался
        assertFalse ("The user has been created", isUserNotCreated);
        // Проверка что ссобщение об ошибке соответвует ожиданимя
        assertEquals("The error message is not correct", expectedErrorMessage, errorMessage);
    }
}
