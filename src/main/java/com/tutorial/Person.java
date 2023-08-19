package com.tutorial;

/**
 * java class
 */

interface HumanInterface {
    // 如果一个抽象类没有字段，所有方法全部都是抽象方法：就可以把该抽象类改写为接口：interface
    String getName();

    void run();
}

abstract class Human implements HumanInterface {
    public abstract void run();

    public abstract String getName();

}

class Person extends Human {
    // 把field从public改成private，外部代码不能访问这些field
    // 为了让子类可以访问父类的字段，我们需要把private改为protected。用protected修饰的字段可以被子类访问：
    protected String name;
    protected int age;

    // 创建实例的时候，实际上是通过构造方法来初始化实例的。
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name.strip();
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("invalid age value:" + age);
        }
        this.age = age;
    }

    public void setNameAndAge(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void run() {
        System.out.println("Person is running");
    }

    @Override
    public String toString() {
        return "Person:name=" + this.name;
    }
}

class Student extends Person {
    protected int score;

    public Student(String name, int age, int score) {
        // 调用父类的构造方法Person(String, int)
        super(name, age);
        this.score = score;
    }

    @Override
    public void run() {
        System.out.println("Student is runing");
    }
}