package Fox.test;

public class testexplicit
{
    public static Object ex(Class b)
    {
        if (b==String.class)
            return "a";
        return 0;
    }

    public static void main(String[] args)
    {
        System.out.println((String)testexplicit.ex(String.class));
        System.out.println(0);
    }
}
