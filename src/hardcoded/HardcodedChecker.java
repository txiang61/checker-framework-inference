package hardcoded;

import org.checkerframework.framework.qual.TypeQualifiers;
import org.checkerframework.javacutil.AnnotationUtils;

import hardcoded.quals.MaybeHardcoded;
import hardcoded.quals.NotHardcoded;
import hardcoded.quals.PolyHardcoded;

import javax.lang.model.util.Elements;

import trusted.TrustedChecker;

import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;

@TypeQualifiers({ NotHardcoded.class, MaybeHardcoded.class, PolyHardcoded.class })
public class HardcodedChecker extends TrustedChecker {

    @Override
    public boolean isConstant(Tree node) {
        return (node instanceof LiteralTree);
    }

    @Override
    protected void setAnnotations() {
        final Elements elements = processingEnv.getElementUtils();      //TODO: Makes you think a utils is being returned

        UNTRUSTED = AnnotationUtils.fromClass(elements, MaybeHardcoded.class);
        TRUSTED   = AnnotationUtils.fromClass(elements, NotHardcoded.class);
    }
}