/**
 *hello world
 */
package com.tutorial;

import java.lang.reflect.Field;

import com.annotations.Range;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello Java!");
        printVariable();
        printArray();

        String response = getPass(85);
        System.out.println(response);

        // Person michael = new Person();
        // michael.setName("michael");
        // michael.setAge(30);
        // michael.setNameAndAge("michael", 30);

        Person michael = new Person("michael", 30);
        System.out.println(michael);
        System.out.println("person name is " + michael.getName());

        Student wen = new Student("wen", 3, 90);
        System.out.println("student score is " + wen.score);
        wen.run();

        printClassInfo(int.class);
        printClassInfo("abc".getClass());
        printClassInfo(String[].class);

        Person longName = new Person("my name is michale jackson", 80);
        System.out.println(longName);

        try {
            check(longName);
        } catch (IllegalArgumentException e) {
            System.out.println("Person " + longName + " check failed");
        } catch (ReflectiveOperationException e) {
            System.out.println("ReflectiveOperationException");
        }
    }

    public static void printVariable() {
        int x = 5;
        System.out.println("x is " + x);

        int i = 2147483647;
        int i2 = -2147483648;
        int i3 = 2_000_000_000; // 加下划线更容易识别
        int i4 = 0xff0000; // 十六进制表示的16711680
        int i5 = 0b1000000000; // 二进制表示的512

        long n1 = 9000000000000000000L; // long型的结尾需要加L
        long n2 = 900; // 没有加L，此处900为int，但int类型可以赋值给long
        // int i6 = 900L; // 错误：不能把long型赋值给int

        float f1 = 3.14f;
        float f2 = 3.14e38f; // 科学计数法表示的3.14x10^38
        // float f3 = 1.0; // 错误：不带f结尾的是double类型，不能赋值给float

        double d = 1.79e308;
        double d2 = -1.79e308;
        double d3 = 4.9e-324; // 科学计数法表示的4.9x10^-324

        // 字符类型char表示一个字符。Java的char类型除了可表示标准的ASCII外，还可以表示一个Unicode字符：
        // 注意char类型使用单引号'，且仅有一个字符，要和双引号"的字符串类型区分开。
        char a = 'A';
        char zh = '中';
        System.out.println(a);
        System.out.println(zh);

        // 除了上述基本类型的变量，剩下的都是引用类型。例如，引用类型最常用的就是String字符串：
        // 引用类型的变量类似于C语言的指针，它内部存储一个“地址”，指向某个对象在内存的位置
        String s = "hello";
        System.out.println("s is " + s);

        String srn = "\r\n";
        System.out.println("srn is empty:" + srn.isEmpty());

        double radius = 5.1;
        double area = getArea(radius);
        System.out.println("area of {radius} is " + area);

    }

    public static double getArea(double ridius) {
        final double PI = 3.14;
        double area = PI * ridius * ridius;
        return area;
    }

    public static void printArray() {
        int[] ns = new int[5];
        ns[0] = 10;
        System.out.println("ns is " + ns.toString());
        System.out.println("ns length is " + ns.length);

        int[] ns2 = new int[] { 68, 79, 91, 85, 62 };
        // int[] ns2 = { 68, 79, 91, 85, 62 };
        System.out.println("ns2 is " + ns2.toString());

        String[] names = { "michale", "tracy", "grace" };
        System.out.println(names);
    }

    public static String getPass(int score) {
        String response = null;
        if (score > 90) {
            response = "You are perfect!";
        } else if (score > 60 && score < 90) {
            response = "You are good";
        } else {
            response = "You are failed";
        }

        return response;
    }

    static void printClassInfo(Class cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }

    static void check(Person person) throws IllegalArgumentException, ReflectiveOperationException {
        // 遍历所有Field:
        for (Field field : person.getClass().getDeclaredFields()) {
            // 获取Field定义的@Range:
            Range range = field.getAnnotation(Range.class);
            // 如果@Range存在:
            if (range != null) {
                // 获取Field的值:
                Object value = field.get(person);
                // 如果值是String:
                if (value instanceof String s) {
                    // 判断值是否满足@Range的min/max:
                    if (s.length() < range.min() || s.length() > range.max()) {
                        throw new IllegalArgumentException("Invalid field: " + field.getName());
                    }
                } else if (value instanceof Integer i) {
                    if (i < 0 || i > 100) {
                        throw new IllegalArgumentException("Invalid field: " + field.getName());
                    }

                }
            }
        }
    }

}