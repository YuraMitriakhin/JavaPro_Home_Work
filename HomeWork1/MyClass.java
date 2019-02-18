import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

class MyClass  {

    @Save
    public int a;

    @Save
    private String b;

    MyClass(int a, String b){
        this.a=a;
        this.b=b;
    }

    MyClass(){
    }

    public int getA(){
        return a;
    }

    public String getB(){
        return b;
    }

    public void setA(){
        this.a=a;
    }

    public void setB(){
        this.b=b;
    }

    @Retention(value = RetentionPolicy.RUNTIME)
    @interface Save{}

    @Override
    public String toString(){
        return "a="+this.a+"; b="+this.b+";";
    }

 }
