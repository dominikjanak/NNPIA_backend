package cz.janakdom.backend.Testing;

import cz.janakdom.backend.Creator;
import cz.janakdom.backend.controller.rest.security.LoginController
import cz.janakdom.backend.model.ApiResponse
import cz.janakdom.backend.model.AuthRequest;
import cz.janakdom.backend.model.database.User
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ControllerTest {
    @Autowired
    private LoginController loginController;

    @Autowired
    private Creator creator;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    void loginTest(){
        String username = "janakdom";
        String password = "123456789";
        User user = new User(username: username, password: encoder.encode(password));
        creator.saveEntity(user);

        AuthRequest authRequest = new AuthRequest(username: username, password: password);

        ApiResponse response = loginController.authenticate(authRequest);

        Assert.assertEquals(response.status, 200);
        Assert.assertEquals(response.status_key, "SUCCESS");
        Assert.assertEquals(response.result.username, username);
    }

}
