package kr.magicbox.shortform.global.configuration;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "kr.magicbox.shortform")
public class PropertiesConfiguration {
}
