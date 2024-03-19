package io.papermc.generator.rewriter;

import io.papermc.generator.utils.ClassHelper;
import org.jetbrains.annotations.Nullable;

public record ClassNamed(String packageName, String simpleName, String dottedNestedName, @Nullable Class<?> knownClass) {

    public ClassNamed(Class<?> knownClass) {
        this(knownClass.getPackageName(), knownClass.getSimpleName(), ClassHelper.retrieveFullNestedName(knownClass), knownClass);
    }

    // the class name shouldn't have any '$' char in it
    // otherwise it will be interpreted as a nested class
    public static ClassNamed of(String packageName, String name) {
        int nestedIndex = name.lastIndexOf('$');
        final String simpleName;
        final String nestedName;
        if (nestedIndex != -1) {
            simpleName = name.substring(nestedIndex + 1);
            nestedName = name.replace('$', '.');
        } else {
            simpleName = name;
            nestedName = name;
        }
        return new ClassNamed(packageName, simpleName, nestedName, null);
    }

    public String rootClassSimpleName() {
        if (this.knownClass != null) {
            return ClassHelper.getRootClass(this.knownClass).getSimpleName();
        }

        int dotIndex = this.dottedNestedName.indexOf('.');
        if (dotIndex != -1) {
            return this.dottedNestedName.substring(0, dotIndex);
        }
        return this.dottedNestedName;
    }

    public String rootClassCanonicalName() {
        return this.packageName + '.' + this.rootClassSimpleName();
    }

    public String canonicalName() {
        if (this.knownClass != null) {
            return this.knownClass.getCanonicalName();
        }

        return this.packageName + '.' + this.dottedNestedName;
    }

}