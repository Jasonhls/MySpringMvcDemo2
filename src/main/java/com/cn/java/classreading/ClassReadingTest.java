package com.cn.java.classreading;

import com.cn.service.DemoService;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-03 17:29
 **/
public class ClassReadingTest {
    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();
    private static final MetadataReaderFactory METADATA_READER_FACTORY = new CachingMetadataReaderFactory();

    @Test
    public void test1() {
        String packagePatterns = "/com/cn/*";
        try {
            Set<Class<?>> classes = scanClasses(packagePatterns, DemoService.class);
            System.out.println(classes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Set<Class<?>> scanClasses(String packagePatterns, Class<?> assignableType) throws IOException {
        Set<Class<?>> classes = new HashSet();
        String[] packagePatternArray = StringUtils.tokenizeToStringArray(packagePatterns, ",; \t\n");
        String[] var5 = packagePatternArray;
        int var6 = packagePatternArray.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String packagePattern = var5[var7];
            Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources("classpath*:" + ClassUtils.convertClassNameToResourcePath(packagePattern) + "/**/*.class");
            Resource[] var10 = resources;
            int var11 = resources.length;

            for(int var12 = 0; var12 < var11; ++var12) {
                Resource resource = var10[var12];

                try {
                    ClassMetadata classMetadata = METADATA_READER_FACTORY.getMetadataReader(resource).getClassMetadata();
                    Class<?> clazz = Class.forName(classMetadata.getClassName());
                    if (assignableType == null || assignableType.isAssignableFrom(clazz)) {
                        classes.add(clazz);
                    }
                } catch (Throwable var16) {
                    var16.printStackTrace();
                }
            }
        }

        return classes;
    }
}
