package GoRestUsers;
import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringGenerator {

    public String randomName() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public String randomEmail() {

        return RandomStringUtils.randomAlphabetic(8) + "@randommail.com";
    }
}
