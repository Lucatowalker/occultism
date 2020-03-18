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

package com.github.klikli_dev.occultism.common.ritual.pentacle;

import com.github.klikli_dev.occultism.common.block.CandleBlock;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.Blocks;
import vazkii.patchouli.api.IMultiblock;

import java.util.Arrays;

public class CraftAfritPentacle extends Pentacle {

    //region Fields
    private final String[][] pattern = new String[][]{
            {
                    "    XNX    ",
                    "    GGG    ",
                    "  XGCPCGX  ",
                    "  G WCW G  ",
                    "XGCWW WWCGX",
                    "ZGPC 0 CPGZ",
                    "XGCWW WWCGX",
                    "  G WCW G  ",
                    "  XGCPCGX  ",
                    "    GGG    ",
                    "    XZX    "
            }
    };

    //endregion Fields

    //region Overrides

    @Override
    protected void setupMapping() {
        super.setupMapping();
        this.mapping.addAll(Arrays.asList(
                'S', this.api.looseBlockMatcher(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()),
                'Z', this.api.looseBlockMatcher(Blocks.SKELETON_SKULL),
                'N', this.api.looseBlockMatcher(Blocks.WITHER_SKELETON_SKULL),
                'C', this.api.predicateMatcher(OccultismBlocks.CANDLE_WHITE.get(), b -> b.getBlock() instanceof CandleBlock)
        ));
    }

    @Override
    protected IMultiblock setupMultiblock() {
        return this.api.makeMultiblock(this.pattern, this.mapping.toArray()).setSymmetrical(false);
    }
    //endregion Overrides
}
