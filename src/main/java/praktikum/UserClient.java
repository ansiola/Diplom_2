package praktikum;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient{
    private static final String USER_PATH = "api/auth";

    @Step ("Создание пользователя")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then();
    }

    @Step ("Авторизация пользователя")
    public ValidatableResponse login(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(USER_PATH + "/login")
                .then();
    }

    @Step ("Информация о пользователе")
    public ValidatableResponse userInfo(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token.replace("Bearer ", ""))
                .when()
                .get(USER_PATH + "/user")
                .then();
    }

    @Step ("Изменение информации о пользователе")
    public ValidatableResponse userInfoChange(String token, UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token.replace("Bearer ", ""))
                .body(userCredentials)
                .when()
                .patch(USER_PATH + "/user")
                .then();
    }
}
