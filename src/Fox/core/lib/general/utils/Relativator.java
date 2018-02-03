package Fox.core.lib.general.utils;

public interface Relativator<T, R, E extends Number>
{
    E RelativeCompare(T o1, R o2) throws IllegalArgumentException;
}
