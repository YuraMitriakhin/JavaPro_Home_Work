import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Task3 {

    public static void main(String[] s) throws IllegalAccessException, IOException, ClassNotFoundException, NoSuchFieldException, InstantiationException {
        MyClass myClass = new MyClass(1, "Hello");
        String values = Serialize(myClass);

        System.out.println(myClass);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream outStream = new ObjectOutputStream(byteOut);
        outStream.writeObject(values);
        outStream.flush();
        byteOut.flush();
        outStream.close();
        byteOut.close();

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream inStream = new ObjectInputStream(byteIn);

        String txt = (String)(inStream.readObject());
        MyClass cls = Deserialaze(txt, myClass.getClass());
        System.out.println(cls);

    }

    public static String Serialize(Object cls) throws IllegalAccessException {
        Class<?> c = cls.getClass();
        StringBuilder value = new StringBuilder();
        Field[] fields = c.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(MyClass.Save.class)) {
                if (Modifier.isPrivate(f.getModifiers())) {
                    f.setAccessible(true);
                }
                value.append(f.getName() + "=");

                if (f.getType() == int.class) {
                    value.append(f.getInt(cls));
                } else if (f.getType() == String.class) {
                    value.append(f.get(cls));
                } else if (f.getType() == long.class) {
                    value.append(f.getLong(cls));
                }

                value.append(";");
            }
        }
        return value.toString();
    }

    public static <T> T Deserialaze(String value, Class<T> cls) throws NoSuchFieldException, IllegalAccessException,InstantiationException{
        T cl = (T)cls.newInstance();

        String[] vals = value.split(";");
        for (String v : vals) {
            String[] num = v.split("=");
            Field fiel = cls.getDeclaredField(num[0]);

            if (Modifier.isPrivate(fiel.getModifiers())) {
                fiel.setAccessible(true);
            }

            if(fiel.isAnnotationPresent(MyClass.Save.class)){
                if(fiel.getType() == int.class){
                    fiel.setInt(cl, Integer.parseInt(num[1]));
                }else if (fiel.getType() == String.class){
                    fiel.set(cl, num[1]);
                }else if ((fiel.getType() == long.class)){
                    fiel.set(cl, Long.parseLong(num[1]));
                }
            }
        }
        return cl;
    }

}
