package checkers.inference.solver.backend.encoder;

import checkers.inference.model.ComparableConstraint.ComparableOperationKind;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.Slot;

/**
 * A marker interface that all constraint encoders that support encoding {@link checkers.inference.model.ComparableConstraint}
 * should implement. Otherwise, the encoder will be considered not supporting encoding comparable
 * constraint and rejected by the {@link AbstractConstraintEncoderFactory#createComparableConstraintEncoder()}
 *
 * @see checkers.inference.model.ComparableConstraint
 * @see AbstractConstraintEncoderFactory#createComparableConstraintEncoder()
 */
public interface ComparableConstraintEncoder<ConstraintEncodingT> {
	ConstraintEncodingT encodeVariable_Variable(ComparableOperationKind operation,
            Slot left, Slot right);

    ConstraintEncodingT encodeVariable_Constant(ComparableOperationKind operation,
    		Slot left, ConstantSlot right);

    ConstraintEncodingT encodeConstant_Variable(ComparableOperationKind operation,
            ConstantSlot left, Slot right);

    ConstraintEncodingT encodeConstant_Constant(ComparableOperationKind operation,
            ConstantSlot left, ConstantSlot right);
}
