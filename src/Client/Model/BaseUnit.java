package Client.Model;

import java.util.Comparator;
import java.util.List;

/**
 * This class has properties of the base unit.
 * Please do not change this class, it is a piece of the internal implementation
 * and you do not need to know anything about this class.
 */

public class BaseUnit {
    private int ap;
    private int typeId;
    private int maxHp;
    private int baseRange;
    private int baseAttack;
    private UnitTarget targetType;
    private boolean isFlying;
    private boolean isMultiple;

    public static void sort(List<BaseUnit> baseUnits)
    {
        baseUnits.sort(new Comparator<BaseUnit>()
        {
            @Override
            public int compare(BaseUnit o1, BaseUnit o2)
            {
                if (o1.getAp() < o2.getAp())
                {
                    return -1;
                }
                else if (o1.getAp() > o2.getAp())
                {
                    return 1;
                }
                return 0;
            }
        });
    }

    public static BaseUnit findBaseUnit(List<BaseUnit> baseUnits, int baseUnitID)
    {
        for (BaseUnit baseUnit : baseUnits)
        {
            if (baseUnit.getTypeId() == baseUnitID)
            {
                return baseUnit;
            }
        }
        return null;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void setBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
    }

    public int getBaseRange() {
        return baseRange;
    }

    public void setBaseRange(int baseRange) {
        this.baseRange = baseRange;
    }

    public UnitTarget getTargetType() {
        return targetType;
    }

    public void setTargetType(UnitTarget targetType) {
        this.targetType = targetType;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }
}
