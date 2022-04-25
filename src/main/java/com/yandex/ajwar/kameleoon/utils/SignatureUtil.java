package com.yandex.ajwar.kameleoon.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright  2000-2004 The Apache Software Foundation
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Modifications copyright (C) 2018 com.github.hervian (Copy pastet parts of file from: http://www.javased.com/index.php?source_dir=jmd/src/org/apache/bcel/classfile/Utility.java)
 *
 * @author Anders Granau Høfft
 */
@UtilityClass
public class SignatureUtil {

    private static ThreadLocal<Integer> consumed_chars = ThreadLocal.withInitial(() -> 0);

    private static int unwrap(ThreadLocal<Integer> tl) {
        return tl.get();
    }

    private static void wrap(ThreadLocal<Integer> tl, int value) {
        tl.set(value);
    }

    /**
     * @param signature Method signature
     * @param chopit    Shorten class names ?
     * @return Array of argument types
     * @throws RuntimeException
     */
    public static String[] methodSignatureArgumentTypes(String signature, boolean chopit) throws RuntimeException {
        List<String> vec = new ArrayList<>();
        int index;
        try { // Read all declarations between for `(' and `)' 
            if (signature.charAt(0) != '(') {
                throw new RuntimeException("Invalid method signature: " + signature);
            }
            index = 1; // current string position 
            while (signature.charAt(index) != ')') {
                vec.add(signatureToString(signature.substring(index), chopit));
                //corrected concurrent private static field acess 
                index += unwrap(consumed_chars); // update position 
            }
        } catch (StringIndexOutOfBoundsException e) { // Should never occur 
            throw new RuntimeException("Invalid method signature: " + signature);
        }
        return vec.toArray(new String[0]);
    }

    /**
     * The field signature represents the value of an argument to a function or
     * the value of a variable. It is a series of bytes generated by the
     * following grammar:
     *
     * <PRE>
     * <field_signature> ::= <field_type>
     * <field_type>      ::= <base_type>|<object_type>|<array_type>
     * <base_type>       ::= B|C|D|F|I|J|S|Z
     * <object_type>     ::= L<fullclassname>;
     * <array_type>      ::= [<field_type>
     * <p>
     * The meaning of the base types is as follows:
     * B byte signed byte
     * C char character
     * D double double precision IEEE float
     * F float single precision IEEE float
     * I int integer
     * J long long integer
     * L<fullclassname>; ... an object of the given class
     * S short signed short
     * Z boolean true or false
     * [<field sig> ... array
     * </PRE>
     * <p>
     * This method converts this string into a Java type declaration such as
     * `String[]' and throws a `RuntimeException' when the parsed type is
     * invalid.
     *
     * @param signature Class signature
     * @param chopit    Flag that determines whether chopping is executed or not
     * @return Java type declaration
     * @throws RuntimeException
     */
    public static String signatureToString(String signature, boolean chopit) {
        //corrected concurrent private static field acess 
        wrap(consumed_chars, 1); // This is the default, read just one char like `B' 
        try {
            switch (signature.charAt(0)) {
                case 'B':
                    return "byte";
                case 'C':
                    return "char";
                case 'D':
                    return "double";
                case 'F':
                    return "float";
                case 'I':
                    return "int";
                case 'J':
                    return "long";
                case 'L': { // Full class name 
                    int index = signature.indexOf(';'); // Look for closing `;' 
                    if (index < 0) {
                        throw new RuntimeException("Invalid signature: " + signature);
                    }
                    //corrected concurrent private static field acess 
                    wrap(consumed_chars, index + 1); // "Lblabla;" `L' and `;' are removed 
                    return compactClassName(signature.substring(1, index), chopit);
                }
                case 'S':
                    return "short";
                case 'Z':
                    return "boolean";
                case '[': { // Array declaration 
                    int n;
                    StringBuilder brackets;
                    String type;
                    int consumed_chars; // Shadows global var 
                    brackets = new StringBuilder(); // Accumulate []'s
                    // Count opening brackets and look for optional size argument 
                    for (n = 0; signature.charAt(n) == '['; n++) {
                        brackets.append("[]");
                    }
                    consumed_chars = n; // Remember value 
                    // The rest of the string denotes a `<field_type>' 
                    type = signatureToString(signature.substring(n), chopit);
                    //corrected concurrent private static field acess 
                    //Utility.consumed_chars += consumed_chars; is replaced by: 
                    int _temp = unwrap(SignatureUtil.consumed_chars) + consumed_chars;
                    wrap(SignatureUtil.consumed_chars, _temp);
                    return type + brackets;
                }
                case 'V':
                    return "void";
                default:
                    throw new RuntimeException("Invalid signature: `" + signature + "'");
            }
        } catch (StringIndexOutOfBoundsException e) { // Should never occur 
            throw new RuntimeException("Invalid signature: " + e + ":" + signature);
        }
    }

    public static String compactClassName(String str) {
        return compactClassName(str, true);
    }


    /**
     * Shorten long class name <em>str</em>, i.e., chop off the <em>prefix</em>,
     * if the
     * class name starts with this string and the flag <em>chopit</em> is true.
     * Slashes <em>/</em> are converted to dots <em>.</em>.
     *
     * @param str    The long class name
     * @param prefix The prefix the get rid off
     * @param chopit Flag that determines whether chopping is executed or not
     * @return Compacted class name
     */
    public static String compactClassName(String str, String prefix, boolean chopit) {
        int len = prefix.length();
        str = str.replace('/', '.'); // Is `/' on all systems, even DOS 
        if (chopit) {
            // If string starts with `prefix' and contains no further dots 
            if (str.startsWith(prefix) && (str.substring(len).indexOf('.') == -1)) {
                str = str.substring(len);
            }
        }
        return str;
    }


    /**
     * Shorten long class names, <em>java/lang/String</em> becomes
     * <em>java.lang.String</em>,
     * e.g.. If <em>chopit</em> is <em>true</em> the prefix <em>java.lang</em>
     * is also removed.
     *
     * @param str    The long class name
     * @param chopit Flag that determines whether chopping is executed or not
     * @return Compacted class name
     */
    public static String compactClassName(String str, boolean chopit) {
        return compactClassName(str, "java.lang.", chopit);
    }
}
