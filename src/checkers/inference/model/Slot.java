package checkers.inference.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Slots represent logical variables over which Constraints are generated.
 *
 * Each slot is attached to a code location that can hold an annotation OR has an intrinsic meaning
 * within type-systems. E.g: an int literal is always NonNull but can't hold an annotation,
 * nonetheless, we generate a ConstantSlot representing the literal.
 */
public abstract class Slot implements Comparable<Slot> {
    /**
     * Uniquely identifies this Slot.  id's are monotonically increasing in value by the order they
     * are generated
     */
    protected final int id;

    /**
     * Slots this variable has been merged to.
     * TODO: Move this to {@link VariableSlot}
     */
    private final Set<LubVariableSlot> mergedToSlots = new HashSet<>();

    public Slot(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract Kind getKind();

    public abstract boolean isInsertable();

    public abstract boolean isVariable();

    public abstract <S, T> S serialize(Serializer<S, T> serializer);

    public Set<LubVariableSlot> getMergedToSlots() {
        return Collections.unmodifiableSet(mergedToSlots);
    }

    public void addMergedToSlot(LubVariableSlot mergedSlot) {
        this.mergedToSlots.add(mergedSlot);
    }

    public boolean isMergedTo(Slot other) {
        for (LubVariableSlot mergedTo: mergedToSlots) {
            if (mergedTo.equals(other)) {
                return true;
            } else {
                if (mergedTo.isMergedTo(other)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int compareTo(Slot other) {
        return Integer.compare(id, other.id);
    }

    public enum Kind {
        VARIABLE,
        CONSTANT,
        REFINEMENT_VARIABLE,
        EXISTENTIAL_VARIABLE,
        COMB_VARIABLE,
        ARITHMETIC_VARIABLE,
        LUB_VARIABLE
    }
}
