// 1 - Pacote
package petstore;

// 2 - biblioteca

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

// 3 - classe
public class Pet {

// 3.1 - atributos
    String uri = "https://petstore.swagger.io/v2/swagger.json";

// 3.2 - metodos e funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

// incluir - creat - post
    @Test // identifoca a funcao ou metodo como teste
    public void incliurPet() throws IOException {
        String jsonBody = lerJson( "Dados/pet1.json");

        //sintaxe gherkin
        //dado - quando - então
        //given - when - then

        given()
                .contentType("application/json") // comum em API mais REST - antigos era "text/txt"
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200);

    }
}

