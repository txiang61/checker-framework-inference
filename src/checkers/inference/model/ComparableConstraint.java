package checkers.inference.model;

import java.util.Arrays;

import org.checkerframework.framework.type.QualifierHierarchy;
import org.checkerframework.javacutil.BugInCF;

import com.sun.source.tree.Tree.Kind;

/**
 * Represents a constraint that two slots must be comparable.
 *
 */
public class ComparableConstraint extends Constraint {

    private final ComparableOperationKind operation;
    private final Slot left;
    private final Slot right;
    
    public enum ComparableOperationKind {
    	REFERENCE(""),
    	EQUAL_TO("=="),
    	NOT_EQUAL_TO("!="),
    	GREATER_THAN(">"),
    	GREATER_THAN_EQUAL(">="),
    	LESS_THAN("<"),
    	LESS_THAN_EQUAL("<="),
    	OTHER("?");

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
                case INSTANCE_OF:
                case TYPE_CAST:
                	return REFERENCE;
                default:
                	// TODO: Handle all cases and throw error on unsupported operations
                    return OTHER;
            }
        }

        public String getSymbol() {
            return opSymbol;
        }
    }

    private ComparableConstraint(ComparableOperationKind operation, Slot left, Slot right,
            AnnotationLocation location) {
        super(Arrays.asList(left, right), location);
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    private ComparableConstraint(ComparableOperationKind operation, Slot left, Slot right) {
        super(Arrays.asList(left, right));
        this.left = left;
        this.right = right;
        this.operation = operation;
    }
    
    protected static Constraint create(ComparableOperationKind operation, Slot left, Slot right,
    		AnnotationLocation location, QualifierHierarchy realQualHierarchy) {
        if (operation == null || left == null || right == null) {
            throw new BugInCF("Create comparable constraint with null argument. "
                    + "Operation: " + operation + " Subtype: "
                    + left + " Supertype: " + right);
        }

        // Normalization cases:
        // C1 <~> C2 => TRUE/FALSE depending on relationship
        // V <~> V => TRUE (every type is always comparable to itself)
        // otherwise => CREATE_REAL_COMPARABLE_CONSTRAINT

        // C1 <~> C2 => TRUE/FALSE depending on relationship
        if (left instanceof ConstantSlot && right instanceof ConstantSlot 
        		&& operation == ComparableOperationKind.REFERENCE) {
            ConstantSlot leftConst = (ConstantSlot) left;
            ConstantSlot rightConst = (ConstantSlot) right;

            return realQualHierarchy.isSubtype(leftConst.getValue(), rightConst.getValue())
                    || realQualHierarchy.isSubtype(rightConst.getValue(), leftConst.getValue())
                            ? AlwaysTrueConstraint.create()
                            : AlwaysFalseConstraint.create();
        }
        
        // V <~> V => TRUE (every type is always comparable to itself)
        if (left == right) {
            return AlwaysTrueConstraint.create();
        }

        // otherwise => CREATE_REAL_COMPARABLE_CONSTRAINT
        return new ComparableConstraint(operation, left, right, location);
    }

    @Override
    public <S, T> T serialize(Serializer<S, T> serializer) {
        return serializer.serialize(this);
    }

    public ComparableOperationKind getOperation() {
        return operation;
    }

    public Slot getLeft() {
        return left;
    }

    public Slot getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        int code = 1;
        code = code + ((left == null) ? 0 : left.hashCode());
        code = code + ((right == null) ? 0 : right.hashCode());
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
        if (left.equals(other.left) && right.equals(other.right) 
        		&& operation.equals(other.operation)) {
            return true;
        } else {
            return false;
        }
    }
}