package checkers.inference.solver.backend.encoder;

import checkers.inference.model.ComparableConstraint.ComparableOperationKind;
import checkers.inference.solver.backend.encoder.binary.BinaryConstraintEncoder;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.VariableSlot;

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
            VariableSlot left, VariableSlot right);

    ConstraintEncodingT encodeVariable_Constant(ComparableOperationKind operation,
            VariableSlot left, ConstantSlot right);

    ConstraintEncodingT encodeConstant_Variable(ComparableOperationKind operation,
            ConstantSlot left, VariableSlot right);

    ConstraintEncodingT encodeConstant_Constant(ComparableOperationKind operation,
            ConstantSlot left, ConstantSlot right);
}
