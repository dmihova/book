package com.tinqin.library.book.documentationexporter.formatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class MethodData {

    @Builder.Default
    private final Set<Modifier> modifiers = Collections.emptySet();
    private final TypeMirror returnType;
    private final String returnTypeClass;
    private final String methodName;
    @Builder.Default
    private final List<? extends VariableElement> parameters = Collections.emptyList();
    private final Annotation methodInfo;

}
