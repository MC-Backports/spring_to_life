package de.larsensmods.stl_backport.quilt.block;

import de.larsensmods.stl_backport.audio.STLSoundTypes;
import de.larsensmods.stl_backport.block.STLLeafLitterBlock;

public class STLLeafLitterBlockQuilt extends STLLeafLitterBlock {

    public STLLeafLitterBlockQuilt(Properties properties) {
        super(properties.sound(STLSoundTypes.LEAF_LITTER));
    }
}
