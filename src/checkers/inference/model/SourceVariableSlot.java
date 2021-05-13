package checkers.inference.model;

import javax.lang.model.type.TypeMirror;

/**
 * SourceVariableSlot is a VariableSlot representing a type use in the source code with undetermined value.
 *
 * After the Solver is run, each SourceVariableSlot should have an assigned value which is then written
 * to the output Jaif file for later reinsertion into the original source code.
 *
 */
public class SourceVariableSlot extends VariableSlot {

    /** Actual type wrapped with this TypeMirror. */
    protected final TypeMirror actualType;

    /**
     * Should this slot be inserted back into the source code.
     * This should be false for types have have an implicit annotation
     * and slots for pre-annotated code.
     */
    private boolean insertable;

    /**
     * @param location Used to locate this variable in code, see @AnnotationLocation
     * @param id      Unique identifier for this variable
     * @param type the underlying type
     * @param insertable indicates whether this slot should be inserted back into the source code
     */
    public SourceVariableSlot(AnnotationLocation location, int id, TypeMirror type, boolean insertable) {
        super(id, location);
        this.actualType = type;
        this.insertable = insertable;
    }

    /**
     * @param type The underlying type of the slot
     * @param id      Unique identifier for this variable
     * @param insertable indicates whether this slot should be inserted back into the source code
     */
    public SourceVariableSlot(int id, TypeMirror type, boolean insertable) {
        super(id);
        this.actualType = type;
    }

    @Override
    public <S, T> S serialize(Serializer<S, T> serializer) {
        return serializer.serialize(this);
    }

    @Override
    public Kind getKind() {
        return Kind.VARIABLE;
    }

    /**
     * Returns the underlying unannotated Java type, which this wraps.
     *
     * @return the underlying type
     */
    public TypeMirror getUnderlyingType() {
        return actualType;
    }

    /**
     * Should this VariableSlot be inserted back into the source code.
     */
    @Override
    public boolean isInsertable() {
        return insertable;
    }

}
