package net.minecraftforge.common.model;

import net.minecraft.util.EnumFacing;

public interface ITransformation
{
    net.minecraft.client.renderer.Matrix4f getMatrix();

    EnumFacing rotate(EnumFacing var1);

    int rotate(EnumFacing var1, int var2);
}
