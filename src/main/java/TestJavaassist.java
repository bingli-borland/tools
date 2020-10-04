import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class TestJavaassist {

    public static void main(String[] args) throws Exception {
        test();
    }

    private static void test() throws NotFoundException, CannotCompileException, IOException {
        // org.apache.maven.plugin.assembly.io.DefaultAssemblyReader
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("org.apache.maven.plugin.assembly.io.DefaultAssemblyReader");
        CtMethod m = cc.getDeclaredMethod("addAssemblyForDescriptorReference");
        m.insertBefore("{ System.out.println(111111); System.out.println(222222); }");
        cc.writeFile("D:/");
    }

}
