package com.cn.jackson.study2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:38
 * @Description:
 */
public class ItemSerializer extends JsonSerializer<Item> {
    @Override
    public void serialize(Item item, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", item.getId());
        jsonGenerator.writeStringField("itemName", item.getItemName());
        jsonGenerator.writeNumberField("owner", item.getOwner().getId());
        jsonGenerator.writeEndObject();
    }
}
