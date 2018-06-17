package com.stud;

public class Main {

    public static void main(String[] args) {
        DirtyClassLoader dirtyClassLoader = new DirtyClassLoader(Main.class.getClassLoader());
        try {
            my.String myString = new my.String();
            System.out.println(myString);

            Class stringClass = dirtyClassLoader.loadClass("my.String");
            Object newInstance = stringClass.newInstance();
            CharSequence my = (CharSequence) newInstance;
            System.out.println("String: " + my);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
