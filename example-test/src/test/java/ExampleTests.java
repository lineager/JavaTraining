import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ExampleTests {
    ObjectMapper mapper = new ObjectMapper();
    Placeholder placeholder;

    @BeforeClass
    public void createPlaceholder() {
        placeholder = Placeholder.builder()
                .userId(1)
                .id(1)
                .title("Hello")
                .body("World")
                .build();
    }

    @Test
    public void createPostTest() throws Exception {
        //Делаем из JSONa String
        String personAsString = mapper.writeValueAsString(placeholder);
        CloseableHttpClient client = HttpClients.createDefault();
        //Добавляем к запросу тело
        HttpPost httppost = new HttpPost("https://jsonplaceholder.typicode.com/posts");
        httppost.setEntity(new StringEntity(personAsString, "UTF-8"));
        //Добавляем к запросу пример корректного заголовка
        httppost.setHeader("content-type:", "text/plain");
        //Отправляем запрос с корректными заголовками
        HttpResponse response = client.execute(httppost);
        //Проверяем что вернется 201
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
    }

    @Test
    public void createPostIncorrectHeaderTest() throws Exception {
        //Делаем из JSONa String
        String personAsString = mapper.writeValueAsString(placeholder);
        CloseableHttpClient client = HttpClients.createDefault();
        //Добавляем к запросу тело
        HttpPost httppost = new HttpPost("https://jsonplaceholder.typicode.com/posts");
        httppost.setEntity(new StringEntity(personAsString, "UTF-8"));
        //Добавляем к запросу пустые заголовки
        httppost.setHeader("ФФФФФ", "");
        //Отправляем запрос с корректными заголовками
        HttpResponse response = client.execute(httppost);
        //Проверяем что вернется 400
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_BAD_REQUEST);
    }
}