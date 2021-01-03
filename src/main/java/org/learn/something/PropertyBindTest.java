package org.learn.something;


import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.boot.bind.PropertySourcesBinder;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyBindTest {

    public static void main(String[] args) {
        propertyBindTest();
    }

    public static void propertyBindTest() {
        final Properties properties = new Properties();
        final InputStream inputStream = PropertyBindTest.class.getClassLoader().getResourceAsStream("datasource.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final MapPropertySource mapPropertySource = new PropertiesPropertySource("datasourceConfiguration", properties);
        final PropertySourcesBinder sourcesBinder = new PropertySourcesBinder(mapPropertySource);

        final PoolProperties poolProperties = new PoolProperties();
        sourcesBinder.bindTo("ds.todo.ds1", poolProperties);

        System.out.println("poolProperties = " + poolProperties.getUrl());
        System.out.println("poolProperties = " + poolProperties.getMaxActive());
        System.out.println("poolProperties = " + poolProperties.getDefaultReadOnly());
        System.out.println("poolProperties = " + poolProperties.getValidationQuery());

        final PropertySources propertySources = sourcesBinder.getPropertySources();
        for (String stringPropertyName : properties.stringPropertyNames()) {
            System.out.println("stringPropertyName = " + stringPropertyName);
        }
    }

}
