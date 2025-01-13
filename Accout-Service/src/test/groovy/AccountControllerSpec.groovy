import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerSpec extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    private TestRestTemplate restTemplate

    def "test create account"() {
        given:
        def account = new Account(username: "testuser", email: "test@example.com", password: "password")

        when:
        def response = restTemplate.postForEntity("http://localhost:$port/accounts", account, Account.class)

        then:
        response.statusCode.value() == 201
        response.body.username == "testuser"
        response.body.email == "test@example.com"
    }

    def "test read account"() {
        given:
        def account = new Account(username: "readuser", email: "read@example.com", password: "password")
        def createdAccount = restTemplate.postForEntity("http://localhost:$port/accounts", account, Account.class).body

        when:
        def response = restTemplate.getForEntity("http://localhost:$port/accounts/${createdAccount.id}", Account.class)

        then:
        response.statusCode.value() == 200
        response.body.username == "readuser"
        response.body.email == "read@example.com"
    }

    def "test update account"() {
        given:
        def account = new Account(username: "updateuser", email: "update@example.com", password: "password")
        def createdAccount = restTemplate.postForEntity("http://localhost:$port/accounts", account, Account.class).body

        when:
        def updatedAccount = new Account(username: "updateduser", email: "updated@example.com", password: "newpassword")
        restTemplate.put("http://localhost:$port/accounts/${createdAccount.id}", updatedAccount)
        def response = restTemplate.getForEntity("http://localhost:$port/accounts/${createdAccount.id}", Account.class)

        then:
        response.statusCode.value() == 200
        response.body.username == "updateduser"
        response.body.email == "updated@example.com"
        response.body.password == "newpassword"
    }

    def "test delete account"() {
        given:
        def account = new Account(username: "deleteuser", email: "delete@example.com", password: "password")
        def createdAccount = restTemplate.postForEntity("http://localhost:$port/accounts", account, Account.class).body

        when:
        restTemplate.delete("http://localhost:$port/accounts/${createdAccount.id}")
        def response = restTemplate.getForEntity("http://localhost:$port/accounts/${createdAccount.id}", Account.class)

        then:
        response.statusCode.value() == 404
    }
}
