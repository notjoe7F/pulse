package notjoe.pulse.api.crafting;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractNamedRegistry<T> {
    protected Map<ResourceLocation, T> objects;

    public AbstractNamedRegistry() {
        objects = HashMap.empty();
    }

    public void add(ResourceLocation name, T obj) {
        objects = objects.put(name, obj);
    }

    public Option<T> getByName(ResourceLocation name) {
        return objects.get(name);
    }

    public Option<ResourceLocation> getName(T obj) {
        return objects.find(nameAndRecipe -> nameAndRecipe._2 == obj).map(nameAndRecipe -> nameAndRecipe._1);
    }
}
