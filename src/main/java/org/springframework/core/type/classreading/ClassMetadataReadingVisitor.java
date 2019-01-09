package org.springframework.core.type.classreading;

import com.sun.org.apache.xpath.internal.compiler.OpCodes;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.springframework.core.asm.ClassVisitor;
import org.springframework.core.asm.Opcodes;
import org.springframework.core.asm.SpringAsmInfo;
import org.springframework.core.type.ClassMetadata;
import org.springframework.util.ClassUtils;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {
    private String className;
    private boolean isInterface;
    private boolean isAnnotation;
    private boolean isAbstract;
    private boolean isFinal;
    private  String enclosingClassName;
    private boolean independentInnerClass;
    private String superClassName;
    private String[] interfaces;
    private Set<String> memberClassNames=new LinkedHashSet<>(4);

    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className= ClassUtils.convertResourcePathToClassName(name);
        this.isInterface=((access & Opcodes.ACC_INTERFACE)!=0);
        this.isAnnotation=((access & Opcodes.ACC_ANNOTATION)!=0);
        this.isAbstract=((access&Opcodes.ACC_ABSTRACT)!=0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (superName != null && !this.isInterface) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(superName);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public boolean isInterface() {
        return false;
    }

    @Override
    public boolean isAnnotation() {
        return false;
    }

    @Override
    public boolean isAbstract() {
        return false;
    }

    @Override
    public boolean isConcrete() {
        return false;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean isIndependent() {
        return false;
    }

    @Override
    public boolean hasEnclosingClass() {
        return false;
    }

    @Override
    public String getEnclosingClassName() {
        return null;
    }

    @Override
    public boolean hasSuperClass() {
        return false;
    }

    @Override
    public String getSuperClassName() {
        return null;
    }

    @Override
    public String[] getInterfaceNames() {
        return new String[0];
    }

    @Override
    public String[] getMemberClassNames() {
        return new String[0];
    }
}
