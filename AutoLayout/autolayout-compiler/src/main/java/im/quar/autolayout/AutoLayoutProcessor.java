package im.quar.autolayout;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;

/**
 * Created by DTHeaven on 15/12/14.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AutoLayoutProcessor extends AbstractProcessor {

    private static final String VIEW_GROUP = "android.view.ViewGroup";
    private static final String PACKAGE_NAME = "im.quar.autolayout";
    private static final String VIEW_CREATOR_IMPL = "ExtViewCreator";

    private static final ClassName VIEW_CREATOR_TYPE = ClassName.get("im.quar.autolayout", "ViewCreator");
    private static final ClassName CONTEXT_TYPE = ClassName.get("android.content", "Context");
    private static final ClassName ATTRIBUTE_SET_TYPE = ClassName.get("android.util", "AttributeSet");
    private static final ClassName VIEW_TYPE = ClassName.get("android.view", "View");

    //AutoLayoutSimple
    private static final ClassName AUTO_LAYOUT_HELPER_TYPE = ClassName.get("im.quar.autolayout.utils", "AutoLayoutHelper");
    private static final ClassName AUTO_LAYOUT_PARAMS_TYPE = ClassName.bestGuess("im.quar.autolayout.utils.AutoLayoutHelper.AutoLayoutParams");
    private static final ClassName AUTO_LAYOUT_INFO_TYPE = ClassName.get("im.quar.autolayout", "AutoLayoutInfo");
    private static final ClassName VIEW_GROUP_LAYOUT_PARAMS_TYPE = ClassName.bestGuess("android.view.ViewGroup.LayoutParams");
    private static final ClassName MARGIN_LAYOUT_PARAMS_TYPE = ClassName.get("android.view.ViewGroup", "MarginLayoutParams");
    private static final String AUTO_LAYOUT_CLASS_SUFFIX = "$$AutoLayout";

    private Messager mMessager;

    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();

        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(AutoLayoutSimple.class.getCanonicalName());
        types.add(AutoLayout.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        List<ParsedInfo> infos = new ArrayList<>();

        for (Element element : roundEnv.getElementsAnnotatedWith(AutoLayoutSimple.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            parseElement(element, infos, AutoLayoutSimple.class);
        }

        exportAutoLayout(infos);

        for (Element element : roundEnv.getElementsAnnotatedWith(AutoLayout.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            parseElement(element, infos, AutoLayout.class);
        }

        exportToFile(infos);

        return true;
    }

    private void parseElement(Element element, List<ParsedInfo> infos, Class<? extends Annotation> annotationClass) {
        if (isBindingInWrongPackage(annotationClass, element)) {
            return;
        }

        TypeElement enclosingElement = (TypeElement) element;

        // Verify that the target type extends from ViewGroup.
        TypeMirror elementType = element.asType();
        if (elementType.getKind() == TypeKind.TYPEVAR) {
            TypeVariable typeVariable = (TypeVariable) elementType;
            elementType = typeVariable.getUpperBound();
        }

        if (!isSubtypeOfType(elementType, VIEW_GROUP)) {
            error(element, "@%s fields must extend from ViewGroup. (%s)",
                    annotationClass.getSimpleName(), enclosingElement.getQualifiedName());
        }

        String name = element.getSimpleName().toString();
        String canonicalName = enclosingElement.getQualifiedName().toString();

        String superCanonicalName = ((TypeElement) element).getSuperclass().toString();
        String superName = superCanonicalName.substring(superCanonicalName.lastIndexOf('.') + 1);

        if (superCanonicalName.startsWith("android.widget.")) {
            superCanonicalName = superCanonicalName.substring(superCanonicalName.lastIndexOf('.') + 1);
        }

        ParsedInfo info = new ParsedInfo();
        info.name = name;
        info.canonicalName = canonicalName;
        info.superName = superName;
        info.superCanonicalName = superCanonicalName;
        infos.add(info);
    }

    private void exportAutoLayout(List<ParsedInfo> infos) {
        for (ParsedInfo info : infos) {

            FieldSpec.Builder layoutParamsFieldBuilder = FieldSpec.builder(AUTO_LAYOUT_INFO_TYPE, "mAutoLayoutInfo", Modifier.PRIVATE);
            MethodSpec.Builder layoutParamsConstructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(CONTEXT_TYPE, "context")
                    .addParameter(ATTRIBUTE_SET_TYPE, "attrs")
                    .addStatement("super(context, attrs)")
                    .addStatement("mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(context, attrs)")
                    .addStatement("mAutoLayoutInfo.fillAttrs(this)");

            MethodSpec.Builder layoutParamsConstructor2Builder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(int.class, "width")
                    .addParameter(int.class, "height")
                    .addStatement("super(width, height)");

            MethodSpec.Builder layoutParamsConstructor3Builder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(VIEW_GROUP_LAYOUT_PARAMS_TYPE, "source")
                    .addStatement("super(source)");

            MethodSpec.Builder layoutParamsConstructor4Builder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(MARGIN_LAYOUT_PARAMS_TYPE, "source")
                    .addStatement("super(source)");

            MethodSpec.Builder getAutoLayoutInfoMethodBuilder = MethodSpec.methodBuilder("getAutoLayoutInfo")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(AUTO_LAYOUT_INFO_TYPE)
                    .addStatement("return mAutoLayoutInfo");

            String exportClassName = info.name + AUTO_LAYOUT_CLASS_SUFFIX;
            ClassName layoutParamsType = ClassName.bestGuess(PACKAGE_NAME + "." + exportClassName + ".LayoutParams");
            TypeSpec.Builder layoutParamsTypeBuilder = TypeSpec.classBuilder(layoutParamsType.simpleName())
                    .addModifiers(Modifier.PUBLIC).addModifiers(Modifier.STATIC)
                    .superclass(ClassName.bestGuess(info.canonicalName + ".LayoutParams"))
                    .addSuperinterface(AUTO_LAYOUT_PARAMS_TYPE)
                    .addField(layoutParamsFieldBuilder.build())
                    .addMethod(layoutParamsConstructorBuilder.build())
                    .addMethod(layoutParamsConstructor2Builder.build())
                    .addMethod(layoutParamsConstructor3Builder.build())
                    .addMethod(layoutParamsConstructor4Builder.build())
                    .addMethod(getAutoLayoutInfoMethodBuilder.build());


            FieldSpec.Builder filedBuilder = FieldSpec.builder(AUTO_LAYOUT_HELPER_TYPE, "mHelper", Modifier.PRIVATE)
                    .initializer("new $T(this)", AUTO_LAYOUT_HELPER_TYPE);

            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(CONTEXT_TYPE, "context")
                    .addParameter(ATTRIBUTE_SET_TYPE, "attrs")
                    .addStatement("super(context, attrs)");

            MethodSpec.Builder onMeasureBuilder = MethodSpec.methodBuilder("onMeasure")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .addParameter(int.class, "widthMeasureSpec")
                    .addParameter(int.class, "heightMeasureSpec")
                    .addStatement("if (!isInEditMode()) mHelper.adjustChildren()")
                    .addStatement("super.onMeasure(widthMeasureSpec, heightMeasureSpec)")
                    .addStatement("if (!isInEditMode()) mHelper.applyAspectRatio()");

            MethodSpec.Builder generateLayoutParamsBuilder = MethodSpec.methodBuilder("generateLayoutParams")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ATTRIBUTE_SET_TYPE, "attrs")
                    .returns(layoutParamsType)
                    .addStatement("return new $T(getContext(), attrs)", layoutParamsType);

            TypeSpec.Builder result = TypeSpec.classBuilder(exportClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(ClassName.bestGuess(info.canonicalName))
                    .addField(filedBuilder.build())
                    .addMethod(constructorBuilder.build())
                    .addMethod(onMeasureBuilder.build())
                    .addMethod(generateLayoutParamsBuilder.build())
                    .addType(layoutParamsTypeBuilder.build());

            try {
                JavaFile.builder(PACKAGE_NAME, result.build())
                        .addFileComment("Generated code from AutoLayout. Do not modify!")
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                error("Unable to write %s.", result.getClass().getCanonicalName());
            }

            info.name = exportClassName;
            info.canonicalName = PACKAGE_NAME + "." + exportClassName;
        }
    }

    private void exportToFile(List<ParsedInfo> infos) {
        if (!infos.isEmpty()) {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("create")
                    .addAnnotation(Override.class)
                    .addModifiers(PUBLIC)
                    .addParameter(String.class, "name")
                    .addParameter(CONTEXT_TYPE, "context")
                    .addParameter(ATTRIBUTE_SET_TYPE, "attrs")
                    .returns(VIEW_TYPE)
                    .beginControlFlow("switch (name)");

            for (ParsedInfo info : infos) {
                if (info.superCanonicalName.startsWith("android.widget.")) {//Standard android layout.
                    info.superCanonicalName = info.superCanonicalName.substring(info.superCanonicalName.lastIndexOf('.') + 1);
                }

                ClassName className = ClassName.bestGuess(info.canonicalName);
                methodBuilder.addStatement("case $S: return new $T(context, attrs)", info.superCanonicalName, className);
            }
            methodBuilder.endControlFlow();
            methodBuilder.addStatement("return null");

            TypeSpec.Builder result = TypeSpec.classBuilder(VIEW_CREATOR_IMPL)
                    .addSuperinterface(VIEW_CREATOR_TYPE)
                    .addModifiers(PUBLIC)
                    .addMethod(methodBuilder.build());

            try {
                JavaFile.builder(PACKAGE_NAME, result.build())
                        .addFileComment("Generated code from AutoLayout. Do not modify!")
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
                error("Unable to write %s.", VIEW_CREATOR_IMPL);
            }
        }
    }


    private boolean isBindingInWrongPackage(Class<? extends Annotation> annotationClass,
                                            Element element) {
        TypeElement enclosingElement = (TypeElement) element;
        String qualifiedName = enclosingElement.getQualifiedName().toString();

        if (qualifiedName.startsWith("android.")) {
            error(element, "@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return true;
        }
        if (qualifiedName.startsWith("java.")) {
            error(element, "@%s-annotated class incorrectly in Java framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return true;
        }

        return false;
    }

    private boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {
        if (otherType.equals(typeMirror.toString())) {
            return true;
        }
        if (typeMirror.getKind() != TypeKind.DECLARED) {
            return false;
        }
        DeclaredType declaredType = (DeclaredType) typeMirror;
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (typeArguments.size() > 0) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            if (typeString.toString().equals(otherType)) {
                return true;
            }
        }
        Element element = declaredType.asElement();
        if (!(element instanceof TypeElement)) {
            return false;
        }
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {
            return true;
        }
        for (TypeMirror interfaceType : typeElement.getInterfaces()) {
            if (isSubtypeOfType(interfaceType, otherType)) {
                return true;
            }
        }
        return false;
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private void info(String s) {
        mMessager.printMessage(NOTE, s);
    }

    private void error(String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mMessager.printMessage(ERROR, message);
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        mMessager.printMessage(ERROR, message, element);
    }

    static class ParsedInfo {
        String name;
        String canonicalName;

        String superName;
        String superCanonicalName;
    }
}
