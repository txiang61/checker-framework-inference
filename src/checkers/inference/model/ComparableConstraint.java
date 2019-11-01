package checkers.inference.model;

import java.util.Arrays;

import org.checkerframework.framework.type.QualifierHierarchy;
import org.checkerframework.javacutil.BugInCF;

import com.sun.source.tree.Tree.Kind;

/**
 * Represents a constraint that two slots must be comparable.
 *
 */
public class ComparableConstraint extends Constraint implements BinaryConstraint {

    private final ComparableOperationKind operation;
    private final Slot first;
    private final Slot second;
    
    public enum ComparableOperationKind {
    	EQUAL_TO("=="),
    	NOT_EQUAL_TO("!="),
    	GREATER_THAN(">"),
    	GREATER_THAN_EQUAL(">="),
    	LESS_THAN("<"),
    	LESS_THAN_EQUAL("<=");

        // stores the symbol of the operation
        private final String opSymbol;

        private ComparableOperationKind(String opSymbol) {
            this.opSymbol = opSymbol;
        }

        public static ComparableOperationKind fromTreeKind(Kind kind) {
            switch (kind) {
                case EQUAL_TO:
                	return EQUAL_TO;
                case NOT_EQUAL_TO:
                	return NOT_EQUAL_TO;
                case GREATER_THAN:
                	return GREATER_THAN;
                case GREATER_THAN_EQUAL:
                	return GREATER_THAN_EQUAL;
                case LESS_THAN:
                	return LESS_THAN;
                case LESS_THAN_EQUAL:
                	return LESS_THAN_EQUAL;
                default:
                    throw new BugInCF("There are no defined ComparableOperationKind "
                            + "for the given com.sun.source.tree.Tree.Kind: " + kind);
            }
        }

        public String getSymbol() {
            return opSymbol;
        }
    }
    
    private ComparableConstraint(Slot first, Slot second, AnnotationLocation location) {
        super(Arrays.asList(first, second), location);
        this.first = first;
        this.second = second;
        this.operation = null;
    }
    
    private ComparableConstraint(Slot first, Slot second) {
        super(Arrays.asList(first, second));
        this.first = first;
        this.second = second;
        this.operation = null;
    }

    private ComparableConstraint(ComparableOperationKind operation, Slot first, Slot second, AnnotationLocation location) {
        super(Arrays.asList(first, second), location);
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    private ComparableConstraint(ComparableOperationKind operation, Slot first, Slot second) {
        super(Arrays.asList(first, second));
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    protected static Constraint create(Slot first, Slot second, AnnotationLocation location,
            QualifierHierarchy realQualHierarchy) {
        if (first == null || second == null) {
            throw new BugInCF("Create comparable constraint with null argument. Subtype: "
                    + first + " Supertype: " + second);
        }

        // Normalization cases:
        // C1 <~> C2 => TRUE/FALSE depending on relationship
        // V <~> V => TRUE (every type is always comparable to itself)
        // otherwise => CREATE_REAL_COMPARABLE_CONSTRAINT

        // C1 <~> C2 => TRUE/FALSE depending on relationship
        if (first instanceof ConstantSlot && second instanceof ConstantSlot) {
            ConstantSlot firstConst = (ConstantSlot) first;
            ConstantSlot secondConst = (ConstantSlot) second;

            return realQualHierarchy.isSubtype(firstConst.getValue(), secondConst.getValue())
                    || realQualHierarchy.isSubtype(secondConst.getValue(), firstConst.getValue())
                            ? AlwaysTrueConstraint.create()
                            : AlwaysFalseConstraint.create();
        }

        // V <~> V => TRUE (every type is always comparable to itself)
        if (first == second) {
            return AlwaysTrueConstraint.create();
        }

        // otherwise => CREATE_REAL_COMPARABLE_CONSTRAINT
        return new ComparableConstraint(first, second, location);
    }
    
    protected static Constraint create(ComparableOperationKind operation, Slot first, Slot second, AnnotationLocation location, QualifierHierarchy realQualHierarchy) {
        if (operation == null || first == null || second == null) {
            throw new BugInCF("Create comparable constraint with null argument. "
                    + "Operation: " + operation + " Subtype: "
                    + first + " Supertype: " + second);
        }
        if (location == null || location.getKind() == AnnotationLocation.Kind.MISSING) {
            throw new BugInCF(
                    "Cannot create an ArithmeticConstraint with a missing annotation location.");
        }

        // otherwise => CREATE_REAL_COMPARABLE_CONSTRAINT
        return new ComparableConstraint(operation, first, second, location);
    }

    @Override
    public <S, T> T serialize(Serializer<S, T> serializer) {
        return serializer.serialize(this);
    }

    public ComparableOperationKind getOperation() {
        return operation;
    }

    @Override
    public Slot getFirst() {
        return first;
    }

    @Override
    public Slot getSecond() {
        return second;
    }

    @Override
    public Constraint make(Slot first, Slot second) {
        return new ComparableConstraint(first, second);
    }

    @Override
    public int hashCode() {
        int code = 1;
        code = code + ((first == null) ? 0 : first.hashCode());
        code = code + ((second == null) ? 0 : second.hashCode());
        code = code + ((operation == null) ? 0 : operation.hashCode());
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComparableConstraint other = (ComparableConstraint) obj;
        if ((first.equals(other.first) && second.equals(other.second))
                || (first.equals(other.second) && (second.equals(other.first)))) {
            return true;
        } else {
            return false;
        }
    }
}