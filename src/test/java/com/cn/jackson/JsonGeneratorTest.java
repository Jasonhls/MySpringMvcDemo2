package com.cn.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @description: jackson-core包中JsonGenerator的使用
 * @author: helisen
 * @create: 2020-10-14 11:18
 **/
public class JsonGeneratorTest {
    @Test
    public void generatorJson() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator generator = jsonFactory.createGenerator(bos);
        generator.writeStartObject();
            generator.writeStringField("name", "smith");
            generator.writeNumberField("age", 29);
            generator.writeArrayFieldStart("hobby");
                generator.writeString("apple");
                generator.writeStartObject();
                    generator.writeNumberField("index", 1);
                    generator.writeStringField("color", "red");
                generator.writeEndObject();
                generator.writeBoolean( true);
            generator.writeEndArray();
            generator.writeBooleanField("isWeight", false);
        generator.writeEndObject();

        generator.flush();
        generator.close();

        String ret = bos.toString();
        bos.close();
        System.out.println(ret);
    }
}
