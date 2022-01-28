package com.nylee.common.config;

import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class CustomConfig {

    private static final Logger logger = LoggerFactory.getLogger(CustomConfig.class);
    @Autowired
    private StandardEnvironment environment;
    private long lastModTime = 0L;
    private PropertySource<?> appConfigPropertySource = null;
    private Path configPath;


    @Value("${spring.config.location}") String realPath;
    @Value("${spring.profiles.active}") String active;

    @PostConstruct
    public void createContext() throws IOException {

        String[] path = realPath.split(",");
        realPath = path[path.length-1];
        String serverType = "";

        if( active.contains("td") ) serverType = "td";
        else if( active.contains("rd") ) serverType = "rd";
        else if( active.contains("master") || active.contains("real") ) serverType = "real";
        else serverType = "local";

        String propertyName = "application-" + serverType + ".yml";
        String property = "applicationConfig: [file:" + realPath + propertyName + "]";

        MutablePropertySources propertySources = environment.getPropertySources();

        if( !serverType.equals("local") ) {
            Resource resource = new FileSystemResource(realPath + propertyName);
            YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();

            List<PropertySource<?>> yamlProperties = sourceLoader.load(property, resource);

            for( PropertySource<?> el : yamlProperties ) {
                propertySources.addLast(el);
            }
        }

        //version 2.3.0 RELEASE
        //String property = "Config resource '" + realPath + PROPERTY_NAME + "' via location '" + realPath + "' (document #0)";

        //version 2.1.9 RELEASE
        PropertySource<?> resourcePropertySource = propertySources.get(property);
        configPath = Paths.get(realPath + "/" + propertyName).toAbsolutePath();

        if( configPath == null ) {
            property = "class path resource [" + propertyName + "]";
            configPath = Paths.get("src/main/resources/" + propertyName).toAbsolutePath();
        }

        resourcePropertySource = propertySources.get(property);
        appConfigPropertySource = resourcePropertySource;
    }

    @Scheduled(fixedRate = 30000)
    public void reload() {

        try{

            long currentModTs = Files.getLastModifiedTime(configPath).toMillis();
            if (currentModTs > lastModTime) {
                lastModTime = currentModTs;
                Properties properties = new Properties();
                Yaml yaml = new Yaml();
                @Cleanup InputStream configStream = new FileInputStream(configPath.toString());
                Map<String, Object> configMap = new LinkedHashMap<>();
                Iterable<Object> rules = yaml.loadAll(configStream);

                for( Object rule : rules ) {
                    Map<String,Object> tempMap =  (Map<String,Object>)rule;

                    if( tempMap.get("custom") == null ) continue;

                    configMap = (Map<String,Object>)( tempMap.get("custom") );

                    break;
                }
                if( configMap.size() == 0 ) return;

                properties.putAll(configMap);
                String property = appConfigPropertySource.getName();
                PropertiesPropertySource updatedProperty = new PropertiesPropertySource(property, properties);
                environment.getPropertySources().replace(property, updatedProperty);
                logger.info("Configs {} were reloaded", property);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    public String getValue( String key ) {
        return environment.getProperty(key);
    }

    public String getValue( String key , String defaultValue ) {
        return environment.getProperty(key, defaultValue);
    }
}
