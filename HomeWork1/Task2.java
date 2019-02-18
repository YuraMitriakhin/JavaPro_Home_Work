import java.io.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Task2 {

    @SaveTo(path = "file.txt")
    static class Conteiner {

        String text = "Hello world!";

        @Save
        public void save(String path, String text){
            try(PrintWriter pf = new PrintWriter(path)) {
                   pf.println(text);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Retention(value = RetentionPolicy.RUNTIME)
    @interface SaveTo{
        String path() default "eror";
    }

    @Retention(value = RetentionPolicy.RUNTIME)
    @interface Save{ }

    public static void main (String[] s) throws InvocationTargetException, IllegalAccessException{
         Conteiner conteiner = new Conteiner();
         Class<?> cls = conteiner.getClass();

         SaveTo save = cls.getAnnotation(SaveTo.class);

        Method[] methods = cls.getMethods();
        for (Method m: methods) {
            if(m.isAnnotationPresent(Save.class)){
                m.invoke(conteiner, save.path(), conteiner.text);
            }
        }
    }

}
