/*
 * MIT License
 *
 * Copyright 2020 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.registry;

import com.github.klikli_dev.occultism.Occultism;
import com.github.klikli_dev.occultism.common.blockentity.DimensionalMineshaftBlockEntity;
import com.github.klikli_dev.occultism.common.blockentity.StableWormholeBlockEntity;
import com.github.klikli_dev.occultism.common.blockentity.StorageControllerBlockEntity;
import com.github.klikli_dev.occultism.common.container.DimensionalMineshaftContainer;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritContainer;
import com.github.klikli_dev.occultism.common.container.spirit.SpiritTransporterContainer;
import com.github.klikli_dev.occultism.common.container.storage.SatchelContainer;
import com.github.klikli_dev.occultism.common.container.storage.StableWormholeContainer;
import com.github.klikli_dev.occultism.common.container.storage.StorageControllerContainer;
import com.github.klikli_dev.occultism.common.container.storage.StorageRemoteContainer;
import com.github.klikli_dev.occultism.common.entity.spirit.SpiritEntity;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismContainers {
    //region Fields
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(
            ForgeRegistries.CONTAINERS, Occultism.MODID);

    public static final RegistryObject<MenuType<StorageControllerContainer>> STORAGE_CONTROLLER =
            CONTAINERS.register("storage_controller",
                    () -> IForgeMenuType
                            .create((windowId, inv, data) -> new StorageControllerContainer(windowId, inv,
                                    (StorageControllerBlockEntity) inv.player.level
                                            .getBlockEntity(data.readBlockPos()))));

    public static final RegistryObject<MenuType<StableWormholeContainer>> STABLE_WORMHOLE =
            CONTAINERS.register("stable_wormhole",
                    () -> IForgeMenuType
                            .create((windowId, inv, data) -> new StableWormholeContainer(windowId, inv,
                                    (StableWormholeBlockEntity) inv.player.level
                                            .getBlockEntity(data.readBlockPos()))));

    public static final RegistryObject<MenuType<StorageRemoteContainer>> STORAGE_REMOTE =
            CONTAINERS.register("storage_remote",
                    () -> IForgeMenuType
                            .create((windowId, inv, data) -> new StorageRemoteContainer(windowId, inv, data.readVarInt())));

    public static final RegistryObject<MenuType<SpiritContainer>> SPIRIT =
            CONTAINERS.register("spirit",
                    () -> IForgeMenuType
                            .create((windowId, inv, data) -> {
                                return new SpiritContainer(windowId, inv,
                                        (SpiritEntity) inv.player.level.getEntity(data.readInt()));
                            }));

    public static final RegistryObject<MenuType<SpiritTransporterContainer>> SPIRIT_TRANSPORTER =
            CONTAINERS.register("spirit_transporter",
                    () -> IForgeMenuType
                            .create((windowId, inv, data) -> {
                                return new SpiritTransporterContainer(windowId, inv,
                                        (SpiritEntity) inv.player.level.getEntity(data.readInt()));
                            }));

    public static final RegistryObject<MenuType<DimensionalMineshaftContainer>> OTHERWORLD_MINER =
            CONTAINERS.register("otherworld_miner",
                    () -> IForgeMenuType
                            .create((windowId, inv, data) -> new DimensionalMineshaftContainer(windowId, inv,
                                    (DimensionalMineshaftBlockEntity) inv.player.level.getBlockEntity(
                                            data.readBlockPos()))));

    public static final RegistryObject<MenuType<SatchelContainer>> SATCHEL =
            CONTAINERS.register("satchel", () -> IForgeMenuType.create(SatchelContainer::createClientContainer));

    //endregion Fields
}
