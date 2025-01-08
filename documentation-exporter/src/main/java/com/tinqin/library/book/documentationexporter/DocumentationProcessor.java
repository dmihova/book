package com.tinqin.library.book.documentationexporter;

import com.google.auto.service.AutoService;
import com.tinqin.library.book.documentationexporter.annotation.DocumentedApi;
import com.tinqin.library.book.documentationexporter.formatter.Formatter;
import com.tinqin.library.book.documentationexporter.formatter.MethodData;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@SupportedAnnotationTypes("com.tinqin.library.book.documentationexporter.annotation.DocumentedApi")
@AutoService(Processor.class)
public class DocumentationProcessor extends AbstractProcessor {
    private final Formatter formatter;

    public DocumentationProcessor() {
        this.formatter = new Formatter();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return true;
        }

        System.out.println(System.lineSeparator());
        System.out.println("=====================================");
        System.out.println(System.lineSeparator());

        roundEnv.getElementsAnnotatedWith(DocumentedApi.class)
                .stream()
                .map(this::fetchMethodData)
                .map(formatter::formatData)
                .forEach(System.out::println);

        System.out.println(System.lineSeparator());
        System.out.println("=====================================");
        System.out.println(System.lineSeparator());

        return true;
    }

    private MethodData fetchMethodData(Element element) {

        String methodName = element.getSimpleName().toString();
        Set<Modifier> modifiers = element.getModifiers();
        TypeMirror returnType = ((ExecutableElement) element).getReturnType();
        List<? extends VariableElement> parameters = ((ExecutableElement) element).getParameters();

        AnnotationMirror annotationMirror = element
                .getAnnotationMirrors()
                .stream()
                .filter(mirror -> mirror.getAnnotationType().toString().equalsIgnoreCase(DocumentedApi.class.getName()))
                .findFirst()
                .orElseThrow();

        String genericClass = annotationMirror
                .getElementValues()
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey().toString(), entry.getValue().toString()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .getOrDefault("classType()", "");

        List<String> genericClassElements = Arrays.asList(genericClass.split("\\."));

        String genericClassName = genericClassElements.get(genericClassElements.size() - 2);

        return MethodData
                .builder()
                .modifiers(modifiers)
                .returnType(returnType)
                .returnTypeClass(genericClassName)
                .methodName(methodName)
                .parameters(parameters)
                .methodInfo(element.getAnnotation(Operation.class))
                .build();
    }
}
