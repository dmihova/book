package com.tinqin.library.book.documentationexporter.formatter;


import io.swagger.v3.oas.annotations.Operation;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Formatter {


    public String formatData(MethodData methodData) {

        String modifiers = methodData
                .getModifiers()
                .stream()
                .map(Modifier::toString)
                .collect(Collectors.joining(" "));

        String returnType = Arrays.stream(methodData
                        .getReturnType()
                        .toString()
                        .split("\\."))
                .reduce((first, second) -> second)
                .map(returns -> returns.replace("?", methodData.getReturnTypeClass()))
                .orElse(methodData.getReturnType().toString());

        String methodName = methodData.getMethodName();

        String parameters = methodData
                .getParameters()
                .stream()
                .map(this::parseParameter)
                .collect(Collectors.joining(", "));

        String summary = ((Operation) methodData.getMethodInfo()).summary();

        return String.format("%s %s %s(%s) - %s", modifiers, returnType, methodName, parameters, summary);
    }

    private String parseParameter(VariableElement parameter) {
        String parameterName = parameter.getSimpleName().toString();

        String parameterType = Arrays.stream(parameter
                        .asType()
                        .toString()
                        .split("\\."))
                .reduce((first, second) -> second)
                .orElse(parameter.asType().toString());

        return String.format("%s %s", parameterType, parameterName);
    }
}
