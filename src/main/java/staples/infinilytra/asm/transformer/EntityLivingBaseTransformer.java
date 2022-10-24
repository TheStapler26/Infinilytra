package staples.infinilytra.asm.transformer;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class EntityLivingBaseTransformer implements IClassTransformer, Opcodes
{
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (!transformedName.equals("net.minecraft.entity.EntityLivingBase"))
            return basicClass;

        ClassReader reader = new ClassReader(basicClass);
        ClassNode mc = new ClassNode();
        reader.accept(mc, 0);
        Iterator itr = mc.methods.iterator();
        Iterator<AbstractInsnNode> operationItr;
        AbstractInsnNode operation = null;

        while (itr.hasNext())
        {
            //Find the updateElytra method signature
            MethodNode method = (MethodNode) itr.next();
            if (verifyMethodNode(method, "()V", 2, 3, 3))
            {
                operationItr = method.instructions.iterator();
                while (operationItr.hasNext())
                {
                    //Find the modulus operator instruction
                    operation = operationItr.next();
                    if (operation.getOpcode() == IREM)
                    {
                        //Replace mod with add
                        int index = method.instructions.indexOf(operation) - 1;
                        method.instructions.remove(operation);
                        method.instructions.insert(method.instructions.get(index), new InsnNode(IADD));

                        //Write changes
                        ClassWriter writer = new ClassWriter(0);
                        mc.accept(writer);
                        return writer.toByteArray();
                    }
                }

                break;
            }
        }

        return basicClass;
    }

    /**
     * @param method the MethodNode to evaluate
     * @param desc the MethodNode description
     * @param access the MethodNode access value
     * @param maxLocals the max local variables the method has
     * @param maxStack the max stack the method has
     * @return true if the method matches desired parameters
     */
    private static boolean verifyMethodNode(MethodNode method, String desc, int access, int maxLocals, int maxStack)
    {
        if (method == null)
            return false;
        if (!method.desc.equals(desc))
            return false;
        if (method.access != access)
            return false;
        if (method.maxLocals != maxLocals)
            return false;
        if (method.maxStack != maxStack)
            return false;
        return true;
    }
}
