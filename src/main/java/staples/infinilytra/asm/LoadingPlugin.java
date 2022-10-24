package staples.infinilytra.asm;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import staples.infinilytra.asm.transformer.EntityLivingBaseTransformer;

@MCVersion("1.12.2")
@Name("Disable Elyctra Degradation")
@TransformerExclusions({ "staples.infinilytra.asm" })
@SortingIndex(9001)
public class LoadingPlugin implements IFMLLoadingPlugin
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { EntityLivingBaseTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map data)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

}
