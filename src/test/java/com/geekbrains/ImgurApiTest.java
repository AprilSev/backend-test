package com.geekbrains;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class ImgurApiTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.API_URL;
    }

    @Test
    @DisplayName("Тест на получение изображения")
    void testGetImage(){
        String imageHash = "6zS8WEj";
        String url = "image/" + imageHash;
        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.animated", is(false))
                .body("data.width", is(2048))
                .log()
                .all()
                .when()
                .get(url);
    }

    @Test
    @DisplayName("Тест на обновление информации у изображения")
    void testUpdateImageInformation() {
        String imageHash = "6zS8WEj";
        String url = "image/" + imageHash;
        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "Some image")
                .formParam("description", "your advertisement could be here")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data", is(true))
                .when()
                .post(url);
    }

    @Test
    @DisplayName("Тест на добавление изображение в избранное")
    void testFavoriteAnImage() {
        String imageHash = "6zS8WEj/";
        String url = "image/" + imageHash + "favorite";
        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .post(url);
    }

    @Test
    @DisplayName("Тест на удаление изображения")
    void testDeletionImage() {
        String deleteHash = "FNPEfIs";
        String url = "image/" + deleteHash;
        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("data", is(true))
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .delete(url);
    }

    @Test
    @DisplayName("Тест на запрос коментария")
    void testComment() {
        String commentHash = "2156728181";
        String url = "comment/" + commentHash;
        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.image_id", is("oHOpJ2F"))
                .body("data.comment", is("Lucky"))
                .log()
                .all()
                .when()
                .get(url);
    }

    @Test
    @DisplayName("Тест на создание коментария")
    void testCreationComment() {
        String url = "comment";
        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .when()
                .formParam("image_id", "oHOpJ2F")
                .formParam("comment", "I'm a giraffe!")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .when()
                .post(url);
    }

}
