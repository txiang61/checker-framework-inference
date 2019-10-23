package checkers.inference.solver.backend.encoder.binary;

import checkers.inference.model.ComparableConstraint.ComparableOperationKind;
import checkers.inference.model.ComparableVariableSlot;
import checkers.inference.model.ConstantSlot;
import checkers.inference.model.VariableSlot;
import checkers.inference.solver.backend.encoder.AbstractConstraintEncoderFactory;

/**
 * A marker interface that all constraint encoders that support encoding {@link checkers.inference.model.ComparableConstraint}
 * should implement. Otherwise, the encoder will be considered not supporting encoding comparable
 * constraint and rejected by the {@link AbstractConstraintEncoderFactory#createComparableConstraintEncoder()}
 *
 * @see checkers.inference.model.ComparableConstraint
 * @see AbstractConstraintEncoderFactory#createComparableConstraintEncoder()
 */
public interface ComparableConstraintEncoder<ConstraintEncodingT> extends BinaryConstraintEncoder<ConstraintEncodingT> {
	ConstraintEncodingT encodeVariable_Variable(ComparableOperationKind operation,
            VariableSlot fst, VariableSlot snd, ComparableVariableSlot result);

    ConstraintEncodingT encodeVariable_Constant(ComparableOperationKind operation,
            VariableSlot fst, ConstantSlot snd, ComparableVariableSlot result);

    ConstraintEncodingT encodeConstant_Variable(ComparableOperationKind operation,
            ConstantSlot fst, VariableSlot snd, ComparableVariableSlot result);

    ConstraintEncodingT encodeConstant_Constant(ComparableOperationKind operation,
            ConstantSlot fst, ConstantSlot snd, ComparableVariableSlot result);
}
