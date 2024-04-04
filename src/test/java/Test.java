import net.telos.boot.EgovBootApplication;
import net.telos.cmmn.utils.Aes;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
@SpringBootTest(classes = EgovBootApplication.class)
public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        System.out.println("+++++++++++++++++++++++++++++++");
        String encryption = Aes.encryption("어명소");
        System.out.println(encryption);
        System.out.println("+++++++++++++++++++++++++++++++");
        String decryption = Aes.decryption(encryption);
        System.out.println(new String(decryption));
        System.out.println("+++++++++++++++++++++++++++++++");
    }

}
