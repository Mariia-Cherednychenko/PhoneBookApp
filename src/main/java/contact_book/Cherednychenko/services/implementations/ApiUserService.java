package contact_book.Cherednychenko.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import contact_book.Cherednychenko.annotations.CreateIfMode;
import contact_book.Cherednychenko.dto.contacts.GetUserListResponse;
import contact_book.Cherednychenko.dto.contacts.LoginRequest;
import contact_book.Cherednychenko.dto.contacts.RegisterRequest;
import contact_book.Cherednychenko.dto.users.LoginResponse;
import contact_book.Cherednychenko.dto.users.RegisterResponse;
import contact_book.Cherednychenko.entities.User;
import contact_book.Cherednychenko.exception.FailedGetContactException;
import contact_book.Cherednychenko.exception.FailedLoginContactException;
import contact_book.Cherednychenko.services.UserService;
import database.DataBaseConnection;
import database.DataBase;
import database.UserDataBase;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CreateIfMode("api")
@RequiredArgsConstructor
@ToString
public class ApiUserService implements UserService {

    private String token;
    private LocalDateTime localDateTime;
    private final String PATHURI;
    private final ObjectMapper OBJECTMAPPER;
    private final HttpClient HTTPCLIENT;
    private UserDataBase userDataBase;
    private int userId;
    private DataBaseConnection connectionToDataBase;


    @Override
    public String getToken() {return token;}

    @Override
    public int getUserId(){return  userId;}


    @Override
    public boolean isAuth() {
        if (token == null || localDateTime == null) {
            return false;
        }
            return Duration.between(LocalDateTime.now(), localDateTime).toMinutes() < 55;

    }

    @Override
    public void register(User user) {
        RegisterRequest registerRequest = new RegisterRequest();

        try {
            HttpRequest httpRequest= createPostRequest("/register",user);
            HttpResponse<String> response = HTTPCLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            RegisterResponse registerResponse = OBJECTMAPPER.readValue(response.body(),RegisterResponse.class);

            if("ok".equals(registerResponse.getStatus())){
                //token=registerResponse.getToken();
                localDateTime=LocalDateTime.now();

            }
            else{
                new FailedLoginContactException().getMessage(registerResponse.getError());
            }

        }
        catch (Exception e) {new FailedLoginContactException().getMessage();}
    }

    @Override
    public void login(User user) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(user.getLogin());
        loginRequest.setPassword(user.getPassword());

        try {
            HttpRequest httpRequest= createPostRequest("/login",loginRequest);
            HttpResponse<String> response = HTTPCLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            LoginResponse loginResponse = OBJECTMAPPER.readValue(response.body(),LoginResponse.class);

            if("ok".equals(loginResponse.getStatus())){
                token=loginResponse.getToken();
                localDateTime=LocalDateTime.now();
            }
            else{
                new FailedLoginContactException().getMessage(loginResponse.getError());
            }

        }
        catch (Exception e) {new FailedLoginContactException().getMessage();}
    }

    @Override
    public List<GetUserListResponse.Users> getAll() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(PATHURI + "/users"))
                .GET()
                .header("Authorization", "Bearer " + getToken())
                .build();

        try {
            HttpResponse<String> response = HTTPCLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            GetUserListResponse userListResponse = OBJECTMAPPER.readValue(response.body(), GetUserListResponse.class);

            if ("error".equals(userListResponse.getStatus())) {
                new FailedGetContactException().getMessage(userListResponse.getError());
            }

            return userListResponse.getUsers().stream()
                    .map(u -> {
                        GetUserListResponse.Users user = new GetUserListResponse.Users();
                        user.setLogin(u.getLogin());
                        user.setDate_born(u.getDate_born());
                        return user;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private HttpRequest createPostRequest(String path, Object request) throws JsonProcessingException {
        return HttpRequest.newBuilder()
                .uri(URI.create(PATHURI +path))
                .POST(HttpRequest.BodyPublishers.ofString(OBJECTMAPPER.writeValueAsString(request)))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .build();
    }

    @Override
    public void createUserServiceDatabase(){
        if (userDataBase== null){
            userDataBase = new UserDataBase();
        }
    }

    @Override
    public DataBase getDataBase() {
        return userDataBase;
    }


}
