package spring.baseanno.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //返回全限定类名
        return new String[]{"spring.baseanno.bean.Fish","spring.baseanno.bean.Tiger"};
        //return null;会在后续空指针异常，请return new String[0];
        //return new String[0];
    }
}
