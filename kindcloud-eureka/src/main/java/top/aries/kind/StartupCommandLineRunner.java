package top.aries.kind;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Author: kindaries
 * Company: shenzhen Aisino
 * Date: Created in 2018-09-20 11:32
 * Created by IntelliJ IDEA.
 */
@Component
@Order(value = 0)
public class StartupCommandLineRunner implements CommandLineRunner {

    @Value("${license.registerKey}")
    private String registerKey;

    @Value("${license.serverLicCode}")
    private String serverLicCode;

    @Value("${license.enable}")
    private boolean licenseEnable;

    @Override
    public void run(String... args) throws Exception {
        if (licenseEnable) {
            if (verifyServerLic(registerKey, serverLicCode)) {
                throw new Exception("授权失败");
            }
        }

    }

    public boolean verifyServerLic(String serverLicCode, String registerKey) {
        if ("007".equals(serverLicCode) && "ASDFGHJKL".equals(registerKey))
            return true;
        else
            return false;
    }

}
