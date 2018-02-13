package Fox.core.lib.general.utils;

import org.jetbrains.annotations.Contract;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.tan;

public class Sorts
{

    /** Simple call of forward sorting.
     * @param elems list of elements.
     * @param comparator Comparator for this class.
     * @param <T> type of elements.
     * @throws IllegalArgumentException if list or comparator is null.
     */
    @Contract("null, _ -> fail; !null, null -> fail")
    public static <T> void ForwardSorting(List<T> elems, Comparator<? super T> comparator)
            throws IllegalArgumentException
    {
        Sort(elems, comparator, true);
    }

    /** Simple call of backward sorting.
     * @param elems list of elements.
     * @param comparator Comparator for this class.
     * @param <T> type of elements.
     * @throws IllegalArgumentException if list or comparator is null.
     */
    @Contract("null, _ -> fail; !null, null -> fail")
    public static <T> void BackwardSorting(List<T> elems, Comparator<? super T> comparator)
            throws IllegalArgumentException
    {
        Sort(elems, comparator, false);
    }

    /**
     * Sorting elems with comparator. For language level 7.
     * @param elems List for sorting
     * @param comparator comparator
     * @param isForward is true, the least elements at start, the most at the end. Else reversing sorting.
     * @param <T> type of elements
     * @throws IllegalArgumentException if list of comparator is null
     */
    @Contract("null, _, _ -> fail; !null, null, _ -> fail")
    private static <T> void Sort(List<T> elems, Comparator<? super T> comparator, boolean isForward)
            throws IllegalArgumentException
    {
        if (elems == null || comparator == null)
            throw new IllegalArgumentException();

        int left = 0;
        T buff1;
        int right = elems.size() - 1;
        do
        {
            for (int i = left; i < right; i++)
            {
                buff1 = elems.get(i);
                int compare = comparator.compare(buff1, elems.get(i + 1));

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
                int compare = comparator.compare(buff1, elems.get(i - 1));

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

    /** Simple call of forward relative sorting.
     * @param elems list of elements.
     * @param Relativator Relativator for this class.
     * @param <T> type of elements.
     * @param <R> type of value for generate relative value.
     * @param <E> type of value that represents relative.
     * @throws IllegalArgumentException if list or Relativator is null.
     */
    @Contract("null, _, _ -> fail; !null, null, _ -> fail")
    public static <T, R, E extends Number> void ForwardRelativeSort(Collection<T> elems,
                                                                    Relativator<? super T, R, E> Relativator,
                                                                    R RelativeValue)
            throws IllegalArgumentException
    {
        RelativeSort(elems, Relativator, RelativeValue, true);
    }

    /** Simple call of backward relative sorting.
     * @param elems list of elements.
     * @param Relativator Relativator for this class.
     * @param <T> type of elements.
     * @param <R> type of value for generate relative value.
     * @param <E> type of value that represents relative.
     * @throws IllegalArgumentException if list or Relativator is null.
     */
    @Contract("null, _, _ -> fail; !null, null, _ -> fail")
    public static <T, R, E extends Number> void BackwardRelativeSort(Collection<T> elems,
                                                                     Relativator<? super T, R, E> Relativator,
                                                                     R RelativeValue)
            throws IllegalArgumentException
    {
        RelativeSort(elems, Relativator, RelativeValue, false);
    }

    /**
     * Sorting elems in results from Relativator for single element. For language level 7.
     * @param elems collection for sorting
     * @param Relativator Relativator, which sorts the list
     * @param RelativeValue param for sorting relativator
     * @param isForward if true sorting from the least relative value to the most. Else reversing sorting.
     * @param <T> type of elements
     * @param <R> type of relativator base
     * @param <E> type of relativator value
     * @throws IllegalArgumentException if list or comparator is null
     */
    @Contract("null, _, _, _ -> fail; !null, null, _, _ -> fail")
    private static <T, R, E extends Number> void RelativeSort(Collection<T> elems,
                                                                Relativator<? super T, R, E> Relativator,
                                                                R RelativeValue,
                                                                boolean isForward)
            throws IllegalArgumentException
    {
        if (elems == null || Relativator == null)
            throw new IllegalArgumentException();

        if (elems.isEmpty())
            return ;


        List<T> temp = new LinkedList<>();

        for(T elem : elems)
        {
            int size = temp.size();
            long insertHash = Relativator.RelativeCompare(elem, RelativeValue).longValue();

            if (size == 0)
            {
                temp.add(elem);
                continue;
            }

            if (size == 1)
            {
                long longValue = Relativator.RelativeCompare(temp.get(0), RelativeValue).longValue();

                if ((isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
                    temp.add(0, elem);
                else
                    temp.add(elem);

                continue;
            }

            long longValue = Relativator.RelativeCompare(temp.get(0), RelativeValue).longValue();

            if ((isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
            {
                temp.add(0, elem);
                continue;
            }

            longValue = Relativator.RelativeCompare(temp.get(size - 1), RelativeValue).longValue();

            if ((isForward && insertHash > longValue) || (!isForward && insertHash < longValue))
            {
                temp.add(elem);
                continue;
            }

            for(int i = size/2, l = 0; ; )
            {
                int det = abs(i - l);
                longValue = Relativator.RelativeCompare(temp.get(i), RelativeValue).longValue();

                if (insertHash == longValue)
                {
                    temp.add(i, elem);
                    break;
                }
                if (det <= 1)
                {
                    if ((isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
                    {
                        temp.add(i, elem);
                        break;
                    }
                    else if ((isForward && insertHash > longValue) || (!isForward && insertHash < longValue))
                    {
                        temp.add(i + 1, elem);
                        break;
                    }
                }
                if ((isForward && insertHash < longValue) || (!isForward && insertHash > longValue))
                    if (l == 0)
                    {
                        i = i - i/2;
                    }
                    else
                    {
                        i = i - det/2;
                    }
                if ((isForward && insertHash > longValue) || (!isForward && insertHash < longValue))
                    if (l == 0)
                    {
                        i = i + i/2;
                    }
                    else
                    {
                        i = i + det/2;
                    }
                l = i;
            }
        }

        elems.clear();
        elems.addAll(temp);
    }

    /** Converting one <T> instance to <R> instance
     * @param source What T you need to convert into R
     * @param <T> type of instance for convert
     * @param <R> type of instance for return
     * @param Converter param that implement converting
     * @return converting instance
     */
    public static <T, R> R Convert(T source, Converter<T, R> Converter)
    {
      return Converter.Convert(source);
    }

    /** Converting list of T into list of R using Converter<<T>, <R>> implementation
     * @param source what list of T you need to convert into list of R
     * @param Converter instance of Converter<<T>, <R>> implementation
     * @param <T> type of source param
     * @param <R> type of return
     * @return converting list of instances
     */
    public static <T, R> List<R> Convert(List<T> source, Converter<T, R> Converter)
    {
        List<R> temp = new ArrayList<>(source.size());
        for(T elem : source)
            temp.add(Convert(elem, Converter));
        return temp;
    }

    /** Relativator is replacing BiFunction<<T>, <R>, <E>> interface for API 1.7 and earlier.
     * @param <T> the type of the first argument to the function
     * @param <R> the type of the second argument to the function
     * @param <E> the type of the result of the function
     */
    public interface Relativator<T, R, E extends Number>
    {
        E RelativeCompare(T o1, R o2) throws IllegalArgumentException;
    }


    /** Interface for Converting method of library
     * @param <T> type of incoming instance
     * @param <R> type of returning instance
     */
    public interface Converter<T, R>
    {
        R Convert(T elem);
    }

    /** Do merging some of instances in the list.
     * @param ElemList list of type <T>
     * @param <T> type of elements. This class should implements Cloneable<T> end extends Merger<<T>, <R>, <E>>.
     * @param <R> type of hash for element of type <T>
     * @param <E> type of additional information for merging list to instance
     * @return instances of merging target instances
     */
    public static <T extends Merger<T, R, E>, R, E> List<T> Merging(final List<T> ElemList)
    {
        //FUTURES debug this
        Map<R, Boolean> SecAssistMap = new HashMap<>();
        List<T> Target = new ArrayList<>();

        for(T elem:ElemList)
        {
            R elemHash = elem.Hash();
            if (SecAssistMap.get(elemHash) == null)
            {
                List<T> similar = elem.Similar(ElemList, elemHash);
                Target.add(elem.Merge(similar, elem.ExtendValue(similar)).clone());
                SecAssistMap.put(elemHash, true);
            }
        }
        return Target;
    }

    /** Abstract class for fast merging, represents in Merging method. Define rules for merging.
     * @param <T> type of elements for merging.
     * @param <R> type of hash for elems.
     * @param <E> type of additional instance for merging.
     */
    public static abstract class Merger<T, R, E> implements Cloneable<T>
    {
        protected abstract R Hash();
        protected abstract T Merge(final List<T> list, E value);
        protected abstract boolean HashEquals(R first, R second);
        protected abstract E ExtendValue(final List<T> elems);

        protected <T extends Merger<T, R, E>> List<T> Similar(final List<T> elems, R hash)
        {
            List<T> result = new ArrayList<>();
            for (T elem1 : elems)
                if (elem1.HashEquals(elem1.Hash(), hash))
                    result.add(elem1);
            return result;
        }

        @Override
        public abstract T clone();
    }

    /** Interface for cloning instance of type <T>. Use in Merger abstract class.
     * @param <T> type of instance
     */
    public interface Cloneable<T>
    {
        T clone();
    }
}
