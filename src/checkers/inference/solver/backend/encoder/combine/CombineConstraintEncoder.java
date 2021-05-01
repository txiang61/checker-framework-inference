package checkers.inference.solver.backend.encoder.combine;

import checkers.inference.model.ConstantSlot;
import checkers.inference.model.Slot;
import checkers.inference.model.VariableSlot;

/**
 * Interface that defines operations to encode a {@link checkers.inference.model.CombineConstraint}. It has four methods
 * depending on the {@link checkers.inference.solver.backend.encoder.SlotSlotCombo} of {@code target} and {@code
 * declared} slots.
 *
 * <p>
 * {@code result} is always {@link checkers.inference.model.CombVariableSlot}, which is essentially {@link Slot},
 * whose {@link Slot#id} is the only interesting knowledge in encoding phase. Therefore there don't exist
 * methods in which {@code result} is {@link ConstantSlot}.
 *
 * @see checkers.inference.model.CombineConstraint
 * @see checkers.inference.solver.backend.encoder.SlotSlotCombo
 */
public interface CombineConstraintEncoder<ConstraintEncodingT> {

    ConstraintEncodingT encodeVariable_Variable(VariableSlot target, VariableSlot declared, Slot result);

    ConstraintEncodingT encodeVariable_Constant(VariableSlot target, ConstantSlot declared, Slot result);

    ConstraintEncodingT encodeConstant_Variable(ConstantSlot target, VariableSlot declared, Slot result);

    ConstraintEncodingT encodeConstant_Constant(ConstantSlot target, ConstantSlot declared, Slot result);
}
