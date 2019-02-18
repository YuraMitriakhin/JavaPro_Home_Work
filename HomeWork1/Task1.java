import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Task1 {

    public static void main(String[] s) throws InvocationTargetException, IllegalAccessException {

        ClassAnnotation clas = new ClassAnnotation();
        Class<?> cs = ClassAnnotation.class;

        if(cs.isAnnotationPresent(ClassAn.class)){
            System.out.println("Yes");
        }

        Method[] met = cs.getMethods();
        for (Method method: met) {
            if(method.isAnnotationPresent(MyAnnotation.class)){
                MyAnnotation an = method.getAnnotation(MyAnnotation.class);
                method.invoke(clas,an.a(),an.b());
            }
        }

    }

    @Target(value= ElementType.TYPE)
    @Retention(value= RetentionPolicy.RUNTIME)
    @interface ClassAn{}

    @Target(value= ElementType.METHOD)
    @Retention(value= RetentionPolicy.RUNTIME)
    @interface MyAnnotation{
        int a() default 1;
        int b() default 1;
    }

    @ClassAn
    static class ClassAnnotation{

        @MyAnnotation(a = 4, b = 5)
        public void func(int a, int b) {
            System.out.println("a=" + a + " b=" + b);
        }
    }

}
