package io.papermc.generator.types.craftblockdata.property.holder.appender;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import io.papermc.generator.types.StructuredGenerator;
import io.papermc.generator.types.craftblockdata.Types;
import io.papermc.generator.types.craftblockdata.property.converter.ConverterBase;
import io.papermc.generator.types.craftblockdata.property.holder.DataHolderType;
import io.papermc.generator.utils.NamingManager;
import java.util.Set;

public class ArrayAppender implements DataAppender {

    @Override
    public DataHolderType getType() {
        return DataHolderType.ARRAY;
    }

    @Override
    public void addExtras(final TypeSpec.Builder builder, final FieldSpec field, final ParameterSpec indexParameter, final ConverterBase childConverter, final StructuredGenerator<?> generator, final NamingManager naming) {
        if (childConverter.getApiType() == Boolean.TYPE) {
            String collectFieldName = naming.getVariableNameWrapper().post("s").concat();
            MethodSpec.Builder methodBuilder = generator.createMethod(naming.getMethodNameWrapper().post("s").concat());
            methodBuilder.addStatement("$T $L = $T.builder()", ParameterizedTypeName.get(ImmutableSet.Builder.class, Integer.class), collectFieldName, ImmutableSet.class);
            methodBuilder.beginControlFlow("for (int $1L = 0, len = $2N.length; $1L < len; $1L++)", Types.INDEX_VARIABLE, field);
            {
                methodBuilder.beginControlFlow("if (" + childConverter.rawGetExprent().formatted("$N[$N]") + ")", field, indexParameter);
                {
                    methodBuilder.addStatement("$L.add($L)", collectFieldName, Types.INDEX_VARIABLE);
                }
                methodBuilder.endControlFlow();
            }
            methodBuilder.endControlFlow();
            methodBuilder.addStatement("return $L.build()", collectFieldName);
            methodBuilder.returns(ParameterizedTypeName.get(Set.class, Integer.class));

            builder.addMethod(methodBuilder.build());
        }

        {
            MethodSpec.Builder methodBuilder = generator.createMethod(naming.getMethodNameWrapper().pre("Maximum").post("s").concat());
            methodBuilder.addStatement("return $N.length", field);
            methodBuilder.returns(Integer.TYPE);

            builder.addMethod(methodBuilder.build());
        }
    }
}
