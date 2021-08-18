// 1 - Pacote
package petstore;

// 2 - biblioteca

import io.restassured.specification.Argument;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.stringContainsInOrder;

// 3 - classe
public class Pet {

// 3.1 - atributos
    String uri = "https://petstore.swagger.io/v2/pet";

// 3.2 - metodos e funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

// incluir - creat - post
    @Test(priority = 1) // identifoca a funcao ou metodo como teste
    public void incluirPet() throws IOException {
        String jsonBody = lerJson( "dados/pet1.json");

        //sintaxe gherkin
        //dado - quando - então
        //given - when - then

        List<Argument> is;
        given()
                .contentType("application/json") // comum em API mais REST - antigos era "text/txt"
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Boris"))
                .body("status", is("available"))
                .body("category.name", is ("Dog"))
                .body("tags.name", contains("sta"))
        ;
    }
    @Test(priority = 2)// identifoca a funcao ou metodo como teste
    public void consultarPet(){
        String petId = "08060806";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Boris"))
                .body("status", is("available"))
                .body("category.name", is ("Dog"))
                .body("tags.name", contains("sta"));
    }

    @Test
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("dados/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is ("Boris"))
                .body("status", is ("sold"));
    }

    @Test
    public void excluirPet() {
        String petId = "08060806";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is (200));
    }

}


