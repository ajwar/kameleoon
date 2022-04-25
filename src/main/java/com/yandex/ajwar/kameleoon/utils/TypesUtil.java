package com.yandex.ajwar.kameleoon.utils;

import lombok.experimental.UtilityClass;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@UtilityClass
public class TypesUtil {

    private interface SuperConsumer extends Serializable {
    }

    public @FunctionalInterface
    interface Supplier<T> extends SuperConsumer {
        void getDeclaredMethod() throws Exception;
    }

    public @FunctionalInterface
    interface Consumer<T> extends SuperConsumer {
        void getDeclaredMethod(T t) throws Exception;
    }

    public @FunctionalInterface
    interface BiConsumer<T1, T2> extends SuperConsumer {
        void getDeclaredMethod(T1 t1, T2 t2) throws Exception;
    }

    public @FunctionalInterface
    interface TriConsumer<T1, T2, T3> extends SuperConsumer {
        void getDeclaredMethod(T1 t1, T2 t2, T3 t3) throws Exception;
    }

    public @FunctionalInterface
    interface QuadConsumer<T1, T2, T3, T4> extends SuperConsumer {
        void getDeclaredMethod(T1 t1, T2 t2, T3 t3, T4 t4) throws Exception;
    }

    public @FunctionalInterface
    interface PentaConsumer<T1, T2, T3, T4, T5> extends SuperConsumer {
        void getDeclaredMethod(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) throws Exception;
    }

    public @FunctionalInterface
    interface SextConsumer<T1, T2, T3, T4, T5, T6> extends SuperConsumer {
        void getDeclaredMethod(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) throws Exception;
    }


    public static <T1> Method createMethod(Supplier<T1> consumer) {
        return createMethodFromSuperConsumer(consumer);
    }

    public static <T1> Method createMethod(Consumer<T1> consumer) {
        return createMethodFromSuperConsumer(consumer);
    }

    public static <T1, T2> Method createMethod(BiConsumer<T1, T2> consumer) {
        return createMethodFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3> Method createMethod(TriConsumer<T1, T2, T3> consumer) {
        return createMethodFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3, T4> Method createMethod(QuadConsumer<T1, T2, T3, T4> consumer) {
        return createMethodFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3, T4, T5> Method createMethod(PentaConsumer<T1, T2, T3, T4, T5> consumer) {
        return createMethodFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3, T4, T5, T6> Method createMethod(SextConsumer<T1, T2, T3, T4, T5, T6> consumer) {
        return createMethodFromSuperConsumer(consumer);
    }

    public static <T1> String getName(Supplier<T1> consumer) {
        return createMethodNameFromSuperConsumer(consumer);
    }

    public static <T1> String getName(Consumer<T1> consumer) {
        return createMethodNameFromSuperConsumer(consumer);
    }

    public static <T1, T2> String getName(BiConsumer<T1, T2> consumer) {
        return createMethodNameFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3> String getName(TriConsumer<T1, T2, T3> consumer) {
        return createMethodNameFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3, T4> String getName(QuadConsumer<T1, T2, T3, T4> consumer) {
        return createMethodNameFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3, T4, T5> String getName(PentaConsumer<T1, T2, T3, T4, T5> consumer) {
        return createMethodNameFromSuperConsumer(consumer);
    }

    public static <T1, T2, T3, T4, T5, T6> String getName(SextConsumer<T1, T2, T3, T4, T5, T6> consumer) {
        return createMethodNameFromSuperConsumer(consumer);
    }

    public static <T1> String getFieldName(Supplier<T1> consumer) {
        return getFieldName(createMethodNameFromSuperConsumer(consumer));
    }

    public static <T1> String getFieldName(Consumer<T1> consumer) {
        return getFieldName(createMethodNameFromSuperConsumer(consumer));
    }

    public static <T1, T2> String getFieldName(BiConsumer<T1, T2> consumer) {
        return getFieldName(createMethodNameFromSuperConsumer(consumer));
    }

    public static <T1, T2, T3> String getFieldName(TriConsumer<T1, T2, T3> consumer) {
        return getFieldName(createMethodNameFromSuperConsumer(consumer));
    }

    public static <T1, T2, T3, T4> String getFieldName(QuadConsumer<T1, T2, T3, T4> consumer) {
        return getFieldName(createMethodNameFromSuperConsumer(consumer));
    }

    public static <T1, T2, T3, T4, T5> String getFieldName(PentaConsumer<T1, T2, T3, T4, T5> consumer) {
        return getFieldName(createMethodNameFromSuperConsumer(consumer));
    }

    public static <T1, T2, T3, T4, T5, T6> String getFieldName(SextConsumer<T1, T2, T3, T4, T5, T6> consumer) {
        return getFieldName(createMethodNameFromSuperConsumer(consumer));
    }

    /**
     * Thanks to Holger for this StackOverflow answer: https://stackoverflow.com/a/21879031/6095334
     */
    private static Method createMethodFromSuperConsumer(SuperConsumer lambda) {
        SerializedLambda serializedLambda = getSerializedLambda(lambda);
        if (serializedLambda == null) {
            return null;
        } else {
            String className = SignatureUtil.compactClassName(serializedLambda.getImplClass(), false);
            try {
                return Class.forName(className).getDeclaredMethod(serializedLambda.getImplMethodName(), getParameters(serializedLambda.getImplMethodSignature()));
            } catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static SerializedLambda getSerializedLambda(SuperConsumer lambda) {
        SerializedLambda serializedLambda = null;
        for (Class<?> cl = lambda.getClass(); cl != null; cl = cl.getSuperclass()) {
            try {
                Method m = cl.getDeclaredMethod("writeReplace");
                m.setAccessible(true);
                Object replacement = m.invoke(lambda);
                if (!(replacement instanceof SerializedLambda))
                    break;// custom interface implementation
                serializedLambda = (SerializedLambda) replacement;
                break;
            } catch (NoSuchMethodException ignored) {
            } catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
        return serializedLambda;
    }

    private static String createMethodNameFromSuperConsumer(SuperConsumer lambda) {
        SerializedLambda serializedLambda = getSerializedLambda(lambda);
        return serializedLambda.getImplMethodName();
    }

    private static Class<?>[] getParameters(String signature) throws ClassNotFoundException {
        String[] parameters = signature.substring(1, signature.lastIndexOf(')')).replaceAll("/", ".").split(";");
        String[] params = SignatureUtil.methodSignatureArgumentTypes(signature, false);

        Class<?>[] paramTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            //Arrays must somewhat surprising be in something resembling the JVM format (fx: [Ljava.lang.String) whereas regular classes must be in the format of class.getName (fx java.lang.String)
            paramTypes[i] = Class.forName(params[i].contains("[") ? parameters[i] + ";" : params[i]);
        }
        return paramTypes;
    }

    private static String getFieldName(String methodName) {
        if (methodName == null) {
            return null;
        } else {
            return methodName.startsWith("is") ? methodName.substring(2, 3).toLowerCase() + methodName.substring(3) :
                    methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
        }
    }
}
