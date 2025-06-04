package de.larsensmods.stl_backport.fabriclike;

import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public final class SpringToLifeModFabricLike {

    public static void init(IRegistrationProvider registrationProvider) {
        SpringToLifeMod.init(registrationProvider, FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT));
    }

}
