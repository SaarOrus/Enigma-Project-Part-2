package components;

public class Reflector {
    //the value in index i is the reflection of index i
    private int reflection[];

    //copyCtor
    public Reflector(Reflector reflector){
        int copyIntReflection[]=new int[reflector.reflection.length];
        System.arraycopy(reflector.reflection,0,copyIntReflection,0,reflector.reflection.length);
        this.reflection=copyIntReflection;
    }

    public Reflector(int reflectionIndex[]) {
        this.reflection =reflectionIndex;
    }

    public int reflectIndex(int enteredPosition) {
        return reflection[enteredPosition];
    }

}
