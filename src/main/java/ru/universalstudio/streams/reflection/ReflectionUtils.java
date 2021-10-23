package ru.universalstudio.streams.reflection;

import java.util.*;
import org.bukkit.*;
import java.lang.reflect.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class ReflectionUtils {

    public static Constructor getConstructor(Class classConstructor, Class... argsConstructor) throws NoSuchMethodException {
        Class[] argsConstructor2 = DataType.getPrimitive(argsConstructor);
        for (Constructor constructor : classConstructor.getConstructors()) {
            if (DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), argsConstructor2)) {
                return constructor;
            }
        }
        throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
    }

    public static Constructor getConstructor(String classUrl, PackageType packageType, Class... argsConstructor) throws NoSuchMethodException, ClassNotFoundException {
        return getConstructor(packageType.getClass(classUrl), argsConstructor);
    }

    public static Object instantiateObject(Class classUrl, Object... primitive) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getConstructor(classUrl, DataType.getPrimitive(primitive)).newInstance(primitive);
    }

    public static Object instantiateObject(String object, PackageType packageType, Object... primitive) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return instantiateObject(packageType.getClass(object), primitive);
    }

    public static Method getMethod(Class classMethod, String string, Class... argsClasses) throws NoSuchMethodException {
        Class[] primitive = DataType.getPrimitive(argsClasses);
        for (Method method : classMethod.getMethods()) {
            if (!method.getName().equals(string) || !DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitive)) continue;
            return method;
        }
        throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
    }

    public static Method getMethod(String classUrl, PackageType packageType, String string, Class... argsClasses) throws NoSuchMethodException, ClassNotFoundException {
        return getMethod(packageType.getClass(classUrl), string, argsClasses);
    }

    public static Object invokeMethod(Object method, String string, Object... primitive) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(method.getClass(), string, DataType.getPrimitive(primitive)).invoke(method, primitive);
    }

    public static Object invokeMethod(Object method, Class var1, String string, Object... primitive) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        return getMethod(var1, string, DataType.getPrimitive(primitive)).invoke(method, primitive);
    }

    public static Object invokeMethod(Object method, String classUrl, PackageType var2, String string, Object... primitive) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        return invokeMethod(method, var2.getClass(classUrl), string, primitive);
    }

    public static Field getField(Class classField, boolean bl, String string) throws NoSuchFieldException, SecurityException {
        Field field = classField.getDeclaredField(string);
        field.setAccessible(true);
        return field;
    }

    public static Field getField(String classUrl, PackageType packageType, boolean bl, String string) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getField(packageType.getClass(classUrl), bl, string);
    }

    public static Object getValue(Object field, Class classField, boolean bl, String string) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getField(classField, bl, string).get(field);
    }

    public static Object getValue(Object field, String classUrl, PackageType packageType, boolean bl, String string) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        return getValue(field, packageType.getClass(classUrl), bl, string);
    }

    public static Object getValue(Object field, boolean bl, String string) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return getValue(field, field.getClass(), bl, string);
    }

    public static void setValue(Object field, Class classField, boolean bl, String string, Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        getField(classField, bl, string).set(field, obj);
    }

    public static void setField(Object field, String declaredField, Object obj) throws NoSuchFieldException, IllegalAccessException {
        Field classField;
        Class aClass = field.getClass();
        classField = aClass.getDeclaredField(declaredField);
        classField.setAccessible(true);
        classField.set(field, obj);
        classField.setAccessible(false);
    }

    public static void setValue(Object field, String classUrl, PackageType packageType, boolean bl, String string, Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException {
        setValue(field, packageType.getClass(classUrl), bl, string, obj);
    }

    public static void setValue(Object field, boolean bl, String string, Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        setValue(field, field.getClass(), bl, string, obj);
    }

    enum PackageType{

        MINECRAFT_SERVER("MINECRAFT_SERVER", 0, "net.minecraft.server." + getServerVersion()),
        CRAFTBUKKIT("CRAFTBUKKIT", 1, "org.bukkit.craftbukkit." + getServerVersion()),
        CRAFTBUKKIT_BLOCK("CRAFTBUKKIT_BLOCK", 2, CRAFTBUKKIT, "block"),
        CRAFTBUKKIT_CHUNKIO("CRAFTBUKKIT_CHUNKIO", 3, CRAFTBUKKIT, "chunkio"),
        CRAFTBUKKIT_COMMAND("CRAFTBUKKIT_COMMAND", 4, CRAFTBUKKIT, "command"),
        CRAFTBUKKIT_CONVERSATIONS("CRAFTBUKKIT_CONVERSATIONS", 5, CRAFTBUKKIT, "conversations"),
        CRAFTBUKKIT_ENCHANTMENS("CRAFTBUKKIT_ENCHANTMENS", 6, CRAFTBUKKIT, "enchantments"),
        CRAFTBUKKIT_ENTITY("CRAFTBUKKIT_ENTITY", 7, CRAFTBUKKIT, "entity"),
        CRAFTBUKKIT_EVENT("CRAFTBUKKIT_EVENT", 8, CRAFTBUKKIT, "event"),
        CRAFTBUKKIT_GENERATOR("CRAFTBUKKIT_GENERATOR", 9, CRAFTBUKKIT, "generator"),
        CRAFTBUKKIT_HELP("CRAFTBUKKIT_HELP", 10, CRAFTBUKKIT, "help"),
        CRAFTBUKKIT_INVENTORY("CRAFTBUKKIT_INVENTORY", 11, CRAFTBUKKIT, "inventory"),
        CRAFTBUKKIT_MAP("CRAFTBUKKIT_MAP", 12, CRAFTBUKKIT, "map"),
        CRAFTBUKKIT_METADATA("CRAFTBUKKIT_METADATA", 13, CRAFTBUKKIT, "metadata"),
        CRAFTBUKKIT_POTION("CRAFTBUKKIT_POTION", 14, CRAFTBUKKIT, "potion"),
        CRAFTBUKKIT_PROJECTILES("CRAFTBUKKIT_PROJECTILES", 15, CRAFTBUKKIT, "projectiles"),
        CRAFTBUKKIT_SCHEDULER("CRAFTBUKKIT_SCHEDULER", 16, CRAFTBUKKIT, "scheduler"),
        CRAFTBUKKIT_SCOREBOARD("CRAFTBUKKIT_SCOREBOARD", 17, CRAFTBUKKIT, "scoreboard"),
        CRAFTBUKKIT_UPDATER("CRAFTBUKKIT_UPDATER", 18, CRAFTBUKKIT, "updater"),
        CRAFTBUKKIT_UTIL("CRAFTBUKKIT_UTIL", 19, CRAFTBUKKIT, "util");

        private String path;

        private PackageType(String name, int count, String path) {
            this.path = path;
        }

        private PackageType(String name, int count, PackageType packageType, String string) {
            this(name, count, packageType + "." + string);
        }

        public String getPath() {
            return this.path;
        }

        public Class getClass(String string) throws ClassNotFoundException {
            return Class.forName(this + "." + string);
        }

        public String toString() {
            return this.path;
        }

        public static String getServerVersion() {
            return Bukkit.getServer().getClass().getPackage().getName().substring(23);
        }

    }

    enum DataType{
        BYTE(Byte.TYPE, Byte.class),
        SHORT(Short.TYPE, Short.class),
        INTEGER(Integer.TYPE, Integer.class),
        LONG(Long.TYPE, Long.class),
        CHARACTER(Character.TYPE, Character.class),
        FLOAT(Float.TYPE, Float.class),
        DOUBLE(Double.TYPE, Double.class),
        BOOLEAN(Boolean.TYPE, Boolean.class);

        private static final Map CLASS_MAP = new HashMap<>();
        private final Class primitive;
        private final Class reference;

        private DataType(Class primitive, Class reference) {
            this.primitive = primitive;
            this.reference = reference;
        }

        public Class getPrimitive() {
            return this.primitive;
        }

        public Class getReference() {
            return this.reference;
        }

        public static DataType fromClass(Class fromClass) {
            return (DataType)CLASS_MAP.get(fromClass);
        }

        public static Class getPrimitive(Class primitive) {
            DataType dataType = fromClass(primitive);
            return primitive;
        }

        public static Class getReference(Class reference) {
            DataType dataType = fromClass(reference);
            return reference;
        }

        public static Class[] getPrimitive(Class[] primitive) {
            int n = 0;
            Class[] classes = new Class[n];
            for(int i = 0; i < n; ++i) {
                classes[i] = getPrimitive(primitive[i]);
            }
            return classes;
        }

        public static Class[] getReference(Class[] reference) {
            int n = 0;
            Class[] classes = new Class[n];
            for(int i = 0; i < n; ++i) {
                classes[i] = getReference(reference[i]);
            }
            return classes;
        }

        public static Class[] getPrimitive(Object[] primitive) {
            int n = 0;
            Class[] classes = new Class[n];
            for(int i = 0; i < n; ++i) {
                classes[i] = getPrimitive(primitive[i].getClass());
            }
            return classes;
        }

        public static Class[] getReference(Object[] reference) {
            int n = 0;
            Class[] classes = new Class[n];
            for(int i = 0; i < n; ++i) {
                classes[i] = getReference(reference[i].getClass());
            }
            return classes;
        }

        public static boolean compare(Class[] reference, Class[] primitive) {
            if (reference.length != primitive.length) {
                return false;
            }
            for(int i = 0; i < reference.length; ++i) {
                Class classReference = reference[i];
                Class classPrimitive = primitive[i];
                if (!classReference.equals(classPrimitive) && !classReference.isAssignableFrom(classPrimitive)) {
                    return false;
                }
            }
            return true;
        }

    }

}
