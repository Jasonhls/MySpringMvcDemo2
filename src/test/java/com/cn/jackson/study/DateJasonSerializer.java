package com.cn.jackson.study;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:16
 * @Description:
 */
public class DateJasonSerializer extends DateSerializer {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(sdf.format(value));
    }
}
