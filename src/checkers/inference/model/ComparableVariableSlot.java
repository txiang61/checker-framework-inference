package checkers.inference.model;

import checkers.inference.model.Slot.Kind;

/**
 * ComparableVariableSlot represent the result of an comparable operation between two other
 * {@link VariableSlot}s. Note that this slot is serialized identically to a {@link VariableSlot}.
 */
public class ComparableVariableSlot extends VariableSlot {

	public ComparableVariableSlot(AnnotationLocation location, int id) {
		super(location, id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Kind getKind() {
        return Kind.COMPARABLE_VARIABLE;
    }

    @Override
    public <S, T> S serialize(Serializer<S, T> serializer) {
        return serializer.serialize(this);
    }

    /**
     * ComparableVariables should never be re-inserted into the source code.
     *
     * @return false
     */
    @Override
    public boolean isInsertable() {
        return false;
    }

}
