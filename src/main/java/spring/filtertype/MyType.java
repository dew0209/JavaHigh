package spring.filtertype;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义过滤
 * metadataReader：注解的元数据,读取到当前正在扫描的类信息
 * metadataReaderFactory：可以获取到其他任何类的信息
 *
 */
public class MyType implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();//获取当前类注解信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();//获取当前正在扫描的类的信息
        Resource resource = metadataReader.getResource();//获取当前类资源
        String className = classMetadata.getClassName();
        /**
         * 对spring内置的bean的注册是不起作用的
         * --->spring.baseanno.controller.OrderController
         * --->spring.baseanno.dao.OrderDao
         * --->spring.baseanno.service.OrderService
         * --->spring.baseanno.TestAll
         */
        System.out.println("--->" + className);
        if (className.contains("Dao")){
            System.out.println("添加-->" + className);
            return true;
        }
        return false;
    }
}
