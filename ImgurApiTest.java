package com.geekbrains;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class ImgurApiTest {

    ResponseSpecification responseSpecification = null;

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.API_URL;
    }

    @BeforeEach
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("success", is(true))
                .expectBody("status", is(200))
                .build();
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
                .spec(responseSpecification)
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

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addFormParam("title", "Some image")
                .addFormParam("description", "your advertisement could be here")
                .build();

        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .spec(requestSpecification)
                .expect()
                .log()
                .all()
                .spec(responseSpecification)
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
                .spec(responseSpecification)
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
                .spec(responseSpecification)
                .body("data", is(true))
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
                .spec(responseSpecification)
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

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addFormParam("image_id", "oHOpJ2F")
                .addFormParam("comment", "I'm a giraffe!")
                .build();

        given()
                .when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .when()
                .spec(requestSpecification)
                .expect()
                .log()
                .all()
                .spec(responseSpecification)
                .when()
                .post(url);
    }

}
