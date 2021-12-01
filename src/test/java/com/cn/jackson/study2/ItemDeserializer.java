package com.cn.jackson.study2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:45
 * @Description:
 */
public class ItemDeserializer extends JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = (Integer)(node.get("id")).numberValue();
        String itemName = node.get("itemName").asText();
        int userId = (Integer) node.get("id").numberValue();
        return new Item(id, itemName, new User(userId, null));
    }
}
