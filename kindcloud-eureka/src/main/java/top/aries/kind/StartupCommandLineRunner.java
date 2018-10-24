package top.aries.kind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Value("${license.enable}")
    private boolean licenseEnable;

    @Value("${license.serverLicCode}")
    private String serverLicCode;

    @Value("${license.registerKey}")
    private String registerKey;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {
        logger.info("触发授权机制......");
        if (licenseEnable) {
            if (!verifyServerLic(serverLicCode, registerKey)) {
                throw new Exception("授权失败");
            }
        }

    }

    public boolean verifyServerLic(String serverLicCode, String registerKey) {
        if ("6dH7aVC5PsZf".equals(serverLicCode) && "abcdefghijklmnopqrstuvwxyz123456".equals(registerKey)) {
            logger.info("授权成功");
            return true;
        } else {
            logger.error("授权失败");
            return false;
        }
    }

}
