package Fox.core.lib.general.utils;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorts
{
    /**
     * Sorting elems with comparator
     * @param elems list for sorting
     * @param c comparator
     * @param isForward is true, the least elements at start, the most at the end. Else reversing sorting.
     * @param <T> type of elements
     * @throws IllegalArgumentException if list of comparator is null
     */
    @Contract("null, _, _ -> fail; !null, null, _ -> fail")
    public static <T> void Sort(List<T> elems, Comparator<? super T> c, boolean isForward)
            throws IllegalArgumentException
    {
        if (elems == null || c == null)
            throw new IllegalArgumentException();

        int left = 0;
        T buff1;
        int right = elems.size() - 1;
        do
        {
            for (int i = left; i < right; i++)
            {
                buff1 = elems.get(i);
                int compare = c.compare(buff1, elems.get(i + 1));

                if ((!isForward && compare < 0)
                        || (isForward && compare > 0))
                {
                    elems.remove(i);
                    elems.add(i + 1, buff1);
                }
            }
            right--;
            for (int i = right; i > left; i--)
            {
                buff1 = elems.get(i);
                int compare = c.compare(buff1, elems.get(i - 1));

                if ((!isForward && compare > 0)
                        || (isForward && compare < 0))
                {
                    elems.remove(i);
                    elems.add(i - 1, buff1);
                }
            }
            left++;
        }
        while (left < right);
    }

    /**
     * Sorting elems in results from Relativator for single element
     * @param elems list for sorting
     * @param r Relativator, which sorts the list
     * @param Relativator param for sorting relativator
     * @param isForward if true sorting from the least relative value to the most. Else reversing sorting.
     * @param <T> type of elements
     * @param <R> type relativator base
     * @param <E> type of relativator value
     * @throws IllegalArgumentException if list or comparator is null
     */
    @Contract("null, _, _, _ -> fail; !null, null, _, _ -> fail")
    public static <T, R, E extends Number> void RelativeSort(List<T> elems,
                                                                Relativator<? super T, R, E> r,
                                                                R Relativator,
                                                                boolean isForward)
            throws IllegalArgumentException
    {
        if (elems == null || r == null)
            throw new IllegalArgumentException();

        if (elems.isEmpty())
            return ;

        List<T> temp = new ArrayList<>();

        for(T elem : elems)
        {
            int size = temp.size();
            long insertHash = r.RelativeCompare(elem, Relativator).longValue();

            if (size == 0)
            {
                temp.add(elem);
                continue;
            }

            if (size == 1)
            {
                long longValue = r.RelativeCompare(temp.get(0), Relativator).longValue();

                if ((isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
                    temp.add(0, elem);
                else
                    temp.add(elem);

                continue;
            }

            long longValue = r.RelativeCompare(temp.get(0), Relativator).longValue();

            if ((isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
            {
                temp.add(0, elem);
                continue;
            }

            longValue = r.RelativeCompare(temp.get(size - 1), Relativator).longValue();

            if ((isForward && insertHash > longValue) || (!isForward && insertHash < longValue))
            {
                temp.add(elem);
                continue;
            }

            for(int i = size/2; i >= 1; )
            {
                longValue = r.RelativeCompare(temp.get(i), Relativator).longValue();
                if (insertHash == longValue)
                {
                    temp.add(i, elem);
                    break;
                }
                if (i == 1 && (isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
                {
                    temp.add(i, elem);
                    break;
                }
                if (i == 1 && (isForward && insertHash > longValue) || (!isForward && insertHash < longValue))
                {
                    temp.add(i + 1, elem);
                    break;
                }
                if ((isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
                    i = i - i/2;
                if ((isForward && insertHash > longValue) || (!isForward && insertHash < longValue))
                    i = i + i/2;
            }
        }

        elems.clear();
        elems.addAll(temp);
    }
}
