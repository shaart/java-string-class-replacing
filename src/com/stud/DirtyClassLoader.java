package com.stud;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DirtyClassLoader extends ClassLoader {

    /**
     * This constructor is used to set the parent ClassLoader
     */
    public DirtyClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * Loads the class from the file system. The class file should be located in the file system.
     * The name should be relative to get the file location
     *
     * @param name Fully Classified name of class, for example com.journaldev.Foo
     */
    private Class getClass(String name) throws ClassNotFoundException {
        String file = name.replace('.', File.separatorChar) + ".class";
        byte[] b = null;
        try {
            // This loads the byte code data from the file
            b = loadClassFileData(file);
            // defineClass is inherited from the ClassLoader class
            // that converts byte array into a Class. defineClass is Final
            // so we cannot override it
            Class c = defineClass(name, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Every request for a class passes through this method. If the class is in com.journaldev
     * package, we will use this classloader or else delegate the request to parent classloader.
     *
     * @param name Full class name
     */
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        System.out.println("Loading Class '" + name + "'");
        if (name.equals("my.String") || name.equals("java.lang.String")) {
            System.out.println("Loading Class using " + this.getClass().getName());
            return getClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * Reads the file (.class) into a byte array. The file should be accessible as a resource and
     * make sure that its not in Classpath to avoid any confusion.
     *
     * @param name File name
     * @return Byte array read from the file
     * @throws IOException if any exception comes in reading the file
     */
    private byte[] loadClassFileData(String name) throws IOException {
        if (name.contains("String.class")) {
            System.out.println(new File(".").getAbsoluteFile());
            try (InputStream stream = new FileInputStream("out/production/javaStringClassReplacing/my/String.class")) {
                int size = stream.available();
                byte buff[] = new byte[size];
                DataInputStream in = new DataInputStream(stream);
                in.readFully(buff);
                in.close();
                return buff;
            }
        } else {
            InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
            int size = stream.available();
            byte buff[] = new byte[size];
            DataInputStream in = new DataInputStream(stream);
            in.readFully(buff);
            in.close();
            return buff;
        }
    }
}
