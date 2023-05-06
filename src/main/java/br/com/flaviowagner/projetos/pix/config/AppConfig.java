package br.com.flaviowagner.projetos.pix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
    private static Environment env;
    @Autowired
    public void setEnvironment(Environment env) {
        AppConfig.env = env;
    }
    public static String getProperty(String key) {
        return env.getProperty(key);
    }

    public static Environment getEnvironment() {
        return env;
    }
}
