package checkers.inference.model;


import java.util.HashSet;
import java.util.Set;

/**
 * VariableSlot is a Slot representing an undetermined value (i.e. a variable we are solving for).
 * After the Solver is run, each VariableSlot should have an assigned value which is then written
 * to the output Jaif file for later reinsertion into the original source code.
 *
 * Before the Solver is run, VariableSlots are represented by @VarAnnot( slot id ) annotations
 * on AnnotatedTypeMirrors.  When an AnnotatedTypeMirror is encountered in a position that would
 * generate constraints (e.g. either side of an assignment ), its @VarAnnots are converted into
 * VariableSlots which are then used in the generated constraints.
 *
 * E.g.  @VarAnnot(0) String s;
 * The above example implies that a VariableSlot with id 0 represents the possible annotations
 * on the declaration of s.
 *
 * Variable slot hold references to slots it is refined by.
 *
 */
public abstract class VariableSlot extends Slot {

    /**
     * Used to locate this Slot in source code. ASTRecords are written to Jaif files along with the
     * Annotation determined for this slot by the Solver.
     */
    private AnnotationLocation location;

    /** Refinement variables that refine this slot. */
    private final Set<RefinementVariableSlot> refinedToSlots = new HashSet<>();

    /**
     * Create a Slot with the given annotation location.
     *
     * @param id Unique identifier for this variable
     * @param location an AnnotationLocation for which the slot is attached to
     */
    public VariableSlot(int id, AnnotationLocation location) {
        super(id);
        this.location = location;
    }

    /**
     * Create a slot with a default location of
     * {@link AnnotationLocation#MISSING_LOCATION}.
     *
     * @param id Unique identifier for this variable
     */
    public VariableSlot(int id) {
        this(id, AnnotationLocation.MISSING_LOCATION);
    }

    public AnnotationLocation getLocation() {
        return location;
    }

    public void setLocation(AnnotationLocation location) {
        this.location = location;
    }

    public Set<RefinementVariableSlot> getRefinedToSlots() {
        return refinedToSlots;
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + id + ")";
    }
}
